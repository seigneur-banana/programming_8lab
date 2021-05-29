package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class RemoveById extends Command {
    private Integer id;

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            try {
                id = Integer.parseInt(args[0]);
                return true;
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }
        } else {
            System.out.println("Почему без аргументов?");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        boolean result = false;
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            if (id.equals(iterator.next().getId())) {
                iterator.remove();
                result = true;
            }
        }
        if (!result) return "Элемента с таким ID и не было :)";
        return "Элемент удалён";
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return " id : удалить элемент из коллекции по его id";
    }
}
