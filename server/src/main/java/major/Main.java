package major;

import appliances.CommandHandler;

public class Main {
    private static String[] args;

    public static void main(String[] args) {
        CommandHandler ch = new CommandHandler();
        ch.execute();
    }
    public static String[] getArgs() {
        return args;
    }
}
