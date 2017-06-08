/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.shape;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import hufs.ces.grimpan.core.GrimPanController;
import hufs.ces.grimpan.core.GrimPanModel;

/**
 * @author cskim
 *
 */
public class PencilBuilderState implements EditState {

	//public static final int STATE_TYPE = 4;
	GrimPanModel model = null;
	
	public PencilBuilderState(GrimPanModel model){
		this.model = model;
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_PENCIL;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);
		model.setLastMousePosition(model.getMousePosition());				
		model.curDrawShape = new Path2D.Double();
		((Path2D)model.curDrawShape).moveTo((double)p1.x, (double)p1.y);

	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		((Path2D)model.curDrawShape).lineTo((double)p1.x, (double)p1.y);
		model.getController().addShapeAction();

	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);
		((Path2D)model.curDrawShape).lineTo((double)p1.x, (double)p1.y);

	}

}
