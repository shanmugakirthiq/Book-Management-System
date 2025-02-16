package com.stradegi.book_management.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long memberId;
    private String name;
    private String email;
    private String mobileNumber;
    private String gender;
    private boolean isActive;
    private double walletBalance;
    private Long subscriptionId;
}
