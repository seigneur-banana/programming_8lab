package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class Clear extends Command {

    public Clear(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        return true;
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        if (commandHandler.getGroups().size() > 0) {
            if (!user.getName().equals("admin")) {
                if (commandHandler.getGroups().stream().anyMatch(x -> x.getUser().getName().equals(user.getName()))) {
                    boolean noPermissionErrors = true;
                    boolean noSqlErrors = true;
                    for (Iterator<StudyGroup> iter = commandHandler.getGroups().iterator(); iter.hasNext(); ) {
                        StudyGroup product = iter.next();
                        if (product.getUser().getName().equals(user.getName())) {
                            if (dbUnit.removeGroupFromDB(product)) {
                                iter.remove();
                            } else {
                                noSqlErrors = false;
                            }
                        } else {
                            noPermissionErrors = false;
                        }
                    }
                    if (!noPermissionErrors && !noSqlErrors) {
                        return "clearError1";
                    } else if (noPermissionErrors && !noSqlErrors) {
                        return "clearError2";
                    } else if (!noPermissionErrors) {
                        return "clearError3";
                    } else {
                        return "clearSuccess";
                    }
                } else {
                    return getStringFromBundle("clearError4");
                }
            } else {
                boolean noErrors = true;
                for (Iterator<StudyGroup> iter = commandHandler.getGroups().iterator(); iter.hasNext(); ) {
                    StudyGroup product = iter.next();
                    if (dbUnit.removeGroupFromDB(product)) {
                        iter.remove();
                    } else {
                        noErrors = false;
                    }
                }
                if (noErrors) {
                    return "success";
                } else {
                    return "error";
                }
            }
        }
        return "clearError5";
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return " : clear the commandHandler.getGroups()";
    }
}
