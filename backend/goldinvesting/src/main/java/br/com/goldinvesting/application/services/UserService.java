package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.UserDTO;
import br.com.goldinvesting.application.dto.converter.UserConverter;
import br.com.goldinvesting.application.ports.in.UserUseCase;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.application.ports.out.WalletRepository;
import br.com.goldinvesting.domain.model.User;
import br.com.goldinvesting.domain.model.Wallet;
import br.com.goldinvesting.exceptions.BadCredentialsException;
import br.com.goldinvesting.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserConverter.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserConverter.toDTO(savedUser);
    }

    @Transactional
    @Override
    public UserDTO getUserById(long id) {
        return userRepository.findById(id)
                .map(UserConverter::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(UserConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO authenticate(UserDTO userDTO) {
        Optional<User> userReturned = userRepository.findByEmail(userDTO.getEmail());
        if (!userReturned.isPresent() || !userReturned.get().getPassword().equals(userDTO.getPassword())) {
            throw new BadCredentialsException("Usuário não encontrado.");
        }
        return UserConverter.toDTO(userReturned.get());
    }

    @Transactional
    @Override
    public void updateUser(UserDTO userDTO) {
        getUserById(userDTO.getId());
        User user = UserConverter.toEntity(userDTO);
        userRepository.setUser(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}
