package com.boilerplate.javaspringbootboilerplate.application.member;

import com.boilerplate.javaspringbootboilerplate.application.exception.BoilerplateException;
import com.boilerplate.javaspringbootboilerplate.application.exception.ErrorCode;
import com.boilerplate.javaspringbootboilerplate.infrastructure.database.entity.Member;
import com.boilerplate.javaspringbootboilerplate.infrastructure.database.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(String username, String password) {
        return memberRepository.findByUsernameAndPassword(username, password)
            .orElseThrow(() -> new BoilerplateException(ErrorCode.ENTITY_NOT_FOUND));
    }

    public Member register(String username, String password) {
        memberRepository.findByUsername(username).ifPresent(member -> {
            throw new BoilerplateException(ErrorCode.DUPLICATED_ENTITY);
        });
        return memberRepository.save(new Member(username, password));
    }

    public Member update(Member member, String password) {
        member.update(password);
        return memberRepository.save(member);
    }

    public void deregister(Member member) {
        memberRepository.delete(member);
    }
}
