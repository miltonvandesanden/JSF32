/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space;

import jsf32_week17_jna.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;
import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author milton
 */
public interface I_DiskSpace extends Library
{
    I_DiskSpace INSTANCE = (I_DiskSpace) Native.loadLibrary("kernel32", I_DiskSpace.class);
    
    // Reference: https://msdn.microsoft.com/nl-nl/library/windows/desktop/aa364935%28v=vs.85%29.aspx?f=255&MSPPError=-2147217396
    /**
     * Bereken vrij ruimte op path (ASCII)
     * @param path                      Path naar schijf die je wilt berekenen
     * @param lpSectorsPerCluster       Aantal sectoren per cluster
     * @param lpBytesPerSector          Aantal bytes per sector
     * @param lpNumberOfFreeClusters    Aantal vrije clusters
     * @param lpTotalNumberOfClusters   Aantal clusters
     */
    boolean GetDiskFreeSpaceA(String path, IntByReference lpSectorsPerCluster, IntByReference lpBytesPerSector, IntByReference lpNumberOfFreeClusters, IntByReference lpTotalNumberOfClusters);

    /**
     * Bereken vrij ruimte op path (Wide String)
     * @param path                      Path naar schijf die je wilt berekenen
     * @param lpSectorsPerCluster       Aantal sectoren per cluster
     * @param lpBytesPerSector          Aantal bytes per sector
     * @param lpNumberOfFreeClusters    Aantal vrije clusters
     * @param lpTotalNumberOfClusters   Aantal clusters
     */
    boolean GetDiskFreeSpaceW(char[] path, IntByReference lpSectorsPerCluster, IntByReference lpBytesPerSector, IntByReference lpNumberOfFreeClusters, IntByReference lpTotalNumberOfClusters);

}
