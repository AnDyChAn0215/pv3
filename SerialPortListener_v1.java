

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

	private String appName = "串列埠";
	private int timeout = 2000;          //開啟串列埠的等待時間
	private int threadTime = 0;

	private CommPortIdentifier commPort;
	private SerialPort serialPort;
	private InputStream inputStream;
	private OutputStream outputStream;

	int[] myMdata = {0};  //利用陣列傳遞測量數據
	String readStr = "";

	SerialPortListener_v1(int[] mdata){
		myMdata = mdata;
	}

	//列出所有可用的串列埠
	@SuppressWarnings("rawtypes")
	public void listPort(){
		CommPortIdentifier cpid;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		System.out.println("列出全部串列埠：");
		while(en.hasMoreElements()){
			cpid = (CommPortIdentifier)en.nextElement();
			//檢查是否為串列埠
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
			System.out.println("無法找名字為"+portName+"的通訊埠！");
		else{
			try{
				serialPort = (SerialPort)commPort.open(appName, timeout);
			}catch(PortInUseException e){
				throw new RuntimeException(String.format("端口'%1$s'正在使用中！",commPort.getName()));
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
			throw new RuntimeException("向串列埠傳送資料失敗："+e.getMessage());
		}
	}

	public void startRead(int time){
		checkPort();
		try{
			inputStream = new BufferedInputStream(serialPort.getInputStream());
		}catch(IOException e){
			throw new RuntimeException("無法從串列埠的InputStream讀出原因："+e.getMessage());
		}

		try{
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			//System.out.println("checkpoint1:  成功同步連接Arduino");
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
						//System.out.println("----------USB-readBuffer原始數據:"+readStr);  //比對Arduino數值用，確認無誤後可註解掉
					}
					int begin = readStr.lastIndexOf("b");
					int end = readStr.lastIndexOf("e");
					if(  begin > -1 &&  end > -1 && end > begin){
						String dataStr = readStr.substring(begin+1,end);
						myMdata[0] = Integer.parseInt(dataStr);
						//System.out.println("-----正確抓到數據:"+myMdata[0]);  //用來比對軟體與Arduino數值用，確認無誤後可註解掉
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
