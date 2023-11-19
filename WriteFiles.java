import java.io.*;

//簡單的寫入文件範例
public class WriteFiles {
    CAL cal = new CAL();

    public void main(String data,String avg, String FRSUM, String YY, String LR, int count ,String Distance) {
        try {
            String FileName = "01";    //在這裡修改檔案名稱
            FileWriter fWrite = new FileWriter("C:\\Workspace\\TestFile\\" + FileName + ".txt", true);  //在這裡修改位址
            BufferedWriter fOut = new BufferedWriter(fWrite);

            fOut.write("第" + count + "筆資料");
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
            System.out.println("檔案寫入成功!");
        } catch (Exception e) {
            System.out.println("檔案處理有誤!");
        }

    }

}
