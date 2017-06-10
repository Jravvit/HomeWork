/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.command;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.shape.GrimShape;

/**
 * @author cskim
 *
 */
public class DelCommand implements Command {

	GrimPanModel model = null;
	GrimShape grimShape = null;
	int removedGrimShape = 0;
	int removedRectShape = 0;
	
	
	public DelCommand(GrimPanModel model, GrimShape grimShape){
	
		this.model=model;
		this.grimShape=grimShape;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		removedGrimShape=model.getSelectedShapeIndex();
		model.setSavedPositionShape(model.shapeList.get(removedGrimShape));
		if(removedGrimShape!=-1)
			model.shapeList.remove(removedGrimShape);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		if(removedGrimShape!=-1)
		{
			//grimShape.setStrokeColor(model.getShapeStrokeColor());
			System.out.println(grimShape);
			model.shapeList.add(grimShape);
			model.setShapeCount();
		}
		
	}

}
