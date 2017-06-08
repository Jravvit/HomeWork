/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.core;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import hufs.ces.grimpan.shape.EditState;
import hufs.ces.grimpan.shape.GrimShape;

public class DrawPanelView 
	extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GrimPanController controller = null;
	private GrimPanModel model = null;
	private JPanel frm = new JPanel();
	private JPopupMenu popupMenu = null;
	private JMenuItem deleteItem = null;
	public MouseEvent mEvent = null;
	
	
	public DrawPanelView(final GrimPanController controller){
		this.controller = controller;
		this.model = GrimPanModel.getInstance();
		popupMenu = new JPopupMenu();
		deleteItem = new JMenuItem("delete");
		deleteItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			EditState tmpState = model.getEditState();
			model.setEditState(model.STATE_REMOVE);
			model.editState.performMouseReleased(mEvent);
			model.setEditState(tmpState);
			repaint();
			}
		});
		popupMenu.add(deleteItem);
		frm.add(popupMenu);
		
		DrawPanelMouseAdapter mouseAdapter = new DrawPanelMouseAdapter(model, this, popupMenu);
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (GrimShape grimShape:model.shapeList){
			grimShape.draw(g2);
		}

		if (model.curDrawShape != null){
			GrimShape curGrimShape = new GrimShape(model.curDrawShape, 
					model.getShapeStrokeWidth(), 
					model.getShapeStrokeColor(),
					model.getShapeFillColor());
			curGrimShape.draw(g2);
		}
		model.setShapeCount();
	}
}
