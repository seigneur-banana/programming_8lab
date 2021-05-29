package major;
import appliances.CommandHandler;
import commands.Command;

import java.io.*;
import java.net.*;
import java.util.logging.Level;

import static major.Main.*;

public class Server extends Thread {
    private DatagramSocket socket;

    public Server() {
        if (/*getArgs().length < 2*/false) {
            System.out.println("Сервер не запущен, так как не указан порт!\n(число от 0 до 65535 должно быть передано вторым аргументом командной строки)");
        } else {
           // System.out.println("Пытаемся запустить сервер на порте "+getArgs()[0]+"...");
            try {
                socket = new DatagramSocket(/*(int) Long.parseLong(getArgs()[0])*/1337);
                System.out.println("Сервер успешно запущен!");
            } catch (SocketException e) {
                System.out.println("Не удалось запустить сервер на этом порте!");
            } catch (NumberFormatException e) {
                System.out.println("Сервер не запущен, так как указан неправильный формат порта!\n(число от 0 до 65535 должно быть передано вторым аргументом командной строки)");
            }
        }
    }

    public void run() {
        while(true) {
            try {
                byte[] b = new byte[10000];
                DatagramPacket packet = new DatagramPacket(b, b.length);

                socket.receive(packet);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Command command = (Command) objectInputStream.readObject();

                try {
                     String response = command.execute(Main.getCommandHadler());
                     b = response.getBytes();
                    packet = new DatagramPacket(b, b.length, packet.getAddress(), packet.getPort());
                    socket.send(packet);
                } catch (IOException e) {
                    System.out.println("Ошибка");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}