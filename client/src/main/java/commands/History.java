package commands;

import appliances.CommandHandler;

import java.util.Map;

public class History implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            for (Object object : commandHandler.getHistory()) {
                System.out.println((String) object);
            }
            return true;
        } else return false;
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        return true;
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return " : вывести последние 8 команд (без их аргументов)";
    }
}
