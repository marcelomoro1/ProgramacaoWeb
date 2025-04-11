package com.aula203.atividade1.service;

import com.aula203.atividade1.dto.CreateUserDTO;
import com.aula203.atividade1.model.User;
import com.aula203.atividade1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //createBatch
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final int BATCH_SIZE = 100; // Define o tamanho máximo de cada lote
    @Transactional // Garante que todas as operações sejam atômicas (ou todas são salvas ou nenhuma é)
    public List<User> createBatch(List<CreateUserDTO> usersDTO) {
        // Log inicial do processamento
        log.info("Iniciando processamento em lote de {} usuários", usersDTO.size());
        
        // Validação de emails duplicados
        // Extrai todos os emails da lista de DTOs
        List<String> emails = usersDTO.stream()
                .map(CreateUserDTO::email)
                .collect(Collectors.toList());
        
        // Verifica se algum dos emails já existe no banco
        if (userRepository.existsByEmailIn(emails)) {
            log.warn("Tentativa de criar usuários com emails já existentes");
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Um ou mais emails já estão cadastrados"
            );
        }

        // Inicialização das listas para processamento em lote
        List<User> savedUsers = new ArrayList<>(); // Lista final com todos os usuários salvos
        List<User> batch = new ArrayList<>();      // Lista temporária para cada lote
        
        // Processamento dos usuários em lotes
        for (CreateUserDTO dto : usersDTO) {
            // Cria um novo usuário a partir do DTO
            User user = new User();
            user.setNome(dto.nome());
            user.setEmail(dto.email());
            user.setSenha(dto.senha());
            
            // Adiciona o usuário ao lote atual
            batch.add(user);
            
            // Verifica se o lote atingiu o tamanho máximo
            if (batch.size() >= BATCH_SIZE) {
                // Salva o lote inteiro no banco
                savedUsers.addAll(userRepository.saveAll(batch));
                // Limpa o lote para o próximo processamento
                batch.clear();
                // Log do processamento do lote
                log.debug("Processado lote de {} usuários", BATCH_SIZE);
            }
        }
        
        // Processamento do último lote (se houver usuários restantes)
        if (!batch.isEmpty()) {
            savedUsers.addAll(userRepository.saveAll(batch));
            log.debug("Processado último lote de {} usuários", batch.size());
        }
        
        // Log final do processamento
        log.info("Processamento em lote concluído. {} usuários criados com sucesso", savedUsers.size());
        
        // Retorna todos os usuários salvos
        return savedUsers;
    }

    //create
    public User createUser(User user) {
        return userRepository.save(user);
    }

    //getAll
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //getById
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //update
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (user.getNome() != null) {
                        existingUser.setNome(user.getNome());
                    }
                    if (user.getEmail() != null) {
                        existingUser.setEmail(user.getEmail());
                    }
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
    }

    //delete
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }
        userRepository.deleteById(id);
    }
}