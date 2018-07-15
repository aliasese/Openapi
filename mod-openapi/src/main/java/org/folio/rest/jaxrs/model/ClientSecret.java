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
@JsonPropertyOrder({ "client_secret" })
public class ClientSecret {

    @JsonProperty("client_secret")
    private String clientSecret;

    /**
     * 
     * @return
     *     The clientSecret
     */
    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 
     * @param clientSecret
     *     The client_secret
     */
    @JsonProperty("client_secret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public ClientSecret withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }
}
