package commands;
import appliances.CommandHandler;
import java.util.Map;

public class Help implements Command{
    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args == null) {
            Map<String, Command> commands = commandHandler.getCommands();
            for (Command cmd : commands.values()) {
                System.out.println(cmd.getName()  + cmd.getDescription());
            }
            return true;
        }
        else return false;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return " : вывести справку по доступным командам";
    }
}
