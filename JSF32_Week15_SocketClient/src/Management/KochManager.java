/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
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
    
    private String ip;
    private int port;
    
    private DataInputStream dataInputStream;
    
    public KochManager(JSF32_Week12_ReadFileFromBinaryFile application) throws FileNotFoundException, IOException
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        filePath = "C:\\Users\\milton\\Documents\\GitHub\\JSF32\\consoleNaarTekst\\consoleNaarTekst\\src\\kochFractal.bin";
        ip = "localhost";
        port = 8189;
        
//        dataInputStream = new DataInputStream(new FileInputStream(filePath));
    }
    
    public void changeLevel(final int nxt)
    {
        try
        {
            timeStamp = new TimeStamp();
            timeStamp.setBegin("Begin uitlezen van edges.");
            
            Socket socket = new Socket(ip, port);
            OutputStream outputStream;
            InputStream inputStream;
            ObjectOutputStream objectOutputStream;
            ObjectInputStream objectInputStream;
            
            try
            {
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();

                objectOutputStream = new ObjectOutputStream(outputStream);
                objectInputStream = new ObjectInputStream(inputStream);
                
                System.out.println("sending level: " + nxt);
                objectOutputStream.writeBytes("" + nxt);
                objectOutputStream.flush();
                
                while (objectInputStream.available() < 1)
                {
                    
                }
                
                String result = (String)objectInputStream.readObject();
                
                String[] edgesArray = result.split("-");
                
                double X1;
                double X2;
                double Y1;
                double Y2;
                
                double red;
                double green;
                double blue;
                
                for (String edge : edgesArray)
                {
                    String[] edgeParts = edge.split(";");
                    
                    X1 = Double.parseDouble(edgeParts[0]);
                    Y1 = Double.parseDouble(edgeParts[1]);
                    X2 = Double.parseDouble(edgeParts[2]);
                    Y2 = Double.parseDouble(edgeParts[3]);
                    
                    String[] color = edgeParts[4].split(",");
                    
                    red = Double.parseDouble(color[0]);
                    green = Double.parseDouble(color[1]);
                    blue = Double.parseDouble(color[2]);
                    
                    edges.add(new Edge(X1, Y1, X2, Y2, new Color(red, green, blue, 1)));
                }
            } catch (Exception e)
            {
            }
            finally
            {
                socket.close();                
            }
//            
//            if (dataInputStream.available() > 0)
//            {
//                int level = dataInputStream.readInt();
//
//                while(dataInputStream.available() > 0)
//                {
//
//                    double X1 = dataInputStream.readDouble();
//                    double Y1 = dataInputStream.readDouble();
//                    double X2 = dataInputStream.readDouble();
//                    double Y2 = dataInputStream.readDouble();
//
//                    double red = dataInputStream.readDouble();
//                    double green = dataInputStream.readDouble();
//                    double blue = dataInputStream.readDouble();
//
//                    edges.add(new Edge(X1, Y1, X2, Y2, new Color(red, green, blue, 1)));
//                }                
//            }
                        
//            Edge edge = (Edge) dataInputStream.
//            
//            edges.add(edge);
            
            
                        
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
