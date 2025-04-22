package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;


public abstract class NimoComponent extends JComponent {
    protected static final int DEFAULT_BORDER_RADIUS = 15;
    protected static final int DEFAULT_BORDER_WIDTH = 2;
    protected static final Color DEFAULT_BORDER_COLOR = new Color(7, 4, 4); // Using colors.black
    
    protected int borderRadius;
    protected int borderWidth;
    protected Color backgroundColor;
    protected Color borderColor;
    protected boolean isPressed;
    protected boolean isHovered;
    protected boolean isFocused;
    
    // Use the colors class
    protected static final NimoColors appColors = new NimoColors();
    
    // Shape used for hit detection
    private Shape componentShape;
    
    public NimoComponent() {
        this.borderRadius = DEFAULT_BORDER_RADIUS;
        this.borderWidth = DEFAULT_BORDER_WIDTH;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.backgroundColor = appColors.background;
        
        setOpaque(false);
        setFocusable(true);
        
        // Add mouse listeners for interactive states
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (contains(e.getPoint())) {
                    isPressed = true;
                    repaint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isPressed) {
                    isPressed = false;
                    repaint();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }
        };
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        // Add focus listener for keyboard accessibility
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                isFocused = true;
                repaint();
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                isFocused = false;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Paint background
        paintBackground(g2);
        
        // Paint border
        paintBorder(g2);
        
        // Paint focus indicator if component has focus
        if (isFocused) {
            paintFocusIndicator(g2);
        }
        
        // Paint content
        paintContent(g2);
        
        g2.dispose();
    }
    
    protected void paintBackground(Graphics2D g2) {
        if (backgroundColor != null) {
            g2.setColor(getCurrentBackgroundColor());
            RoundRectangle2D roundRect = new RoundRectangle2D.Float(
                borderWidth/2f, borderWidth/2f, 
                getWidth() - borderWidth, getHeight() - borderWidth,
                borderRadius, borderRadius
            );
            g2.fill(roundRect);
            
            // Update component shape for proper hit detection
            componentShape = roundRect;
        }
    }
    
    protected void paintBorder(Graphics2D g2) {
        if (borderWidth > 0 && borderColor != null) {
            g2.setColor(getCurrentBorderColor());
            g2.setStroke(new BasicStroke(borderWidth));
            g2.draw(new RoundRectangle2D.Float(
                borderWidth/2f, borderWidth/2f, 
                getWidth() - borderWidth, getHeight() - borderWidth,
                borderRadius, borderRadius
            ));
        }
    }
    
    protected void paintFocusIndicator(Graphics2D g2) {
        float dashPhase = System.currentTimeMillis() % 1000 / 1000f * 10f;
        g2.setColor(borderColor.brighter());
        g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_BUTT, 
                                    BasicStroke.JOIN_ROUND, 0, new float[] {5f, 5f}, dashPhase));
        g2.draw(new RoundRectangle2D.Float(
            borderWidth * 2, borderWidth * 2, 
            getWidth() - borderWidth * 4, getHeight() - borderWidth * 4,
            borderRadius - borderWidth, borderRadius - borderWidth
        ));
    }
    
    /**
     * Paint the specific content of this component.
     * Subclasses must implement this method to define their visual appearance.
     * 
     * @param g2 Graphics context for rendering
     */
    protected abstract void paintContent(Graphics2D g2);
    
    /**
     * Returns the background color modified based on component state
     * @return Color adjusted for current component state
     */
    protected Color getCurrentBackgroundColor() {
        if (isPressed) {
            return applyColorAdjustment(backgroundColor, 0.8f);
        } else if (isHovered) {
            return applyColorAdjustment(backgroundColor, 1.1f);
        }
        return backgroundColor;
    }
    
    /**
     * Returns the border color modified based on component state
     * @return Color adjusted for current component state
     */
    protected Color getCurrentBorderColor() {
        if (isPressed) {
            return applyColorAdjustment(borderColor, 0.7f);
        } else if (isHovered || isFocused) {
            return applyColorAdjustment(borderColor, 1.2f);
        }
        return borderColor;
    }
    
    /**
     * Applies a brightness adjustment to a color
     * @param color The color to adjust
     * @param factor Factor greater than 1.0 brightens, less than 1.0 darkens
     * @return The adjusted color
     */
    protected Color applyColorAdjustment(Color color, float factor) {
        if (color == null) return null;
        
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[2] = Math.max(0f, Math.min(1f, hsb[2] * factor)); // Adjust brightness
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
    
    /**
     * Override contains method to properly handle rounded corners
     */
    @Override
    public boolean contains(Point p) {
        return componentShape != null && componentShape.contains(p);
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Update component shape when size changes
        componentShape = new RoundRectangle2D.Float(
            borderWidth/2f, borderWidth/2f, 
            width - borderWidth, height - borderWidth,
            borderRadius, borderRadius
        );
    }
    
    // Getters and setters
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }
    
    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
    
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        // Update shape when radius changes
        if (getWidth() > 0 && getHeight() > 0) {
            componentShape = new RoundRectangle2D.Float(
                borderWidth/2f, borderWidth/2f, 
                getWidth() - borderWidth, getHeight() - borderWidth,
                borderRadius, borderRadius
            );
        }
        repaint();
    }
    
    public void setBorderWidth(int width) {
        this.borderWidth = width;
        repaint();
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public int getBorderRadius() {
        return borderRadius;
    }
    
    public int getBorderWidth() {
        return borderWidth;
    }
}