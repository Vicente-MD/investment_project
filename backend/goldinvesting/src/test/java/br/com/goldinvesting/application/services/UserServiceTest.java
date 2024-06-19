package br.com.goldinvesting.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.goldinvesting.application.dto.UserDTO;
import br.com.goldinvesting.application.dto.converter.UserConverter;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.domain.model.User;
import br.com.goldinvesting.exceptions.BadCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");

        user = UserConverter.toEntity(userDTO);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUserDTO = userService.createUser(userDTO);

        assertNotNull(createdUserDTO, "The created UserDTO should not be null");
        assertEquals(user.getId(), createdUserDTO.getId(), "The user ID should match");
        assertEquals(user.getName(), createdUserDTO.getName(), "The user name should match");
        assertEquals(user.getEmail(), createdUserDTO.getEmail(), "The user email should match");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO foundUserDTO = userService.getUserById(1L);

        assertNotNull(foundUserDTO, "The found UserDTO should not be null");
        assertEquals(user.getId(), foundUserDTO.getId(), "The user ID should match");

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testAuthenticate() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        UserDTO authenticatedUserDTO = userService.authenticate(userDTO);

        assertNotNull(authenticatedUserDTO, "The authenticated UserDTO should not be null");
        assertEquals(user.getId(), authenticatedUserDTO.getId(), "The user ID should match");
        assertEquals(user.getEmail(), authenticatedUserDTO.getEmail(), "The user email should match");

        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void testAuthenticateBadCredentials() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        UserDTO invalidUserDTO = new UserDTO();
        invalidUserDTO.setEmail("john.doe@example.com");
        invalidUserDTO.setPassword("wrongpassword");

        assertThrows(BadCredentialsException.class, () -> {
            userService.authenticate(invalidUserDTO);
        }, "Should throw BadCredentialsException for wrong password");

        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }
}