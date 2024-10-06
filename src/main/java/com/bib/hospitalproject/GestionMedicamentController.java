package com.bib.hospitalproject;

import com.bib.hospitalproject.data.MedicamentUtil;
import com.bib.hospitalproject.data.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.bib.hospitalproject.data.Medicament;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionMedicamentController {

    @FXML
    private TextField iref;

    @FXML
    private TextField ilibelle;

    @FXML
    private TextField iprix;

    @FXML
    private Button navigateButton ;

    @FXML
    private Button closeButton ;

    @FXML
    private TableView<Medicament> medicamentTable;

    @FXML
    private TableColumn<Medicament, Integer> refColumn;

    @FXML
    private TableColumn<Medicament, String> libelleColumn;

    @FXML
    private TableColumn<Medicament, Double> prixColumn;

    private ObservableList<Medicament> medicamentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        refColumn.setCellValueFactory(new PropertyValueFactory<>("ref"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        medicamentTable.setItems(medicamentList);
        loadMedicaments();

        medicamentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMedicamentDetails(newValue)
        );
    }

    @FXML
    private void handleAddMedicament(ActionEvent event) {
        String libelle = ilibelle.getText();
        double prix;

        try {
            prix = Double.parseDouble(iprix.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "Veuillez saisir un prix valide");
            return;
        }

        if (libelle.isEmpty()) {
            showAlert("Erreur de validation", "Veuillez remplir tous les champs");
            return;
        }

        boolean success = MedicamentUtil.ajouterMedicament(libelle, prix);
        if (success) {
            loadMedicaments();
            clearFields();
            showAlert("Succès", "Médicament ajouté avec succès");
        } else {
            showAlert("Erreur", "Erreur lors de l'ajout du médicament");
        }
    }

    @FXML
    private void handleUpdateMedicament(ActionEvent event) {
        int ref;
        try {
            ref = Integer.parseInt(iref.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "Veuillez saisir une référence valide");
            return;
        }

        String libelle = ilibelle.getText();
        double prix;
        try {
            prix = Double.parseDouble(iprix.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "Veuillez saisir un prix valide");
            return;
        }

        if (libelle.isEmpty()) {
            showAlert("Erreur de validation", "Veuillez remplir tous les champs");
            return;
        }

        boolean success = MedicamentUtil.modifierMedicament(ref, libelle, prix);
        if (success) {
            loadMedicaments();
            clearFields();
            showAlert("Succès", "Médicament modifié avec succès");
        } else {
            showAlert("Erreur", "Erreur lors de la modification du médicament");
        }
    }

    @FXML
    private void handleDeleteMedicament(ActionEvent event) {
        int ref;
        try {
            ref = Integer.parseInt(iref.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "Veuillez saisir une référence valide");
            return;
        }

        boolean success = MedicamentUtil.supprimerMedicament(ref);
        if (success) {
            loadMedicaments();
            clearFields();
            showAlert("Succès", "Médicament supprimé avec succès");
        } else {
            showAlert("Erreur", "Erreur lors de la suppression du médicament");
        }
    }

    @FXML
    private void handleSearchMedicament(ActionEvent event) {
        String libelle = ilibelle.getText();

        if (libelle.isEmpty()) {
            showAlert("Erreur de validation", "Veuillez saisir le libellé du médicament à rechercher");
            return;
        }

        ObservableList<Medicament> result = MedicamentUtil.rechercherMedicaments(libelle);
        if (!result.isEmpty()) {
            medicamentList.setAll(result);
        } else {
            showAlert("Information", "Aucun médicament trouvé avec le libellé spécifié");
        }
    }

    private void loadMedicaments() {
        medicamentList.setAll(MedicamentUtil.getMedicaments());
    }

    private void clearFields() {
        iref.clear();
        ilibelle.clear();
        iprix.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showMedicamentDetails(Medicament medicament) {
        if (medicament != null) {
            iref.setText(String.valueOf(medicament.getRef()));
            ilibelle.setText(medicament.getLibelle());
            iprix.setText(String.valueOf(medicament.getPrix()));
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
