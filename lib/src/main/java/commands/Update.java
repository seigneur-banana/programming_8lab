package commands;

import appliances.CommandHandler;
import appliances.Person;
import appliances.Semester;
import appliances.StudyGroup;
import jdk.nashorn.internal.runtime.ECMAException;
import major.DBUnit;
import major.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;

public class Update extends Command {
    private String name, sem;
    private double xCor = 0, yCor = 0;
    private int count = 0, transfer = 0, mark = 0, idAdmin = 0;
    private Integer idUpdate = 0;

    public Update(User user) {
        super(user);
    }

    @Override
    public boolean validation(CommandHandler commandHandler, String... args) {
        if (args != null && args.length == 1) {
            try{
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
                    try{ idUpdate = Integer.parseInt(jo.get("id").toString()); }
                    catch (Exception e){e.printStackTrace();}

                    return true;
                }
                else return false;
            }catch (Exception e) {e.printStackTrace(); return false;}

        }
        return false;
            /*try {
                idUpdate = Integer.parseInt(args[0]);
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
                    idAdmin = Integer.parseInt(scanner.nextLine());
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
                    System.out.println("Invalid input format, please try again.");
                }
            } while (true);
            return true;
        }
        return false;*/
    }
    @Override
    public synchronized String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
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

                    StudyGroup tmp = new StudyGroup(
                            idUpdate,
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

                    if (dbUnit.updateGroupInDB(tmp)) {
                        System.out.println("Added to the database");
                    } else {
                        return "When adding an element, an SQL error occurred!";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error, not updated";
                }
                list = commandHandler.sortGroups();
                Collections.sort(list, new StudyGroup.DateComparator());
                list.get(commandHandler.getGroups().size() - 1).setId(idUpdate);
                return "The element has been updated";
            }
        }
        return "Nothing was found with this id";

    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return " id {element} : update the value of a collection element whose id is equal to the specified one";
    }
}
