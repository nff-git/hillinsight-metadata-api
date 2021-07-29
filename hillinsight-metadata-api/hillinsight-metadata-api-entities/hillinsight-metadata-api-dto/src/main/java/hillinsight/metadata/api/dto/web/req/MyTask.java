package hillinsight.metadata.api.dto.web.req;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import hillinsight.metadata.api.web.ThirdFieldInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2021/07/08
 */
public class MyTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("开始走了");
    }

    public static void main3(String[] args) {
        //创建定时器对象
        Timer t=new Timer();
        //在3秒后执行MyTask类中的run方法
        t.schedule(new MyTask(), 0);
        t.cancel();
        PollThreadConfig pollThreadConfig = new PollThreadConfig();
        pollThreadConfig.setThreadId("hello");
        pollThreadConfig.setThreadName("");
//        PollThreadConfig pollThreadConfig2 = new PollThreadConfig();
//        pollThreadConfig2.setThreadId("");
//        pollThreadConfig2.setThreadName("");
        boolean b = checkObjAllFieldsIsNull(pollThreadConfig);
        System.out.println("================="+b);

    }

    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void main(String[] args) {
        Persion p = new Persion(1l, "陈俊生");
        System.out.println("person Seria:" + p);
        try {
            FileOutputStream fos = new FileOutputStream("Persion.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 定时删除指定位置的文件
     */
    public static void mai2n(String[] args) {

        File[] files = File.listRoots();
        System.out.println(files);
        for (File file : files) {
            System.out.println(file);
        }

        //获取D盘下的所以文件/获取D盘下的text文件
        File file = new File("D://");
        System.out.println("fileList==========="+file.listFiles());
        String[] list = file.list();
        for (String s : list) {
            if (s.endsWith(".txt")) {
                System.out.println(s);
            }
        }
        File[] fileList=file.listFiles((pathname)->{
            if (pathname.isDirectory()){
                return true;
            }
            String fileName=pathname.getName();
            return fileName.endsWith(".java");
        });


    }


}


