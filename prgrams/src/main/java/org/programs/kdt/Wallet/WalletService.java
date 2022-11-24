package org.programs.kdt.Wallet;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.DUPLICATION_WALLET_ID;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public Wallet create(Wallet wallet) {

        checkDuplicationWalletId(wallet.getWalletId());

        walletRepository.insert(wallet);
        return wallet;
    }



    public List <Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Transactional
    public void delete(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public List<Wallet> findByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId);
    }
    public List<Wallet> findByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId);
    }

    public Optional<Wallet> findByWalletId(UUID walletId) {
        return walletRepository.findById(walletId);
    }

    public List<Wallet> findByCustomerEmail(String email) {
        return walletRepository.findByCustomerEmail(email);
    }

    @Transactional
    public void deleteAll() {
        walletRepository.deleteAll();
    }

    private void checkFoundWalletId(UUID walletId) {
        boolean isId = walletRepository.existWalletId(walletId);
        if (!isId) {
            throw new EntityNotFoundException(DUPLICATION_WALLET_ID);
        }
    }

    private void checkDuplicationWalletId(UUID walletId) {
        boolean isId = walletRepository.existWalletId(walletId);
        if (isId) {
            throw new DuplicationException(DUPLICATION_WALLET_ID);
        }
    }

    @Transactional
    public void deleteByCustomerId(UUID customerId) {
        walletRepository.deleteByCustomerId(customerId);
    }
    @Transactional
    public void deleteById(UUID walletId) {
        checkFoundWalletId(walletId);
        walletRepository.deleteById(walletId);
    }

    public void deleteByVoucherId(UUID voucherId) {
        walletRepository.deleteByVoucherId(voucherId);
    }
}
