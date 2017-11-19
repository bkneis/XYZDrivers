package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Claim;
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
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        List<Object[]> results;
        List<Claim> claims = new ArrayList<>();
        
        try {
            results = sqlService.retrieve("claims");
            for (Object[] result : results) {
                Calendar date = Calendar.getInstance();
                date.setTime(df.parse(result[2].toString()));
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
    public void insert(Claim model) throws RepositoryException {
        throw new UnsupportedOperationException();
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
            for (Object[] claimData : results)
            {
                Calendar date = Calendar.getInstance();
                date.setTime(df.parse(claimData[2].toString()));
                
                Claim claim = new Claim(claimData[1].toString(),                    //mem_id
                                                           date,                    //date
                                        claimData[3].toString(),                    //rational
                                        claimData[4].toString(),                    //status
                                        (double)claimData[5]);                       //amount
                claims.add(claim);
            }
        } catch (SQLException | IllegalArgumentException | ParseException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return claims;
    }
    
    
}
