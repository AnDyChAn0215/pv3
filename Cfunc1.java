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
import javax.swing.plaf.FontUIResource; //�ק�JOptionPane�r��һݮM��
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
//����MP3���֩һݮM��
//import com.sun.scenario.effect.Offset;
import jaco.mp3.player.MP3Player;

import java.io.File;

class Cfunc1 {

    //�s�W����-----------------------------------------------------------------------------------

    int STARTYEAR = 1900;
    int ENDYEAR = 2030;

    JPanel pone = new JPanel();    //���ҫ��s��
    JPanel ptwo = new JPanel();    //���q��
    JPanel pthr = new JPanel();    //���q���G��
    JPanel pfou = new JPanel();    //�Τ�޲z��

    ImageIcon iconout = new ImageIcon("images/signout.png");
    ImageIcon iconuser = new ImageIcon("images/user.png");
    ImageIcon iconhome = new ImageIcon("images/home.png");
    ImageIcon icondata = new ImageIcon("images/analytics.png");
    ImageIcon iconsearch = new ImageIcon("images/search.png");

    JButton btn_f = new JButton("����", iconhome);
    JButton btn_a = new JButton("���q���G", icondata);
    JButton btn_b = new JButton("�Τ�޲z", iconuser);
    JButton btn_out = new JButton("�n�X", iconout);
    JButton btn_search = new JButton(iconsearch);        //�j�M���s

    Font ft1 = new Font("�L�n������", Font.BOLD, 24);        //������r�榡
    Font ft2 = new Font("�L�n������", Font.BOLD, 38);        //���Y��r�榡
    Font ft3 = new Font("�L�n������", Font.BOLD, 18);        //���s��r�榡
    Font ft4 = new Font("�L�n������", Font.BOLD, 60);        //�I����r

    JPanel pd = new JPanel();        //�b��޲z����
    JPanel pd1 = new JPanel();        //�k1�e��(�b��)
    JPanel pd2 = new JPanel();        //�k2�e��(�b��)
    JPanel pd3 = new JPanel();        //�k3�e��(�b��)
    JPanel pd4 = new JPanel();        //�k4�e��(�b��)
    JPanel pd5 = new JPanel();        //�k5�e��(�b��)

    JLabel d1 = new JLabel("¾��");        //�b��
    JLabel d2 = new JLabel("�m�W");        //�b��
    JLabel d3 = new JLabel("�b��");        //�b��
    JLabel d4 = new JLabel("�K�X");        //�b��

    JButton btnd1 = new JButton("��X");        //�b��
    JButton btnd2 = new JButton("�R��");        //�b��
    JButton btnd3 = new JButton("�M��");        //�b��

    JTextField tfd1 = new JTextField();            //�b��


    //��{������---------------------------------------------------------------------------------

    //���o�ù��e�]w�^�P���]h�^
    Toolkit kt = Toolkit.getDefaultToolkit();
    Dimension dm = kt.getScreenSize();
    int w = (int) dm.getWidth();
    int h = (int) dm.getHeight();

    // Ū��usb��ƩҥΪ��Ҳժ���P�ܼ�
    int[] mdata = {0};
    SerialPortListener_v1 sp = new SerialPortListener_v1(mdata); // �qusbŪArduino���
    CgetSerialCom_v1 getCom = new CgetSerialCom_v1();             // �������ousb�q�T�𪫥�
    String[] portList = getCom.getComPortList();                 // �s����o��usb�q�T��

    // �p���k
    double[] number = new double[24];
    CAL mycal = new CAL();

    Data data = new Data();
    DataBase db = new DataBase();
    RegisterData RD = new RegisterData();
    String UserName = "";

    //�C��
    Color MyColor = new Color(161, 204, 209);
    Color MyColor1 = new Color(233, 179, 132);
    Color MyColor2 = new Color(124, 157, 150);
    Color MyColor3 = new Color(244, 242, 222);

    Color color1 = new Color(0, 47, 73);            //�Ӯa��
    Color color2 = new Color(238, 238, 238);        //����
    Color color3 = new Color(173, 217, 232);        //�ަ�
    Color color4 = new Color(144, 144, 144);        //�ؽu
    Color color5 = new Color(112, 134, 163);        //���D
    Color color6 = new Color(112, 134, 163);
    Color color7 = new Color(214, 217, 223);

    // �����
    Thread thread_readData;
    Thread thread_changeField;
    private final int STOP = -1;
    private final int SUSPEND = 0;
    private final int RUNNING = 1;
    private int thread_readData_status = STOP;
    private int thread_changeField_status = STOP;

    // ����y�{
    int fieldCount = 0;
    int pressStopMeasure = 0; // 0: not press, 1: press the STOP Measure button
    int outputcount = 0;

    // HCI����
    JFrame f = new JFrame();

    Font font1 = new Font("�L�n������", Font.BOLD, 18);
    Font font2 = new Font("�L�n������", Font.BOLD, 15);
    Font fontb = new Font("�L�n������", Font.BOLD, 28);
    Font labelFont = new Font("�L�n������", Font.BOLD, 20);
    Font buttonFont = new Font("�L�n������", Font.BOLD, 18);


