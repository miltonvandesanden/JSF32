/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32_week17_jna;

import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;
import static java.lang.System.nanoTime;

/**
 *
 * @author milton
 */
public class JSF32_Week17_JNA
{ 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SYSTEMTIME forStart = new SYSTEMTIME();
        SYSTEMTIME forEnd = new SYSTEMTIME();
        
        long nanoTimeStart;
        long nanoTimeEnd;
        
        I_SystemTime.INSTANCE.GetSystemTime(forStart);
        nanoTimeStart = nanoTime();
        
        for (int i = 0; i < 1000000000; i++)
        {
            
        }
        
        I_SystemTime.INSTANCE.GetSystemTime(forEnd);
        nanoTimeEnd = nanoTime();

        long systemTimeDifference = forEnd.wMilliseconds - forStart.wMilliseconds;
        long nanoTimeDifference = nanoTimeEnd - nanoTimeStart;
        
        System.out.println("SystemTime: " + systemTimeDifference + " ms");
        System.out.println("nanoTime: " + nanoTimeDifference + " ms");
    }    
}
