package major;

import appliances.CommandHandler;
import appliances.ParsedCommand;
import commands.*;

import java.io.InputStream;
import java.util.Scanner;

public class CommandHandlerForClient extends CommandHandler {

    public CommandHandlerForClient(User user) {
        super(user);
    }
    public void execute(InputStream stream) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приветствие!!! @Допиши сюда что-то хорошее@ ");
        while (scanner.hasNext()) {
            try {
                System.out.print("> ");
                String str = scanner.nextLine();

                ParsedCommand pc = new ParsedCommand(str);
                if (pc.getCommand() == null || "".equals(pc.getCommand())) {
                    continue;
                }
                Command cmd = commands.get(pc.getCommand().toLowerCase());

                if (cmd == null) {
                    System.out.println("Команда не распознана, список команд => help");
                    continue;
                } else {
                    if (cmd.validation(this, pc.getArgs())) {
                        Client.send(cmd);
                    } else {
                        System.out.println("Такой команды не существует! Список команд: help");
                    }
                }
                addToHistory(str);

            } catch (Exception e) {
                System.out.println("Ошибка вызовы функции");
            }
        }
    }
}
