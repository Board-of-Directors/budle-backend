package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.Exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.Budle.dao.Code;
import ru.nsu.fit.pak.Budle.repository.CodeRepository;
import ru.nsu.fit.pak.Budle.repository.UserRepository;
import ru.nsu.fit.pak.Budle.utils.RequestSender;

import java.io.IOException;
import java.util.Map;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    CodeRepository codeRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkCode(String phoneNumber, String code) {
        return codeRepository.existsByPhoneNumberAndCode(phoneNumber, code);
    }

    @Override
    public void generateCode(String phoneNumber) throws IOException {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException("User with such number already exists");
        } else {
            RequestSender sender = new RequestSender();
            Map<String, Object> map = sender.sendUCaller(phoneNumber);
            Code code = new Code();
            code.setCode((String) map.get("code"));
            code.setPhoneNumber(phoneNumber);
            codeRepository.save(code);
        }
    }

}
