package org.folio.rest.jaxrs.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * clientKeyMonitorPageQueryCondition.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "tenantName", "clientSecret", "appName", "authModel", "pageNum", "pageRecordCount", "orderField", "orderType" })
public class ClientKeyMonitorPageQueryCondition {

    /**
     * 
     */
    @JsonProperty("tenantName")
    private String tenantName;

    /**
     * 
     */
    @JsonProperty("clientSecret")
    private String clientSecret;

    /**
     * 
     */
    @JsonProperty("appName")
    private String appName;

    /**
     * 
     */
    @JsonProperty("authModel")
    private String authModel;

    /**
     * 
     */
    @JsonProperty("pageNum")
    private Integer pageNum;

    /**
     * 
     */
    @JsonProperty("pageRecordCount")
    private Integer pageRecordCount;

    /**
     * 
     */
    @JsonProperty("orderField")
    private String orderField;

    /**
     * 
     */
    @JsonProperty("orderType")
    private String orderType;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
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
     * @param tenantName
     *     The tenantName
     */
    @JsonProperty("tenantName")
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public ClientKeyMonitorPageQueryCondition withTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    /**
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
     * @param clientSecret
     *     The clientSecret
     */
    @JsonProperty("clientSecret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public ClientKeyMonitorPageQueryCondition withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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

    public ClientKeyMonitorPageQueryCondition withAppName(String appName) {
        this.appName = appName;
        return this;
    }

    /**
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
     * @param authModel
     *     The authModel
     */
    @JsonProperty("authModel")
    public void setAuthModel(String authModel) {
        this.authModel = authModel;
    }

    public ClientKeyMonitorPageQueryCondition withAuthModel(String authModel) {
        this.authModel = authModel;
        return this;
    }

    /**
     * 
     * @return
     *     The pageNum
     */
    @JsonProperty("pageNum")
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * 
     * @param pageNum
     *     The pageNum
     */
    @JsonProperty("pageNum")
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public ClientKeyMonitorPageQueryCondition withPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    /**
     * 
     * @return
     *     The pageRecordCount
     */
    @JsonProperty("pageRecordCount")
    public Integer getPageRecordCount() {
        return pageRecordCount;
    }

    /**
     * 
     * @param pageRecordCount
     *     The pageRecordCount
     */
    @JsonProperty("pageRecordCount")
    public void setPageRecordCount(Integer pageRecordCount) {
        this.pageRecordCount = pageRecordCount;
    }

    public ClientKeyMonitorPageQueryCondition withPageRecordCount(Integer pageRecordCount) {
        this.pageRecordCount = pageRecordCount;
        return this;
    }

    /**
     * 
     * @return
     *     The orderField
     */
    @JsonProperty("orderField")
    public String getOrderField() {
        return orderField;
    }

    /**
     * 
     * @param orderField
     *     The orderField
     */
    @JsonProperty("orderField")
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public ClientKeyMonitorPageQueryCondition withOrderField(String orderField) {
        this.orderField = orderField;
        return this;
    }

    /**
     * 
     * @return
     *     The orderType
     */
    @JsonProperty("orderType")
    public String getOrderType() {
        return orderType;
    }

    /**
     * 
     * @param orderType
     *     The orderType
     */
    @JsonProperty("orderType")
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public ClientKeyMonitorPageQueryCondition withOrderType(String orderType) {
        this.orderType = orderType;
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

    public ClientKeyMonitorPageQueryCondition withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
