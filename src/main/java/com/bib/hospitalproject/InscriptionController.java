package com.bib.hospitalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.bib.hospitalproject.data.PersonnelUtil ;
import javafx.stage.Stage;

import java.io.IOException;

public class InscriptionController {

    @FXML
    private TextField cinins;
    @FXML
    private TextField nomins;
    @FXML
    private TextField prensin;
    @FXML
    private TextField loginins;
    @FXML
    private TextField mdpsin;
    @FXML
    private TextField fonctionsin;
    @FXML
    private Button btnins;
    @FXML
    private Button btncloseins;

    @FXML
    private void initialize() {
        btnins.setOnAction(event -> handleInscription());
        btncloseins.setOnAction(event -> handleClose());
    }

    @FXML
    private void handleInscription() {
        String cin = cinins.getText();
        String nom = nomins.getText();
        String prenom = prensin.getText();
        String login = loginins.getText();
        String password = mdpsin.getText();
        String fonction = fonctionsin.getText();

        if (cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty() || fonction.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please enter all the fields");
            return;
        }

        boolean success = PersonnelUtil.ajouterPersonnel(cin, nom, prenom, login, password, fonction);

        if (success) {
            showAlert(AlertType.INFORMATION, "Registration Successful!", "New personnel registered successfully.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Acceuil-view.fxml"));
                Scene newScene = new Scene(loader.load());

                Stage stage = (Stage) btnins.getScene().getWindow();
                stage.setScene(newScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            showAlert(AlertType.ERROR, "Registration Failed", "Unable to register new personnel.");
        }
    }

    private void handleClose() {
        btncloseins.getScene().getWindow().hide();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        cinins.clear();
        nomins.clear();
        prensin.clear();
        loginins.clear();
        mdpsin.clear();
        fonctionsin.clear();
    }
}