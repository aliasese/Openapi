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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "clientKeyApplyPageQueryArray", "totalRecords", "pageCount", "pageNum" })
public class ClientKeyApplyPageQueryResp {

    @JsonProperty("clientKeyApplyPageQueryArray")
    @Valid
    private List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArray = new ArrayList<ClientKeyApplyPageQueryArray>();

    @JsonProperty("totalRecords")
    private Integer totalRecords;

    @JsonProperty("pageCount")
    private Integer pageCount;

    @JsonProperty("pageNum")
    private Integer pageNum;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The clientKeyApplyPageQueryArray
     */
    @JsonProperty("clientKeyApplyPageQueryArray")
    public List<ClientKeyApplyPageQueryArray> getClientKeyApplyPageQueryArray() {
        return clientKeyApplyPageQueryArray;
    }

    /**
     * 
     * @param clientKeyApplyPageQueryArray
     *     The clientKeyApplyPageQueryArray
     */
    @JsonProperty("clientKeyApplyPageQueryArray")
    public void setClientKeyApplyPageQueryArray(List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArray) {
        this.clientKeyApplyPageQueryArray = clientKeyApplyPageQueryArray;
    }

    public ClientKeyApplyPageQueryResp withClientKeyApplyPageQueryArray(List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArray) {
        this.clientKeyApplyPageQueryArray = clientKeyApplyPageQueryArray;
        return this;
    }

    /**
     * 
     * @return
     *     The totalRecords
     */
    @JsonProperty("totalRecords")
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * 
     * @param totalRecords
     *     The totalRecords
     */
    @JsonProperty("totalRecords")
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public ClientKeyApplyPageQueryResp withTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
        return this;
    }

    /**
     * 
     * @return
     *     The pageCount
     */
    @JsonProperty("pageCount")
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * 
     * @param pageCount
     *     The pageCount
     */
    @JsonProperty("pageCount")
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public ClientKeyApplyPageQueryResp withPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    /**
     * 
     * @return
     *     The pageNum
     */
    @JsonProperty("pageNum")
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * 
     * @param pageNum
     *     The pageNum
     */
    @JsonProperty("pageNum")
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public ClientKeyApplyPageQueryResp withPageNum(Integer pageNum) {
        this.pageNum = pageNum;
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

    public ClientKeyApplyPageQueryResp withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
