package draw.Processors;

import draw.Model.EnhancedRectangleShape;
import draw.Model.OvalShape;
import draw.Model.RectangleShape;
import draw.Model.Shape;
import draw.Model.TriangleShape;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class DialogProcessor extends DisplayProcessor {
  private Shape selection;

  private boolean isDragging;

  private Point lastLocation;

  public DialogProcessor() {}

  public void addRandomRectangle() {
    int x = 100 + (int) Math.round(Math.random() * 900);
    int y = 100 + (int) Math.round(Math.random() * 500);
    RectangleShape rect = new RectangleShape(new Rectangle(x, y, 100, 200));
    rect.setFillColor(Color.GRAY);
    rect.setBorderColor(Color.GREEN);
    rect.setBorderSize(5);
    shapeList.add(rect);
  }

  public void addRandomOval() {
    int x = 100 + (int) Math.round(Math.random() * 900);
    int y = 100 + (int) Math.round(Math.random() * 500);
    OvalShape oval = new OvalShape(new Rectangle(x, y, 100, 200));
    oval.setFillColor(Color.GRAY);
    oval.setBorderColor(Color.GREEN);
    oval.setBorderSize(5);
    shapeList.add(oval);
  }

  public void addRandomEnhancedRectangle() {
    int x = 100 + (int) Math.round(Math.random() * 900);
    int y = 100 + (int) Math.round(Math.random() * 500);
    EnhancedRectangleShape enhancedRectangle = new EnhancedRectangleShape(new Rectangle(x, y, 100, 200));
    enhancedRectangle.setFillColor(Color.GRAY);
    enhancedRectangle.setBorderColor(Color.GREEN);
    enhancedRectangle.setBorderSize(5);
    shapeList.add(enhancedRectangle);
  }

  public void addRandomTriangle() {
    int x = 100 + (int) Math.round(Math.random() * 900);
    int y = 100 + (int) Math.round(Math.random() * 500);
    TriangleShape triangle = new TriangleShape(new Rectangle(x, y, 100, 200));
    triangle.setFillColor(Color.GRAY);
    triangle.setBorderColor(Color.GREEN);
    triangle.setBorderSize(5);
    shapeList.add(triangle);
  }

  public void rotateFigure(Shape figure) {
    figure.setRotate(true);
  }

  public void removeFigure(Shape figure) {
    shapeList.remove(figure);
  }

  public void increaseBorderSize(Shape figure) {
    figure.setBorderSize(figure.getBorderSize() + 1);
  }

  public void decreaseBorderSize(Shape figure) {
    int borderSize = figure.getBorderSize();
    if (borderSize > 0) {
      figure.setBorderSize(borderSize - 1);
    }
  }

  public Shape containsPoint(Point point) {
    for (int i = shapeList.size() - 1; i >= 0; i--) {
      if (shapeList.get(i).contains(point)) {
        Shape shape = shapeList.get(i);
        shape.setPreviousColor(shape.getFillColor());
        return shape;
      }
    }
    return null;
  }

  public void translateTo(Point p) {
    if (selection != null) {
      selection.setLocation(
          new Point(
              selection.getLocation().x + p.x - lastLocation.x,
              selection.getLocation().y + p.y - lastLocation.y));
      lastLocation = p;
    }
  }

  public Shape getSelection() {
    return selection;
  }

  public void setSelection(Shape value) {
    selection = value;
  }

  public boolean isDragging() {
    return isDragging;
  }

  public void setDragging(boolean value, Shape shape) {
    isDragging = value;
    shape.setFillColor(Color.RED);
  }

  public void setDragging(boolean value) {
    isDragging = value;
  }

  public Point getLastLocation() {
    return lastLocation;
  }

  public void setLastLocation(Point value) {
    lastLocation = value;
  }

  public void resetDragColor(Shape shape) {
    shape.setFillColor(shape.getPreviousColor());
  }

  public void fillFigureColor(Shape shape, Color color) {
    shape.setFillColor(color);
  }
}
