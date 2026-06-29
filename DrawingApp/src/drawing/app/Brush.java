package drawing.app;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Brush extends AbstractDrawingTool {
    
    public Brush() {
        this.toolName = "Кисть";
    }
    
    @Override
    public void draw(int x, int y, Graphics2D g) {
        g.setColor(color);
        // Толстая линия с мягкими краями
        g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Рисуем несколько кругов для эффекта кисти
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                if (Math.sqrt(i*i + j*j) <= 3) {
                    int offsetX = x + i * size/2;
                    int offsetY = y + j * size/2;
                    g.fillOval(offsetX - size/2, offsetY - size/2, size, size);
                }
            }
        }
    }
}