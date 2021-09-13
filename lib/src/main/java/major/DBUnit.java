package major;

import appliances.*;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;


import static java.sql.Types.NULL;
import static java.sql.Types.OTHER;

public class DBUnit {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    public DBUnit(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }
    public void loadCollectionFromDB(HashMap<Integer, Location> locations, HashMap<Integer, Coordinates> coordinates, HashMap<Integer, Person> persons, LinkedHashSet<StudyGroup> collection) {
        System.out.println("Trying to load a collection from the database...");
        try {
            PreparedStatement statement = connection.prepareStatement("select * from collections");
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                Coordinates c = new Coordinates(result.getDouble(3), result.getDouble(4));
                Location l = new Location(result.getInt(14), result.getFloat(13), result.getString(15));
                Person p = new Person(result.getString(9),
                        result.getInt(10),
                        Color.valueOf(result.getString(11)),
                        Color.valueOf(result.getString(17)),
                        Country.valueOf(result.getString(12)),
                        l
                );
                StudyGroup group = new StudyGroup(
                        result.getInt(1),
                        result.getString(2),
                        c,
                        result.getTimestamp(5),
                        result.getInt(6),
                        result.getInt(7),
                        result.getInt(8),
                        Semester.valueOf(result.getString(16)),
                        p,
                        new User(result.getString(18))
                );
                locations.put(locations.size(), l);
                coordinates.put(coordinates.size(), c);
                persons.put(persons.size(), p);
                collection.add(group);
                /*group.setCreationDate(result.getTimestamp(13));
                if (Creator.createProduct(product, false) == null) {
                    System.out.println("Элемент с id " + result.getLong(1) + " не загружен из-за перечисленных выше ошибок формата данных!");
                } else {
                    Optional<Organization> optional = organizations.stream().filter(x -> x.equals(product.getManufacturer())).findAny();
                    if (optional.isPresent()) {
                        product.setManufacturer(optional.get());
                    } else {
                        product.getManufacturer().createId(organizations);
                        organizations.add(product.getManufacturer());
                    }
                    product.setId(result.getLong(1));
                    product.setUser(new User(result.getString(14)));
                    collection.add(product);
                }*/
            }
            System.out.println("Elements loaded: " + collection.size() + ".");
        } catch (SQLException e) {
            System.out.println("An SQL error occurred when loading the collection from the database: " + e.getMessage());
        }
    }

    public boolean addGroupToDB(StudyGroup group) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into collections values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setInt(1, group.getId());
            statement.setString(2, group.getName());
            statement.setDouble(3, group.getCoordinates().getX());
            statement.setDouble(4, group.getCoordinates().getY());
            try {
                statement.setTimestamp(5, new java.sql.Timestamp(group.getCreationDate().getTime()));
            }catch (NullPointerException e) {
                statement.setNull(5, NULL);
            }
            statement.setInt(6, group.getStudentsCount());
            statement.setInt(7, group.getTransferredStudents());
            statement.setInt(8, group.getAverageMark());
            statement.setString(9, group.getGroupAdmin().getName());
            statement.setInt(10, group.getGroupAdmin().getHeight());
            try {
            statement.setObject(11, group.getGroupAdmin().getEyeColor(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(11, NULL);
            }
            try {
                statement.setObject(12, group.getGroupAdmin().getCountry(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(12, NULL);
            }
            statement.setFloat(13, group.getGroupAdmin().getLocation().getX());
            statement.setInt(14, group.getGroupAdmin().getLocation().getY());
            statement.setString(15, group.getGroupAdmin().getLocation().getName());

            try {
                statement.setObject(16, group.getSemesterEnum(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(16, NULL);
            }
            try {
                statement.setObject(17, group.getGroupAdmin().getHairColor(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(17, NULL);
            }
            statement.setString(18, group.getUser().getName());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGroupInDB(StudyGroup group) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "update collections set name = ?, " +
                            "cor_x = ?, " +
                            "cor_y = ?, " +
                            "\"students_count\" = ?, " +
                            "\"transferred_students\" = ?, " +
                            "\"average_mark\" = ?, " +
                            "\"person_name\" = ?, " +
                            "\"person_height\" = ?, " +
                            "\"person_eye_color\" = ?, " +
                            "\"person_country\" = ?, " +
                            "\"person_location_x\" = ?, " +
                            "\"person_location_y\" = ?, " +
                            "\"person_location_name\" = ?, " +
                            "\"semester\" = ?, " +
                            "\"person_hair_color\" = ?, " +
                            "\"user_name\" = ? " +
                            "where id = " + group.getId());
            statement.setString(1, group.getName());
            statement.setDouble(2, group.getCoordinates().getX());
            statement.setDouble(3, group.getCoordinates().getY());
            statement.setInt(4, group.getStudentsCount());
            statement.setInt(5, group.getTransferredStudents());
            statement.setInt(6, group.getAverageMark());
            statement.setString(7, group.getGroupAdmin().getName());
            statement.setInt(8, group.getGroupAdmin().getHeight());
            try {
                statement.setObject(9, group.getGroupAdmin().getEyeColor(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(9, NULL);
            }
            try {
                statement.setObject(10, group.getGroupAdmin().getCountry(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(10, NULL);
            }
            statement.setFloat(11, group.getGroupAdmin().getLocation().getX());
            statement.setInt(12, group.getGroupAdmin().getLocation().getY());
            statement.setString(13, group.getGroupAdmin().getLocation().getName());

            try {
                statement.setObject(14, group.getSemesterEnum(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(14, NULL);
            }
            try {
                statement.setObject(15, group.getGroupAdmin().getHairColor(), OTHER);
            } catch (NullPointerException e) {
                statement.setNull(15, NULL);
            }
            statement.setString(16, group.getUser().getName());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeGroupFromDB(StudyGroup studyGroup) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from collections where id = " + studyGroup.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error when deleting");
            return false;
        }
    }

    public User checkUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where name='" + user.getName() + "'");//+"\'and password=\'"+user.getPassword()+"\'");
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (!result.getString(2).equals(user.getPassword())) {
                    user.setErrorId(1); // Неправильный пароль
                }
            } else {
                user.setErrorId(2); // Такого пользователя не существует
            }
        } catch (SQLException e) {
            System.out.println("Error when verifying the user");
            user.setErrorId(3); // Ошибка SQL
        }
        return user;
    }

    public User addUserToDB(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where name='" + user.getName() + "'");
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user.setErrorId(4); // Такой пользователь уже существует
            } else {
                try {
                    statement = connection.prepareStatement("insert into users values (?,?)");
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getPassword());
                    statement.executeUpdate();
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Error when adding a user to the database");
                    user.setErrorId(3); // Ошибка SQL
                }
            }
        } catch (SQLException e) {
            System.out.println("Error when adding a user to the database");
            user.setErrorId(3); // Ошибка SQL
        }
        return user;
    }

}
