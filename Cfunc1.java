import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.util.Date;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.text.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.plaf.FontUIResource; //修改JOptionPane字體所需套件
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
//播放MP3音樂所需套件
//import com.sun.scenario.effect.Offset;
import jaco.mp3.player.MP3Player;
import org.jfree.layout.CenterLayout;

import java.io.File;

class Cfunc1 {

    //新增部分-----------------------------------------------------------------------------------

    int STARTYEAR = 1900;
    int ENDYEAR = 2030;

    JPanel pone = new JPanel();    //頁籤按鈕區
    JPanel ptwo = new JPanel();    //測量區
    JPanel pthr = new JPanel();    //測量結果區
    JPanel pfou = new JPanel();    //用戶管理區

    ImageIcon iconout = new ImageIcon("images/signout.png");
    ImageIcon iconuser = new ImageIcon("images/user.png");
    ImageIcon iconhome = new ImageIcon("images/home.png");
    ImageIcon icondata = new ImageIcon("images/analytics.png");
    ImageIcon iconsearch = new ImageIcon("images/search.png");
    ImageIcon heartIcon = new ImageIcon("images/result.png");
    ImageIcon cliptIcon = new ImageIcon("images/calculation.png");

    JButton btn_f = new JButton("首頁", iconhome);
    JButton btn_a = new JButton("用戶管理", iconuser);
    JButton btn_out = new JButton("登出", iconout);

    Font ft1 = new Font("微軟正黑體", Font.BOLD, 24);        //內頁文字格式
    Font ft2 = new Font("微軟正黑體", Font.BOLD, 38);        //抬頭文字格式
    Font ft3 = new Font("微軟正黑體", Font.BOLD, 18);        //按鈕文字格式
    Font ft4 = new Font("微軟正黑體", Font.BOLD, 60);        //背景文字

    JLabel d1 = new JLabel("職位");        //帳戶
    JLabel d2 = new JLabel("姓名");        //帳戶
    JLabel d3 = new JLabel("帳號");        //帳戶
    JLabel d4 = new JLabel("密碼");        //帳戶

    JButton btnd1 = new JButton("輸出");        //帳戶
    JButton btnd2 = new JButton("刪除");        //帳戶
    JButton btnd3 = new JButton("清空");        //帳戶

    JTextField tfd1 = new JTextField();            //帳戶


    //原程式部分---------------------------------------------------------------------------------

    //取得螢幕寬（w）與高（h）
    Toolkit kt = Toolkit.getDefaultToolkit();
    Dimension dm = kt.getScreenSize();
    int w = (int) dm.getWidth();
    int h = (int) dm.getHeight();

    // 讀取usb資料所用的模組物件與變數
    int[] mdata = {0};
    SerialPortListener_v1 sp = new SerialPortListener_v1(mdata); // 從usb讀Arduino資料
    CgetSerialCom_v1 getCom = new CgetSerialCom_v1();             // 偵測取得usb通訊埠物件
    String[] portList = getCom.getComPortList();                 // 存放取得的usb通訊埠

    // 計算方法
    double[] number = new double[24];
    CAL mycal = new CAL();
    Data data = new Data();
    DataBase db = new DataBase();
    RegisterData RD = new RegisterData();
    String UserName = "";

    //顏色
    Color MyColor = new Color(161, 204, 209);
    Color MyColor1 = new Color(233, 179, 132);
    Color MyColor2 = new Color(124, 157, 150);
    Color MyColor3 = new Color(244, 242, 222);

    Color color1 = new Color(0, 47, 73);            //皇家藍
    Color color2 = new Color(238, 238, 238);        //部位
    Color color3 = new Color(173, 217, 232);        //穴位
    Color color4 = new Color(144, 144, 144);        //框線
    Color color5 = new Color(112, 134, 163);        //標題
    Color color6 = new Color(112, 134, 163);
    Color color7 = new Color(214, 217, 223);

    // 執行緒
    Thread thread_readData;
    Thread thread_changeField;
    private final int STOP = -1;
    private final int SUSPEND = 0;
    private final int RUNNING = 1;
    private int thread_readData_status = STOP;
    private int thread_changeField_status = STOP;

    // 控制流程
    int fieldCount = 0;
    int pressStopMeasure = 0; // 0: not press, 1: press the STOP Measure button
    int count = 0;
    int outputcount = 0;

    // HCI物件
    JFrame f = new JFrame();

    Font font1 = new Font("微軟正黑體", Font.BOLD, 18);
    Font font2 = new Font("微軟正黑體", Font.BOLD, 15);
    Font fontb = new Font("微軟正黑體", Font.BOLD, 28);
    Font labelFont = new Font("微軟正黑體", Font.BOLD, 20);
    Font buttonFont = new Font("微軟正黑體", Font.BOLD, 18);
    Font tabFont = new Font("微軟正黑體", Font.BOLD, 20);


    JTabbedPane tabbedPane = new JTabbedPane();

    JMenuBar menuBar = new JMenuBar();
    JMenu portMenu = new JMenu("連接硬體設定");
    JMenuItem[] portMenuItem = new JMenuItem[20];
    JButton btn_start = new JButton("開始測量");
    JButton btn_stop = new JButton("取消測量");

    ImageIcon nameIcon = new ImageIcon("images/name_color.png");
    JLabel namelabel = new JLabel(nameIcon);
    ImageIcon phoneIcon = new ImageIcon("images/phone_color.png");
    JLabel phonelabel = new JLabel(phoneIcon);
    ImageIcon sexualIcon = new ImageIcon("images/gender_color.png");
    JLabel sexuallabel = new JLabel(sexualIcon);
    ImageIcon locationIcon = new ImageIcon("images/city_color.png");
    JLabel locationlabel = new JLabel(locationIcon);
    ImageIcon dateIcon = new ImageIcon("images/birthyear_color.png");
    JLabel datelabel = new JLabel(dateIcon);

    ImageIcon nameIcon_edit = new ImageIcon("images/name_color.png");
    JLabel namelabel_edit = new JLabel(nameIcon_edit);
    ImageIcon phoneIcon_edit = new ImageIcon("images/phone_color.png");
    JLabel phonelabel_edit = new JLabel(phoneIcon_edit);
    ImageIcon sexualIcon_edit = new ImageIcon("images/gender_color.png");
    JLabel sexuallabel_edit = new JLabel(sexualIcon_edit);
    ImageIcon locationIcon_edit = new ImageIcon("images/city_color.png");
    JLabel locationlabel_edit = new JLabel(locationIcon_edit);
    ImageIcon dateIcon_edit = new ImageIcon("images/birthyear_color.png");
    JLabel datelabel_edit = new JLabel(dateIcon_edit);

    ImageIcon[] positionIcon = new ImageIcon[12];
    // 350x350像素大圖用於顯示測量位置的圖
    String[] positionIconFileName = {"images/H1.png", "images/H2.png", "images/H3.png", "images/H4.png",
            "images/H5.png", "images/H6.png", "images/F1.png", "images/F2.png", "images/F3.png", "images/F4.png",
            "images/F5.png", "images/F6.png"};
    JLabel positionPicLabel = new JLabel();

    ImageIcon[] nextPositionIcon = new ImageIcon[12];
    // 320x320像素小一點的圖用於顯示下一個測量位置的圖
    JLabel nextPositionPicLabel = new JLabel();
    JLabel MainUserNameLabel = new JLabel();
    JLabel UserNameLabel = new JLabel();
    JLabel UserNameTextLabel = new JLabel();
    JLabel UserPhoneTextLabel = new JLabel();
    JLabel UserGenderTextLabel = new JLabel();
    JLabel UserBirthTextLabel = new JLabel();
    JLabel UserCityTextLabel = new JLabel();

    JLabel UserNameTextLabel_edit = new JLabel();

    JPanel settingpanel = new JPanel();
    JPanel[] pane = new JPanel[6];
    JPanel buttonpanel_left_top = new JPanel();
    JPanel buttonpanel_left = new JPanel();
    JPanel settingpanel_left = new JPanel();
    JPanel settingpanel_left_top = new JPanel();
    JPanel settingpanel_right_top = new JPanel();
    JPanel settingpanel_right_top_edit = new JPanel();
    JPanel settingpanel_right_title = new JPanel();
    JPanel settingpanel_right = new JPanel();
    JPanel settingpanel_right_down = new JPanel();
    JPanel calculatepanel_right_down = new JPanel();

    JTextField[] LRHandLabel = new JTextField[2];
    String[] LRHandLabelText = {"左手", "右手"};
    JTextField[] H1toH6PositionLabel = new JTextField[12];
    String[] H1toH6PositionLabelText = {"H1 太淵穴", "H2 大陵穴", "H3 神門穴", "H4 陽谷穴", "H5 陽池穴", "H6 陽谿穴", "H1 太淵穴", "H2 大陵穴",
            "H3 神門穴", "H4 陽谷穴", "H5 陽池穴", "H6 陽谿穴"};
    JTextField[] H1toH6MDataField = new JTextField[12];
    JTextField[] LRFootLabel = new JTextField[2];
    String[] LRFootLabelText = {"左腳", "右腳"};
    JTextField[] F1toF6PositionLabel = new JTextField[12];
    String[] F1toF6PositionLabelText = {"F1 太白穴", "F2 太衝穴", "F3 大鐘穴", "F4 束骨穴", "F5 俠溪穴", "F6 內庭穴", "F1 太白穴", "F2 太衝穴",
            "F3 大鐘穴", "F4 束骨穴", "F5 俠溪穴", "F6 內庭穴"};
    JTextField[] F1toF6MDataField = new JTextField[12];

