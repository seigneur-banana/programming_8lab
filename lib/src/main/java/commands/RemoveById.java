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
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }
        } else {
            System.out.println("Почему без аргументов?");
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
                    return "Элемент с id " + id + " успешно удалён!";
                } else {
                    return "При удалении элемента с id " + id + " произошла ошибка SQL!";
                }
            } else {
                return "Вы не являетесь владельцем элемента с id " + id + ", поэтому у вас нет прав на его удаление!";
            }
        } else {
            return "Удаление невозможно, так как в коллекции нет элемента с id " + id + ".";
        }
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
