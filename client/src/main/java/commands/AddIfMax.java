package commands;

import appliances.CommandHandler;
import appliances.StudyGroup;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AddIfMax implements Command {
    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null && args.length == 1) {
            int max;
            try {
                max = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }

            String name;
            Scanner scanner = new Scanner(System.in);
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
        if (args != null) {
            if (args.length != 1) return false;
            int count;
            try {
                count = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("В качестве аргумента не Integer или <0");
                return false;
            }

            if (count < 0) return false;
            List<StudyGroup> list = commandHandler.sortGroups();
            Collections.sort(list);

            if (commandHandler.getGroups().size() == 0) {
                Command cmd = commandHandler.getCommands().get("add");
                if (cmd.execute(commandHandler, "studygroup")) {
                    System.out.println("Добавлен элемнт");
                } else System.out.println("Команда не выполнена, неверный аргумент(ы)");
            } else {
                if (count > list.get(commandHandler.getGroups().size() - 1).getStudentsCount()) {
                    Command cmd = commandHandler.getCommands().get("add");
                    if (cmd.execute(commandHandler, "studygroup")) {
                        list = commandHandler.sortGroups();
                        Collections.sort(list, new StudyGroup.DateComparator());
                        list.get(commandHandler.getGroups().size() - 1).setCount(count);
                    } else System.out.println("Команда не выполнена, неверный аргумент(ы)");
                }
            }
        }
        return true;
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
