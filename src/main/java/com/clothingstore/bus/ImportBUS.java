package com.clothingstore.bus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.clothingstore.dao.ImportDAO;
import com.clothingstore.interfaces.IBUS;
import com.clothingstore.models.ImportModel;

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

  public List<ImportModel> searchDateToDate(Date fromDate, Date toDate) {
    checkDateValidation(fromDate, toDate);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fromDateStr = (fromDate != null) ? sdf.format(fromDate) : null;
    String toDateStr = (toDate != null) ? sdf.format(toDate) : null;
    List<ImportModel> results = ImportDAO.getInstance().searchDatetoDate(fromDateStr, toDateStr);
    if (results.isEmpty())
      throw new IllegalArgumentException("Không có đơn nhập hàng nào trong khoảng thời gian mà bạn tìm kiếm");
    return results;
  }

  private void checkDateValidation(Date fromDate, Date toDate) {
    Date currentDate = new Date();
    if (fromDate == null && toDate != null) {
      throw new IllegalArgumentException("Không được để trống ngày bắt đầu");
    } else if (toDate == null && fromDate != null) {
      throw new IllegalArgumentException("Không được để trống ngày kết thúc");
    } else if (fromDate == null && toDate == null) {
      throw new IllegalArgumentException("Chưa nhập dữ liệu ngày tháng");
    } else {
      int result1 = fromDate.compareTo(currentDate);
      int result2 = toDate.compareTo(currentDate);
      int result3 = fromDate.compareTo(toDate);
      if (result1 > 0) {
        throw new IllegalArgumentException("Ngày bắt đầu không thể lớn hơn ngày hiện tại.");
      } else if (result2 > 0) {
        throw new IllegalArgumentException("Ngày kết thúc không thể lớn hơn ngày hiện tại.");
      } else if (result3 > 0) {
        throw new IllegalArgumentException("Ngày bắt đầu không thể lớn hơn ngày kết thúc.");
      }
    }
  }

}
