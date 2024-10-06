package com.bib.hospitalproject;

import com.bib.hospitalproject.data.PersonnelUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.bib.hospitalproject.data.Personnel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginField ;
    @FXML
    private TextField passwordField ;
    @FXML
    private Button btnLogIn ;
    @FXML
    private Button BtnRegister ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PersonnelUtil.connectDB();
        setButtonHandlers();
    }

    private void setButtonHandlers() {
        btnLogIn.setOnAction(this::handleLogin);
        BtnRegister.setOnAction(this::handleRegister);
    }

    @FXML
    private void handleRegister(ActionEvent event ){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage stage = (Stage) BtnRegister.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            showError("Erreur de connexion", "Veuillez remplir tous les champs");
            return;
        }

        Personnel personnel = PersonnelUtil.getPersonnelByLogin(login);

        if (personnel != null && personnel.getPassword().equals(password)) {
            try {
                // Load the welcome-view.fxml
                Parent root = FXMLLoader.load(getClass().getResource("/com/bib/hospitalproject/Acceuil-view.fxml"));

                // Get the current stage
                Stage stage = (Stage) btnLogIn.getScene().getWindow();

                // Create a new scene with the loaded FXML
                Scene scene = new Scene(root);

                // Set the scene to the stage and show it
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Erreur","Failed to load the welcome view.");
            }

        } else {
            showError("Erreur de connexion", "Login ou mot de passe incorrect");
        }
    }





    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
