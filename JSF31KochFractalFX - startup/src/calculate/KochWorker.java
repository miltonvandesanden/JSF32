/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CyclicBarrier;
import javafx.concurrent.Task;

/**
 *
 * @author milton
 */
public class KochWorker extends Task<List<Edge>> implements Observer
{
    //direction, 1 = left, 2 is dow, 3 is right
    private int direction;
    private KochFractal kochFractal;
    private CyclicBarrier cyclicBarrier;
    
    public KochWorker(int direction, KochFractal kochFractal, CyclicBarrier cyclicBarrier)
    {
        this.direction = direction;
        this.kochFractal = kochFractal;
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    protected List<Edge> call() throws Exception
    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Edge> edges = new ArrayList<>();
        
        switch (direction)
        {
            case 1:
                edges = kochFractal.generateLeftEdge();
                break;
            case 2:
                edges = kochFractal.generateBottomEdge();
                break;
            case 3:
                edges = kochFractal.generateRightEdge();
                break;
        }
        return edges;
    }

    @Override
    public void update(Observable o, Object o1)
    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    
}
