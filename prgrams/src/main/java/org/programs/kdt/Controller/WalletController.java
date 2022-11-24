package org.programs.kdt.Controller;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerService;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.service.VoucherService;
import org.programs.kdt.Wallet.Wallet;
import org.programs.kdt.Wallet.WalletCreateRequest;
import org.programs.kdt.Wallet.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    @GetMapping
    public String findWalletList(Model model) {
        List<Wallet> walletList = walletService.findAll();
        model.addAttribute("walletList", walletList);

        return "walletList";
    }

    @GetMapping("/{walletId}")
    public String findWallet(@PathVariable UUID walletId, Model model) {
        Optional<Wallet> walletOptional = walletService.findByWalletId(walletId);
        if (walletOptional.isPresent()) {
            model.addAttribute("wallet", walletOptional.get());
        }
        return "wallet";
    }

    @GetMapping("/create")
    public String createWalletView(Model model) {
        return "createWallet";
    }

    @PostMapping("/create")
    public String createWallet(WalletCreateRequest walletCreateRequest) {

        Customer customer = customerService
                .findById(walletCreateRequest.customerId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_CUSTOMER_ID));
        Voucher voucher = voucherService.findById(walletCreateRequest.voucherId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID));
        Wallet wallet = new Wallet(voucher, customer, UUID.randomUUID(), LocalDateTime.now());
        walletService.create(wallet);
        return "redirect:/wallet";
    }

    @PostMapping("/delete/{walletId}")
    public String deleteWallet(@PathVariable UUID walletId) {
        walletService.delete(walletId);
        return "redirect:/wallet";
    }

    @GetMapping("voucher/{voucherId}")
    public String findVoucherId(@PathVariable UUID voucherId, Model model) {
        List<Wallet> walletList = walletService.findByVoucherId(voucherId);
        model.addAttribute("walletList", walletList);
        return "walletList";
    }

    @GetMapping("customer/{customerId}")
    public String findCustomerId(@PathVariable UUID customerId, Model model) {
        List<Wallet> walletList = walletService.findByCustomerId(customerId);
        model.addAttribute("walletList", walletList);
        return "walletList";
    }
}
