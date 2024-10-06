package com.bib.hospitalproject.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PersonnelUtil {
    private static String dernierTitreErreur = "";
    private static String dernierMessageErreur = "";

    private static String JDBC_Driver = "com.mysql.cj.jdbc.Driver";

    private static String DB_Url = "jdbc:mysql://localhost:3306/hospitalproject?user=root&password=";

    public static Connection  cnx =null ;

    public static boolean connectDB()
    {
        try{
            cnx= DriverManager.getConnection(DB_Url, "root" , "");
            System.out.println("succes de la connexion");
            return true;
        } catch(Exception e){
            System.out.println("echec de la connexion");
            return false ;
        }
    }

    public static ObservableList<Personnel> getPersonnels() {
        ObservableList<Personnel> liste = FXCollections.observableArrayList();
        String req = "SELECT * FROM personnel ";

        try (
                Statement stmt = cnx.createStatement();
                ResultSet rslt = stmt.executeQuery(req)) {
            while (rslt.next()) {
                String cin = rslt.getString("cin");
                String nom = rslt.getString("nom");
                String prenom = rslt.getString("prenom");
                String login = rslt.getString("login");
                String password = rslt.getString("password");
                String fonction = rslt.getString("fonction");
                Personnel personnel = new Personnel(cin, nom, prenom, login, password, fonction);
                liste.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static Personnel getPersonnelByCin(String cin) {
        Personnel p = null;
        String query = "SELECT * from personnel where cin=? ";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, cin);
            try (ResultSet rslt = preparedStatement.executeQuery()) {
                if (rslt.next()) {
                    String nom = rslt.getString("nom");
                    String prenom = rslt.getString("prenom");
                    String login = rslt.getString("login");
                    String password = rslt.getString("password");
                    String fonction = rslt.getString("fonction");
                    p = new Personnel(cin, nom, prenom, login, password, fonction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static Personnel getPersonnelByLogin(String login) {
        Personnel p = null;
        String query = "SELECT * from personnel where login = ? ";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            try (ResultSet rslt = preparedStatement.executeQuery()) {
                if (rslt.next()) {
                    String cin = rslt.getString("cin");
                    String nom = rslt.getString("nom");
                    String prenom = rslt.getString("prenom");
                    String password = rslt.getString("password");
                    String fonction = rslt.getString("fonction");
                    p = new Personnel(cin, nom, prenom, login, password, fonction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


    public static boolean ajouterPersonnel(String cin, String nom, String prenom, String login, String password ,String fonction) {
        String query = "INSERT INTO personnel (cin, nom, prenom, login, password , fonction) VALUES (?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, cin);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, fonction);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierPersonnel(String cin, String nom, String prenom, String login, String password ,String fonction) {
        String query = "UPDATE personnel SET nom = ?, prenom = ?, login = ?, password = ? , fonction= ?  WHERE cin = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, fonction);
            preparedStatement.setString(6, cin);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Personnel> rechercherPersonnels(String nom) {
        ObservableList<Personnel> liste = FXCollections.observableArrayList();
        String query = "SELECT cin, nom, prenom, fonction FROM personnel WHERE nom LIKE ? ORDER BY cin";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + nom + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String cin = resultSet.getString("cin");
                    String nomPersonnel = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String fonction = resultSet.getString("fonction");

                    Personnel personnel = new Personnel(cin, nomPersonnel, prenom, fonction);
                    liste.add(personnel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }


    public static boolean supprimerToutPersonnels() {
        String query = "DELETE FROM personnel";
        try {
            Statement statement = cnx.createStatement();
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerPersonnel(String cin) {
        String query = "DELETE FROM personnel WHERE cin = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, cin);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}






