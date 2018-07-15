package org.folio.rest.impl;


import com.cnebula.lsp.openapi.utils.RecordLogs;
import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.apache.commons.lang3.StringUtils;
import org.folio.rest.RestVerticle;
import org.folio.rest.jaxrs.model.*;
import org.folio.rest.jaxrs.resource.OpenapidocResource;
import org.folio.rest.persist.Criteria.*;
import org.folio.rest.persist.PostgresClient;
import org.folio.rest.tools.messages.Messages;
import org.folio.rest.tools.utils.TenantTool;

import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by calis on 2017/9/29.
 */
public class OpenapiDocImpl implements OpenapidocResource {
    public static final String TABLE_NAME_OPENAPI_DOC = "tb_openapidoc";

    private final Messages messages = Messages.getInstance();
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "'username'";
    private static final String OKAPI_HEADER_TENANT = "x-okapi-tenant";
    private final Logger logger = LoggerFactory.getLogger(OpenapiDocImpl.class);
    //Vertx vertx = Vertx.vertx();
    private static Integer pageNum = 10;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static WebClient webClient;

    public OpenapiDocImpl(Vertx vertx, String tenantId) {
        logger.info("tenantId: "+tenantId);
        PostgresClient.getInstance(vertx,tenantId).setIdField(ID_FIELD);
        webClient = WebClient.create(vertx, new WebClientOptions().setConnectTimeout(5 * 1000));//5秒超时
    }

