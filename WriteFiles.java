import java.io.*;

//²�檺�g�J���d��
public class WriteFiles {
    CAL cal = new CAL();

    public void main(String data,String avg, String FRSUM, String YY, String LR, int count ,String Distance) {
        try {
            String FileName = "output";    //�b�o�̭ק��ɮצW��
            FileWriter fWrite = new FileWriter("./" + FileName + ".txt",true);  //�b�o�̭ק��}
            BufferedWriter fOut = new BufferedWriter(fWrite);

            fOut.write("��" + count + "�����");
            fOut.newLine();
            fOut.write(avg);
            fOut.write(" ");
            fOut.write(FRSUM);
            fOut.write(" ");
            fOut.write(YY);
            fOut.write(" ");
            fOut.write(LR);
            fOut.write(" ");
            fOut.write(Distance);
            fOut.newLine();
            fOut.flush();

            fOut.close();
            System.out.println("�ɮ׼g�J���\!");
        } catch (Exception e) {
            System.out.println("�ɮ׳B�z���~!");
        }

    }

}
