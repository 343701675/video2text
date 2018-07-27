import utils.AsciiPicUtils;
import utils.Constants;

import java.io.*;

public class Text2Img2Video {
    private final static String app_dir=System.getProperty("user.dir");

    /**
     * text字符画转图片
     * @return
     */
    public static Boolean text2Pic(){
        boolean result=true;
        File file = new File(app_dir+"\\"+Constants.txt_dir);
        if(file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for(int i=0;i<files.length;i++){
                    try {
                        File file1 = new File(app_dir+"\\"+Constants.txt_dir+"\\"+(i+1)+".txt");
                        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file1));
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while((line=bufferedReader.readLine())!=null){
                            sb.append(line);
                        }
                        saveToImgFile(sb.toString());
                    } catch (Exception e) {
                        result = false;
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * text字符画转图片
     * @param sb
     */
    private static void saveToImgFile(String sb) {
        if (sb == null || sb.length() == 0) {
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(app_dir+"\\"+Constants.new_img_dir));
            byte[] bytes = sb.getBytes();
            for (int i = 0; i < bytes.length; i += 2) {
                out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * text字符画转图片
     * @param ch
     * @return
     */
    private static int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }


    /**
     * 图片转视频
     * @param reso 宽高
     * @param frame 帧率
     */
    public static boolean pic2Video(String reso, String frame) {
        //建立外部调用线程
        StringBuilder cmd = new StringBuilder();
        cmd.append(app_dir+"\\"+"ffmpeg.exe");
        cmd.append(" -f image2");
        cmd.append(" -i "+Constants.new_img_dir+"/%d.jpg");
        cmd.append(" -vcodec libx264");
        cmd.append(" -r "+frame);
        cmd.append(" "+Constants.video_name);
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
            result=false;
            e.printStackTrace();
        }
        return result;
    }
}

