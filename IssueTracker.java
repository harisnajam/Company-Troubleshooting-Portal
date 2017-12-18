/*
 * Names:       Haris Najam, Danish Paracha, Gurpreet Kaur
 * Course:      PROG24178
 * Assignment:  Final Group Project
 * Instructor:  Will Channa
 * Class:       IssueTracker.java
 *
 */

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.io.File;

public class IssueTracker extends Application {

	/* There will be 4 Scenes in total, each with their major panes */
	GridPane pane1 = new GridPane();
	BorderPane pane2 = new BorderPane();
	BorderPane pane3 = new BorderPane();
	BorderPane pane4 = new BorderPane();
	Scene scene1 = new Scene(pane1, 700, 300);
	Scene scene2 = new Scene(pane2, 700, 500);
	Scene scene3 = new Scene(pane3, 700, 500);
	Scene scene4 = new Scene(pane4, 700, 500);

	/* Elements, Controls, and Panes for Sign In screen */
	Label lblUsername = new Label("Username   ");
	Label lblPassword = new Label("Password   ");
	Label lblTitle = new Label("Welcome to Issue Tracker");
	Label lblError = new Label();
	TextField txtUsername = new TextField();
	TextField txtPassword = new TextField();
	Button btnLogin = new Button("Sign in");

	/* Elements, Controls, and Pane for Issue List screen */
	Label lblTitle2 = new Label("  Issues List");
	Label lblGreeting2 = new Label();
	Button btnLogOut = new Button("\u2190 Log Out");
	Button btnSubmitNew = new Button("New Issue");
	Button btnAddUser = new Button("Add User");
	VBox paneTitle2 = new VBox();
	HBox paneButton2 = new HBox();
	VBox paneCenter2 = new VBox();
	ListView<Issue> lstIssue = new ListView();          // This will display the issues

	/* Elements, Controls, and Panes for the Issue Details screen */
	Label lblTitle3 = new Label("  Issue Details:");
	Label lblGreeting3 = new Label();
        Label lblNumber = new Label("Issue Number:");
        Label lblNumberShow = new Label();
        Label lblUser = new Label("Issued By:");
        Label lblUserShow = new Label();
        Label lblAssigned = new Label("Assigned To:");
        Label lblAssignedShow = new Label();
        Label lblSubject = new Label("Subject:");
        Label lblSubjectShow = new Label();
        Label lblStatus = new Label("Status:");
        Label lblStatusShow = new Label();
        Label lblDetails = new Label("Details:");
        Label lblDetailsShow = new Label();
        TextField txtSubject =  new TextField();
        TextArea taDetails = new TextArea();
        Label lblError3 = new Label();
	Button btnCancel3 = new Button("\u2717 Cancel");
	Button btnSubmitIssue = new Button("\u2713 Submit");
	Button btnOpen = new Button("\u2713 Open");
	Button btnReject = new Button("\u2717 Reject");
	Button btnResolve = new Button("\u2713 Resolve");
	Button btnAssign = new Button("Assign to developer");
	Button btnClose = new Button("Close Issue");
	Button btnValidate = new Button("\u2713 Validate");
	Button btnFail = new Button("\u2717 Fail Issue");
        ChoiceBox<Developer> cbDeveloper = new ChoiceBox();
 	VBox paneTitle3 = new VBox();
	HBox paneButton3 = new HBox();
	VBox paneCenterLeft = new VBox();
        VBox paneCenterRight = new VBox();
        
        /* Elements, Controls, and Panes for the Add User screen */
        Label lblTitle4 = new Label("  Add New User");
	Label lblGreeting4 = new Label();
        Label lblNewUser = new Label("Enter Username:");
        Label lblNewPassword = new Label("Enter Password:");
        Label lblType = new Label("Select Type:");
        Label lblError4 = new Label();
        TextField txtNewUser = new TextField();
        TextField txtNewPassword = new TextField();
        ChoiceBox cbType = new ChoiceBox(FXCollections.observableArrayList(
                                         "User", "Developer", "Manager")
        );
        Button btnCancel4 = new Button("\u2717 Cancel");
        Button btnSubmit4 = new Button("\u2713 Submit");
        VBox paneTitle4 = new VBox();
	HBox paneButton4 = new HBox();
	VBox paneCenterLeft4 = new VBox();
        VBox paneCenterRight4 = new VBox();

