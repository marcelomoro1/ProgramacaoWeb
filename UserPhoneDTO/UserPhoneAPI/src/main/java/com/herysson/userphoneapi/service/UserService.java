package com.herysson.userphoneapi.service;

import com.herysson.userphoneapi.dto.UserRequestDTO;
import com.herysson.userphoneapi.dto.UserUpdateDTO;
import com.herysson.userphoneapi.mapper.UserMapper;
import com.herysson.userphoneapi.model.Phone;
import com.herysson.userphoneapi.model.User;
import com.herysson.userphoneapi.repository.PhoneRepository;
import com.herysson.userphoneapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios relacionada aos usuários
 * Implementa as regras de validação e manipulação dos dados
 */
@Service
public class UserService {
    // Injeção de dependência do repositório de usuários
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    /**
     * Salva um novo usuário no banco de dados
     * @param userDTO DTO com os dados do usuário a ser criado
     * @return Usuário salvo no banco
     * @throws IllegalArgumentException se o email já existir ou se houver problemas com os telefones
     */
    public User saveUser(UserRequestDTO userDTO) {
        // Converte o DTO em uma entidade User
        User user = UserMapper.toEntity(userDTO);
        
        // Verifica se o email já está cadastrado
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Cliente com esse e-mail já existe.");
        }

        // Verifica se o usuário tem mais de 3 telefones
        if(user.getPhones().size() > 3){
            throw new IllegalArgumentException("Usuario ja possui o numero maximo de telefones.");
        }

        // Valida cada telefone do usuário
        for (Phone phone : user.getPhones()) {
            String number = phone.getNumber();
            if (number.length() >= 2) {
                String dddStr = number.substring(0, 2);
                int ddd = Integer.parseInt(dddStr);

                // Verifica se o DDD é válido (entre 11 e 99)
                if (ddd < 11 || ddd > 99) {
                    throw new IllegalArgumentException("DDD inválido. O DDD deve estar entre 11 e 99.");
                }
            } else {
                throw new IllegalArgumentException("Número de telefone inválido. O número deve ter pelo menos 2 dígitos.");
            }
        }

        // Salva o usuário no banco de dados
        User savedUser = userRepository.save(user);
        
        // Garante que todos os telefones tenham o relacionamento com o usuário
        for (Phone phone : savedUser.getPhones()) {
            phone.setUser(savedUser);
            phoneRepository.save(phone);
        }
        
        return savedUser;
    }

    /**
     * Retorna todos os usuários cadastrados
     * @return Lista de usuários
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return Optional contendo o usuário, se encontrado
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Verifica se um usuário existe pelo ID
     * @param id ID do usuário
     * @return true se o usuário existe, false caso contrário
     */
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Deleta um usuário pelo ID
     * @param id ID do usuário a ser deletado
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Atualiza os dados de um usuário existente
     * @param id ID do usuário a ser atualizado
     * @param userDTO DTO com os novos dados do usuário
     * @return Usuário atualizado
     * @throws IllegalArgumentException se o usuário não existir ou se houver problemas com os telefones
     */
    public User updateUser(Long id, UserUpdateDTO userDTO) {
        // Verifica se o usuário existe
        if(!existsById(id)){
            throw new IllegalArgumentException("User com ID " + id + " não encontrado");
        }

        // Busca o usuário existente
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User com ID " + id + " não encontrado"));

        // Verifica se há campos para atualizar
        if (!userDTO.hasUpdates()) {
            return existingUser;
        }

        // Atualiza apenas os campos que foram enviados
        UserMapper.updateEntity(existingUser, userDTO);

        // Se o email foi atualizado, verifica se já existe
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
            if(userRepository.existsByEmail(userDTO.getEmail())){
                throw new IllegalArgumentException("Cliente com esse e-mail já existe.");
            }
        }

        // Se os telefones foram atualizados, valida cada um
        if (userDTO.getPhones() != null) {
            if(userDTO.getPhones().size() > 3){
                throw new IllegalArgumentException("Usuario ja possui o numero maximo de telefones.");
            }

            for (Phone phone : existingUser.getPhones()) {
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
        }

        // Salva as alterações no banco de dados
        User updatedUser = userRepository.save(existingUser);
        
        // Garante que todos os telefones tenham o relacionamento com o usuário
        if (userDTO.getPhones() != null) {
            for (Phone phone : updatedUser.getPhones()) {
                phone.setUser(updatedUser);
                phoneRepository.save(phone);
            }
        }
        
        return updatedUser;
    }
}