    /*用戶管理*/
    JLabel UserSignUp = new JLabel("新增使用者");
    JLabel UserData = new JLabel("測量結果");
    JLabel userLabel = new JLabel("姓名：");
    JLabel phoneLabel = new JLabel("電話號碼:");
    JLabel genderLabel = new JLabel("性別：");
    JLabel yearJLabel = new JLabel("出生年：");
    JLabel cityLable = new JLabel("城市：");

    JLabel backgroundTextLabel = new JLabel("姓名：");
    JLabel GenderLabel = new JLabel("性別：");
    JLabel PhoneNumberLabel = new JLabel("電話號碼:");
    JLabel BirthyearLabel = new JLabel("出生年（西元）：");
    JLabel CityLabel = new JLabel("城市：");

    JLabel backgroundTextLabel_edit = new JLabel("姓名：");
    JLabel GenderLabel_edit = new JLabel("性別：");
    JLabel PhoneNumberLabel_edit = new JLabel("電話號碼：");
    JLabel BirthyearLabel_edit = new JLabel("出生年（西元）：");
    JLabel CityLabel_edit = new JLabel("城市：");

    JButton measurementdataButton = new JButton("測量數據", heartIcon);
    JButton datacalculationButton = new JButton("數據計算", cliptIcon);
    JButton outputdataButton = new JButton("輸出資料");
    JButton registerButton = new JButton("註冊");
    JButton Login = new JButton("切換");
    JButton Delete = new JButton("刪除");
    JButton Back = new JButton("返回");
    JButton ChangePhoneNumber = new JButton("編輯");
    JButton SavePhoneNumber = new JButton("儲存");

    JTextField PhoneNumberTextField = new JTextField();

    String[] UserDataText = new String[10];
    JComboBox<String> backgroundComboBox;

    JTextField userTextField = new JTextField();
    JTextField phoneTextField = new JTextField();
    JComboBox genderBox = new JComboBox<>();
    JComboBox yearBox = new JComboBox<>();
    JComboBox cityComboBox = new JComboBox<>();

    String[] title1 = {"時間", "左手數值", "左腳數值", "右手數值", "右腳數值"};
    String[][] data1 = new String[200][5];
    String[] title2 = {"時間", "平均值", "虛實值", "陰陽值", "左右值", "理想值距離"};
    String[][] data2 = new String[200][6];
    String[] title3 = {"時間", "左手數值", "左腳數值", "右手數值", "右腳數值"};
    String[][] data3 = new String[20][5];

    // Create a DefaultTableModel
    DefaultTableModel model = new DefaultTableModel(data1, title1);

    // Create a custom JTable with custom rendering
    JTable table1 = new JTable(model) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component = super.prepareRenderer(renderer, row, column);

            // Customize the cell background color
            if (row % 2 == 0) {
                component.setBackground(color3);
            } else {
                component.setBackground(Color.WHITE);
            }

            // Customize the selected cell background color
            if (isCellSelected(row, column)) {
                component.setBackground(color3); // Light Blue
            }

