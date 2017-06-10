package hufs.ces.grimpan.shape;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.Util;

public class RemoveBuilderState implements EditState {
	//public static final int STATE_TYPE = 0;
	GrimPanModel model = null;
	
	public RemoveBuilderState(GrimPanModel model){
		this.model = model;
	}
	@Override
	public int getStateType() {
		return EditState.EDIT_MOVE;
	}
	public void performMouseRightClicked(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);
		removeShape();
	}
	
	private void getSelectedShape(){
		int selIndex = -1;
		GrimShape shape = null;
		for (int i=model.shapeList.size()-1; i >= 0; --i){
			shape = model.shapeList.get(i);
			if (shape.contains(model.getMousePosition().getX(), model.getMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		model.setSelectedShapeIndex(selIndex);
	}
	
	private void removeShape() {
		try {
			int selIndex = -1;
			getSelectedShape();
			model.getController().removeShapeAction();
		} catch (Exception e){
		}
	}
	
	@Override
	public void performMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);
		getSelectedShape();
		model.getController().removeShapeAction();
		
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
