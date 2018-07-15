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
 * Authorize Openapi for Tenant Page query Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "apiName", "tenantName", "catalog", "authdatef", "authdatet", "pageNum", "pageRecordCount", "orderField", "orderType" })
public class AuthorizePageQueryCondition {

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
    @JsonProperty("catalog")
    private String catalog;

    /**
     * 
     */
    @JsonProperty("authdatef")
    private String authdatef;

    /**
     * 
     */
    @JsonProperty("authdatet")
    private String authdatet;

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

    public AuthorizePageQueryCondition withApiName(String apiName) {
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

    public AuthorizePageQueryCondition withTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    /**
     * 
     * @return
     *     The catalog
     */
    @JsonProperty("catalog")
    public String getCatalog() {
        return catalog;
    }

    /**
     * 
     * @param catalog
     *     The catalog
     */
    @JsonProperty("catalog")
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public AuthorizePageQueryCondition withCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    /**
     * 
     * @return
     *     The authdatef
     */
    @JsonProperty("authdatef")
    public String getAuthdatef() {
        return authdatef;
    }

    /**
     * 
     * @param authdatef
     *     The authdatef
     */
    @JsonProperty("authdatef")
    public void setAuthdatef(String authdatef) {
        this.authdatef = authdatef;
    }

    public AuthorizePageQueryCondition withAuthdatef(String authdatef) {
        this.authdatef = authdatef;
        return this;
    }

    /**
     * 
     * @return
     *     The authdatet
     */
    @JsonProperty("authdatet")
    public String getAuthdatet() {
        return authdatet;
    }

    /**
     * 
     * @param authdatet
     *     The authdatet
     */
    @JsonProperty("authdatet")
    public void setAuthdatet(String authdatet) {
        this.authdatet = authdatet;
    }

    public AuthorizePageQueryCondition withAuthdatet(String authdatet) {
        this.authdatet = authdatet;
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

    public AuthorizePageQueryCondition withPageNum(Integer pageNum) {
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

    public AuthorizePageQueryCondition withPageRecordCount(Integer pageRecordCount) {
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

    public AuthorizePageQueryCondition withOrderField(String orderField) {
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

    public AuthorizePageQueryCondition withOrderType(String orderType) {
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

    public AuthorizePageQueryCondition withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
