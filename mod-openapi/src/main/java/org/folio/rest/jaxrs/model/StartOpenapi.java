package org.folio.rest.jaxrs.model;

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
@JsonPropertyOrder({ "log", "openapiIdArray" })
public class StartOpenapi {

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
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("openapiIdArray")
    @Valid
    private List<String> openapiIdArray = null;

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

    public StartOpenapi withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The openapiIdArray
     */
    @JsonProperty("openapiIdArray")
    public List<String> getOpenapiIdArray() {
        return openapiIdArray;
    }

    /**
     * 
     * (Required)
     * 
     * @param openapiIdArray
     *     The openapiIdArray
     */
    @JsonProperty("openapiIdArray")
    public void setOpenapiIdArray(List<String> openapiIdArray) {
        this.openapiIdArray = openapiIdArray;
    }

    public StartOpenapi withOpenapiIdArray(List<String> openapiIdArray) {
        this.openapiIdArray = openapiIdArray;
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

    public StartOpenapi withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
