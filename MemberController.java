package com.stradegi.book_management.controller;

import com.stradegi.book_management.model.dto.MemberDTO;
import com.stradegi.book_management.model.entity.SubscriptionPlan;
import com.stradegi.book_management.service.MemberService;
import com.stradegi.book_management.service.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemberController {

    private final MemberService memberService;
    private final SubscriptionPlanService subscriptionPlanService;

    @PostMapping("/register")
    public String registerMember(@RequestBody MemberDTO memberDTO) {
        memberService.registerMember(memberDTO);
        log.info("New member registered: {}", memberDTO.getEmail());
        return "Registration successful. Awaiting admin approval.";
    }


    @PostMapping("/approve/{memberId}/{subscriptionId}")
    public String approveMember(@PathVariable Long memberId, @PathVariable Long subscriptionId) {
        try {
            memberService.approveMember(memberId, subscriptionId);
            return "Member approved successfully!";
        } catch (Exception e) {
            log.error("Error approving member: {}", e.getMessage());
            return "Failed to approve member.";
        }
    }

    @GetMapping("/all")
    public List<MemberDTO> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return memberService.getAllMembers(page, size);
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionPlan> getAllSubscriptionPlans() {
        return subscriptionPlanService.getAllPlans();
    }

    @GetMapping("/status/{memberId}")
    public boolean isMemberActive(@PathVariable Long memberId) {
        return memberService.isMemberActive(memberId);
    }

    @GetMapping("/pending")
    public List<MemberDTO> getPendingMembers() {
        return memberService.getPendingMembers();
    }

    @PutMapping("/update/{memberId}")
    public String updateMember(@PathVariable Long memberId, @RequestBody MemberDTO memberDTO) {
        memberService.updateMember(memberId, memberDTO);
        return "Member updated successfully!";
    }

    @DeleteMapping("/delete/{memberId}")
    public String deleteMember(@PathVariable Long memberId) {
        log.info("Attempting to delete member with ID: {}", memberId);
        memberService.deleteMember(memberId);
        log.info("Member with ID: {} deleted successfully.", memberId);
        return "Member deleted successfully.";
    }
}
