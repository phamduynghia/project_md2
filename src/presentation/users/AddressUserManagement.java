package presentation.users;

import entity.Address;
import entity.User;
import feature.impl.AddressFeatureImpl;
import presentation.run.MenuManagement;
import util.InputMethods;
import util.Messages;

import static util.Colors.*;

public class AddressUserManagement {
    public AddressFeatureImpl addressFeature = new AddressFeatureImpl();
    public AddressUserManagement () {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ Menu Địa chỉ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃         " + PURPLE + "1. Hiển thị tất cả địa chỉ người dùng                                      " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "2. Thêm mới địa chỉ người dùng                                             " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "3. Sửa mới địa chỉ người dùng                                              " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "4. Xóa địa chỉ người dùng                                                  " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "5. Quay lại                                                                " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    displayAllAddress();
                    break;
                case 2:
                    addNewAddress();
                    break;
                case 3:
                    editAddressById();
                    break;
                case 4:
                    deleteAddressById();
                    break;
                case 5:
                    isExist = false;
                    break;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 6" + RESET);
            }
        } while (isExist);
    }

    private void displayAllAddress() {
        User user = MenuManagement.userLogin;
        if (user == null) {
            System.err.println("Vui lòng đăng nhập để xem địa chỉ!");
            return;
        }
        if (addressFeature.getAll().stream().filter(item-> item.getUserId().getId() == user.getId()).count() <= 0) {
            System.err.println(RED + "Không có địa chỉ thêm cho người dùng !" +RESET);
            return;
        }
        System.out.println("+----+---------------+----------------------------------------------+---------------+--------------------+");
        System.out.println("| ID | ID_user       |                    Địa chỉ                   | Số điện thoại |   Tên người nhận   |");
        System.out.println("+----+---------------+----------------------------------------------+---------------+--------------------+");
        for (Address address : addressFeature.getAll()) {
            address.displayData();
        }
        System.out.println();
    }

    private void addNewAddress() {
        System.out.println(BLUE + "Mời bạn nhập thông tin địa chỉ của bạn" + RESET);
        User user = MenuManagement.userLogin;
        Address newAddress = new Address();
        newAddress.inputData(user);
        addressFeature.save(newAddress);
        System.out.println(GREEN + "Đã thêm địa chỉ mới bạn" + RESET);
    }

    private void editAddressById() {
        System.out.println(BLUE + "Mời bạn nhập vào mã địa chỉ cần thay đổi thông tin: " + RESET);
        User user = MenuManagement.userLogin;
        int idAddress = InputMethods.getInteger();
        Address indexAddress = addressFeature.findById(idAddress);
        if(indexAddress != null) {
            indexAddress.inputData(user);
            addressFeature.save(indexAddress);
            System.out.println(GREEN + "Đã thay đổi thông tin địa chỉ thành công" + RESET);
        }
        else {
            System.err.println(RED + "Không tìm thấy thông tin địa chỉ của bạn. " + RESET);
        }
    }

    private void deleteAddressById() {
        System.out.println(BLUE + "Mời bạn nhập vào mã địa chỉ cần xóa thông tin: " + RESET);
        User user = MenuManagement.userLogin;
        int idAddress = InputMethods.getInteger();
        boolean isExist = false;
        for(Address address :addressFeature.getAll()){
            if (address.getAddressId() == idAddress && address.getUserId().getId() == user.getId()){
                addressFeature.delete(idAddress);
                isExist = true;
                break;
            }
        }
        if(isExist) {
            System.out.println(GREEN + "Đã xóa thành công địa chỉ" + RESET);
        } else {
            System.err.println(RED + "Không tìm thấy thông tin địa chỉ của bạn." + RESET);
        }
    }
}