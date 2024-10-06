package com.bib.hospitalproject.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientMedicamentUtil {

    private static Connection cnx;

    static {
        try {
            String DB_Url = "jdbc:mysql://localhost:3306/hospitalproject?user=root&password=";
            cnx = DriverManager.getConnection(DB_Url, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<PatientMedicament> getAllPatientMedicaments() {
        List<PatientMedicament> liste = new ArrayList<>();
        String query = "SELECT * FROM patientmed";

        try (Statement stmt = cnx.createStatement();
             ResultSet rslt = stmt.executeQuery(query)) {

            while (rslt.next()) {
                int cinpat = rslt.getInt("cinPat");
                int refmed = rslt.getInt("refMed");
                PatientMedicament pm = new PatientMedicament(cinpat, refmed);
                liste.add(pm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Integer> getAllPatients() {
        List<Integer> patientList = new ArrayList<>();
        String query = "SELECT cin FROM patient";

        try (Statement stmt = cnx.createStatement();
             ResultSet rslt = stmt.executeQuery(query)) {

            while (rslt.next()) {
                int cin = rslt.getInt("cin");
                patientList.add(cin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }

    public static List<Integer> getAllMedicaments() {
        List<Integer> medicamentList = new ArrayList<>();
        String query = "SELECT ref FROM medicament";

        try (Statement stmt = cnx.createStatement();
             ResultSet rslt = stmt.executeQuery(query)) {

            while (rslt.next()) {
                int ref = rslt.getInt("ref");
                medicamentList.add(ref);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentList;
    }

    public static void insertPatientMedicament(int cinpat, int refmed) {
        String query = "INSERT INTO patientmed(refMed, cinPat) VALUES (?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, refmed);
            pstmt.setInt(2, cinpat);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
