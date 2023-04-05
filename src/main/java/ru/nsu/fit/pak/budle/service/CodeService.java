package ru.nsu.fit.pak.budle.service;

/**
 * Service, that responsible for verification codes.
 */
public interface CodeService {
    /**
     * Method, that checking existing of the code in our database.
     *
     * @param phoneNumber - with what phone number must be associated code.
     * @param code        - code, that user provided as his code.
     * @return true - if data is valid, false - otherwise.
     */
    boolean checkCode(String phoneNumber, String code);


    /**
     * Method, that generated code by user request and put it into database.
     * Also, verified phone number in API service.
     *
     * @param phoneNumber - for what number we need to generate code.
     * @return true - if was success, false - otherwise.
     */

    boolean generateCode(String phoneNumber);
}
