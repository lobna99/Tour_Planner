package com.example.tour_planner.viewmodel;

import com.example.tour_planner.BL.BL;
import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.view.TourOverviewController;

public class MainWindowViewModel extends TourDetailsViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourOverviewViewModel tourOverviewViewModel;
    private TourDetailsViewModel tourDetailsViewModel;

    public MainWindowViewModel(SearchBarViewModel searchBarViewModel, TourOverviewViewModel tourOverviewViewModel,TourDetailsViewModel tourDetailsViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.tourOverviewViewModel = tourOverviewViewModel;
        this.tourDetailsViewModel =tourDetailsViewModel;
        this.searchBarViewModel.addSearchListener(searchString->searchTours(searchString));
        this.tourOverviewViewModel.addSelectionChangedListener(selectedTour->selectTour(selectedTour));
    }

    private void selectTour(Tour selectedTour) {
        tourDetailsViewModel.setTourModel(selectedTour);
    }

    private void searchTours(String searchString) {
        var tours = BL.getInstance().getSearchLogic().findMatchingTours(searchString);
        tourOverviewViewModel.setTours(tours);
    }

    public void addImportTour(String path) {
        Tour importedTour = DAL.getInstance().fileAccess().importTour(path);
        tourOverviewViewModel.getObservableTours().add(importedTour);
    }

}
