package draw.Model;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class TriangleShape extends Shape {

  public TriangleShape(Rectangle rect) {
    super(rect);
  }

  public TriangleShape(TriangleShape triangle) {}

  @Override
  public boolean contains(Point point) {
    Rectangle rectangle = getRectangle();
    double width = rectangle.getWidth();
    double height = rectangle.getHeight();

    // A
    double x1 = rectangle.x;
    double y1 = rectangle.y;

    // B
    double x2 = rectangle.x;
    double y2 = rectangle.y + width;

    // C
    double x3 = rectangle.x + height;
    double y3 = rectangle.y;

    // P
    double p1 = point.x;
    double p2 = point.y;

    double totalTriangleArea = triangleArea(x1, y1, x2, y2, x3, y3);

    double PBC = triangleArea(p1, p2, x2, y2, x3, y3);

    double PAC = triangleArea(x1, y1, p1, p2, x3, y3);

    double PAB = triangleArea(x1, y1, x2, y2, p1, p2);

    return totalTriangleArea == PBC + PAB + PAC;
  }

  @Override
  public void drawSelf(Graphics grfx) {
    super.drawSelf(grfx);
    Rectangle rectangle = getRectangle();

    double width = rectangle.getWidth();
    double height = rectangle.getHeight();

    int[] xCoords = {rectangle.x, rectangle.x, (int) (rectangle.x + height)};
    int[] yCoords = {rectangle.y, (int) (rectangle.y + width), rectangle.y};

    grfx.setColor(getFillColor());
    grfx.fillPolygon(xCoords, yCoords, 3);
    grfx.setColor(getBorderColor());

    Graphics2D g2d = (Graphics2D) grfx;
    g2d.setStroke(new BasicStroke(getBorderSize()));
    g2d.drawPolygon(xCoords, yCoords, 3);
  }

  public double triangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
    return Math.abs(((x1 - x2) * (y1 + y2) + (x2 - x3) * (y2 + y3) + (x3 - x1) * (y3 + y1)) / 2);
  }
}
