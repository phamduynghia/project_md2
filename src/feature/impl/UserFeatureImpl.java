package feature.impl;

import entity.User;
import feature.IUser;
import util.IOFiles;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

public class UserFeatureImpl implements IUser {

    public static List<User> userList ;

    public UserFeatureImpl() {
        List<User> users = (List<User>) IOFiles.readFromFile(IOFiles.USER_PATH);
        if (users == null) {
            // Nếu không đọc được dữ liệu từ file, khởi tạo danh sách userList trống
            users = new ArrayList<>();
        }
        userList = users;
    }

    @Override
    public List<User> getAll() {
        return userList;
    }

    @Override
    public void save(User element) {
        User existingUser = findById(element.getId());
        if (findById(element.getId()) == null) {
            userList.add(element);
        }
        else {
            userList.set(userList.indexOf(findById(element.getId())), element);
        }
        IOFiles.writeToFile(userList, IOFiles.USER_PATH);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            System.err.println(Messages.NOT_FOUND);
            return;
        }
        userList.remove(findById(id));
        IOFiles.writeToFile(userList, IOFiles.USER_PATH);
    }

    @Override
    public User findById(Integer id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Integer getNewId() {
        int maxId = 0;
        for (User user : userList) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }

    public void changeStatus(Integer id) {
        User user = findById(id);
        if (user == null) {
            System.err.println(Messages.NOT_FOUND);
            return;
        }
        user.setStatus(!user.isStatus());
        save(user);
    }

    public User login(String username, String password) {
        for (User user : userList) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean register(String username) {
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                return false;
            }
        }
        return true;
    }
}