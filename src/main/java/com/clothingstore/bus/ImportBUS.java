package com.clothingstore.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.dao.ImportDAO;
import com.clothingstore.dao.OrderDAO;
import com.clothingstore.interfaces.IBUS;
import com.clothingstore.models.ImportModel;
import com.clothingstore.models.OrderModel;

public class ImportBUS implements IBUS<ImportModel> {
    private final List<ImportModel> importList = new ArrayList<>();
    private static ImportBUS instance;

    public static ImportBUS getInstance() {
        if (instance == null) {
            instance = new ImportBUS();
        }
        return instance;
    }

    private ImportBUS() {
        this.importList.addAll(ImportDAO.getInstance().readDatabase());
    }

    @Override
    public List<ImportModel> getAllModels() {
        return Collections.unmodifiableList(importList);
    }

    @Override
    public void refreshData() {
        importList.clear();
        importList.addAll(ImportDAO.getInstance().readDatabase());
    }

    @Override
    public ImportModel getModelById(int id) {
        refreshData();
        for (ImportModel imports : importList) {
            if (imports.getId() == id) {
                return imports;
            }
        }
        return null;
    }

    private ImportModel mapToEntity(ImportModel from) {
        ImportModel to = new ImportModel();
        updateEntityFields(from, to);
        return to;
    }

    private void updateEntityFields(ImportModel from, ImportModel to) {
        to.setId(from.getId());
        to.setUserId(from.getUserId());
        to.setImportDate(from.getImportDate());
        to.setTotalPrice(from.getTotalPrice());
    }

    @Override
    public int addModel(ImportModel model) {
        if (model == null ||
                // model.getImportDate() == null ||
                model.getTotalPrice() <= 0) {
            throw new IllegalArgumentException(
                    "There may be errors in required fields, please check your input and try again.");
        }
        int id = ImportDAO.getInstance().insert(mapToEntity(model));
        importList.add(model);
        return id;
    }

    @Override
    public int updateModel(ImportModel model) {
        int updatedRows = ImportDAO.getInstance().update(model);
        if (updatedRows > 0) {
            int index = importList.indexOf(model);
            if (index != -1) {
                importList.set(index, model);
            }
        }
        return updatedRows;
    }

    @Override
    public int deleteModel(int id) {
        ImportModel importModel = getModelById(id);
        if (importModel == null) {
            throw new IllegalArgumentException(
                    "Imports with ID: " + id + " don't exist.");
        }
        int deletedRows = ImportDAO.getInstance().delete(id);
        if (deletedRows > 0) {
            importList.remove(importModel);
        }
        return deletedRows;
    }

    private boolean checkFilter(
            ImportModel importModel,
            String value,
            String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "id" -> {
                    if (Integer.parseInt(value) == importModel.getId()) {
                        return true;
                    }
                }
                case "import_date" -> {
                    if (importModel.getImportDate().toString().equals(value)) {
                        return true;
                    }
                }
                case "total_price" -> {
                    if (Double.valueOf(importModel.getTotalPrice()).equals(Double.valueOf(value))) {
                        return true;
                    }
                }
                default -> {
                    if (checkAllColumns(importModel, value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkAllColumns(ImportModel importModel, String value) {
        return (importModel.getId() == Integer.parseInt(value) ||
                importModel.getImportDate().toString().equals(value) ||
                Double.valueOf(importModel.getTotalPrice()).equals(Double.valueOf(value)));
    }

    @Override
    public List<ImportModel> searchModel(String value, String[] columns) {
        List<ImportModel> results = new ArrayList<>();
        List<ImportModel> entities = ImportDAO.getInstance().search(value, columns);
        for (ImportModel entity : entities) {
            ImportModel model = mapToEntity(entity);
            if (checkFilter(model, value, columns)) {
                results.add(model);
            }
        }
        return results;
    }

    public List<ImportModel> searchDateToDate(String fromDate, String toDate) {
    List<ImportModel> results = new ArrayList<>();
    results = ImportDAO.getInstance().searchDatetoDate(fromDate, toDate);
    return results;
  }
}
