package com.example.tour_planner;

import com.example.tour_planner.view.MainWindowController;
import com.example.tour_planner.view.SearchBarController;
import com.example.tour_planner.view.TourDetailsController;
import com.example.tour_planner.view.TourOverviewController;
import com.example.tour_planner.viewmodel.MainWindowViewModel;
import com.example.tour_planner.viewmodel.TourDetailsViewModel;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import com.example.tour_planner.viewmodel.SearchBarViewModel;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourOverviewViewModel tourOverviewViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;

    public ControllerFactory() {
        searchBarViewModel = new SearchBarViewModel();
        tourOverviewViewModel = new TourOverviewViewModel();
        tourDetailsViewModel = new TourDetailsViewModel();
        mainWindowViewModel = new MainWindowViewModel(searchBarViewModel, tourOverviewViewModel, tourDetailsViewModel);
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
       if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        }else if (controllerClass == TourDetailsController.class) {
           return new TourDetailsController(tourDetailsViewModel);
       }else if (controllerClass == TourOverviewController.class) {
            return new TourOverviewController(tourOverviewViewModel);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }


    //
    // Singleton-Pattern with early-binding
    //
    private static final ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }

}
