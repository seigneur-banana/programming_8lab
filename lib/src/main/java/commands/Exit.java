package commands;

import appliances.CommandHandler;
import major.User;
import org.json.simple.JSONObject;

public class Exit extends Command {
    public Exit(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            System.out.println("Goodbye my friend ;)");
            System.exit(0);
        }
        return true;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return " : end the program (without saving it to a file)";
    }
}
