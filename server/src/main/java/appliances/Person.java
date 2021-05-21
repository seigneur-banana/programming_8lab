package appliances;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null

    Person(String name, int height, Color eye, Color hair, Country country, Location location) {
        this.name = name;
        this.height = height;
        this.eyeColor = eye;
        this.hairColor = hair;
        this.nationality = country;
        this.location = location;
    }

    @Override
    public String toString() {
        return " (name: " + name +
                " height: " + height +
                " eyeColor: " + eyeColor +
                " hairColor: " + hairColor +
                " nationality: " + nationality +
                " location: " + location + ")\n";
    }

    public String getName() {
        return name;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public int getHeight() {
        return height;
    }

    public Country getCountry() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }
}
