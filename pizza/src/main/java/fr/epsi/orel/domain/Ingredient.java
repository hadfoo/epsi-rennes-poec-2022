package fr.epsi.orel.domain;

public class Ingredient {

    private int id;
    private String type;
    private String label;
    private int nbCalories;
    private double prix;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNbCalories() {
        return nbCalories;
    }

    public void setNbCalories(int nbCalories) {
        this.nbCalories = nbCalories;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
