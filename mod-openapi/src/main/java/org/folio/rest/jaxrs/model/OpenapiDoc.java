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
 * create openapi data Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "category", "title", "context", "createTime", "updateTime", "status" })
public class OpenapiDoc {

    /**
     * 
     */
    @JsonProperty("id")
    private String id;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("category")
    private String category;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("title")
    private String title;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("context")
    private String context;

    /**
     * 
     */
    @JsonProperty("createTime")
    private String createTime;

    /**
     * 
     */
    @JsonProperty("updateTime")
    private String updateTime;

    /**
     * 
     */
    @JsonProperty("status")
    private String status;

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

    public OpenapiDoc withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * 
     * (Required)
     * 
     * @param category
     *     The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public OpenapiDoc withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * (Required)
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public OpenapiDoc withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The context
     */
    @JsonProperty("context")
    public String getContext() {
        return context;
    }

    /**
     * 
     * (Required)
     * 
     * @param context
     *     The context
     */
    @JsonProperty("context")
    public void setContext(String context) {
        this.context = context;
    }

    public OpenapiDoc withContext(String context) {
        this.context = context;
        return this;
    }

    /**
     * 
     * @return
     *     The createTime
     */
    @JsonProperty("createTime")
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The createTime
     */
    @JsonProperty("createTime")
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public OpenapiDoc withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 
     * @return
     *     The updateTime
     */
    @JsonProperty("updateTime")
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime
     *     The updateTime
     */
    @JsonProperty("updateTime")
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public OpenapiDoc withUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public OpenapiDoc withStatus(String status) {
        this.status = status;
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

    public OpenapiDoc withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
