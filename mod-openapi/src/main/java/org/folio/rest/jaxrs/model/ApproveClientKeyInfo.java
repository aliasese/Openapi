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
 * create approveClientKeyInfo.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "approveMan", "appName", "appUrl", "clientSecret", "authModel", "approveDate", "expireDate" })
public class ApproveClientKeyInfo {

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
    @JsonProperty("approveMan")
    private String approveMan;

    /**
     * 
     */
    @JsonProperty("appName")
    private String appName;

    /**
     * 
     */
    @JsonProperty("appUrl")
    private String appUrl;

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

    public ApproveClientKeyInfo withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The approveMan
     */
    @JsonProperty("approveMan")
    public String getApproveMan() {
        return approveMan;
    }

    /**
     * 
     * (Required)
     * 
     * @param approveMan
     *     The approveMan
     */
    @JsonProperty("approveMan")
    public void setApproveMan(String approveMan) {
        this.approveMan = approveMan;
    }

    public ApproveClientKeyInfo withApproveMan(String approveMan) {
        this.approveMan = approveMan;
        return this;
    }

    /**
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
     * @param appName
     *     The appName
     */
    @JsonProperty("appName")
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ApproveClientKeyInfo withAppName(String appName) {
        this.appName = appName;
        return this;
    }

    /**
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
     * @param appUrl
     *     The appUrl
     */
    @JsonProperty("appUrl")
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public ApproveClientKeyInfo withAppUrl(String appUrl) {
        this.appUrl = appUrl;
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

    public ApproveClientKeyInfo withClientSecret(String clientSecret) {
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

    public ApproveClientKeyInfo withAuthModel(String authModel) {
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

    public ApproveClientKeyInfo withApproveDate(String approveDate) {
        this.approveDate = approveDate;
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

    public ApproveClientKeyInfo withExpireDate(String expireDate) {
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

    public ApproveClientKeyInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
