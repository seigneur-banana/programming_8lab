
package major;
import commands.Command;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client extends Thread {
    private static String hostname;
    private static int port;
    private static User user;

    public static void setProperties(String hostname, int port) {
        Client.hostname = hostname;
        Client.port = port;
    }

    public static void setUser() {
        System.out.println("Hello client");
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("To log in enter 1; to register enter 2; to exit enter 3 (without spaces):");
                String check = in.nextLine();
                if (!check.equals("1") & !check.equals("2") & !check.equals("3")) {
                    throw new InputMismatchException();
                } else if (check.equals("3")){
                    System.out.println("Goodbye");
                    System.exit(1);
                } else {
                    System.out.println("Enter Login:");
                    String name = in.nextLine();
                    System.out.println("Enter password: ");
                    String password = in.nextLine();
                    User user = new User(name, password);
                    user.setCheckOrAdd(check.equals("1"));
                    user = sendAndReceiveUser(user);
                    if (user != null) {
                        Client.user = user;
                        if (check.equals("1")) {
                            System.out.print("You have successfully logged in! ");
                        } else {
                            System.out.print("You have successfully registered! ");
                        }
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid data!");
            }
        }
    }

    public static void send(Command command) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
            byte[] b = byteArrayOutputStream.toByteArray();

            try {
                    SocketAddress address = new InetSocketAddress(hostname, port);
                    DatagramSocket socket = new DatagramSocket();
                    socket.setSoTimeout(10000);
                    DatagramPacket packet = new DatagramPacket(b, b.length, address);
                    socket.send(packet);

                    try {
                        b = new byte[10000];
                        packet = new DatagramPacket(b, b.length);
                        socket.receive(packet);
                        System.out.println(new String(b).trim());
                    } catch (SocketTimeoutException e) {
                        System.out.println("The waiting time for a response from the server has expired!");
                    }
            } catch (SocketException e) {
                System.out.println("Error sending the package!");
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to connect to the server!");
            }
        } catch (IOException e) {
            System.out.println("Serialization error");
        }
    }

    public static User sendAndReceiveUser(User user) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            byte[] b = byteArrayOutputStream.toByteArray();

            try {
                SocketAddress address = new InetSocketAddress(hostname, port);
                DatagramSocket socket = new DatagramSocket();
                socket.setSoTimeout(5000);
                DatagramPacket packet = new DatagramPacket(b, b.length, address);
                socket.send(packet);
                try {
                    b = new byte[10000];
                    packet = new DatagramPacket(b, b.length);
                    socket.receive(packet);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    try {
                        user = (User) objectInputStream.readObject();
                        switch (user.getErrorId()) {
                            case 0:
                                return user;
                            case 1:
                                System.out.println("Wrong password! Repeat the input.");
                                break;
                            case 2:
                                System.out.println("There is no user with this name and password! Repeat the input.");
                                break;
                            case 3:
                                System.out.println("An SQL error occurred while checking/creating the username and password! Repeat the input.");
                                break;
                            case 4:
                                System.out.println("A user with this name is already registered! Repeat the input.");
                                break;
                        }
                        return null;
                    } catch (ClassNotFoundException e) {
                        System.out.println("User deserialization error! Repeat the input.");
                        return null;
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("The waiting time for a response about the user from the server has expired! Repeat the input.");
                    return null;
                }
            } catch (SocketException e) {
                System.out.println("Error sending a package about the user! Repeat the input.");
                return null;
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to connect to the server " + hostname + ":" + port + "! Check the connection data, repeat the input.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("User serialization error! Repeat the input.");
            return null;
        }
    }

    public static User getUser() {
        return user;
    }

}