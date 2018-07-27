import utils.Constants;

import java.io.File;
import java.util.Scanner;

public class MainMethod {
    public static void main(String[] args) {
        System.out.println("Input name of video/gif,use \"reload\" to play the already converted file");
        Scanner scanner = new Scanner(System.in);
        String vname = scanner.nextLine();
//        if(vname.equals("reload")){
//            if(!Reload.play()){
//                System.out.println("play Error");
//                return;
//            }
//        }

        //执行转换
        System.out.println("Input Resolution of video/gif,  dafalut 960x540");
        String reso = scanner.nextLine();
        if(reso==null || reso.length()==0){
            reso = "960x540";
        }
        System.out.println("Input frame rate, default 25");
        String frame = scanner.nextLine();
        if(frame.length()==0){
            frame = "25";
        }

        //文件夹相关
        File file = new File(System.getProperty("user.dir")+"\\"+Constants.img_dir);
        if(file.exists()){
            file.delete();
            file.mkdirs();
        }
        else{
            file.mkdirs();
        }

        File file1 = new File(System.getProperty("user.dir")+"\\"+Constants.txt_dir);
        if(file1.exists()){
            file1.delete();
            file1.mkdirs();
        }
        else{
            file1.mkdirs();
        }

        File file2 = new File(System.getProperty("user.dir")+"\\"+Constants.new_img_dir);
        if(file1.exists()){
            file1.delete();
            file1.mkdirs();
        }
        else{
            file1.mkdirs();
        }

        System.out.println("Converting video to image...");
        if(!Video2Img2Text.video2Pic(vname,reso,frame)){
            System.out.println("video2Pic Error");
            return;
        }

        System.out.println("Converting pic to text...");
        if (!Video2Img2Text.pic2Text()) {
            System.out.println("pic2Text Error");
            return;
        }

        System.out.println("Converting text to image...");
        if (!Text2Img2Video.text2Pic()) {
            System.out.println("text2Pic Error");
            return;
        }

        System.out.println("Converting image to video...");
        if (!Text2Img2Video.pic2Video(reso,frame)) {
            System.out.println("pic2Video Error");
            return;
        }

        // 暂停3秒提示
        try {
            for (int i = 3; i > 0; i--) {
                System.out.println(String.format("Conversion over, play in %s seconds",i));
                Thread.sleep(1000);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

//        //play起来
//        if(!Reload.play()){
//            System.out.println("play Error");
//            return;
//        }
    }
}
