package app.workers;

import app.beans.Personne;
import app.exceptions.MyDBException;
import app.helpers.DateTimeLib;
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
        final String url_remote = "jdbc:mysql://172.23.85.187:3306/" + nomDB;
        final String user = "223";
        final String password = "emf123";

        System.out.println("url:" + url_remote);
        try {
            dbConnexion = DriverManager.getConnection(url_remote, user, password);
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
                if (!rs.wasNull()) {
                    salaire = rs.getDouble("Salaire");
                }
                Personne per = new Personne(rs.getInt("PK_PERS"), rs.getString("Nom"), rs.getString("Prenom"), rs.getDate("Date_naissance"), rs.getInt("No_rue"), rs.getString("Rue"), rs.getInt("NPA"), rs.getString("Ville"), rs.getBoolean("Actif"), salaire, rs.getDate("date_modif"));
                listePersonnes.add(per);

            }
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }

        return listePersonnes;
    }

 

    @Override
    public Personne lire(int nb)throws MyDBException {
        Personne p2 = new Personne();
        if (nb < listePersonnes.size()) {
            p2 = listePersonnes.get(nb);
        }
        return p2;
    }

    @Override
    public void creer(Personne p)throws MyDBException {
        if (p != null) {
            String prep = "INSERT INTO t_personne set Nom=?, Prenom=?, Date_naissance=?, No_rue=?, "
                    + "Rue=?, NPA=?, Salaire=?, Ville=?, Actif=?, date_modif=?";

             try (PreparedStatement ps = dbConnexion.prepareStatement(prep, Statement.RETURN_GENERATED_KEYS)){
                
                ps.setString(1, p.getNom());
                ps.setString(2, p.getPrenom());
//                ps.setDate(3, DateTimeLib.stringToSqldate(p.getDateNaissance().toString()));
                ps.setDate(3, new java.sql.Date(p.getDateNaissance().getTime()));
                ps.setInt(4, p.getNoRue());
                ps.setString(5, p.getRue());
                ps.setInt(6, p.getNpa());
                ps.setDouble(7, p.getSalaire());
                ps.setString(8, p.getLocalite());
                ps.setBoolean(9, p.isActif());
//                ps.setDate(10, DateTimeLib.stringToSqldate(DateTimeLib.getNow().toString()));
                ps.setDate(10, new java.sql.Date(p.getDateModif().getTime()));
                
                int nb =ps.executeUpdate();

            } catch (SQLException ex) {
                 throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
            }
        }
    }

    @Override
    public void modifier(Personne p) throws MyDBException {
        if (p != null) {
            String prep = "update 223_personne_1table.t_personne set Prenom=?, Nom=?, Date_naissance=?, No_rue=?, Rue=?, NPA=?, Ville=?, Actif=?, Salaire=?, date_modif=? where PK_PERS=?";
            try ( PreparedStatement ps = dbConnexion.prepareStatement(prep )) {

                ps.setString(1, p.getPrenom());
                ps.setString(2, p.getNom());
//                ps.setDate(3, DateTimeLib.stringToSqldate(p.getDateNaissance().toString()));
                ps.setDate(3, new java.sql.Date(p.getDateNaissance().getTime()));
                ps.setInt(4, p.getNoRue());
                ps.setString(5, p.getRue());
                ps.setInt(6, p.getNpa());
                ps.setString(7, p.getLocalite());
                ps.setBoolean(8, p.isActif());
                ps.setDouble(9, p.getSalaire());
//                ps.setDate(10, DateTimeLib.stringToSqldate(DateTimeLib.getNow().toString()));
                ps.setDate(10, new java.sql.Date(p.getDateModif().getTime()));
                ps.setInt(11, p.getPkPers());
                ps.executeUpdate();
               
            } catch (SQLException ex) {
                 throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
            }

        }
    }

    @Override
    public void effacer(Personne p) throws MyDBException{
        String prep = "delete from t_personne where pk_pers=?";
        try (PreparedStatement ps = dbConnexion.prepareStatement(prep)) {
            ps.setInt(1, p.getPkPers());
            int nb = ps.executeUpdate();
            if(nb != 1){
                 throw new MyDBException(SystemLib.getFullMethodName(), "Erreur de suppression");
            }



       } catch (Exception e) {
            throw new MyDBException(SystemLib.getFullMethodName(), e.getMessage());
        }

    }

}
