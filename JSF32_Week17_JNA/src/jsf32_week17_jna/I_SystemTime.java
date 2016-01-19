/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32_week17_jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;

/**
 *
 * @author milton
 */
public interface I_SystemTime extends Library
{
    I_SystemTime INSTANCE = (I_SystemTime) Native.loadLibrary("kernel32", I_SystemTime.class);
    
    void GetSystemTime(SYSTEMTIME systemtime);
}
