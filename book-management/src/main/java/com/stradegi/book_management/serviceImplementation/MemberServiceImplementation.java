package com.stradegi.book_management.serviceImplementation;

import com.stradegi.book_management.dao.MemberDAO;
import com.stradegi.book_management.dao.SubscriptionPlanDAO;
import com.stradegi.book_management.model.dto.MemberDTO;
import com.stradegi.book_management.model.entity.Member;
import com.stradegi.book_management.model.entity.SubscriptionPlan;
import com.stradegi.book_management.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class MemberServiceImplementation implements MemberService {

    private final MemberDAO memberDAO;
    private final SubscriptionPlanDAO subscriptionPlanDAO;
    private final ModelMapper modelMapper;

    public MemberServiceImplementation(MemberDAO memberDAO, SubscriptionPlanDAO subscriptionPlanDAO, ModelMapper modelMapper) {
        this.memberDAO = memberDAO;
        this.subscriptionPlanDAO = subscriptionPlanDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerMember(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        memberDAO.registerMember(member);
        log.info("Member {} registered successfully.", memberDTO.getEmail());
    }

    @Override
    public void approveMember(Long memberId, Long subscriptionId) {
        SubscriptionPlan plan = subscriptionPlanDAO.getPlanById(subscriptionId);
        double price = plan.getPrice();
        double commission = plan.getAdminCommission();
        int durationDays = plan.getDurationDays();

        memberDAO.approveMember(memberId, subscriptionId, price, commission, durationDays);
        log.info("Subscription approved for Member ID {}. Ends in {} days.", memberId, durationDays);
    }

    @Override
    public boolean isMemberActive(Long memberId) {
        return memberDAO.isMemberActive(memberId);
    }

    @Override
    public List<MemberDTO> getAllMembers(int page, int size) {
        return memberDAO.getAllMembers(page, size);
    }

    @Override
    public List<MemberDTO> getPendingMembers() {
        return memberDAO.getPendingMembers();
    }

    @Override
    public void deleteMember(Long memberId) {
        log.info("Deleting member with ID: {}", memberId);
        memberDAO.deleteMember(memberId);
    }

    @Override
    public void updateMember(Long memberId, MemberDTO memberDTO) {
        Member updatedMember = modelMapper.map(memberDTO, Member.class);
        updatedMember.setMemberId(memberId);
        memberDAO.updateMember(updatedMember);
        log.info("Updated member with ID: {}", memberId);
    }


    @Override
    public boolean validateMemberCredentials(String email, String password) {
        return memberDAO.checkCredentials(email, password);
    }

    @Override
    public boolean isFirstLogin(String email) {
        return memberDAO.isFirstLogin(email);
    }

    @Override
    public Long getMemberIdByEmail(String email) {
        return memberDAO.getMemberIdByEmail(email);
    }

    @Override
    public void changePassword(Long memberId, String newPassword) {
        memberDAO.updateMemberPassword(memberId, newPassword);
    }
}
