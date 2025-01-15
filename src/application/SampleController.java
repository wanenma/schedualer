package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SampleController implements Initializable{

    @FXML
    private TextField Tnumber;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAdd2;

    @FXML
    private Button btnMod;

    @FXML
    private Button btnReq;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btndel;

    @FXML
    private TableColumn<?, ?> classC;

    @FXML
    private ChoiceBox<String> classCB;
    
    private String[] classes= {"1st grade", "2nd grade", "3rd grade", "4th grade", "5th grade"};

    @FXML
    private TextField contact;

    @FXML
    private TableColumn<?, ?> contactC;

    @FXML
    private TableColumn<?, ?> dayC;
    
    @FXML
    private ChoiceBox<String> dayCB;
    
    private String[] days= {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    @FXML
    private TableColumn<?, ?> hourC;

    @FXML
    private ChoiceBox<String> hourCB;
    
    private String[] hours= {"1st period", "2nd period", "3rd period", "4th period", "5th period", "6th period"};

    @FXML
    private TextField name;

    @FXML
    private TableColumn<?, ?> nameC;

    @FXML
    private TextField number;

    @FXML
    private TableColumn<?, ?> numberC;

    @FXML
    private TextField subject;

    @FXML
    private TableColumn<?, ?> subjectC;

    @FXML
    private TableView<?> table1;

    @FXML
    private TableView<?> table2;

    @FXML
    private TableColumn<?, ?> teacherC;
    

    @FXML
    void Add(ActionEvent event) {

    }

    @FXML
    void Add2(ActionEvent event) {

    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Modify(ActionEvent event) {

    }

    @FXML
    void Requests(ActionEvent event) {

    }

    @FXML
    void Search(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		classCB.getItems().addAll(classes);
		dayCB.getItems().addAll(days);
		hourCB.getItems().addAll(hours);
	}

}
