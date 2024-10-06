package com.bib.hospitalproject.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PatientUtil {

    private static Connection cnx;

    // Initialize the database connection
    static {
        try {
            String DB_Url = "jdbc:mysql://localhost:3306/hospitalproject?user=root&password=";
            cnx = DriverManager.getConnection(DB_Url, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Patient> getPatients() {
        ObservableList<Patient> liste = FXCollections.observableArrayList();
        String query = "SELECT * FROM patient";

        try (Statement stmt = cnx.createStatement();
             ResultSet rslt = stmt.executeQuery(query)) {

            while (rslt.next()) {
                String cin = rslt.getString("cin");
                String nom = rslt.getString("nom");
                String prenom = rslt.getString("prenom");
                String sexe = rslt.getString("sexe");
                String tel = rslt.getString("tel");
                Patient patient = new Patient(cin, nom, prenom, sexe, tel);
                liste.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static Patient getPatientByCin(String cin) {
        Patient patient = null;
        String query = "SELECT * FROM patient WHERE cin = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, cin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String sexe = resultSet.getString("sexe");
                    String tel = resultSet.getString("tel");
                    patient = new Patient(cin, nom, prenom, sexe, tel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public static boolean ajouterPatient(String cin, String nom, String prenom, String sexe, String tel) {
        String query = "INSERT INTO patient(cin, nom, prenom, sexe, tel) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, cin);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, sexe);
            preparedStatement.setString(5, tel);
            int result = preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierPatient(String cin, String nom, String prenom, String sexe, String tel) {
        String query = "UPDATE patient SET nom = ?, prenom = ?, sexe = ?, tel = ? WHERE cin = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, sexe);
            preparedStatement.setString(4, tel);
            preparedStatement.setString(5, cin);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean supprimerToutPatients() {
        String query = "DELETE FROM patient";
        try {
            Statement statement = cnx.createStatement();
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerPatient(int cin) {
        String query = "DELETE FROM patient WHERE cin = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, cin);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
