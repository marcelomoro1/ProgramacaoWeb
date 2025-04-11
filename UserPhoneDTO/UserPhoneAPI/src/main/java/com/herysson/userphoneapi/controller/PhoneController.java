package com.herysson.userphoneapi.controller;

import com.herysson.userphoneapi.dto.PhoneRequestDTO;
import com.herysson.userphoneapi.dto.PhoneResponseDTO;
import com.herysson.userphoneapi.mapper.PhoneMapper;
import com.herysson.userphoneapi.model.Phone;
import com.herysson.userphoneapi.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST responsável por gerenciar as operações relacionadas a telefones
 * Expõe endpoints para CRUD de telefones
 */
@RestController
@RequestMapping("/phones")
public class PhoneController {

    // Injeção de dependência do serviço de telefones
    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }


    @GetMapping
    public List<PhoneResponseDTO> getAllPhones() {
        return phoneService.findAll().stream()
                .map(PhoneMapper::toDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PhoneResponseDTO> getPhoneById(@PathVariable Long id) {
        return phoneService.findById(id)
                .map(PhoneMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/user/{userId}")
    public ResponseEntity<PhoneResponseDTO> createPhone(
            @PathVariable Long userId,
            @Valid @RequestBody PhoneRequestDTO phoneDTO) {
        try {
            Phone phone = phoneService.createPhone(userId, phoneDTO.getNumber());
            return ResponseEntity.ok(PhoneMapper.toDTO(phone));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PhoneResponseDTO> updatePhone(
            @PathVariable Long id,
            @Valid @RequestBody PhoneRequestDTO phoneDTO) {
        try {
            Phone phone = phoneService.updatePhone(id, phoneDTO.getNumber());
            return ResponseEntity.ok(PhoneMapper.toDTO(phone));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        try {
            phoneService.deletePhone(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 