package com.xyzdrivers.services;

import com.xyzdrivers.models.Member;
import com.xyzdrivers.models.MembershipPayment;
import com.xyzdrivers.repositories.MembersRepo;
import com.xyzdrivers.repositories.PaymentsRepo;
import com.xyzdrivers.repositories.RepositoryException;
import java.lang.reflect.Field;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/*
 * @author Joe Dicker
 */
@RequestScoped
public class InsertPaymentService {
    
    @Inject
    private MembersRepo membersRepo;
    
    @Inject
    private PaymentsRepo paymentsRepo;
    
    @Inject
    private SQLService sqlService;

    public void InsertPayment(MembershipPayment mp) throws IllegalAccessException, RepositoryException {

        if (mp == null) {
            throw new IllegalArgumentException("The object mp is null.");
        }

        for (Field f : mp.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(mp);
            if (value == null) {
                throw new IllegalArgumentException("One of the declared fields in object mp is null.");
            }
        }
        
        paymentsRepo.insert(mp);
        
        Member member = membersRepo.get(mp.getMemberID());
        member.setBalance(member.getBalance() - mp.getPaymentAmount());
        membersRepo.update(member);
        
    }

}
