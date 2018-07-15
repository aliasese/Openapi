package com.cnebula.lsp.openapi.utils;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.folio.rest.persist.PostgresClient;

/**
 * Created by calis on 2017/9/30.
 */
public class RecordLogs {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordLogs.class);
    public static final String TABLE_NAME_OPENAPI_LOG = "log_operations";
    public static Future<Boolean> recordLogs(PostgresClient postgresClient, JsonObject record) throws Exception{
        Future<Boolean> future = Future.<Boolean>future();
        postgresClient.save(TABLE_NAME_OPENAPI_LOG,record,false,reply->{
            if (reply.succeeded()){
                LOGGER.info("Record log successfully ",reply.result());
                future.complete(true);
            } else {
                LOGGER.error("Fail record log ", reply.cause().getMessage());
            }
        });
        return future;
    }
}
