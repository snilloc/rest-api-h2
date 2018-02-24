package com.snilloc.controller;
/**
 *
 */
import com.snilloc.config.AppConfiguration;
import com.snilloc.dao.AdvertiserDao;
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.DaoDataException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api")
@Api(value = "Advertiser Resource", description = "Advertiser information")
public class RestRequestController {

    @Autowired
    private AdvertiserDao dao;

    public RestRequestController() {
        AppConfiguration app = new AppConfiguration();
    }

    public void init() throws DaoDataException {
        try {
            dao.init();
        } catch (DaoDataException ex) {
            throw ex;
        }
    }

    /**
     *
     * @param ad
     * @return
     */
    @ApiOperation(value = "returns db index of document")
    @ApiResponses(
            value = { @ApiResponse(code = 100, message = "100 message "),
            @ApiResponse(code = 101, message = "Success")}
    )
    @PostMapping(value = "/advertisers", consumes = "application/json; charset=utf-8")
    public String create(@RequestBody Advertiser ad) {
        try {
            log.warn("Saving ad index...");
            UUID uuid = dao.save(ad);
            return uuid.toString();
        } catch (DaoDataException ex) {
            // TODO Handled if failed to save data

            return null;
        }
    }

    /**
     *
     * @param document
     */
    @ApiOperation(value = "returns db index of document")
    @PostMapping(value = "/advertisers/?", params = "id", consumes = "application/json; charset=utf-8")
    public void update(@RequestBody Advertiser document){
        try {
            String id = null;
            log.warn("Updating....");
            dao.update(document);
        } catch (DaoDataException e) {
            // Handle if not found

            return;
        }
    }

    /**
     *
     * @param id of advertiser to delete
     */
    @DeleteMapping(value = "/advertiser/?", params = "id",  consumes="application/json; charset=utf-8")
    public void delete(String id){
        try {
            log.warn("Deleting id: " + id);
            UUID uuid = UUID.fromString(id);
            dao.delete(uuid);
        } catch (DaoDataException ex) {
            // Ignore if not found
            return ;
        }
    }

    /**
     * Get all of the advertisers
     *
     * @return List of {@link Advertiser}
     */
    @GetMapping(value = "/advertisers/all", produces = "application/json; charset=utf-8")
    public List<Advertiser> getAllDocuments() {
        try {
            log.warn("Getting all docs:");
            return dao.get();
        } catch (DaoDataException ex) {
            return null;
        }
    }

    public void setAdvertiserDao(AdvertiserDao dao) {
        this.dao = dao;
    }
}
