package draw.Model;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class EnhancedRectangleShape extends Shape {

  public EnhancedRectangleShape(Rectangle rect) {
    super(rect);
  }

  public EnhancedRectangleShape(EnhancedRectangleShape shape) {}

  @Override
  public void drawSelf(Graphics grfx) {
    super.drawSelf(grfx);

    Rectangle rectangle = getRectangle();

    Graphics2D g2d = (Graphics2D) grfx;

    g2d.setColor(getFillColor());
    g2d.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    g2d.setColor(getBorderColor());
    g2d.setStroke(new BasicStroke(getBorderSize()));
    g2d.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

    g2d.drawLine(rectangle.x, rectangle.y + 200, rectangle.x + 100, rectangle.y);
    g2d.drawLine(rectangle.x + 100, rectangle.y + 200, rectangle.x, rectangle.y);
  }
}
