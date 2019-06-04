package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.entity.Record;
import model.entity.Vehicle;
import model.util.JdbcUtils;
import view.AlertView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


public class ReturnController implements EventHandler<ActionEvent> {

    private Stage returnStage;
    private int id;


    public ReturnController(Stage stage, int id) {
        this.id = id;
        this.returnStage = stage;
    }


    @Override
    public void handle(ActionEvent e) {

        Vehicle v = JdbcUtils.selectVehicleById(id);

        if (v.getStatus().equals(Vehicle.Available)) {
            AlertView.getInstance().show("Fail.\nVehicle: " + id + " could not be returned now!");
        }

        if (v.getStatus().equals(Vehicle.Maintenance)) {
            AlertView.getInstance().show("Fail.\nVehicle: " + id + " is under maintenance \n It could not be returned.");

        }

        if (v.getStatus().equals(Vehicle.Rented)) {
            List<Record> list =JdbcUtils.selectRecordByVid(id);
            Record r = list.stream()
                    .filter(x -> !x.isReturn())
                    .findFirst().get();

            r.setA_rdate(LocalDate.now());
            Period period = Period.between(r.getE_rdate(),r.getA_rdate());
            long days = period.getDays();
            if( days < 0 ){
                AlertView.getInstance().show("Fail.\nThe date of return has not arrived.\n");

            }else {
                System.out.println(days);
                r.setLate_fee(days*v.getLate_rate());
                JdbcUtils.updateRecord(r);
                System.out.println(r);
                JdbcUtils.updateVehicle(id,Vehicle.Available,false);
                AlertView.getInstance().show("Success.\nReturn Successfully.");
            }

        }

    }


}