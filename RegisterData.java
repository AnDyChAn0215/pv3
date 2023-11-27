//匯入需要的各類套件

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;


//問題領域層類別
//RegisterData: Class ProblemDomain_register (註冊類別)

class RegisterData {

    private String name;        //屬性:姓名字串
    private String phone;        //屬性:電話字串
    private String city;        //屬性:城市字串
    private String birthyear;   //屬性:出生年（西元）字串
    private String gender;    //屬性:性別字串

    //建構子:類別CPD_register
    public RegisterData() {

        name = "";
        phone = "";
        city = "";
        birthyear = "";
        gender = "";

    }

    //方法:設定姓名
    public void setName(String Name) {
        name = Name;
    }

    //方法:設定電話
    public void setPhone(String Phone) {
        phone = Phone;
    }

    //方法:設定城市
    public void setCity(String City) {
        city = City;
    }

    //方法:設定出生年（西元）
    public void setBirthyear(String Birthyear) {
        birthyear = Birthyear;
    }

    //方法:設定性別
    public void setGender(String Gender) {
        gender = Gender;
    }

    //方法:取得姓名
    public String getName() {
        return (name);
    }

    //方法:取得電話
    public String getPhone() {
        return (phone);
    }

    //方法:取得城市
    public String getCity() {
        return (city);
    }

    //方法:取得出生年（西元）
    public String getBirthyear() {
        return (birthyear);
    }

    //方法:取得性別
    public String getGender() {
        return (gender);
    }

} //end for: class RegisterData

