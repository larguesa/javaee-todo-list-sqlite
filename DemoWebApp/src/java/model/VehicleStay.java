package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import web.AppListener;

/**
 *
 * @author rlarg
 */
public class VehicleStay {

    private long rowId;
    private String VehicleModel;
    private String VehicleColor;
    private String VehiclePlate;
    private Date beginStay;
    private Date endStay;
    private double price;

    public static String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS vehicles_stays(\n"
                + "    vehicle_model varchar(50) not null\n"
                + "    , vehicle_color varchar(20) not null\n"
                + "    , vehicle_plate varchar(7) not null\n"
                + "    , begin_stay datetime not null\n"
                + "    , end_stay datetime\n"
                + "    , price numeric(10,2)\n"
                + ")";
    }

    public VehicleStay(long rowId, String VehicleModel, String VehicleColor, String VehiclePlate, Date beginStay) {
        this.rowId = rowId;
        this.VehicleModel = VehicleModel;
        this.VehicleColor = VehicleColor;
        this.VehiclePlate = VehiclePlate;
        this.beginStay = beginStay;
    }

    public VehicleStay(long rowId, String VehicleModel, String VehicleColor, String VehiclePlate, Date beginStay, Date endStay, double price) {
        this.rowId = rowId;
        this.VehicleModel = VehicleModel;
        this.VehicleColor = VehicleColor;
        this.VehiclePlate = VehiclePlate;
        this.beginStay = beginStay;
        this.endStay = endStay;
        this.price = price;
    }

    public static VehicleStay getStay(long rowid) throws Exception {
        VehicleStay vs = null;
        String SQL = "SELECT rowid, * FROM vehicles_stays WHERE rowid=?";
        Connection con = AppListener.getConnection();
        PreparedStatement s = con.prepareStatement(SQL);
        s.setLong(1, rowid);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            vs = new VehicleStay(
                    rs.getInt("rowid"),
                     rs.getString("vehicle_model"),
                     rs.getString("vehicle_color"),
                     rs.getString("vehicle_plate"),
                     rs.getTimestamp("begin_stay")
            );
        }
        rs.close();
        s.close();
        con.close();
        return vs;
    }

    public static ArrayList<VehicleStay> getList() throws Exception {
        ArrayList<VehicleStay> list = new ArrayList<>();
        Connection con = AppListener.getConnection();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT rowid, * FROM vehicles_stays"
                + " WHERE end_stay IS NULL");
        while (rs.next()) {
            VehicleStay vs = new VehicleStay(
                    rs.getLong("rowid"),
                     rs.getString("vehicle_model"),
                     rs.getString("vehicle_color"),
                     rs.getString("vehicle_plate"),
                     rs.getTimestamp("begin_stay")
            );
            list.add(vs);
        }
        rs.close();
        s.close();
        con.close();
        return list;
    }

    public static ArrayList<VehicleStay> getHistoryList(String plate, String beginDate) throws Exception {
        ArrayList<VehicleStay> list = new ArrayList<>();
        String SQL = "SELECT rowid, * FROM vehicles_stays WHERE end_stay IS NOT NULL ";
        if (plate != null) {
            SQL += " AND vehicle_plate = ?";
        }
        if (beginDate != null) {
            SQL += " AND begin_stay >= '" + beginDate + "'";
        }
        Connection con = AppListener.getConnection();
        PreparedStatement s = con.prepareStatement(SQL);
        if (plate != null) {
            s.setString(1, plate);
        }
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
            VehicleStay vs = new VehicleStay(
                    rs.getLong("rowid"),
                     rs.getString("vehicle_model"),
                     rs.getString("vehicle_color"),
                     rs.getString("vehicle_plate"),
                     rs.getTimestamp("begin_stay"),
                     rs.getTimestamp("end_stay"),
                     rs.getDouble("price")
            );
            list.add(vs);
        }
        rs.close();
        s.close();
        con.close();
        return list;
    }

    public static void addVehicleStay(String model, String color, String plate)
            throws Exception {
        String SQL = "INSERT INTO vehicles_stays VALUES("
                + "?" //vehicle_model
                + ", ?" //vehicle_color
                + ", ?" //vehicle_plate
                + ", ?" //begin_stay
                + ", NULL" //end_stay
                + ", NULL" //price
                + ")";
        Connection con = AppListener.getConnection();
        PreparedStatement s = con.prepareStatement(SQL);
        s.setString(1, model);
        s.setString(2, color);
        s.setString(3, plate);
        s.setTimestamp(4, new Timestamp(new Date().getTime()));
        s.execute();
        s.close();
        con.close();
    }

    public static void finishVehicleStay(long rowid, double price)
            throws Exception {
        String SQL = "UPDATE vehicles_stays"
                + " SET end_stay=?, price=?"
                + " WHERE rowid =?";
        Connection con = AppListener.getConnection();
        PreparedStatement s = con.prepareStatement(SQL);
        s.setTimestamp(1, new Timestamp(new Date().getTime()));
        s.setDouble(2, price);
        s.setLong(3, rowid);
        s.execute();
        s.close();
        con.close();
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String VehicleModel) {
        this.VehicleModel = VehicleModel;
    }

    public String getVehicleColor() {
        return VehicleColor;
    }

    public void setVehicleColor(String VehicleColor) {
        this.VehicleColor = VehicleColor;
    }

    public String getVehiclePlate() {
        return VehiclePlate;
    }

    public void setVehiclePlate(String VehiclePlate) {
        this.VehiclePlate = VehiclePlate;
    }

    public Date getBeginStay() {
        return beginStay;
    }

    public void setBeginStay(Date beginStay) {
        this.beginStay = beginStay;
    }

    public Date getEndStay() {
        return endStay;
    }

    public void setEndStay(Date endStay) {
        this.endStay = endStay;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() throws Exception {
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginStay);
        Calendar now = Calendar.getInstance();
        int horas = now.get(Calendar.HOUR_OF_DAY) - begin.get(Calendar.HOUR_OF_DAY) + 1;
        return horas * HourPrice.getCurrentPrice();
    }
}
