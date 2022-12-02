package Domeinklasse;

import javax.xml.crypto.Data;

import java.util.ArrayList;

public class Product {
    private int prodcutNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    private ArrayList<OVchipkaart> ovchipkaarten;


    public Product (int prnr, String nm, String besch, double prs) {
        prodcutNummer = prnr;
        naam = nm;
        beschrijving = besch;
        prijs = prs ;
        ovchipkaarten = new ArrayList<OVchipkaart>();
    }

    public int getProdcutNummer() {
        return prodcutNummer;
    }

    public void setProdcutNummer(int prodcutNummer) {
        this.prodcutNummer = prodcutNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<OVchipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

    public void voegOvchipkaartToe(OVchipkaart oVchipkaart) {
        ovchipkaarten.add(oVchipkaart);
    }

    public void verwijderOvchipkaart(OVchipkaart oVchipkaart) {
        ovchipkaarten.remove(oVchipkaart);
    }

    public String toString () {

        String s = "Productnummer:"+ prodcutNummer + ", Naam:" + " " + naam + ", Beschrijving:" + " " + beschrijving  + " Prijs:" + " " + prijs;
        return s;
    }
}

