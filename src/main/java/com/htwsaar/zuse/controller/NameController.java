package com.htwsaar.zuse.controller;

import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.service.IWarehouseService;
import com.htwsaar.zuse.service.WarehouseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NameController implements INameController {

    private IWarehouseService warehouseService;
	
	@FXML
	private Button btConfirmWarehouseCreation;
	
	@FXML
	public void btConfirmWarehouseCreationClick() {
		Warehouse creation = new Warehouse(tfWarehouseName.getText().trim());
		warehouseService.create(creation);
		Stage stage = (Stage) btConfirmWarehouseCreation.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private Button btCancelWarehouseCreation;
	
	@FXML
	public void btCancelWarehouseCreationClick() {
	    Stage stage = (Stage) btCancelWarehouseCreation.getScene().getWindow();
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
		btConfirmWarehouseCreationClick();
	}

	public void initializeTextField(String warehouseNr) {
		tfWarehouseName.setText("Warehouse " + warehouseNr);
	}
}
