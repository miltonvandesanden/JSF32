/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf31kochfractalfx;

import calculate.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author Nico Kuijpers
 */
public class JSF31KochFractalFX extends Application {


    // Koch manager
    private KochManager kochManager;
    private int level;
    
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
            //writeText(e);
            //bufferWriteText(e);
            writeBinaryText(e);
            //bufferWriteBinaryText(e);
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
    
    private void writeText(List<Edge> el) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        String path = "src/kochFractal.txt";
        FileWriter fw = new FileWriter(path);
        
        fw.write(level + "\n");
        
        for(Edge e : el) {
            fw.write(e.X1 + ";" + e.X2 + ";" + e.Y1 + ";" + e.Y2 + ";" + e.color.getRed() + "," + e.color.getBlue() + "," + e.color.getGreen());
            fw.write("\n");
        }
        
        fw.close();
    }
    
    private void bufferWriteText(List<Edge> el) throws IOException {
        String path = "src/kochFractal.txt";
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(level+"");
        bw.newLine();
        
        for(Edge e : el) {
            bw.write(e.X1 + ";" + e.X2 + ";" + e.Y1 + ";" + e.Y2 + ";" + e.color.getRed() + "," + e.color.getBlue() + "," + e.color.getGreen());
            bw.newLine();
        }
        
        bw.close();
        fw.close();
    }
    
    private void writeBinaryText(List<Edge> el) throws IOException {
        String path = "src/kochFractal.bin";
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(path));
        dos.writeInt(level);
        for(Edge e : el)
        {
            dos.writeDouble(e.X1);
            dos.writeDouble(e.Y1);
            dos.writeDouble(e.X2);
            dos.writeDouble(e.Y2);
            dos.writeDouble(e.color.getRed());
            dos.writeDouble(e.color.getGreen());
            dos.writeDouble(e.color.getBlue());
        }
        dos.close();
    }
    
    private void bufferWriteBinaryText(List<Edge> el) throws IOException {
        String path = "src/kochFractal.bin";
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        dos.writeInt(level);
        for(Edge e : el)
        {
            dos.writeDouble(e.X1);
            dos.writeDouble(e.Y1);
            dos.writeDouble(e.X2);
            dos.writeDouble(e.Y2);
            dos.writeDouble(e.color.getRed());
            dos.writeDouble(e.color.getGreen());
            dos.writeDouble(e.color.getBlue());
        }
        dos.close();
    }
}
