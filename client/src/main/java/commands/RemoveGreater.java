package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class RemoveGreater implements Command {
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
            int count;
            boolean result = false;
            try {
                count = Integer.parseInt(args[0]);
                if (count < 0) return false;
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }

            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                if (count < iterator.next().getStudentsCount()) {
                    iterator.remove();
                    result = true;
                }
            }
            if (!result) System.out.println("Групп с большим кол-вом студентов и не было :)");
            return true;
        } else return false;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return " {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
