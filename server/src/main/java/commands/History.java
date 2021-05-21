package commands;

import appliances.CommandHandler;

public class History implements Command {

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args == null) {

            for (Object object : commandHandler.getHistory()) {
                System.out.println((String) object);
            }
            return true;
        } else return false;
        /*if (args == null) {
            Iterator iterator = queue.iterator();
            while (iterator.hasNext()) {
                System.out.println((String) iterator.next());
            }
        }*/
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return " : вывести последние 8 команд (без их аргументов)";
    }
}
