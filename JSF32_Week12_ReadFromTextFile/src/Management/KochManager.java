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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
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
    private BufferedReader bufferedReader;
    
    public KochManager(JSF32_Week12_ReadFromTextFile application)
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        filePath = "/kochFractal.txt";
        
        try
        {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        bufferedReader = new BufferedReader(fileReader);
    }
    
    public void changeLevel(final int nxt)
    {
        try
        {
            timeStamp = new TimeStamp();
            timeStamp.setBegin("Begin uitlezen van edges.");
            
            int count = 1;
            String rawData;
            
            while (!bufferedReader.readLine().isEmpty())
            {
                String line = bufferedReader.readLine();
                double X1 = 0;
                double Y1 = 0;
                double X2 = 0;
                double Y2 = 0;
                Color color = new Color(0, 0, 0, 0);
                
                if(count == 1)
                {
                    level = Integer.parseInt(line);
                    
                    count++;
                }
                else
                {
                    String[] parts = line.split(";");
                    
                    for (int i = 0; i < parts.length; i++)
                    {
                        if (i == 0)
                        {
                            X1 = Double.parseDouble(parts[i]);
                        }
                        else if (i == 1)
                        {
                            Y1 = Double.parseDouble(parts[i]);
                        }
                        else if (i == 2)
                        {
                            X2 = Double.parseDouble(parts[i]);
                        }
                        else if (i == 3)
                        {
                            Y2 = Double.parseDouble(parts[i]);
                        }
                        else if (1 ==4)
                        {
                            String colorString = parts[i];
                            
                            String[] colorParts = colorString.split(",");
                            
                            double red = 0;
                            double green = 0;
                            double blue = 0;
                            
                            for (int j = 0; j < colorParts.length; j++)
                            {
                                if (j == 0)
                                {
                                    red = Double.parseDouble(colorParts[j]);
                                }
                                else if (j == 1)
                                {
                                    green = Double.parseDouble(colorParts[j]);
                                }
                                else if (j == 2)
                                {
                                    blue = Double.parseDouble(colorParts[j]);
                                }
                            }
                            
                            color = new Color(red, green, blue, 1);
                        }
                    }
                }
                
                edges.add(new Edge(X1, Y1, X2, Y2, color));
            }
            
            timeStamp.setEnd("Edges uitgelezen!");
            
            application.setTextCalc(timeStamp.toString());
            application.requestDrawEdges();
            application.setTextNrEdges(edges.size()+"");
        }
        catch(IOException | NumberFormatException e)
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
