/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf31kochfractalfx;

import Write.Edge;
import Write.KochManager;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.stage.Stage;
import sun.nio.cs.ext.SJIS;

/**
 *
 * @author Nico Kuijpers
 */
public class JSF31KochFractalFX extends Application {


    // Koch manager
    private KochManager kochManager;
    private int level;
    String path = "src/kochFractal.txt";
    
    boolean busy = true;
    
    File old;
    File write;
    
    public void start(Stage primaryStage) {
        level = 1;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Level to generate: ");
        try{
            level = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        } catch (IOException ex) {
            Logger.getLogger(JSF31KochFractalFX.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kochManager = new KochManager(this);
        kochManager.changeLevel(level);
    }
    
    public void drawEdge(List<Edge> e) {
        try {
            writeBinaryText(e);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JSF31KochFractalFX.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSF31KochFractalFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void requestDrawEdges() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                kochManager.drawEdges();
            }
        });
    }
    
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private void writeBinaryText(List<Edge> el) throws IOException {
        System.out.println("Start Writing");
        
        File file = new File(path);
        if(file.exists())
            file.delete();
        file.createNewFile();
        
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fc = raf.getChannel();
        
        FileLock lock;

        raf.writeInt(level);
        raf.writeBytes("\n");
        
        String line = "";
        for(Edge e : el)
        {
            line = "";
            line += e.X1 + ";";
            line += e.Y1 + ";";
            line += e.X2 + ";";
            line += e.Y2 + ";";
            line += e.color.getRed() + ",";
            line += e.color.getGreen() + ",";
            line += e.color.getBlue() + "\n";
            
            lock = fc.lock(raf.length(), line.getBytes().length, false);
            raf.writeBytes(line);
            lock.release();
        }
        
        raf.close();
    }
}
