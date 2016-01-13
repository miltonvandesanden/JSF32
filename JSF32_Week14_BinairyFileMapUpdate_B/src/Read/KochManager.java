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
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import jsf32_week14_read.JSF32_Week14_Read;

/**
 *
 * @author milton
 */
public class KochManager
{
    private JSF32_Week14_Read application;
    private TimeStamp timeStamp;
    
    private List<Edge> edges;
    private int level;

    
    public KochManager(JSF32_Week14_Read application) throws FileNotFoundException, IOException
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
    }
    
    public void changeLevel(final int nxt)
    {
        try
        {
            timeStamp = new TimeStamp();
            timeStamp.setBegin("Begin uitlezen van edges.");

            RandomAccessFile raFile = new RandomAccessFile("src/kochFractal.txt", "r");
            FileChannel fc = raFile.getChannel();
            
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            
            //level = raFile.readChar();
            
            System.out.println("Level: " + raFile.readChar());
            
            int lines = (int) (3 * Math.pow(4, level-1));

            do {
                edges.clear();
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
                    X1 = Double.parseDouble(vals[0]);
                    X2 = Double.parseDouble(vals[1]);
                    Y1 = Double.parseDouble(vals[2]);
                    Y2 = Double.parseDouble(vals[3]);
                    
                    String[] colors = vals[4].split(",");
                    r = Double.parseDouble(colors[0]);
                    g = Double.parseDouble(colors[1]);
                    b = Double.parseDouble(colors[2]);

                    edges.add(new Edge(X1, Y1, X2, Y2, new Color(r, g, b, 1)));
                }        
            } while (edges.size() < lines);
            
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
