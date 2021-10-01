package major;

import appliances.CommandHandler;
import appliances.ParsedCommand;
import commands.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStream;

public class CommandHandlerForClient extends CommandHandler {

    public CommandHandlerForClient(User user) {
        super(user);
    }

    public void fromStream(InputStream stream, boolean isInteractive) {
        Scanner in = new Scanner(stream);
        while (in.hasNext()) {
            String s = in.nextLine();
            if (!s.matches("\\s*")) {
                String com = "";
                String arg = "";
                Matcher m = Pattern.compile("[^\\s]+").matcher(s);
                if (m.find()) {
                    com = m.group();
                    s = m.replaceFirst("");
                }
                m = Pattern.compile("[\\s]+").matcher(s);
                if (m.find()) {
                    arg = m.replaceFirst("");
                }
                addToHistory(com);
                ParsedCommand pc = new ParsedCommand(com);
                Command command = commands.get(pc.getCommand().toLowerCase());
                if (command != null) {
                    if (command.validation(this, pc.getArgs())) {
                        Client.send(command);
                    }
                } else {
                    System.out.println("Команды " + com + " не существует! Список команд: help");
                }
            }
        }
    }

    public String fromString(String com, String arg) {
        addToHistory(com);
        ParsedCommand pc = new ParsedCommand(com);
        Command command = commands.get(pc.getCommand().toLowerCase());

        if (command.validation(this, pc.getArgs())) {
            return Client.send(command);
        } else {
            return "";
        }
    }
}
