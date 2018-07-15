package org.folio.rest.impl;

import com.cnebula.lsp.openapi.utils.RecordLogs;
import com.cnebula.lsp.openapi.utils.UrlUtil;
import com.sun.jna.platform.win32.OaIdl;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.apache.commons.lang3.StringUtils;
import org.folio.rest.RestVerticle;
import org.folio.rest.jaxrs.model.*;
import org.folio.rest.jaxrs.resource.OpenapiResource;
import org.folio.rest.persist.Criteria.*;
import org.folio.rest.persist.PostgresClient;
import org.folio.rest.persist.interfaces.Results;
import org.folio.rest.tools.messages.Messages;
import org.folio.rest.tools.utils.TenantTool;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by calis on 2017/9/24.
 */
public class OpenapiAPI implements OpenapiResource {
    public static final String TABLE_NAME_OPENAPI = "tb_openapi";
    public static final String TABLE_NAME_UNAUTHORIZEDTENANTS = "tb_unauthtenants";
    public static final String TABLE_NAME_EXCEPTION = "tb_exception";

    private final Messages messages = Messages.getInstance();
    // private final String USER_COLLECTION = "user";
    private static final String ID_FIELD = "'id'";
    private static final String NAME_FIELD = "'username'";
    private static final String OKAPI_HEADER_TENANT = "x-okapi-tenant";
    private final Logger logger = LoggerFactory.getLogger(OpenapiAPI.class);
    //Vertx vertx = Vertx.vertx();
    private static Integer pageNum = 1;
    private static Integer pageRecordCount = 10;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static WebClient webClient;
    private static final String srvcIdPrefix = "openapi";
    private AsyncSQLClient sqlClient;
    private Set<String> methods = new HashSet();

   /* public OpenapiAPI(Vertx vertx, Map<String, String> okapiHeaders) {
       // this.vertx = vertx;
        PostgresClient instance = PostgresClient.getInstance(vertx);
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        System.out.println(instance+"============="+tenantId);
    }*/

    public OpenapiAPI(Vertx vertx, String tenantId) {
        logger.info("tenantId: "+tenantId);
        PostgresClient.getInstance(vertx,tenantId).setIdField("id");
        webClient = WebClient.create(vertx, new WebClientOptions().setConnectTimeout(5 * 1000));//5秒超时
        sqlClient = PostgreSQLClient.createShared(vertx, PostgresClient.getInstance(vertx, tenantId).getConnectionConfig());
        methods.add("GET");
        methods.add("POST");
        methods.add("PUT");
        methods.add("DELETE");
        methods.add("OPTION");
        methods.add("FETCH");
    }

    @Override
    public void postOpenapi(OpenapiPagequeryCondition entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        Criteria criteria = new Criteria();
        Criteria criteria1 = new Criteria();
        Criteria criteria2 = new Criteria();
        Criteria criteria3 = new Criteria();
        Criteria criteria4 = new Criteria();
        Criterion criterion =new Criterion();
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        if (entity!=null){
            if (!StringUtils.isBlank(entity.getName())){
                criteria.addField("'name'").setOperation("=").setValue("'"+entity.getName()+"'");
                criterion.addCriterion(criteria);
            }
            if (!StringUtils.isBlank(entity.getSummary())){
                criteria1.addField("'summary'").setOperation("like").setValue("%"+entity.getSummary()+"%");
                criterion.addCriterion(criteria1);
            }
            if (!StringUtils.isBlank(entity.getStatus())){
                criteria2.addField("'status'").setOperation("=").setValue("'"+entity.getStatus()+"'");
                criterion.addCriterion(criteria2);
            }
            if (!StringUtils.isBlank(entity.getRegdatef())){
                criteria3.addField("'create_time'").setOperation(">=").setValue(entity.getRegdatef());
                criterion.addCriterion(criteria3);
            }
            if (!StringUtils.isBlank(entity.getRegdatet())){
                criteria4.addField("'create_time'").setOperation("<=").setValue(entity.getRegdatet());
                criterion.addCriterion(criteria4);
            }
            if (!StringUtils.isBlank(entity.getOrderField())&&!StringUtils.isBlank(entity.getOrderType())&&entity.getOrderType().equalsIgnoreCase("ASC")) {
                Order order = new Order();
                order.asc("jsonb::json ->> '"+entity.getOrderField()+"'");
                criterion.setOrder(order);
            }
            if (!StringUtils.isBlank(entity.getOrderField())&&!StringUtils.isBlank(entity.getOrderType())&&entity.getOrderType().equalsIgnoreCase("DESC")) {
                Order order = new Order();
                order.desc("jsonb::json ->> '" + entity.getOrderField() + "'");
                criterion.setOrder(order);
            }
            logger.info(criterion.toString());
            pageNum = entity.getPageNum() == null || entity.getPageNum() < 1 ? pageNum : entity.getPageNum();
            pageRecordCount = entity.getPageRecordCount() == null || entity.getPageRecordCount() < 10 ? pageRecordCount : entity.getPageRecordCount();
            criterion.setLimit(new Limit(pageRecordCount));
            criterion.setOffset(new Offset((pageNum-1)*pageRecordCount));
        }
        final Integer pageNum2 = pageNum;
        final Integer pageRecordCount2 = pageRecordCount;

        String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
        String token = routingContext.request().getHeader("X-Okapi-Token");
        String hostPort = routingContext.request().getHeader("Host");
        String typeURI = "/sysman/codes";
        String okapiUrl = "http://"+hostPort+typeURI+"?query="+URLEncoder.encode("[[\"tabid\",9,\"=\",\"AND\"]]","utf-8");
        //?query=`+encodeURI(JSON.stringify([["tabid",`${tabid}`,"=","AND"]]));

        Future<JsonArray> apiStatusFuture = Future.future();
        Future<List<PageQueryResp>> queryFuture = Future.future();
        OpenapiCollection openapiCollection = new OpenapiCollection();
        webClient.getAbs(okapiUrl)
                .putHeader("X-Okapi-Tenant",tenant)
                .putHeader("X-Okapi-Token",token)
                .send(type -> {
                    if (type.succeeded()) {
                        JsonObject types = type.result().bodyAsJsonObject();
                        JsonArray dataType = types.getJsonArray("data");
                        logger.info("Get API status success from: " + okapiUrl);
                        logger.info("API status data: " + dataType);
                        apiStatusFuture.complete(dataType);
                    } else {
                        apiStatusFuture.fail(type.cause());
                    }
                });

        vertxContext.runOnContext(v -> {
            try {
                PostgresClient.getInstance(vertxContext.owner(),tenantId).get(TABLE_NAME_OPENAPI, PageQueryResp.class,criterion,true, true, reply->{
                    if (reply.succeeded()){
                        List<PageQueryResp> apis = (List<PageQueryResp>) reply.result().getResults();
                        openapiCollection.setPageNum(pageNum2);
                        openapiCollection.setTotalRecords((Integer) reply.result().getResultInfo().getTotalRecords());
                        logger.info("Page by querying API info success");
                        queryFuture.complete(apis);
                    } else {
                        logger.error(400,"分页条件查询失败");
                        queryFuture.fail(reply.cause());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                queryFuture.fail(e);
            }
        });

        CompositeFuture.join(queryFuture,apiStatusFuture).setHandler(re -> {
            if (re.succeeded()) {
                logger.info("All future are success");
                for (PageQueryResp pageQueryResp:queryFuture.result()) {
                    Map<String, Object> additionalProperties = pageQueryResp.getAdditionalProperties();
                    for (String name : additionalProperties.keySet()) {
                        pageQueryResp.setAdditionalProperty(name, null);
                    }
                    for (Object status : apiStatusFuture.result()) {
                        if (new JsonObject(status.toString()).getInteger("tabid") == 9) {
                            if (new JsonObject(status.toString()).getJsonObject("cvalue").getValue("id").toString().contentEquals(pageQueryResp.getStatus())) {
                                pageQueryResp.setStatus(new JsonObject(status.toString()).getJsonObject("cvalue").getString("name"));
                                continue;
                            }
                        }
                    }
                }

                openapiCollection.setPageQueryResp(queryFuture.result());
                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiResponse.withJsonOK(openapiCollection)));
            } else {
                logger.error("One future fail, caused: " + re.cause().getLocalizedMessage());
                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiResponse.withPlainBadRequest("分页条件查询失败")));
            }
        });


