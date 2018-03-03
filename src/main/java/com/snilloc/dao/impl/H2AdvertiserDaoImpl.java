package com.snilloc.dao.impl;

import com.snilloc.dao.AdvertiserDao;
import com.snilloc.entity.Advertiser;
import com.snilloc.exceptions.DaoDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * H2 Database Movie Data Access Object that implements Movie DAO
 */
@Slf4j
@Repository("advertiserDao")
public class H2AdvertiserDaoImpl implements AdvertiserDao {

    private Connection connection;

    // TABLE
    private final String CREATE_ADVERTISER_TABLE_SQL = "CREATE TABLE ADVERTISER (UID VARCHAR(255) PRIMARY KEY, " +
            "NAME VARCHAR(255), CONTACT_NAME VARCHAR(255), CREDIT_AMOUNT DOUBLE)";

    // SQL
    private final String GET_ADVERTISER_BY_ID_SQL = "SELECT UID, NAME, CONTACT_NAME, CREDIT_AMOUNT FROM ADVERTISER WHERE UID = ? ";
    private final String GET_ALL_ADVERTISER_SQL = "SELECT * FROM ADVERTISER";
    private final String INSERT_ADVERTISER_BY_ID_SQL = "INSERT INTO ADVERTISER (UID, NAME, CONTACT_NAME, CREDIT_AMOUNT) VALUES (?, ?, ?, ?)";
    private final String UPDATE_ADVERTISER_BY_ID_SQL = "UPDATE ADVERTISER SET NAME= ?, CONTACT_NAME= ?, CREDIT_AMOUNT= ? WHERE UID = ? ";
    private final String DELETE_ADVERTISER_BY_ID_SQL = "DELETE ADVERTISER WHERE UID = ?";

    public H2AdvertiserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void init() throws DaoDataException {
        PreparedStatement createStatement = null;
        try {
            createStatement = this.connection.prepareStatement(CREATE_ADVERTISER_TABLE_SQL);
            createStatement.executeUpdate();
            log.warn("ADVERTISER TABLE Created");
        } catch (SQLException ex) {
            log.warn("SQL Exception: " + ex.getMessage());
            throw new DaoDataException("SQL Error", ex);
        } catch (Exception ex) {
            log.error("Unknown error: " + ex.getMessage());
            throw new DaoDataException("unknown error", ex);
        } finally {
            close(createStatement);
        }
    }

    public void delete(UUID id) throws DaoDataException {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement(DELETE_ADVERTISER_BY_ID_SQL);
            deleteStatement.setString(1, id.toString());
            deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(deleteStatement);
        }
    }

    public void update(Advertiser advertiser) throws DaoDataException {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(UPDATE_ADVERTISER_BY_ID_SQL);
            updateStatement.setString(1, advertiser.getName());
            updateStatement.setString(2, advertiser.getContactName());
            updateStatement.setDouble(3, advertiser.getCreditAmount());
            updateStatement.setString(4, advertiser.getId().toString());
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(updateStatement);
        }
    }

    public UUID save(Advertiser ad) throws DaoDataException {
        PreparedStatement insertStatement = null;
        try {
            UUID newId = UUID.randomUUID();
            // Check if newID already does not exists
            // TODO
            /*
            while (get(newId) != null) {
                newId = UUID.randomUUID();
            } */

            // Save movie to DB
            insertStatement = connection.prepareStatement(INSERT_ADVERTISER_BY_ID_SQL);
            insertStatement.setString(1, newId.toString());
            insertStatement.setString(2, ad.getName());
            insertStatement.setString(3, ad.getContactName());
            insertStatement.setDouble(4, ad.getCreditAmount());
            insertStatement.executeUpdate();
            return newId;
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(insertStatement);
        }
    }

    public Advertiser get(UUID ud) throws DaoDataException {
        PreparedStatement getStatement = null;
        try {
            String id = ud.toString();
            getStatement = connection.prepareStatement(GET_ADVERTISER_BY_ID_SQL);
            getStatement.setString(1, id);
            ResultSet rs = getStatement.executeQuery();
            Advertiser ad = null;
            if (rs == null) {
                throw new DaoDataException("User " + ud.toString() + " not found.");
            }

            while (rs.next()) {
                String name = rs.getString("NAME");
                String contact_name = rs.getString("CONTACT_NAME");
                Double creditAmount = rs.getDouble("CREDIT_AMOUNT");
                System.out.println("ID: " + id.toString() + " NAME: " + name + " CONTACT_NAME: " + contact_name +
                        " CREDIT_AMOUNT:" + creditAmount);
                ad = new Advertiser(ud, name, contact_name, creditAmount);
                // return the first answer
                return ad;
            }

            throw new DaoDataException("User " + ud.toString() + " not found.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoDataException(ex);
        } finally {
            close(getStatement);
        }
    }

    ;

    public List<Advertiser> get() throws DaoDataException {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_ADVERTISER_SQL);
            System.out.println("H2 In-Memory Database inserted through Statement");
            List<Advertiser> ads = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("UID");
                String name = rs.getString("NAME");
                String genre = rs.getString("CONTACT_NAME");
                Double creditAmt = rs.getDouble("CREDIT_AMOUNT");
                System.out.println("ID: " + id + " NAME: " + name + " CONTACT_NAME: " + genre +
                        " CREDIT_AMOUNT:" + creditAmt);
                UUID uid = UUID.fromString(id);
                Advertiser movie = new Advertiser(uid, name, genre, creditAmt);
                ads.add(movie);
            }
            stmt.close();
            return ads;
        } catch (NumberFormatException ex) {
            throw new DaoDataException(ex);
        } catch (SQLException ex) {
            throw new DaoDataException(ex);
        } finally {
            close(stmt);
        }
    }

    public void close(Statement ps) {
        try {
            ps.close();
        } catch (SQLException ex) {

        }
    }
}