    JTabbedPane tabbedPane = new JTabbedPane();

    JMenuBar menuBar = new JMenuBar();
    JMenu portMenu = new JMenu("�s���w��]�w");
    JMenuItem[] portMenuItem = new JMenuItem[20];
    JButton btn_start = new JButton("�}�l���q");
    JButton btn_stop = new JButton("�������q");
    JButton btn_test = new JButton("�p��");
    JButton btn_data = new JButton("���Ͷü�");
    JButton btn_Database = new JButton("�ǰe���Ʈw");

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
    // 350x350�����j�ϥΩ���ܴ��q��m����
    String[] positionIconFileName = {"images/H1.png", "images/H2.png", "images/H3.png", "images/H4.png",
            "images/H5.png", "images/H6.png", "images/F1.png", "images/F2.png", "images/F3.png", "images/F4.png",
            "images/F5.png", "images/F6.png"};
    JLabel positionPicLabel = new JLabel();

    ImageIcon[] nextPositionIcon = new ImageIcon[12];
    // 320x320�����p�@�I���ϥΩ���ܤU�@�Ӵ��q��m����
    String[] nextPositionIconFileName = {"images/sH1.png", "images/sH2.png", "images/sH3.png", "images/sH4.png",
            "images/sH5.png", "images/sH6.png", "images/sF1.png", "images/sF2.png", "images/sF3.png", "images/sF4.png",
            "images/sF5.png", "images/sF6.png"};
    JTextField nextPosTxtFd = new JTextField("�U�@�Ӵ��q�I�]�w�i�^");
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
    String[] LRHandLabelText = {"����", "�k��"};
    JTextField[] H1toH6PositionLabel = new JTextField[12];
    String[] H1toH6PositionLabelText = {"H1 �ӲW��", "H2 �j����", "H3 ������", "H4 ������", "H5 ������", "H6 ���ƥ�", "H1 �ӲW��", "H2 �j����",
            "H3 ������", "H4 ������", "H5 ������", "H6 ���ƥ�"};
    JTextField[] H1toH6MDataField = new JTextField[12];
    JTextField[] LRFootLabel = new JTextField[2];
    String[] LRFootLabelText = {"���}", "�k�}"};
    JTextField[] F1toF6PositionLabel = new JTextField[12];
    String[] F1toF6PositionLabelText = {"F1 �ӥե�", "F2 �ӽĥ�", "F3 �j����", "F4 ������", "F5 �L�˥�", "F6 ���x��", "F1 �ӥե�", "F2 �ӽĥ�",
            "F3 �j����", "F4 ������", "F5 �L�˥�", "F6 ���x��"};
    JTextField[] F1toF6MDataField = new JTextField[12];

    /*�Τ�޲z*/
    JLabel UserSignUp = new JLabel("�s�W�ϥΪ�");
    JLabel UserData = new JLabel("���q�O��");
    JLabel userLabel = new JLabel("�m�W�G");
    JLabel phoneLabel = new JLabel("�q�ܸ��X�G");
    JLabel genderLabel = new JLabel("�ʧO�G");
    JLabel yearJLabel = new JLabel("�X�ͦ~�G");
    JLabel cityLable = new JLabel("�����G");
    JLabel backgroundTextLabel = new JLabel("��ܨϥΪ̡G");
    JLabel PhoneNumberLabel = new JLabel("�q�ܸ��X�G");

    JButton registerButton = new JButton("���U");
    JButton Login = new JButton("����");
    JButton Delete = new JButton("�R��");
    JButton ChangePhoneNumber = new JButton("�s��");
    JButton SavePhoneNumber = new JButton("�x�s");

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

    String[] title1 = {"�ɶ�", "����ƭ�", "���}�ƭ�", "�k��ƭ�", "�k�}�ƭ�"};
    String[][] data1 = new String[20][5];
    String[] title2 = {"�ɶ�", "������", "����", "������", "���k��", "�z�Q�ȶZ��"};
    String[][] data2 = new String[20][6];
    String[] title3 = {"�m�W", "�q�ܸ��X", "�ʧO", "�X�ͦ~�]�褸�^", "����"};
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

    // ����MP3���֩һݪ���
    File f1 = new File("musics/music.mp3"); // ���ݮɪ��t��
    File f2 = new File("musics/button04a.mp3"); // �i�ױ����ʪ��t��
    File f3 = new File("musics/press.mp3"); // �����ɪ��t��
    MP3Player music1_Player = new MP3Player(f1);
    MP3Player music2_Player = new MP3Player(f2);
    MP3Player music3_Player = new MP3Player(f3);
    int stepsOfAMeasure = 0; // �p�ƨC�@�����q����6�ӨB�J

    // �i�ױ�
    JProgressBar progressBar = new JProgressBar();

    //��u��
    ClineChart chart = new ClineChart();
    Graphics g;

    int[] pointValue = new int[6];   //6���I����
    int[] px = new int[6];    //�I����������x�y��
    int[] py = new int[6];    //�I����������y�y��

    WriteFiles Write = new WriteFiles();

