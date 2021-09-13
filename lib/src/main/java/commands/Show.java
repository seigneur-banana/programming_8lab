package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
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
        List<StudyGroup> list = commandHandler.sortGroups();
        if (list.size() == 0) return "Collection is empty";
        else return "StudyGroups: \n" + list.toString();
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
