package commands;

import appliances.CommandHandler;

import java.text.SimpleDateFormat;

public class Info extends Command {

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("У info не может быть аргументов");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        return  "StudyGroup\nДата инициализации " + commandHandler.getGroups().getClass() + " : \n" +
                new SimpleDateFormat("dd.MM.yyyy k:mm").format(commandHandler.getTime()) +
                "\nКол-во элементов : " + commandHandler.getGroups().size();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}

