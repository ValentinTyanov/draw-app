package draw.Model;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class OvalShape extends Shape {

  public OvalShape(Rectangle rect) {
    super(rect);
  }

  public OvalShape(OvalShape oval) {}

  @Override
  public boolean contains(Point point) {
    Rectangle rectangle = getRectangle();
    double width = rectangle.getWidth();
    double height = rectangle.getHeight();

    double h = rectangle.x + width / 2;
    double k = rectangle.y + height / 2;

    double a = width / 2;
    double b = height / 2;

    double x = point.x;
    double y = point.y;

    return Math.pow((x - h), 2) / Math.pow(a, 2) + Math.pow((y - k), 2) / Math.pow(b, 2) <= 1;
  }

  @Override
  public void drawSelf(Graphics grfx) {
    super.drawSelf(grfx);
    Rectangle rectangle = getRectangle();
    grfx.setColor(getFillColor());
    grfx.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    grfx.setColor(getBorderColor());

    Graphics2D g2d = (Graphics2D) grfx;
    g2d.setStroke(new BasicStroke(getBorderSize()));
    g2d.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
  }
}
