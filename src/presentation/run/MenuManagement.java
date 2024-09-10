package presentation.run;

import constants.Roles;
import entity.User;
import feature.impl.UserFeatureImpl;
import presentation.admin.MenuAdminManagement;
import presentation.moderator.ModeratorManagement;
import presentation.users.MenuHomeManagement;
import presentation.users.MenuUserManagement;
import util.InputMethods;
import util.Messages;
import util.Validate;


import static util.Colors.*;

public class MenuManagement {

    public static UserFeatureImpl userFeature = new UserFeatureImpl();
    public static User userLogin = null;

    public static void main(String[] args) {
        do {
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━WEB BÁN HÀNG━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                           ┃                             ┃                          ┃                          ┃                          ┃");
            System.out.println("┃      " + PURPLE + "1. TRANG CHỦ         " + BLUE + "┃       " + YELLOW + "2. ĐĂNG NHẬP          " + BLUE + "┃     " + GREEN + "   3. ĐĂNG KÝ   " + BLUE + "     ┃    " + WHITE + "4. QUÊN MẬT KHẨU      " + BLUE + "┃"  + RED + "          5. THOÁT  " + BLUE + "      ┃");
            System.out.println("┃                           ┃                             ┃                          ┃                          ┃                          ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    new MenuHomeManagement();
                    break;
                case 2:
                    inputLogin();
                    break;
                case 3:
                    inputRegister();
                    break;
                case 4:
                    forgetPassword();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Vui lòng nhập lại từ 1 -> 5");
            }
        } while (true);
    }

    public static void inputLogin() {
        System.out.println("Nhập vào tên đăng nhập: ");
        String username = InputMethods.getString();
        System.out.println("Nhập vào mật khẩu: ");
        String password = InputMethods.getString();
        User user = userFeature.login(username, password);
        if (user == null) {
            System.err.println(Messages.SIGN_IN_FAILED);
            return;
        }
        userLogin = user;
        if (user.getRoles().equals(Roles.ROLE_ADMIN)) {
            System.out.println(Messages.SIGN_IN_SUCCESS);
            new MenuAdminManagement();
        } else if (user.getRoles().equals(Roles.ROLE_MODERATOR)) {
            System.out.println(Messages.SIGN_IN_SUCCESS);
            new ModeratorManagement();
        } else {
            if (user.isStatus()) {
                System.out.println(Messages.SIGN_IN_SUCCESS);
                new MenuUserManagement();
            }
            else  {
                System.err.println(Messages.ACCOUNT_BLOCKS);
            }
        }

    }
    public static void inputRegister() {
        User user = new User();
        user.setId(userFeature.getNewId());
        user.inputData();
        System.out.println(BLUE + "Xác nhận mật khẩu: " + RESET);
        String confirmPassword = InputMethods.getString();
        if (Validate.usernames(user.getUserName())) {
            if (Validate.password(user.getPassword())) {
                user.setPassword(user.getPassword());
                if (user.getPassword().equals(confirmPassword)) {
                    System.out.println(Messages.REGISTER_SUCCESS);
                    userFeature.save(user);
                } else {
                    System.err.println(RED + "Mật khẩu không khớp. Vui lòng thử lại." + RESET);
                }
            } else {
                System.err.println(RED + "Mật khẩu không hợp lệ. Vui lòng thử lại." + RESET);
            }
        } else {
            System.err.println(RED + "Tên người dùng không hợp lệ. Vui lòng thử lại." + RESET);
        }
    }
    public static void forgetPassword() {
        System.out.println("Mời bạn nhập tên đăng nhập: ");
        String username = InputMethods.getString();
        User myUser = null;
        for (User user : userFeature.getAll()) {
            if (user.getUserName().equals(username)){
                myUser = user;
            }
        }
        if (myUser == null) {
            System.out.println(Messages.NOT_FOUND);
            return;
        }
        String newPassword = newPassword();
        System.out.println(YELLOW + "Mật khẩu mới là: " + newPassword + RESET);
        myUser.setPassword(newPassword);
        userFeature.save(myUser);
    }

    public static String newPassword() {
        StringBuilder result = null;
        for (int i = 0; i < 5; i++) {
            if (result == null) {
                result = new StringBuilder(String.valueOf((int) Math.ceil(Math.random() * 9)));
            }
            result.append(String.valueOf((int) Math.ceil(Math.random() * 9)));
        }
        return result.toString();
    }
}