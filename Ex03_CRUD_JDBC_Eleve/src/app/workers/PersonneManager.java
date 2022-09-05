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

    private int index = 0;
    private List<Personne> listePersonnes;

    public PersonneManager() {
    }

    public Personne courantPersonne() {
        return listePersonnes.get(index);
    }

    public Personne debutPersonne() {
        this.index = 0;
        return listePersonnes.get(index);
    }

    public Personne finPersonne() {
        this.index = listePersonnes.size() - 1;
        return listePersonnes.get(index);
    }

    public Personne precedentPersonne() {
        if (index > 0) {
            this.index--;
        }

        return listePersonnes.get(index);
    }

    public Personne suivantPersonne() {
        if (index < listePersonnes.size() - 1) {
            this.index++;
        }

        return listePersonnes.get(index);
    }

    public Personne setPersonnes(List<Personne> listePersonnes) {

        this.listePersonnes = listePersonnes;

        return listePersonnes.get(index);
    }

}
