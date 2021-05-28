package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class RemoveById implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            int id;
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
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1) return false;
            Integer id;
            boolean result = false;
            try {
                id = Integer.parseInt(args[0]);
                if (id < 0) return false;
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer");
                return false;
            }
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                if (id.equals(iterator.next().getId())) {
                    iterator.remove();
                    result = true;
                }
            }
            if (!result) System.out.println("Элемента с таким ID и не было :)");
            return true;
        } else return false;
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
