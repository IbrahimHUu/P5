package Main;



import DAO.*;
import Domeinklasse.Adres;
import Domeinklasse.OVchipkaart;
import Domeinklasse.Product;
import Domeinklasse.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {

    private static Connection connection;


    public static void main(String[] args) throws SQLException {
        getConnection();
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        testReizigerDAO(rdao);

        AdresDAOPsql adao = new AdresDAOPsql(connection);
        testAdresDAO(adao);

        ProductDAO pdao = new ProductDAOPsql(connection);
        testProductDAO(pdao);

        OVchipkaartDAO ovdao = new OVchipkaartDAOPsql(connection);
        testOVchipkaartDAO(ovdao);
    }


    public static void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Ibrahim123";
        connection = DriverManager.getConnection(url);
    }

    public static void closeConnection() throws SQLException {
        connection.close();

    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

         //Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        //SAVE
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //SAVE VOOR testadres
        String gb = "2003-05-03";
        Reiziger r1 = new Reiziger(7, "I", "", "Errahoui", Date.valueOf(gb));
        rdao.save(r1);

        //SAVE VOOR testovchipkaart
        String geb = "2002-01-01";
        Reiziger r2 = new Reiziger(8, "J", "", "Boom", Date.valueOf(gb));
        rdao.save(r2);

       //DELETE
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //UPDATE
        //Voorletters moeten hetzelfde zijn bij deze Method!
        System.out.print("[Test] ");
        rdao.findById(3);
        System.out.println("Is na ReizigerDAO.update(), geupdate naar");
        String gbdatumLubben = "2001-01-01";
        Reiziger Lubben = new Reiziger(3, "H", "Van", "Persie", Date.valueOf(gbdatumLubben));
        rdao.update(Lubben);

       //FINDBYID
        System.out.println("\n[TEST]");
        rdao.findById(1);

        //FINDBYGEBOORTEDATUM
        System.out.println("\n[TEST]");
        String geboortedt = "2002-10-22";
        rdao.findByGbdatum(geboortedt);
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        //FINDALL METHOD/OPHALEN ALLE REIZIGERS
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        //SAVE
        Adres adres1 = new Adres(8, "3554BV", "82", "C.Roobolstraat", "Utrecht", 7);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save()");
        adao.save(adres1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");


        //DELETE
        //Voorletters moeten hetzelfde zijn bij deze Method!
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(adres1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        //UPDATE
        //AdresId moeten hetzelfde zijn bij deze Method!
        System.out.print("[Test] ");
        System.out.println("Het ingevulde adres is na AdresDAO.update(), geupdate naar");
        Adres Visscherplein = new Adres(1, "3511LX", "36", "Visscherplein", "Utrecht", 1);
        adao.update(Visscherplein);

        //FIND ADRES BY REIZIGER
        System.out.println("\n[TEST]");
        String gd = "2002-09-17";
        Reiziger Rijn1 = new Reiziger(1, "G", "van", "Rijn", java.sql.Date.valueOf(gd));
        adao.findByReiziger(Rijn1);
    }

    private static void testOVchipkaartDAO(OVchipkaartDAO ovdao) throws SQLException {
        System.out.println("\n---------- Test OVchipkaartDAO -------------");

        List<OVchipkaart> ovchipkaarten = ovdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende ovchipkaarten:");
        for (OVchipkaart ovck : ovchipkaarten) {
            System.out.println(ovck);
        }
        System.out.println();

        //SAVE
        String geldigheid = "2023-01-31";
        OVchipkaart kaart1 = new OVchipkaart(18000, java.sql.Date.valueOf(geldigheid), 2, 100.00, 8);
        Product product1 = new Product(7, "Utrecht Travel Ticket", "Reis Oke met het OV", 16.0);
        Product product2 = new Product(9, "Amsterdam Travel Ticket", "Reis Oke met het OV", 17.0);
        kaart1.voegProductToe(product1);
        kaart1.voegProductToe(product2);
        System.out.print("[Test] Eerst " + ovchipkaarten.size() + " ovchipkaarten, na OVchipkaartDAO.save()");
        ovdao.save(kaart1);
        ovchipkaarten = ovdao.findAll();
        System.out.println(ovchipkaarten.size() + " ovchipkaarten\n");


        //DELETE
        System.out.print("[Test] Eerst " + ovchipkaarten.size() + " ovchipkaarten, na OVchipkaartDAO.delete() ");
        kaart1.verwijderProduct(product1);
        kaart1.verwijderProduct(product2);
        ovdao.delete(kaart1);
        ovchipkaarten = ovdao.findAll();
        System.out.println(ovchipkaarten.size() + " ovchipkaarten\n");

        //UPDATE
        String geldigheidUpdate = "2023-03-03";
        System.out.print("[Test] ");
        System.out.println("Ge-update!");
        OVchipkaart kaart1Update = new OVchipkaart(18000, java.sql.Date.valueOf(geldigheidUpdate), 2, 100.00, 8);
        ovdao.update(kaart1Update);


        //FINDBYREIZGER
            System.out.println("\n[TEST]");
            String gd = "2002-12-03";
            Reiziger Piccardio = new Reiziger(5, "G", "van", "Piccardio", java.sql.Date.valueOf(gd));
            ovdao.findByReiziger(Piccardio);
    }




    private static void testProductDAO(ProductDAO pdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        System.out.println("[Test] ProductDAO.findAll() geeft de volgende Producten:");
        List<Product> producten = pdao.findAll();
        for (Product product : producten) {
            System.out.println(product);
        }

        Product product2 = new Product(9, "Amsterdam Travel Ticket", "Reis Oke met het OV", 17.0);
        pdao.save(product2);

        //(ZIE kaart1--product2)
        Product pr1 = new Product(8, "Utrecht Train Ticket", "Reis Oke met het OV", 18.0);


        //SAVE
        String geldigheid = "2017-12-31";
        OVchipkaart ovchipkaart = new OVchipkaart(18326, java.sql.Date.valueOf(geldigheid), 2, 0.00, 5);
        pr1.voegOvchipkaartToe(ovchipkaart);

        System.out.print("\n[Test] Eerst " + producten.size() + " producten, na productenDAO.save()");
        pdao.save(pr1);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");

        //DELETE
        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductenDAO.delete() ");
        pr1.verwijderOvchipkaart(ovchipkaart);
        pdao.delete(pr1);
        System.out.println(producten.size() + " producten\n");

        //UPDATE
        String geldigheidUpdate = "2023-03-03";
        System.out.print("[Test] ");
        System.out.println("Ge-update!");
        Product pr = new Product(5, "Railrunner", "Voordelig reizen voor kinderen.", 2.60);
        pdao.update(pr);


        //FIND BY OVCHIPKAART
        System.out.println("\n[TEST]");
        String gd = "2023-03-03";
        OVchipkaart ovchipkaart1 = new OVchipkaart(18000, java.sql.Date.valueOf(gd), 2, 100.00, 8);
        pdao.findByOVchipkaart(ovchipkaart1);



    }


}


