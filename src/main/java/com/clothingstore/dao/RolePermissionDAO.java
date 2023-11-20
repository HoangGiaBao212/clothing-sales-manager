package com.clothingstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.enums.RolePermissionStatus;
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
    int userId = rs.getInt("user_id");
    int permissionId = rs.getInt("permission_id");
    RolePermissionStatus status = RolePermissionStatus.valueOf(rs.getString("status").toUpperCase());
    return new RolePermissionModel(id, roleId, userId, permissionId, status);
  }

  @Override
  public ArrayList<RolePermissionModel> readDatabase() {
    ArrayList<RolePermissionModel> rolePermissionList = new ArrayList<>();
    try (ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM role_permissions")) {
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
  public int insert(RolePermissionModel rolePermission) {
    String insertSql = "INSERT INTO role_permissions (user_id, permission_id, role_id, status) VALUES (?, ?, ?, ?)";
    Object[] args = {
        rolePermission.getUserId(),
        rolePermission.getPermissionId(),
        rolePermission.getRoleId(), // Thêm giá trị role_id
        rolePermission.getRolePermissionStatus().toString().toUpperCase()
    };
    try {
      return DatabaseConnection.executeUpdate(insertSql, args);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public int update(RolePermissionModel rolePermission) {
    String updateSql = "UPDATE role_permissions SET user_id = ?, permission_id = ?, role_id = ?, status = ? WHERE id = ?";
    Object[] args = {
        rolePermission.getUserId(),
        rolePermission.getPermissionId(),
        rolePermission.getRoleId(), // Thêm giá trị role_id
        rolePermission.getRolePermissionStatus().toString().toUpperCase(),
        rolePermission.getId()
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
        throw new IllegalArgumentException("Search condition cannot be empty or null");
      }

      String query;
      if (columnNames == null || columnNames.length == 0) {
        query = "SELECT * FROM role_permissions WHERE CONCAT(id, user_id, permission_id, status) LIKE ?";
      } else if (columnNames.length == 1) {
        String column = columnNames[0];
        query = "SELECT * FROM role_permissions WHERE " + column + " LIKE ?";
      } else {
        query = "SELECT id, user_id, permission_id, status FROM role_permissions WHERE CONCAT("
            + String.join(", ", columnNames) +
            ") LIKE ?";
      }

      try (PreparedStatement pst = DatabaseConnection.getPreparedStatement(query, "%" + condition + "%")) {
        try (ResultSet rs = pst.executeQuery()) {
          List<RolePermissionModel> rolePermissionList = new ArrayList<>();
          while (rs.next()) {
            RolePermissionModel rolePermissionModel = createRolePermissionModelFromResultSet(rs);
            rolePermissionList.add(rolePermissionModel);
          }

          if (rolePermissionList.isEmpty()) {
            throw new SQLException("No records found for the given condition: " + condition);
          }

          return rolePermissionList;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public RolePermissionModel getRolePermissionById(int id) {
    String query = "SELECT * FROM role_permissions WHERE id = ?";
    Object[] args = { id };
    try (PreparedStatement pst = DatabaseConnection.getPreparedStatement(query, args);
        ResultSet rs = pst.executeQuery()) {
      if (rs.next()) {
        return createRolePermissionModelFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
