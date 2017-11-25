package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Claim;
import java.lang.reflect.Field;
import java.sql.Date;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 * DAO for claims model
 *
 * @author arthur
 */
@RequestScoped
public class ClaimsRepo extends Repo<Claim, Integer> {

    @Override
    public Claim get(Integer id) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Retrieve all claims
     *
     * @return ArrayList<Claim> All claims
     * @throws com.xyzdrivers.repositories.RepositoryException
     */
    @Override
    public List<Claim> get() throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object[]> results;
        List<Claim> claims = new ArrayList<>();
        Calendar date = Calendar.getInstance();

        try {
            results = sqlService.retrieve("claims");
            for (Object[] result : results) {              
                String dateTime = df.format(result[2]);
                date.setTime(df.parse(dateTime));
                Claim cl = new Claim(result[1].toString(), date, result[3].toString(), result[4].toString(), Float.parseFloat(result[5].toString()));
                claims.add(cl);
            }
        } catch (SQLException | ParseException ex) {
            throw new RepositoryException("Retrieval failed.", ex);
        }

        return claims;
    }

    @Override
    public void update(Claim model) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Claim model) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Claim c) throws RepositoryException {
        Object[] splitClaim = new Object[5];

        try {
            if (c == null) {
                throw new IllegalArgumentException("The object c is null.");
            }
            for (Field field : c.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(c);
                if (value == null) {
                    throw new IllegalArgumentException("One of the declared fields in object c is null.");
                }
            }
                                      
            splitClaim[0] = c.getMemberID();
            splitClaim[1] = new Date(c.getDate().getTimeInMillis());
            splitClaim[2] = c.getReason();
            splitClaim[3] = c.getStatus();
            splitClaim[4] = c.getAmount();
            
            String columns = "(MEM_ID, DATE, RATIONALE, STATUS, AMOUNT)";
        

            this.sqlService.insert("CLAIMS", columns, splitClaim);

        } catch (SQLException | IllegalAccessException ex) {
            throw new RepositoryException("Insert failed.", ex);
        }

    }

    @Override
    public List<Claim> getWhere(String keyColumn, Object keyValue) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        List<Object[]> results;
        List<Claim> claims = new ArrayList();

        try {
            // get data
            results = this.sqlService.retrieve("CLAIMS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] claimData : results) {
                Calendar date = Calendar.getInstance();
                date.setTime(df.parse(claimData[2].toString()));

                Claim claim = new Claim(claimData[1].toString(), //mem_id
                        date, //date
                        claimData[3].toString(), //rational
                        claimData[4].toString(), //status
                        (double) claimData[5]);                       //amount
                claims.add(claim);
            }
        } catch (SQLException | IllegalArgumentException | ParseException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return claims;
    }

}
