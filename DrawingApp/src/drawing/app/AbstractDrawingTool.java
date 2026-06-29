package drawing.app;

import java.awt.Color;

public abstract class AbstractDrawingTool implements DrawingTool {
    protected Color color;
    protected int size;
    protected String toolName;
    
    public AbstractDrawingTool() {
        this.color = Color.BLACK;
        this.size = 5;
        this.toolName = "Инструмент";
    }
    
    @Override
    public void setColor(Color color) {
        this.color = color;
    }
    
    @Override
    public void setSize(int size) {
        this.size = Math.max(1, Math.min(size, 50));
    }
    
    @Override
    public String getToolName() {
        return toolName;
    }
}