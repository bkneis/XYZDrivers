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
public class ClaimsRepo extends Repo<Claim, Integer> {
    
    public ClaimsRepo() {}

    @Override
    public Claim get(Integer id) {
        Claim claim = null;
        try {
            Object[] result = sqlService.retrieve(Claim.TABLE_NAME, Claim.PRIMARY_KEY, id.toString());
            LocalDate date = LocalDate.parse(result[2].toString());
            claim = new Claim(
                    Integer.parseInt(result[0].toString()),
                    result[1].toString(),
                    date,
                    result[3].toString(),
                    result[4].toString(),
                    Float.parseFloat(result[5].toString())
            );
        } catch (IllegalArgumentException | SQLException ex) {
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
        List<Object[]> results;
        List<Claim> claims = new ArrayList<>();
        
        try {
            results = sqlService.retrieve("claims");
            for (Object[] result : results) {
                LocalDate date = LocalDate.parse(result[2].toString());
                Claim cl = new Claim(
                    Integer.parseInt(result[0].toString()),
                    result[1].toString(),
                    date,
                    result[3].toString(),
                    result[4].toString(),
                    Float.parseFloat(result[5].toString())
            );
                claims.add(cl);
            }
        } catch (SQLException ex) {
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
            this.sqlService.executeUpdateStatement("UPDATE claims SET \"date\"=?, \"amount\"=?, \"rationale\"=?, \"status\"=? WHERE \"id\"=?", parameters);
        } catch (SQLException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return claim;
    }

    @Override
    public void insert(Claim model) throws RepositoryException {
        throw new UnsupportedOperationException();
    }
        
    @Override
    public List<Claim> getWhere(String keyColumn, Object keyValue) {
        List<Object[]> results;
        List<Claim> claims = new ArrayList();
            
        try {
            // get data
            results = this.sqlService.retrieve("CLAIMS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] claimData : results)
            {
                Claim claim = new Claim(claimData[1].toString(),                    //mem_id
                                        LocalDate.parse(claimData[2].toString()),   //date
                                        claimData[3].toString(),                    //rational
                                        claimData[4].toString(),                    //status
                                        (double)claimData[5]);                       //amount
                claims.add(claim);
            }
        } catch (SQLException | IllegalArgumentException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return claims;
    }

    @Override
    void delete(Claim model) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
