package org.programmers;

import org.programmers.order.OrderService;
import org.programmers.voucher.VoucherRepository;
import org.programmers.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        Console console = new Console();

        while (true) {
            console.printPrompt();

            String inputString = console.input("> ");
            if (inputString.equals("create")) {
                var voucherService = applicationContext.getBean(VoucherService.class);

                console.printVoucherTypes();

                String voucherType = console.input("> ");
                if (voucherType.equals("f")) {
                    console.askAmount();

                    long amount = Long.parseLong(console.input("> "));

                    voucherService.createFixedAmountVoucher(amount);
                } else if (voucherType.equals("p")) {
                    console.askPercentage();

                    long percentage = Long.parseLong(console.input("> "));

                    voucherService.createPercentDiscountVoucher(percentage);
                }
            } else if (inputString.equals("list")) {
                var voucherRepository = applicationContext.getBean(VoucherRepository.class);

                System.out.println(voucherRepository.getAllVouchers().toString());
            } else if (inputString.equals("exit")) {
                break;
            }
        }
    }
}
