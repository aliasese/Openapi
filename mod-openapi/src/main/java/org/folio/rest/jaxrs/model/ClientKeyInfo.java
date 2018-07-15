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
 * create clientKeyInfo.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "tenantID", "tenantName", "appName", "appUrl", "applyMan", "applyManEmail", "applyManPhone", "applyDate", "status", "clientId", "clientSecret", "authModel", "approveDate", "refuseDate", "startDate", "refuseReason", "startReason", "expireDate" })
public class ClientKeyInfo {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
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
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyDate")
    private String applyDate;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("status")
    private String status;

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
    @JsonProperty("clientSecret")
    private String clientSecret;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("authModel")
    private String authModel;

    /**
     * 
     */
    @JsonProperty("approveDate")
    private String approveDate;

    /**
     * 
     */
    @JsonProperty("refuseDate")
    private String refuseDate;

    /**
     * 
     */
    @JsonProperty("startDate")
    private String startDate;

    /**
     * 
     */
    @JsonProperty("refuseReason")
    private String refuseReason;

    /**
     * 
     */
    @JsonProperty("startReason")
    private String startReason;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("expireDate")
    private String expireDate;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ClientKeyInfo withId(String id) {
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

    public ClientKeyInfo withTenantID(String tenantID) {
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

    public ClientKeyInfo withTenantName(String tenantName) {
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

    public ClientKeyInfo withAppName(String appName) {
        this.appName = appName;
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

    public ClientKeyInfo withAppUrl(String appUrl) {
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

    public ClientKeyInfo withApplyMan(String applyMan) {
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

    public ClientKeyInfo withApplyManEmail(String applyManEmail) {
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

    public ClientKeyInfo withApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param applyDate
     *     The applyDate
     */
    @JsonProperty("applyDate")
    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public ClientKeyInfo withApplyDate(String applyDate) {
        this.applyDate = applyDate;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public ClientKeyInfo withStatus(String status) {
        this.status = status;
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

    public ClientKeyInfo withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The clientSecret
     */
    @JsonProperty("clientSecret")
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 
     * (Required)
     * 
     * @param clientSecret
     *     The clientSecret
     */
    @JsonProperty("clientSecret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public ClientKeyInfo withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The authModel
     */
    @JsonProperty("authModel")
    public String getAuthModel() {
        return authModel;
    }

    /**
     * 
     * (Required)
     * 
     * @param authModel
     *     The authModel
     */
    @JsonProperty("authModel")
    public void setAuthModel(String authModel) {
        this.authModel = authModel;
    }

    public ClientKeyInfo withAuthModel(String authModel) {
        this.authModel = authModel;
        return this;
    }

    /**
     * 
     * @return
     *     The approveDate
     */
    @JsonProperty("approveDate")
    public String getApproveDate() {
        return approveDate;
    }

    /**
     * 
     * @param approveDate
     *     The approveDate
     */
    @JsonProperty("approveDate")
    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public ClientKeyInfo withApproveDate(String approveDate) {
        this.approveDate = approveDate;
        return this;
    }

    /**
     * 
     * @return
     *     The refuseDate
     */
    @JsonProperty("refuseDate")
    public String getRefuseDate() {
        return refuseDate;
    }

    /**
     * 
     * @param refuseDate
     *     The refuseDate
     */
    @JsonProperty("refuseDate")
    public void setRefuseDate(String refuseDate) {
        this.refuseDate = refuseDate;
    }

    public ClientKeyInfo withRefuseDate(String refuseDate) {
        this.refuseDate = refuseDate;
        return this;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The startDate
     */
    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ClientKeyInfo withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * 
     * @return
     *     The refuseReason
     */
    @JsonProperty("refuseReason")
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * 
     * @param refuseReason
     *     The refuseReason
     */
    @JsonProperty("refuseReason")
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public ClientKeyInfo withRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
        return this;
    }

    /**
     * 
     * @return
     *     The startReason
     */
    @JsonProperty("startReason")
    public String getStartReason() {
        return startReason;
    }

    /**
     * 
     * @param startReason
     *     The startReason
     */
    @JsonProperty("startReason")
    public void setStartReason(String startReason) {
        this.startReason = startReason;
    }

    public ClientKeyInfo withStartReason(String startReason) {
        this.startReason = startReason;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The expireDate
     */
    @JsonProperty("expireDate")
    public String getExpireDate() {
        return expireDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param expireDate
     *     The expireDate
     */
    @JsonProperty("expireDate")
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public ClientKeyInfo withExpireDate(String expireDate) {
        this.expireDate = expireDate;
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

    public ClientKeyInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
