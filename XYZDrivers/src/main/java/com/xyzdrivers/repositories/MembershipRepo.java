package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Membership;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class MembershipRepo extends Repo<Membership> {

    @Override
    Membership get(int id) {
        Membership member = null;
        try {
            Object result = this.sql.retrieve(Membership.TABLE_NAME, Membership.PRIMARY_KEY, id);
            LocalDate dob = LocalDate.now();
            LocalDate dor = LocalDate.now();
            member = new Membership("dsfsdfsd", "dsfdsf", dor, dob, "sfsdf", 0.11f);
            member = new Membership(result[1].toString(), result[2].toString(), dob, dor, result[5].toString(), Float.parseFloat(result[6].toString()));
        } catch (SQLException ex) {
            Logger.getLogger(MembershipRepo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MembershipRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return member;
    }

    @Override
    List<Membership> get() {
        List<Object[]> results;
        List<Membership> memberships = new ArrayList<>();
        try {
            results = this.sql.retrieve("members");
            for (Object[] result : results) {
                Membership mem = new Membership();
                memberships.add(mem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memberships;
    }

    @Override
    List<Membership> getWhere(String[] conditions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    Membership update(Membership membership) {
        
        int id = membership.getId();
        
        Object[] parameters = {
            membership.getName(),
            membership.getAddress(),
            membership.getDob(),
            membership.getStatus(),
            membership.getBalance(),
            id
        };
        
        try {
            this.sql.executeUpdateStatement("UPDATE members SET name=?, address=?, dob=?, status=?, balance=? WHERE id=?", parameters);
        } catch (SQLException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        membership = this.get(id);
        return membership;
    }

    @Override
    void delete(Membership model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
