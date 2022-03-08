package com.example.tour_planner.viewmodel;

import com.example.tour_planner.model.Tour;
import com.example.tour_planner.viewmodel.SearchBarViewModel;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;

public class MainWindowViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourOverviewViewModel mediaOverviewViewModel;
    //private MediaDetailsViewModel mediaDetailsViewModel;

    public MainWindowViewModel(SearchBarViewModel searchBarViewModel, TourOverviewViewModel mediaOverviewViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.mediaOverviewViewModel = mediaOverviewViewModel;
        //this.mediaDetailsViewModel = mediaDetailsViewModel;

        //this.searchBarViewModel.addSearchListener(searchString->searchTours(searchString));
        // instead of the lambda-expression from above, you also can use the following "classical" event-handler implementation with anonymous inner classes
//        this.searchBarViewModel.addSearchListener(new SearchBarViewModel.SearchListener() {
//            @Override
//            public void search(String searchString) {
//                var tours = BL.getInstance().findMatchingTours( searchString );
//                toursOverviewViewModel.setTours(tours);
//            }
//        });

        //this.mediaOverviewViewModel.addSelectionChangedListener(selectedTour->selectTour(selectedTour));
    }

    /*private void selectTour(Tour selectedMediaItem) {
        mediaDetailsViewModel.setTourModel(selectedMediaItem);
    }*/

    /*private void searchTours(String searchString) {
        var tours = BL.getInstance().findMatchingTours( searchString );
        mediaOverviewViewModel.setTours(tours);
    }*/
}
