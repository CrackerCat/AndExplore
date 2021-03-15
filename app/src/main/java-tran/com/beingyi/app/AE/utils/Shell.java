package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.application.MyApplication;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Shell
{
    static String TAG="Shell";

    
    /**
     * 执行命令并且输出结果
     */
    public static String execRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            dos.writeBytes(cmd + "\n");
            dos.flush();
            String line = null;
            while ((line = dis.readLine()) != null) {
                Log.d("result", line);
                result += line;
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    
    // 执行命令但不关注结果输出 
    public static int execRootCmdSilent(String cmd)
    {  
        int result = -1; 
        DataOutputStream dos = null; 
        try
        {  Process p = Runtime.getRuntime().exec("su"); 
            dos = new DataOutputStream(p.getOutputStream());  

            dos.writeBytes(cmd + "\n");  
            dos.flush(); 
            dos.writeBytes("exit\n");
            dos.flush();  
            p.waitFor(); 
            result = p.exitValue();
        }
        catch (Exception e)
        { 
            e.printStackTrace();  }
        finally
        {  
            if (dos != null)
            {  
                try
                {  dos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();  }  }  }
        return result;  
    }  
    
    
    
}
