package com.clothingstore.models;

import com.clothingstore.enums.UserPermissionStatus;

public class UserPermissionModel {
    private int id;
    private int userId;
    private int permissionId;
    private UserPermissionStatus userPermissionStatus;

    public UserPermissionModel() {
    }

    public UserPermissionModel(int id, int userId, int permissionId, UserPermissionStatus userPermissionStatus) {
        this.id = id;
        this.userId = userId;
        this.permissionId = permissionId;
        this.userPermissionStatus = userPermissionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserPermissionStatus getStatus() {
        return userPermissionStatus;
    }

    public void setStatus(UserPermissionStatus userPermissionStatus) {
        this.userPermissionStatus = userPermissionStatus;
    }

}
