package com.clothingstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.enums.UserPermissionStatus;
import com.clothingstore.interfaces.IDAO;
import com.clothingstore.models.UserPermissionModel;

public class UserPermissionDAO implements IDAO<UserPermissionModel> {

  private static UserPermissionDAO instance;

  public static UserPermissionDAO getInstance() {
    if (instance == null) {
      instance = new UserPermissionDAO();
    }
    return instance;
  }

  private static UserPermissionModel createUserPermissionModelFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    int userId = rs.getInt("user_id");
    int permissionId = rs.getInt("permission_id");
    UserPermissionStatus status = UserPermissionStatus.valueOf(rs.getString("status").toUpperCase());
    return new UserPermissionModel(id, userId, permissionId, status);
  }

  @Override
  public ArrayList<UserPermissionModel> readDatabase() {
    ArrayList<UserPermissionModel> userPermissionList = new ArrayList<>();
    try (
        ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM user_permissions")) {
      while (rs.next()) {
        UserPermissionModel userPermissionModel = createUserPermissionModelFromResultSet(rs);
        userPermissionList.add(userPermissionModel);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return userPermissionList;
  }

  @Override
  public int insert(UserPermissionModel userPermissionModel) {
    String insertSql = "INSERT INTO user_permissions (user_id, permission_id, status) VALUES (?, ?, ?)";
    Object[] args = {
        userPermissionModel.getUserId(),
        userPermissionModel.getPermissionId(),
        userPermissionModel.getStatus()
    };
    try {
      return DatabaseConnection.executeUpdate(insertSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int update(UserPermissionModel userPermissionModel) {
    String updateSql = "UPDATE user_permissions SET user_id = ?, permission_id = ?, status = ? WHERE id = ?";
    Object[] args = {
        userPermissionModel.getUserId(),
        userPermissionModel.getPermissionId(),
        userPermissionModel.getStatus() == UserPermissionStatus.ACTIVE ? "ACTIVE" : "INACTIVE",
        userPermissionModel.getId()
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
    String deleteSql = "DELETE FROM user_permissions WHERE id = ?";
    Object[] args = { id };
    try {
      return DatabaseConnection.executeUpdate(deleteSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public List<UserPermissionModel> search(String condition, String[] columnNames) {
    try {
      if (condition == null || condition.trim().isEmpty()) {
        throw new IllegalArgumentException(
            "Search condition cannot be empty or null");
      }

      String query;
      if (columnNames == null || columnNames.length == 0) {
        // Search all columns
        query = "SELECT * FROM user_permissions WHERE CONCAT(id, user_id, permission_id, status) LIKE ?";
      } else if (columnNames.length == 1) {
        // Search specific column in user_permissions table
        String column = columnNames[0];
        query = "SELECT * FROM user_permissions WHERE " + column + " LIKE ?";
      } else {
        // Search specific columns in user_permissions table
        query = "SELECT id, user_id, permission_id, status FROM user_permissions WHERE CONCAT("
            +
            String.join(", ", columnNames) +
            ") LIKE ?";
      }

      try (
          PreparedStatement pst = DatabaseConnection.getPreparedStatement(
              query,
              "%" + condition + "%")) {
        try (ResultSet rs = pst.executeQuery()) {
          List<UserPermissionModel> userPermissionList = new ArrayList<>();
          while (rs.next()) {
            UserPermissionModel userPermissionModel = createUserPermissionModelFromResultSet(rs);
            userPermissionList.add(userPermissionModel);
          }

          if (userPermissionList.isEmpty()) {
            throw new SQLException(
                "No records found for the given condition: " + condition);
          }

          return userPermissionList;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
