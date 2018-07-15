package org.folio.rest.impl;

import com.cnebula.lsp.openapi.utils.RecordLogs;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.folio.rest.jaxrs.model.*;
import org.folio.rest.jaxrs.resource.ApiApplyResource;
import org.folio.rest.persist.Criteria.*;
import org.folio.rest.persist.PostgresClient;
import org.folio.rest.persist.interfaces.Results;
import org.folio.rest.tools.messages.Messages;
import org.folio.rest.tools.utils.TenantTool;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by calis on 2017/10/9.
 */
public class ApiApplyImpl implements ApiApplyResource {

    public static final String TABLE_NAME_OPENAPI = "tb_openapi";
    public static final String TABLE_NAME_TENANT= "tb_tenantapply";
    public static final String TABLE_NAME_OPENAPIAPPLY= "tb_openapiapply";
    public static final String TABLE_NAME_CLIENTKEY = "tb_clientkey";

    private final Messages messages = Messages.getInstance();
    // private final String USER_COLLECTION = "user";
    private static final String ID_FIELD = "'id'";
    private static final String NAME_FIELD = "'username'";
    private static final String OKAPI_HEADER_TENANT = "x-okapi-tenant";
    private final Logger logger = LoggerFactory.getLogger(ApiApplyImpl.class);

    public ApiApplyImpl(Vertx vertx, String tenantId) {
        logger.info("tenantId: "+tenantId);
        PostgresClient.getInstance(vertx,tenantId).setIdField("id");
    }

    private Future<OpenapiApplyTenant> findOpenapiApplyTenant(PostgresClient postgresClient,String openapiID) throws Exception{
        Future<OpenapiApplyTenant> future = Future.<OpenapiApplyTenant>future();
        postgresClient.get(TABLE_NAME_OPENAPIAPPLY,OpenapiApplyTenant.class,"jsonb","WHERE (jsonb ->> 'id') = '"+openapiID+"'",true,false,false, reply-> {
            if (reply.succeeded()) {
                if (reply.result()!=null&&((List<OpenapiApplyTenant>) reply.result().getResults()).size()!=0){
                    List<OpenapiApplyTenant> tenants = (List<OpenapiApplyTenant>) reply.result().getResults();
                    OpenapiApplyTenant openapiApplyTenant = tenants.get(0);
                    logger.info("findOpenapiApplyTenant: success "+ openapiApplyTenant);
                    future.complete(openapiApplyTenant);
                } else {
                    logger.error("findOpenapiApplyTenant: OpenapiApplyTenant Blank ");
                    future.complete(null);
                }
            } else {
                logger.error("findOpenapiApplyTenant: Fail "+reply.cause().getMessage());
                future.fail("findOpenapiApplyTenant: Fail "+reply.cause().getMessage());
            }
        });
        return future;
    }

    private Future<List<Tenant>> findTenants(PostgresClient postgresClient,String placeholders2) throws Exception{
        Future<List<Tenant>> future = Future.<List<Tenant>>future();
        postgresClient.get(TABLE_NAME_TENANT,Tenant.class,"jsonb","WHERE (jsonb ->> 'id') IN ("+placeholders2+")",true,false,false, reply-> {
            if (reply.succeeded()) {
                if (reply.result()!=null&&((List<OpenapiInfo>) reply.result().getResults()).size()!=0){
                    List<Tenant> tenants = (List<Tenant>) reply.result().getResults();
                    logger.info("findTenants: success "+ tenants);
                    future.complete(tenants);
                } else {
                    logger.error("findTenants: Tenants Blank ");
                    future.complete(new ArrayList<>());
                }
            } else {
                logger.error("findTenants: Fail "+reply.cause().getMessage());
                future.fail("findTenants: Fail "+reply.cause().getMessage());
            }
        });
        return future;
    }

    private Future<List<String>> findOpenapis(PostgresClient postgresClient,String placeholders1) throws Exception{
        Future<List<String>> future = Future.<List<String>>future();
        postgresClient.get(TABLE_NAME_OPENAPI,OpenapiInfo.class,"jsonb","WHERE (jsonb ->> 'id') IN ("+placeholders1+")",true,false,false, reply-> {
            if (reply.succeeded()) {
                if (reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0){
                    List<OpenapiInfo> openapis = (List<OpenapiInfo>) reply.result().getResults();
                    ArrayList<String> openapiIdArray = new ArrayList<>();
                    for (OpenapiInfo o : openapis){
                        openapiIdArray.add(o.getId());
                    }
                    logger.info("findOpenapi : success "+openapis);
                    future.complete(openapiIdArray);
                } else {
                    logger.error("findOpenapi: Openapis Blank ");
                    future.complete(null);
                }
            } else {
                logger.error("findOpenapi: Fail "+reply.cause().getMessage());
                future.fail("findOpenapi: Fail "+reply.cause().getMessage());
            }
        });
        return future;
    }

    private Future<Integer> updateOpenapiApplyTenant(PostgresClient postgresClient,OpenapiApplyTenant openapiApplyTenant) throws Exception{
        Future<Integer> future = Future.<Integer>future();
        Criterion criterion = new Criterion();
        Criteria criteria = new Criteria();
        if (!StringUtils.isBlank(openapiApplyTenant.getId())){
            criteria.addField("'id'").setJSONB(true).setOperation("=").setValue(openapiApplyTenant.getId());
            criterion.addCriterion(criteria);
        }
        postgresClient.update(TABLE_NAME_OPENAPIAPPLY,openapiApplyTenant,criterion,false,reply->{
            if (reply.succeeded()&&reply.result()!=null){
                int updated = reply.result().getUpdated();
                if (updated==1){
                    logger.info("updateOpenapiApplyTenant : Update OpenapiApplyTenant success ");
                    future.complete(updated);
                } else {
                    logger.info("updateOpenapiApplyTenant : Update OpenapiApplyTenant fail ");
                    future.complete(0);
                }
            } else {
                logger.error("updateOpenapiApplyTenant : Update OpenapiApplyTenant fail ");
                future.fail("updateOpenapiApplyTenant : Update OpenapiApplyTenant fail "+reply.cause().getMessage());
            }
        });
        return future;
    }

    private Future<Boolean> insertOpenapiApplyTenant(PostgresClient postgresClient,OpenapiApplyTenant openapiApplyTenant) throws Exception{
        Future<Boolean> future = Future.<Boolean>future();
        postgresClient.save(TABLE_NAME_OPENAPIAPPLY,openapiApplyTenant,false,reply->{
            if (reply.succeeded()&&reply.result()!=null){
                logger.info("insertOpenapiApplyTenant : Insert OpenapiApplyTenant success ");
                future.complete(true);
            } else {
                logger.error("insertOpenapiApplyTenant : Insert OpenapiApplyTenant fail ");
                future.complete(false);
            }
        });
        return future;
    }

