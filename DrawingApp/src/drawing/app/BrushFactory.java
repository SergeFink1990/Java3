package drawing.app;

public class BrushFactory implements DrawingToolFactory {
    @Override
    public DrawingTool createTool() {
        return new Brush();
    }
}