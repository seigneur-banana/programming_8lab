package commands;

import appliances.CommandHandler;

import java.text.SimpleDateFormat;

public class Info implements Command {

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        return args == null;
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        System.out.print("StudyGroup\nДата инициализации " + commandHandler.getGroups().getClass() + " : ");
        System.out.println(new SimpleDateFormat("dd.MM.yyyy k:mm").format(commandHandler.getTime()));
        System.out.println("Кол-во элементов : " + commandHandler.getGroups().size());
        return true;
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

