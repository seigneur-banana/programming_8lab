package commands;

import appliances.CommandHandler;
import major.DBUnit;
import major.User;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Command implements Serializable {
    protected User user;
    protected String tag;
    protected String content;

    public Command(User user) {
        this.user = user;
    }

    public abstract boolean validation(CommandHandler commandHandler, String... args);

    public String execute(CommandHandler commandHandler, DBUnit dbUnit, String... args) {
        return "The command is executed.";
    }

    public abstract String getName();

    public abstract String getDescription();

    protected String getStringFromBundle(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Language", Locale.forLanguageTag(tag));
        return new String(bundle.getString(key).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getContent() {
        return content;
    }
}
