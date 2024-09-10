package presentation.run;

import constants.Roles;
import entity.User;
import feature.impl.UserFeatureImpl;

import java.util.Date;

public class DataTest {
    static final UserFeatureImpl userFeature = new UserFeatureImpl();

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setUserName("ADMIN");
        user.setPassword("Nghia10011996@");
        user.setFullName("Phạm Duy Nghĩa");
        user.setEmail("Dr3amm1n1st3r@gmail.com");
        user.setRoles(Roles.ROLE_ADMIN);
        user.setCreatedAt(new Date());
        user.setAddress("Duy Tiên, Hà Nam");
        user.setPhone("0377332810");
        user.setStatus(true);

        user.setId(2);
        user.setUserName("nguoidung");
        user.setPassword("Nd10011996@");
        user.setFullName("Phan ích");
        user.setEmail("Hanoi@gmail.com");
        user.setRoles(Roles.ROLE_USER);
        user.setCreatedAt(new Date());
        user.setAddress("Hoàng Mai, Hà Nội");
        user.setPhone("0987654321");
        user.setStatus(true);

        user.setId(3);
        user.setUserName("nguoidung2");
        user.setPassword("Nd10011996@");
        user.setFullName("Phan ích Huy");
        user.setEmail("Hanoi2@gmail.com");
        user.setRoles(Roles.ROLE_USER);
        user.setCreatedAt(new Date());
        user.setAddress("Hoàng Mai 2, Hà Nội");
        user.setPhone("0987654326");
        user.setStatus(true);
        userFeature.save(user);
        userFeature.getAll().forEach(User::displayData);
    }
}