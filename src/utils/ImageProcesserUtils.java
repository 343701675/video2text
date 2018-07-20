package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageProcesserUtils {

    private static final char[] charset1 =  {'M','8','V','|',':','.',' '};   //默认字符素材集
    private char[] charset;   //字符画素材集
    private String imgString = "";   //储存转化后的字符串


    //使用指定字符集构造
    public ImageProcesserUtils(char[] charset){
        this.charset = charset;
    }
    //使用默认字符集构造
    public ImageProcesserUtils(){
        this.charset = charset1;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    /*将图形文件转化为字符画字符串*/
    public ImageProcesserUtils toBitmapConvert(File imageFile){
        StringBuffer sb = new StringBuffer();
        if(!imageFile.exists()){    //当读取的文件不存在时，结束程序
            System.out.println("File is not exists!");
            System.exit(1);
        }
        Color color;
        try{
            BufferedImage buff = ImageIO.read(imageFile);   //将图片文件装载如BufferedImage流
            buff = compressImage(buff);
            int bitmapH = buff.getHeight();
            int bitmapW = buff.getWidth();
            //逐行扫描图像的像素点，读取RGB值，取其平均值，并从charset中获取相应的字符素材，并装载到sb中
            for(int y=0; y<bitmapH; y++){
                for(int x=0; x<bitmapW; x++){
                    int rgb = buff.getRGB(x,y);
                    color = new Color(rgb);

                    int cvalue = (color.getRed()+color.getGreen()+color.getBlue()) / 3;
                    sb.append(charset[(int)((cvalue * charset.length - 1)/255)]+" ");
                }
                sb.append("\r\n");
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        imgString = sb.toString();
        return this;
    }


    /*图像文件预处理:将图片压缩到 最长边为 100px*/
    private  BufferedImage compressImage(BufferedImage srcImg){
        int h =  srcImg.getHeight();
        int w = srcImg.getWidth();
        if(Math.max(h,w)<=100)
            return srcImg;
        int new_H;
        int new_W;
        if(w>h){
            new_W = 100;
            new_H = 100*h/w ;
        }else{
            new_H = 100;
            new_W = 100*w/h;
        }
        BufferedImage smallImg = new BufferedImage(new_W,new_H,srcImg.getType());
        Graphics g = smallImg.getGraphics();
        g.drawImage(srcImg,0,0,new_W,new_H,null);
        g.dispose();
        return smallImg;
    }

    /*将字符串保存为.txt文件*/
    public void saveAsTxt(String fileName){
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for(int i = 0;i<imgString.length();i++){
                out.print(imgString.charAt(i));
            }
            out.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /*批处理图像文件*/
    public static void batchImgFile(String srcfile, String tragetfile){
        File folder = new File(tragetfile);   //生成图片的文件夹
        File srcfolder = new File(srcfile);
        if(!folder.exists() || !folder.isDirectory())
            folder.mkdirs();
        ImageProcesserUtils processer = new ImageProcesserUtils();
        File[] filelist = srcfolder.listFiles();

        for(int i=0;i<filelist.length;i++){
            if(!filelist[i].isFile())
                continue;
            processer.toBitmapConvert(filelist[i]);
            processer.saveAsTxt(tragetfile+"/"+(i+1)+".txt");
            System.out.println(filelist[i].getName()+" is converted!");
        }
        System.out.println("All img were converted!");

    }

    public static void main(String[] args) {
        File file1 = new File("D:\\video\\video2text"+"\\img"+"\\"+"1.jpg");
        if(!file1.isFile()){
            System.out.println("this file is not a file..."+1);
            return;
        }
        ImageProcesserUtils processer = new ImageProcesserUtils();
        processer.toBitmapConvert(file1);
        processer.saveAsTxt("D:\\video\\video2text"+"\\txt"+"\\"+"1.txt");
        System.out.println(file1.getName()+" is converted!");
    }

}
