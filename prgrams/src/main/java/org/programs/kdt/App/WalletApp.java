package org.programs.kdt.App;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Command.WalletMenu;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerService;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.IO.Console;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.service.VoucherService;
import org.programs.kdt.Wallet.Wallet;
import org.programs.kdt.Wallet.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.INVALID_COMMAND_TYPE;
import static org.programs.kdt.Exception.ErrorCode.INVALID_ENUM_TYPE;

@RequiredArgsConstructor
@Component
public class WalletApp {

    private static final Logger logger = LoggerFactory.getLogger(WalletApp.class);
    private final Console console;
    private final WalletService walletService;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public void walletProcess() {

        WalletMenu walletMenu = WalletMenu.ERROR;
        while (!walletMenu.equals(WalletMenu.RETURN)) {
            console.output(WalletMenu.toMenu());
            String input = console.input("명령어를 입력해주세요");
            walletMenu = WalletMenu.find(input);
            walletMenuProcess(walletMenu);
        }
    }

    private void walletMenuProcess(WalletMenu walletMenu) {
        switch (walletMenu) {
            case CREATE:
                walletCreateProcess();
                break;
            case FIND_ALL:
                walletFindAllProcess();
                break;
            case FIND_VOUCHER:
                walletFindVoucherProcess();
                break;
            case FIND_CUSTOMER:
                walletFindCustomerProcess();
                break;
            case ERROR:
                logger.error(INVALID_COMMAND_TYPE.getMessage());
                break;
            case DELETE:
                walletDeleteProcess();
                break;
            case RETURN:
                break;
            default:
                logger.error(INVALID_ENUM_TYPE.getMessage());
        }
    }

    private void walletFindCustomerProcess() {
        UUID customerId = console.inputUUID("유저 id를 입력해주세요");
        List<Wallet> walletListByVoucherId = walletService.findByCustomerId(customerId);
        console.printWalletList(walletListByVoucherId);
    }

    private void walletFindVoucherProcess() {
        UUID voucherId = console.inputUUID(console.input("바우처 id를 입력해주세요"));
        List<Wallet> walletListByVoucherId = walletService.findByVoucherId(voucherId);
        console.printWalletList(walletListByVoucherId);
    }

    private void walletDeleteProcess() {
        UUID walletId = console.inputUUID("삭제하실 바우처 지갑 id를 입력해주세요");
        walletService.delete(walletId);
    }

    private void walletFindAllProcess() {
        List<Wallet> walletList = walletService.findAll();
        console.printWalletList(walletList);
    }

    private void walletCreateProcess() {

        UUID customerUUID = console.inputUUID("유저 uuid을 입력해주세요.");
        UUID voucherUUID = console.inputUUID("바우처 uuid를 입력해주세요");

        try {
            Customer customer =
                    customerService
                            .findById(customerUUID)
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_CUSTOMER_ID));
            Voucher voucher =
                    voucherService
                            .findById(voucherUUID)
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID));
            Wallet wallet = new Wallet(voucher, customer, UUID.randomUUID(), LocalDateTime.now());
            walletService.create(wallet);
        } catch (EntityNotFoundException e) {
            console.printError(e.getErrorCode());
        }
    }
}
