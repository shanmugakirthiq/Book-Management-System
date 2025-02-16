package com.stradegi.book_management.service;

import com.stradegi.book_management.model.dto.MemberDTO;
import com.stradegi.book_management.model.entity.Member;

import java.util.List;

public interface MemberService {
    void registerMember(MemberDTO memberDTO);
    void approveMember(Long memberId, Long subscriptionId);
    boolean isMemberActive(Long memberId);
    List<MemberDTO> getAllMembers(int page, int size);
    List<MemberDTO> getPendingMembers();
    void updateMember(Long memberId, MemberDTO memberDTO);
    void deleteMember(Long memberId);
    Long getMemberIdByEmail(String email);
    boolean validateMemberCredentials(String email, String password);
    boolean isFirstLogin(String email);
    void changePassword(Long memberId, String newPassword);
}
