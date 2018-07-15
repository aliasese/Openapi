package org.folio.rest.jaxrs.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * create authorize openapi for tenant Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "log", "openapiApplyData" })
public class ApplyData {

    /**
     * create openapi log Schema
     * <p>
     * 
     * 
     */
    @JsonProperty("log")
    @Valid
    private Log log;

    /**
     * create authorize APIs for tenants Schema
     * <p>
     * 
     * 
     */
    @JsonProperty("openapiApplyData")
    @Valid
    private OpenapiApplyData openapiApplyData;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * create openapi log Schema
     * <p>
     * 
     * 
     * @return
     *     The log
     */
    @JsonProperty("log")
    public Log getLog() {
        return log;
    }

    /**
     * create openapi log Schema
     * <p>
     * 
     * 
     * @param log
     *     The log
     */
    @JsonProperty("log")
    public void setLog(Log log) {
        this.log = log;
    }

    public ApplyData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * create authorize APIs for tenants Schema
     * <p>
     * 
     * 
     * @return
     *     The openapiApplyData
     */
    @JsonProperty("openapiApplyData")
    public OpenapiApplyData getOpenapiApplyData() {
        return openapiApplyData;
    }

    /**
     * create authorize APIs for tenants Schema
     * <p>
     * 
     * 
     * @param openapiApplyData
     *     The openapiApplyData
     */
    @JsonProperty("openapiApplyData")
    public void setOpenapiApplyData(OpenapiApplyData openapiApplyData) {
        this.openapiApplyData = openapiApplyData;
    }

    public ApplyData withOpenapiApplyData(OpenapiApplyData openapiApplyData) {
        this.openapiApplyData = openapiApplyData;
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

    public ApplyData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
