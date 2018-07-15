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
 * create authApplyDataLog.json response Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "log", "authApplyData" })
public class AuthApplyDataLog {

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
     * authApplyData.json Schema
     * <p>
     * 
     * 
     */
    @JsonProperty("authApplyData")
    @Valid
    private AuthApplyData authApplyData;

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

    public AuthApplyDataLog withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * authApplyData.json Schema
     * <p>
     * 
     * 
     * @return
     *     The authApplyData
     */
    @JsonProperty("authApplyData")
    public AuthApplyData getAuthApplyData() {
        return authApplyData;
    }

    /**
     * authApplyData.json Schema
     * <p>
     * 
     * 
     * @param authApplyData
     *     The authApplyData
     */
    @JsonProperty("authApplyData")
    public void setAuthApplyData(AuthApplyData authApplyData) {
        this.authApplyData = authApplyData;
    }

    public AuthApplyDataLog withAuthApplyData(AuthApplyData authApplyData) {
        this.authApplyData = authApplyData;
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

    public AuthApplyDataLog withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
