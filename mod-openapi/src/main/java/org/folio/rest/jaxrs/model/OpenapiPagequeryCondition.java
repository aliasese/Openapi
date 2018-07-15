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
 * Openapi Credentials Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "name", "summary", "status", "regdatef", "regdatet", "pageNum", "pageRecordCount", "orderField", "orderType" })
public class OpenapiPagequeryCondition {

    /**
     * 
     */
    @JsonProperty("name")
    private String name;

    /**
     * 
     */
    @JsonProperty("summary")
    private String summary;

    /**
     * 
     */
    @JsonProperty("status")
    private String status;

    /**
     * 
     */
    @JsonProperty("regdatef")
    private String regdatef;

    /**
     * 
     */
    @JsonProperty("regdatet")
    private String regdatet;

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
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public OpenapiPagequeryCondition withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public OpenapiPagequeryCondition withSummary(String summary) {
        this.summary = summary;
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

    public OpenapiPagequeryCondition withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 
     * @return
     *     The regdatef
     */
    @JsonProperty("regdatef")
    public String getRegdatef() {
        return regdatef;
    }

    /**
     * 
     * @param regdatef
     *     The regdatef
     */
    @JsonProperty("regdatef")
    public void setRegdatef(String regdatef) {
        this.regdatef = regdatef;
    }

    public OpenapiPagequeryCondition withRegdatef(String regdatef) {
        this.regdatef = regdatef;
        return this;
    }

    /**
     * 
     * @return
     *     The regdatet
     */
    @JsonProperty("regdatet")
    public String getRegdatet() {
        return regdatet;
    }

    /**
     * 
     * @param regdatet
     *     The regdatet
     */
    @JsonProperty("regdatet")
    public void setRegdatet(String regdatet) {
        this.regdatet = regdatet;
    }

    public OpenapiPagequeryCondition withRegdatet(String regdatet) {
        this.regdatet = regdatet;
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

    public OpenapiPagequeryCondition withPageNum(Integer pageNum) {
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

    public OpenapiPagequeryCondition withPageRecordCount(Integer pageRecordCount) {
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

    public OpenapiPagequeryCondition withOrderField(String orderField) {
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

    public OpenapiPagequeryCondition withOrderType(String orderType) {
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

    public OpenapiPagequeryCondition withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
