package ru.nsu.fit.pak.Budle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    Boolean success;
    T result;
    Exception exception;
}
