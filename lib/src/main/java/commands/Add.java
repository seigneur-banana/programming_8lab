package commands;

import appliances.*;
import major.DBUnit;
import major.User;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Add extends Command {
    private String name, sem;
    private double xCor = 0, yCor = 0;
    private int count = 0, transfer = 0, mark = 0, id = 0;

    public Add(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("StudyGroups, enter data(Required fields *):" +
                    "\n1*. Name (String)\n2*. id Admin (Person)\n3. Coordinates x y " +
                    "\n4. Count of students (int).\n5. Transferred students (int)\n6. Avg mark (int, знач > 0)" +
                    "\n7. NUM of SEM.");

            do {
                System.out.print("Name> ");
                name = scanner.nextLine();
                if (!name.equals("")) break;
                else System.out.println("Invalid input format, please try again.");
            } while (true);

            do {
                try {
                    System.out.print("Enter id_Person for Admin> ");
                    id = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input format, please try again.");
                }
            } while (true);
            do {
                try {
                    System.out.print("Enter coordinate x> ");
                    xCor = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter coordinate y> ");
                    yCor = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input format, please try again");
                }
            } while (true);

            System.out.print("Enter count of students> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0) count = temp;
            } catch (Exception e) {
                System.out.println("Invalid input format, assigned 0");
            }

            System.out.print("Enter count of transferred students> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0) transfer = temp;
            } catch (Exception e) {
                System.out.println("Invalid input format, assigned 0");
            }

            System.out.print("Enter average mark of the group> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0 && temp <= 5) mark = temp;
            } catch (Exception e) {
                System.out.println("Invalid input format, assigned 0");
            }

            do {
                System.out.print("Enter number of semester> ");
                try {
                    sem = scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input format, please try again");
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
            /*commandHandler.setGroups(
                    new_id,
                    name,
                    commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),
                    count,
                    transfer,
                    mark,
                    semestr,
                    admin,
                    user
            );*/
            StudyGroup tmp = new StudyGroup(
                    isItIdUnique(commandHandler, commandHandler.getGroups().size()),
                    name,
                    commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),
                    new Date(),
                    count,
                    transfer,
                    mark,
                    semestr,
                    admin,
                    user
            );
            commandHandler.getGroups().add(tmp);

            if (dbUnit.addGroupToDB(tmp)) {
                return "The item was successfully added!";
            } else {
                return "An SQL error occurred while adding an element!";
            }

        } catch (Exception e) {
            return "Error when adding: "+ e.toString();
        }
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return " {element} : add new element";
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

    public static boolean addFromScript(CommandHandler commandHandler, Integer idGroup, String str, int choice, DBUnit dbUnit) throws ParseException {
        if (choice == 1) {
            boolean match = false;
            for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); )
                if (idGroup.equals(iterator.next().getId())) match = true;
            if (!match) {
                System.out.println("There was no element with this id . . .");
                return false;
            }
            commandHandler.getGroups().removeIf(studyGroup -> idGroup.equals(studyGroup.getId()));
        }
        ArrayList<String> ar = FileParser.parseJSON(str);
        double corY = 0.0, corX = 0.0;
        int count = 0, transfer = 0, mark = 0, id = 0;
        Semester sem = null;
        String name = ar.get(0);
        try {
            corY = Double.parseDouble(ar.get(1));
        } catch (Exception e) {
                System.out.println("Error when reading the coordinate Y");
        }
        try {
            corX = Double.parseDouble(ar.get(2));
        } catch (Exception e) {
            System.out.println("Error when reading the coordinate X");
        }
        try {
            if (Integer.parseInt(ar.get(3)) > 0) count = Integer.parseInt(ar.get(3));
        } catch (Exception e) {
            System.out.println("Error when reading the COUNT of STUDENTS");
        }
        try {
            if (Integer.parseInt(ar.get(4)) > 0) transfer = Integer.parseInt(ar.get(4));
        } catch (Exception e) {
            System.out.println("Error when reading the COUNT OF TRANSFERRED STUDENTS");
        }
        try {
            if (Integer.parseInt(ar.get(5)) > 0 && Integer.parseInt(ar.get(5)) <= 5)
                mark = Integer.parseInt(ar.get(5));
        } catch (Exception e) {
            System.out.println("Error when reading AVG MARK");
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
            System.out.println("Error when reading the admin id");
        }
        commandHandler.setCoordinates(corY, corX);
        User user1 = new User("admin", "admin");
        commandHandler.setGroups(
                idGroup,                                                                        //id group
                name,                                                                           //name group
                commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),//coordinates
                count,
                transfer,
                mark,
                sem,
                commandHandler.getPersons().get(id),                                             //admin
                user1
        );

        for (Iterator<StudyGroup> iterator = commandHandler.getGroups().iterator(); iterator.hasNext(); ) {
            if (idGroup.equals(iterator.next().getId())) {
                return dbUnit.addGroupToDB((StudyGroup) iterator);
            }
        }
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
