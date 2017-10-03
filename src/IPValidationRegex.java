import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * This Class validate the IP from a dedicated input file,
 * and output the valid IP to console and a dedicated file
 */

public class IPValidationRegex {

    private String regexString = "((25[0-5])|(2[0-4]\\d)|([0-1]\\d\\d)|([0-9]\\d)|\\d)"
            + "(\\.((25[0-5])|(2[0-4]\\d)|([0-1]\\d\\d)|([0-9]\\d)|\\d)){3}";

    private String fileName;
    private String outputFileName;


    /**
     * @param  fileName of the input file contains IP to be filtered
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     *
     * @param outputFileName name of the output file contains IP filtered
     */
    public void setOutputFileName(String outputFileName)
    {
        this.outputFileName = outputFileName;
    }

    /**
     *
     * @param ip IP string to be processed
     * @return true if valid, false if invalid
     */
    public boolean isIPValid(String ip)
    {
        return Pattern.matches(regexString, ip);
    }
    /*
    *   Function to do the IP Validation
    * */
    public int doIPValidation()
    {
        boolean isOverRide = false;
        int valid_no = 0;

        try{
            String filepath = IPValidationRegex.class.getResource("/").getFile() +fileName;
            String outputFilepath = IPValidationRegex.class.getResource("/").getFile() + outputFileName;

            if(filepath.equals(outputFilepath)){
                //use temp_output.txt as a temporary file
                outputFilepath = IPValidationRegex.class.getResource("/").getFile() + "files/temp_output.txt";
                isOverRide = true;
            }

            File file = new File(filepath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            File outfile = new File(outputFilepath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outfile));

            String str = null;

            while((str=bufferedReader.readLine())!=null){
                if(isIPValid(str)) {//check if ip valid
                    valid_no++;
                    System.out.println(str);
                    bufferedWriter.write(str);
                    bufferedWriter.newLine();//return
                }
            }
            bufferedReader.close();
            bufferedWriter.close();

            if(isOverRide == true){
                file = new File(outputFilepath);
                bufferedReader = new BufferedReader(new FileReader(file));

                outfile = new File(filepath);
                bufferedWriter = new BufferedWriter(new FileWriter(outfile));
                while((str=bufferedReader.readLine())!=null){
                    bufferedWriter.write(str);
                    bufferedWriter.newLine();//return
                }
                bufferedReader.close();
                bufferedWriter.close();
                file.delete();
            }

        } catch(FileNotFoundException e){
            System.out.println("File " + fileName + " not exist !");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return valid_no;
    }

    public static void main(String[] args) {
        IPValidationRegex ipvr = new IPValidationRegex();
        //no arguments specified
        if(args.length == 0){
        ipvr.setFileName("files/input_ip.txt");
        }else{
        ipvr.setFileName("files/"+args[0]);
        }
        //only one arguments specified
        if(args.length <2) {
            ipvr.setOutputFileName("files/output_ip.txt");
        }else{
            ipvr.setOutputFileName("files/"+args[1]);
        }

        //Calling the validation function
        ipvr.doIPValidation();

    }//end of main
}
