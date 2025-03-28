package com.herysson.userphoneapi.service;

import com.herysson.userphoneapi.model.Phone;
import com.herysson.userphoneapi.model.User;
import com.herysson.userphoneapi.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public List<Phone> save(List<Phone> phones) {



        return phoneRepository.saveAll(phones);
    }
}