            return component;
        }
    };

    // Create a DefaultTableModel
    DefaultTableModel model2 = new DefaultTableModel(data2, title2);

    // Create a custom JTable with custom rendering
    JTable table2 = new JTable(model2) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component = super.prepareRenderer(renderer, row, column);

            // Customize the cell background color
            if (row % 2 == 0) {
                component.setBackground(color3);
            } else {
                component.setBackground(Color.WHITE);
            }

            // Customize the selected cell background color
            if (isCellSelected(row, column)) {
                component.setBackground(color3); // Light Blue
            }

            return component;
        }
    };

    String[] DateResult = new String[200];
    String[] LeftHandResult = new String[200];
    String[] LeftFootResult = new String[200];
    String[] RightHandResult = new String[200];
    String[] RightFootResult = new String[200];

    String PhoneReault = new String();
    String GenderResult = new String();
    String BirthyearResult = new String();
    String CityResult = new String();


    // 播放MP3音樂所需物件
    File f1 = new File("musics/music.mp3"); // 等待時的配樂
    File f2 = new File("musics/button04a.mp3"); // 進度條跳動的配樂
    File f3 = new File("musics/press.mp3"); // 切換時的配樂
    MP3Player music1_Player = new MP3Player(f1);
    MP3Player music2_Player = new MP3Player(f2);
    MP3Player music3_Player = new MP3Player(f3);
    int stepsOfAMeasure = 0; // 計數每一次測量分成6個步驟

    // 進度條
    JProgressBar progressBar = new JProgressBar();

    //折線圖
    ClineChart chart = new ClineChart();
    Graphics g;

    int[] pointValue = new int[6];   //6個點的值
    int[] px = new int[6];    //點對應的像素x座標
    int[] py = new int[6];    //點對應的像素y座標

    WriteFiles Write = new WriteFiles();

    Cfunc1() {

        //新增部分-----------------------------------------------------------------------------------

        pone.setLayout(null);
        pone.setBounds(0, 0, 1600, 50);
        pone.setVisible(true);
        f.add(pone);

        ptwo.setLayout(null);
        ptwo.setBounds(0, 50, 1600, 980);
        ptwo.setBackground(Color.white);
        ptwo.setVisible(true);
        f.add(ptwo);

        btn_f.setLayout(null);
        btn_f.setBounds(0, 0, 200, 50);
        btn_f.setFont(fontb);
        btn_f.setForeground(Color.black);
        btn_f.setBackground(Color.lightGray);
        btn_f.setBorderPainted(false);
        btn_f.setFocusPainted(false);
        btn_f.setEnabled(false);
        btn_f.addActionListener(CpBtn);
        pone.add(btn_f);

        btn_a.setLayout(null);
        btn_a.setBounds(200, 0, 200, 50);
        btn_a.setFont(fontb);
        btn_a.setForeground(Color.black);
        btn_a.setBackground(Color.lightGray);
        btn_a.setBorderPainted(false);
        btn_a.setFocusPainted(false);
        btn_a.addActionListener(CpBtn);
        pone.add(btn_a);

        btn_out.setLayout(null);
        btn_out.setBounds(1446, 0, 150, 50);
        btn_out.setFont(fontb);
        btn_out.setForeground(Color.black);
        btn_out.setBackground(Color.lightGray);
        btn_out.setBorderPainted(false);
        btn_out.setFocusPainted(false);
        btn_out.addActionListener(LogOut);
        pone.add(btn_out);

        pthr.setLayout(null);
        pthr.setBounds(0, 50, 1600, 980);
        pthr.setBackground(Color.white);
        pthr.setVisible(false);
        f.add(pthr);

        //原程式部分---------------------------------------------------------------------------------


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 添加ICON到頁籤
        ImageIcon manIcon = new ImageIcon("images/man1.png");

        JLabel manlabel = new JLabel(manIcon);
        JLabel manlabel1 = new JLabel(manIcon);

        // 建立有接上電腦的USB埠選單
        for (int x = 0; x < portList.length; x++) {
            portMenuItem[x] = new JMenuItem(portList[x]);
            portMenuItem[x].setFont(font1);
            portMenuItem[x].setBackground(Color.pink);
            portMenuItem[x].addActionListener(ProcessPortSelection);
            portMenu.add(portMenuItem[x]);
        }
        portMenu.setFont(font1);
        menuBar.add(portMenu);
        menuBar.setBounds(30, 80, 130, 50);
        ptwo.add(menuBar);

        // 開始與停止讀取USB數據
        btn_stop.setBounds(1120, 80, 200, 50);
        btn_stop.setFont(font1);
        btn_stop.setEnabled(false);
        btn_stop.addActionListener(ProcessStopReadUSB);
        btn_stop.setBackground(MyColor1);
        ptwo.add(btn_stop);

        btn_start.setBounds(1350, 80, 200, 50);
        btn_start.setFont(font1);
        btn_start.setEnabled(false);
        btn_start.addActionListener(ProcessStartReadUSB);
        btn_start.setBackground(MyColor);
        ptwo.add(btn_start);

        manlabel.setBounds(1186, (int) (5.15), 50, 40);
        pone.add(manlabel);

        JLabel MainUserLabel = new JLabel("目前使用者：");
        MainUserLabel.setFont(font1);
        MainUserLabel.setBounds(1236, (int) (5.15), 110, 40);
        pone.add(MainUserLabel);

        MainUserNameLabel.setFont(font1);
        MainUserNameLabel.setBounds(1346, (int) (5.15), 100, 40);
        pone.add(MainUserNameLabel);

        // 顯示測量點圖片
        for (int i = 0; i < positionIcon.length; i++) {
            positionIcon[i] = new ImageIcon(positionIconFileName[i]);
        }
        positionPicLabel.setIcon(null);
        positionPicLabel.setText("預備測量〔左手〕");
        positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
        positionPicLabel.setBounds(790, 160, 755, 350);
        positionPicLabel.setHorizontalAlignment(JLabel.CENTER);
        positionPicLabel.setBorder(BorderFactory.createLineBorder(MyColor, 3));
        ptwo.add(positionPicLabel);

        // 測量進度條
        progressBar.setBounds(30, 520, 1520, 50);
        progressBar.setBackground(MyColor3);
        progressBar.setForeground(Color.orange);
        // progressBar.setStringPainted(true);
        progressBar.setFont(font1);
        ptwo.add(progressBar);

        // 顯示各測量點的數值
        pane[0] = new JPanel();
        pane[0].setBounds(30, 580, 1520, 50);
        pane[0].setBackground(Color.white);
        pane[0].setLayout(new GridLayout(1, 2, 5, 5));
        ptwo.add(pane[0]);

        for (int i = 0; i < LRHandLabel.length; i++) {
            LRHandLabel[i] = new JTextField(LRHandLabelText[i]);
            LRHandLabel[i].setHorizontalAlignment(JTextField.CENTER);
            LRHandLabel[i].setBorder(BorderFactory.createLineBorder(color2, 1));
            LRHandLabel[i].setBackground(color2);
            LRHandLabel[i].setFont(font1);
            LRHandLabel[i].setEditable(false);
            pane[0].add(LRHandLabel[i]);
        }

        pane[1] = new JPanel();
        pane[1].setBounds(30, 640, 1520, 50);
        pane[1].setBackground(Color.white);
        pane[1].setLayout(new GridLayout(1, 12, 5, 5));
        ptwo.add(pane[1]);

        for (int i = 0; i < H1toH6PositionLabel.length; i++) {
            H1toH6PositionLabel[i] = new JTextField(H1toH6PositionLabelText[i]);
            H1toH6PositionLabel[i].setHorizontalAlignment(JTextField.CENTER);
            H1toH6PositionLabel[i].setBorder(BorderFactory.createLineBorder(color4, 1));
            H1toH6PositionLabel[i].setBackground(color3);
            H1toH6PositionLabel[i].setFont(font1);
            H1toH6PositionLabel[i].setEditable(false);
            pane[1].add(H1toH6PositionLabel[i]);
        }

        pane[2] = new JPanel();
        pane[2].setBounds(30, 700, 1520, 50);
        pane[2].setBackground(Color.white);
        pane[2].setLayout(new GridLayout(1, 12, 5, 5));
        ptwo.add(pane[2]);

        for (int i = 0; i < H1toH6MDataField.length; i++) {
            H1toH6MDataField[i] = new JTextField("");
            H1toH6MDataField[i].setHorizontalAlignment(JTextField.CENTER);
            H1toH6MDataField[i].setFont(font1);
            H1toH6MDataField[i].setBackground(Color.white);
            H1toH6MDataField[i].setEditable(false);
            H1toH6MDataField[i].setBorder(BorderFactory.createLineBorder(color4, 1));
            pane[2].add(H1toH6MDataField[i]);
        }

        pane[3] = new JPanel();
        pane[3].setBounds(30, 760, 1520, 50);
        pane[3].setBackground(Color.white);
        pane[3].setLayout(new GridLayout(1, 2, 5, 5));
        ptwo.add(pane[3]);

        for (int i = 0; i < LRFootLabel.length; i++) {
            LRFootLabel[i] = new JTextField(LRFootLabelText[i]);
            LRFootLabel[i].setHorizontalAlignment(JTextField.CENTER);
            LRFootLabel[i].setBorder(BorderFactory.createLineBorder(color2, 1));
            LRFootLabel[i].setBackground(color2);
            LRFootLabel[i].setFont(font1);
            LRFootLabel[i].setEditable(false);
            pane[3].add(LRFootLabel[i]);
        }

        pane[4] = new JPanel();
        pane[4].setBounds(30, 820, 1520, 50);
        pane[4].setBackground(Color.white);
        pane[4].setLayout(new GridLayout(1, 12, 5, 5));
        ptwo.add(pane[4]);

        for (int i = 0; i < F1toF6PositionLabel.length; i++) {
            F1toF6PositionLabel[i] = new JTextField(F1toF6PositionLabelText[i]);
            F1toF6PositionLabel[i].setHorizontalAlignment(JTextField.CENTER);
            F1toF6PositionLabel[i].setBorder(BorderFactory.createLineBorder(color4, 1));
            F1toF6PositionLabel[i].setBackground(color3);
            F1toF6PositionLabel[i].setFont(font1);
            F1toF6PositionLabel[i].setEditable(false);
            pane[4].add(F1toF6PositionLabel[i]);
        }

        pane[5] = new JPanel();
        pane[5].setBounds(30, 880, 1520, 50);
        pane[5].setBackground(Color.white);
        pane[5].setLayout(new GridLayout(1, 12, 5, 5));
        ptwo.add(pane[5]);

        for (int i = 0; i < F1toF6MDataField.length; i++) {
            F1toF6MDataField[i] = new JTextField("");
            F1toF6MDataField[i].setHorizontalAlignment(JTextField.CENTER);
            F1toF6MDataField[i].setBackground(Color.white);
            F1toF6MDataField[i].setFont(font1);
            F1toF6MDataField[i].setEditable(false);
            F1toF6MDataField[i].setBorder(BorderFactory.createLineBorder(color4, 1));
            pane[5].add(F1toF6MDataField[i]);
        }

        //折線圖位置
        chart.setBounds(30, 160, 755, 350);
        chart.setBorder(BorderFactory.createLineBorder(MyColor, 3));
        // 啟用 x 軸和 y 軸的網格線
        ptwo.add(chart);
        ptwo.setBounds(0, 0, 1600, 1000);

        /*顯示目前使用者*/
        manlabel1.setBounds(40, 10, 50, 50);
        settingpanel.add(manlabel1);

        JLabel User = new JLabel("目前使用者：");
        User.setFont(font1);
        User.setBounds(80, 10, 100, 50);
        settingpanel.add(User);

        UserNameLabel.setFont(font1);
        UserNameLabel.setBounds(180, 10, 100, 50);
        settingpanel.add(UserNameLabel);

        /*-------------------------------------------*/

        //用戶管理 左下頁籤按鈕區panel
        buttonpanel_left_top.setBounds(30, 300, 150, 100);
        buttonpanel_left_top.setLayout(null);

        //用戶管理 左下功能按鈕區panel
        buttonpanel_left.setBounds(30, 400, 150, 530);
        buttonpanel_left.setBackground(Color.white);
        buttonpanel_left.setLayout(null);

        /*用戶管理-測量結果-左下方測量數據按鈕*/
        measurementdataButton.setLayout(null);
        measurementdataButton.setBounds(0, 0, 150, 50);
        measurementdataButton.setForeground(Color.black);
        measurementdataButton.setBackground(Color.lightGray);
        measurementdataButton.setFont(tabFont);
        measurementdataButton.setBorderPainted(false);
        measurementdataButton.setFocusPainted(false);
        measurementdataButton.setEnabled(false);
        measurementdataButton.addActionListener(dataBtn);
        buttonpanel_left_top.add(measurementdataButton);

        /*用戶管理-測量結果-左下方數據計算按鈕*/
        datacalculationButton.setLayout(null);
        datacalculationButton.setBounds(0, 50, 150, 50);
        datacalculationButton.setForeground(Color.black);
        datacalculationButton.setBackground(Color.lightGray);
        datacalculationButton.setFont(tabFont);
        datacalculationButton.setBorderPainted(false);
        datacalculationButton.setFocusPainted(false);
        datacalculationButton.setEnabled(true);
        datacalculationButton.addActionListener(dataBtn);
        buttonpanel_left_top.add(datacalculationButton);

        /*用戶管理-測量結果-左下方輸出資料按鈕*/
        outputdataButton.setLayout(null);
        outputdataButton.setBounds(0, 480, 150, 50);
        outputdataButton.setForeground(Color.black);
        outputdataButton.setBackground(Color.lightGray);
        outputdataButton.setFont(tabFont);
        outputdataButton.setBorderPainted(false);
        outputdataButton.setFocusPainted(false);
        outputdataButton.setEnabled(true);
        outputdataButton.addActionListener(output);
        buttonpanel_left.add(outputdataButton);

        //用戶管理 左上標題panel
        //settingpanel_left_top.setBounds(30, 60, 500, 30);
        UserSignUp.setFont(font1);
        settingpanel_left_top.add(UserSignUp);

        //用戶管理 左側panel
        //settingpanel_left.setBounds(30, 100, 500, 800);
        settingpanel_left.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_left.setBackground(Color.white);
        settingpanel_left.setLayout(null);

        /*用戶管理-新增使用者-LOGO標籤*/
        namelabel.setBounds(30, 100, 50, 50);        //姓名
        settingpanel_left.add(namelabel);

        phonelabel.setBounds(30, 200, 50, 50);        //電話
        settingpanel_left.add(phonelabel);

        sexuallabel.setBounds(30, 300, 50, 50);    //性別
        settingpanel_left.add(sexuallabel);

        datelabel.setBounds(30, 400, 50, 50);        //出生年（西元）
        settingpanel_left.add(datelabel);

        locationlabel.setBounds(30, 500, 50, 50);    //城市
        settingpanel_left.add(locationlabel);


        /*用戶管理-新增使用者-文字標籤*/
        userLabel.setBounds(90, 100, 80, 50);        //姓名
        userLabel.setFont(labelFont);
        settingpanel_left.add(userLabel);

        phoneLabel.setBounds(90, 200, 120, 50);    //電話
        phoneLabel.setFont(labelFont);
        settingpanel_left.add(phoneLabel);

        genderLabel.setBounds(90, 300, 80, 50);    //性別
        genderLabel.setFont(labelFont);
        settingpanel_left.add(genderLabel);

        yearJLabel.setBounds(90, 400, 120, 50);    //出生年（西元）
        yearJLabel.setFont(labelFont);
        settingpanel_left.add(yearJLabel);

        cityLable.setBounds(90, 500, 80, 50);        //城市
        cityLable.setFont(labelFont);
        settingpanel_left.add(cityLable);

        /*用戶管理-新增使用者-下方註冊按鈕*/
        registerButton.setBounds(200, 650, 120, 50);
        registerButton.setBackground(color1);
        registerButton.setForeground(Color.white);
        registerButton.setFont(buttonFont);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(Register);
        settingpanel_left.add(registerButton);

        //用戶管理 右上標題Panel
        JLabel UserSelect = new JLabel("使用者資料");
        settingpanel_right_title.setBounds(30, 10, 1540, 30);
        UserSelect.setFont(font1);
        settingpanel_right_title.add(UserSelect);

        /*用戶管理-使用者資料-顯示*/
        settingpanel_right_top.setBounds(30, 50, 1540, 200);
        settingpanel_right_top.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_right_top.setBackground(Color.white);
        settingpanel_right_top.setLayout(null);
        settingpanel_right_top.setVisible(true);

        /*用戶管理-使用者資料-編輯*/
        settingpanel_right_top_edit.setBounds(30, 50, 1540, 200);
        settingpanel_right_top_edit.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_right_top_edit.setBackground(Color.white);
        settingpanel_right_top_edit.setLayout(null);
        settingpanel_right_top_edit.setVisible(false);

        /*用戶管理-使用者資料-LOGO標籤*/
        namelabel.setBounds(60, 50, 25, 30);                    //姓名
        settingpanel_right_top.add(namelabel);

        sexuallabel.setBounds(410, 50, 25, 30);                //性別
        settingpanel_right_top.add(sexuallabel);

        phonelabel.setBounds(60, 130, 25, 30);                //電話
        settingpanel_right_top.add(phonelabel);

        locationlabel.setBounds(410, 130, 25, 30);            //城市
        settingpanel_right_top.add(locationlabel);

        datelabel.setBounds(700, 130, 25, 30);                    //出生年（西元）
        settingpanel_right_top.add(datelabel);

        /*用戶管理-使用者資料-標題標籤*/
        backgroundTextLabel.setFont(labelFont);                 //姓名
        backgroundTextLabel.setBounds(110, 50, 60, 30);
        settingpanel_right_top.add(backgroundTextLabel);

        GenderLabel.setFont(labelFont);                        //性別
        GenderLabel.setBounds(440, 50, 60, 30);
        settingpanel_right_top.add(GenderLabel);

        PhoneNumberLabel.setFont(labelFont);                    //電話號碼
        PhoneNumberLabel.setBounds(110, 130, 100, 30);
        settingpanel_right_top.add(PhoneNumberLabel);

        CityLabel.setFont(labelFont);                            //城市
        CityLabel.setBounds(440, 130, 60, 30);
        settingpanel_right_top.add(CityLabel);

        BirthyearLabel.setFont(labelFont);                        //出生年（西元）
        BirthyearLabel.setBounds(730, 130, 160, 30);
        settingpanel_right_top.add(BirthyearLabel);

        /*用戶管理-使用者資料-內容標籤*/
        UserNameTextLabel.setFont(labelFont);
        UserNameTextLabel.setBounds(170, 50, 150, 30);            //姓名
        settingpanel_right_top.add(UserNameTextLabel);

        UserGenderTextLabel.setFont(labelFont);                    //性別
        UserGenderTextLabel.setBounds(500, 50, 150, 30);
        settingpanel_right_top.add(UserGenderTextLabel);

        UserPhoneTextLabel.setFont(labelFont);                    //電話號碼
        UserPhoneTextLabel.setBounds(210, 130, 150, 30);
        settingpanel_right_top.add(UserPhoneTextLabel);

        UserCityTextLabel.setFont(labelFont);                    //城市
        UserCityTextLabel.setBounds(500, 130, 150, 30);
        settingpanel_right_top.add(UserCityTextLabel);

        UserBirthTextLabel.setFont(labelFont);                    //出生年（西元）
        UserBirthTextLabel.setBounds(890, 130, 150, 30);
        settingpanel_right_top.add(UserBirthTextLabel);

        /*用戶管理-使用者資料-編輯-LOGO標籤*/
        namelabel_edit.setBounds(60, 50, 25, 30);                //姓名
        settingpanel_right_top_edit.add(namelabel_edit);

        sexuallabel_edit.setBounds(410, 50, 25, 30);                //性別
        settingpanel_right_top_edit.add(sexuallabel_edit);

        phonelabel_edit.setBounds(60, 130, 25, 30);                //電話
        settingpanel_right_top_edit.add(phonelabel_edit);

        locationlabel_edit.setBounds(410, 130, 25, 30);            //城市
        settingpanel_right_top_edit.add(locationlabel_edit);

        datelabel_edit.setBounds(700, 130, 25, 30);                    //出生年（西元）
        settingpanel_right_top_edit.add(datelabel_edit);

        /*用戶管理-使用者資料-編輯-標題標籤*/
        backgroundTextLabel_edit.setFont(labelFont);                //姓名
        backgroundTextLabel_edit.setBounds(110, 50, 60, 30);
        settingpanel_right_top_edit.add(backgroundTextLabel_edit);

        GenderLabel_edit.setFont(labelFont);                        //性別
        GenderLabel_edit.setBounds(440, 50, 60, 30);
        settingpanel_right_top_edit.add(GenderLabel_edit);

        PhoneNumberLabel_edit.setFont(labelFont);                    //電話號碼
        PhoneNumberLabel_edit.setBounds(110, 130, 100, 30);
        settingpanel_right_top_edit.add(PhoneNumberLabel_edit);

        CityLabel_edit.setFont(labelFont);                            //城市
        CityLabel_edit.setBounds(440, 130, 60, 30);
        settingpanel_right_top_edit.add(CityLabel_edit);

        BirthyearLabel_edit.setFont(labelFont);                    //出生年（西元）
        BirthyearLabel_edit.setBounds(730, 130, 160, 30);
        settingpanel_right_top_edit.add(BirthyearLabel_edit);

        /*用戶管理-使用者資料-內容標籤*/
        UserNameTextLabel_edit.setFont(labelFont);
        UserNameTextLabel_edit.setBounds(170, 50, 150, 30);            //姓名
        settingpanel_right_top_edit.add(UserNameTextLabel_edit);

        //電話號碼顯示及修改的文字框
        PhoneNumberTextField.setFont(font1);
        PhoneNumberTextField.setBounds(210, 130, 150, 30);
        PhoneNumberTextField.setEnabled(false);
        PhoneNumberTextField.setDocument(new JTextFieldLimit(10));
        settingpanel_right_top_edit.add(PhoneNumberTextField);

        genderBox.setBounds(500, 50, 150, 30);              //性別
        genderBox.addItem("男");
        genderBox.addItem("女");
        genderBox.setFont(font1);
        settingpanel_right_top_edit.add(genderBox);

        for (int i = STARTYEAR; i <= ENDYEAR; i++) {          //出生年（西元）
            yearBox.addItem(i);
        }
        yearBox.setBounds(890, 130, 150, 30);
        yearBox.setFont(labelFont);
        settingpanel_right_top_edit.add(yearBox);

        cityComboBox.setBounds(500, 130, 150, 30);            //城市
        cityComboBox.addItem("臺北市");
        cityComboBox.addItem("新北市");
        cityComboBox.addItem("基隆市");
        cityComboBox.addItem("桃園市");
        cityComboBox.addItem("新竹市");
        cityComboBox.addItem("苗栗縣");
        cityComboBox.addItem("臺中市");
        cityComboBox.addItem("彰化縣");
        cityComboBox.addItem("南投縣");
        cityComboBox.addItem("雲林縣");
        cityComboBox.addItem("嘉義市");
        cityComboBox.addItem("臺南市");
        cityComboBox.addItem("高雄市");
        cityComboBox.addItem("屏東縣");
        cityComboBox.addItem("宜蘭縣");
        cityComboBox.addItem("花蓮縣");
        cityComboBox.addItem("臺東縣");
        cityComboBox.addItem("澎湖縣");
        cityComboBox.addItem("金門縣");
        cityComboBox.addItem("連江縣");
        cityComboBox.setFont(font1);
        settingpanel_right_top_edit.add(cityComboBox);

        /*用戶管理-切換使用者-切換按鈕*/
        Login.setFont(font1);
        Login.setBounds(350, 50, 100, 30);
        Login.addActionListener(SwitchUser);
        Login.setFocusable(false);
        //settingpanel_right_top.add(Login);

        /*用戶管理-切換使用者-刪除按鈕*/
        Delete.setFont(font1);
        Delete.setBounds(1380, 130, 100, 30);
        Delete.addActionListener(delUser);
        Delete.setFocusable(false);
        Delete.setVisible(true);
        settingpanel_right_top.add(Delete);

        /*用戶管理-切換使用者-返回按鈕*/
        Back.setFont(font1);
        Back.setBounds(1380, 130, 100, 30);
        Back.addActionListener(CpBtn);
        Back.setFocusable(false);
        Back.setVisible(false);
        settingpanel_right_top_edit.add(Back);

        /*用戶管理-切換使用者-編輯按鈕*/
        ChangePhoneNumber.setFont(font1);
        ChangePhoneNumber.setBounds(1380, 50, 100, 30);
        ChangePhoneNumber.setFocusable(false);
        ChangePhoneNumber.setEnabled(false);
        ChangePhoneNumber.setVisible(true);
        ChangePhoneNumber.addActionListener(ChangePhoneNum);
        settingpanel_right_top.add(ChangePhoneNumber);

        /*用戶管理-切換使用者-電話號碼儲存按鈕*/
        SavePhoneNumber.setFont(font1);
        SavePhoneNumber.setBounds(1380, 50, 100, 30);
        SavePhoneNumber.setFocusable(false);
        SavePhoneNumber.addActionListener(SavePhoneNum);
        SavePhoneNumber.setVisible(false);
        settingpanel_right_top_edit.add(SavePhoneNumber);

        //用戶管理 右下標題Panel
        settingpanel_right.setBounds(30, 260, 1540, 30);
        UserData.setFont(font1);
        settingpanel_right.add(UserData);

        //用戶管理 右下測量數據記錄
        settingpanel_right_down.setBounds(180, 300, 1390, 630);
        settingpanel_right_down.setLayout(null);
        settingpanel_right_down.setVisible(true);

        //用戶管理 右下數據計算記錄
        calculatepanel_right_down.setBounds(180, 300, 1390, 630);
        calculatepanel_right_down.setLayout(null);
        calculatepanel_right_down.setVisible(false);


        //用戶管理 右下測量數據記錄 內容
        data1 = new String[][]{{"datetime", "lefthand", "leftfoot", "righthand", "rightfoot"}};
        table1.setEnabled(false);
        JTableHeader head1 = table1.getTableHeader();
        table1.getTableHeader().setReorderingAllowed(false);
        table1.setFont(labelFont);
        table1.setRowHeight(40);
        table1.setGridColor(new Color(220, 220, 220));

        //將head1字體置中
        DefaultTableCellRenderer headerRenderer1 = new DefaultTableCellRenderer();
        headerRenderer1.setHorizontalAlignment(JLabel.CENTER);
        head1.setDefaultRenderer(headerRenderer1);

        head1.setFont(labelFont);
        JScrollPane sp = new JScrollPane(table1,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setBounds(0, 0, 1390, 630);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        //用戶管理 右下數據計算記錄 內容
        data2 = new String[][]{{"datetime", "lefthand", "leftfoot", "righthand", "rightfoot"}};

        table2.setEnabled(false);
        JTableHeader head2 = table2.getTableHeader();
        table2.getTableHeader().setReorderingAllowed(false);
        table2.setFont(labelFont);
        table2.setRowHeight(40);
        table2.setGridColor(new Color(220, 220, 220));

        //將head2字體置中
        DefaultTableCellRenderer headerRenderer2 = new DefaultTableCellRenderer();
        headerRenderer2.setHorizontalAlignment(JLabel.CENTER);
        head2.setDefaultRenderer(headerRenderer2);

        head2.setFont(labelFont);
        JScrollPane sp1 = new JScrollPane(table2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp1.setBounds(0, 0, 1390, 630);

        for (int i = 0; i < table2.getColumnCount(); i++) {
            table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        settingpanel_right_down.add(sp);
        calculatepanel_right_down.add(sp1);


        pthr.add(buttonpanel_left_top);
        pthr.add(buttonpanel_left);
        pthr.add(settingpanel_right_top);
        pthr.add(settingpanel_right_top_edit);
        pthr.add(settingpanel_right_title);
        pthr.add(settingpanel_right);
        pthr.add(settingpanel_right_down);
        pthr.add(calculatepanel_right_down);


        f.setLayout(null);
        f.setBounds((int) (0.085 * w), 0, 1600, 1030);
        f.setVisible(true);
        f.setResizable(false);
        f.setIconImage(new ImageIcon("images/logo.png").getImage());
        f.setTitle("良導絡經絡測量系統");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

//----------------------------------------------------------------------
//方法程式區
//----------------------------------------------------------------------

    //限制輸入長度
    public class JTextFieldLimit extends PlainDocument {
        private int limit;

        public JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;
            if (getLength() + str.length() <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    // 清空資訊回歸初始
    public void clearDisplay() {

        for (int i = 0; i < H1toH6MDataField.length; i++) {
            H1toH6MDataField[i].setText("");
        }
        for (int i = 0; i < F1toF6MDataField.length; i++) {
            F1toF6MDataField[i].setText("");
        }
        positionPicLabel.setIcon(null);
        positionPicLabel.setText("預備測量[左手]");
        positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
        nextPositionPicLabel.setIcon(nextPositionIcon[0]);

        progressBar.setValue(0);
        music1_Player.stop();
        music2_Player.stop();
        music3_Player.stop();

        for (int x = 0; x < chart.pointValue.length; x++) chart.pointValue[x] = -1; //清空畫圖點數據
        g = chart.getGraphics();       //重新取得畫布
        chart.paintComponent(g);
    }

    // 依照傳入的fieldCount值把量測資料顯示到對應欄位
    public synchronized void showMeasureData(int fieldCount) {
        if (stepsOfAMeasure != -1) {
            if (fieldCount >= 1 && fieldCount < 7 && stepsOfAMeasure < 6) {
                H1toH6MDataField[fieldCount - 1].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //設定點的數值
            } else if (fieldCount >= 8 && fieldCount <= 13 && stepsOfAMeasure < 6) {
                H1toH6MDataField[fieldCount - 2].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //設定點的數值
            } else if (fieldCount >= 15 && fieldCount <= 20 && stepsOfAMeasure < 6) {
                F1toF6MDataField[fieldCount - 15].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //設定點的數值
            } else if (fieldCount >= 22 && fieldCount <= 27 && stepsOfAMeasure < 6) {
                F1toF6MDataField[fieldCount - 16].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //設定點的數值
            } else {
            }

            try {
                if (fieldCount == 0 || fieldCount == 7 || fieldCount == 14 || fieldCount == 21) { // 預備、左右與手腳切換時不播放
                    if (stepsOfAMeasure > -1)
                        music1_Player.play();
                    while (!music1_Player.isStopped()) {
                        Thread.sleep(5);
                    }
                    stepsOfAMeasure = -1;
                    progressBar.setValue(0);
                }
                if (fieldCount != 0 && fieldCount != 7 && fieldCount != 14 && fieldCount != 21) { // 左右與手腳切換時不播放
                    if (stepsOfAMeasure < 6) {
                        // System.out.println("Steps="+String.valueOf(stepsOfAMeasure));
                        progressBar.setValue(17 * (stepsOfAMeasure + 1));
                        g = chart.getGraphics();       //取得畫布
                        chart.paintComponent(g);       //執行畫圖
                        music2_Player.play();
                        while (!music2_Player.isStopped()) {
                            Thread.sleep(10);
                        }
                        stepsOfAMeasure = stepsOfAMeasure + 1;

                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            stepsOfAMeasure = 0;
        }

    }

    // 依照傳入的fieldCount值把量測資料顯示到對應欄位
    public synchronized void changePicture(int fieldCount) {

        if (fieldCount >= 1 && fieldCount < 7) {
            positionPicLabel.setText("");
            positionPicLabel.setIcon(positionIcon[fieldCount - 1]);
            if (fieldCount == 6)
                nextPositionPicLabel.setIcon(nextPositionIcon[0]);
            else
                nextPositionPicLabel.setIcon(nextPositionIcon[fieldCount]);
        } else if (fieldCount >= 8 && fieldCount <= 13) {
            positionPicLabel.setText("");
            positionPicLabel.setIcon(positionIcon[fieldCount - 8]);
            nextPositionPicLabel.setIcon(nextPositionIcon[fieldCount - 7]);
        } else if (fieldCount >= 15 && fieldCount <= 20) {
            positionPicLabel.setText("");
            positionPicLabel.setIcon(positionIcon[fieldCount - 15 + 6]);
            if (fieldCount == 20)
                nextPositionPicLabel.setIcon(nextPositionIcon[6]);
            else
                nextPositionPicLabel.setIcon(nextPositionIcon[fieldCount - 14 + 6]);
        } else if (fieldCount >= 22 && fieldCount <= 27) {
            positionPicLabel.setText("");
            positionPicLabel.setIcon(positionIcon[fieldCount - 22 + 6]);
            if (fieldCount == 27) {
                nextPositionPicLabel.setIcon(null);
            } else {
                nextPositionPicLabel.setIcon(nextPositionIcon[fieldCount - 21 + 6]);
            }
        } else if (fieldCount == 0) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("預備測量〔左手〕");
            positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[0]);
        } else if (fieldCount == 7) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("預備測量〔右手〕");
            positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[0]);
        } else if (fieldCount == 14) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("預備測量〔左腳〕");
            positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[6]);
        } else if (fieldCount == 21) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("預備測量〔右腳〕");
            positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[6]);
        } else if (fieldCount == 28) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("完成測量");
            positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(null);

            //是否儲存資料
            int result = JOptionPane.showConfirmDialog(f, "是否要儲存本次測量資料？", "測量完成", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
                String dateStr = dateFormat.format(date);
                data.setDatetime(dateStr);
                System.out.println(dateStr);

                data.setUser(UserName);
                System.out.println(UserName);

                String LeftHands = new String();
                LeftHands = H1toH6MDataField[0].getText() + " " + H1toH6MDataField[1].getText() + " " + H1toH6MDataField[2].getText() + " " + H1toH6MDataField[3].getText() + " " + H1toH6MDataField[4].getText() + " " + H1toH6MDataField[5].getText();
                data.setLefthand(LeftHands);
                System.out.println(LeftHands);

                String LeftFoot = new String();
                LeftFoot = F1toF6MDataField[0].getText() + " " + F1toF6MDataField[1].getText() + " " + F1toF6MDataField[2].getText() + " " + F1toF6MDataField[3].getText() + " " + F1toF6MDataField[4].getText() + " " + F1toF6MDataField[5].getText();
                data.setLeftfoot(LeftFoot);
                System.out.println(LeftFoot);

                String RightHand = new String();
                RightHand = H1toH6MDataField[6].getText() + " " + H1toH6MDataField[7].getText() + " " + H1toH6MDataField[8].getText() + " " + H1toH6MDataField[9].getText() + " " + H1toH6MDataField[10].getText() + " " + H1toH6MDataField[11].getText();
                data.setRighthand(RightHand);
                System.out.println(RightHand);

                String RightFoot = new String();
                RightFoot = F1toF6MDataField[6].getText() + " " + F1toF6MDataField[7].getText() + " " + F1toF6MDataField[8].getText() + " " + F1toF6MDataField[9].getText() + " " + F1toF6MDataField[10].getText() + " " + F1toF6MDataField[11].getText();
                data.setRightfoot(RightFoot);
                System.out.println(RightFoot);

                db.InsertData(data);
                btn_start.setEnabled(true);
                btn_stop.setEnabled(false);
                btn_a.setEnabled(true);
                btn_out.setEnabled(true);
                backgroundComboBox.setEnabled(true);
            }
        }
        stepsOfAMeasure = -1;
        progressBar.setValue(0);
        for (int x = 0; x < chart.pointValue.length; x++) chart.pointValue[x] = -1; //清空畫圖點數據
    }

    //登入後接收使用者名稱
    public void setUser(String USER) {
        UserName = USER;
        MainUserNameLabel.setText(UserName);
        UserNameLabel.setText(UserName);
        UserNameTextLabel.setText(UserName);
        UserNameTextLabel_edit.setText(UserName);

        //將下拉選單預設選中為目前使用者
        UserDataText = db.SelectUserDESC(UserName);
        ArrayList<String> backgroundOptionsList = new ArrayList<>();
        for (int i = 0; i < UserDataText.length; i++) {
            if (!UserDataText[i].equals("null")) {
                backgroundOptionsList.add(UserDataText[i]);
            }
        }
        String[] backgroundOptions = backgroundOptionsList.toArray(new String[0]);
        backgroundComboBox = new JComboBox<>(backgroundOptions);
        backgroundComboBox.setFont(font1);
        backgroundComboBox.setBounds(190, 80, 130, 50);
        backgroundComboBox.addActionListener(SelectUserBox);
        ptwo.add(backgroundComboBox);
    }

    //取得資料庫中每一筆測量記錄到特定變數
    public void LogCal() {
        String RightHandNumber[] = new String[db.getRighthandResult().length];
        double[][] RightHandArrays = new double[RightHandNumber.length][];
        String RightFootNumber[] = new String[db.getRightfootResult().length];
        double[][] RightFootArrays = new double[RightFootNumber.length][];
        String LeftFootNumber[] = new String[db.getLeftfootResult().length];
        double[][] LeftFootArrays = new double[LeftFootNumber.length][];
        String TimeNumber[] = new String[db.getDateResult().length];
        String LeftHandNumber[] = new String[db.getLefthandResult().length];
        double[][] LeftHandArrays = new double[LeftHandNumber.length][];


        //時間TimeArrays
        TimeNumber = db.getDateResult();

        //左手數值LeftHandArrays
        LeftHandNumber = db.getLefthandResult();

        for (int i = 0; i < LeftHandNumber.length; i++) {
            String[] stringArray = new String[LeftHandNumber.length];
            if (LeftHandNumber[i] != null) {
                stringArray = LeftHandNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }

            LeftHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }
        }

        //左腳數值LeftFootArrays
        LeftFootNumber = db.getLeftfootResult();

        for (int i = 0; i < LeftFootNumber.length; i++) {
            String[] stringArray = new String[LeftFootNumber.length];
            if (LeftFootNumber[i] != null) {
                stringArray = LeftFootNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }

            LeftFootArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftFootArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        //右手數值RightHandArrays
        RightHandNumber = db.getRighthandResult();

        for (int i = 0; i < RightHandNumber.length; i++) {
            String[] stringArray = new String[RightHandNumber.length];
            if (RightHandNumber[i] != null) {
                stringArray = RightHandNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }

            RightHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                RightHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        //右腳數值RightFootArrays
        RightFootNumber = db.getRightfootResult();

        for (int i = 0; i < RightFootNumber.length; i++) {
            String[] stringArray = new String[RightFootNumber.length];
            if (RightFootNumber[i] != null) {
                stringArray = RightFootNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }

            RightFootArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                RightFootArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }


        for (int j = 0; j < TimeNumber.length; j++) {
            if (DateResult[j] != null) {
                //將一筆資料進行運算
                double[] Log = new double[24];
                //將資料庫中一筆測量記錄存到Log[]內
                for (int i = 0; i <= 5; i++) {
                    Log[i] = LeftHandArrays[j][i];
                    Log[i + 6] = LeftFootArrays[j][i];
                    Log[i + 12] = RightHandArrays[j][i];
                    Log[i + 18] = RightFootArrays[j][i];
                }
                // 開始計算數值
                System.out.print("\n");
                mycal.Getnumber(Log);
                System.out.print("\n");
                mycal.avg();
                mycal.FR();
                mycal.YY();
                mycal.LR();
                mycal.CalDistance();
                mycal.CheckTheData();

                //資料格式化 只顯示到小數點後三位
                String formattedAvg = String.format("%.3f", mycal.CSUM);
                String formattedFRSUM = String.format("%.3f", mycal.CFRSUM);
                String formattedYY = String.format("%.3f", mycal.CYY);
                String formattedLR = String.format("%.3f", mycal.CLR);
                String formattedSTD = String.format("%.3f", mycal.CStandard);


                //傳送數據到數據計算table
                if (DateResult[count] != null) {
                    table2.setValueAt(DateResult[count], count, 0);
                    table2.setValueAt(formattedAvg, count, 1);
                    table2.setValueAt(formattedFRSUM, count, 2);
                    table2.setValueAt(formattedYY, count, 3);
                    table2.setValueAt(formattedLR, count, 4);
                    table2.setValueAt(formattedSTD, count, 5);
                }
                count++;


                mycal.ToMyString();
//                mycal.showdetail();
                mycal.ToDefault();


            }
        }
    }

    public void LogCalOutPut() {
        //時間TimeNumber
        String TimeNumber[] = new String[db.getDateResult().length];
        TimeNumber = db.getDateResult();
        System.out.println(TimeNumber[0]);

        //左手數值LeftHandArrays
        String LeftHandNumber[] = new String[db.getLefthandResult().length];
        double[][] LeftHandArrays = new double[LeftHandNumber.length][];
        LeftHandNumber = db.getLefthandResult();
        for (int i = 0; i < LeftHandNumber.length; i++) {
            String[] stringArray = new String[LeftHandNumber.length];
            if (LeftHandNumber[i] != null) {
                stringArray = LeftHandNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }
            LeftHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }
        }

        //左腳數值LeftFootArrays
        String LeftFootNumber[] = new String[db.getLeftfootResult().length];
        double[][] LeftFootArrays = new double[LeftFootNumber.length][];
        LeftFootNumber = db.getLeftfootResult();
        for (int i = 0; i < LeftFootNumber.length; i++) {
            String[] stringArray = new String[LeftFootNumber.length];
            if (LeftFootNumber[i] != null) {
                stringArray = LeftFootNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }
            LeftFootArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftFootArrays[i][j] = Integer.parseInt(stringArray[j]);
            }
        }

        //右手數值RightHandArrays
        String RightHandNumber[] = new String[db.getRighthandResult().length];
        double[][] RightHandArrays = new double[RightHandNumber.length][];
        RightHandNumber = db.getRighthandResult();
        for (int i = 0; i < RightHandNumber.length; i++) {
            String[] stringArray = new String[RightHandNumber.length];
            if (RightHandNumber[i] != null) {
                stringArray = RightHandNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }
            RightHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                RightHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        //右腳數值RightFootArrays
        String RightFootNumber[] = new String[db.getRightfootResult().length];
        double[][] RightFootArrays = new double[RightFootNumber.length][];
        RightFootNumber = db.getRightfootResult();
        for (int i = 0; i < RightFootNumber.length; i++) {
            String[] stringArray = new String[RightFootNumber.length];
            if (RightFootNumber[i] != null) {
                stringArray = RightFootNumber[i].split(" "); // 去除字串中的空格
            } else {
                break;
            }
            RightFootArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                RightFootArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        for (int j = 0; j < TimeNumber.length; j++) {
            if (TimeNumber[j] != null) {
                //將一筆資料進行運算
                double[] Log = new double[24];
                // 開始計算數值
                for (int i = 0; i <= 5; i++) {
                    Log[i] = LeftHandArrays[j][i];
                    Log[i + 6] = LeftFootArrays[j][i];
                    Log[i + 12] = RightHandArrays[j][i];
                    Log[i + 18] = RightFootArrays[j][i];
                }
                outputcount++;

                System.out.print("\n");
                mycal.Getnumber(Log);
                System.out.print("\n");
                mycal.avg();
                mycal.FR();
                mycal.YY();
                mycal.LR();
                mycal.CalDistance();
                mycal.CheckTheData();
                mycal.ToMyString();

                //傳送數據到WriteFiles
                Write.main(mycal.StrData, mycal.StrAvg, mycal.StrFRSUM, mycal.StrYY, mycal.StrLR, outputcount, mycal.StrSTD);
                mycal.ToDefault();

            }
        }
    }

//----------------------------------------------------------------------
//事件傾聽器程式區
//----------------------------------------------------------------------

    public ActionListener CpBtn = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btn_f) {

                btn_f.setEnabled(false);
                btn_a.setEnabled(true);
                ptwo.setVisible(true);
                pthr.setVisible(false);

            }

            if (e.getSource() == btn_a) {

                btn_f.setEnabled(true);
                btn_a.setEnabled(false);
                ptwo.setVisible(false);
                pthr.setVisible(true);

                UserName = MainUserNameLabel.getText();

                db.UserLog(UserName);
                db.UserDetail(UserName);

                DateResult = db.getDateResult();
                LeftHandResult = db.getLefthandResult();
                LeftFootResult = db.getLeftfootResult();
                RightHandResult = db.getRighthandResult();
                RightFootResult = db.getRightfootResult();

                //修改table1內可以顯示的資料列數
                for (int row = 0; row < db.getDateResult().length; row++) {
                    if (DateResult[row] != null) {
                        table1.setValueAt(DateResult[row], row, 0);
                        table1.setValueAt(LeftHandResult[row], row, 1);
                        table1.setValueAt(LeftFootResult[row], row, 2);
                        table1.setValueAt(RightHandResult[row], row, 3);
                        table1.setValueAt(RightFootResult[row], row, 4);
                    }
                }

                LogCal();

                PhoneReault = db.getPhoneResult();
                GenderResult = db.getGenderResult();
                BirthyearResult = db.getBirthyearResult();
                CityResult = db.getCityResult();
                if (CityResult.equals("屏東市")) {
                    CityResult = "屏東縣";
                }
                UserPhoneTextLabel.setText(PhoneReault);
                UserGenderTextLabel.setText(GenderResult);
                UserBirthTextLabel.setText(BirthyearResult);
                UserCityTextLabel.setText(CityResult);
                PhoneNumberTextField.setText(PhoneReault);
                ChangePhoneNumber.setEnabled(true);
                PhoneNumberTextField.setEnabled(false);

                db.CleanDate();

            }

            if (e.getSource() == Back) {

                settingpanel_right_top.setVisible(true);
                settingpanel_right_top_edit.setVisible(false);
                ChangePhoneNumber.setVisible(true);
                SavePhoneNumber.setVisible(false);
                Delete.setVisible(true);
                Back.setVisible(false);

            }
        }
    };

    public ActionListener dataBtn = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == measurementdataButton) {

                measurementdataButton.setEnabled(false);
                datacalculationButton.setEnabled(true);
                settingpanel_right_down.setVisible(true);
                calculatepanel_right_down.setVisible(false);

            }

            if (e.getSource() == datacalculationButton) {

                measurementdataButton.setEnabled(true);
                datacalculationButton.setEnabled(false);
                settingpanel_right_down.setVisible(false);
                calculatepanel_right_down.setVisible(true);

            }

        }
    };

    public ActionListener output = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            db.UserLog(UserName);
            db.UserDetail(UserName);
            DateResult = db.getDateResult();
            LeftHandResult = db.getLefthandResult();
            LeftFootResult = db.getLeftfootResult();
            RightHandResult = db.getRighthandResult();
            RightFootResult = db.getRightfootResult();

            mycal.ToDefault();
            LogCalOutPut();
            db.CleanDate();
            DateResult = null;
            LeftHandResult = null;
            LeftFootResult = null;
            RightHandResult = null;
            RightFootResult = null;
        }
    };

    public ActionListener LogOut = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            LoginFrame login = new LoginFrame();
            f.dispose();

        }

    };

    public ActionListener ProcessPortSelection = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            JMenuItem selPortItem = (JMenuItem) e.getSource();
            for (int i = 0; i < portList.length; i++) {
                if (selPortItem == portMenuItem[i]) {
                    sp.openPort(portList[i]);
                    sp.startRead(0);
                    btn_start.setEnabled(true);
                }
            }

        }
    };

    public ActionListener ProcessStartReadUSB = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            thread_readData_status = RUNNING;
            thread_changeField_status = RUNNING;
            pressStopMeasure = 0;
            btn_stop.setEnabled(true);
            btn_out.setEnabled(false);
            btn_a.setEnabled(false);
            backgroundComboBox.setEnabled(false);
            fieldCount = 0;
            stepsOfAMeasure = 0;
            final int[] timeInterval = {10000, 5000, 5000, 5000, 5000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000,
                    5000, 20000, 5000, 5000, 5000, 5000, 5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000, 5000};

            clearDisplay();

            thread_readData = new Thread(new Runnable() {
                public synchronized void run() {

                    try {
                        sp.write("2"); // 停止Arduino傳送資料的控制訊號為"2"(預防前一次未停止測量，故先停止後再啟動)
                        Thread.sleep(100);
                        sp.write("1"); // 啟動Arduino傳送資料的控制訊號為"1"
                    } catch (InterruptedException ioe) {
                        ioe.printStackTrace();
                    }

                    while (thread_readData_status == RUNNING) {
                        try {
                            showMeasureData(fieldCount);
                            Thread.sleep(200);
                        } catch (InterruptedException ioe) {
                            ioe.printStackTrace();
                        }

                    }

                }
            });

            thread_changeField = new Thread(new Runnable() {
                public synchronized void run() {

                    while (thread_changeField_status == RUNNING) {
                        try {

                            Thread.sleep(timeInterval[fieldCount]);
                            if (pressStopMeasure == 0) {
                                music3_Player.play();
                                for (int x = 0; x < chart.pointValue.length; x++) chart.pointValue[x] = -1; //清空畫圖點數據
                                g = chart.getGraphics();       //取得畫布
                                chart.paintComponent(g);
                            }

                            while (!music3_Player.isStopped()) {
                                Thread.sleep(10);
                            }
                            fieldCount = fieldCount + 1;
                            if (pressStopMeasure == 0)
                                changePicture(fieldCount);
                            stepsOfAMeasure = -1; // 每個測量分割成6個steps
                            Thread.sleep(10);

                            if (fieldCount == 28) {
                                positionPicLabel.setIcon(null);
                                positionPicLabel.setText("完成測量");
                                positionPicLabel.setFont(new Font("微軟正黑體", Font.BOLD, 36));
                                thread_changeField_status = STOP;
                                thread_readData_status = STOP;
                                btn_start.setEnabled(true);
                            }

                        } catch (InterruptedException ioe) {
                            ioe.printStackTrace();
                        }

                    }

                }
            });

            thread_readData.start(); // 抓取測量資料的執行緒啟動
            thread_changeField.start(); // 啟動自動變換測量點的執行緒

            btn_start.setEnabled(false);

        }
    };

    public ActionListener ProcessStopReadUSB = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            try {
                pressStopMeasure = 1;
                thread_readData_status = STOP;
                thread_changeField_status = STOP;
                fieldCount = 0;
                stepsOfAMeasure = -1;
                progressBar.setValue(0);
                clearDisplay();
                sp.write("2"); // 停止Arduino傳送資料的控制訊號為"2"
                Thread.sleep(20); // test: 200, 20
                stepsOfAMeasure = -1;
                fieldCount = 0;
                clearDisplay();

                Thread.sleep(1000);
                btn_start.setEnabled(true);
                btn_stop.setEnabled(false);
                btn_out.setEnabled(true);
                btn_a.setEnabled(true);
                backgroundComboBox.setEnabled(true);

            } catch (InterruptedException ioe) {
                ioe.printStackTrace();
            }
        }
    };

    //切換使用者
    public ActionListener SwitchUser = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if ((String) backgroundComboBox.getSelectedItem() != "選擇使用者") {
                UserName = (String) backgroundComboBox.getSelectedItem();
                MainUserNameLabel.setText(UserName);
                UserNameLabel.setText(UserName);

                db.UserLog(UserName);
                db.UserDetail(UserName);

                DateResult = db.getDateResult();
                LeftHandResult = db.getLefthandResult();
                LeftFootResult = db.getLeftfootResult();
                RightHandResult = db.getRighthandResult();
                RightFootResult = db.getRightfootResult();

                for (int row = 0; row < 20; row++) {
                    if (DateResult[row] != null) {
                        table1.setValueAt(DateResult[row], row, 0);
                        table1.setValueAt(LeftHandResult[row], row, 1);
                        table1.setValueAt(LeftFootResult[row], row, 2);
                        table1.setValueAt(RightHandResult[row], row, 3);
                        table1.setValueAt(RightFootResult[row], row, 4);
                    }
                }

                PhoneReault = db.getPhoneResult();
                PhoneNumberTextField.setText(PhoneReault);
                ChangePhoneNumber.setEnabled(true);
                PhoneNumberTextField.setEnabled(false);

                db.CleanDate();
                System.out.println("使用者已切換成：" + UserName);

            } else if (backgroundComboBox.getSelectedItem() == "選擇使用者") {
                ChangePhoneNumber.setEnabled(false);
            }

        }
    };

    //編輯使用者資料
    public ActionListener ChangePhoneNum = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            settingpanel_right_top.setVisible(false);
            settingpanel_right_top_edit.setVisible(true);
            ChangePhoneNumber.setVisible(false);
            SavePhoneNumber.setVisible(true);
            Delete.setVisible(false);
            Back.setVisible(true);
            PhoneNumberTextField.setEnabled(true);

            //從資料庫接收使用者資料
            CityResult = UserCityTextLabel.getText();
            BirthyearResult = UserBirthTextLabel.getText();
            GenderResult = UserGenderTextLabel.getText();
            PhoneReault = UserPhoneTextLabel.getText();

            //預設選中使用者資料
            cityComboBox.setSelectedItem(CityResult);
            int Birthyear = Integer.parseInt(BirthyearResult);//因為yearBox內的item資料型態為int 所以必須做型態的轉換
            yearBox.setSelectedItem(Birthyear);
            genderBox.setSelectedItem(GenderResult);
            UserPhoneTextLabel.setText(PhoneReault);

        }
    };

    //儲存編輯後的使用者資料
    public ActionListener SavePhoneNum = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            settingpanel_right_top.setVisible(true);
            settingpanel_right_top_edit.setVisible(false);
            ChangePhoneNumber.setVisible(true);
            SavePhoneNumber.setVisible(false);
            Delete.setVisible(true);
            Back.setVisible(false);
            PhoneNumberTextField.setEnabled(false);

            //將資料傳入資料庫內
            data.setUser(UserName);
            data.setPhone(PhoneNumberTextField.getText());
            data.setBirth((Integer) yearBox.getSelectedItem());
            data.setGender((String) genderBox.getSelectedItem());
            data.setcity((String) cityComboBox.getSelectedItem());
            db.UpdatePhone();
            db.UpdateBirth();
            db.UpdateCity();
            db.UpdateGender();

            //更新顯示資料
            UserGenderTextLabel.setText(data.getGender());
            UserPhoneTextLabel.setText(data.getPhone());
            UserBirthTextLabel.setText(String.valueOf(data.getBirth()));
            UserCityTextLabel.setText(data.getCity());
            JOptionPane.showMessageDialog(null, "資料修改完成！");
        }
    };

    //選中使用者
    public ActionListener SelectUserBox = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            for (int row = 0; row < 20; row++) {
                for (int col = 0; col < 5; col++) {
                    table1.setValueAt(null, row, col);
                }
            }
            PhoneNumberTextField.setText("");
            SavePhoneNumber.setVisible(false);
            ChangePhoneNumber.setVisible(true);
            ChangePhoneNumber.setEnabled(false);
            db.CleanDate();

            //選中的測量使用者
            String SelectedUser = (String) backgroundComboBox.getSelectedItem();
            UserName = SelectedUser;
            System.out.println("將為此使用者測量:" + UserName);

        }
    };

    //註冊新使用者
    public ActionListener Register = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (db.CountUser() < 10) {
                String name = userTextField.getText();
                String phone = phoneTextField.getText();
                String gender = genderBox.getSelectedItem().toString();
                String birthyear = yearBox.getSelectedItem().toString();
                String city = cityComboBox.getSelectedItem().toString();
                if (name.equals("") || phone.equals("") || gender.equals("請選擇") || birthyear.equals("") || city.equals("請選擇")) {
                    JOptionPane.showMessageDialog(null, "所有的欄位都要填寫!!");
                } else {
                    RD.setName(name);
                    RD.setPhone(phone);
                    RD.setGender(gender);
                    RD.setBirthyear(birthyear);
                    RD.setCity(city);
                    db.RegisterInsertData(RD);

                    //加入選擇使用者欄位中
                    backgroundComboBox.addItem(name);

                    //清空註冊欄位
                    userTextField.setText("");
                    phoneTextField.setText("");
                    genderBox.setSelectedIndex(0);
                    yearBox.setSelectedIndex(0);
                    cityComboBox.setSelectedIndex(0);

                    //清空變數
                    name = "";
                    phone = "";
                    gender = "";
                    birthyear = "";
                    city = "";
                }
            } else {
                JOptionPane.showMessageDialog(null, "使用者已達上限（10）！", "操作警訊", JOptionPane.ERROR_MESSAGE);
                //清空註冊欄位
                userTextField.setText("");
                phoneTextField.setText("");
                genderBox.setSelectedIndex(0);
                yearBox.setSelectedIndex(0);
                cityComboBox.setSelectedIndex(0);
            }
        }
    };

    //刪除使用者
    public ActionListener delUser = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //加入二次詢問 以免誤刪
            int result = JOptionPane.showConfirmDialog(f, "是否要刪除使用者，測量記錄也會刪除？", "注意!!!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                data.setDeleteUser(UserName);//要刪除的使用者
                db.DeleteUser();    //刪除使用者
                db.DeleteLog();     //刪除測量記錄
                //退出到登入畫面
                LoginFrame login = new LoginFrame();
                f.dispose();
            }
        }
    };

    //折線圖
    class ClineChart extends JPanel {

        String[] xScaleStr = {"0", "1", "2", "3", "4", "5"};  //x軸刻度
        String[] yScaleStr = {"0", "50", "100", "150", "200", "250"};     //y軸刻度
        int[] pointValue = new int[6];   //6個點的值
        int[] px = new int[6];    //點對應的像素x座標
        int[] py = new int[6];    //點對應的像素y座標


        ClineChart() {
            setSize(755, 280);    //ClineChart本身就是JPanel，直接對其設大小
            for (int i = 0; i < pointValue.length; i++)
                pointValue[i] = -1;     //預設-1，不會畫出<0的點
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new Color(238, 238, 238));
            g2.fillRect(50, 40, 650, 280); // 填上原始顏色，即清空

            int xAxisStart = 50;
            int xAxisEnd = 700;
            int yAxisStart = 320;
            int yAxisEnd = 40;

            // Draw top border line
            g2.setStroke(new BasicStroke(3.0f));
            g2.setColor(Color.black);
            g2.drawLine(xAxisStart, yAxisEnd, xAxisEnd, yAxisEnd);

            // Draw left border line
            g2.drawLine(xAxisStart, yAxisStart, xAxisStart, yAxisEnd);

            // Draw right border line
            g2.drawLine(xAxisEnd, yAxisStart, xAxisEnd, yAxisEnd);

            // Draw x-axis
            g2.setStroke(new BasicStroke(3.0f));
            g2.setColor(Color.black);
            g2.drawLine(xAxisStart, yAxisStart, xAxisEnd, yAxisStart);

            // Draw x-axis scale values and dashed lines
            g2.setColor(Color.black);
            g2.setFont(font2);
            float[] xdashPattern = {5, 5};
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, xdashPattern, 2.0f));
            for (int i = 0; i < 6; i++) {
                int x = xAxisStart + 116 * i;
                g2.drawString(xScaleStr[i], x, yAxisStart + 20); // Adjust the Y coordinate for x-axis labels
                g2.drawLine(x, yAxisStart, x, yAxisEnd); // Draw dashed lines for x-axis

            }

            // Draw y-axis
            g2.setStroke(new BasicStroke(3.0f));
            g2.setColor(Color.black);
            g2.drawLine(xAxisStart, yAxisEnd, xAxisStart, yAxisStart);

            // Draw y-axis scale values and dashed lines
            g2.setColor(Color.black);
            g2.setFont(font2);
            float[] dashPattern = {5, 5};
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dashPattern, 2.0f));
            for (int i = 0; i < 6; i++) {
                int y = yAxisStart - i * 56;
                g2.drawString(yScaleStr[i], xAxisStart - 30, y); // Adjust the X coordinate for y-axis labels
                g2.drawLine(xAxisStart, y, xAxisEnd, y); // Draw dashed lines for y-axis
            }

            // 畫折線圖
            px[0] = xAxisStart;
            py[0] = yAxisStart;
            for (int i = 1; i < pointValue.length; i++) {
                if (pointValue[i] >= 0) {
                    px[i] = xAxisStart + 116 * i;
                    py[i] = yAxisStart - (int) (0.116 * pointValue[i]);
                    // 創建一個不使用虛線樣式的 BasicStroke 物件
                    g2.setStroke(new BasicStroke(3.0f));


                    g2.setColor(Color.black);
                    g2.drawLine(px[i - 1], py[i - 1], px[i], py[i]);

                    // 畫黑色實心小圓形
                    if (xScaleStr[i].equals("5")) {
                        g2.setColor(MyColor1);
                        int circleSize = 10; // 圓的大小
                        g2.fillOval(px[i] - circleSize / 2, py[i] - circleSize / 2, circleSize, circleSize);
                    }

                }
            }
        }
    }

}
