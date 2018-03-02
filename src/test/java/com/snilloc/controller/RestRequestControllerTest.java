package com.snilloc.controller;

import com.snilloc.config.TestConfiguration;
import com.snilloc.entity.Advertiser;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Slf4j
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration( classes =  TestConfiguration.class  )
@Controller
@Configurable
public class RestRequestControllerTest {

//    @Autowired
//    static TestConfiguration configuration;

//    @Autowired
    static RestRequestController restRequestController;

    @BeforeClass
    public static void setup() {

        try {
            TestConfiguration configuration = new TestConfiguration();
            restRequestController = new RestRequestController(configuration.getConnection());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRequestRequestController_getAll() {
        List<Advertiser> results = restRequestController.getAllDocuments();
        Assert.assertTrue("DB should have values", results.size() == 0);
    }

    @Test
    public void testRestRequestController_post() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Save");
        map.put("contactName", "Post");
        map.put("creditAmount", "543.40");
        restRequestController.create(map);
    }

    @Test
    public void testRestRequestController_delete() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Delete");
        map.put("contactName", "Post");
        map.put("creditAmount", "543.40");
        ResponseEntity<String> id = restRequestController.create(map);
        String body = id.getBody();
        restRequestController.delete(body);
    }
}
