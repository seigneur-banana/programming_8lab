package major;

import appliances.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;


public class Main {
    private static String url;
    private static String username;
    private static String password;
    private static int port;
    private static Date date;
    private static DBUnit dbUnit;
    private static Listener listener;
    private static CommandHandlerForServer ch;


    private static java.sql.Connection connection;

    public static void main(String[] args) {
        //check
        /*try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:8456/studs", "s297537", "pur526");
            System.out.println("Подключено");
        }catch (Exception e){
            e.printStackTrace();
        }*/

       // ch = new CommandHandler();
        /*FileParser io = new FileParser();
        try {
            io.read(ch);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        if (setConnectionDetails(args)) {
            date = new Date();
            dbUnit = new DBUnit(url, username, password);
            try {
                dbUnit.connect();

                Scanner in = new Scanner(System.in);
                while (true) {
                    System.out.println("Enter the admin password:");
                    int errorId = dbUnit.checkUser(new User("admin", in.nextLine())).getErrorId();
                    if (errorId == 0) {
                        System.out.println("You have successfully logged in!");
                        break;
                    } else if (errorId == 1) {
                        System.out.println("incorrect password!");
                    } else if (errorId == 2) {
                        System.out.println("Admin user don`t exists... goodbye");
                        System.exit(1);
                    } else if (errorId == 3) {
                        System.out.println("checking the password SQL Exception!");
                    }
                    System.out.println("To exit, enter \"exit\" (without spaces), and to re-enter the password, enter any line or press Enter.");
                    if (in.nextLine().equals("exit")) {
                        System.out.println("goodbye");
                        System.exit(1);
                    }
                }
                ch = new CommandHandlerForServer(date, dbUnit, new User("admin"));
                dbUnit.loadCollectionFromDB(
                        (HashMap<Integer, Location>) ch.getLocations(),
                        (HashMap<Integer, Coordinates>) ch.getCoordinates(),
                        (HashMap<Integer, Person>) ch.getPersons(),
                        (LinkedHashSet<StudyGroup>) ch.getGroups()
                );

                if (setPort(args)) {
                    if (!(listener = new Listener()).bind(port)) {
                        listener = null;
                    }
                }
                if (listener != null) {
                    listener.start();
                }


                ch.execute(dbUnit);
            } catch (SQLException e) {
                System.out.println("Exception: " + e.getMessage());
                System.out.println("The program shuts down because there is no connection to the database!");
            }
        }
        else {
            System.out.println("Programm isn't started");
        }
    }
    public static boolean setPort(String[] args) {
        if (args.length >= 2) {
                System.out.println("starting the server on the port: " + args[1] + "...");
            try {
                int port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535) {
                    throw new NumberFormatException();
                } else {
                    Main.port = port;
                }
                return true;
            } catch (NumberFormatException e) {
                System.out.println("The server is not running because the wrong port format is specified!\n" +
                        "(the number from 1 to 65535 should be passed as the second command line argument)");
                }
        } else {
            System.out.println("The server is not running because the wrong port format is specified!\n" +
                    "(the number from 1 to 65535 should be passed as the second command line argument)");
        }
        return false;
    }

    public static boolean setConnectionDetails(String[] args) {
        if (args.length > 0) {
            if (!Files.exists(Paths.get(args[0]))) {
                System.out.println("The file to connect to the database with the name " + args[0] + " doesn't exists!");
            } else if (Files.isDirectory(Paths.get(args[0]))) {
                System.out.println("The file to connect to the database with the name " + args[0] + " it can't be opened - a directory was passed as a file!");
            } else if (!Files.isRegularFile(Paths.get(args[0]))) {
                System.out.println("The file to connect to the database with the name " + args[0] + " can't be opened - a special file was transferred!");
            } else if (!Files.isReadable(Paths.get(args[0]))) {
                System.out.println("The file to connect to the database with the name " + args[0] + " can't be opened - no read rights!");
            } else {
                try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
                    String s;
                    if ((s = in.readLine()) != null) {
                        url = s;
                        if ((s = in.readLine()) != null) {
                            username = s;
                            if ((s = in.readLine()) != null) {
                                password = s;
                                return true;
                            } else {
                                    System.out.println("In the file for connecting to the database with the name " + args[0] + " missing password!");
                            }
                        } else {
                            System.out.println("In the file for connecting to the database with the name " + args[0] + " missing username и password!");
                        }
                    } else {
                        System.out.println("In the file for connecting to the database with the name " + args[0] + " is empty!");
                    }
                } catch (IOException e) {
                    System.out.println("In the file for connecting to the database with the name " + args[0] + " doesn't exists!");
                }
            }
        }
        return false;
    }


    public static CommandHandler getCommandHandler(){
        return ch;
    }

    public static Date getDate() {
        return date;
    }

    public static DBUnit getDBUnit() {
        return dbUnit;
    }
}
