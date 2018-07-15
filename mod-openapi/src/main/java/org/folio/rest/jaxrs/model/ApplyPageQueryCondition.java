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
 * Process Tenant's Apply Page query Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "apiName", "tenantName", "status", "applydatef", "applydatet", "pageNum", "pageRecordCount", "orderField", "orderType" })
public class ApplyPageQueryCondition {

    /**
     * 
     */
    @JsonProperty("apiName")
    private String apiName;

    /**
     * 
     */
    @JsonProperty("tenantName")
    private String tenantName;

    /**
     * 
     */
    @JsonProperty("status")
    private String status;

    /**
     * 
     */
    @JsonProperty("applydatef")
    private String applydatef;

    /**
     * 
     */
    @JsonProperty("applydatet")
    private String applydatet;

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
     *     The apiName
     */
    @JsonProperty("apiName")
    public String getApiName() {
        return apiName;
    }

    /**
     * 
     * @param apiName
     *     The apiName
     */
    @JsonProperty("apiName")
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public ApplyPageQueryCondition withApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

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

    public ApplyPageQueryCondition withTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    public ApplyPageQueryCondition withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 
     * @return
     *     The applydatef
     */
    @JsonProperty("applydatef")
    public String getApplydatef() {
        return applydatef;
    }

    /**
     * 
     * @param applydatef
     *     The applydatef
     */
    @JsonProperty("applydatef")
    public void setApplydatef(String applydatef) {
        this.applydatef = applydatef;
    }

    public ApplyPageQueryCondition withApplydatef(String applydatef) {
        this.applydatef = applydatef;
        return this;
    }

    /**
     * 
     * @return
     *     The applydatet
     */
    @JsonProperty("applydatet")
    public String getApplydatet() {
        return applydatet;
    }

    /**
     * 
     * @param applydatet
     *     The applydatet
     */
    @JsonProperty("applydatet")
    public void setApplydatet(String applydatet) {
        this.applydatet = applydatet;
    }

    public ApplyPageQueryCondition withApplydatet(String applydatet) {
        this.applydatet = applydatet;
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

    public ApplyPageQueryCondition withPageNum(Integer pageNum) {
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

    public ApplyPageQueryCondition withPageRecordCount(Integer pageRecordCount) {
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

    public ApplyPageQueryCondition withOrderField(String orderField) {
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

    public ApplyPageQueryCondition withOrderType(String orderType) {
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

    public ApplyPageQueryCondition withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
