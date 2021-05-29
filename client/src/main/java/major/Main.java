package major;

import appliances.CommandHandler;
import commands.Show;

public class Main {
    private static String[] args;

    public static void main(String[] args) {
        Main.args = args;
        CommandHandler ch = new CommandHandler();
        Show sh = new Show();
        new Thread(new Client()).start();
        Client.send(sh);
        ch.execute();
    }
    public static String[] getArgs() {
        return args;
    }
}