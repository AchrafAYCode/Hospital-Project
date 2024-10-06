package com.bib.hospitalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class AcceuilController {

    @FXML
    private Button BtnPatient;

    @FXML
    private Button BtnMedicament;

    @FXML
    private void handlePatButtonAction() {
        loadView("/com/bib/hospitalproject/gestionPatient.fxml");
    }

    @FXML
    private void handleMedButtonAction() {
        loadView("/com/bib/hospitalproject/gestionMedicament.fxml");
    }

    @FXML
    private void handlePatMedButtonAction() {
        loadView("/com/bib/hospitalproject/PatientMedicament.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            Parent newView = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) BtnPatient.getScene().getWindow();
            stage.setScene(new Scene(newView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
