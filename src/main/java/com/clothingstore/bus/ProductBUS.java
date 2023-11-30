package com.clothingstore.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.clothingstore.dao.ProductDAO;
import com.clothingstore.interfaces.IBUS;
import com.clothingstore.models.ProductModel;

public class ProductBUS implements IBUS<ProductModel> {
  private final List<ProductModel> productList = new ArrayList<>();
  private static ProductBUS instance;

  public static ProductBUS getInstance() {
    if (instance == null) {
      instance = new ProductBUS();
    }
    return instance;
  }

  private ProductBUS() {
    this.productList.addAll(ProductDAO.getInstance().readDatabase());
  }

  @Override
  public List<ProductModel> getAllModels() {
    return Collections.unmodifiableList(productList);
  }

  @Override
  public void refreshData() {
    productList.clear();
    productList.addAll(ProductDAO.getInstance().readDatabase());
  }

  @Override
  public ProductModel getModelById(int id) {
    refreshData();
    for (ProductModel product : productList) {
      if (product.getId() == id) {
        return product;
      }
    }
    return null;
  }

  private ProductModel mapToEntity(ProductModel from) {
    ProductModel to = new ProductModel();
    updateEntityFields(from, to);
    return to;
  }

  private void updateEntityFields(ProductModel from, ProductModel to) {
    to.setId(from.getId());
    to.setName(from.getName());
    to.setCategoryId(from.getCategoryId());
    to.setImage(from.getImage());
    to.setGender(from.getGender());
    to.setPrice(from.getPrice());
    to.setStatus(from.getStatus());
  }

  @Override
  public int addModel(ProductModel model) {
    if (model == null || model.getCategoryId() <= 0 || model.getPrice() <= 0) {
      throw new IllegalArgumentException(
          "There may be errors in required fields, please check your input and try again.");
    }
    int id = ProductDAO.getInstance().insert(mapToEntity(model));
    productList.add(model);
    return id;
  }

  @Override
  public int updateModel(ProductModel model) {
    int updatedRows = ProductDAO.getInstance().update(model);
    if (updatedRows > 0) {
      int index = productList.indexOf(model);
      if (index != -1) {
        productList.set(index, model);
      }
    }
    return updatedRows;
  }

  @Override
  public int deleteModel(int id) {
    ProductModel product = getModelById(id);
    if (product == null) {
      throw new IllegalArgumentException(
          "Product with ID: " + id + " doesn't exist.");
    }
    int deletedRows = ProductDAO.getInstance().delete(id);
    if (deletedRows > 0) {
      productList.remove(product);
    }
    return deletedRows;
  }

  private boolean checkFilter(
      ProductModel product,
      String value,
      String[] columns) {
    for (String column : columns) {
      switch (column.toLowerCase()) {
        case "id" -> {
          if (Integer.parseInt(value) == product.getId()) {
            return true;
          }
        }
        case "category_id" -> {
          if (Integer.parseInt(value) == product.getCategoryId()) {
            return true;
          }
        }
        case "price" -> {
          if (Double.valueOf(product.getPrice()).equals(Double.valueOf(value))) {
            return true;
          }
        }
        case "name" -> {
          if (product.getName().toLowerCase().contains(value)) {
            return true;
          }
        }
        case "status" -> {
          if (product.getStatus() == Integer.parseInt(value)) {
            return true;
          }
        }
        default -> {
          if (checkAllColumns(product, value)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean checkAllColumns(ProductModel product, String value) {
    return (product.getId() == Integer.parseInt(value) ||
        product.getCategoryId() == Integer.parseInt(value) ||
        Double.valueOf(product.getPrice()).equals(Double.valueOf(value))) ||
        product.getName().toLowerCase().contains(value);
  }

  @Override
  public List<ProductModel> searchModel(String value, String[] columns) {
    List<ProductModel> results = new ArrayList<>();
    List<ProductModel> entities = ProductDAO.getInstance().search(value, columns);
    for (ProductModel entity : entities) {
      ProductModel model = mapToEntity(entity);
      if (checkFilter(model, value, columns)) {
        results.add(model);
      }
    }
    if (results.size() <= 0) {
      throw new IllegalArgumentException(
          "Không có sản phẩm nào!");
    }
    return results;
  }
}
