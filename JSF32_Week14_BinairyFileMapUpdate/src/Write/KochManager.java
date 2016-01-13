/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Write;

import static java.lang.Thread.sleep;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractalfx.JSF31KochFractalFX;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

/**
 *
 * @author Martin
 */
public class KochManager {
    
    private JSF31KochFractalFX application;
    private List<Edge> edgeList; 
    private List<Edge> tempEdgeList; 
    private KochFractal kochFractal;
    
    private Task<Void> rightTask = null;
    private Task<Void> leftTask = null;
    private Task<Void> bottomTask = null;
    private Task<Void> drawTask = null;
    
    private Thread tRight = null;
    private Thread tLeft = null;
    private Thread tBottom = null;
    private Thread tDraw = null;
    
    private int count;
    
    public KochManager(JSF31KochFractalFX application)
    {
        this.edgeList = new ArrayList<Edge>();
        this.tempEdgeList = new ArrayList<Edge>();
        this.application = application; //Add application
        
        this.kochFractal = new KochFractal();
        this.kochFractal.setLevel(1);
        
        KochFractalObserver kochFractalObserver = new KochFractalObserver();//Create observer
        this.kochFractal.addObserver(kochFractalObserver);//Add observer
    }
    
    public void changeLevel(final int nxt)
    {
        this.tempEdgeList = new ArrayList<Edge>();
        //application.requestDrawEdges();
        this.kochFractal.cancel();
        
        if(this.leftTask != null && this.leftTask.isRunning())
            this.leftTask.cancel();
        if(this.rightTask != null && this.rightTask.isRunning())
            this.rightTask.cancel();
        if(this.bottomTask != null && this.bottomTask.isRunning())
            this.bottomTask.cancel();

        this.count = 0;
        this.kochFractal = new KochFractal();
        this.kochFractal.setLevel(nxt);
        
        KochFractalObserver kochFractalObserver = new KochFractalObserver();//Create observer
        this.kochFractal.addObserver(kochFractalObserver);//Add observer

        leftTask = new Task<Void>()
        {
            
            @Override 
            public Void call() throws InterruptedException {
                ObservableList edges = FXCollections.observableList(new ArrayList<Edge>());
                edges.addListener(new ListChangeListener() {
                    int numberOfEdges;
                    @Override
                    public void onChanged(ListChangeListener.Change change) {
                        numberOfEdges++;
                        updateProgress(numberOfEdges, kochFractal.getNrOfEdges()/3);
                    }
                });
                
                updateProgress(0, kochFractal.getNrOfEdges());
                kochFractal.generateLeftEdge(edges);
                return null;
            }
            
            @Override 
            protected void done()
            {
                edgesCalculated();
            }
        };

        bottomTask = new Task<Void>()
        {
            
            @Override 
            public Void call() throws InterruptedException {
                ObservableList edges = FXCollections.observableList(new ArrayList<Edge>());
                edges.addListener(new ListChangeListener() {
                    int numberOfEdges;
                    @Override
                    public void onChanged(ListChangeListener.Change change) {
                        numberOfEdges++;
                        updateProgress(numberOfEdges, kochFractal.getNrOfEdges()/3);
                    }
                });
                
                updateProgress(0, kochFractal.getNrOfEdges());
                kochFractal.generateBottomEdge(edges);
                return null;
            }
            
            @Override 
            protected void done()
            {
                edgesCalculated();
            }
        };

        rightTask = new Task<Void>()
        {
            
            @Override 
            public Void call() throws InterruptedException {
                ObservableList edges = FXCollections.observableList(new ArrayList<Edge>());
                edges.addListener(new ListChangeListener() {
                    int numberOfEdges;
                    @Override
                    public void onChanged(ListChangeListener.Change change) {
                        numberOfEdges++;
                        updateProgress(numberOfEdges, kochFractal.getNrOfEdges()/3);
                    }
                });
                
                updateProgress(0, kochFractal.getNrOfEdges());
                kochFractal.generateRightEdge(edges);
                return null;
            }
            
            @Override 
            protected void done()
            {
                edgesCalculated();
            }
        };

        tLeft = new Thread(leftTask);
        tBottom = new Thread(bottomTask);
        tRight = new Thread(rightTask);
        //tDraw = new Thread(drawTask);

        tLeft.start();
        tBottom.start();
        tRight.start();
        //tDraw.start();

    }

    public void edgesCalculated()
    {
        count++;
        if(count != 3 || kochFractal.isCancelled())
            return;

        edgeList = tempEdgeList;

        application.requestDrawEdges();
    }
    
    public KochFractal getNewKochFractal(int level)
    {
        
        return kochFractal;
        
    }
    
    public void drawEdges()
    {
        this.application.drawEdge(edgeList);
    }
    
    public class KochFractalObserver implements Observer
    {
        @Override
        public synchronized void update(Observable o, Object arg)
        {
            final Edge edge = (Edge) arg;
            tempEdgeList.add(edge);
        }
    }
}