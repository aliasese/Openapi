package org.folio.rest.jaxrs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * create Tenant Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "openapiArray" })
public class Tenant {

    /**
     * 
     */
    @JsonProperty("id")
    private String id;

    @JsonProperty("openapiArray")
    @Valid
    private List<String> openapiArray = new ArrayList<String>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Tenant withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The openapiArray
     */
    @JsonProperty("openapiArray")
    public List<String> getOpenapiArray() {
        return openapiArray;
    }

    /**
     * 
     * @param openapiArray
     *     The openapiArray
     */
    @JsonProperty("openapiArray")
    public void setOpenapiArray(List<String> openapiArray) {
        this.openapiArray = openapiArray;
    }

    public Tenant withOpenapiArray(List<String> openapiArray) {
        this.openapiArray = openapiArray;
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

    public Tenant withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
