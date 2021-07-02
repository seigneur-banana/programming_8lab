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
                    System.out.println("Введите пароль для входа в программу-сервер:");
                    int errorId = dbUnit.checkUser(new User("admin", in.nextLine())).getErrorId();
                    if (errorId == 0) {
                        System.out.println("Вы успешно вошли!");
                        break;
                    } else if (errorId == 1) {
                        System.out.println("Неправильный пароль!");
                    } else if (errorId == 2) {
                        System.out.println("Пользователя admin нет в базе данных! Запуск программы невозможен, программа завершает работу.");
                        System.exit(1);
                    } else if (errorId == 3) {
                        System.out.println("При проверке пароля возникла ошибка SQL!");
                    }
                    System.out.println("Для выхода введите exit (без пробелов), а для повторного ввода пароля - любую строку или нажмите Enter.");
                    if (in.nextLine().equals("exit")) {
                        System.out.println("Программа завершает работу.");
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
                System.out.println("Возникла ошибка: " + e.getMessage());
                System.out.println("Программа завершает работу, т.к. нет подключения к базе данных!");
            }
        }
        else {
            System.out.println("Программа не запущена");
        }
    }
    public static boolean setPort(String[] args) {
        if (args.length >= 2) {
            System.out.println("Пытаемся запустить сервер на порте " + args[1] + "...");
            try {
                int port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535) {
                    throw new NumberFormatException();
                } else {
                    Main.port = port;
                }
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Сервер не запущен, так как указан неправильный формат порта!\n(число от 1 до 65535 должно быть передано вторым аргументом командной строки)");
                }
        } else {
            System.out.println("Сервер не запущен, так как не указан порт!\n(число от 1 до 65535 должно быть передано вторым аргументом командной строки)");
        }
        return false;
    }

    public static boolean setConnectionDetails(String[] args) {
        if (args.length > 0) {
            if (!Files.exists(Paths.get(args[0]))) {
                System.out.println("Файл для подключения к базе данных с именем " + args[0] + " не существует!");
            } else if (Files.isDirectory(Paths.get(args[0]))) {
                System.out.println("Файл для подключения к базе данных с именем " + args[0] + " не может быть открыт - в качестве файла была передана директория!");
            } else if (!Files.isRegularFile(Paths.get(args[0]))) {
                System.out.println("Файл для подключения к базе данных с именем " + args[0] + " не может быть открыт -  был передан специальный файл!");
            } else if (!Files.isReadable(Paths.get(args[0]))) {
                System.out.println("Файл для подключения к базе данных с именем " + args[0] + " не может быть открыт - нет прав на чтение!");
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
                                System.out.println("В файле для подключения к базе данных с именем " + args[0] + " отсутсвует password!");
                            }
                        } else {
                            System.out.println("В файле для подключения к базе данных с именем " + args[0] + " отсутсвуют username и password!");
                        }
                    } else {
                        System.out.println("Файл для подключения к базе данных с именем " + args[0] + " пуст!");
                    }
                } catch (IOException e) {
                    System.out.println("Файл для подключения к базе данных с именем " + args[0] + " не существует!");
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
