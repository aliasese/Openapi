package org.folio.rest.impl;

import com.cnebula.lsp.openapi.utils.IPUtils;
import com.cnebula.lsp.openapi.utils.MD5Util;
import com.cnebula.lsp.openapi.utils.PreAuthModOpenapiUtil;
import com.cnebula.lsp.openapi.utils.RecordLogs;
import io.netty.util.internal.StringUtil;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.apache.commons.lang3.StringUtils;
import org.folio.rest.jaxrs.model.*;
import org.folio.rest.jaxrs.resource.ClientkeyResource;
import org.folio.rest.persist.Criteria.*;
import org.folio.rest.persist.PostgresClient;
import org.folio.rest.tools.utils.TenantTool;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by calis on 2017/10/17.
 */
public class ClientKeyImpl implements ClientkeyResource {
    public static final String TABLE_NAME_CLIENTKEY = "tb_clientkey";
    private static final String OKAPI_HEADER_TENANT = "x-okapi-tenant";
    private static final String ID_FIELD = "id";
    private static WebClient webClient;
    private AsyncSQLClient sqlClient;
    private final Logger logger = LoggerFactory.getLogger(ClientKeyImpl.class);
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @see 使用RMB开发的项目，在请求接口时，必须在header添加X-Okapi-Tenant属性
     *      若使用FOLIO封装的PostgresClient操作数据库，在该实现类的带参构造里添加如下：
     *      PostgresClient.getInstance(vertx,tenantId).setIdField(ID_FIELD);
     *      对PostgresClient进行实例化，则要求PostgreSQL表所在schema命名规范必须满足：
     *      {tenant}_{artifactId},
     *      例如：X-Okapi-Tenant:testlib
     *           <artifactId>mod-openapi</artifactId>
     *       则：schema:testlib_mod_openapi
     * @param vertx
     * @param tenantId
     */
    public ClientKeyImpl(Vertx vertx, String tenantId) {
        logger.info("tenantId: "+tenantId);
        PostgresClient.getInstance(vertx, tenantId).setIdField(ID_FIELD);
        sqlClient = PostgreSQLClient.createShared(vertx, PostgresClient.getInstance(vertx, tenantId).getConnectionConfig());
        webClient = WebClient.create(vertx, new WebClientOptions().setConnectTimeout(5 * 1000));//5秒超时
    }

