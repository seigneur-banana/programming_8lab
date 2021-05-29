package major;

import appliances.CommandHandler;
import appliances.FileParser;
import commands.Save;

public class Main {
    private static CommandHandler ch;
    private static String[] args;

    public static void main(String[] args) {
        ch = new CommandHandler();
        FileParser io = new FileParser();
        try {
            io.read(ch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.args = args;
        Server server = new Server();
        if (server.getSocket() != null) {
            server.setDaemon(true);
            server.start();
        }
        ch.addCommand("save", new Save());
        CommandHandler.switchMode();
        ch.execute();
    }
    public static String[] getArgs() {
        return args;
    }
    public static CommandHandler getCommandHadler(){
        return ch;
    }
}
