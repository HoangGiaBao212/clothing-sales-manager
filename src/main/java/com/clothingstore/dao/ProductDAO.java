package com.clothingstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.interfaces.IDAO;
import com.clothingstore.models.ProductModel;

public class ProductDAO implements IDAO<ProductModel> {
  private static ProductDAO instance;

  public static ProductDAO getInstance() {
    if (instance == null) {
      instance = new ProductDAO();
    }
    return instance;
  }

  private ProductModel createProductModelFromResultSet(ResultSet rs) throws SQLException {
    return new ProductModel(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getInt("category_id"),
        rs.getString("image"),
        rs.getInt("gender"),
        rs.getDouble("price"),
        rs.getInt("status"));
  }

  @Override
  public ArrayList<ProductModel> readDatabase() {
    ArrayList<ProductModel> productList = new ArrayList<>();
    try (
        ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM products");) {
      while (rs.next()) {
        ProductModel productModel = createProductModelFromResultSet(rs);
        productList.add(productModel);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return productList;
  }

  @Override
  public int insert(ProductModel product) {
    String insertSql = "INSERT INTO products(name, category_id, image, gender, price, status) VALUES(?, ?, ?, ?, ?, ?)";
    Object[] args = {
        product.getName(),
        product.getCategoryId(),
        product.getImage(),
        product.getGender(),
        product.getPrice(),
        product.getStatus()
    };
    try {
      return DatabaseConnection.executeUpdate(insertSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int update(ProductModel product) {
    String updateSql = "UPDATE products SET name = ?, category_id = ?, image = ?, gender = ?, price = ?, status = ? WHERE id = ?";
    Object[] args = {
        product.getName(),
        product.getCategoryId(),
        product.getImage(),
        product.getGender(),
        product.getPrice(),
        product.getStatus(),
        product.getId(),
    };
    try {
      return DatabaseConnection.executeUpdate(updateSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int delete(int id) {
    String deleteSql = "DELETE FROM products WHERE id = ?";
    Object[] args = { id };
    try {
      return DatabaseConnection.executeUpdate(deleteSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public List<ProductModel> search(String condition, String[] columnNames) {
    if (condition == null || condition.trim().isEmpty()) {
      throw new IllegalArgumentException("Search condition cannot be empty or null");
    }

    String query;
    if (columnNames == null || columnNames.length == 0) {
      query = "SELECT * FROM products WHERE CONCAT(id, name, category_id, image, gender, price, status) LIKE ?";
    } else if (columnNames.length == 1) {
      String column = columnNames[0];
      query = "SELECT * FROM products WHERE " + column + " LIKE ?";
    } else {
      String columns = String.join(",", columnNames);
      query = "SELECT id, name, category_id, image, gender, price, status FROM products WHERE CONCAT(" + columns
          + ") LIKE ?";
    }

    try (PreparedStatement pst = DatabaseConnection.getPreparedStatement(query, "%" + condition + "%")) {
      try (ResultSet rs = pst.executeQuery()) {
        List<ProductModel> productList = new ArrayList<>();
        while (rs.next()) {
          ProductModel productModel = createProductModelFromResultSet(rs);
          productList.add(productModel);
        }
        return productList;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
