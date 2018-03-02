package com.snilloc.services.impl;

import com.snilloc.dao.AdvertiserDao;
import com.snilloc.dao.impl.H2AdvertiserDaoImpl;
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.DaoDataException;
import com.snilloc.exceptions.ServiceException;
import com.snilloc.services.AdvertiserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AdvertiserServiceImpl implements AdvertiserService {

//    @Qualifier("h2AdvertiserDaoImpl")
    @Autowired
    private AdvertiserDao advertiserDao;

    public AdvertiserServiceImpl(Connection connection) {

        try {
            advertiserDao = new H2AdvertiserDaoImpl(connection);
            advertiserDao.init();
        } catch (DaoDataException ex) {
            //System.out.println("ADVERT TABLE already created");
            //ex.printStackTrace();
        }
    }


    public List<Advertiser> get() throws ServiceException {
        try {
            List<Advertiser> ads = null;
            ads = advertiserDao.get();
            return ads;
        } catch (DaoDataException ex) {
            throw new ServiceException("Failed to find any", ex);
        }
    }


    public Advertiser get(String id) throws IllegalArgumentException, ServiceException {
        try {
            if (id != null) {
                throw new IllegalArgumentException("Invalid id");
            }

            UUID uid = UUID.fromString(id);

            Advertiser ads = advertiserDao.get(uid);
            return ads;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (DaoDataException ex) {
            throw new ServiceException("Failed to find any", ex);
        }
    }

    public UUID save(Map<String, String> body) throws IllegalArgumentException, ServiceException {
        Advertiser ad = null;
        try {
            String name = body.get("name");
            String contactName = body.get("contactName");
            Double creditAmount = Double.parseDouble(body.get("creditAmount"));
            String id = body.get("id");
            UUID uid = null;
            if (id != null) {
                uid = UUID.fromString(id);
                ad = new Advertiser(uid, name, contactName, creditAmount);
                advertiserDao.update(ad);
                return ad.getId();
            } else {
                ad = new Advertiser(uid, name, contactName, creditAmount);
                UUID idCreated = advertiserDao.save(ad);
                return idCreated;
            }

        } catch (DaoDataException ex) {
            throw new ServiceException("Failed to update: " + ad.getId(), ex);
        }
    }

    public void delete(String id) {
        try {
            log.warn("Deleting id: " + id);
            UUID uuid = UUID.fromString(id);
            advertiserDao.delete(uuid);
        } catch (DaoDataException ex) {
            // Ignore if not found
            return;
        }
    }

    public void setAdvertiserDao(AdvertiserDao dao) {
        this.advertiserDao = dao;
    }


}
