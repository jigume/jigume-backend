package site.jigume.global.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        /**
         * 잘못된 요청
         */
        if (exception == null) {
            log.info("잘못된 요청");
            setResponse(response, AuthExceptionCode.BAD_REQUEST_EXCEPTION);
            return;
        }

        /**
         * 토큰 없는 경우
         */
        if (exception.equals(AuthExceptionCode.REQUEST_TOKEN_NOT_FOUND.getCode())) {
            log.info("AccessToken이 없음");
            setResponse(response, AuthExceptionCode.REQUEST_TOKEN_NOT_FOUND);
            return;
        }

        /**
         *  해당 멤버가 데이터베이스에 없을 경우
         */
        if (exception.equals(AuthExceptionCode.AUTH_MEMBER_NOT_FOUND.getCode())) {
            setResponse(response, AuthExceptionCode.AUTH_MEMBER_NOT_FOUND);
            return;
        }

        /**
         * 토큰 만료된 경우
         */
        if (exception.equals(AuthExceptionCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, AuthExceptionCode.EXPIRED_TOKEN);
            return;
        }

        /**
         * 유효하지 않은 토큰일 경우
         */
        if (exception.equals(AuthExceptionCode.INVALID_TOKEN.getCode())) {
            setResponse(response, AuthExceptionCode.INVALID_TOKEN);
        }
    }

    private void setResponse(HttpServletResponse response, AuthExceptionCode errorCode) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().println(
                "{ " +
                        "\"statusCode\" : \"" + errorCode.getHttpStatus()
                        + "\", \"code\" : \"" + errorCode.getCode()
                        + "\", \"message\" : \"" + errorCode.getMessage()
                        + "\", \"timestamp\" : \"" + timestamp + "\""
                        + "}");
    }
}
