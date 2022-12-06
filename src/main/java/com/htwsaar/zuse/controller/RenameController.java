package com.htwsaar.zuse.controller;

import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.service.IWarehouseService;
import com.htwsaar.zuse.service.WarehouseService;
import com.htwsaar.zuse.service.WarehouseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RenameController implements INameController{

    private IWarehouseService warehouseService;
	
	@FXML
	private Button btConfirmWarehouseUpdate;
	
	@FXML
	public void btConfirmWarehouseUpdateClick() {
		
		Stage stage = (Stage) btConfirmWarehouseUpdate.getScene().getWindow();
		Object o = stage.getUserData();
		if (o instanceof Warehouse) {
			Warehouse toUpdate = (Warehouse) o;
			toUpdate.setName(tfWarehouseName.getText().trim());
			warehouseService.update(toUpdate);
		} else {
			throw new IllegalArgumentException();
		}
	    stage.close();
	}
	
	@FXML
	private Button btCancelWarehouseUpdate;
	
	@FXML
	public void btCancelWarehouseUpdateClick() {
	    Stage stage = (Stage) btCancelWarehouseUpdate.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void initialize() {
//		tfWarehouseName.setText("Meddl Leudde");
		warehouseService = new WarehouseService();
	}
	
	@FXML
	private TextField tfWarehouseName;
	
	@FXML
	public void tfWarehouseNameEnter() {
		btConfirmWarehouseUpdateClick();
	}

	public void initializeTextField(String currentName) {
		tfWarehouseName.setText(currentName);
	}
}
