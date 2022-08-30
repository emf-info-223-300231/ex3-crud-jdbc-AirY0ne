package app.workers;

import app.beans.Personne;
import app.exceptions.MyDBException;
import java.util.List;

public interface DbWorkerItf {

  void connecterBdMySQL( String nomDB ) throws MyDBException;
  void connecterBdHSQLDB( String nomDB ) throws MyDBException;
  void connecterBdAccess( String nomDB ) throws MyDBException;
  void deconnecter() throws MyDBException; 
  Personne precedentPersonne() throws MyDBException;
  Personne suivantPersonne() throws MyDBException; 
  List<Personne> lirePersonnes()throws MyDBException;
  void creer(Personne p);
  void effacer(Personne p);
  Personne lire(int nb);
  void modifier(Personne p);

}
