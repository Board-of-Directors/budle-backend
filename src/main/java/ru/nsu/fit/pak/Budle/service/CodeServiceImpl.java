package ru.nsu.fit.pak.Budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.Exceptions.IncorrectDataException;
import ru.nsu.fit.pak.Budle.Exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.Budle.dao.Code;
import ru.nsu.fit.pak.Budle.repository.CodeRepository;
import ru.nsu.fit.pak.Budle.repository.UserRepository;
import ru.nsu.fit.pak.Budle.utils.RequestSender;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;

    @Override
    public boolean checkCode(String phoneNumber, String code) {
        boolean res = codeRepository.existsByPhoneNumberAndCode(phoneNumber, code);
        if (!res) {
            throw new IncorrectDataException("Код введен неверно.");
        } else {
            return true;
        }
    }

    @Override
    public boolean generateCode(String phoneNumber) throws IOException {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException("Пользователь с таким номером уже существует.");
        } else {
            RequestSender sender = new RequestSender();
            Map<String, Object> map = sender.sendUCaller(phoneNumber);
            if (map.get("status").equals(false)) {
                throw new IncorrectDataException((String) map.get("error"));
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
