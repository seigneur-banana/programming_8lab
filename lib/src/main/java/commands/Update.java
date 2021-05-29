package commands;

import appliances.CommandHandler;
import appliances.Person;
import appliances.Semester;
import appliances.StudyGroup;

import java.util.*;

public class Update extends Command {
    private String name, sem;
    private double xCor = 0, yCor = 0;
    private int count = 0, transfer = 0, mark = 0, idAdmin = 0;
    private Integer idUpdate = 0;

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null && args.length == 1) {
            try {
                idUpdate = Integer.parseInt(args[0]);
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
                    idAdmin = Integer.parseInt(scanner.nextLine());
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
    public synchronized String execute(CommandHandler commandHandler, String... args) {
        Person admin;
        Semester semestr = null;
        List<StudyGroup> list;
        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            StudyGroup temp = iterator.next();
            if (idUpdate.equals(temp.getId())) {
                iterator.remove();
                try {
                    try {
                        admin = commandHandler.getPersons().get(idAdmin);
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
                            idUpdate,
                            name,
                            commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),
                            count,
                            transfer,
                            mark,
                            semestr,
                            admin
                    );
                } catch (Exception e) {
                    System.out.println("Добавление не выполнено");
                    return "Ошибка, не обновлен";
                }
                list = commandHandler.sortGroups();
                Collections.sort(list, new StudyGroup.DateComparator());
                list.get(commandHandler.getGroups().size() - 1).setId(idUpdate);
                return "Элемент обновлён";
            }
        }
        return "С таким id ничего не найдено";

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return " id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
