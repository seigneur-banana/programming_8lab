package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.List;

public class PrintDescending extends Command {

    public PrintDescending(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("PrintDescending can't have arguments");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        List<StudyGroup> list = commandHandler.sortGroups();
        Collections.sort(list, Collections.reverseOrder());
        return "Descending StudyGroup :\n" + list.toString();
    }

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getDescription() {
        return " : display the elements of the collection in descending order";
    }
}
