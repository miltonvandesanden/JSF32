/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import java.util.Scanner;
import jsf32_week12_readfromtextfile.JSF32_Week12_ReadFromTextFile;

/**
 *
 * @author milton
 */
public class KochManager
{
    private JSF32_Week12_ReadFromTextFile application;
    private TimeStamp timeStamp;
    
    private List<Edge> edges;
    private int level;
    
    private String filePath;
    private FileReader fileReader;
//    private BufferedReader bufferedReader;
    private Scanner scanner;
    private Scanner scanner2;
    
    public KochManager(JSF32_Week12_ReadFromTextFile application)
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        filePath = "src\\kochFractal.bin";        
    }
    
    public void changeLevel(final int nxt) throws IOException
    {
        timeStamp = new TimeStamp();
        timeStamp.setBegin("Begin uitlezen van edges.");
        
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
        
        randomAccessFile.seek(0);
        level = randomAccessFile.readInt();
        
        
        while (randomAccessFile.getFilePointer() < randomAccessFile.length())
        {
            double X1 = randomAccessFile.readDouble();
            double Y1 = randomAccessFile.readDouble();
            double X2 = randomAccessFile.readDouble();
            double Y2 = randomAccessFile.readDouble();
            double r = randomAccessFile.readDouble();
            double g = randomAccessFile.readDouble();
            double b = randomAccessFile.readDouble();

            edges.add(new Edge(X1, Y1, X2, Y2, new Color(r, g, b, 0)));
        }
        
        randomAccessFile.close();
            
        timeStamp.setEnd("Edges uitgelezen!");

        application.setTextCalc(timeStamp.toString());
        application.requestDrawEdges();
        application.setTextNrEdges(edges.size()+"");
    }
    
    
    public void drawEdges()
    { 
        this.application.clearKochPanel();
        // Opdracht 8 Verplaats naar Change Level
/*        this.kochFractal.generateLeftEdge();
        this.kochFractal.generateBottomEdge();
        this.kochFractal.generateRightEdge();*/

        TimeStamp timeStampDraw = new TimeStamp();
        timeStampDraw.setBegin("Begin met tekenen.");

        for(Edge edge : edges)
        {
            this.application.drawEdge(edge);
        }
        
        timeStampDraw.setEnd("Klaar met tekenen!");
        this.application.setTextDraw(timeStamp.toString());
    }    
}
