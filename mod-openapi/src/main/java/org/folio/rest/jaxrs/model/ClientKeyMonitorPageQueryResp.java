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
@JsonPropertyOrder({ "clientKeyMonitorPageQueryArray", "totalRecords", "pageCount", "pageNum" })
public class ClientKeyMonitorPageQueryResp {

    @JsonProperty("clientKeyMonitorPageQueryArray")
    @Valid
    private List<ClientKeyMonitorPageQueryArray> clientKeyMonitorPageQueryArray = new ArrayList<ClientKeyMonitorPageQueryArray>();

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
     *     The clientKeyMonitorPageQueryArray
     */
    @JsonProperty("clientKeyMonitorPageQueryArray")
    public List<ClientKeyMonitorPageQueryArray> getClientKeyMonitorPageQueryArray() {
        return clientKeyMonitorPageQueryArray;
    }

    /**
     * 
     * @param clientKeyMonitorPageQueryArray
     *     The clientKeyMonitorPageQueryArray
     */
    @JsonProperty("clientKeyMonitorPageQueryArray")
    public void setClientKeyMonitorPageQueryArray(List<ClientKeyMonitorPageQueryArray> clientKeyMonitorPageQueryArray) {
        this.clientKeyMonitorPageQueryArray = clientKeyMonitorPageQueryArray;
    }

    public ClientKeyMonitorPageQueryResp withClientKeyMonitorPageQueryArray(List<ClientKeyMonitorPageQueryArray> clientKeyMonitorPageQueryArray) {
        this.clientKeyMonitorPageQueryArray = clientKeyMonitorPageQueryArray;
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

    public ClientKeyMonitorPageQueryResp withTotalRecords(Integer totalRecords) {
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

    public ClientKeyMonitorPageQueryResp withPageCount(Integer pageCount) {
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

    public ClientKeyMonitorPageQueryResp withPageNum(Integer pageNum) {
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

    public ClientKeyMonitorPageQueryResp withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
