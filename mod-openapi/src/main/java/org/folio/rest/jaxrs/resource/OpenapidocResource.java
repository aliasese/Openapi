
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
import org.folio.rest.jaxrs.model.CreateResp;
import org.folio.rest.jaxrs.model.Log;
import org.folio.rest.jaxrs.model.OpenapiDoc;
import org.folio.rest.jaxrs.model.OpenapiDocCondition;
import org.folio.rest.jaxrs.model.OpenapiDocData;
import org.folio.rest.jaxrs.model.OpenapiDocDataPut;
import org.folio.rest.jaxrs.model.Openapidocs;
import org.folio.rest.jaxrs.model.Title;

@Path("openapidoc")
public interface OpenapidocResource {


    /**
     * Get OpenAPI Document List
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
    void postOpenapidoc(OpenapiDocCondition entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * edit a openapidoc
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
    @PUT
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void putOpenapidoc(OpenapiDocDataPut entity, RoutingContext routingContext, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Check if OpenAPIDoc title exists
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("check/title")
    @Consumes("application/json")
    @Produces({
        "text/plain"
    })
    @Validate
    void postOpenapidocCheckTitle(Title entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * OpenAPI Document Release
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
    void postOpenapidocCreatePost(OpenapiDocData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Get single OpenAPI Doc detail Info
     * 
     * @param openapidocid
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("{openapidocid}")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getOpenapidocByOpenapidocid(
        @PathParam("openapidocid")
        @NotNull
        String openapidocid, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * delete a openapidoc
     * 
     * @param openapidocid
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("{openapidocid}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postOpenapidocByOpenapidocid(
        @PathParam("openapidocid")
        @NotNull
        String openapidocid, Log entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    public class GetOpenapidocByOpenapidocidResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetOpenapidocByOpenapidocidResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a openapi DOC successfully
         * 
         * @param entity
         *     
         */
        public static OpenapidocResource.GetOpenapidocByOpenapidocidResponse withJsonOK(OpenapiDoc entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapidocResource.GetOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapidocResource.GetOpenapidocByOpenapidocidResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.GetOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapidocResource.GetOpenapidocByOpenapidocidResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.GetOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapidocResource.GetOpenapidocByOpenapidocidResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.GetOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

    }

    public class PostOpenapidocByOpenapidocidResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapidocByOpenapidocidResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Delete a openapidoc successfully
         * 
         * @param entity
         *     
         */
        public static OpenapidocResource.PostOpenapidocByOpenapidocidResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Bad request
         * 
         * @param entity
         *     Bad request
         */
        public static OpenapidocResource.PostOpenapidocByOpenapidocidResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

        /**
         * openapidoc not found e.g. openapidoc not found
         * 
         * @param entity
         *     openapidoc not found
         */
        public static OpenapidocResource.PostOpenapidocByOpenapidocidResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

        /**
         * openapidoc server error e.g. openapidoc server error
         * 
         * @param entity
         *     openapidoc server error
         */
        public static OpenapidocResource.PostOpenapidocByOpenapidocidResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocByOpenapidocidResponse(responseBuilder.build());
        }

    }

    public class PostOpenapidocCheckTitleResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapidocCheckTitleResponse(Response delegate) {
            super(delegate);
        }

        /**
         * Title doesn't exist e.g. success
         * 
         * @param entity
         *     success
         */
        public static OpenapidocResource.PostOpenapidocCheckTitleResponse withPlainOK(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCheckTitleResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Title has existed
         * 
         * @param entity
         *     Title has existed
         */
        public static OpenapidocResource.PostOpenapidocCheckTitleResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCheckTitleResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapidocResource.PostOpenapidocCheckTitleResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCheckTitleResponse(responseBuilder.build());
        }

    }

    public class PostOpenapidocCreatePostResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapidocCreatePostResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a list of openapis successfully
         * 
         * @param entity
         *     
         */
        public static OpenapidocResource.PostOpenapidocCreatePostResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCreatePostResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapidocResource.PostOpenapidocCreatePostResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCreatePostResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapidocResource.PostOpenapidocCreatePostResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCreatePostResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapidocResource.PostOpenapidocCreatePostResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocCreatePostResponse(responseBuilder.build());
        }

    }

    public class PostOpenapidocResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostOpenapidocResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a list of openapis successfully
         * 
         * @param entity
         *     
         */
        public static OpenapidocResource.PostOpenapidocResponse withJsonOK(Openapidocs entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapidocResource.PostOpenapidocResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapidocResource.PostOpenapidocResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapidocResource.PostOpenapidocResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PostOpenapidocResponse(responseBuilder.build());
        }

    }

    public class PutOpenapidocResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PutOpenapidocResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a updated openapidoc successfully
         * 
         * @param entity
         *     
         */
        public static OpenapidocResource.PutOpenapidocResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PutOpenapidocResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail to start Openapi
         * 
         * @param entity
         *     Fail to start Openapi
         */
        public static OpenapidocResource.PutOpenapidocResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PutOpenapidocResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static OpenapidocResource.PutOpenapidocResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PutOpenapidocResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static OpenapidocResource.PutOpenapidocResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new OpenapidocResource.PutOpenapidocResponse(responseBuilder.build());
        }

    }

}