    private Future<Boolean> insertTenant(PostgresClient postgresClient,JsonObject tenantApply){
        Future<Boolean> future = Future.<Boolean>future();
        try {
            postgresClient.save(TABLE_NAME_TENANT,tenantApply,false,reply->{
                if (reply.succeeded()){
                    logger.info("insertTenant : Insert Tenant Success ");
                    future.complete(true);
                } else {
                    logger.info("insertTenant : Insert Tenant Fail ");
                    future.complete(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            future.complete(false);
        }
        return future;
    }

    private Future<Boolean> insertOpenapiApply(PostgresClient postgresClient,JsonObject tenantApply){
        Future<Boolean> future = Future.<Boolean>future();
        try {
            postgresClient.save(TABLE_NAME_TENANT,tenantApply,false,reply->{
                if (reply.succeeded()){
                    logger.info("insertTenant : Insert Tenant Success ");
                    future.complete(true);
                } else {
                    logger.info("insertTenant : Insert Tenant Fail ");
                    future.complete(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            future.complete(false);
        }
        return future;
    }

    private Future<Boolean> compositeInsertOpenapiApply(PostgresClient postgresClient,List<String> apiArray,List<String> tenantIDArray){
        Future<Boolean> future = Future.<Boolean>future();
        CompositeFuture.join(apiArray.stream().map(openapiID->{return Future.future(future2->{
            try {
                findOpenapiApplyTenant(postgresClient,openapiID).setHandler(ar->{
                    if (ar.succeeded()&&ar.result()!=null){
                        //Update
                        OpenapiApplyTenant openapiApplyTenant = ar.result();
                        List<String> tenantArray = openapiApplyTenant.getTenantArray();
                        for (String tenantID:tenantIDArray){
                            Boolean exist = true;
                            for (String tenantId:tenantArray){
                                if (tenantId.contentEquals(tenantID)){
                                    exist = false;
                                    continue;
                                }
                            }
                            if (exist){
                                tenantArray.add(tenantID);
                            }
                        }
                        try {
                            updateOpenapiApplyTenant(postgresClient,openapiApplyTenant).setHandler(ar0->{
                                if (ar0.succeeded()){
                                    logger.info("compositeInsertOpenapiApply : updateOpenapiApplyTenant success ");
                                    future2.complete();
                                } else {
                                    logger.error("compositeInsertOpenapiApply : updateOpenapiApplyTenant fail ",ar0.cause().getMessage());
                                    future2.fail("compositeInsertOpenapiApply : updateOpenapiApplyTenant fail "+ar0.cause().getMessage());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            future2.fail("compositeInsertOpenapiApply: Fail to updateOpenapiApplyTenant caused: "+message);
                        }

                    } else {
                        //Insert
                        OpenapiApplyTenant openapiApplyTenant = new OpenapiApplyTenant();
                        openapiApplyTenant.setId(openapiID);
                        openapiApplyTenant.setTenantArray(tenantIDArray);
                        try {
                            insertOpenapiApplyTenant(postgresClient,openapiApplyTenant).setHandler(ar1->{
                                if (ar1.succeeded()){
                                    logger.info("compositeInsertOpenapiApply : insertOpenapiApplyTenant success ");
                                    future2.complete();
                                } else {
                                    logger.error("compositeInsertOpenapiApply : insertOpenapiApplyTenant fail ",ar1.cause().getMessage());
                                    future2.fail("compositeInsertOpenapiApply : insertOpenapiApplyTenant fail "+ar1.cause().getMessage());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            future2.fail("compositeInsertOpenapiApply: Fail to insertOpenapiApplyTenant caused: "+message);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                future2.fail("compositeInsertOpenapiApply: Fail to findOpenapiApplyTenant caused: "+message);
            }
        });}).collect(Collectors.toList())).setHandler(ar0->{
            if (ar0.succeeded()){
                logger.info("compositeInsertOpenapiApply : success");
                future.complete(true);
            } else {
                logger.error("compositeInsertOpenapiApply : Fail");
                future.complete(false);
            }
        });
        return future;
    }

    @Override
    public void postApiApply(ApplyData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            vertxContext.runOnContext(v -> {
                String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
                PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                postgresClient.startTx(beginTx -> {
                    OpenapiApplyData openapiApplyData = entity.getOpenapiApplyData();
                    List<TenantArray> tenantArray = openapiApplyData.getTenantArray();
                    String catalog = openapiApplyData.getCatalog();
                    String applyMan = openapiApplyData.getApplyMan();
                    String applyManEmail = openapiApplyData.getApplyManEmail();
                    String applyManPhone = openapiApplyData.getApplyManPhone();
                    List<String> apiArray = openapiApplyData.getOpenapiArray();
                    String placeholders1 = apiArray.stream().map(id -> "'"+id+"'").collect(Collectors.joining(", "));
                    List<String> tenantIDArray = tenantArray.stream().map(tenant -> tenant.getId() ).collect(Collectors.toList());
                    String placeholders2 = tenantArray.stream().map(tenant -> "'"+tenant.getId()+"'").collect(Collectors.joining(", "));
                    Criteria criteria = new Criteria();
                    JsonObject tenantApply = new JsonObject();
                    tenantApply.put("catalog",catalog);
                    tenantApply.put("applyMan",applyMan);
                    tenantApply.put("applyManEmail",applyManEmail);
                    tenantApply.put("applyManPhone",applyManPhone);
                    tenantApply.put("applyDate",date);
                    compositeInsertOpenapiApply(postgresClient,apiArray,tenantIDArray).setHandler(ar->{
                        if (ar.succeeded()&&ar.result()){
                            CompositeFuture.join(tenantArray.stream().map(tenant->{return Future.future(future2->{
                                try {
                                    try {
                                        findOpenapis(postgresClient,placeholders1).compose(h ->{
                                            if (h!=null){
                                                // Insert OpenapiApply
                                                tenantApply.put("openapiArray",h);
                                                tenantApply.put("tenantID",tenant.getId());
                                                tenantApply.put("tenantName",tenant.getTenantName());
                                                tenantApply.put("status","已申请");
                                                return insertTenant(postgresClient,tenantApply);
                                            } else {
                                                return Future.<Boolean>future(ar2->{ar2.complete(false);});
                                            }
                                        }).setHandler(ar3->{
                                            if (ar3.succeeded()){
                                                logger.info("postApiApply : Insert Tenant Success ");
                                                future2.complete();
                                            } else {
                                                logger.error("postApiApply : Insert Tenant Fail ");
                                                future2.fail("postApiApply : Insert Tenant Fail "+ar3.cause().getMessage());
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        future2.fail("postApiApply: Fail to Authorize Openapi for Tenant caused: "+message);
                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withPlainInternalServerError(message)));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    future2.fail("postApiApply: Fail to Authorize Openapi for Tenant caused: "+message);
                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withPlainInternalServerError(message)));
                                }
                            });}).collect(Collectors.toList())).setHandler(ar0->{
                                if (ar0.succeeded()){
                                    try {
                                        RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("content",new JsonObject().put("operation","Authorize Openapi for Tenant").put("content",tenantApply).put("create_time",date))).setHandler(ar4->{
                                            if (ar4.succeeded()&&ar4.result()){
                                                logger.info("postApiApply : Add Openapi for Tenant success");
                                                CreateResp createResp = new CreateResp();
                                                createResp.setSuccess(true);
                                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withJsonOK(createResp)));
                                            } else {
                                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withPlainBadRequest("postApiApply: Fail to Authorize Openapi for Tenant reason: "+ar4.cause().getMessage())));
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withPlainInternalServerError(message)));
                                    }
                                } else {
                                    logger.info(ar0.cause().getMessage());
                                    CreateResp createResp = new CreateResp();
                                    createResp.setSuccess(false);
                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withJsonOK(createResp)));
                                }
                            });
                        } else {
                            logger.info(ar.cause().getMessage());
                            CreateResp createResp = new CreateResp();
                            createResp.setSuccess(false);
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withJsonOK(createResp)));
                        }
                    });
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postApiApplyOpenapi(Catalog entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        Criteria criteria = new Criteria();
        Criterion criterion = new Criterion();
        if (!StringUtils.isBlank(entity.getCatalog())){
            criteria.addField("'catalog'").setOperation("=").setValue(entity.getCatalog());
            criterion.addCriterion(criteria);
        }
        try {
            PostgresClient.getInstance(vertxContext.owner(), tenantId).get(TABLE_NAME_OPENAPI,OpenapiApply.class, criterion,true,false, reply->{
                if (reply.succeeded()&&reply.result()!=null&&((List<OpenapiApply>) reply.result().getResults()).size()!=0){
                    logger.info("getApiApplyByCatalog: get openapi success");
                    List<OpenapiApply> apis = (List<OpenapiApply>) reply.result().getResults();
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyOpenapiResponse.withJsonOK(apis)));
                } else {
                    logger.error("getApiApplyByCatalog: get openapi Fail");
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyOpenapiResponse.withPlainNotFound("Openapi context is null")));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyOpenapiResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void getApiApplyOpenapi(Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        try {
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                //postgresClient.get(TABLE_NAME_OPENAPI,);

            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetApiApplyOpenapiResponse.withPlainInternalServerError(message)));
        }
    }

    private Future<Criterion> setUpTenantID(PostgresClient postgresClient,Criterion criterion,ApplyPageQueryCondition entity,Handler<AsyncResult<Response>> asyncResultHandler) throws Exception{
        Future<Criterion> future = Future.<Criterion>future();
        if (entity != null) {
            if (!StringUtils.isBlank(entity.getApiName())) {
                Criterion criterion1 = new Criterion();
                GroupedCriterias groupedCriterias = new GroupedCriterias();
                Criteria criteria = new Criteria();
                criteria.addField("'name'").setOperation("like").setValue("%"+entity.getApiName()+"%");
                criterion1.addCriterion(criteria);
                Future<Results> future1 = Future.<Results>future();
                postgresClient.get(TABLE_NAME_OPENAPI, OpenapiInfo.class, criterion1, false, false, future1);
                future1.setHandler(reply -> {
                    if (reply.succeeded() && reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0) {
                        Criterion criterion2 = new Criterion();
                        Criteria criteria5 = new Criteria();
                        List<OpenapiInfo> openapis = (List<OpenapiInfo>) reply.result().getResults();
                        CompositeFuture.join(openapis.stream().map(openapi -> {
                            return Future.future(future2 -> {
                                criteria5.setJSONB(true).addField("'id'").setOperation("=").setValue(openapi.getId());
                                criterion2.addCriterion(criteria5);
                                try {
                                    postgresClient.get(TABLE_NAME_OPENAPIAPPLY, OpenapiApplyTenant.class, criterion2, false, false, reply1 -> {
                                        if (reply1.succeeded() && reply1.result() != null && ((List<OpenapiApplyTenant>) reply1.result().getResults()).size() != 0) {
                                            List<OpenapiApplyTenant> openapiApplyTenants = (List<OpenapiApplyTenant>) reply1.result().getResults();
                                            OpenapiApplyTenant openapiApplyTenant = openapiApplyTenants.get(0);
                                            List<String> tenantArray = openapiApplyTenant.getTenantArray();
                                            //String placeholders1 = tenantArray.stream().map(tenantID -> "'"+tenantID+"'").collect(Collectors.joining(", "));
                                            for (String tenantID : tenantArray) {
                                                Criteria criteria6 = new Criteria();
                                                criteria6.addField("'tenantID'").setOperation("=").setValue(tenantID);
                                                groupedCriterias.addCriteria(criteria6, Criteria.OP_OR);
                                            }
                                            //criterion.addGroupOfCriterias(groupedCriterias);
                                            future2.complete();
                                        } else {
                                            logger.error(reply.cause().getMessage());
                                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainBadRequest(reply.cause().getMessage())));
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    future2.fail(message);
                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainInternalServerError(message)));
                                }
                            });
                        }).collect(Collectors.toList())).setHandler(ar0 -> {
                            if (ar0.succeeded()) {
                                logger.info("postApiApplyPageQuery : Express API名称 Success");
                                logger.info(criterion.toString());
                                criterion.addGroupOfCriterias(groupedCriterias);
                                logger.info(criterion.toString());
                                future.complete(criterion);
                            } else {
                                logger.error("postApiApplyPageQuery : Express API名称 Success", ar0.cause().getMessage());
                                future.complete(null);
                            }
                        });}
                });
            } else {
                future.complete(null);
            }
        }
        return future;
    }

    private Future<Criterion> setUpTenantID2(PostgresClient postgresClient,Criterion criterion,AuthorizePageQueryCondition entity,Handler<AsyncResult<Response>> asyncResultHandler) throws Exception{
        Future<Criterion> future = Future.<Criterion>future();
        if (entity != null) {
            if (!StringUtils.isBlank(entity.getApiName())) {
                Criterion criterion1 = new Criterion();
                GroupedCriterias groupedCriterias = new GroupedCriterias();
                Criteria criteria = new Criteria();
                criteria.addField("'name'").setOperation("like").setValue("%"+entity.getApiName()+"%");
                criterion1.addCriterion(criteria);
                Future<Results> future1 = Future.<Results>future();
                postgresClient.get(TABLE_NAME_OPENAPI, OpenapiInfo.class, criterion1, false, false, future1);
                future1.setHandler(reply -> {
                    if (reply.succeeded() && reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0) {
                        Criterion criterion2 = new Criterion();
                        Criteria criteria5 = new Criteria();
                        List<OpenapiInfo> openapis = (List<OpenapiInfo>) reply.result().getResults();
                        CompositeFuture.join(openapis.stream().map(openapi -> {
                            return Future.future(future2 -> {
                                criteria5.setJSONB(true).addField("'id'").setOperation("=").setValue(openapi.getId());
                                criterion2.addCriterion(criteria5);
                                try {
                                    postgresClient.get(TABLE_NAME_OPENAPIAPPLY, OpenapiApplyTenant.class, criterion2, false, false, reply1 -> {
                                        if (reply1.succeeded() && reply1.result() != null && ((List<OpenapiApplyTenant>) reply1.result().getResults()).size() != 0) {
                                            List<OpenapiApplyTenant> openapiApplyTenants = (List<OpenapiApplyTenant>) reply1.result().getResults();
                                            OpenapiApplyTenant openapiApplyTenant = openapiApplyTenants.get(0);
                                            List<String> tenantArray = openapiApplyTenant.getTenantArray();
                                            //String placeholders1 = tenantArray.stream().map(tenantID -> "'"+tenantID+"'").collect(Collectors.joining(", "));
                                            for (String tenantID : tenantArray) {
                                                Criteria criteria6 = new Criteria();
                                                criteria6.addField("'tenantID'").setOperation("=").setValue(tenantID);
                                                groupedCriterias.addCriteria(criteria6, Criteria.OP_OR);
                                            }
                                            //criterion.addGroupOfCriterias(groupedCriterias);
                                            future2.complete();
                                        } else {
                                            logger.error(reply1.cause().getMessage());
                                            future2.fail(reply1.cause().getMessage());
                                            //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainBadRequest(reply1.cause().getMessage())));
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    future2.fail(message);
                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainInternalServerError(message)));
                                }
                            });
                        }).collect(Collectors.toList())).setHandler(ar0 -> {
                            if (ar0.succeeded()) {
                                logger.info("postApiApplyPageQuery : Express API名称 Success");
                                logger.info(criterion.toString());
                                criterion.addGroupOfCriterias(groupedCriterias);
                                logger.info(criterion.toString());
                                future.complete(criterion);
                            } else {
                                logger.error("postApiApplyPageQuery : Express API名称 Success", ar0.cause().getMessage());
                                future.complete(null);
                            }
                        });}
                });
            } else {
                future.complete(criterion);
            }
        }
        return future;
    }

    private Future<Criterion> setUpTenantID3(PostgresClient postgresClient,Criterion criterion,AuthorizePageQueryCondition entity,Handler<AsyncResult<Response>> asyncResultHandler) throws Exception{
        Future<Criterion> future = Future.<Criterion>future();
        if (entity != null) {
            Criterion criterion1 = new Criterion();
            GroupedCriterias groupedCriterias = new GroupedCriterias();
            if (!StringUtils.isBlank(entity.getCatalog())) {
                Criteria criteria = new Criteria();
                criteria.addField("'catalog'").setOperation("=").setValue(entity.getCatalog());
                criterion1.addCriterion(criteria);
            }

            if (!StringUtils.isBlank(entity.getApiName())) {
                Criteria criteria = new Criteria();
                criteria.addField("'name'").setOperation("like").setValue("%"+entity.getApiName()+"%");
                criterion1.addCriterion(criteria);
            }
            logger.info(criterion1.toString());
            /*if (criterion1.toString().trim().contentEquals("")){

            }*/
            Future<Results> future1 = Future.<Results>future();
            postgresClient.get(TABLE_NAME_OPENAPI, OpenapiInfo.class, criterion1, false, false, future1);
            future1.setHandler(reply -> {
                if (reply.succeeded() && reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0) {
                    Criterion criterion2 = new Criterion();
                    Criteria criteria5 = new Criteria();
                    List<OpenapiInfo> openapis = (List<OpenapiInfo>) reply.result().getResults();
                    GroupedCriterias groupedCriterias1 = new GroupedCriterias();

                    for (OpenapiInfo openapi_:openapis){
                        Criteria criteria1 = new Criteria();
                        criteria1.setJSONB(true).addField("'id'").setOperation("=").setValue(openapi_.getId());
                        groupedCriterias1.addCriteria(criteria1, Criteria.OP_OR);
                    }
                    criterion2.addGroupOfCriterias(groupedCriterias1);

                    try {
                        postgresClient.get(TABLE_NAME_OPENAPIAPPLY, OpenapiApplyTenant.class, criterion2, false, false, reply1 -> {
                            if (reply1.succeeded() && reply1.result() != null && ((List<OpenapiApplyTenant>) reply1.result().getResults()).size() != 0) {
                                List<OpenapiApplyTenant> openapiApplyTenants = (List<OpenapiApplyTenant>) reply1.result().getResults();
                                HashSet<String> tenantApplyIdSet = new HashSet<String>();
                                for (OpenapiApplyTenant openapiApplyTenant:openapiApplyTenants){
                                    List<String> tenantArray = openapiApplyTenant.getTenantArray();
                                    for (String tenantId:tenantArray){
                                        tenantApplyIdSet.add(tenantId);
                                    }
                                }
                                for (String tenantId:tenantApplyIdSet){
                                    Criteria criteria1 = new Criteria();
                                    criteria1.setJSONB(true).addField("'tenantID'").setOperation("=").setValue(tenantId);
                                    groupedCriterias.addCriteria(criteria1, Criteria.OP_OR);
                                }
                                criterion.addGroupOfCriterias(groupedCriterias);
                                future.complete(criterion);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        String message = e.getLocalizedMessage();
                        logger.error(message, e);
                        //future2.fail(message);
                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainInternalServerError(message)));
                    }
                } else {
                    future.complete(criterion);
                }
            });
        }
        return future;
    }


    private Future<ApplyPageQueryResponse> setUpOtherCondition(PostgresClient postgresClient,ApplyPageQueryCondition entity,Criterion criterion,Handler<AsyncResult<Response>> asyncResultHandler) throws Exception{
        Future<ApplyPageQueryResponse> future = Future.<ApplyPageQueryResponse>future();
        Integer pageNum = 1;
        Integer pageRecordCount = 10;
        if (entity != null) {
            Criteria criteria1 = new Criteria();
            Criteria criteria2 = new Criteria();
            Criteria criteria3 = new Criteria();
            Criteria criteria4 = new Criteria();
            if (!StringUtils.isBlank(entity.getTenantName())) {
                criteria1.addField("'tenantName'").setOperation("like").setValue("%"+entity.getTenantName()+"%");
                criterion.addCriterion(criteria1);
            }
            if (!StringUtils.isBlank(entity.getStatus())) {
                criteria2.addField("'status'").setOperation("=").setValue(entity.getStatus());
                criterion.addCriterion(criteria2);
            }
            if (!StringUtils.isBlank(entity.getApplydatef())) {
                criteria3.addField("'applyDate'").setOperation(">=").setValue(entity.getApplydatef());
                criterion.addCriterion(criteria3);
            }
            if (!StringUtils.isBlank(entity.getApplydatet())) {
                criteria4.addField("'applyDate'").setOperation("<=").setValue(entity.getApplydatet());
                criterion.addCriterion(criteria4);
            }
            if (entity.getOrderField() != null && entity.getOrderType().equalsIgnoreCase("ASC")) {
                Order order = new Order();
                order.asc("jsonb::json ->> '" + entity.getOrderField() + "'");
                criterion.setOrder(order);
            }
            if (entity.getOrderField() != null && entity.getOrderType().equalsIgnoreCase("DESC")) {
                Order order = new Order();
                order.desc("jsonb::json ->> '" + entity.getOrderField() + "'");
                criterion.setOrder(order);
            }
            logger.info(criterion.toString());
            pageNum = entity.getPageNum() == null || entity.getPageNum() <= 1 ? pageNum : entity.getPageNum();
            pageRecordCount = entity.getPageRecordCount() == null || entity.getPageRecordCount() <= 10 ? pageRecordCount : entity.getPageRecordCount();
            criterion.setLimit(new Limit(pageRecordCount));
            criterion.setOffset(new Offset((pageNum - 1) * pageRecordCount));
            final Integer pageNum2 = pageNum;
            final Integer pageRecordCount2 = pageRecordCount;
            postgresClient.get(TABLE_NAME_TENANT, Openapi.class, criterion,true, true, reply -> {
                if (reply.succeeded()) {
                    List<ApplyPageQueryResp> apis = (List<ApplyPageQueryResp>) reply.result().getResults();
                    ApplyPageQueryResponse openapiCollection = new ApplyPageQueryResponse();
                    openapiCollection.setPageNum(pageNum2);
                    openapiCollection.setTotalRecords((Integer) reply.result().getResultInfo().getTotalRecords());
                    openapiCollection.setApplyPageQueryResps(apis);
                    Integer totalRecordCount = (Integer) reply.result().getResultInfo().getTotalRecords();
                    openapiCollection.setPageCount(totalRecordCount % pageRecordCount2 == 0 ? totalRecordCount / pageRecordCount2 : totalRecordCount / pageRecordCount2 + 1);
                    logger.info("分页条件查询成功");
                    future.complete(openapiCollection);
                    //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withJsonOK(openapiCollection)));
                } else {
                    logger.error(400, "分页条件查询失败");
                    future.complete(null);
                    //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainBadRequest("分页条件查询失败" + reply.cause().getMessage())));
                }
            });
        }
        return future;
    }

    private Future<ApplyPageQueryResponse> setUpOtherCondition2(PostgresClient postgresClient,AuthorizePageQueryCondition entity,Criterion criterion,Handler<AsyncResult<Response>> asyncResultHandler) throws Exception{
        Future<ApplyPageQueryResponse> future = Future.<ApplyPageQueryResponse>future();
        Integer pageNum = 1;
        Integer pageRecordCount = 10;
        if (entity != null) {
            Criteria criteria1 = new Criteria();
            Criteria criteria2 = new Criteria();
            Criteria criteria3 = new Criteria();
            Criteria criteria4 = new Criteria();
            Criteria criteria5 = new Criteria();
            if (!StringUtils.isBlank(entity.getTenantName())) {
                criteria1.addField("'tenantName'").setOperation("like").setValue("%"+entity.getTenantName()+"%");
                criterion.addCriterion(criteria1);
            }

            if (!StringUtils.isBlank(entity.getCatalog())) {
                criteria5.addField("'catalog'").setOperation("=").setValue(entity.getCatalog());
                criterion.addCriterion(criteria5);
            }

            criteria2.addField("'status'").setOperation("=").setValue("已授权");
            criterion.addCriterion(criteria2);

            if (!StringUtils.isBlank(entity.getAuthdatef())) {
                criteria3.addField("'authDate'").setOperation(">=").setValue(entity.getAuthdatef());
                criterion.addCriterion(criteria3);
            }
            if (!StringUtils.isBlank(entity.getAuthdatet())) {
                criteria4.addField("'authDate'").setOperation("<=").setValue(entity.getAuthdatet());
                criterion.addCriterion(criteria4);
            }
            if (entity.getOrderField() != null && entity.getOrderType().equalsIgnoreCase("ASC")) {
                Order order = new Order();
                order.asc("jsonb::json ->> '" + entity.getOrderField() + "'");
                criterion.setOrder(order);
            }
            if (entity.getOrderField() != null && entity.getOrderType().equalsIgnoreCase("DESC")) {
                Order order = new Order();
                order.desc("jsonb::json ->> '" + entity.getOrderField() + "'");
                criterion.setOrder(order);
            }
            logger.info(criterion.toString());
            pageNum = entity.getPageNum() == null || entity.getPageNum() <= 1 ? pageNum : entity.getPageNum();
            pageRecordCount = entity.getPageRecordCount() == null || entity.getPageRecordCount() <= 10 ? pageRecordCount : entity.getPageRecordCount();
            criterion.setLimit(new Limit(pageRecordCount));
            criterion.setOffset(new Offset((pageNum - 1) * pageRecordCount));
            final Integer pageNum2 = pageNum;
            final Integer pageRecordCount2 = pageRecordCount;
            postgresClient.get(TABLE_NAME_TENANT, Openapi.class, criterion,true, true, reply -> {
                if (reply.succeeded()) {
                    List<ApplyPageQueryResp> apis = (List<ApplyPageQueryResp>) reply.result().getResults();
                    ApplyPageQueryResponse openapiCollection = new ApplyPageQueryResponse();
                    openapiCollection.setPageNum(pageNum2);
                    openapiCollection.setTotalRecords((Integer) reply.result().getResultInfo().getTotalRecords());
                    openapiCollection.setApplyPageQueryResps(apis);
                    Integer totalRecordCount = (Integer) reply.result().getResultInfo().getTotalRecords();
                    openapiCollection.setPageCount(totalRecordCount % pageRecordCount2 == 0 ? totalRecordCount / pageRecordCount2 : totalRecordCount / pageRecordCount2 + 1);
                    logger.info("分页条件查询成功");
                    future.complete(openapiCollection);
                    //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withJsonOK(openapiCollection)));
                } else {
                    logger.error(400, "分页条件查询失败");
                    future.complete(null);
                    //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainBadRequest("分页条件查询失败" + reply.cause().getMessage())));
                }
            });
        }
        return future;
    }

    private Future<Void> findOpenapiNameById(PostgresClient postgresClient,ApplyPageQueryResponse openapiCollection,Handler<AsyncResult<Response>> asyncResultHandler){
        Future<Void> future = Future.<Void>future();
        List<ApplyPageQueryResp> openapis = openapiCollection.getApplyPageQueryResps();
        List<ApplyPageQueryResp> openapis1 = new ArrayList<>();
        CompositeFuture.join(openapis.stream().map(openapi -> {
            return Future.future(future2 -> {
                ApplyPageQueryResp openapi1 = openapi;
                List<String> openapiIDArray = openapi.getOpenapiArray();
                ArrayList<String> openapiNameArray = new ArrayList<>();
                CompositeFuture.join(openapiIDArray.stream().map(openapiID -> {
                    return Future.future(future3 -> {
                        Criterion criterion = new Criterion();
                        if (!StringUtils.isBlank(openapiID)) {
                            Criteria criteria = new Criteria();
                            criteria.addField("'id'").setOperation("=").setValue(openapiID);
                            criterion.addCriterion(criteria);
                        }
                        try {
                            postgresClient.get(TABLE_NAME_OPENAPI,OpenapiInfo.class,criterion,false,reply->{
                                if (reply.succeeded() && reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0) {
                                    List<OpenapiInfo> openapiArray = (List<OpenapiInfo>) reply.result().getResults();
                                    for (OpenapiInfo openapi_:openapiArray){
                                        openapiNameArray.add(openapi_.getName());
                                    }
                                    //openapi1.setOpenapiArray(openapiNameArray);
                                    future3.complete();
                                } else {
                                    logger.error("findOpenapiNameById: Error",reply.cause().getMessage());
                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainBadRequest(reply.cause().getMessage())));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            future3.fail(message);
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainInternalServerError(message)));
                        }
                    });
                }).collect(Collectors.toList())).setHandler(ar0 -> {
                    if (ar0.succeeded()){
                        logger.info("findOpenapiNameById : ID为"+openapi.getId()+"换取名称成功");
                        openapi1.setOpenapiArray(openapiNameArray);
                        openapis1.add(openapi1);
                        future2.complete();
                    } else {
                        logger.error("findOpenapiNameById : ID为"+openapi.getId()+"换取名称失败,原因：",ar0.cause().getMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainBadRequest("分页条件查询失败" + ar0.cause().getMessage())));
                        future2.fail("findOpenapiNameById : ID为"+openapi.getId()+"换取名称失败,原因："+ar0.cause().getMessage());
                    }
                });

            });

        }).collect(Collectors.toList())).setHandler(ar0 -> {
            if (ar0.succeeded()){
                logger.info("findOpenapiNameById : 所有OpenAPI换取名称成功");
                openapiCollection.setApplyPageQueryResps(openapis1);
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withJsonOK(openapiCollection)));
                future.complete();
            } else {
                logger.error("findOpenapiNameById : 所有OpenAPI换取名称失败,原因：",ar0.cause().getMessage());
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainBadRequest("分页条件查询失败" + ar0.cause().getMessage())));
                future.fail("findOpenapiNameById : 所有OpenAPI换取名称失败,原因："+ar0.cause().getMessage());
            }
        });
        return future;
    }

    private Future<Void> findOpenapiNameById2(PostgresClient postgresClient,ApplyPageQueryResponse openapiCollection,Handler<AsyncResult<Response>> asyncResultHandler){
        Future<Void> future = Future.<Void>future();
        List<ApplyPageQueryResp> openapis = openapiCollection.getApplyPageQueryResps();
        List<ApplyPageQueryResp> openapis1 = new ArrayList<>();
        CompositeFuture.join(openapis.stream().map(openapi -> {
            return Future.future(future2 -> {
                ApplyPageQueryResp openapi1 = openapi;
                List<String> openapiIDArray = openapi.getOpenapiArray();
                ArrayList<String> openapiNameArray = new ArrayList<>();
                CompositeFuture.join(openapiIDArray.stream().map(openapiID -> {
                    return Future.future(future3 -> {
                        Criterion criterion = new Criterion();
                        if (!StringUtils.isBlank(openapiID)) {
                            Criteria criteria = new Criteria();
                            criteria.addField("'id'").setOperation("=").setValue(openapiID);
                            criterion.addCriterion(criteria);
                        }
                        try {
                            postgresClient.get(TABLE_NAME_OPENAPI,OpenapiInfo.class,criterion,false,reply->{
                                if (reply.succeeded() && reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0) {
                                    List<OpenapiInfo> openapiArray = (List<OpenapiInfo>) reply.result().getResults();
                                    for (OpenapiInfo openapi_:openapiArray){
                                        openapiNameArray.add(openapi_.getName());
                                    }
                                    //openapi1.setOpenapiArray(openapiNameArray);
                                    future3.complete();
                                } else {
                                    logger.error("findOpenapiNameById: Error",reply.cause().getMessage());
                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainBadRequest(reply.cause().getMessage())));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            //future3.fail(message);
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainInternalServerError(message)));
                        }
                    });
                }).collect(Collectors.toList())).setHandler(ar0 -> {
                    if (ar0.succeeded()){
                        logger.info("findOpenapiNameById : ID为"+openapi.getId()+"换取名称成功");
                        openapi1.setOpenapiArray(openapiNameArray);
                        openapis1.add(openapi1);
                        future2.complete();
                    } else {
                        logger.error("findOpenapiNameById : ID为"+openapi.getId()+"换取名称失败,原因：",ar0.cause().getMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainBadRequest("分页条件查询失败" + ar0.cause().getMessage())));
                        future2.fail("findOpenapiNameById : ID为"+openapi.getId()+"换取名称失败,原因："+ar0.cause().getMessage());
                    }
                });

            });

        }).collect(Collectors.toList())).setHandler(ar0 -> {
            if (ar0.succeeded()){
                logger.info("findOpenapiNameById : 所有OpenAPI换取名称成功");
                openapiCollection.setApplyPageQueryResps(openapis1);
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withJsonOK(openapiCollection)));
                future.complete();
            } else {
                logger.error("findOpenapiNameById : 所有OpenAPI换取名称失败,原因：",ar0.cause().getMessage());
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainBadRequest("分页条件查询失败" + ar0.cause().getMessage())));
                future.fail("findOpenapiNameById : 所有OpenAPI换取名称失败,原因："+ar0.cause().getMessage());
            }
        });
        return future;
    }


    @Override
    public void postApiApplyPageQuery(ApplyPageQueryCondition entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        try {
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                try {
                    setUpTenantID(postgresClient,criterion,entity,asyncResultHandler).setHandler(ar->{
                        if (ar.result()!=null){
                            try {
                                setUpOtherCondition(postgresClient,entity,ar.result(),asyncResultHandler).setHandler(result->{
                                    findOpenapiNameById(postgresClient,result.result(),asyncResultHandler);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                setUpOtherCondition(postgresClient,entity,new Criterion(),asyncResultHandler).setHandler(result->{
                                    findOpenapiNameById(postgresClient,result.result(),asyncResultHandler);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postApiApplyApprove(ApproveData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            vertxContext.runOnContext(v -> {
                try {
                    List<String> idArray = entity.getIdArray();
                    CompositeFuture.join(idArray.stream().map(tenantApplyId -> {
                        return Future.future(future2 -> {
                            Criterion criterion = new Criterion();
                            if (!StringUtils.isBlank(tenantApplyId)) {
                                Criteria criteria = new Criteria();
                                criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+tenantApplyId+"'");
                                criterion.addCriterion(criteria);
                            }
                            try {
                                postgresClient.get(TABLE_NAME_TENANT,TenantApplyTB2Entity.class,criterion,false,false,reply->{
                                    if (reply.succeeded()) {
                                        if (reply.result() != null && ((List<TenantApplyTB2Entity>) reply.result().getResults()).size() != 0){
                                            logger.info("postApiApplyApprove: find TenantApply Success");
                                            List<TenantApplyTB2Entity> tenantApplyTB2Entities = (List<TenantApplyTB2Entity>) reply.result().getResults();
                                            TenantApplyTB2Entity tenantApplyTB2Entity = tenantApplyTB2Entities.get(0);
                                            try {
                                                tenantApplyTB2Entity.setStatus("已批准");
                                                tenantApplyTB2Entity.setApproveDate(date);
                                                Criteria criteria = new Criteria();
                                                criteria.setJSONB(true).addField("'status'").setOperation("=").setValue("已申请");
                                                criterion.addCriterion(criteria);
                                                postgresClient.update(TABLE_NAME_TENANT,tenantApplyTB2Entity,criterion,true,reply0->{
                                                    if (reply0.succeeded()){
                                                        if (reply0.result().getUpdated()!=0){
                                                            logger.info("postApiApplyApprove : update TenantApply success");
                                                            future2.complete();
                                                        } else {
                                                            logger.error("postApiApplyApprove : update TenantApply Fail");
                                                            future2.fail("update TenantApply Fail,请查看被批准的OpenAPI申请信息："+tenantApplyId+" 状态为‘已申请’");
                                                        }
                                                    } else {
                                                        logger.error("postApiApplyApprove : update TenantApply Error , caused: ",reply0.cause().getMessage());
                                                        future2.fail("postApiApplyApprove : update TenantApply Error , caused: "+reply0.cause().getMessage());
                                                        //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withJsonOK(createResp)));
                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                String message = e.getLocalizedMessage();
                                                logger.error(message, e);
                                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainInternalServerError(message)));
                                                //future2.fail("postApiApplyApprove : update TenantApply success , caused: "+reply.cause().getMessage());
                                            }
                                        } else {
                                            logger.error("postApiApplyApprove: 该次: "+tenantApplyId+" Tenant申请的OpenAPI信息为空");
                                            future2.fail("该次: "+tenantApplyId+" Tenant申请的OpenAPI信息为空");
                                        }
                                    } else {
                                        logger.error("postApiApplyApprove: find TenantApply Error",reply.cause().getMessage());
                                        future2.fail("postApiApplyApprove: find TenantApply Error"+reply.cause().getMessage());
                                        //asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainBadRequest(reply.cause().getMessage())));
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainInternalServerError(message)));
                                //future2.fail("postApiApplyApprove: find TenantApply Error"+message);
                            }
                        });
                    }).collect(Collectors.toList())).setHandler(ar0 -> {
                        if (ar0.succeeded()){
                            try {
                                RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","批准租客该次申请的OpenAPI").put("content",entity.getIdArray()).put("create_time",date)).setHandler(ar1->{
                                    if (ar1.succeeded()&&ar1.result()){
                                        logger.info(" 批量批准租客该次申请的OpenAPI成功");
                                        CreateResp createResp = new CreateResp();
                                        createResp.setSuccess(true);
                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withJsonOK(createResp)));
                                    } else {
                                        CreateResp createResp = new CreateResp();
                                        createResp.setSuccess(false);
                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withJsonOK(createResp)));
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainInternalServerError(message)));
                            }
                        } else {
                            logger.info(" 批量批准租客该次申请的OpenAPI失败");
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainNotFound(ar0.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyApproveResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postApiApplyRefuseByTenantApplyId(String tenantApplyId, RefuseData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            vertxContext.runOnContext(v -> {
                try {
                    Criterion criterion = new Criterion();
                    if (!StringUtils.isBlank(tenantApplyId)) {
                        Criteria criteria = new Criteria();
                        criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+tenantApplyId+"'");
                        criterion.addCriterion(criteria);
                    }
                    postgresClient.get(TABLE_NAME_TENANT,TenantApplyTB2Entity.class,criterion,false,false,reply->{
                        if (reply.succeeded() && reply.result() != null && ((List<TenantApplyTB2Entity>) reply.result().getResults()).size() != 0) {
                            logger.info("postApiApplyRefuseByTenantApplyId: find TenantApply Success");
                            List<TenantApplyTB2Entity> tenantApplyTB2Entities = (List<TenantApplyTB2Entity>) reply.result().getResults();
                            TenantApplyTB2Entity tenantApplyTB2Entity = tenantApplyTB2Entities.get(0);
                            try {
                                tenantApplyTB2Entity.setStatus("已拒绝");
                                tenantApplyTB2Entity.setRefuseDate(date);
                                tenantApplyTB2Entity.setRefuseReason(entity.getReason());
                                postgresClient.update(TABLE_NAME_TENANT,tenantApplyTB2Entity,criterion,true,reply0->{
                                    if (reply0.succeeded()&&reply0.result().getUpdated()!=0){
                                        logger.info("postApiApplyRefuseByTenantApplyId : update TenantApply success");
                                        try {
                                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","拒绝租客该次申请的OpenAPI").put("content",JsonObject.mapFrom(tenantApplyTB2Entity).put("create_time",date))).setHandler(ar0->{
                                                if (ar0.succeeded()&&ar0.result()){
                                                    CreateResp createResp = new CreateResp();
                                                    createResp.setSuccess(true);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withJsonOK(createResp)));
                                                } else {
                                                    CreateResp createResp = new CreateResp();
                                                    createResp.setSuccess(false);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withJsonOK(createResp)));
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            String message = e.getLocalizedMessage();
                                            logger.error(message, e);
                                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withPlainInternalServerError(message)));
                                        }
                                    } else {
                                        logger.error("postApiApplyRefuseByTenantApplyId : update TenantApply success , caused: ",reply0.cause().getMessage());
                                        CreateResp createResp = new CreateResp();
                                        createResp.setSuccess(false);
                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withJsonOK(createResp)));
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withPlainInternalServerError(message)));
                            }
                        } else {
                            logger.error("postApiApplyRefuseByTenantApplyId: find TenantApply Error",reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withPlainBadRequest(reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyRefuseByTenantApplyIdResponse.withPlainInternalServerError(message)));
        }
    }

    private Future<ArrayEntity> findNotExistIds(PostgresClient postgresClient,List<String> idArray,Handler<AsyncResult<Response>> asyncResultHandler) {
        Future<ArrayEntity> future = Future.<ArrayEntity>future();
        ArrayEntity arrayEntity = new ArrayEntity();
        List<String> existIdArray = new ArrayList<String>();
        List<String> notExistIdArray = new ArrayList<String>();
        CompositeFuture.join(idArray.stream().map(id -> {
            return Future.future(future2 -> {
                Criterion criterion = new Criterion();
                if (!StringUtils.isBlank(id)) {
                    Criteria criteria = new Criteria();
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterion.addCriterion(criteria);
                }
                if (!criterion.toString().trim().contentEquals("")){
                    try {
                        postgresClient.get(TABLE_NAME_TENANT,TenantApplyTB2Entity.class,criterion,true,true,reply->{
                            if (reply.succeeded()){
                                logger.info("findNotExistIds: 查询Id是否存在成功");
                                if (reply.result() != null && ((List<TenantApplyTB2Entity>) reply.result().getResults()).size() != 0){
                                    logger.info("checkNotExistIds: 查询ID: "+id+" Tenant申请OpenAPI信息存在");
                                    existIdArray.add(id);
                                    future2.complete();
                                } else {
                                    logger.error("checkNotExistIds: 查询ID: "+id+" Tenant申请OpenAPI信息不存在");
                                    notExistIdArray.add(id);
                                    future2.complete();
                                }
                            } else {
                                logger.error("findNotExistIds: 查询Id是否存在失败,Caused: "+reply.cause().getMessage());
                                future2.fail("查询Id是否存在失败,Caused: "+reply.cause().getMessage());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        String message = e.getLocalizedMessage();
                        logger.error(message, e);
                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainInternalServerError(message)));
                    }
                } else {
                    logger.error("findNotExistIds: id不能为空");
                    future2.fail("id不能为空");
                }
            });
        }).collect(Collectors.toList())).setHandler(ar0 -> {
            if (ar0.succeeded()){
                logger.info("findNotExistIds: 查询全部Id是否存在成功");
                arrayEntity.setExistIdArray(existIdArray);
                arrayEntity.setNotExistIdArray(notExistIdArray);
                future.complete(arrayEntity);
            } else {
                logger.error("findNotExistIds: 查询全部Id是否存在失败");
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("查询全部Id是否存在失败,Caused: "+ar0.cause().getMessage())));
            }
        });
        return future;
    }

    private Future<Boolean> deleteExistIds(PostgresClient postgresClient,List<String> existIdArray,Handler<AsyncResult<Response>> asyncResultHandler){
        Future<Boolean> future = Future.<Boolean>future();
        Criterion criterion = new Criterion();
        GroupedCriterias groupedCriterias = new GroupedCriterias();
        for (String id : existIdArray) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
            groupedCriterias.addCriteria(criteria, Criteria.OP_OR);
        }
        criterion.addGroupOfCriterias(groupedCriterias);
        if (!criterion.toString().trim().contentEquals("")){
            try {
                postgresClient.delete(TABLE_NAME_TENANT,criterion,reply->{
                    if (reply.succeeded()){
                        logger.info("checkNotExistIds: 删除存在的IdArray: "+Arrays.toString(existIdArray.toArray())+" 成功");
                        if (reply.result() != null && reply.result().getUpdated()==existIdArray.size()){
                            logger.info("checkNotExistIds: 删除存在的IdArray全部成功");
                            future.complete(true);
                        } else {
                            logger.info("checkNotExistIds: 删除存在的IdArray没有全部成功");
                            future.complete(false);
                        }
                    } else {
                        logger.error("checkNotExistIds: 删除存在的IdArray: "+Arrays.toString(existIdArray.toArray())+" 成功,Caused: "+reply.cause().getMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("删除存在的IdArray: "+Arrays.toString(existIdArray.toArray())+" 成功,Caused: "+reply.cause().getMessage())));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainInternalServerError(message)));
            }
        } else {
            logger.error("checkNotExistIds:  传入的Id不能为空");
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("传入的Id不能为空")));
        }
        return future;
    }

    @Override
    public void postApiApplyDelete(DeleteData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                List<String> idArray = entity.getIdArray();
                if (idArray!=null&&idArray.size()!=0){
                    findNotExistIds(postgresClient, idArray, asyncResultHandler).setHandler(arrayEntiy -> {
                        if (arrayEntiy.succeeded()) {
                            logger.info("判断Id是否存在成功");
                            if (arrayEntiy.result().getExistIdArray().size() != 0) {
                                deleteExistIds(postgresClient, arrayEntiy.result().getExistIdArray(), asyncResultHandler).setHandler(deleted -> {
                                    if (deleted.succeeded()) {
                                        DeleteResp deleteResp = new DeleteResp();
                                        if (arrayEntiy.result().getNotExistIdArray().size()!=0){
                                            deleteResp.setNotExistIdArray(arrayEntiy.result().getNotExistIdArray());
                                        }
                                        if (deleted.result()) {
                                            try {
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                String date = simpleDateFormat.format(new Date());
                                                RecordLogs.recordLogs(postgresClient, new JsonObject().put("log", JsonObject.mapFrom(entity.getLog())).put("operation", "批量删除租客该次申请的OpenAPI").put("content", entity.getIdArray()).put("create_time", date)).setHandler(ar1 -> {
                                                    if (ar1.succeeded() && ar1.result()) {
                                                        logger.info("批量删除租客该次申请的OpenAPI成功");
                                                        deleteResp.setSuccess(true);
                                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withJsonOK(deleteResp)));
                                                    } else {
                                                        deleteResp.setSuccess(false);
                                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withJsonOK(deleteResp)));
                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                String message = e.getLocalizedMessage();
                                                logger.error(message, e);
                                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainInternalServerError(message)));
                                            }
                                        } else {
                                            deleteResp.setSuccess(false);
                                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("删除已经存在的IdArray: " + JsonObject.mapFrom(deleteResp) + "没有全部成功")));
                                        }
                                    } else {
                                        logger.error("postApiApplyDelete: deleteExistIds出现错误,Caused: "+ deleted.cause().getMessage());
                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("删除已经存在的IdArray: " + Arrays.toString(arrayEntiy.result().getExistIdArray().toArray()) + "出现错误,Caused: " + deleted.cause().getMessage())));
                                    }
                                });
                            } else if (arrayEntiy.result().getNotExistIdArray().size() == idArray.size()) {
                                logger.error("postApiApplyDelete: findNotExistIds所有Id: " + Arrays.toString(arrayEntiy.result().getNotExistIdArray().toArray()) + " 都不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainNotFound("所有Id: " + Arrays.toString(arrayEntiy.result().getNotExistIdArray().toArray()) + " 都不存在")));
                            }
                        } else {
                            logger.error("postApiApplyDelete: findNotExistIds 判断Id是否存在出现错误,Caused: "+ arrayEntiy.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("判断Id是否存在出现错误,Caused: " + arrayEntiy.cause().getMessage())));
                        }
                    });
                } else {
                    logger.error("postApiApplyDelete: 需要删除的ID不能为空");
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainBadRequest("需要删除的idArray不能为空")));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyDeleteResponse.withPlainInternalServerError(message)));
        }
    }

    private Future<Boolean> checkClientSecretExist(PostgresClient postgresClient,String tenantID,String clientSecret,Handler<AsyncResult<Response>> asyncResultHandler){
        Future<Boolean> future = Future.<Boolean>future();
        Criterion criterion = new Criterion();
        if (!StringUtils.isBlank(tenantID)) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(true).addField("'tenantID'").setOperation("=").setValue(tenantID);
            criterion.addCriterion(criteria);
        }
        if (!StringUtils.isBlank(clientSecret)) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(true).addField("'clientSecret'").setOperation("=").setValue(clientSecret);
            criterion.addCriterion(criteria);
        }
        if (!StringUtils.isBlank(clientSecret)) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(true).addField("'status'").setOperation("=").setValue("ClientKey已发放");
            criterion.addCriterion(criteria);
        }
        if (!criterion.toString().trim().contentEquals("")){
            try {
                postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,true,false,reply->{
                    if (reply.succeeded()) {
                        if (reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0){
                            future.complete(true);
                        } else {
                            logger.error("checkClientSecretExist: tenantID or clientSecret 不存在");
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainNotFound("tenantID or clientSecret 不存在")));
                        }
                    } else {
                        logger.error("checkClientSecretExist: 查询clientSecret是否存在出现异常",reply.cause().getMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainBadRequest("查询clientSecret是否存在出现异常,Caused: "+reply.cause().getMessage())));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainInternalServerError(message)));
            }
        } else {
            logger.error("checkClientSecretExist: tenantID or clientSecret can not be blank");
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainBadRequest("tenantID or clientSecret can not be blank")));
        }
        return future;
    }

    @Override
    public void postApiApplyAuthorize(AuthApplyDataLog entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                if (entity.getAuthApplyData()!=null&&!StringUtils.isBlank(entity.getAuthApplyData().getId())) {
                    Criteria criteria = new Criteria();
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+entity.getAuthApplyData().getId()+"'");
                    criterion.addCriterion(criteria);
                }
                try {
                    findTenantApplyInfoById(entity.getAuthApplyData().getId(),postgresClient).setHandler(ar->{
                        if (ar.succeeded()){
                            if (ar.result()!=null){
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date = simpleDateFormat.format(new Date());
                                TenantApplyTB2Entity tenantApplyTB2Entity = ar.result();
                                checkClientSecretExist(postgresClient,tenantApplyTB2Entity.getTenantID(),tenantApplyTB2Entity.getClientSecret(),asyncResultHandler).setHandler(exist->{
                                    if (exist.succeeded()&&exist.result()){
                                        tenantApplyTB2Entity.setStatus("已授权");
                                        tenantApplyTB2Entity.setClientSecret(entity.getAuthApplyData().getClientSecret());
                                        tenantApplyTB2Entity.setAuthDate(date);
                                        try {
                                            postgresClient.update(TABLE_NAME_TENANT,tenantApplyTB2Entity,criterion,true,reply->{
                                                if (reply.succeeded()){
                                                    if (reply.result().getUpdated()!=0){
                                                        logger.info("postApiApplyAuthorize : 授权Tenant该次申请OpenAPI成功");
                                                        try {
                                                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","授权Tenant该次申请OpenAPI成功").put("content",JsonObject.mapFrom(entity.getAuthApplyData()).put("create_time",date))).setHandler(ar0->{
                                                                if (ar0.succeeded()&&ar0.result()){
                                                                    CreateResp createResp = new CreateResp();
                                                                    createResp.setSuccess(true);
                                                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withJsonOK(createResp)));
                                                                } else {
                                                                    CreateResp createResp = new CreateResp();
                                                                    createResp.setSuccess(false);
                                                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withJsonOK(createResp)));
                                                                }
                                                            });
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                            String message = e.getLocalizedMessage();
                                                            logger.error(message, e);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainInternalServerError(message)));
                                                        }
                                                    } else {
                                                        logger.error("postApiApplyAuthorize: 授权Tenant该次申请OpenAPI失败，该次申请信息不存在");
                                                        asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainNotFound("授权Tenant该次申请OpenAPI失败，该次申请信息不存在")));
                                                    }
                                                } else {
                                                    logger.error("postApiApplyAuthorize: 授权Tenant该次申请OpenAPI失败");
                                                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainBadRequest("授权Tenant该次申请OpenAPI失败,Caused: "+reply.cause().getMessage())));
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            String message = e.getLocalizedMessage();
                                            logger.error(message, e);
                                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainInternalServerError(message)));
                                        }
                                    }
                                });
                            } else {
                                logger.error("postApiApplyAuthorize: 根据id查询出来的Tenant申请的OpenAPI信息为空");
                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainNotFound("根据id查询出来的Tenant申请的OpenAPI信息为空")));
                            }
                        } else {
                            logger.error("postApiApplyAuthorize: findTenantApplyInfoById 查询出现异常");
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainBadRequest("postApiApplyAuthorize: findTenantApplyInfoById 查询出现异常,Caused: "+ar.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizeResponse.withPlainInternalServerError(message)));
        }
    }

    private Future<TenantApplyShowClientId> findOpenapiNameById(PostgresClient postgresClient,TenantApplyShowClientId tenantApplyTB2Entity,Handler<AsyncResult<Response>> asyncResultHandler){
        Future<TenantApplyShowClientId> future = Future.<TenantApplyShowClientId>future();
        List<String> openapiIDArray = tenantApplyTB2Entity.getOpenapiArray();
        List<String> openapiNameArray = new ArrayList<>();
        CompositeFuture.join(openapiIDArray.stream().map(openapiID -> {
            return Future.future(future3 -> {
                Criterion criterion = new Criterion();
                if (!StringUtils.isBlank(openapiID)) {
                    Criteria criteria = new Criteria();
                    criteria.addField("'id'").setOperation("=").setValue(openapiID);
                    criterion.addCriterion(criteria);
                }
                try {
                    postgresClient.get(TABLE_NAME_OPENAPI,OpenapiInfo.class,criterion,false,reply->{
                        if (reply.succeeded() && reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0) {
                            List<OpenapiInfo> openapiArray = (List<OpenapiInfo>) reply.result().getResults();
                            for (OpenapiInfo openapi_:openapiArray){
                                openapiNameArray.add(openapi_.getName());
                            }
                            //openapi1.setOpenapiArray(openapiNameArray);
                            future3.complete();
                        } else {
                            logger.error("findOpenapiNameById: Error",reply.cause().getMessage());
                            future3.fail("findOpenapiNameById: Error, Caused: "+reply.cause().getMessage());
                            //asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest(reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    //future3.fail(message);
                    asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainInternalServerError(message)));
                }
            });
        }).collect(Collectors.toList())).setHandler(ar0 -> {
            if (ar0.succeeded()){
                logger.info("findOpenapiNameById : 根据OpenAPI ID 换取名称全部成功 Success");
                tenantApplyTB2Entity.setOpenapiArray(openapiNameArray);
                future.complete(tenantApplyTB2Entity);
                //asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withJsonOK(tenantApplyTB2Entity)));
            } else {
                logger.error("findOpenapiNameById : 根据OpenAPI ID 换取名称全部失败 Fail");
                future.complete(null);
                //asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest(ar0.cause().getMessage())));
            }
        });
        return future;
    }

    private Future<TenantApplyShowClientId> findTenantApplyInfoById2(String tenantApplyId,PostgresClient postgresClient,Handler<AsyncResult<Response>> asyncResultHandler) throws Exception {
        Future<TenantApplyShowClientId> future = Future.<TenantApplyShowClientId>future();
        Criterion criterion = new Criterion();
        if (!StringUtils.isBlank(tenantApplyId)) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+tenantApplyId+"'");
            criterion.addCriterion(criteria);
        }
        Criteria criteria = new Criteria();
        criteria.setJSONB(true).addField("'status'").setOperation("=").setValue("已批准");
        criterion.addCriterion(criteria);
        logger.info(criterion.toString());
        if (!criterion.toString().trim().contentEquals("")){
            postgresClient.get(TABLE_NAME_TENANT,TenantApplyShowClientId.class,criterion,false,true,reply-> {
                if (reply.succeeded()) {
                    if (reply.result() != null && ((List<TenantApplyShowClientId>) reply.result().getResults()).size() != 0) {
                        logger.info("getApiApplyAuthorizeShowByTenantApplyId: find TenantApply Success");
                        List<TenantApplyShowClientId> tenantApplyTB2Entities = (List<TenantApplyShowClientId>) reply.result().getResults();
                        TenantApplyShowClientId tenantApplyTB2Entity = tenantApplyTB2Entities.get(0);
                        future.complete(tenantApplyTB2Entity);
                    } else {
                        future.complete(null);
                    }
                } else {
                    throw new RuntimeException(reply.cause().getMessage());
                }
            });
        } else {
            future.complete(null);
        }
        return future;
    }

    private Future<TenantApplyTB2Entity> findTenantApplyInfoById(String tenantApplyId,PostgresClient postgresClient) throws Exception {
        Future<TenantApplyTB2Entity> future = Future.<TenantApplyTB2Entity>future();
        Criterion criterion = new Criterion();
        if (!StringUtils.isBlank(tenantApplyId)) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+tenantApplyId+"'");
            criterion.addCriterion(criteria);
        }
        if (!criterion.toString().trim().contentEquals("")){
            postgresClient.get(TABLE_NAME_TENANT,TenantApplyTB2Entity.class,criterion,false,false,reply-> {
                if (reply.succeeded()) {
                    if (reply.result() != null && ((List<TenantApplyTB2Entity>) reply.result().getResults()).size() != 0) {
                        logger.info("getApiApplyAuthorizeShowByTenantApplyId: find TenantApply Success");
                        List<TenantApplyTB2Entity> tenantApplyTB2Entities = (List<TenantApplyTB2Entity>) reply.result().getResults();
                        TenantApplyTB2Entity tenantApplyTB2Entity = tenantApplyTB2Entities.get(0);
                        future.complete(tenantApplyTB2Entity);
                    } else {
                        future.complete(null);
                    }
                } else {
                    throw new RuntimeException(reply.cause().getMessage());
                }
            });
        } else {
            future.complete(null);
        }
        return future;
    }

    private void findRespClientIds(TenantApplyShowClientId tenantApplyTB2Entity,PostgresClient postgresClient,Handler<AsyncResult<Response>> asyncResultHandler){
        Criterion criterion = new Criterion();
        if (!StringUtils.isBlank(tenantApplyTB2Entity.getTenantID())) {
            Criteria criteria = new Criteria();
            criteria.setJSONB(true).addField("'tenantID'").setOperation("=").setValue(tenantApplyTB2Entity.getTenantID());
            criterion.addCriterion(criteria);
        }
        if (!criterion.toString().trim().contentEquals("")){
            try {
                postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,true,false,reply->{
                    if (reply.succeeded() && reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0) {
                        List<ClientKeyInfo> clientKeyInfoArray = (List<ClientKeyInfo>) reply.result().getResults();
                        ArrayList<String> clientSecretArray = new ArrayList<>();
                        for (ClientKeyInfo clientKeyInfo:clientKeyInfoArray){
                            clientSecretArray.add(clientKeyInfo.getClientSecret());
                        }
                        tenantApplyTB2Entity.setClientSecretArray(clientSecretArray);
                        asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withJsonOK(tenantApplyTB2Entity)));
                    } else {
                        logger.error("findRespClientIds: 该Tenant："+tenantApplyTB2Entity.getTenantID()+" "+tenantApplyTB2Entity.getTenantName()+"未申请ClientKey");
                        asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest("该Tenant："+tenantApplyTB2Entity.getTenantID()+" "+tenantApplyTB2Entity.getTenantName()+"未申请ClientKey")));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainInternalServerError(message)));
            }
        } else{
            logger.error("tenantID不能为空");
            asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest("tenantID不能为空")));
        }
    }

    @Override
    public void getApiApplyAuthorizeShowByTenantApplyId(String tenantApplyId, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                try {
                    findTenantApplyInfoById2(tenantApplyId,postgresClient,asyncResultHandler).setHandler(ar->{
                        if (ar.succeeded()){
                            if (ar.result()!=null){
                                findOpenapiNameById(postgresClient,ar.result(),asyncResultHandler).setHandler(tenantApplyTB2Entity->{
                                    if (tenantApplyTB2Entity.succeeded()){
                                        if (tenantApplyTB2Entity.result()!=null){
                                            findRespClientIds(tenantApplyTB2Entity.result(),postgresClient,asyncResultHandler);
                                        } else {
                                            logger.error("getApiApplyAuthorizeShowByTenantApplyId: findOpenapiNameById根据OpenAPI ID 查询出来的OpenAPI Name为空");
                                            asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest("根据OpenAPI ID 查询出来的OpenAPI Name为空")));
                                        }
                                    } else {
                                        logger.error("getApiApplyAuthorizeShowByTenantApplyId: findOpenapiNameById Error,Caused: "+tenantApplyTB2Entity.cause().getMessage());
                                        asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest(tenantApplyTB2Entity.cause().getMessage())));
                                    }
                                });
                            } else {
                                logger.error("getApiApplyAuthorizeShowByTenantApplyId: 根据tenantApplyId查询出来的Tenant申请的OpenAPI信息为空，请查看该次申请状态为‘已批准’");
                                asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest("根据tenantApplyId查询出来的Tenant申请的OpenAPI信息为空，请查看该次申请状态为‘已批准’")));
                            }
                        } else {
                            logger.error("getApiApplyAuthorizeShowByTenantApplyId: find TenantApply Error Reason: ",ar.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainBadRequest("find TenantApply Error Reason: "+ar.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetApiApplyAuthorizeShowByTenantApplyIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postApiApplyAuthorizePageQuery(AuthorizePageQueryCondition entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        try {
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                try {
                    setUpTenantID3(postgresClient,criterion,entity,asyncResultHandler).setHandler(ar0->{
                        if (!ar0.result().toString().trim().contentEquals("")){
                            try {
                                setUpOtherCondition2(postgresClient,entity,ar0.result(),asyncResultHandler).setHandler(result->{
                                    findOpenapiNameById2(postgresClient,result.result(),asyncResultHandler);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainInternalServerError(message)));
                            }
                        } else {
                            logger.error("postApiApplyAuthorizePageQuery : 根据apiName或者catalog查询内容为空");
                            ApplyPageQueryResponse openapiCollection = new ApplyPageQueryResponse();
                            openapiCollection.setApplyPageQueryResps(new ArrayList<>());
                            openapiCollection.setPageCount(0);
                            openapiCollection.setPageNum(1);
                            openapiCollection.setTotalRecords(0);
                            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyPageQueryResponse.withJsonOK(openapiCollection)));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostApiApplyAuthorizePageQueryResponse.withPlainInternalServerError(message)));
        }
    }
}