package com.snilloc.services;

import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

public interface AdvertiserService {


    public List<Advertiser> get() throws ServiceException;

    public Advertiser get(String id) throws IllegalArgumentException, ServiceException;

    public UUID save(Advertiser ad) throws IllegalArgumentException, ServiceException;

    public void delete(String id);
}
