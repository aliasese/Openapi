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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "cpname", "type", "require", "description" })
public class CommonParam {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("cpname")
    private String cpname;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private String type;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("require")
    private String require;

    /**
     * 
     */
    @JsonProperty("description")
    private String description;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The cpname
     */
    @JsonProperty("cpname")
    public String getCpname() {
        return cpname;
    }

    /**
     * 
     * (Required)
     * 
     * @param cpname
     *     The cpname
     */
    @JsonProperty("cpname")
    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public CommonParam withCpname(String cpname) {
        this.cpname = cpname;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public CommonParam withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The require
     */
    @JsonProperty("require")
    public String getRequire() {
        return require;
    }

    /**
     * 
     * (Required)
     * 
     * @param require
     *     The require
     */
    @JsonProperty("require")
    public void setRequire(String require) {
        this.require = require;
    }

    public CommonParam withRequire(String require) {
        this.require = require;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public CommonParam withDescription(String description) {
        this.description = description;
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

    public CommonParam withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
