package appliances;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParsedCommand {
    private String command;
    private String[] args;

    public ParsedCommand(String line) {
        String[] parts = line.split(" ");
        command = parts[0];
        if (parts.length > 1) {
            args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);
        }
    }

    public static JSONObject parseJSON(CommandHandler ch, String str) throws ParseException {
        Object obj = new JSONParser().parse(str);
        return (JSONObject) obj;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
