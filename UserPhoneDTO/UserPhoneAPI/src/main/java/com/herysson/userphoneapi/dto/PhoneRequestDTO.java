package com.herysson.userphoneapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PhoneRequestDTO {
    
    @NotBlank(message = "O número do telefone é obrigatório")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "O número do telefone deve conter entre 10 e 11 dígitos")
    private String number;

    public PhoneRequestDTO() {
    }

    public PhoneRequestDTO(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
} 