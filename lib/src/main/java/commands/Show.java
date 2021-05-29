package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.List;

public class Show extends Command {

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("У show не может быть аргументов");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        List<StudyGroup> list = commandHandler.sortGroups();
        if (list.size() == 0) return "Коллекция пуста";
        else return "StudyGroups: \n" + list.toString();
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
