package com.snilloc.controller;
/**
 *
 */
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.ServiceException;
import com.snilloc.services.AdvertiserService;
import com.snilloc.services.impl.AdvertiserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api")
@Api(value = "Advertiser Resource", description = "Advertiser information")
public class RestRequestController {

    private AdvertiserService advertiserService;

    public RestRequestController(Connection connection) {
        advertiserService = new AdvertiserServiceImpl(connection);
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
            UUID uuid = advertiserService.save(ad);
            return uuid.toString();
        } catch (ServiceException ex) {
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
            advertiserService.save(document);
        } catch (ServiceException e) {
            // Handle if not found

            return;
        }
    }

    /**
     *
     * @param id of advertiser to delete
     */
    @DeleteMapping(value = "/advertisers/?", params = "id",  consumes="application/json; charset=utf-8")
    public void delete(String id){
         log.warn("Deleting id: " + id);
         advertiserService.delete(id);
    }

    @GetMapping(value = "/advertisers/?", params = "id",  consumes="application/json; charset=utf-8")
    public Advertiser get(String id){
        log.warn("Retrieving id: " + id);
        try {
            Advertiser ad = advertiserService.get(id);
            return ad;
        } catch (ServiceException ex) {
            // TODO return handling
            return null;
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
            return advertiserService.get();
        } catch (ServiceException ex) {
            return null;
        }
    }

    public void setAdvertiserService(AdvertiserService ad) {
        this.advertiserService = ad;
    }

}