    Cfunc1() {

        //�s�W����-----------------------------------------------------------------------------------

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

        //�k1�e��+++++++++++++++++++++++++++++++++++++++++++++++++
        pd1.setLayout(null);
        pd1.setBounds(8, 8, 1578, 90);
        //pd1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd1.setBackground(color3);
        pthr.add(pd1);

        /*//��^���s
        btnsd1.setLayout(null);
        btnsd1.setBounds((int)(0.02*w),(int)(0.0225*h),(int)(0.045*h),(int)(0.045*h));
        btnsd1.setBackground(color3);
        btnsd1.setBorderPainted(false);
        pd1.add(btnsd1);*/

        //¾��
        UserDataText = db.SelectUser();
        ArrayList<String> backgroundOptionsList1 = new ArrayList<>();
        backgroundOptionsList1.add("��ܨϥΪ�");
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

        //�m�W
        tfd1.setBounds(754, 20, 200, 50);
        tfd1.setFont(ft1);
        pd1.add(tfd1);

        //�j�M���s
        btn_search.setLayout(null);
        btn_search.setBounds(984, 20, 50, 50);
        btn_search.setBackground(color3);
        btn_search.setBorderPainted(false);
        btn_search.addActionListener(SwitchUser1);
        pd1.add(btn_search);

        //�k2�e��+++++++++++++++++++++++++++++++++++++++++
        pd2.setLayout(null);
        pd2.setBounds(8, 106, 1578, 90);
        //pd2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd2.setBackground(color3);
        pthr.add(pd2);

        //��X���s
        btnd1.setBounds(449, 15, 80, 60);
        btnd1.setFont(ft3);
        btnd1.setBackground(color3);
        btnd1.addActionListener(output);
        pd2.add(btnd1);

        //�R�����s
        btnd2.setBounds(749, 15, 80, 60);
        btnd2.setFont(ft3);
        btnd2.setBackground(color3);
        pd2.add(btnd2);

        //�M�ū��s
        btnd3.setBounds(1049, 15, 80, 60);
        btnd3.setFont(ft3);
        btnd3.setBackground(color3);
        //btnd3.addActionListener(ProcessClearFields);
        pd2.add(btnd3);

        //�ƾڰO��
        pd4.setBounds(8, 204, 1578, 700);
        pd4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd4.setLayout(null);

        //�����ȰO��
        pd5.setBounds(8, 204, 1578, 700);
        pd5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pd5.setLayout(null);

        //�ƾڰO�����
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

        //�����ȰO�����
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


        //��{������---------------------------------------------------------------------------------


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //�]�w���Үe��
        tabbedPane.setFocusable(false);

        // �������Ҫ���ةM�I����
        tabbedPane.setUI(new NoTabBorderAndBackgroundTabbedPaneUI());

        // ���Ҧr��
        Font tabFont = new Font("�L�n������", Font.BOLD, 20);
        tabbedPane.setFont(tabFont);

        // �K�[ICON�쭶��
        ImageIcon heartIcon = new ImageIcon("images/result.png");

        ImageIcon cliptIcon = new ImageIcon("images/calculation.png");

        ImageIcon manIcon = new ImageIcon("images/man1.png");

        JLabel manlabel = new JLabel(manIcon);
        JLabel manlabel1 = new JLabel(manIcon);

        tabbedPane.addTab("���q�ƾ�", heartIcon, pd4, "���q�ƾ�");
        tabbedPane.addTab("�ƾڭp��", cliptIcon, pd5, "�ƾڭp��");
        tabbedPane.setBounds(8, 204, 1578, 700);
        pthr.add(tabbedPane);

        // �إߦ����W�q����USB����
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

        // �}�l�P����Ū��USB�ƾ�
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
         //�p���k
         btn_test.setBounds(890, 90, 200, 50);
         btn_test.setFont(font1);
         btn_test.addActionListener(SendData);
         mainpanel.add(btn_test);

         // �����H���ƭ�(�Ω����)
         btn_data.setBounds(660, 90, 200, 50);
         btn_data.setFont(font1);
         btn_data.addActionListener(GetRandomData);
         mainpanel.add(btn_data);

         //�ǰe��ƨ��Ʈw
         btn_Database.setBounds(200, 90, 200, 50);
         btn_Database.setFont(font1);
         btn_Database.addActionListener(ToDataBase);
         mainpanel.add(btn_Database);
         **/

        manlabel.setBounds(1186, (int) (5.15), 50, 40);
        pone.add(manlabel);

        JLabel MainUserLabel = new JLabel("�ثe�ϥΪ̡G");
        MainUserLabel.setFont(font1);
        MainUserLabel.setBounds(1236, (int) (5.15), 110, 40);
        pone.add(MainUserLabel);

        MainUserNameLabel.setFont(font1);
        MainUserNameLabel.setBounds(1346, (int) (5.15), 100, 40);
        pone.add(MainUserNameLabel);

        // ��ܴ��q�I�Ϥ�
        for (int i = 0; i < positionIcon.length; i++) {
            positionIcon[i] = new ImageIcon(positionIconFileName[i]);
        }
        positionPicLabel.setIcon(null);
        positionPicLabel.setText("�w�ƴ��q�e����f");
        positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
        positionPicLabel.setBounds(790, 160, 755, 350);
        positionPicLabel.setHorizontalAlignment(JLabel.CENTER);
        positionPicLabel.setBorder(BorderFactory.createLineBorder(MyColor, 3));
        ptwo.add(positionPicLabel);

        // ���q�i�ױ�
        progressBar.setBounds(30, 520, 1520, 50);
        progressBar.setBackground(MyColor3);
        progressBar.setForeground(Color.orange);
        // progressBar.setStringPainted(true);
        progressBar.setFont(font1);
        ptwo.add(progressBar);

        // ��ܦU���q�I���ƭ�
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

        //��u�Ϧ�m
        chart.setBounds(30, 160, 755, 350);
        chart.setBorder(BorderFactory.createLineBorder(MyColor, 3));
        // �ҥ� x �b�M y �b������u
        ptwo.add(chart);
        ptwo.setBounds(0, 0, 1600, 1000);

        /*��ܥثe�ϥΪ�*/
        manlabel1.setBounds(40, 10, 50, 50);
        settingpanel.add(manlabel1);

        JLabel User = new JLabel("�ثe�ϥΪ̡G");
        User.setFont(font1);
        User.setBounds(80, 10, 100, 50);
        settingpanel.add(User);

        UserNameLabel.setFont(font1);
        UserNameLabel.setBounds(180, 10, 100, 50);
        settingpanel.add(UserNameLabel);

        /*-------------------------------------------*/

        //�Τ�޲z ���W���Dpanel
        settingpanel_left_top.setBounds(30, 60, 500, 30);
        UserSignUp.setFont(font1);
        settingpanel_left_top.add(UserSignUp);

        //�Τ�޲z ����panel
        settingpanel_left.setBounds(30, 100, 500, 800);
        settingpanel_left.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_left.setBackground(Color.white);
        settingpanel_left.setLayout(null);


        //�Τ�޲z ����Panel���e

        /*�Τ�޲z-�s�W�ϥΪ�-LOGO����*/
        namelabel.setBounds(30, 100, 50, 50);        //�m�W
        settingpanel_left.add(namelabel);

        phonelabel.setBounds(30, 200, 50, 50);        //�q��
        settingpanel_left.add(phonelabel);

        sexuallabel.setBounds(30, 300, 50, 50);    //�ʧO
        settingpanel_left.add(sexuallabel);

        datelabel.setBounds(30, 400, 50, 50);        //�X�ͦ~�]�褸�^
        settingpanel_left.add(datelabel);

        locationlabel.setBounds(30, 500, 50, 50);    //����
        settingpanel_left.add(locationlabel);


        /*�Τ�޲z-�s�W�ϥΪ�-��r����*/
        userLabel.setBounds(90, 100, 80, 50);        //�m�W
        userLabel.setFont(labelFont);
        settingpanel_left.add(userLabel);

        phoneLabel.setBounds(90, 200, 120, 50);    //�q��
        phoneLabel.setFont(labelFont);
        settingpanel_left.add(phoneLabel);

        genderLabel.setBounds(90, 300, 80, 50);    //�ʧO
        genderLabel.setFont(labelFont);
        settingpanel_left.add(genderLabel);

        yearJLabel.setBounds(90, 400, 120, 50);    //�X�ͦ~�]�褸�^
        yearJLabel.setFont(labelFont);
        settingpanel_left.add(yearJLabel);

        cityLable.setBounds(90, 500, 80, 50);        //����
        cityLable.setFont(labelFont);
        settingpanel_left.add(cityLable);

        /*�Τ�޲z-�s�W�ϥΪ�-��r�ؤΤU�Կ��*/
        userTextField.setBounds(260, 100, 180, 50);        //�m�W
        userTextField.setFont(labelFont);
        settingpanel_left.add(userTextField);

        phoneTextField.setBounds(260, 200, 180, 50);        //�q��
        phoneTextField.setFont(labelFont);
        settingpanel_left.add(phoneTextField);

        genderBox.setBounds(260, 300, 180, 50);            //�ʧO
        genderBox.addItem("�п��");
        genderBox.addItem("�k");
        genderBox.addItem("�k");
        genderBox.setFont(font1);
        settingpanel_left.add(genderBox);

        for (int i = STARTYEAR; i <= ENDYEAR; i++) {    //�X�ͦ~�]�褸�^
            yearBox.addItem(i);
        }
        yearBox.setBounds(260, 400, 180, 50);
        yearBox.setFont(labelFont);
        settingpanel_left.add(yearBox);

        cityComboBox.setBounds(260, 500, 180, 50);            //����
        cityComboBox.addItem("�п��");
        cityComboBox.addItem("�O�_��");
        cityComboBox.addItem("�s�_��");
        cityComboBox.addItem("�򶩥�");
        cityComboBox.addItem("��饫");
        cityComboBox.addItem("�s�˥�");
        cityComboBox.addItem("�]�߿�");
        cityComboBox.addItem("�O����");
        cityComboBox.addItem("���ƿ�");
        cityComboBox.addItem("�n�뿤");
        cityComboBox.addItem("���L��");
        cityComboBox.addItem("�Ÿq��");
        cityComboBox.addItem("�O�n��");
        cityComboBox.addItem("������");
        cityComboBox.addItem("�̪F��");
        cityComboBox.addItem("�y����");
        cityComboBox.addItem("�Ὤ��");
        cityComboBox.addItem("�O�F��");
        cityComboBox.addItem("���");
        cityComboBox.addItem("������");
        cityComboBox.addItem("�s����");
        cityComboBox.setFont(font1);
        settingpanel_left.add(cityComboBox);

        /*�Τ�޲z-�s�W�ϥΪ�-�U����U���s*/
        registerButton.setBounds(200, 650, 120, 50);
        registerButton.setBackground(color1);
        registerButton.setForeground(Color.white);
        registerButton.setFont(buttonFont);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(Register);
        settingpanel_left.add(registerButton);

        //�Τ�޲z �k�W���DPanel
        JLabel UserSelect = new JLabel("�����ϥΪ�");
        settingpanel_right_title.setBounds(540, 60, 1010, 30);
        UserSelect.setFont(font1);
        settingpanel_right_title.add(UserSelect);

        //�Τ�޲z �k�WPanel
        settingpanel_right_top.setBounds(540, 100, 1010, 200);
        settingpanel_right_top.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_right_top.setLayout(null);

        /*�Τ�޲z-�����ϥΪ�-��ܨϥΪ�*/

        //��r���D
        backgroundTextLabel.setFont(font1);                    //��ܨϥΪ�
        backgroundTextLabel.setBounds(50, 50, 150, 30);
        settingpanel_right_top.add(backgroundTextLabel);

        PhoneNumberLabel.setFont(font1);                    //�q�ܸ��X
        PhoneNumberLabel.setBounds(50, 130, 150, 30);
        settingpanel_right_top.add(PhoneNumberLabel);

        //�U�Կ��
        UserDataText = db.SelectUser();
        ArrayList<String> backgroundOptionsList = new ArrayList<>();
        backgroundOptionsList.add("��ܨϥΪ�");
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


        /*�Τ�޲z-�����ϥΪ�-�������s*/
        Login.setFont(font1);
        Login.setBounds(350, 50, 100, 30);
        Login.addActionListener(SwitchUser);
        Login.setFocusable(false);
        settingpanel_right_top.add(Login);

        /*�Τ�޲z-�����ϥΪ�-�R�����s*/
        Delete.setFont(font1);
        Delete.setBounds(480, 50, 100, 30);
        //Delete.addActionListener(SwitchUser);
        Delete.setFocusable(false);
        settingpanel_right_top.add(Delete);

        /*�Τ�޲z-�����ϥΪ�-�q�ܸ��X�s����s*/
        ChangePhoneNumber.setFont(font1);
        ChangePhoneNumber.setBounds(350, 130, 100, 30);
        ChangePhoneNumber.setFocusable(false);
        ChangePhoneNumber.setEnabled(false);
        ChangePhoneNumber.addActionListener(ChangePhoneNum);
        settingpanel_right_top.add(ChangePhoneNumber);

        /*�Τ�޲z-�����ϥΪ�-�q�ܸ��X�x�s���s*/
        SavePhoneNumber.setFont(font1);
        SavePhoneNumber.setBounds(350, 130, 100, 30);
        SavePhoneNumber.setFocusable(false);
        SavePhoneNumber.addActionListener(SavePhoneNum);
        SavePhoneNumber.setVisible(false);
        settingpanel_right_top.add(SavePhoneNumber);


        //�q�ܸ��X��ܤέק諸��r��
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
                    //�N��ƶǤJ��Ʈw��
                    data.setUser(UserName);
                    data.setPhone(PhoneNumberTextField.getText());
                    db.UpdatePhone();
                }
            }
        });
        settingpanel_right_top.add(PhoneNumberTextField);

        //�Τ�޲z �k�U���DPanel
        settingpanel_right.setBounds(540, 310, 1010, 30);
        UserData.setFont(font1);
        settingpanel_right.add(UserData);

        //�Τ�޲z �k�U���q�O��
        settingpanel_right_down.setBounds(540, 350, 1010, 550);
        settingpanel_right_down.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        settingpanel_right_down.setLayout(null);

        //�Τ�޲z �k�U���q�O�� ���e
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

        // �۩w�q�������ʱ����~�[
        JScrollBar verticalScrollBar = sp1.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        // �۩w�q�������ʱ����~�[
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
        f.setTitle("�}�ɵ��g�����q�t��");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

