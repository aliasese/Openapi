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
 * create clientKeyApplyData.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "log", "clientKeyApplyData" })
public class ClientKeyApplyData {

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
     * create clientKeyApply.json Schema
     * <p>
     * 
     * 
     */
    @JsonProperty("clientKeyApplyData")
    @Valid
    private ClientKeyApplyData_ clientKeyApplyData;

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

    public ClientKeyApplyData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * create clientKeyApply.json Schema
     * <p>
     * 
     * 
     * @return
     *     The clientKeyApplyData
     */
    @JsonProperty("clientKeyApplyData")
    public ClientKeyApplyData_ getClientKeyApplyData() {
        return clientKeyApplyData;
    }

    /**
     * create clientKeyApply.json Schema
     * <p>
     * 
     * 
     * @param clientKeyApplyData
     *     The clientKeyApplyData
     */
    @JsonProperty("clientKeyApplyData")
    public void setClientKeyApplyData(ClientKeyApplyData_ clientKeyApplyData) {
        this.clientKeyApplyData = clientKeyApplyData;
    }

    public ClientKeyApplyData withClientKeyApplyData(ClientKeyApplyData_ clientKeyApplyData) {
        this.clientKeyApplyData = clientKeyApplyData;
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

    public ClientKeyApplyData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
