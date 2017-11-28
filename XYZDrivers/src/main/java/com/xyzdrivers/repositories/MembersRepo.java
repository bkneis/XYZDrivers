package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 *
 * @author bryan, alex
 */
@RequestScoped
public class MembersRepo extends Repo<Member, String> {

    @Override
    public Member get(String id) throws RepositoryException {
        Member member = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Object[] result = this.sqlService.retrieve(Member.TABLE_NAME, Member.PRIMARY_KEY, id);
            Calendar dob = Calendar.getInstance();
            Calendar dor = Calendar.getInstance();

            String dateTime = df.format(result[3]);
            String dateTime2 = df.format(result[4]);

            dob.setTime(df.parse(dateTime));
            dor.setTime(df.parse(dateTime2));
            member = new Member(result[0].toString(), result[1].toString(), result[2].toString(), dob, dor, result[5].toString(), Float.parseFloat(result[6].toString()));
        } catch (IllegalArgumentException | SQLException ex) {
            throw new RepositoryException("Failed to retrieve data", ex);
        } catch (ParseException ex) {
            Logger.getLogger(MembersRepo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return member;
    }

    /**
     * Retrieve ALL data from MEMBERS table
     *
     * @return List of Members objects
     * @throws com.xyzdrivers.repositories.RepositoryException
     */
    @Override
    public List<Member> get() throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Object[]> results;

        List<Member> members = new ArrayList();

        try {
            //get data
            results = this.sqlService.retrieve("MEMBERS");
            //parse members data
            for (Object[] memberData : results) {
                Calendar dob = Calendar.getInstance();
                Calendar dor = Calendar.getInstance();

                String dateTime = df.format(memberData[3]);
                String dateTime2 = df.format(memberData[4]);

                dob.setTime(df.parse(dateTime));
                dor.setTime(df.parse(dateTime2));

                Member member = new Member(memberData[0].toString(), //id
                        memberData[1].toString(), //name
                        memberData[2].toString(), //address
                        dob, //dob
                        dor, //dor
                        memberData[5].toString(), //status
                        (double) memberData[6]);  //balance
                members.add(member);
            }
        } catch (SQLException | ParseException ex) {
            throw new RepositoryException("Failed to retrieve members. See inner exception for details.", ex);
        }

        return members;
    }

    public List<Member> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Object[]> results;
        List<Member> members = new ArrayList();

        try {
            // get data
            results = this.sqlService.retrieve("MEMBERS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] memberData : results) {
                Calendar dob = Calendar.getInstance();
                Calendar dor = Calendar.getInstance();

                String dateTime = df.format(memberData[3]);
                String dateTime2 = df.format(memberData[4]);

                dob.setTime(df.parse(dateTime));
                dor.setTime(df.parse(dateTime2));

                Member member = new Member(memberData[0].toString(), //id
                        memberData[1].toString(), //name
                        memberData[2].toString(), //address
                        dob, //dob
                        dor, //dor
                        memberData[5].toString(), //status
                        (double) memberData[6]);  //balance
                members.add(member);
            }
        } catch (SQLException | ParseException ex) {
            throw new RepositoryException("Failed to retrieve matching Members. See inner exception for details.", ex);
        }

        return members;
    }

    @Override
    public Member update(Member membership) throws RepositoryException {

        Object[] parameters = {
            membership.getName(),
            membership.getAddress(),
            membership.getDob().toString(),
            membership.getStatus(),
            membership.getBalance(),
            membership.getId()
        };

        String updateQuery = "UPDATE members SET \"name\"=?, \"address\"=?, \"dob\"=?, \"status\"=?, \"balance\"=? WHERE \"id\"=?";

        try {
            this.sqlService.executeUpdateStatement(updateQuery, parameters);
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to retrieve data", ex);
        }

        return membership;
    }

    public void update(String username) throws RepositoryException {
                if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("The string used to identify a user is null or empty: " + username);
        }

         String updateQuery = "UPDATE MEMBERS SET STATUS = ? WHERE ID = ?";
         
         try {
             this.sqlService.executeUpdateStatement(updateQuery, username);
         }
         catch(SQLException ex) {
             throw new RepositoryException("Failed to update data.", ex);
         }
                
    }

    @Override
    public void delete(Member model) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Member model) throws RepositoryException {
        try {
            String columns = "(\"id\", \"name\", \"address\", \"dob\", \"dor\", \"status\", \"balance\")";
            sqlService.insert(Member.TABLE_NAME, columns,  new Object[]{
                model.getId(),
                model.getName(),
                model.getAddress(),
                model.getDob(),
                model.getDor(),
                model.getStatus(),
                model.getBalance()
            });
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to insert member. See inner exception for details.", ex);
        }
    }

}
