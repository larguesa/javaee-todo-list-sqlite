package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import web.AppListener;

/**
 *
 * @author rlarg
 */
public class HourPrice {

    private long rowId;
    private Date dateTime;
    private double price;

    public static String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS hour_price(\n"
                + "    datetime datetime not null\n"
                + "    , price numeric(10,2) not null\n"
                + ")";
    }

    public HourPrice(long rowId, Date dateTime, double price) {
        this.rowId = rowId;
        this.dateTime = dateTime;
        this.price = price;
    }

    public static double getCurrentPrice() throws Exception {
        double price = 0;
        Connection con = AppListener.getConnection();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT rowid, * FROM hour_price ORDER BY datetime DESC");
        if (rs.next()) {
            price = rs.getDouble("price");
        }
        rs.close();
        s.close();
        con.close();
        return price;
    }

    public static ArrayList<HourPrice> getList() throws Exception {
        ArrayList<HourPrice> list = new ArrayList<>();
        Connection con = AppListener.getConnection();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT rowid, * FROM hour_price");
        while (rs.next()) {
            HourPrice vs = new HourPrice(
                    rs.getLong("rowid"),
                     rs.getTimestamp("datetime"),
                     rs.getDouble("price")
            );
            list.add(vs);
        }
        rs.close();
        s.close();
        con.close();
        return list;
    }

    public static void addPrice(double price) throws Exception {
        String SQL = "INSERT INTO hour_price VALUES("
                + "?" //datetime
                + ", ?" //price
                + ")";
        Connection con = AppListener.getConnection();
        PreparedStatement s = con.prepareStatement(SQL);
        s.setTimestamp(1, new Timestamp(new Date().getTime()));
        s.setDouble(2, price);
        s.execute();
        s.close();
        con.close();
    }

    public long getId() {
        return rowId;
    }

    public void setId(long id) {
        this.rowId = rowId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
