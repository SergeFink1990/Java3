package drawing.app;

import java.awt.Color;
import java.awt.Graphics2D;

public interface DrawingTool {
    void draw(int x, int y, Graphics2D g);
    void setColor(Color color);
    void setSize(int size);
    String getToolName();
}