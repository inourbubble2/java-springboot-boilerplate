package com.boilerplate.javaspringbootboilerplate.presentation.member;

import com.boilerplate.javaspringbootboilerplate.application.member.MemberService;
import com.boilerplate.javaspringbootboilerplate.infrastructure.database.entity.Member;
import com.boilerplate.javaspringbootboilerplate.presentation.auth.AuthenticatedMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public MemberResponse register(
        @RequestBody @Valid RegisterMemberRequest request
    ) {
        val member = memberService.register(request.username(), request.password());
        return MemberResponse.from(member);
    }

    @PatchMapping
    public MemberResponse update(
        @AuthenticatedMember Member member,
        @RequestBody @Valid UpdateMemberRequest request
    ) {
        val updated = memberService.update(member, request.password());
        return MemberResponse.from(updated);
    }

    @DeleteMapping
    public void deregister(
        @AuthenticatedMember Member member
    ) {
        memberService.deregister(member);
    }
}
