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
        setTitle("註冊使用者");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 0, 40));
        getContentPane().setBackground(new Color(252, 252, 252));

        Font labelFont = new Font("微軟正黑體", Font.PLAIN, 14);
        Font buttonFont = new Font("微軟正黑體", Font.BOLD, 14);

        userLabel = new JLabel("姓名:");
        userLabel.setFont(labelFont);
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setFont(labelFont);
        add(userTextField);

        phoneLabel = new JLabel("電話號碼:");
        phoneLabel.setFont(labelFont);
        add(phoneLabel);

        phoneTextField = new JTextField();
        phoneTextField.setFont(labelFont);
        add(phoneTextField);

        genderLabel = new JLabel("性別:");
        genderLabel.setFont(labelFont);
        add(genderLabel);

        genderBox = new JComboBox<>();
        genderBox.addItem("請選擇");
        genderBox.addItem("男");
        genderBox.addItem("女");
        add(genderBox);

        yearJLabel = new JLabel("出生年(西元):");
        yearJLabel.setFont(labelFont);
        add(yearJLabel);

        yearTextField = new JTextField();
        yearTextField.setFont(labelFont);
        add(yearTextField);

        cityLable = new JLabel("城市:");
        cityLable.setFont(labelFont);
        add(cityLable);

        cityTextField = new JTextField();
        cityTextField.setFont(labelFont);
        add(cityTextField);

        registerButton = new JButton("註冊");
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

            if (name.equals("") || phone.equals("") || gender.equals("請選擇") || birthyear.equals("") || city.equals("")) {
                statusLabel.setForeground(Color.red);
                statusLabel.setText("所有欄位都必須填寫！");
            }
            else {
                System.out.println(name);
                System.out.println(phone);
                System.out.println(gender);
                System.out.println(birthyear);
                System.out.println(city);

                if (db.CheckNamePhone(name,phone) == 1){
                    JOptionPane.showMessageDialog(null,"已存在的姓名電話!");
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
