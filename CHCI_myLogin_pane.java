//�פJ�ݭn���U���M��
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;
import javax.swing.UIManager;

//�H�����ʼh���O
class CHCI_myLogin_pane extends JPanel{
	 
	JPanel p1 = new JPanel(); 	//�n�J���D��
	JPanel p2 = new JPanel();	//��r�ذ�
	JPanel p3 = new JPanel();   //���s��
	
	//�إ߹ϥܪ���
	ImageIcon icon1 = new ImageIcon("images/name.png");				//�m�W
	ImageIcon icon2 = new ImageIcon("images/phone.png");		//�q�ܸ��X

	Font font1 = new Font("�L�n������", Font.BOLD, 36); //�إߦr������font1���L�n������B�ʱ���B�r��j�p��36�I���r
	Font font2 = new Font("�L�n������", Font.BOLD, 20); 
	Font font3 = new Font("�L�n������", Font.BOLD, 16);

	JLabel b0 = new JLabel("");			//���ܤp�r
	JLabel b1 = new JLabel("�n�J");
	JLabel b2 = new JLabel(icon1);		//�b��
	JLabel b3 = new JLabel(icon2);		//�K�X
	
	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
                  
	JButton btn0 = new JButton("�X��");			  
	JButton btn1 = new JButton("�n�J");
	JButton btn2 = new JButton("�M��");
	JButton btn3 = new JButton("���U");
	

	Color color1 = new Color(0,47,73);		//�Ӯa��
	Color color2 = new Color(255,255,255);
		
	//���o�ù��e�]w�^�P���]h�^
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight(); 
	
	
    //�غc�l:���OCHCI_myLogin_pane
    public CHCI_myLogin_pane(){
		
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
		 
		//�n�J���D��
		p1.setLayout(null);		
		p1.setBounds(0, 0,(int)(0.25*w),(int)(0.08*h));
		p1.setBackground(color2);
		add(p1);
		
		//��r��
		p2.setLayout(null);		
		p2.setBounds(0, (int)(0.05*h),(int)(0.25*w),(int)(0.225*h));
		p2.setBackground(color2);
		add(p2);
		
		//���s��
		p3.setLayout(null);		
		p3.setBounds(0, (int)(0.275*h),(int)(0.25*w),(int)(0.2*h));
		p3.setBackground(color2);
		add(p3);
		
		//�n�J���D��		
		//�n�J
		b1.setLayout(null);
		b1.setBounds(0, 0, (int)(0.25*w), (int)(0.08*h));
		b1.setHorizontalAlignment(JTextField.CENTER);
		b1.setFont(font1);
		p1.add(b1);	 
		
		//��r�ذ�
		//�m�W
		b2.setLayout(null);
		b2.setBounds(0, (int)(0.051*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b2);
		
		//�q�ܸ��X
		b3.setLayout(null);
		b3.setBounds(0, (int)(0.151*h), (int)(0.0625*w), (int)(0.05*h));
		p2.add(b3);
		
		//�]�w��r���
		tf1.setLayout(null);
		tf1.setBounds((int)(0.0625*w), (int)(0.051*h), (int)(0.15625*w),(int)(0.05*h));
		tf1.setFont(font2);
		tf1.setBackground(Color.white);
		p2.add(tf1);
		
		//�]�w�q�ܸ��X���
		tf2.setLayout(null);
		tf2.setBounds((int)(0.0625*w), (int)(0.151*h), (int)(0.15625*w),(int)(0.05*h));
		tf2.setFont(font2);
		tf2.setBackground(Color.white);
		p2.add(tf2);
		
		//��ܵn�J���G��
		b0.setLayout(null);
		b0.setBounds((int)(0.0625*w), (int)(0.2015*h), (int)(0.15625*w),(int)(0.025*h));
		b0.setFont(font3);
		p2.add(b0);
		
		//���s��		
		//�]�w���s�]�X�ȡ^
		btn0.setBounds((int)(0.02*w), (int)(0.1*h), (int)(0.2*w), (int)(0.05*h));
		btn0.setFont(font2);
		btn0.setForeground(Color.white);
		btn0.setBackground(color1);
		btn0.setFocusPainted(false);
		p3.add(btn0);	
		
		//�]�w���s�]�n�J�^
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
				
		//�]�w���s�]���U�^
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

    //��k:�M�Ůe�������
    public void clearPane(){

		tf1.setText("");
		tf2.setText("");
		b0.setText("");

    }

    //�ƥ��ť�{��: �B�z�I��[�M��]���s
    public ActionListener ProcessClearFields = new ActionListener(){
        public void actionPerformed(ActionEvent e){
     
            clearPane();
 
        }    
    };
     
 } //end for: class CHCI_myLogin_pane
