//�פJ�ݭn���U���M��
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
import javax.swing.plaf.FontUIResource; //�ק�JOptionPane�r��һݮM��
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;

public class LoginFrame extends JFrame {
	
	DataBase db = new DataBase();
    Data data = new Data();
	RegisterData RD = new RegisterData();
	
	//�إߥ��t�Ωһݪ��U�Ӫ���
    Clogin_frame  myFrame = new Clogin_frame(); 				//�n�J�Ҳա]myFrame,�̭��S�t���GmyLogo, myLogin_pane, myRegister_pane�^
	
	//���o�ù��e�]w�^�P���]h�^
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight(); 	 
   
   //�غc�l:���OClogin_frame
   public LoginFrame(){
	   
		myFrame.myLogin_pane.tf1.addKeyListener(LoginSelection);             						//�n�J�����m�W��J��
		myFrame.myLogin_pane.tf2.addKeyListener(LoginSelection);             						//�n�J�����q�ܸ��X��J��
	   	myFrame.myLogin_pane.btn0.addActionListener(ProcessLoginSelection);             			//�n�J�����n�J���s
		myFrame.myLogin_pane.btn1.addActionListener(ProcessLoginSelection);             			//�n�J�����n�J���s
		myFrame.myLogin_pane.btn3.addActionListener(ProcessFunSelection);               			//�n�J�������U���s�]���������^		
        myFrame.myRegister_pane.btn3.addActionListener(ProcessFunSelection);      	    			//���U������^���s�]���������^
		myFrame.myRegister_pane.btn1.addActionListener(ProcessSaveStaffInformation);    			//���U�������U���s�]�x�s���U��ơ^

    }

//----------------------------------------------------------------------
//��k�{����
//----------------------------------------------------------------------
	//�ƥ��ť���{���G�B�z�n�J���s
	public ActionListener ProcessLoginSelection = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if( e.getSource() == myFrame.myLogin_pane.btn1 ){
				String accountString = myFrame.myLogin_pane.tf1.getText().trim();  			//���o[�n�J���]����[�b���r��]
				String passwordString = myFrame.myLogin_pane.tf2.getText().trim(); 			//���o[�n�J���]����[�K�X�r��]
				if(  (accountString.length() > 0) && (passwordString.length() > 0) ){    	//�p�G[�m�W�r��]���פj��0,�Y����J�m�W���,�~�i�J�d�߳B�z
					String[] loginResult = db.LoginCheck(accountString);   					//�I�s[��Ʈw�ާ@�s������(myDBMA)]���d�߾ǥͬ�����k(findRD_in_TB_sttaff())�h�d�߭��u����,�æ^���x�s��loginResult��
					if( ( accountString.equals(loginResult[0]) ) && ( passwordString.equals(loginResult[1]) ) ){
						myFrame.myLogin_pane.b0.setText("�n�J���\");							
						myFrame.myLogin_pane.b0.setForeground(Color.black);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
						myFrame.setVisible(false);		//���õn�J�e��
						Cfunc1 mainFrame = new Cfunc1();		
						mainFrame.setUser(loginResult[0]);
						dispose();
					}else{
						myFrame.myLogin_pane.b0.setText("�b�K���~");
						myFrame.myLogin_pane.b0.setForeground(Color.red);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
					}

				} else {
                JOptionPane.showMessageDialog(null,"�������J�n�J��ơA�ж�g��A�n�J�I","�ާ@ĵ�T",JOptionPane.ERROR_MESSAGE);
				}
			}else if (e.getSource() == myFrame.myLogin_pane.btn0) {
				myFrame.setVisible(false);		//���õn�J�e��	
				Cfunc1 mainFrame = new Cfunc1();	
				mainFrame.setUser("�X��");
				dispose();
			}
		}
	};		//�`�N�G�ƥ��ť���{��������";"	

    //�ƥ��ť�{���G�B�z�n�J���ε��U�����s
    public ActionListener ProcessFunSelection = new ActionListener(){
        public void actionPerformed(ActionEvent e){
			
            if( e.getSource() == myFrame.myLogin_pane.btn3 ){
				
				if( db.CountUser() < 10 ){
					myFrame.myLogin_pane.setVisible(false);		     //���õn�J���� 
					myFrame.myLogin_pane.clearPane();                //�M�ŵn�J����   
					myFrame.myRegister_pane.setVisible(true);		 //��ܵ��U����
					myFrame.myRegister_pane.clearPane();             //�M�ŵ��U����				
				}else{
					JOptionPane.showMessageDialog(null,"�ϥΪ̤w�F�W���]10�^�I","�ާ@ĵ�T",JOptionPane.ERROR_MESSAGE);
				}	
				
			}
            if( e.getSource() == myFrame.myRegister_pane.btn3 ){
				
				myFrame.myRegister_pane.setVisible(false);		 	 //���õ��U����
                myFrame.myRegister_pane.clearPane();             	 //�M�ŵ��U����
				myFrame.myLogin_pane.setVisible(true);		     	 //��ܵn�J���� 
				myFrame.myLogin_pane.clearPane();                	 //�M�ŵn�J����   

            }
        }    
    };

    //�ƥ��ť�{���G�B�z���U����x�s
    public ActionListener ProcessSaveStaffInformation = new ActionListener(){
        public void actionPerformed(ActionEvent e){
             
			if( e.getSource() == myFrame.myRegister_pane.btn1 && db.CountUser() < 10){
				
            boolean checkPass = true;            	//�ΨӰO��[��J�����U���]�ˬd���G
			
            String nameString = myFrame.myRegister_pane.tf1.getText().trim();  						//���o[��J�����U���]����[�m�W�r��]
            String phoneString = myFrame.myRegister_pane.tf2.getText().trim(); 		 				//���o[��J�����U���]����[�q�ܦr��] (��:trim()��k�|��r��᭱�ťհ���)
            String cityString = myFrame.myRegister_pane.cbox1.getSelectedItem().toString();  		//���o[��J�����U���]����[�����r��]
			String birthyearString = myFrame.myRegister_pane.cbox2.getSelectedItem().toString(); 	//���o[��J�����U���]����[�X�ͦ~�]�褸�^�r��]
			String genderString = myFrame.myRegister_pane.cbox3.getSelectedItem().toString();  	 	//���o[��J�����U���]����[�ʧO�r��]
				
				if(  nameString.length() == 0 ){        //�ˬdnameString�O�_����J����r��,length()���p��r����פ�k,�Y���׬�0�h����J���
					checkPass = false;
					JOptionPane.showMessageDialog(null,"�i�ϥΪ̩m�W�j�ťե���J��ơI","�ާ@ĵ�T",JOptionPane.ERROR_MESSAGE);
				}
	
				if(  phoneString.length() == 0 ){       //�ˬdphoneString�O�_����J����r��,length()���p��r����פ�k,�Y���׬�0�h����J���
					checkPass = false;
					JOptionPane.showMessageDialog(null,"�i�ϥΪ̹q�ܡj�ťե���J��ơI","�ާ@ĵ�T",JOptionPane.ERROR_MESSAGE);
				}
				
				if (db.CheckNamePhone(nameString, phoneString) == 1){
					checkPass = false;
                    JOptionPane.showMessageDialog(null,"�w�s�b���m�W�q�ܡI");
				}
	
				//�p�G�W�z�T���ˬd���S�o�{���~,�hcheckPass�|����true,�Y�q�L�ˬd,�]���N���u��¾��A�m�W�A�b���A�K�X�]�w��myRegister���󤤹����ݩʽ�  
				if( checkPass == true ){
					
					RD.setName(nameString);
					RD.setPhone(phoneString);
					RD.setGender(genderString);
					RD.setBirthyear(birthyearString);
					RD.setCity(cityString);

					myFrame.myRegister_pane.tf1.setText(""); 
					myFrame.myRegister_pane.tf2.setText(""); 
					
					db.RegisterInsertData(RD);//�N���U����ǤJ[��Ʈw�ާ@�s������(db)]���x�s�ϥΪ̬�����k(RegisterInsertData())�h�x�s�ϥΪ̬������Ʈw
				}
			}else{
				JOptionPane.showMessageDialog(null,"�ϥΪ̤w�F�W���]10�^�I","�ާ@ĵ�T",JOptionPane.ERROR_MESSAGE);
				myFrame.myRegister_pane.clearPane();
			}  
		}
    };
	
	//�����ť���{���G�B�z�n�J���s
	public KeyListener LoginSelection = new KeyAdapter(){
		@Override
		public void keyTyped(KeyEvent e){
			if( e.getKeyChar() == KeyEvent.VK_ENTER ){
				String accountString = myFrame.myLogin_pane.tf1.getText().trim();  			//���o[�n�J���]����[�b���r��]
				String passwordString = myFrame.myLogin_pane.tf2.getText().trim(); 			//���o[�n�J���]����[�K�X�r��]
				if(  (accountString.length() > 0) && (passwordString.length() > 0) ){    	//�p�G[�m�W�r��]���פj��0,�Y����J�m�W���,�~�i�J�d�߳B�z
					String[] loginResult = db.LoginCheck(accountString);   					//�I�s[��Ʈw�ާ@�s������(myDBMA)]���d�߾ǥͬ�����k(findRD_in_TB_sttaff())�h�d�߭��u����,�æ^���x�s��loginResult��
					if( ( accountString.equals(loginResult[0]) ) && ( passwordString.equals(loginResult[1]) ) ){
						myFrame.myLogin_pane.b0.setText("�n�J���\");							
						myFrame.myLogin_pane.b0.setForeground(Color.black);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
						myFrame.setVisible(false);		//���õn�J�e��
						Cfunc1 mainFrame = new Cfunc1();		
						mainFrame.setUser(loginResult[0]);
						dispose();
					}else{
						myFrame.myLogin_pane.b0.setText("�b�K���~");
						myFrame.myLogin_pane.b0.setForeground(Color.red);
						myFrame.myLogin_pane.b0.setHorizontalAlignment(JLabel.RIGHT);
					}

				} else {
                JOptionPane.showMessageDialog(null,"�������J�n�J��ơA�ж�g��A�n�J�I","�ާ@ĵ�T",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};		//�`�N�G�ƥ��ť���{��������";"	
}
