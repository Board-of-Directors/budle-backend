package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Code;
import ru.nsu.fit.pak.budle.dao.CodeType;
import ru.nsu.fit.pak.budle.exceptions.IncorrectPhoneNumberFormatException;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.VerificationCodeWasFalseException;
import ru.nsu.fit.pak.budle.repository.CodeRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.RequestSender;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;

    private final RequestSender requestSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public boolean checkCode(String phoneNumber, String code) {
        logger.info("Checking code");
        if (codeRepository.existsByPhoneNumberAndCode(phoneNumber, code)) {
            logger.debug("Checking code was true");
            return true;
        } else {
            throw new VerificationCodeWasFalseException();
        }
    }

    @Override
    public boolean generateCode(String phoneNumber) {
        logger.info("Generating code");
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            logger.debug("User with number " + phoneNumber + " already exists");
            throw new UserAlreadyExistsException();
        } else {
            Map<String, Object> response = requestSender.sendUCaller(phoneNumber);
            if (codeRequestWasFalse(response)) {
                logger.debug("Checking code for " + phoneNumber + " was false");
                throw new IncorrectPhoneNumberFormatException();
            } else {
                logger.info("Creating new instance of code");
                Code code = new Code();
                code.setCode((String) response.get("code"));
                code.setPhoneNumber(phoneNumber);
                code.setType(CodeType.registration);
                codeRepository.save(code);
                logger.debug("Code " + code + "was created successfully ");
                return true;
            }
        }
    }

    private boolean codeRequestWasFalse(Map<String, Object> response) {
        return response.get("status").equals(false);
    }

}
