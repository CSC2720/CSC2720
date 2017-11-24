package Constructs;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Main extends Application {

	Stage window;
	Scene Master, EmployeeCreator, EmployeeInstantiated, EmployeeRecords, EmployeeSchedule, EmployeePayroll;
	Label firstNameVaid,lastNameValid, SSNValid=new Label(""), phoneValid;
	TextField LastNameField, FirstNameField, TitleField, SSNField, AddressField, PhoneField;
	TableView<Employee> EmployeeRecord;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		//Master-Labels and Buttons
		Label AddEmployeeLab = new Label("Add an Employee to the records");
		Button AddEmployeeBut = new Button("Click to add Employee");
		AddEmployeeBut.setOnAction(e -> {
			window.setScene(EmployeeCreator);
			window.setTitle("Employee Creator")
			;});
		Label RetrieveLab = new Label("Employee Records");
		Button RetrieveBut = new Button("Click to Retrieve Employee information");
		RetrieveBut.setOnAction(e -> {
			EmployeeRecord.setItems(getInfo());
			window.setScene(EmployeeRecords);
			window.setTitle("Employee Information");
		});
		Label ScheduleLab = new Label("Employee Schedule");
		Button ScheduleBut = new Button("Click to see Employee Schedule");
		ScheduleBut.setOnAction(e -> window.setScene(EmployeeSchedule));
		Label PayrollLab = new Label("Employee Payroll");
		Button PayrollBut = new Button("Click to to see payroll");
		PayrollBut.setOnAction(e -> window.setScene(EmployeePayroll));

		//Master-LayOut
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(AddEmployeeLab,AddEmployeeBut,RetrieveLab,RetrieveBut,ScheduleLab,ScheduleBut,PayrollLab,PayrollBut);
		Master = new Scene(layout1, 400, 400);
		//EmployeeCreator Buttons, Labels, and Textfields

		Button ReturnButton1 = new Button("Return to Main Menu");
		ReturnButton1.setOnAction(e -> window.setScene(Master));
		Label RequiredFields = new Label("* Means Required");
		Label LastNameText = new Label("Last Name*:");
		LastNameField= new TextField();
		Label FirstNameText = new Label("First Name*:");
		FirstNameField= new TextField();
		Label TitleText = new Label("JobTitle*:");
		TitleField= new TextField();
		Label SSNText = new Label("Social Security Number*:");
		SSNField= new TextField();
		SSNField.setOnKeyPressed(e -> {
			if(SSNField.getText().length()<9) {
				SSNValid.setText("The Value Entered Is Too Short");
			}else {
			String text=SSNField.getText();
			for(int i=0; i <text.length();i++)
				if((int) text.charAt(i)<48||(int)text.charAt(i)>58) {
					SSNValid.setText("There Is A Character That Is Not A Number");
					break;
				}
			SSNValid.setText("Seems good!");
			}
		});
		Label AddressText = new Label("Address:");
		AddressField= new TextField();
		Label PhoneText = new Label("Phone Number:");
		PhoneField= new TextField();
		Button Submit= new Button("Submit");
		Submit.setOnAction(e -> {
			if (SSNValidityCheck(SSNField)&&PhoneValidityCheck(PhoneField)&&notNull(LastNameField,FirstNameField,TitleField,SSNField)) {
				String lastName = LastNameField.getText();
				String firstName = FirstNameField.getText(); 
				String title = TitleField.getText();
				int ssn = Integer.valueOf(SSNField.getText());
				String address = AddressField.getText();
				String phone=null;
				if(!PhoneField.getText().isEmpty()) {
					phone = PhoneField.getText();
				}
				new Employee(lastName,firstName,title,ssn,address,phone);
				LastNameField.clear();
				FirstNameField.clear();
				TitleField.clear();
				SSNField.clear();
				AddressField.clear();
				PhoneField.clear();
				window.setScene(EmployeeInstantiated);
			}
		});

		//EmployeeCreator LayOut
		VBox layout2 = new VBox();
		layout2.getChildren().addAll(LastNameText,LastNameField,FirstNameText,FirstNameField,TitleText,TitleField,SSNText,SSNField,SSNValid,AddressText,AddressField,PhoneText,PhoneField,RequiredFields,Submit,ReturnButton1);
		EmployeeCreator = new Scene(layout2, 400, 600);
		//EmployeeInstantiated Buttons And Labels 
		Button ReturnButton2 = new Button("Return to Main Menu");
		ReturnButton2.setOnAction(e -> window.setScene(Master));
		Label Success= new Label("The Employee Information Has Been Stored");
		//EmployeeInstantiated LayOut
		VBox layout3 = new VBox();
		layout3.getChildren().addAll(ReturnButton2,Success);
		EmployeeInstantiated = new Scene(layout3, 600, 300);
		//Neel use this format for generating tables
		Button ReturnButton3 = new Button("Return to Main Menu");
		ReturnButton3.setOnAction(e -> window.setScene(Master));

		TableColumn<Employee, Integer> EIDColumn = new TableColumn<>("Employee ID");
		EIDColumn.setMinWidth(200);
		EIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
		lastNameColumn.setMinWidth(200);
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
		firstNameColumn.setMinWidth(200);
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		TableColumn<Employee, String> titleColumn = new TableColumn<>("Job Title");
		titleColumn.setMinWidth(200);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Employee, Integer> SSNColumn = new TableColumn<>("SSN");
		SSNColumn.setMinWidth(200);
		SSNColumn.setCellValueFactory(new PropertyValueFactory<>("SSN"));
		TableColumn<Employee, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setMinWidth(200);
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		TableColumn<Employee, String> phoneColumn = new TableColumn<>("Phone");
		phoneColumn.setMinWidth(200);
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		EmployeeRecord = new TableView<>();
		EmployeeRecord.setItems(getInfo());
		EmployeeRecord.getColumns().addAll(EIDColumn,lastNameColumn, firstNameColumn, titleColumn, SSNColumn, addressColumn, phoneColumn );

		VBox layout4 = new VBox();
		layout4.getChildren().addAll(EmployeeRecord,ReturnButton3);
		EmployeeRecords = new Scene(layout4);
		//Neel Stop here
		//Display scene 1 at first
		window.setScene(Master);
		window.setTitle("Manager Application");
		window.show();
	}

	public ObservableList<Employee> getInfo(){
		ObservableList<Employee> EmployeeInfo = FXCollections.observableArrayList(Arrays.asList(new Employee().getAllEID()));
		return EmployeeInfo;
	}
	public boolean notNull(TextField ln, TextField fn, TextField t, TextField ssn) {
		return !ln.getText().isEmpty()&&!fn.getText().isEmpty()&&!t.getText().isEmpty()&&!ssn.getText().isEmpty();
	}
	public boolean SSNValidityCheck(TextField SSNField) {
		String text=SSNField.getText();
		for(int i=0; i <text.length();i++)
			if((int) text.charAt(i)<48||(int)text.charAt(i)>58) {
				AlertBox.display("Error","INVALID!!! There is a Character Is Not A Number");
				return false;
			}
		if(text.length()!=9) {
			AlertBox.display("Error","INVALID!!! A Social Security Number Needs 9 Numbers");
			return false;
		}
		Employee [] a = new Employee().getAllEID();
		for(int i=0;i<a.length;i++) {
			if(Integer.valueOf(text)==a[i].getSSN()) {
				AlertBox.display("Error","INVALID!!! Social Security Is Not Unique");
				return false;
			}
		}
		return true;
	}
	public boolean PhoneValidityCheck(TextField PhoneField) {
		String text=PhoneField.getText();
		if(!text.isEmpty()) {
			for(int i=0; i <text.length();i++)
				if((int) text.charAt(i)<48||(int)text.charAt(i)>58) {
					AlertBox.display("Error","INVALID!!! That Character Is Not A Number");
					return false;
				}
			if(text.length()<9||text.length()>12) {
				AlertBox.display("Error","INVALID!!! A Phone Number Needs 9-12 Numbers");
				return false;
			}
			Employee [] a = new Employee().getAllEID();
			for(int i=0;i<a.length;i++) {
				if(a[i].getPhone()!=null)
					if(text==a[i].getPhone()) {
						AlertBox.display("Error","INVALID!!! Phone Number Is Not Unique");
						return false;
					}
			}
		}
		return true;
	}

}