package appliances;

public enum Color {
    GREEN(1),
    BLUE(2),
    BROWN(3),
    RED(4),
    BLACK(5),
    ORANGE(6);
    private int value;

    Color(int i) {
    }

    public int getValue() {
        return value;
    }

    public static Color getColorById(int id) {

        Color color = null;

        switch (id) {
            case 1:
                color = GREEN;
                break;
            case 2:
                color = BLUE;
                break;
            case 3:
                color = BROWN;
                break;
            case 4:
                color = RED;
                break;
            case 5:
                color = BLACK;
                break;
            case 6:
                color = ORANGE;
                break;

            default:
                break;
        }
        return color;
    }
}
