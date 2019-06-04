package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.entity.Vehicle;
import model.util.JdbcUtils;
import view.AlertView;

public class MaintenanceController implements EventHandler<ActionEvent> {

    private int id;

    public MaintenanceController( int id) {
        this.id = id;
    }

    @Override
    public void handle(ActionEvent event) {

        Vehicle v = JdbcUtils.selectVehicleById(id);

        if (v.getStatus().equals(Vehicle.Rented)) {
            AlertView.getInstance().show("Fail.\nVehicle: " + id +" is rented.");
        }
        if (v.getStatus().equals(Vehicle.Maintenance)) {
            AlertView.getInstance().show("Fail.\nVehicle: " + id + " is already under maintenance!");
        }
        if (v.getStatus().equals(Vehicle.Available)) {

            JdbcUtils.updateVehicle(id,Vehicle.Maintenance,false);
            AlertView.getInstance().show("Success.\nVehicle: " + id + " is now under maintenance!");
        }

    }

}
