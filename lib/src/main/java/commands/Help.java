package commands;
import appliances.CommandHandler;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.util.Map;

public class Help extends Command{
    public Help(User user) {
        super(user);
    }

    @Override
    public String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        Map<String, Command> commands = commandHandler.getCommands();
        StringBuilder s = new StringBuilder();
        for (Command cmd : commands.values()) {
            s.append(cmd.getName()).append(cmd.getDescription());
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
            return true;
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
