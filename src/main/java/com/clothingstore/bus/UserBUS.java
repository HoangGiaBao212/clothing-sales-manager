package com.clothingstore.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import com.clothingstore.dao.UserDAO;
import com.clothingstore.enums.UserStatus;
import com.clothingstore.interfaces.IBUS;
import com.clothingstore.models.UserModel;

import services.PasswordUtils;
import services.Validation;

public class UserBUS implements IBUS<UserModel> {

  private final List<UserModel> userList = new ArrayList<>();
  private static UserBUS instance;

  public static UserBUS getInstance() {
    if (instance == null) {
      instance = new UserBUS();
    }
    return instance;
  }

  private UserBUS() {
    this.userList.addAll(UserDAO.getInstance().readDatabase());
  }

  @Override
  public List<UserModel> getAllModels() {
    return Collections.unmodifiableList(userList);
  }

  @Override
  public void refreshData() {
    userList.clear();
    userList.addAll(UserDAO.getInstance().readDatabase());
  }

  @Override
  public UserModel getModelById(int id) {
    for (UserModel userModel : userList) {
      if (userModel.getId() == id) {
        return userModel;
      }
    }

    UserModel userModel = UserDAO.getInstance().getUserById(id);
    if (userModel != null) {
      userList.add(userModel);
    }
    return userModel;
  }

  @Override
  public int addModel(UserModel userModel) {
    if (userModel.getUsername() == null ||
        userModel.getUsername().isEmpty() ||
        userModel.getName() == null ||
        userModel.getName().isEmpty() ||
        userModel.getPassword() == null ||
        userModel.getPassword().isEmpty()) {
      throw new IllegalArgumentException(
          "Tên đăng nhập, tên và mật khẩu không được để trống. Vui lòng kiểm tra lại đầu vào.");
    }

    ArrayList<UserModel> userList = UserDAO.getInstance().readDatabase();
    for (UserModel userModel2 : userList) {
      if (userModel2.getUsername().equals(userModel.getUsername())) {
        throw new IllegalArgumentException("Tài khoản đã tồn tại!");
      }
      if (userModel2.getEmail().equals(userModel.getEmail())) {
        throw new IllegalArgumentException("Email đã tồn tại!");
      }
      if (userModel2.getPhone().equals(userModel.getPhone())) {
        throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
      }
    }

    boolean hasPhone = userModel.getPhone() != null && !userModel.getPhone().isEmpty();
    boolean hasEmail = userModel.getEmail() != null && !userModel.getEmail().isEmpty();

    if (hasEmail && !Validation.isValidEmail(userModel.getEmail())) {
      throw new IllegalArgumentException("Email không hợp lệ!");
    }

    if (hasPhone && !Validation.isValidPhoneNumber(userModel.getPhone())) {
      throw new IllegalArgumentException("Số điện thoại không hợp lệ!");
    }

    // 1 is admin, 2 is manager, 3 is employee

    userModel.setRoleId(userModel.getRoleId());
    userModel.setPassword(PasswordUtils.hashPassword(userModel.getPassword()));
    userModel.setUserStatus(
        userModel.getUserStatus() != null ? userModel.getUserStatus() : UserStatus.ACTIVE);
    int id = UserDAO.getInstance().insert(userModel);
    userModel.setId(id);
    userList.add(userModel);
    return id;
  }

  @Override
  public int updateModel(UserModel userModel) {
    if (userModel.getUsername() == null ||
        userModel.getUsername().isEmpty() ||
        userModel.getName() == null ||
        userModel.getName().isEmpty() ||
        userModel.getPassword() == null ||
        userModel.getPassword().isEmpty()) {
      throw new IllegalArgumentException(
          "Tên đăng nhập, tên và mật khẩu không được để trống. Vui lòng kiểm tra lại đầu vào.");
    }

    ArrayList<UserModel> userList = UserDAO.getInstance().readDatabase();
    boolean hasPhone = userModel.getPhone() != null && !userModel.getPhone().isEmpty();
    boolean hasEmail = userModel.getEmail() != null && !userModel.getEmail().isEmpty();

    if (hasEmail && !Validation.isValidEmail(userModel.getEmail())) {
      throw new IllegalArgumentException("Email không hợp lệ!");
    }

    if (hasPhone && !Validation.isValidPhoneNumber(userModel.getPhone())) {
      throw new IllegalArgumentException("Số điện thoại không hợp lệ!");
    }
    int updatedRows = UserDAO.getInstance().update(userModel);
    if (updatedRows > 0) {
      for (int i = 0; i < userList.size(); i++) {
        if (userList.get(i).getId() == userModel.getId()) {
          userList.set(i, userModel);
          break;
        }
      }
    }
    return updatedRows;
  }

