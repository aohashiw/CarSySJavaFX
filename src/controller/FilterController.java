package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.GeneralView;
import view.MainView;

public class FilterController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        MainView.myList.setContent(GeneralView.getInstance().listCreate(MainView.filterVehicleList));
    }
}
