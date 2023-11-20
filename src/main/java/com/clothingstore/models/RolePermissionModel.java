package com.clothingstore.models;

import com.clothingstore.enums.RolePermissionStatus;

public class RolePermissionModel {
    private int id;
    private int roleId;
    private int userId;
    private int permissionId;
    private RolePermissionStatus rolePermissionStatus;

    public RolePermissionModel() {
    }

    public RolePermissionModel(int id, int roleId, int userId, int permissionId,
            RolePermissionStatus rolePermissionStatus) {
        this.id = id;
        this.roleId = roleId;
        this.userId = userId;
        this.permissionId = permissionId;
        this.rolePermissionStatus = rolePermissionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public RolePermissionStatus getRolePermissionStatus() {
        return rolePermissionStatus;
    }

    public void setRolePermissionStatus(RolePermissionStatus rolePermissionStatus) {
        this.rolePermissionStatus = rolePermissionStatus;
    }

}
