package commands;

import appliances.CommandHandler;

import java.util.Map;

public class History extends Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            for (Object object : commandHandler.getHistory()) {
                System.out.println((String) object);
            }
            return true;
        } else {
            System.out.println("У history не может быть аргументов");
            return false;
        }
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
