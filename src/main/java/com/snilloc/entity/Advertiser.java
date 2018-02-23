package com.snilloc.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Advertiser {

    public Advertiser(UUID uuid, String name, String contactName, double creditAmount) {
        this.id = uuid;
        this.name = name;
        this.contactName = contactName;
        this.creditAmount = creditAmount;
    }

    @ApiModelProperty(notes = "unique id ")
    private UUID id;
    @ApiModelProperty(notes = "name of Advertiser")
    private String name;
    @ApiModelProperty(notes = "contact name")
    private String contactName;
    @ApiModelProperty(notes = "credit amount available")
    private double creditAmount;
}
