package appliances;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;


public class FileParser {
    public void read(CommandHandler commandHandler){
        // маппинг csv
        //String[] mapping = new String[]{"groupName", "coorX", "coorY", "count", "transfer", "mark", "sem", "admin",
        //        "height", "eye", "hair", "country", "locX", "locY", "locName"};

        try {
            FileInputStream fileInputStream = new FileInputStream("in.csv");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
            int i;
            StringBuilder s = new StringBuilder();
            while ((i = bufferedInputStream.read()) != -1) {
                try{
                    s.append((char) i);
                    if ((char) i == '\n') {
                        String[] columns = s.toString().split(",");
                        Semester sem = null;
                        Color eye = null, hair = null;
                        Country country = null;
                        int height = 0, count = 0, transfer = 0, mark = 0;
                        try {
                            if (Integer.parseInt(columns[8]) > 0) height = Integer.parseInt(columns[8]);
                        } catch (Exception e) {
                            System.out.println("Error when reading ROST");
                        }
                        try {
                            if (Integer.parseInt(columns[3]) > 0) count = Integer.parseInt(columns[3]);
                        } catch (Exception e) {
                            System.out.println("Error when reading growth");
                        }
                        try {
                            if (Integer.parseInt(columns[4]) > 0) transfer = Integer.parseInt(columns[4]);
                        } catch (Exception e) {
                            System.out.println("Error when reading COUNT OF TRANSFERRED STUDENTS");
                        }
                        try {
                            if (Integer.parseInt(columns[5]) > 0 && Integer.parseInt(columns[5]) <= 5)
                                mark = Integer.parseInt(columns[5]);
                        } catch (Exception e) {
                            System.out.println("Error when reading AVG MARK");
                        }
                        for (Semester semester : Semester.values()) {
                            if (columns[6].toLowerCase().equals(semester.name().toLowerCase())) {
                                sem = semester;
                                break;
                            }
                        }
                        for (Color color : Color.values()) {
                            if (columns[9].toLowerCase().equals(color.name().toLowerCase())) {
                                eye = color;
                                break;
                            }
                        }
                        for (Color color : Color.values()) {
                            if (columns[10].toLowerCase().equals(color.name().toLowerCase())) {
                                hair = color;
                                break;
                            }
                        }
                        for (Country country1 : Country.values()) {
                            if (columns[11].toLowerCase().equals(country1.name().toLowerCase())) {
                                country = country1;
                                break;
                            }
                        }
                        double corY = 0.0, corX = 0.0;
                        int locY = 0;
                        float locX = 0.0f;
                        try {
                            corY = Double.parseDouble(columns[2]);
                        } catch (Exception e) {
                            System.out.println("Error when reading the coordinate Y");
                        }
                        try {
                            corX = Double.parseDouble(columns[1]);
                        } catch (Exception e) {
                            System.out.println("Error when reading the coordinate Х");
                        }
                        try {
                            locY = Integer.parseInt(columns[13]);
                        } catch (Exception e) {
                            System.out.println("Error when reading the location Y");
                        }
                        try {
                            locX = Float.parseFloat(columns[12]);
                        } catch (Exception e) {
                            System.out.println("Error when reading the coordinate X");
                        }

                        commandHandler.setCoordinates(corY, corX); //y,x
                        commandHandler.setLocations(locY, locX, columns[14]);//y,x,locName
                        commandHandler.setPersons(
                                columns[7],                                                                     //name Person
                                height,
                                eye,
                                hair,
                                country,
                                commandHandler.getLocations().get(commandHandler.getLocations().size() - 1)     //coordinates
                        );

                        /*commandHandler.setGroups(
                                commandHandler.getGroups().size(),            //id group
                                columns[0],                                                                     //name group
                                commandHandler.getCoordinates().get(commandHandler.getCoordinates().size() - 1),//coordinates
                                count,
                                transfer,
                                mark,
                                sem,
                                commandHandler.getPersons().get(commandHandler.getPersons().size() - 1)         //admin
                        );*/
                        s.setLength(0);
                    }
                }catch (Exception e){
                    System.out.println("Reading exception");
                }
            }
            System.out.println("The data is loaded from the database . . .");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> parseJSON(String s) {
        ArrayList<String> ar = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(s);

            ar.add((String) jsonObject.get("name"));
            System.out.println(jsonObject.get("name").toString());
            ar.add((String) jsonObject.get("Y"));
            ar.add((String) jsonObject.get("X"));
            ar.add((String) jsonObject.get("count"));
            ar.add((String) jsonObject.get("transfer"));
            ar.add((String) jsonObject.get("mark"));
            ar.add((String) jsonObject.get("sem"));
            ar.add((String) jsonObject.get("idPerson"));
        } catch (Exception e) {
            System.out.println("Invalid json file");
        }
        return ar;
    }
}