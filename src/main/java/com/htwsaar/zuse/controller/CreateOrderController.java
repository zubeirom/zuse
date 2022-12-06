package com.htwsaar.zuse.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.model.Article;
import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.model.Order;
import com.htwsaar.zuse.model.OrderItem;

import javafx.collections.ObservableList;

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
import com.htwsaar.zuse.service.OrderItemService;
import com.htwsaar.zuse.service.OrderService;
import com.htwsaar.zuse.util.Util;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.event.*;

public class CreateOrderController {
    @FXML
    Label lbCreateOrderErrors;
    @FXML
    VBox vBoxNewOrderPositions;
    @FXML
    private Button btConfirmOrderCreate;

    @FXML
    private void btConfirmOrderCreateClick(){
        int customer = cbNewOrderCustomer.getValue().getId();
        Address address = addressService.findByCustomer(customer).get(0);
        Order currentOrder = new Order(address);
        int len = vBoxNewOrderPositions.getChildren().size();
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        List<Article> articleList = new ArrayList<Article>();
        for (int i = 0; i < len; i++){
            // TODO: in jedem Schleifendurchlauf neues OrderItem anlegen (order, article, price, quantity)
            // TODO: in jedem Schleifendurchlauf 1x orderItemService.create() aufrufen
            
            HBox currentPosition = (HBox) vBoxNewOrderPositions.getChildren().get(i);
            ComboBox<Article> currentBox =
                (ComboBox<Article>) currentPosition.getChildren().get(0);
            Article currentArticle = currentBox.getValue();
            Spinner<Integer> tally =
                (Spinner<Integer>) currentPosition.getChildren().get(1); 
            double price = currentArticle.getPrice();
            int quantity = tally.getValue();
            articleList.add(currentArticle);
            orderItemList.add(new OrderItem(currentOrder, currentArticle, price, quantity));
        }
        Set<Article> articleSet = new HashSet<Article>(articleList);
        if (articleSet.size()==articleList.size()){
            orderService.create(currentOrder);
            for (int i = 0; i < orderItemList.size(); i++){
                orderItemService.create(orderItemList.get(i));
            }
            Stage stage = (Stage) btConfirmOrderCreate.getScene().getWindow();
            stage.close();
        } else {
            lbCreateOrderErrors.setText("Error: No duplicate articles allowed.");
        }
        

    }

    private List<Article> articles;
    private List<Customer> customers;
    @FXML
    private ComboBox<Customer> cbNewOrderCustomer;
    
    private IArticleService articleService;
    private ICustomerService customerService;
    private ICategoryService categoryService;
    private IOrderService orderService;
    private IOrderItemService orderItemService;
    private IAddressService addressService;

    @FXML
    private void initialize(){
        articleService = new ArticleService();
        customerService = new CustomerService();
        categoryService = new CategoryService();
        orderService = new OrderService();
        orderItemService = new OrderItemService();
        addressService = new AddressService();
        refreshComboBoxArticles();
        refreshFullPrice();
        initializeComboBoxCustomers();
    }

    private void refreshComboBoxArticles(){
        articles = articleService.getAll();
        if (articles != null){
            articles.sort(new Comparator <Article>(){
                public int compare(Article o1, Article o2) {
                    return Article.compare(o1,o2);
                }});
        } else {
            lbCreateOrderErrors.setText("Error: No articles found.");
        }
    }

    private void initializeComboBoxCustomers(){
        customers = customerService.getAll();
        if (customers != null){
            customers.sort(new Comparator<Customer>() {
                @Override
                public int compare(Customer o1, Customer o2) {
                    return Customer.compare(o1, o2);
                }
            });
            cbNewOrderCustomer.getItems().addAll(customers);
        }
    }

    @FXML
    private void cbNewOrderCustomerChange(){
        btNewOrderAddOrderPosition.setDisable(false);
    }

