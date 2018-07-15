package com.cnebula.lsp.openapi.test;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import javafx.geometry.Pos;
import org.folio.rest.persist.PostgresClient;
import org.folio.rest.tools.utils.NetworkUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calis on 2017/10/7.
 */
@RunWith(VertxUnitRunner.class)
public class OpeanapiUnitTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(OpeanapiUnitTest.class);
    public static final String DEFAULT_TEMP_DIR = System.getProperty("java.io.tmpdir");
    private Vertx vertx;
    private Integer port;
    private WebClient webClient;
    private final static String URL = "localhost";
    private final static String uriOfStartOpenapi = "/openapi/start/post";
    private final static String uriOfStopOpenapi = "/openapi/stop/post";
    private final static String tenant = "diku_openapi";
    private AsyncSQLClient sqlClient;

    @Before
    public void setUp(TestContext context) throws IOException {
        VertxOptions vOptions = new VertxOptions().setMaxWorkerExecuteTime(4L * 1000 * 1000000)
                .setWarningExceptionTime(6L * 1000 * 1000000)//6秒
                .setBlockedThreadCheckInterval(6L * 1000 * 1000000);
        vertx = Vertx.vertx(vOptions);
        webClient = WebClient.create(vertx);
        sqlClient = PostgreSQLClient.createShared(vertx, PostgresClient.getInstance(vertx).getConnectionConfig());
        // ServerSocket socket = new ServerSocket(80);
        // port = socket.getLocalPort();
        port = NetworkUtils.nextFreePort();
        System.out.println(port);
        // socket.close();
        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", port)
                ).setInstances(1);
        vertx.deployVerticle(org.folio.rest.RestVerticle.class.getName(), options, context.asyncAssertSuccess());
    }
    @After
    public void tearDown(TestContext context) {
        deleteTempFilesCreated();
        vertx.close(context.asyncAssertSuccess());
    }

    private void deleteTempFilesCreated(){
        System.out.println("deleting created files");
        // Lists all files in folder
        File folder = new File(DEFAULT_TEMP_DIR);
        File fList[] = folder.listFiles();
        // Searchs test.json
        for (int i = 0; i < fList.length; i++) {
            String pes = fList[i].getName();
            // and deletes
            boolean success = fList[i].delete();
        }
    }

    @Rule
    public Timeout rule = Timeout.seconds(16);

    @Test
    public void testPostgreClient() {
        PostgresClient instance = PostgresClient.getInstance(vertx);
        instance.select("SELECT * FROM testlib_mod_openapi.tb_clientkey UNION ALL SELECT * FROM vendor100000_mod_openapi.tb_clientkey",  reply -> {
            if (reply.succeeded()) {
                LOGGER.info("Success query ");
                //List<String> result = reply.result();
                //LOGGER.info(result);
            } else {
                LOGGER.error(reply.cause().getLocalizedMessage(), reply.cause());

            }
        });
        /*Future<Object> startFuture = Future.future();
        startFuture.complete();
        startFuture.compose(getConn -> {
            Future<SQLConnection> future = Future.future();
            sqlClient.getConnection(conn -> {
                if (conn.succeeded()) {
                    future.complete(conn.result());
                } else {
                    LOGGER.error("Fail to get connection from PostgreSQL connection pool, Caused: " + conn.cause().getLocalizedMessage());
                    future.fail("Fail to get connection from PostgreSQL connection pool, Caused: " + conn.cause().getLocalizedMessage());
                }
            });
            return future;
        }).setHandler(ar -> {
            instance.select("SELECT count(*) FROM tb_clientkey",  reply -> {
                if (reply.succeeded()) {
                    LOGGER.info("Success query ");
                    //List<String> result = reply.result();
                    //LOGGER.info(result);
                } else {
                    LOGGER.error(reply.cause().getLocalizedMessage(), reply.cause());

                }
            });
        });*/
    }

    private void readFile2(String fileName,Handler<AsyncResult<JsonObject>> handler){
        try {
            vertx.fileSystem().readFile("src/test/resources/"+fileName,ar->{
                if (ar.succeeded()){
                    LOGGER.info("Read parameter data successfully");
                    JsonObject jsonObject = ar.result().toJsonObject();
                    handler.handle(Future.succeededFuture(jsonObject));
                    return;
                }
                LOGGER.error("Fail to read parameter data");
                handler.handle(Future.failedFuture("Fail to read parameter data"));
            });
        } catch (Exception e) {
            e.printStackTrace();
            //handler.handle(new cn.edu.calis.lsp.module.entity.Failure<JsonObject>());
        }
    }

    //@Test
    public void testLSPOpenapi(TestContext context) {
        Async async = context.async();
        List<Future> futures = new ArrayList<Future>();
        futures.add(testStartOpenapi());
        futures.add(testStopOpenapi());
        CompositeFuture compositeFuture = CompositeFuture.join(futures).setHandler(ar->{
            System.out.println(ar.result());
            context.assertTrue(ar.succeeded());
            async.complete();
        });
    }

    private Future<Boolean> testStartOpenapi(){
        Future<Boolean> future = Future.<Boolean>future();
        //webClient = WebClient.create(vertx);
        readFile2("StartOpenapi.json",ar->{
            webClient.post(port, URL, uriOfStartOpenapi).putHeader("Content-type", "application/json").putHeader("Accept","text/plain").putHeader("X-Okapi-Tenant",tenant).sendJsonObject(ar.result(),response->{
                if (response.succeeded()&&response.result().statusCode()==200){
                    LOGGER.info("Start Openapi API Successfully");
                    String result = response.result().bodyAsString();
                    LOGGER.info(result);
                    future.complete(true);
                    return;
                }
                //response.cause().printStackTrace();
                LOGGER.error("Fail to Start Openapi API");
                future.complete(false);
            });
        });
        return future;
    }

    private Future<Boolean> testStopOpenapi(){
        Future<Boolean> future = Future.<Boolean>future();
        //webClient = WebClient.create(vertx);
        readFile2("StartOpenapi.json",ar->{
            webClient.post(port, URL, uriOfStopOpenapi).putHeader("Content-type", "application/json").putHeader("Accept","text/plain").putHeader("X-Okapi-Tenant",tenant).sendJsonObject(ar.result(),response->{
                if (response.succeeded()&&response.result().statusCode()==200){
                    LOGGER.info("Start Openapi API Successfully");
                    String result = response.result().bodyAsString();
                    LOGGER.info(result);
                    future.complete(true);
                    return;
                }
                //response.cause().printStackTrace();
                LOGGER.error("Fail to Start Openapi API");
                future.complete(false);
            });
        });
        return future;
    }

}
