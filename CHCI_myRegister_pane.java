//匯入需要的各類套件
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

 class CHCI_myRegister_pane extends JPanel{
	 
	int STARTYEAR = 1900;
	int ENDYEAR = 2030;

	JPanel p1 = new JPanel(); 	//註冊標題區
	JPanel p2 = new JPanel();	//文字框區
	JPanel p3 = new JPanel();   //按鈕區
	
	//建立圖示物件
	ImageIcon icon1 = new ImageIcon("images/name.png");		//姓名
	ImageIcon icon2 = new ImageIcon("images/phone.png");	//電話號碼
	ImageIcon icon3 = new ImageIcon("images/city.png");		//城市
	ImageIcon icon4 = new ImageIcon("images/birthyear.png");//出生年（西元）
	ImageIcon icon5 = new ImageIcon("images/gender.png");	//性別
	

	Font font1 = new Font("微軟正黑體", Font.BOLD, 36); //建立字型物件font1為微軟正黑體、粗斜體、字體大小為18點的字
	Font font2 = new Font("微軟正黑體", Font.BOLD, 20); 
	Font font3 = new Font("微軟正黑體", Font.BOLD, 16);

	JLabel b1 = new JLabel("註冊");
	JLabel b2 = new JLabel(icon1);	//姓名
	JLabel b3 = new JLabel(icon2);	//電話號碼
	JLabel b4 = new JLabel(icon3);	//城市
	JLabel b5 = new JLabel(icon4);	//出生年（西元）
	JLabel b6 = new JLabel(icon5);	//性別
	
	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	
	JButton btn1 = new JButton("註冊");
	JButton btn2 = new JButton("清空");
	JButton btn3 = new JButton("返回");

	String[] items1 = {"臺北市", "新北市", "基隆市", "桃園市", "新竹市", "苗栗縣", "臺中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "臺南市", "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};		//下拉選單的內容
	JComboBox cbox1 = new JComboBox (items1);		//建立下拉選單

	JComboBox cbox2 = new JComboBox ();				//建立下拉選單	
	
	String[] items3 = {"男", "女"};					//下拉選單的內容
	JComboBox cbox3 = new JComboBox (items3);		//建立下拉選單
	
	Color color1 = new Color(0,47,73);		//皇家藍
	Color color2 = new Color(255,255,255);
		
	//取得螢幕寬（w）與高（h）
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight();               
    
     //建構子:類別CHCI_myRegister_pane
    public CHCI_myRegister_pane(){
		 
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//註冊標題區
		p1.setLayout(null);		
		p1.setBounds(0, 0,(int)(0.25*w),(int)(0.08*h));
		p1.setBackground(color2);
		add(p1);
		
		//文字框
		p2.setLayout(null);		
		p2.setBounds(0, (int)(0.05*h),(int)(0.25*w),(int)(0.3*h));
		p2.setBackground(color2);
		add(p2);
		
		//按鈕區
		p3.setLayout(null);		
		p3.setBounds(0, (int)(0.35*h),(int)(0.25*w),(int)(0.15*h));
		p3.setBackground(color2);
		add(p3);
		
		//註冊標題區
		//註冊標題
		b1.setLayout(null);
		b1.setBounds(0, 0, (int)(0.25*w), (int)(0.08*h));
		b1.setHorizontalAlignment(JTextField.CENTER);
		b1.setFont(font1);
		p1.add(b1);
		
		//文字框區		
		//姓名
		b2.setLayout(null);
		b2.setBounds(0, (int)(0.03*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b2);

		//電話號碼
		b3.setLayout(null);
		b3.setBounds(0, (int)(0.103*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b3);
		
		//城市
		b4.setLayout(null);
		b4.setBounds(0, (int)(0.176*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b4);
				
		//出生年（西元）：
		b5.setLayout(null);
		b5.setBounds(0, (int)(0.249*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b5);
		
		//性別
		b6.setLayout(null);
		b6.setBounds((int)(0.109375*w), (int)(0.249*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b6);
		
		//設定文字欄位	
		//姓名
		tf1.setLayout(null);
		tf1.setBounds((int)(0.0625*w), (int)(0.03*h), (int)(0.15625*w), (int)(0.05*h));
		tf1.setFont(font2);
		tf1.setBackground(Color.white);
		p2.add(tf1);
		
		//電話號碼		
		tf2.setLayout(null);
		tf2.setBounds((int)(0.0625*w), (int)(0.103*h), (int)(0.15625*w),(int)(0.05*h));
		tf2.setFont(font2);
		tf2.setBackground(Color.white);
		p2.add(tf2);

		//城市
		cbox1.setBounds((int)(0.0625*w), (int)(0.176*h), (int)(0.15625*w),(int)(0.05*h));
		cbox1.setFont(font2);		
		p2.add(cbox1);	

		//出生年（西元）
		for (int i = STARTYEAR; i <= ENDYEAR; i++) {
			cbox2.addItem(i);
		}
		cbox2.setBounds((int)(0.0625*w), (int)(0.249*h), (int)(0.046875*w),(int)(0.05*h));
		cbox2.setFont(font2);		
		p2.add(cbox2);		
		
		//性別
		cbox3.setBounds((int)(0.171875*w), (int)(0.249*h), (int)(0.046875*w),(int)(0.05*h));
		cbox3.setFont(font2);		
		p2.add(cbox3);	
		
		//按鈕區
		//設定按鈕（註冊）		
		btn1.setBounds((int)(0.02*w), (int)(0.025*h), (int)(0.05*w), (int)(0.05*h));
		btn1.setFont(font2);
		btn1.setForeground(Color.white);
		btn1.setBackground(color1);
		btn1.setFocusPainted(false);
		p3.add(btn1);
		
		//設定按鈕（清空）
		btn2.setBounds((int)(0.095*w), (int)(0.025*h), (int)(0.05*w), (int)(0.05*h));
		btn2.setFont(font2);
		btn2.setForeground(Color.white);
		btn2.setBackground(color1);
		btn2.addActionListener(ProcessClearFields);   //[新增學生資料]操作畫面中[清除]按鈕加到[事件傾聽程式]
		btn2.setFocusPainted(false);
		p3.add(btn2);
		
		//設定按鈕（返回）
		btn3.setBounds((int)(0.17*w), (int)(0.025*h), (int)(0.05*w), (int)(0.05*h));
		btn3.setFont(font2);
		btn3.setForeground(Color.white);
		btn3.setBackground(color1);
		btn3.setFocusPainted(false);
		p3.add(btn3);

		setLayout(null);		
		setBounds((int)(0.25*w), 0,(int)(0.25*w),(int)(0.5*h));
		setBackground(color2);
		setVisible(false);


     }

     //方法:清空容器內欄位
    public void clearPane(){

		tf1.setText("");
		tf2.setText("");

    }

     //事件傾聽程式: 處理點按[清空]按鈕
    public ActionListener ProcessClearFields = new ActionListener(){
        public void actionPerformed(ActionEvent e){
     
             clearPane();
 
        }    
    };

 } //end for: class CHCI_myRegister_pane

 