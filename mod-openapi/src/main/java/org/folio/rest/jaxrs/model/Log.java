
package org.folio.rest.jaxrs.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * create openapi log Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "loginname",
    "tmname",
    "fmname",
    "smname"
})
public class Log {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("loginname")
    private String loginname;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("tmname")
    private String tmname;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("fmname")
    private String fmname;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("smname")
    private String smname;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The loginname
     */
    @JsonProperty("loginname")
    public String getLoginname() {
        return loginname;
    }

    /**
     * 
     * (Required)
     * 
     * @param loginname
     *     The loginname
     */
    @JsonProperty("loginname")
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public Log withLoginname(String loginname) {
        this.loginname = loginname;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tmname
     */
    @JsonProperty("tmname")
    public String getTmname() {
        return tmname;
    }

    /**
     * 
     * (Required)
     * 
     * @param tmname
     *     The tmname
     */
    @JsonProperty("tmname")
    public void setTmname(String tmname) {
        this.tmname = tmname;
    }

    public Log withTmname(String tmname) {
        this.tmname = tmname;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The fmname
     */
    @JsonProperty("fmname")
    public String getFmname() {
        return fmname;
    }

    /**
     * 
     * (Required)
     * 
     * @param fmname
     *     The fmname
     */
    @JsonProperty("fmname")
    public void setFmname(String fmname) {
        this.fmname = fmname;
    }

    public Log withFmname(String fmname) {
        this.fmname = fmname;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The smname
     */
    @JsonProperty("smname")
    public String getSmname() {
        return smname;
    }

    /**
     * 
     * (Required)
     * 
     * @param smname
     *     The smname
     */
    @JsonProperty("smname")
    public void setSmname(String smname) {
        this.smname = smname;
    }

    public Log withSmname(String smname) {
        this.smname = smname;
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

    public Log withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
