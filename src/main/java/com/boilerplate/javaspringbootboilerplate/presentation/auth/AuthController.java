package com.boilerplate.javaspringbootboilerplate.presentation.auth;

import com.boilerplate.javaspringbootboilerplate.application.auth.AuthService;
import com.boilerplate.javaspringbootboilerplate.application.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/token/issue")
    public TokenResponse login(
        @RequestBody @Valid IssueTokenRequest request
    ) {
        val member = memberService.getMember(request.username(), request.password());
        val tokens = authService.generateTokenBy(LocalDateTime.now(), member.getId());
        return new TokenResponse(tokens.getFirst(), tokens.getSecond());
    }

    @PostMapping("/token/reissue")
    public String reissue(
        @RequestParam("refreshToken") @NotBlank String refreshToken
    ) {
        return authService.reissue(LocalDateTime.now(), refreshToken);
    }
}
