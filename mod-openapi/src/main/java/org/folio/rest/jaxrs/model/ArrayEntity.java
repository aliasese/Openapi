
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
 * create ArrayEntity.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "existIdArray",
    "notExistIdArray",
    "openapiInfo",
    "successIdArray",
    "failIdArray"
})
public class ArrayEntity {

    @JsonProperty("existIdArray")
    @Valid
    private List<String> existIdArray = new ArrayList<String>();
    @JsonProperty("notExistIdArray")
    @Valid
    private List<String> notExistIdArray = new ArrayList<String>();
    @JsonProperty("openapiInfo")
    @Valid
    private List<OpenapiInfo> openapiInfo = new ArrayList<OpenapiInfo>();
    @JsonProperty("successIdArray")
    @Valid
    private List<String> successIdArray = new ArrayList<String>();
    @JsonProperty("failIdArray")
    @Valid
    private List<String> failIdArray = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The existIdArray
     */
    @JsonProperty("existIdArray")
    public List<String> getExistIdArray() {
        return existIdArray;
    }

    /**
     * 
     * @param existIdArray
     *     The existIdArray
     */
    @JsonProperty("existIdArray")
    public void setExistIdArray(List<String> existIdArray) {
        this.existIdArray = existIdArray;
    }

    public ArrayEntity withExistIdArray(List<String> existIdArray) {
        this.existIdArray = existIdArray;
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

    public ArrayEntity withNotExistIdArray(List<String> notExistIdArray) {
        this.notExistIdArray = notExistIdArray;
        return this;
    }

    /**
     * 
     * @return
     *     The openapiInfo
     */
    @JsonProperty("openapiInfo")
    public List<OpenapiInfo> getOpenapiInfo() {
        return openapiInfo;
    }

    /**
     * 
     * @param openapiInfo
     *     The openapiInfo
     */
    @JsonProperty("openapiInfo")
    public void setOpenapiInfo(List<OpenapiInfo> openapiInfo) {
        this.openapiInfo = openapiInfo;
    }

    public ArrayEntity withOpenapiInfo(List<OpenapiInfo> openapiInfo) {
        this.openapiInfo = openapiInfo;
        return this;
    }

    /**
     * 
     * @return
     *     The successIdArray
     */
    @JsonProperty("successIdArray")
    public List<String> getSuccessIdArray() {
        return successIdArray;
    }

    /**
     * 
     * @param successIdArray
     *     The successIdArray
     */
    @JsonProperty("successIdArray")
    public void setSuccessIdArray(List<String> successIdArray) {
        this.successIdArray = successIdArray;
    }

    public ArrayEntity withSuccessIdArray(List<String> successIdArray) {
        this.successIdArray = successIdArray;
        return this;
    }

    /**
     * 
     * @return
     *     The failIdArray
     */
    @JsonProperty("failIdArray")
    public List<String> getFailIdArray() {
        return failIdArray;
    }

    /**
     * 
     * @param failIdArray
     *     The failIdArray
     */
    @JsonProperty("failIdArray")
    public void setFailIdArray(List<String> failIdArray) {
        this.failIdArray = failIdArray;
    }

    public ArrayEntity withFailIdArray(List<String> failIdArray) {
        this.failIdArray = failIdArray;
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

    public ArrayEntity withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
