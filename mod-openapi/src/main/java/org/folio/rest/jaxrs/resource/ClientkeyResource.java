
package org.folio.rest.jaxrs.resource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import io.vertx.core.Context;
import io.vertx.ext.web.RoutingContext;
import org.folio.rest.annotations.Validate;
import org.folio.rest.jaxrs.model.ApproveClientKeyData;
import org.folio.rest.jaxrs.model.ClientId;
import org.folio.rest.jaxrs.model.ClientKeyApplyData;
import org.folio.rest.jaxrs.model.ClientKeyApplyData_;
import org.folio.rest.jaxrs.model.ClientKeyApplyPageQueryCondition;
import org.folio.rest.jaxrs.model.ClientKeyApplyPageQueryResp;
import org.folio.rest.jaxrs.model.ClientKeyInfo;
import org.folio.rest.jaxrs.model.ClientKeyMonitorPageQueryCondition;
import org.folio.rest.jaxrs.model.ClientKeyMonitorPageQueryResp;
import org.folio.rest.jaxrs.model.ClientName;
import org.folio.rest.jaxrs.model.ClientSecret;
import org.folio.rest.jaxrs.model.CreateResp;
import org.folio.rest.jaxrs.model.Log;
import org.folio.rest.jaxrs.model.RefuseData;
import org.folio.rest.jaxrs.model.StartData;
import org.folio.rest.jaxrs.model.StopData;

@Path("clientkey")
public interface ClientkeyResource {


