/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

@Entity
@Table(name = "MXK_HISTORY_SYNCHRONIZER")  
public class HistorySynchronizer  extends JpaBaseEntity  implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1184644499009162756L;
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO,generator="snowflakeid")
    String id;
    @Column
    String syncId;
    
    @Column
    String sessionId;
    
    @Column
    String syncName;
    @Column
    String objectId;
    @Column
    String objectType;
    @Column
    String objectName;
    String syncTime;
    @Column
    String result;
	@Column
	private String instId;

	private String instName;
    String startDate;
    String endDate;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSyncId() {
        return syncId;
    }
    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }
    public String getSyncName() {
        return syncName;
    }
    public void setSyncName(String syncName) {
        this.syncName = syncName;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getObjectType() {
        return objectType;
    }
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getSyncTime() {
        return syncTime;
    }
    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public HistorySynchronizer() {
        super();
    }
    
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HistorySynchronizer [id=");
        builder.append(id);
        builder.append(", syncId=");
        builder.append(syncId);
        builder.append(", sessionId=");
        builder.append(sessionId);
        builder.append(", syncName=");
        builder.append(syncName);
        builder.append(", objectId=");
        builder.append(objectId);
        builder.append(", objectType=");
        builder.append(objectType);
        builder.append(", objectName=");
        builder.append(objectName);
        builder.append(", syncTime=");
        builder.append(syncTime);
        builder.append(", result=");
        builder.append(result);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append("]");
        return builder.toString();
    }
    
    
}
