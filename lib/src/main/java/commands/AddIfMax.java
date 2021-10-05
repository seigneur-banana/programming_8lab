package commands;

import appliances.CommandHandler;
import appliances.Person;
import appliances.Semester;
import appliances.StudyGroup;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class AddIfMax extends Command {
    private String name, sem;
    private double xCor = 0, yCor = 0;
    private int count = 0, transfer = 0, mark = 0, id = 0, max = 0;

    public AddIfMax(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        /*if (args != null && args.length == 1) {
            try {
                max = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("As an argument, not Integer or <0");
                return false;
            }

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
                System.out.print("Enter coordinates x y> ");
                try {
                    xCor = Double.parseDouble(scanner.nextLine());
                    yCor = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input format, please try again.");
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

            System.out.print("Enter AVG mark> ");
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp >= 0 && temp <= 5) mark = temp;
            } catch (Exception e) {
                System.out.println("Invalid input format, assigned 0");
            }

            do {
                System.out.print("Enter semester number> ");
                try {
                    sem = scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input format, please try again.");
                }
            } while (true);
            return true;
        }
        return false;*/

        try{
            if(args.length == 1){
                Object obj = new JSONParser().parse(args[0]);
                JSONObject jo = (JSONObject) obj;
                if (!jo.isEmpty()) {
                    name = (String) jo.get("name");
                    try{
                        yCor = (double) jo.get("yCoordinate");
                        xCor = (double) jo.get("xCoordinate");
                    }
                    catch (Exception e){e.printStackTrace();}
                    commandHandler.setCoordinates(yCor, xCor);

                    try{ count = Integer.parseInt(jo.get("count").toString()); }
                    catch (Exception e){e.printStackTrace();}
                    try{ transfer = Integer.parseInt(jo.get("transferred").toString()); }
                    catch (Exception e){e.printStackTrace();}
                    try{ mark = Integer.parseInt(jo.get("avgMark").toString()); }
                    catch (Exception e){e.printStackTrace();}

                    sem = (String) jo.get("semester");
                    id = new Random().nextInt(3);
                    return true;
                }
                else return false;
            }
            return false;
        }catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        Person admin;
        Semester semestr = null;

        List<StudyGroup> list = commandHandler.sortGroups();
        Collections.sort(list);

        for (StudyGroup temp : commandHandler.getGroups()) {
            if (temp.getStudentsCount() > max) {
                max = temp.getStudentsCount();
            }
        }

        if (commandHandler.getGroups().size() == 0 || count > max) {
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
                        Add.isItIdUnique(commandHandler, commandHandler.getGroups().size()),
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
                            return "The item was successfully added!";
                        } else {
                            return "When adding an element, an SQL error occurred!";
                        }
                    }
                }

                return "The item was successfully added!";
            } catch (Exception e) {
                return "The addition was not completed";
            }
        } else {
            return "The element is not the maximum";
        }

    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return " {id_StudyGroup} : add new " +
                "an element is added to a collection if its value exceeds the value of the largest element of this collection";
    }
}
