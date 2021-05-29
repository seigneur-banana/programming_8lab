package commands;

import appliances.CommandHandler;
import java.io.Serializable;

public abstract class Command implements Serializable {

    public String execute(CommandHandler commandHandler, String... args){
        return "Команда выполнена.";
    }
    public abstract boolean validation(CommandHandler commandHandler, String... args);

    public abstract String getName();

    public abstract String getDescription();
}
