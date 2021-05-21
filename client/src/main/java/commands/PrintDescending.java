package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Collections;
import java.util.List;

public class PrintDescending implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        return args == null;
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args == null) {
            List<StudyGroup> list = commandHandler.sortGroups();
            Collections.sort(list, Collections.reverseOrder());
            System.out.println("Descending StudyGroup :\n" + list);
            return true;
        } else return false;
    }

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getDescription() {
        return " : вывести элементы коллекции в порядке убывания";
    }
}
