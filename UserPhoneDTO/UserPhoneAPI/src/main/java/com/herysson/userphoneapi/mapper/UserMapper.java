package com.herysson.userphoneapi.mapper;

import com.herysson.userphoneapi.dto.PhoneRequestDTO;
import com.herysson.userphoneapi.dto.PhoneResponseDTO;
import com.herysson.userphoneapi.dto.UserRequestDTO;
import com.herysson.userphoneapi.dto.UserResponseDTO;
import com.herysson.userphoneapi.dto.UserUpdateDTO;
import com.herysson.userphoneapi.model.Phone;
import com.herysson.userphoneapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter entre DTOs e entidades
 * Implementa o padrão Mapper para separar a lógica de conversão
 */
public class UserMapper {
    
    /**
     * Converte um UserRequestDTO em uma entidade User
     * @param dto DTO com os dados de entrada
     * @return Entidade User pronta para ser salva no banco
     */
    public static User toEntity(UserRequestDTO dto) {
        // Cria uma nova instância de User
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        
        // Converte a lista de PhoneRequestDTO em uma lista de Phone
        if (dto.getPhones() != null) {
            List<Phone> phones = dto.getPhones().stream()
                    .map(phoneDTO -> {
                        Phone phone = new Phone();
                        phone.setNumber(phoneDTO.getNumber());
                        phone.setUser(user);
                        return phone;
                    })
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }
        
        return user;
    }

    /**
     * Converte uma entidade User em um UserResponseDTO
     * @param user Entidade User do banco de dados
     * @return DTO formatado para resposta da API
     */
    public static UserResponseDTO toDTO(User user) {
        // Cria uma nova instância de UserResponseDTO
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        
        // Converte a lista de Phone em uma lista de PhoneResponseDTO
        if (user.getPhones() != null) {
            List<PhoneResponseDTO> phones = user.getPhones().stream()
                    .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                    .collect(Collectors.toList());
            dto.setPhones(phones);
        }
        
        return dto;
    }

    /**
     * Atualiza uma entidade User com os dados de um UserUpdateDTO
     * Apenas os campos não nulos do DTO serão atualizados na entidade
     * @param user Entidade User a ser atualizada
     * @param dto DTO com os dados de atualização
     * @return Entidade User atualizada
     */
    public static User updateEntity(User user, UserUpdateDTO dto) {
        // Atualiza o nome se presente no DTO
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        // Atualiza o email se presente no DTO
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        // Atualiza a lista de telefones se presente no DTO
        if (dto.getPhones() != null) {
            List<Phone> phones = dto.getPhones().stream()
                    .map(phoneDTO -> {
                        Phone phone = new Phone();
                        phone.setNumber(phoneDTO.getNumber());
                        phone.setUser(user);
                        return phone;
                    })
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }

        return user;
    }
} 