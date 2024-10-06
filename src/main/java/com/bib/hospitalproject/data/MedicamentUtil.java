package com.bib.hospitalproject.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MedicamentUtil {

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

    public static ObservableList<Medicament> getMedicaments() {
        ObservableList<Medicament> liste = FXCollections.observableArrayList();
        String query = "SELECT * FROM medicament";

        try (Statement stmt = cnx.createStatement();
             ResultSet rslt = stmt.executeQuery(query)) {

            while (rslt.next()) {
                int ref = rslt.getInt("ref");
                String libelle = rslt.getString("libelle");
                double prix = rslt.getDouble("prix");
                Medicament medicament = new Medicament(ref, libelle, prix);
                liste.add(medicament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static Medicament getMedicamentByRef(int ref) {
        Medicament medicament = null;
        String query = "SELECT * FROM medicament WHERE ref = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, ref);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String libelle = resultSet.getString("libelle");
                    double prix = resultSet.getDouble("prix");
                    medicament = new Medicament(ref, libelle, prix);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }

    public static boolean ajouterMedicament(String libelle, double prix) {
        String query = "INSERT INTO medicament (libelle, prix) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, libelle);
            preparedStatement.setDouble(2, prix);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierMedicament(int ref, String libelle, double prix) {
        String query = "UPDATE medicament SET libelle = ?, prix = ? WHERE ref = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, libelle);
            preparedStatement.setDouble(2, prix);
            preparedStatement.setInt(3, ref);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Medicament> rechercherMedicaments(String libelleR) {
        ObservableList<Medicament> liste = FXCollections.observableArrayList();
        String query = "SELECT * FROM medicament WHERE libelle LIKE ? ORDER BY ref";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + libelleR + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ref = resultSet.getInt("ref");
                    String libelle = resultSet.getString("libelle");
                    double prix = resultSet.getDouble("prix");
                    Medicament medicament = new Medicament(ref, libelle, prix);
                    liste.add(medicament);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public static boolean supprimerToutMedicaments() {
        String query = "DELETE FROM medicament";
        try {
            Statement statement = cnx.createStatement();
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerMedicament(int ref) {
        String query = "DELETE FROM medicament WHERE ref = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, ref);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}