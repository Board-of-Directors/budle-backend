package ru.nsu.fit.pak.Budle.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.nsu.fit.pak.Budle.BaseResponse;
import ru.nsu.fit.pak.Budle.Exceptions.IncorrectDataException;
import ru.nsu.fit.pak.Budle.Exceptions.BaseException;
import ru.nsu.fit.pak.Budle.Exceptions.UserAlreadyExistsException;

@ControllerAdvice
public class ArticleController implements ResponseBodyAdvice<Object> {
    @ExceptionHandler({UserAlreadyExistsException.class, IncorrectDataException.class})
    public <T extends BaseException> ResponseEntity<BaseResponse<Object>> handleException(T e) {
        BaseResponse<Object> response = new BaseResponse<>(e.getMessage(), e.getType());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResponse<?>) {
            return body;
        }
        return new BaseResponse<>(body);
    }
}
