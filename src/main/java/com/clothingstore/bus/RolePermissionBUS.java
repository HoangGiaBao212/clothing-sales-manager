package com.clothingstore.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.dao.RolePermissionDAO;
import com.clothingstore.enums.RolePermissionStatus;
import com.clothingstore.models.RolePermissionModel;

public class RolePermissionBUS {
    private final List<RolePermissionModel> rolePermissionList = new ArrayList<>();
    private static RolePermissionBUS instance;

    public static RolePermissionBUS getInstance() {
        if (instance == null) {
            instance = new RolePermissionBUS();
        }
        return instance;
    }

    private RolePermissionBUS() {
        this.rolePermissionList.addAll(RolePermissionDAO.getInstance().readDatabase());
    }

    public List<RolePermissionModel> getAllModels() {
        return Collections.unmodifiableList(rolePermissionList);
    }

    public void refreshData() {
        rolePermissionList.clear();
        rolePermissionList.addAll(RolePermissionDAO.getInstance().readDatabase());
    }

    public RolePermissionModel getRolePermissionById(int id) {
        refreshData();
        for (RolePermissionModel rolePermission : rolePermissionList) {
            if (rolePermission.getId() == id) {
                return rolePermission;
            }
        }
        return null;
    }

    public int addRolePermission(RolePermissionModel rolePermission) {
        int id = RolePermissionDAO.getInstance().insert(rolePermission);
        rolePermission.setId(id);
        rolePermission.setRolePermissionStatus(
                rolePermission.getRolePermissionStatus() != null ? rolePermission.getRolePermissionStatus()
                        : RolePermissionStatus.ACTIVE);
        rolePermissionList.add(rolePermission);
        return id;
    }

    public int updateRolePermission(RolePermissionModel rolePermission) {
        int updatedRows = RolePermissionDAO.getInstance().update(rolePermission);
        if (updatedRows > 0) {
            int index = rolePermissionList.indexOf(rolePermission);
            if (index != -1) {
                rolePermissionList.set(index, rolePermission);
            }
        }
        return updatedRows;
    }

    public int deleteRolePermission(int id) {
        RolePermissionModel rolePermission = getRolePermissionById(id);
        if (rolePermission == null) {
            throw new IllegalArgumentException(
                    "RolePermission with ID: " + id + " doesn't exist.");
        }
        int deletedRows = RolePermissionDAO.getInstance().delete(id);
        if (deletedRows > 0) {
            rolePermissionList.remove(rolePermission);
        }
        return deletedRows;
    }

    public List<RolePermissionModel> searchRolePermission(String value, String[] columns) {
        List<RolePermissionModel> results = new ArrayList<>();
        List<RolePermissionModel> rolePermissions = getAllModels();
        for (RolePermissionModel rolePermission : rolePermissions) {
            if (checkFilter(rolePermission, value, columns)) {
                results.add(rolePermission);
            }
        }
        return results;
    }

    private boolean checkFilter(
            RolePermissionModel rolePermission,
            String value,
            String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "id" -> {
                    if (Integer.parseInt(value) == rolePermission.getId()) {
                        return true;
                    }
                }
                case "role_id" -> {
                    if (Integer.parseInt(value) == rolePermission.getRoleId()) {
                        return true;
                    }
                }
                case "permission_id" -> {
                    if (Integer.parseInt(value) == rolePermission.getPermissionId()) {
                        return true;
                    }
                }
                case "status" -> {
                    if (rolePermission.getRolePermissionStatus().toString().toLowerCase().equalsIgnoreCase(value)) {
                        return true;
                    }
                }
                default -> {
                }
            }
        }
        return false;
    }
    public int getNewID() {
        return rolePermissionList.size() + 1;
    }
}
