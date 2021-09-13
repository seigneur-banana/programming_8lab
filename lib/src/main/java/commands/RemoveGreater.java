package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;

import java.util.Iterator;

public class RemoveGreater extends Command {
    private Integer id;

    public RemoveGreater(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            try {
                id = Integer.parseInt(args[0]);
                return true;
            } catch (Exception e) {
                System.out.println("As an argument, not Integer or <0");
                return false;
            }
        } else {
            System.out.println("Why without arguments?");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        boolean result = false;
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            if (id < iterator.next().getStudentsCount()) {
                if (dbUnit.removeGroupFromDB((StudyGroup)iterator)) {
                    iterator.remove();
                    result = true;
                }
            }
        }
        if (!result) return "There was no element with this ID :)";
        return "The element was deleted";
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return " {element} : remove all items from the collection that exceed the specified limit";
    }
}
