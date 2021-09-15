package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.command.Command;
import com.programmers.kdtspringorder.command.CreateCommandAction;
import com.programmers.kdtspringorder.command.CustomerListCommandAction;
import com.programmers.kdtspringorder.command.VoucherListCommandAction;
import com.programmers.kdtspringorder.customer.service.CustomerService;
import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run(String... args) throws Exception {
        output.printMessage("=== Voucher Program ===");
        manageVoucherProcess();
    }

    private void manageVoucherProcess() {
        while (true) {
            printCommandMessage();
            String inputText = input.inputText();
            Command command = Command.valueOf(inputText);

            switch (command) {
                case EXIT -> System.exit(0);
                case CREATE -> {
                    command.execute(new CreateCommandAction(input, output, voucherService));
                }
                case VOUCHERS -> {
                    command.execute(new VoucherListCommandAction(input, output, voucherService, customerService));
                }
                case CUSTOMERS -> {
                    command.execute(new CustomerListCommandAction(input, output, voucherService, customerService));
                }
                default -> defaultProcess();
            }
            output.newLine();
        }
    }

    private void defaultProcess() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String command : Command.getAllCommand()) {
            stringBuilder.append(command).append(" ");
        }
        output.printMessage("잘못 입력 하셨습니다. 입력 가능한 명령어는 " + stringBuilder + " 입니다.");
    }

    private void printCommandMessage() {
        List<String> allMessage = Command.getAllMessage();
        for (String commandMessage : allMessage) {
            output.printMessage(commandMessage);
        }
    }

}