package commands;

import appliances.CommandHandler;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.util.Map;

public class History extends Command {
    public History(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        return true;
    }

    @Override
    public String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        StringBuilder s = new StringBuilder();
            for (String str : commandHandler.getHistory()) {
               s.append(str);
               s.append("\n");
            }
            return s.toString();
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
