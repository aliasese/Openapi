package org.folio.rest.jaxrs.model;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ClientId Define
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "client_name", "tenant_id" })
public class ClientName {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("client_name")
    @NotNull
    private String clientName;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("tenant_id")
    @NotNull
    private String tenantId;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The clientName
     */
    @JsonProperty("client_name")
    public String getClientName() {
        return clientName;
    }

    /**
     * 
     * (Required)
     * 
     * @param clientName
     *     The client_name
     */
    @JsonProperty("client_name")
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ClientName withClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tenantId
     */
    @JsonProperty("tenant_id")
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 
     * (Required)
     * 
     * @param tenantId
     *     The tenant_id
     */
    @JsonProperty("tenant_id")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ClientName withTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }
}
