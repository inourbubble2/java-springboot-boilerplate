package com.boilerplate.javaspringbootboilerplate.presentation.auth;

import com.boilerplate.javaspringbootboilerplate.application.auth.JwtUtil;
import com.boilerplate.javaspringbootboilerplate.application.exception.BoilerplateException;
import com.boilerplate.javaspringbootboilerplate.application.exception.ErrorCode;
import com.boilerplate.javaspringbootboilerplate.infrastructure.database.entity.Member;
import com.boilerplate.javaspringbootboilerplate.infrastructure.database.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class)
            && Member.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        setAuthentication(request);

        String memberId = request.getAttribute("memberId").toString();

        return memberRepository.findById(Long.parseLong(memberId))
            .orElseThrow(() -> new BoilerplateException(ErrorCode.UNAUTHORIZED));
    }

    private void setAuthentication(HttpServletRequest request) {
        String accessToken = jwtUtil.getAccessToken(request);
        try {
            String memberId = jwtUtil.getSubject(accessToken);
            request.setAttribute("memberId", Long.parseLong(memberId));
        } catch (RuntimeException e) {
            throw new BoilerplateException(ErrorCode.UNAUTHORIZED);
        }
    }
}
