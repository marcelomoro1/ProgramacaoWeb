package com.herysson.userphoneapi.dto;

import java.util.List;

/**
 * DTO (Data Transfer Object) para enviar os dados de resposta da API
 * Contém os dados formatados do usuário para exibição
 */
public class UserResponseDTO {
    // ID único do usuário no banco de dados
    private Long id;
    
    // Nome do usuário
    private String name;
    
    // Email do usuário
    private String email;
    
    // Lista de telefones do usuário no formato DTO
    private List<PhoneResponseDTO> phones;

    // Construtor padrão necessário para o Spring
    public UserResponseDTO() {
    }

    // Construtor com todos os campos
    public UserResponseDTO(Long id, String name, String email, List<PhoneResponseDTO> phones) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phones = phones;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PhoneResponseDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneResponseDTO> phones) {
        this.phones = phones;
    }
} 