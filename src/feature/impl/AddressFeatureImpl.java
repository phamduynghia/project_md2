package feature.impl;

import entity.Address;
import feature.IAddress;
import util.IOFiles;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

public class AddressFeatureImpl implements IAddress {
    public static List<Address> addressList = (List<Address>) IOFiles.readFromFile(IOFiles.ADDRESS_PATH);

    public AddressFeatureImpl() {
        List<Address> address = (List<Address>) IOFiles.readFromFile(IOFiles.ADDRESS_PATH);
        if (address == null) {
            //Nếu không đọc được dữ liệu từ file, khởi tạo danh sách addressList trống
            address = new ArrayList<>();
        }
        addressList = address;
    }

    @Override
    public List<Address> getAll() {
        return addressList;
    }

    @Override
    public void save(Address element) {
        if (findById(element.getAddressId()) == null) {
            element.setAddressId(getNewId());
            addressList.add(element);
        }
        else {
            addressList.set(addressList.indexOf(findById(element.getAddressId())), element);
        }
        IOFiles.writeToFile(addressList, IOFiles.ADDRESS_PATH);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            System.out.println(Messages.NOT_FOUND);
        }
        addressList.remove(findById(id));
        IOFiles.writeToFile(addressList, IOFiles.ADDRESS_PATH);
    }

    @Override
    public Address findById(Integer id) {
        for (Address address : addressList) {
            if (address.getAddressId() == id) {
                return address;
            }
        }
        return null;
    }

    @Override
    public Integer getNewId() {
        int maxId = 0;
        for (Address address : addressList) {
            if (address.getAddressId() > maxId) {
                maxId = address.getAddressId();
            }
        }
        return maxId + 1;
    }
}