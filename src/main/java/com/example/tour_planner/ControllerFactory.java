package com.example.tour_planner;

import com.example.tour_planner.viewmodel.MainWindowViewModel;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import com.example.tour_planner.viewmodel.SearchBarViewModel;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourOverviewViewModel mediaOverviewViewModel;
    //private final MediaDetailsViewModel mediaDetailsViewModel;

    public ControllerFactory() {
        searchBarViewModel = new SearchBarViewModel();
        mediaOverviewViewModel = new TourOverviewViewModel();
        //mediaDetailsViewModel = new MediaDetailsViewModel();
        mainWindowViewModel = new MainWindowViewModel(searchBarViewModel, mediaOverviewViewModel);
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
       if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        }/* else if (controllerClass == MediaDetailsController.class) {
            return new MediaDetailsController(mediaDetailsViewModel);
        */else if (controllerClass == TourOverviewController.class) {
            return new TourOverviewController(mediaOverviewViewModel);
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
