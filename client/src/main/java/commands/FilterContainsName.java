package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class FilterContainsName implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1 || args[0].equals("")){
                System.out.println("неверное кол-во аргументов");
                return false;
            }
            else return true;
        } else {
            System.out.println("Почему без аргументов?");
            return false;
        }
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1 || args[0].equals("")) return false;
            boolean result = false;

            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                StudyGroup temp = iterator.next();
                if (temp.getName().toLowerCase().contains(args[0].toLowerCase())) {
                    System.out.println(temp);
                    result = true;
                }
            }
            if (!result) System.out.println("Элементов с таким семетром и не было :)");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return "filter_contains_name";
    }

    @Override
    public String getDescription() {
        return " name : вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
