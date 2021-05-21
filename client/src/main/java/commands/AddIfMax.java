package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Collections;
import java.util.List;

public class AddIfMax implements Command {
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
            if (args.length != 1) return false;
            int count;
            try {
                count = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }

            if (count < 0) return false;
            List<StudyGroup> list = commandHandler.sortGroups();
            Collections.sort(list);

            if (commandHandler.getGroups().size() == 0) {
                Command cmd = commandHandler.getCommands().get("add");
                if (cmd.execute(commandHandler, "studygroup")) {
                    System.out.println("Добавлен элемнт");
                } else System.out.println("Команда не выполнена, неверный аргумент(ы)");
            } else {
                if (count > list.get(commandHandler.getGroups().size() - 1).getStudentsCount()) {
                    Command cmd = commandHandler.getCommands().get("add");
                    if (cmd.execute(commandHandler, "studygroup")) {
                        list = commandHandler.sortGroups();
                        Collections.sort(list, new StudyGroup.DateComparator());
                        list.get(commandHandler.getGroups().size() - 1).setCount(count);
                    } else System.out.println("Команда не выполнена, неверный аргумент(ы)");
                }
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return " {id_StudyGroup} (с которым вы хотите добавить новый элемент) : добавить новый " +
                "элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
