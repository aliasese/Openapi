package com.cnebula.lsp.openapi.test;

import com.cnebula.lsp.openapi.utils.IPUtils;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.folio.rest.jaxrs.model.Tenant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by calis on 2017/10/11.
 */
public class TestList {

    private final static Logger log = LoggerFactory.getLogger(TestList.class);

    @Test
    public void testIPV4(){
        Boolean b = IPUtils.checkIPV4("292.168.2.66");
        log.info(b);
    }

    @Test
    public void testHashSet(){
        HashSet<String> set = new HashSet<>();
        set.add("122a");
        set.add("122b");
        set.add("122a");
        System.out.println(set);
    }

    @Test
    public void testContinue1(){
        System.out.println("--------");
        for (int i = 0; i < 9; i++) {
                if (i ==2){
                    continue;
                }
            System.out.println(i);
        }
    }

    @Test
    public void testContinue(){
        System.out.println("--------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i ==2&&i<j){
                    System.out.println(i+"===="+j);
                    continue;
                }
            }
            System.out.println(i);
        }
    }

    @Test
    public void testList(){
        Tenant tenant = new Tenant();
        ArrayList<String> openapiIds = new ArrayList<>();
        openapiIds.add("1000");
        tenant.setOpenapiArray(openapiIds);
        List<String> openapiArray = tenant.getOpenapiArray();
        System.out.println(System.identityHashCode(new ArrayList<>())+"***********"+System.identityHashCode(openapiArray)+"=================="+System.identityHashCode(tenant.getOpenapiArray())+"------------------"+System.identityHashCode(openapiIds));
    }

    @Test
    public void testTrim(){
        System.out.println("  A  B  ".trim());
    }
}
