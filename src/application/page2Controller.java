package application;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class page2Controller implements Initializable {

    @FXML
    private TableColumn<ObservableList<String>, String> classC;

    @FXML
    private ChoiceBox<String> classCB;
    
    @FXML
    private ChoiceBox<String> classCB2;

    private String[] classes = {"1st grade", "2nd grade", "3rd grade", "4th grade", "5th grade"};

    @FXML
    private TableColumn<ObservableList<String>, String> dayC;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<ObservableList<String>, String> hourC;

    @FXML
    private TextField id;

    @FXML
    private TableColumn<ObservableList<String>, String> idC;

    @FXML
    private Button search;

    @FXML
    private Button search1;

    @FXML
    private TableColumn<ObservableList<String>, String> subjectC;

    @FXML
    private TextField subject;

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private TableColumn<ObservableList<String>, String> teacherC;

    @FXML
    private TableColumn<ObservableList<String>, String> teachercC;

    private ObservableList<ObservableList<String>> scheduleData;

    private final db_connection db = new db_connection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classCB2.getItems().addAll(classes);
        classCB.getItems().addAll(classes);
        initializeTable();
    }

    private void initializeTable() {
        idC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(0)));
        classC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(1)));
        dayC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(2)));
        subjectC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(3)));
        hourC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(4)));
        teacherC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(5)));
        teachercC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(6)));

        scheduleData = FXCollections.observableArrayList();
        table.setItems(scheduleData);
    }

    @FXML
    void Delete(ActionEvent event) {
        String scheduleId = id.getText();

        if (scheduleId.isEmpty()) {
            showAlert("Error", "ID is required!");
            return;
        }

        String query = "DELETE FROM sessions WHERE id = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, scheduleId);
            stmt.executeUpdate();
            // Reapply the current search filter
            Search(event);
            showAlert("Success", "Schedule deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete schedule! " + e.getMessage());
        }
    }

    @FXML
    void search1(ActionEvent event) {
        String selectedClass = classCB.getValue();
        String courseSubject = subject.getText();

        if (selectedClass == null || courseSubject.isEmpty()) {
            showAlert("Error", "Class and Subject are required!");
            return;
        }

        scheduleData.clear();
        String query = "SELECT s.id, c.name AS class, s.day_of_week, s.subject, s.time, t.name AS teacher, t.contact " +
                       "FROM sessions s " +
                       "JOIN classes c ON s.class_id = c.id " +
                       "JOIN teachers t ON s.teacher_id = t.id " +
                       "WHERE c.name = ? AND s.subject = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, selectedClass);
            stmt.setString(2, courseSubject);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("id"));
                row.add(rs.getString("class"));
                row.add(rs.getString("day_of_week"));
                row.add(rs.getString("subject"));
                row.add(rs.getString("time"));
                row.add(rs.getString("teacher"));
                row.add(rs.getString("contact"));
                scheduleData.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load schedule! " + e.getMessage());
        }
    }

    @FXML
    void Search(ActionEvent event) {
        String selectedClass = classCB2.getValue();

        if (selectedClass == null) {
            showAlert("Error", "Class is required!");
            return;
        }

        scheduleData.clear();
        String query = "SELECT s.id, c.name AS class, s.day_of_week, s.subject, s.time, t.name AS teacher, t.contact " +
                       "FROM sessions s " +
                       "JOIN classes c ON s.class_id = c.id " +
                       "JOIN teachers t ON s.teacher_id = t.id " +
                       "WHERE c.name = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, selectedClass);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("id"));
                row.add(rs.getString("class"));
                row.add(rs.getString("day_of_week"));
                row.add(rs.getString("subject"));
                row.add(rs.getString("time"));
                row.add(rs.getString("teacher"));
                row.add(rs.getString("contact"));
                scheduleData.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load schedule! " + e.getMessage());
        }
    }

    private void loadSchedule() {
        scheduleData.clear();
        String query = "SELECT s.id, c.name AS class, s.day_of_week, s.subject, s.time, t.name AS teacher, t.contact " +
                       "FROM sessions s " +
                       "JOIN classes c ON s.class_id = c.id " +
                       "JOIN teachers t ON s.teacher_id = t.id";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("id"));
                row.add(rs.getString("class"));
                row.add(rs.getString("day_of_week"));
                row.add(rs.getString("subject"));
                row.add(rs.getString("time"));
                row.add(rs.getString("teacher"));
                row.add(rs.getString("contact"));
                scheduleData.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load schedule! " + e.getMessage());
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