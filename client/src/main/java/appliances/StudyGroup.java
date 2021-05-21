package appliances;

import java.text.SimpleDateFormat;
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

    StudyGroup(Integer id, String name, Coordinates coordinates, Date date, int count, int transfer, int mark, Semester sem, Person admin) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = date;
        this.studentsCount = count;
        this.transferredStudents = transfer;
        this.averageMark = mark;
        this.semesterEnum = sem;
        this.groupAdmin = admin;
    }

    @Override
    public String toString() {
        return " id: " + id +
                "; name: " + name +
                "; coordinates: " + coordinates +
                "; creationDate: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(creationDate) +
                "; studentsCount: " + studentsCount +
                "; transferredStudents: " + transferredStudents +
                "; averageMark: " + averageMark +
                "; semesterEnum: " + semesterEnum +
                "; groupAdmin: " + groupAdmin.getName() + "\n";
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

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
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
}