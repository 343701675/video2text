import java.io.*;

public class Reload {
    private static String app_dir = "D:\\video\\video2text";
    private static String txt_dir = "txt";

    /**
     * 输出字符文件序列
     * @return
     */
    public static Boolean play() {
        Console c = System.console();
        boolean result=true;
        File file = new File(app_dir+"\\"+txt_dir);
        if(file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for(int i=0;i<files.length;i++){
                    try {
//                        //清屏，防抖动
                        new ProcessBuilder("cmd", "/c", "cls")
                                // 将 ProcessBuilder 对象的输出管道和 Java 的进程进行关联，这个函数的返回值也是一个
                                // ProcessBuilder
                                .inheritIO()
                                // 开始执行 ProcessBuilder 中的命令
                                .start()
                                // 等待 ProcessBuilder 中的清屏命令执行完毕
                                // 如果不等待则会出现清屏代码后面的输出被清掉的情况
                                .waitFor(); // 清屏命令
                        File file1 = new File(app_dir+"\\"+txt_dir+"\\"+(i+1)+".txt");
                        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file1));
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String line;
                        while((line=bufferedReader.readLine())!=null){
                            c.writer().println(line);
                            //System.out.println(line);
                        }
                        Thread.sleep(40);
                    } catch (Exception e) {
                        result = false;
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
