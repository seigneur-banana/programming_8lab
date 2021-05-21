package appliances;

import commands.*;

import java.util.*;

public class CommandHandler {
    private static final String ERR_MSG = "Command not found";

    private Queue<String> history = new LinkedList<>();
    private Map<String, Command> commands = new HashMap<>();
    private Map<Integer, Location> locations = new HashMap<>();

    private Map<Integer, Coordinates> coordinates = new HashMap<>();
    private Map<Integer, Person> persons = new HashMap<>();
    private Set<StudyGroup> groups = new LinkedHashSet<>();
    private Date time = new Date();

    {
        Command cmd = new History();
        commands.put(cmd.getName(), cmd);
        cmd = new Help();
        commands.put(cmd.getName(), cmd);
        cmd = new Exit();
        commands.put(cmd.getName(), cmd);
        cmd = new Save();
        commands.put(cmd.getName(), cmd);
        cmd = new ExecuteScript();
        commands.put(cmd.getName(), cmd);
        cmd = new Add();
        commands.put(cmd.getName(), cmd);
        cmd = new Info();
        commands.put(cmd.getName(), cmd);
        cmd = new Show();
        commands.put(cmd.getName(), cmd);
        cmd = new Clear();
        commands.put(cmd.getName(), cmd);
        cmd = new AddIfMax();
        commands.put(cmd.getName(), cmd);
        cmd = new RemoveById();
        commands.put(cmd.getName(), cmd);
        cmd = new FilterContainsName();
        commands.put(cmd.getName(), cmd);
        cmd = new PrintDescending();
        commands.put(cmd.getName(), cmd);
        cmd = new RemoveAllBySemesterEnum();
        commands.put(cmd.getName(), cmd);
        cmd = new RemoveGreater();
        commands.put(cmd.getName(), cmd);
        cmd = new Update();
        commands.put(cmd.getName(), cmd);
    }

    public CommandHandler() {
        FileParser io = new FileParser();
        try {
            io.read(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приветствие!!! @Допиши сюда что-то хорошее@ ");
            while (true) {
                try {
                    System.out.print("> ");
                    String str = scanner.nextLine();

                    ParsedCommand pc = new ParsedCommand(str);
                    if (pc.getCommand() == null || "".equals(pc.getCommand())) {
                        continue;
                    }
                    Command cmd = commands.get(pc.getCommand().toLowerCase());

                    if (cmd == null) {
                        System.out.println("Команда не распознана, список команд => help");
                        continue;
                    }
                    if (!cmd.execute(this, pc.getArgs())) {
                        System.out.println("Команда не выполнена, неверный аргумент(ы)");
                    }

                    history.add(str);
                    if (history.size() == 9) {
                        history.remove();
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка вызовы функции");
                    break;
                }
            }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public Queue<String> getHistory() {
        return history;
    }

    public Map<Integer, Location> getLocations() {
        return locations;
    }

    public Map<Integer, Coordinates> getCoordinates() {
        return coordinates;
    }

    public Map<Integer, Person> getPersons() {
        return persons;
    }

    public Set<StudyGroup> getGroups() {
        return groups;
    }

    public String getErrMsg() {
        return ERR_MSG;
    }

    public Date getTime() {
        return time;
    }

    public void setLocations(Integer y, Float x, String name) {
        Location tmp = new Location(y, x, name);
        locations.put(locations.size(), tmp);
    }

    public void setPersons(String name, int height, Color eyeColor, Color hairColor, Country nationality, Location location) {
        Person tmp = new Person(name, height, eyeColor, hairColor, nationality, location);
        persons.put(persons.size(), tmp);
    }

    public void setCoordinates(Double y, double x) {
        Coordinates tmp = new Coordinates(y, x);
        coordinates.put(coordinates.size(), tmp);
    }

    public void setGroups(Integer id, String name, Coordinates coordinates, int count, int transfer, int mark, Semester sem, Person admin) {
        StudyGroup tmp = new StudyGroup(id, name, coordinates, new Date(), count, transfer, mark, sem, admin);
        groups.add(tmp);
    }

    public void clearGroups() {
        groups.clear();
    }

    public List<StudyGroup> sortGroups() {
        List<StudyGroup> list = new ArrayList<>(groups);
        Collections.sort(list);
        return list;
    }

}
