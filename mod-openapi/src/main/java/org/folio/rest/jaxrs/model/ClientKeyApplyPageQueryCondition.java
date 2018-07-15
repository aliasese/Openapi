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
 * clientKeyApplyPageQueryCondition.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "tenantName", "applyDatef", "applyDatet", "pageNum", "pageRecordCount", "orderField", "orderType" })
public class ClientKeyApplyPageQueryCondition {

    /**
     * 
     */
    @JsonProperty("tenantName")
    private String tenantName;

    /**
     * 
     */
    @JsonProperty("applyDatef")
    private String applyDatef;

    /**
     * 
     */
    @JsonProperty("applyDatet")
    private String applyDatet;

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

    public ClientKeyApplyPageQueryCondition withTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    /**
     * 
     * @return
     *     The applyDatef
     */
    @JsonProperty("applyDatef")
    public String getApplyDatef() {
        return applyDatef;
    }

    /**
     * 
     * @param applyDatef
     *     The applyDatef
     */
    @JsonProperty("applyDatef")
    public void setApplyDatef(String applyDatef) {
        this.applyDatef = applyDatef;
    }

    public ClientKeyApplyPageQueryCondition withApplyDatef(String applyDatef) {
        this.applyDatef = applyDatef;
        return this;
    }

    /**
     * 
     * @return
     *     The applyDatet
     */
    @JsonProperty("applyDatet")
    public String getApplyDatet() {
        return applyDatet;
    }

    /**
     * 
     * @param applyDatet
     *     The applyDatet
     */
    @JsonProperty("applyDatet")
    public void setApplyDatet(String applyDatet) {
        this.applyDatet = applyDatet;
    }

    public ClientKeyApplyPageQueryCondition withApplyDatet(String applyDatet) {
        this.applyDatet = applyDatet;
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

    public ClientKeyApplyPageQueryCondition withPageNum(Integer pageNum) {
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

    public ClientKeyApplyPageQueryCondition withPageRecordCount(Integer pageRecordCount) {
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

    public ClientKeyApplyPageQueryCondition withOrderField(String orderField) {
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

    public ClientKeyApplyPageQueryCondition withOrderType(String orderType) {
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

    public ClientKeyApplyPageQueryCondition withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
