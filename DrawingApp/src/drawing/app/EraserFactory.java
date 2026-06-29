package drawing.app;

public class EraserFactory implements DrawingToolFactory {
    @Override
    public DrawingTool createTool() {
        return new Eraser();
    }
}