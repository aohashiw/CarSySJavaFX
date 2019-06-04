package model.util;

import model.entity.Record;
import model.entity.Vehicle;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils {

    private static final String driverClass;
    private static final String url;

    static {
        driverClass = "org.sqlite.JDBC";
        url = "jdbc:sqlite:databases\\thriftysystem.sqlite3";
    }

    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(driverClass);
    }

    public static Connection getConnection() throws Exception {
        loadDriver();
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    public static void release( Statement stmt, Connection conn) {

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }
    public static void release(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

     static Vehicle createVehicle (ResultSet rs) throws SQLException {

        Integer id = rs.getInt("id");
        int year = rs.getInt("year");
        int seats = rs.getInt("seats");
        String make = rs.getString("make");
        String type =  rs.getString("type");
        String status = rs.getString("status");
        String img = (rs.getString("img")==null)?"default.jpg":rs.getString("img");
        LocalDate maintenance_date = (rs.getDate("maintenance_date")==null)?null:rs.getDate("maintenance_date").toLocalDate();
        LocalDate complete_maintenance_date = (rs.getDate("c_maintenance_date")==null)?null:rs.getDate("c_maintenance_date").toLocalDate();
        double rent_rate =rs.getDouble("rent_rate");
        double late_rate = rs.getDouble("late_rate");
        return new Vehicle(id,year,seats,make,type,status,img,maintenance_date,complete_maintenance_date,rent_rate,late_rate);
    }

     static Record createRecord (ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        int vid = rs.getInt("v_id");
        int uid = rs.getInt("u_id");
        LocalDate rentdate = (rs.getDate("rentdate")==null)?null:rs.getDate("rentdate").toLocalDate();
        LocalDate a_rdate = (rs.getDate("a_rdate")==null)?null:rs.getDate("a_rdate").toLocalDate();
        LocalDate e_rdate = (rs.getDate("e_rdate")==null)?null:rs.getDate("e_rdate").toLocalDate();
        double rental_fee =rs.getDouble("rental_fee");
        double late_fee = rs.getDouble("late_fee");
        boolean isReturn = rs.getBoolean("isReturn");
        return new Record(id,vid,uid,
                rentdate,e_rdate,a_rdate,
                rental_fee,late_fee,isReturn);
    }

    public static Vehicle selectVehicleById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vehicle vehicle = new Vehicle();
        try {
            conn = getConnection();
            String sql = "select * from vehicles where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vehicle = createVehicle(rs);
            }
            return vehicle;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(rs, pstmt, conn);
        }
        return vehicle;
    }


    public static List<Vehicle> selectVehicle() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Vehicle> res = new ArrayList<Vehicle>();
        try {
            conn = getConnection();
            String sql = "select * from vehicles";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                res.add(createVehicle(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(rs, pstmt, conn);
        }
        return res;
    }


    public static void insertVehicle(Vehicle v) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "insert into  vehicles(year,make,seats,type,status,img,rent_rate,late_rate)" +
                    "VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,v.getYear());
            pstmt.setString(2,v.getMake());
            pstmt.setInt(3,v.getSeats());
            pstmt.setString(4,v.getType());
            pstmt.setString(5,v.getStatus());
            pstmt.setString(6,v.getImg());
            pstmt.setDouble(7,v.getRent_rate());
            pstmt.setDouble(8,v.getLate_rate());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(pstmt, conn);
        }
    }
    public static void insertVehicle2(Vehicle v) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "insert into  vehicles(id,year,make,seats,type,status,img,rent_rate,late_rate)" +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            System.out.println(v);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,v.getId());
            pstmt.setInt(2,v.getYear());
            pstmt.setString(3,v.getMake());
            pstmt.setInt(4,v.getSeats());
            pstmt.setString(5,v.getType());
            pstmt.setString(6,v.getStatus());
            pstmt.setString(7,v.getImg());
            pstmt.setDouble(8,v.getRent_rate());
            pstmt.setDouble(9,v.getLate_rate());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(pstmt, conn);
        }
    }


    public static void updateVehicle(int id,String s,boolean isComplete) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            if (s.equals(Vehicle.Maintenance)){
                String sql = "update  vehicles  set status =?,maintenance_date=? where id =?";
                System.out.println(s);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,s);
                pstmt.setDate(2,Date.valueOf(LocalDate.now()));
                pstmt.setInt(3,id);

            }else
                if (s.equals(Vehicle.Available) && isComplete){

                String sql = "update  vehicles  set status =?,c_maintenance_date=? where id =?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,s);
                pstmt.setDate(2,Date.valueOf(LocalDate.now()));
                pstmt.setInt(3,id);

            } else{
                String sql = "update  vehicles  set status =? where id =?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,s);
                pstmt.setInt(2,id);
            }

            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(pstmt, conn);
        }
    }




    public static List<Record> selectRecordByVid(Integer vid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Record> recordList = new ArrayList<Record>();
        try {
            conn = getConnection();
            String sql = "select * from records where v_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, vid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                recordList.add(createRecord(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(rs, pstmt, conn);
        }
        return recordList;
    }

    public static void updateRecord(Record r) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "update  records set a_rdate= ?,late_fee=?,isReturn=true where id=?";
            Date d = Date.valueOf(r.getA_rdate());
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1,d);
            pstmt.setDouble(2,r.getLate_fee());
            pstmt.setInt(3,r.getId());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(pstmt, conn);
        }
    }


    public static void insertRecord(Record r) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "insert into  records(u_id,v_id,rentdate,e_rdate,rental_fee)" +
                    "VALUES (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,r.getUid());
            pstmt.setInt(2,r.getVid());
            pstmt.setDate(3,Date.valueOf(r.getRentdate()));
            pstmt.setDate(4,Date.valueOf(r.getE_rdate()));
            pstmt.setDouble(5,r.getRental_fee());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(pstmt, conn);
        }
    }


    public static void insertRecord2(Record r) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "insert into  records(id,v_id,rentdate,e_rdate,a_rdate,rental_fee,late_fee,isReturn)" +
                    "VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,r.getId());
            pstmt.setInt(2,r.getVid());
            pstmt.setDate(3,Date.valueOf(r.getRentdate()));
            pstmt.setDate(4,Date.valueOf(r.getE_rdate()));
            pstmt.setDouble(6,r.getRental_fee());
            pstmt.setDouble(7,r.getLate_fee());
            pstmt.setBoolean(8,r.isReturn());
            if(!(r.getA_rdate()==null)){
                pstmt.setDate(5,Date.valueOf(r.getRentdate()));
            }else pstmt.setNull(5, Types.DATE);
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(pstmt, conn);
        }
    }
}







