package commands;

import appliances.CommandHandler;
import appliances.ParsedCommand;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteScript extends Command {
    public ExecuteScript(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null) {
            if (args.length != 1 || args[0].equals("")) {
                System.out.println("неверное кол-во аргументов");
                return false;
            } else return true;
        } else {
            System.out.println("Почему без аргументов?");
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) { //пофиксить рекурсию
        if (args != null) {
            if (args.length != 1) return "Неверное количество аргументов";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(args[0]));
                String line;
                Map<String, Command> commands = commandHandler.getCommands();

                while (true) {
                    line = reader.readLine();
                    if (line == null) break;

                    if (line.matches("\\s*add\\s+\\{ *\"[^\"\\r\\n]*\" *: *\"[^\"\\r\\n]*\"( *, *\"[^\"\\r\\n]*\" *: *\"[^\"\\r\\n]*\"){7} *}")) {
                        Matcher m = Pattern.compile("\\{\\s*[^{}]+\\s*}").matcher(line);
                        if (m.find()) {
                            if (Add.addFromScript(commandHandler, Add.isItIdUnique(commandHandler, commandHandler.getGroups().size()), m.group(), 0, dbUnit))
                                System.out.println("Элемент добавлен");
                            continue;
                        }
                    }
                    if (line.matches("\\s*update\\s+\\d\\s+\\{ *\"[^\"\\r\\n]*\" *: *\"[^\"\\r\\n]*\"( *, *\"[^\"\\r\\n]*\" *: *\"[^\"\\r\\n]*\"){7} *}")) {
                        Matcher m = Pattern.compile("\\{\\s*[^{}]+\\s*}").matcher(line);
                        if (m.find()) {
                            String[] columns = line.split(" ");
                            if (Add.addFromScript(commandHandler, Integer.parseInt(columns[1]), m.group(), 1, dbUnit))
                                System.out.println("Элемент обновлен");
                            continue;
                        }
                    }
                    if (line.matches("\\s*add_if_max\\s+\\{ *\"[^\"\\r\\n]*\" *: *\"[^\"\\r\\n]*\"( *, *\"[^\"\\r\\n]*\" *: *\"[^\"\\r\\n]*\"){7} *}")) {
                        Matcher m = Pattern.compile("\\{\\s*[^{}]+\\s*}").matcher(line);
                        if (m.find()) {
                            if (Add.addFromScript(commandHandler, Add.isItIdUnique(commandHandler, commandHandler.getGroups().size()), m.group(), 2,  dbUnit))
                                System.out.println("Элемент добавлен");
                            continue;
                        }
                    }
                    ParsedCommand pc = new ParsedCommand(line);
                    Command cmd = commands.get(pc.getCommand().toLowerCase());
                    if (cmd == null) {
                        System.out.println(commandHandler.getErrMsg() + " in executeScript");
                        continue;
                    }
                    if (cmd.getName().toLowerCase().equals("execute_script") && pc.getArgs()[0].toLowerCase().equals(args[0].toLowerCase())) {
                        System.out.println("Скрипт вызывает сам себя, Удалите в файле эту строчку :)");
                    } else {
                        cmd.execute(commandHandler, dbUnit, pc.getArgs());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Выполнено";
        } else return "fail";

    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return " file_name : считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, " +
                "в котором их вводит пользователь в интерактивном режиме.";
    }
}

