

import java.util.Random;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.TooManyListenersException;



public class SerialPortListener_v1 implements Runnable, SerialPortEventListener {

	private String appName = "��C��";
	private int timeout = 2000;          //�}�Ҧ�C�𪺵��ݮɶ�
	private int threadTime = 0;

	private CommPortIdentifier commPort;
	private SerialPort serialPort;
	private InputStream inputStream;
	private OutputStream outputStream;

	int[] myMdata = {0};  //�Q�ΰ}�C�ǻ����q�ƾ�
	String readStr = "";

	SerialPortListener_v1(int[] mdata){
		myMdata = mdata;
	}

	//�C�X�Ҧ��i�Ϊ���C��
	@SuppressWarnings("rawtypes")
	public void listPort(){
		CommPortIdentifier cpid;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		System.out.println("�C�X������C��G");
		while(en.hasMoreElements()){
			cpid = (CommPortIdentifier)en.nextElement();
			//�ˬd�O�_����C��
			if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL){
				System.out.println(cpid.getName());
			}
		}
	}

	public void openPort(String portName){
		this.commPort = null;
		CommPortIdentifier cpid;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();

		while(en.hasMoreElements()){
			cpid = (CommPortIdentifier)en.nextElement();
			if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL && cpid.getName().equals(portName)){
				this.commPort = cpid;
				break;
			}
		}

		if(commPort == null)
			System.out.println("�L�k��W�r��"+portName+"���q�T��I");
		else{
			try{
				serialPort = (SerialPort)commPort.open(appName, timeout);
			}catch(PortInUseException e){
				throw new RuntimeException(String.format("�ݤf'%1$s'���b�ϥΤ��I",commPort.getName()));
			}
		}
	}

	public void checkPort(){
		if(commPort == null){
			throw new RuntimeException("commPort null");
		}
		if(serialPort == null){
			throw new RuntimeException("SerialPort null");
		}
	}

	public void write(String message) throws InterruptedException {
		checkPort();
		try{
			outputStream = new BufferedOutputStream(serialPort.getOutputStream());
			outputStream.write(message.getBytes());
			outputStream.close();
		}catch(IOException e){
			throw new RuntimeException("�V��C��ǰe��ƥ��ѡG"+e.getMessage());
		}
	}

	public void startRead(int time){
		checkPort();
		try{
			inputStream = new BufferedInputStream(serialPort.getInputStream());
		}catch(IOException e){
			throw new RuntimeException("�L�k�q��C��InputStreamŪ�X��]�G"+e.getMessage());
		}

		try{
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			//System.out.println("checkpoint1:  ���\�P�B�s��Arduino");
		}catch(TooManyListenersException e){
			throw new RuntimeException(e.getMessage());
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}

		if(time > 0){
			this.threadTime = time*1000;
			Thread t = new Thread(this);
			t.start();
		}
	}

	public void close(){
		serialPort.close();
		serialPort = null;
		commPort = null;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0){

		switch(arg0.getEventType()){
			case SerialPortEvent.BI:/*Break interrupt*/
			case SerialPortEvent.OE:/*Overrun error*/
			case SerialPortEvent.FE:/*Framing error*/
			case SerialPortEvent.PE:/*Parity error*/
			case SerialPortEvent.CD:/*Carrier detect*/
			case SerialPortEvent.CTS:/*Clear to send*/
			case SerialPortEvent.DSR:/*Data set ready*/
			case SerialPortEvent.RI:/*Ring indicator*/
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty*/
				break;
			case SerialPortEvent.DATA_AVAILABLE: /*Data available at the serial port*/

				byte[] readBuffer = new byte[1024];
				int count = 0;

				try {

					while (inputStream.available() > 0 ) {
						inputStream.read(readBuffer);
						readStr += new String(readBuffer).trim();
						//System.out.println("----------USB-readBuffer��l�ƾ�:"+readStr);  //���Arduino�ƭȥΡA�T�{�L�~��i���ѱ�
					}
					int begin = readStr.lastIndexOf("b");
					int end = readStr.lastIndexOf("e");
					if(  begin > -1 &&  end > -1 && end > begin){
						String dataStr = readStr.substring(begin+1,end);
						myMdata[0] = Integer.parseInt(dataStr);
						//System.out.println("-----���T���ƾ�:"+myMdata[0]);  //�ΨӤ��n��PArduino�ƭȥΡA�T�{�L�~��i���ѱ�
						readStr="";
					}
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
		}
	}


	@Override
	public void run() {
		try{
			Thread.sleep(threadTime);
			serialPort.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
