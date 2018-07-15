package org.folio.rest.jaxrs.model;

import java.util.ArrayList;
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
 * create DeleteResp.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "success", "notExistIdArray" })
public class DeleteResp {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("notExistIdArray")
    @Valid
    private List<String> notExistIdArray = new ArrayList<String>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * (Required)
     * 
     * @param success
     *     The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DeleteResp withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * 
     * @return
     *     The notExistIdArray
     */
    @JsonProperty("notExistIdArray")
    public List<String> getNotExistIdArray() {
        return notExistIdArray;
    }

    /**
     * 
     * @param notExistIdArray
     *     The notExistIdArray
     */
    @JsonProperty("notExistIdArray")
    public void setNotExistIdArray(List<String> notExistIdArray) {
        this.notExistIdArray = notExistIdArray;
    }

    public DeleteResp withNotExistIdArray(List<String> notExistIdArray) {
        this.notExistIdArray = notExistIdArray;
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

    public DeleteResp withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
