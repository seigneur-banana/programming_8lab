package commands;

import appliances.CommandHandler;

public interface Command {

    boolean execute(CommandHandler commandHandler, String... args);
    boolean validation(CommandHandler commandHandler, String... args);

    String getName();

    String getDescription();
}
