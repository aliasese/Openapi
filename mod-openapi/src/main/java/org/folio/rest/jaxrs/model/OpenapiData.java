package org.folio.rest.jaxrs.model;

import java.util.HashMap;
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

/**
 * create openapi response Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "log", "openapiInfo" })
public class OpenapiData {

    /**
     * create openapi log Schema
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("log")
    @Valid
    @NotNull
    private Log log;

    /**
     * openApi Schema
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("openapiInfo")
    @Valid
    @NotNull
    private OpenapiInfo openapiInfo;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * create openapi log Schema
     * <p>
     * 
     * (Required)
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
     * (Required)
     * 
     * @param log
     *     The log
     */
    @JsonProperty("log")
    public void setLog(Log log) {
        this.log = log;
    }

    public OpenapiData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * openApi Schema
     * <p>
     * 
     * (Required)
     * 
     * @return
     *     The openapiInfo
     */
    @JsonProperty("openapiInfo")
    public OpenapiInfo getOpenapiInfo() {
        return openapiInfo;
    }

    /**
     * openApi Schema
     * <p>
     * 
     * (Required)
     * 
     * @param openapiInfo
     *     The openapiInfo
     */
    @JsonProperty("openapiInfo")
    public void setOpenapiInfo(OpenapiInfo openapiInfo) {
        this.openapiInfo = openapiInfo;
    }

    public OpenapiData withOpenapiInfo(OpenapiInfo openapiInfo) {
        this.openapiInfo = openapiInfo;
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

    public OpenapiData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
