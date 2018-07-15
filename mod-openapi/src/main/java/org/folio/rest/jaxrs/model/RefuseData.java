
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
@JsonPropertyOrder({
    "log",
    "reason",
    "refuseMan"
})
public class RefuseData {

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
     * 
     * (Required)
     * 
     */
    @JsonProperty("reason")
    @NotNull
    private String reason;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("refuseMan")
    @NotNull
    private String refuseMan;
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

    public RefuseData withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The reason
     */
    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    /**
     * 
     * (Required)
     * 
     * @param reason
     *     The reason
     */
    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    public RefuseData withReason(String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The refuseMan
     */
    @JsonProperty("refuseMan")
    public String getRefuseMan() {
        return refuseMan;
    }

    /**
     * 
     * (Required)
     * 
     * @param refuseMan
     *     The refuseMan
     */
    @JsonProperty("refuseMan")
    public void setRefuseMan(String refuseMan) {
        this.refuseMan = refuseMan;
    }

    public RefuseData withRefuseMan(String refuseMan) {
        this.refuseMan = refuseMan;
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

    public RefuseData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