    private void newOrderPosition(){
		if (articles != null){
            ComboBox<Article> articleBox = new ComboBox<Article>();
            articleBox.getItems().addAll(articles);
            // articleBox.setEditable(true);
            HBox hBoxOrderPosition = new HBox();
            hBoxOrderPosition.setSpacing(5.0);
            hBoxOrderPosition.setAlignment(Pos.CENTER_LEFT);
            hBoxOrderPosition.getChildren().add(articleBox);
            if (cbNewOrderCustomer.getValue()==null){
                articleBox.setDisable(true);
            }
            articleBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    Article currentArticle = articleBox.getValue();
                        hBoxOrderPosition.getChildren().clear();

                        hBoxOrderPosition.getChildren().add(articleBox);

                        Spinner<Integer> spNewTally = new Spinner<Integer>();
                        int stock = currentArticle.getStock();
                        spNewTally.setValueFactory(new SpinnerValueFactory.
                            IntegerSpinnerValueFactory(1, stock, stock));
                        spNewTally.setMaxWidth(70.0);
                        spNewTally.setMinWidth(70.0);
                        spNewTally.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue,
                                    Integer newValue) {
                                refreshFullPrice();
                            }
                            
                        });
                        hBoxOrderPosition.getChildren().add(spNewTally);

                        Label piecePrice = new Label(Double.toString(
                            currentArticle.getPrice()));
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
                        btConfirmOrderCreate.setDisable(false);
                        btNewOrderAddOrderPosition.setDisable(false);
                        refreshFullPrice();

                        // vBoxNewOrderPositions.getChildren().remove(
                        //     vBoxNewOrderPositions.getChildren().size()-1);
                        // vBoxNewOrderPositions.getChildren().add(
                        //     hBoxOrderPosition);
                    // } else {
                    //     lbCreateOrderErrors.setText(
                    //         "Error: Article not found.");
                    //     articleBox.setValue(null);
                    // }
                }
            });
            vBoxNewOrderPositions.getChildren().add(0, hBoxOrderPosition);
            
        }
    }
    
    @FXML
    private Label lbNewOrderFullPrice;

    private void refreshFullPrice(){
        double total = 0.0;
        if (vBoxNewOrderPositions != null){
            int len = vBoxNewOrderPositions.getChildren().size();
            for (int i = 0; i < len; i++){
                HBox currentPosition = (HBox) vBoxNewOrderPositions.getChildren().get(i);
                ComboBox<Article> currentBox =
                    (ComboBox<Article>) currentPosition.getChildren().get(0);
                Article currentArticle = currentBox.getValue();
                Spinner<Integer> tally =
                    (Spinner<Integer>) currentPosition.getChildren().get(1); 
                total += currentArticle.getPrice() * tally.getValue();
            }
        }
        lbNewOrderFullPrice.setText(Double.toString(total));
    }

    protected void refreshAllArticleComboBoxes() {
        ObservableList<Node> orderPositions = vBoxNewOrderPositions.getChildren();
        if (orderPositions != null){
            int len = orderPositions.size();
            for (int i = 0; i < len; i++){
                HBox currentHBox = (HBox) orderPositions.get(i);
                ComboBox<Article> currentComboBox =
                    (ComboBox<Article>) currentHBox.getChildren().get(0);
                // ComboBox<Article> currentComboBox = (ComboBox<Article>)orderPositions.get(i);
                Article currentArticle = currentComboBox.getValue();
                currentComboBox.getItems().clear();
                currentComboBox.setValue(currentArticle);
                currentComboBox.getItems().addAll(articles);
            }
        }
    }

    @FXML
    private Button btNewOrderAddOrderPosition;

    @FXML
    private void btNewOrderAddOrderPositionClick(){
        newOrderPosition();
        btNewOrderAddOrderPosition.setDisable(true);
        btConfirmOrderCreate.setDisable(true);
    }

    @FXML
    private ScrollPane spNewOrderPositions;

    private void lbDeleteOrderPositionClick(HBox parentBox){
        vBoxNewOrderPositions.getChildren().remove(parentBox);
        if (vBoxNewOrderPositions.getChildren().size()==0) {
            btConfirmOrderCreate.setDisable(true);
        }
        refreshFullPrice();
    }

    @FXML
    private Button btCancelOrderCreate;
    @FXML
    private void btCancelOrderCreateClick(){
        Stage stage = (Stage) btCancelOrderCreate.getScene().getWindow();
        stage.close();
    }
}
