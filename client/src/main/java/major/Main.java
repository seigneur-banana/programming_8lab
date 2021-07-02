package major;

import appliances.CommandHandler;
import commands.Show;

public class Main {
    public static void main(String[] args) {
        if (args.length<2) {
            System.out.println("Программа не запущена, так как не переданы IP (или hostname) и порт сервера!\n(Они должны быть переданы через аргументы командной строки. Формат IP: xxx.xxx.xxx.xxx; формат hostname: непустая строка; формат порта: число от 1 до 65535.)");
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
                System.out.println("Программа не запущена, так как указан неправильный формат порта!\n(число от 1 до 65535 должно быть передано вторым аргументом командной строки)");
            }

        }
    }
}