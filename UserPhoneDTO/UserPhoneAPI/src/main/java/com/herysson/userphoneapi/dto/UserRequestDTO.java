package com.herysson.userphoneapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO (Data Transfer Object) para receber os dados de entrada na criação/atualização de usuários
 * Contém as validações necessárias para os campos
 */
public class UserRequestDTO {
    // Nome do usuário - campo obrigatório
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    // Email do usuário - campo obrigatório e deve ser um email válido
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // Lista de telefones do usuário - deve ter entre 1 e 3 telefones
    @Size(min = 1, max = 3, message = "Um usuário pode ter de 1 a 3 telefones")
    private List<PhoneRequestDTO> phones;

    // Construtor padrão necessário para o Spring
    public UserRequestDTO() {
    }

    // Construtor com todos os campos
    public UserRequestDTO(String name, String email, List<PhoneRequestDTO> phones) {
        this.name = name;
        this.email = email;
        this.phones = phones;
    }

    // Getters e Setters
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

    public List<PhoneRequestDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequestDTO> phones) {
        this.phones = phones;
    }
} 