package com.herysson.userphoneapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO (Data Transfer Object) para atualização parcial de usuários
 * Todos os campos são opcionais, permitindo atualizar apenas os campos desejados
 */
public class UserUpdateDTO {
    // Nome do usuário - opcional
    @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
    private String name;

    // Email do usuário - opcional
    @Email(message = "Email inválido")
    private String email;

    // Lista de telefones do usuário - opcional
    @Size(max = 3, message = "Um usuário pode ter no máximo 3 telefones")
    private List<PhoneRequestDTO> phones;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String name, String email, List<PhoneRequestDTO> phones) {
        this.name = name;
        this.email = email;
        this.phones = phones;
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

    public List<PhoneRequestDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequestDTO> phones) {
        this.phones = phones;
    }

    /**
     * Verifica se o DTO contém algum campo para atualização
     * @return true se pelo menos um campo estiver presente, false caso contrário
     */
    public boolean hasUpdates() {
        return name != null || email != null || phones != null;
    }
} 