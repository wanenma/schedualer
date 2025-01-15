package application;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
public class page2Controller implements Initializable{

    @FXML
    private TableColumn<?, ?> classC;

    @FXML
    private ChoiceBox<String> classCB;
    
    private String[] classes= {"1st grade", "2nd grade", "3rd grade", "4th grade", "5th grade"};
 

    @FXML
    private TableColumn<?, ?> dayC;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<?, ?> hourC;

    @FXML
    private TextField id;

    @FXML
    private TableColumn<?, ?> idC;

    @FXML
    private Button search;

    @FXML
    private TableColumn<?, ?> subjectC;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> teacherC;

    @FXML
    private TableColumn<?, ?> teachercC;

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Search(ActionEvent event) {

    }

    @FXML
    void subject(ActionEvent event) {

}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		classCB.getItems().addAll(classes);
		
	}}
