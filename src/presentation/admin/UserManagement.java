package presentation.admin;

import constants.Roles;
import entity.User;
import feature.impl.UserFeatureImpl;
import util.InputMethods;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

import static util.Colors.*;

public class UserManagement {
    private final UserFeatureImpl userFeature = new UserFeatureImpl();

    public UserManagement() {
        boolean isExist = true;
        do {
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ MANAGER USER ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃           " + PURPLE + "1. HIỂN THỊ NGƯỜI DÙNG                   2. TÌM KIẾM NGƯỜI DÙNG          " + BLUE + "┃");
            System.out.println("┃           " + PURPLE + "3. THAY ĐỔI TRẠNG THÁI NGƯỜI DÙNG        4. TRỞ LẠI                      " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    showListUsers();
                    break;
                case 2:
                    searchByNameUser();
                    break;
                case 3:
                    changeStatusUser();
                    break;
                case 4:
                    isExist = false;
                    break;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 4" + RESET);
            }
        } while (isExist);
    }

    private void showListUsers() {
        List<User> users = new ArrayList<>(userFeature.getAll());
        if (users.isEmpty()) {
            System.err.println(Messages.IS_EMPTY);
            return;
        }
        System.out.println("+----+---------------+-----------------+---------------------------------+--------------------------------+---------------+------------+------------+");
        System.out.println("| ID | Tên đăng nhập |     Vai trò     |              Email              |           Địa chỉ              | Số điện thoại | Trạng thái |  Ngày tạo  |");
        System.out.println("+----+---------------+-----------------+---------------------------------+--------------------------------+---------------+------------+------------+");
        for (User user : users) {
            user.displayData();
        }
        System.out.println();
    }
    private void searchByNameUser() {
        System.out.println(BLUE + "Mời nhập tên người dùng cần tìm kiếm: ");
        String textName = InputMethods.getString();
        boolean check = true;
        System.out.println("+----+---------------+-----------------+---------------------------------+--------------------------------+---------------+------------+------------+");
        System.out.println("| ID | Tên đăng nhập |     Vai trò     |              Email              |           Địa chỉ              | Số điện thoại | Trạng thái |  Ngày tạo  |");
        System.out.println("+----+---------------+-----------------+---------------------------------+--------------------------------+---------------+------------+------------+");
        for (User user : userFeature.getAll()) {
            if (user.getFullName().toLowerCase().contains(textName.trim().toLowerCase()) && user.getRoles().equals(Roles.ROLE_USER)) {
                user.displayData();
                check = false;
            }
        }
        if (check) {
            System.out.println(Messages.NOT_FOUND);
        }
        else {
            System.out.println(GREEN + "Đã tìm thấy người dùng" + RESET);
        }
    }

    private void changeStatusUser(){
        System.out.println(BLUE + "Mời bạn nhập vào mã người dùng để thay đổi trạng thái: ");
        int idUser = InputMethods.getInteger();
        User user = userFeature.findById(idUser);
        if (user == null || user.getRoles().equals(Roles.ROLE_ADMIN) || user.getRoles().equals(Roles.ROLE_MODERATOR)) {
            System.out.println(Messages.NOT_FOUND);
            return;
        }
        userFeature.changeStatus(idUser);
        System.out.println(GREEN + "Đã thay đổi thành công trạng thái người dùng" + RESET);
    }
}