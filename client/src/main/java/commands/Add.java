package commands;

import appliances.*;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Add implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null && args.length == 1 && args[0].toLowerCase().equals("studygroup")) {
            Scanner scanner = new Scanner(System.in);
            String name;
            double xCor = 0, yCor = 0;
            int count = 0, transfer = 0, mark = 0, id = 0, sem = 0;

            System.out.println("StudyGroups, введите такие поля, как (Обязательные поля помечены *):" +
                    "\n1*. Название Name (String)\n2*. id Админа (Person)\n3. Координаты x y " +
                    "\n4. Кол-во студентов (int).\n5. Перевед-ые студенты (int)\n6. Средн.Оценка (int, знач > 0)" +
                    "\n7. Номер семестра.");

            do {
                System.out.print("Введите Название> ");
                name = scanner.nextLine();
                if (!name.equals("")) break;
                else System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
            } while (true);

            do {
                try {
                    System.out.print("Введите id_Person для выбора Админа группы> ");
                    id = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                }
            } while (true);
            do {
                System.out.print("Введите Координаты x y> ");
                try {
                    xCor = Double.parseDouble(scanner.nextLine());
                    yCor = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Неверный формат координат, введите заново");
                }
            } while (true);

            System.out.print("Введите Количество студентов> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0) count = temp;
            } catch (Exception e) {
                System.out.println("Неверный формат, присвоен 0");
            }

            System.out.print("Введите Количество переведенных студентов> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0) transfer = temp;
            } catch (Exception e) {
                System.out.println("Неверный формат, присвоен 0");
            }

            System.out.print("Введите значение сред. оценки в группе> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0 && temp <= 5) mark = temp;
            } catch (Exception e) {
                System.out.println("Неверный формат, присвоен 0");
            }

            do {
                System.out.print("Введите номер семестра> ");
                try {
                    sem = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Неверный формат координат, введите заново");
                }
            } while (true);
            return true;
        }
        return false;
    }

    @Override
    public boolean execute(CommandHandler commandHandler, String... args) {
        if (args != null && args.length == 1) {
            Scanner scanner = new Scanner(System.in);
            switch (args[0].toLowerCase()) {
                case "coordinates": {
                    double x, y;
                    System.out.println("Чтобы добавить элемент в Coordinates, введите такие поля, как (Обязательные поля помечены *):" +
                            "\n1*. Координата Y (Double Значение поля должно быть больше -748)\n2. Координата Х (double).");
                    do {
                        try {
                            System.out.print("Введите Y> ");
                            y = Double.parseDouble(scanner.nextLine()); //y = scanner.nextDouble();
                            if (y < -748) {
                                System.out.println("Введено неверное значение для Y (меньше -748)");
                            } else break;
                        } catch (Exception e) {
                            System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                        }
                    } while (true);
                    try {
                        System.out.print("Введите X> ");
                        x = Double.parseDouble(scanner.nextLine()); //x = scanner.nextDouble();
                    } catch (Exception e) {
                        x = 0;
                    }
                    commandHandler.setCoordinates(y, x);
                }
                break;

                case "location": {
                    float x;
                    int y;
                    String name;
                    System.out.println("Чтобы добавить элемент в Location, введите такие поля, как (Обязательные поля помечены *):" +
                            "\n1*. Координата X (Float)\n2*. Координата Y (Integer)\n3. Название name (String).");
                    do {
                        try {
                            System.out.print("Введите X> ");
                            x = Float.parseFloat(scanner.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                        }
                    } while (true);
                    do {
                        try {
                            System.out.print("Введите Y> ");
                            y = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                        }
                    } while (true);
                    System.out.print("Введите name> ");
                    name = scanner.nextLine();
                    if (name.equals("")) name = null;
                    commandHandler.setLocations(y, x, name);
                }
                break;

                case "person": {
                    String name, temp;
                    int height;
                    Color eyeColor = null, hairColor = null;
                    Country nationality = null;
                    Location location = null;
                    System.out.print("Чтобы добавить элемент в Person, введите такие поля, как (Обязательные поля помечены *):");
                    System.out.print("\n1*. Name (String)\n2*.PocT Height (int, > 0)\n3. eyeColor (Color).");
                    System.out.println("\n4. ЦветПричёски hairColor (Color).\n5. Национальность nationality (Country)\n6. Локация location (Location).");
                    do {
                        System.out.print("Введите имя>");
                        name = scanner.nextLine();
                        if (!name.equals("")) break;
                        else System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                    } while (true);

                    do {
                        try {
                            System.out.print("Введите height> ");
                            height = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                        }
                    } while (true);

                    System.out.print("Введите ЦветГлаз> ");
                    temp = scanner.nextLine();
                    for (Color clr : Color.values()) {
                        if (temp.toLowerCase().equals(clr.name().toLowerCase())) {
                            eyeColor = clr;
                            break;
                        }
                    }

                    System.out.print("Введите ЦветПричёски> ");
                    temp = scanner.nextLine();
                    for (Color clr : Color.values()) {
                        if (temp.toLowerCase().equals(clr.name().toLowerCase())) {
                            hairColor = clr;
                            break;
                        }
                    }

                    System.out.print("Введите Национальность> ");
                    temp = scanner.nextLine();
                    for (Country cntr : Country.values()) {
                        if (temp.toLowerCase().equals(cntr.name().toLowerCase())) {
                            nationality = cntr;
                            break;
                        }
                    }

                    System.out.println("Доступные Locations: " + commandHandler.getLocations());
                    System.out.print("Введите id location> ");
                    Integer idLocation;
                    try {
                        idLocation = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Неверный формат id Location");
                        idLocation = null;
                    }
                    if (idLocation != null && idLocation < commandHandler.getLocations().size() && idLocation >= 0)
                        location = commandHandler.getLocations().get(idLocation);
                    commandHandler.setPersons(name, height, eyeColor, hairColor, nationality, location);
                }
                break;

                case "studygroup": {
                    String name;
                    Coordinates coordinates = null;
                    int count = 0, transfer = 0, mark = 0;
                    Semester sem = null;
                    Person admin;

                    if (commandHandler.getPersons().size() == 0) {
                        System.out.println("В базе нет доступных Person для выбора админом группы," +
                                " пожалуйста, сначала добавьте Person (add Person)");
                        return false;
                    }
                    System.out.println("StudyGroups, введите такие поля, как (Обязательные поля помечены *):" +
                            "\n1*. Название Name (String)\n2*. id Админа (Person)\n3. id Координат (Coordinates)" +
                            "\n4. Кол-во студентов (int).\n5. Перевед-ые студенты (int)\n6. Средн.Оценка (int, знач > 0)" +
                            "\n7. Номер семестра (SemesterEnum).");

                    do {
                        System.out.print("Введите Название> ");
                        name = scanner.nextLine();
                        if (!name.equals("")) break;
                        else System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                    } while (true);

                    do {
                        try {
                            System.out.println("Доступные Persons: " + commandHandler.getPersons());
                            System.out.print("Введите id_Person для выбора Админа группы> ");
                            int tmp = Integer.parseInt(scanner.nextLine());
                            if (tmp >= commandHandler.getPersons().size() || tmp < 0) continue;
                            else admin = commandHandler.getPersons().get(tmp);
                            break;
                        } catch (Exception e) {
                            System.out.println("Неверный формат ввода, пожалуйста, попробуйте снова.");
                        }
                    } while (true);

                    System.out.println("Доступные Coordinates: " + commandHandler.getCoordinates());
                    System.out.print("Введите id_Координат> ");
                    try {
                        int idCoordinates = Integer.parseInt(scanner.nextLine());
                        if (idCoordinates < commandHandler.getCoordinates().size() && idCoordinates >= 0)
                            coordinates = commandHandler.getCoordinates().get(idCoordinates);
                    } catch (Exception e) {
                        System.out.println("Неверный формат id_Coordinates присвоен null");
                    }

                    System.out.print("Введите Количество студентов> ");
                    try {
                        int temp = Integer.parseInt(scanner.nextLine());
                        if (temp >= 0) count = temp;
                    } catch (Exception e) {
                        System.out.println("Неверный формат, присвоен 0");
                    }

                    System.out.print("Введите Количество переведенных студентов> ");
                    try {
                        int temp = Integer.parseInt(scanner.nextLine());
                        if (temp >= 0) transfer = temp;
                    } catch (Exception e) {
                        System.out.println("Неверный формат, присвоен 0");
                    }

                    System.out.print("Введите значение сред. оценки в группе> ");
                    try {
                        int temp = Integer.parseInt(scanner.nextLine());
                        if (temp >= 0 && temp <= 5) mark = temp;
                    } catch (Exception e) {
                        System.out.println("Неверный формат, присвоен 0");
                    }

                    System.out.print("Введите номер семестра> ");
                    String temp = scanner.nextLine();
                    for (Semester semester : Semester.values()) {
                        if (temp.toLowerCase().equals(semester.name().toLowerCase())) {
                            sem = semester;
                            break;
                        }
                    }
                    commandHandler.setGroups(isItIdUnique(commandHandler, commandHandler.getGroups().size()),
                            name, coordinates, count, transfer, mark, sem, admin);
                }
                break;

                default: {
                    System.out.println("Аргумент не распознан, попробуйте снова :) ");
                }
                break;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return " {element} : добавить новый элемент в коллекцию";
    }

    public static int isItIdUnique(CommandHandler commandHandler, Integer id) {
        int result = 0;
        boolean match = false;
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            if (id.equals(iterator.next().getId())) {
                match = true;
                result = id + 1;
                int temp = isItIdUnique(commandHandler, result);
                if (temp > 0) result = temp;
            }
        }
        if (match) return result;
        else return id;
    }

    public static boolean addFromScript(CommandHandler commandHandler, Integer idGroup, String str, int choice) throws ParseException {
        if (choice == 1) {
            boolean match = false;
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); )
                if (idGroup.equals(iterator.next().getId())) match = true;
            if (!match) {
                System.out.println("Элемента с таким id не было . . .");
                return false;
            }
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); )
                if (idGroup.equals(iterator.next().getId()))
                    iterator.remove();
        }
        ArrayList<String> ar = FileParser.parseJSON(str);
        double corY = 0.0, corX = 0.0;
        int count = 0, transfer = 0, mark = 0, id = 0;
        Semester sem = null;
        String name = ar.get(0);
        try {
            corY = Double.parseDouble(ar.get(1));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении координаты Y");
        }
        try {
            corX = Double.parseDouble(ar.get(2));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении координаты X");
        }
        try {
            if (Integer.parseInt(ar.get(3)) > 0) count = Integer.parseInt(ar.get(3));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении кол-ва студентов");
        }
        try {
            if (Integer.parseInt(ar.get(4)) > 0) transfer = Integer.parseInt(ar.get(4));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении кол-ва переведенных сутудентов");
        }
        try {
            if (Integer.parseInt(ar.get(5)) > 0 && Integer.parseInt(ar.get(5)) <= 5)
                mark = Integer.parseInt(ar.get(5));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении ср. оценки");
        }
        for (Semester semester : Semester.values()) {
            if (ar.get(6).toLowerCase().equals(semester.name().toLowerCase())) {
                sem = semester;
                break;
            }
        }
        try {
            if (Integer.parseInt(ar.get(7)) > 0) id = Integer.parseInt(ar.get(7));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении id админа");
        }
        commandHandler.setCoordinates(corY, corX);
        commandHandler.setGroups(
                idGroup,                                                                        //id group
                name,                                                                           //name group
                commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),//coordinates
                count,
                transfer,
                mark,
                sem,
                commandHandler.getPersons().get(id)                                             //admin
        );
        if (choice == 2) {
            List<StudyGroup> list = commandHandler.sortGroups();
            Collections.sort(list);
            if (count > list.get(commandHandler.getGroups().size() - 1).getStudentsCount()) {
                list = commandHandler.sortGroups();
                Collections.sort(list, new StudyGroup.DateComparator());
                list.get(commandHandler.getGroups().size() - 1).setCount(count);
            }
        }
        return true;
    }
}
