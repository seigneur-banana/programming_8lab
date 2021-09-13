package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;

import java.util.Iterator;
import java.util.Optional;

public class RemoveById extends Command {
    private Integer id;

    public RemoveById(User user) {
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
        /*boolean result = false;
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            if (id.equals(iterator.next().getId())) {
                iterator.remove();
                result = true;
            }
        }
        if (!result) return "Элемента с таким ID и не было :)";
        return "Элемент удалён";*/

        Optional<StudyGroup> optional = commandHandler.getGroups().stream().filter(x -> x.getId().equals(id)).findFirst();
        if (optional.isPresent()) {
            if (user.getName().equals("admin") || optional.get().getUser().getName().equals(user.getName())) {
                if (dbUnit.removeGroupFromDB(optional.get())) {
                    commandHandler.getGroups().remove(optional.get());
                    return "The element with the id "+ id + " has been successfully deleted!";
                } else {
                    return "When deleting an element с id " + id + " an SQL error has occurred!";
                }
            } else {
                return "You are not the owner of the item с id " + id + ", therefore, you do not have the rights to delete it!";
            }
        } else {
            return "Deletion is not possible, because there is no element in the collection with id " + id + ".";
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return " id : delete an item from the collection by its id";
    }
}
