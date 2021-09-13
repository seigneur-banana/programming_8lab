package commands;
import appliances.CommandHandler;
import major.User;

import java.util.Map;

public class Help extends Command{
    public Help(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            Map<String, Command> commands = commandHandler.getCommands();
            for (Command cmd : commands.values()) {
                System.out.println(cmd.getName()  + cmd.getDescription());
            }
            return true;
        }
        else {
            System.out.println("Help can't have arguments");
            return false;
        }
    }
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return " : display help for available commands";
    }
}
