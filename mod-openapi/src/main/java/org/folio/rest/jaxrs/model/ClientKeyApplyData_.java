package org.folio.rest.jaxrs.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * create clientKeyApply.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "tenantID", "tenantName", "appName", "clientId", "appUrl", "applyMan", "applyManEmail", "applyManPhone", "applyDate", "status" })
public class ClientKeyApplyData_ {

    /**
     * 
     */
    @JsonProperty("id")
    private String id;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("tenantID")
    private String tenantID;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("tenantName")
    private String tenantName;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("appName")
    private String appName;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("clientId")
    private String clientId;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("appUrl")
    private String appUrl;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyMan")
    private String applyMan;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyManEmail")
    private String applyManEmail;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyManPhone")
    private String applyManPhone;

    /**
     * 
     */
    @JsonProperty("applyDate")
    private String applyDate;

    /**
     * 
     */
    @JsonProperty("status")
    private String status;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ClientKeyApplyData_ withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tenantID
     */
    @JsonProperty("tenantID")
    public String getTenantID() {
        return tenantID;
    }

    /**
     * 
     * (Required)
     * 
     * @param tenantID
     *     The tenantID
     */
    @JsonProperty("tenantID")
    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public ClientKeyApplyData_ withTenantID(String tenantID) {
        this.tenantID = tenantID;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tenantName
     */
    @JsonProperty("tenantName")
    public String getTenantName() {
        return tenantName;
    }

    /**
     * 
     * (Required)
     * 
     * @param tenantName
     *     The tenantName
     */
    @JsonProperty("tenantName")
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public ClientKeyApplyData_ withTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The appName
     */
    @JsonProperty("appName")
    public String getAppName() {
        return appName;
    }

    /**
     * 
     * (Required)
     * 
     * @param appName
     *     The appName
     */
    @JsonProperty("appName")
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ClientKeyApplyData_ withAppName(String appName) {
        this.appName = appName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The clientId
     */
    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    /**
     * 
     * (Required)
     * 
     * @param clientId
     *     The clientId
     */
    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientKeyApplyData_ withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The appUrl
     */
    @JsonProperty("appUrl")
    public String getAppUrl() {
        return appUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param appUrl
     *     The appUrl
     */
    @JsonProperty("appUrl")
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public ClientKeyApplyData_ withAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyMan
     */
    @JsonProperty("applyMan")
    public String getApplyMan() {
        return applyMan;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyMan
     *     The applyMan
     */
    @JsonProperty("applyMan")
    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }

    public ClientKeyApplyData_ withApplyMan(String applyMan) {
        this.applyMan = applyMan;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyManEmail
     */
    @JsonProperty("applyManEmail")
    public String getApplyManEmail() {
        return applyManEmail;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyManEmail
     *     The applyManEmail
     */
    @JsonProperty("applyManEmail")
    public void setApplyManEmail(String applyManEmail) {
        this.applyManEmail = applyManEmail;
    }

    public ClientKeyApplyData_ withApplyManEmail(String applyManEmail) {
        this.applyManEmail = applyManEmail;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyManPhone
     */
    @JsonProperty("applyManPhone")
    public String getApplyManPhone() {
        return applyManPhone;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyManPhone
     *     The applyManPhone
     */
    @JsonProperty("applyManPhone")
    public void setApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
    }

    public ClientKeyApplyData_ withApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
        return this;
    }

    /**
     * 
     * @return
     *     The applyDate
     */
    @JsonProperty("applyDate")
    public String getApplyDate() {
        return applyDate;
    }

    /**
     * 
     * @param applyDate
     *     The applyDate
     */
    @JsonProperty("applyDate")
    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public ClientKeyApplyData_ withApplyDate(String applyDate) {
        this.applyDate = applyDate;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public ClientKeyApplyData_ withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ClientKeyApplyData_ withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
