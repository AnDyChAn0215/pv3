//�פJ�ݭn���U���M��
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

class CHCI_logo extends JPanel{    

	//�إ߹ϥܪ���
	ImageIcon icon1 = new ImageIcon("images/logo.png");   

	JLabel b1 = new JLabel(icon1);	
	
	Color color1 = new Color(0,47,73);		//�Ӯa��
	Color color2 = new Color(255,255,255);
		
	//���o�ù��e�]w�^�P���]h�^
	Toolkit kt = Toolkit.getDefaultToolkit();
	Dimension dm = kt.getScreenSize();
	int w = (int)dm.getWidth();
	int h = (int)dm.getHeight(); 
   
    //�غc�l:���OCHCI_logo
    public CHCI_logo(){
		
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//Logo
		b1.setLayout(null);
		b1.setBounds(0,0,(int)(0.25*w),(int)(0.5*h));
		add(b1);

		setLayout(null);		
		setBounds(0,0,(int)(0.25*w),(int)(0.5*h));
		setBackground(color1);

    }

} //end for: class CHCI_logo
 
