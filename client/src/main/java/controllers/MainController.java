package controllers;

import appliances.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import commands.Add;
import commands.Show;
import major.Client;
import major.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static major.Main.*;
import static major.WindowManager.*;
import static java.lang.Thread.sleep;

public class MainController extends Controller {

    @FXML
    public Button proceedButton;

    @FXML
    public ImageView signoutImage;

    @FXML
    public Label goBackLabel;

    @FXML
    public Line underlineFilter;

    @FXML
    public ChoiceBox<String> filterChoiceBox;

    @FXML
    public Button filterButton;

    @FXML
    public Label filterMirrorLabel;

    @FXML
    public TextField filterField;

    @FXML
    public ImageView flag;

    @FXML
    public ChoiceBox<String> languageChoiceBox;

    @FXML
    public Button mapButton;

    @FXML
    public Line underlineMapButton;

    @FXML
    private TableView<StudyGroup> tableOfStudyGroups;

    @FXML
    private TableColumn<StudyGroup, Integer> idColumn;

    @FXML
    private TableColumn<StudyGroup, String> nameColumn;

    @FXML
    private TableColumn<StudyGroup, Double> xColumn;

    @FXML
    private TableColumn<StudyGroup, Double> yColumn;

    @FXML
    private TableColumn<StudyGroup, Integer> countColumn;

    @FXML
    private TableColumn<StudyGroup, String> transferredColumn;

    @FXML
    private TableColumn<StudyGroup, Integer> averageMarkColumn;

    @FXML
    private TableColumn<StudyGroup, Location> personLocationColumn;

    @FXML
    private TableColumn<StudyGroup, String> personNameColumn;

    @FXML
    private TableColumn<StudyGroup, Integer> personHeightColumn;

    @FXML
    private TableColumn<StudyGroup, appliances.Color> personEyeColumn;

    @FXML
    private TableColumn<StudyGroup, appliances.Color> personHairColumn;

    @FXML
    private TableColumn<StudyGroup, Country> countryColumn;

    @FXML
    private TableColumn<StudyGroup, Semester> semesterColumn;

    @FXML
    private TableColumn<StudyGroup, ZonedDateTime> creationDateColumn;

    @FXML
    private TableColumn<StudyGroup, String> ownerColumn;

    @FXML
    private ChoiceBox<String> commandChoiceBox;

    @FXML
    private Line underlineCommandButton;

    @FXML
    private Button commandButton;

    @FXML
    private Label commandMirrorLabel;

    @FXML
    private AnchorPane commandsAnchorPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label chosenCommandLabel;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField countField;

    @FXML
    private TextField transferredField;

    @FXML
    private TextField avgMarkField;
    @FXML
    public TextField personIDField;

    @FXML
    private TextField semesterField;

    @FXML
    private Label userLabel;

    private final HashMap<String, Integer> commandsAndModes = new HashMap<>();

    private StudyGroup chosenStudyGroup;

    private final SynchronizerService synchronizer = new SynchronizerService();

    private AlertType alertType;
    private String title;
    private String header;
    private String content;
    private int errCount;
    private boolean collectionIsEmpty;
    private boolean filterIsSet;
    private String filterOperator;
    private Date date1 = new Date();
    public static volatile ObservableList<StudyGroup> observableList1 = FXCollections.observableArrayList();;