        /*vertxContext.runOnContext(v -> {
            try {
                PostgresClient.getInstance(vertxContext.owner(),tenantId).get(TABLE_NAME_OPENAPI, PageQueryResp.class,criterion,true, true, reply->{
                    if (reply.succeeded()){
                        List<PageQueryResp> apis = (List<PageQueryResp>) reply.result().getResults();
                        OpenapiCollection openapiCollection = new OpenapiCollection();
                        openapiCollection.setPageNum(pageNum2);
                        openapiCollection.setTotalRecords((Integer) reply.result().getResultInfo().getTotalRecords());
                        for (PageQueryResp pageQueryResp:apis){
                            Map<String, Object> additionalProperties = pageQueryResp.getAdditionalProperties();
                            for (String name:additionalProperties.keySet()){
                                pageQueryResp.setAdditionalProperty(name,null);
                            }
                        }
                        openapiCollection.setPageQueryResp(apis);
                        Integer totalRecordCount = (Integer) reply.result().getResultInfo().getTotalRecords();
                        openapiCollection.setPageCount(totalRecordCount%pageRecordCount2==0?totalRecordCount/pageRecordCount2:totalRecordCount/pageRecordCount2+1);
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiResponse.withJsonOK(openapiCollection)));
                    } else {
                        logger.error(400,"分页条件查询失败");
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiResponse.withPlainBadRequest("分页条件查询失败")));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiResponse.withPlainInternalServerError(e.getCause().getMessage())));
            }
        });*/
    }

    @Override
    public void postOpenapiCheckName(Name entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        vertxContext.runOnContext(v -> {
            try {
                Criteria criteria = new Criteria();
                Criterion criterion =new Criterion();
                criteria.setJSONB(true).addField("'name'").setOperation("=").setValue("'"+entity.getName()+"'");
                criterion.addCriterion(criteria);
                checkExistByCriterion(postgresClient,criterion).setHandler(result->{
                    if (result.failed()){
                        logger.error("验证name唯一性失败,Caused: "+result.cause().getLocalizedMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCheckNameResponse.withPlainInternalServerError("验证name唯一性失败,Caused: "+result.cause().getLocalizedMessage())));
                    } else {
                        if (result.result()==null){
                            logger.info("name: "+entity.getName()+" 可以使用");
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCheckNameResponse.withPlainOK("name: "+entity.getName()+" 可以使用")));
                        } else {
                            logger.error("name: "+entity.getName()+" 已经存存在");
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCheckNameResponse.withPlainBadRequest("name: "+entity.getName()+" 已经存在")));
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCheckNameResponse.withPlainInternalServerError(e.getCause().getMessage())));
            }
        });
    }

    /*public Future<Boolean> checkIdExist(String id,PostgresClient postgresClient) throws Exception{
        Future<Boolean> future = Future.<Boolean>future();
        Criteria criteria = new Criteria();
        Criterion criterion =new Criterion();
        criteria.addField("'name'").setOperation("=").setValue(id);
        criterion.addCriterion(criteria);
        postgresClient.get(TABLE_NAME_OPENAPI,Openapi.class,criterion,true,reply->{
            if ((Integer)reply.result().getResultInfo().getTotalRecords()!=0){
                future.complete(true);
            } else {
                future.complete(false);
            }
        });
        return future;
    }*/

    public Future<List<OpenapiInfo>> checkExistByCriterion(PostgresClient postgresClient,Criterion criterion){
        Future<List<OpenapiInfo>> future = Future.<List<OpenapiInfo>>future();
        try {
            postgresClient.get(TABLE_NAME_OPENAPI,OpenapiInfo.class,criterion,true,true,reply->{
                if (reply.succeeded()){
                    logger.info("checkNameExist: 根据Criterion查询OpenAPI信息成功");
                    if (reply.result().getResultInfo().getTotalRecords()!=0){
                        logger.info("checkNameExist: 根据Criterion查询OpenAPI信息不为空");
                        List<OpenapiInfo> openapiInfos = (List<OpenapiInfo>) reply.result().getResults();
                        future.complete(openapiInfos);
                    } else {
                        logger.error("checkNameExist: 根据Criterion查询OpenAPI信息为空");
                        future.complete(null);
                    }
                } else {
                    logger.error("checkNameExist: 根据Criterion查询OpenAPI信息失败, Caused: "+reply.cause().getMessage());
                    future.fail("查询OpenAPI信息失败, Caused: "+reply.cause().getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error("checkNameExist: 根据Criterion查询OpenAPI信息失败, Caused: "+message);
            future.fail("查询OpenAPI信息失败, Caused: "+message);
        }
        return future;
    }

    @Override
    public void postOpenapiCreatePost(OpenapiData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        vertxContext.runOnContext(v -> {
            try{
                OpenapiInfo openapiInfo = entity.getOpenapiInfo();
                Field[] declaredFields = openapiInfo.getClass().getDeclaredFields();
                Field.setAccessible(declaredFields, true);
                for (Field filed : declaredFields) {
                    if (filed.getName().toLowerCase().contains("url")) {
                        if (!UrlUtil.checkUrl(filed.get(openapiInfo).toString())) {
                            throw new Exception(filed + " format is illegal ");
                        }
                    } else if (filed.getName().toLowerCase().contains("method")) {
                        ((ArrayList)filed.get(openapiInfo)).forEach((method) -> {
                            if (!methods.contains(method)) {
                                throw new RuntimeException(" method is illegal");
                            }
                        } );
                    }
                }
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                openapiInfo.setStatus("0");
                openapiInfo.setCreateTime(date);
                openapiInfo.setUpdateTime(date);
                openapiInfo.setOnlineSrvcId(srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".")));
                Criteria criteria = new Criteria();
                Criterion criterion =new Criterion();
                criteria.setJSONB(true).addField("'name'").setOperation("=").setValue("'"+openapiInfo.getName()+"'");
                criterion.addCriterion(criteria);
                checkExistByCriterion(postgresClient,criterion).setHandler(exist->{
                    if (exist.succeeded()){
                        logger.info("postOpenapiCreatePost: checkExistByCriterion Success");
                        if (exist.result()==null){
                            try {
                                postgresClient.save(TABLE_NAME_OPENAPI,openapiInfo,true,reply->{//这里添加true,会找id
                                    if (reply.succeeded()){
                                        logger.info("注册OpenAPI信息成功");
                                        try {
                                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("operate","注册OpenAPI信息").put("log",JsonObject.mapFrom(entity.getLog())).put("content",JsonObject.mapFrom(entity.getOpenapiInfo()).put("create_time",date))).setHandler(ar0->{
                                                CreateResp createResp = new CreateResp();
                                                if (ar0.succeeded()&&ar0.result()){
                                                    createResp.setSuccess(true);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withJsonOK(createResp)));
                                                } else {
                                                    createResp.setSuccess(false);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withJsonOK(createResp)));
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            String message = e.getLocalizedMessage();
                                            logger.error(message, e);
                                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withPlainInternalServerError(message)));
                                        }
                                    }else {
                                        logger.error("postOpenapiCreatePost: 注册OpenAPI信息失败, Caused: "+reply.cause().getMessage());
                                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withPlainBadRequest("注册OpenAPI信息失败")));
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withPlainInternalServerError(e.getCause().getMessage())));
                            }
                        } else {
                            logger.error("postOpenapiCreatePost: checkExistByCriterion , 查询OpenAPI信息Name名称已经存在");
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withPlainBadRequest("OpenAPI信息Name名称已经存在")));
                        }
                    } else {
                        logger.error("postOpenapiCreatePost: checkExistByCriterion Fail");
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withPlainBadRequest(exist.cause().getMessage())));
                    }
                });

            }catch (Exception e){
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiCreatePostResponse.withPlainInternalServerError(message)));
            }
        });

    }

    @Override
    public void getOpenapiById(String id, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                Criteria criteria = new Criteria();
                if (!StringUtils.isBlank(id)) {
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterion.addCriterion(criteria);
                }
                try {
                    checkExistByCriterion(postgresClient,criterion).setHandler(exist->{
                        if (exist.succeeded()){
                            if (exist.result()!=null){
                                OpenapiInfo openapiInfo = exist.result().get(0);
                                asyncResultHandler.handle(Future.succeededFuture(GetOpenapiByIdResponse.withJsonOK(openapiInfo)));
                            } else {
                                logger.error("getOpenapiByOpenapiId: checkExistByCriterion 根据id查询OpenAPI注册信息为空");
                                asyncResultHandler.handle(Future.succeededFuture(GetOpenapiByIdResponse.withPlainNotFound("根据id查询OpenAPI注册信息为空")));
                            }
                        } else {
                            logger.error("getOpenapiByOpenapiId: checkExistByCriterion 根据id查询OpenAPI注册信息出现异常, Caused: "+exist.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(GetOpenapiByIdResponse.withPlainBadRequest("根据id查询OpenAPI注册信息出现异常,Caused: "+exist.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(GetOpenapiByIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetOpenapiByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void putOpenapiById(String id,OpenapiData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception{
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        vertxContext.runOnContext(v -> {
            try{
                OpenapiInfo openapiInfo = entity.getOpenapiInfo();
                try {
                    Criterion criterionId =new Criterion();
                    Criteria criteriaId = new Criteria();
                    criteriaId.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterionId.addCriterion(criteriaId);
                    checkExistByCriterion(postgresClient,criterionId).setHandler(existId->{
                        if (existId.succeeded()){
                            logger.info("putOpenapi: checkExistByCriterion 验证id存在 Success");
                            if (existId.result()!=null){
                                Criteria criteria = new Criteria();
                                Criterion criterion =new Criterion();
                                criteria.setJSONB(true).addField("'name'").setOperation("=").setValue("'"+openapiInfo.getName()+"'");
                                criterion.addCriterion(criteria);
                                try {
                                    checkExistByCriterion(postgresClient,criterion).setHandler(exist->{
                                        if (exist.succeeded()){
                                            logger.info("putOpenapi: checkExist checkExistByCriterion 验证name返回结果成功");
                                            if (exist.result()!=null&&exist.result().size()>1||exist.result()!=null&&exist.result().size()==1&&!exist.result().get(0).getId().contentEquals(id)){
                                                logger.error("putOpenapi: checkExist, OpenAPI name 名称已经存在");
                                                asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainBadRequest("OpenAPI name 名称已经存在")));
                                            } else {
                                                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                String date = simpleDateFormat.format(new Date());
                                                openapiInfo.setUpdateTime(date);
                                                try {
                                                    List<OpenapiInfo> openapiInfos = existId.result();
                                                    OpenapiInfo openapiInfoOrigin = openapiInfos.get(0);
                                                    String createTime = openapiInfoOrigin.getCreateTime();
                                                    String status = openapiInfoOrigin.getStatus();
                                                    openapiInfo.setCreateTime(createTime);
                                                    openapiInfo.setStatus(status);
                                                    postgresClient.update(TABLE_NAME_OPENAPI,openapiInfo,criterionId,true,reply->{
                                                        if (reply.succeeded()){
                                                            if (reply.result().getUpdated()!=0){
                                                                logger.info("putOpenapi: 更新OpenAPI信息成功");
                                                                try {
                                                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("operate","更新OpenAPI信息").put("log",JsonObject.mapFrom(entity.getLog())).put("content",JsonObject.mapFrom(entity.getOpenapiInfo()).put("create_time",date))).setHandler(ar0->{
                                                                        CreateResp createResp = new CreateResp();
                                                                        if (ar0.succeeded()&&ar0.result()){
                                                                            createResp.setSuccess(true);
                                                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withJsonOK(createResp)));
                                                                        } else {
                                                                            createResp.setSuccess(false);
                                                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withJsonOK(createResp)));
                                                                        }
                                                                    });
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                    String message = e.getLocalizedMessage();
                                                                    logger.error(message, e);
                                                                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainInternalServerError(message)));
                                                                }
                                                            } else {
                                                                logger.error("putOpenapi: 更新OpenAPI信息失败");
                                                                asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainBadRequest("更新OpenAPI信息失败")));
                                                            }
                                                        } else {
                                                            logger.error("putOpenapi: 更新OpenAPI信息出现异常,Caused: "+reply.cause().getMessage());
                                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainBadRequest("更新OpenAPI信息出现异常,Caused: "+reply.cause().getMessage())));
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    String message = e.getLocalizedMessage();
                                                    logger.error(message, e);
                                                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainInternalServerError(message)));
                                                }
                                            }
                                        } else {
                                            logger.error("putOpenapi: checkExistByCriterion, 验证OpenAPI name名称返回结果出现异常, Caused: "+exist.cause().getMessage());
                                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainBadRequest("验证OpenAPI name名称返回结果出现异常, Caused: "+exist.cause().getMessage())));
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainInternalServerError(message)));
                                }
                            } else {
                                logger.error("putOpenapi: checkExistByCriterion 需要修改的OpenAPI注册信息id不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainNotFound("需要修改的OpenAPI注册信息id不存在")));
                            }
                        } else {
                            logger.info("putOpenapi: checkExistByCriterion 验证id存在 Fail, Caused: "+existId.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainBadRequest("验证id存在失败, Caused: "+existId.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainInternalServerError(e.getCause().getMessage())));
                }
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getLocalizedMessage();
                logger.error(message, e);
                asyncResultHandler.handle(Future.succeededFuture(PutOpenapiByIdResponse.withPlainInternalServerError(e.getCause().getMessage())));
            }
        });
    }

    @Override
    public void postOpenapiById(String id, Log log, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        Criterion criterion = new Criterion();
        Criteria criteria = new Criteria();
        if (!StringUtils.isBlank(id)){
            criteria.addField("'id'").setOperation("=").setValue(id);
            criterion.addCriterion(criteria);
        }
        try {
            PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            postgresClient.delete(TABLE_NAME_OPENAPI,criterion,reply->{
                if (reply.succeeded()){
                    CreateResp createResp = new CreateResp();
                    if (reply.result().getUpdated()==1){
                        try {
                            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = simpleDateFormat.format(new Date());
                            RecordLogs.recordLogs(postgresClient,JsonObject.mapFrom(log).put("content",new JsonObject().put("operation","删除OpenAPI注册信息").put("openapiId",id).put("create_time",date))).setHandler(ar0->{
                                if (ar0.succeeded()&&ar0.result()){
                                    createResp.setSuccess(true);
                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiByIdResponse.withJsonOK(createResp)));
                                } else {
                                    createResp.setSuccess(false);
                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiByIdResponse.withJsonOK(createResp)));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiByIdResponse.withPlainInternalServerError(message)));
                        }
                    } else if (reply.result().getUpdated()==0){
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiByIdResponse.withPlainNotFound("OpenAPI不存在！")));
                    }
                } else {
                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiByIdResponse.withPlainBadRequest("")));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postOpenapiDeleteById(String id, Log entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                try {
                    Criterion criterionId = new Criterion();
                    if (!StringUtils.isBlank(id)) {
                        Criteria criteria = new Criteria();
                        criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                        criterionId.addCriterion(criteria);
                    }
                    checkExistByCriterion(postgresClient,criterionId).setHandler(exist->{
                        if (exist.succeeded()){
                            logger.info("postOpenapiDeleteById: checkExistByCriterion Success");
                                if (exist.result()!=null){
                                    try {
                                        postgresClient.delete(TABLE_NAME_OPENAPI,criterionId,reply->{
                                            if (reply.succeeded()){
                                                logger.info("postOpenapiDeleteById: 删除OpenAPI信息成功");
                                                try {
                                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("opearation:","删除单个OpenAPI信息").put("log",JsonObject.mapFrom(entity)).put("content",id).put("create_time",date)).setHandler(ar0->{
                                                        CreateResp createResp = new CreateResp();
                                                        if (ar0.succeeded()&&ar0.result()){
                                                            createResp.setSuccess(true);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withJsonOK(createResp)));
                                                        } else {
                                                            createResp.setSuccess(false);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withJsonOK(createResp)));
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    String message = e.getLocalizedMessage();
                                                    logger.error(message, e);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withPlainInternalServerError(message)));
                                                }
                                            } else {
                                                logger.error("postOpenapiDeleteById: 删除OpenAPI信息失败");
                                                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withPlainBadRequest("删除OpenAPI信息失败")));
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withPlainInternalServerError(message)));
                                    }
                                } else {
                                    logger.error("postOpenapiDeleteById: checkIdExist,要删除的OpenAPI信息为空");
                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withPlainNotFound("要删除的OpenAPI信息为空")));
                                }
                        } else {
                            logger.error("postOpenapiDeleteById: checkIdExist Fail");

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiDeleteByIdResponse.withPlainInternalServerError(message)));
        }
    }

    private Future<ArrayEntity> findNotExistIds(PostgresClient postgresClient,List<String> idArray,Handler<AsyncResult<Response>> asyncResultHandler) {
        Future<ArrayEntity> future = Future.<ArrayEntity>future();
        ArrayEntity arrayEntity = new ArrayEntity();
        List<String> existIdArray = new ArrayList<String>();
        List<String> notExistIdArray = new ArrayList<String>();
        List<OpenapiInfo> openapiInfoArray = new ArrayList<OpenapiInfo>();
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
                        postgresClient.get(TABLE_NAME_OPENAPI,OpenapiInfo.class,criterion,true,true,reply->{
                            if (reply.succeeded()){
                                logger.info("findNotExistIds: 查询Id是否存在成功");
                                if (reply.result() != null && ((List<OpenapiInfo>) reply.result().getResults()).size() != 0){
                                    logger.info("checkNotExistIds: 查询ID: "+id+" OpenAPI信息存在");
                                    List<OpenapiInfo> openapiInfos = (List<OpenapiInfo>) reply.result().getResults();
                                    openapiInfoArray.add(openapiInfos.get(0));
                                    existIdArray.add(id);
                                    future2.complete();
                                } else {
                                    logger.error("checkNotExistIds: 查询ID: "+id+" OpenAPI信息不存在");
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
                        future2.fail(e.getCause().getLocalizedMessage());
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
                arrayEntity.setOpenapiInfo(openapiInfoArray);
                future.complete(arrayEntity);
            } else {
                logger.error("findNotExistIds: 查询全部Id是否存在失败");
                future.fail("findNotExistIds: 查询全部Id是否存在失败, Caused: " + ar0.cause().getLocalizedMessage());
            }
        });
        return future;
    }

    private Future<ArrayEntity> startOrStopExistIds(PostgresClient postgresClient,ArrayEntity arrayEntity,String status) throws RuntimeException{
        Future<ArrayEntity> future = Future.<ArrayEntity>future();
        ArrayEntity startOrStopEntiry = new ArrayEntity();
        List<String> startOrStopSuccessIds = new ArrayList<String>();
        List<String> startOrStopFailIds = new ArrayList<String>();
        List<OpenapiInfo> openapiInfoList = arrayEntity.getOpenapiInfo();
        CompositeFuture.join(openapiInfoList.stream().map(openapiInfo -> {
            return Future.future(future2 -> {
                Criterion criterion = new Criterion();
                Criteria criteria = new Criteria();
                criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+openapiInfo.getId()+"'");
                criterion.addCriterion(criteria);
                try {
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = simpleDateFormat.format(new Date());
                    if ("1".contentEquals(status)){
                        if ("2".contentEquals(openapiInfo.getStatus())||"0".contentEquals(openapiInfo.getStatus())){
                            openapiInfo.setStatus("1");
                            JsonObject openapi = JsonObject.mapFrom(openapiInfo);
                            openapi.remove("id");
                            openapi.put("startTime",date);
                            postgresClient.update(TABLE_NAME_OPENAPI,openapi,criterion,true,reply->{
                                if (reply.succeeded()){
                                    if (reply.result().getUpdated()!=0){
                                        logger.info("startOrStopExistIds: "+status+"单个OpenAPI成功");
                                        startOrStopSuccessIds.add(openapiInfo.getId());
                                        future2.complete();
                                    } else {
                                        logger.info("startOrStopExistIds: "+status+"单个OpenAPI失败");
                                        startOrStopFailIds.add(openapiInfo.getId());
                                        future2.complete();
                                    }
                                } else {
                                    logger.error("startOrStopExistIds: "+status+"单个OpenAPI出现异常,Caused: "+reply.cause().getMessage());
                                    future2.fail(status+"单个OpenAPI出现异常,Caused: "+reply.cause().getMessage());
                                }
                            });
                        } else {
                            logger.error("OpenAPI: "+openapiInfo.getName()+" 已经启用");
                            future2.fail("OpenAPI: "+openapiInfo.getName()+" 已经启用");
                        }
                    } else
                    if ("2".contentEquals(status)){
                        if ("1".contentEquals(openapiInfo.getStatus())){
                            openapiInfo.setStatus("2");
                            JsonObject openapi = JsonObject.mapFrom(openapiInfo);
                            openapi.remove("id");
                            openapi.put("stopTime",date);
                            postgresClient.update(TABLE_NAME_OPENAPI,openapi,criterion,true,reply->{
                                if (reply.succeeded()){
                                    if (reply.result().getUpdated()!=0){
                                        logger.info("startOrStopExistIds: "+status+"单个OpenAPI成功");
                                        startOrStopSuccessIds.add(openapiInfo.getId());
                                        future2.complete();
                                    } else {
                                        logger.info("startOrStopExistIds: "+status+"单个OpenAPI失败");
                                        startOrStopFailIds.add(openapiInfo.getId());
                                        future2.complete();
                                    }
                                } else {
                                    logger.error("startOrStopExistIds: "+status+"单个OpenAPI出现异常,Caused: "+reply.cause().getMessage());
                                    future2.fail(status+"单个OpenAPI出现异常,Caused: "+reply.cause().getMessage());
                                }
                            });
                        } else {
                            logger.error("OpenAPI: "+openapiInfo.getName()+" 已经停用");
                            future2.fail("OpenAPI: "+openapiInfo.getName()+" 已经停用");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    throw new RuntimeException(message);
                }

            });
        }).collect(Collectors.toList())).setHandler(ar0 -> {
            if (ar0.succeeded()){
                startOrStopEntiry.setSuccessIdArray(startOrStopSuccessIds);
                startOrStopEntiry.setFailIdArray(startOrStopFailIds);
                startOrStopEntiry.setNotExistIdArray(arrayEntity.getNotExistIdArray());
                future.complete(startOrStopEntiry);
            } else {
                logger.error("startOrStopExistIds: "+status+"多个OpenAPI注册信息失败,Caused: "+ar0.cause().getMessage());
                future.fail(status+" 多个OpenAPI注册信息失败,Caused: "+ar0.cause().getMessage());
            }
        });
        return future;
    }

    private void registDeploymentDescriptor2Okapi(OpenapiInfo openapiInfo, Future postOrDeleteDeploymentDescriptorFuture) {
        String okapiUrl = openapiInfo.getPublishUrl();
        okapiUrl = okapiUrl.concat("/_/discovery/modules");
        JsonObject deploymentDescriptor = new JsonObject();
        deploymentDescriptor.put("instId",UUID.randomUUID().toString());
        deploymentDescriptor.put("srvcId",srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".").concat("-0.0.1")));
        deploymentDescriptor.put("url",openapiInfo.getDeployUrl());
        webClient.postAbs(okapiUrl).putHeader("Content-Type", "application/json").sendJsonObject(deploymentDescriptor, response -> {
            if (response.succeeded()) {
                logger.info("Success to regist DeploymentDescriptor to Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId());
                if (response.result().statusCode() == 201) {
                    logger.info("Success to regist DeploymentDescriptor to Okappi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId());
                    postOrDeleteDeploymentDescriptorFuture.complete();
                } else {
                    logger.info("Fail to regist DeploymentDescriptor to Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", httpCode: " + response.result().statusCode() + " , message: " + response.result().bodyAsString());
                    postOrDeleteDeploymentDescriptorFuture.fail("Fail to regist DeploymentDescriptor to Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", httpCode: " + response.result().statusCode() + " , message: " + response.result().bodyAsString());
                }
            } else {
                logger.error("Exception to regist DeploymentDescriptor to Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", Caused: " + response.cause().getLocalizedMessage());
                postOrDeleteDeploymentDescriptorFuture.fail("Fail to regist DeploymentDescriptor to Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", Caused: " + response.cause().getLocalizedMessage());
            }
        });
    }

    private void recursiveTraverse(List<OpenapiInfo> openapis, Future<Object> future) {
        if (openapis.isEmpty()) {
            logger.info("Traverse has completed already");
            future.complete();
        } else {
            logger.info("Need to process traverse");
            OpenapiInfo openapiInfo = openapis.get(0);
            Future<SQLConnection> getConnFutre = Future.future();
            sqlClient.getConnection(getConnFutre);

            //----------------------Start two threads-------------------------
            // ModuleDescriptor
            Future<Object> startModuleFuture = Future.future();
            startModuleFuture.complete();
            Future<Object> completedModuleFuture = Future.future();

            // DeploymentDescriptor
            Future<Object> startDeployFuture = Future.future();
            startDeployFuture.complete();
            Future<Object> completedDeployFuture = Future.future();
            //----------------------Start two threads-------------------------
            startDeployFuture.compose(existDeploy -> {
                Future<Object> getDeployFuture = Future.future();
                String onlineSrvcId = openapiInfo.getOnlineSrvcId();
                String okapiUrl = openapiInfo.getPublishUrl();
                okapiUrl = okapiUrl.concat("/_/discovery/modules/"+onlineSrvcId);
                webClient.getAbs(okapiUrl).send(response -> {
                    if (response.succeeded()) {
                        if (response.result().statusCode() == 200) {
                            logger.info("Success to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId());
                            getDeployFuture.complete(response.result().bodyAsJsonArray());
                        } else if (response.result().statusCode() == 404) {
                            logger.error("Not found DeploymentDescriptor of srvcId: " + onlineSrvcId + " from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId());
                            getDeployFuture.complete(false);
                        } else {
                            logger.error("Fail to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", httpCode: " + response.result().statusCode() + " , message: " + response.result().bodyAsString());
                            getDeployFuture.fail("Fail to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", httpCode: " + response.result().statusCode() + " , message: " + response.result().bodyAsString());
                        }
                    } else {
                        logger.error("Exception to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", Caused: " + response.cause().getLocalizedMessage());
                        getDeployFuture.fail("Get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", Caused: " + response.cause().getLocalizedMessage());
                    }
                });
                return getDeployFuture;
            }).compose(deploymentDescriptorOnLine -> {
                Future<Object> postOrDeleteDeploymentDescriptorFuture = Future.future();
                if (deploymentDescriptorOnLine.equals(false)) {
                    registDeploymentDescriptor2Okapi(openapiInfo,postOrDeleteDeploymentDescriptorFuture);
                } else {
                    if (new JsonArray(deploymentDescriptorOnLine.toString()).getJsonObject(0).getString("srvcId").contentEquals(srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".").concat("-0.0.1"))) && new JsonArray(deploymentDescriptorOnLine.toString()).getJsonObject(0).getString("url").contentEquals(openapiInfo.getDeployUrl())) {
                        postOrDeleteDeploymentDescriptorFuture.complete();
                    } else {
                        CompositeFuture.join(new JsonArray(deploymentDescriptorOnLine.toString()).stream().map(deploymentDescriptor -> {
                            return Future.future(future0 -> {
                                String onlineSrvcId = openapiInfo.getOnlineSrvcId();
                                String okapiUrl = openapiInfo.getPublishUrl();
                                okapiUrl = okapiUrl.concat("/_/discovery/modules/"+onlineSrvcId+"/"+new JsonObject(deploymentDescriptor.toString()).getString("instId"));
                                webClient.deleteAbs(okapiUrl).send(response -> {
                                    if (response.succeeded()) {
                                        if (response.result().statusCode() == 204) {
                                            logger.info("Success delete single instId: " + new JsonObject(deploymentDescriptor.toString()).getString("instId") + " of DeploymentDescriptor on Okapi");
                                            future0.complete();
                                        } else {
                                            logger.info("Fail to delete single instId: " + new JsonObject(deploymentDescriptor.toString()).getString("instId") + " of DeploymentDescriptor on Okapi" + " , httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                            future0.fail("Fail to delete single instId: " + new JsonObject(deploymentDescriptor.toString()).getString("instId") + " of DeploymentDescriptor on Okapi" + " , httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                        }
                                    } else {
                                        logger.error("Exception from Okpai when delete single instId of DeploymentDescriptor, Caused: " + response.cause().getLocalizedMessage());
                                        future0.fail("Exception from Okpai when delete DeploymentDescriptor, Caused: " + response.cause().getLocalizedMessage());
                                    }
                                });
                            });
                        }).collect(Collectors.toList())).setHandler(arAll -> {
                            if (arAll.succeeded()) {
                                logger.info("Success to delete all instIds of srvcId");
                                registDeploymentDescriptor2Okapi(openapiInfo,postOrDeleteDeploymentDescriptorFuture);
                                //postOrDeleteDeploymentDescriptorFuture.complete();
                            } else {
                                logger.error("Exception when delete all instIds of srvcId: " + openapiInfo.getOnlineSrvcId() + " ,Caused: " + arAll.cause().getLocalizedMessage());
                                postOrDeleteDeploymentDescriptorFuture.fail("Exception when delete all instId of srvcId: " + openapiInfo.getOnlineSrvcId() + " ,Caused: " + arAll.cause().getLocalizedMessage());
                            }
                        });
                    }
                }
                return postOrDeleteDeploymentDescriptorFuture;
            }).setHandler(deployRe -> {
                if (deployRe.succeeded()) {
                    logger.info("Success to regist single DeploymentDescriptor to Okapi");
                    completedDeployFuture.complete();
                } else {
                    logger.error("Exception to regist single DeploymentDescriptor to Okapi, Caused: " + deployRe.cause().getLocalizedMessage());
                    completedDeployFuture.fail("Exception to regist single DeploymentDescriptor to Okapi, Caused: " + deployRe.cause().getLocalizedMessage());
                }
            });
            // ModuleDescriptor
            startModuleFuture.compose(sf0 -> {
                Future<Boolean> sFuture0 = Future.future();
                String onlineSrvcId = openapiInfo.getOnlineSrvcId();
                String okapiUrl = openapiInfo.getPublishUrl();
                okapiUrl = okapiUrl.concat("/_/proxy/modules/"+onlineSrvcId);
                webClient.getAbs(okapiUrl)
                        //.putHeader("X-Okapi-Tenant",tenant)
                        //.putHeader("X-Okapi-Token",token)
                        .send(existModule -> {
                            if (existModule.succeeded()) {
                                if (existModule.result().statusCode() == 200) {
                                    logger.info("This OpenAPI " + openapiInfo.getName() + " has registered before, and it's ModuleDescriptor is: " + existModule.result().bodyAsJsonObject());
                                    sFuture0.complete(true);
                                } else if (existModule.result().statusCode() == 404) {
                                    logger.info("This OpenAPI hasn't registered before");
                                    sFuture0.complete(false);
                                } else {
                                    logger.error("Okapi response error: httpCode: " + existModule.result().statusCode() + ", message: " + existModule.result().bodyAsString());
                                    sFuture0.fail(existModule.result().bodyAsString());
                                }
                            } else {
                                logger.error("Emerging exception when request Okapi: " + openapiInfo.getPublishUrl().concat("/_/proxy/modules/"+onlineSrvcId) + " Caused: " + existModule.cause().getLocalizedMessage());
                                sFuture0.fail(existModule.cause());
                            }
                        });
                return sFuture0;
            }).compose(sf1 -> {
                Future<Object> sFuture1 = Future.future();
                if (sf1 == false) {
                    postModuleDescriptor2Okapi(openapiInfo, sFuture1);
                } else if (sf1 == true) {
                    String srvcId = srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".").concat("-0.0.1"));
                    if (openapiInfo.getOnlineSrvcId().contentEquals(srvcId)) {
                        recursiveUpdateModule(openapiInfo,sFuture1,getConnFutre);
                    } else {
                        recursiveDeleteModule(openapiInfo,sFuture1,getConnFutre);
                    }
                }
                return sFuture1;
            }).compose(sf2 -> {
                Future<List<JsonArray>> reAuthTenantFuture = Future.future();
                getConnFutre.setHandler(conn -> {
                    conn.result().close();
                    if (conn.succeeded()) {
                        logger.info("Get connection success");
                        conn.result().query("SELECT jsonb ->> 'unAuthTenants' FROM " + TABLE_NAME_UNAUTHORIZEDTENANTS + " WHERE jsonb ->> 'srvcId' = '" + openapiInfo.getOnlineSrvcId() + "' ", selectRe -> {
                            if (selectRe.succeeded()) {
                                List<JsonArray> unAuthTenants;
                                if (selectRe.result().getResults().isEmpty()) {
                                    unAuthTenants = new ArrayList<JsonArray>();
                                } else {
                                    logger.info("Success to select tenants who needed to reAuthorize");
                                    unAuthTenants = selectRe.result().getResults().get(0).stream().map(tenantArr -> {
                                        return new JsonArray(tenantArr.toString());
                                    }).collect(Collectors.toList());
                                }
                                System.out.println(unAuthTenants);
                                reAuthTenantFuture.complete(unAuthTenants);
                            } else {
                                logger.error("Fail to select tenants who needed to reAuthorize, Caused: " + selectRe.cause().getLocalizedMessage());
                                reAuthTenantFuture.fail("Fail to select tenants who needed to reAuthorize, Caused: " + selectRe.cause().getLocalizedMessage());
                            }
                        });
                    } else {
                        logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                        reAuthTenantFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());

                    }
                });
                return reAuthTenantFuture;
            }).compose(reAuth -> {
                Future<Object> reAuthFuture = Future.future();
                if (reAuth.isEmpty()) {
                    logger.info("Needn't to reAuthorize tenant to Okapi yet");
                    logger.info("Start single OpenAPI success");
                    reAuthFuture.complete();
                } else {
                    logger.info("Need to reAuthorize tenant to okapi again");
                    JsonArray needReAuthtenants = reAuth.get(0).copy();
                    CompositeFuture.join(reAuth.get(0).stream().map(tenantID -> {
                        return Future.future(future3 -> {
                            String okapiUrl = openapiInfo.getPublishUrl();
                            okapiUrl = okapiUrl.concat("/_/proxy/tenants/" + tenantID + "/modules");
                            webClient.postAbs(okapiUrl).putHeader("Content-Type","application/json").sendJsonObject(new JsonObject().put("id", srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".").concat("-0.0.1"))), response -> {
                                if (response.succeeded()) {
                                    if (response.result().statusCode() == 201) {
                                        logger.info("Success to ReAuthorize single teannt: " + tenantID);
                                        needReAuthtenants.remove(tenantID);
                                        future3.complete();
                                    } else {
                                        logger.error("Error to ReAuthorize single tenant: " + tenantID + " ,httpCode: " + response.result().statusCode() + " , " + response.result().bodyAsString());
                                        future3.fail("Error to ReAuthorize single tenant: " + tenantID + " ,httpCode: " + response.result().statusCode() + " , " + response.result().bodyAsString());
                                    }
                                } else {
                                    logger.error("Fail to ReAuthorize single teannt: " + tenantID + " , Caused: " + response.cause().getLocalizedMessage());
                                    future3.fail("Fail to ReAuthorize single teannt: " + tenantID + " , Caused: " + response.cause().getLocalizedMessage());
                                }
                            });
                        });
                    }).collect(Collectors.toList())).setHandler(reAuthtenantAll -> {
                        if (reAuthtenantAll.succeeded()) {
                            logger.info("ReUpdate unAuthorizedTenants");
                            getConnFutre.setHandler(conn -> {
                                conn.result().close();
                                if (conn.succeeded()) {
                                    logger.info("Get connection success");
                                    String sql;
                                    if (needReAuthtenants.isEmpty()) {
                                        logger.info("Having reAuthorized tenants inner PostgreSQL who need to be reAuthorized to ModuleDescriptor of this time.");
                                        logger.info("So need to delete record of this srvcId: " + openapiInfo.getOnlineSrvcId() + " inner postgreSQL");
                                        logger.info("Meanwhile, this is normal after each single successful starting OpenAPI operation that there in't any record of this srvcId inner table of: " + TABLE_NAME_UNAUTHORIZEDTENANTS);
                                        sql = " DELETE FROM " + TABLE_NAME_UNAUTHORIZEDTENANTS + " WHERE jsonb ->> 'srvcId' = '";
                                        conn.result().update(sql + openapiInfo.getOnlineSrvcId() + "' ", deleteRe -> {
                                            if (deleteRe.succeeded()) {
                                                logger.info("Success to delete record of srvcId: " + openapiInfo.getOnlineSrvcId());
                                                reAuthFuture.complete();
                                            } else {
                                                logger.error("Exception from Deleting record of crvcId: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + deleteRe.cause().getLocalizedMessage());
                                                reAuthFuture.fail("Exception from Deleting record of crvcId: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + deleteRe.cause().getLocalizedMessage());
                                            }
                                        });
                                    } else {
                                        conn.result().updateWithParams("UPDATE " + TABLE_NAME_UNAUTHORIZEDTENANTS + " SET jsonb = jsonb_set(jsonb,'{unAuthTenants}',?::jsonb) WHERE jsonb ->> 'srvcId' = ? ", new JsonArray().add(needReAuthtenants.toString()).add(openapiInfo.getOnlineSrvcId()), selectRe -> {
                                            if (selectRe.succeeded()) {
                                                logger.info("Success to update table tenants who needed to reAuthorize");
                                                reAuthFuture.complete();
                                            } else {
                                                logger.error("Fail to update table tenants who needed to reAuthorize, Caused: " + selectRe.cause().getLocalizedMessage());
                                                reAuthFuture.fail("Fail to update table tenants who needed to reAuthorize, Caused: " + selectRe.cause().getLocalizedMessage());
                                            }
                                        });
                                    }
                                } else {
                                    logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                                    reAuthFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());

                                }
                            });
                        } else {
                            logger.info("ReAuthorizedTenants exception, Caused: " + reAuthtenantAll.cause().getLocalizedMessage());
                            reAuthFuture.fail("ReAuthorizedTenants exception, Caused: " + reAuthtenantAll.cause().getLocalizedMessage());
                        }
                    });
                }
                return reAuthFuture;
            }).setHandler(singleRegModule -> {
                if (singleRegModule.succeeded()) {
                    logger.info("Success to regist single ModuleDescriptor to Okapi");
                    completedModuleFuture.complete();
                } else {
                    logger.error("Fail to regist single ModuleDescriptor to Okapi, Caused: " + singleRegModule.cause().getLocalizedMessage());
                    completedModuleFuture.fail("Fail to regist single ModuleDescriptor to Okapi, Caused: " + singleRegModule.cause().getLocalizedMessage());
                }
            });
            //-------------------Update onlineSrvcId and status----------------------------------
            CompositeFuture.join(completedModuleFuture, completedDeployFuture)
                    .compose(updateOnlineSrvcId -> {
                        Future<Object> updateOnlineSrvcIdFuture = Future.future();
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                conn.result().updateWithParams(" UPDATE " + TABLE_NAME_OPENAPI + " SET jsonb = jsonb_set(jsonb,'{onlineSrvcId}',?) WHERE jsonb ->> 'onlineSrvcId' = ? ", new JsonArray().add("\""+srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".")).concat("-0.0.1")+"\"").add(openapiInfo.getOnlineSrvcId()), updateSrvcIdRe -> {
                                    if (updateSrvcIdRe.succeeded()) {
                                        logger.info("Success to update onlineSrvcId of OpenAPI: " + openapiInfo.getName() + " in postgreSQL");
                                        updateOnlineSrvcIdFuture.complete();
                                    } else {
                                        logger.error("Exception from Update onlineSrvcId of OpenAPI: " + openapiInfo.getName() + " in postgreSQL, Caused: " + updateSrvcIdRe.cause().getLocalizedMessage());
                                        updateOnlineSrvcIdFuture.fail("Exception from Update onlineSrvcId of OpenAPI: " + openapiInfo.getName() + " in postgreSQL, Caused: " + updateSrvcIdRe.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                                updateOnlineSrvcIdFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });
                        return updateOnlineSrvcIdFuture;
                    }).compose(updateStatus -> {
                Future<Object> updateOpenAPIStatusFuture = Future.future();
                getConnFutre.setHandler(conn -> {
                    conn.result().close();
                    if (conn.succeeded()) {
                        logger.info("Get connection success");
                        conn.result().updateWithParams(" UPDATE " + TABLE_NAME_OPENAPI + " SET jsonb = jsonb_set(jsonb,'{status}',?) WHERE id = ? ", new JsonArray().add("1").add(openapiInfo.getId()), updateStatusRe -> {
                            if (updateStatusRe.succeeded()) {
                                logger.info("Success to update status of OpenAPI: " + openapiInfo.getName());
                                updateOpenAPIStatusFuture.complete();
                            } else {
                                logger.error("Exception from updating status of OpenAPI: " + openapiInfo.getName() + " , Caused: " + updateStatusRe.cause().getLocalizedMessage());
                                updateOpenAPIStatusFuture.fail("Exception from updating status of OpenAPI: " + openapiInfo.getName() + " , Caused: " + updateStatusRe.cause().getLocalizedMessage());
                            }
                        });
                    } else {
                        logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                        updateOpenAPIStatusFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());

                    }
                });
                return updateOpenAPIStatusFuture;
            }).setHandler(twoThreadsRe -> {
                if (twoThreadsRe.succeeded()) {
                    logger.info("Success process two threads");
                    openapis.remove(0);
                    recursiveTraverse(openapis, future);
                } else {
                    logger.error("Exception from processing two threads, Caused: " + twoThreadsRe.cause().getLocalizedMessage());
                    Future<Object> startRecordLogFuture = Future.future();
                    startRecordLogFuture.complete();
                    startRecordLogFuture.compose(recordLog -> {
                        Future<Object> recordLogFuture = Future.future();
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                JsonObject jsonb = new JsonObject();
                                jsonb.put("period", "start");
                                jsonb.put("time", simpleDateFormat.format(new Date()));
                                jsonb.put("exception", twoThreadsRe.cause().getLocalizedMessage());
                                conn.result().updateWithParams(" INSERT INTO " + TABLE_NAME_EXCEPTION + " (jsonb,openapi_id) VALUES(?,?) ", new JsonArray().add(jsonb.toString()).add(openapiInfo.getId()), recordRe -> {
                                   if (recordRe.succeeded()) {
                                        logger.info("Success to record exception into PostgreSQL table: " + TABLE_NAME_EXCEPTION);
                                        recordLogFuture.complete();
                                   } else {
                                       logger.error("Exception when record excdeption log, Caused: " + recordRe.cause().getLocalizedMessage());
                                       recordLogFuture.fail("Exception when record excdeption log, Caused: " + recordRe.cause().getLocalizedMessage());
                                   }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                                recordLogFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });
                        return recordLogFuture;
                    }).compose(recordExceptionStatus -> {
                        Future<Object> recordExceptionStatusFuture = Future.future();
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                conn.result().updateWithParams(" UPDATE " + TABLE_NAME_OPENAPI + " SET jsonb = jsonb_set(jsonb,'{status}',?) WHERE id = ? ", new JsonArray().add("3").add(openapiInfo.getId()), updateStatusRe -> {
                                    if (updateStatusRe.succeeded()) {
                                        logger.info("Exception emerged when start OpenAPI, and this process isn't overall, so need to set its status: 启用异常(3)");
                                        recordExceptionStatusFuture.fail("Exception emerged when start OpenAPI, and this process isn't overall, so need to set its status: 启用异常(3)");
                                    } else {
                                        logger.info("Exception emerged when start OpenAPI, and this process isn't overall, so need to set its status: 启用异常(3), but failed Caused: " + updateStatusRe.cause().getLocalizedMessage());
                                        recordExceptionStatusFuture.fail("Exception emerged when start OpenAPI, and this process isn't overall, so need to set its status: 启用异常(3), but failed Caused: " + updateStatusRe.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                                recordExceptionStatusFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });
                        return recordExceptionStatusFuture;
                    }).setHandler(recordLogRe -> {
                        if (recordLogRe.succeeded()) {
                            logger.info("Success to record exception and exception status in process of start OpenAPI");
                            future.fail("Exception occured, but has recorded log already in process of start OpenAPI");
                        } else {
                            logger.error("Exception when record exception and exception status in process of start OpenAPI, Caused: " + recordLogRe.cause().getLocalizedMessage());
                            future.fail("Exception when record exception and exception status in process of start OpenAPI, Caused: " + recordLogRe.cause().getLocalizedMessage());
                        }
                    });
                    //future.fail("Exception from processing two threads, Caused: " + twoThreadsRe.cause().getLocalizedMessage());
                }
            });
            //-------------------Update onlineSrvcId and status----------------------------------
        }
    }

    @Override
    public void postOpenapiStartPost(StartOpenapi entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        List<String> idArray = entity.getOpenapiIdArray();
        String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
        String token = routingContext.request().getHeader("X-Okapi-Token");
        Future<ArrayEntity> responseFuture = Future.future();

        try {
            vertxContext.runOnContext(v -> {
                Future<Object> startFuture = Future.future();
                startFuture.complete();
                startFuture.compose(f0 -> {
                    Future<List<OpenapiInfo>> future = Future.future();
                    findNotExistIds(postgresClient,idArray,asyncResultHandler).setHandler(arrayEntiy-> {
                        if (arrayEntiy.succeeded()) {
                            logger.info("判断Id是否存在成功");
                            if (arrayEntiy.result().getExistIdArray().size() != 0) {
                                logger.info("Existing ids: " + arrayEntiy.result().getExistIdArray());
                                responseFuture.complete(arrayEntiy.result());
                                future.complete(arrayEntiy.result().getOpenapiInfo());
                            } else {
                                logger.error("There isn't any id exists");
                                future.complete(null);
                            }
                        } else {
                            logger.error("判断Id是否存在失败, Caused: " + arrayEntiy.cause().getLocalizedMessage(), arrayEntiy.cause());
                            future.fail(arrayEntiy.cause().getLocalizedMessage());
                        }
                    });
                    return future;
                }).compose(f1 -> {
                    Future<Object> future = Future.future();
                    /*LinkedList linkedList = new LinkedList(f1);
                    linkedList.addAll(f1);*/
                    recursiveTraverse(f1, future);
                    return future;
                }).setHandler(result -> {
                   if (result.succeeded()){
                       logger.info("postOpenapiStartPost: Start all OpenAPIs success, Info: "+JsonObject.mapFrom(responseFuture.result()));
                       asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStartPostResponse.withJsonOK(responseFuture.result())));
                       return;
                   } else {
                       logger.error("postOpenapiStartPost: Start all OpenAPIs fail, Caused: "+result.cause().getLocalizedMessage(), result.cause());
                       logger.error("postOpenapiStartPost: Start all OpenAPIs fail, process entity: "+JsonObject.mapFrom(responseFuture.result()), result.cause());
                       asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStartPostResponse.withPlainBadRequest("Fail to start Openapi reason: "+result.cause().getLocalizedMessage())));
                       return;
                   }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStartPostResponse.withPlainInternalServerError(message)));
        }
    }

    private void postModuleDescriptor2Okapi(OpenapiInfo openapiInfo, Future sFuture1) {
        String srvcId = srvcIdPrefix.concat(openapiInfo.getName().replaceAll("/",".")).concat("-0.0.1");
        System.out.println(srvcId);
        JsonObject moduleDescriptor = new JsonObject();
        moduleDescriptor.put("id",srvcId);
        moduleDescriptor.put("name",srvcId);
        JsonArray provides = new JsonArray();
        moduleDescriptor.put("provides",provides);
        provides.add(new JsonObject().put("id",srvcId).put("version","0.0.1").put("handlers",new JsonArray().add(new JsonObject().put("methods",openapiInfo.getMethod()).put("pathPattern",openapiInfo.getName()).put("level",120).put("type", "request-response").put("permissionsRequired",new JsonArray().add("privilege.signature.check").add("privilege.clientkey.check"))))).add(new JsonObject().put("id","_tenant").put("version","1.0").put("interfaceType","system").put("handlers",new JsonArray().add(new JsonObject().put("methods",new JsonArray().add("POST").add("DELETE")).put("pathPattern","/_/tenant"))));
        String okapiUrl = openapiInfo.getPublishUrl();
        okapiUrl = okapiUrl.concat("/_/proxy/modules");
        webClient.postAbs(okapiUrl).putHeader("Content-Type","application/json")
                .sendJsonObject(moduleDescriptor, response -> {
                    if (response.succeeded()) {
                        if (response.result().statusCode() == 201) {
                            logger.info("Regist ModuleDescriptor success");
                            sFuture1.complete();
                        } else {
                            logger.info("Regist ModuleDescriptor error form Okapi: " + response.result().statusCode() + " , " + response.result().bodyAsString());
                            sFuture1.fail(response.result().bodyAsString());
                        }
                    } else {
                        logger.error("Regist ModuleDescriptor exception, Caused: " + response.cause().getLocalizedMessage(), response.cause());
                        sFuture1.fail(response.cause());
                    }
                });
    }

    private void recursiveUpdateModule(OpenapiInfo openapiInfo, Future<Object> sFuture1, Future<SQLConnection> getConnFutre) {
        /*String okapiUrl = openapiInfo.getPublishUrl();
        String srvcId = openapiInfo.getOnlineSrvcId();
        okapiUrl = okapiUrl.concat("/_/proxy/modules/" + srvcId);*/
        Future<Object> startDeleteFuture = Future.future();
        startDeleteFuture.complete();
        startDeleteFuture.compose(d0 -> {
            Future<Object> putFuture = Future.future();
            String srvcId = openapiInfo.getOnlineSrvcId();
            JsonObject moduleDescriptor = new JsonObject();
            moduleDescriptor.put("id",srvcId);
            moduleDescriptor.put("name",srvcId);
            JsonArray provides = new JsonArray();
            moduleDescriptor.put("provides",provides);
            provides.add(new JsonObject().put("id",srvcId).put("version","0.0.1").put("handlers",new JsonArray().add(new JsonObject().put("methods",openapiInfo.getMethod()).put("pathPattern",openapiInfo.getName()).put("level",120).put("type", "request-response").put("permissionsRequired",new JsonArray().add("privilege.signature.check").add("privilege.clientkey.check"))))).add(new JsonObject().put("id","_tenant").put("version","1.0").put("interfaceType","system").put("handlers",new JsonArray().add(new JsonObject().put("methods",new JsonArray().add("POST").add("DELETE")).put("pathPattern","/_/tenant"))));
            webClient.putAbs(openapiInfo.getPublishUrl().concat("/_/proxy/modules/" + openapiInfo.getOnlineSrvcId()))
                    .putHeader("Content-type", "application/json")
                    .sendJsonObject(moduleDescriptor, response -> {
                        if (response.succeeded()) {
                            if (response.result().statusCode() == 200) {
                                logger.info("Update ModuleDescriptor success");
                                putFuture.complete("success");
                            } else if (response.result().statusCode() == 400 && response.result().bodyAsString().contains("is used by tenant")){
                                String usedTenantId = response.result().bodyAsString().substring(response.result().bodyAsString().lastIndexOf("tenant ") + 7);
                                logger.info("There is tenant: " + usedTenantId + " has been authorized on Module of: " + srvcId);
                                putFuture.complete(usedTenantId);
                                //judgeSameFuture.complete("used");
                            } else {
                                logger.error("There is error from Okapi when put ModuleDescriptor: " + response.result().statusCode() + " , " +response.result().bodyAsString());
                                putFuture.fail(response.result().statusCode() + " , " +response.result().bodyAsString());
                            }
                        } else {
                            logger.error("Update ModuleDescriptor exception, Caused: " + response.cause().getLocalizedMessage(), response.cause());
                            putFuture.fail(response.cause());
                        }
            });
            return putFuture;
        }).setHandler(d1 -> {
            // Future<Object> future = Future.future();
            if (d1.result().toString().contentEquals("success")) {
                sFuture1.complete();
            } else {
                Future<Object> tenantIdFuture = Future.future();
                tenantIdFuture.complete(d1.result());
                Future<Object> startSelectIfExistFuture = Future.future();
                startSelectIfExistFuture.complete();
                startSelectIfExistFuture.compose(select -> {
                    Future<List<JsonObject>> selectFuture = Future.future();
                    getConnFutre.setHandler(conn -> {
                        conn.result().close();
                        if (conn.succeeded()) {
                            logger.info("Get connection success");
                            conn.result().query("SELECT jsonb FROM " + TABLE_NAME_UNAUTHORIZEDTENANTS + " WHERE jsonb ->> 'srvcId' = '" + openapiInfo.getOnlineSrvcId() + "' ",re -> {
                                if (re.succeeded()) {
                                    logger.info("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants success");
                                    if (!re.result().getRows().isEmpty()) {
                                        logger.info("Tenants who need to be reauthorized are not blank");
                                        //List<JsonObject> rows = re.result().getRows();
                                        re.result().getRows().stream().forEach((jsonb) -> {
                                            jsonb.put("jsonb",new JsonObject(jsonb.getString("jsonb")));
                                        });
                                        selectFuture.complete(re.result().getRows());
                                    } else {
                                        logger.error("Tenants who need to be reauthorized are blank");
                                        selectFuture.complete(new ArrayList<JsonObject>());
                                    }
                                } else {
                                    logger.error("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants exception, Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                    selectFuture.fail("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants exception, Caused: " + re.cause().getLocalizedMessage());
                                }
                            });
                        } else {
                            logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                            selectFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                        }
                    });
                    return selectFuture;
                }).compose(insertOrUpdate -> {
                    Future<Object> insertOrUpdateFuture = Future.future();
                    if (insertOrUpdate.isEmpty()) {
                        logger.info("Insert will be unAuthorized Tenant: " + tenantIdFuture.result());
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                System.out.println(new JsonArray().add(new JsonObject().put("srvcId",openapiInfo.getOnlineSrvcId()).put("unAuthTenants",new JsonArray().add(tenantIdFuture.result()))));
                                conn.result().updateWithParams("INSERT INTO " + TABLE_NAME_UNAUTHORIZEDTENANTS + " (jsonb) VALUES(?::jsonb)", new JsonArray().add(new JsonObject().put("srvcId",openapiInfo.getOnlineSrvcId()).put("unAuthTenants",new JsonArray().add(tenantIdFuture.result())).toString()),re -> {
                                    if (re.succeeded()) {
                                        logger.info("Success to insert will be unAuthorized tenant: " + tenantIdFuture.result());
                                        insertOrUpdateFuture.complete();
                                    } else {
                                        logger.error("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                        insertOrUpdateFuture.fail("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                insertOrUpdateFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });

                    } else {
                        logger.info("Update will be unAuthorized Tenant: " + tenantIdFuture.result());
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                System.out.println(insertOrUpdate.get(0).getJsonArray("unAuthTenants"));
                                List<Object> collect = insertOrUpdate.get(0).getJsonObject("jsonb").getJsonArray("unAuthTenants").stream().filter((tenantId) -> {
                                    return !String.valueOf(tenantId).contentEquals(tenantIdFuture.result().toString());
                                }).collect(Collectors.toList());
                                collect.add(tenantIdFuture.result());
                                conn.result().updateWithParams("UPDATE " + TABLE_NAME_UNAUTHORIZEDTENANTS + " SET jsonb = jsonb_set(jsonb,'{unAuthTenants}',?::jsonb) WHERE jsonb ->> 'srvcId' = ? ", new JsonArray().add(new JsonArray(collect).toString()).add(openapiInfo.getOnlineSrvcId()),re -> {
                                    if (re.succeeded()) {
                                        logger.info("Success to update will be unAuthorized tenant: " + tenantIdFuture.result());
                                        insertOrUpdateFuture.complete();
                                    } else {
                                        logger.error("Fail to update will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                        insertOrUpdateFuture.fail("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                insertOrUpdateFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });

                    }
                    return insertOrUpdateFuture;
                }).compose(unAuthTenantFromModule -> {
                    Future<Object> unAuthTenantFromModuleFuture = Future.future();
                    webClient.deleteAbs(openapiInfo.getPublishUrl().concat("/_/proxy/tenants/"+ tenantIdFuture.result() + "/modules/" + openapiInfo.getOnlineSrvcId())).send(response -> {
                        if (response.succeeded()) {
                            if (response.result().statusCode() == 204) {
                                logger.info("Success to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId());
                                unAuthTenantFromModuleFuture.complete();
                            } else {
                                logger.error("There error from Okapi when unAuthorize tenant: " + tenantIdFuture.result() + " from module: " + openapiInfo.getOnlineSrvcId() + " httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                unAuthTenantFromModuleFuture.fail("There error from Okapi when unAuthorize tenant: " + tenantIdFuture.result() + " from module: " + openapiInfo.getOnlineSrvcId() + " httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                            }
                        } else {
                            logger.error("Fail to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + response.cause().getLocalizedMessage(), response.cause());
                            unAuthTenantFromModuleFuture.fail("Fail to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + response.cause().getLocalizedMessage());
                        }
                    });
                    return unAuthTenantFromModuleFuture;
                }).setHandler(saveTenants -> {
                    if (saveTenants.succeeded()) {
                        logger.info("Success to unAuthorize tenant from module");
                        recursiveUpdateModule(openapiInfo,sFuture1,getConnFutre);
                    } else {
                        logger.error("Fail to unAuthorize tenant from module, Caused: " + saveTenants.cause().getLocalizedMessage(), saveTenants.cause());
                        sFuture1.fail("Fail to unAuthorize tenant from module, Caused: " + saveTenants.cause().getLocalizedMessage());
                    }
                });
            }
        });
    }

    private void recursiveDeleteModule(OpenapiInfo openapiInfo, Future<Object> sFuture1, Future<SQLConnection> getConnFutre) {
        /*String okapiUrl = openapiInfo.getPublishUrl();
        String srvcId = openapiInfo.getOnlineSrvcId();
        okapiUrl = okapiUrl.concat("/_/proxy/modules/" + srvcId);*/
        Future<Object> startDeleteFuture = Future.future();
        startDeleteFuture.complete();
        startDeleteFuture.compose(d0 -> {
            Future<Object> deleteFuture = Future.future();
            webClient.deleteAbs(openapiInfo.getPublishUrl().concat("/_/proxy/modules/" + openapiInfo.getOnlineSrvcId())).send(response -> {
                if (response.succeeded()) {
                    if (response.result().statusCode() == 204) {
                        logger.info("Delete ModuleDescriptor success");
                        deleteFuture.complete("free");
                    } else if (response.result().statusCode() == 400 && response.result().bodyAsString().contains("is used by tenant")){
                        String usedTenantId = response.result().bodyAsString().substring(response.result().bodyAsString().lastIndexOf("tenant ") + 7);
                        logger.info("There is tenant: " + usedTenantId + " has been authorized on Module of: " + openapiInfo.getOnlineSrvcId());
                        deleteFuture.complete(usedTenantId);
                        //judgeSameFuture.complete("used");
                    } else {
                        logger.error("There is error from Okapi when delete ModuleDescriptor: " + response.result().statusCode() + " , " +response.result().bodyAsString());
                        deleteFuture.fail(response.result().statusCode() + " , " +response.result().bodyAsString());
                    }
                } else {
                    logger.error("Delete ModuleDescriptor exception, Caused: " + response.cause().getLocalizedMessage(), response.cause());
                    deleteFuture.fail(response.cause());
                }
            });
            return deleteFuture;
        }).setHandler(d1 -> {
            // Future<Object> future = Future.future();
            if (d1.result().toString().contentEquals("free")) {
                postModuleDescriptor2Okapi(openapiInfo, sFuture1);
            } else {
                Future<Object> tenantIdFuture = Future.future();
                tenantIdFuture.complete(d1.result());
                Future<Object> startSelectIfExistFuture = Future.future();
                startSelectIfExistFuture.complete();
                startSelectIfExistFuture.compose(select -> {
                    Future<List<JsonObject>> selectFuture = Future.future();
                    getConnFutre.setHandler(conn -> {
                        conn.result().close();
                        if (conn.succeeded()) {
                            logger.info("Get connection success");
                            conn.result().query("SELECT jsonb FROM " + TABLE_NAME_UNAUTHORIZEDTENANTS + " WHERE jsonb ->> 'srvcId' = '" + openapiInfo.getOnlineSrvcId() + "' ",re -> {
                                if (re.succeeded()) {
                                    logger.info("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants success");
                                    if (!re.result().getRows().isEmpty()) {
                                        logger.info("Tenants who need to be reauthorized are not blank");
                                        //List<JsonObject> rows = re.result().getRows();
                                        re.result().getRows().stream().forEach((jsonb) -> {
                                            jsonb.put("jsonb",new JsonObject(jsonb.getString("jsonb")));
                                        });
                                        selectFuture.complete(re.result().getRows());
                                    } else {
                                        logger.error("Tenants who need to be reauthorized are blank");
                                        selectFuture.complete(new ArrayList<JsonObject>());
                                    }
                                } else {
                                    logger.error("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants exception, Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                    selectFuture.fail("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants exception, Caused: " + re.cause().getLocalizedMessage());
                                }
                            });
                        } else {
                            logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                            selectFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                        }
                    });
                    return selectFuture;
                }).compose(insertOrUpdate -> {
                    Future<Object> insertOrUpdateFuture = Future.future();
                    if (insertOrUpdate.isEmpty()) {
                        logger.info("Insert will be unAuthorized Tenant: " + tenantIdFuture.result());
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                           if (conn.succeeded()) {
                               logger.info("Get connection success");
                               logger.info(new JsonObject().put("srvcId",openapiInfo.getOnlineSrvcId()).put("unAuthTenants",new JsonArray().add(tenantIdFuture.result())).toString());
                               conn.result().updateWithParams("INSERT INTO " + TABLE_NAME_UNAUTHORIZEDTENANTS + " (jsonb) VALUES(?::jsonb)", new JsonArray().add(new JsonObject().put("srvcId",openapiInfo.getOnlineSrvcId()).put("unAuthTenants",new JsonArray().add(tenantIdFuture.result())).toString()),re -> {
                                    if (re.succeeded()) {
                                        logger.info("Success to insert will be unAuthorized tenant: " + tenantIdFuture.result());
                                        insertOrUpdateFuture.complete();
                                    } else {
                                        logger.error("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                        insertOrUpdateFuture.fail("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                    }
                               });
                           } else {
                               logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                               insertOrUpdateFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                           }
                        });

                    } else {
                        logger.info("Update will be unAuthorized Tenant: " + tenantIdFuture.result());
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                List<Object> collect = insertOrUpdate.get(0).getJsonObject("jsonb").getJsonArray("unAuthTenants").stream().filter((tenantId) -> {
                                    return !String.valueOf(tenantId).contentEquals(tenantIdFuture.result().toString());
                                }).collect(Collectors.toList());
                                collect.add(tenantIdFuture.result());
                                conn.result().updateWithParams("UPDATE " + TABLE_NAME_UNAUTHORIZEDTENANTS + " SET jsonb = jsonb_set(jsonb,'{unAuthTenants}',?::jsonb) WHERE jsonb ->> 'srvcId' = ? ", new JsonArray().add(new JsonArray(collect).toString()).add(openapiInfo.getOnlineSrvcId()),re -> {
                                    if (re.succeeded()) {
                                        logger.info("Success to update will be unAuthorized tenant: " + tenantIdFuture.result());
                                        insertOrUpdateFuture.complete();
                                    } else {
                                        logger.error("Fail to update will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                        insertOrUpdateFuture.fail("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                insertOrUpdateFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });

                    }
                    return insertOrUpdateFuture;
                }).compose(unAuthTenantFromModule -> {
                    Future<Object> unAuthTenantFromModuleFuture = Future.future();
                    webClient.deleteAbs(openapiInfo.getPublishUrl().concat("/_/proxy/tenants/"+ tenantIdFuture.result() + "/modules/" + openapiInfo.getOnlineSrvcId())).send(response -> {
                        if (response.succeeded()) {
                            if (response.result().statusCode() == 204) {
                                logger.info("Success to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId());
                                unAuthTenantFromModuleFuture.complete();
                            } else {
                                logger.error("There error from Okapi when unAuthorize tenant: " + tenantIdFuture.result() + " from module: " + openapiInfo.getOnlineSrvcId() + " httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                unAuthTenantFromModuleFuture.fail("There error from Okapi when unAuthorize tenant: " + tenantIdFuture.result() + " from module: " + openapiInfo.getOnlineSrvcId() + " httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                            }
                        } else {
                            logger.error("Fail to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + response.cause().getLocalizedMessage(), response.cause());
                            unAuthTenantFromModuleFuture.fail("Fail to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + response.cause().getLocalizedMessage());
                        }
                    });
                    return unAuthTenantFromModuleFuture;
                }).setHandler(saveTenants -> {
                   if (saveTenants.succeeded()) {
                        logger.info("Success to unAuthorize tenant from module");
                        recursiveDeleteModule(openapiInfo,sFuture1,getConnFutre);
                   } else {
                        logger.error("Fail to unAuthorize tenant from module, CAused: " + saveTenants.cause().getLocalizedMessage(), saveTenants.cause());
                        sFuture1.fail("Fail to unAuthorize tenant from module, CAused: " + saveTenants.cause().getLocalizedMessage());
                   }
                });
            }
            //return future;
        })/*.setHandler(singleRe -> {
            if (singleRe.succeeded()) {
                logger.info("Success to direct to Post Modules to Okapi or delete tenant from Module before post");

            } else {
                logger.error("Fail to direct to Post Modules to Okapi or delete tenant from Module before post");
            }

        })*/;

    }

    private void recursiveDeleteModuleStop(OpenapiInfo openapiInfo, Future<Object> sFuture1, Future<SQLConnection> getConnFutre) {
        /*String okapiUrl = openapiInfo.getPublishUrl();
        String srvcId = openapiInfo.getOnlineSrvcId();
        okapiUrl = okapiUrl.concat("/_/proxy/modules/" + srvcId);*/
        Future<Object> startDeleteFuture = Future.future();
        startDeleteFuture.complete();
        startDeleteFuture.compose(d0 -> {
            Future<Object> deleteFuture = Future.future();
            webClient.deleteAbs(openapiInfo.getPublishUrl().concat("/_/proxy/modules/" + openapiInfo.getOnlineSrvcId())).send(response -> {
                if (response.succeeded()) {
                    if (response.result().statusCode() == 204) {
                        logger.info("Delete ModuleDescriptor success");
                        deleteFuture.complete("free");
                    } else if (response.result().statusCode() == 400 && response.result().bodyAsString().contains("is used by tenant")){
                        String usedTenantId = response.result().bodyAsString().substring(response.result().bodyAsString().lastIndexOf("tenant ") + 7);
                        logger.info("There is tenant: " + usedTenantId + " has been authorized on Module of: " + openapiInfo.getOnlineSrvcId());
                        deleteFuture.complete(usedTenantId);
                        //judgeSameFuture.complete("used");
                    } else {
                        logger.error("There is error from Okapi when delete ModuleDescriptor: " + response.result().statusCode() + " , " +response.result().bodyAsString());
                        deleteFuture.fail(response.result().statusCode() + " , " +response.result().bodyAsString());
                    }
                } else {
                    logger.error("Delete ModuleDescriptor exception, Caused: " + response.cause().getLocalizedMessage(), response.cause());
                    deleteFuture.fail(response.cause());
                }
            });
            return deleteFuture;
        }).setHandler(d1 -> {
            // Future<Object> future = Future.future();
            if (d1.result().toString().contentEquals("free")) {
                logger.info("Success to unAuthorize ModuleDescriptor form OKapi");
                sFuture1.complete();
            } else {
                Future<Object> tenantIdFuture = Future.future();
                tenantIdFuture.complete(d1.result());
                Future<Object> startSelectIfExistFuture = Future.future();
                startSelectIfExistFuture.complete();
                startSelectIfExistFuture.compose(select -> {
                    Future<List<JsonObject>> selectFuture = Future.future();
                    getConnFutre.setHandler(conn -> {
                        conn.result().close();
                        if (conn.succeeded()) {
                            logger.info("Get connection success");
                            conn.result().query("SELECT jsonb FROM " + TABLE_NAME_UNAUTHORIZEDTENANTS + " WHERE jsonb ->> 'srvcId' = '" + openapiInfo.getOnlineSrvcId() + "' ",re -> {
                                if (re.succeeded()) {
                                    logger.info("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants success");
                                    if (!re.result().getRows().isEmpty()) {
                                        logger.info("Tenants who need to be reauthorized are not blank");
                                        //List<JsonObject> rows = re.result().getRows();
                                        re.result().getRows().stream().forEach((jsonb) -> {
                                            jsonb.put("jsonb",new JsonObject(jsonb.getString("jsonb")));
                                        });
                                        selectFuture.complete(re.result().getRows());
                                    } else {
                                        logger.error("Tenants who need to be reauthorized are blank");
                                        selectFuture.complete(new ArrayList<JsonObject>());
                                    }
                                } else {
                                    logger.error("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants exception, Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                    selectFuture.fail("Select if srvcId: " + openapiInfo.getOnlineSrvcId() + " has unauthorized tenants exception, Caused: " + re.cause().getLocalizedMessage());
                                }
                            });
                        } else {
                            logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            selectFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                        }
                    });
                    return selectFuture;
                }).compose(insertOrUpdate -> {
                    Future<Object> insertOrUpdateFuture = Future.future();
                    if (insertOrUpdate.isEmpty()) {
                        logger.info("Insert will be unAuthorized Tenant: " + tenantIdFuture.result());
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                System.out.println(new JsonObject().put("srvcId",openapiInfo.getOnlineSrvcId()).put("unAuthTenants",new JsonArray().add(tenantIdFuture.result())).toString());
                                conn.result().updateWithParams("INSERT INTO " + TABLE_NAME_UNAUTHORIZEDTENANTS + " (jsonb) VALUES(?::jsonb)", new JsonArray().add(new JsonObject().put("srvcId",openapiInfo.getOnlineSrvcId()).put("unAuthTenants",new JsonArray().add(tenantIdFuture.result())).toString()),re -> {
                                    if (re.succeeded()) {
                                        logger.info("Success to insert will be unAuthorized tenant: " + tenantIdFuture.result());
                                        insertOrUpdateFuture.complete();
                                    } else {
                                        logger.error("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                        insertOrUpdateFuture.fail("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                insertOrUpdateFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });

                    } else {
                        logger.info("Update will be unAuthorized Tenant: " + tenantIdFuture.result());
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                List<Object> collect = insertOrUpdate.get(0).getJsonObject("jsonb").getJsonArray("unAuthTenants").stream().filter((tenantId) -> {
                                    return !String.valueOf(tenantId).contentEquals(tenantIdFuture.result().toString());
                                }).collect(Collectors.toList());
                                collect.add(tenantIdFuture.result());
                                conn.result().updateWithParams("UPDATE " + TABLE_NAME_UNAUTHORIZEDTENANTS + " SET jsonb = jsonb_set(jsonb,'{unAuthTenants}',?::jsonb) WHERE jsonb ->> 'srvcId' = ? ", new JsonArray().add(new JsonArray(collect).toString()).add(openapiInfo.getOnlineSrvcId()),re -> {
                                    if (re.succeeded()) {
                                        logger.info("Success to update will be unAuthorized tenant: " + tenantIdFuture.result());
                                        insertOrUpdateFuture.complete();
                                    } else {
                                        logger.error("Fail to update will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage(), re.cause());
                                        insertOrUpdateFuture.fail("Fail to insert will be unAuthorized tenant: " + tenantIdFuture.result() + " , Caused: " + re.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                insertOrUpdateFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });

                    }
                    return insertOrUpdateFuture;
                }).compose(unAuthTenantFromModule -> {
                    Future<Object> unAuthTenantFromModuleFuture = Future.future();
                    webClient.deleteAbs(openapiInfo.getPublishUrl().concat("/_/proxy/tenants/"+ tenantIdFuture.result() + "/modules/" + openapiInfo.getOnlineSrvcId())).send(response -> {
                        if (response.succeeded()) {
                            if (response.result().statusCode() == 204) {
                                logger.info("Success to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId());
                                unAuthTenantFromModuleFuture.complete();
                            } else {
                                logger.error("There error from Okapi when unAuthorize tenant: " + tenantIdFuture.result() + " from module: " + openapiInfo.getOnlineSrvcId() + " httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                unAuthTenantFromModuleFuture.fail("There error from Okapi when unAuthorize tenant: " + tenantIdFuture.result() + " from module: " + openapiInfo.getOnlineSrvcId() + " httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                            }
                        } else {
                            logger.error("Fail to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + response.cause().getLocalizedMessage(), response.cause());
                            unAuthTenantFromModuleFuture.fail("Fail to unAuthorize tenant: " + tenantIdFuture.result() + " from Module: " + openapiInfo.getOnlineSrvcId() + " , Caused: " + response.cause().getLocalizedMessage());
                        }
                    });
                    return unAuthTenantFromModuleFuture;
                }).setHandler(saveTenants -> {
                    if (saveTenants.succeeded()) {
                        logger.info("Success to unAuthorize tenant from module");
                        recursiveDeleteModuleStop(openapiInfo,sFuture1,getConnFutre);
                    } else {
                        logger.error("Fail to unAuthorize tenant from module, CAused: " + saveTenants.cause().getLocalizedMessage(), saveTenants.cause());
                        sFuture1.fail("Fail to unAuthorize tenant from module, CAused: " + saveTenants.cause().getLocalizedMessage());
                    }
                });
            }
            //return future;
        })/*.setHandler(singleRe -> {
            if (singleRe.succeeded()) {
                logger.info("Success to direct to Post Modules to Okapi or delete tenant from Module before post");

            } else {
                logger.error("Fail to direct to Post Modules to Okapi or delete tenant from Module before post");
            }

        })*/;

    }

    private void recursiveTraverseStop(List<OpenapiInfo> openapis, Future<Object> future) {
        if (openapis.isEmpty()) {
            logger.info("Success to traverse stop ModuleDescriptor and DeploymentDescriptor");
            future.complete();
        } else {
            logger.info("Need to traverse to stop ModuleDescriptor and DeploymentDescriptor");
            OpenapiInfo openapiInfo = openapis.get(0);
            Future<SQLConnection> getConnFutre = Future.future();
            sqlClient.getConnection(getConnFutre);

            //----------------------Start two threads-------------------------
            // ModuleDescriptor
            Future<Object> startModuleFuture = Future.future();
            startModuleFuture.complete();
            Future<Object> completedModuleFuture = Future.future();

            // DeploymentDescriptor
            Future<Object> startDeployFuture = Future.future();
            startDeployFuture.complete();
            Future<Object> completedDeployFuture = Future.future();
            //----------------------Start two threads-------------------------
            startDeployFuture.compose(existDeploy -> {
                Future<Object> getDeployFuture = Future.future();
                String onlineSrvcId = openapiInfo.getOnlineSrvcId();
                String okapiUrl = openapiInfo.getPublishUrl();
                okapiUrl = okapiUrl.concat("/_/discovery/modules/" + onlineSrvcId);
                webClient.getAbs(okapiUrl).send(response -> {
                    if (response.succeeded()) {
                        if (response.result().statusCode() == 200) {
                            logger.info("Success to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId());
                            getDeployFuture.complete(response.result().bodyAsJsonArray());
                        } else if (response.result().statusCode() == 404) {
                            logger.error("Not found DeploymentDescriptor of srvcId: " + onlineSrvcId + " from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId());
                            getDeployFuture.complete(false);
                        } else {
                            logger.error("Fail to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", httpCode: " + response.result().statusCode() + " , message: " + response.result().bodyAsString());
                            getDeployFuture.fail("Fail to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", httpCode: " + response.result().statusCode() + " , message: " + response.result().bodyAsString());
                        }
                    } else {
                        logger.error("Exception to get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", Caused: " + response.cause().getLocalizedMessage(), response.cause());
                        getDeployFuture.fail("Get DeploymentDescriptor from Okapi: " + openapiInfo.getOnlineSrvcId().concat("/_/discovery/modules/") + openapiInfo.getOnlineSrvcId() + ", Caused: " + response.cause().getLocalizedMessage());
                    }
                });
                return getDeployFuture;
            }).setHandler(unAuthDeployRe -> {
                if (unAuthDeployRe.succeeded()) {
                    if (unAuthDeployRe.result().equals(false)) {
                        logger.info("DeploymentDescriptor hasn't beed authorized to Okapi yet");
                        completedDeployFuture.complete();
                    } else {
                        logger.info("DeploymentDescriptor has beed authorized to Okapi yet, so need to aunthorize them");
                        CompositeFuture.join(new JsonArray(unAuthDeployRe.result().toString()).stream().map(deploymentDescriptor -> {
                            return Future.future(future0 -> {
                                String onlineSrvcId = openapiInfo.getOnlineSrvcId();
                                String okapiUrl = openapiInfo.getPublishUrl();
                                okapiUrl = okapiUrl.concat("/_/discovery/modules/"+onlineSrvcId+"/"+new JsonObject(deploymentDescriptor.toString()).getString("instId"));
                                webClient.deleteAbs(okapiUrl).send(response -> {
                                    if (response.succeeded()) {
                                        if (response.result().statusCode() == 204) {
                                            logger.info("Success delete single instId: " + new JsonObject(deploymentDescriptor.toString()).getString("instId") + " of DeploymentDescriptor on Okapi");
                                            future0.complete();
                                        } else {
                                            logger.info("Fail to delete single instId: " + new JsonObject(deploymentDescriptor.toString()).getString("instId") + " of DeploymentDescriptor on Okapi" + " , httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                            future0.fail("Fail to delete single instId: " + new JsonObject(deploymentDescriptor.toString()).getString("instId") + " of DeploymentDescriptor on Okapi" + " , httpCode: " + response.result().statusCode() + " ,message: " + response.result().bodyAsString());
                                        }
                                    } else {
                                        logger.error("Exception from Okpai when delete single instId of DeploymentDescriptor, Caused: " + response.cause().getLocalizedMessage(), response.cause());
                                        future0.fail("Exception from Okpai when delete DeploymentDescriptor, Caused: " + response.cause().getLocalizedMessage());
                                    }
                                });
                            });
                        }).collect(Collectors.toList())).setHandler(arAll -> {
                            if (arAll.succeeded()) {
                                logger.info("Success to delete all instIds of srvcId");
                                completedDeployFuture.complete();
                            } else {
                                logger.error("Exception when delete all instIds of srvcId: " + openapiInfo.getOnlineSrvcId() + " ,Caused: " + arAll.cause().getLocalizedMessage(), arAll.cause());
                                completedDeployFuture.fail("Exception when delete all instId of srvcId: " + openapiInfo.getOnlineSrvcId() + " ,Caused: " + arAll.cause().getLocalizedMessage());
                            }
                        });
                    }
                } else {
                    logger.error("Exception when unauthorize DeploymentDescriptor form Okapi, Caused: " + unAuthDeployRe.cause().getLocalizedMessage(), unAuthDeployRe.cause());
                    completedDeployFuture.fail("Exception when unauthorize DeploymentDescriptor form Okapi, Caused: " + unAuthDeployRe.cause().getLocalizedMessage());
                }
            });
            // ModuleDescriptor
            startModuleFuture.compose(sf0 -> {
                Future<Boolean> sFuture0 = Future.future();
                String onlineSrvcId = openapiInfo.getOnlineSrvcId();
                String okapiUrl = openapiInfo.getPublishUrl();
                okapiUrl = okapiUrl.concat("/_/proxy/modules/" + onlineSrvcId);
                webClient.getAbs(okapiUrl)
                        //.putHeader("X-Okapi-Tenant",tenant)
                        //.putHeader("X-Okapi-Token",token)
                        .send(existModule -> {
                            if (existModule.succeeded()) {
                                if (existModule.result().statusCode() == 200) {
                                    logger.info("This OpenAPI " + openapiInfo.getName() + " has registered before, and it's ModuleDescriptor is: " + existModule.result().bodyAsJsonObject());
                                    sFuture0.complete(true);
                                } else if (existModule.result().statusCode() == 404) {
                                    logger.info("This OpenAPI hasn't registered before");
                                    sFuture0.complete(false);
                                } else {
                                    logger.error("Okapi response error: httpCode: " + existModule.result().statusCode() + ", message: " + existModule.result().bodyAsString());
                                    sFuture0.fail(existModule.result().bodyAsString());
                                }
                            } else {
                                logger.error("Emerging exception when request Okapi: " + openapiInfo.getPublishUrl().concat("/_/proxy/modules/" + onlineSrvcId) + " Caused: " + existModule.cause().getLocalizedMessage(), existModule.cause());
                                sFuture0.fail(existModule.cause());
                            }
                        });
                return sFuture0;
            }).setHandler(unAuthModuleRe -> {
                if (unAuthModuleRe.succeeded()) {
                    if (unAuthModuleRe.result() == false) {
                        logger.info("ModuleDescriptor hasn't beed authorized to Okapi yet");
                        completedModuleFuture.complete();
                    } else {
                        logger.info("ModuleDescriptor has beed authorized to Okapi yet, so need to aunthorize them");
                        recursiveDeleteModuleStop(openapiInfo, completedModuleFuture, getConnFutre);
                    }
                } else {
                    logger.error("Exception when unauthorize ModuleDescriptor form Okapi, Caused: " + unAuthModuleRe.cause().getLocalizedMessage(), unAuthModuleRe.cause());
                    completedModuleFuture.fail("Exception when unauthorize ModuleDescriptor form Okapi, Caused: " + unAuthModuleRe.cause().getLocalizedMessage());
                }
            });
            //-------------------Update status----------------------------------
            CompositeFuture.join(completedModuleFuture, completedDeployFuture)
                    .compose(updateStatus -> {
                        Future<Object> updateOpenAPIStatusFuture = Future.future();
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                conn.result().updateWithParams(" UPDATE " + TABLE_NAME_OPENAPI + " SET jsonb = jsonb_set(jsonb,'{status}',?) WHERE id = ? ", new JsonArray().add("2").add(openapiInfo.getId()), updateStatusRe -> {
                                    conn.result().close();
                                    if (updateStatusRe.succeeded()) {
                                        logger.info("Success to update status of OpenAPI: " + openapiInfo.getName());
                                        updateOpenAPIStatusFuture.complete();
                                    } else {
                                        logger.error("Exception from updating status of OpenAPI: " + openapiInfo.getName() + " , Caused: " + updateStatusRe.cause().getLocalizedMessage());
                                        updateOpenAPIStatusFuture.fail("Exception from updating status of OpenAPI: " + openapiInfo.getName() + " , Caused: " + updateStatusRe.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                updateOpenAPIStatusFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });
                        return updateOpenAPIStatusFuture;
                    }).setHandler(twoThreadsRe -> {
                if (twoThreadsRe.succeeded()) {
                    logger.info("Success process two threads");
                    openapis.remove(0);
                    recursiveTraverseStop(openapis, future);
                } else {
                    logger.error("Exception from processing two threads, Caused: " + twoThreadsRe.cause().getLocalizedMessage());
                    Future<Object> startRecordLogFuture = Future.future();
                    startRecordLogFuture.complete();
                    startRecordLogFuture.compose(recordLog -> {
                        Future<Object> recordLogFuture = Future.future();
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                JsonObject jsonb = new JsonObject();
                                jsonb.put("period", "stop");
                                jsonb.put("time", simpleDateFormat.format(new Date()));
                                jsonb.put("exception", twoThreadsRe.cause().getLocalizedMessage());
                                conn.result().updateWithParams(" INSERT INTO " + TABLE_NAME_EXCEPTION + " (jsonb,openapi_id) VALUES(?,?) ", new JsonArray().add(jsonb.toString()).add(openapiInfo.getId()), recordRe -> {
                                    if (recordRe.succeeded()) {
                                        logger.info("Success to record exception into PostgreSQL table: " + TABLE_NAME_EXCEPTION);
                                        recordLogFuture.complete();
                                    } else {
                                        logger.error("Exception when record excdeption log, Caused: " + recordRe.cause().getLocalizedMessage(), recordRe.cause());
                                        recordLogFuture.fail("Exception when record excdeption log, Caused: " + recordRe.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                recordLogFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });
                        return recordLogFuture;
                    }).compose(recordExceptionStatus -> {
                        Future<Object> recordExceptionStatusFuture = Future.future();
                        getConnFutre.setHandler(conn -> {
                            conn.result().close();
                            if (conn.succeeded()) {
                                logger.info("Get connection success");
                                conn.result().updateWithParams(" UPDATE " + TABLE_NAME_OPENAPI + " SET jsonb = jsonb_set(jsonb,'{status}',?) WHERE id = ? ", new JsonArray().add("4").add(openapiInfo.getId()), updateStatusRe -> {
                                    if (updateStatusRe.succeeded()) {
                                        logger.info("Exception emerged when stop OpenAPI, and this process isn't overall, so need to set its status: 启用异常(4)");
                                        recordExceptionStatusFuture.fail("Exception emerged when start OpenAPI, and this process isn't overall, so need to set its status: 启用异常(4)");
                                    } else {
                                        logger.info("Exception emerged when stop OpenAPI, and this process isn't overall, so need to set its status: 启用异常(4), but failed Caused: " + updateStatusRe.cause().getLocalizedMessage(), updateStatusRe.cause());
                                        recordExceptionStatusFuture.fail("Exception emerged when stop OpenAPI, and this process isn't overall, so need to set its status: 启用异常(4), but failed Caused: " + updateStatusRe.cause().getLocalizedMessage());
                                    }
                                });
                            } else {
                                logger.error("Get connection exception, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                                recordExceptionStatusFuture.fail("Get connection exception, Caused: " + conn.cause().getLocalizedMessage());
                            }
                        });
                        return recordExceptionStatusFuture;
                    }).setHandler(recordLogRe -> {
                        if (recordLogRe.succeeded()) {
                            logger.info("Success to record exception and exception status in process of stop OpenAPI");
                            future.fail("Exception occured, but has recorded log already in process of stop OpenAPI");
                        } else {
                            logger.error("Exception when record exception and exception status in process of stop OpenAPI, Caused: " + recordLogRe.cause().getLocalizedMessage(), recordLogRe.cause());
                            future.fail("Exception when record exception and exception status in process of stop OpenAPI, Caused: " + recordLogRe.cause().getLocalizedMessage());
                        }
                    });
                    //future.fail("Exception from processing two threads, Caused: " + twoThreadsRe.cause().getLocalizedMessage());
                }
            });
            //-------------------Update status----------------------------------
        }
    }

    @Override
    public void postOpenapiStopPost(StopOpenapi entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(RestVerticle.OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        List<String> idArray = entity.getOpenapiIdArray();
        Future<ArrayEntity> responseFuture = Future.future();
        try {
            vertxContext.runOnContext(v -> {
                Future<Object> startFuture = Future.future();
                startFuture.complete();
                startFuture.compose(f0 -> {
                    Future<List<OpenapiInfo>> future = Future.future();
                    findNotExistIds(postgresClient,idArray,asyncResultHandler).setHandler(arrayEntiy-> {
                        if (arrayEntiy.succeeded()) {
                            logger.info("判断Id是否存在成功");
                            if (arrayEntiy.result().getExistIdArray().size() != 0) {
                                logger.info("Existing ids: " + arrayEntiy.result().getExistIdArray());
                                responseFuture.complete(arrayEntiy.result());
                                future.complete(arrayEntiy.result().getOpenapiInfo());
                            } else {
                                logger.error("There isn't any id exists");
                                future.complete(null);
                            }
                        } else {
                            logger.error("判断Id是否存在失败");
                            future.fail(arrayEntiy.cause().getLocalizedMessage());
                        }
                    });
                    return future;
                }).compose(f1 -> {
                    Future<Object> future = Future.future();
                    recursiveTraverseStop(f1, future);
                    return future;
                }).setHandler(stopRe -> {
                    if (stopRe.succeeded()) {
                        logger.info("postOpenapiStopPost: startOrStopExistIds 停用OpenAPI成功, Info: "+JsonObject.mapFrom(responseFuture.result()));
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withJsonOK(responseFuture.result())));
                    } else {
                        logger.error("Exception when stop OpenAPI, Caused: " + stopRe.cause().getLocalizedMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainBadRequest("Exception when stop OpenAPI, Caused: " + stopRe.cause().getLocalizedMessage())));
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainInternalServerError(message)));
        }

        /*try {
            vertxContext.runOnContext(v -> {
                findNotExistIds(postgresClient,idArray,asyncResultHandler).setHandler(arrayEntiy->{
                    if (arrayEntiy.succeeded()) {
                        logger.info("判断Id是否存在成功");
                        if (arrayEntiy.result().getExistIdArray().size() != 0) {
                            startOrStopExistIds(postgresClient,arrayEntiy.result(),"2").setHandler(start->{
                                if (start.succeeded()){
                                    try {
                                        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String date = simpleDateFormat.format(new Date());
                                        RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operate","停用OpenAPI").put("content",JsonObject.mapFrom(start.result())).put("openapiIdArray",entity.getOpenapiIdArray()).put("create_time",date)).setHandler(ar0->{
                                            if (ar0.succeeded()){
                                                if (ar0.result()){
                                                    logger.info("postOpenapiStopPost: startOrStopExistIds 停用OpenAPI成功, Info: "+JsonObject.mapFrom(start.result()));
                                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withJsonOK(start.result())));
                                                } else {

                                                }
                                            } else {
                                                logger.info("postOpenapiStopPost: startOrStopExistIds 停用OpenAPI失败, Info: "+JsonObject.mapFrom(start.result()));
                                                asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainBadRequest("Fail to stop Openapi reason: "+ar0.cause().getMessage())));
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainInternalServerError(message)));
                                    }
                                } else {
                                    logger.error("postOpenapiStopPost: startOrStopExistIds 停用OpenAPI出现异常,Caused: "+start.cause().getMessage());
                                    asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainBadRequest("停用OpenAPI出现异常,Caused: "+start.cause().getMessage())));
                                }
                            });
                        } else if (arrayEntiy.result().getNotExistIdArray().size() == idArray.size()) {
                            logger.error("postOpenapiStopPost: findNotExistIds所有Id: " + Arrays.toString(arrayEntiy.result().getNotExistIdArray().toArray()) + " 都不存在");
                            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainNotFound("所有Id: " + Arrays.toString(arrayEntiy.result().getNotExistIdArray().toArray()) + " 都不存在")));
                        }
                    } else {
                        logger.error("postOpenapiStopPost: findNotExistIds 判断Id是否存在出现错误,Caused: "+ arrayEntiy.cause().getMessage());
                        asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainBadRequest("判断Id是否存在出现错误, Caused: "+arrayEntiy.cause().getMessage())));
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostOpenapiStopPostResponse.withPlainInternalServerError(message)));
        }*/
    }

}
