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


    //���ҵn�J��T�O�_���T
    public String[] LoginCheck(String account) {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        String Name = "";
        String Phone = "";

        String[] myResult = new String[2];


        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
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
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
        }

        myResult[0] = String.valueOf(Name);
        myResult[1] = String.valueOf(Phone);

        return (myResult);
    }

    //���ҬO�_�w���U�L
    public int CheckNamePhone(String Name, String Phone) {
        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;


        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "SELECT * FROM user WHERE name = ? AND phone = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Name);
            statement.setString(2, Phone);
            result = statement.executeQuery();

            while (result.next()) {
                //�w���b��
                return 1;
            }

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
        }
        return 0;

    }


    //���U�b��
    public void RegisterInsertData(RegisterData RegisterD) {
        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
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
            JOptionPane.showMessageDialog(null, "���U���\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "���U���ѡI");
        }

    }

    //��ܤw���U���ϥΪ�
    public String[] SelectUser() {
        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        String[] theResult = new String[10];
        String[] myResult = new String[10];
        int i = 0;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "SELECT * FROM user";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            result = statement.executeQuery();

            while (result.next()) {
                //�C�X�Ҧ����b��10��
                theResult[i] = result.getString("name");
                i++;
            }
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
        }

        for (int k = 0; k <= 9; k++) {
            myResult[k] = String.valueOf(theResult[k]);
        }

        return (myResult);

    }

    //�H��J���ϥΪ̬��u�� ��ܩҦ��ϥΪ�
    public String[] SelectUserDESC(String Name) {
        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        String[] theResult = new String[10];
        String[] myResult = new String[10];
        int i = 0;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "SELECT * FROM user ORDER BY name = ? DESC";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");
            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Name);
            result = statement.executeQuery();

            while (result.next()) {
                //�C�X�Ҧ����b��10��
                theResult[i] = result.getString("name");
                i++;
            }
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
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

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
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
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
        }

        return Count;
    }

    //�d�ߨϥΪ̸ԲӸ�T
    public void UserDetail(String Name) {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;


        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
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
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
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
	
    //�d�̪߳���q�O��
    public void UserLog(String Name) {

        Connection connection;
        PreparedStatement statement;
        ResultSet result;
        String cmdData;

        int i = 0;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
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
            JOptionPane.showMessageDialog(null, "��Ʈw�ާ@�o�ͨ�L���~�I");
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

    //�ǤJ�@�����
    public void InsertData(Data aData) {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
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
            JOptionPane.showMessageDialog(null, "�g�J��Ʈw���\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�g�J��Ʈw�o�Ϳ��~�I");
        }
    }

    //�ק�ϥΪ̹q�ܸ��X
    public void UpdatePhone() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "UPDATE user SET phone= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getPhone());
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "�ק令�\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�ק�q�ܸ�Ƶo�Ϳ��~�I");
        }
    }
    //�ק�ϥΪ̥ͤ�~
    public void UpdateBirth() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "UPDATE user SET birthyear= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, String.valueOf(Data.getBirth()));
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "�ק令�\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�ק�X�ͦ~��Ƶo�Ϳ��~�I");
        }
    }
    //�ק�ϥΪ̥X�ͦa
    public void UpdateCity() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "UPDATE user SET city= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getCity());
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "�ק令�\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�ק�X�ͦa��Ƶo�Ϳ��~�I");
        }
    }
    //�ק�ϥΪ̩ʧO
    public void UpdateGender() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "UPDATE user SET gender= ? WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getGender());
            statement.setString(2, Data.getUser());
            statement.executeUpdate();
//            JOptionPane.showMessageDialog(null, "�ק令�\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�ק�ʧO��Ƶo�Ϳ��~�I");
        }
    }


    //�R���ϥΪ�
    public void DeleteUser() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "DELETE FROM user WHERE name = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getDeleteUser());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "�ϥΪ̧R�����\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�ק��Ƶo�Ϳ��~�I");
        }
    }

    //�R�����q�O��
    public void DeleteLog() {

        Connection connection;
        PreparedStatement statement;
        String cmdData;

        //��Ʈw�e�m�@�~
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MySQL�X�ʵ{���w�˥��ѡI");
        }

        try {
            cmdData = "DELETE FROM mdata WHERE user = ?";
            connection = DriverManager.getConnection("jdbc:mysql://210.65.88.131/isudb2" + "?user=112isuproject&password=yang3807e");

            statement = connection.prepareStatement(cmdData);
            statement.setString(1, Data.getDeleteUser());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "���q�O���R�����\�I");
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�ק��Ƶo�Ϳ��~�I");
        }
    }
	
}