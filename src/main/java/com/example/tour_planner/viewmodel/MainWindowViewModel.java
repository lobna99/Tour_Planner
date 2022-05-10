package com.example.tour_planner.viewmodel;

import com.example.tour_planner.model.Tour;

public class MainWindowViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourOverviewViewModel tourOverviewViewModel;
    private TourDetailsViewModel tourDetailsViewModel;

    public MainWindowViewModel(SearchBarViewModel searchBarViewModel, TourOverviewViewModel mediaOverviewViewModel,TourDetailsViewModel tourDetailsViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.tourOverviewViewModel = mediaOverviewViewModel;
        this.tourDetailsViewModel =tourDetailsViewModel;

        this.searchBarViewModel.addSearchListener(searchString->searchTours(searchString));
        // instead of the lambda-expression from above, you also can use the following "classical" event-handler implementation with anonymous inner classes
//        this.searchBarViewModel.addSearchListener(new SearchBarViewModel.SearchListener() {
//            @Override
//            public void search(String searchString) {
//                var tours = BL.getInstance().findMatchingTours( searchString );
//                toursOverviewViewModel.setTours(tours);
//            }
//        });

        this.tourOverviewViewModel.addSelectionChangedListener(selectedTour->selectTour(selectedTour));
    }

    private void selectTour(Tour selectedMediaItem) {
        tourDetailsViewModel.setTourModel(selectedMediaItem);
    }

    private void searchTours(String searchString) {
        //var tours = BL.getInstance().findMatchingTours( searchString );
        //TourOverviewViewModel.setTours(tours);
    }
}
