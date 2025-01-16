package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connecter.db_connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SampleController implements Initializable {

    // Teacher Management
    @FXML
    private TextField number;

    @FXML
    private TextField name;

    @FXML
    private TextField contact;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnMod;

    @FXML
    private Button btndel;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<ObservableList<String>> table1;

    @FXML
    private TableColumn<ObservableList<String>, String> numberC;

    @FXML
    private TableColumn<ObservableList<String>, String> nameC;

    @FXML
    private TableColumn<ObservableList<String>, String> contactC;

    private ObservableList<ObservableList<String>> teacherData;

    // Course Management
    @FXML
    private TableView<ObservableList<String>> table2;

    @FXML
    private TableColumn<ObservableList<String>, String> classC;

    @FXML
    private TableColumn<ObservableList<String>, String> subjectC;

    @FXML
    private TableColumn<ObservableList<String>, String> dayC;

    @FXML
    private TableColumn<ObservableList<String>, String> timeC;

    @FXML
    private TableColumn<ObservableList<String>, String> teacherC;

    private ObservableList<ObservableList<String>> courseData;

    @FXML
    private ChoiceBox<String> classCB;

    @FXML
    private ChoiceBox<String> dayCB;

    @FXML
    private ChoiceBox<String> hourCB;

    @FXML
    private TextField subject;

    @FXML
    private TextField Tnumber;

    @FXML
    private Button btnAdd2;

    @FXML
    private Button btnReq;

    private final String[] classes = {"1st grade", "2nd grade", "3rd grade", "4th grade", "5th grade"};
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private final String[] hours = {"08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00"};

    private final db_connection db = new db_connection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ChoiceBoxes
        classCB.getItems().addAll(classes);
        dayCB.getItems().addAll(days);
        hourCB.getItems().addAll(hours);
        initializeCourseTable();
        loadCourses();

        // Initialize teacher table
        initializeTeacherTable();
        loadTeachers();
    }

    private void initializeTeacherTable() {
        numberC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(0)));
        nameC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(1)));
        contactC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(2)));

        teacherData = FXCollections.observableArrayList();
        table1.setItems(teacherData);
    }

    private void initializeCourseTable() {
        classC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(0)));
        subjectC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(1)));
        dayC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(2)));
        timeC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(3)));
        teacherC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(4)));

        courseData = FXCollections.observableArrayList();
        table2.setItems(courseData);
    }

    private void loadTeachers() {
        teacherData.clear();
        String query = "SELECT employee_number, name, contact FROM teachers";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("employee_number"));
                row.add(rs.getString("name"));
                row.add(rs.getString("contact"));
                teacherData.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        courseData.clear();
        String query = "SELECT c.name AS class, s.subject, s.day_of_week, s.time, t.name AS teacher " +
                       "FROM sessions s " +
                       "JOIN classes c ON s.class_id = c.id " +
                       "JOIN teachers t ON s.teacher_id = t.id";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("class"));
                row.add(rs.getString("subject"));
                row.add(rs.getString("day_of_week"));
                row.add(rs.getString("time"));
                row.add(rs.getString("teacher"));
                courseData.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Add(ActionEvent event) {
        String teacherNumber = number.getText();
        String teacherName = name.getText();
        String teacherContact = contact.getText();

        if (teacherNumber.isEmpty() || teacherName.isEmpty() || teacherContact.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        String query = "INSERT INTO teachers (employee_number, name, contact) VALUES (?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacherNumber);
            stmt.setString(2, teacherName);
            stmt.setString(3, teacherContact);
            stmt.executeUpdate();
            loadTeachers();
            showAlert("Success", "Teacher added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Modify(ActionEvent event) {
        String teacherNumber = number.getText();
        String teacherName = name.getText();
        String teacherContact = contact.getText();

        if (teacherNumber.isEmpty() || teacherName.isEmpty() || teacherContact.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        String query = "UPDATE teachers SET name = ?, contact = ? WHERE employee_number = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacherName);
            stmt.setString(2, teacherContact);
            stmt.setString(3, teacherNumber);
            stmt.executeUpdate();
            loadTeachers();
            showAlert("Success", "Teacher modified successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        String teacherNumber = number.getText();

        if (teacherNumber.isEmpty()) {
            showAlert("Error", "Employee number is required!");
            return;
        }

        String query = "DELETE FROM teachers WHERE employee_number = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacherNumber);
            stmt.executeUpdate();
            loadTeachers();
            showAlert("Success", "Teacher deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Search(ActionEvent event) {
        String teacherName = name.getText();

        if (teacherName.isEmpty()) {
            showAlert("Error", "Teacher name is required!");
            return;
        }

        String query = "SELECT employee_number, contact FROM teachers WHERE name = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacherName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                number.setText(rs.getString("employee_number"));
                contact.setText(rs.getString("contact"));
                showAlert("Success", "Teacher found!");
            } else {
                showAlert("Info", "Teacher not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Add2(ActionEvent event) {
        String selectedClass = classCB.getValue();
        String selectedDay = dayCB.getValue();
        String selectedHour = hourCB.getValue();
        String courseSubject = subject.getText();
        String teacherNumber = Tnumber.getText();

        if (selectedClass == null || selectedDay == null || selectedHour == null || courseSubject.isEmpty() || teacherNumber.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Add new class to the classes table if it doesn't exist
        String addClassQuery = "INSERT INTO classes (name) VALUES (?) ON DUPLICATE KEY UPDATE name = name";
        try (Connection conn = db.connect();
             PreparedStatement addClassStmt = conn.prepareStatement(addClassQuery)) {
            addClassStmt.setString(1, selectedClass);
            addClassStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add class!");
            return;
        }

        // Add new session to the sessions table
        String addSessionQuery = "INSERT INTO sessions (class_id, subject, day_of_week, time, teacher_id) " +
                "VALUES ((SELECT id FROM classes WHERE name = ?), ?, ?, ?, (SELECT id FROM teachers WHERE employee_number = ?))";
        try (Connection conn = db.connect();
             PreparedStatement addSessionStmt = conn.prepareStatement(addSessionQuery)) {
            addSessionStmt.setString(1, selectedClass);
            addSessionStmt.setString(2, courseSubject);
            addSessionStmt.setString(3, selectedDay);
            addSessionStmt.setString(4, selectedHour);
            addSessionStmt.setString(5, teacherNumber);
            addSessionStmt.executeUpdate();
            loadCourses();
            showAlert("Success", "Class and course added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add course! " + e.getMessage());
        }
    }

    @FXML
    void Requests(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("page2.fxml"));
            BorderPane root = loader.load();
            Stage stage = (Stage) btnReq.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the requests page! " + e.getMessage());
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}