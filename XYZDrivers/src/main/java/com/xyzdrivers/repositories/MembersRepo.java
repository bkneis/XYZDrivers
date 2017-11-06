package com.xyzdrivers.repositories;

import com.xyzdrivers.models.Member;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersRepo extends Repo<Member> {

    @Override
    Member get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Retrieve ALL data from MEMBERS table
     * 
     * @return List of Members objects
     */
    @Override
    public List<Member> get()
    {
        List<Object[]> results;
        List<Member> claims = new ArrayList<>();
        
        try {
            //get data
            results = this.sql.retrieve("MEMBERS");
            //parse members data
            for (Object[] memberData : results)
            {
                Member member = new Member (memberData[0].toString(),                   //id
                                            memberData[1].toString(),                   //name
                                            memberData[2].toString(),                   //address
                                            LocalDate.parse(memberData[3].toString()),  //dob
                                            LocalDate.parse(memberData[4].toString()),  //dor
                                            memberData[5].toString(),                   //status
                                            (double)memberData[6]);                     //balance
            }
        } catch (SQLException ex) {
            //@TODO ?
        }
        
        return claims;
    }

    @Override
    List<Member> getWhere(String[] conditions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    Member update(Member model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void delete(Member model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
