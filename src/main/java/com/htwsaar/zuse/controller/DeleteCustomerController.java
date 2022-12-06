package com.htwsaar.zuse.controller;

import java.util.List;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.service.AddressService;
import com.htwsaar.zuse.service.CustomerService;
import com.htwsaar.zuse.service.IAddressService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteCustomerController {
    @FXML
    private Button btConfirmCustomerDelete;
    
    @FXML
    private Button btCancelCustomerDelete;

    IAddressService addressService;

    @FXML
    private void btConfirmCustomerDeleteClick(){
        Stage stage = (Stage) btConfirmCustomerDelete.getScene().getWindow();
        
        Object o = stage.getUserData();
        int currentCustomerId = (int) o;
        Address address = getCustomerAddress(currentCustomerId);
        
        if (address != null) {
            addressService = new AddressService();
            addressService.delete(address.getId());
        }
        CustomerService customerService = new CustomerService();
        customerService.delete(currentCustomerId);

        stage.close();
    }

    // FIXME:Frontend Logik im Backend
    private Address getCustomerAddress(int customerId){
        addressService = new AddressService();
        List<Address> addresses = addressService.getAll();
        if (addresses != null){
            int len = addresses.size();
            for (int i = 0; i < len; i++){
                if (addresses.get(i).getCustomer().getId() == customerId){
                    return addresses.get(i);
                }
            }
        }
        return null;
    }

    @FXML
    private void btCancelCustomerDeleteClick(){
        Stage stage = (Stage) btCancelCustomerDelete.getScene().getWindow();
        stage.close();
    }
} 
