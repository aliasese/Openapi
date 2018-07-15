package org.folio.rest.jaxrs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "openapiArray", "tenantName", "applyDate", "status" })
public class ApplyPageQueryResp {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("id")
    private String id;

    @JsonProperty("openapiArray")
    @Valid
    private List<String> openapiArray = new ArrayList<String>();

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

    public ApplyPageQueryResp withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The openapiArray
     */
    @JsonProperty("openapiArray")
    public List<String> getOpenapiArray() {
        return openapiArray;
    }

    /**
     * 
     * @param openapiArray
     *     The openapiArray
     */
    @JsonProperty("openapiArray")
    public void setOpenapiArray(List<String> openapiArray) {
        this.openapiArray = openapiArray;
    }

    public ApplyPageQueryResp withOpenapiArray(List<String> openapiArray) {
        this.openapiArray = openapiArray;
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

    public ApplyPageQueryResp withTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    public ApplyPageQueryResp withApplyDate(String applyDate) {
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

    public ApplyPageQueryResp withStatus(String status) {
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

    public ApplyPageQueryResp withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
