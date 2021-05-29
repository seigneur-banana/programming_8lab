package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Collections;
import java.util.List;

public class PrintDescending extends Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("У PrintDescending не может быть аргументов");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, String... args) {
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
        return " : вывести элементы коллекции в порядке убывания";
    }
}