    public Future<List<OpenapiDoc>> checkOpenapiDocExist(PostgresClient postgresClient, Criterion criterion) throws RuntimeException{
        Future<List<OpenapiDoc>> future = Future.<List<OpenapiDoc>>future();
        try {
            postgresClient.get(TABLE_NAME_OPENAPI_DOC,OpenapiDoc.class,criterion,true, false, reply->{
                if (reply.succeeded()){
                    if (reply.result()!=null&&reply.result().getResultInfo().getTotalRecords()!=0){
                        future.complete((List<OpenapiDoc>)reply.result().getResults());
                    } else {
                        future.complete(null);
                    }
                } else {
                    logger.error("checkOpenapiDocExist: 查询OpenapiDoc信息出现异常,Caused: "+reply.cause().getMessage());
                    throw new RuntimeException("查询OpenapiDoc信息出现异常,Caused: "+reply.cause().getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            throw new RuntimeException(message);
        }
        return future;
    }

    public Future<Boolean> checkExist(String name,Context vertxContext,String tenantId) {
        Future<Boolean> future = Future.<Boolean>future();
        Criteria criteria = new Criteria();
        Criterion criterion =new Criterion();
        criteria.addField("'title'").setOperation("=").setValue(name);
        criterion.addCriterion(criteria);
        try {
            PostgresClient.getInstance(vertxContext.owner(),tenantId).get(TABLE_NAME_OPENAPI_DOC,OpenapiDocPut.class,criterion,true, false, reply->{
                if ((Integer)reply.result().getResultInfo().getTotalRecords()!=0){
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            future.fail(message);
        }
        return future;
    }

    public Future<Boolean> checkExist2(String id,Context vertxContext,String tenantId) throws Exception{
        Future<Boolean> future = Future.<Boolean>future();
        Criteria criteria = new Criteria();
        Criterion criterion =new Criterion();
        criteria.addField("'id'").setOperation("=").setValue(id);
        criterion.addCriterion(criteria);
        PostgresClient.getInstance(vertxContext.owner(),tenantId).get(TABLE_NAME_OPENAPI_DOC,OpenapiDocPut.class,criterion,true, false, reply->{
            if ((Integer)reply.result().getResultInfo().getTotalRecords()!=0){
                future.complete(true);
            } else {
                future.complete(false);
            }
        });
        return future;
    }

    @Override
    public void postOpenapidoc(OpenapiDocCondition entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
            Integer pageNum = 1;
            Integer pageRecordCount = 10;
            Criteria criteria = new Criteria();
            Criteria criteria1 = new Criteria();
            Criteria criteria2 = new Criteria();
            Criteria criteria3 = new Criteria();
            Criteria criteria4 = new Criteria();
            Criterion criterion =new Criterion();
            if (entity!=null) {
                if (!StringUtils.isBlank(entity.getCategory()))
                    criteria.addField("'category'").setOperation("=").setValue("'"+entity.getCategory()+"'");
                criterion.addCriterion(criteria);
                if (!StringUtils.isBlank(entity.getTitle())) {
                    criteria1.addField("'title'").setOperation("like").setValue("%" + entity.getTitle() + "%");
                    criterion.addCriterion(criteria1);
                }
                if (!StringUtils.isBlank(entity.getStatus())) {
                    criteria2.addField("'status'").setOperation("=").setValue("'"+entity.getStatus()+"'");
                    criterion.addCriterion(criteria2);
                }
                if (!StringUtils.isBlank(entity.getRegdatef())) {
                    criteria3.addField("'createTime'").setOperation(">=").setValue(entity.getRegdatef());
                    criterion.addCriterion(criteria3);
                }
                if (!StringUtils.isBlank(entity.getRegdatet())) {
                    criteria4.addField("'createTime'").setOperation("<=").setValue(entity.getRegdatet());
                    criterion.addCriterion(criteria4);
                }
                if (!StringUtils.isBlank(entity.getOrderField()) && !StringUtils.isBlank(entity.getOrderType()) && entity.getOrderType().equalsIgnoreCase("ASC")) {
                    Order order = new Order();
                    order.asc("jsonb ->> '" + entity.getOrderField() + "'");
                    criterion.setOrder(order);
                }
                if (!StringUtils.isBlank(entity.getOrderField()) && !StringUtils.isBlank(entity.getOrderType()) && entity.getOrderType().equalsIgnoreCase("DESC")) {
                    Order order = new Order();
                    order.desc("jsonb ->> '" + entity.getOrderField() + "'");
                    criterion.setOrder(order);
                }
            }
                logger.info(criterion.toString());
                pageNum = entity.getPageNum() == null || entity.getPageNum() <= 1 ? pageNum : entity.getPageNum();
                pageRecordCount = entity.getPageRecordCount() == null || entity.getPageRecordCount() <= 10 ? pageRecordCount : entity.getPageRecordCount();
                criterion.setLimit(new Limit(pageRecordCount));
                criterion.setOffset(new Offset((pageNum - 1) * pageRecordCount));
                final Integer pageNum2 = pageNum;
                final Integer pageRecordCount2 = pageRecordCount;
                try {

                    String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
                    String token = routingContext.request().getHeader("X-Okapi-Token");
                    String hostPort = routingContext.request().getHeader("Host");
                    String typeURI = "/sysman/codes";
                    String okapiUrl = "http://"+hostPort+typeURI+"?query="+URLEncoder.encode("[[\"tabid\",8,\"=\",\"AND\"],[\"tabid\",16,\"=\",\"OR\"]]","utf-8");
                    //?query=`+encodeURI(JSON.stringify([["tabid",`${tabid}`,"=","AND"]]));

                    Future<JsonArray> apiCatalogFuture = Future.future();
                    Future<List<OpenapiDocPage>> queryFuture = Future.future();
                    Openapidocs openapidocs = new Openapidocs();
                    webClient.getAbs(okapiUrl)
                            .putHeader("X-Okapi-Tenant",tenant)
                            .putHeader("X-Okapi-Token",token)
                            .send(type -> {
                                if (type.succeeded()) {
                                    JsonObject types = type.result().bodyAsJsonObject();
                                    JsonArray dataType = types.getJsonArray("data");
                                    logger.info("Get API catalog success from: " + okapiUrl);
                                    logger.info("API catalog data: " + dataType);
                                    apiCatalogFuture.complete(dataType);
                                } else {
                                    apiCatalogFuture.fail(type.cause());
                                }
                            });

                    postgresClient.get(TABLE_NAME_OPENAPI_DOC, OpenapiDocPage.class, criterion.toString(), true, true, reply -> {
                        if (reply.succeeded()) {
                            List<OpenapiDocPage> apidocs = (List<OpenapiDocPage>) reply.result().getResults();
                            for (OpenapiDocPage apidoc:apidocs) {
                                apidoc.setContext(null);
                            }
                            openapidocs.setPageNum(pageNum2);
                            openapidocs.setTotalRecords(reply.result().getResultInfo().getTotalRecords());
                            //openapidocs.setOpenapiDocPages(apidocs);
                            Integer totalRecordCount = reply.result().getResultInfo().getTotalRecords();
                            openapidocs.setPageCount(totalRecordCount % pageRecordCount2 == 0 ? totalRecordCount / pageRecordCount2 : totalRecordCount / pageRecordCount2 + 1);
                            queryFuture.complete(apidocs);
                            logger.info("Success to select OpenAPI doc info page by querying");
                        } else {
                            logger.error(400, "分页条件查询失败, Caused: " + reply.cause().getLocalizedMessage());
                            queryFuture.fail("分页条件查询失败, Caused: " + reply.cause().getLocalizedMessage());
                        }
                    });

                    CompositeFuture.join(queryFuture, apiCatalogFuture).setHandler(re -> {
                        if (re.succeeded()) {
                            for (OpenapiDocPage openapiDocPage:queryFuture.result()) {
                                for (Object catalogOrStatus : apiCatalogFuture.result()) {
                                    JsonObject entries = new JsonObject(catalogOrStatus.toString());
                                    if (entries.getInteger("tabid") == 8) {
                                        if (entries.getJsonObject("cvalue").getValue("id").toString().contentEquals(openapiDocPage.getCategory())) {
                                            openapiDocPage.setCategory(entries.getJsonObject("cvalue").getString("name"));
                                        }
                                    } else if (entries.getInteger("tabid") == 16) {
                                        if (entries.getJsonObject("cvalue").getValue("id").toString().contentEquals(openapiDocPage.getStatus())) {
                                            openapiDocPage.setStatus(entries.getJsonObject("cvalue").getString("name"));
                                        }
                                    }
                                }
                            }
                            openapidocs.setOpenapiDocPages(queryFuture.result());
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocResponse.withJsonOK(openapidocs)));
                        } else {
                            logger.error("Exception emerged when compose OpenAPI info, Caused: " + re.cause().getLocalizedMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocResponse.withPlainBadRequest("Exception emerged when compose OpenAPI info, Caused: " + re.cause().getLocalizedMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocResponse.withPlainInternalServerError(e.getCause().getMessage())));
                }
            });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocResponse.withPlainInternalServerError(e.getCause().getMessage())));
            }
    }

    @Override
    public void postOpenapidocCreatePost(OpenapiDocData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>>asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                OpenapiDoc openapiDoc = entity.getOpenapiDoc();
                openapiDoc.setCreateTime(date);
                openapiDoc.setUpdateTime(date);
                openapiDoc.setStatus("0");
                Criterion criterion = new Criterion();
                Criteria criteria = new Criteria();
                criteria.setJSONB(true).addField("'title'").setOperation("=").setValue(openapiDoc.getTitle());
                criterion.addCriterion(criteria);
                checkOpenapiDocExist(postgresClient,criterion).setHandler(exist->{
                    if (exist.succeeded()){
                        if (exist.result()==null){
                            try {
                                postgresClient.save(TABLE_NAME_OPENAPI_DOC,openapiDoc,true,reply->{
                                    if (reply.succeeded()){
                                        logger.info("postOpenapidocCreatePost: 保存OpenapiDoc文档信息成功");
                                        try {
                                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operate","保存OpenapiDoc文档信息成功").put("content",JsonObject.mapFrom(entity.getOpenapiDoc())).put("create_time",date)).setHandler(ar0->{
                                                CreateResp createResp = new CreateResp();
                                                if (ar0.succeeded()&&ar0.result()){
                                                    createResp.setSuccess(true);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withJsonOK(createResp)));
                                                } else {
                                                    createResp.setSuccess(false);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withJsonOK(createResp)));
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            String message = e.getLocalizedMessage();
                                            logger.error(message, e);
                                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainInternalServerError(message)));
                                        }
                                    } else {
                                        logger.error("postOpenapidocCreatePost: 保存OpenapiDoc文档信息出现异常,Caused: "+reply.cause().getMessage());
                                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainBadRequest("OpenapiDoc标题Title已经存在")));
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainInternalServerError(message)));
                            }
                        } else {
                            logger.error("postOpenapidocCreatePost: checkOpenapiDocExist OpenapiDoc标题Title: '"+openapiDoc.getTitle()+"' 已经存在");
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainBadRequest("OpenapiDoc标题Title: '"+openapiDoc.getTitle()+"' 已经存在")));
                        }
                    } else {
                        logger.error("postOpenapidocCreatePost: checkOpenapiDocExist 验证OpenapiDoc Title唯一性出现异常,Caused: "+exist.cause().getMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainBadRequest("验证OpenapiDoc Title唯一性出现异常,Caused: "+exist.cause().getMessage())));
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainInternalServerError(message)));
        }

    }

    @Override
    public void getOpenapidocByOpenapidocid(String openapidocid, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        try {
            Criterion criterion = new Criterion();
            Criteria criteria = new Criteria();
            if (!StringUtils.isBlank(openapidocid)){
                criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+openapidocid+"'");
                criterion.addCriterion(criteria);
            }
            PostgresClient.getInstance(vertxContext.owner(),tenantId).get(TABLE_NAME_OPENAPI_DOC,OpenapiDoc.class, criterion,true,true, reply->{
                if (reply.succeeded()){
                    if (reply.result()!=null&&reply.result().getResultInfo()!=null&&reply.result().getResultInfo().getTotalRecords()!=0){
                        logger.info("getOpenapidocByOpenapidocid: 根据id获取OpenapiDoc文档成功");
                        OpenapiDoc openapiDoc = ((List<OpenapiDoc>) reply.result().getResults()).get(0);
                        asyncResultHandler.handle(Future.succeededFuture(GetOpenapidocByOpenapidocidResponse.withJsonOK(openapiDoc)));
                    } else {
                        asyncResultHandler.handle(Future.succeededFuture(GetOpenapidocByOpenapidocidResponse.withPlainNotFound("OpenAPIDOC不存在！")));
                    }
                } else {
                    logger.error("getOpenapidocByOpenapidocid: 根据id获取OpenapiDoc文档出现异常,Caused: "+reply.cause().getMessage());
                    asyncResultHandler.handle(Future.succeededFuture(GetOpenapidocByOpenapidocidResponse.withPlainBadRequest("根据id获取OpenapiDoc文档出现异常,Caused: "+reply.cause().getMessage())));
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetOpenapidocByOpenapidocidResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postOpenapidocByOpenapidocid(String openapidocid, Log entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        Criterion criterion = new Criterion();
        Criteria criteria = new Criteria();
        if (!StringUtils.isBlank(openapidocid)){
            criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+openapidocid+"'");
            criterion.addCriterion(criteria);
        }
        String date = simpleDateFormat.format(new Date());
        try {
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            postgresClient.delete(TABLE_NAME_OPENAPI_DOC,criterion,reply->{
                if (reply.succeeded()){
                    if (reply.result().getUpdated()==1){
                        try {
                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity)).put("operate","删除OpenapiDoc文档").put("content",new JsonObject().put("openapidocId",openapidocid).put("create_time",date))).setHandler(ar0->{
                            CreateResp createResp = new CreateResp();
                                if (ar0.succeeded()&&ar0.result()){
                                    createResp.setSuccess(true);
                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withJsonOK(createResp)));
                                } else {
                                    createResp.setSuccess(false);
                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withJsonOK(createResp)));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCreatePostResponse.withPlainInternalServerError(message)));
                        }
                    } else if (reply.result().getUpdated()==0){
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocByOpenapidocidResponse.withPlainNotFound("OpenAPIDOC不存在！")));
                    }
                } else {
                    logger.error("getOpenapidocByOpenapidocid: 根据id删除OpenapiDoc文档出现异常,Caused: "+reply.cause().getMessage());
                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocByOpenapidocidResponse.withPlainBadRequest("根据id删除OpenapiDoc文档出现异常,Caused: "+reply.cause().getMessage())));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocByOpenapidocidResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void putOpenapidoc(OpenapiDocDataPut entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            String date = simpleDateFormat.format(new Date());
            OpenapiDocPut openapiDocPut = entity.getOpenapiDocPut();
            Criterion criterion = new Criterion();
            Criteria criteria = new Criteria();
            if (!StringUtils.isBlank(entity.getOpenapiDocPut().getId())){
                criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+openapiDocPut.getId()+"'");
                criterion.addCriterion(criteria);
            }
            vertxContext.runOnContext(v -> {
                try {
                    checkOpenapiDocExist(postgresClient,criterion).setHandler(exist->{
                        if (exist.succeeded()){
                            if (exist.result()!=null){
                                logger.error("putOpenapidoc: checkOpenapiDocExist 被修改的OpenapiDoc文档id存在");
                                OpenapiDoc openapiDoc = exist.result().get(0);
                                openapiDocPut.setCreateTime(openapiDoc.getCreateTime());
                                JsonObject openapiDocJsonObject = JsonObject.mapFrom(openapiDocPut);
                                openapiDocJsonObject.put("updateTime",date);
                                openapiDocJsonObject.remove("id");
                                try {
                                    postgresClient.update(TABLE_NAME_OPENAPI_DOC,openapiDocJsonObject,criterion,true,reply->{
                                        if (reply.succeeded()){
                                            if (reply.result().getUpdated()!=0){
                                                logger.info("putOpenapidoc: 更新OpenapiDoc文档成功");
                                                try {
                                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operate","更新OpenapiDoc文档").put("content",JsonObject.mapFrom(entity.getOpenapiDocPut()).put("create_time",date))).setHandler(ar0->{
                                                        CreateResp createResp = new CreateResp();
                                                        if (ar0.succeeded()&&ar0.result()){
                                                            createResp.setSuccess(true);
                                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withJsonOK(createResp)));
                                                        } else {
                                                            createResp.setSuccess(false);
                                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withJsonOK(createResp)));
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    String message = e.getLocalizedMessage();
                                                    logger.error(message, e);
                                                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainInternalServerError(message)));
                                                }
                                            } else {
                                                logger.info("putOpenapidoc: 更新OpenapiDoc文档失败");
                                                asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainBadRequest("更新OpenapiDoc文档失败")));
                                            }
                                        } else {
                                            logger.error("putOpenapidoc: 更新OpenapiDoc文档出现异常,Caused: "+reply.cause().getMessage());
                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainBadRequest("更新OpenapiDoc文档出现异常,Caused: "+reply.cause().getMessage())));
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainInternalServerError(message)));
                                }
                            } else {
                                logger.error("putOpenapidoc: checkOpenapiDocExist 被修改的OpenapiDoc文档id不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainBadRequest("被修改的OpenapiDoc文档id: "+entity.getOpenapiDocPut().getId()+" 不存在")));
                            }
                        } else {
                            logger.error("putOpenapidoc: checkOpenapiDocExist 查询OpenAPI文档出现异常,Caused: "+exist.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainBadRequest("查询OpenAPI文档出现异常,Caused: "+exist.cause().getMessage())));
                        }
                    });
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PutOpenapidocResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postOpenapidocCheckTitle(Title entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
                try {
                    checkExist(entity.getTitle(),vertxContext,tenantId).setHandler(exist->{
                        if (exist.succeeded()){
                            if (exist.result()){
                                logger.error("Title: "+entity.getTitle()+" 已经存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCheckTitleResponse.withPlainBadRequest("Title: "+entity.getTitle()+" 已经存在")));
                            } else {
                                logger.info("Title: "+entity.getTitle()+" 可以使用");
                                asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCheckTitleResponse.withPlainOK("success")));
                            }
                        } else {
                            logger.info("服务异常,Caused: "+exist.cause().getLocalizedMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCheckTitleResponse.withPlainInternalServerError("服务异常,Caused: "+exist.cause().getLocalizedMessage())));
                        }
                    });
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCheckTitleResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapidocCheckTitleResponse.withPlainInternalServerError(message)));
        }
    }

}
