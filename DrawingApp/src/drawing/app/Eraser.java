package drawing.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Eraser extends AbstractDrawingTool {
    private BufferedImage canvas;
    
    public Eraser() {
        this.toolName = "Ластик";
        this.color = Color.WHITE;
    }
    
    public void setCanvas(BufferedImage canvas) {
        this.canvas = canvas;
    }
    
    @Override
    public void draw(int x, int y, Graphics2D g) {
        if (canvas != null) {
            // Рисуем на холсте цветом фона (стираем)
            Graphics2D canvasG = canvas.createGraphics();
            canvasG.setColor(Color.WHITE);
            canvasG.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            canvasG.fillOval(x - size, y - size, size * 2, size * 2);
            canvasG.dispose();
        }
        // Обновляем отображение
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.fillOval(x - size, y - size, size * 2, size * 2);
    }
}