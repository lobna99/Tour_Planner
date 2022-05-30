package com.example.tour_planner.view;

import com.example.tour_planner.model.TourLog;
import com.example.tour_planner.viewmodel.TourDetailsViewModel;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;


@ExtendWith(ApplicationExtension.class)
class FormsHandlerTest{


    private Button button;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        FormsHandler.tourForm(new TourOverviewViewModel());
        FormsHandler.logForm(new TourDetailsViewModel());
        FormsHandler.tourUpdateForm(new TourDetailsViewModel());
        FormsHandler.tourLogUpdateForm(new TourDetailsViewModel(),new TourLog("","",1,"",1,""));
    }

    @Test
    void tourForm(FxRobot robot) {
        TextField name = robot.lookup("#name").queryAs(TextField.class);
        ChoiceBox choice = robot.lookup("#choice").queryAs(ChoiceBox.class);
        FxAssert.verifyThat(name, n -> n.getPromptText().equals("enter tour name"));
        FxAssert.verifyThat(choice,c -> c.getItems().size()==3 );
    }

    @Test
    void logForm(FxRobot robot) {
        RadioButton radioButton = robot.lookup("#diff1").queryAs(RadioButton.class);
        FxAssert.verifyThat(radioButton, ToggleButton::isSelected);
    }

    @Test
    void tourUpdateForm() {
        FxAssert.verifyThat("#update",LabeledMatchers.hasText("update"));
    }

    @Test
    void tourLogUpdateForm(FxRobot robot) {
        TextField comment = robot.lookup("#comment").queryAs(TextField.class);
        RadioButton radioButton = robot.lookup("#diff2").queryAs(RadioButton.class);
        FxAssert.verifyThat(comment, n -> n.getPromptText().equals("Comment"));
        FxAssert.verifyThat(radioButton,c -> c.getText().equals("2"));
    }
}