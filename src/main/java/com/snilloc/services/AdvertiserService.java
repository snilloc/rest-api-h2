package com.snilloc.services;

import com.snilloc.dao.AdvertiserDao;
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface AdvertiserService {


    public List<Advertiser> get() throws ServiceException;

    public Advertiser get(String id) throws IllegalArgumentException, ServiceException;

    public UUID save(Map<String, String> ad) throws IllegalArgumentException, ServiceException;

    public void delete(String id);

    public void setAdvertiserDao(AdvertiserDao dao);
}
