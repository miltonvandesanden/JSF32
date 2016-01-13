/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import java.util.Scanner;
import jsf32_week13_readfromrandom.JSF32_Week13_ReadFromRandom;

/**
 *
 * @author milton
 */
public class KochManager
{
    private JSF32_Week13_ReadFromRandom application;
    private TimeStamp timeStamp;
    
    private List<Edge> edges;
    private int level;
    
    private String filePath;
    
    public KochManager(JSF32_Week13_ReadFromRandom application)
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        filePath = "src\\kochFractal.txt";
    }
    
    public void changeLevel(final int nxt) throws IOException
    {
        timeStamp = new TimeStamp();
        timeStamp.setBegin("Begin uitlezen van edges.");
        
        RandomAccessFile raFile = new RandomAccessFile(filePath, "r");

//        level = raFile.readInt();
        level = Integer.parseInt(raFile.readLine());
        
        String line = "";
        double X1 = 0;
        double Y1 = 0;
        double X2 = 0;
        double Y2 = 0;
        double r = 0;
        double g = 0;
        double b = 0;
        
        while (raFile.getFilePointer() < raFile.length())
        {
            line = raFile.readLine();
            String[] vals = line.split(";");
            
            System.out.println("babla: " + vals[0]);
            X1 = Double.parseDouble(vals[0]);
            X2 = Double.parseDouble(vals[1]);
            Y1 = Double.parseDouble(vals[2]);
            Y2 = Double.parseDouble(vals[3]);

            /*X1 = raFile.readDouble();
            X2 = raFile.readDouble();
            Y1 = raFile.readDouble();
            Y2 = raFile.readDouble();
            r = raFile.readDouble();
            g = raFile.readDouble();
            b = raFile.readDouble();
            */
            String[] colors = vals[4].split(",");
            r = Double.parseDouble(colors[0]);
            g = Double.parseDouble(colors[1]);
            b = Double.parseDouble(colors[2]);
            
            edges.add(new Edge(X1, Y1, X2, Y2, new Color(r, g, b, 1)));
        }
        
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
