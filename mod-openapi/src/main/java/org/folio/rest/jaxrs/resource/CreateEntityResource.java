
package org.folio.rest.jaxrs.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import io.vertx.core.Context;
import org.folio.rest.annotations.Validate;
import org.folio.rest.jaxrs.model.ArrayEntity;
import org.folio.rest.jaxrs.model.Openapi;
import org.folio.rest.jaxrs.model.OpenapiApplyTenant;
import org.folio.rest.jaxrs.model.Tenant;
import org.folio.rest.jaxrs.model.TenantApplyTB2Entity;

@Path("createEntity")
public interface CreateEntityResource {


    /**
     * post
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "text/plain"
    })
    @Validate
    void postCreateEntity(Tenant entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * post
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("test")
    @Consumes("application/json")
    @Validate
    void postCreateEntityTest(OpenapiApplyTenant entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * TenantApplyTB2Entity
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("test1")
    @Consumes("application/json")
    @Validate
    void postCreateEntityTest1(TenantApplyTB2Entity entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * TenantApplyTB2Entity
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("test2")
    @Consumes("application/json")
    @Validate
    void postCreateEntityTest2(ArrayEntity entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * TenantApplyTB2Entity
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("test3")
    @Consumes("application/json")
    @Validate
    void postCreateEntityTest3(Openapi entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    public class PostCreateEntityResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostCreateEntityResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a openapi successfully
         * 
         * @param entity
         *     
         */
        public static CreateEntityResource.PostCreateEntityResponse withPlainOK(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new CreateEntityResource.PostCreateEntityResponse(responseBuilder.build());
        }

        /**
         * Bad request
         * 
         */
        public static CreateEntityResource.PostCreateEntityResponse withBadRequest() {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            return new CreateEntityResource.PostCreateEntityResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static CreateEntityResource.PostCreateEntityResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new CreateEntityResource.PostCreateEntityResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static CreateEntityResource.PostCreateEntityResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new CreateEntityResource.PostCreateEntityResponse(responseBuilder.build());
        }

    }

    public class PostCreateEntityTest1Response
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostCreateEntityTest1Response(Response delegate) {
            super(delegate);
        }

    }

    public class PostCreateEntityTest2Response
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostCreateEntityTest2Response(Response delegate) {
            super(delegate);
        }

    }

    public class PostCreateEntityTest3Response
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostCreateEntityTest3Response(Response delegate) {
            super(delegate);
        }

    }

    public class PostCreateEntityTestResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostCreateEntityTestResponse(Response delegate) {
            super(delegate);
        }

    }

}
