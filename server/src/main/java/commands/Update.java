package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.*;

public class Update implements Command {
    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1) return false;

            Integer id;
            boolean result = false;
            List<StudyGroup> list;
            try {
                id = Integer.parseInt(args[0]);
                if (id < 0) return false;
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer");
                return false;
            }
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                StudyGroup temp = iterator.next();
                if (id.equals(temp.getId())) {
                    iterator.remove();
                    Command cmd = commandHandler.getCommands().get("add");
                    result = cmd.execute(commandHandler, "studygroup");
                    list = commandHandler.sortGroups();
                    Collections.sort(list, new StudyGroup.DateComparator());
                    list.get(commandHandler.getGroups().size() - 1).setId(id);
                }
            }
            if (!result) System.out.println("Элемента с таким ID и не было :)");
            return true;
        } else return false;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return " id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
