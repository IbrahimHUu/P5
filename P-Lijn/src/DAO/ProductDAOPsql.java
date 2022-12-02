package DAO;

import Domeinklasse.OVchipkaart;
import Domeinklasse.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private static Connection conn;


    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(Product product) {
        try {
            String sql = "INSERT INTO product (product_nummer,naam,beschrijving,prijs) VALUES (?, ?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, product.getProdcutNummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setDouble(4, product.getPrijs());
            pst.executeUpdate();

            for (OVchipkaart Ovchipkaart : product.getOvchipkaarten()) {
                String sqlOvchipkaartProduct = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?);";
                PreparedStatement pst2 = conn.prepareStatement(sqlOvchipkaartProduct);
                pst2.setInt(1, Ovchipkaart.getKaartNummer());
                pst2.setInt(2, product.getProdcutNummer());
                pst2.executeUpdate();
                pst2.close();
            }

                pst.executeUpdate();
                pst.close();
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("(Product is al toegevoegd)");
            return false;
        }

    }

    @Override
    public boolean update(Product product) {

        try {
            String sql = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ?,  product_nummer = ? WHERE product_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, product.getNaam());
            pst.setString(2, product.getBeschrijving());
            pst.setDouble(3, product.getPrijs());
            pst.setInt(4, product.getProdcutNummer());
            pst.setInt(5, product.getProdcutNummer());
            pst.executeUpdate();


                String sqlOvchipkaartProduct = "UPDATE ov_chipkaart_product SET product_nummer = ? WHERE product_nummer = ?";
                PreparedStatement pst2 = conn.prepareStatement(sqlOvchipkaartProduct);
                pst2.setInt(1, product.getProdcutNummer());
                pst2.setInt(2, product.getProdcutNummer());
                pst2.executeUpdate();
                pst2.close();


            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(Product product) {
        try {

            String sqlOvchipkaartProduct = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ? ";
            PreparedStatement pst2 = conn.prepareStatement(sqlOvchipkaartProduct);
            pst2.setInt(1, product.getProdcutNummer());
            pst2.executeUpdate();

            String sql = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, product.getProdcutNummer());
            pst.executeUpdate();
            pst.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product findByOVchipkaart(OVchipkaart ovchipkaart) {
        try {
            String sql = "SELECT * FROM product INNER JOIN ov_chipkaart_product ON ov_chipkaart_product.product_nummer = product.product_nummer " +
                    "WHERE kaart_nummer = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, ovchipkaart.getKaartNummer());
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                int productNummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                System.out.println("\n" + "Product: " + "Productnummer:"+ productNummer + ", Naam:" + " " + naam + ", Beschrijving:" + " " + beschrijving  + ", Prijs:" + " " + prijs);
            }
            pst.close();


        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }


    @Override
    public List<Product> findAll() {
        try {
            String sql = "SELECT * FROM product";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            List<Product> producten = new ArrayList<Product>();


            while (rs.next()) {
                int productNummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                Product product = new Product(productNummer, naam, beschrijving, prijs);
                producten.add(product);
            }
            pst.close();
            return producten;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
