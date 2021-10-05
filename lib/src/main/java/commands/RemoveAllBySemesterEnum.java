package commands;

import appliances.CommandHandler;
import appliances.Semester;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class RemoveAllBySemesterEnum extends Command {
    private String s = "";
    public RemoveAllBySemesterEnum(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1 || args[0].equals("")) {
                System.out.println("invalid number of arguments");
                return false;
            } else {
                s = args[0];
                return true;
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
            StudyGroup temp = iterator.next();
            if (s.toLowerCase().equals(temp.getSemesterEnum().toString().toLowerCase())) {
                if (dbUnit.removeGroupFromDB(temp)) {
                    iterator.remove();
                    result = true;
                }
            }
        }
        if (!result) return "There was no element with this ID :)";
        return "Elements removed";
    }

    @Override
    public String getName() {
        return "remove_all_by_semester";
    }

    @Override
    public String getDescription() {
        return " semesterEnum : remove from the collection all elements whose semesterEnum field value is equivalent to the specified one";
    }
}
