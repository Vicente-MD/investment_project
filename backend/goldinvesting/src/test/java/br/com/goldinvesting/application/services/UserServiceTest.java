package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.UserDTO;
import br.com.goldinvesting.application.dto.converter.UserConverter;
import br.com.goldinvesting.application.dto.converter.WalletConverter;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.application.ports.out.WalletRepository;
import br.com.goldinvesting.domain.model.User;
import br.com.goldinvesting.domain.model.Wallet;
import br.com.goldinvesting.exceptions.BadCredentialsException;
import br.com.goldinvesting.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(1000.0);
        userDTO.setWallet(WalletConverter.toDTO(wallet));

        User user = UserConverter.toEntity(userDTO);
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(wallet.getBalance(), result.getWallet().getBalance());

        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user2.getName(), result.get(1).getName());
    }

    @Test
    void authenticate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");

        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        UserDTO result = userService.authenticate(userDTO);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void authenticate_badCredentials() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        assertThrows(BadCredentialsException.class, () -> userService.authenticate(userDTO));
    }

    @Test
    void updateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe Updated");
        userDTO.setEmail("john.doe.updated@example.com");
        userDTO.setPassword("newpassword");

        User user = UserConverter.toEntity(userDTO);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).setUser(user.getId(), user.getName(), user.getEmail(), user.getPassword());

        userService.updateUser(userDTO);

        verify(userRepository, times(1)).setUser(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}
