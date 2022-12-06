package com.htwsaar.zuse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.htwsaar.zuse.model.Category;
import com.htwsaar.zuse.service.ArticleService;
import com.htwsaar.zuse.service.CategoryService;
import com.htwsaar.zuse.model.Article;
import com.htwsaar.zuse.model.Warehouse;

public class CreateArticleController {
    @FXML
    private TextField tfNewArticleName;
	@FXML
	private TextField tfNewArticlePrice;
	@FXML
	private TextField tfNewArticleStock;
	@FXML
	private TextArea tfNewArticleDescription;

    @FXML
    private Button btConfirmArticleCreate;
    @FXML
    private Button btCancelArticleCreate;

    @FXML
    private ComboBox<String> cbNewArticleCategory;

    private CategoryService categoryService;

	@FXML
	private Label lbCreateArticleErrors;

    @FXML
    private void initialize(){
        categoryService = new CategoryService();
        List<Category> categories = categoryService.getAll();
		if (categories != null) {
			int len = categories.size();
			List<String> categoryNames = new ArrayList<String>();
			for (int i = 0; i < len; i++){
				String newCategory = categories.get(i).getName();
                if (newCategory.equals("All")){
                    continue;
                }
				categoryNames.add(newCategory);
			}
			Collections.sort(categoryNames);
			len = categoryNames.size();
			cbNewArticleCategory.getItems().clear();
			for (int i = 0; i < len; i++){
				String newCategory = categoryNames.get(i);
				cbNewArticleCategory.getItems().add(newCategory);
			}
		} else {
            categoryService.create(new Category("none"));
        }
        cbNewArticleCategory.setValue("none");
    }

    private int getCategoryId(String name){
		List<Category> categories = categoryService.getAll();
		if (categories != null){
			int len = categories.size();
			for (int i = 0; i < len; i++){
				if (categories.get(i).getName().equals(name)){
					return categories.get(i).getId();
				}
			}
		} else {
			Category none = new Category("none");
			categoryService.create(none);
			return none.getId();
		}
		return -1;
	}

    @FXML
    private void btConfirmArticleCreateClick() {
        try {
        Stage stage = (Stage) btConfirmArticleCreate.getScene().getWindow();
		Object o = stage.getUserData();
		if (o instanceof Warehouse) {
			Warehouse currentWarehouse = (Warehouse) o;
            String newArticleName = tfNewArticleName.getText().trim();

			// TODO: Eventuelle Komma im Preis zu Punkten konvertieren

			
			double newArticlePrice;
			try {
				newArticlePrice = Double.parseDouble(tfNewArticlePrice.getText().trim());
			} catch (NumberFormatException e) {
				lbCreateArticleErrors.setText("Error: Price must be a number.");
				return;
			}
			int newArticleStock;
			try {
				newArticleStock = Integer.parseInt(tfNewArticleStock.getText().trim());
			} catch (NumberFormatException e) {
				lbCreateArticleErrors.setText("Error: Stock must be a number.");
				return;
			}
			
			String newArticleDescription = tfNewArticleDescription.getText().trim();
            Article newArticle = new Article(currentWarehouse, newArticleName, newArticleDescription, newArticlePrice, newArticleStock);
            newArticle.setCategory(categoryService.getOne(getCategoryId(cbNewArticleCategory.getValue())));
            ArticleService articleService = new ArticleService();
            articleService.create(newArticle);
			lbCreateArticleErrors.setText("");
		} else {
			throw new IllegalArgumentException();
		}

        stage.close();

		} catch (Exception e) {
			//TODO: handle exception
		}

    }

    @FXML
    private void btCancelArticleCreateClick() {
        Stage stage = (Stage) btConfirmArticleCreate.getScene().getWindow();
        stage.close();       
    }
}
