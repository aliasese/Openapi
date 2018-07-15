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

/**
 * create approveData.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "log", "idArray" })
public class ApproveData {

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
    @JsonProperty("idArray")
    @Valid
    private List<String> idArray = null;

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

    public ApproveData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The idArray
     */
    @JsonProperty("idArray")
    public List<String> getIdArray() {
        return idArray;
    }

    /**
     * 
     * (Required)
     * 
     * @param idArray
     *     The idArray
     */
    @JsonProperty("idArray")
    public void setIdArray(List<String> idArray) {
        this.idArray = idArray;
    }

    public ApproveData withIdArray(List<String> idArray) {
        this.idArray = idArray;
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

    public ApproveData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
