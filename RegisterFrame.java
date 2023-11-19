import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends JFrame implements ActionListener {

    private JLabel userLabel, genderLabel, cityLable, phoneLabel,yearJLabel,statusLabel;
    private JTextField userTextField, phoneTextField,yearTextField,cityTextField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JComboBox genderBox;


    DataBase db = new DataBase();
    RegisterData RD = new RegisterData();

    public RegisterFrame() {
        setTitle("���U�ϥΪ�");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 0, 40));
        getContentPane().setBackground(new Color(252, 252, 252));

        Font labelFont = new Font("�L�n������", Font.PLAIN, 14);
        Font buttonFont = new Font("�L�n������", Font.BOLD, 14);

        userLabel = new JLabel("�m�W:");
        userLabel.setFont(labelFont);
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setFont(labelFont);
        add(userTextField);

        phoneLabel = new JLabel("�q�ܸ��X:");
        phoneLabel.setFont(labelFont);
        add(phoneLabel);

        phoneTextField = new JTextField();
        phoneTextField.setFont(labelFont);
        add(phoneTextField);

        genderLabel = new JLabel("�ʧO:");
        genderLabel.setFont(labelFont);
        add(genderLabel);

        genderBox = new JComboBox<>();
        genderBox.addItem("�п��");
        genderBox.addItem("�k");
        genderBox.addItem("�k");
        add(genderBox);

        yearJLabel = new JLabel("�X�ͦ~(�褸):");
        yearJLabel.setFont(labelFont);
        add(yearJLabel);

        yearTextField = new JTextField();
        yearTextField.setFont(labelFont);
        add(yearTextField);

        cityLable = new JLabel("����:");
        cityLable.setFont(labelFont);
        add(cityLable);

        cityTextField = new JTextField();
        cityTextField.setFont(labelFont);
        add(cityTextField);

        registerButton = new JButton("���U");
        registerButton.setFont(buttonFont);
        registerButton.setBackground(new Color(0, 157, 204));
        registerButton.setForeground(Color.white);
        add(registerButton);
        registerButton.addActionListener(this);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(labelFont);
        add(statusLabel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = userTextField.getText();
            String phone = phoneTextField.getText();
            String gender = genderBox.getSelectedItem().toString();
            String birthyear = yearTextField.getText();
            String city = cityTextField.getText();

            if (name.equals("") || phone.equals("") || gender.equals("�п��") || birthyear.equals("") || city.equals("")) {
                statusLabel.setForeground(Color.red);
                statusLabel.setText("�Ҧ���쳣������g�I");
            }
            else {
                System.out.println(name);
                System.out.println(phone);
                System.out.println(gender);
                System.out.println(birthyear);
                System.out.println(city);

                if (db.CheckNamePhone(name,phone) == 1){
                    JOptionPane.showMessageDialog(null,"�w�s�b���m�W�q��!");
                }else {
                    RD.setName(name);
                    RD.setPhone(phone);
                    RD.setGender(gender);
                    RD.setBirthyear(birthyear);
                    RD.setCity(city);

                    db.RegisterInsertData(RD);
                }
                statusLabel.setForeground(new Color(0, 157, 204));
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        RegisterFrame registerFrame = new RegisterFrame();
        
    }
}
