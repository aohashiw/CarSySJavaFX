package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.util.JdbcUtils;
import view.GeneralView;
import view.MainView;

public class FreshController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        MainView.vehicleList = JdbcUtils.selectVehicle();
        MainView.filterVehicleList = MainView.vehicleList;
        MainView.myList.setContent(GeneralView.getInstance().listCreate(MainView.filterVehicleList));

    }
}
