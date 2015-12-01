/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author milton
 */
public class KochManagerWorker
{
    private CyclicBarrier cyclicBarrier;
    private KochFractal kochFractal;
    private ExecutorService pool;
    
    public KochManagerWorker(KochFractal kochFractal)
    {
        this.kochFractal = kochFractal;
        
        pool = Executors.newFixedThreadPool(3);
        cyclicBarrier = new CyclicBarrier(3);
    }
    
    public void ChangeLevel()
    {
        for (int i = 1; i <= 3; i++)
        {
            pool.submit(new KochWorker(i, kochFractal, cyclicBarrier));
        }
    }
}
