package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class AsciiPic {
    private String imgString = "";   //储存转化后的字符串

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }


    public void createAsciiPic(File file) {
        StringBuffer sb = new StringBuffer();
        final String base = "@#&$%*o!;.";// 字符串由复杂到简单
        try {
            BufferedImage image = ImageIO.read(file);
            image = compressImage(image);
            for (int y = 0; y < image.getHeight(); y += 2) {
                for (int x = 0; x < image.getWidth(); x++) {
                    final int pixel = image.getRGB(x, y);
                    final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                    final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                    final int index = Math.round(gray * (base.length() + 1) / 255);
                    sb.append(index >= base.length() ? " " : String.valueOf(base.charAt(index)));
                }
                sb.append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgString = sb.toString();
    }

    /*图像文件预处理:将图片压缩到 最长边为 100px*/
    private  BufferedImage compressImage(BufferedImage srcImg){
        int h =  srcImg.getHeight();
        int w = srcImg.getWidth();
        if(Math.max(h,w)<=200)
            return srcImg;
        int new_H;
        int new_W;
        if(w>h){
            new_W = 200;
            new_H = 200*h/w ;
        }else{
            new_H = 200;
            new_W = 200*w/h;
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

    public static void main(final String[] args) {
        File file1 = new File("C:\\Users\\Administrator\\Desktop\\1.jpg");
        if(!file1.isFile()){
            System.out.println("this file is not a file..."+1);
            return;
        }
        AsciiPic asciiPic = new AsciiPic();
        asciiPic.createAsciiPic(file1);
        asciiPic.saveAsTxt("C:\\Users\\Administrator\\Desktop\\2.txt");
        System.out.println(file1.getName()+" is converted!");
    }
}
