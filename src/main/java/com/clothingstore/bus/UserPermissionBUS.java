package com.clothingstore.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.dao.UserPermissionDAO;
import com.clothingstore.enums.UserPermissionStatus;
import com.clothingstore.interfaces.IBUS;
import com.clothingstore.models.UserPermissionModel;

public class UserPermissionBUS implements IBUS<UserPermissionModel> {
    private final List<UserPermissionModel> userPermissionList = new ArrayList<>();
    private static UserPermissionBUS instance;

    public static UserPermissionBUS getInstance() {
        if (instance == null) {
            instance = new UserPermissionBUS();
        }
        return instance;
    }

    private UserPermissionBUS() {
        this.userPermissionList.addAll(UserPermissionDAO.getInstance().readDatabase());
    }

    @Override
    public List<UserPermissionModel> getAllModels() {
        return Collections.unmodifiableList(userPermissionList);
    }

    @Override
    public void refreshData() {
        userPermissionList.clear();
        userPermissionList.addAll(UserPermissionDAO.getInstance().readDatabase());
    }

    @Override
    public UserPermissionModel getModelById(int id) {
        refreshData();
        for (UserPermissionModel userPermissionModel : userPermissionList) {
            if (userPermissionModel.getId() == id) {
                return userPermissionModel;
            }
        }
        return null;
    }

    @Override
    public int addModel(UserPermissionModel userPermissionModel) {
        int id = UserPermissionDAO.getInstance().insert(userPermissionModel);
        userPermissionModel.setId(id);
        userPermissionList.add(userPermissionModel);
        return id;
    }

    @Override
    public int updateModel(UserPermissionModel userPermissionModel) {
        int updatedRows = UserPermissionDAO.getInstance().update(userPermissionModel);
        if (updatedRows > 0) {
            int index = userPermissionList.indexOf(userPermissionModel);
            if (index != -1) {
                userPermissionList.set(index, userPermissionModel);
            }
        }
        return updatedRows;
    }

    @Override
    public int deleteModel(int id) {
        UserPermissionModel userPermissionModel = getModelById(id);
        if (userPermissionModel == null) {
            throw new IllegalArgumentException(
                    "User Permission with ID: " + id + " doesn't exist.");
        }
        int deletedRows = UserPermissionDAO.getInstance().delete(id);
        if (deletedRows > 0) {
            userPermissionList.remove(userPermissionModel);
        }
        return deletedRows;
    }

    @Override
    public List<UserPermissionModel> searchModel(String value, String[] columns) {
        List<UserPermissionModel> results = new ArrayList<>();
        List<UserPermissionModel> entities = getAllModels();
        for (UserPermissionModel entity : entities) {
            if (checkFilter(entity, value, columns)) {
                results.add(entity);
            }
        }
        return results;
    }

    private boolean checkFilter(
            UserPermissionModel userPermissionModel,
            String value,
            String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "id" -> {
                    if (Integer.parseInt(value) == userPermissionModel.getId()) {
                        return true;
                    }
                }
                case "user_id" -> {
                    if (Integer.parseInt(value) == userPermissionModel.getUserId()) {
                        return true;
                    }
                }
                case "permission_id" -> {
                    if (Integer.parseInt(value) == userPermissionModel.getPermissionId()) {
                        return true;
                    }
                }
                case "status" -> {
                    if (value.equals(userPermissionModel.getStatus())) {
                        return true;
                    }
                }
                default -> {
                }
            }
        }
        return false;
    }
}
