package com.herysson.userphoneapi.service;

import com.herysson.userphoneapi.model.Phone;
import com.herysson.userphoneapi.model.User;
import com.herysson.userphoneapi.repository.PhoneRepository;
import com.herysson.userphoneapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Cliente com esse e-mail já existe.");
        }

        if(user.getPhones().size()>=3){
            throw new IllegalArgumentException("Usuario ja possui o numero maximo de telefones.");
        }

        for (Phone phone : user.getPhones()) { //for percorrendo a lista dos telefones adicionados
            String number = phone.getNumber();
            if (number.length() >= 2) {
                String dddStr = number.substring(0, 2);
                int ddd = Integer.parseInt(dddStr);

                if (ddd < 11 || ddd > 99) {
                    throw new IllegalArgumentException("DDD inválido. O DDD deve estar entre 11 e 99.");
                }
            } else {
                throw new IllegalArgumentException("Número de telefone inválido. O número deve ter pelo menos 2 dígitos.");
            }
        }

        return userRepository.save(user);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        if(!existsById(id)){
            throw new IllegalArgumentException("User com ID " + id + " não encontrado");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User com ID " + id + " não encontrado"));


        if(user.getPhones().size()>=3){
            throw new IllegalArgumentException("Usuario ja possui o numero maximo de telefones.");
        }

        for (Phone phone : user.getPhones()) { //for percorrendo a lista dos telefones adicionados
            String number = phone.getNumber();
            if (number.length() >= 2) {
                String dddStr = number.substring(0, 2);
                int ddd = Integer.parseInt(dddStr);

                if (ddd < 11 || ddd > 99) {
                    throw new IllegalArgumentException("DDD inválido. O DDD deve estar entre 11 e 99.");
                }
            } else {
                throw new IllegalArgumentException("Número de telefone inválido. O número deve ter pelo menos 2 dígitos.");
            }
        }


        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);}
}
