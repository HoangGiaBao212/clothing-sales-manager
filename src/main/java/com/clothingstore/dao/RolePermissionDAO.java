package com.clothingstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.interfaces.IDAO;
import com.clothingstore.models.RolePermissionModel;

public class RolePermissionDAO implements IDAO<RolePermissionModel> {

  private static RolePermissionDAO instance;

  public static RolePermissionDAO getInstance() {
    if (instance == null) {
      instance = new RolePermissionDAO();
    }
    return instance;
  }

  private static RolePermissionModel createRolePermissionModelFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    int roleId = rs.getInt("role_id");
    int permissionId = rs.getInt("permission_id");
    return new RolePermissionModel(id, roleId, permissionId);
  }

  @Override
  public ArrayList<RolePermissionModel> readDatabase() {
    ArrayList<RolePermissionModel> rolePermissionList = new ArrayList<>();
    try (
        ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM role_permissions")) {
      while (rs.next()) {
        RolePermissionModel rolePermissionModel = createRolePermissionModelFromResultSet(rs);
        rolePermissionList.add(rolePermissionModel);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rolePermissionList;
  }

  @Override
  public int insert(RolePermissionModel rolePermissionModel) {
    String insertSql = "INSERT INTO role_permissions (role_id, permission_id) VALUES (?, ?)";
    Object[] args = {
        rolePermissionModel.getRoleId(),
        rolePermissionModel.getPermissionId(),
    };
    try {
      return DatabaseConnection.executeUpdate(insertSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int update(RolePermissionModel rolePermissionModel) {
    String updateSql = "UPDATE role_permissions SET role_id = ?, permission_id = ? WHERE id = ?";
    Object[] args = {
        rolePermissionModel.getRoleId(),
        rolePermissionModel.getPermissionId(),
        rolePermissionModel.getId()
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
    String deleteSql = "DELETE FROM role_permissions WHERE id = ?";
    Object[] args = { id };
    try {
      return DatabaseConnection.executeUpdate(deleteSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public List<RolePermissionModel> search(String condition, String[] columnNames) {
    try {
      if (condition == null || condition.trim().isEmpty()) {
        throw new IllegalArgumentException(
            "Search condition cannot be empty or null");
      }

      String query;
      if (columnNames == null || columnNames.length == 0) {
        query = "SELECT * FROM role_permissions WHERE CONCAT(id, role_id, permission_id) LIKE ?";
      } else if (columnNames.length == 1) {
        String column = columnNames[0];
        query = "SELECT * FROM role_permissions WHERE " + column + " LIKE ?";
      } else {
        query = "SELECT id, role_id, permission_id FROM role_permissions WHERE CONCAT("
            +
            String.join(", ", columnNames) +
            ") LIKE ?";
      }

      try (
          PreparedStatement pst = DatabaseConnection.getPreparedStatement(
              query,
              "%" + condition + "%")) {
        try (ResultSet rs = pst.executeQuery()) {
          List<RolePermissionModel> rolePermissionList = new ArrayList<>();
          while (rs.next()) {
            RolePermissionModel rolePermissionModel = createRolePermissionModelFromResultSet(rs);
            rolePermissionList.add(rolePermissionModel);
          }

          if (rolePermissionList.isEmpty()) {
            throw new SQLException(
                "No records found for the given condition: " + condition);
          }

          return rolePermissionList;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
