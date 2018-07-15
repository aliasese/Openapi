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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "log", "approveClientKeyInfo" })
public class ApproveClientKeyData {

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
     * create approveClientKeyInfo.json Schema
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("approveClientKeyInfo")
    @Valid
    @NotNull
    private ApproveClientKeyInfo approveClientKeyInfo;

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

    public ApproveClientKeyData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * create approveClientKeyInfo.json Schema
     * <p>
     * 
     * (Required)
     * 
     * @return
     *     The approveClientKeyInfo
     */
    @JsonProperty("approveClientKeyInfo")
    public ApproveClientKeyInfo getApproveClientKeyInfo() {
        return approveClientKeyInfo;
    }

    /**
     * create approveClientKeyInfo.json Schema
     * <p>
     * 
     * (Required)
     * 
     * @param approveClientKeyInfo
     *     The approveClientKeyInfo
     */
    @JsonProperty("approveClientKeyInfo")
    public void setApproveClientKeyInfo(ApproveClientKeyInfo approveClientKeyInfo) {
        this.approveClientKeyInfo = approveClientKeyInfo;
    }

    public ApproveClientKeyData withApproveClientKeyInfo(ApproveClientKeyInfo approveClientKeyInfo) {
        this.approveClientKeyInfo = approveClientKeyInfo;
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

    public ApproveClientKeyData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
