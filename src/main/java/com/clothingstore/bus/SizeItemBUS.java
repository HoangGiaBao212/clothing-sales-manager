package com.clothingstore.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.dao.SizeItemDAO;
import com.clothingstore.interfaces.IBUS;
import com.clothingstore.models.SizeItemModel;

public class SizeItemBUS implements IBUS<SizeItemModel> {
  private final List<SizeItemModel> sizeItemList = new ArrayList<>();
  private static SizeItemBUS instance;

  public static SizeItemBUS getInstance() {
    if (instance == null) {
      instance = new SizeItemBUS();
    }
    return instance;
  }

  private SizeItemBUS() {
    this.sizeItemList.addAll(SizeItemDAO.getInstance().readDatabase());
  }

  @Override
  public List<SizeItemModel> getAllModels() {
    return Collections.unmodifiableList(sizeItemList);
  }

  @Override
  public void refreshData() {
    sizeItemList.clear();
    sizeItemList.addAll(SizeItemDAO.getInstance().readDatabase());
  }

  @Override
  public SizeItemModel getModelById(int id) {
    refreshData();
    for (SizeItemModel sizeItemModel : sizeItemList) {
      if (sizeItemModel.getId() == id) {
        return sizeItemModel;
      }
    }
    return null;
  }

  @Override
  public int addModel(SizeItemModel sizeItemModel) {
    int id = SizeItemDAO.getInstance().insert(sizeItemModel);
    sizeItemModel.setId(id);
    sizeItemList.add(sizeItemModel);
    return id;
  }

  @Override
  public int updateModel(SizeItemModel sizeItemModel) {
    int updatedRows = SizeItemDAO.getInstance().update(sizeItemModel);
    if (updatedRows > 0) {
      int index = sizeItemList.indexOf(sizeItemModel);
      if (index != -1) {
        sizeItemList.set(index, sizeItemModel);
      }
    }
    return updatedRows;
  }

  @Override
  public int deleteModel(int id) {
    SizeItemModel sizeItemModel = getModelById(id);
    if (sizeItemModel == null) {
      throw new IllegalArgumentException(
          "Size item with ID: " + id + " doesn't exist.");
    }
    int deletedRows = SizeItemDAO.getInstance().delete(id);
    if (deletedRows > 0) {
      sizeItemList.remove(sizeItemModel);
    }
    return deletedRows;
  }

  @Override
  public List<SizeItemModel> searchModel(String value, String[] columns) {
    List<SizeItemModel> results = new ArrayList<>();
    List<SizeItemModel> entities = getAllModels();
    for (SizeItemModel entity : entities) {
      if (checkFilter(entity, value, columns)) {
        results.add(entity);
      }
    }
    return results;
  }

  public List<SizeItemModel> getSizeItemsByProductId(int productId) {
    SizeItemDAO sizeItemDAO = SizeItemDAO.getInstance();
    if (sizeItemDAO != null) {
      return sizeItemDAO.getSizeItemsByProductId(productId);
    } else {
      return Collections.emptyList();
    }
  }

  private boolean checkFilter(
      SizeItemModel sizeItemModel,
      String value,
      String[] columns) {
    for (String column : columns) {
      switch (column.toLowerCase()) {
        case "id" -> {
          if (Integer.parseInt(value) == sizeItemModel.getId()) {
            return true;
          }
        }
        case "product_id" -> {
          if (Integer.parseInt(value) == sizeItemModel.getProductId()) {
            return true;
          }
        }
        case "size_id" -> {
          if (Integer.parseInt(value) == sizeItemModel.getSizeId()) {
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
