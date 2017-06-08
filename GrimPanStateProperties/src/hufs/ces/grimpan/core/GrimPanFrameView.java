/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.core;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import hufs.ces.grimpan.shape.GrimShape;

/**
 * @author cskim
 *
 */
public class GrimPanFrameView extends JFrame implements MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GrimPanController controller = null;
	private GrimPanModel model = null;
	private GrimPanFrameView thisClass = this;
	private DrawPanelView drawPanelView = null;

	private JPanel contentPane;

	public JCheckBoxMenuItem jcmiFill = null;
	JFileChooser jFileChooser1 = null;
	JFileChooser jFileChooser2 = null;

	JMenuBar panMenuBar;	
	JMenu fileMenu;
	JMenuItem jmiNew;
	JMenuItem jmiOpen;
	JMenuItem jmiSave;
	JMenuItem jmiSaveAs;
	JMenuItem jmiSaveAsSvg;
	JMenuItem jmiExit;
	JMenu shapeMenu;
	JRadioButtonMenuItem rdbtnmntmLine;
	JRadioButtonMenuItem rdbtnmntmPen;	
	JRadioButtonMenuItem rdbtnmntmPoly;
	JRadioButtonMenuItem rdbtnmntmRegular;
	JRadioButtonMenuItem rdbtnmntmOval;
	JMenu editMenu;	
	JMenuItem jmiRemove;
	JMenuItem jmiMove;
	JMenu settingMenu;
	JMenuItem jmiStroke;
	JMenuItem jmiStorkeColor;	
	JMenuItem jmiFillColor;

	JMenu helpMenu;	
	JMenuItem jmiAbout;

	ButtonGroup btnGroup = new ButtonGroup();
	JPanel statusPanel;
	JLabel shapeLbl;
	JLabel sizeLbl;
	JLabel countLbl;
	JMenuItem rmiAdd;
	JLabel messageLbl;

	public JLabel modeLbl;
	private JMenuItem jmiRecovery;
	private JMenuItem jmiUndo;

	Properties prop;

	/**
	 * Create the frame.
	 */
	public GrimPanFrameView(GrimPanController controller, GrimPanModel model) {
		super();
		this.controller = controller;
		this.model = model;
		this.model.setFrameView(this);

		initialize();
	}

	private void initialize(){
		prop = model.grimpanPM.getPanProperties();
		setTitle(prop.getProperty("title.label"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		
		System.out.println(prop.getProperty("title.label"));
		System.out.println(prop);
		
		System.out.println(prop.getProperty("title.label.file"));

		panMenuBar = new JMenuBar();
		setJMenuBar(panMenuBar);
		
		fileMenu = new JMenu(prop.getProperty("title.label.file"));
		panMenuBar.add(fileMenu);

		jmiNew = new JMenuItem(prop.getProperty("title.label.new"));
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clearAllShape();
			}
		});
		fileMenu.add(jmiNew);

		jmiOpen = new JMenuItem(prop.getProperty("title.label.open"));
		jmiOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.openAction();
			}
		});
		fileMenu.add(jmiOpen);

		jmiSave = new JMenuItem(prop.getProperty("title.label.save"));
		jmiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveAction();
			}
		});
		fileMenu.add(jmiSave);

		jmiSaveAs = new JMenuItem("Save  As...");
		jmiSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveAsAction();
			}
		});
		fileMenu.add(jmiSaveAs);

		jmiExit = new JMenuItem("Exit");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		jmiRecovery = new JMenuItem("Recover");
		jmiRecovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.recoveryAction();
			}
		});
		fileMenu.add(jmiRecovery);
		fileMenu.add(jmiExit);

		shapeMenu = new JMenu(prop.getProperty("menu.label.shape"));
		panMenuBar.add(shapeMenu);

		rdbtnmntmLine = new JRadioButtonMenuItem(prop.getProperty("menu.label.line"));
		rdbtnmntmLine.setSelected(true);
		rdbtnmntmLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(model.STATE_LINE);
				}
			}
		});
		shapeMenu.add(rdbtnmntmLine);

		rdbtnmntmPen = new JRadioButtonMenuItem(prop.getProperty("menu.label.pencil"));
		rdbtnmntmPen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(model.STATE_PENCIL);
				}
			}
		});
		shapeMenu.add(rdbtnmntmPen);

		rdbtnmntmPoly = new JRadioButtonMenuItem(prop.getProperty("menu.label.polygon"));
		rdbtnmntmPoly.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(model.STATE_POLYGON);
				}
			}
		});
		shapeMenu.add(rdbtnmntmPoly);

		rdbtnmntmRegular = new JRadioButtonMenuItem(prop.getProperty("menu.label.regular"));
		rdbtnmntmRegular.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(model.STATE_REGULAR);
					if (model.curDrawShape != null){
						model.shapeList
						.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
								model.getShapeStrokeColor(), model.getShapeFillColor()));
						model.curDrawShape = null;
					}
					Object[] possibleValues = { 
							"3", "4", "5", "6", "7",
							"8", "9", "10", "11", "12"
					};
					Object selectedValue = JOptionPane.showInputDialog(null,
							"Choose one", "Input",
							JOptionPane.INFORMATION_MESSAGE, null,
							possibleValues, possibleValues[0]);
					model.setNPolygon(Integer.parseInt((String)selectedValue));
					//drawPanelView.repaint();
				}
			}
		});
		shapeMenu.add(rdbtnmntmRegular);

		rdbtnmntmOval = new JRadioButtonMenuItem(prop.getProperty("menu.label.oval"));
		rdbtnmntmOval.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.setEditState(model.STATE_OVAL);
					if (model.curDrawShape != null){
						model.shapeList
						.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
								model.getShapeStrokeColor(), model.getShapeFillColor()));
						model.curDrawShape = null;
					}
				}
			}
		});
		shapeMenu.add(rdbtnmntmOval);

		btnGroup.add(rdbtnmntmLine);
		btnGroup.add(rdbtnmntmPen);
		btnGroup.add(rdbtnmntmPoly);
		btnGroup.add(rdbtnmntmRegular);
		btnGroup.add(rdbtnmntmOval);		

		editMenu = new JMenu("Edit  ");
		panMenuBar.add(editMenu);

		jmiUndo = new JMenuItem("Undo");
		jmiUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undoAction();
			}
		});
		editMenu.add(jmiUndo);

		rmiAdd = new JMenuItem("추가");
		rmiAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setAddShapeState();
			}
		});
		editMenu.add(rmiAdd);

		jmiRemove = new JMenuItem("제거");
		editMenu.add(jmiRemove);

		jmiMove = new JMenuItem("이동");
		jmiMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setMoveShapeState();
			}
		});
		editMenu.add(jmiMove);

		settingMenu = new JMenu("Setting");
		panMenuBar.add(settingMenu);

		jmiStroke = new JMenuItem("선두께");
		jmiStroke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setStrokeWithAction();
			}
		});
		settingMenu.add(jmiStroke);

		jmiStorkeColor = new JMenuItem("테두리색");
		jmiStorkeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setBoundaryColorAction();
			}
		});
		settingMenu.add(jmiStorkeColor);

		jcmiFill = new JCheckBoxMenuItem("채움");
		jcmiFill.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				model.setShapeFill(jcmiFill.getState());
			}
		});
		settingMenu.add(jcmiFill);

		jmiFillColor = new JMenuItem("채움색");
		jmiFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setFillColorAction();
			}
		});
		settingMenu.add(jmiFillColor);

		helpMenu = new JMenu("Help  ");
		panMenuBar.add(helpMenu);

		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.2.3 \nProgrammed by cskim, ces, hufs.ac.kr", 
						"About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		helpMenu.add(jmiAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		drawPanelView = new DrawPanelView(controller);
		drawPanelView.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				String sizeText = String.format("Size: %d x %d  ", 
						drawPanelView.getSize().width, drawPanelView.getSize().height);
				thisClass.sizeLbl.setText(sizeText);
			}
		});
		contentPane.add(drawPanelView, BorderLayout.CENTER);
		controller.setDrawPanelView(drawPanelView);

		statusPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) statusPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		statusPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		sizeLbl = new JLabel("Size:               ");
		statusPanel.add(sizeLbl);

		shapeLbl = new JLabel("Shape:              ");
		shapeLbl.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(shapeLbl);

		countLbl = new JLabel("Count:             ");
		countLbl.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(countLbl);
		
		modeLbl = new JLabel("Mode:               ");
		statusPanel.add(modeLbl);

		messageLbl = new JLabel("  ");
		statusPanel.add(messageLbl);

		jFileChooser1 = new JFileChooser(model.getDefaultDir());
		jFileChooser1.setDialogTitle("Open Saved GrimPan");

		jFileChooser2 = new JFileChooser(model.getDefaultDir());
		jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
		jFileChooser2.setDialogTitle("Save As ...");

		model.setEditState(model.STATE_LINE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		System.out.println("Sdfsdfsdf");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		System.out.println("Sdfsdfsdf");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		System.out.println("Sdfsdfsdf");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
