package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Code;
import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;
import ru.nsu.fit.pak.budle.exceptions.IncorrectPhoneNumberException;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.repository.CodeRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.RequestSender;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;

    private final RequestSender requestSender;

    @Override
    public boolean checkCode(String phoneNumber, String code) {
        if (codeRepository.existsByPhoneNumberAndCode(phoneNumber, code)) {
            return true;
        } else {
            throw new IncorrectDataException();
        }
    }

    @Override
    public boolean generateCode(String phoneNumber) throws IOException {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException();
        } else {
            Map<String, Object> map = requestSender.sendUCaller(phoneNumber);
            if (map.get("status").equals(false)) {
                throw new IncorrectPhoneNumberException();
            } else {
                Code code = new Code();
                code.setCode((String) map.get("code"));
                code.setPhoneNumber(phoneNumber);
                codeRepository.save(code);
                return true;
            }
        }
    }

}
