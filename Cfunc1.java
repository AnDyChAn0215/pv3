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

    JButton btn_f = new JButton("首頁", iconhome);
    JButton btn_a = new JButton("測量結果", icondata);
    JButton btn_b = new JButton("用戶管理", iconuser);
    JButton btn_out = new JButton("登出", iconout);
    JButton btn_search = new JButton(iconsearch);        //搜尋按鈕

    Font ft1 = new Font("微軟正黑體", Font.BOLD, 24);        //內頁文字格式
    Font ft2 = new Font("微軟正黑體", Font.BOLD, 38);        //抬頭文字格式
    Font ft3 = new Font("微軟正黑體", Font.BOLD, 18);        //按鈕文字格式
    Font ft4 = new Font("微軟正黑體", Font.BOLD, 60);        //背景文字

    JPanel pd = new JPanel();        //帳戶管理介面
    JPanel pd1 = new JPanel();        //右1容器(帳戶)
    JPanel pd2 = new JPanel();        //右2容器(帳戶)
    JPanel pd3 = new JPanel();        //右3容器(帳戶)
    JPanel pd4 = new JPanel();        //右4容器(帳戶)
    JPanel pd5 = new JPanel();        //右5容器(帳戶)

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
    int outputcount = 0;

    // HCI物件
    JFrame f = new JFrame();

    Font font1 = new Font("微軟正黑體", Font.BOLD, 18);
    Font font2 = new Font("微軟正黑體", Font.BOLD, 15);
    Font fontb = new Font("微軟正黑體", Font.BOLD, 28);
    Font labelFont = new Font("微軟正黑體", Font.BOLD, 20);
    Font buttonFont = new Font("微軟正黑體", Font.BOLD, 18);


    JTabbedPane tabbedPane = new JTabbedPane();

    JMenuBar menuBar = new JMenuBar();
    JMenu portMenu = new JMenu("連接硬體設定");
    JMenuItem[] portMenuItem = new JMenuItem[20];
    JButton btn_start = new JButton("開始測量");
    JButton btn_stop = new JButton("取消測量");
    JButton btn_test = new JButton("計算");
    JButton btn_data = new JButton("產生亂數");
    JButton btn_Database = new JButton("傳送到資料庫");

    ImageIcon nameIcon = new ImageIcon("images/name1.png");
    JLabel namelabel = new JLabel(nameIcon);

    ImageIcon phoneIcon = new ImageIcon("images/phone1.png");
    JLabel phonelabel = new JLabel(phoneIcon);

    ImageIcon sexualIcon = new ImageIcon("images/sexual1.png");
    JLabel sexuallabel = new JLabel(sexualIcon);

    ImageIcon locationIcon = new ImageIcon("images/location1.png");
    JLabel locationlabel = new JLabel(locationIcon);

    ImageIcon dateIcon = new ImageIcon("images/date1.png");
    JLabel datelabel = new JLabel(dateIcon);

    ImageIcon[] positionIcon = new ImageIcon[12];
    // 350x350像素大圖用於顯示測量位置的圖
    String[] positionIconFileName = {"images/H1.png", "images/H2.png", "images/H3.png", "images/H4.png",
            "images/H5.png", "images/H6.png", "images/F1.png", "images/F2.png", "images/F3.png", "images/F4.png",
            "images/F5.png", "images/F6.png"};
    JLabel positionPicLabel = new JLabel();

    ImageIcon[] nextPositionIcon = new ImageIcon[12];
    // 320x320像素小一點的圖用於顯示下一個測量位置的圖
    String[] nextPositionIconFileName = {"images/sH1.png", "images/sH2.png", "images/sH3.png", "images/sH4.png",
            "images/sH5.png", "images/sH6.png", "images/sF1.png", "images/sF2.png", "images/sF3.png", "images/sF4.png",
            "images/sF5.png", "images/sF6.png"};
    JTextField nextPosTxtFd = new JTextField("下一個測量點（預告）");
    JLabel nextPositionPicLabel = new JLabel();
    JLabel MainUserNameLabel = new JLabel();
    JLabel UserNameLabel = new JLabel();

    JPanel mainpanel = new JPanel();
    JPanel settingpanel = new JPanel();
    JPanel[] pane = new JPanel[6];
    JPanel settingpanel_left = new JPanel();
    JPanel settingpanel_left_top = new JPanel();
    JPanel settingpanel_right_top = new JPanel();
    JPanel settingpanel_right_title = new JPanel();
    JPanel settingpanel_right = new JPanel();
    JPanel settingpanel_right_down = new JPanel();

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
    JLabel UserData = new JLabel("測量記錄");
    JLabel userLabel = new JLabel("姓名：");
    JLabel phoneLabel = new JLabel("電話號碼：");
    JLabel genderLabel = new JLabel("性別：");
    JLabel yearJLabel = new JLabel("出生年：");
    JLabel cityLable = new JLabel("城市：");
    JLabel backgroundTextLabel = new JLabel("選擇使用者：");
    JLabel PhoneNumberLabel = new JLabel("電話號碼：");

    JButton registerButton = new JButton("註冊");
    JButton Login = new JButton("切換");
    JButton Delete = new JButton("刪除");
    JButton ChangePhoneNumber = new JButton("編輯");
    JButton SavePhoneNumber = new JButton("儲存");

    JTextField PhoneNumberTextField = new JTextField();

    String[] UserDataText = new String[10];
    JButton[] UserSelect = new JButton[10];
    JComboBox<String> backgroundComboBox;
    JComboBox<String> backgroundComboBox1;

    JTextField userTextField = new JTextField();
    JTextField phoneTextField = new JTextField();
    JComboBox genderBox = new JComboBox<>();
    JComboBox yearBox = new JComboBox<>();
    JComboBox cityComboBox = new JComboBox<>();

    String[] title1 = {"時間", "左手數值", "左腳數值", "右手數值", "右腳數值"};
    String[][] data1 = new String[20][5];
    String[] title2 = {"時間", "平均值", "虛實值", "陰陽值", "左右值", "理想值距離"};
    String[][] data2 = new String[20][6];
    String[] title3 = {"姓名", "電話號碼", "性別", "出生年（西元）", "城市"};
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
                component.setBackground(color7);
            } else {
                component.setBackground(Color.WHITE);
            }

            // Customize the selected cell background color
            if (isCellSelected(row, column)) {
                component.setBackground(color7); // Light Blue
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
                component.setBackground(color7);
            } else {
                component.setBackground(Color.WHITE);
            }

            // Customize the selected cell background color
            if (isCellSelected(row, column)) {
                component.setBackground(color7); // Light Blue
            }

            return component;
        }
    };

    // Create a DefaultTableModel
    DefaultTableModel model3 = new DefaultTableModel(data3, title3);

    // Create a custom JTable with custom rendering
    JTable table3 = new JTable(model3) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component = super.prepareRenderer(renderer, row, column);

            // Customize the cell background color
            if (row % 2 == 0) {
                component.setBackground(color7);
            } else {
                component.setBackground(Color.WHITE);
            }

            // Customize the selected cell background color
            if (isCellSelected(row, column)) {
                component.setBackground(color7); // Light Blue
            }

            return component;
        }
    };
    String[] DateResult = new String[20];
    String[] LeftHandResult = new String[10];
    String[] LeftFootResult = new String[10];
    String[] RightHandResult = new String[10];
    String[] RightFootResult = new String[10];

    String PhoneReault = new String();

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

        btn_b.setLayout(null);
        btn_b.setBounds(400, 0, 200, 50);
        btn_b.setFont(fontb);
        btn_b.setForeground(Color.black);
        btn_b.setBackground(Color.lightGray);
        btn_b.setBorderPainted(false);
        btn_b.setFocusPainted(false);
        btn_b.addActionListener(CpBtn);
        pone.add(btn_b);

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

        pfou.setLayout(null);
        pfou.setBounds(0, 50, 1600, 980);
        pfou.setBackground(Color.white);
        pfou.setVisible(false);
        f.add(pfou);

        /*1600 1030*/

        //右1容器+++++++++++++++++++++++++++++++++++++++++++++++++
        pd1.setLayout(null);
        pd1.setBounds(8, 8, 1578, 90);
        //pd1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd1.setBackground(color3);
        pthr.add(pd1);

        /*//返回按鈕
        btnsd1.setLayout(null);
        btnsd1.setBounds((int)(0.02*w),(int)(0.0225*h),(int)(0.045*h),(int)(0.045*h));
        btnsd1.setBackground(color3);
        btnsd1.setBorderPainted(false);
        pd1.add(btnsd1);*/

        //職位
        UserDataText = db.SelectUser();
        ArrayList<String> backgroundOptionsList1 = new ArrayList<>();
        backgroundOptionsList1.add("選擇使用者");
        for (int i = 0; i < UserDataText.length; i++) {
            if (!UserDataText[i].equals("null")) {
                backgroundOptionsList1.add(UserDataText[i]);
            }
        }
        String[] backgroundOptions1 = backgroundOptionsList1.toArray(new String[0]);
        backgroundComboBox1 = new JComboBox<>(backgroundOptions1);
        backgroundComboBox1.setFont(ft1);
        backgroundComboBox1.setBounds(544, 20, 180, 50);
        backgroundComboBox1.addActionListener(SelectUserBox);
        pd1.add(backgroundComboBox1);

        //姓名
        tfd1.setBounds(754, 20, 200, 50);
        tfd1.setFont(ft1);
        pd1.add(tfd1);

        //搜尋按鈕
        btn_search.setLayout(null);
        btn_search.setBounds(984, 20, 50, 50);
        btn_search.setBackground(color3);
        btn_search.setBorderPainted(false);
        btn_search.addActionListener(SwitchUser1);
        pd1.add(btn_search);

        //右2容器+++++++++++++++++++++++++++++++++++++++++
        pd2.setLayout(null);
        pd2.setBounds(8, 106, 1578, 90);
        //pd2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd2.setBackground(color3);
        pthr.add(pd2);

        //輸出按鈕
        btnd1.setBounds(449, 15, 80, 60);
        btnd1.setFont(ft3);
        btnd1.setBackground(color3);
        btnd1.addActionListener(output);
        pd2.add(btnd1);

        //刪除按鈕
        btnd2.setBounds(749, 15, 80, 60);
        btnd2.setFont(ft3);
        btnd2.setBackground(color3);
        pd2.add(btnd2);

        //清空按鈕
        btnd3.setBounds(1049, 15, 80, 60);
        btnd3.setFont(ft3);
        btnd3.setBackground(color3);
        //btnd3.addActionListener(ProcessClearFields);
        pd2.add(btnd3);

        //數據記錄
        pd4.setBounds(8, 204, 1578, 700);
        pd4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd4.setLayout(null);

        //陰陽值記錄
        pd5.setBounds(8, 204, 1578, 700);
        pd5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd5.setLayout(null);

        //數據記錄表格
        data1 = new String[][]{{"datetime", "lefthand", "leftfoot", "righthand", "rightfoot"}};

        table1.setEnabled(false);
        JTableHeader head1 = table1.getTableHeader();
        table1.getTableHeader().setReorderingAllowed(false);
        table1.setFont(labelFont);
        table1.setRowHeight(40);
        table1.setGridColor(new Color(220, 220, 220));

        head1.setFont(labelFont);
        JScrollPane sp = new JScrollPane(table1,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setBounds(0, 0, 1578, 700);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);


        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        pd4.add(sp);

        //陰陽值記錄表格
        data2 = new String[][]{{"datetime", "lefthand", "leftfoot", "righthand", "rightfoot"}};

        table2.setEnabled(false);
        JTableHeader head2 = table2.getTableHeader();
        table2.getTableHeader().setReorderingAllowed(false);
        table2.setFont(labelFont);
        table2.setRowHeight(40);
        table2.setGridColor(new Color(220, 220, 220));

        head2.setFont(labelFont);
        JScrollPane sp1 = new JScrollPane(table2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp1.setBounds(0, 0, 1578, 700);

        pd5.add(sp1);

        for (int i = 0; i < table2.getColumnCount(); i++) {
            table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        //原程式部分---------------------------------------------------------------------------------


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //設定頁籤容器
        tabbedPane.setFocusable(false);

        // 移除頁籤的邊框和背景色
        tabbedPane.setUI(new NoTabBorderAndBackgroundTabbedPaneUI());

        // 頁籤字體
        Font tabFont = new Font("微軟正黑體", Font.BOLD, 20);
        tabbedPane.setFont(tabFont);

        // 添加ICON到頁籤
        ImageIcon heartIcon = new ImageIcon("images/result.png");

        ImageIcon cliptIcon = new ImageIcon("images/calculation.png");

        ImageIcon manIcon = new ImageIcon("images/man1.png");

        JLabel manlabel = new JLabel(manIcon);
        JLabel manlabel1 = new JLabel(manIcon);

        tabbedPane.addTab("測量數據", heartIcon, pd4, "測量數據");
        tabbedPane.addTab("數據計算", cliptIcon, pd5, "數據計算");
        tabbedPane.setBounds(8, 204, 1578, 700);
        pthr.add(tabbedPane);

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

        /**
         //計算方法
         btn_test.setBounds(890, 90, 200, 50);
         btn_test.setFont(font1);
         btn_test.addActionListener(SendData);
         mainpanel.add(btn_test);

         // 產生隨機數值(用於測試)
         btn_data.setBounds(660, 90, 200, 50);
         btn_data.setFont(font1);
         btn_data.addActionListener(GetRandomData);
         mainpanel.add(btn_data);

         //傳送資料到資料庫
         btn_Database.setBounds(200, 90, 200, 50);
         btn_Database.setFont(font1);
         btn_Database.addActionListener(ToDataBase);
         mainpanel.add(btn_Database);
         **/

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

        //用戶管理 左上標題panel
        settingpanel_left_top.setBounds(30, 60, 500, 30);
        UserSignUp.setFont(font1);
        settingpanel_left_top.add(UserSignUp);

        //用戶管理 左側panel
        settingpanel_left.setBounds(30, 100, 500, 800);
        settingpanel_left.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_left.setBackground(Color.white);
        settingpanel_left.setLayout(null);


        //用戶管理 左側Panel內容

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

        /*用戶管理-新增使用者-文字框及下拉選單*/
        userTextField.setBounds(260, 100, 180, 50);        //姓名
        userTextField.setFont(labelFont);
        settingpanel_left.add(userTextField);

        phoneTextField.setBounds(260, 200, 180, 50);        //電話
        phoneTextField.setFont(labelFont);
        settingpanel_left.add(phoneTextField);

        genderBox.setBounds(260, 300, 180, 50);            //性別
        genderBox.addItem("請選擇");
        genderBox.addItem("男");
        genderBox.addItem("女");
        genderBox.setFont(font1);
        settingpanel_left.add(genderBox);

        for (int i = STARTYEAR; i <= ENDYEAR; i++) {    //出生年（西元）
            yearBox.addItem(i);
        }
        yearBox.setBounds(260, 400, 180, 50);
        yearBox.setFont(labelFont);
        settingpanel_left.add(yearBox);

        cityComboBox.setBounds(260, 500, 180, 50);            //城市
        cityComboBox.addItem("請選擇");
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
        settingpanel_left.add(cityComboBox);

        /*用戶管理-新增使用者-下方註冊按鈕*/
        registerButton.setBounds(200, 650, 120, 50);
        registerButton.setBackground(color1);
        registerButton.setForeground(Color.white);
        registerButton.setFont(buttonFont);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(Register);
        settingpanel_left.add(registerButton);

        //用戶管理 右上標題Panel
        JLabel UserSelect = new JLabel("切換使用者");
        settingpanel_right_title.setBounds(540, 60, 1010, 30);
        UserSelect.setFont(font1);
        settingpanel_right_title.add(UserSelect);

        //用戶管理 右上Panel
        settingpanel_right_top.setBounds(540, 100, 1010, 200);
        settingpanel_right_top.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_right_top.setLayout(null);

        /*用戶管理-切換使用者-選擇使用者*/

        //文字標題
        backgroundTextLabel.setFont(font1);                    //選擇使用者
        backgroundTextLabel.setBounds(50, 50, 150, 30);
        settingpanel_right_top.add(backgroundTextLabel);

        PhoneNumberLabel.setFont(font1);                    //電話號碼
        PhoneNumberLabel.setBounds(50, 130, 150, 30);
        settingpanel_right_top.add(PhoneNumberLabel);

        //下拉選單
        UserDataText = db.SelectUser();
        ArrayList<String> backgroundOptionsList = new ArrayList<>();
        backgroundOptionsList.add("選擇使用者");
        for (int i = 0; i < UserDataText.length; i++) {
            if (!UserDataText[i].equals("null")) {
                backgroundOptionsList.add(UserDataText[i]);
            }
        }
        String[] backgroundOptions = backgroundOptionsList.toArray(new String[0]);
        backgroundComboBox = new JComboBox<>(backgroundOptions);
        backgroundComboBox.setFont(font1);
        backgroundComboBox.setBounds(170, 50, 150, 30);
        backgroundComboBox.addActionListener(SelectUserBox);
        settingpanel_right_top.add(backgroundComboBox);


        /*用戶管理-切換使用者-切換按鈕*/
        Login.setFont(font1);
        Login.setBounds(350, 50, 100, 30);
        Login.addActionListener(SwitchUser);
        Login.setFocusable(false);
        settingpanel_right_top.add(Login);

        /*用戶管理-切換使用者-刪除按鈕*/
        Delete.setFont(font1);
        Delete.setBounds(480, 50, 100, 30);
        //Delete.addActionListener(SwitchUser);
        Delete.setFocusable(false);
        settingpanel_right_top.add(Delete);

        /*用戶管理-切換使用者-電話號碼編輯按鈕*/
        ChangePhoneNumber.setFont(font1);
        ChangePhoneNumber.setBounds(350, 130, 100, 30);
        ChangePhoneNumber.setFocusable(false);
        ChangePhoneNumber.setEnabled(false);
        ChangePhoneNumber.addActionListener(ChangePhoneNum);
        settingpanel_right_top.add(ChangePhoneNumber);

        /*用戶管理-切換使用者-電話號碼儲存按鈕*/
        SavePhoneNumber.setFont(font1);
        SavePhoneNumber.setBounds(350, 130, 100, 30);
        SavePhoneNumber.setFocusable(false);
        SavePhoneNumber.addActionListener(SavePhoneNum);
        SavePhoneNumber.setVisible(false);
        settingpanel_right_top.add(SavePhoneNumber);


        //電話號碼顯示及修改的文字框
        PhoneNumberTextField.setFont(font1);
        PhoneNumberTextField.setBounds(170, 130, 150, 30);
        PhoneNumberTextField.setEnabled(false);
        PhoneNumberTextField.setDocument(new JTextFieldLimit(10));
        PhoneNumberTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    ChangePhoneNumber.setVisible(true);
                    SavePhoneNumber.setVisible(false);
                    PhoneNumberTextField.setEnabled(false);
                    //將資料傳入資料庫內
                    data.setUser(UserName);
                    data.setPhone(PhoneNumberTextField.getText());
                    db.UpdatePhone();
                }
            }
        });
        settingpanel_right_top.add(PhoneNumberTextField);

        //用戶管理 右下標題Panel
        settingpanel_right.setBounds(540, 310, 1010, 30);
        UserData.setFont(font1);
        settingpanel_right.add(UserData);

        //用戶管理 右下測量記錄
        settingpanel_right_down.setBounds(540, 350, 1010, 550);
        settingpanel_right_down.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_right_down.setLayout(null);

        //用戶管理 右下測量記錄 內容
        data3 = new String[][]{{"datetime", "lefthand", "leftfoot", "righthand", "rightfoot"}};

        table3.setEnabled(false);
        JTableHeader head3 = table3.getTableHeader();
        table3.getTableHeader().setReorderingAllowed(false);
        table3.setFont(labelFont);
        table3.setRowHeight(40);
        table3.setGridColor(new Color(220, 220, 220));

        head3.setFont(labelFont);
        JScrollPane sp2 = new JScrollPane(table3,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp2.setBounds(0, 0, 1010, 550);

        // 自定義垂直捲動條的外觀
        JScrollBar verticalScrollBar = sp1.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        // 自定義垂直捲動條的外觀
        JScrollBar HorizontalScrollBar = sp1.getHorizontalScrollBar();
        HorizontalScrollBar.setUI(new CustomScrollBarUI());

        for (int i = 0; i < table3.getColumnCount(); i++) {
            table3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        settingpanel_right_down.add(sp2);

        pfou.add(settingpanel_left);
        pfou.add(settingpanel_left_top);
        pfou.add(settingpanel_right_top);
        pfou.add(settingpanel_right_title);
        pfou.add(settingpanel_right);
        pfou.add(settingpanel_right_down);


        f.setLayout(null);
        f.setBounds((int) (0.085 * w), 0, 1600, 1030);
        //f.setBounds((int)(0.085*w),0,(int)(0.83*w),(int)(0.95*h));
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

    static class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createEmptyButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createEmptyButton();
        }

        private JButton createEmptyButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            g.setColor(Color.GRAY);
            g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
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
                // 開始計算數值
                for (int i = 0; i <= 5; i++) {
                    Log[i] = LeftHandArrays[j][i];
                    Log[i + 6] = LeftFootArrays[j][i];
                    Log[i + 12] = RightHandArrays[j][i];
                    Log[i + 18] = RightFootArrays[j][i];
                }

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
//              mycal.showdetail();

                //next傳送數據到數據計算table


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
                btn_b.setEnabled(true);
                ptwo.setVisible(true);
                pthr.setVisible(false);
                pfou.setVisible(false);

            }

            if (e.getSource() == btn_a) {

                btn_f.setEnabled(true);
                btn_a.setEnabled(false);
                btn_b.setEnabled(true);
                ptwo.setVisible(false);
                pthr.setVisible(true);
                pfou.setVisible(false);

            }

            if (e.getSource() == btn_b) {

                btn_f.setEnabled(true);
                btn_a.setEnabled(true);
                btn_b.setEnabled(false);
                ptwo.setVisible(false);
                pthr.setVisible(false);
                pfou.setVisible(true);

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

    // 產生隨機數字
    public ActionListener GetRandomData = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i <= 11; i++) {
                Random random = new Random();
                int randomNumber = random.nextInt(11);
                int randomNumber2 = random.nextInt(11);
                H1toH6MDataField[i].setText(String.valueOf(randomNumber));
                F1toF6MDataField[i].setText(String.valueOf(randomNumber2));
            }

        }
    };

    // 傳送資料到副程式
    public ActionListener SendData = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.print("原始數值" + "\n");
            // 開始計算數值
            for (int i = 0; i <= 11; i++) {
                number[i] = Double.parseDouble(H1toH6MDataField[i].getText());
                number[i + 12] = Double.parseDouble(F1toF6MDataField[i].getText());
            }
            for (int i = 0; i <= 23; i++) {
                System.out.print(number[i] + ",");
            }
            System.out.print("\n");
            mycal.Getnumber(number);
            System.out.print("\n");
            mycal.avg();
            mycal.FR();
            mycal.YY();
            mycal.LR();
            mycal.showdetail();

        }
    };

    public ActionListener ProcessStartReadUSB = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            thread_readData_status = RUNNING;
            thread_changeField_status = RUNNING;
            pressStopMeasure = 0;
            btn_stop.setEnabled(true);
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

            } catch (InterruptedException ioe) {
                ioe.printStackTrace();
            }
        }
    };

    public ActionListener ToDataBase = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

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

    //切換使用者
    public ActionListener SwitchUser1 = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if ((String) backgroundComboBox1.getSelectedItem() != "選擇使用者") {
                UserName = (String) backgroundComboBox1.getSelectedItem();
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

                LogCal();

                db.CleanDate();
                System.out.println("使用者已切換成：" + UserName);

            } else if (backgroundComboBox1.getSelectedItem() == "選擇使用者") {
                ChangePhoneNumber.setEnabled(false);
            }

        }
    };

    //編輯電話號碼
    public ActionListener ChangePhoneNum = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ChangePhoneNumber.setVisible(false);
            SavePhoneNumber.setVisible(true);
            PhoneNumberTextField.setEnabled(true);

        }
    };

    //儲存電話號碼
    public ActionListener SavePhoneNum = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ChangePhoneNumber.setVisible(true);
            SavePhoneNumber.setVisible(false);
            PhoneNumberTextField.setEnabled(false);
            //將資料傳入資料庫內
            data.setUser(UserName);
            data.setPhone(PhoneNumberTextField.getText());
            db.UpdatePhone();
        }
    };

    //清空測量記錄&電話號碼
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

    // 自定?TabbedPaneUI以移除???框
    class NoTabBorderAndBackgroundTabbedPaneUI extends BasicTabbedPaneUI {
        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            // 不繪製標籤邊框
        }

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            // 不繪製標籤背景

            // 選中標籤時底線
            if (isSelected) {
                g.setColor(Color.BLUE); // 底線?色
                g.fillRect(x, y + h - 5, w, 5); // 底線的位置和大小
            }
        }
    }

}
