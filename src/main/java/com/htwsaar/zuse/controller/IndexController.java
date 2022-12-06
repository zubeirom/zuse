/**
 * @author lux,johannes; neu,wiebke; lorenz,robin
 */
package com.htwsaar.zuse.controller;

import com.htwsaar.zuse.IndexApplication;
import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.model.Article;
import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.model.Order;
import com.htwsaar.zuse.model.OrderItem;
import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.service.AddressService;
import com.htwsaar.zuse.service.ArticleService;
import com.htwsaar.zuse.service.CategoryService;
import com.htwsaar.zuse.service.CustomerService;
import com.htwsaar.zuse.service.IAddressService;
import com.htwsaar.zuse.service.IArticleService;
import com.htwsaar.zuse.service.ICategoryService;
import com.htwsaar.zuse.service.ICustomerService;
import com.htwsaar.zuse.service.IOrderItemService;
import com.htwsaar.zuse.service.IOrderService;
import com.htwsaar.zuse.service.IWarehouseService;
import com.htwsaar.zuse.service.OrderItemService;
import com.htwsaar.zuse.service.OrderService;
import com.htwsaar.zuse.service.WarehouseService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class IndexController {

	private final static int NEW = 0;
	private final static int UPDATE = 1;
	private final static int TILE_SIZE = 70;
	private final static int WAREHOUSE_SIZE = 70;
	private final static int CUSTOMER_TAB_INDEX = 0;
	private final static int ARTICLE_TAB_INDEX = 2;
	private final static int ORDER_TAB_INDEX = 1;
	private final static int FONT_SIZE_PLUS = 36;

	// IDs for remembering the current state of the UI
	private int currentWarehouseId;
	private int currentCustomerId;
	private int currentArticleId;
	private int currentOrderId;
	private Order.OrderStatus currentOrderStatus;

	@FXML
	private StackPane sPaneRoot;

	/*  The central area, where dynamic Buttons are generated for customers,
		orders and articles */
	@FXML
	private TilePane tpButtons;
	/* The Label, where Error Messages are displayed*/
	@FXML
	private Label lbErrors;
	/**	The Box containing the different lines of an order */
	@FXML
	private VBox vBoxOrderPositions;
	/** The Services necessary for communicating with the database */
	private IWarehouseService warehouseService;
	private IArticleService articleService;
	private ICategoryService categoryService;
	private ICustomerService customerService;
	private IAddressService addressService;
	private IOrderService orderService;
	private IOrderItemService orderItemService;

	@FXML
	private Button btAddWarehouse;
	@FXML
	private HBox hBoxWarehouse;
	@FXML
	private Button btUpdate;
	@FXML
	private Button btDelete;
	@FXML
	private ComboBox<String> cbCategories;
	@FXML
	private ComboBox<String> cbArticleCategory;
	@FXML
	private TextField tfNewCategory;
	@FXML
	private ScrollPane spWarehouse;
	@FXML
	private HBox hBoxWarehouseInSP;
	@FXML
	private Button btAddArticle;

	@FXML
	private TextField tfArticleName;
	@FXML
	private TextField tfArticleId;
	@FXML
	private TextField tfArticlePrice;
	@FXML
	private TextField tfArticleStock;
	@FXML
	private TextArea tfArticleDescription;

	@FXML
	private Button btNewCategory;
	@FXML
	private Button btDeleteWarehouse;

	@FXML
	private TextField tfCustomerId;
	@FXML
	private TextField tfCustomerFirstName;
	@FXML
	private TextField tfCustomerLastName;
	@FXML
	private TextField tfCustomerCompanyName;
	@FXML
	private TextField tfCustomerEMail;
	@FXML
	private TextField tfCustomerPhoneNumber;
	@FXML
	private TextArea tfCustomerAddress;

	@FXML
	private Tab tabArticle;
	@FXML
	private Tab tabCustomer;
	@FXML
	private Tab tabOrder;
	@FXML
	private TabPane tabPane;
	SingleSelectionModel<Tab> selectionModel;

	@FXML
	private SplitPane splitpaneLeftColumn;

	@FXML
	private Spinner<Integer> spTally;

	/**
	 * TODO : comment
	 */
	@FXML
	private void tfNewCategoryEnter() {
		btNewCategoryClick();
	}

	/**
	 * function to initialize the main window and generate dynamic buttons etc.
	 */
	@FXML
	private void initialize() {
		warehouseService = new WarehouseService();
		articleService = new ArticleService();
		categoryService = new CategoryService();
		customerService = new CustomerService();
		addressService = new AddressService();
		orderService = new OrderService();
		orderItemService = new OrderItemService();
		tpButtons.setPrefTileWidth(TILE_SIZE);
		tpButtons.setPrefTileHeight(TILE_SIZE);
		tpButtons.setPadding(new Insets(5.0));
		tpButtons.setHgap(5.0);
		tpButtons.setVgap(5.0);
		initializeSelectionModel();
		initializeCategories();
		initializeComboBoxCustomers();
		spWarehouse.setVisible(true);
		currentWarehouseId = -1;
		refreshAll();
		cbArticleCategory.setValue("Categories");
		cbCategories.setValue("Categories");
		initializeToggleGroup();
	}

	@FXML
	private RadioButton rbIncoming;
	@FXML
	private RadioButton rbCommission;
	@FXML
	private RadioButton rbOutgoing;

	/**
	 * function for the radiobuttons listener for incoming, comission and outgoing
	 */
	private void initializeToggleGroup() {
		orderGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				RadioButton currenRadioButton = (RadioButton) newValue;
				String rbText = currenRadioButton.getText();
				switch (rbText){
					case "Incoming":
						currentOrderStatus = Order.OrderStatus.incoming;
						break;
					case "Commission":
						currentOrderStatus = Order.OrderStatus.commission;
						break;
					case "Outgoing":
						currentOrderStatus = Order.OrderStatus.outgoing;
						break;
				}
				currentOrderId = -1;
				clearRightColumnOrder();
				selectionModel.select(tabOrder);
				refreshOrderButtons();
			}
			
		});
	}



	/**
	 * TODO : comment
	 */
	private void initializeComboBoxCustomers(){
        List<Customer>customers = customerService.getAll();
        if (customers != null){
            customers.sort(new Comparator<Customer>() {
                @Override
                public int compare(Customer o1, Customer o2) {
                    return Customer.compare(o1, o2);
                }
            });
            cbOrderCustomer.getItems().addAll(customers);
        }
    }
	
	/**
	 *  TODO : comment
	 */
	private void initializeCategories() {
		if (categoryService.findByName("none") == null) {
			categoryService.create(new Category("none"));
		}
		if (categoryService.findByName("All") == null) {
			categoryService.create(new Category("All"));
		}
	}
	
	/**
	 * TODO : comment
	 */
	private void initializeSelectionModel() {
		selectionModel = tabPane.getSelectionModel();
		selectionModel.selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int newValueInt = (int) newValue;
				switch (newValueInt) {
					case ARTICLE_TAB_INDEX:
						tabArticleSelected();
						break;
					case CUSTOMER_TAB_INDEX:
						tabCustomerSelected();
						break;
					case ORDER_TAB_INDEX:
						tabOrderSelected();
						break;
				}
			}
		});
	}

	/**
	 * functions when "Add Warehouse" button gets clicked
	 */
	@FXML
	protected void btAddWarehouseClick() {
		openNameWindow(NEW);
		refreshWarehouseButtons();
		lbErrors.setText("");
	}

	/**
	 * functions when tab "order" in tabpane gets clicked
	 */
	private void tabOrderSelected() {
		orderGroup.selectToggle(rbIncoming);
		refreshOrderButtons();
		currentWarehouseId = -1;
		currentArticleId = -1;
		clearRightColumnArticle();
		refreshWarehouseButtons();
		tabArticle.setDisable(true);
		btUpdateWarehouse.setDisable(true);
		btDeleteWarehouse.setDisable(true);
	}

	/**
	 * functions when tab "article" in tabpane gets clicked
	 */
	private void tabArticleSelected() {
		
		refreshArticleButtons();
		

	}

	/**
	 * functions when tab "customer" in tabpane gets clicked
	 */
	private void tabCustomerSelected() {
		currentWarehouseId = -1;
		currentArticleId = -1;
		clearRightColumnArticle();
		refreshWarehouseButtons();
		refreshCustomerButtons();
		tabArticle.setDisable(true);
		btUpdateWarehouse.setDisable(true);
		btDeleteWarehouse.setDisable(true);
	}
	
	/**
	 * functions when "delete warehouse" button gets clicked
	 */
	@FXML
	private void btDeleteWarehouseClick() {
		int before = warehouseService.getAll().size();
		openWarehouseDeleteWindow();
		int after = warehouseService.getAll().size();
		if (before > after) {
			refreshWarehouseButtons();
			currentWarehouseId = -1;
			refreshArticleButtons();
		}
		lbErrors.setText("");
	}

	@FXML
	private Button btUpdateWarehouse;

	/**
	 * functions when "update warehouse" button gets clicked
	 */
	@FXML
	private void btUpdateWarehouseClick() {
		openNameWindow(UPDATE);
		refreshWarehouseButtons();
		lbErrors.setText("");
	}

	/**
	 * functions with switch case when "update" button in right collumn gets clicked
	 */
	@FXML
	private void btUpdateClick() {
		lbErrors.setText("");
		switch (selectionModel.getSelectedIndex()) {
			case ARTICLE_TAB_INDEX:
				updateArticle();
				break;
			case CUSTOMER_TAB_INDEX:
				updateCustomer();
				break;
			case ORDER_TAB_INDEX:
				updateOrder();
				break;
		}

	}

	/**
	 * function to update an article object
	 */
	private void updateArticle() {
		Article toUpdate = articleService.getOne(currentArticleId);
		toUpdate.setArticleName(tfArticleName.getText());
		try {
			toUpdate.setPrice(Double.parseDouble(tfArticlePrice.getText()));
		} catch (NumberFormatException e) {
			lbErrors.setText("Error: Price must be a number.");
			return;
		}
		try {
			toUpdate.setStock(Integer.parseInt(tfArticleStock.getText()));
		} catch (NumberFormatException e) {
			lbErrors.setText("Error: Stock must be a number.");
			return;
		}
		toUpdate.setCategory(categoryService.findByName(cbArticleCategory.getValue()));
		toUpdate.setDescription(tfArticleDescription.getText());
		articleService.update(toUpdate);
		refreshArticleButtons();
	}

	/**
	 * function to update a customer object
	 */
	private void updateCustomer() {
		if (updateAddress()) {
			Customer toUpdate = customerService.getOne(currentCustomerId);
			toUpdate.setCompanyName(tfCustomerCompanyName.getText());
			toUpdate.setEmail(tfCustomerEMail.getText());
			toUpdate.setFirstName(tfCustomerFirstName.getText());
			toUpdate.setLastName(tfCustomerLastName.getText());
			toUpdate.setPhoneNumber(tfCustomerPhoneNumber.getText());
			customerService.update(toUpdate);
			refreshCustomerButtons();
		}
	}

	/**
	 * function to catch userillegalinputs for the customer update button
	 * 
	 * @return if there was an error in userinput
	 */
	private boolean updateAddress() {
		String customerAddress = tfCustomerAddress.getText();
		Address toUpdate = addressService.findByCustomer(
			currentCustomerId).get(0);
		if (toUpdate == null){
			lbErrors.setText("Error: Address not found.");
			throw new IllegalArgumentException();
		}
		String[] splitAddress = customerAddress.split("\n");
		if (splitAddress.length == 4) {
			boolean isEmpty = false;
			for (int i = 0; i < 3; i++) {
				if (splitAddress[i].trim().isEmpty()) {
					isEmpty = true;
				}
			}

			if (!isEmpty) {
				toUpdate.setStreet(splitAddress[0]);
				toUpdate.setPostalCode(splitAddress[1]);
				toUpdate.setCity(splitAddress[2]);
				toUpdate.setCountry(splitAddress[3]);
				addressService.update(toUpdate);
				return true;
			} else {
				lbErrors.setText("Error: There was an empty line in Address.");
				return false;
			}

		} else {
			lbErrors.setText("Error: There was a formatting error in Address.");
			return false;
		}

	}

	/**
	 * function to update an order object. reads all items in order, throws errormessage if item 
	 * duplicates, updates right collumn
	 */
	private void updateOrder() {

		Order order = orderService.getOne(currentOrderId);
		switch (order.getStatus()){
			case outgoing:
				lbErrors.setText("Error: Orders in Outgoing can't be updated.");
				return;
			case commission:
				lbErrors.setText("Error: Orders in Commission can't be updated.");
				return;

			case incoming:
			default:
				break;
		}
		Order currentOrder = orderService.getOne(currentOrderId);
		int customer = cbOrderCustomer.getValue().getId();
        Address address = addressService.findByCustomer(customer).get(0);
		currentOrder.setAddress(address);
        int len = vBoxOrderPositions.getChildren().size();
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        List<Article> articleList = new ArrayList<Article>();
		orderItemList = orderItemService.findByOrder(currentOrderId);
        for (int i = 0; i < len; i++){            
            HBox currentPosition = (HBox) vBoxOrderPositions.getChildren().get(i);
            ComboBox<Article> currentBox =
                (ComboBox<Article>) currentPosition.getChildren().get(0);
            Article currentArticle = currentBox.getValue();
            Spinner<Integer> tally =
                (Spinner<Integer>) currentPosition.getChildren().get(1); 
            double price = currentArticle.getPrice();
            int quantity = tally.getValue();
            articleList.add(currentArticle);
			OrderItem currentOrderItem = orderItemList.get(i);
			currentOrderItem.setQuantity(tally.getValue());
			currentOrderItem.setArticle(currentBox.getValue());
        }
        Set<Article> articleSet = new HashSet<Article>(articleList);
        if (articleSet.size()==articleList.size()){
            	orderService.update(currentOrder);
				for (int i = 0; i < orderItemList.size(); i++){
                orderItemService.update(orderItemList.get(i));
            }

        } else {
            lbErrors.setText("Error: No duplicate articles allowed.");
        }

		refreshOrderButtons();
	}

	/**
	 * generates new category from texfield underneath
	 */
	@FXML
	private void btNewCategoryClick() {
		String newCategory = tfNewCategory.getText()/* .trim() */;
		try {
			categoryService.create(new Category(newCategory));
		} catch (Exception e) {
		}
		tfNewCategory.setText("");
		refreshCategoryBoxes();
		lbErrors.setText("");
	}

	/**
	 * refreshes the category combobox and drops duplicates
	 */
	private void refreshCategoryBoxes() {
		List<Category> categories = categoryService.getAll();
		if (categories != null) {
			int len = categories.size();
			List<String> categoryNames = new ArrayList<String>();
			for (int i = 0; i < len; i++) {
				String newCategory = categories.get(i).getName();
				categoryNames.add(newCategory);
			}
			Collections.sort(categoryNames);
			len = categoryNames.size();
			String articleCategory = cbArticleCategory.getValue();
			String globalCategory = cbCategories.getValue();
			cbCategories.getItems().clear();
			cbArticleCategory.getItems().clear();
			for (int i = 0; i < len; i++) {
				String newCategory = categoryNames.get(i);
				cbCategories.getItems().add(newCategory);
				if (newCategory.equals("All")) {
					continue;
				}
				cbArticleCategory.getItems().add(newCategory);
			}
			cbArticleCategory.setValue(articleCategory);
			cbCategories.setValue(globalCategory);
		}
	}

	/**
	 * function when "new article" button gets clicked. opens "new article" window 
	 */
	@FXML
	private void btAddArticleClick() {
		openNewArticleWindow();
		refreshArticleButtons();
		lbErrors.setText("");
	}

	/**
	 * function to open the "new article" window 
	 */
	private void openNewArticleWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("create-article-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 300, 440);
			Stage stage = new Stage();
			stage.setUserData(warehouseService.getOne(currentWarehouseId));
			stage.setTitle("Create Article");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to refresh warehouse buttons
	 */
	private void refreshWarehouseButtons() {
		List<Warehouse> allWarehouses = warehouseService.getAll();
		if (allWarehouses != null) {
			int warehouseListSize = allWarehouses.size();
			int buttonListSize = hBoxWarehouseInSP.getChildren().size();

			if (warehouseListSize == buttonListSize) {
				newWarehouseButton(allWarehouses.get(buttonListSize - 1));
			} else if (warehouseListSize + 1 == buttonListSize) {
				for (int i = 0; i < warehouseListSize; i++) {
					Button currentButton = (Button) hBoxWarehouseInSP.getChildren().get(i + 1);
					if (currentButton.isDisabled() == true && currentWarehouseId == -1) {
						currentButton.setDisable(false);
					}
					currentButton.setText(allWarehouses.get(i).getName());
				}
			} else {
				Node plusButton = hBoxWarehouseInSP.getChildren().get(0);
				hBoxWarehouseInSP.getChildren().clear();
				hBoxWarehouseInSP.getChildren().add(plusButton);
				for (int i = 0; i < warehouseListSize; i++) {
					newWarehouseButton(allWarehouses.get(i));
				}
			}
		}
	}

	/**
	 * generates a warehouse button
	 * 
	 * @param currentWarehouse a warehouse object that is attached to the button
	 */
	private void newWarehouseButton(Warehouse currentWarehouse) {
		Button tempWarehouse = new Button(currentWarehouse.getName());
		tempWarehouse.setWrapText(true);
		tempWarehouse.setMaxHeight(WAREHOUSE_SIZE);
		tempWarehouse.setMinHeight(WAREHOUSE_SIZE);
		tempWarehouse.setMaxWidth(WAREHOUSE_SIZE);
		tempWarehouse.setMinWidth(WAREHOUSE_SIZE);
		tempWarehouse.setPadding(new Insets(5.0));
		tempWarehouse.setTextAlignment(TextAlignment.CENTER);
		tempWarehouse.setUserData(currentWarehouse);
		tempWarehouse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {

				/*
				 * FIXED: weirde Errors bei der Datenübergabe
				 * For-Schleife darf erst bei 1 anfangen, da der
				 * Plusknopf an Index 0 liegt und da keine UserData drin
				 * gespeichert ist
				 */

				currentWarehouseId = currentWarehouse.getId();

				if (currentWarehouseId != -1) {
					btUpdateWarehouse.setDisable(false);
					btDeleteWarehouse.setDisable(false);
				}
				clearRightColumnCustomer();
				currentCustomerId = -1;
				currentOrderId = -1;
				clearRightColumnOrder();
				tabArticle.setDisable(false);
				tabOrder.setDisable(false);
				tabCustomer.setDisable(false);
				selectionModel.select(tabArticle);
				refreshArticleButtons();

				ObservableList<Node> buttonList = hBoxWarehouseInSP.getChildren();

				for (int i = 1; i < buttonList.size(); i++) {
					Object userData = buttonList.get(i).getUserData();
					Warehouse buttonWarehouse = (Warehouse) userData;
					if (buttonWarehouse.getId() == currentWarehouseId) {
						buttonList.get(i).setDisable(true);
					} else {
						buttonList.get(i).setDisable(false);
					}
				}
				lbErrors.setText("");
			}

		});
		hBoxWarehouseInSP.getChildren().add(tempWarehouse);
	}

	/**
	 * refreshes the article objects in the middle. changes in the DB changes also in the middle
	 */
	private void refreshArticleButtons() {
		tpButtons.getChildren().clear();
		List<Article> articles = articleService.getAll();
		if (currentWarehouseId != -1) {
			Category currentCategory = categoryService.findByName(
				cbCategories.getValue());
			if (currentCategory == null || currentCategory.getName().equals("All")){
				articles.removeIf(
					article -> article.getWarehouse().getId() != currentWarehouseId);
			} else {
				articles = articleService.findByWarehouseAndCategory(
					currentWarehouseId,
					currentCategory.getId());
			}
			newArticlePlusButton();
			if (articles != null) {
				articles.sort(new Comparator<Article>() {
					public int compare(Article o1, Article o2) {
						return Article.compare(o1, o2);
					}
				});
				int len = articles.size();

				for (int i = 0; i < len; i++) {
					newArticleButton(articles.get(i));
				}
			}
		} else {
			btUpdateWarehouse.setDisable(true);
			btDeleteWarehouse.setDisable(true);
			btUpdate.setDisable(true);
			btDelete.setDisable(true);
		}
	}

	/** 
	 * function to refresh articles after category got picked in combobox
	 */
	@FXML
	private void cbCategoriesChange() {
		refreshArticleButtons();
	}

	/**
	 * generates new article object for middle tilepane after the information has been gathered in
	 * the new article window or if the article buttons are refreshed
	 * 
	 * @param currentButtonArticle an article object that is attached to the button 
	 */
	private void newArticleButton(Article currentButtonArticle) {
		Button tempArticle = newTileButton(currentButtonArticle.getArticleName());
		tempArticle.setUserData(currentButtonArticle.getId());
		if (currentButtonArticle.getId() == currentArticleId) {
			tempArticle.setDisable(true);
		}
		tempArticle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				btUpdateWarehouse.setDisable(false);
				btDeleteWarehouse.setDisable(false);
				btUpdate.setDisable(false);
				btDelete.setDisable(false);
				currentArticleId = currentButtonArticle.getId();
				Article updatedCurrentArticle = articleService.getOne(currentArticleId);
				tfArticleName.setText(updatedCurrentArticle.getArticleName());
				tfArticleId.setText(Integer.toString(updatedCurrentArticle.getId()));
				tfArticlePrice.setText(Double.toString(updatedCurrentArticle.getPrice()));
				tfArticleStock.setText(Integer.toString(updatedCurrentArticle.getStock()));
				setDisableRightColumn(false);
				String updatedCategory = "";
				if (updatedCurrentArticle.getCategory() != null) {
					updatedCategory = updatedCurrentArticle.getCategory().getName();
				}
				cbArticleCategory.setValue(updatedCategory);
				tfArticleDescription.setText(updatedCurrentArticle.getDescription());
				refreshArticleButtons();
				lbErrors.setText("");
			}

		});
		tpButtons.getChildren().add(tempArticle);
	}

	/**
	 * functions of the "+" button for article
	 */
	private void newArticlePlusButton() {
		Button plusButton = newTileButton("+");
		plusButton.setFont(new Font(FONT_SIZE_PLUS));
		plusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				btAddArticleClick();
				lbErrors.setText("");
			}

		});
		tpButtons.getChildren().add(plusButton);
	}

	/**
	 * function to refresh all middle tilepanes (articles, cbCategory, customer and warehouses)
	 */
	private void refreshAll() {
		refreshArticleButtons();
		refreshCategoryBoxes();
		refreshCustomerButtons();
		refreshWarehouseButtons();
	}

	/**
	 * opens rename warehouse or create warehouse window
	 * 
	 * @param mode to decide if rename or create
	 */
	private void openNameWindow(int mode) {
		try {
			// Deklaration
			String resourcePath = "";
			String windowTitle = "";
			// String presetText = "";
			// Belegung Stringvars
			switch (mode) {
				case NEW:
					resourcePath = "name-warehouse.fxml";
					windowTitle = "Create new warehouse";
					break;

				case UPDATE:
					resourcePath = "rename-warehouse.fxml";
					windowTitle = "Rename current warehouse";
					break;

				default:
					throw new IllegalArgumentException();
			}
			FXMLLoader fxmlLoader = new FXMLLoader(IndexApplication.class.getResource(resourcePath));
			Scene scene = new Scene(fxmlLoader.load(), 300, 100);
			Stage stage = new Stage();
			stage.setTitle(windowTitle);
			stage.setScene(scene);
			// NameController controller = fxmlLoader.getController();
			INameController controller = fxmlLoader.getController();

			stage.addEventHandler(WindowEvent.WINDOW_SHOWING,
					new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent window) {
							controller.initializeTextField(getPresetText(mode));
						}

					});
			Warehouse currentWarehouse = warehouseService.getOne(currentWarehouseId);
			stage.setUserData(currentWarehouse);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * generates suggestion to name or rename a warehouse
	 * 
	 * @param mode to decide if rename or create
	 * @return warehousenumber
	 */
	private String getPresetText(int mode) {
		if (mode == NEW) {
			List<Warehouse> warehouses = warehouseService.getAll();
			int warehouseNr = 1;
			if (warehouses != null) {
				warehouseNr = warehouses.size() + 1;
			}
			return Integer.toString(warehouseNr);
		}
		if (mode == UPDATE) {
			return warehouseService.getOne(currentWarehouseId).getName();
		}
		throw new IllegalArgumentException();
	}

	/**
	 * function when "delete" button gets clicked in right collumn. decides by itself what
	 * gets deleted
	 */
	@FXML
	private void btDeleteClick() {
		lbErrors.setText("");
		switch (selectionModel.getSelectedIndex()) {
			case ARTICLE_TAB_INDEX:
				deleteArticle();
				break;
			case CUSTOMER_TAB_INDEX:
				deleteCustomer();
				break;
			case ORDER_TAB_INDEX:
				deleteOrder();
				break;
		}
	}

	/**
	 * function to delete article
	 */
	private void deleteArticle() {
		lbErrors.setText("");
		int before = articleService.getAll().size();
		openArticleDeleteWindow();
		int after = articleService.getAll().size();
		if (before > after) {
			refreshArticleButtons();
			clearRightColumnArticle();
		}

	}

	/**
	 * function to delete customer
	 */
	private void deleteCustomer() {
		lbErrors.setText("");
		int before = customerService.getAll().size();
		openCustomerDeleteWindow();
		int after = customerService.getAll().size();
		if (before > after) {
			refreshCustomerButtons();
			clearRightColumnCustomer();
		}
	}

	/**
	 * function to delete order
	 */
	private void deleteOrder() {
		lbErrors.setText("");
		Order order = orderService.getOne(currentOrderId);
		switch (order.getStatus()){
			case outgoing:
				lbErrors.setText("Error: Orders in Outgoing can't be deleted.");
				return;
			case commission:
				lbErrors.setText("Error: Orders in Commission can't be updated.");
				return;

			case incoming:
			default:
				break;
		}
		int before = orderService.getAll().size();
		openOrderDeleteWindow();
		int after = orderService.getAll().size();
		if (before > after) {
			refreshOrderButtons();
			clearRightColumnOrder();
		}
	}

	/**
	 * TODO : comment
	 */
	private void refreshOrderButtons() {
		List<Order> orders = orderService.getAll();
		orders.removeIf(order -> order.getStatus() != currentOrderStatus);
		tpButtons.getChildren().clear();
		newOrderPlusButton();
			if (orders != null) {
				orders.sort(new Comparator<Order>() {
					@Override
					public int compare(Order o1, Order o2) {
						return Order.compare(o1, o2);
					}
				});
				int len = orders.size();
				for (int i = 0; i < len; i++) {
					newOrderButton(orders.get(i));
				}
			}
	}

	/**
	 * genrates new button in middle tilepane when orders are refreshed 
	 * 
	 * @param order an order object that is attached to the button
	 */
	private void newOrderButton(Order order) {
		Customer customer = order.getCustomer();
		Button tempOrder = newTileButton(
				customer.getLastName() + ", " + customer.getFirstName() + "\n#" + order.getId());
		tempOrder.setUserData(order);
		if (order.getId() == currentOrderId) {
			tempOrder.setDisable(true);
		}
		tempOrder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				btUpdate.setDisable(false);
				btDelete.setDisable(false);
				vBoxOrderPositions.getChildren().clear();
				currentOrderId = order.getId();
				switch (order.getStatus()){
					case incoming:
						btSendOrderNext.setVisible(true);
						btSendOrderNext.setText("Send to commission");
						break;
					case commission:
						btSendOrderNext.setVisible(true);
						btSendOrderNext.setText("Send to outgoing");
						break;
					case outgoing:
						btSendOrderNext.setVisible(false);
						default:
						break;
				}


				// btAddOrderPosition.setDisable(false);
				btSendOrderNext.setDisable(false);
				cbOrderCustomer.setValue(order.getCustomer());
				List<OrderItem> orderItems = orderItemService.findByOrder(
					currentOrderId);
				orderItems.sort(new Comparator<OrderItem>() {
					@Override
					public int compare(OrderItem o1, OrderItem o2) {
						return o1.getArticle().getArticleName().compareTo(
							o2.getArticle().getArticleName());
					}
					
				});
				for (int i = 0; i < orderItems.size(); i++){
					newOrderPosition(orderItems.get(i));
				}
				refreshFullPrice();
				btUpdate.setDisable(false);
				btDelete.setDisable(false);
				refreshOrderButtons();
			}
		});

		tpButtons.getChildren().add(tempOrder);
	}

	/**
	 * generates new button after order got created
	 */
	private void newOrderPlusButton() {
		Button plusButton = newTileButton("+");
		plusButton.setFont(new Font(FONT_SIZE_PLUS));
		if (articleService.getAll() == null || customerService.getAll() == null) {
			plusButton.setDisable(true);
		}
		plusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbErrors.setText("");
				openOrderCreateWindow();
			}

		});
		tpButtons.getChildren().add(plusButton);
	}

	/**
	 * opens the "order create" window
	 */
	protected void openOrderCreateWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("create-order-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 400, 400);
			Stage stage = new Stage();
			stage.setTitle("Create new order");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			refreshOrderButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * opens window to confirm that an article shall get deleted
	 */
	private void openArticleDeleteWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("delete-article-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 350, 80);
			Stage stage = new Stage();
			stage.setTitle("Delete " + tfArticleName.getText() + "?");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setUserData(currentArticleId);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * opens window to confirm that an customer shall get deleted
	 */
	private void openCustomerDeleteWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("delete-customer-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 350, 80);
			Stage stage = new Stage();
			stage.setTitle("Delete " + tfCustomerFirstName.getText() + " " + tfCustomerLastName.getText() + " ("
					+ tfCustomerCompanyName.getText() + ")?");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setUserData(currentCustomerId);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * opens window to confirm that an order shall get deleted
	 */
	private void openOrderDeleteWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("delete-order-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 350, 80);
			Stage stage = new Stage();
			stage.setTitle("Delete Order "+orderService.getOne(currentOrderId)+"?");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setUserData(currentOrderId);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private ToggleGroup orderGroup;
	
	/**
	 * clears textfields etc. in right column for articles
	 */
	private void clearRightColumnArticle() {
		tfArticleName.setText("");
		tfArticleId.setText("");
		tfArticlePrice.setText("");
		tfArticleStock.setText("");
		cbArticleCategory.setValue("none");
		tfArticleDescription.setText("");
		btDelete.setDisable(true);
		btUpdate.setDisable(true);
		setDisableRightColumn(true);
	}

	/**
	 * clears textfields etc. in right column for customer
	 */
	private void clearRightColumnCustomer() {
		tfCustomerId.setText("");
		tfCustomerFirstName.setText("");
		tfCustomerLastName.setText("");
		tfCustomerCompanyName.setText("");
		tfCustomerEMail.setText("");
		tfCustomerPhoneNumber.setText("");
		tfCustomerAddress.setText("");
		btDelete.setDisable(true);
		btUpdate.setDisable(true);
	}

	/**
	 * clears textfields etc. in right column for orders
	 */
	private void clearRightColumnOrder() {
		vBoxOrderPositions.getChildren().clear();
		cbOrderCustomer.setValue(null);
		lbOrderFullPrice.setText("");
		// btAddOrderPosition.setDisable(true);
		btSendOrderNext.setDisable(true);
	}

	/**
	 * disables or enables textfields in tab article
	 * @param value what textfield shall dis- or enabled
	 */
	private void setDisableRightColumn(boolean value) {
		tfArticleName.setDisable(value);
		tfArticlePrice.setDisable(value);
		tfArticleStock.setDisable(value);
		cbArticleCategory.setDisable(value);
		tfArticleDescription.setDisable(value);
	}

	/**
	 * opens window to confirm that a warehouse shall get deleted
	 */
	private void openWarehouseDeleteWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("delete-warehouse-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 350, 80);
			Stage stage = new Stage();
			stage.setUserData(warehouseService.getOne(currentWarehouseId));
			stage.setTitle("Delete " + warehouseService.getOne(currentWarehouseId).getName() + "?");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * refreshes customer buttons in middle tilepane
	 */
	private void refreshCustomerButtons() {
		if (currentWarehouseId == -1) {
			List<Customer> customers = customerService.getAll();
			customers.sort(new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {
					return Customer.compare(o1, o2);
				}

			});
			tpButtons.getChildren().clear();
			newCustomerPlusButton();
			if (customers != null) {
				int len = customers.size();
				for (int i = 0; i < len; i++) {
					newCustomerButton(customers.get(i));
				}
			}
		}
	}

	/**
	 * generates the "add" button in middle tilepane for customers
	 */
	private void newCustomerPlusButton() {
		Button plusButton = newTileButton("+");
		plusButton.setFont(new Font(FONT_SIZE_PLUS));
		plusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				lbErrors.setText("");
				openCustomerCreateWindow();

			}
		});
		tpButtons.getChildren().add(plusButton);
	}

	/**
	 * generic add button function, add order, customer, article are using this
	 * 
	 * @param text text that is written on the button
	 * @return button with text
	 */
	private Button newTileButton(String text) {
		Button result = new Button(text);
		result.setWrapText(true);
		result.setMaxHeight(TILE_SIZE);
		result.setMinHeight(TILE_SIZE);
		result.setMaxWidth(TILE_SIZE);
		result.setMinWidth(TILE_SIZE);
		result.setPadding(new Insets(5.0));
		result.setTextAlignment(TextAlignment.CENTER);
		return result;
	}

	/**
	 * generate new customer button
	 * 
	 * @param currentButtonCustomer 
	 */
	private void newCustomerButton(Customer currentButtonCustomer) {
		Button tempCustomer = newTileButton(currentButtonCustomer.getLastName() + ", "
				+ currentButtonCustomer.getFirstName() + " (" + currentButtonCustomer.getCompanyName() + ")");
		tempCustomer.setUserData(currentButtonCustomer.getId());
		if (currentButtonCustomer.getId() == currentCustomerId) {
			tempCustomer.setDisable(true);
		}
		tempCustomer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				btUpdateWarehouse.setDisable(true);
				btDeleteWarehouse.setDisable(true);
				btUpdate.setDisable(false);
				btDelete.setDisable(false);
				currentCustomerId = currentButtonCustomer.getId();
				Customer customer = customerService.getOne(currentCustomerId);
				tabCustomer.setDisable(false);
				selectionModel.select(tabCustomer);
				setCustomerFields(customer);
				refreshCustomerButtons();
				lbErrors.setText("");
			}

		});
		tpButtons.getChildren().add(tempCustomer);
	}
	
	/**
	 * sets textfields etc in the right column in customertab
	 * 
	 * @param customer gives all information for textfields
	 */
	private void setCustomerFields(Customer customer) {
		tfCustomerId.setText(Integer.toString(customer.getId()));
		tfCustomerFirstName.setText(customer.getFirstName());
		tfCustomerLastName.setText(customer.getLastName());
		tfCustomerCompanyName.setText(customer.getCompanyName());
		tfCustomerEMail.setText(customer.getEmail());
		tfCustomerPhoneNumber.setText(customer.getPhoneNumber());
		Address address = addressService.findByCustomer(customer.getId()).get(0);
		if (address != null) {
			tfCustomerAddress.setText(address.toStringFancy());
		}
	}

	/**
	 * opens "customer create" window and generates new customer in DB
	 */
	private void openCustomerCreateWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					IndexApplication.class.getResource("create-customer-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 250, 400);
			Stage stage = new Stage();
			stage.setUserData(warehouseService.getOne(currentWarehouseId));
			stage.setTitle("Create new customer");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			refreshCustomerButtons();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private Button btAddOrderPosition;

	@FXML
	private Label lbOrderFullPrice;

	@FXML
	private ComboBox<Customer> cbOrderCustomer;

	@FXML
	private void cbNewOrderCustomerChange(){

	}

	/**
	 * delete single article from order
	 * 
	 * @param hBox what article shall get deleted
	 */
	@FXML
	private void lbDeleteOrderPositionClick(HBox hBox) {
		vBoxOrderPositions.getChildren().remove(hBox);
        if (vBoxOrderPositions.getChildren().size()==0) {
            btUpdate.setDisable(true);
        }
        refreshFullPrice();

	};

	/**
	 * calculates full price from oder and refreshes on every quantity spinner and cbArticle 
	 * change and new article to order
	 */
	private void refreshFullPrice() {
		double total = 0.0;
		
        if (vBoxOrderPositions != null){
            int len = vBoxOrderPositions.getChildren().size();
            for (int i = 0; i < len; i++){
                HBox currentPosition = (HBox) vBoxOrderPositions.getChildren().get(i);
                ComboBox<Article> currentBox =
                    (ComboBox<Article>) currentPosition.getChildren().get(0);
                Article currentArticle = currentBox.getValue();
                Spinner<Integer> tally =
                    (Spinner<Integer>) currentPosition.getChildren().get(1); 
                total += currentArticle.getPrice() * tally.getValue();
            }
        }
		total *= 100;
		total = Math.round(total);
		total /= 100.0;
        lbOrderFullPrice.setText(String.format("%.2f", total)+"€");
	}

	@FXML
	private Button btSendOrderNext;

	/**
	 * changes status from an order from incoming to comission to outgoing
	 */
	@FXML
	private void btSendOrderNextClick(){
		Order currentOrder = orderService.getOne(currentOrderId);
		switch (currentOrder.getStatus()){
			case incoming:
				currentOrder.setStatus(Order.OrderStatus.commission);
				decreaseStock(currentOrder);
				break;
			case commission:
				currentOrder.setStatus(Order.OrderStatus.outgoing);
				break;
			case outgoing:
				break;
			default:
				break;
		}
		currentOrderId = -1;
		clearRightColumnOrder();
		orderService.update(currentOrder);
		refreshOrderButtons();
	}


	/**
	 * decreases the stock of an article. Is called when an order goes into commission
	 * @param order The instance of order on which the stock is decreased
	 */
	private void decreaseStock(Order order) {
		List<OrderItem> orderItemList=orderItemService.findByOrder(order.getId());
		int len = orderItemList.size();
		for (int i = 0; i < len; i++){
			OrderItem currentOrderItem = orderItemList.get(i);
			int stock = currentOrderItem.getArticle().getStock();
			if (currentOrderItem.getQuantity() > stock){
				currentOrderItem.getArticle().setStock(0);
			} else {
				currentOrderItem.getArticle().setStock(stock-currentOrderItem.getQuantity());
			}
			articleService.update(currentOrderItem.getArticle());
		}

	}
	/**
	* adds an order position in the UI and Implements a change listener for the new combo box
	* @param currentOrderItem OrderItem object
	*/	
	private void newOrderPosition(OrderItem currentOrderItem) {
		Article currentArticle = currentOrderItem.getArticle();
		HBox hBoxOrderPosition = new HBox();
		hBoxOrderPosition.setSpacing(5.0);
		ObservableList<Article> articles = FXCollections.observableArrayList();
		articles.addAll(articleService.getAll());
		ComboBox<Article> articleBox = new ComboBox<Article>(articles);
		articleBox.getEditor().setFont(new Font(10));
		hBoxOrderPosition.getChildren().add(articleBox);

		hBoxOrderPosition.getChildren().clear();

		hBoxOrderPosition.getChildren().add(articleBox);

		articleBox.setValue(currentArticle);
		int stock = currentArticle.getStock();
		
		Spinner<Integer> spNewTally = new Spinner<Integer>();

		Label piecePrice = new Label(String.format(
			"%.2f", currentArticle.getPrice())+"€");
		piecePrice.setMinWidth(45.0);

		if (stock <= 0){
			piecePrice.setText("Out of stock");
			spNewTally.setVisible(false);
			spNewTally.setMinWidth(0);
			spNewTally.setMaxWidth(0);
			spNewTally.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0,0));
		} else if (currentOrderItem.getQuantity()>stock){
			spNewTally.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
				1,stock, stock));
			spNewTally.setMaxWidth(70.0);
			spNewTally.setMinWidth(70.0);
		} else {
			spNewTally.setValueFactory(new SpinnerValueFactory.
			IntegerSpinnerValueFactory(1, stock, currentOrderItem.getQuantity()));
			spNewTally.setMaxWidth(70.0);
			spNewTally.setMinWidth(70.0);
		}

		
		
		
		spNewTally.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable,
			Integer oldValue, Integer newValue) {
				refreshFullPrice();
				
			}
			
		});
		spNewTally.getValueFactory().setValue(currentOrderItem.getQuantity());
		hBoxOrderPosition.getChildren().add(spNewTally);

		
		hBoxOrderPosition.getChildren().add(piecePrice);

		Label deleteLabel = new Label("x");
		deleteLabel.setCursor(Cursor.HAND);
		deleteLabel.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				lbDeleteOrderPositionClick(hBoxOrderPosition);
			}

		});
		hBoxOrderPosition.getChildren().add(deleteLabel);
		btSendOrderNext.setDisable(false);
		refreshFullPrice();

		articleBox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				hBoxOrderPosition.getChildren().clear();

				hBoxOrderPosition.getChildren().add(articleBox);
				Article currentArticle = articleBox.getValue();

				Label piecePrice = new Label(Double.toString(
					currentArticle.getPrice()));

				Spinner<Integer> spNewTally = new Spinner<Integer>();
				int stock = currentArticle.getStock();
				if (stock <= 0){
					piecePrice.setText("Out of stock");
					spNewTally.setVisible(false);
					spNewTally.setMinWidth(0);
					spNewTally.setMaxWidth(0);
					spNewTally.setValueFactory(
						new SpinnerValueFactory.IntegerSpinnerValueFactory(0,0));
				} else if (currentOrderItem.getQuantity()>stock){
					spNewTally.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						1,stock, stock));
					spNewTally.setVisible(true);
					spNewTally.setMaxWidth(70.0);
					spNewTally.setMinWidth(70.0);
				} else {
					spNewTally.setVisible(true);
					spNewTally.setValueFactory(new SpinnerValueFactory.
					IntegerSpinnerValueFactory(1, stock, currentOrderItem.getQuantity()));
					spNewTally.setMaxWidth(70.0);
					spNewTally.setMinWidth(70.0);
				}
				spNewTally.valueProperty().addListener(new ChangeListener<Integer>() {
					@Override
					public void changed(ObservableValue<? extends Integer> observable, Integer oldValue,
							Integer newValue) {
						refreshFullPrice();
					}
					
				});
				hBoxOrderPosition.getChildren().add(spNewTally);

				
				piecePrice.setMinWidth(45.0);
				hBoxOrderPosition.getChildren().add(piecePrice);

				Label deleteLabel = new Label("x");
				deleteLabel.setCursor(Cursor.HAND);
				deleteLabel.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event event) {
						lbDeleteOrderPositionClick(hBoxOrderPosition);
					}

				});
				hBoxOrderPosition.getChildren().add(deleteLabel);
				btSendOrderNext.setDisable(false);
				refreshFullPrice();
			}
		});
		
		vBoxOrderPositions.getChildren().add(0, hBoxOrderPosition);
	}
}