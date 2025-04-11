package com.herysson.userphoneapi.service;

import com.herysson.userphoneapi.model.Phone;
import com.herysson.userphoneapi.model.User;
import com.herysson.userphoneapi.repository.PhoneRepository;
import com.herysson.userphoneapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public Optional<Phone> findById(Long id) {
        return phoneRepository.findById(id);
    }

    public Phone createPhone(Long userId, String number) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (user.getPhones().size() >= 3) {
            throw new IllegalArgumentException("Usuário já possui o número máximo de telefones.");
        }

        Phone phone = new Phone();
        phone.setNumber(number);
        phone.setUser(user);
        return phoneRepository.save(phone);
    }

    public Phone updatePhone(Long id, String number) {
        Phone existingPhone = phoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado"));

        existingPhone.setNumber(number);
        return phoneRepository.save(existingPhone);
    }

    public void deletePhone(Long id) {
        if (!phoneRepository.existsById(id)) {
            throw new IllegalArgumentException("Telefone não encontrado");
        }
        phoneRepository.deleteById(id);
    }
}
