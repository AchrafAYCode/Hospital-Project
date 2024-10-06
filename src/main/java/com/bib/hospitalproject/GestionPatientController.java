package com.bib.hospitalproject;

import com.bib.hospitalproject.data.PatientUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.bib.hospitalproject.data.Patient;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GestionPatientController {

    @FXML
    private TextField icin;

    @FXML
    private TextField inom;

    @FXML
    private TextField iprenom;
    @FXML
    private ToggleGroup genderGroup ;
    @FXML
    private TextField itel;
    @FXML
    private RadioButton masculinRadioButton ;
    @FXML
    private RadioButton femininRadioButton ;
    @FXML
    private Button navigateButton ;

    @FXML
    private Button closeButton ;

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> cinColumn;

    @FXML
    private TableColumn<Patient, String> nomColumn;

    @FXML
    private TableColumn<Patient, String> prenomColumn;

    @FXML
    private TableColumn<Patient, String> telColumn;

    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        patientTable.setItems(patientList);
        loadPatients();
        patientTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPatientDetails(newValue)
        );

    }

    @FXML
    private void handleAddPatient(ActionEvent event) {
        String cin = icin.getText();
        String nom = inom.getText();
        String prenom = iprenom.getText();
        String sexe = genderGroup.getToggles().toString();
        String tel = itel.getText();

        if (cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || tel.isEmpty()) {
            showAlert("Erreur de validation", "Veuillez remplir tous les champs");
            return;
        }

        if (cin.length() < 8) {
            showAlert("Erreur de validation", "Le CIN doit contenir  8 caractères");
            return;
        }

        if (tel.length() != 8) {
            showAlert("Erreur de validation", "Le numéro de téléphone doit contenir exactement 8 caractères");
            return;
        }

        try {
            int cinInt = Integer.parseInt(cin);
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "Le CIN doit être un nombre");
            return;
        }

        try {
            int telInt = Integer.parseInt(tel);
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "Le numéro de téléphone doit être un nombre");
            return;
        }

        boolean success = PatientUtil.ajouterPatient(cin, nom, prenom, sexe, tel);
        if (success) {
            patientList.add(new Patient(cin, nom, prenom, sexe, tel));
            clearFields();
            showAlert("Succès", "Patient ajouté avec succès");
        } else {
            showAlert("Erreur", "Erreur lors de l'ajout du patient");
        }
    }

    @FXML
    private void handleUpdatePatient(ActionEvent event) {
        String cin = icin.getText();
        String nom = inom.getText();
        String prenom = iprenom.getText();
        String sexe = genderGroup.getToggles().toString();
        String tel = itel.getText();

        if (cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || tel.isEmpty()) {
            showAlert("Erreur de validation", "Veuillez remplir tous les champs");
            return;
        }

        boolean success = PatientUtil.modifierPatient(cin, nom, prenom, sexe, tel);
        if (success) {
            loadPatients();
            clearFields();
            showAlert("Succès", "Patient modifié avec succès");
        } else {
            showAlert("Erreur", "Erreur lors de la modification du patient");
        }
    }

    @FXML
    private void handleDeletePatient(ActionEvent event) {
        String cin = icin.getText();

        if (cin.isEmpty()) {
            showAlert("Erreur de validation", "Veuillez saisir le CIN du patient à supprimer");
            return;
        }

        boolean success = PatientUtil.supprimerPatient(Integer.parseInt(cin));
        if (success) {
            loadPatients();
            clearFields();
            showAlert("Succès", "Patient supprimé avec succès");
        } else {
            showAlert("Erreur", "Erreur lors de la suppression du patient");
        }
    }



    private void loadPatients() {
        patientList.setAll(PatientUtil.getPatients());
    }

    private void clearFields() {
        icin.clear();
        inom.clear();
        iprenom.clear();
        genderGroup.selectToggle(null);
        itel.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showPatientDetails(Patient patient) {
        if (patient != null) {
            icin.setText(String.valueOf(patient.getCin()));
            inom.setText(patient.getNom());
            iprenom.setText(patient.getPrenom());
            itel.setText(patient.getTel());
            if ("Masculin".equalsIgnoreCase(patient.getSexe())) {
                masculinRadioButton.setSelected(true);
            } else {
                femininRadioButton.setSelected(true);
            }
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleNavigateButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Acceuil-view.fxml"));
            Scene newScene = new Scene(loader.load());

            Stage stage = (Stage) navigateButton.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



}
