package com.snilloc.dao;
/*
 *
 */

import com.snilloc.config.TestConfiguration;
import com.snilloc.dao.impl.H2AdvertiserDaoImpl;
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.DaoDataException;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
//@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration( classes = { TestConfiguration.class } )
public class AdvertiserDaoTest {
//public class AdvertiserDaoTest implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger(AdvertiserDaoTest.class.getName());

 //   @Autowired
    private static AdvertiserDao advertiserDao;
//    static ApplicationContext context;

//    @Autowired
    private static Connection connection;

    /*
    public void setApplicationContext(ApplicationContext context) {
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        connection = (Connection) context.getBean(Connection.class);
        if (connection == null) {
            throw new IllegalStateException("No Connection Bean Found");
        }
    } */


    @BeforeClass
    public static void setup() {
        try {
            TestConfiguration test = new TestConfiguration();
            connection = test.getConnection();
            advertiserDao = new H2AdvertiserDaoImpl(connection);
            // Create Advertiser Table
            advertiserDao.init();
        } catch (Exception ex) {
            // DATA Already created continue
//            log.error("Failed to Connect to db or create table", ex);
        }
    }

    @Test
    public void testAdvertiserSave_success() throws IOException {

        try {
            log.warn("Creating John smith user ");
            log.warn("---------------------------------");
            Advertiser ad = new Advertiser(null, "Walmart", "Bill", 1000.0);
            UUID id = advertiserDao.save(ad);
            assertNotNull(id);
            log.warn("Saved User:" + id.toString());

            // Verify data is stored in database
            Advertiser found = advertiserDao.get(id);
            log.warn("Found User: " + found.getName());
            assertTrue(found != null);
            assertTrue(found.getId() != null);
            // Clean up test database
            log.warn("Deleting user:"+ found.getId());
            advertiserDao.delete(found.getId());
        } catch (DaoDataException ex) {
            ex.printStackTrace();
            Assert.fail("Exception should not have been thrown");
        } catch (Exception ex) {
            Assert.fail("Exception should not have been thrown");
            ex.printStackTrace();
        }
    }


    @Test
    public void testAdvertiserUpdate_success() {
        log.warn("Updating John smith message in DB");
        log.warn("---------------------------------");
        UUID id = UUID.randomUUID();

        try {
            Advertiser ad = new Advertiser(null, "123", "John", 100.0);
            UUID index = advertiserDao.save(ad);

            log.warn("Saved User:" + index.toString());
            // Updating Message
            ad = new Advertiser(index, "John", "WellSmith", 50.0);
            advertiserDao.update(ad);
            log.warn("Updating user: " + index.toString());

            // Clean up test database
            advertiserDao.delete(index);
            log.warn("Deleting user:" + index.toString());
        } catch (DaoDataException ex) {
            ex.printStackTrace();
            assertTrue("DaoDAtaException should not have been thrown", false);
        }
    }

    @Test
    public void testAdvertiserGetAll_success() {
        log.warn("Getting All Advertisers");
        log.warn("---------------------------------");

        try {
            Advertiser ad = new Advertiser(null, "Walmart", "Test Contact1", 1000.0);
            UUID id = advertiserDao.save(ad);
            ad = new Advertiser(id, "Party City", "Test Contact2", 100.0);
            id = advertiserDao.save(ad);
            ad = new Advertiser(id, "iHeartMedia", "Test Contact3", 100.0);
            id = advertiserDao.save(ad);

            // Updating Message
            List<Advertiser> list = advertiserDao.get();
            assertTrue("Should have thrown an error.  Not Found.", list.size() > 0);
        } catch (DaoDataException ex) {
            assertTrue("Should have return a list of Ads > 2", false);
        }
    }

    @Test
    public void testAdvertiserUpdate_notFound() {
        log.warn("Updating an Invalid id in DB");
        log.warn("---------------------------------");
        UUID id = UUID.randomUUID();

        try {
            Advertiser ad = new Advertiser(id, "123", "John", 100.0);
            // Updating Message
            advertiserDao.update(ad);
            log.warn("Updating user: " + id.toString());
            //assertTrue("Should have thrown an error.  Not Found.", false);
        } catch (DaoDataException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdvertiserDelete_notFound() {
        log.warn("Deleting Invalid User in DB");
        log.warn("---------------------------------");
        UUID id = UUID.randomUUID();

        try {
            advertiserDao.delete(id);
            log.warn("Deleting user:" + id.toString());
        } catch (DaoDataException ex) {
            assertTrue(false);
        }
    }

    @Test
    public void testAdvertiserGet_notFound() {
        log.warn("Updating John smith message in DB");
        log.warn("---------------------------------");
        UUID id = UUID.randomUUID();

        try {
            Advertiser ad = advertiserDao.get(id);
            if (ad == null) {

            } else {
                System.out.println("Found: " + ad.toString());
                assertTrue("Should have not found the Ad.", false);
            }
        } catch (DaoDataException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdvertiserTable_createAdTableAgain() {
        try {
            advertiserDao.init();
            assertTrue("Should have failed create another table" ,false);
        } catch (Exception ex) {

        }
    }

    @Test
    public void testAdvertiserGet_noConnection() {
        AdvertiserDao testDao = new H2AdvertiserDaoImpl(null);
        try {

            List<Advertiser> list = testDao.get();
            assertTrue("Should have failed to connection" ,false);
        } catch (Exception ex) {

        }
    }

    @Test
    public void testAdvertiserSave_noConnection() {
        AdvertiserDao testDao = new H2AdvertiserDaoImpl(null);
        try {
            Advertiser ad = new Advertiser(null, "Test", "Contract", 1000);
            testDao.save(ad);
            assertTrue("Should have failed to connection" ,false);
        } catch (Exception ex) {

        }
    }

    @Test
    public void testAdvertiserUpdate_noConnection() {
        AdvertiserDao testDao = new H2AdvertiserDaoImpl(null);
        try {
            Advertiser ad = new Advertiser(null, "Test", "Contract", 1000);
            testDao.update(ad);
            assertTrue("Should have failed to connection" ,false);
        } catch (Exception ex) {

        }
    }

}
