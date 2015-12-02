/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsf32_week12_readfilefrombinaryfile.JSF32_Week12_ReadFileFromBinaryFile;

/**
 *
 * @author milton
 */
public class KochManager
{
    private JSF32_Week12_ReadFileFromBinaryFile application;
    private TimeStamp timeStamp;
    
    private List<Edge> edges;
    private int level;
    
    private String filePath;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    
    public KochManager(JSF32_Week12_ReadFileFromBinaryFile application) throws FileNotFoundException, IOException
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        filePath = "/kochFractal.txt";
        
        fileInputStream = new FileInputStream(filePath);
        objectInputStream = new ObjectInputStream(fileInputStream);
    }
    
    public void changeLevel(final int nxt)
    {
        try
        {
            timeStamp = new TimeStamp();
            timeStamp.setBegin("Begin uitlezen van edges.");
            
            Edge edge = (Edge) objectInputStream.readObject();
            
            edges.add(edge);
                        
            timeStamp.setEnd("Edges uitgelezen!");
            
            application.setTextCalc(timeStamp.toString());
            application.requestDrawEdges();
            application.setTextNrEdges(edges.size()+"");
        }
        catch (Exception exception)
        {
            
        }
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
