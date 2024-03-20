package br.com.goldinvesting.wallet.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.wallet.model.Wallet;
import br.com.goldinvesting.wallet.repository.WalletRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    @Transactional
    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet getWalletById(long id) {
        var wallet = walletRepository.findById(id);
        if (wallet.isPresent())
            return wallet.get();
        return null;
    }

    @Transactional
    public void deleteWallet(long id) {
        walletRepository.deleteById(id);
    }

    @Transactional
    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }
}
