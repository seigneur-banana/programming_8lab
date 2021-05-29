package appliances;

public class Location {
    private Float x; //Поле не может быть null
    private Integer y; //Поле не может быть null
    private String name; //Поле может быть null

    Location(Integer y, Float x, String name) {
        this.y = y;
        this.x = x;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ": (X=" + x + "; Y=" + y + ") ";
    }

    public String getName() {
        return name;
    }

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}