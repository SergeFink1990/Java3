package drawing.app;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Pencil extends AbstractDrawingTool {
    
    public Pencil() {
        this.toolName = "Карандаш";
    }
    
    @Override
    public void draw(int x, int y, Graphics2D g) {
        g.setColor(color);
        // Тонкая линия с острыми краями
        g.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.fillOval(x - size/2, y - size/2, size, size);
    }
}