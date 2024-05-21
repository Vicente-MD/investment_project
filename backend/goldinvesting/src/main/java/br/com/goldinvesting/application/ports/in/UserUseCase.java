package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.UserDTO;
import java.util.List;

public interface UserUseCase {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(long id);
    void deleteUser(long id);
    List<UserDTO> getUsers();
    UserDTO authenticate(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
}
