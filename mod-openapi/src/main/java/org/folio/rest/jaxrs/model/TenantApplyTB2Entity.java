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
 * authShowData.json Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "tenantID", "catalog", "tenantName", "openapiArray", "applyDate", "applyMan", "applyManEmail", "applyManPhone", "approveDate", "refuseDate", "refuseReason", "authDate", "clientSecret", "status" })
public class TenantApplyTB2Entity {

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
    @JsonProperty("tenantID")
    private String tenantID;

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
    @JsonProperty("tenantName")
    private String tenantName;

    @JsonProperty("openapiArray")
    @Valid
    private List<String> openapiArray = new ArrayList<String>();

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyDate")
    private String applyDate;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyMan")
    private String applyMan;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyManEmail")
    private String applyManEmail;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("applyManPhone")
    private String applyManPhone;

    /**
     * 
     */
    @JsonProperty("approveDate")
    private String approveDate;

    /**
     * 
     */
    @JsonProperty("refuseDate")
    private String refuseDate;

    /**
     * 
     */
    @JsonProperty("refuseReason")
    private String refuseReason;

    /**
     * 
     */
    @JsonProperty("authDate")
    private String authDate;

    /**
     * 
     */
    @JsonProperty("clientSecret")
    private String clientSecret;

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
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

    public TenantApplyTB2Entity withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tenantID
     */
    @JsonProperty("tenantID")
    public String getTenantID() {
        return tenantID;
    }

    /**
     * 
     * (Required)
     * 
     * @param tenantID
     *     The tenantID
     */
    @JsonProperty("tenantID")
    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public TenantApplyTB2Entity withTenantID(String tenantID) {
        this.tenantID = tenantID;
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

    public TenantApplyTB2Entity withCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tenantName
     */
    @JsonProperty("tenantName")
    public String getTenantName() {
        return tenantName;
    }

    /**
     * 
     * (Required)
     * 
     * @param tenantName
     *     The tenantName
     */
    @JsonProperty("tenantName")
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public TenantApplyTB2Entity withTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    public TenantApplyTB2Entity withOpenapiArray(List<String> openapiArray) {
        this.openapiArray = openapiArray;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyDate
     */
    @JsonProperty("applyDate")
    public String getApplyDate() {
        return applyDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyDate
     *     The applyDate
     */
    @JsonProperty("applyDate")
    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public TenantApplyTB2Entity withApplyDate(String applyDate) {
        this.applyDate = applyDate;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyMan
     */
    @JsonProperty("applyMan")
    public String getApplyMan() {
        return applyMan;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyMan
     *     The applyMan
     */
    @JsonProperty("applyMan")
    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }

    public TenantApplyTB2Entity withApplyMan(String applyMan) {
        this.applyMan = applyMan;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyManEmail
     */
    @JsonProperty("applyManEmail")
    public String getApplyManEmail() {
        return applyManEmail;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyManEmail
     *     The applyManEmail
     */
    @JsonProperty("applyManEmail")
    public void setApplyManEmail(String applyManEmail) {
        this.applyManEmail = applyManEmail;
    }

    public TenantApplyTB2Entity withApplyManEmail(String applyManEmail) {
        this.applyManEmail = applyManEmail;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyManPhone
     */
    @JsonProperty("applyManPhone")
    public String getApplyManPhone() {
        return applyManPhone;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyManPhone
     *     The applyManPhone
     */
    @JsonProperty("applyManPhone")
    public void setApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
    }

    public TenantApplyTB2Entity withApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
        return this;
    }

    /**
     * 
     * @return
     *     The approveDate
     */
    @JsonProperty("approveDate")
    public String getApproveDate() {
        return approveDate;
    }

    /**
     * 
     * @param approveDate
     *     The approveDate
     */
    @JsonProperty("approveDate")
    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public TenantApplyTB2Entity withApproveDate(String approveDate) {
        this.approveDate = approveDate;
        return this;
    }

    /**
     * 
     * @return
     *     The refuseDate
     */
    @JsonProperty("refuseDate")
    public String getRefuseDate() {
        return refuseDate;
    }

    /**
     * 
     * @param refuseDate
     *     The refuseDate
     */
    @JsonProperty("refuseDate")
    public void setRefuseDate(String refuseDate) {
        this.refuseDate = refuseDate;
    }

    public TenantApplyTB2Entity withRefuseDate(String refuseDate) {
        this.refuseDate = refuseDate;
        return this;
    }

    /**
     * 
     * @return
     *     The refuseReason
     */
    @JsonProperty("refuseReason")
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * 
     * @param refuseReason
     *     The refuseReason
     */
    @JsonProperty("refuseReason")
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public TenantApplyTB2Entity withRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
        return this;
    }

    /**
     * 
     * @return
     *     The authDate
     */
    @JsonProperty("authDate")
    public String getAuthDate() {
        return authDate;
    }

    /**
     * 
     * @param authDate
     *     The authDate
     */
    @JsonProperty("authDate")
    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public TenantApplyTB2Entity withAuthDate(String authDate) {
        this.authDate = authDate;
        return this;
    }

    /**
     * 
     * @return
     *     The clientSecret
     */
    @JsonProperty("clientSecret")
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 
     * @param clientSecret
     *     The clientSecret
     */
    @JsonProperty("clientSecret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public TenantApplyTB2Entity withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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

    public TenantApplyTB2Entity withStatus(String status) {
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

    public TenantApplyTB2Entity withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
