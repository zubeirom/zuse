package com.htwsaar.zuse;


import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class IndexApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(IndexApplication.class.getResource("index-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
//        scene.getStylesheets().add("/zuse/src/main/resources/com/htwsaar/zuse/modena-dark.css");
//        stage.setMinHeight(750);
//        stage.setMinWidth(750);
//        stage.setTitle("Zuse Warehouse Manager");
//        stage.setScene(scene);
//        stage.show();
        Customer customer = new Customer("BPG", "Zubeir", "Mohamed", "myemail@mail.com", "dcsdfvdfvfbfb");
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(customer);
        entityTransaction.commit();
        entityManager.close();
    }

    public static void main(String[] args) {launch();}
}