  @Override
  public int deleteModel(int id) {
    UserModel userModel = getModelById(id);
    if (userModel == null) {
      throw new IllegalArgumentException(
          "User with ID " + id + " does not exist.");
    }
    int deletedRows = UserDAO.getInstance().delete(id);
    if (deletedRows > 0) {
      userList.remove(userModel);
    }
    return deletedRows;
  }

  @Override
  public List<UserModel> searchModel(String value, String[] columns) {
    List<UserModel> results = new ArrayList<>();
    List<UserModel> entities = UserDAO.getInstance().search(value, columns);
    for (UserModel entity : entities) {
      UserModel model = mapToEntity(entity);
      if (checkFilter(model, value, columns)) {
        results.add(model);
      }
    }
    return results;
  }

  private UserModel mapToEntity(UserModel from) {
    UserModel to = new UserModel();
    updateEntityFields(from, to);
    return to;
  }

  private void updateEntityFields(UserModel from, UserModel to) {
    to.setId(from.getId());
    to.setUsername(from.getUsername());
    to.setPassword(from.getPassword());
    to.setEmail(from.getEmail());
    to.setName(from.getName());
    to.setPhone(from.getPhone());
    to.setGender(from.getGender());
    to.setImage(from.getImage());
    to.setRoleId(from.getRoleId());
    to.setAddress(from.getAddress());
    to.setUserStatus(from.getUserStatus());
  }

  private boolean checkFilter(
      UserModel userModel,
      String value,
      String[] columns) {
    value.toLowerCase();
    for (String column : columns) {
      switch (column.toLowerCase()) {
        case "id" -> {
          if (userModel.getId() == Integer.parseInt(value)) {
            return true;
          }
        }
        case "username" -> {
          if (userModel.getUsername().toLowerCase().contains(value)) {
            return true;
          }
        }
        case "status" -> {
          if (userModel.getUserStatus().toString().toLowerCase().equalsIgnoreCase(value)) {
            return true;
          }
        }
        case "name" -> {
          if (userModel.getName().toLowerCase().contains(value)) {
            return true;
          }
        }
        case "email" -> {
          if (userModel.getEmail().toLowerCase().contains(value)) {
            return true;
          }
        }
        case "phone" -> {
          if (userModel.getPhone().equals(value)) {
            return true;
          }
        }
        case "role_id" -> {
          if (String.valueOf(userModel.getRoleId()).contains(value)) {
            return true;
          }
        }
        case "address" -> {
          if (userModel.getAddress().toLowerCase().contains(value)) {
            return true;
          }
        }
        default -> {
          if (checkAllColumns(userModel, value)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean checkAllColumns(UserModel userModel, String value) {
    return (userModel.getId() == Integer.parseInt(value) ||
        userModel.getUsername().toLowerCase().contains(value) ||
        userModel.getUserStatus().toString().toLowerCase().equalsIgnoreCase(value) ||
        userModel.getName().toLowerCase().contains(value) ||
        userModel.getEmail().toLowerCase().contains(value) ||
        userModel.getPhone().equals(value) ||
        String.valueOf(userModel.getRoleId()).toLowerCase().contains(value));
  }

  public boolean checkForDuplicate(List<String> values, String[] columns) {
    Optional<UserModel> optionalUser = UserBUS.getInstance().getAllModels().stream().filter(user -> {
      for (String value : values) {
        if (Arrays.asList(columns).contains("username") &&
            !value.isEmpty() &&
            user.getUsername().equals(value)) {
          return true;
        }
        if (Arrays.asList(columns).contains("email") &&
            user.getEmail().equals(value)) {
          return true;
        }
        if (Arrays.asList(columns).contains("phone") &&
            user.getPhone().equals(value)) {
          return true;
        }
      }
      return false;
    })
        .findFirst();
    return optionalUser.isPresent();
  }

  public UserModel login(String username, String password) throws LoginException {
    if (!Validation.isValidUsername(username)) {
      throw new LoginException("Tên đăng nhập phải đủ 8 kí tự trở lên, phải có chữ và số");
    }
    if (!Validation.isValidPassword(password)) {
      throw new LoginException(
          "Mật khẩu phải đủ 8 kí tự trở lên, phải có chữ và số và in hoa kí tự đầu");
    }
    UserModel userModel = UserDAO.getInstance().getUserByUsername(username);
    if (userModel == null) {
      throw new LoginException("Tên đăng nhập không tồn tại!");
    }
    if (!PasswordUtils.checkPassword(password, userModel.getPassword())) {
      throw new LoginException("Mật khẩu không đúng");
    }
    if (userModel.getUserStatus() == UserStatus.INACTIVE) {
      throw new LoginException("Tài khoản đang hoạt động");
    }
    if (userModel.getUserStatus() == UserStatus.BANNED) {
      throw new LoginException("Tài khoản bị khóa!");
    }
    return userModel;
  }
}
