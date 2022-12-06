package com.htwsaar.zuse.controller;

import com.htwsaar.zuse.service.ArticleService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteArticleController {
    @FXML
    private Button btConfirmArticleDelete;
    
    @FXML
    private Button btCancelArticleDelete;

    @FXML
    private void btConfirmArticleDeleteClick(){
        Stage stage = (Stage) btConfirmArticleDelete.getScene().getWindow();
        
        Object o = stage.getUserData();
        int currentArticleId = (int) o;
        ArticleService articleService = new ArticleService();
        articleService.delete(currentArticleId);

        stage.close();
    }

    @FXML
    private void btCancelArticleDeleteClick(){
        Stage stage = (Stage) btCancelArticleDelete.getScene().getWindow();
        stage.close();
    }
}
