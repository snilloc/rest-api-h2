package com.snilloc.services.impl;

import com.snilloc.dao.AdvertiserDao;
import com.snilloc.dao.impl.H2AdvertiserDaoImpl;
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.DaoDataException;
import com.snilloc.exceptions.ServiceException;
import com.snilloc.services.AdvertiserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@Slf4j
public class AdvertiserServiceImpl implements AdvertiserService {

    @Autowired
    private AdvertiserDao dao;

    public AdvertiserServiceImpl(Connection connection) {
        try {
            dao = new H2AdvertiserDaoImpl(connection);
            dao.init();
        } catch (DaoDataException ex) {
//              throw ex;
        }
    }



    public List<Advertiser> get() throws ServiceException {
        try {
            List<Advertiser> ads = null;
            ads = dao.get();
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

            Advertiser ads = dao.get(uid);
            return ads;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (DaoDataException ex) {
            throw new ServiceException("Failed to find any", ex);
        }
    }

    public UUID save(Advertiser ad) throws IllegalArgumentException, ServiceException {
        try {
            if (ad.getId() == null) {
                UUID id = dao.save(ad);
                return id;
            } else {
                dao.update(ad);
                return ad.getId();
            }

        } catch (DaoDataException ex) {
            throw new ServiceException("Failed to update: " + ad.getId(), ex);
        }
    }

        public void delete(String id) {
            try {
                log.warn("Deleting id: " + id);
                UUID uuid = UUID.fromString(id);
                dao.delete(uuid);
            } catch (DaoDataException ex) {
                // Ignore if not found
                return ;
            }
        }


}
