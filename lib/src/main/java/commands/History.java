package commands;

import appliances.CommandHandler;
import major.User;

import java.util.Map;

public class History extends Command {
    public History(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            for (Object object : commandHandler.getHistory()) {
                System.out.println((String) object);
            }
            return true;
        } else {
            System.out.println("History can't have arguments");
            return false;
        }
    }
    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return " : output the last 8 commands (without their arguments)";
    }
}
