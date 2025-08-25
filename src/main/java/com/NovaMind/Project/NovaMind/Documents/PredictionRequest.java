package com.NovaMind.Project.NovaMind.Documents;

public class PredictionRequest {
    private int nbCoursParMois;
    private int heuresParSemaine;
    private String niveau; // beginner, intermediate, advanced

    // Getters et Setters
    public int getNbCoursParMois() {
        return nbCoursParMois;
    }

    public void setNbCoursParMois(int nbCoursParMois) {
        this.nbCoursParMois = nbCoursParMois;
    }

    public int getHeuresParSemaine() {
        return heuresParSemaine;
    }

    public void setHeuresParSemaine(int heuresParSemaine) {
        this.heuresParSemaine = heuresParSemaine;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
