package commands;

import appliances.CommandHandler;
import major.DBUnit;
import major.User;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected User user;

    public Command(User user) {
        this.user = user;
    }

    public abstract boolean validation(CommandHandler commandHandler, String... args);

    public String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        return "The command is executed.";
    }

    public abstract String getName();

    public abstract String getDescription();
}