        /* All users and issues will be saved in their relevant ObservableLists */
        ObservableList<User> userList = FXCollections.observableArrayList();
	ObservableList<Developer> developerList = FXCollections.observableArrayList();
	ObservableList<Manager> managerList = FXCollections.observableArrayList();
	ObservableList<Issue> issueListMaster = FXCollections.observableArrayList();    // The master list will contain all of the issues from which the specific issues will be extracted
        ObservableList<Issue> issueListTemp = FXCollections.observableArrayList();      // The temp list will extract and display specific issues from the master list based on the user

	/* Global variables */
	String loggedInType;                // Specifies whether the user is a User, Developer, or Manager
	String loggedInUser;                // Specifies the Username of the user
	boolean usernameExists = false;     // Specifies whether the entered username exists
	boolean passwordMatches = false;    // Specifies whether the password matches for the username
        
        public static void main(String[] args) {
               launch(args);
        }

	public void start(Stage primaryStage) {
                
                /* Stylesheets for all the scenes */
		String stylesheet1 = getClass().getResource("login.css").toExternalForm();
		String stylesheet2 = getClass().getResource("list.css").toExternalForm();
                String stylesheet3 = getClass().getResource("detail.css").toExternalForm();
                String stylesheet4 = getClass().getResource("adduser.css").toExternalForm();
        	scene1.getStylesheets().add(stylesheet1);
        	scene2.getStylesheets().add(stylesheet2);
                scene3.getStylesheets().add(stylesheet3);
                scene4.getStylesheets().add(stylesheet4);

		load();

                /* [1] All of the nodes are set up for the first scene - the Login Scene */
                ColumnConstraints col10 = new ColumnConstraints();
		col10.setPercentWidth(10);
		pane1.getColumnConstraints().addAll(col10,col10,col10,col10,col10,col10,col10,col10,col10,col10);
		pane1.add(lblTitle, 1, 0, 7, 1);
		pane1.add(lblUsername, 0, 2, 3, 1);
		pane1.add(lblPassword, 0, 3, 3, 1);
		pane1.add(txtUsername, 3, 2, 6, 1);
		pane1.add(txtPassword, 3, 3, 6, 1);
		pane1.add(lblError, 3, 4, 4, 1);
		pane1.add(btnLogin, 6, 4, 3, 1);
		pane1.setPadding(new Insets(60, 40, 80, 40));
		pane1.setVgap(5);
		GridPane.setHalignment(lblUsername, HPos.RIGHT);
		GridPane.setHalignment(lblPassword, HPos.RIGHT);
		btnLogin.setMaxWidth(Double.MAX_VALUE);
		lblTitle.setId("lblTitle");
		lblError.setId("lblError");
                
		btnLogin.setOnAction(e -> {
			clickLogin();
			if (usernameExists && passwordMatches) {
                                prepareListView();
				primaryStage.setScene(scene2);
			}
		});
		txtUsername.setOnKeyPressed(e -> {
			lblError.setText("");
			if (e.getCode() == KeyCode.ENTER)  {
				clickLogin();
				if (usernameExists && passwordMatches) {
                                        prepareListView();
					primaryStage.setScene(scene2);
				}
			}
		});
		txtPassword.setOnKeyPressed(e -> {
			lblError.setText("");
			if (e.getCode() == KeyCode.ENTER)  {
				clickLogin();
				if (usernameExists && passwordMatches) {
                                        prepareListView();
					primaryStage.setScene(scene2);
				}
			}
		});

                /* [2] All of the nodes are set up for the second scene - the List View Scene */
		paneTitle2.getChildren().addAll(lblGreeting2, lblTitle2);
		paneButton2.getChildren().addAll(btnLogOut, btnSubmitNew, btnAddUser);
		paneCenter2.getChildren().addAll(lstIssue);
		paneButton2.setAlignment(Pos.CENTER);
		paneCenter2.setAlignment(Pos.CENTER);
		pane2.setTop(paneTitle2);
		pane2.setBottom(paneButton2);
		pane2.setCenter(paneCenter2);
		lblGreeting2.setId("lblGreeting2");
		lblTitle2.setId("lblTitle2");
		paneTitle2.setId("listTitle");
		paneButton2.setId("paneBtn");
		paneCenter2.setId("paneCenter");
                
                btnLogOut.setOnAction(e -> {
			clickLogOut();
			primaryStage.setScene(scene1);
		});
                btnSubmitNew.setOnAction(e -> {
                        prepareDetailViewNew();
                        primaryStage.setScene(scene3);
                        txtSubject.requestFocus();
                });
                btnAddUser.setOnAction(e -> {
                        prepareAddUserView();
                        primaryStage.setScene(scene4);
                });
                lstIssue.setOnMouseClicked(e -> {
                        if (!lstIssue.getSelectionModel().isEmpty()) {
                                prepareDetailViewExisting();
                                primaryStage.setScene(scene3);
                        }
                });

                /* [3] All of the nodes are set up for the third scene - the Issue Details Scene */
		paneTitle3.getChildren().addAll(lblGreeting3, lblTitle3);
		paneButton3.getChildren().addAll(btnCancel3, btnSubmitIssue, btnOpen, btnReject, btnResolve,
						 btnAssign, btnClose, btnValidate, btnFail);
                paneCenterLeft.getChildren().addAll(lblNumber, lblUser, lblStatus, lblAssigned,
                                                    lblSubject, lblDetails, lblError3);
                paneCenterRight.getChildren().addAll(lblNumberShow, lblUserShow, lblStatusShow, lblAssignedShow,
                                                     cbDeveloper, lblSubjectShow, lblDetailsShow, txtSubject, taDetails);
                pane3.setTop(paneTitle3);
                pane3.setBottom(paneButton3);
                pane3.setLeft(paneCenterLeft);
                pane3.setRight(paneCenterRight);
                paneCenterLeft.setAlignment(Pos.TOP_RIGHT);
                paneCenterRight.setAlignment(Pos.TOP_LEFT);
                paneButton3.setAlignment(Pos.CENTER);
                cbDeveloper.setTooltip(new Tooltip("Select the Developer"));
                paneCenterLeft.setId("paneleft");
                paneCenterRight.setId("paneright");
                lblTitle3.setId("title");
                lblGreeting3.setId("greeting");
                paneButton3.setId("panebutton");
                paneTitle3.setId("panetitle");
                taDetails.setId("details");
                lblError3.setId("lblError");
                lblSubjectShow.setId("lblSubjectShow");
                lblDetailsShow.setId("lblDetailsShow");
                
                btnSubmitIssue.setOnAction(e -> {                        
                        if (checkSubmit()) {
                                primaryStage.setScene(scene2);
                        }     
                });
                txtSubject.setOnKeyPressed(e -> {
                        lblError3.setText("");
                });
                taDetails.setOnKeyPressed(e -> {
                        lblError3.setText("");
                });
                btnCancel3.setOnAction(e -> {
                        prepareCancelDetails();
                        primaryStage.setScene(scene2);
                });
                btnAssign.setOnAction(e -> {
                        if(assignDeveloper()) {
                                primaryStage.setScene(scene2);
                        }
                });
                btnOpen.setOnAction(e -> {
                        openIssue();
                        primaryStage.setScene(scene2);
                });
                btnReject.setOnAction(e -> {
                        rejectIssue();
                        primaryStage.setScene(scene2);
                });
                btnResolve.setOnAction(e -> {
                        resolveIssue();
                        primaryStage.setScene(scene2);
                });
                btnValidate.setOnAction(e -> {
                        validateIssue();
                        primaryStage.setScene(scene2);
                });
                btnFail.setOnAction(e -> {
                        failIssue();
                        primaryStage.setScene(scene2);
                });
                btnClose.setOnAction(e -> {
                        closeIssue();
                        primaryStage.setScene(scene2);
                });
                
                /* [4] All of the nodes are set up for the fourth scene - the Add New User scene */
                paneTitle4.getChildren().addAll(lblGreeting4, lblTitle4);
		paneButton4.getChildren().addAll(btnCancel4, btnSubmit4);
                paneCenterLeft4.getChildren().addAll(lblNewUser, lblNewPassword, lblType, lblError4);
                paneCenterRight4.getChildren().addAll(txtNewUser, txtNewPassword, cbType);
                pane4.setTop(paneTitle4);
                pane4.setBottom(paneButton4);
                pane4.setLeft(paneCenterLeft4);
                pane4.setRight(paneCenterRight4);
                paneCenterLeft4.setAlignment(Pos.CENTER_RIGHT);
                paneCenterRight4.setAlignment(Pos.CENTER_LEFT);
                paneButton4.setAlignment(Pos.CENTER);
                cbType.setTooltip(new Tooltip("Select type of User"));
                lblError4.setId("lblError");
                paneCenterLeft4.setId("paneleft");
                paneCenterRight4.setId("paneright");
                lblTitle4.setId("title");
                lblGreeting4.setId("greeting");
                paneButton4.setId("panebutton");
                paneTitle4.setId("panetitle");
                
                btnCancel4.setOnAction(e -> {
                        prepareListView();
                        primaryStage.setScene(scene2);
                });
                btnSubmit4.setOnAction(e -> {
                        if (addNewUser()) {
                                primaryStage.setScene(scene2);
                        }
                });
                txtNewUser.setOnKeyPressed(e -> {
			lblError4.setText("");
                        if (e.getCode() == KeyCode.ENTER) {
                                if (addNewUser()) {
                                        primaryStage.setScene(scene2);
                                }
                        }
		});
                txtNewPassword.setOnKeyPressed(e -> {
			lblError4.setText("");
                        if (e.getCode() == KeyCode.ENTER) {
                                if (addNewUser()) {
                                        primaryStage.setScene(scene2);
                                }
                        }
		});

                primaryStage.setTitle("Issue Tracker Program");
		primaryStage.setScene(scene1);
		primaryStage.show();
	}

