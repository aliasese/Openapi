package com.cnebula.lsp.openapi.utils;

import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;

public class PreAuthModOpenapiUtil {
    private static final Logger logger = LoggerFactory.getLogger(PreAuthModOpenapiUtil.class);

    public static void getTenantAuthorizedModOpenapi(WebClient webClient, String hostPort, String tenant, String token, Future<JsonArray> originFuture) {
        Future<Object> startFuture = Future.future();
        startFuture.complete();
        startFuture.compose(getTenantFromOkapi -> {
            Future<JsonArray> future = Future.future();
            webClient.getAbs("http://" + hostPort + "/_/proxy/tenants")
                    .putHeader("Accept","application/json;charset=utf-8")
                    .send(reply -> {
                        if (reply.succeeded()) {
                            if (reply.result().statusCode() == 200) {
                                logger.info("Success to get All tenants from Okapi");
                                future.complete(reply.result().bodyAsJsonArray());
                            } else {
                                logger.error("Fail to get All tenants from Okapi, httpCode: " + reply.result().statusCode() + " , Message: " + reply.result().statusMessage());
                                future.fail("Fail to get All tenants from Okapi, httpCode: " + reply.result().statusCode() + " , Message: " + reply.result().statusMessage());
                            }
                        } else {
                            logger.error("Exception to get All tenants from Okapi, Causeed: " + reply.cause().getLocalizedMessage(), reply.cause());
                            future.fail("Exception to get All tenants from Okapi, Causeed: " + reply.cause().getLocalizedMessage());
                        }
                    });
            return future;
        }).compose(recursiveTenants -> {
            Future<JsonArray> future = Future.future();
            //JsonArray validTenants = new JsonArray();
            recursiveTenants(recursiveTenants, webClient, hostPort, tenant, token, new JsonArray(), future);
            return future;
        }).setHandler(tenantAuthorizedModOpenapiRe -> {
           if (tenantAuthorizedModOpenapiRe.succeeded()) {
                logger.info("Success to get tenants authorized mod-openapi");
                originFuture.complete(tenantAuthorizedModOpenapiRe.result());
           } else {
               logger.error("Exception to get tenants authorized mod-openapi, Caused: " + tenantAuthorizedModOpenapiRe.cause().getLocalizedMessage(), tenantAuthorizedModOpenapiRe.cause());
               originFuture.fail("Exception to get tenants authorized mod-openapi, Caused: " + tenantAuthorizedModOpenapiRe.cause().getLocalizedMessage());
           }
        });
    }

