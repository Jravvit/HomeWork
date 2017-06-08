/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.shape;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import hufs.ces.grimpan.core.GrimPanController;
import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.Util;

/**
 * @author cskim
 *
 */
public class OvalBuilderState implements EditState {

	//public static final int STATE_TYPE = 1;
	GrimPanModel model = null;
	
	public OvalBuilderState(GrimPanModel model){
		this.model = model;
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_OVAL;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		genEllipse2D();
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		genEllipse2D();
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

		genEllipse2D();
	}
	
	private void genEllipse2D(){
		Point pi = model.getMousePosition();
		Point topleft = model.getClickedMousePosition();
		if (pi.distance(new Point2D.Double(topleft.x, topleft.y)) <= Util.MINPOLYDIST)
			return;
		Ellipse2D oval = new Ellipse2D.Double(
				topleft.x, topleft.y,
				pi.x-topleft.x, pi.y-topleft.y);
		model.curDrawShape = oval;
	}

}
