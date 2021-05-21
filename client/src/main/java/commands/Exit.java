package commands;

import appliances.CommandHandler;

public class Exit implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            System.out.println("До скорых встреч! ;)");
            System.exit(0);
        }
        return true;
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args == null) {
            System.exit(0);
        }
        return true;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return " : завершить программу (без сохранения в файл)";
    }
}
