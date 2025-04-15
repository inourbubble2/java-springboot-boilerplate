package com.boilerplate.javaspringbootboilerplate.presentation.member;

import com.boilerplate.javaspringbootboilerplate.infrastructure.database.entity.Member;

public record MemberResponse(
    String username
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getUsername());
    }
}
