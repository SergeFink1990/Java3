package drawing.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawingApp extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private BufferedImage canvas;
    private Graphics2D graphics2D;
    private DrawingTool currentTool;
    private Color currentColor = Color.BLACK;
    private int currentSize = 5;
    private boolean isDrawing = false;
    private int lastX, lastY;
    
    private DrawingPanel drawingPanel;
    private JComboBox<String> toolSelector;
    private JButton colorButton;
    private JSlider sizeSlider;
    
    private final DrawingToolFactory pencilFactory = new PencilFactory();
    private final DrawingToolFactory brushFactory = new BrushFactory();
    private final DrawingToolFactory eraserFactory = new EraserFactory();
    
    public DrawingApp() {
        setTitle("Приложение для рисования");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initComponents();
        createControlPanel();
        
        setCurrentTool(ToolType.PENCIL);
    }
    
    private void initComponents() {
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);
        
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        graphics2D = canvas.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, 800, 600);
        graphics2D.setColor(Color.BLACK);
        
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDrawing = true;
                lastX = e.getX();
                lastY = e.getY();
                drawPoint(lastX, lastY);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
            }
        });
        
        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    int x = e.getX();
                    int y = e.getY();
                    drawLine(lastX, lastY, x, y);
                    lastX = x;
                    lastY = y;
                }
            }
        });
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(Color.LIGHT_GRAY);
        
        String[] tools = {"Карандаш", "Кисть", "Ластик"};
        toolSelector = new JComboBox<>(tools);
        toolSelector.addActionListener(e -> {
            String selected = (String) toolSelector.getSelectedItem();
            switch (selected) {
                case "Карандаш":
                    setCurrentTool(ToolType.PENCIL);
                    break;
                case "Кисть":
                    setCurrentTool(ToolType.BRUSH);
                    break;
                case "Ластик":
                    setCurrentTool(ToolType.ERASER);
                    break;
            }
        });
        controlPanel.add(new JLabel("Инструмент:"));
        controlPanel.add(toolSelector);
        
        colorButton = new JButton();
        colorButton.setBackground(currentColor);
        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Выберите цвет", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                colorButton.setBackground(currentColor);
                if (currentTool != null && !(currentTool instanceof Eraser)) {
                    currentTool.setColor(currentColor);
                }
            }
        });
        controlPanel.add(new JLabel("Цвет:"));
        controlPanel.add(colorButton);
        
        sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, currentSize);
        sizeSlider.setPreferredSize(new Dimension(100, 30));
        sizeSlider.addChangeListener(e -> {
            currentSize = sizeSlider.getValue();
            if (currentTool != null) {
                currentTool.setSize(currentSize);
            }
        });
        controlPanel.add(new JLabel("Размер:"));
        controlPanel.add(sizeSlider);
        
        JButton clearButton = new JButton("Очистить");
        clearButton.addActionListener(e -> clearCanvas());
        controlPanel.add(clearButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }
    
    private void setCurrentTool(ToolType toolType) {
        switch (toolType) {
            case PENCIL:
                currentTool = pencilFactory.createTool();
                break;
            case BRUSH:
                currentTool = brushFactory.createTool();
                break;
            case ERASER:
                currentTool = eraserFactory.createTool();
                break;
        }
        currentTool.setColor(currentColor);
        currentTool.setSize(currentSize);
        System.out.println("Выбран инструмент: " + currentTool.getToolName());
    }
    
    private void drawPoint(int x, int y) {
        if (currentTool != null) {
            currentTool.draw(x, y, graphics2D);
            drawingPanel.repaint();
        }
    }
    
    private void drawLine(int x1, int y1, int x2, int y2) {
        if (currentTool != null) {
            int steps = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
            for (int i = 0; i <= steps; i++) {
                double t = (double) i / steps;
                int x = (int) (x1 + t * (x2 - x1));
                int y = (int) (y1 + t * (y2 - y1));
                currentTool.draw(x, y, graphics2D);
            }
            drawingPanel.repaint();
        }
    }
    
    private void clearCanvas() {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawingPanel.repaint();
    }
    
    private class DrawingPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvas, 0, 0, this);
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }
    }
    
    private enum ToolType {
        PENCIL, BRUSH, ERASER
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingApp().setVisible(true);
        });
    }
}