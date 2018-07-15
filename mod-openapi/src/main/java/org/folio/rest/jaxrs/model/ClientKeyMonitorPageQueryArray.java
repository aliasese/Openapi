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
 * create clientKeyMonitorPageQueryResp.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "tenantID", "tenantName", "appName", "clientSecret", "expireDate", "status" })
public class ClientKeyMonitorPageQueryArray {

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
    @JsonProperty("clientSecret")
    private String clientSecret;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("expireDate")
    private String expireDate;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
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

    public ClientKeyMonitorPageQueryArray withId(String id) {
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

    public ClientKeyMonitorPageQueryArray withTenantID(String tenantID) {
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

    public ClientKeyMonitorPageQueryArray withTenantName(String tenantName) {
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

    public ClientKeyMonitorPageQueryArray withAppName(String appName) {
        this.appName = appName;
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

    public ClientKeyMonitorPageQueryArray withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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

    public ClientKeyMonitorPageQueryArray withExpireDate(String expireDate) {
        this.expireDate = expireDate;
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

    public ClientKeyMonitorPageQueryArray withStatus(String status) {
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

    public ClientKeyMonitorPageQueryArray withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
