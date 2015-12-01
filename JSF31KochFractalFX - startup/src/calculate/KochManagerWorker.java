/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    
    public List<Edge> ChangeLevel() throws InterruptedException
    {
        KochWorker kochWorkerLeft = new KochWorker(1, kochFractal, cyclicBarrier);
        KochWorker kochWorkerBottom = new KochWorker(2, kochFractal, cyclicBarrier);
        KochWorker kochWorkerRight = new KochWorker(3, kochFractal, cyclicBarrier);
        
        Future futureLeft = pool.submit(kochWorkerLeft);
        Future futureBottom = pool.submit(kochWorkerBottom);
        Future futureRight = pool.submit(kochWorkerRight);
        
        while(!futureLeft.isDone() || !futureBottom.isDone() || futureRight.isDone())
        {
            System.out.println("Tasks are not complete yet...");
            Thread.sleep(1);
        }
        
        List<Edge> edges = new ArrayList<>();
        
    }
}
