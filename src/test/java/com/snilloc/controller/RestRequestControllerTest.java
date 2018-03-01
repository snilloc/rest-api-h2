package com.snilloc.controller;

import com.snilloc.config.TestConfiguration;
import com.snilloc.entity.Advertiser;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration( classes = { TestConfiguration.class } )
public class RestRequestControllerTest {

    private static RestRequestController controller;

    @BeforeClass
    public static void setup() {
        try {
            TestConfiguration test = new TestConfiguration();
            controller = new RestRequestController(test.getConnection());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRequestRequestController_getAll() {
        List<Advertiser> results = controller.getAllDocuments();
        Assert.assertTrue("DB should have values", results.size() == 0);
    }

    @Test
    public void testRestRequestController_post() {
        Advertiser ad = new Advertiser(null, "Save", "Post", 5340.0);
        controller.create(ad);
    }

    @Test
    public void testRestRequestController_delete() {
        Advertiser ad = new Advertiser(null, "Delete","Post", 5340.0);
        String id = controller.create(ad);
        controller.delete(id);
    }
}
