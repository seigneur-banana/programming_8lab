package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
import com.google.gson.Gson;
import major.DBUnit;
import major.User;

import java.util.List;

public class Show extends Command {

    public Show(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("Show can't have arguments");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        /*List<StudyGroup> list = commandHandler.sortGroups();
        String json = new Gson().toJson(list);
        if (list.size() == 0) return "Collection is empty";
        else return json;*/

        if (commandHandler.getGroups().size() > 0) {
            StringBuilder msg = new StringBuilder();
            commandHandler.getGroups().stream().min(StudyGroup.byIdComparator).ifPresent(msg::append);
            commandHandler.getGroups().stream().sorted(StudyGroup.byIdComparator).skip(1).forEach(p -> msg.append(",\n").append(p));
            return msg.toString();
        } else {
            return "0";
        }

    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : output all the elements of the collection in a string representation to the standard output stream";
    }
}
