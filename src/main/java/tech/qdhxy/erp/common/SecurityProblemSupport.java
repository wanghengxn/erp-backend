package tech.qdhxy.erp.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tech.qdhxy.erp.common.utils.JsonUtil;
import tech.qdhxy.erp.common.vo.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class SecurityProblemSupport implements AuthenticationEntryPoint, AccessDeniedHandler {
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) {
        //log.error("", exception);
        this.sendResponse(response);
    }

    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException exception) {
        //log.error("", exception);
        this.sendResponse(response);
    }

    @SneakyThrows
    private void sendResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JsonUtil.toJSON(R.error(HttpStatus.UNAUTHORIZED.value(), "jwt auth failed")));
    }

}
