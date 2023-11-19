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
import javax.swing.plaf.FontUIResource; //�ק�JOptionPane�r��һݮM��
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;

class Clogin_frame extends JFrame{      //�t�Ϊ�����

	CHCI_logo myLogo = new CHCI_logo();                 					 //Logo(��JPanel�A���t1��Logo����)     
	CHCI_myLogin_pane myLogin_pane = new CHCI_myLogin_pane();       		 //�n�J����(��JPanel�A���t���ҡA��r���A���s��)
	CHCI_myRegister_pane myRegister_pane = new CHCI_myRegister_pane();   	 //���U����(��JPanel�A���t���ҡA��r���A���s��)  
	
	//���o�ù��e�]w�^�P���]h�^
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight(); 	 
   
   //�غc�l:���OClogin_frame
   public Clogin_frame(){
	   
	   	try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

		add(myLogo);        		//�N[Logo]�[�즹������
		add(myLogin_pane);     		//�N[�n�J����]�[�즹������
		add(myRegister_pane);   	//�N[���U����]�[�즹������

		myLogin_pane.setVisible(true);   	  //�w�][�n�J���]�e�����
		myRegister_pane.setVisible(false);    //�w�][���U���]�e������
		 		 
		//�t�ε������򥻳]�w 
		setLayout(null);
		setBounds((int)(0.25*w),(int)(0.25*h),(int)(0.5*w),(int)(0.5*h));
		//�����m��ù����B���e���@�b�j�p
		setIconImage(new ImageIcon("images/logo.png").getImage());
		setTitle("�}�ɵ��g�����q�t��");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

} //end for: class Clogin_frame

 