    /**
     * ClientKey Apply
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param routingContext
     *     RoutingContext of the request. Note that the RMB framework handles all routing.This should only be used if a third party add-on to vertx needs the RC as input 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkey(ClientKeyApplyData entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * 生成ClientSecret
     * 
     * @param query
     *     JSON array [{"field1","value1","operator1"},{"field2","value2","operator2"},...,{"fieldN","valueN","operatorN"}]
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param routingContext
     *     RoutingContext of the request. Note that the RMB framework handles all routing.This should only be used if a third party add-on to vertx needs the RC as input 
     */
    @GET
    @Path("client_secret")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getClientkeyClientSecret(
        @QueryParam("query")
        String query, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * 获取全局唯一ClientId
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("client_id")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getClientkeyClientId(java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Modify client_id info
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @PUT
    @Path("client_id")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void putClientkeyClientId(ApproveClientKeyData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * 校验client_name唯一性
     * 
     * @param query
     *     JSON array [{"field1","value1","operator1"},{"field2","value2","operator2"},...,{"fieldN","valueN","operatorN"}]
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param routingContext
     *     RoutingContext of the request. Note that the RMB framework handles all routing.This should only be used if a third party add-on to vertx needs the RC as input 
     * @param entity
     *     
     */
    @POST
    @Path("check_exist/client_name")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyCheckExistClientName(
        @QueryParam("query")
        String query, ClientName entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * 校验clientId唯一性
     * 
     * @param clientId
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("checkExist/{clientId}")
    @Produces({
        "text/plain"
    })
    @Validate
    void getClientkeyCheckExistByClientId(
        @PathParam("clientId")
        @NotNull
        String clientId, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * ClientKay Apply Page Query
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param routingContext
     *     RoutingContext of the request. Note that the RMB framework handles all routing.This should only be used if a third party add-on to vertx needs the RC as input 
     * @param entity
     *     
     */
    @POST
    @Path("page_query")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyPageQuery(ClientKeyApplyPageQueryCondition entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * get ClientKey Apply Information
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param id
     *     
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("show/{id}")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getClientkeyShowById(
        @PathParam("id")
        @NotNull
        String id, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Approve ClientKay Apply
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("approve")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyApprove(ApproveClientKeyData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Refuse ClientKey Apply
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param id
     *     
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("refuse/{id}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyRefuseById(
        @PathParam("id")
        @NotNull
        String id, RefuseData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Delete ClientKey apply
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param id
     *     
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("delete/{id}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyDeleteById(
        @PathParam("id")
        @NotNull
        String id, Log entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * ClientKay Apply Monitor Page Query
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("monitor/page_query")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyMonitorPageQuery(ClientKeyMonitorPageQueryCondition entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Stop ClientKey
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param id
     *     
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("monitor/stop/{id}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyMonitorStopById(
        @PathParam("id")
        @NotNull
        String id, StopData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Start ClientKey
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param id
     *     
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("monitor/start/{id}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postClientkeyMonitorStartById(
        @PathParam("id")
        @NotNull
        String id, StartData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * get ClientSecret by clientId
     * 
     * @param clientId
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("client_id/{clientId}")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getClientkeyClientIdByClientId(
        @PathParam("clientId")
        @NotNull
        String clientId, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * 校验应用访问地址IPV4合法性
     * 
     * @param ipv4
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("checkIP/{ipv4}")
    @Produces({
        "text/plain"
    })
    @Validate
    void getClientkeyCheckIPByIpv4(
        @PathParam("ipv4")
        @NotNull
        String ipv4, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    public class GetClientkeyCheckExistByClientIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetClientkeyCheckExistByClientIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * get ClientSecret by clientId successfully e.g. 校验clientId唯一性 success
         * 
         * @param entity
         *     校验clientId唯一性 success
         */
        public static ClientkeyResource.GetClientkeyCheckExistByClientIdResponse withPlainOK(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckExistByClientIdResponse(responseBuilder.build());
        }

        /**
         * ClientKey Apply not found e.g. ClientKey Apply not found
         * 
         * @param entity
         *     ClientKey Apply not found
         */
        public static ClientkeyResource.GetClientkeyCheckExistByClientIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckExistByClientIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.GetClientkeyCheckExistByClientIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckExistByClientIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.GetClientkeyCheckExistByClientIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckExistByClientIdResponse(responseBuilder.build());
        }

    }

    public class GetClientkeyCheckIPByIpv4Response
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetClientkeyCheckIPByIpv4Response(Response delegate) {
            super(delegate);
        }

        /**
         * 应用访问地址IPV4合法 e.g. 应用访问地址IPV4合法
         * 
         * @param entity
         *     应用访问地址IPV4合法
         */
        public static ClientkeyResource.GetClientkeyCheckIPByIpv4Response withPlainOK(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckIPByIpv4Response(responseBuilder.build());
        }

        /**
         * 应用访问地址IPV4合法 e.g. 应用访问地址IPV4不合法
         * 
         * @param entity
         *     应用访问地址IPV4不合法
         */
        public static ClientkeyResource.GetClientkeyCheckIPByIpv4Response withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckIPByIpv4Response(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.GetClientkeyCheckIPByIpv4Response withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyCheckIPByIpv4Response(responseBuilder.build());
        }

    }

    public class GetClientkeyClientIdByClientIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetClientkeyClientIdByClientIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * get ClientSecret by clientId successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.GetClientkeyClientIdByClientIdResponse withJsonOK(ClientKeyInfo entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdByClientIdResponse(responseBuilder.build());
        }

        /**
         * ClientKey Apply not found e.g. ClientKey Apply not found
         * 
         * @param entity
         *     ClientKey Apply not found
         */
        public static ClientkeyResource.GetClientkeyClientIdByClientIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdByClientIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.GetClientkeyClientIdByClientIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdByClientIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.GetClientkeyClientIdByClientIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdByClientIdResponse(responseBuilder.build());
        }

    }

    public class GetClientkeyClientIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetClientkeyClientIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * success
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.GetClientkeyClientIdResponse withJsonOK(ClientId entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdResponse(responseBuilder.build());
        }

        /**
         * API not found e.g. 获取全局唯一ClientId API not found
         * 
         * @param entity
         *     获取全局唯一ClientId API not found
         */
        public static ClientkeyResource.GetClientkeyClientIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail 获取全局唯一ClientId for tenant
         * 
         * @param entity
         *     Fail 获取全局唯一ClientId for tenant
         */
        public static ClientkeyResource.GetClientkeyClientIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.GetClientkeyClientIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientIdResponse(responseBuilder.build());
        }

    }

    public class GetClientkeyClientSecretResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetClientkeyClientSecretResponse(Response delegate) {
            super(delegate);
        }

        /**
         * success
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.GetClientkeyClientSecretResponse withJsonOK(ClientSecret entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientSecretResponse(responseBuilder.build());
        }

        /**
         * API not found e.g. 获取全局唯一ClientSecret API not found
         * 
         * @param entity
         *     获取全局唯一ClientSecret API not found
         */
        public static ClientkeyResource.GetClientkeyClientSecretResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientSecretResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail 获取全局唯一ClientSecret for tenant
         * 
         * @param entity
         *     Fail 获取全局唯一ClientSecret for tenant
         */
        public static ClientkeyResource.GetClientkeyClientSecretResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientSecretResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.GetClientkeyClientSecretResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyClientSecretResponse(responseBuilder.build());
        }

    }

    public class GetClientkeyShowByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetClientkeyShowByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * get ClientKey Apply Information successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.GetClientkeyShowByIdResponse withJsonOK(ClientKeyApplyData_ entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyShowByIdResponse(responseBuilder.build());
        }

        /**
         * ClientKey Apply not found e.g. ClientKey Apply not found
         * 
         * @param entity
         *     ClientKey Apply not found
         */
        public static ClientkeyResource.GetClientkeyShowByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyShowByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.GetClientkeyShowByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyShowByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.GetClientkeyShowByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.GetClientkeyShowByIdResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyApproveResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyApproveResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Approve ClientKey Apply successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyApproveResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyApproveResponse(responseBuilder.build());
        }

        /**
         * ClientKey not found e.g. ClientKey not found
         * 
         * @param entity
         *     ClientKey not found
         */
        public static ClientkeyResource.PostClientkeyApproveResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyApproveResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyApproveResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyApproveResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyApproveResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyApproveResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyCheckExistClientNameResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyCheckExistClientNameResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 校验client_name唯一性 successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyCheckExistClientNameResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyCheckExistClientNameResponse(responseBuilder.build());
        }

        /**
         * 校验client_name唯一性 API not found e.g. 校验client_name唯一性 API not found
         * 
         * @param entity
         *     校验client_name唯一性 API not found
         */
        public static ClientkeyResource.PostClientkeyCheckExistClientNameResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyCheckExistClientNameResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyCheckExistClientNameResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyCheckExistClientNameResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyCheckExistClientNameResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyCheckExistClientNameResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyDeleteByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyDeleteByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Delete ClientKey apply successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyDeleteByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyDeleteByIdResponse(responseBuilder.build());
        }

        /**
         * ClientKey not found e.g. ClientKey not found
         * 
         * @param entity
         *     ClientKey not found
         */
        public static ClientkeyResource.PostClientkeyDeleteByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyDeleteByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyDeleteByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyDeleteByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyDeleteByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyDeleteByIdResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyMonitorPageQueryResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyMonitorPageQueryResponse(Response delegate) {
            super(delegate);
        }

        /**
         * ClientKay Apply successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyMonitorPageQueryResponse withJsonOK(ClientKeyMonitorPageQueryResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorPageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ClientkeyResource.PostClientkeyMonitorPageQueryResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorPageQueryResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyMonitorPageQueryResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorPageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyMonitorPageQueryResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorPageQueryResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyMonitorStartByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyMonitorStartByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyMonitorStartByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStartByIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ClientkeyResource.PostClientkeyMonitorStartByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStartByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyMonitorStartByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStartByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyMonitorStartByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStartByIdResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyMonitorStopByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyMonitorStopByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyMonitorStopByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStopByIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ClientkeyResource.PostClientkeyMonitorStopByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStopByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyMonitorStopByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStopByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyMonitorStopByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyMonitorStopByIdResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyPageQueryResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyPageQueryResponse(Response delegate) {
            super(delegate);
        }

        /**
         * ClientKay Apply successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyPageQueryResponse withJsonOK(ClientKeyApplyPageQueryResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyPageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ClientkeyResource.PostClientkeyPageQueryResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyPageQueryResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyPageQueryResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyPageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyPageQueryResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyPageQueryResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyRefuseByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyRefuseByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyRefuseByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyRefuseByIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ClientkeyResource.PostClientkeyRefuseByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyRefuseByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyRefuseByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyRefuseByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyRefuseByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyRefuseByIdResponse(responseBuilder.build());
        }

    }

    public class PostClientkeyResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostClientkeyResponse(Response delegate) {
            super(delegate);
        }

        /**
         * ClientKay Apply successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PostClientkeyResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ClientkeyResource.PostClientkeyResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ClientkeyResource.PostClientkeyResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ClientkeyResource.PostClientkeyResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PostClientkeyResponse(responseBuilder.build());
        }

    }

    public class PutClientkeyClientIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PutClientkeyClientIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Modify client_id info successfully
         * 
         * @param entity
         *     
         */
        public static ClientkeyResource.PutClientkeyClientIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PutClientkeyClientIdResponse(responseBuilder.build());
        }

        /**
         * Modify client_id info not found e.g. Modify client_id info not found
         * 
         * @param entity
         *     Modify client_id info not found
         */
        public static ClientkeyResource.PutClientkeyClientIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PutClientkeyClientIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail  Modify client_id info
         * 
         * @param entity
         *     Fail  Modify client_id info
         */
        public static ClientkeyResource.PutClientkeyClientIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PutClientkeyClientIdResponse(responseBuilder.build());
        }

        /**
         * Modify client_id info server error e.g. Modify client_id info server error
         * 
         * @param entity
         *     Modify client_id info server error
         */
        public static ClientkeyResource.PutClientkeyClientIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ClientkeyResource.PutClientkeyClientIdResponse(responseBuilder.build());
        }

    }

}
