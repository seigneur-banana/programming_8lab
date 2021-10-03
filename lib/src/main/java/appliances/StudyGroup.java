package appliances;

import major.User;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Date;

public class StudyGroup implements Comparable<StudyGroup> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private int transferredStudents; //Значение поля должно быть больше 0
    private int averageMark; //Значение поля должно быть больше 0
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null
    private User user;

    public StudyGroup(Integer id, String name, Coordinates coordinates, Date date, int count, int transfer, int mark, Semester sem, Person admin, User user) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = date;
        this.studentsCount = count;
        this.transferredStudents = transfer;
        this.averageMark = mark;
        this.semesterEnum = sem;
        this.groupAdmin = admin;
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" : " + id + "," +
                "\"name\" : \"" + name + "\"," +
                "\"xCoordinate\" : " + coordinates.getX() + "," +
                "\"yCoordinate\" : " + coordinates.getY() + "," +
                "\"creationDate\" : \"" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(creationDate) + "\"," +
                "\"count\" : " + studentsCount + "," +
                "\"transferred\" : " + transferredStudents + "," +
                "\"avgMark\" : " + averageMark + "," +
                "\"semester\" : \"" + semesterEnum + "\"," +
                "\"personName\" : \"" + groupAdmin.getName()+ "\"," +
                "\"height\" : " + groupAdmin.getHeight()+ "," +
                "\"eyeColor\" : \"" + groupAdmin.getEyeColor()+ "\"," +
                "\"hairColor\" : \"" + groupAdmin.getHairColor()+ "\"," +
                "\"country\" : \"" + groupAdmin.getCountry()+ "\"," +
                "\"locationName\" : \"" + groupAdmin.getLocation().getName()+ "\"," +
                "\"locationX\" : " + groupAdmin.getLocation().getX()+ "," +
                "\"locationY\" : " + groupAdmin.getLocation().getY()+ "," +
                "\"owner\" : \"" + user.getName() + "\"" +
                "}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCount(Integer count) {
        this.studentsCount = count;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Double getX(){
        return  coordinates.getX();
    }
    public Double getY(){
        return  coordinates.getY();
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public int getTransferredStudents() {
        return transferredStudents;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }
    public String getPersonName(){
        return groupAdmin.getName();
    }
    public Integer getPersonHeight(){
        return groupAdmin.getHeight();
    }
    public Color getPersonEyeColor(){
        return groupAdmin.getEyeColor();
    }
    public Color getPersonHairColor(){
        return groupAdmin.getHairColor();
    }
    public Country getPersonCountry(){
        return groupAdmin.getCountry();
    }
    public String getPersonLocation(){
        return groupAdmin.getLocation().getName();
    }

    @Override
    public int compareTo(StudyGroup o) {
        return this.studentsCount - o.studentsCount;
    }

    public static class DateComparator implements Comparator<StudyGroup> {

        @Override
        public int compare(StudyGroup o1, StudyGroup o2) {
            return o1.creationDate.compareTo(o2.creationDate);
        }
    }
    public User getUser() {
        return user;
    }
    public String getUserName() {
        return user.getName();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Comparator<StudyGroup> byIdComparator = (p1, p2) -> {
        if (p1.id.equals(p2.id)) {
            System.out.println("Что-то пошло не так: у двух продуктов один id!");
            return 0;
        }
        return (p1.id < p2.id) ? -1 : 1;
    };
    public static Comparator<StudyGroup> byPriceComparator = (p1, p2) -> Float.compare(p1.studentsCount, p2.studentsCount);

}
