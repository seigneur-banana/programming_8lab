package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.List;

public class Show implements Command {

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        return args == null;
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args == null) {
            List<StudyGroup> list = commandHandler.sortGroups();
            if (list.size() == 0) System.out.println("Коллекция StudyGroups пуста :( ");
            else System.out.println("StudyGroups:\n" + list);
            return true;
        } else return false;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
