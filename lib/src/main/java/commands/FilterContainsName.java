package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Iterator;

public class FilterContainsName extends Command {
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
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        StringBuilder s = new StringBuilder();
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            StudyGroup temp = iterator.next();
            if (temp.getName().toLowerCase().contains(args[0].toLowerCase())) {
                System.out.println(temp);
                s.append(temp.toString());
            }
        }
        return "Фильтр по имени: " + s.toString();
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
