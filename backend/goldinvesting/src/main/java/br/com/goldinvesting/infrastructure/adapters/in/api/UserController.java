package br.com.goldinvesting.infrastructure.adapters.in.api;

import br.com.goldinvesting.application.dto.UserDTO;
import br.com.goldinvesting.application.ports.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserUseCase userUseCase;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userUseCase.createUser(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        UserDTO userDTO = userUseCase.getUserById(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userUseCase.getUsers());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDTO> authenticate(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userUseCase.authenticate(userDTO));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO) {
        userUseCase.updateUser(userDTO);
        return ResponseEntity.noContent().build();
    }
}
