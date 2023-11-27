//匯入需要的各類套件
import java.awt.*;
import java.sql.*;
import javax.print.attribute.standard.Chromaticity;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;

class Data {
    private String datetime;
    private static String user;
    private String lefthand;
    private String leftfoot;
    private String righthand;
    private String rightfoot;
    private static String phone;
    private static int Birth;
    private  static String City;
    private  static String Gender;
    private static String DeleteUser;


    public Data(){
        datetime = "";
        user = "";
        lefthand = "";
        leftfoot = "";
        righthand = "";
        rightfoot = "";
        DeleteUser = "";
        Birth = 0;
        City = "";
        Gender = "";
    }

    public void setDatetime(String DateTime){
        datetime = DateTime;
    }

    public String getDatetime(){
        return datetime;
    }

    public void setUser(String USER){
        user = USER;
    }

    public static String getUser(){
        return user;
    }

    public void setLefthand(String LeftHand){
        lefthand = LeftHand;
    }

    public String getLefthand(){
        return lefthand;
    }

    public void setLeftfoot(String LeftFoot){
        leftfoot = LeftFoot;
    }

    public String getLeftfoot(){
        return leftfoot;
    }

    public void setRighthand(String RightHand){
        righthand = RightHand;
    }

    public String getRighthand(){
        return righthand;
    }

    public void setRightfoot(String RightFoot){
        rightfoot = RightFoot;
    }

    public String getRightfoot(){
        return rightfoot;
    }

    public void setPhone(String Phone){
        phone = Phone;
    }
    public static String getPhone(){
        return phone;
    }
    public void setBirth(int BIRTH){
        Birth = BIRTH;
    }
    public static int getBirth(){
        return Birth;
    }
    public void setcity(String CITY){
        City = CITY;
    }
    public static String getCity(){
        return City;
    }
    public void setGender(String GENDER){
        Gender = GENDER;
    }
    public static String getGender(){
        return Gender;
    }

    //預留刪除使用者
    public void setDeleteUser(String DELETEUSER){
        DeleteUser = DELETEUSER;
    }
    public static String getDeleteUser(){
        return DeleteUser;
    }
}
