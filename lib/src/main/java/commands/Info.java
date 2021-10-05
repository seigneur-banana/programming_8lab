package commands;

import appliances.CommandHandler;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;

public class Info extends Command {

    public Info(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            return true;
        } else {
            System.out.println("Info can't have arguments");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        return  "StudyGroup\nData initialization " + commandHandler.getGroups().getClass() + " : \n" +
                new SimpleDateFormat("dd.MM.yyyy k:mm").format(commandHandler.getTime()) +
                "\nCount of elements : " + commandHandler.getGroups().size();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return " : output information about the collection (type, initialization date, number of elements, etc.) to the standard output stream.";
    }
}

