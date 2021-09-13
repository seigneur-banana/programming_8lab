package appliances;

import commands.*;
import major.DBUnit;
import major.User;

import java.util.*;


public class CommandHandler {
    private static final String ERR_MSG = "Command not found";

    protected Queue<String> history = new LinkedList<>();
    protected Map<String, Command> commands = new HashMap<>();
    protected Map<Integer, Location> locations = new HashMap<>();

    protected Map<Integer, Coordinates> coordinates = new HashMap<>();
    protected Map<Integer, Person> persons = new HashMap<>();
    protected Set<StudyGroup> groups = new LinkedHashSet<>();
    protected Date time = new Date();
    protected static Command command;

    public CommandHandler(User user) {
        Command cmd = new History(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Help(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Exit(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Add(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Info(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Show(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Clear(user);
        commands.put(cmd.getName(), cmd);
        cmd = new AddIfMax(user);
        commands.put(cmd.getName(), cmd);
        cmd = new RemoveById(user);
        commands.put(cmd.getName(), cmd);
        cmd = new FilterContainsName(user);
        commands.put(cmd.getName(), cmd);
        cmd = new PrintDescending(user);
        commands.put(cmd.getName(), cmd);
        cmd = new RemoveAllBySemesterEnum(user);
        commands.put(cmd.getName(), cmd);
        cmd = new RemoveGreater(user);
        commands.put(cmd.getName(), cmd);
        cmd = new Update(user);
        commands.put(cmd.getName(), cmd);
    }

    protected void addToHistory(String com) {
        history.add(com);
        if (history.size() == 9) {
            history.remove();
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

    public static Command getCommand() {
        return command;
    }

    public static void setCommand(Command com) {
        CommandHandler.command = com;
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

    public void setGroups(Integer id, String name, Coordinates coordinates, int count, int transfer, int mark, Semester sem, Person admin, User user) {
        StudyGroup tmp = new StudyGroup(id, name, coordinates, new Date(), count, transfer, mark, sem, admin, user);
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
