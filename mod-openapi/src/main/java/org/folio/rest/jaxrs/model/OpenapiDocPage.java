package org.folio.rest.jaxrs.model;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * OpenAPIDoc Define
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "category", "title", "context", "createTime", "updateTime", "status" })
public class OpenapiDocPage {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    @NotNull
    private String id;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("category")
    @NotNull
    private String category;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("title")
    @NotNull
    private String title;

    @JsonProperty("context")
    private String context;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("createTime")
    @NotNull
    private String createTime;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("updateTime")
    @NotNull
    private String updateTime;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    @NotNull
    private String status;

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OpenapiDocPage withId(String id) {
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

    public OpenapiDocPage withCategory(String category) {
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

    public OpenapiDocPage withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
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
     * @param context
     *     The context
     */
    @JsonProperty("context")
    public void setContext(String context) {
        this.context = context;
    }

    public OpenapiDocPage withContext(String context) {
        this.context = context;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param createTime
     *     The createTime
     */
    @JsonProperty("createTime")
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public OpenapiDocPage withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param updateTime
     *     The updateTime
     */
    @JsonProperty("updateTime")
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public OpenapiDocPage withUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public OpenapiDocPage withStatus(String status) {
        this.status = status;
        return this;
    }
}
