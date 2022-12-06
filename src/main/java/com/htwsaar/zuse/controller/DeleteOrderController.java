package com.htwsaar.zuse.controller;

import com.htwsaar.zuse.service.OrderService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteOrderController {
    @FXML
    private Button btConfirmOrderDelete;
    
    @FXML
    private Button btCancelOrderDelete;

    @FXML
    private void btConfirmOrderDeleteClick(){
        Stage stage = (Stage) btConfirmOrderDelete.getScene().getWindow();
        
        Object o = stage.getUserData();
        int currentOrderId = (int) o;
        OrderService orderService = new OrderService();
        orderService.delete(currentOrderId);

        stage.close();
    }

    @FXML
    private void btCancelOrderDeleteClick(){
        Stage stage = (Stage) btCancelOrderDelete.getScene().getWindow();
        stage.close();
    }
}
