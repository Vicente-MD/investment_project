package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.WalletDTO;
import br.com.goldinvesting.domain.model.Wallet;

public class WalletConverter {

    public static WalletDTO toDTO(Wallet wallet) {
        if (wallet == null) {
            return null;
        }
        return new WalletDTO(
                wallet.getId(),
                wallet.getBalance()
        );
    }

    public static Wallet toEntity(WalletDTO walletDTO) {
        if (walletDTO == null) {
            return null;
        }
        return new Wallet(
                walletDTO.getId(),
                null,
                walletDTO.getBalance()
        );
    }
}
