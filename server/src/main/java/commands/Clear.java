package commands;

import appliances.CommandHandler;

public class Clear implements Command {
    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args == null) {
            commandHandler.clearGroups();
            System.out.println("Локальная коллекция StudyGroup очищена . . .");
            return true;
        }
        return false;
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
