/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Read;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author milton
 */
public class Edge implements Serializable
{
    public double X1, Y1, X2, Y2;
    public Color color;
    
    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color;
    }    
}
