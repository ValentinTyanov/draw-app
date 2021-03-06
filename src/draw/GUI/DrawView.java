package draw.GUI;

import draw.Model.Shape;
import draw.Processors.DialogProcessor;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.imageio.ImageIO;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskMonitor;

public class DrawView extends FrameView {

  private JMenu editMenu;
  private JToggleButton drawToggleButton;
  private JToggleButton rotateFigure90Button;
  private JToggleButton yellowColorButton;
  private JToggleButton blueColorButton;
  private JToggleButton redColorButton;
  private JToggleButton removeButton;
  private JToggleButton increaseBorderSizeButton;
  private JToggleButton decreaseBorderSizeButton;
  private JButton drawRectangleButton;
  private JButton drawOvalButton;
  private JButton drawEnhancedRectangleButton;
  private JButton drawTriangleButton;;
  private JMenu imageMenu;
  private JMenuBar menuBar;
  private JProgressBar progressBar;
  private JLabel statusAnimationLabel;
  private JLabel statusMessageLabel;
  private JPanel statusPanel;
  private JToolBar toolBar;
  private JPanel viewPort;
  private JDialog aboutBox;

  private final Timer messageTimer;
  private final Timer busyIconTimer;
  private final Icon idleIcon;
  private final Icon[] busyIcons = new Icon[15];
  private int busyIconIndex = 0;

  private DialogProcessor dialogProcessor;

  public DialogProcessor getDialogProcessor() {
    return dialogProcessor;
  }

