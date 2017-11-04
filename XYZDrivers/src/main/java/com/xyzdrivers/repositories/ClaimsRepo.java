package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Claim;
import com.xyzdrivers.services.SQLService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO for claims model
 * @author arthur
 */
public class ClaimsRepo {
    
    private SQLService sql;
    
    public ClaimsRepo(SQLService sql) {
        this.sql = sql;
    }
    
    /**
     * Method to retrieve all of the claims in the database
     * @return List<Claim> claims
     */
    public List<Claim> getAll() {
        List<Object[]> results;
        List<Claim> claims = new ArrayList<>();
        try {
            results = this.sql.retrieve("claims");
            for (Object[] result : results) {
                // TODO change LocalDate
                Claim cl = new Claim(result[1].toString(), LocalDate.now(), result[3].toString(), result[4].toString(), Float.parseFloat(result[5].toString()));
                claims.add(cl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return claims;
    }
    
}
