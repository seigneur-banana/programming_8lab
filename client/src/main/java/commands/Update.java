package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.*;

public class Update implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {

        if (args == null) {
            Scanner scanner = new Scanner(System.in);
            String name;
            double xCor = 0, yCor = 0;
            int count = 0, transfer = 0, mark = 0, id = 0, sem = 0;

            System.out.println("Чтобы обновть StudyGroups, введите такие поля, как (Обязательные поля помечены *):" +
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
        if (args != null) {
            if (args.length != 1) return false;

            Integer id;
            boolean result = false;
            List<StudyGroup> list;
            try {
                id = Integer.parseInt(args[0]);
                if (id < 0) return false;
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer");
                return false;
            }
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
                StudyGroup temp = iterator.next();
                if (id.equals(temp.getId())) {
                    iterator.remove();
                    Command cmd = commandHandler.getCommands().get("add");
                    result = cmd.execute(commandHandler, "studygroup");
                    list = commandHandler.sortGroups();
                    Collections.sort(list, new StudyGroup.DateComparator());
                    list.get(commandHandler.getGroups().size() - 1).setId(id);
                }
            }
            if (!result) System.out.println("Элемента с таким ID и не было :)");
            return true;
        } else return false;
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
