
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
 * openApi Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "onlineSrvcId",
    "name",
    "method",
    "summary",
    "catalog",
    "type",
    "visit_url",
    "visit_test_url",
    "publish_url",
    "deploy_url",
    "publish_test_url",
    "deploy_test_url",
    "common_params",
    "private_params",
    "outputs",
    "status",
    "create_time",
    "update_time"
})
public class OpenapiInfo {

    /**
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * 
     */
    @JsonProperty("onlineSrvcId")
    private String onlineSrvcId;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("name")
    private String name;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("method")
    @Valid
    private List<String> method = new ArrayList<String>();
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("summary")
    private String summary;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("catalog")
    private String catalog;
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
    @JsonProperty("visit_url")
    private String visitUrl;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("visit_test_url")
    private String visitTestUrl;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("publish_url")
    private String publishUrl;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("deploy_url")
    private String deployUrl;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("publish_test_url")
    private String publishTestUrl;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("deploy_test_url")
    private String deployTestUrl;
    @JsonProperty("common_params")
    @Valid
    private List<CommonParam> commonParams = new ArrayList<CommonParam>();
    @JsonProperty("private_params")
    @Valid
    private List<CommonParam> privateParams = new ArrayList<CommonParam>();
    @JsonProperty("outputs")
    @Valid
    private List<CommonParam> outputs = new ArrayList<CommonParam>();
    /**
     * 
     */
    @JsonProperty("status")
    private String status;
    /**
     * 
     */
    @JsonProperty("create_time")
    private String createTime;
    /**
     * 
     */
    @JsonProperty("update_time")
    private String updateTime;
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

    public OpenapiInfo withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The onlineSrvcId
     */
    @JsonProperty("onlineSrvcId")
    public String getOnlineSrvcId() {
        return onlineSrvcId;
    }

    /**
     * 
     * @param onlineSrvcId
     *     The onlineSrvcId
     */
    @JsonProperty("onlineSrvcId")
    public void setOnlineSrvcId(String onlineSrvcId) {
        this.onlineSrvcId = onlineSrvcId;
    }

    public OpenapiInfo withOnlineSrvcId(String onlineSrvcId) {
        this.onlineSrvcId = onlineSrvcId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public OpenapiInfo withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The method
     */
    @JsonProperty("method")
    public List<String> getMethod() {
        return method;
    }

    /**
     * 
     * (Required)
     * 
     * @param method
     *     The method
     */
    @JsonProperty("method")
    public void setMethod(List<String> method) {
        this.method = method;
    }

    public OpenapiInfo withMethod(List<String> method) {
        this.method = method;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * (Required)
     * 
     * @param summary
     *     The summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public OpenapiInfo withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The catalog
     */
    @JsonProperty("catalog")
    public String getCatalog() {
        return catalog;
    }

    /**
     * 
     * (Required)
     * 
     * @param catalog
     *     The catalog
     */
    @JsonProperty("catalog")
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public OpenapiInfo withCatalog(String catalog) {
        this.catalog = catalog;
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

    public OpenapiInfo withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The visitUrl
     */
    @JsonProperty("visit_url")
    public String getVisitUrl() {
        return visitUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param visitUrl
     *     The visit_url
     */
    @JsonProperty("visit_url")
    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public OpenapiInfo withVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The visitTestUrl
     */
    @JsonProperty("visit_test_url")
    public String getVisitTestUrl() {
        return visitTestUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param visitTestUrl
     *     The visit_test_url
     */
    @JsonProperty("visit_test_url")
    public void setVisitTestUrl(String visitTestUrl) {
        this.visitTestUrl = visitTestUrl;
    }

    public OpenapiInfo withVisitTestUrl(String visitTestUrl) {
        this.visitTestUrl = visitTestUrl;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The publishUrl
     */
    @JsonProperty("publish_url")
    public String getPublishUrl() {
        return publishUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param publishUrl
     *     The publish_url
     */
    @JsonProperty("publish_url")
    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public OpenapiInfo withPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The deployUrl
     */
    @JsonProperty("deploy_url")
    public String getDeployUrl() {
        return deployUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param deployUrl
     *     The deploy_url
     */
    @JsonProperty("deploy_url")
    public void setDeployUrl(String deployUrl) {
        this.deployUrl = deployUrl;
    }

    public OpenapiInfo withDeployUrl(String deployUrl) {
        this.deployUrl = deployUrl;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The publishTestUrl
     */
    @JsonProperty("publish_test_url")
    public String getPublishTestUrl() {
        return publishTestUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param publishTestUrl
     *     The publish_test_url
     */
    @JsonProperty("publish_test_url")
    public void setPublishTestUrl(String publishTestUrl) {
        this.publishTestUrl = publishTestUrl;
    }

    public OpenapiInfo withPublishTestUrl(String publishTestUrl) {
        this.publishTestUrl = publishTestUrl;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The deployTestUrl
     */
    @JsonProperty("deploy_test_url")
    public String getDeployTestUrl() {
        return deployTestUrl;
    }

    /**
     * 
     * (Required)
     * 
     * @param deployTestUrl
     *     The deploy_test_url
     */
    @JsonProperty("deploy_test_url")
    public void setDeployTestUrl(String deployTestUrl) {
        this.deployTestUrl = deployTestUrl;
    }

    public OpenapiInfo withDeployTestUrl(String deployTestUrl) {
        this.deployTestUrl = deployTestUrl;
        return this;
    }

    /**
     * 
     * @return
     *     The commonParams
     */
    @JsonProperty("common_params")
    public List<CommonParam> getCommonParams() {
        return commonParams;
    }

    /**
     * 
     * @param commonParams
     *     The common_params
     */
    @JsonProperty("common_params")
    public void setCommonParams(List<CommonParam> commonParams) {
        this.commonParams = commonParams;
    }

    public OpenapiInfo withCommonParams(List<CommonParam> commonParams) {
        this.commonParams = commonParams;
        return this;
    }

    /**
     * 
     * @return
     *     The privateParams
     */
    @JsonProperty("private_params")
    public List<CommonParam> getPrivateParams() {
        return privateParams;
    }

    /**
     * 
     * @param privateParams
     *     The private_params
     */
    @JsonProperty("private_params")
    public void setPrivateParams(List<CommonParam> privateParams) {
        this.privateParams = privateParams;
    }

    public OpenapiInfo withPrivateParams(List<CommonParam> privateParams) {
        this.privateParams = privateParams;
        return this;
    }

    /**
     * 
     * @return
     *     The outputs
     */
    @JsonProperty("outputs")
    public List<CommonParam> getOutputs() {
        return outputs;
    }

    /**
     * 
     * @param outputs
     *     The outputs
     */
    @JsonProperty("outputs")
    public void setOutputs(List<CommonParam> outputs) {
        this.outputs = outputs;
    }

    public OpenapiInfo withOutputs(List<CommonParam> outputs) {
        this.outputs = outputs;
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

    public OpenapiInfo withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 
     * @return
     *     The createTime
     */
    @JsonProperty("create_time")
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The create_time
     */
    @JsonProperty("create_time")
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public OpenapiInfo withCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 
     * @return
     *     The updateTime
     */
    @JsonProperty("update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime
     *     The update_time
     */
    @JsonProperty("update_time")
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public OpenapiInfo withUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public OpenapiInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
