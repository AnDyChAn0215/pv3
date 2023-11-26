//匯入需要的各類套件
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.text.DateFormat;
import javax.swing.UIManager;
import java.text.SimpleDateFormat;
import javax.swing.plaf.FontUIResource; //修改JOptionPane字體所需套件
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;

class Clogin_frame extends JFrame{      //系統的視窗

	CHCI_logo myLogo = new CHCI_logo();                 					 //Logo(為JPanel，內含1個Logo標籤)     
	CHCI_myLogin_pane myLogin_pane = new CHCI_myLogin_pane();       		 //登入頁面(為JPanel，內含標籤，文字欄位，按鈕等)
	CHCI_myRegister_pane myRegister_pane = new CHCI_myRegister_pane();   	 //註冊頁面(為JPanel，內含標籤，文字欄位，按鈕等)  
	
	//取得螢幕寬（w）與高（h）
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight(); 	 
   
   //建構子:類別Clogin_frame
   public Clogin_frame(){
	   
	   	try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

		add(myLogo);        		//將[Logo]加到此視窗中
		add(myLogin_pane);     		//將[登入頁面]加到此視窗中
		add(myRegister_pane);   	//將[註冊頁面]加到此視窗中

		myLogin_pane.setVisible(true);   	  //預設[登入資料]畫面顯示
		myRegister_pane.setVisible(false);    //預設[註冊資料]畫面隱藏
		 		 
		//系統視窗的基本設定 
		setLayout(null);
		setBounds((int)(0.25*w),(int)(0.25*h),(int)(0.5*w),(int)(0.5*h));
		//視窗置於螢幕中且為寬高一半大小
		setIconImage(new ImageIcon("images/logo.png").getImage());
		setTitle("良導絡經絡測量系統");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

} //end for: class Clogin_frame

 