//----------------------------------------------------------------------
//��k�{����
//----------------------------------------------------------------------

    //�����J����
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

    // �M�Ÿ�T�^�k��l
    public void clearDisplay() {

        for (int i = 0; i < H1toH6MDataField.length; i++) {
            H1toH6MDataField[i].setText("");
        }
        for (int i = 0; i < F1toF6MDataField.length; i++) {
            F1toF6MDataField[i].setText("");
        }
        positionPicLabel.setIcon(null);
        positionPicLabel.setText("�w�ƴ��q[����]");
        positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
        nextPositionPicLabel.setIcon(nextPositionIcon[0]);

    }

    // �̷ӶǤJ��fieldCount�ȧ�q�������ܨ�������
    public synchronized void showMeasureData(int fieldCount) {
        if (stepsOfAMeasure != -1) {
            if (fieldCount >= 1 && fieldCount < 7 && stepsOfAMeasure < 6) {
                H1toH6MDataField[fieldCount - 1].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //�]�w�I���ƭ�
            } else if (fieldCount >= 8 && fieldCount <= 13 && stepsOfAMeasure < 6) {
                H1toH6MDataField[fieldCount - 2].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //�]�w�I���ƭ�
            } else if (fieldCount >= 15 && fieldCount <= 20 && stepsOfAMeasure < 6) {
                F1toF6MDataField[fieldCount - 15].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //�]�w�I���ƭ�
            } else if (fieldCount >= 22 && fieldCount <= 27 && stepsOfAMeasure < 6) {
                F1toF6MDataField[fieldCount - 16].setText(String.valueOf(mdata[0]));
                chart.pointValue[stepsOfAMeasure % 6] = mdata[0] * 10;  //�]�w�I���ƭ�
            } else {
            }

            try {
                if (fieldCount == 0 || fieldCount == 7 || fieldCount == 14 || fieldCount == 21) { // �w�ơB���k�P��}�����ɤ�����
                    if (stepsOfAMeasure > -1)
                        music1_Player.play();
                    while (!music1_Player.isStopped()) {
                        Thread.sleep(5);
                    }
                    stepsOfAMeasure = -1;
                    progressBar.setValue(0);
                }
                if (fieldCount != 0 && fieldCount != 7 && fieldCount != 14 && fieldCount != 21) { // ���k�P��}�����ɤ�����
                    if (stepsOfAMeasure < 6) {
                        // System.out.println("Steps="+String.valueOf(stepsOfAMeasure));
                        progressBar.setValue(17 * (stepsOfAMeasure + 1));
                        g = chart.getGraphics();       //���o�e��
                        chart.paintComponent(g);       //����e��
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

    // �̷ӶǤJ��fieldCount�ȧ�q�������ܨ�������
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
            positionPicLabel.setText("�w�ƴ��q�e����f");
            positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[0]);
        } else if (fieldCount == 7) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("�w�ƴ��q�e�k��f");
            positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[0]);
        } else if (fieldCount == 14) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("�w�ƴ��q�e���}�f");
            positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[6]);
        } else if (fieldCount == 21) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("�w�ƴ��q�e�k�}�f");
            positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(nextPositionIcon[6]);
        } else if (fieldCount == 28) {
            positionPicLabel.setIcon(null);
            positionPicLabel.setText("�������q");
            positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
            nextPositionPicLabel.setIcon(null);

            //�O�_�x�s���
            int result = JOptionPane.showConfirmDialog(f, "�O�_�n�x�s�������q��ơH", "���q����", JOptionPane.YES_NO_OPTION);
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
        for (int x = 0; x < chart.pointValue.length; x++) chart.pointValue[x] = -1; //�M�ŵe���I�ƾ�
    }

    //�n�J�ᱵ���ϥΪ̦W��
    public void setUser(String USER) {
        UserName = USER;
        MainUserNameLabel.setText(UserName);
        UserNameLabel.setText(UserName);
    }

    //���o��Ʈw���C�@�����q�O����S�w�ܼ�
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


        //�ɶ�TimeArrays
        TimeNumber = db.getDateResult();

        //����ƭ�LeftHandArrays
        LeftHandNumber = db.getLefthandResult();

        for (int i = 0; i < LeftHandNumber.length; i++) {
            String[] stringArray = new String[LeftHandNumber.length];
            if (LeftHandNumber[i] != null) {
                stringArray = LeftHandNumber[i].split(" "); // �h���r�ꤤ���Ů�
            } else {
                break;
            }

            LeftHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }
        }

        //���}�ƭ�LeftFootArrays
        LeftFootNumber = db.getLeftfootResult();

        for (int i = 0; i < LeftFootNumber.length; i++) {
            String[] stringArray = new String[LeftFootNumber.length];
            if (LeftFootNumber[i] != null) {
                stringArray = LeftFootNumber[i].split(" "); // �h���r�ꤤ���Ů�
            } else {
                break;
            }

            LeftFootArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftFootArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        //�k��ƭ�RightHandArrays
        RightHandNumber = db.getRighthandResult();

        for (int i = 0; i < RightHandNumber.length; i++) {
            String[] stringArray = new String[RightHandNumber.length];
            if (RightHandNumber[i] != null) {
                stringArray = RightHandNumber[i].split(" "); // �h���r�ꤤ���Ů�
            } else {
                break;
            }

            RightHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                RightHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        //�k�}�ƭ�RightFootArrays
        RightFootNumber = db.getRightfootResult();

        for (int i = 0; i < RightFootNumber.length; i++) {
            String[] stringArray = new String[RightFootNumber.length];
            if (RightFootNumber[i] != null) {
                stringArray = RightFootNumber[i].split(" "); // �h���r�ꤤ���Ů�
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
                //�N�@����ƶi��B��
                double[] Log = new double[24];
                // �}�l�p��ƭ�
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

                //next�ǰe�ƾڨ�ƾڭp��table


            }
        }
    }

    public void LogCalOutPut() {
        //�ɶ�TimeNumber
        String TimeNumber[] = new String[db.getDateResult().length];
        TimeNumber = db.getDateResult();
        System.out.println(TimeNumber[0]);

        //����ƭ�LeftHandArrays
        String LeftHandNumber[] = new String[db.getLefthandResult().length];
        double[][] LeftHandArrays = new double[LeftHandNumber.length][];
        LeftHandNumber = db.getLefthandResult();
        for (int i = 0; i < LeftHandNumber.length; i++) {
            String[] stringArray = new String[LeftHandNumber.length];
            if (LeftHandNumber[i] != null) {
                stringArray = LeftHandNumber[i].split(" "); // �h���r�ꤤ���Ů�
            } else {
                break;
            }
            LeftHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }
        }

        //���}�ƭ�LeftFootArrays
        String LeftFootNumber[] = new String[db.getLeftfootResult().length];
        double[][] LeftFootArrays = new double[LeftFootNumber.length][];
        LeftFootNumber = db.getLeftfootResult();
        for (int i = 0; i < LeftFootNumber.length; i++) {
            String[] stringArray = new String[LeftFootNumber.length];
            if (LeftFootNumber[i] != null) {
                stringArray = LeftFootNumber[i].split(" "); // �h���r�ꤤ���Ů�
            } else {
                break;
            }
            LeftFootArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                LeftFootArrays[i][j] = Integer.parseInt(stringArray[j]);
            }
        }

        //�k��ƭ�RightHandArrays
        String RightHandNumber[] = new String[db.getRighthandResult().length];
        double[][] RightHandArrays = new double[RightHandNumber.length][];
        RightHandNumber = db.getRighthandResult();
        for (int i = 0; i < RightHandNumber.length; i++) {
            String[] stringArray = new String[RightHandNumber.length];
            if (RightHandNumber[i] != null) {
                stringArray = RightHandNumber[i].split(" "); // �h���r�ꤤ���Ů�
            } else {
                break;
            }
            RightHandArrays[i] = new double[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                RightHandArrays[i][j] = Integer.parseInt(stringArray[j]);
            }

        }

        //�k�}�ƭ�RightFootArrays
        String RightFootNumber[] = new String[db.getRightfootResult().length];
        double[][] RightFootArrays = new double[RightFootNumber.length][];
        RightFootNumber = db.getRightfootResult();
        for (int i = 0; i < RightFootNumber.length; i++) {
            String[] stringArray = new String[RightFootNumber.length];
            if (RightFootNumber[i] != null) {
                stringArray = RightFootNumber[i].split(" "); // �h���r�ꤤ���Ů�
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
                //�N�@����ƶi��B��
                double[] Log = new double[24];
                // �}�l�p��ƭ�
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

                //�ǰe�ƾڨ�WriteFiles
                Write.main(mycal.StrData, mycal.StrAvg, mycal.StrFRSUM, mycal.StrYY, mycal.StrLR, outputcount, mycal.StrSTD);
                mycal.ToDefault();

            }
        }
    }


