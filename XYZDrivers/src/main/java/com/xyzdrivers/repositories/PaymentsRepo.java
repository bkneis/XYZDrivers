package com.xyzdrivers.repositories;

import com.xyzdrivers.models.MembershipPayment;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        List<Object[]> results;
        List<MembershipPayment> payments = new ArrayList();

        try {
            //get data
            results = this.sqlService.retrieve("PAYMENTS");
            //parse members data
            for (Object[] memberData : results) {
                Calendar date = Calendar.getInstance();
                Calendar time = Calendar.getInstance();
                date.setTime(dateFormat.parse(memberData[4].toString()));
                //time.setTime(timeFormat.parse(memberData[4].toString()));

                MembershipPayment payment = new MembershipPayment(memberData[0].toString(), // mem_id
                        memberData[1].toString(), // Type of payment
                        Float.parseFloat(memberData[3].toString()),
                        date, // date
                        date); // time
                payments.add(payment);
            }
        } catch (SQLException | ParseException ex) {
            throw new RepositoryException("Failed to retrieve payments. See inner exception for details.", ex);
        }

        return payments;
    }

    @Override
    public MembershipPayment get(String id) throws RepositoryException {
        List<MembershipPayment> members = getWhere("ID", id);

        return members.get(0);
    }

    @Override
    public List<MembershipPayment> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        List<Object[]> results;
        List<MembershipPayment> payments = new ArrayList();

        try {
            // get data
            results = this.sqlService.retrieve("MEMBERS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] memberData : results) {
                Calendar date = Calendar.getInstance();
                Calendar time = Calendar.getInstance();
                date.setTime(dateFormat.parse(memberData[3].toString()));
                time.setTime(timeFormat.parse(memberData[4].toString()));

                MembershipPayment payment = new MembershipPayment(memberData[0].toString(), // mem_id
                        memberData[1].toString(), // Type of payment
                        (float) memberData[2],
                        date, // date
                        time); // time
                payments.add(payment);
            }
        } catch (SQLException | IllegalArgumentException | ParseException ex) {
            throw new RepositoryException("Failed to retrieve matching payments. See inner exception for details.", ex);
        }

        return payments;
    }

    @Override
    public MembershipPayment update(MembershipPayment model) {
        throw new UnsupportedOperationException("This method is not required.");
    }

    @Override
    public void delete(MembershipPayment model) {
        throw new UnsupportedOperationException("This method is not required.");
    }

    @Override
    public void insert(MembershipPayment model) throws RepositoryException {
        try {
            String columns = "(MEM_ID, TYPE_OF_PAYMENT, AMOUNT, DATE, TIME)";
            sqlService.insert("PAYMENTS", columns,  new Object[]{
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
