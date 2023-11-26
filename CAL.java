
public class CAL {
    double[] j = new double[24]; // 用於儲存傳入的數字
    String StrData = "";
    String StrAvg;
    String StrFRSUM;
    String StrYY;
    String StrLR;
    String StrSTD;
    // 平均值
    double sum = 0;
    // 虛實值 = HSUM/FSUM
    double FRSUM = 0;
    double HSUM = 0;
    double FSUM = 0;
    // 陰陽值 = PSUM/NSUM
    double YY = 0;
    double PSUM = 0;
    double NSUM = 0;
    // 左右值 = LSUM/RSUM
    double LR = 0;
    double LSUM = 0;
    double RSUM = 0;
    //理想值
    double Standard = 0;
    //用於儲存乾淨的數值
    double CSUM = 0;
    double CFRSUM = 0;
    double CYY = 0;
    double CLR = 0;
    double CStandard = 0;


    public void Getnumber(double[] myArray) {
        System.out.println("接收到的數值:");
        for (int n = 0; n <= 23; n++) {
            j[n] = myArray[n];

            System.out.print(j[n] + " ");
        }
    }

    public void avg() {
        // 平均值
        for (int n = 0; n <= 23; n++) {
            sum = sum + j[n];
        }
        sum = sum / 24;
    }

    public void FR() {
        // 虛實值
        for (int n = 0; n <= 11; n++) {
            HSUM = HSUM + j[n];
        }
        for (int n = 12; n <= 23; n++) {
            FSUM = FSUM + j[n];
        }
        FRSUM = HSUM / FSUM;
    }

    public void YY() {
        // 陰陽值
        for (int n = 0; n <= 2; n++) {
            PSUM = PSUM + j[n];
        }
        for (int n = 6; n <= 8; n++) {
            PSUM = PSUM + j[n];
        }
        for (int n = 12; n <= 14; n++) {
            PSUM = PSUM + j[n];
        }
        for (int n = 18; n <= 20; n++) {
            PSUM = PSUM + j[n];
        }
        for (int n = 3; n <= 5; n++) {
            NSUM = NSUM + j[n];
        }
        for (int n = 9; n <= 11; n++) {
            NSUM = NSUM + j[n];
        }
        for (int n = 15; n <= 17; n++) {
            NSUM = NSUM + j[n];
        }
        for (int n = 21; n <= 23; n++) {
            NSUM = NSUM + j[n];
        }
        YY = PSUM / NSUM;
    }

    public void LR() {
        // 左右值
        for (int n = 0; n <= 5; n++) {
            LSUM = LSUM + j[n];
        }
        for (int n = 12; n <= 17; n++) {
            LSUM = LSUM + j[n];
        }
        for (int n = 6; n <= 11; n++) {
            RSUM = RSUM + j[n];
        }
        for (int n = 18; n <= 23; n++) {
            RSUM = RSUM + j[n];
        }
        LR = LSUM / RSUM;
    }

    //計算與理想值的距離
    public void CalDistance() {
        //x=虛實值 y=左右值 z=陰陽值
        double calFRSM, calYY, calLR;
        calFRSM = (FRSUM - 1);
        calFRSM = Math.pow(calFRSM, 2);
        calYY = (YY - 1);
        calYY = Math.pow(calYY, 2);
        calLR = (LR - 1);
        calLR = Math.pow(calLR, 2);
        Standard = Math.sqrt(calFRSM + calYY + calLR);
        //System.out.println("計算後距離"+Standard);
    }

    //變數重置
    public void ToDefault() {
        StrData = "";
        StrAvg = "";
        StrFRSUM = "";
        StrYY = "";
        StrLR = "";
        StrSTD = "";
        // 平均值
        sum = 0;
        // 虛實值 = HSUM/FSUM
        FRSUM = 0;
        HSUM = 0;
        FSUM = 0;
        // 陰陽值 = PSUM/NSUM
        YY = 0;
        PSUM = 0;
        NSUM = 0;
        // 左右值 = LSUM/RSUM
        LR = 0;
        LSUM = 0;
        RSUM = 0;
        //理想值
        Standard = 0;

        CSUM = 0;
        CFRSUM = 0;
        CYY = 0;
        CLR = 0;
        CStandard = 0;
    }

    //資料處理
    public void CheckTheData() {
        if (FRSUM > 0.25 && YY > 0.25 && LR > 0.25) {
            if (FRSUM < 3.5 && YY < 3.5 && LR < 3.5) {
                CSUM = sum;
                CFRSUM = FRSUM;
                CYY = YY;
                CLR = LR;
                CStandard = Standard;
                sum = 0;
                FRSUM = 0;
                YY = 0;
                LR = 0;
                Standard = 0;
            }
        }
    }

    //用來將Double的數值轉換成字串
    public void ToMyString() {
        StrAvg = Double.toString(CSUM);
        StrFRSUM = Double.toString(CFRSUM);
        StrYY = Double.toString(CYY);
        StrLR = Double.toString(CLR);
        StrSTD = Double.toString(CStandard);
        CSUM = 0;
        CFRSUM = 0;
        CYY = 0;
        CLR = 0;
        CStandard = 0;
    }

    public void showdetail() {
        System.out.println("平均值:" + StrAvg);
        System.out.println("虛實值:" + StrFRSUM);
        System.out.println("陰陽值:" + StrYY);
        System.out.println("左右值:" + StrLR);
        System.out.println("與理想值距離:" + StrSTD);
    }
}
