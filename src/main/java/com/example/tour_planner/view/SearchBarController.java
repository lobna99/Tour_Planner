package com.example.tour_planner.view;


import com.example.tour_planner.viewmodel.SearchBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchBarController {
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchTextField;

    private final SearchBarViewModel searchBarViewModel;

    public SearchBarController(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        return searchBarViewModel;
    }

    @FXML
    void initialize() {
        searchTextField.textProperty().bindBidirectional( searchBarViewModel.searchStringProperty() );
        //searchButton.disableProperty().bind( searchBarViewModel.searchDisabledBinding() );
    }
    public void onSearchButton(ActionEvent actionEvent) {
        searchBarViewModel.doSearch();
    }
}
