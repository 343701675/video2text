import utils.AsciiPic;
import utils.Constants;
import utils.ImageProcesserUtils;

import java.io.*;

public class Video2Text {
    private final static String app_dir=System.getProperty("user.dir");

    /**
     * 视频转图片
     * @param VideoName
     * @param reso
     * @param frame
     */
    public static boolean video2Pic(String VideoName, String reso, String frame) {
        //建立外部调用线程
        StringBuilder cmd = new StringBuilder();
        cmd.append(app_dir+"\\"+"ffmpeg.exe");
        cmd.append(" -i "+VideoName);
        cmd.append(" -r "+frame);
        cmd.append(" -f image2");
        cmd.append(" -s " + reso);
        cmd.append(" img/%d.jpg");
        boolean result = true;
        try{
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            System.out.println("cmd="+cmd.toString());
            //开启其他线程防止阻塞
            PrintStream error = new PrintStream(proc.getErrorStream());
            PrintStream output = new PrintStream(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor(); //阻塞等待进程结束
        }catch(Exception e){
            e.printStackTrace();
            result=false;
        }
        return result;
    }

    /**
     * 图片转text字符画
     * @return
     */
    public static Boolean pic2Text() {
        boolean result=true;
        File file = new File(app_dir+"\\"+Constants.img_dir);
        if(file.isDirectory() && file.exists()){
            File[] files = file.listFiles();
            int length=files.length;
            if(files!=null && length>0){
                for (int i=0;i<files.length;i++){
                    try {
                        System.out.println(String.format("Converting the image to ASCII code...%s/%s ", (i+1), length));
                        File file1 = new File(app_dir+"\\"+Constants.img_dir+"\\"+(i+1)+".jpg");
                        if(!file1.isFile()){
                            System.out.println("this file is not a file..."+(i+1));
                            continue;
                        }
                        AsciiPic asciiPic = new AsciiPic();
                        asciiPic.createAsciiPic(file1);
                        asciiPic.saveAsTxt(app_dir+"\\"+Constants.txt_dir+"\\"+(i+1)+".txt");
                        System.out.println(file1.getName()+" is converted!");
                    } catch(Exception e) {
                        e.printStackTrace();
                        result=false;
                    }
                }
            }

        }
        return result;
    }
}

class PrintStream extends  Thread{
    private InputStream is;

    public PrintStream(InputStream is){
        this.is = is;
    }

    public void run(){
        try{
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line.toString()); //输出内容
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
