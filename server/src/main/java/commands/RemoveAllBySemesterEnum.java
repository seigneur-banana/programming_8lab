package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class RemoveAllBySemesterEnum implements Command {
    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        boolean result = false;
        if (args != null) {
            if (args.length != 1 || args[0].equals("")) return false;
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                if (args[0].toLowerCase().equals(iterator.next().getSemesterEnum().toString().toLowerCase())) {
                    iterator.remove();
                    result = true;
                }
            }
            if (!result) System.out.println("Элемента с таким ID и не было :)");
            return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return "remove_all_by_semester_enum";
    }

    @Override
    public String getDescription() {
        return " semesterEnum : удалить из коллекции все элементы, значение поля semesterEnum которого эквивалентно заданному";
    }
}
