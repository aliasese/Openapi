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
@JsonPropertyOrder({ "log", "openapiDoc" })
public class OpenapiDocData {

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
     * create openapi data Schema
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("openapiDoc")
    @Valid
    @NotNull
    private OpenapiDoc openapiDoc;

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

    public OpenapiDocData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * create openapi data Schema
     * <p>
     * 
     * (Required)
     * 
     * @return
     *     The openapiDoc
     */
    @JsonProperty("openapiDoc")
    public OpenapiDoc getOpenapiDoc() {
        return openapiDoc;
    }

    /**
     * create openapi data Schema
     * <p>
     * 
     * (Required)
     * 
     * @param openapiDoc
     *     The openapiDoc
     */
    @JsonProperty("openapiDoc")
    public void setOpenapiDoc(OpenapiDoc openapiDoc) {
        this.openapiDoc = openapiDoc;
    }

    public OpenapiDocData withOpenapiDoc(OpenapiDoc openapiDoc) {
        this.openapiDoc = openapiDoc;
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

    public OpenapiDocData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
