package org.folio.rest.jaxrs.model;

import javax.annotation.Generated;
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
@JsonPropertyOrder({ "client_id" })
public class ClientId {

    @JsonProperty("client_id")
    private String clientId;

    /**
     * 
     * @return
     *     The clientId
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * 
     * @param clientId
     *     The client_id
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientId withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }
}
