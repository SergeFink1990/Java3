package drawing.app;

public class PencilFactory implements DrawingToolFactory {
    @Override
    public DrawingTool createTool() {
        return new Pencil();
    }
}