  public DrawView(SingleFrameApplication app) {
    super(app);
    initComponents();
    dialogProcessor = new DialogProcessor();

    DrawViewPortPaint drawViewPortPaint = new DrawViewPortPaint(this);
    setComponent(drawViewPortPaint);

    drawViewPortPaint.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent evt) {
            if (drawToggleButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Selection of a primitive");
                dialogProcessor.setDragging(true, shape);
                dialogProcessor.setLastLocation(evt.getPoint());
              }
            } else if (rotateFigure90Button.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Rotation of a primitive");
                dialogProcessor.rotateFigure(shape);
              }
            } else if (yellowColorButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Coloring a primitive");
                dialogProcessor.fillFigureColor(shape, Color.YELLOW);
              }
            } else if (blueColorButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Coloring a primitive");
                dialogProcessor.fillFigureColor(shape, Color.BLUE);
              }
            } else if (redColorButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Coloring a primitive");
                dialogProcessor.fillFigureColor(shape, Color.RED);
              }
            } else if (removeButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Removing a primitive");
                dialogProcessor.removeFigure(shape);
              }
            } else if (increaseBorderSizeButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Increase border size");
                dialogProcessor.increaseBorderSize(shape);
              }
            } else if (decreaseBorderSizeButton.isSelected()) {
              dialogProcessor.setSelection(dialogProcessor.containsPoint(evt.getPoint()));
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                statusMessageLabel.setText("Last action: Decrease border size");
                dialogProcessor.decreaseBorderSize(shape);
              }
            }
          }

          @Override
          public void mouseReleased(MouseEvent evt) {
            if (dialogProcessor.isDragging()) {
              Shape shape = dialogProcessor.getSelection();
              if (shape != null) {
                dialogProcessor.resetDragColor(shape);
              }
              dialogProcessor.setDragging(false);
            }
            dialogProcessor.repaint();
          }
        });

    drawViewPortPaint.addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent evt) {
            if (dialogProcessor.isDragging()) {
              if (dialogProcessor.getSelection() != null)
                statusMessageLabel.setText("Last action: Dragging");
              dialogProcessor.translateTo(evt.getPoint());
              dialogProcessor.repaint();
            }
          }
        });

    // status bar initialization - message timeout, idle icon and busy animation, etc
    ResourceMap resourceMap = getResourceMap();
    int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
    messageTimer =
        new Timer(
            messageTimeout,
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
              }
            });
    messageTimer.setRepeats(false);
    int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
    for (int i = 0; i < busyIcons.length; i++) {
      busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
    }
    busyIconTimer =
        new Timer(
            busyAnimationRate,
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
              }
            });
    idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
    statusAnimationLabel.setIcon(idleIcon);
    progressBar.setVisible(false);

    ImageIcon icon = resourceMap.getImageIcon("DrawIcon");
    getFrame().setIconImage(icon.getImage());

    // connecting action tasks to status bar via TaskMonitor
    TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
    taskMonitor.addPropertyChangeListener(
        new PropertyChangeListener() {

          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if ("started".equals(propertyName)) {
              if (!busyIconTimer.isRunning()) {
                statusAnimationLabel.setIcon(busyIcons[0]);
                busyIconIndex = 0;
                busyIconTimer.start();
              }
              progressBar.setVisible(true);
              progressBar.setIndeterminate(true);
            } else if ("done".equals(propertyName)) {
              busyIconTimer.stop();
              statusAnimationLabel.setIcon(idleIcon);
              progressBar.setVisible(false);
              progressBar.setValue(0);
            } else if ("message".equals(propertyName)) {
              String text = (String) (evt.getNewValue());
              statusMessageLabel.setText((text == null) ? "" : text);
              messageTimer.restart();
            } else if ("progress".equals(propertyName)) {
              int value = (Integer) (evt.getNewValue());
              progressBar.setVisible(true);
              progressBar.setIndeterminate(false);
              progressBar.setValue(value);
            }
          }
        });
  }

  /** Показва диалогова кутия с информация за програмата. */
  @Action
  public void showAboutBox() {
    if (aboutBox == null) {
      JFrame mainFrame = DrawApp.getApplication().getMainFrame();
      aboutBox = new DrawAboutBox(mainFrame);
      aboutBox.setLocationRelativeTo(mainFrame);
    }
    DrawApp.getApplication().show(aboutBox);
  }

  private void initComponents() {
    menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu();
    JMenuItem exitMenuItem = new JMenuItem();
    editMenu = new JMenu();
    imageMenu = new JMenu();
    JMenu helpMenu = new JMenu();
    JMenuItem aboutMenuItem = new JMenuItem();
    statusPanel = new JPanel();
    JSeparator statusPanelSeparator = new JSeparator();
    statusMessageLabel = new JLabel();
    statusAnimationLabel = new JLabel();
    progressBar = new JProgressBar();
    toolBar = new JToolBar();
    drawRectangleButton = new JButton();
    drawOvalButton = new JButton();
    drawEnhancedRectangleButton = new JButton();
    drawTriangleButton = new JButton();
    rotateFigure90Button = new JToggleButton();
    drawToggleButton = new JToggleButton();
    yellowColorButton = new JToggleButton();
    blueColorButton = new JToggleButton();
    redColorButton = new JToggleButton();
    removeButton = new JToggleButton();
    increaseBorderSizeButton = new JToggleButton();
    decreaseBorderSizeButton = new JToggleButton();
    viewPort = new JPanel();

    menuBar.setName("menuBar");

    ResourceMap resourceMap =
        Application.getInstance(DrawApp.class).getContext().getResourceMap(DrawView.class);
    fileMenu.setText(resourceMap.getString("fileMenu.text"));
    fileMenu.setName("fileMenu");

    ActionMap actionMap =
        Application.getInstance(DrawApp.class).getContext().getActionMap(DrawView.class, this);
    exitMenuItem.setAction(actionMap.get("quit"));
    exitMenuItem.setName("exitMenuItem");
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    editMenu.setText(resourceMap.getString("editMenu.text"));
    editMenu.setName("editMenu");
    menuBar.add(editMenu);

    imageMenu.setText(resourceMap.getString("imageMenu.text"));
    imageMenu.setName("imageMenu");
    menuBar.add(imageMenu);

    helpMenu.setText(resourceMap.getString("helpMenu.text"));
    helpMenu.setName("helpMenu");

    aboutMenuItem.setAction(actionMap.get("showAboutBox"));
    aboutMenuItem.setName("aboutMenuItem");
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    statusPanel.setName("statusPanel");

    statusPanelSeparator.setName("statusPanelSeparator");

    statusMessageLabel.setName("statusMessageLabel");

    statusAnimationLabel.setHorizontalAlignment(SwingConstants.LEFT);
    statusAnimationLabel.setName("statusAnimationLabel");

    progressBar.setName("progressBar");

    GroupLayout statusPanelLayout = new GroupLayout(statusPanel);
    statusPanel.setLayout(statusPanelLayout);
    statusPanelLayout.setHorizontalGroup(
        statusPanelLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
            .addGroup(
                statusPanelLayout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(statusMessageLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
                    .addComponent(
                        progressBar,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(statusAnimationLabel)
                    .addContainerGap()));
    statusPanelLayout.setVerticalGroup(
        statusPanelLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                statusPanelLayout
                    .createSequentialGroup()
                    .addComponent(
                        statusPanelSeparator,
                        GroupLayout.PREFERRED_SIZE,
                        2,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        LayoutStyle.ComponentPlacement.RELATED,
                        GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addGroup(
                        statusPanelLayout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(statusMessageLabel)
                            .addComponent(statusAnimationLabel)
                            .addComponent(
                                progressBar,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addGap(3, 3, 3)));

    toolBar.setRollover(true);
    toolBar.setName("toolBar");

    Image img;
    try {
      img = ImageIO.read(getClass().getResource("resources/OvalTool.png"));
      drawOvalButton.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/RectangleTool.png"));
      drawRectangleButton.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/TriangleTool.png"));
      drawTriangleButton.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/RotateTool.png"));
      rotateFigure90Button.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/YellowColor.png"));
      yellowColorButton.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/BlueColor.png"));
      blueColorButton.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/RedColor.png"));
      redColorButton.setIcon(new ImageIcon(img));
      img = ImageIO.read(getClass().getResource("resources/RemoveFigure.png"));
      removeButton.setIcon(new ImageIcon(img));
    } catch (Exception e) {
      e.printStackTrace();
    }

    drawRectangleButton.addActionListener(new AddRectangle());
    drawRectangleButton.setFocusable(false);
    drawRectangleButton.setHorizontalTextPosition(SwingConstants.CENTER);
    drawRectangleButton.setName("DrawRectangleButton");
    drawRectangleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(drawRectangleButton);

    drawOvalButton.addActionListener(new AddOval());
    drawOvalButton.setFocusable(false);
    drawOvalButton.setHorizontalTextPosition(SwingConstants.CENTER);
    drawOvalButton.setName("DrawOvalButton");
    drawOvalButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(drawOvalButton);

    drawEnhancedRectangleButton.setText("Enhanced Rectangle");
    drawEnhancedRectangleButton.addActionListener(new AddEnhancedRectangleShape());
    drawEnhancedRectangleButton.setFocusable(false);
    drawEnhancedRectangleButton.setHorizontalTextPosition(SwingConstants.CENTER);
    drawEnhancedRectangleButton.setName("DrawEnhancedRectangleButton");
    drawEnhancedRectangleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(drawEnhancedRectangleButton);

    drawTriangleButton.addActionListener(new AddTriangle());
    drawTriangleButton.setFocusable(false);
    drawTriangleButton.setHorizontalTextPosition(SwingConstants.CENTER);
    drawTriangleButton.setName("DrawTriangleButton");
    drawTriangleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(drawTriangleButton);

    rotateFigure90Button.setFocusable(false);
    rotateFigure90Button.setHorizontalTextPosition(SwingConstants.CENTER);
    rotateFigure90Button.setName("RotateFigure90Button");
    rotateFigure90Button.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(rotateFigure90Button);

    drawToggleButton.setIcon(resourceMap.getIcon("DragToggleButton.icon"));
    drawToggleButton.setFocusable(false);
    drawToggleButton.setHorizontalTextPosition(SwingConstants.CENTER);
    drawToggleButton.setName("DragToggleButton");
    drawToggleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(drawToggleButton);

    yellowColorButton.setFocusable(false);
    yellowColorButton.setHorizontalTextPosition(SwingConstants.CENTER);
    yellowColorButton.setName("Yellow");
    yellowColorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(yellowColorButton);

    blueColorButton.setFocusable(false);
    blueColorButton.setHorizontalTextPosition(SwingConstants.CENTER);
    blueColorButton.setName("Blue");
    blueColorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(blueColorButton);

    redColorButton.setFocusable(false);
    redColorButton.setHorizontalTextPosition(SwingConstants.CENTER);
    redColorButton.setName("Red");
    redColorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(redColorButton);

    removeButton.setFocusable(false);
    removeButton.setHorizontalTextPosition(SwingConstants.CENTER);
    removeButton.setName("Remove");
    removeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(removeButton);

    increaseBorderSizeButton.setFocusable(false);
    increaseBorderSizeButton.setText("Increase Border Size");
    increaseBorderSizeButton.setHorizontalTextPosition(SwingConstants.CENTER);
    increaseBorderSizeButton.setName("Increase Border Size");
    increaseBorderSizeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(increaseBorderSizeButton);

    decreaseBorderSizeButton.setFocusable(false);
    decreaseBorderSizeButton.setText("Decrease Border Size");
    decreaseBorderSizeButton.setHorizontalTextPosition(SwingConstants.CENTER);
    decreaseBorderSizeButton.setName("Decrease Border Size");
    decreaseBorderSizeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    toolBar.add(decreaseBorderSizeButton);

    viewPort.setName("viewPort");

    GroupLayout viewPortLayout = new GroupLayout(viewPort);
    viewPort.setLayout(viewPortLayout);
    viewPortLayout.setHorizontalGroup(
        viewPortLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE));
    viewPortLayout.setVerticalGroup(
        viewPortLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE));

    setComponent(viewPort);
    setMenuBar(menuBar);
    setStatusBar(statusPanel);
    setToolBar(toolBar);
  }

  class AddOval implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      dialogProcessor.addRandomOval();
      statusMessageLabel.setText("Last action: Drawing an oval");
      dialogProcessor.repaint();
    }
  }

  class AddEnhancedRectangleShape implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      dialogProcessor.addRandomEnhancedRectangle();
      statusMessageLabel.setText("Last action: Drawing an enhanced rectangle");
      dialogProcessor.repaint();
    }
  }

  class AddRectangle implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      dialogProcessor.addRandomRectangle();
      statusMessageLabel.setText("Last action: Drawing a rectangle");
      dialogProcessor.repaint();
    }
  }

  class AddTriangle implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      dialogProcessor.addRandomTriangle();
      statusMessageLabel.setText("Last action: Drawing a triangle");
      dialogProcessor.repaint();
    }
  }
}
