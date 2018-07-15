
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
 * create authorize APIs for tenants Schema
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "catalog",
    "apply_man",
    "apply_man_email",
    "apply_man_phone",
    "openapiArray",
    "tenantArray"
})
public class OpenapiApplyData {

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
    @JsonProperty("apply_man")
    private String applyMan;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("apply_man_email")
    private String applyManEmail;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("apply_man_phone")
    private String applyManPhone;
    @JsonProperty("openapiArray")
    @Valid
    private List<String> openapiArray = new ArrayList<String>();
    @JsonProperty("tenantArray")
    @Valid
    private List<TenantArray> tenantArray = new ArrayList<TenantArray>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public OpenapiApplyData withCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The applyMan
     */
    @JsonProperty("apply_man")
    public String getApplyMan() {
        return applyMan;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyMan
     *     The apply_man
     */
    @JsonProperty("apply_man")
    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }

    public OpenapiApplyData withApplyMan(String applyMan) {
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
    @JsonProperty("apply_man_email")
    public String getApplyManEmail() {
        return applyManEmail;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyManEmail
     *     The apply_man_email
     */
    @JsonProperty("apply_man_email")
    public void setApplyManEmail(String applyManEmail) {
        this.applyManEmail = applyManEmail;
    }

    public OpenapiApplyData withApplyManEmail(String applyManEmail) {
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
    @JsonProperty("apply_man_phone")
    public String getApplyManPhone() {
        return applyManPhone;
    }

    /**
     * 
     * (Required)
     * 
     * @param applyManPhone
     *     The apply_man_phone
     */
    @JsonProperty("apply_man_phone")
    public void setApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
    }

    public OpenapiApplyData withApplyManPhone(String applyManPhone) {
        this.applyManPhone = applyManPhone;
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

    public OpenapiApplyData withOpenapiArray(List<String> openapiArray) {
        this.openapiArray = openapiArray;
        return this;
    }

    /**
     * 
     * @return
     *     The tenantArray
     */
    @JsonProperty("tenantArray")
    public List<TenantArray> getTenantArray() {
        return tenantArray;
    }

    /**
     * 
     * @param tenantArray
     *     The tenantArray
     */
    @JsonProperty("tenantArray")
    public void setTenantArray(List<TenantArray> tenantArray) {
        this.tenantArray = tenantArray;
    }

    public OpenapiApplyData withTenantArray(List<TenantArray> tenantArray) {
        this.tenantArray = tenantArray;
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

    public OpenapiApplyData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
