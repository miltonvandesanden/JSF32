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
    
    private DataInputStream dataInputStream;
    
    public KochManager(JSF32_Week12_ReadFileFromBinaryFile application) throws FileNotFoundException, IOException
    {
        this.application = application; //Add application
        
        edges = new ArrayList<>();
        level = 1;
        
        filePath = "C:\\Users\\milton\\Documents\\GitHub\\JSF32\\consoleNaarTekst\\consoleNaarTekst\\src\\kochFractal.bin";
        
        dataInputStream = new DataInputStream(new FileInputStream(filePath));
    }
    
    public void changeLevel(final int nxt) throws Exception
    {
        try
        {
            Socket socket = new Socket("localhost", 8189);
            
            try
            {
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                
                System.out.println("sending lvl");
                objectOutputStream.writeObject(nxt);
                objectOutputStream.flush();
                
                List<Edge> edges = (List<Edge>)objectInputStream.readObject();
                System.out.println("ontvangen antwoord: " + edges.toString());
                
                double X1 = dataInputStream.readDouble();
                double Y1 = dataInputStream.readDouble();
                double X2 = dataInputStream.readDouble();
                double Y2 = dataInputStream.readDouble();

                double red = dataInputStream.readDouble();
                double green = dataInputStream.readDouble();
                double blue = dataInputStream.readDouble();

                edges.add(new Edge(X1, Y1, X2, Y2, new Color(red, green, blue, 1)));

            } catch (Exception e)
            {
                throw e;
            }
            finally
            {
                socket.close();
            }
        } catch (IOException e)
        {
            throw e;
        }
        catch (ClassNotFoundException nfe)
        {
            throw nfe;
        }
        
        
        /*
        try
        {
            timeStamp = new TimeStamp();
            timeStamp.setBegin("Begin uitlezen van edges.");
            
            if (dataInputStream.available() > 0)
            {
                int level = dataInputStream.readInt();

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
            
            application.setTextCalc(timeStamp.toString());
            application.requestDrawEdges();
            application.setTextNrEdges(edges.size()+"");
        }
        catch (Exception exception)
        {
            
        }
        */
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
