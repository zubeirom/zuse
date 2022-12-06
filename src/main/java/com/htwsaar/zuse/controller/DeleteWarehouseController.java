package com.htwsaar.zuse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.service.IWarehouseService;
import com.htwsaar.zuse.service.WarehouseService;

public class DeleteWarehouseController {

    Stage stage;

    @FXML
    public void initialize (){}

    @FXML
    private Button btConfirmWarehouseDelete;

    @FXML
    public void btConfirmWarehouseDeleteClick(){
        stage = (Stage) btConfirmWarehouseDelete.getScene().getWindow();
        Object o = stage.getUserData();
        if (o instanceof Warehouse){
            Warehouse currentWarehouse = (Warehouse)o;
            IWarehouseService warehouseService = new WarehouseService();
            warehouseService.delete(currentWarehouse.getId());
        } else {
            throw new IllegalArgumentException();
        }
        stage.close();
    }

    @FXML
    private Button btCancelWarehouseDelete; 

    @FXML
    public void btCancelWarehouseDeleteClick(){
        stage = (Stage) btConfirmWarehouseDelete.getScene().getWindow();
        stage.close();    
    }
}
