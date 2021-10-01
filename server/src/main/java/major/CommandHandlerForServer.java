package major;

import appliances.CommandHandler;
import appliances.ParsedCommand;
import commands.Command;

import java.util.Date;
import java.util.Scanner;

public class CommandHandlerForServer extends CommandHandler {
    private final Date date;
    private final DBUnit dbUnit;

    public CommandHandlerForServer (Date date, DBUnit dbUnit, User user) {
        super(user);
        this.date = date;
        this.dbUnit = dbUnit;
    }

    public void execute(DBUnit dbUnit) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello world!!! you look beautiful");
        while (true) {
            try {
                System.out.print("> ");
                String str = scanner.nextLine();

                ParsedCommand pc = new ParsedCommand(str);
                if (pc.getCommand() == null || "".equals(pc.getCommand())) {
                    continue;
                }
                Command cmd = commands.get(pc.getCommand().toLowerCase());

                if (cmd == null) {
                    System.out.println("Command not detected, available commands => help");
                    continue;
                } else {
                    if (cmd.validation(this, pc.getArgs())) {
                            System.out.println(cmd.execute(this, dbUnit, pc.getArgs()));

                    } else {
                        System.out.println("Command not detected, available commands => help");
                    }
                }

                addToHistory(str);

            } catch (Exception e) {
                System.out.println("CommandHandler Exception");
            }
        }
    }

}
