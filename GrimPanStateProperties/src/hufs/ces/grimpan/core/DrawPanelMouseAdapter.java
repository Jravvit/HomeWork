/**
 * Created on 2015. 4. 13.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.core;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * @author cskim
 *
 */
public class DrawPanelMouseAdapter extends MouseInputAdapter {

	private GrimPanModel model = null;
	private DrawPanelView drawView = null;
	private JPopupMenu popupMenu = null;
	
	public DrawPanelMouseAdapter(GrimPanModel model, DrawPanelView drawView, JPopupMenu popupMenu){
		this.model = model;
		this.drawView = drawView;
		this.popupMenu = popupMenu;
		
	}
	public void mousePressed(MouseEvent ev) {

		if (SwingUtilities.isLeftMouseButton(ev)){
			model.editState.performMousePressed(ev);
		}
		drawView.repaint();
	}

	public void mouseReleased(MouseEvent ev) {

		if (SwingUtilities.isLeftMouseButton(ev)){
			model.editState.performMouseReleased(ev);
		} else if (SwingUtilities.isRightMouseButton(ev)){
			popupMenu.show((Component) ev.getSource(),ev.getX(),ev.getY());
			drawView.mEvent = ev;
		}
		drawView.repaint();

	}

	public void mouseDragged(MouseEvent ev) {
		
		if (SwingUtilities.isLeftMouseButton(ev)){
			model.editState.performMouseDragged(ev);
		}
		drawView.repaint();

	}

}
