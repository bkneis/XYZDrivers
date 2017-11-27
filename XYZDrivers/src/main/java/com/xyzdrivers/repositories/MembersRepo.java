package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Member;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MembersRepo extends Repo<Member, String> {

    /**
     * Retrieve ALL data from MEMBERS table
     *
     * @return List of Members objects
     */
    @Override
    public List<Member> get() throws RepositoryException {
        List<Object[]> results;
        List<Member> members = new ArrayList();

        try {
            //get data
            results = this.sqlService.retrieve("MEMBERS");
            //parse members data
            for (Object[] memberData : results) {
                Member member = new Member(memberData[0].toString(), //id
                        memberData[1].toString(), //name
                        memberData[2].toString(), //address
                        LocalDate.parse(memberData[3].toString()), //dob
                        LocalDate.parse(memberData[4].toString()), //dor
                        memberData[5].toString(), //status
                        (double) memberData[6]);                     //balance
                members.add(member);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to retrieve members. See inner exception for details.", ex);
        }

        return members;
    }

    @Override
    public Member get(String id) throws RepositoryException {
        List<Member> members = getWhere("id", id);

        return members.get(0);
    }

    @Override
    public List<Member> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        List<Object[]> results;
        List<Member> members = new ArrayList();

        try {
            // get data
            results = this.sqlService.retrieve("MEMBERS", "*", keyColumn, keyValue);
            //parse members data
            for (Object[] memberData : results) {
                Member member = new Member(memberData[0].toString(), //id
                        memberData[1].toString(), //name
                        memberData[2].toString(), //address
                        LocalDate.parse(memberData[3].toString()), //dob
                        LocalDate.parse(memberData[4].toString()), //dor
                        memberData[5].toString(), //status
                        (double) memberData[6]);                     //balance
                members.add(member);
            }
        } catch (SQLException | IllegalArgumentException ex) {
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
