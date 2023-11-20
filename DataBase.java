import javax.swing.*;
import java.sql.*;

class DataBase {
    Connection connection;
    Statement statement;
    public Object isudb2;

    public String[] DateResult = new String[100];
    public String[] LefthandResult = new String[100];
    public String[] LeftfootResult = new String[100];
    public String[] RighthandResult = new String[100];
    public String[] RightfootResult = new String[100];
    public String PhoneResult = new String();
    public String GenderResult = new String();
    public String BirthyearResult = new String();
    public String CityResult = new String();

    public int[] LefthandResultArray = new int[100];

    public DataBase() {

    }


    //驗證登入資訊是否正確
    public String[] LoginCheck(String account) {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        String Name = "";
        String Phone = "";

        String[] myResult = new String[2];


        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "SELECT * FROM user WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, account);
            result = statement.executeQuery();


            while (result.next()) {
                Name = result.getString("name");
                Phone = result.getString("phone");

            }
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }

        myResult[0] = String.valueOf(Name);
        myResult[1] = String.valueOf(Phone);

        return (myResult);
    }

    //驗證是否已註冊過
    public int CheckNamePhone(String Name, String Phone) {
        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;


        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "SELECT * FROM user WHERE name = ? AND phone = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Name);
            statement.setString(2, Phone);
            result = statement.executeQuery();

            while (result.next()) {
                //已有帳號
                return 1;
            }

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }
        return 0;

    }


    //註冊帳號
    public void RegisterInsertData(RegisterData RegisterD) {
        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "INSERT INTO user(name,phone,gender,birthyear,city)" +
                    "VALUES(?,?,?,?,?)";

            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);

            statement.setString(1, RegisterD.getName());
            statement.setString(2, RegisterD.getPhone());
            statement.setString(3, RegisterD.getGender());
            statement.setString(4, RegisterD.getBirthyear());
            statement.setString(5, RegisterD.getCity());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "註冊成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "註冊失敗！");
        }

    }

    //顯示已註冊的使用者
    public String[] SelectUser() {
        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        String[] theResult = new String[10];
        String[] myResult = new String[10];
        int i = 0;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "SELECT * FROM user";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            result = statement.executeQuery();

            while (result.next()) {
                //列出所有的帳號10個
                theResult[i] = result.getString("name");
                i++;
            }
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }

        for (int k = 0; k <= 9; k++) {
            myResult[k] = String.valueOf(theResult[k]);
        }

        return (myResult);

    }

    //以輸入的使用者為優先 顯示所有使用者
    public String[] SelectUserDESC(String Name) {
        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        String[] theResult = new String[10];
        String[] myResult = new String[10];
        int i = 0;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "SELECT * FROM user ORDER BY name = ? DESC";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Name);
            result = statement.executeQuery();

            while (result.next()) {
                //列出所有的帳號10個
                theResult[i] = result.getString("name");
                i++;
            }
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }

        for (int k = 0; k <= 9; k++) {
            myResult[k] = String.valueOf(theResult[k]);
        }

        return (myResult);

    }

    public int CountUser() {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        int Count = 0;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "SELECT * FROM user";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            result = statement.executeQuery();

            while (result.next()) {

                Count++;

            }
            statement.close();


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }

        return Count;
    }

    //查詢使用者詳細資訊
    public void UserDetail(String Name) {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;


        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }
        try {
            cmdData = "SELECT * FROM `user` WHERE name = ? ";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Name);
            result = statement.executeQuery();

            while (result.next()) {
                PhoneResult = result.getString("phone");
                GenderResult = result.getString("gender");
                BirthyearResult = result.getString("birthyear");
                CityResult = result.getString("city");
            }
            statement.close();


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }
    }

    public String getPhoneResult() {
        return PhoneResult;
    }

    public String getGenderResult() {
        return GenderResult;
    }
	
    public String getBirthyearResult() {
        return BirthyearResult;
    }
	
    public String getCityResult() {
        return CityResult;
    }
	
    //查詢最近測量記錄
    public void UserLog(String Name) {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        int i = 0;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }
        try {
            cmdData = "SELECT * FROM `mdata` WHERE user = ? ORDER BY 1 DESC";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Name);
            result = statement.executeQuery();

            while (result.next()) {
                DateResult[i] = result.getString("datetime");
                LefthandResult[i] = result.getString("lefthand");
                LeftfootResult[i] = result.getString("leftfoot");
                RighthandResult[i] = result.getString("righthand");
                RightfootResult[i] = result.getString("rightfoot");
                i++;

            }
            statement.close();


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "資料庫操作發生其他錯誤！");
        }
    }

    public void CleanDate() {
        for (int i = 0; i < 100; i++) {
            DateResult[i] = null;
            LefthandResult[i] = null;
            LeftfootResult[i] = null;
            RighthandResult[i] = null;
            RightfootResult[i] = null;

            PhoneResult = null;
            GenderResult = null;
            BirthyearResult = null;
            CityResult = null;

        }
    }

    public String[] getDateResult() {
        return (DateResult);
    }

    public String[] getLefthandResult() {
        return (LefthandResult);
    }

    public String[] getLeftfootResult() {
        return (LeftfootResult);
    }

    public String[] getRighthandResult() {
        return (RighthandResult);
    }

    public String[] getRightfootResult() {
        return (RightfootResult);
    }

    //傳入一筆資料
    public void InsertData(Data aData) {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "INSERT INTO mdata(datetime,user,lefthand,leftfoot,righthand,rightfoot)" +
                    "VALUES(?,?,?,?,?,?)";

            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);

            statement.setString(1, aData.getDatetime());
            statement.setString(2, aData.getUser());
            statement.setString(3, aData.getLefthand());
            statement.setString(4, aData.getLeftfoot());
            statement.setString(5, aData.getRighthand());
            statement.setString(6, aData.getRightfoot());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "寫入資料庫成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "寫入資料庫發生錯誤！");
        }
    }

    //修改使用者電話號碼
    public void UpdatePhone() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "UPDATE user SET phone= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getPhone());
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "修改成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改電話資料發生錯誤！");
        }
    }
    //修改使用者生日年
    public void UpdateBirth() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "UPDATE user SET birthyear= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, String.valueOf(Data.getBirth()));
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "修改成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改出生年資料發生錯誤！");
        }
    }
    //修改使用者出生地
    public void UpdateCity() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "UPDATE user SET city= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getCity());
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "修改成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改出生地資料發生錯誤！");
        }
    }
    //修改使用者性別
    public void UpdateGender() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "UPDATE user SET gender= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getGender());
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "修改成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改性別資料發生錯誤！");
        }
    }


    //刪除使用者
    public void DeleteUser() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "DELETE FROM user WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getDeleteUser());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "使用者刪除成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改資料發生錯誤！");
        }
    }

    //刪除測量記錄
    public void DeleteLog() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //資料庫前置作業
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL驅動程式安裝失敗！");
        }

        try {
            cmdData = "DELETE FROM mdata WHERE user = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getDeleteUser());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "測量記錄刪除成功！");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改資料發生錯誤！");
        }
    }
	
}