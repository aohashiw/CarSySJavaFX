package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.entity.Vehicle;
import model.util.JdbcUtils;
import view.AlertView;

public class CMaintenanceController implements EventHandler<ActionEvent> {

    private int id;

    public CMaintenanceController( int id) {
        this.id = id;
    }

    @Override
    public void handle(ActionEvent event) {

        Vehicle v = JdbcUtils.selectVehicleById(id);

        if (!v.getStatus().equals(Vehicle.Maintenance)) {

            AlertView.getInstance().show("Fail.\nVehicle: " + id +" is not under maintenance!");
        } else  {

            JdbcUtils.updateVehicle(id,Vehicle.Available,true);
            AlertView.getInstance().show("Success.\nVehicle: " + id + " has all maintenance completed and ready for rent.");

        }



    }

}
