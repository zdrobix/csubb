package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import service.Context;
import service.Service;

public class Controller {
    private Service ServiceA;
    private Context ContextA;

     @FXML
    private VBox loginVbox;
    @FXML
    private VBox signupVbox;
    @FXML
    private VBox userInfoVbox;
    @FXML
    private Label labelLogIn, labelSignUp;

    @FXML
    private TextField usernameTextFieldLogIn;

    @FXML
    private PasswordField passwordTextFieldLogIn;

    @FXML
    private TextField usernameTextFieldSignUp;

    @FXML
    private TextField firstNameTextFieldSignUp;

    @FXML
    private TextField lastNameTextFieldSignUp;

    @FXML
    private PasswordField passwordTextFieldSignUp;

    @FXML
    private TextField firstNameTextUserInfo;
    @FXML
    private TextField lastNameTextUserInfo;

    public void setService(Service service) {
        this.ServiceA = service;
    }

    public void handleLogin(ActionEvent actionEvent) {
       
        usernameTextFieldLogIn.clear();
        passwordTextFieldLogIn.clear();
    }

    public void showLoginVbox(MouseEvent mouseEvent) {
        labelLogIn.setVisible(true);
        labelSignUp.setVisible(true);
        signupVbox.setVisible(false);
        loginVbox.setVisible(true);
        labelLogIn.setUnderline(true);
        labelSignUp.setUnderline(false);
    }

    public void showSignupVbox(MouseEvent mouseEvent) {
        signupVbox.setVisible(true);
        loginVbox.setVisible(false);
        labelLogIn.setUnderline(false);
        labelSignUp.setUnderline(true);
    }

}
