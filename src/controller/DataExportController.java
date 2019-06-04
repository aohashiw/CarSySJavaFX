package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.entity.Record;
import model.entity.Vehicle;
import model.util.JdbcUtils;
import view.AlertView;
import view.MainView;

import java.io.*;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataExportController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        File root = new File(MainView.path);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(root);
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        if (file == null)
            return;
        if (file.exists()) {
            file.delete();
        }
        export(file);
    }


    static void export(File file) {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(osw);
            List<Vehicle> vehicleList = JdbcUtils.selectVehicle();
            if (vehicleList != null && !vehicleList.isEmpty()) {
                for (int i = 0; i < vehicleList.size(); i++) {
                    Vehicle v = vehicleList.get(i);
                    bw.append(v.toString());
                    List<Record> recordList = JdbcUtils.selectRecordByVid(v.getId());
                    if (recordList != null && !recordList.isEmpty()) {

                        Collections.sort(recordList, new Comparator<Record>() {
                            public int compare(Record o1, Record o2) {
                                Period period = Period.between(o1.getRentdate(), o2.getRentdate());
                                int days = period.getDays();
                                if (days > 0) return 1;
                                else return -1;
                            }

                        });
                        for (int j = 0; j < recordList.size(); j++) {
                            System.out.println(recordList.get(j).getRentdate());
                            bw.append(recordList.get(j).toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (Exception e) {
                    AlertView.getInstance().show("Fail!");
                }
            }

        }
    }


}
