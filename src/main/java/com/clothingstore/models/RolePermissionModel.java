package com.clothingstore.models;

public class RolePermissionModel {
    private int id;
    private int roleId;
    private int permissionId;

    public RolePermissionModel() {
    }

    public RolePermissionModel(int id, int roleId, int permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
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

}
