package com.htwsaar.zuse.controller;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.service.AddressService;
import com.htwsaar.zuse.service.CustomerService;
import com.htwsaar.zuse.service.IAddressService;
import com.htwsaar.zuse.service.ICustomerService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateCustomerController {
    
    @FXML
    private TextField tfNewCustomerFirstName;
    @FXML
    private TextField tfNewCustomerName;
    @FXML
    private TextField tfNewCustomerCompanyName;
    @FXML
    private TextField tfNewCustomerEMail;
    @FXML
    private TextField tfNewCustomerPhoneNumer;
    @FXML
    private TextField tfNewCustomerAddressStreet;
    @FXML
    private TextField tfNewCustomerAddressPostalCode;
    @FXML
    private TextField tfNewCustomerAddressCity;
    @FXML
    private TextField tfNewCustomerAddressCountry;

    @FXML
    private Button btConfirmCustomerCreate;
    @FXML
    private Button btCancelCustomerCreate;

    @FXML
    private Label lbCreateCustomerErrors;

    @FXML
    private void btConfirmCustomerCreateClick(){
        String firstName = tfNewCustomerFirstName.getText();
        String name = tfNewCustomerName.getText();
        String companyName = tfNewCustomerCompanyName.getText();
        String eMail = tfNewCustomerEMail.getText();

        // TODO: überprüfen, ob Telefonnummer aus Ziffern besteht
        String phoneNumber = tfNewCustomerPhoneNumer.getText();
        
        Customer customer = new Customer(companyName, firstName, name, eMail,phoneNumber);
        String street = tfNewCustomerAddressStreet.getText();
        String postalCode = tfNewCustomerAddressPostalCode.getText();
        String city = tfNewCustomerAddressCity.getText();
        String country = tfNewCustomerAddressCountry.getText();
        Address address = new Address(customer, street, postalCode, city, country);
        IAddressService addressService = new AddressService();
        ICustomerService customerService = new CustomerService();
        customerService.create(customer);
        addressService.create(address);
        Stage stage = (Stage) btConfirmCustomerCreate.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btCancelCustomerCreateClick(){
        Stage stage = (Stage) btCancelCustomerCreate.getScene().getWindow();
        stage.close();
    }
}
