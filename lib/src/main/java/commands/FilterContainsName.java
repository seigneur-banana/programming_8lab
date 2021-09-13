package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;

import java.util.Iterator;

public class FilterContainsName extends Command {
    public FilterContainsName(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1 || args[0].equals("")){
                System.out.println("invalid number of arguments");
                return false;
            }
            else return true;
        } else {
            System.out.println("Why without arguments?");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        StringBuilder s = new StringBuilder();
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            StudyGroup temp = iterator.next();
            if (temp.getName().toLowerCase().contains(args[0].toLowerCase())) {
                System.out.println(temp);
                s.append(temp.toString());
            }
        }
        return "Filter by name: " + s.toString();
    }

    @Override
    public String getName() {
        return "filter_contains_name";
    }

    @Override
    public String getDescription() {
        return " name : output elements whose name field value contains the specified substring";
    }
}
