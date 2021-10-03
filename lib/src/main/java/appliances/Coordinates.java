package appliances;

public class Coordinates {
    private double x;
    private Double y; //Значение поля должно быть больше -748, Поле не может быть null

    public Coordinates(Double y, double x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public String toString() {
        return " (X=" + x + "; Y=" + y + ")";
    }

    public double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public String xToString() {
        return ""+x;
    }
}