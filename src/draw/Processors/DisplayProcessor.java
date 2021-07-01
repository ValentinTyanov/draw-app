package draw.Processors;

import draw.GUI.DrawApp;
import draw.Model.Shape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

public class DisplayProcessor {
  public Vector<Shape> shapeList = new Vector<Shape>();

  public DisplayProcessor() {}

  public void ReDraw(Object sender, Graphics grfx) {
    Graphics2D grfx2 = (Graphics2D) grfx;
    grfx2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    draw(grfx);
  }

  public void draw(Graphics grfx) {
    for (int i = 0; i < shapeList.size(); i++) {
      drawShape(grfx, shapeList.get(i));
    }
  }

  public void drawShape(Graphics grfx, Shape item) {
    item.drawSelf(grfx);
  }

  public Vector<Shape> getShapeList() {
    return shapeList;
  }

  public void setShapeList(Vector<Shape> value) {
    shapeList = value;
  }

  public void repaint() {
    DrawApp.getApplication().getMainView().getComponent().repaint();
  }
}
