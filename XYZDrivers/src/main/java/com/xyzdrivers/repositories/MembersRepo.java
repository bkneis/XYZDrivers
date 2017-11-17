package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Member;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author bryan, alex
 */
@RequestScoped
public class MembersRepo extends Repo<Member, String> {

    @Override
    public Member get(String id) {
        Member member = null;
        try {
            Object[] result = this.sqlService.retrieve(Member.TABLE_NAME, Member.PRIMARY_KEY, id);
            LocalDate dob = LocalDate.parse(result[3].toString());
            LocalDate dor = LocalDate.parse(result[4].toString());
            member = new Member(result[0].toString(), result[1].toString(), result[2].toString(), dob, dor, result[5].toString(), Float.parseFloat(result[6].toString()));
        } catch (IllegalArgumentException | SQLException ex) {
            Logger.getLogger(MembersRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return member;
    }

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

        return memberships;
    }

    @Override
    public List<Member> getWhere(String[] conditions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            Logger.getLogger(ClaimsRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return membership;
    }

    @Override
    public void delete(Member model) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Member model) throws RepositoryException {
        throw new UnsupportedOperationException();
    }

}
