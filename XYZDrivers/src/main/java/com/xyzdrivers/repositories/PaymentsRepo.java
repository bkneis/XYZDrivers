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

@RequestScoped
public class PaymentsRepo extends Repo<MembershipPayment, String> {

    /**
     * Retrieve ALL data from MEMBERS table
     *
     * @return List of MembershipPayments objects
     */
    @Override
    public List<MembershipPayment> get() throws RepositoryException {
        List<Object[]> results;
        List<MembershipPayment> payments = new ArrayList();

        try {
            //get data
            results = this.sqlService.retrieve("MEMBERS");
            //parse members data
            for (Object[] memberData : results) {
                MembershipPayment payment = new MembershipPayment(memberData[0].toString(), // mem_id
                        memberData[1].toString(), // Type of payment
                        (float) memberData[2],
                        LocalDate.parse(memberData[3].toString()), // date
                        LocalTime.parse(memberData[4].toString())); // time
                payments.add(payment);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to retrieve payments. See inner exception for details.", ex);
        }

        return payments;
    }

    @Override
    public MembershipPayment get(String id) throws RepositoryException {
        List<MembershipPayment> members = getWhere("id", id);

        return members.get(0);
    }

    @Override
    public List<MembershipPayment> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        List<Object[]> results;
        List<MembershipPayment> payments = new ArrayList();

        try {
            // get data
            results = this.sqlService.retrieve("MEMBERS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] memberData : results) {
                MembershipPayment payment = new MembershipPayment(memberData[0].toString(), // mem_id
                        memberData[1].toString(), // Type of payment
                        (float) memberData[2],
                        LocalDate.parse(memberData[3].toString()), // date
                        LocalTime.parse(memberData[4].toString())); // time
                payments.add(payment);
            }
        } catch (SQLException | IllegalArgumentException ex) {
            throw new RepositoryException("Failed to retrieve matching payments. See inner exception for details.", ex);
        }

        return payments;
    }

    @Override
    public void update(MembershipPayment model) {
        throw new UnsupportedOperationException("This method is not required.");
    }

    @Override
    public void delete(MembershipPayment model) {
        throw new UnsupportedOperationException("This method is not required.");
    }

    @Override
    public void insert(MembershipPayment model) throws RepositoryException {
        try {
            sqlService.insert("payments", new Object [] {
                model.getMemberID(),
                model.getPaymentType(),
                model.getPaymentAmount(),
                model.getDate(),
                model.getTime()
            });
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to insert payment. See inner exception for details.", ex);
        }
    }
}
