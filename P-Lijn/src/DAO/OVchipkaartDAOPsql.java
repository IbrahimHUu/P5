package DAO;

import Domeinklasse.OVchipkaart;
import Domeinklasse.Product;
import Domeinklasse.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OVchipkaartDAOPsql implements OVchipkaartDAO {

    private static Connection conn;


    public OVchipkaartDAOPsql(Connection conn) {
        this.conn = conn;


    }


    @Override
    public boolean save(OVchipkaart ovchipkaart) {
        try {
            String sql = "INSERT INTO ov_chipkaart (kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, ovchipkaart.getKaartNummer());
            pst.setDate(2, ovchipkaart.getGeldigTot());
            pst.setInt(3, ovchipkaart.getKlasse());
            pst.setDouble(4, ovchipkaart.getSaldo());
            pst.setInt(5, ovchipkaart.getReizigerId());
            pst.executeUpdate();


            for (Product product : ovchipkaart.getProducten()) {
                String sqlOvchipkaartProduct = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?);";
                PreparedStatement pst2 = conn.prepareStatement(sqlOvchipkaartProduct);

                pst2.setInt(1, ovchipkaart.getKaartNummer());
                pst2.setInt(2, product.getProdcutNummer());
                pst2.executeUpdate();
                pst2.close();
            }
            pst.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("(OV is al toegevoegd)");
            return false;

        }

    }

    @Override
    public boolean update(OVchipkaart ovchipkaart) {
        try {
            String sql = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ? WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, ovchipkaart.getKaartNummer());
            pst.setDate(2, ovchipkaart.getGeldigTot());
            pst.setInt(3, ovchipkaart.getKlasse());
            pst.setDouble(4, ovchipkaart.getSaldo());
            pst.setInt(5, ovchipkaart.getReizigerId());
            pst.executeUpdate();


            for (Product product : ovchipkaart.getProducten()) {
                String sqlOvchipkaartProduct = "UPDATE ov_chipkaart_product SET product_nummer = ?, kaart_nummer = ? WHERE kaart_nummer = ?";
                PreparedStatement pst2 = conn.prepareStatement(sqlOvchipkaartProduct);
                pst2.setInt(1, product.getProdcutNummer());
                pst2.setInt(2, ovchipkaart.getKaartNummer());
                pst2.setInt(3, ovchipkaart.getKaartNummer());
                pst2.executeUpdate();
                pst2.close();
            }

            pst.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(OVchipkaart ovchipkaart) {
        try {
                String sqlOvchipkaartProduct = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? ";
                PreparedStatement pst2 = conn.prepareStatement(sqlOvchipkaartProduct);
                pst2.setInt(1, ovchipkaart.getKaartNummer());

                pst2.executeUpdate();
                pst2.close();


            String sql = "DELETE FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, ovchipkaart.getReizigerId());
            pst.executeUpdate();
            pst.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVchipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String sql = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, reiziger.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                int kaartNummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int Klasse = rs.getInt("klasse");
                Double Saldo = rs.getDouble("saldo");
                int reizigerId = rs.getInt("reiziger_id");

                System.out.println("\n" + "OVChipkaart: " + "Kaartnummer:"+ kaartNummer + ", Geldig tot:" + " " + geldigTot + ", Klasse:" + " " + Klasse  + ", Saldo:" + " " + Saldo
                        + ", reiziger_id:" + " " + reizigerId);
            }
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }

    @Override
    public List<OVchipkaart> findAll() {
        try {
            String sql = "SELECT * FROM ov_chipkaart";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            List<OVchipkaart> ovchipkaarten = new ArrayList<OVchipkaart>();


            while (rs.next()) {
                int kaartNummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int Klasse = rs.getInt("klasse");
                Double Saldo = rs.getDouble("saldo");
                int reizigerId = rs.getInt("reiziger_id");
                OVchipkaart ovchipkaart = new OVchipkaart(kaartNummer, geldigTot, Klasse, Saldo, reizigerId);
                ovchipkaarten.add(ovchipkaart);
            }
            pst.close();
            return ovchipkaarten;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
