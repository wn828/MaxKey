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
 

/**
 * 
 */
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;


import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.entity.apps.Apps;
import org.maxkey.persistence.service.AppsCasDetailsService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "1-2认证总地址文档模块")
@Controller
public class AuthorizeEndpoint extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(AuthorizeEndpoint.class);
	
	@Autowired
	AppsCasDetailsService casDetailsService;
	
	//all single sign on url
	@Operation(summary = "认证总地址接口", description = "参数应用ID，分发到不同应用的认证地址",method="GET")
	@RequestMapping("/authz/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		ModelAndView modelAndView=null;
		Apps  app=getApp(id);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, app.getId());
		
		if(app.getProtocol().equalsIgnoreCase(ConstantsProtocols.EXTEND_API)){
			modelAndView=WebContext.forward("/authz/api/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.FORMBASED)){
			 modelAndView=WebContext.forward("/authz/formbased/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.OAUTH20)){
			 modelAndView=WebContext.forward("/authz/oauth/v20/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.OAUTH21)){
		    modelAndView=WebContext.redirect(app.getLoginUrl());
        }else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.OPEN_ID_CONNECT10)){
            modelAndView=WebContext.forward("/authz/oauth/v20/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.SAML20)){
			 modelAndView=WebContext.forward("/authz/saml20/idpinit/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.TOKENBASED)){
			modelAndView=WebContext.forward("/authz/tokenbased/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.CAS)){
			modelAndView=WebContext.forward("/authz/cas/"+app.getId());
		}else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.JWT)){
            modelAndView=WebContext.forward("/authz/jwt/"+app.getId());
        }else if (app.getProtocol().equalsIgnoreCase(ConstantsProtocols.BASIC)){
			modelAndView=WebContext.redirect(app.getLoginUrl());
		}
		
		_logger.debug(modelAndView.getViewName());
		
		return modelAndView;
	}
	
}
