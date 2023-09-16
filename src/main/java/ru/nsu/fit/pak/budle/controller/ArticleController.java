package ru.nsu.fit.pak.budle.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.nsu.fit.pak.budle.exceptions.*;
import ru.nsu.fit.pak.budle.utils.BaseResponse;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

@RestControllerAdvice(basePackages = "ru.nsu.fit.pak.budle.controller")
public class ArticleController extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {
    private static final String NOT_VALID_EXCEPTION = "notValidException";

    @ExceptionHandler({
        EstablishmentAlreadyExistsException.class,
        EstablishmentNotFoundException.class,
        ImageLoadingException.class,
        ImageSavingException.class,
        IncorrectCategoryException.class,
        IncorrectCuisineCountryException.class,
        IncorrectDayOfWeekException.class,
        IncorrectEstablishmentType.class,
        IncorrectPhoneNumberFormatException.class,
        IncorrectTagException.class,
        InvalidBookingTime.class,
        NotEnoughRightsException.class,
        OrderNotFoundException.class,
        SpotNotFoundException.class,
        UserAlreadyExistsException.class,
        UserNotFoundException.class,
        VerificationCodeWasFalseException.class,
        WorkerNotFoundException.class,
        IncorrectOrderStatusException.class,
        ErrorWhileParsingEstablishmentMapException.class,
        EstablishmentMapDoesntExistException.class,
        UserNotLoggedInException.class
    })
    public <T extends BaseException> ResponseEntity<BaseResponse<Object>> handleException(T e) {
        BaseResponse<Object> response = new BaseResponse<>(e.getMessage(), e.getType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public boolean supports(
        MethodParameter returnType,
        @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return !returnType.hasMethodAnnotation(ApiIgnore.class);
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        @NonNull MethodParameter returnType,
        @NonNull MediaType selectedContentType,
        @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
        @NonNull ServerHttpRequest request,
        @NonNull ServerHttpResponse response
    ) {
        if (body instanceof BaseResponse<?> || body instanceof LinkedHashMap<?, ?>) {
            return body;
        } else if (selectedContentType.toString().equals(MediaType.APPLICATION_XML_VALUE)) {
            return body;
        }
        return new BaseResponse<>(body);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        @NonNull MethodArgumentNotValidException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatus status,
        @NonNull WebRequest request
    ) {
        String message = getDefaultMessage(ex);
        BaseResponse<Object> response = new BaseResponse<>(message, NOT_VALID_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatus status,
        @NonNull WebRequest request
    ) {
        String message = ex.getMessage();
        BaseResponse<Object> response = new BaseResponse<>(message, NOT_VALID_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleBindException(
        @NonNull BindException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatus status,
        @NonNull WebRequest request
    ) {
        String message = getDefaultMessage(ex);
        BaseResponse<Object> response = new BaseResponse<>(message, NOT_VALID_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @NonNull
    private String getDefaultMessage(BindException ex) {
        return ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList()
            .get(0);

    }
}
