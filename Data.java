//�פJ�ݭn���U���M��
import java.awt.*;
import java.sql.*;
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


    public Data(){
        datetime = "";
        user = "";
        lefthand = "";
        leftfoot = "";
        righthand = "";
        rightfoot = "";
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
}
