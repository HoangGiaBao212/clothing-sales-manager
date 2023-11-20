package com.clothingstore.models;

import com.clothingstore.enums.RolePermissionStatus;

public class RolePermissionModel {
    private int id;
    private int roleId;
    private int permissionId;
    private RolePermissionStatus rolePermissionStatus;

    public RolePermissionModel() {
    }

    public RolePermissionModel(int id, int roleId, int permissionId,
            RolePermissionStatus rolePermissionStatus) {
        this.id = id;
        this.roleId = roleId;
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
