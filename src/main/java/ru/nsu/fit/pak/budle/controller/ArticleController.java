package ru.nsu.fit.pak.budle.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.nsu.fit.pak.budle.BaseResponse;
import ru.nsu.fit.pak.budle.exceptions.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

@RestControllerAdvice(basePackages = "ru.nsu.fit.pak.budle.controller")
public class ArticleController extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {


    @ExceptionHandler({UserAlreadyExistsException.class,
            IncorrectDataException.class,
            WorkerNotFoundException.class,
            EstablishmentAlreadyExistsException.class,
            NotEnoughRightsException.class,
            OrderNotFoundException.class,
            EstablishmentNotFoundException.class,
            UserNotFoundException.class})
    public <T extends BaseException> ResponseEntity<BaseResponse<Object>> handleException(T e) {
        BaseResponse<Object> response = new BaseResponse<>(e.getMessage(), e.getType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(ApiIgnore.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResponse<?> || body instanceof LinkedHashMap<?, ?>) {
            return body;
        }
        return new BaseResponse<>(body);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList()
                .get(0);
        BaseResponse<Object> response = new BaseResponse<>(message, "notValidException");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
