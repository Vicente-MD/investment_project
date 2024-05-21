package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.UserDTO;
import br.com.goldinvesting.domain.model.User;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                WalletConverter.toDTO(user.getWallet())
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                WalletConverter.toEntity(userDTO.getWallet())
        );
    }
}
