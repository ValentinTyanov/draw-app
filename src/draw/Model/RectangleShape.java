package draw.Model;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RectangleShape extends Shape {

  public RectangleShape(Rectangle rect) {
    super(rect);
  }

  public RectangleShape(Shape rectangle) {}

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
  }
}
