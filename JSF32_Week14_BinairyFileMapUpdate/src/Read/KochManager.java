/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Read;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import jsf32_week14_readfilefrombinaryfile.JSF32_Week12_ReadFileFromBinaryFile;

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
    
    private  String path = "src/kochFractal.bin";
    
    private DataInputStream dataInputStream;
    
    public KochManager(JSF32_Week12_ReadFileFromBinaryFile application) throws FileNotFoundException, IOException
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        dataInputStream = new DataInputStream(new FileInputStream(path));
    }
    
    public void changeLevel(final int nxt)
    {
        try
        {
            timeStamp = new TimeStamp();
            timeStamp.setBegin("Begin uitlezen van edges.");
            
            if (dataInputStream.available() > 0)
            {
                level = dataInputStream.readInt();

                while(dataInputStream.available() > 0)
                {

                    double X1 = dataInputStream.readDouble();
                    double Y1 = dataInputStream.readDouble();
                    double X2 = dataInputStream.readDouble();
                    double Y2 = dataInputStream.readDouble();

                    double red = dataInputStream.readDouble();
                    double green = dataInputStream.readDouble();
                    double blue = dataInputStream.readDouble();

                    edges.add(new Edge(X1, Y1, X2, Y2, new Color(red, green, blue, 1)));
                }                
            }
                        
//            Edge edge = (Edge) dataInputStream.
//            
//            edges.add(edge);
            
            
                        
            timeStamp.setEnd("Edges uitgelezen!");
            application.setLevel(level);
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
