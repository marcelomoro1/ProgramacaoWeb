package com.herysson.userphoneapi.controller;

import com.herysson.userphoneapi.dto.UserRequestDTO;
import com.herysson.userphoneapi.dto.UserResponseDTO;
import com.herysson.userphoneapi.dto.UserUpdateDTO;
import com.herysson.userphoneapi.mapper.UserMapper;
import com.herysson.userphoneapi.model.User;
import com.herysson.userphoneapi.repository.UserRepository;
import com.herysson.userphoneapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST responsável por gerenciar as operações relacionadas a usuários
 * Expõe endpoints para CRUD de usuários
 */
@RestController
@RequestMapping("/users")
public class UserController {
    // Injeção de dependência do serviço de usuários
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userDTO) {
        User user = userService.saveUser(userDTO);
        return UserMapper.toDTO(user);
    }


    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userDTO) {
        User user = userService.updateUser(id, userDTO);
        return UserMapper.toDTO(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

