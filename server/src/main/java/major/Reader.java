package major;

import commands.Command;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;

import static major.Main.getDBUnit;

public class Reader extends Thread{
    private final DatagramSocket socket;
    private final DatagramPacket packet;
    private final byte[] b;

    public Reader(DatagramSocket socket, DatagramPacket packet, byte[] b) {
        this.socket = socket;
        this.packet = packet;
        this.b = b;
    }

    @Override
    public void run() {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Command command = (Command) objectInputStream.readObject();

            new Writer(socket, packet, command).start();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialization error");
        } catch (ClassCastException e) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                User user = (User) objectInputStream.readObject();

                if (user.getCheckOrAdd()) {
                    user = getDBUnit().checkUser(user);
                } else {
                    user = getDBUnit().addUserToDB(user);
                }

                new Writer(socket, packet, user).start();
            } catch (IOException | ClassNotFoundException e1) {
                System.out.println("Deserialization error");
            }
        }
    }
}
