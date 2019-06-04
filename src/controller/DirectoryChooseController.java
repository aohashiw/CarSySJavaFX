package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.DirectoryChooserBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entity.Record;
import model.entity.Vehicle;
import model.util.JdbcUtils;
import view.AlertView;
import view.MainView;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


public class DirectoryChooseController implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(MainView.path));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        Stage s = new Stage();
        File file = fileChooser.showOpenDialog(s);
        if (file != null) {
            importFile(file);
        }
}


    static void importFile(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));// 文件名
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if(Pattern.matches("^\\d+:\\d{4}:.*$",line)){
                    String[] s = line.split(":");
                    Vehicle v = new Vehicle();
                    v.setId(Integer.parseInt(s[0]));
                    v.setYear(Integer.parseInt(s[1]));
                    v.setMake(s[2]);
                    v.setType(s[3]);
                    v.setSeats(Integer.parseInt(s[4]));
                    v.setStatus(s[5]);
                    count=v.getId();
                    if (s.length==7){
                        v.setImg(s[6]);
                    }else
                        v.setImg("default.jpg");
                    JdbcUtils.insertVehicle2(v);
                }else {
                    String[] s = line.split(":");
                    Record r = new Record();
                    r.setVid(count);
                    r.setId(Integer.parseInt(s[0]));
                    r.setRentdate(LocalDate.parse(s[1]));
                    r.setE_rdate(LocalDate.parse(s[2]));
                    r.setRental_fee(Double.parseDouble(s[4]));
                    if(!s[3].equals("none")){
                        r.setLate_fee(0);
                        r.setReturn(false);
                    }else {
                        r.setLate_fee(Double.parseDouble(s[5]));
                        r.setReturn(true);
                    }

                    JdbcUtils.insertRecord2(r);
                }
            }
            AlertView.getInstance().show("Success!");
        } catch (Exception e) {
            //e.printStackTrace();
            AlertView.getInstance().show("Fail!\nError Format File.");
        }


    }


}