    public static void recursiveTenants(JsonArray tenants, WebClient webClient, String hostPort, String tenant, String token, JsonArray validTenants, Future<JsonArray> future) {
        if (tenants.isEmpty()) {
            future.complete(validTenants);
        } else {
            Future<Object> startFuture = Future.future();
            startFuture.complete();
            String tenant_id = tenants.getJsonObject(0).getString("id");
            startFuture.compose(getModulesOfTenant -> {
                Future<JsonArray> future1 = Future.future();
                webClient.getAbs("http://" + hostPort + "/_/proxy/tenants/" + tenant_id + "/modules")
                        .putHeader("Accept", "application/json;charset=utf-8")
                        .send(reply -> {
                            if (reply.succeeded()) {
                                if (reply.result().statusCode() == 200) {
                                    logger.info("Success to get modules that have been authorized to tenant of: " + tenant_id);
                                    future1.complete(reply.result().bodyAsJsonArray());
                                } else {
                                    logger.error("Fail to get modules that have been authorized to tenant of: " + tenant_id + " , httpCode: " + reply.result().statusCode() + " , Message: " + reply.result().statusMessage());
                                    future1.fail("Fail to get modules that have been authorized to tenant of: " + tenant_id + " , httpCode: " + reply.result().statusCode() + " , Message: " + reply.result().statusMessage());
                                }
                            } else {
                                logger.error("Exception to get modules that have been authorized to tenant of: " + tenant_id + " , Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                                future1.fail("Exception to get modules that have been authorized to tenant of: " + tenant_id + " , Caused: " + reply.cause().getLocalizedMessage());
                            }
                        });
                return future1;
            }).compose(checExistModOpenapi -> {
                Future<Object> future1 = Future.future();
                checExistModOpenapi.forEach((moduleId) -> {
                    if (new JsonObject(moduleId.toString()).getString("id").contains("mod-openapi")) {
                        //validTenants.add(tenant_id);
                        future1.complete(tenant_id);
                        return;
                    }
                });
                if (!future1.isComplete())
                    future1.complete("");
                return future1;
            }).setHandler(re -> {
               if (re.succeeded()) {
                   logger.info("Success to get modules of tenant of: " + tenant_id);
                   if (!StringUtil.isNullOrEmpty(re.result().toString()))
                       validTenants.add(tenant_id);
                   tenants.remove(0);
                   recursiveTenants(tenants, webClient, hostPort, tenant, token, validTenants, future);
               } else {
                   logger.error("Exception to get modules of tenant of: " + tenant_id + " , Caused: " + re.cause().getLocalizedMessage(), re.cause());
                   future.fail("Exception to get modules of tenant of: " + tenant_id + " , Caused: " + re.cause().getLocalizedMessage());
               }
            });
        }
    }

    public static void checkModOpenapiAuth(WebClient webClient, String hostPort, String tenant_id, String tenant, String token, Future<Object> originFuture) {
        String okapiUrl = "http://" + hostPort + "/_/proxy/tenants/" + tenant_id + "/modules";
        Future<Object> startFuture = Future.future();
        startFuture.complete();
        startFuture.compose(checkTenant -> {
            Future<Object> future = Future.future();
            /*webClient.getAbs(okapiUrl)
                    .putHeader("Accept", "application/json;charset=utf-8")
                    .send(reply -> {
                        if (reply.succeeded()) {
                            if (reply.result().statusCode() == 200) {
                                future.complete(reply.result().bodyAsJsonArray());
                            } else if (reply.result().statusCode() == 404) {
                                future.complete("NO Such Tenant");
                            } {
                                logger.error("Fail to get Tenant list form Okapi: " + okapiUrl + " , Reason: httpCode: " + reply.result().statusCode() + " , Message: " + reply.result().statusMessage());
                                future.fail("Fail to get Tenant list form Okapi: " + okapiUrl + " , Reason: httpCode: " + reply.result().statusCode() + " , Message: " + reply.result().statusMessage());
                            }
                        } else {
                            logger.error("Exception to get Tenant list from Okapi: " + okapiUrl + " , Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                            future.fail("Exception to get Tenant list from Okapi: " + okapiUrl + " , Caused: " + reply.cause().getLocalizedMessage());
                        }
                    });*/
            webClient.getAbs(okapiUrl)
                    .putHeader("Accept", "application/json;charset=utf-8")
                    //.putHeader("X-Okapi-Tenant", tenant)
                    //.putHeader("X-Okapi-Token", token)
                    .send(reply -> {
                        if (reply.succeeded()) {
                            if (reply.result().statusCode() == 200) {
                                logger.info("Success to get modules of tenant of: " + tenant_id);
                                future.complete(reply.result().bodyAsJsonArray());
                            } else if (reply.result().statusCode() == 404) {
                                future.complete(false);
                            } else {
                                logger.error("Fail to get modules of tenant of: " + tenant_id + " , Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                                future.fail("Fail to get modules of tenant of: " + tenant_id + " , Caused: " + reply.cause().getLocalizedMessage());
                            }
                        } else {
                            logger.error("Exception to get modules of tenant of: " + tenant_id + " Caused: " + reply.cause().getLocalizedMessage(), reply.cause());
                            future.fail("Exception to get modules of tenant of: " + tenant_id + " Caused: " + reply.cause().getLocalizedMessage());
                        }
                    });
            return future;
        }).compose(checkAuthOpenapi -> {
            Future<Object> future = Future.future();
            if (checkAuthOpenapi.equals(false)) {
                future.complete(false);
            } else {
                /*new JsonArray(judgeExistTenant.toString()).forEach((originTenant) -> {
                    if (JsonObject.mapFrom(originTenant).getString("id").contentEquals(tenant_id)) {
                        future.complete(true);
                    }
                });*/
                new JsonArray(checkAuthOpenapi.toString()).forEach((moduleId) -> {
                    if (new JsonObject(moduleId.toString()).getString("id").contains("mod-openapi")){
                        future.complete(true);
                        return;
                    }
                });
            }
            if (!future.isComplete())
                future.complete("UnAuthModOpenapi");
            return future;
        }).compose(judgeRe -> {
            Future<Object> future = Future.future();
            if (judgeRe.equals(true)) {
                future.complete(true);
            } else if (judgeRe.equals(false)){
                Future<Object> stratRegFuture = Future.future();
                stratRegFuture.complete();
                startFuture.compose(getOriginTenant -> {
                    Future<JsonObject> tenantFuture = Future.future();
                    webClient.getAbs("http://" + hostPort + "/tenantman/tenants/" + tenant_id)
                            .putHeader("X-Okapi-Tenant", tenant)
                            .putHeader("X-Okapi-Token", token)
                            .send(tenantInfo -> {
                                if (tenantInfo.succeeded()) {
                                    JsonObject tenantReg = new JsonObject();
                                    JsonObject entries = tenantInfo.result().bodyAsJsonObject();
                                    JsonObject data = entries.getJsonObject("data");
                                    //tenantFuture.complete(data);
                                    tenantReg.put("id", data.getString("id"));
                                    tenantReg.put("name", data.getJsonObject("tenant").getString("tenant_name_cn"));
                                    tenantReg.put("description", data.getJsonObject("tenant").getString("tenant_intro"));
                                    tenantFuture.complete(tenantReg);
                                } else {
                                    tenantFuture.fail(tenantInfo.cause());
                                }
                            });
                    return tenantFuture;
                }).compose(regTenant -> {
                    Future<Object> registTenantFuture = Future.future();
                    webClient.postAbs("http://" + hostPort + "/_/proxy/tenants")
                            //.putHeader("X-Okapi-Tenant", tenant)
                            //.putHeader("X-Okapi-Token", token)
                            .sendJsonObject(regTenant, ar -> {
                                if (ar.succeeded()) {
                                    if (ar.result().statusCode() == 201) {
                                        registTenantFuture.complete();
                                    } else if (ar.result().statusCode() == 400 && ar.result().bodyAsString().contains("Duplicate tenant id")) {
                                        registTenantFuture.complete();
                                    } else {
                                        registTenantFuture.fail(ar.result().bodyAsString());
                                    }
                                } else {
                                    registTenantFuture.fail(ar.cause());
                                }
                            });
                    return registTenantFuture;
                }).setHandler(regRe -> {
                    if (regRe.succeeded()) {
                        logger.info("Success to regist tenant of: " + tenant_id);
                        future.complete(false);
                    } else {
                        logger.error("Exception to regist tenant of: " + tenant_id + " , Caused: " + regRe.cause().getLocalizedMessage(), regRe.cause());
                        future.fail("Exception to regist tenant of: " + tenant_id + " , Caused: " + regRe.cause().getLocalizedMessage());
                    }
                });
            } else {
                future.complete(false);
            }
            return future;
        }).compose(getAuthModules -> {
            Future<Object> future = Future.future();
            if (getAuthModules.equals(true)) {
                future.complete(true);
            } else {
                Future<Object> startGetFuture = Future.future();
                startGetFuture.complete();
                startFuture.compose(getModOpenapi -> {
                    Future<Object> getFuture = Future.future();
                    webClient.getAbs("http://" + hostPort + "/_/discovery/health")
                            //.putHeader("X-Okapi-Tenant", tenant)
                            //.putHeader("X-Okapi-Token", token)
                            .send(baseFL -> {
                                if (baseFL.succeeded()) {
                                    if (baseFL.result().statusCode() == 200) {
                                        baseFL.result().bodyAsJsonArray().forEach((base) -> {
                                            if (new JsonObject(base.toString()).getString("healthMessage").contentEquals("OK")) {
                                                if (new JsonObject(base.toString()).getString("srvcId").contains("mod-openapi")) {
                                                    getFuture.complete(new JsonObject(base.toString()).getString("srvcId"));
                                                    return;
                                                }
                                            }
                                        });
                                        if (!getFuture.isComplete()) {
                                            logger.error("Mod-openapi don't exist now, please authorize mod-openapi to Okapi firstly");
                                            getFuture.fail("Mod-openapi don't exist now, please authorize mod-openapi to Okapi firstly");
                                        }
                                    } else {
                                        logger.error("Fail to get healthy modules, httpCode: " + baseFL.result().statusCode() + " , Message: " + baseFL.result().statusMessage());
                                        getFuture.fail("Fail to get healthy modules, httpCode: " + baseFL.result().statusCode() + " , Message: " + baseFL.result().statusMessage());
                                    }
                                } else {
                                    logger.error("Exception to get healthy mod-openapi, Caused: " + baseFL.cause().getLocalizedMessage(), baseFL.cause());
                                    getFuture.fail("Exception to get healthy mod-openapi, Caused: " + baseFL.cause().getLocalizedMessage());
                                }
                            });
                    return getFuture;
                }).setHandler(getRe -> {
                    if (getRe.succeeded()) {
                        webClient.postAbs("http://" + hostPort + "/_/proxy/tenants/" + tenant_id + "/modules")
                                //.putHeader("X-Okapi-Tenant", tenant)
                                //.putHeader("X-Okapi-Token", token)
                                .sendJsonObject(new JsonObject().put("id", getRe.result().toString()), ar -> {
                                    if (ar.succeeded()) {
                                        if (ar.result().statusCode() == 201) {
                                            future.complete();
                                        } else if (ar.result().statusCode() == 400 && ar.result().bodyAsString().contains("already provided")) {
                                            future.complete();
                                        } else  {
                                            future.fail(ar.result().bodyAsString());
                                        }
                                    } else {
                                        future.fail(ar.cause());
                                    }
                                });
                    } else {
                        logger.error("Exception to ReAuthorize mod-openapi to tenant of: " + tenant_id + " , Caused: " + getRe.cause().getLocalizedMessage(), getRe.cause());
                        future.fail("Exception to ReAuthorize mod-openapi to tenant of: " + tenant_id + " , Caused: " + getRe.cause().getLocalizedMessage());
                    }
                });
            }
            return future;
        }).setHandler(re -> {
            if (re.succeeded()) {
                logger.info("Success to check if mod-openapi has been authorized to tenant of: " + tenant_id);
                originFuture.complete();
            } else {
                logger.error("Exception to check if mod-openapi has been authorized to tenant of: " + tenant_id + " , Caused: " + re.cause().getLocalizedMessage(), re.cause());
                originFuture.fail("Exception to check if mod-openapi has been authorized to tenant of: " + tenant_id + " , Caused: " + re.cause().getLocalizedMessage());
            }
        });
    }

}
