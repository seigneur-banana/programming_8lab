package major;

import appliances.CommandHandler;
import commands.Show;

public class Main {
    public static void main(String[] args) {
        if (args.length<2) {
            System.out.println("The program is not running, because the IP (or hostname) and the server port are not passed!\n" +
                    "(They must be passed through command-line arguments. IP format: xxx. xxx.xxx. xxx; hostname format: non-empty string; port format: a number from 1 to 65535.)");
        } else {
            try{
                int port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535) {
                    throw new NumberFormatException();
                } else {
                    Client.setProperties(args[0], port);
                    Client.setUser();
                    CommandHandlerForClient ch = new CommandHandlerForClient(Client.getUser());
                    ch.execute(System.in);
                }
            }
            catch (NumberFormatException e){
                System.out.println("The program is not running because the wrong port format is specified!\n" +
                        "(the number from 1 to 65535 should be passed as the second command line argument)");
            }

        }
    }
}