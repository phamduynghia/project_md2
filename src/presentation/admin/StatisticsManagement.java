package presentation.admin;

import entity.Orders;
import feature.IOrder;
import feature.impl.OrderFeatureImpl;
import util.Colors;
import util.InputMethods;
import util.Messages;

import java.util.Calendar;

import static util.Colors.*;
import static util.Colors.RESET;

public class StatisticsManagement {
    private static final IOrder orderList = new OrderFeatureImpl();
    public StatisticsManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ORDER MANAGEMENT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃            " + PURPLE + "1. Thống kê doanh thu theo tháng                                        " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "2. Thông kê số lượng đơn hàng thành công, hủy theo tháng                " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "3. Thông kê doanh thu từ ngày a đến ngày b                              " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "4. Quay lại                                                             " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    MonthlyRevenueStatistics();
                    break;
                case 2:
                    StatisticsSuccessfulCanceledOrdersMonth();
                    break;
                case 3:
                    RevenueStatisticsAtob();
                    break;
                case 4:
                    return;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 4" + RESET);
            }
        } while (isExist);
    }

    private static void RevenueStatisticsAtob() {
    }

    private static void StatisticsSuccessfulCanceledOrdersMonth() {
    }

    private static void MonthlyRevenueStatistics() {
            int month;
            while (true) {
                System.out.println("Nhập tháng");
                month = InputMethods.getInteger();
                if (month < 1 || month > 12) {
                    System.out.println("Tháng không hợp lệ");
                } else {
                    break;
                }
            }
            System.out.println("Nhập năm");
            int year = InputMethods.getInteger();
            int count = 0;
            int sum = 0;
            for (Orders o : orderList.getAll()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(o.getCreateAt());
                int orderMonth = cal.get(Calendar.MONTH) + 1;
                int orderYear = cal.get(Calendar.YEAR);
                if (orderMonth == month && orderYear == year) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                System.err.println("Không tìm thấy đơn hàng");
            } else {
                count = 0;
                System.out.printf(Colors.GREEN + "%3s | %15s | %15s | %15s | %10s | %10s | %10s \n"
                        , "ID", "ADDRESS", "RECEIVE PHONE", "PRICE", "ORDER AT", "RECEIVE AT", "STATUS" + Colors.RESET);
                for (Orders o : orderList.getAll()) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(o.getCreateAt());
                    int orderMonth = cal.get(Calendar.MONTH) + 1;
                    int orderYear = cal.get(Calendar.YEAR);
                    if (orderMonth == month && orderYear == year) {
                        count++;
                        sum += (int) o.getTotalPrice();
                        o.displayData();
                    }
                }
                System.out.println(Colors.GREEN + "Có " + count + " Đơn hàng trong tháng: " + month + " và Năm: " + year + Colors.RESET);
                System.out.println(Colors.GREEN + "Tổng doanh thu: " + sum + Colors.RESET);
            }
        }
    }

