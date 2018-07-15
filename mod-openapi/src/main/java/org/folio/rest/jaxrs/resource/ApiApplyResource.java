
package org.folio.rest.jaxrs.resource;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import io.vertx.core.Context;
import org.folio.rest.annotations.Validate;
import org.folio.rest.jaxrs.model.ApplyData;
import org.folio.rest.jaxrs.model.ApplyPageQueryCondition;
import org.folio.rest.jaxrs.model.ApplyPageQueryResponse;
import org.folio.rest.jaxrs.model.ApproveData;
import org.folio.rest.jaxrs.model.AuthApplyDataLog;
import org.folio.rest.jaxrs.model.AuthorizePageQueryCondition;
import org.folio.rest.jaxrs.model.Catalog;
import org.folio.rest.jaxrs.model.CatalogResp;
import org.folio.rest.jaxrs.model.CreateResp;
import org.folio.rest.jaxrs.model.DeleteData;
import org.folio.rest.jaxrs.model.DeleteResp;
import org.folio.rest.jaxrs.model.OpenapiApply;
import org.folio.rest.jaxrs.model.RefuseData;
import org.folio.rest.jaxrs.model.TenantApplyShowClientId;

@Path("api_apply")
public interface ApiApplyResource {


    /**
     * authorize APIs for tenants
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
        "application/json",
        "text/plain"
    })
    @Validate
    void postApiApply(ApplyData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * acquire openapi by catalog
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("openapi")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postApiApplyOpenapi(Catalog entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * get OpenAPI catalog
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("openapi")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getApiApplyOpenapi(java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Process Tenant's Apply Page Query
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
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
    void postApiApplyPageQuery(ApplyPageQueryCondition entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Batch Approve Tenant's apply for OpenAPI
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
    void postApiApplyApprove(ApproveData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Refuse tenant's apply
     * 
     * @param tenantApplyId
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("refuse/{tenantApplyId}")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postApiApplyRefuseByTenantApplyId(
        @PathParam("tenantApplyId")
        @NotNull
        String tenantApplyId, RefuseData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Batch Delete tenant's apply
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("delete")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postApiApplyDelete(DeleteData entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Authorize tenant's apply
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("authorize")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postApiApplyAuthorize(AuthApplyDataLog entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * 根据tenantApplyId获取Tenant申请OpenAPI详情
     * 
     * @param tenantApplyId
     *     
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     */
    @GET
    @Path("authorize/show/{tenantApplyId}")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void getApiApplyAuthorizeShowByTenantApplyId(
        @PathParam("tenantApplyId")
        @NotNull
        String tenantApplyId, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    /**
     * Page Query of Authorize OpenAPI for Tenant
     * 
     * @param vertxContext
     *      The Vertx Context Object <code>io.vertx.core.Context</code> 
     * @param asyncResultHandler
     *     A <code>Handler<AsyncResult<Response>>></code> handler {@link io.vertx.core.Handler} which must be called as follows - Note the 'GetPatronsResponse' should be replaced with '[nameOfYourFunction]Response': (example only) <code>asyncResultHandler.handle(io.vertx.core.Future.succeededFuture(GetPatronsResponse.withJsonOK( new ObjectMapper().readValue(reply.result().body().toString(), Patron.class))));</code> in the final callback (most internal callback) of the function.
     * @param entity
     *     
     */
    @POST
    @Path("authorize/page_query")
    @Consumes("application/json")
    @Produces({
        "application/json",
        "text/plain"
    })
    @Validate
    void postApiApplyAuthorizePageQuery(AuthorizePageQueryCondition entity, java.util.Map<String, String>okapiHeaders, io.vertx.core.Handler<io.vertx.core.AsyncResult<Response>>asyncResultHandler, Context vertxContext)
        throws Exception
    ;

    public class GetApiApplyAuthorizeShowByTenantApplyIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetApiApplyAuthorizeShowByTenantApplyIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse withJsonOK(TenantApplyShowClientId entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyAuthorizeShowByTenantApplyIdResponse(responseBuilder.build());
        }

    }

    public class GetApiApplyOpenapiResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private GetApiApplyOpenapiResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.GetApiApplyOpenapiResponse withJsonOK(List<CatalogResp> entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyOpenapiResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.GetApiApplyOpenapiResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyOpenapiResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.GetApiApplyOpenapiResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyOpenapiResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.GetApiApplyOpenapiResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.GetApiApplyOpenapiResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyApproveResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyApproveResponse(Response delegate) {
            super(delegate);
        }

        /**
         * returns a updated openapidoc successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyApproveResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyApproveResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. openapidoc not found
         * 
         * @param entity
         *     openapidoc not found
         */
        public static ApiApplyResource.PostApiApplyApproveResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyApproveResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyApproveResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyApproveResponse(responseBuilder.build());
        }

        /**
         * Internal server error e.g. Internal server error, contact administrator
         * 
         * @param entity
         *     Internal server error, contact administrator
         */
        public static ApiApplyResource.PostApiApplyApproveResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyApproveResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyAuthorizePageQueryResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyAuthorizePageQueryResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyAuthorizePageQueryResponse withJsonOK(ApplyPageQueryResponse entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizePageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyAuthorizePageQueryResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizePageQueryResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.PostApiApplyAuthorizePageQueryResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizePageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyAuthorizePageQueryResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizePageQueryResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyAuthorizeResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyAuthorizeResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyAuthorizeResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizeResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyAuthorizeResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizeResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.PostApiApplyAuthorizeResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizeResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyAuthorizeResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyAuthorizeResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyDeleteResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyDeleteResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyDeleteResponse withJsonOK(DeleteResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyDeleteResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyDeleteResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyDeleteResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.PostApiApplyDeleteResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyDeleteResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyDeleteResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyDeleteResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyOpenapiResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyOpenapiResponse(Response delegate) {
            super(delegate);
        }

        /**
         * get openapi by catalog successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyOpenapiResponse withJsonOK(List<OpenapiApply> entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyOpenapiResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyOpenapiResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyOpenapiResponse(responseBuilder.build());
        }

        /**
         * Bad request
         * 
         */
        public static ApiApplyResource.PostApiApplyOpenapiResponse withBadRequest() {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            return new ApiApplyResource.PostApiApplyOpenapiResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyOpenapiResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyOpenapiResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyPageQueryResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyPageQueryResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyPageQueryResponse withJsonOK(ApplyPageQueryResponse entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyPageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyPageQueryResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyPageQueryResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.PostApiApplyPageQueryResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyPageQueryResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyPageQueryResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyPageQueryResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyRefuseByTenantApplyIdResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyRefuseByTenantApplyIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyRefuseByTenantApplyIdResponse(responseBuilder.build());
        }

    }

    public class PostApiApplyResponse
        extends org.folio.rest.jaxrs.resource.support.ResponseWrapper
    {


        private PostApiApplyResponse(Response delegate) {
            super(delegate);
        }

        /**
         * authorize APIs for tenants successfully
         * 
         * @param entity
         *     
         */
        public static ApiApplyResource.PostApiApplyResponse withJsonOK(CreateResp entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyResponse(responseBuilder.build());
        }

        /**
         * openapi not found e.g. openapi not found
         * 
         * @param entity
         *     openapi not found
         */
        public static ApiApplyResource.PostApiApplyResponse withPlainNotFound(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyResponse(responseBuilder.build());
        }

        /**
         * Bad request e.g. Fail authorize Openapi for tenant
         * 
         * @param entity
         *     Fail authorize Openapi for tenant
         */
        public static ApiApplyResource.PostApiApplyResponse withPlainBadRequest(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyResponse(responseBuilder.build());
        }

        /**
         * openapi server error e.g. openapi server error
         * 
         * @param entity
         *     openapi server error
         */
        public static ApiApplyResource.PostApiApplyResponse withPlainInternalServerError(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "text/plain");
            responseBuilder.entity(entity);
            return new ApiApplyResource.PostApiApplyResponse(responseBuilder.build());
        }

    }

}
