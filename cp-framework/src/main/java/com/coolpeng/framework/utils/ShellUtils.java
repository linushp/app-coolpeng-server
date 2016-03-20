package com.coolpeng.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Tan Liang
 * @since 2015-07-16
 */
public class ShellUtils {
    private static Logger logger = LoggerFactory.getLogger(ShellUtils.class);

    public static final boolean isWindowsOS;
    static {
        String osName = System.getProperties().getProperty("os.name");
        if(osName == null) {
            isWindowsOS = false;
        }
        else {
            isWindowsOS =osName.toUpperCase().contains("WINDOWS");
        }
    }
    private static String sh = isWindowsOS ? "cmd.exe":"sh";
    private static String shParam = isWindowsOS ? "/c":"-c";


    public static void runCmd(String cmd) {
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            System.out.println(cmd);
            Process p = run.exec(new String[]{sh, shParam, cmd});// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader( in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            inBr.close();
            in.close();

            in = new BufferedInputStream(p.getErrorStream());
            inBr = new BufferedReader(new InputStreamReader( in));
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                    System.err.println("命令执行失败!");
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
