package model.entity;

import java.time.LocalDate;
import java.time.Period;

public class Record  implements Comparable<Record> {

    private int id;
    private int vid;
    private int uid;

    public Record() {
    }

    public LocalDate getRentdate() {
        return rentdate;
    }

    public void setRentdate(LocalDate rentdate) {
        this.rentdate = rentdate;
    }

    private LocalDate rentdate;
    private LocalDate a_rdate;
    private LocalDate e_rdate;
    private double rental_fee;
    private double late_fee;
    private boolean isReturn;


    public Record(int uid, int vid, LocalDate rentdate,LocalDate e_rdate,double rental_fee) {
        this.uid = uid;
        this.vid = vid;
        this.rentdate = rentdate;
        this.e_rdate = e_rdate;
        this.rental_fee = rental_fee;
    }

    public Record(int id, int vid, int uid, LocalDate rentdate,LocalDate e_rdate, LocalDate a_rdate, double rental_fee, double late_fee,boolean isReturn) {
        this.id = id;
        this.vid = vid;
        this.uid = uid;
        this.rentdate = rentdate;
        this.e_rdate = e_rdate;
        this.a_rdate = a_rdate;

        this.rental_fee = rental_fee;
        this.late_fee = late_fee;
        this.isReturn = isReturn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public LocalDate getA_rdate() {
        return a_rdate;
    }

    public void setA_rdate(LocalDate a_rdate) {
        this.a_rdate = a_rdate;
    }

    public LocalDate getE_rdate() {
        return e_rdate;
    }

    public void setE_rdate(LocalDate e_rdate) {
        this.e_rdate = e_rdate;
    }

    public double getLate_fee() {
        return late_fee;
    }

    public void setLate_fee(double late_fee) {
        this.late_fee = late_fee;
    }

    public double getRental_fee() {
        return rental_fee;
    }

    public void setRental_fee(double rental_fee) {
        this.rental_fee = rental_fee;
    }


    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append(":"+rentdate);
        sb.append(":"+e_rdate);
        if(a_rdate==null) {
            sb.append(":none:none:none");
        }else {
            sb.append(":"+a_rdate);
            sb.append(":"+String.format("%.2f", rental_fee));
            sb.append(":"+String.format("%.2f", late_fee));

        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Record o) {
        Period period = Period.between(this.rentdate, o.rentdate);
        int days = period.getDays();
        if (days>0){
            return 1;
        }else {
            return -1;
        }
    }
}
