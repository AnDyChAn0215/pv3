//匯入需要的各類套件
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;
import javax.swing.UIManager;

//人機互動層類別
class CHCI_myLogin_pane extends JPanel{

	JPanel p1 = new JPanel(); 	//登入標題區
	JPanel p2 = new JPanel();	//文字框區
	JPanel p3 = new JPanel();   //按鈕區

	//建立圖示物件
	ImageIcon icon1 = new ImageIcon("images/name.png");				//姓名
	ImageIcon icon2 = new ImageIcon("images/phone.png");		//電話號碼

	Font font1 = new Font("微軟正黑體", Font.BOLD, 36); //建立字型物件font1為微軟正黑體、粗斜體、字體大小為36點的字
	Font font2 = new Font("微軟正黑體", Font.BOLD, 20);
	Font font3 = new Font("微軟正黑體", Font.BOLD, 16);

	JLabel b0 = new JLabel("");			//提示小字
	JLabel b1 = new JLabel("登入");
	JLabel b2 = new JLabel(icon1);		//帳號
	JLabel b3 = new JLabel(icon2);		//密碼

	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();

	JButton btn0 = new JButton("訪客");
	JButton btn1 = new JButton("登入");
	JButton btn2 = new JButton("清空");
	JButton btn3 = new JButton("註冊");


	Color color1 = new Color(0,47,73);		//皇家藍
	Color color2 = new Color(255,255,255);

	//取得螢幕寬（w）與高（h）
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight();


	//建構子:類別CHCI_myLogin_pane
	public CHCI_myLogin_pane(){

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		//登入標題區
		p1.setLayout(null);
		p1.setBounds(0, 0,(int)(0.25*w),(int)(0.08*h));
		p1.setBackground(color2);
		add(p1);

		//文字框
		p2.setLayout(null);
		p2.setBounds(0, (int)(0.05*h),(int)(0.25*w),(int)(0.225*h));
		p2.setBackground(color2);
		add(p2);

		//按鈕區
		p3.setLayout(null);
		p3.setBounds(0, (int)(0.275*h),(int)(0.25*w),(int)(0.2*h));
		p3.setBackground(color2);
		add(p3);

		//登入標題區
		//登入
		b1.setLayout(null);
		b1.setBounds(0, 0, (int)(0.25*w), (int)(0.08*h));
		b1.setHorizontalAlignment(JTextField.CENTER);
		b1.setFont(font1);
		p1.add(b1);

		//文字框區
		//姓名
		b2.setLayout(null);
		b2.setBounds(0, (int)(0.051*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b2);

		//電話號碼
		b3.setLayout(null);
		b3.setBounds(0, (int)(0.151*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b3);

		//設定文字欄位
		tf1.setLayout(null);
		tf1.setBounds((int)(0.0625*w), (int)(0.051*h), (int)(0.15625*w),(int)(0.05*h));
		tf1.setFont(font2);
		tf1.setBackground(Color.white);
		p2.add(tf1);

		//設定電話號碼欄位
		tf2.setLayout(null);
		tf2.setBounds((int)(0.0625*w), (int)(0.151*h), (int)(0.15625*w),(int)(0.05*h));
		tf2.setFont(font2);
		tf2.setBackground(Color.white);
		p2.add(tf2);

		//顯示登入結果用
		b0.setLayout(null);
		b0.setBounds((int)(0.0625*w), (int)(0.2015*h), (int)(0.15625*w),(int)(0.025*h));
		b0.setFont(font3);
		p2.add(b0);

		//按鈕區
		//設定按鈕（訪客）
		btn0.setBounds((int)(0.02*w), (int)(0.1*h), (int)(0.2*w), (int)(0.05*h));
		btn0.setFont(font2);
		btn0.setForeground(Color.white);
		btn0.setBackground(color1);
		btn0.setFocusPainted(false);
		p3.add(btn0);

		//設定按鈕（登入）
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

		//設定按鈕（註冊）
		btn3.setBounds((int)(0.17*w), (int)(0.025*h), (int)(0.05*w), (int)(0.05*h));
		btn3.setFont(font2);
		btn3.setForeground(Color.white);
		btn3.setBackground(color1);
		btn3.setFocusPainted(false);
		p3.add(btn3);

		setLayout(null);
		setBounds((int)(0.25*w), 0,(int)(0.25*w),(int)(0.5*h));
		setBackground(color2);
		setVisible(true);

	}

	//方法:清空容器內欄位
	public void clearPane(){

		tf1.setText("");
		tf2.setText("");
		b0.setText("");

	}

	//事件傾聽程式: 處理點按[清空]按鈕
	public ActionListener ProcessClearFields = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			clearPane();

		}
	};

} //end for: class CHCI_myLogin_pane
