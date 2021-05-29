package commands;

import appliances.CommandHandler;

public class Clear extends Command {

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("У clear не может быть аргументов");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        commandHandler.clearGroups();
        return "Локальная коллекция StudyGroup очищена . . .";
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return " : очистить коллекцию";
    }
}
