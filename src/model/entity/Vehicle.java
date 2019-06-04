package model.entity;


import java.time.LocalDate;

public class Vehicle {


    public final static String Available ="Available";
    public final static String Rented = "Rented";
    public final static String Maintenance="Maintenance";
    public final static int interval = 12;

    private Integer id;
    private int year;
    private int seats;
    private String make;
    private String type;
    private String status;
    private String img;
    private LocalDate maintenance_date;
    private LocalDate complete_Maintenance_date;
    private double rent_rate;
    private double late_rate;

    public Vehicle(){
    }

    public Vehicle(int year, int seats, String make, String type) {
        this.year = year;
        this.seats = seats;
        this.make = make;
        this.type = type;
        this.status = Available;
        this.img = "default.jpg";
        if (this.type.equals("Van")){
            this.rent_rate=235;
            this.late_rate=299;
        }else {
            if (this.seats==4){
                this.rent_rate=78;
            }else if (this.seats==7){
                this.rent_rate = 113;
            }
            late_rate=rent_rate*1.25;
        }


    }

    public Vehicle(Integer id, int year, int seats, String make, String type, String status, String img, LocalDate maintenance_date, LocalDate complete_Maintenance_date, double rent_rate, double late_rate) {
        this.id = id;
        this.year = year;
        this.seats = seats;
        this.make = make;
        this.type = type;
        this.status = status;
        this.img = img;
        this.maintenance_date = maintenance_date;
        this.complete_Maintenance_date = complete_Maintenance_date;
        this.rent_rate = rent_rate;
        this.late_rate = late_rate;
    }

    public  String getAvailable() {
        return Available;
    }

    public  String getRented() {
        return Rented;
    }

    public  String getMaintenance() {
        return Maintenance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
        if(seats == 4) {
            rent_rate = 78;
            late_rate = rent_rate * 1.25;

        }else if(seats == 7) {
            rent_rate = 113;
            late_rate = rent_rate * 1.25;

        }else {
            rent_rate =235;
            late_rate=299;
        }

    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDate getMaintenance_date() {
        return maintenance_date;
    }

    public void setMaintenance_date(LocalDate maintenance_date) {
        this.maintenance_date = maintenance_date;
    }

    public LocalDate getComplete_Maintenance_date() {
        return complete_Maintenance_date;
    }

    public void setComplete_Maintenance_date(LocalDate complete_Maintenance_date) {
        this.complete_Maintenance_date = complete_Maintenance_date;
    }

    public double getRent_rate() {
        return rent_rate;
    }

    public double getLate_rate() {
        return late_rate;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id+":"+year+":"+make+":"+type+":"+seats+":"+status.toString());
        if(this.type == "Van") {
            sb.append(":"+this.complete_Maintenance_date);
        }
        if (this.img!=null){
            sb.append(":"+this.img);
        }
        sb.append("\n");
        return sb.toString();
    }
}
