/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class KochFractal extends Observable {

    private int level = 1;      // The current level of the fractal
    private int nrOfEdges = 3;  // The number of edges in the current level of the fractal
    private float hue;          // Hue value of color for next edge
    private boolean cancelled;  // Flag to indicate that calculation has been cancelled 

    private void drawKochEdge(double ax, double ay, double bx, double by, int n, long sleep, List<Edge> edges) throws InterruptedException {
        sleep(sleep);
        if (!cancelled) {
            if (n == 1) {
                hue = hue + 1.0f / nrOfEdges;
                Edge e = new Edge(ax, ay, bx, by, Color.hsb(hue*360.0, 1.0, 1.0));
                
                synchronized (this)
                {
                    edges.add(e);
                    this.setChanged();
                    this.notifyObservers(e);
                }
            } else {
                double angle = Math.PI / 3.0 + Math.atan2(by - ay, bx - ax);
                double distabdiv3 = Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay)) / 3;
                double cx = Math.cos(angle) * distabdiv3 + (bx - ax) / 3 + ax;
                double cy = Math.sin(angle) * distabdiv3 + (by - ay) / 3 + ay;
                final double midabx = (bx - ax) / 3 + ax;
                final double midaby = (by - ay) / 3 + ay;
                drawKochEdge(ax, ay, midabx, midaby, n - 1, sleep, edges);
                drawKochEdge(midabx, midaby, cx, cy, n - 1, sleep, edges);
                drawKochEdge(cx, cy, (midabx + bx) / 2, (midaby + by) / 2, n - 1, sleep, edges);
                drawKochEdge((midabx + bx) / 2, (midaby + by) / 2, bx, by, n - 1, sleep, edges);
            }
        }
    }

    public void generateLeftEdge(List<Edge> edges) throws InterruptedException { 
        hue = 0f;
        cancelled = false;
        
        long sleep = (new Random()).nextInt(3) + 1;
        drawKochEdge(0.5, 0.0, (1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, level, sleep, edges);
    }

    public void generateBottomEdge(List<Edge> edges) throws InterruptedException {
        hue = 1f / 3f;
        cancelled = false;
        
        long sleep = (new Random()).nextInt(3) + 1;
        drawKochEdge((1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, (1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, level, sleep, edges);
    }

    public void generateRightEdge(List<Edge> edges) throws InterruptedException {
        hue = 2f / 3f;
        cancelled = false;
        
        long sleep = (new Random()).nextInt(3) + 1;
        drawKochEdge((1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, 0.5, 0.0, level, sleep, edges);
        
    }
    
    public boolean isCancelled()
    {
        return cancelled;
    }
    
    public void cancel() {
        cancelled = true;
    }

    public void setLevel(int lvl) {
        level = lvl;
        nrOfEdges = (int) (3 * Math.pow(4, level - 1));
    }

    public int getLevel() {
        return level;
    }

    public int getNrOfEdges() {
        return nrOfEdges;
    }
}