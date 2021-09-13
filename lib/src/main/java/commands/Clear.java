package commands;

import appliances.CommandHandler;
import major.DBUnit;
import major.User;

public class Clear extends Command {

    public Clear(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("Clear can't have arguments");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        commandHandler.clearGroups();
        return "Local Collection StudyGroup is clear . . .";
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return " : clear the collection";
    }
}
