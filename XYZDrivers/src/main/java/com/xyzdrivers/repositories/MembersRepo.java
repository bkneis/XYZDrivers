package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Member;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MembersRepo extends Repo<Member, Integer> {

    /**
     * Retrieve ALL data from MEMBERS table
     *
     * @return List of Members objects
     */
    @Override
    public List<Member> get() throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dob = Calendar.getInstance();
        Calendar dor = Calendar.getInstance();
        List<Object[]> results;
        List<Member> members = new ArrayList();

        try {
            //get data
            results = this.sqlService.retrieve("MEMBERS");
            //parse members data
            for (Object[] memberData : results) {
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

    @Override
    public Member get(Integer id) throws RepositoryException {
        List<Member> members = getWhere("id", id);

        return members.get(0);
    }

    @Override
    public List<Member> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dob = Calendar.getInstance();
        Calendar dor = Calendar.getInstance();
        List<Object[]> results;
        List<Member> members = new ArrayList();

        try {
            // get data
            results = this.sqlService.retrieve("MEMBERS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] memberData : results) {
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
        } catch (SQLException | IllegalArgumentException | ParseException ex) {
            throw new RepositoryException("Failed to retrieve matching Members. See inner exception for details.", ex);
        }

        return members;
    }

    @Override
    public void update(Member model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Member model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Member model) throws RepositoryException {
        throw new UnsupportedOperationException();
    }
}
