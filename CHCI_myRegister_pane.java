//�פJ�ݭn���U���M��
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

	JPanel p1 = new JPanel(); 	//���U���D��
	JPanel p2 = new JPanel();	//��r�ذ�
	JPanel p3 = new JPanel();   //���s��
	
	//�إ߹ϥܪ���
	ImageIcon icon1 = new ImageIcon("images/name.png");		//�m�W
	ImageIcon icon2 = new ImageIcon("images/phone.png");	//�q�ܸ��X
	ImageIcon icon3 = new ImageIcon("images/city.png");		//����
	ImageIcon icon4 = new ImageIcon("images/birthyear.png");//�X�ͦ~�]�褸�^
	ImageIcon icon5 = new ImageIcon("images/gender.png");	//�ʧO
	

	Font font1 = new Font("�L�n������", Font.BOLD, 36); //�إߦr������font1���L�n������B�ʱ���B�r��j�p��18�I���r
	Font font2 = new Font("�L�n������", Font.BOLD, 20); 
	Font font3 = new Font("�L�n������", Font.BOLD, 16);

	JLabel b1 = new JLabel("���U");
	JLabel b2 = new JLabel(icon1);	//�m�W
	JLabel b3 = new JLabel(icon2);	//�q�ܸ��X
	JLabel b4 = new JLabel(icon3);	//����
	JLabel b5 = new JLabel(icon4);	//�X�ͦ~�]�褸�^
	JLabel b6 = new JLabel(icon5);	//�ʧO
	
	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	
	JButton btn1 = new JButton("���U");
	JButton btn2 = new JButton("�M��");
	JButton btn3 = new JButton("��^");

	String[] items1 = {"�O�_��", "�s�_��", "�򶩥�", "��饫", "�s�˥�", "�]�߿�", "�O����", "���ƿ�", "�n�뿤", "���L��", "�Ÿq��", "�O�n��", "������", "�̪F��", "�y����", "�Ὤ��", "�O�F��", "���", "������", "�s����"};		//�U�Կ�檺���e
	JComboBox cbox1 = new JComboBox (items1);		//�إߤU�Կ��

	JComboBox cbox2 = new JComboBox ();				//�إߤU�Կ��	
	
	String[] items3 = {"�k", "�k"};					//�U�Կ�檺���e
	JComboBox cbox3 = new JComboBox (items3);		//�إߤU�Կ��
	
	Color color1 = new Color(0,47,73);		//�Ӯa��
	Color color2 = new Color(255,255,255);
		
	//���o�ù��e�]w�^�P���]h�^
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight();               
    
     //�غc�l:���OCHCI_myRegister_pane
    public CHCI_myRegister_pane(){
		 
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//���U���D��
		p1.setLayout(null);		
		p1.setBounds(0, 0,(int)(0.25*w),(int)(0.08*h));
		p1.setBackground(color2);
		add(p1);
		
		//��r��
		p2.setLayout(null);		
		p2.setBounds(0, (int)(0.05*h),(int)(0.25*w),(int)(0.3*h));
		p2.setBackground(color2);
		add(p2);
		
		//���s��
		p3.setLayout(null);		
		p3.setBounds(0, (int)(0.35*h),(int)(0.25*w),(int)(0.15*h));
		p3.setBackground(color2);
		add(p3);
		
		//���U���D��
		//���U���D
		b1.setLayout(null);
		b1.setBounds(0, 0, (int)(0.25*w), (int)(0.08*h));
		b1.setHorizontalAlignment(JTextField.CENTER);
		b1.setFont(font1);
		p1.add(b1);
		
		//��r�ذ�		
		//�m�W
		b2.setLayout(null);
		b2.setBounds(0, (int)(0.03*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b2);

		//�q�ܸ��X
		b3.setLayout(null);
		b3.setBounds(0, (int)(0.103*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b3);
		
		//����
		b4.setLayout(null);
		b4.setBounds(0, (int)(0.176*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b4);
				
		//�X�ͦ~�]�褸�^�G
		b5.setLayout(null);
		b5.setBounds(0, (int)(0.249*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b5);
		
		//�ʧO
		b6.setLayout(null);
		b6.setBounds((int)(0.109375*w), (int)(0.249*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b6);
		
		//�]�w��r���	
		//�m�W
		tf1.setLayout(null);
		tf1.setBounds((int)(0.0625*w), (int)(0.03*h), (int)(0.15625*w), (int)(0.05*h));
		tf1.setFont(font2);
		tf1.setBackground(Color.white);
		p2.add(tf1);
		
		//�q�ܸ��X		
		tf2.setLayout(null);
		tf2.setBounds((int)(0.0625*w), (int)(0.103*h), (int)(0.15625*w),(int)(0.05*h));
		tf2.setFont(font2);
		tf2.setBackground(Color.white);
		p2.add(tf2);

		//����
		cbox1.setBounds((int)(0.0625*w), (int)(0.176*h), (int)(0.15625*w),(int)(0.05*h));
		cbox1.setFont(font2);		
		p2.add(cbox1);	

		//�X�ͦ~�]�褸�^
		for (int i = STARTYEAR; i <= ENDYEAR; i++) {
			cbox2.addItem(i);
		}
		cbox2.setBounds((int)(0.0625*w), (int)(0.249*h), (int)(0.046875*w),(int)(0.05*h));
		cbox2.setFont(font2);		
		p2.add(cbox2);		
		
		//�ʧO
		cbox3.setBounds((int)(0.171875*w), (int)(0.249*h), (int)(0.046875*w),(int)(0.05*h));
		cbox3.setFont(font2);		
		p2.add(cbox3);	
		
		//���s��
		//�]�w���s�]���U�^		
		btn1.setBounds((int)(0.02*w), (int)(0.025*h), (int)(0.05*w), (int)(0.05*h));
		btn1.setFont(font2);
		btn1.setForeground(Color.white);
		btn1.setBackground(color1);
		btn1.setFocusPainted(false);
		p3.add(btn1);
		
		//�]�w���s�]�M�š^
		btn2.setBounds((int)(0.095*w), (int)(0.025*h), (int)(0.05*w), (int)(0.05*h));
		btn2.setFont(font2);
		btn2.setForeground(Color.white);
		btn2.setBackground(color1);
		btn2.addActionListener(ProcessClearFields);   //[�s�W�ǥ͸��]�ާ@�e����[�M��]���s�[��[�ƥ��ť�{��]
		btn2.setFocusPainted(false);
		p3.add(btn2);
		
		//�]�w���s�]��^�^
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

     //��k:�M�Ůe�������
    public void clearPane(){

		tf1.setText("");
		tf2.setText("");

    }

     //�ƥ��ť�{��: �B�z�I��[�M��]���s
    public ActionListener ProcessClearFields = new ActionListener(){
        public void actionPerformed(ActionEvent e){
     
             clearPane();
 
        }    
    };

 } //end for: class CHCI_myRegister_pane

 