package com.snilloc.dao;

import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.DaoDataException;

import java.util.List;
import java.util.UUID;

public interface AdvertiserDao {

    public void init() throws DaoDataException;

    public UUID save(Advertiser movie) throws DaoDataException;

    public void update(Advertiser advertiser) throws DaoDataException;

    public Advertiser get(UUID id) throws DaoDataException;

    public List<Advertiser> get() throws DaoDataException;

    public void delete(UUID id) throws DaoDataException;


}
