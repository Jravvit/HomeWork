/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.core;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * @author cskim
 *
 */
public class Util {

	public static final int MINPOLYDIST = 6;
	
	public static final String[] SHAPE_NAME = {
			"정다각형", "타원형", "다각형", "선분", "연필", "직사각형","이동","제거",
	};
	
	public static Path2D buildPath2DFromPoints(ArrayList<Point> points){
		Path2D result = new Path2D.Double();
		if (points != null && points.size() > 0) {
			Point p0 = points.get(0);
			result.moveTo((double)(p0.x), (double)(p0.y));
			for (int i=1; i<points.size(); ++i){
				p0 = points.get(i);
				result.lineTo((double)(p0.x), (double)(p0.y));
			}
		}
		
		return result;
	}
}
