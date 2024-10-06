package com.bib.hospitalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.bib.hospitalproject.data.PatientMedicament;
import com.bib.hospitalproject.data.PatientMedicamentUtil;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PatientMedicamentController {

    @FXML
    private TableView<PatientMedicament> tablemedPat;

    @FXML
    private TableColumn<PatientMedicament, Integer> colonneRefmed;

    @FXML
    private TableColumn<PatientMedicament, Integer> colonneCinpat;

    @FXML
    private ComboBox<Integer> PatientComboBox;

    @FXML
    private ComboBox<Integer> medicamentComboBox;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button navigateButton ;

    @FXML
    private Button closeButton ;





    private ObservableList<PatientMedicament> patientMedicamentList = FXCollections.observableArrayList();

    private ObservableList<Integer> patientList = FXCollections.observableArrayList();

    private ObservableList<Integer> medicamentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colonneRefmed.setCellValueFactory(new PropertyValueFactory<>("refMed"));
        colonneCinpat.setCellValueFactory(new PropertyValueFactory<>("cinPat"));

        buttonAdd.setOnAction(event -> handleAdd());


        loadPatientMedicamentData();
        loadPatients();
        loadMedicaments();
    }

    private void loadPatientMedicamentData() {
        patientMedicamentList = FXCollections.observableArrayList(PatientMedicamentUtil.getAllPatientMedicaments());
        tablemedPat.setItems(patientMedicamentList);
    }

    private void loadPatients() {
        List<Integer> patients = PatientMedicamentUtil.getAllPatients();
        patientList.addAll(patients);
        PatientComboBox.setItems(patientList);
    }

    private void loadMedicaments() {
        List<Integer> medicaments = PatientMedicamentUtil.getAllMedicaments();
        medicamentList.addAll(medicaments);
        medicamentComboBox.setItems(medicamentList);
    }

    @FXML
    private void handleAdd() {
        Integer patientCin = PatientComboBox.getValue();
        Integer medicamentRef = medicamentComboBox.getValue();

        if (patientCin != null && medicamentRef != null) {
            PatientMedicamentUtil.insertPatientMedicament(patientCin, medicamentRef);
            loadPatientMedicamentData();
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un patient et un médicament.");
        }
    }



    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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