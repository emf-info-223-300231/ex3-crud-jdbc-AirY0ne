/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.workers;

import app.beans.Personne;
import java.util.List;

/**
 *
 * @author kabashie
 */
public class PersonneManager {
    private int index=0;
    private List<Personne> listePersonnes;
    

    public PersonneManager() {
    }
    public Personne courantPersonne(){
        return null;
    }
    
    public Personne debutPersonne(){
        return null;
    }
    
    public Personne finPersonne(){
        return null;
    }
    
    public Personne precedentPersonne(){
        return null;
    }
    public Personne suivantPersonne(){
        return null;
    }

    public void setPersonnes(List<Personne> listePersonnes) {
        this.listePersonnes = listePersonnes;
    }
    
    
    
    
}
