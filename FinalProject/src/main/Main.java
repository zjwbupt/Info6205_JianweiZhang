
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main{
    static private String filePath;
    static private int initSetsNum;

    //6. Login Function to keep track of the progress
	public static void main(String[] args) {
		String filePath = "src/Maps.txt";
		//Initial Number of Population
		int initSetsNum = 1000;

		GA tool = new GA(filePath, initSetsNum);
		tool.goOutMaze();


		ArrayList ave = tool.getAverageValue();
        ArrayList top = tool.getTopValue();
        try {
            FileOutputStream fis = new FileOutputStream("topAndAverage.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            bw.write("Top,Average\n");
            bw.flush();
            for(int i=0;i<ave.size();i++){
                double aveNum = (double)ave.get(i);
                double topNum = (double)top.get(i);
                String content = topNum + "," +aveNum+ "\n";
                bw.write(content);
                bw.flush();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public String getFilePath(){
        return filePath;
    }

    public int getInitSetsNum(){
        return initSetsNum;
    }

}