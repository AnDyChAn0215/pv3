import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Enumeration;
import gnu.io.*;

class CgetSerialCom_v1
{
	//@SuppressWarnings("unchecked")
	static Enumeration portList;
	static CommPortIdentifier portId;
	String[] portMenu = new String[20];
	int portCount = 0, c;
	String[] portResult;

	CgetSerialCom_v1(){

		try{
			portList = CommPortIdentifier.getPortIdentifiers();  //此行會印出stable library...
			portCount = 0;
			while (portList.hasMoreElements()){
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL){

					if (portId.getName().indexOf("COM") != -1 ){
						portMenu[portCount] = portId.getName();
						portCount++;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		//for test: print the elements of portMenu
		portResult = new String[portCount];
		for(int i=0; i<portCount; i++){
			portResult[i] = portMenu[i];
		}


	}

	String[] getComPortList(){
		return( portResult );
	}
}