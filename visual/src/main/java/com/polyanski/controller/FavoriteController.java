package com.polyanski.controller;

import com.polyanski.StageLoader;
import com.polyanski.common.dao.api.entities.DishEntity;
import com.polyanski.common.dao.api.entities.FavoriteDishEntity;
import com.polyanski.common.dao.api.entities.IngredientEntity;
import com.polyanski.common.dao.impl.services.DishService;
import com.polyanski.common.dao.impl.services.FavoriteDishService;
import com.polyanski.common.dao.impl.services.HistoryService;
import com.polyanski.elements.DishHBox;
import com.polyanski.search.service.api.SearchService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: vadym_polyanski
 * Date: 09.04.17
 * Time: 20:56
 */
@Component
public class FavoriteController extends WindowController {

    @Autowired
    private SearchService dishSerchService;

    @Autowired
    private FavoriteDishService favoriteDishService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private DishService dishService;

    @FXML
    private VBox dishPanel;

    public void closeButtonClick() {
        StageLoader.showWindow("smartCook.fxml", "SmartCook");
    }

    @FXML
    public void initialize() {
        dishPanel.getChildren().clear();
        List<FavoriteDishEntity> favoriteDishEntities = favoriteDishService.fingAll();

        for (FavoriteDishEntity favoriteDishEntity : favoriteDishEntities) {
            DishEntity dishEntity = dishService.findByDishId(favoriteDishEntity.getDishUuid());
            List<IngredientEntity> ingredients = dishSerchService.getDetails(dishEntity);
            dishPanel.getChildren().add(new DishHBox(dishEntity, ingredients, favoriteDishService, historyService));
        }
    }
}
