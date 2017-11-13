package com.xyzdrivers.repositories;

import com.xyzdrivers.models.MembershipPayment;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author bryan
 */
@RequestScoped
public class PaymentRepo extends Repo<MembershipPayment, Integer> {

    @Override
    MembershipPayment get(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MembershipPayment> get() {
        List<Object[]> results;
        List<MembershipPayment> payments = new ArrayList<>();
        try {
            results = this.sqlService.retrieve(MembershipPayment.TABLE_NAME);
            for (Object[] result : results) {
                LocalDate date = LocalDate.parse(result[4].toString());
                LocalTime time = LocalTime.parse(result[5].toString());
                MembershipPayment payment = new MembershipPayment(
                        Integer.parseInt(result[0].toString()),
                        result[1].toString(),
                        result[2].toString(),
                        Float.parseFloat(result[3].toString()),
                        date,
                        time
                );
                payments.add(payment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payments;
    }

    @Override
    List<MembershipPayment> getWhere(String[] conditions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    List<MembershipPayment> getWhere(String keyColumn, Object keyValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    MembershipPayment update(MembershipPayment model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void delete(MembershipPayment model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
