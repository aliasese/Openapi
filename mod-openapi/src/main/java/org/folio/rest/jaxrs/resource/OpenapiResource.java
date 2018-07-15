
package org.folio.rest.jaxrs.resource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import io.vertx.core.Context;
import io.vertx.ext.web.RoutingContext;
import org.folio.rest.annotations.Validate;
import org.folio.rest.jaxrs.model.ArrayEntity;
import org.folio.rest.jaxrs.model.CreateResp;
import org.folio.rest.jaxrs.model.Log;
import org.folio.rest.jaxrs.model.Name;
import org.folio.rest.jaxrs.model.OpenapiCollection;
import org.folio.rest.jaxrs.model.OpenapiData;
import org.folio.rest.jaxrs.model.OpenapiInfo;
import org.folio.rest.jaxrs.model.OpenapiPagequeryCondition;
import org.folio.rest.jaxrs.model.StartOpenapi;
import org.folio.rest.jaxrs.model.StopOpenapi;

@Path("openapi")
public interface OpenapiResource {


    /**
     * Return a list of openapi
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
    void postOpenapi(OpenapiPagequeryCondition entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Check if OpenAPI name exists
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("check/name")
    @Consumes("application/json")
    @Produces({
        "text/plain"
    })
    @Validate
    void postOpenapiCheckName(Name entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * create a openapi
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("create/post")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postOpenapiCreatePost(OpenapiData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * edit a openapi
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
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void putOpenapiById(
        @PathParam("id")
        @NotNull
        String id, OpenapiData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * get information of a openapi
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param id
     *     
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("{id}")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getOpenapiById(
        @PathParam("id")
        @NotNull
        String id, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * delete a openapi
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
    @Path("{id}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postOpenapiById(
        @PathParam("id")
        @NotNull
        String id, Log entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Delete OpenAPI 注册信息
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
    void postOpenapiDeleteById(
        @PathParam("id")
        @NotNull
        String id, Log entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * start a openapi service
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
    @Path("start/post")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postOpenapiStartPost(StartOpenapi entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * stop a openapi service
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
    @Path("stop/post")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postOpenapiStopPost(StopOpenapi entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    public class GetOpenapiByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetOpenapiByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a openapi successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.GetOpenapiByIdResponse withJsonOK(OpenapiInfo entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.GetOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Bad Request
         * 
         * @param entity
         *     Bad Request
         */
        public static OpenapiResource.GetOpenapiByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.GetOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapiResource.GetOpenapiByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.GetOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapiResource.GetOpenapiByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.GetOpenapiByIdResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Delete a openapi successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PostOpenapiByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * Bad Request e.g. Bad Request
         * 
         * @param entity
         *     Bad Request
         */
        public static OpenapiResource.PostOpenapiByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapiResource.PostOpenapiByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static OpenapiResource.PostOpenapiByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiByIdResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiCheckNameResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiCheckNameResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Check if OpenAPI name exists e.g. success
         * 
         * @param entity
         *     success
         */
        public static OpenapiResource.PostOpenapiCheckNameResponse withPlainOK(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiCheckNameResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Name has existed
         * 
         * @param entity
         *     Name has existed
         */
        public static OpenapiResource.PostOpenapiCheckNameResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiCheckNameResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapiResource.PostOpenapiCheckNameResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiCheckNameResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiCreatePostResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiCreatePostResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Good request
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PostOpenapiCreatePostResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiCreatePostResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Bad request
         * 
         * @param entity
         *     Bad request
         */
        public static OpenapiResource.PostOpenapiCreatePostResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiCreatePostResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error
         * 
         * @param entity
         *     Internal server error
         */
        public static OpenapiResource.PostOpenapiCreatePostResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiCreatePostResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiDeleteByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiDeleteByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Delete OpenAPI 注册信息 successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PostOpenapiDeleteByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiDeleteByIdResponse(responseBuilder.build());
        }

        /**
         * OpenAPI not found e.g. OpenAPI not found
         * 
         * @param entity
         *     OpenAPI not found
         */
        public static OpenapiResource.PostOpenapiDeleteByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiDeleteByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Delete OpenAPI 注册信息出现异常
         * 
         * @param entity
         *     Delete OpenAPI 注册信息出现异常
         */
        public static OpenapiResource.PostOpenapiDeleteByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiDeleteByIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static OpenapiResource.PostOpenapiDeleteByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiDeleteByIdResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a list of openapis successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PostOpenapiResponse withJsonOK(OpenapiCollection entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Bad request
         * 
         * @param entity
         *     Bad request
         */
        public static OpenapiResource.PostOpenapiResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiResponse(responseBuilder.build());
        }

        /**
         * OpenAPI not found e.g. OpenAPI not found
         * 
         * @param entity
         *     OpenAPI not found
         */
        public static OpenapiResource.PostOpenapiResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapiResource.PostOpenapiResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiStartPostResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiStartPostResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns openapi starting successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PostOpenapiStartPostResponse withJsonOK(ArrayEntity entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStartPostResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapiResource.PostOpenapiStartPostResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStartPostResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapiResource.PostOpenapiStartPostResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStartPostResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapiResource.PostOpenapiStartPostResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStartPostResponse(responseBuilder.build());
        }

    }

    public class PostOpenapiStopPostResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapiStopPostResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns openapi stopping successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PostOpenapiStopPostResponse withJsonOK(ArrayEntity entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStopPostResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapiResource.PostOpenapiStopPostResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStopPostResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapiResource.PostOpenapiStopPostResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStopPostResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapiResource.PostOpenapiStopPostResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PostOpenapiStopPostResponse(responseBuilder.build());
        }

    }

    public class PutOpenapiByIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PutOpenapiByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a updated openapi successfully
         * 
         * @param entity
         *     
         */
        public static OpenapiResource.PutOpenapiByIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapiResource.PutOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapiResource.PutOpenapiByIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PutOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapiResource.PutOpenapiByIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PutOpenapiByIdResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapiResource.PutOpenapiByIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapiResource.PutOpenapiByIdResponse(responseBuilder.build());
        }

    }

}
