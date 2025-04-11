package com.aula203.atividade1.controller;

import com.aula203.atividade1.dto.CreateUserDTO;
import com.aula203.atividade1.dto.GetUserDTO;
import com.aula203.atividade1.dto.UpdateUserDTO;
import com.aula203.atividade1.model.User;
import com.aula203.atividade1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "API de gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //createBatch
    @Operation(summary = "Cria múltiplos usuários em lote")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuários criados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/batch")
    public ResponseEntity<List<GetUserDTO>> createBatch(
            @Parameter(description = "Lista de usuários a serem criados")
            @Valid @RequestBody List<CreateUserDTO> usersDTO) {
        
        List<User> createdUsers = userService.createBatch(usersDTO);
        
        List<GetUserDTO> createdUsersDTO = createdUsers.stream()
                .map(user -> new GetUserDTO(
                        user.getId(),
                        user.getNome(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsersDTO);
    }

    //getAll
    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getAll() {
        List<User> users = userService.getAllUsers(); //pega os usuarios do banco de dados
        List<GetUserDTO> userDTOs = users.stream() //transforma os usuarios em DTOs
                .map(user -> new GetUserDTO(user.getId(), user.getNome(), user.getEmail())) 
                .collect(Collectors.toList()); 
        return ResponseEntity.ok(userDTOs);
    }

    //getById
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getById(@PathVariable Long id) {
        return userService.getUserById(id) //pega o usuario do banco de dados
                .map(user -> new GetUserDTO(user.getId(), user.getNome(), user.getEmail())) //transforma o usuario em DTO
                .map(ResponseEntity::ok) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    //create
    @PostMapping
    public ResponseEntity<GetUserDTO> create(@RequestBody CreateUserDTO userDTO) {
        User user = new User(); 
        user.setNome(userDTO.nome());
        user.setEmail(userDTO.email());
        user.setSenha(userDTO.senha());

        User createdUser = userService.createUser(user); //cria o usuario no banco de dados
        GetUserDTO createdUserDTO = new GetUserDTO( //transforma o usuario em DTO
                createdUser.getId(),
                createdUser.getNome(),
                createdUser.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO); 
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<GetUserDTO> update(@PathVariable Long id, @RequestBody UpdateUserDTO userDTO) {
        User user = new User();
        user.setNome(userDTO.nome()); 
        user.setEmail(userDTO.email());

        User updatedUser = userService.updateUser(id, user); //atualiza o usuario no banco de dados
        GetUserDTO updatedUserDTO = new GetUserDTO( //transforma o usuario em DTO
                updatedUser.getId(),
                updatedUser.getNome(),
                updatedUser.getEmail()
        );
        return ResponseEntity.ok(updatedUserDTO);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}