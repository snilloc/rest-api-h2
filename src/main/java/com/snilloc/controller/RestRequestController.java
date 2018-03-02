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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
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
     * @return
     */
    @ApiOperation(value = "returns db index of document")
    @ApiResponses(
            value = { @ApiResponse(code = 100, message = "100 message "),
            @ApiResponse(code = 101, message = "Success")}
    )
    @PostMapping(value = "/advertisers", consumes = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<String> create(@RequestParam Map<String, String> body) {
        try {
            log.warn("Saving ad index...");
            UUID uuid = advertiserService.save(body);
            return new ResponseEntity<String>(uuid.toString(), HttpStatus.CREATED);
        } catch (ServiceException ex) {
            return new ResponseEntity<String>("Failed to save the request.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param document
     */
    @ApiOperation(value = "returns db index of document")
    @PutMapping(value = "/advertisers", consumes = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<String> update(@RequestParam Map<String, String> document){
        try {
            log.warn("Updating....");
            advertiserService.save(document);
            return new ResponseEntity<String>("PUT Response", HttpStatus.ACCEPTED);
        } catch (ServiceException e) {
            // Handle if not found
            String id = document.get("id");
            return new ResponseEntity<String>("Failed to update id: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param id of advertiser to delete
     */
    @DeleteMapping(value = "/advertisers/{id}",  consumes="application/json; charset=utf-8")
    public void delete(@PathVariable (value="id", required=true) String id){
         log.warn("Deleting id: " + id);
         advertiserService.delete(id);
    }

    @GetMapping(value = "/advertisers/{id}", params = "id",  consumes="application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<Advertiser> get(@RequestParam (value="id", required = true) String id) throws ServiceException {
        log.warn("Retrieving id: " + id);
        try {
            Advertiser ad = advertiserService.get(id);
            return new ResponseEntity<Advertiser>(ad, HttpStatus.OK);
        } catch (ServiceException ex) {
            throw ex;
           // return new ResponseEntity<Advertiser>("Failed to retrieve id: " + id, HttpStatus.BAD_REQUEST);
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
