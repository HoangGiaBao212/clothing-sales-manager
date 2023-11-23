package com.clothingstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.interfaces.IDAO;
import com.clothingstore.models.ImportModel;

public class ImportDAO implements IDAO<ImportModel> {
  private static ImportDAO instance;

  public static ImportDAO getInstance() {
    if (instance == null) {
      instance = new ImportDAO();
    }
    return instance;
  }

  private static ImportModel createImportModelFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    LocalDateTime importDateTime = rs.getTimestamp("import_date").toLocalDateTime();
    int userId = rs.getInt("user_id");
    Double totalCost = rs.getDouble("total_price");
    return new ImportModel(id, userId, importDateTime, totalCost);
  }

  @Override
  public ArrayList<ImportModel> readDatabase() {
    ArrayList<ImportModel> importList = new ArrayList<>();
    try (
        ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM imports")) {
      while (rs.next()) {
        ImportModel ImportModel = createImportModelFromResultSet(rs);
        importList.add(ImportModel);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return importList;
  }

  @Override
  public int insert(ImportModel importModel) {
    String insertSql = "INSERT INTO imports (user_id, import_date, total_price) VALUES (?, CURRENT_TIMESTAMP, ?)";
    Object[] args = {
        importModel.getUserId(),
        importModel.getTotalPrice()
    };
    try {
      return DatabaseConnection.executeUpdate(insertSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int update(ImportModel importModel) {
    String updateSql = "UPDATE imports SET import_date = ?, total_price = ? WHERE id = ?";
    Object[] args = {
        importModel.getImportDate(),
        importModel.getTotalPrice(),
        importModel.getId()
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
    String deleteSql = "DELETE FROM imports WHERE id = ?";
    Object[] args = { id };
    try {
      return DatabaseConnection.executeUpdate(deleteSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public List<ImportModel> search(String condition, String[] columnNames) {
    if (condition == null || condition.trim().isEmpty()) {
      throw new IllegalArgumentException("Search condition cannot be empty or null");
    }

    String query;
    if (columnNames == null || columnNames.length == 0) {
      query = "SELECT * FROM imports WHERE CONCAT(id, user_id, import_date, total_price) LIKE ?";
    } else if (columnNames.length == 1) {
      String column = columnNames[0];
      query = "SELECT * FROM imports WHERE " + column + " LIKE ?";
    } else {
      String columns = String.join(",", columnNames);
      query = "SELECT id, user_id, import_date, total_price FROM imports WHERE CONCAT(" + columns + ") LIKE ?";
    }

    try (PreparedStatement pst = DatabaseConnection.getPreparedStatement(query, "%" + condition + "%")) {
      try (ResultSet rs = pst.executeQuery()) {
        List<ImportModel> importList = new ArrayList<>();
        while (rs.next()) {
          ImportModel ImportModel = createImportModelFromResultSet(rs);
          importList.add(ImportModel);
        }
        if (importList.isEmpty()) {
          throw new SQLException("No records found for the given condition: " + condition);
        }
        return importList;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public List<ImportModel> searchDatetoDate(String fromDate, String toDate) {
    try {
      String query;
      if (fromDate == null && toDate == null) {
        query = "SELECT * FROM imports";
      } else if (fromDate == null || toDate == null) {
        throw new IllegalArgumentException("Both fromDate and toDate must be specified.");
      } else {
        query = "SELECT * FROM imports WHERE import_date BETWEEN ? AND ?";
      }
      try (
          PreparedStatement pst = (fromDate != null && toDate != null)
              ? DatabaseConnection.getPreparedStatement(query, fromDate, toDate)
              : DatabaseConnection.getPreparedStatement(query);
          ResultSet rs = pst.executeQuery()) {
        List<ImportModel> importList = new ArrayList<>();
        while (rs.next()) {
          ImportModel importModel = createImportModelFromResultSet(rs);
          importList.add(importModel);
        }
        return importList;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
