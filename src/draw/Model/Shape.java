package draw.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = OvalShape.class, name = "OvalShape"),
  @JsonSubTypes.Type(value = TriangleShape.class, name = "TriangleShape"),
  @JsonSubTypes.Type(value = TriangleShape.class, name = "ExamShape"),
  @JsonSubTypes.Type(value = RectangleShape.class, name = "RectangleShape")
})
public abstract class Shape {
  private Rectangle rectangle;

  private Color fillColor;

  private Color previousColor;

  private Color borderColor;

  private int borderSize;

  private boolean rotate;

  public int getBorderSize() {
    return borderSize;
  }

  public Shape() {}

  public Shape(Rectangle rect) {
    rectangle = rect;
  }

  public Shape(Shape shape) {
    this.setHeight(shape.getHeight());
    this.setLocation(shape.getLocation());
    this.rectangle = shape.rectangle;
    this.setWidth(shape.getWidth());
    this.setFillColor(shape.getFillColor());
    this.setBorderColor(shape.getBorderColor());
    this.setBorderSize(shape.getBorderSize());
  }

  public boolean contains(Point point) {
    return getRectangle().contains(point);
  }

  public void drawSelf(Graphics grfx) {
    if (isRotate()) {
      int oldWidth = rectangle.width;
      rectangle.width = rectangle.height;
      rectangle.height = oldWidth;
      setRotate(false);
    }
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public void setRectangle(Rectangle value) {
    rectangle = value;
  }

  public int getWidth() {
    return this.getRectangle().width;
  }

  public void setWidth(int value) {
    rectangle.width = value;
  }

  public int getHeight() {
    return this.getRectangle().height;
  }

  public void setHeight(int value) {
    rectangle.height = value;
  }

  public Point getLocation() {
    return this.getRectangle().getLocation();
  }

  public void setLocation(Point value) {
    rectangle.setLocation(value);
  }

  public Color getFillColor() {
    return fillColor;
  }

  public void setFillColor(Color value) {
    fillColor = value;
  }

  public Color getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }

  public void setBorderSize(int borderSize) {
    this.borderSize = borderSize;
  }

  public boolean isRotate() {
    return rotate;
  }

  public void setRotate(boolean isRotate) {
    this.rotate = isRotate;
  }

  public Color getPreviousColor() {
    return previousColor;
  }

  public void setPreviousColor(Color previousColor) {
    this.previousColor = previousColor;
  }
}
