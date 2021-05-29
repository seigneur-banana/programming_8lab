package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class RemoveAllBySemesterEnum extends Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1 || args[0].equals("")) {
                System.out.println("неверное кол-во аргументов");
                return false;
            } else return true;
        } else {
            System.out.println("Почему без аргументов?");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        boolean result = false;

        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            if (args[0].toLowerCase().equals(iterator.next().getSemesterEnum().toString().toLowerCase())) {
                iterator.remove();
                result = true;
            }
        }
        if (!result) return "Элемента с таким ID и не было :)";
        return "Элемент удалён";
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