    private Future<Boolean> checkExist(Criterion criterion,PostgresClient postgresClient, Future<Boolean> future) throws Exception{
        if (!criterion.toString().trim().contentEquals("")){
            postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,true,true,reply->{
                if (reply.succeeded()){
                    if (reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0) {
                        logger.info("checkExist: tenantId has already existed!");
                        future.complete(true);
                    } else {
                        future.complete(false);
                    }
                } else {
                    logger.error("checkExist: 验证tenantId是否存在出现异常,Caused: "+reply.cause().getMessage());
                    future.fail("验证tenantId是否存在出现异常,Caused: "+reply.cause().getMessage());
                }
            });
        } else {
            logger.error("clientId不能为空");
            future.fail("clientId不能为空");
        }
        return future;
    }

    @Override
    public void postClientkey(ClientKeyApplyData entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        String token = routingContext.request().getHeader("X-Okapi-Token");
        String hostPort = routingContext.request().getHeader("Host");
        String tenant_id = entity.getClientKeyApplyData().getTenantID();
        PostgresClient.getInstance(vertxContext.owner(), tenant_id).setIdField(ID_FIELD);
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(),tenant_id);
        try {
            ClientKeyApplyData_ clientKeyApplyData = entity.getClientKeyApplyData();
            if (!StringUtil.isNullOrEmpty(clientKeyApplyData.getAppUrl())) {
                String[] ipV4Arr = clientKeyApplyData.getAppUrl().split(",");
                for (int i = 0; i < ipV4Arr.length; i++) {
                    Boolean b = IPUtils.checkIPV4(ipV4Arr[i]);
                    if (!b){
                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest("应用访问地址请输入正确IPV4")));
                        return;
                    }
                }
            }
            String date = simpleDateFormat.format(new Date());
            vertxContext.runOnContext(v -> {
                try {
                    Criterion criterion = new Criterion();
                    Criteria criteria = new Criteria();
                    if (!StringUtils.isBlank(entity.getClientKeyApplyData().getClientId())) {
                        criteria.addField("'clientId'").setOperation("=").setValue("'"+entity.getClientKeyApplyData().getClientId()+"'");
                        criterion.addCriterion(criteria);
                    }
                    Criterion criterion2 = new Criterion();
                    Criteria clientNameCriteria = new Criteria();
                    Criteria tenantCriteria = new Criteria();
                    if (!StringUtils.isBlank(entity.getClientKeyApplyData().getAppName())) {
                        clientNameCriteria.addField("'appName'").setOperation("=").setValue("'"+entity.getClientKeyApplyData().getAppName()+"'");
                        tenantCriteria.addField("'tenantID'").setOperation("=").setValue("'"+entity.getClientKeyApplyData().getTenantID()+"'");
                        criterion2.addCriterion(clientNameCriteria);
                        criterion2.addCriterion(tenantCriteria);
                    }
                    Future<Boolean> checkClientIdFuture = Future.future();
                    Future<Boolean> checkClientNameFuture = Future.future();
                    Future<Object> startFuture = Future.future();
                    PreAuthModOpenapiUtil.checkModOpenapiAuth(webClient, hostPort, tenant_id, tenantId, token, startFuture);
                    CompositeFuture.join(checkClientIdFuture,checkClientNameFuture,startFuture).setHandler(exist -> {
                        if (exist.succeeded()) {
                            if (checkClientIdFuture.result()) {
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest("ClientID已经存在")));
                                return;
                            }
                            if (checkClientNameFuture.result()) {
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest("ClientName已经存在")));
                                return;
                            }
                            try {
                                clientKeyApplyData.setStatus("0");
                                clientKeyApplyData.setApplyDate(date);
                                clientKeyApplyData.setAdditionalProperty("updateDate",date);
                                postgresClient.save(TABLE_NAME_CLIENTKEY,clientKeyApplyData,true,reply->{
                                    CreateResp createResp = new CreateResp();
                                    if (reply.succeeded()){
                                        logger.info("postClientkey: ClientKey Apply Success");
                                        try {
                                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","运营中心管理员代租客向运营中心提交ClientKey使用申请").put("content",JsonObject.mapFrom(entity.getClientKeyApplyData())).put("create_time",date)).setHandler(ar1->{
                                                if (ar1.succeeded()&&ar1.result()){
                                                    logger.info("postClientkey: 运营中心管理员代租客向运营中心提交ClientKey使用申请成功");
                                                    createResp.setSuccess(true);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withJsonOK(createResp)));
                                                } else {
                                                    logger.error("postClientkey: 运营中心管理员代租客向运营中心提交ClientKey使用申请失败,Caused: "+reply.cause().getMessage());
                                                    createResp.setSuccess(false);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withJsonOK(createResp)));
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            String message = e.getLocalizedMessage();
                                            logger.error(message, e);
                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainInternalServerError(message)));
                                        }
                                    } else {
                                        logger.error("postClientkey: ClientKey Apply Fail",reply.cause().getMessage());
                                        //createResp.setSuccess(false);
                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest("postClientkey: ClientKey Apply Fail,Caused: "+reply.cause().getMessage())));
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = e.getLocalizedMessage();
                                logger.error(message, e);
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainInternalServerError(message)));
                            }
                        } else {
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest(exist.cause().getMessage())));
                        }
                    });
                    checkExist(criterion,postgresClient,checkClientIdFuture);
                    checkExist(criterion2,postgresClient,checkClientNameFuture);
                    /*checkExist(criterion,postgresClient,checkClientNameFuture).setHandler(exist->{
                        if (exist.succeeded()){
                            if (!exist.result()) {
                                clientKeyApplyData.setApplyDate(date);
                                try {
                                    postgresClient.save(TABLE_NAME_CLIENTKEY,clientKeyApplyData,true,reply->{
                                        CreateResp createResp = new CreateResp();
                                        if (reply.succeeded()){
                                            logger.info("postClientkey: ClientKey Apply Success");
                                            try {
                                                RecordLogs.recordLogs(vertxContext,tenantId,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","运营中心管理员代租客向运营中心提交ClientKey使用申请").put("content",JsonObject.mapFrom(entity.getClientKeyApplyData())).put("create_time",date)).setHandler(ar1->{
                                                    if (ar1.succeeded()&&ar1.result()){
                                                        logger.info("postClientkey: 运营中心管理员代租客向运营中心提交ClientKey使用申请成功");
                                                        createResp.setSuccess(true);
                                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withJsonOK(createResp)));
                                                    } else {
                                                        logger.error("postClientkey: 运营中心管理员代租客向运营中心提交ClientKey使用申请失败,Caused: "+reply.cause().getMessage());
                                                        createResp.setSuccess(false);
                                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withJsonOK(createResp)));
                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                String message = e.getLocalizedMessage();
                                                logger.error(message, e);
                                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainInternalServerError(message)));
                                            }
                                        } else {
                                            logger.error("postClientkey: ClientKey Apply Fail",reply.cause().getMessage());
                                            //createResp.setSuccess(false);
                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest("postClientkey: ClientKey Apply Fail,Caused: "+reply.cause().getMessage())));
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainInternalServerError(message)));
                                }
                            } else {
                                logger.error("clientId已经存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest("clientId已经存在")));
                            }
                        } else {
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainBadRequest(exist.cause().getMessage())));
                        }
                    });*/
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void getClientkeyClientSecret(String query, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String hostPort = routingContext.request().getHeader("Host");
        String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
        String token = routingContext.request().getHeader("X-Okapi-Token");
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            //PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                String typeURI = "/clientkey/show/"+query;
                String okapiUrl = "http://"+hostPort+typeURI;
                Future<HttpResponse<Buffer>> clientInfoFuture = Future.future();
                webClient.getAbs(okapiUrl)
                        .putHeader("X-Okapi-Tenant",tenant)
                        .putHeader("X-Okapi-Token",token)
                        .putHeader("Accept","application/json;charset=utf-8")
                        .send(clientInfoFuture);
                clientInfoFuture.setHandler(clientIdInfoRe -> {
                    if (clientIdInfoRe.succeeded()) {
                        JsonObject clientIdInfo = clientIdInfoRe.result().bodyAsJsonObject();
                        Map<String,String> originClientSecretMap = new HashMap<String,String>();
                        originClientSecretMap.put("ip",String.valueOf(clientIdInfo.getValue("appUrl")));
                        originClientSecretMap.put("client_id",String.valueOf(clientIdInfo.getValue("clientId")));
                        //originClientSecretMap.put("client_name",String.valueOf(clientIdInfo.getValue("appName")));
                        originClientSecretMap.put("tenant_id",tenantId);
                        //originClientSecretMap.put("update_date",String.valueOf(clientIdInfo.getValue("updateDate")));
                        StringBuffer clientSecret = new StringBuffer();
                        originClientSecretMap.entrySet().stream().sorted(Map.Entry.<String,String>comparingByKey()).forEachOrdered(e -> {
                            clientSecret.append(e.getKey()).append(e.getValue());
                        });
                        try {
                            ClientSecret clientSecretRes = new ClientSecret();
                            clientSecretRes.setClientSecret(MD5Util.md5(clientSecret.insert(0,clientIdInfo.getString("id")).append(clientIdInfo.getString("id")).toString()));
                            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretResponse.withJsonOK(clientSecretRes)));
                        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                            logger.error(e.getLocalizedMessage(),e);
                            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretResponse.withPlainInternalServerError(e.getLocalizedMessage())));
                        }
                    } else {
                        logger.error(clientIdInfoRe.cause().getLocalizedMessage(), clientIdInfoRe.cause());
                        asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretResponse.withPlainInternalServerError(clientIdInfoRe.cause().getLocalizedMessage())));
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage(),e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretResponse.withPlainInternalServerError(e.getLocalizedMessage())));
        }
    }

    //@Override
    /*public void getClientkeyClientSecretById(String id, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String hostPort = routingContext.request().getHeader("Host");
        String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
        String token = routingContext.request().getHeader("X-Okapi-Token");
        try {
            String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
            //PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
            vertxContext.runOnContext(v -> {
                String typeURI = "/clientkey/client_secret/"+id;
                String okapiUrl = "http://"+hostPort+typeURI;
                Future<HttpResponse<Buffer>> clientInfoFuture = Future.future();
                webClient.getAbs(okapiUrl)
                        .putHeader("X-Okapi-Tenant",tenant)
                        .putHeader("X-Okapi-Token",token)
                        //.putHeader("Accept","application/json;charset=utf-8")
                        .send(clientInfoFuture);
                clientInfoFuture.setHandler(clientIdInfoRe -> {
                    if (clientIdInfoRe.succeeded()) {
                        JsonObject clientIdInfo = clientIdInfoRe.result().bodyAsJsonObject();
                        Map<String,String> originClientSecretMap = new HashMap<String,String>();
                        originClientSecretMap.put("id",String.valueOf(clientIdInfo.getValue("id")));
                        originClientSecretMap.put("client_id",String.valueOf(clientIdInfo.getValue("clientId")));
                        originClientSecretMap.put("client_name",String.valueOf(clientIdInfo.getValue("appName")));
                        originClientSecretMap.put("tenant_id",tenantId);
                        originClientSecretMap.put("update_date",String.valueOf(clientIdInfo.getValue("updateDate")));
                        StringBuffer clientSecret = new StringBuffer();
                        originClientSecretMap.entrySet().stream().sorted(Map.Entry.<String,String>comparingByKey()).forEachOrdered(e -> {
                            clientSecret.append(e.getKey()).append(e.getValue());
                        });

                        ClientSecret clientSecretRes = new ClientSecret();
                        clientSecretRes.setClientSecret(clientSecret.toString());
                        asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretByIdResponse.withJsonOK(clientSecretRes)));
                    } else {
                        logger.error(clientIdInfoRe.cause().getLocalizedMessage(), clientIdInfoRe.cause());
                        asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretByIdResponse.withPlainInternalServerError(clientIdInfoRe.cause().getLocalizedMessage())));
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage(),e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientSecretByIdResponse.withPlainInternalServerError(e.getLocalizedMessage())));
        }
    }*/

    @Override
    public void getClientkeyClientId(Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            vertxContext.runOnContext(v -> {
                ClientId clientId = new ClientId();
                clientId.setClientId(UUID.randomUUID().toString());
                asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdResponse.withJsonOK(clientId)));
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage(),e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdResponse.withPlainInternalServerError(e.getLocalizedMessage())));
        }
    }

    @Override
    public void putClientkeyClientId(ApproveClientKeyData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantID = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantID);
        try {
            vertxContext.runOnContext(v -> {
                Future<Object> startModifyFuture = Future.future();
                startModifyFuture.complete();
                startModifyFuture.compose(queryClientIdInfo -> {
                    Future<JsonObject> future = Future.future();
                    sqlClient.getConnection(conn -> {
                        if (conn.succeeded()) {
                            conn.result().queryWithParams("SELECT * FORM " + TABLE_NAME_CLIENTKEY + " WHERE id = ?", new JsonArray().add(entity.getApproveClientKeyInfo().getId()), reply -> {
                                if (reply.succeeded()) {
                                    logger.info("Query ClientId info success");
                                    if (reply.result().getRows().isEmpty()) {
                                        reply.result().getRows().forEach((clientIdInfo) -> {
                                            clientIdInfo = new JsonObject(clientIdInfo.toString());
                                        });
                                        future.complete(reply.result().getRows().get(0));
                                    } else {
                                        logger.error("This record belongs to id of: " + entity.getApproveClientKeyInfo().getId() + " doesn't exist yet");
                                        future.fail("This record belongs to id of: " + entity.getApproveClientKeyInfo().getId() + " doesn't exist yet");
                                    }
                                } else {
                                    logger.error("Exception to query ClientId info, Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                                    future.fail("Exception to query ClientId info, Caused: " + reply.cause().getLocalizedMessage());
                                }
                            });
                        } else {
                            logger.error("Exception to get connection from PostgreSQL connection pool, Caused: " + conn.cause().getLocalizedMessage(), conn.cause());
                            future.fail("Exception to get connection from PostgreSQL connection pool, Caused: " + conn.cause().getLocalizedMessage());
                        }
                    });
                    return future;
                }).compose(updateClientIdInfo -> {
                    Future<Object> future = Future.future();
                    updateClientIdInfo.put("appUrl", entity.getApproveClientKeyInfo().getAppUrl());
                    updateClientIdInfo.put("appUrl", entity.getApproveClientKeyInfo().getAppUrl());
                    updateClientIdInfo.put("appUrl", entity.getApproveClientKeyInfo().getAppUrl());
                    updateClientIdInfo.put("appUrl", entity.getApproveClientKeyInfo().getAppUrl());
                    return future;
                }).setHandler(re -> {
                    if (re.succeeded()) {

                    } else {

                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PutClientkeyClientIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyCheckExistClientName(String query, ClientName entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantID = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
        String token = routingContext.request().getHeader("X-Okapi-Token");
        String hostPort = routingContext.request().getHeader("Host");
        String typeURI = "/sysman/codes";
        String tenant_id = entity.getTenantId();
        String okapiUrl = "http://" + hostPort + "/_/proxy/tenants/" + tenant_id + "/modules";

        Future<Object> startFuture = Future.future();
        PreAuthModOpenapiUtil.checkModOpenapiAuth(webClient, hostPort, tenant_id, tenantID, token, startFuture);
        /*setHandler(re -> {
            if (re.succeeded()) {
                logger.info("");

            } else {
                logger.error("Exception to check appName of: " + entity.getClientName() + " for specific tenantId of: " + entity.getTenantId() + " , Caused: " + re.cause().getLocalizedMessage());
                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withPlainInternalServerError("Exception to check appName of: " + entity.getClientName() + " for specific tenantId of: " + entity.getTenantId() + " , Caused: " + re.cause().getLocalizedMessage())));
                return;
            }
        });*/
        PostgresClient.getInstance(vertxContext.owner(), entity.getTenantId()).setIdField(ID_FIELD);
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(),entity.getTenantId());
        try {
            vertxContext.runOnContext(v -> {
                startFuture.setHandler(ar -> {
                    if (ar.succeeded()) {
                        logger.info("Start to check appName");
                        try {
                            Criterion criterion = new Criterion();
                            Criteria criteria = new Criteria();
                            Criteria tenantCriteria = new Criteria();
                            if (!StringUtils.isBlank(entity.getClientName())) {
                                criteria.setJSONB(true).addField("'appName'").setOperation("=").setValue("'"+entity.getClientName()+"'");
                                criterion.addCriterion(criteria);
                            }
                            if (!StringUtils.isBlank(entity.getTenantId())) {
                                tenantCriteria.addField("'tenantID'").setOperation("=").setValue("'"+entity.getTenantId()+"'");
                                criterion.addCriterion(tenantCriteria);
                            }
                            if (!StringUtil.isNullOrEmpty(query)) {
                                Criteria idCriteria = new Criteria();
                                idCriteria.setJSONB(false).addField("id").setOperation("!=").setValue("'"+query+"'");
                                criterion.addCriterion(idCriteria);
                            }
                            Future<Boolean> future = Future.future();
                            checkExist(criterion,postgresClient,future).setHandler(exist->{
                                if (exist.succeeded()){
                                    logger.info("getClientkeyCheckExistByClientName: checkExist Success");
                                    CreateResp createResp = new CreateResp();
                                    if (exist.result()){
                                        logger.error("clientName已经存在");
                                        createResp.setSuccess(true);
                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withJsonOK(createResp)));
                                    } else {
                                        logger.info("clientName可以使用");
                                        createResp.setSuccess(false);
                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withJsonOK(createResp)));
                                    }
                                } else {
                                    logger.error("getClientkeyCheckExistByClientName: checkExist Error, Caused: "+exist.cause().getMessage(),exist.cause());
                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withPlainBadRequest("校验clientName唯一性出错，Caused: "+exist.cause().getMessage())));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withPlainInternalServerError(message)));
                        }
                    } else {
                        logger.error("Exception to start check appName, Caused: " + ar.cause().getLocalizedMessage(), ar.cause());
                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withPlainInternalServerError("Exception to start check appName, Caused: " + ar.cause().getLocalizedMessage())));
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyCheckExistClientNameResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void getClientkeyCheckExistByClientId(String clientId, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
                try {
                    Criterion criterion = new Criterion();
                    Criteria criteria = new Criteria();
                    if (!StringUtils.isBlank(clientId)) {
                        criteria.addField("'clientId'").setOperation("=").setValue(clientId);
                        criterion.addCriterion(criteria);
                    }
                    Future<Boolean> future = Future.future();
                    checkExist(criterion,postgresClient,future).setHandler(exist->{
                        if (exist.succeeded()){
                            logger.info("getClientkeyCheckExistByClientId: checkExist Success");
                            if (exist.result()){
                                logger.error("clientId已经存在");
                                asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckExistByClientIdResponse.withPlainOK("clientId已经存在")));
                            } else {
                                logger.info("clientId可以使用");
                                asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckExistByClientIdResponse.withPlainOK("clientId可以使用")));
                            }
                        } else {
                            logger.error("getClientkeyCheckExistByClientId: checkExist Error, Caused: "+exist.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckExistByClientIdResponse.withPlainBadRequest("校验clientId唯一性出错，Caused: "+exist.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckExistByClientIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckExistByClientIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyPageQuery(ClientKeyApplyPageQueryCondition entity, RoutingContext routingContext, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        try {
            vertxContext.runOnContext(v -> {
                PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
                String tenantName = entity.getTenantName();
                String applyDatef = entity.getApplyDatef();
                String applyDatet = entity.getApplyDatet();
                Integer pageNum = entity.getPageNum();
                Integer pageRecordCount = entity.getPageRecordCount();
                String orderField = entity.getOrderField();
                String orderType = entity.getOrderType();
                Criterion criterion = new Criterion();
                Criteria criteria1 = new Criteria();
                Criteria criteria2 = new Criteria();
                Criteria criteria3 = new Criteria();
                if (!StringUtils.isBlank(tenantName)) {
                    criteria1.addField("'tenantName'").setOperation("like").setValue("%"+tenantName+"%");
                    criterion.addCriterion(criteria1);
                }
                if (!StringUtils.isBlank(applyDatef)) {
                    criteria2.addField("'applyDate'").setOperation(">=").setValue(applyDatef);
                    criterion.addCriterion(criteria2);
                }
                if (!StringUtils.isBlank(applyDatet)) {
                    criteria3.addField("'applyDate'").setOperation("<=").setValue(applyDatet);
                    criterion.addCriterion(criteria3);
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

                String tenant = routingContext.request().getHeader("X-Okapi-Tenant");
                String token = routingContext.request().getHeader("X-Okapi-Token");
                String hostPort = routingContext.request().getHeader("Host");
                String typeURI = "/sysman/codes";
                String okapiUrl = null;
                try {
                    okapiUrl = "http://"+hostPort+typeURI+"?query="+URLEncoder.encode("[[\"tabid\",17,\"=\",\"AND\"]]","utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    logger.error(e.getLocalizedMessage(), e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyPageQueryResponse.withPlainInternalServerError(e.getLocalizedMessage())));
                }
                //?query=`+encodeURI(JSON.stringify([["tabid",`${tabid}`,"=","AND"]]));
                Future<JsonArray> clientIdStatusFuture = Future.future();
                Future<ClientKeyApplyPageQueryResp> queryFuture = Future.future();
                webClient.getAbs(okapiUrl)
                        .putHeader("X-Okapi-Tenant",tenant)
                        .putHeader("X-Okapi-Token",token)
                        .send(type -> {
                            if (type.succeeded()) {
                                JsonObject types = type.result().bodyAsJsonObject();
                                JsonArray dataType = types.getJsonArray("data");
                                logger.info("API status data: " + dataType);
                                clientIdStatusFuture.complete(dataType);
                            } else {
                                clientIdStatusFuture.fail(type.cause());
                            }
                        });
                Future<JsonArray> originFuture = Future.future();

                PreAuthModOpenapiUtil.getTenantAuthorizedModOpenapi(webClient,hostPort,tenantId,token,originFuture);
                originFuture.compose(validTenants -> {
                    Future<String> future = Future.future();
                    future.complete(originFuture.result().stream().map(validTenantId -> " SELECT * FROM " + validTenantId.toString() + "_mod_openapi" + "." + TABLE_NAME_CLIENTKEY).collect(Collectors.joining(" UNION ALL ")));
                    return future;
                }).compose(pageQuery -> {
                    Future<ClientKeyApplyPageQueryResp> innerFuture = Future.future();
                    ClientKeyApplyPageQueryResp clientKeyApplyPageQueryResp = new ClientKeyApplyPageQueryResp();
                    List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArrays = new ArrayList<ClientKeyApplyPageQueryArray>();
                    clientKeyApplyPageQueryResp.setClientKeyApplyPageQueryArray(clientKeyApplyPageQueryArrays);
                    postgresClient.select(" SELECT * FROM ( " + pageQuery + " ) AS A " + criterion.toString(),reply-> {
                        if (reply.succeeded()) {
                            //List<JsonObject> clientIds = reply.result().getRows();
                            //"id", "tenantID", "tenantName", "appName", "applyDate", "status"
                            Class<? extends ClientKeyApplyPageQueryArray> aClass = ClientKeyApplyPageQueryArray.class;
                            Field[] declaredFields = aClass.getDeclaredFields();
                            Field.setAccessible(declaredFields, true);

                            /*reply.result().getRows().forEach((clientId) -> {
                                clientId.put("jsonb", new JsonObject(clientId.getString("jsonb")));
                            });*/
                            List<JsonObject> rows = reply.result().getRows();
                            for (JsonObject clientId:rows) {
                                ClientKeyApplyPageQueryArray clientKeyApplyPageQueryArray = new ClientKeyApplyPageQueryArray();
                                clientKeyApplyPageQueryArrays.add(clientKeyApplyPageQueryArray);
                                clientKeyApplyPageQueryArray.setId(clientId.getString("id"));
                            }
                            /*reply.result().getRows().forEach((clientId) -> {
                                ClientKeyApplyPageQueryArray clientKeyApplyPageQueryArray = new ClientKeyApplyPageQueryArray();
                                clientKeyApplyPageQueryArrays.add(clientKeyApplyPageQueryArray);
                                clientKeyApplyPageQueryArray.setId(clientId.getString("id"));
                                *//*Arrays.asList(aClass.getDeclaredFields()).forEach((fieldKV) -> {
                                    try {
                                        fieldKV.set(clientKeyApplyPageQueryArray, clientId.getJsonObject("jsonb").getValue(fieldKV.getName()));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                });*//*
//                                        clientId.getJsonObject("jsonb").forEach((item) -> {
//                                            item.getKey()
//                                        });
                            });*/
                            //List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArrays = (List<ClientKeyApplyPageQueryArray>) reply.result().getResults();
                            clientKeyApplyPageQueryResp.setPageNum(pageNum2);
                            Integer totalRecordCount = (Integer) reply.result().getNumRows();
                            clientKeyApplyPageQueryResp.setTotalRecords(totalRecordCount);
                            clientKeyApplyPageQueryResp.setPageCount(totalRecordCount % pageRecordCount2 == 0 ? totalRecordCount / pageRecordCount2 : totalRecordCount / pageRecordCount2 + 1);
                            logger.info("postClientkeyPageQuery: 分页条件查询成功");
                            innerFuture.complete(clientKeyApplyPageQueryResp);
                        } else {
                            logger.error("postClientkeyPageQuery: 分页条件查询失败,Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                            innerFuture.fail("postClientkeyPageQuery: 分页条件查询失败,Caused: " + reply.cause().getLocalizedMessage());
                        }
                    });
                    return innerFuture;
                }).setHandler(re -> {
                    if (re.succeeded()) {
                        logger.info("Success to split dataSourceSql: " + re.result().toString());
                        queryFuture.complete(re.result());
                        /*try {

                        } catch (Exception e) {
                            e.printStackTrace();
                            String message = e.getLocalizedMessage();
                            logger.error(message, e);
                            queryFuture.fail("postClientkeyPageQuery: 分页条件查询失败,Caused: "+e.getLocalizedMessage());
                        }*/
                    } else {
                        logger.error("Exception to split dataSourceSql, Caused: " + re.cause().getLocalizedMessage(), re.cause());
                        queryFuture.fail("Exception to split dataSourceSql, Caused: " + re.cause().getLocalizedMessage());
                    }
                });
                CompositeFuture.join(clientIdStatusFuture,queryFuture)/*.compose(pageQuery -> {
                    Future<ClientKeyApplyPageQueryResp> future = Future.future();
                        postgresClient.select(" SELECT * FROM ( " + queryFuture.result().toString() + " ) AS A " + criterion.toString(),reply-> {
                            System.out.println("=================================");
                            if (reply.succeeded()) {
                                List<JsonObject> clientIds = reply.result().getRows();
                                //"id", "tenantID", "tenantName", "appName", "applyDate", "status"
                            *//*Class<? extends ClientKeyApplyPageQueryArray> aClass = ClientKeyApplyPageQueryArray.class;
                            Field[] declaredFields = aClass.getDeclaredFields();
                            Field.setAccessible(declaredFields, true);*//*
                                ClientKeyApplyPageQueryResp clientKeyApplyPageQueryResp = new ClientKeyApplyPageQueryResp();
                                List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArrays = new ArrayList<ClientKeyApplyPageQueryArray>();
                                clientKeyApplyPageQueryResp.setClientKeyApplyPageQueryArray(clientKeyApplyPageQueryArrays);
                            *//*clientIds.forEach((clientId) -> {
                                clientId.put("jsonb", new JsonObject(clientId.getString("jsonb")));
                            });*//*
                                clientIds.forEach((clientId) -> {
                                    ClientKeyApplyPageQueryArray clientKeyApplyPageQueryArray = new ClientKeyApplyPageQueryArray();
                                    clientKeyApplyPageQueryArrays.add(clientKeyApplyPageQueryArray);
                                    clientKeyApplyPageQueryArray.setId(clientId.getString("id"));
                                *//*Arrays.asList(aClass.getDeclaredFields()).forEach((fieldKV) -> {
                                    try {
                                        fieldKV.set(clientKeyApplyPageQueryArray, clientId.getJsonObject("jsonb").getValue(fieldKV.getName()));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                });*//*
//                                        clientId.getJsonObject("jsonb").forEach((item) -> {
//                                            item.getKey()
//                                        });
                                });
                                //List<ClientKeyApplyPageQueryArray> clientKeyApplyPageQueryArrays = (List<ClientKeyApplyPageQueryArray>) reply.result().getResults();
                                clientKeyApplyPageQueryResp.setPageNum(pageNum2);
                                Integer totalRecordCount = (Integer) reply.result().getNumRows();
                                clientKeyApplyPageQueryResp.setTotalRecords(totalRecordCount);
                                clientKeyApplyPageQueryResp.setPageCount(totalRecordCount % pageRecordCount2 == 0 ? totalRecordCount / pageRecordCount2 : totalRecordCount / pageRecordCount2 + 1);
                                logger.info("postClientkeyPageQuery: 分页条件查询成功");
                                future.complete(clientKeyApplyPageQueryResp);
                            } else {
                                logger.error("postClientkeyPageQuery: 分页条件查询失败,Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                                future.fail("postClientkeyPageQuery: 分页条件查询失败,Caused: " + reply.cause().getLocalizedMessage());
                            }
                        });
                    return future;
                })*/.setHandler(re -> {
                   if (re.succeeded()) {
                        logger.info("CompositeFuture Success");
                       //ClientKeyApplyPageQueryResp o = (ClientKeyApplyPageQueryResp)re.result().list().get(1);
                       queryFuture.result().getClientKeyApplyPageQueryArray().forEach(clientIdInfo -> {
                            for (Object status : clientIdStatusFuture.result()) {
                                if (new JsonObject(status.toString()).getInteger("tabid") == 17) {
                                    if (new JsonObject(status.toString()).getJsonObject("cvalue").getValue("id").toString().contentEquals(clientIdInfo.getStatus())) {
                                        clientIdInfo.setStatus(new JsonObject(status.toString()).getJsonObject("cvalue").getString("name"));
                                        continue;
                                    }
                                }
                            }
                        });
                       asyncResultHandler.handle(Future.succeededFuture(PostClientkeyPageQueryResponse.withJsonOK(queryFuture.result())));
                       return;
                   } else {
                       logger.error(re.cause().getLocalizedMessage(), re.cause());
                       asyncResultHandler.handle(Future.succeededFuture(PostClientkeyPageQueryResponse.withPlainInternalServerError(re.cause().getLocalizedMessage())));
                       return;
                   }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyPageQueryResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void getClientkeyShowById(String id, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
                try {
                    Criterion criterion = new Criterion();
                    if (!StringUtils.isBlank(id)) {
                        Criteria criteria = new Criteria();
                        criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                        criterion.addCriterion(criteria);
                    }
                    postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyApplyData_.class,criterion,true,true,reply->{
                        if (reply.succeeded()) {
                            if (reply.result() != null && ((List<ClientKeyApplyData_>) reply.result().getResults()).size() != 0){
                                logger.info("getClientkeyShowById: find ClientKey Info Success");
                                List<ClientKeyApplyData_> clientKeyApplyDataArray = (List<ClientKeyApplyData_>) reply.result().getResults();
                                ClientKeyApplyData_ clientKeyApplyData_ = clientKeyApplyDataArray.get(0);
                                asyncResultHandler.handle(Future.succeededFuture(GetClientkeyShowByIdResponse.withJsonOK(clientKeyApplyData_)));
                            } else {
                                logger.error("getClientkeyShowById: 查询信息为空");
                                asyncResultHandler.handle(Future.succeededFuture(GetClientkeyShowByIdResponse.withPlainNotFound("查询信息为空")));
                            }
                        } else {
                            logger.error("getClientkeyShowById: find ClientKey Info Fail, Caused: "+reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyShowByIdResponse.withPlainBadRequest("getClientkeyShowById: find ClientKey Info Fail, Caused: "+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyShowByIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyShowByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyApprove(ApproveClientKeyData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                //ClientKeyInfo clientKeyInfo = entity.getClientKeyInfo();
                ApproveClientKeyInfo approveClientKeyInfo = entity.getApproveClientKeyInfo();
                String id = approveClientKeyInfo.getId();
                try {
                    Criterion criterion = new Criterion();
                    if (!StringUtils.isBlank(id)) {
                        Criteria criteria = new Criteria();
                        criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                        criterion.addCriterion(criteria);
                    }
                    Future<Object> startFuture = Future.future();
                    startFuture.complete();
                    startFuture.compose(query -> {
                        Future<JsonObject> future = Future.future();
                        try {
                            postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,true,true,reply0->{
                                if (reply0.succeeded()) {
                                    if (reply0.result() != null && ((List<ClientKeyInfo>) reply0.result().getResults()).size() != 0) {
                                        ClientKeyInfo clientKeyInfo = ((List<ClientKeyInfo>) reply0.result().getResults()).get(0);
                                        JsonObject clientKeyApply = JsonObject.mapFrom(clientKeyInfo);
                                        if ("0".contentEquals(clientKeyInfo.getStatus())) {
                                            /*clientKeyInfo.setClientSecret(approveClientKeyInfo.getClientSecret());
                                            //clientKeyInfo.setStartDate(date);
                                            clientKeyInfo.setApproveDate(date);*/
                                            clientKeyApply.put("approveDate", date);
                                            clientKeyApply.put("clientSecret", approveClientKeyInfo.getClientSecret());
                                            clientKeyApply.put("approveMan", approveClientKeyInfo.getApproveMan());
                                            clientKeyApply.put("authModel", approveClientKeyInfo.getAuthModel());
                                            clientKeyApply.put("expireDate", approveClientKeyInfo.getExpireDate());
                                            clientKeyApply.put("updateDate", date);
                                            //clientKeyApply.put("clientSecret",approveClientKeyInfo.getClientSecret());
                                            clientKeyApply.put("status", "1");
                                            future.complete(clientKeyApply);
                                        } else {
                                            logger.error("该次ClientID申请信息已经被批准启用");
                                            sqlClient.getConnection(conn -> {
                                                conn.result().close();
                                                if (conn.succeeded()) {
                                                    logger.info(" Success to get connection");
                                                    conn.result().queryWithParams("SELECT * FROM " + TABLE_NAME_CLIENTKEY + " WHERE jsonb ->> 'tenantID' = ? AND jsonb ->> 'appName' = ? AND id != ?", new JsonArray().add(clientKeyApply.getString("tenantID")).add(approveClientKeyInfo.getAppName()).add(clientKeyApply.getString("id")), reply -> {
                                                        if (reply.succeeded()) {
                                                            if (reply.result().getRows().isEmpty()) {
                                                                //asyncResultHandler.handle(Future.succeededFuture(PostClientkeyApproveResponse.withPlainBadRequest("该次ClientID申请信息已经被批准启用")));
                                                                if (!approveClientKeyInfo.getClientSecret().contentEquals(clientKeyApply.getString("clientSecret"))) {
                                                                    clientKeyApply.put("approveDate", date);
                                                                    clientKeyApply.put("clientSecret", approveClientKeyInfo.getClientSecret());
                                                                }
                                                                clientKeyApply.put("appUrl", approveClientKeyInfo.getAppUrl());
                                                                clientKeyApply.put("appName", approveClientKeyInfo.getAppName());
                                                                clientKeyApply.put("approveMan", approveClientKeyInfo.getApproveMan());
                                                                clientKeyApply.put("authModel", approveClientKeyInfo.getAuthModel());
                                                                clientKeyApply.put("expireDate", approveClientKeyInfo.getExpireDate());
                                                                clientKeyApply.put("updateDate", date);
                                                                future.complete(clientKeyApply);
                                                            } else {
                                                                future.fail("AppName of: " + clientKeyApply.getString("appName") + " that belogs to tenantId of: " + clientKeyApply.getString("tenantID") + " has existed yet");
                                                            }
                                                        } else {
                                                            logger.error("Exception to query if appName of: " + clientKeyApply.getString("appName") + " that belogs to tenantId of: " + clientKeyApply.getString("tenantID"));
                                                            future.fail("Exception to query if appName of: " + clientKeyApply.getString("appName") + " that belogs to tenantId of: " + clientKeyApply.getString("tenantID"));
                                                        }
                                                    });
                                                } else {
                                                    logger.error(" Exception to get connection from PostgreSQL connection pool, Caused: " + conn.cause().getLocalizedMessage());
                                                    future.fail(" Exception to get connection from PostgreSQL connection pool, Caused: " + conn.cause().getLocalizedMessage());
                                                }
                                            });
                                        }
                                    } else {
                                        logger.error("This record belogs to id of: " + entity.getApproveClientKeyInfo().getId() + " doesn't exist yet ");
                                        future.fail("This record belogs to id of: " + entity.getApproveClientKeyInfo().getId() + " doesn't exist yet ");
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            future.fail(e.getLocalizedMessage());
                        }
                        return future;
                    }).compose(update -> {
                        Future<JsonObject> future = Future.future();
                        try {
                            postgresClient.update(TABLE_NAME_CLIENTKEY,update,criterion,true,reply-> {
                                if (reply.succeeded()) {
                                    future.complete(update);
                                } else {
                                    logger.error("Exception to update ClientId info, Caused: " + reply.cause().getLocalizedMessage());
                                    future.fail("Exception to update ClientId info, Caused: " + reply.cause().getLocalizedMessage());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            future.fail(e.getLocalizedMessage());
                        }
                        return future;
                    }).compose(recordLog -> {
                        Future<Object> future = Future.future();
                        try {
                            RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog()).toString()).put("operation","批准clientSecret成功").put("content",recordLog).put("create_time",date)).setHandler(ar1->{
                                if (ar1.succeeded()&&ar1.result()){
                                    logger.info("postClientkeyApprove: Distribute ClientKey and ClientSecret Success");
                                    future.complete();
                                } else {
                                    logger.error("postClientkeyApprove: Distribute ClientKey and ClientSecret Fail,Caused: "+ar1.cause().getMessage());
                                    future.fail("postClientkeyApprove: Distribute ClientKey and ClientSecret Fail,Caused: "+ar1.cause().getMessage());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            future.fail(e.getLocalizedMessage());
                        }
                        return future;
                    }).setHandler(re -> {
                       if (re.succeeded()) {
                           logger.info("Success to modify ClientId info");
                           CreateResp createResp = new CreateResp();
                           createResp.setSuccess(true);
                           asyncResultHandler.handle(Future.succeededFuture(PostClientkeyApproveResponse.withJsonOK(createResp)));
                       } else {
                           logger.error("Exception to modify ClientId info, Caused: " + re.cause().getLocalizedMessage(), re.cause());
                           asyncResultHandler.handle(Future.succeededFuture(PostClientkeyApproveResponse.withPlainInternalServerError("Exception to modify ClientId info, Caused: " + re.cause().getLocalizedMessage())));
                       }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyApproveResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyApproveResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyRefuseById(String id, RefuseData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                if (!StringUtils.isBlank(id)) {
                    Criteria criteria = new Criteria();
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterion.addCriterion(criteria);
                }
                try {
                    postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,false,false,reply->{
                        if (reply.succeeded() ) {
                            if (reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0){
                                logger.info("postClientkeyRefuseById: find ClientApply Success");
                                List<ClientKeyInfo> clientKeyInfo_Array = (List<ClientKeyInfo>) reply.result().getResults();
                                ClientKeyInfo clientKeyInfo = clientKeyInfo_Array.get(0);
                                if (!"0".contentEquals(clientKeyInfo.getStatus())){
                                    logger.error("postClientkeyRefuseById: 只能拒绝‘申请已提交，待处理’的ClientKey申请信息");
                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainBadRequest("只能拒绝‘申请已提交，待处理’的ClientKey申请信息")));
                                } else {
                                    try {
                                        clientKeyInfo.setStatus("2");
                                        clientKeyInfo.setRefuseDate(date);
                                        clientKeyInfo.setRefuseReason(entity.getReason());
                                        clientKeyInfo.setAdditionalProperty("refuseMan",entity.getRefuseMan());
                                        clientKeyInfo.setAdditionalProperty("updateDate",date);
                                        postgresClient.update(TABLE_NAME_CLIENTKEY,clientKeyInfo,criterion,true,reply0->{
                                            if (reply0.succeeded()&&reply0.result().getUpdated()!=0){
                                                logger.info("postClientkeyRefuseById : update tb_clientkey success");
                                                try {
                                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","拒绝租客该次申请的ClientKey").put("content",JsonObject.mapFrom(clientKeyInfo).put("create_time",date))).setHandler(ar0->{
                                                        if (ar0.succeeded()&&ar0.result()){
                                                            CreateResp createResp = new CreateResp();
                                                            createResp.setSuccess(true);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withJsonOK(createResp)));
                                                        } else {
                                                            CreateResp createResp = new CreateResp();
                                                            createResp.setSuccess(false);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withJsonOK(createResp)));
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    String message = e.getLocalizedMessage();
                                                    logger.error(message, e);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainInternalServerError(message)));
                                                }
                                            } else {
                                                logger.error("postClientkeyRefuseById : update tb_clientkey Fail , caused: ",reply0.cause().getMessage());
                                                CreateResp createResp = new CreateResp();
                                                createResp.setSuccess(false);
                                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withJsonOK(createResp)));
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainInternalServerError(message)));
                                    }
                                }
                            } else {
                                logger.error("postClientkeyRefuseById: 该次申请ClientKey信息不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainNotFound("该次申请ClientKey信息不存在")));
                            }

                        } else {
                            logger.error("postClientkeyRefuseById: find TenantApply Error,Caused ",reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainBadRequest("postClientkeyRefuseByTenantApplyId: find TenantApply Error,Caused "+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainInternalServerError(message)));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyRefuseByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyDeleteById(String id,Log log, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                if (!StringUtils.isBlank(id)) {
                    Criteria criteria = new Criteria();
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterion.addCriterion(criteria);
                }
                try {
                    postgresClient.delete(TABLE_NAME_CLIENTKEY,criterion,reply->{
                        CreateResp createResp = new CreateResp();
                        if (reply.succeeded()){
                            if (reply.result().getUpdated()!=0){
                                logger.info("postClientkeyDeleteById : 删除单个Tenant该次申请ClientKey成功");
                                try {
                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(log)).put("operation","删除单个Tenant该次申请ClientKey成功").put("content",id).put("create_time",date)).setHandler(ar0->{
                                        if (ar0.succeeded()&&ar0.result()){
                                            createResp.setSuccess(true);
                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withJsonOK(createResp)));
                                        } else {
                                            createResp.setSuccess(false);
                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withJsonOK(createResp)));
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = e.getLocalizedMessage();
                                    logger.error(message, e);
                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withPlainInternalServerError(message)));
                                }
                            } else {
                                logger.error("postClientkeyDeleteById: 该次申请ClientKey信息不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withPlainNotFound("该次申请ClientKey信息不存在")));
                            }
                        } else {
                            logger.error("postClientkeyDeleteById : 删除Tenant该次申请ClientKey成功，原因：",reply.cause().getMessage());
                            //createResp.setSuccess(false);
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withPlainBadRequest("postClientkeyDeleteById : 删除Tenant该次申请ClientKey成功，原因："+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyDeleteByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyMonitorPageQuery(ClientKeyMonitorPageQueryCondition entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
                String tenantName = entity.getTenantName();
                String clientSecret = entity.getClientSecret();
                String appName = entity.getAppName();
                String authModel = entity.getAuthModel();
                Integer pageNum = entity.getPageNum();
                Integer pageRecordCount = entity.getPageRecordCount();
                Criterion criterion = new Criterion();
                Criteria criteria1 = new Criteria();
                Criteria criteria2 = new Criteria();
                Criteria criteria3 = new Criteria();
                Criteria criteria4 = new Criteria();
                Criteria criteria5 = new Criteria();
                if (!StringUtils.isBlank(tenantName)) {
                    criteria1.addField("'tenantName'").setOperation("like").setValue("%"+tenantName+"%");
                    criterion.addCriterion(criteria1);
                }
                if (!StringUtils.isBlank(clientSecret)) {
                    criteria2.addField("'clientSecret'").setOperation("like").setValue("%"+clientSecret+"%");
                    criterion.addCriterion(criteria2);
                }
                if (!StringUtils.isBlank(appName)) {
                    criteria3.addField("'appName'").setOperation("like").setValue("%"+appName+"%");
                    criterion.addCriterion(criteria3);
                }
                if (!StringUtils.isBlank(authModel)) {
                    criteria4.addField("'authModel'").setOperation("=").setValue(authModel);
                    criterion.addCriterion(criteria4);
                }
                criteria5.addField("'status'").setOperation("=").setValue("ClientKey已启用");
                criterion.addCriterion(criteria5);
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
                try {
                    postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyMonitorPageQueryArray.class,criterion,true,true,reply->{
                        if (reply.succeeded()) {
                            ClientKeyMonitorPageQueryResp clientKeyMonitorPageQueryResp = new ClientKeyMonitorPageQueryResp();
                            if (reply.result() != null && ((List<ClientKeyMonitorPageQueryArray>) reply.result().getResults()).size() != 0){
                                List<ClientKeyMonitorPageQueryArray> clientKeyMonitorPageQueryData = (List<ClientKeyMonitorPageQueryArray>) reply.result().getResults();
                                clientKeyMonitorPageQueryResp.setClientKeyMonitorPageQueryArray(clientKeyMonitorPageQueryData);
                                clientKeyMonitorPageQueryResp.setPageNum(pageNum2);
                                Integer totalRecordCount = (Integer) reply.result().getResultInfo().getTotalRecords();
                                clientKeyMonitorPageQueryResp.setTotalRecords(totalRecordCount);
                                clientKeyMonitorPageQueryResp.setPageCount(totalRecordCount % pageRecordCount2 == 0 ? totalRecordCount / pageRecordCount2 : totalRecordCount / pageRecordCount2 + 1);
                                logger.info("postClientkeyMonitorPageQuery: 分页条件查询成功");
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorPageQueryResponse.withJsonOK(clientKeyMonitorPageQueryResp)));
                            } else {
                                logger.error("postClientkeyMonitorPageQuery: 分页条件查询结果为空");
                                clientKeyMonitorPageQueryResp.setClientKeyMonitorPageQueryArray(new ArrayList<>());
                                clientKeyMonitorPageQueryResp.setPageCount(0);
                                clientKeyMonitorPageQueryResp.setPageNum(pageNum2);
                                clientKeyMonitorPageQueryResp.setTotalRecords(0);
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorPageQueryResponse.withJsonOK(clientKeyMonitorPageQueryResp)));
                            }
                        } else {
                            logger.error("postClientkeyMonitorPageQuery: 分页条件查询失败,Caused: "+reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorPageQueryResponse.withPlainBadRequest("postClientkeyMonitorPageQuery: 分页条件查询失败,Caused: "+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorPageQueryResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorPageQueryResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyMonitorStopById(String id, StopData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                if (!StringUtils.isBlank(id)) {
                    Criteria criteria = new Criteria();
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterion.addCriterion(criteria);
                }
                try {
                    postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,false,false,reply->{
                        if (reply.succeeded()) {
                            if (reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0){
                                logger.info("postClientkeyMonitorStopById: find ClientApply Success");
                                List<ClientKeyInfo> clientKeyInfoArray = (List<ClientKeyInfo>) reply.result().getResults();
                                ClientKeyInfo clientKeyInfo = clientKeyInfoArray.get(0);
                                if (!"ClientKey已启用".contentEquals(clientKeyInfo.getStatus())){
                                    logger.error("postClientkeyMonitorStopById: 只能停用‘ClientKey已启用’的ClientSecret");
                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainBadRequest("只能停用‘ClientKey已启用’的ClientSecret")));
                                } else {
                                    try {
                                        clientKeyInfo.setStatus("ClientKey已停用");
                                        clientKeyInfo.setRefuseDate(date);
                                        clientKeyInfo.setRefuseReason(entity.getReason());
                                        postgresClient.update(TABLE_NAME_CLIENTKEY,clientKeyInfo,criterion,true,reply0->{
                                            if (reply0.succeeded()&&reply0.result().getUpdated()!=0){
                                                logger.info("postClientkeyMonitorStopById : update tb_clientkey success");
                                                try {
                                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","停用租客该次申请的ClientKey").put("content",JsonObject.mapFrom(clientKeyInfo).put("create_time",date))).setHandler(ar0->{
                                                        if (ar0.succeeded()&&ar0.result()){
                                                            CreateResp createResp = new CreateResp();
                                                            createResp.setSuccess(true);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withJsonOK(createResp)));
                                                        } else {
                                                            CreateResp createResp = new CreateResp();
                                                            createResp.setSuccess(false);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withJsonOK(createResp)));
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    String message = e.getLocalizedMessage();
                                                    logger.error(message, e);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainInternalServerError(message)));
                                                }
                                            } else {
                                                logger.error("postClientkeyMonitorStopById : update tb_clientkey Fail, caused: ",reply0.cause().getMessage());
                                                CreateResp createResp = new CreateResp();
                                                createResp.setSuccess(false);
                                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withJsonOK(createResp)));
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainInternalServerError(message)));
                                    }
                                }
                            } else {
                                logger.error("postClientkeyMonitorStopById: ClientSecret信息不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainBadRequest("ClientSecret信息不存在")));
                            }
                        } else {
                            logger.error("postClientkeyMonitorStopById: find TenantApply Error,Caused ",reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainBadRequest("postClientkeyRefuseByTenantApplyId: find TenantApply Error,Caused "+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStopByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void postClientkeyMonitorStartById(String id, StartData entity, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        String date = simpleDateFormat.format(new Date());
        try {
            vertxContext.runOnContext(v -> {
                Criterion criterion = new Criterion();
                if (!StringUtils.isBlank(id)) {
                    Criteria criteria = new Criteria();
                    criteria.setJSONB(false).addField("id").setOperation("=").setValue("'"+id+"'");
                    criterion.addCriterion(criteria);
                }
                try {
                    postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,false,false,reply->{
                        if (reply.succeeded()) {
                            if (reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0){
                                logger.info("postClientkeyMonitorStartById: find ClientApply Success");
                                List<ClientKeyInfo> clientKeyInfoArray = (List<ClientKeyInfo>) reply.result().getResults();
                                ClientKeyInfo clientKeyInfo = clientKeyInfoArray.get(0);
                                if (!"ClientKey已停用".contentEquals(clientKeyInfo.getStatus())){
                                    logger.error("postClientkeyMonitorStopById: 只能启用‘ClientKey已停用’的ClientSecret");
                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainBadRequest("只能启用‘ClientKey已停用’的ClientSecret")));
                                } else {
                                    try {
                                        clientKeyInfo.setStatus("ClientKey已启用");
                                        clientKeyInfo.setStartDate(date);
                                        clientKeyInfo.setStartReason(entity.getReason());
                                        postgresClient.update(TABLE_NAME_CLIENTKEY,clientKeyInfo,criterion,true,reply0->{
                                            if (reply0.succeeded()&&reply0.result().getUpdated()!=0){
                                                logger.info("postClientkeyMonitorStartById : update tb_clientkey success");
                                                try {
                                                    RecordLogs.recordLogs(postgresClient,new JsonObject().put("log",JsonObject.mapFrom(entity.getLog())).put("operation","启用租客该次申请的ClientKey").put("content",JsonObject.mapFrom(clientKeyInfo).put("create_time",date))).setHandler(ar0->{
                                                        if (ar0.succeeded()&&ar0.result()){
                                                            CreateResp createResp = new CreateResp();
                                                            createResp.setSuccess(true);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withJsonOK(createResp)));
                                                        } else {
                                                            CreateResp createResp = new CreateResp();
                                                            createResp.setSuccess(false);
                                                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withJsonOK(createResp)));
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    String message = e.getLocalizedMessage();
                                                    logger.error(message, e);
                                                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainInternalServerError(message)));
                                                }
                                            } else {
                                                logger.error("postClientkeyMonitorStartById : update tb_clientkey Fail, caused: ",reply0.cause().getMessage());
                                                CreateResp createResp = new CreateResp();
                                                createResp.setSuccess(false);
                                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withJsonOK(createResp)));
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainInternalServerError(message)));
                                    }
                                }
                            } else {
                                logger.error("postClientkeyMonitorStartById: ClientSecret信息不存在");
                                asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainBadRequest("ClientSecret信息不存在")));
                            }
                        } else {
                            logger.error("postClientkeyMonitorStartById: find TenantApply Error,Caused ",reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainBadRequest("postClientkeyRefuseByTenantApplyId: find TenantApply Error,Caused "+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(PostClientkeyMonitorStartByIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void getClientkeyClientIdByClientId(String clientId, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        String tenantId = TenantTool.calculateTenantId(okapiHeaders.get(OKAPI_HEADER_TENANT));
        PostgresClient postgresClient = PostgresClient.getInstance(vertxContext.owner(), tenantId);
        try {
            vertxContext.runOnContext(v -> {
                try {
                    Criterion criterion = new Criterion();
                    if (!StringUtils.isBlank(clientId)) {
                        Criteria criteria = new Criteria();
                        criteria.setJSONB(true).addField("'clientId'").setOperation("=").setValue("'"+clientId+"'");
                        criterion.addCriterion(criteria);
                    }
                    postgresClient.get(TABLE_NAME_CLIENTKEY,ClientKeyInfo.class,criterion,true,true,reply->{
                        if (reply.succeeded()) {
                            if (reply.result() != null && ((List<ClientKeyInfo>) reply.result().getResults()).size() != 0){
                                logger.info("getClientkeyShowById: find ClientKey Info Success");
                                List<ClientKeyInfo> clientKeyInfoArray = (List<ClientKeyInfo>) reply.result().getResults();
                                ClientKeyInfo clientKeyInfo_ = clientKeyInfoArray.get(0);
                                String status = clientKeyInfo_.getStatus();
                                if ("申请已提交，待处理".contentEquals(status)){
                                    clientKeyInfo_.setStatus("0");
                                }
                                if ("ClientKey已启用".contentEquals(status)){
                                    clientKeyInfo_.setStatus("1");
                                }
                                //拒绝Tenant提出的ClientSecret申请
                                if ("已拒绝".contentEquals(status)){
                                    clientKeyInfo_.setStatus("2");
                                }
                                if ("ClientKey已停用".contentEquals(status)){
                                    clientKeyInfo_.setStatus("3");
                                }
                                if (!"0".contentEquals(clientKeyInfo_.getStatus())){
                                    String expireDate = clientKeyInfo_.getExpireDate();
                                    try {
                                        long time = simpleDateFormat.parse(expireDate).getTime();
                                        clientKeyInfo_.setExpireDate(Long.toString(time));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        String message = e.getLocalizedMessage();
                                        logger.error(message, e);
                                        asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withPlainInternalServerError("有效时间转换出错，Caused: "+message)));
                                    }
                                }
                                /*if (!"ClientKey已启用".contentEquals(clientKeyInfo_.getStatus())){
                                    logger.info("getClientkeyClientIdByClientId: ClientSecret无效");
                                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withPlainBadRequest("ClientSecret无效")));
                                } else {*/
                                    logger.info("getClientkeyClientIdByClientId: ClientSecret成功");
                                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withJsonOK(clientKeyInfo_)));
                                //}
                            } else {
                                logger.error("getClientkeyClientIdByClientId: 根据clientId查询出来的clientSecret为空");
                                asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withPlainNotFound("根据clientId查询出来的clientSecret为空")));
                            }
                        } else {
                            logger.error("getClientkeyClientIdByClientId: find ClientKey Info Fail, Caused: "+reply.cause().getMessage());
                            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withPlainBadRequest("getClientkeyShowById: find ClientKey Info Fail, Caused: "+reply.cause().getMessage())));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    String message = e.getLocalizedMessage();
                    logger.error(message, e);
                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withPlainInternalServerError(message)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyClientIdByClientIdResponse.withPlainInternalServerError(message)));
        }
    }

    @Override
    public void getClientkeyCheckIPByIpv4(String ipv4, Map<String, String> okapiHeaders, Handler<AsyncResult<Response>> asyncResultHandler, Context vertxContext) throws Exception {
        try {
            vertxContext.runOnContext(v -> {
                Boolean b = IPUtils.checkIPV4(ipv4);
                if (!b){
                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckIPByIpv4Response.withPlainBadRequest("应用访问地址IPV4不合法")));
                } else {
                    asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckIPByIpv4Response.withPlainOK("应用访问地址IPV4合法")));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getLocalizedMessage();
            logger.error(message, e);
            asyncResultHandler.handle(Future.succeededFuture(GetClientkeyCheckIPByIpv4Response.withPlainInternalServerError(message)));
        }
    }

}
