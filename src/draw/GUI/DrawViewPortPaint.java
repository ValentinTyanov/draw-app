package draw.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawViewPortPaint extends JPanel {

  private static final long serialVersionUID = -3449422514805385782L;
  private DrawView parent;

  public DrawViewPortPaint(DrawView view) {
    super();
    setLayout(new BorderLayout());

    parent = view;
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension layoutSize = super.getPreferredSize();
    int max = Math.max(layoutSize.width, layoutSize.height);
    return new Dimension(max + 100, max + 100);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    parent.getDialogProcessor().ReDraw(this, g);
  }
}
