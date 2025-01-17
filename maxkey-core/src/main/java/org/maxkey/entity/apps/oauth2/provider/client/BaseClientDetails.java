/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.entity.apps.oauth2.provider.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

/**
 * Base implementation of
 * {@link org.maxkey.entity.apps.oauth2.provider.ClientDetails}.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
@SuppressWarnings("serial")
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class BaseClientDetails implements ClientDetails {

	@com.fasterxml.jackson.annotation.JsonProperty("client_id")
	private String clientId;

	@com.fasterxml.jackson.annotation.JsonProperty("client_secret")
	private String clientSecret;

	@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> scope = Collections.emptySet();

	@com.fasterxml.jackson.annotation.JsonProperty("resource_ids")
	@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> resourceIds = Collections.emptySet();

	@com.fasterxml.jackson.annotation.JsonProperty("authorized_grant_types")
	@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> authorizedGrantTypes = Collections.emptySet();

	@com.fasterxml.jackson.annotation.JsonProperty("redirect_uri")
	@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> registeredRedirectUris;

	@com.fasterxml.jackson.annotation.JsonProperty("autoapprove")
	@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> autoApproveScopes;

	private List<GrantedAuthority> authorities = Collections.emptyList();

	@com.fasterxml.jackson.annotation.JsonProperty("access_token_validity")
	private Integer accessTokenValiditySeconds;

	@com.fasterxml.jackson.annotation.JsonProperty("refresh_token_validity")
	private Integer refreshTokenValiditySeconds;

	@com.fasterxml.jackson.annotation.JsonIgnore
	private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

	//for OpenID Connect
	private String idTokenSigningAlgorithm;
	private String idTokenEncryptedAlgorithm;
	private String idTokenEncryptionMethod;

	private String userInfoSigningAlgorithm;
	private String userInfoEncryptedAlgorithm;
	private String userInfoEncryptionMethod;
	
	private String jwksUri;
	
	private String approvalPrompt;
	
	private String pkce;
	
	private String protocol;
	
	private String instId;
	
	
	public BaseClientDetails() {
	}

	public BaseClientDetails(ClientDetails prototype) {
		this();
		setAccessTokenValiditySeconds(prototype.getAccessTokenValiditySeconds());
		setRefreshTokenValiditySeconds(prototype
				.getRefreshTokenValiditySeconds());
		setAuthorities(prototype.getAuthorities());
		setAuthorizedGrantTypes(prototype.getAuthorizedGrantTypes());
		setClientId(prototype.getClientId());
		setClientSecret(prototype.getClientSecret());
		setRegisteredRedirectUri(prototype.getRegisteredRedirectUri());
		setScope(prototype.getScope());
		setResourceIds(prototype.getResourceIds());
	}

	public BaseClientDetails(String clientId, String resourceIds,
			String scopes, String grantTypes, String authorities) {
		this(clientId, resourceIds, scopes, grantTypes, authorities, null);
	}

	public BaseClientDetails(String clientId, String resourceIds,
			String scopes, String grantTypes, String authorities,
			String redirectUris) {

		this.clientId = clientId;

		if (StringUtils.hasText(resourceIds)) {
			Set<String> resources = StringUtils
					.commaDelimitedListToSet(resourceIds);
			if (!resources.isEmpty()) {
				this.resourceIds = resources;
			}
		}

		if (StringUtils.hasText(scopes)) {
			Set<String> scopeList = StringUtils.commaDelimitedListToSet(scopes);
			if (!scopeList.isEmpty()) {
				this.scope = scopeList;
			}
		}

		if (StringUtils.hasText(grantTypes)) {
			this.authorizedGrantTypes = StringUtils
					.commaDelimitedListToSet(grantTypes);
		} else {
			this.authorizedGrantTypes = new HashSet<String>(Arrays.asList(
					"authorization_code", "refresh_token"));
		}

		if (StringUtils.hasText(authorities)) {
			this.authorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList(authorities);
		}

		if (StringUtils.hasText(redirectUris)) {
			this.registeredRedirectUris = StringUtils
					.commaDelimitedListToSet(redirectUris);
		}
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setAutoApproveScopes(Collection<String> autoApproveScopes) {
		this.autoApproveScopes = new HashSet<String>(autoApproveScopes);
	}

	@Override
	public boolean isAutoApprove(String scope) {
		if (autoApproveScopes == null) {
			return false;
		}
		for (String auto : autoApproveScopes) {
			if (auto.equals("true") || scope.matches(auto)) {
				return true;
			}
		}
		return false;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Set<String> getAutoApproveScopes() {
		return autoApproveScopes;
	}
	
	@com.fasterxml.jackson.annotation.JsonIgnore
	public boolean isSecretRequired() {
		return this.clientSecret != null;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public boolean isScoped() {
		return this.scope != null && !this.scope.isEmpty();
	}

	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Collection<String> scope) {
		this.scope = scope == null ? Collections.<String> emptySet()
				: new LinkedHashSet<String>(scope);
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(Collection<String> resourceIds) {
		this.resourceIds = resourceIds == null ? Collections
				.<String> emptySet() : new LinkedHashSet<String>(resourceIds);
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(Collection<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = new LinkedHashSet<String>(
				authorizedGrantTypes);
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUris;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
		this.registeredRedirectUris = registeredRedirectUris == null ? null
				: new LinkedHashSet<String>(registeredRedirectUris);
	}

	@com.fasterxml.jackson.annotation.JsonProperty("authorities")
	private List<String> getAuthoritiesAsStrings() {
		return new ArrayList<String>(
				AuthorityUtils.authorityListToSet(authorities));
	}

	@com.fasterxml.jackson.annotation.JsonProperty("authorities")
	@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private void setAuthoritiesAsStrings(Set<String> values) {
		setAuthorities(AuthorityUtils.createAuthorityList(values
				.toArray(new String[values.size()])));
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public void setAuthorities(
			Collection<? extends GrantedAuthority> authorities) {
		this.authorities = new ArrayList<GrantedAuthority>(authorities);
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(
			Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public void setAdditionalInformation(Map<String, ?> additionalInformation) {
		this.additionalInformation = new LinkedHashMap<String, Object>(
				additionalInformation);
	}

	@com.fasterxml.jackson.annotation.JsonAnyGetter
	public Map<String, Object> getAdditionalInformation() {
		return Collections.unmodifiableMap(this.additionalInformation);
	}

	@com.fasterxml.jackson.annotation.JsonAnySetter
	public void addAdditionalInformation(String key, Object value) {
		this.additionalInformation.put(key, value);
	}
	
	

	public String getIdTokenEncryptedAlgorithm() {
		return idTokenEncryptedAlgorithm;
	}

	public void setIdTokenEncryptedAlgorithm(String idTokenEncryptedAlgorithm) {
		this.idTokenEncryptedAlgorithm = idTokenEncryptedAlgorithm;
	}

	public String getIdTokenSigningAlgorithm() {
		return idTokenSigningAlgorithm;
	}

	public void setIdTokenSigningAlgorithm(String idTokenSigningAlgorithm) {
		this.idTokenSigningAlgorithm = idTokenSigningAlgorithm;
	}

	public String getIdTokenEncryptionMethod() {
		return idTokenEncryptionMethod;
	}

	public void setIdTokenEncryptionMethod(String idTokenEncryptionMethod) {
		this.idTokenEncryptionMethod = idTokenEncryptionMethod;
	}

	public String getUserInfoSigningAlgorithm() {
		return userInfoSigningAlgorithm;
	}

	public void setUserInfoSigningAlgorithm(String userInfoSigningAlgorithm) {
		this.userInfoSigningAlgorithm = userInfoSigningAlgorithm;
	}

	public String getUserInfoEncryptedAlgorithm() {
		return userInfoEncryptedAlgorithm;
	}

	public void setUserInfoEncryptedAlgorithm(String userInfoEncryptedAlgorithm) {
		this.userInfoEncryptedAlgorithm = userInfoEncryptedAlgorithm;
	}

	public String getUserInfoEncryptionMethod() {
		return userInfoEncryptionMethod;
	}

	public void setUserInfoEncryptionMethod(String userInfoEncryptionMethod) {
		this.userInfoEncryptionMethod = userInfoEncryptionMethod;
	}

	public String getJwksUri() {
		return jwksUri;
	}

	public void setJwksUri(String jwksUri) {
		this.jwksUri = jwksUri;
	}

	
	public String getApprovalPrompt() {
        return approvalPrompt;
    }

    public void setApprovalPrompt(String approvalPrompt) {
        this.approvalPrompt = approvalPrompt;
    }
 
    public String getPkce() {
        return pkce;
    }

    public void setPkce(String pkce) {
        this.pkce = pkce;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

	@Override
	public String getInstId() {
		return this.instId;
	}
	
    public void setInstId(String instId) {
		this.instId = instId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accessTokenValiditySeconds == null) ? 0
						: accessTokenValiditySeconds);
		result = prime
				* result
				+ ((refreshTokenValiditySeconds == null) ? 0
						: refreshTokenValiditySeconds);
		result = prime * result
				+ ((authorities == null) ? 0 : authorities.hashCode());
		result = prime
				* result
				+ ((authorizedGrantTypes == null) ? 0 : authorizedGrantTypes
						.hashCode());
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result
				+ ((clientSecret == null) ? 0 : clientSecret.hashCode());
		result = prime
				* result
				+ ((registeredRedirectUris == null) ? 0
						: registeredRedirectUris.hashCode());
		result = prime * result
				+ ((resourceIds == null) ? 0 : resourceIds.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + additionalInformation.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseClientDetails other = (BaseClientDetails) obj;
		if (accessTokenValiditySeconds != other.accessTokenValiditySeconds)
			return false;
		if (refreshTokenValiditySeconds != other.refreshTokenValiditySeconds)
			return false;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (authorizedGrantTypes == null) {
			if (other.authorizedGrantTypes != null)
				return false;
		} else if (!authorizedGrantTypes.equals(other.authorizedGrantTypes))
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (clientSecret == null) {
			if (other.clientSecret != null)
				return false;
		} else if (!clientSecret.equals(other.clientSecret))
			return false;
		if (registeredRedirectUris == null) {
			if (other.registeredRedirectUris != null)
				return false;
		} else if (!registeredRedirectUris.equals(other.registeredRedirectUris))
			return false;
		if (resourceIds == null) {
			if (other.resourceIds != null)
				return false;
		} else if (!resourceIds.equals(other.resourceIds))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (additionalInformation == null) {
			if (other.additionalInformation != null)
				return false;
		} else if (!additionalInformation.equals(other.additionalInformation))
			return false;
		return true;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseClientDetails [clientId=");
        builder.append(clientId);
        builder.append(", clientSecret=");
        builder.append(clientSecret);
        builder.append(", scope=");
        builder.append(scope);
        builder.append(", resourceIds=");
        builder.append(resourceIds);
        builder.append(", authorizedGrantTypes=");
        builder.append(authorizedGrantTypes);
        builder.append(", registeredRedirectUris=");
        builder.append(registeredRedirectUris);
        builder.append(", autoApproveScopes=");
        builder.append(autoApproveScopes);
        builder.append(", authorities=");
        builder.append(authorities);
        builder.append(", accessTokenValiditySeconds=");
        builder.append(accessTokenValiditySeconds);
        builder.append(", refreshTokenValiditySeconds=");
        builder.append(refreshTokenValiditySeconds);
        builder.append(", additionalInformation=");
        builder.append(additionalInformation);
        builder.append(", idTokenSigningAlgorithm=");
        builder.append(idTokenSigningAlgorithm);
        builder.append(", idTokenEncryptedAlgorithm=");
        builder.append(idTokenEncryptedAlgorithm);
        builder.append(", idTokenEncryptionMethod=");
        builder.append(idTokenEncryptionMethod);
        builder.append(", userInfoSigningAlgorithm=");
        builder.append(userInfoSigningAlgorithm);
        builder.append(", userInfoEncryptedAlgorithm=");
        builder.append(userInfoEncryptedAlgorithm);
        builder.append(", userInfoEncryptionMethod=");
        builder.append(userInfoEncryptionMethod);
        builder.append(", jwksUri=");
        builder.append(jwksUri);
        builder.append(", approvalPrompt=");
        builder.append(approvalPrompt);
        builder.append("]");
        return builder.toString();
    }



}
