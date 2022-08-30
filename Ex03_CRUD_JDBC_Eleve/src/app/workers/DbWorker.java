package app.workers;

import app.beans.Personne;
import app.exceptions.MyDBException;
import app.helpers.SystemLib;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbWorker implements DbWorkerItf {

    private Connection dbConnexion;
    private List<Personne> listePersonnes;
    private int index = 0;

    /**
     * Constructeur du worker
     */
    public DbWorker() {
    }

    @Override
    public void connecterBdMySQL(String nomDB) throws MyDBException {
        final String url_local = "jdbc:mysql://localhost:3306/" + nomDB;
        final String url_remote = "jdbc:mysql://LAPEMFB37-21.edu.net.fr.ch:3306/" + nomDB;
        final String user = "root";
        final String password = "emf123";

        System.out.println("url:" + url_local);
        try {
            dbConnexion = DriverManager.getConnection(url_local, user, password);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    @Override
    public void connecterBdHSQLDB(String nomDB) throws MyDBException {
        final String url = "jdbc:hsqldb:file:" + nomDB + ";shutdown=true";
        final String user = "SA";
        final String password = "";
        System.out.println("url:" + url);
        try {
            dbConnexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    @Override
    public void connecterBdAccess(String nomDB) throws MyDBException {
        final String url = "jdbc:ucanaccess://" + nomDB;
        System.out.println("url=" + url);
        try {
            dbConnexion = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    @Override
    public void deconnecter() throws MyDBException {
        try {
            if (dbConnexion != null) {
                dbConnexion.close();
            }
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }
    
    @Override
    public List<Personne> lirePersonnes() throws MyDBException {
        listePersonnes = new ArrayList<Personne>();
        try {
            Statement st = dbConnexion.createStatement();
            ResultSet rs = st.executeQuery("select *  from t_personne");

            while (rs.next()) {
                double salaire = 0.0;
                if(rs.getDouble("Salaire") != null){
                    salaire = rs.getDouble("Salaire");
                }
                Personne per = new Personne(rs.getInt("PK_PERS"), rs.getString("Nom"), rs.getString("Prenom"), rs.getDate("Date_naissance"), rs.getInt("No_rue"), rs.getString("Rue"), rs.getInt("NPA"), rs.getString("Ville"), rs.getBoolean("Actif"), salaire, rs.getDate("date_modif"));
                listePersonnes.add(per);

            }
        } catch (SQLException ex) {

        }

        return listePersonnes;
    }

    @Override
    public Personne precedentPersonne() throws MyDBException {
        Personne p = new Personne();
        if (listePersonnes == null) {
            lirePersonnes();
            p = listePersonnes.get(index);
        }

        if (index > 0) {

            index--;
        }
        p = listePersonnes.get(index);
        return p;

    }

    public Personne suivantPersonne() throws MyDBException {
        if (listePersonnes == null) {
            lirePersonnes();
            listePersonnes.get(index);
        }
        if (index < listePersonnes.size() - 1 && (listePersonnes.get(index) != null)) {
            index++;
        }

        return listePersonnes.get(index);

    }

    @Override
    public Personne lire(int nb) {
        return null;
    }

    @Override
    public void creer(Personne p) {
       

    }

    @Override
    public void modifier(Personne p) {
       
    }

    @Override
    public void effacer(Personne p) {
        
    }

}
