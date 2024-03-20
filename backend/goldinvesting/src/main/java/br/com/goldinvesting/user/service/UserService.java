package br.com.goldinvesting.user.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.exceptions.BadCredentialsException;
import br.com.goldinvesting.exceptions.ResourceNotFoundException;
import br.com.goldinvesting.user.model.User;
import br.com.goldinvesting.user.repository.UserRepository;
import br.com.goldinvesting.wallet.model.Wallet;
import br.com.goldinvesting.wallet.repository.WalletRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public User createUser(User user) {
        var wallet = new Wallet(0, user);
        user.setWallet(walletRepository.save(wallet));
        return userRepository.save(user);
    }

    @Transactional
    public User getUserById(long id) {
        var user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        throw new ResourceNotFoundException("Usuário não encontrado.");
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User authenticate(User user) {
        Optional<User> userReturned = userRepository.findByEmail(user.getEmail());
        if(!userReturned.isPresent() || !userReturned.get().getPassword().equals(user.getPassword())){
            throw new BadCredentialsException("Usuário não encontrado.");
        }else return userReturned.get();
    }

    @Transactional
    public void updateUser(User userGiven) {
        getUserById(userGiven.getId());
        userRepository.setUser(userGiven.getId(), userGiven.getName(),
        userGiven.getEmail(), userGiven.getPassword());
    }
}
