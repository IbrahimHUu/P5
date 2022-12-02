package DAO;

import Domeinklasse.OVchipkaart;
import Domeinklasse.Product;

import java.util.List;

public interface ProductDAO {


    boolean save(Product product);
    boolean update (Product product);
    boolean delete (Product product);
    Product findByOVchipkaart(OVchipkaart oVchipkaart);
    List<Product> findAll();
}
