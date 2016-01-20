/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space;

import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author Martijn
 */
public class calculateSpace_week17 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        I_DiskSpace i = I_DiskSpace.INSTANCE;
        String path = "C:\\";

        IntByReference spc = new IntByReference();
        IntByReference bps = new IntByReference();
        IntByReference fcl = new IntByReference();
        IntByReference tcl = new IntByReference();
        
        // via ASCII
            i.GetDiskFreeSpaceA(path, spc, bps, fcl, tcl);
        // Via Wide String
            //i.GetDiskFreeSpaceW(path.toCharArray(), spc, bps, fcl, tcl);
        
        long totalBytes = ((long)(tcl.getValue() * spc.getValue()) * bps.getValue())/1024;
        long freeBytes = ((long)(fcl.getValue() * spc.getValue()) * bps.getValue())/1024;
        
        System.out.println("Vrije ruimte op '"+path+"': "+freeBytes/1024+" kb vrij van "+totalBytes/1024+" kb");
    }
    
}
