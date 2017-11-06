package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Claim;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO for claims model
 * 
 * @author arthur
 */
public class ClaimsRepo extends Repo<Claim> {

    @Override
    Claim get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Retrieve all claims
     * 
     * @return ArrayList<Claim> All claims
     */
    @Override
    public List<Claim> get() {
        List<Object[]> results;
        List<Claim> claims = new ArrayList<>();
        try {
            results = this.sql.retrieve("claims");
            for (Object[] result : results) {
                LocalDate date = LocalDate.parse(result[2].toString());
                Claim cl = new Claim(result[1].toString(), date, result[3].toString(), result[4].toString(), Float.parseFloat(result[5].toString()));
                claims.add(cl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return claims;
    }

    @Override
    public List<Claim> getWhere(String[] conditions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Claim update(Claim model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Claim model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    List<Claim> getWhere(String keyColumn, Object keyValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
