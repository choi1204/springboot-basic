package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandOperator;
import org.prgrms.kdt.io.Console;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:34 오전
 */

@Configuration
public class CommandLineApplication implements Runnable, ApplicationRunner {

    private final Console console;
    private final CommandOperator commandOperator;

    public CommandLineApplication(Console console, CommandOperator commandOperator) {
        this.console = console;
        this.commandOperator = commandOperator;
    }

    @Override
    public void run(ApplicationArguments args) {
        new CommandLineApplication(new Console(), commandOperator).run();
    }

    @Override
    public void run() {
        console.guide();

        while (true) {
            switch (console.inputCommand()) {
                case CREATE -> {
                    commandOperator.create(console.inputVoucher());
                    console.successCreate();
                }
                case LIST -> {
                    console.printVouchers(commandOperator.getAll());
                    console.printNextCommand();
                }
                case EXIT -> commandOperator.exit();
                case ERROR -> console.commandError();
            }
        }
    }

}
