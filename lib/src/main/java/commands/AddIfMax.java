package commands;

import appliances.CommandHandler;
import appliances.Person;
import appliances.Semester;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AddIfMax extends Command {
    private String name, sem;
    private double xCor = 0, yCor = 0;
    private int count = 0, transfer = 0, mark = 0, id = 0, max;

    public AddIfMax(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null && args.length == 1) {
            try {
                max = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }

            Scanner scanner = new Scanner(System.in);

            System.out.println("Чтобы добавить StudyGroups, введите такие поля, как (Обязательные поля помечены *):" +
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
                    sem = scanner.nextLine();
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
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        Person admin;
        Semester semestr = null;

        List<StudyGroup> list = commandHandler.sortGroups();
        Collections.sort(list);

        if (commandHandler.getGroups().size() == 0 || max > list.get(commandHandler.getGroups().size() - 1).getStudentsCount()) {
            try {
                try {
                    admin = commandHandler.getPersons().get(id);
                } catch (Exception e) {
                    admin = null;
                }

                for (Semester semester : Semester.values()) {
                    if (sem.toLowerCase().equals(semester.name().toLowerCase())) {
                        semestr = semester;
                        break;
                    }
                }

                commandHandler.setCoordinates(yCor, xCor);
                commandHandler.setGroups(
                        max,
                        name,
                        commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),
                        count,
                        transfer,
                        mark,
                        semestr,
                        admin,
                        user
                );
                for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                    if (max == iterator.next().getId()) {
                        if (dbUnit.addGroupToDB((StudyGroup)iterator)) {
                            return "Элемент успешно добавлен!";
                        } else {
                            return "При добавлении элемента возникла ошибка SQL!";
                        }
                    }
                }

                return "Элемент добавлен";
            } catch (Exception e) {
                return "Добавление не выполнено";
            }
        } else {
            return "Элемент не максимальный";
        }

    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return " {id_StudyGroup} (с которым вы хотите добавить новый элемент) : добавить новый " +
                "элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