        /**
         * This method is to be executed when the program will run. It will set the initial
         * settings.
         */
	private void load() {
		lblError.setText("");
                File fileUser = new File("users.dat");
                File fileDeveloper = new File("developers.dat");
                File fileManager = new File("managers.dat");
                File fileIssue = new File("issues.dat");
                
                /* The following code will take the data from the users.dat, developers.dat, managers.dat,
                 * and issues.dat files and store them into ObservableLists. If any file does not
                 * exist, an empty one will be created.
                 */
		try {
                        fileUser.createNewFile();
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileUser));
                        
                        User userTemp;
                        while ((userTemp = (User) input.readObject()) != null) {
                                userList.add(userTemp);
                        }
			input.close();
		} catch (EOFException EOFe) {
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
                
                try {
                        fileDeveloper.createNewFile();
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileDeveloper));
                        
                        Developer developerTemp;
                        while ((developerTemp = (Developer) input.readObject()) != null) {
                                developerList.add(developerTemp);
                        }
			input.close();
		} catch (EOFException EOFe) {
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
                
                try {
                        fileManager.createNewFile();
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileManager));
                        
                        Manager managerTemp;
                        while ((managerTemp = (Manager) input.readObject()) != null) {
                                managerList.add(managerTemp);
                        }
			input.close();
		} catch (EOFException EOFe) {
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
                
                try {
                        fileIssue.createNewFile();
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileIssue));
                        
                        Issue issueTemp;
                        while ((issueTemp = (Issue) input.readObject()) != null) {
                                issueListMaster.add(issueTemp);
                        }
			input.close();
		} catch (EOFException EOFe) {
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
                
                /* In case a managers.dat file will be empty, a default Manager will be added to the
                 * Observable list so that this application will have the minimum functionality.
                 */
                if (managerList.size() == 0) {
                        managerList.add(new Manager("admin", "admin"));
                }
	}

        /**
         * The clickLogin() method will make sure that the username and password entered match with
         * an existing user. If not, an error message will be displayed.
         */
	private void clickLogin() {
		usernameExists = false;
		passwordMatches = false;
                
                /* The following code will cycle through all of the users' lists to find a match */
		for (User u : userList) {
                    
			if (u.getUsername().toLowerCase().equals(txtUsername.getText().toLowerCase().trim())) {
                                usernameExists = true;

                                if (u.getPassword().equals(txtPassword.getText())) {
                                        passwordMatches = true;
                                }
                                loggedInType = "User";
                                loggedInUser = u.getUsername();
                                break;
                        }
		}

		if (!usernameExists) {
                    
			for (Developer d : developerList) {
                            
				if (d.getUsername().toLowerCase().equals(txtUsername.getText().toLowerCase().trim())) {
					usernameExists = true;
                                        
					if (d.getPassword().equals(txtPassword.getText())) {
						passwordMatches = true;
					}
					loggedInType = "Developer";
					loggedInUser = d.getUsername();
					break;
				}
			}
		}

		if (!usernameExists) {
                    
			for (Manager m : managerList) {
                            
				if (m.getUsername().toLowerCase().equals(txtUsername.getText().toLowerCase().trim())) {
					usernameExists = true;
                                        
					if (m.getPassword().equals(txtPassword.getText())) {
						passwordMatches = true;
					}
					loggedInType = "Manager";
					loggedInUser = m.getUsername();
					break;
				}
			}
		}
		if (txtUsername.getText().equals("")) {
			lblError.setText("Enter a username");
		} else if (txtPassword.getText().equals("")) {
			lblError.setText("Enter password");
		} else if (!usernameExists) {
			lblError.setText("Username does not exist.");			
		} else if (!passwordMatches) {
			lblError.setText("Incorrect password.");
		}
	}

        /**
         * The prepareListView() method will run if a successful login will happen. It
         * will also run if the user will return from the Issue Details screen or from the
         * Add New User scene. This will prepare the second scene (the List View scene)
         * based on the type of user who will be logged in.
         */
	private void prepareListView() {
		lblGreeting2.setText("    Welcome, " + loggedInUser);
		lblGreeting3.setText("    Welcome, " + loggedInUser);
                lblGreeting4.setText("    Welcome, " + loggedInUser);
                issueListTemp.clear();
                lstIssue.getSelectionModel().clearSelection();

                /* The following code will display the list items based on the logged-in user. A regular
                 * user will only see the issues that he has issued. A developer will see issues that he
                 * has issued plus any other issues assigned to him. A manager will see only non-closed issues
                 * from all users.
                 */
                for (Issue i : issueListMaster) {
                        if (loggedInType.equals("User")) {
                                if (i.getUsername().equals(loggedInUser)) {
                                        issueListTemp.add(i);
                                }
                        } else if (loggedInType.equals("Developer")) {
                                if (i.getUsername().equals(loggedInUser)) {
                                        issueListTemp.add(i);
                                } else if (i.getAssigned() != null) {
                                        if (i.getAssigned().getUsername().equals(loggedInUser)) {
                                                issueListTemp.add(i);
                                        }
                                }
                        } else {
                                if (!i.getStatus().equals("Closed")) {
                                        issueListTemp.add(i);
                                }
                        }       
                }
                
                /* The correct set of issues will be displayed in the Issue list */
		lstIssue.setItems(issueListTemp);
                
                /* An "Add User" button will become available if the user will be a Manager */
		if (loggedInType.equals("Manager")) {
			enable(btnAddUser);
		} else {
			disable(btnAddUser);
		}
	}
        
        /**
         * This method will prepare all of the nodes and other settings in the third scene, the
         * Issue Details scene, if the user will get there by clicking on the "New Issue" button.
         * This screen will look identical for all users.
         */
        private void prepareDetailViewNew() {
                txtSubject.setText("");
                taDetails.setText("");
                lblError3.setText("");
                lblNumberShow.setText("# " + String.format("%03d", issueListMaster.size() + 1));
                Node[] newEnable = {btnSubmitIssue, txtSubject, taDetails};
                Node[] newDisable = {btnOpen, btnReject, btnResolve, btnAssign, btnClose, btnValidate, btnFail,
                                     lblUser, lblStatus, lblAssigned, lblUserShow, lblStatusShow, lblAssignedShow,
                                     cbDeveloper, lblSubjectShow, lblDetailsShow};                
                enable(newEnable);
                disable(newDisable);
        }
        
        /**
         * This method will also prepare all of the nodes and other settings in the third scene if
         * the user will get there by viewing an existing scene. There will be different inputs,
         * controls, and labels based on the user and the issue's status. Initially all of the nodes
         * which will have to be disabled in at least one scenario will be disabled. After that,
         * depending on the specific situation, the appropriate nodes will be enabled again for
         * the user to interact with.
         */
        private void prepareDetailViewExisting() {
                lblNumberShow.setText("# " + String.format("%03d", issueListMaster.size() + 1));
                Issue issueSelected = lstIssue.getSelectionModel().getSelectedItem();
                Node[] allNodes = {btnSubmitIssue, btnOpen, btnReject, btnResolve, btnAssign, btnClose, btnValidate,
                                   btnFail, lblUser, lblStatus, lblAssigned, lblUserShow, lblStatusShow, lblAssignedShow,
                                   cbDeveloper, lblSubjectShow, lblDetailsShow, txtSubject, taDetails};
                disable(allNodes);
                
                if (loggedInType.equals("User")) {
                        Node[] enableNodes = {lblStatus, lblStatusShow, lblSubject, lblSubjectShow, lblDetailsShow};
                        enable(enableNodes);
                } else if (loggedInType.equals("Developer")) {
                    
                        if (issueSelected.getStatus().equals("New")) {
                                Node[] enableNodes = {lblUser, lblStatus, lblUserShow, lblStatusShow, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else if (issueSelected.getStatus().equals("Assigned")) {
                                Node[] enableNodes = {btnOpen, btnReject, lblUser, lblStatus, lblUserShow,
                                                      lblStatusShow, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else if (issueSelected.getStatus().equals("Resolved")) {
                                Node[] enableNodes = {lblUser, lblStatus, lblUserShow, lblStatusShow,
                                                      lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else {
                                Node[] enableNodes = {btnResolve, lblUser, lblStatus, lblUserShow, lblStatusShow,
                                                      lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        }
                } else {
                
                        if (issueSelected.getStatus().equals("New")) {
                                Node[] enableNodes = {btnAssign, lblUser, lblStatus, lblAssigned, lblUserShow,
                                                      lblStatusShow, cbDeveloper, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else if (issueSelected.getStatus().equals("Assigned")) {
                                Node[] enableNodes = {lblUser, lblStatus, lblAssigned, lblUserShow, lblStatusShow,
                                                      lblAssignedShow, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else if (issueSelected.getStatus().equals("Resolved")) {
                                Node[] enableNodes = {btnValidate, btnFail, lblUser, lblStatus, lblAssigned, lblUserShow,
                                                      lblStatusShow, lblAssignedShow, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else if (issueSelected.getStatus().equals("Rejected")) {
                                Node[] enableNodes = {btnAssign, btnClose, lblUser, lblStatus, lblUserShow,
                                                      lblStatusShow, cbDeveloper, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else if (issueSelected.getStatus().equals("Validated")) {
                                Node[] enableNodes = {btnClose, lblUser, lblStatus, lblUserShow, lblStatusShow,
                                                      lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        } else {                            // i.e, Opened or Failed
                                Node[] enableNodes = {lblUser, lblStatus, lblAssigned, lblUserShow, lblStatusShow,
                                                      lblAssignedShow, lblSubjectShow, lblDetailsShow};
                                enable(enableNodes);
                        }
                        prepareChoiceBox();     // The ChoiceBox will be populated with the developers. It is not visisble for all cases.
                }
                
                /* All of the labels which will give the details of the issue will be set to display the
                 * appropriate information. Not all of them will be visible in the different situations.
                */
                lblUserShow.setText(issueSelected.getUsername());
                lblStatusShow.setText(issueSelected.getStatus());
                lblSubjectShow.setText(issueSelected.getSubject());
                lblDetailsShow.setText(issueSelected.getDetails());
                String issueStatus = issueSelected.getStatus();
                if (issueStatus.equals("Assigned") || issueStatus.equals("Failed") || issueStatus.equals("Opened") ||
                                issueStatus.equals("Resovled")) {
                        lblAssignedShow.setText(issueSelected.getAssigned().getUsername());
                }
        }
        
        /**
         * This method will determine whether a developer is selected in the ChoiceBox
         */
        private boolean assignDeveloper() {
                if (!cbDeveloper.getSelectionModel().isEmpty()) {
                        int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                        lstIssue.getSelectionModel().getSelectedItem().setAssigned(cbDeveloper.getSelectionModel().getSelectedItem());
                        lstIssue.getSelectionModel().getSelectedItem().setStatus("Assigned");
                        updateFile(index);   
                        prepareListView();
                        lblError3.setText("");
                        return true;
                } else {
                        lblError3.setText("Select a Developer");
                        return false;
                }
        }
        
        /**
         * This method will set the ChoiceBox's default choice to be blank and will
         * populate it based on the number of developers in the system.
         */
        private void prepareChoiceBox() {
                cbDeveloper.getSelectionModel().clearSelection();
                cbDeveloper.getItems().clear();
                for (Developer d : developerList) {
                        cbDeveloper.getItems().add(d);
                }
        }
        
        /**
         * This method will check whether all of the fields for entering a new issue are
         * completed. If one field is empty, an error message will be shown.
         */
        private boolean checkSubmit() {
                if (txtSubject.getText().trim().equals("") || taDetails.getText().trim().equals("")) {
                        lblError3.setText("Complete both fields");
                        return false;
                } else {
                        issueListMaster.add(new Issue(issueListMaster.size() + 1, loggedInUser, "New", null, txtSubject.getText().trim(), taDetails.getText().trim()));
                        lstIssue.setItems(issueListTemp);
                        
                        /* Writing the updated list to the issues file */
                        updateFileNewIssue();
                        prepareListView();
                        return true;
                }
        }
        
        /**
         * If existing issue's status will change, the master list will update the selected issue
         * in itself. After that, it will update the issues.dat file to make the changes permanent.
         */
        private void updateFile(int index) {
                issueListMaster.set(index, lstIssue.getSelectionModel().getSelectedItem());
                
                try {
                        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("issues.dat"));

                        for (Issue i : issueListMaster) {
                                output.writeObject(i);
                        }
                        output.close();
                } catch (EOFException EOFe) {
                } catch (Exception e) {
                        System.out.println("Error: " + e);
                }
        }
        
        /**
         * If a new issue will be submitted, this method will execute. It is similar to the
         * updateFile() method, except that it will not have an index to refer to an existing
         * issue. The issue will be new, so the file will be updated accordingly.
         */
        private void updateFileNewIssue() {              
                try {
                        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("issues.dat"));

                        for (Issue i : issueListMaster) {
                                output.writeObject(i);
                        }
                        output.close();
                } catch (EOFException EOFe) {
                } catch (Exception e) {
                        System.out.println("Error: " + e);
                }
        }
        
        /**
         * This method will execute if the issue's status will change to "Open"
         */
        private void openIssue() {
                int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                lstIssue.getSelectionModel().getSelectedItem().setStatus("Opened");
                updateFile(index);
                prepareListView();
        }
        
        /**
         * This method will execute if the issue's status will change to "Reject"
         */
        private void rejectIssue() {
                int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                lstIssue.getSelectionModel().getSelectedItem().setStatus("Rejected");
                lstIssue.getSelectionModel().getSelectedItem().setAssigned(null);
                updateFile(index);
                prepareListView();
        }
        
        /**
         * This method will execute if the issue's status will change to "Resolved"
         */
        private void resolveIssue() {
                int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                lstIssue.getSelectionModel().getSelectedItem().setStatus("Resolved");
                updateFile(index);
                prepareListView();
        }
        
        /**
         * This method will execute if the issue's status will change to "Validated"
         */
        private void validateIssue() {
                int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                lstIssue.getSelectionModel().getSelectedItem().setStatus("Validated");
                lstIssue.getSelectionModel().getSelectedItem().setAssigned(null);
                updateFile(index);
                prepareListView();
        }
        
        /**
         * This method will execute if the issue's status will change to "Failed"
         */
        private void failIssue() {
                int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                lstIssue.getSelectionModel().getSelectedItem().setStatus("Failed");
                updateFile(index);
                prepareListView();
        }
        
        /**
         * This method will execute if the issue's status will change to "Closed"
         */
        private void closeIssue() {
                int index = issueListMaster.indexOf(lstIssue.getSelectionModel().getSelectedItem());
                lstIssue.getSelectionModel().getSelectedItem().setStatus("Closed");
                lstIssue.getSelectionModel().getSelectedItem().setAssigned(null);
                updateFile(index);
                prepareListView();
        }
        
        /**
         * If a manager will click on the "Add User" button, this method will render the
         * TextFields blank and will set the ChoiceBox's default choice to "User".
         */
        private void prepareAddUserView() {
                txtNewUser.setText("");
                txtNewPassword.setText("");
                lblError4.setText("");
                cbType.getSelectionModel().select(0);
        }
        
        /**
         * This method will execute if a manager will click on the "Submit" button in the
         * fourth scene, the Add New User scene. It will check whether all of the required
         * fields are completed (i.e., not empty) and that no other user exists with the
         * entered username. If another user exists with that name, an error message will
         * appear. If all the conditions will be met, the appropriate ObservableList
         * will be updated and boolean variable will be returned to allow the scene to change.
         */
        private boolean addNewUser() {
                boolean isUnique = true;
                if (!txtNewUser.getText().equals("") && !txtNewPassword.getText().equals("")) {
                        for (User u : userList) {
                                if (u.getUsername().equals(txtNewUser.getText())) {
                                        isUnique = false;
                                        break;
                                }
                        }

                        if (isUnique) {
                                for (Developer d : developerList) {
                                        if (d.getUsername().equals(txtNewUser.getText())) {
                                                isUnique = false;
                                                break;
                                        }
                                }
                        }

                        if (isUnique) {
                                for (Manager m : managerList) {
                                        if (m.getUsername().equals(txtNewUser.getText())) {
                                                isUnique = false;
                                                break;
                                        }
                                }
                        }

                        if (isUnique) {
                                lstIssue.getSelectionModel().clearSelection();
                                if (cbType.getSelectionModel().getSelectedItem().equals("User")) {
                                        updateUserList();
                                } else if (cbType.getSelectionModel().getSelectedItem().equals("Developer")) {
                                        updateDeveloperList();
                                } else {
                                        updateManagerList();
                                }
                        } else {
                                lblError4.setText("Username already exists");
                        }
                } else {
                        lblError4.setText("Complete both fields");
                        isUnique = false;
                }
                return isUnique;
        }
        
        /**
         * This method will execute if the addNewUser() method will detect a new User added
         * to the system. The user list will be updated with the new addition and then
         * it will update the file from that to make the changes permanent.
         */
        private void updateUserList() {
                userList.add(new User(txtNewUser.getText(), txtNewPassword.getText()));
                File fileUser = new File("users.dat");
                
                try {
                        fileUser.createNewFile();
                        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileUser));
                        
                        for (User u : userList) {
                                output.writeObject(u);
                        }
                        output.close();
                } catch (EOFException EOFe) {
                } catch (Exception e) {
                        System.out.println("Error: " + e);
                }
        }
        
        /**
         * This method will execute if the addNewUser() method will detect a new Developer added
         * to the system. The developer list will be updated with the new addition and then
         * it will update the file from that to make the changes permanent.
         */
        private void updateDeveloperList() {
                developerList.add(new Developer(txtNewUser.getText(), txtNewPassword.getText()));
                File fileDeveloper = new File("developers.dat");
                
                try {
                        fileDeveloper.createNewFile();
                        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileDeveloper));
                        
                        for (Developer d : developerList) {
                                output.writeObject(d);
                        }
                        output.close();
                } catch (EOFException EOFe) {
                } catch (Exception e) {
                        System.out.println("Error: " + e);
                }
        }
        
        /**
         * This method will execute if the addNewUser() method will detect a new Manager added
         * to the system. The manager list will be updated with the new addition and then
         * it will update the file from that to make the changes permanent.
         */
        private void updateManagerList() {
                managerList.add(new Manager(txtNewUser.getText(), txtNewPassword.getText()));
                File fileManager = new File("managers.dat");
                
                try {
                        fileManager.createNewFile();
                        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileManager));
                        
                        for (Manager m : managerList) {
                                output.writeObject(m);
                        }
                        output.close();
                } catch (EOFException EOFe) {
                } catch (Exception e) {
                        System.out.println("Error: " + e);
                }
        }

        /**
         * This method will execute if the user will click on "Cancel" from the "New Issue"
         * screen or "Issue Details" screen. It will make that sure that the next time this
         * screen will appear, the text in the fields will be empty.
         */
        private void prepareCancelDetails() {
                txtSubject.setText("");
                taDetails.setText("");
                lblError3.setText("");
                lstIssue.getSelectionModel().clearSelection();
        }

        /**
         * This method will hide any Button, Label, TextField, TextArea, or ChoiceBox from the
         * scene and will let the other nodes move up to take its place. This will create a
         * consistent look in the scene.
         */
	private void disable(Node node) {
		node.setVisible(false);
		node.setManaged(false);
	}

        /**
         * This method will make any node re-appear in the scene and will make the other nodes
         * move downward to give it room. 
         */
	private void enable(Node node) {
		node.setVisible(true);
		node.setManaged(true);
	}
        
        /**
         * This is an overloaded disable() method, which will take an entire array of nodes and
         * will disable all of them.
         */
        private void disable(Node[] node) {
                for (Node n : node) {
                    n.setVisible(false);
                    n.setManaged(false);
                }
	}

        /**
         * This is an overloaded enable() method, which will take an entire array of nodes and
         * will enable all of them.
         */
	private void enable(Node[] node) {
                for (Node n : node) {
                        n.setVisible(true);
                        n.setManaged(true);
                }
	}
        
        /**
         * This method will ensure that the previously entered username and password in the
         * login screen will be erased when the user will log out of the system.
         */
        private void clickLogOut() {
		lblError.setText("Logged out");
		txtUsername.setText("");
		txtPassword.setText("");
		txtUsername.requestFocus();
	}
}