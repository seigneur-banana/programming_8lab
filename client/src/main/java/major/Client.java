package major;

import commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;


public class Client {
    public static void send(Command command) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
            byte[] b = byteArrayOutputStream.toByteArray();

            try {
                SocketAddress address = new InetSocketAddress("127.0.0.1", 1337);
                DatagramSocket socket = new DatagramSocket();
                socket.setSoTimeout(10000);
                DatagramPacket packet = new DatagramPacket(b, b.length, address);
                socket.send(packet);

                try {
                    b = new byte[10000];
                    packet = new DatagramPacket(b, b.length);
                    socket.receive(packet);
                    System.out.println(new String(b));
                } catch (SocketTimeoutException e) {
                    System.out.println("Время ожидания ответа от сервера истекло!");
                }
            } catch (SocketException e) {
                System.out.println("Ошибка отправки пакета!");
            } catch (IllegalArgumentException e) {
                System.out.println("Не удаётся подключиться к серверу!");
            }
        } catch (IOException e) {
            System.out.println("Ошибка сериализации");
        }
    }
}