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
@JsonPropertyOrder({ "log", "openapiDocPut" })
public class OpenapiDocDataPut {

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
     * create openapi response Schema
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("openapiDocPut")
    @Valid
    @NotNull
    private OpenapiDocPut openapiDocPut;

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

    public OpenapiDocDataPut withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * create openapi response Schema
     * <p>
     * 
     * (Required)
     * 
     * @return
     *     The openapiDocPut
     */
    @JsonProperty("openapiDocPut")
    public OpenapiDocPut getOpenapiDocPut() {
        return openapiDocPut;
    }

    /**
     * create openapi response Schema
     * <p>
     * 
     * (Required)
     * 
     * @param openapiDocPut
     *     The openapiDocPut
     */
    @JsonProperty("openapiDocPut")
    public void setOpenapiDocPut(OpenapiDocPut openapiDocPut) {
        this.openapiDocPut = openapiDocPut;
    }

    public OpenapiDocDataPut withOpenapiDocPut(OpenapiDocPut openapiDocPut) {
        this.openapiDocPut = openapiDocPut;
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

    public OpenapiDocDataPut withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
