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
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.plaf.FontUIResource; //修改JOptionPane字體所需套件
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;

public class LoginFrame extends JFrame {
	
	DataBase db = new DataBase();
    Data data = new Data();
	RegisterData RD = new RegisterData();
	
	//建立本系統所需的各個物件
    Clogin_frame  myFrame = new Clogin_frame(); 				//登入模組（myFrame,裡面又含有：myLogo, myLogin_pane, myRegister_pane）
	
	//取得螢幕寬（w）與高（h）
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight(); 	 
   
   //建構子:類別Clogin_frame
   public LoginFrame(){
	   
		myFrame.myLogin_pane.tf1.addKeyListener(LoginSelection);             						//登入頁面姓名輸入框
		myFrame.myLogin_pane.tf2.addKeyListener(LoginSelection);             						//登入頁面電話號碼輸入框
	   	myFrame.myLogin_pane.btn0.addActionListener(ProcessLoginSelection);             			//登入頁面登入按鈕
		myFrame.myLogin_pane.btn1.addActionListener(ProcessLoginSelection);             			//登入頁面登入按鈕
		myFrame.myLogin_pane.btn3.addActionListener(ProcessFunSelection);               			//登入頁面註冊按鈕（切換頁面）		
        myFrame.myRegister_pane.btn3.addActionListener(ProcessFunSelection);      	    			//註冊頁面返回按鈕（切換頁面）
		myFrame.myRegister_pane.btn1.addActionListener(ProcessSaveStaffInformation);    			//註冊頁面註冊按鈕（儲存註冊資料）

    }

//----------------------------------------------------------------------
//方法程式區
//----------------------------------------------------------------------
	//事件傾聽器程式：處理登入按鈕
	public ActionListener ProcessLoginSelection = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if( e.getSource() == myFrame.myLogin_pane.btn1 ){
				String accountString = myFrame.myLogin_pane.tf1.getText().trim();  			//取得[登入資料]中的[帳號字串]
				String passwordString = myFrame.myLogin_pane.tf2.getText().trim(); 			//取得[登入資料]中的[密碼字串]
				if(  (accountString.length() > 0) && (passwordString.length() > 0) ){    	//如果[姓名字串]長度大於0,即有輸入姓名資料,才進入查詢處理
					String[] loginResult = db.LoginCheck(accountString);   					//呼叫[資料庫操作存取物件(myDBMA)]的查詢學生紀錄方法(findRD_in_TB_sttaff())去查詢員工紀錄,並回傳儲存到loginResult中
					if( ( accountString.equals(loginResult[0]) ) && ( passwordString.equals(loginResult[1]) ) ){
						myFrame.myLogin_pane.b0.setText("登入成功");							
						myFrame.myLogin_pane.b0.setForeground(Color.black);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
						myFrame.setVisible(false);		//隱藏登入畫面
						Cfunc1 mainFrame = new Cfunc1();		
						mainFrame.setUser(loginResult[0]);
						dispose();
					}else{
						myFrame.myLogin_pane.b0.setText("帳密錯誤");
						myFrame.myLogin_pane.b0.setForeground(Color.red);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
					}

				} else {
                JOptionPane.showMessageDialog(null,"未完整輸入登入資料，請填寫後再登入！","操作警訊",JOptionPane.ERROR_MESSAGE);
				}
			}else if (e.getSource() == myFrame.myLogin_pane.btn0) {
				myFrame.setVisible(false);		//隱藏登入畫面	
				Cfunc1 mainFrame = new Cfunc1();	
				mainFrame.setUser("訪客");
				dispose();
			}
		}
	};		//注意：事件傾聽器程式結尾有";"	

    //事件傾聽程式：處理登入頁及註冊頁按鈕
    public ActionListener ProcessFunSelection = new ActionListener(){
        public void actionPerformed(ActionEvent e){
			
            if( e.getSource() == myFrame.myLogin_pane.btn3 ){
				
				if( db.CountUser() < 10 ){
					myFrame.myLogin_pane.setVisible(false);		     //隱藏登入頁面 
					myFrame.myLogin_pane.clearPane();                //清空登入頁面   
					myFrame.myRegister_pane.setVisible(true);		 //顯示註冊頁面
					myFrame.myRegister_pane.clearPane();             //清空註冊頁面				
				}else{
					JOptionPane.showMessageDialog(null,"使用者已達上限（10）！","操作警訊",JOptionPane.ERROR_MESSAGE);
				}	
				
			}
            if( e.getSource() == myFrame.myRegister_pane.btn3 ){
				
				myFrame.myRegister_pane.setVisible(false);		 	 //隱藏註冊頁面
                myFrame.myRegister_pane.clearPane();             	 //清空註冊頁面
				myFrame.myLogin_pane.setVisible(true);		     	 //顯示登入頁面 
				myFrame.myLogin_pane.clearPane();                	 //清空登入頁面   

            }
        }    
    };

    //事件傾聽程式：處理註冊資料儲存
    public ActionListener ProcessSaveStaffInformation = new ActionListener(){
        public void actionPerformed(ActionEvent e){
             
			if( e.getSource() == myFrame.myRegister_pane.btn1 && db.CountUser() < 10){
				
            boolean checkPass = true;            	//用來記錄[輸入的註冊資料]檢查結果
			
            String nameString = myFrame.myRegister_pane.tf1.getText().trim();  						//取得[輸入的註冊資料]中的[姓名字串]
            String phoneString = myFrame.myRegister_pane.tf2.getText().trim(); 		 				//取得[輸入的註冊資料]中的[電話字串] (註:trim()方法會把字串後面空白除掉)
            String cityString = myFrame.myRegister_pane.cbox1.getSelectedItem().toString();  		//取得[輸入的註冊資料]中的[城市字串]
			String birthyearString = myFrame.myRegister_pane.cbox2.getSelectedItem().toString(); 	//取得[輸入的註冊資料]中的[出生年（西元）字串]
			String genderString = myFrame.myRegister_pane.cbox3.getSelectedItem().toString();  	 	//取得[輸入的註冊資料]中的[性別字串]
				
				if(  nameString.length() == 0 ){        //檢查nameString是否有輸入任何字元,length()為計算字串長度方法,若長度為0則未輸入資料
					checkPass = false;
					JOptionPane.showMessageDialog(null,"【使用者姓名】空白未輸入資料！","操作警訊",JOptionPane.ERROR_MESSAGE);
				}
	
				if(  phoneString.length() == 0 ){       //檢查phoneString是否有輸入任何字元,length()為計算字串長度方法,若長度為0則未輸入資料
					checkPass = false;
					JOptionPane.showMessageDialog(null,"【使用者電話】空白未輸入資料！","操作警訊",JOptionPane.ERROR_MESSAGE);
				}
				
				if (db.CheckNamePhone(nameString, phoneString) == 1){
					checkPass = false;
                    JOptionPane.showMessageDialog(null,"已存在的姓名電話！");
				}
	
				//如果上述三個檢查都沒發現錯誤,則checkPass會維持true,即通過檢查,因此將員工的職位，姓名，帳號，密碼設定到myRegister物件中對應屬性質  
				if( checkPass == true ){
					
					RD.setName(nameString);
					RD.setPhone(phoneString);
					RD.setGender(genderString);
					RD.setBirthyear(birthyearString);
					RD.setCity(cityString);

					myFrame.myRegister_pane.tf1.setText(""); 
					myFrame.myRegister_pane.tf2.setText(""); 
					
					db.RegisterInsertData(RD);//將註冊物件傳入[資料庫操作存取物件(db)]的儲存使用者紀錄方法(RegisterInsertData())去儲存使用者紀錄到資料庫
				}
			}else{
				JOptionPane.showMessageDialog(null,"使用者已達上限（10）！","操作警訊",JOptionPane.ERROR_MESSAGE);
				myFrame.myRegister_pane.clearPane();
			}  
		}
    };
	
	//按鍵傾聽器程式：處理登入按鈕
	public KeyListener LoginSelection = new KeyAdapter(){
		@Override
		public void keyTyped(KeyEvent e){
			if( e.getKeyChar() == KeyEvent.VK_ENTER ){
				String accountString = myFrame.myLogin_pane.tf1.getText().trim();  			//取得[登入資料]中的[帳號字串]
				String passwordString = myFrame.myLogin_pane.tf2.getText().trim(); 			//取得[登入資料]中的[密碼字串]
				if(  (accountString.length() > 0) && (passwordString.length() > 0) ){    	//如果[姓名字串]長度大於0,即有輸入姓名資料,才進入查詢處理
					String[] loginResult = db.LoginCheck(accountString);   					//呼叫[資料庫操作存取物件(myDBMA)]的查詢學生紀錄方法(findRD_in_TB_sttaff())去查詢員工紀錄,並回傳儲存到loginResult中
					if( ( accountString.equals(loginResult[0]) ) && ( passwordString.equals(loginResult[1]) ) ){
						myFrame.myLogin_pane.b0.setText("登入成功");							
						myFrame.myLogin_pane.b0.setForeground(Color.black);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
						myFrame.setVisible(false);		//隱藏登入畫面
						Cfunc1 mainFrame = new Cfunc1();		
						mainFrame.setUser(loginResult[0]);
						dispose();
					}else{
						myFrame.myLogin_pane.b0.setText("帳密錯誤");
						myFrame.myLogin_pane.b0.setForeground(Color.red);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
					}

				} else {
                JOptionPane.showMessageDialog(null,"未完整輸入登入資料，請填寫後再登入！","操作警訊",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};		//注意：事件傾聽器程式結尾有";"	
}
