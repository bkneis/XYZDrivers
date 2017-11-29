package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Claim;
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
        public ClaimsRepo() {}
    @Override
    public Claim get(Integer id) throws RepositoryException {
        Claim claim = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Object[] result = sqlService.retrieve(Claim.TABLE_NAME, Claim.PRIMARY_KEY, id.toString());
            Calendar date = Calendar.getInstance();
            String dateTime = df.format(result[3]);
            date.setTime(df.parse(result[2].toString()));
            claim = new Claim(
                    result[1].toString(),
                    date,
                    result[3].toString(),
                    result[4].toString(),
                    (double) result[5]
            );
        } catch (IllegalArgumentException | SQLException ex) {
            throw new RepositoryException("Failed to retrieve data", ex);
        }   catch (ParseException ex) {
                Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
            }

        return claim;
    }

    /**
     * Retrieve all claims
     *
     * @return ArrayList<Claim> All claims
     * @throws com.xyzdrivers.repositories.RepositoryException
     */
    @Override
    public List<Claim> get() throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Object[]> results;
        List<Claim> claims = new ArrayList<>();

        try {
            results = sqlService.retrieve("CLAIMS");
            for (Object[] result : results) {
                Calendar date = Calendar.getInstance();
                String tempDateTime = df.format(result[2]);
                date.setTime(df.parse(tempDateTime));
                Claim cl = new Claim(
                    result[1].toString(),
                    date,
                    result[3].toString(),
                    result[4].toString(),
                    Float.parseFloat(result[5].toString())
            );
                claims.add(cl);
            }
        } catch (SQLException | ParseException ex) {
            throw new RepositoryException("Retrieval failed.", ex);
        }

        return claims;
    }

    @Override
    public Claim update(Claim claim) throws RepositoryException {

        Object[] parameters = {
            claim.getDate().toString(),
            claim.getAmount(),
            claim.getReason(),
            claim.getStatus(),
            claim.getId()
        };

        try {
            this.sqlService.executeUpdateStatement("UPDATE claims SET DATE=?, AMOUNT=?, RATIONALE=?, STATUS=? WHERE ID=?", parameters);
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to retrieve data", ex);
        }

        return claim;
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
    public List<Claim> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
        } catch (SQLException | ParseException ex) {
           throw new RepositoryException("Retrieval failed.", ex);
        }

        return claims;
    }
    
    @Override
    void delete(Claim model) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
