package tech.qdhxy.erp.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tech.qdhxy.erp.common.exceptions.BadRequestException;
import tech.qdhxy.erp.common.exceptions.BusinessException;
import tech.qdhxy.erp.common.utils.JsonUtil;
import tech.qdhxy.erp.common.vo.R;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "tech.qdhxy.erp.web.rest")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof String) {
            return JsonUtil.toJSON(R.success(body));
        }
        if (body instanceof R) {
            return body;
        }
        return R.success(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return handleConstraintViolationException(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations()
                .stream()
                .map(x -> {
                    PathImpl path = (PathImpl) x.getPropertyPath();
                    return "参数[" + path.getLeafNode().getName() + "]" + x.getMessage();
                })
                .collect(Collectors.joining(";"));
        log.error("ConstraintViolationException请求参数错误:{}", errorMsg, e);
        return R.error(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleConstraintViolationException(BindException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMsg = errors.stream()
                .map(x -> {
                    if (x instanceof FieldError) {
                        return "参数[" + ((FieldError) x).getField() + "]" + x.getDefaultMessage();
                    } else {
                        return x.getDefaultMessage();
                    }
                }).collect(Collectors.joining(";"));
        log.error("BindException请求参数错误:{}", errorMsg, e);
        return R.error(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException请求参数错误:{}", e.getMsg(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMsg());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleBusinessException(BusinessException e) {
        log.error("业务错误{}", e.getMsg(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMsg());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleInterbalError(Throwable e) {
        log.error("内部错误", e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统错误");
    }


}
