package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Claim;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class ClaimsRepo extends Repo<Claim> {

    @Override
    Claim get(Integer id) {
        Claim claim = null;
        try {
            Object result = this.sql.retrieve(Claim.TABLE_NAME, Claim.PRIMARY_KEY, id);
            // TODO fix sqlService for retrieve of one object
            LocalDate date = LocalDate.parse(result[2].toString());
            Claim cl = new Claim(result[1].toString(), date, result[3].toString(), result[4].toString(), Float.parseFloat(result[5].toString()));
        } catch (SQLException | IllegalArgumentException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return claim;
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
            results = sqlService.retrieve("claims");
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