    @FXML
    void initialize() {
        getCommandHandler().setTag(getCurrentBundleName());
        userLabel.setText(getUser().getName());
        commandButton.setText(getStringFromBundle("executeCommand"));
        filterButton.setText(getStringFromBundle("applyFilter"));
        filterField.setPromptText(getStringFromBundle("condition"));
        proceedButton.setText(getStringFromBundle("proceed"));
        mapButton.setText(getStringFromBundle("toMap"));

        ObservableList<String> languages = FXCollections.observableArrayList("en-CA", "ru-RU", "sl-SI", "sq-AL");
        languages.forEach(x -> {
            if (!languageChoiceBox.getItems().contains(x)) {
                languageChoiceBox.getItems().add(x);
            }
        });
        languageChoiceBox.getSelectionModel().select(getCurrentBundleName());
        languageChoiceBox.setOnAction(event -> {
            setCurrentBundle(languageChoiceBox.getValue());
            initialize();
        });

        Tooltip.install(flag, getTooltipWithDelay("Change language", 10));

        flag.setImage(new Image("/images/" + getCurrentBundleName() + ".png"));

        flag.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));

        flag.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));

        flag.setOnMouseClicked(event -> languageChoiceBox.show());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        transferredColumn.setCellValueFactory(new PropertyValueFactory<>("transferredStudents"));
        averageMarkColumn.setCellValueFactory(new PropertyValueFactory<>("averageMark"));
        personNameColumn.setCellValueFactory(new PropertyValueFactory<>("personName"));
        personHeightColumn.setCellValueFactory(new PropertyValueFactory<>("personHeight"));
        personEyeColumn.setCellValueFactory(new PropertyValueFactory<>("personEyeColor"));
        personHairColumn.setCellValueFactory(new PropertyValueFactory<>("personHairColor"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("personCountry"));
        personLocationColumn.setCellValueFactory(new PropertyValueFactory<>("personLocation"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semesterEnum"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        commandsAndModes.put("add", 2);
        commandsAndModes.put("add_if_max", 2);
        commandsAndModes.put("clear", 0);
        //commandsAndModes.put("execute_script", 0);
        commandsAndModes.put("history", 0);
        commandsAndModes.put("help", 0);
        commandsAndModes.put("remove_by_id", 0);
        commandsAndModes.put("remove_all_by_semester", 0);
        commandsAndModes.put("remove_greater", 0);
        commandsAndModes.put("update", 1);

        ObservableList<String> commands = FXCollections.observableArrayList();
        commands.addAll(commandsAndModes.keySet());
        commandChoiceBox.setItems(commands);

        Tooltip.install(commandMirrorLabel, getTooltipWithDelay(getStringFromBundle("chooseCommand"), 10));

        commandMirrorLabel.setOnMouseClicked(event -> commandChoiceBox.show());

        commandMirrorLabel.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));

        commandMirrorLabel.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));

        Tooltip.install(commandChoiceBox, getTooltipWithDelay(getStringFromBundle("chooseCommand"), 10));

        commandChoiceBox.setOnAction(event -> {
            commandMirrorLabel.setText(commandChoiceBox.getValue());
            commandChoiceBox.requestFocus();
        });

        commandChoiceBox.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));

        commandChoiceBox.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));

        commandButton.setOnAction(event -> {
            if (!commandMirrorLabel.getText().isEmpty()) {
                prepareCommand();
                getScene().setCursor(Cursor.DEFAULT);
            }
        });

        commandButton.setOnMouseEntered(event -> {
            if (!commandMirrorLabel.getText().isEmpty()) {
                underlineCommandButton.setStroke(Color.web("#5454FF"));
                getScene().setCursor(Cursor.HAND);
            }
        });

        commandButton.setOnMouseExited(event -> {
            if (!commandMirrorLabel.getText().isEmpty()) {
                underlineCommandButton.setStroke(Color.web("white"));
                getScene().setCursor(Cursor.DEFAULT);
            }
        });

        filterIsSet = false;
        ObservableList<String> fields = FXCollections.observableArrayList();
        fields.add("");
        fields.add("id");
        fields.add("name");
        fields.add("x");
        fields.add("y");
        fields.add("count");
        fields.add("transferred");
        fields.add("avgMark");
        fields.add("semester");
        fields.add("personID");
        filterChoiceBox.setItems(fields);

        Tooltip.install(filterMirrorLabel, getTooltipWithDelay(getStringFromBundle("chooseField"), 10));

        filterMirrorLabel.setOnMouseClicked(event -> filterChoiceBox.show());

        filterMirrorLabel.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));

        filterMirrorLabel.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));

        Tooltip.install(filterChoiceBox, getTooltipWithDelay(getStringFromBundle("chooseField"), 10));

        filterChoiceBox.setOnAction(event -> {
            filterMirrorLabel.setText(filterChoiceBox.getValue());
            filterIsSet = false;
            underlineFilter.setStroke(Color.web("white"));
            filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        });

        filterChoiceBox.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));

        filterChoiceBox.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));

        filterButton.setOnAction(event -> {
            if (!filterMirrorLabel.getText().isEmpty()) {
                underlineFilter.setStroke(Color.web("#5454FF"));
                if (filterField.getText().matches("[<>=][^<>=\\s]+")) {
                    filterOperator = String.valueOf(filterField.getText().charAt(0));
                    filterIsSet = true;
                } else if (filterField.getText().matches("[<>!]=[^<>!=\\s]+")) {
                    filterOperator = filterField.getText().substring(0, 2);
                    filterIsSet = true;
                } else {
                    filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    showAlert(ERROR, "ERROR", getStringFromBundle("filterAlertHeader"), getStringFromBundle("filterConditionAlertContent"));
                    underlineFilter.setStroke(Color.web("white"));
                }
                getScene().setCursor(Cursor.DEFAULT);
            }
        });

        filterButton.setOnMouseEntered(event -> {
            if (!filterMirrorLabel.getText().isEmpty() && !filterIsSet && !filterField.getText().isEmpty()) {
                underlineFilter.setStroke(Color.web("#5454FF"));
                getScene().setCursor(Cursor.HAND);
            }
        });

        filterButton.setOnMouseExited(event -> {
            if (!filterIsSet) {
                underlineFilter.setStroke(Color.web("white"));
                getScene().setCursor(Cursor.DEFAULT);
            }
        });

        mapButton.setOnAction(event -> {
            try {
                MapController.setStudyGroup(tableOfStudyGroups.getItems());
                changeScene("map.fxml", "ITMO: " + getStringFromBundle("mapWindowTitle"));
            } catch (IOException e) {
                showAlert(ERROR, "Error", getStringFromBundle("changeSceneError"), e.getMessage());
            }
        });

        mapButton.setOnMouseEntered(event -> {
            underlineMapButton.setStroke(Color.web("#5454FF"));
            getScene().setCursor(Cursor.HAND);
        });

        mapButton.setOnMouseExited(event -> {
            underlineMapButton.setStroke(Color.web("white"));
            getScene().setCursor(Cursor.DEFAULT);
        });

        Tooltip.install(filterField, getTooltipWithDelay(getStringFromBundle("condition"), 300));

        filterField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                filterButton.fire();
            } else {
                filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
                underlineFilter.setStroke(Color.web("white"));
                filterIsSet = false;
            }
        });

        tableOfStudyGroups.setRowFactory(tv -> {
            TableRow<StudyGroup> row = new TableRow<>();
            MenuItem itemUpdate = new MenuItem(getStringFromBundle("update"));
            itemUpdate.setOnAction(event -> {
                chosenStudyGroup = row.getItem();
                commandChoiceBox.setValue("update");
                commandMirrorLabel.setText("update");
                prepareCommand();
                setFields();
            });
            MenuItem itemRemove = new MenuItem(getStringFromBundle("remove"));
            itemRemove.setOnAction(event -> {
                commandChoiceBox.setValue("remove_by_id");
                commandMirrorLabel.setText("remove_by_id");
                String result = getCommandHandler().fromString("remove_by_id", row.getItem().getId().toString());
                if (result == null) {
                    showAlert(ERROR, "ERROR", getStringFromBundle("sendErrorAlertHeader"), Client.getContent());
                } else if (result.isEmpty()) {
                    showAlert(ERROR, "ERROR", getStringFromBundle("prepareErrorAlertHeader"), getCommandHandler().getContent());
                } else {
                    showAlert(AlertType.INFORMATION, "INFO", getStringFromBundle("command") + commandMirrorLabel.getText() + getStringFromBundle("returnedResult"), result);
                }
            });
            ContextMenu menu = new ContextMenu(itemUpdate, itemRemove);
            row.setOnMouseClicked(event -> {
                if (menu.isShowing()) {
                    menu.hide();
                }
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    chosenStudyGroup = row.getItem();
                    commandMirrorLabel.setText("update");
                    prepareCommand();
                    setFields();
                }
            });
            row.setOnMouseEntered(event -> {
                if (!row.isEmpty()) {
                    Tooltip.install(row, getTooltipWithDelay(getStringFromBundle("updateTooltip"), 700));
                    if (!menu.isShowing()) {
                        getScene().setCursor(Cursor.HAND);
                    }
                }
            });
            row.setOnMouseExited(event -> {
                if (!row.isEmpty()) {
                    row.setTooltip(null);
                    getScene().setCursor(Cursor.DEFAULT);
                }
            });

            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    if (menu.isShowing()) {
                        menu.hide();
                    }
                    menu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });

        proceedButton.setOnMouseEntered(event -> {
            getScene().setCursor(Cursor.HAND);
            proceedButton.setStyle("-fx-background-color: #4E3BEC");
        });

        proceedButton.setOnMouseExited(event -> {
            if (!proceedButton.isDefaultButton()) {
                proceedButton.setStyle("-fx-background-color: #1600D9");
            }
            getScene().setCursor(Cursor.DEFAULT);
        });

        proceedButton.setOnAction(event -> {
            proceedButton.setStyle("-fx-background-color: #3629A3");
            proceedButton.setDefaultButton(true);
            proceed();
            proceedButton.setDefaultButton(false);
            proceedButton.setStyle("-fx-background-color: #1600D9");

            getScene().setCursor(Cursor.DEFAULT);
        });

        Tooltip.install(signoutImage, getTooltipWithDelay(getStringFromBundle("signOut"), 10));

        signoutImage.setOnMouseEntered(event -> {
            getScene().setCursor(Cursor.HAND);
            signoutImage.setImage(new Image("/images/sing-out-hover.png"));
        });

        signoutImage.setOnMouseExited(event -> {
            getScene().setCursor(Cursor.DEFAULT);
            signoutImage.setImage(new Image("/images/sing-out.png"));
        });

        signoutImage.setOnMouseClicked(event -> {
            try {
                changeScene("start.fxml", "ITMO: " + getStringFromBundle("startWindowTitle"));
                synchronizer.cancel();
            } catch (IOException e) {
                showAlert(ERROR, "ERROR", getStringFromBundle("changeSceneError"), e.getMessage());
            }
        });

        idField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (nameField.isVisible()) {
                    nameField.requestFocus();
                } else {
                    proceedButton.fire();
                }
            } else {
                idField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                xField.requestFocus();
            } else {
                nameField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        xField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                yField.requestFocus();
            } else {
                xField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        yField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                countField.requestFocus();
            } else {
                yField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        countField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                transferredField.requestFocus();
            } else {
                countField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        transferredField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                avgMarkField.requestFocus();
            } else {
                transferredField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        avgMarkField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                personIDField.requestFocus();
            } else {
                avgMarkField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        personIDField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                semesterField.requestFocus();
            } else {
                personIDField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });
        semesterField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                semesterField.requestFocus();
            } else {
                semesterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
            }
        });

        Tooltip.install(goBackLabel, getTooltipWithDelay(getStringFromBundle("back"), 10));

        goBackLabel.setOnMouseEntered(event -> {
            getScene().setCursor(Cursor.HAND);
            goBackLabel.setTextFill(Color.web("#5454FF"));
        });

        goBackLabel.setOnMouseExited(event -> {
            getScene().setCursor(Cursor.DEFAULT);
            goBackLabel.setTextFill(Color.web("white"));
        });

        goBackLabel.setOnMouseClicked(event -> {
            getScene().setCursor(Cursor.DEFAULT);
            commandsAnchorPane.setVisible(false);
            resetFields();
            mainAnchorPane.setVisible(true);
        });

        errCount = 0;
        collectionIsEmpty = false;

        synchronizer.setOnSucceeded(event -> {
            if (errCount < 4) {
                showAlert(alertType, title, header, content);
                underlineFilter.setStroke(Color.web("white"));
                synchronizer.reset();
                synchronizer.start();
            } else {
                showAlert(ERROR, "ERROR", getStringFromBundle("stopSessionAlertHeader"), getStringFromBundle("stopSessionAlertContent"));
                try {
                    changeScene("start.fxml", "ITMO: " + getStringFromBundle("startWindowTitle"));
                } catch (IOException e) {
                    showAlert(ERROR, "ERROR", getStringFromBundle("changeSceneError"), e.getMessage());
                }
            }
        });
        if (!synchronizer.isRunning()) {
            synchronizer.start();
        }
    }

    private class SynchronizerService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    long count = 0;
                    while (true) {
                        if (fillTable(count++) == count) {
                            break;
                        }
                        try {
                            sleep(250);
                        } catch (InterruptedException e) {
                            break;
                        }
                        if (count == 0) {
                            count++;
                        }
                    }
                    return null;
                }
            };
        }
    }

    private void prepareCommand() {
        resetFields();
        mainAnchorPane.setVisible(false);
        idField.setVisible(false);
        idField.setEditable(true);
        idField.setPromptText("id");
        chosenCommandLabel.setText(commandMirrorLabel.getText());
        switch (commandsAndModes.get(commandMirrorLabel.getText())) {
            case 0:
                if (commandMirrorLabel.getText().equals("execute_script")) {
                    idField.setPromptText("file");
                    Tooltip.install(idField, getTooltipWithDelay("file", 10));
                    idField.setVisible(true);
                } else if (commandMirrorLabel.getText().equals("remove_any_by_semester")) {
                    idField.setPromptText("semester");
                    Tooltip.install(idField, getTooltipWithDelay("semester", 10));
                    idField.setVisible(true);
                } else if (commandMirrorLabel.getText().equals("remove_by_id")) {
                    idField.setVisible(true);
                } else if ((commandMirrorLabel.getText().equals("remove_greater"))) {
                    idField.setPromptText("count");
                    Tooltip.install(idField, getTooltipWithDelay("count", 10));
                    idField.setVisible(true);
                } else if ((commandMirrorLabel.getText().equals("remove_all_by_semester"))) {
                    idField.setPromptText("semester");
                    Tooltip.install(idField, getTooltipWithDelay("semester", 10));
                    idField.setVisible(true);
                }

                nameField.setVisible(false);
                xField.setVisible(false);
                yField.setVisible(false);
                countField.setVisible(false);
                transferredField.setVisible(false);
                avgMarkField.setVisible(false);
                personIDField.setVisible(false);
                semesterField.setVisible(false);
                break;
            case 1:
                idField.setVisible(true);
            case 2:
                nameField.setVisible(true);
                xField.setVisible(true);
                yField.setVisible(true);
                countField.setVisible(true);
                transferredField.setVisible(true);
                avgMarkField.setVisible(true);
                personIDField.setVisible(true);
                semesterField.setVisible(true);
                break;
        }
        commandsAnchorPane.setVisible(true);
    }

    private void setFields() {
        idField.setText(chosenStudyGroup.getId().toString());
        idField.setEditable(false);
        nameField.setText(chosenStudyGroup.getName());
        try {
            xField.setText(String.valueOf(chosenStudyGroup.getCoordinates().getX()));
        } catch (NullPointerException e) {
            xField.setText("");
        }
        try {
            yField.setText(chosenStudyGroup.getCoordinates().getY().toString());
        } catch (NullPointerException e) {
            yField.setText("");
        }
        try {
            countField.setText(String.valueOf(chosenStudyGroup.getStudentsCount()));
        } catch (NullPointerException e) {
            countField.setText("");
        }
        try {
            transferredField.setText(String.valueOf(chosenStudyGroup.getTransferredStudents()));
        } catch (NullPointerException e) {
            transferredField.setText("");
        }
        try {
            avgMarkField.setText(String.valueOf(chosenStudyGroup.getAverageMark()));
        } catch (NullPointerException e) {
            avgMarkField.setText("");
        }
        try {
            personIDField.setText(chosenStudyGroup.getGroupAdmin().getName());
        } catch (NullPointerException e) {
            personIDField.setText("");
        }
        try {
            semesterField.setText(chosenStudyGroup.getSemesterEnum().toString());
        } catch (NullPointerException e) {
            semesterField.setText("");
        }
    }

    private void proceed() {
        String result;
        switch (commandsAndModes.get(commandMirrorLabel.getText())) {
            case 0:
                result = getCommandHandler().fromString(commandMirrorLabel.getText(), idField.getText());
                if (commandMirrorLabel.getText().equals("history")){
                    StringBuilder s = new StringBuilder();
                    for (String str : getCommandHandler().getHistory()) {
                        s.append(str);
                        s.append("\n");
                    }
                    showAlert(INFORMATION, "INFO", getStringFromBundle("command") + " History", s.toString());
                    break;
                }
                if (result == null) {
                    showAlert(ERROR, "ERROR", getStringFromBundle("sendErrorAlertHeader"), Client.getContent());
                } else if (result.isEmpty()) {
                    showAlert(ERROR, "ERROR", getStringFromBundle("prepareErrorAlertHeader"), getCommandHandler().getContent());
                    idField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                } else {
                    showAlert(INFORMATION, "INFO", getStringFromBundle("command") + commandMirrorLabel.getText() + getStringFromBundle("returnedResult"), result);
                    commandsAnchorPane.setVisible(false);
                    mainAnchorPane.setVisible(true);
                }
                break;
            case 1:
            case 2:
                Stack<String> errors = new Stack<>();
                int id = 0;
                if (idField.isVisible()) {
                    try {
                        id = Integer.parseInt(idField.getText());
                        if (id <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        idField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                        errors.push(getStringFromBundle("idError"));
                    }
                } else id = observableList1.size(); //Add.isItIdUnique();
                String name = nameField.getText();
                if (name == null || name.matches("\\s*")) {
                    nameField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("nameError"));
                }
                double x = 0.0;
                try {
                    x = Double.parseDouble(xField.getText());
                } catch (NumberFormatException e) {
                    xField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("xError"));
                }
                double y = 0.0;
                try {
                    y = Double.parseDouble(yField.getText());
                } catch (NumberFormatException e) {
                    yField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("yError"));
                }
                int count = 0;
                try {
                    count = Integer.parseInt(countField.getText());
                    if (count <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    countField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("countError"));
                }
                int transferred = 0;
                transferredField.getText();
                try {
                    transferred = Integer.parseInt(transferredField.getText());
                    if (transferred <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    transferredField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("countError"));
                }


                int avgMark = 0;
                try {
                    avgMark = Integer.parseInt(avgMarkField.getText());
                    if (avgMark <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    avgMarkField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("manCostError"));
                }

                int personID = 0;
                try {
                    personID = Integer.parseInt(personIDField.getText());
                    if (personID < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    personIDField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("manCostError"));
                }

                appliances.Semester semester = Semester.FIRST;
                try {
                    for (appliances.Semester sem : appliances.Semester.values()) {
                        if (sem.toString().toLowerCase().equals(semesterField.getText().toLowerCase())) {
                            semester = sem;
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    semesterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                    errors.push(getStringFromBundle("empCountError"));
                }


                if (errors.isEmpty()) {
                    Coordinates coordinates = new Coordinates(y, x);
                    Person person = getCommandHandler().getPersons().get(personID);
                    StudyGroup studyGroup = new StudyGroup(id, name, coordinates, new Date(), count, transferred, avgMark, semester, person, getUser());

                    result = getCommandHandler().fromString(commandMirrorLabel.getText(), studyGroup.toString());
                    /*if (result == null) {
                        showAlert(ERROR, "ERROR", getStringFromBundle("sendErrorAlertHeader"), Client.getContent());
                    } else if (result.isEmpty()) {
                        showAlert(ERROR, "ERROR", getStringFromBundle("prepareErrorAlertHeader"), getCommandHandler().getContent());
                    } else if (result.equals("0")) {
                        showAlert(INFORMATION, "SUCCESS", getStringFromBundle("command") + commandMirrorLabel.getText() + getStringFromBundle("executedSuccessfully"), "");
                    } else {
                        showAlert(ERROR, "ERROR", getStringFromBundle("executeFailure"), result);
                    }*/
                    commandsAnchorPane.setVisible(false);
                    mainAnchorPane.setVisible(true);
                } else {
                    StringBuilder builder = new StringBuilder();
                    errors.forEach(builder::append);
                    showAlert(ERROR, "ERROR", getStringFromBundle("checkArgumentsAlertHeader"), builder.toString());
                }
                break;
        }
    }

    private void resetFields() {
        idField.clear();
        nameField.clear();
        xField.clear();
        yField.clear();
        countField.clear();
        transferredField.clear();
        avgMarkField.clear();
        personIDField.clear();
        semesterField.clear();

        idField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        nameField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        xField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        yField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        countField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        transferredField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        avgMarkField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        personIDField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");
        semesterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: white;");

        Tooltip.install(idField, getTooltipWithDelay("id", 10));
        Tooltip.install(nameField, getTooltipWithDelay("name", 10));
        Tooltip.install(xField, getTooltipWithDelay("x", 10));
        Tooltip.install(yField, getTooltipWithDelay("y", 10));
        Tooltip.install(countField, getTooltipWithDelay("count", 10));
        Tooltip.install(transferredField, getTooltipWithDelay("transferred", 10));
        Tooltip.install(personIDField, getTooltipWithDelay("personID", 10));
        Tooltip.install(avgMarkField, getTooltipWithDelay("avgMark", 10));
        Tooltip.install(semesterField, getTooltipWithDelay("semester", 10));
    }

    private boolean matchesFilter(StudyGroup StudyGroup) {
        try {
            Field field = StudyGroup.getClass().getDeclaredField(filterChoiceBox.getValue());
            field.setAccessible(true);
            String condition = filterField.getText();
            Object fieldValue = field.get(StudyGroup);

            boolean isNumber;
            try {
                Number number = (Number) fieldValue;
                isNumber = true;
            } catch (ClassCastException e) {
                isNumber = false;
            }

            Integer result = compareValues(fieldValue, condition.substring(filterOperator.length()), isNumber);
            if (result == null) {
                alertType = ERROR;
                title = "ERROR";
                header = getStringFromBundle("filterAlertHeader");
                content = getStringFromBundle("filterComparisonAlertContent");
                filterIsSet = false;
                filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                return false;
            } else {
                switch (filterOperator) {
                    case "<":
                        if (isNumber) {
                            return result > 0;
                        } else {
                            alertType = ERROR;
                            title = "ERROR";
                            header = getStringFromBundle("filterAlertHeader");
                            content = getStringFromBundle("filterConditionAlertContent");
                            filterIsSet = false;
                            filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                            return false;
                        }
                    case ">":
                        if (isNumber) {
                            return result < 0;
                        } else {
                            alertType = ERROR;
                            title = "ERROR";
                            header = getStringFromBundle("filterAlertHeader");
                            content = getStringFromBundle("filterConditionAlertContent");
                            filterIsSet = false;
                            filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                            return false;
                        }
                    case "=":
                        return result == 0;
                    case "<=":
                        if (isNumber) {
                            return result >= 0;
                        } else {
                            alertType = ERROR;
                            title = "ERROR";
                            header = getStringFromBundle("filterAlertHeader");
                            content = getStringFromBundle("filterConditionAlertContent");
                            filterIsSet = false;
                            filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                            return false;
                        }
                    case ">=":
                        if (isNumber) {
                            return result <= 0;
                        } else {
                            alertType = ERROR;
                            title = "ERROR";
                            header = getStringFromBundle("filterAlertHeader");
                            content = getStringFromBundle("filterConditionAlertContent");
                            filterIsSet = false;
                            filterField.setStyle("-fx-background-color: transparent; -fx-background-image: url('/images/field-bg2.png'); -fx-text-fill: #ff2626;");
                            return false;
                        }
                    case "!=":
                        return result != 0;
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Integer compareValues(Object fieldValue, String conditionValue, boolean isNumber) {
        if (isNumber) {
            try {
                Number number = NumberFormat.getInstance().parse(conditionValue);
                Comparable<Object> numberValue = (Comparable<Object>) number;
                return numberValue.compareTo(fieldValue);
            } catch (java.text.ParseException e) {
                return null;
            }
        } else {
            return fieldValue.toString().compareTo(conditionValue);
        }
    }

    private long fillTable(long count) {
        ObservableList<StudyGroup> observableList = FXCollections.observableArrayList();

        String jsonString = Client.send(new Show(getUser()));

        if (jsonString != null) {
            if (!jsonString.equals("0")) {
                try {
                    JSONArray jsonArray = (JSONArray) new JSONParser().parse("[" + jsonString + "]");
                    for (Object element : jsonArray) {
                        try {
                            JSONObject o = (JSONObject) element;
                            int locY, height, mark, countSt, trans, id;
                            float locX;
                            Date date = date1;
                            try {
                                locY = Integer.parseInt(o.get("locationY").toString());
                            } catch (Exception e) {
                                locY = 0;
                            }
                            try {
                                locX = Float.parseFloat(o.get("locationX").toString());
                            } catch (Exception e) {
                                locX = 0.f;
                            }
                            try {
                                height = Integer.parseInt(o.get("height").toString());
                            } catch (Exception e) {
                                height = 0;
                            }
                            try {
                                id = Integer.parseInt(o.get("id").toString());
                            } catch (Exception e) {
                                id = 0;
                            }
                            try {
                                countSt = Integer.parseInt(o.get("count").toString());
                            } catch (Exception e) {
                                countSt = 0;
                            }
                            try {
                                trans = Integer.parseInt(o.get("transferred").toString());
                            } catch (Exception e) {
                                trans = 0;
                            }
                            try {
                                mark = Integer.parseInt(o.get("avgMark").toString());
                            } catch (Exception e) {
                                mark = 0;
                            }
                            try {
                                DateFormat date2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                                String x = o.get("creationDate").toString();
                                date = date2.parse(o.get("creationDate").toString());
                            } catch (Exception e) {
                            }

                            Coordinates coordinate = new Coordinates((Double) o.get("yCoordinate"), (Double) o.get("xCoordinate"));
                            getCommandHandler().setCoordinates((Double) o.get("yCoordinate"), (Double) o.get("xCoordinate"));
                            Location location = new Location(locY, locX, o.get("locationName").toString());
                            getCommandHandler().setLocations(locY, locX, o.get("locationName").toString());
                            Person person = new Person(
                                    o.get("personName").toString(),
                                    height,
                                    new Gson().fromJson(o.get("eyeColor").toString(), appliances.Color.class),
                                    new Gson().fromJson(o.get("hairColor").toString(), appliances.Color.class),
                                    new Gson().fromJson(o.get("country").toString(), appliances.Country.class),
                                    location
                            );
                            getCommandHandler().setPersons(o.get("personName").toString(),
                                    height,
                                    new Gson().fromJson(o.get("eyeColor").toString(), appliances.Color.class),
                                    new Gson().fromJson(o.get("hairColor").toString(), appliances.Color.class),
                                    new Gson().fromJson(o.get("country").toString(), appliances.Country.class),
                                    location
                            );
                            User user = new User(o.get("owner").toString());
                            StudyGroup StudyGroup = new StudyGroup(
                                    id,
                                    o.get("name").toString(),
                                    coordinate,
                                    date,
                                    countSt,
                                    trans,
                                    mark,
                                    new Gson().fromJson(o.get("semester").toString(), Semester.class),
                                    person,
                                    user
                            );
                            getCommandHandler().setGroups(id,
                                    o.get("name").toString(),
                                    coordinate,
                                    countSt,
                                    trans,
                                    mark,
                                    new Gson().fromJson(o.get("semester").toString(), Semester.class),
                                    person,
                                    user);

                            if (filterIsSet) {
                                if (matchesFilter(StudyGroup)) {
                                    observableList.add(StudyGroup);
                                } else if (!filterIsSet) {
                                    observableList.add(StudyGroup);
                                    return count + 1;
                                }
                            } else {
                                observableList.add(StudyGroup);
                            }
                        } catch (JsonSyntaxException e) {
                            errCount++;
                            alertType = ERROR;
                            title = "ERROR";
                            header = getStringFromBundle("collectionLoadAlertHeader");
                            content = e.getMessage();
                            return count + 1;
                        }
                    }
                } catch (ParseException e) {
                    errCount++;
                    alertType = ERROR;
                    title = "ERROR";
                    header = getStringFromBundle("collectionLoadAlertHeader");
                    content = e.getMessage();
                    return count + 1;
                }
            } else if ((count == 0) && (!collectionIsEmpty)) {
                collectionIsEmpty = true;
                alertType = INFORMATION;
                title = "INFO";
                header = getStringFromBundle("collectionEmptyAlertHeader");
                content = getStringFromBundle("collectionEmptyAlertContent");
                return count + 1;
            }
        } else {
            errCount++;
            alertType = ERROR;
            title = "ERROR";
            header = getStringFromBundle("collectionLoadAlertHeader");
            content = Client.getContent();
            return count + 1;
        }

        tableOfStudyGroups.getItems().clear();
        tableOfStudyGroups.getItems().addAll(observableList);
        observableList1.clear();
        observableList1.addAll(observableList);
        return 0;
    }
}