//----------------------------------------------------------------------
//�ƥ��ť���{����
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

    // �����H���Ʀr
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

    // �ǰe��ƨ�Ƶ{��
    public ActionListener SendData = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.print("��l�ƭ�" + "\n");
            // �}�l�p��ƭ�
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
                        sp.write("2"); // ����Arduino�ǰe��ƪ�����T����"2"(�w���e�@����������q�A�G�������A�Ұ�)
                        Thread.sleep(100);
                        sp.write("1"); // �Ұ�Arduino�ǰe��ƪ�����T����"1"
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
                                for (int x = 0; x < chart.pointValue.length; x++) chart.pointValue[x] = -1; //�M�ŵe���I�ƾ�
                                g = chart.getGraphics();       //���o�e��
                                chart.paintComponent(g);
                            }

                            while (!music3_Player.isStopped()) {
                                Thread.sleep(10);
                            }
                            fieldCount = fieldCount + 1;
                            if (pressStopMeasure == 0)
                                changePicture(fieldCount);
                            stepsOfAMeasure = -1; // �C�Ӵ��q���Φ�6��steps
                            Thread.sleep(10);

                            if (fieldCount == 28) {
                                positionPicLabel.setIcon(null);
                                positionPicLabel.setText("�������q");
                                positionPicLabel.setFont(new Font("�L�n������", Font.BOLD, 36));
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

            thread_readData.start(); // ������q��ƪ�������Ұ�
            thread_changeField.start(); // �Ұʦ۰��ܴ����q�I�������

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
                sp.write("2"); // ����Arduino�ǰe��ƪ�����T����"2"
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

    //�����ϥΪ�
    public ActionListener SwitchUser = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if ((String) backgroundComboBox.getSelectedItem() != "��ܨϥΪ�") {
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
                System.out.println("�ϥΪ̤w�������G" + UserName);

            } else if (backgroundComboBox.getSelectedItem() == "��ܨϥΪ�") {
                ChangePhoneNumber.setEnabled(false);
            }

        }
    };

    //�����ϥΪ�
    public ActionListener SwitchUser1 = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if ((String) backgroundComboBox1.getSelectedItem() != "��ܨϥΪ�") {
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
                System.out.println("�ϥΪ̤w�������G" + UserName);

            } else if (backgroundComboBox1.getSelectedItem() == "��ܨϥΪ�") {
                ChangePhoneNumber.setEnabled(false);
            }

        }
    };

    //�s��q�ܸ��X
    public ActionListener ChangePhoneNum = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ChangePhoneNumber.setVisible(false);
            SavePhoneNumber.setVisible(true);
            PhoneNumberTextField.setEnabled(true);

        }
    };

    //�x�s�q�ܸ��X
    public ActionListener SavePhoneNum = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ChangePhoneNumber.setVisible(true);
            SavePhoneNumber.setVisible(false);
            PhoneNumberTextField.setEnabled(false);
            //�N��ƶǤJ��Ʈw��
            data.setUser(UserName);
            data.setPhone(PhoneNumberTextField.getText());
            db.UpdatePhone();
        }
    };

    //�M�Ŵ��q�O��&�q�ܸ��X
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

    //���U�s�ϥΪ�
    public ActionListener Register = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (db.CountUser() < 10) {
                String name = userTextField.getText();
                String phone = phoneTextField.getText();
                String gender = genderBox.getSelectedItem().toString();
                String birthyear = yearBox.getSelectedItem().toString();
                String city = cityComboBox.getSelectedItem().toString();
                if (name.equals("") || phone.equals("") || gender.equals("�п��") || birthyear.equals("") || city.equals("�п��")) {
                    JOptionPane.showMessageDialog(null, "�Ҧ�����쳣�n��g!!");
                } else {
                    RD.setName(name);
                    RD.setPhone(phone);
                    RD.setGender(gender);
                    RD.setBirthyear(birthyear);
                    RD.setCity(city);
                    db.RegisterInsertData(RD);

                    //�[�J��ܨϥΪ���줤
                    backgroundComboBox.addItem(name);

                    //�M�ŵ��U���
                    userTextField.setText("");
                    phoneTextField.setText("");
                    genderBox.setSelectedIndex(0);
                    yearBox.setSelectedIndex(0);
                    cityComboBox.setSelectedIndex(0);

                    //�M���ܼ�
                    name = "";
                    phone = "";
                    gender = "";
                    birthyear = "";
                    city = "";
                }
            } else {
                JOptionPane.showMessageDialog(null, "�ϥΪ̤w�F�W���]10�^�I", "�ާ@ĵ�T", JOptionPane.ERROR_MESSAGE);
                //�M�ŵ��U���
                userTextField.setText("");
                phoneTextField.setText("");
                genderBox.setSelectedIndex(0);
                yearBox.setSelectedIndex(0);
                cityComboBox.setSelectedIndex(0);
            }
        }
    };

    //��u��
    class ClineChart extends JPanel {

        String[] xScaleStr = {"0", "1", "2", "3", "4", "5"};  //x�b���
        String[] yScaleStr = {"0", "50", "100", "150", "200", "250"};     //y�b���
        int[] pointValue = new int[6];   //6���I����
        int[] px = new int[6];    //�I����������x�y��
        int[] py = new int[6];    //�I����������y�y��


        ClineChart() {
            setSize(755, 280);    //ClineChart�����N�OJPanel�A�������]�j�p
            for (int i = 0; i < pointValue.length; i++)
                pointValue[i] = -1;     //�w�]-1�A���|�e�X<0���I
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new Color(238, 238, 238));
            g2.fillRect(50, 40, 650, 280); // ��W��l�C��A�Y�M��

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

            // �e��u��
            px[0] = xAxisStart;
            py[0] = yAxisStart;
            for (int i = 1; i < pointValue.length; i++) {
                if (pointValue[i] >= 0) {
                    px[i] = xAxisStart + 116 * i;
                    py[i] = yAxisStart - (int) (0.116 * pointValue[i]);
                    // �Ыؤ@�Ӥ��ϥε�u�˦��� BasicStroke ����
                    g2.setStroke(new BasicStroke(3.0f));


                    g2.setColor(Color.black);
                    g2.drawLine(px[i - 1], py[i - 1], px[i], py[i]);

                    // �e�¦��ߤp���
                    if (xScaleStr[i].equals("5")) {
                        g2.setColor(MyColor1);
                        int circleSize = 10; // �ꪺ�j�p
                        g2.fillOval(px[i] - circleSize / 2, py[i] - circleSize / 2, circleSize, circleSize);
                    }

                }
            }
        }
    }

    // �۩w?TabbedPaneUI�H����???��
    class NoTabBorderAndBackgroundTabbedPaneUI extends BasicTabbedPaneUI {
        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            // ��ø�s�������
        }

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            // ��ø�s���ҭI��

            // �襤���Үɩ��u
            if (isSelected) {
                g.setColor(Color.BLUE); // ���u?��
                g.fillRect(x, y + h - 5, w, 5); // ���u����m�M�j�p
            }
        }
    }

}
