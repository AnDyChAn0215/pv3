//�פJ�ݭn���U���M��
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.border.*;


//���D���h���O
//RegisterData: Class ProblemDomain_register (���U���O)

class RegisterData{                    
	
    private String name;   	    //�ݩ�:�m�W�r��
    private String phone;    	//�ݩ�:�q�ܦr��
    private String city;   		//�ݩ�:�����r��
	private String birthyear;   //�ݩ�:�X�ͦ~�]�褸�^�r��
	private String gender;   	//�ݩ�:�ʧO�r��
    
    //�غc�l:���OCPD_register
    public RegisterData(){
		
		name = "";
        phone = "";
        city = "";
        birthyear = "";
		gender = "";
		
    }

    //��k:�]�w�m�W
    public void setName(String Name){
        name = Name;
    }
	//��k:�]�w�q��
    public void setPhone(String Phone){
        phone = Phone;
    }
	//��k:�]�w����
    public void setCity(String City){
        city = City;
    }
	//��k:�]�w�X�ͦ~�]�褸�^
    public void setBirthyear(String Birthyear){
        birthyear = Birthyear;
    }
	//��k:�]�w�ʧO
    public void setGender(String Gender){
        gender = Gender;
    }
	
	//��k:���o�m�W
    public String getName(){
        return( name );
    }
    //��k:���o�q��
    public String getPhone(){
        return( phone );
    }
    //��k:���o����
    public String getCity(){
        return( city );
    }
    //��k:���o�X�ͦ~�]�褸�^
    public String getBirthyear(){
        return( birthyear );
    }	
	//��k:���o�ʧO
	public String getGender(){
        return( gender );
    }

 } //end for: class RegisterData

