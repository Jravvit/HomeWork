/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * @author cskim
 *
 */
public class GrimShape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape = null;
	private float strokeWidth = 1f;
	private Color strokeColor = Color.black;
	private Paint grimPaint = null;
	/**
	 * 
	 */
	public GrimShape(Shape grimShape){
		this(grimShape, 1f, Color.black, null);
	}
	/**
	 * @param grimShape
	 * @param grimStrokeWidth
	 * @param grimStrokeColor
	 * @param grimFill
	 * @param grimFillColor
	 */
	public GrimShape(Shape grimShape, float strokeWidth, Color strokeColor, Paint grimPaint) {
		this.shape = grimShape;
		this.strokeWidth = strokeWidth;
		this.strokeColor = strokeColor;
		this.grimPaint = grimPaint;
	}
	public GrimShape(GrimShape grimShape){
		this.shape = grimShape.getShape();
		this.strokeWidth = grimShape.getStrokeWidth();
		this.strokeColor = grimShape.getStrokeColor();
		this.grimPaint = grimShape.getGrimPaint();
	}
	
	public GrimShape clone(){
		return new GrimShape(shape, strokeWidth, strokeColor, grimPaint);
	}
	public void draw(Graphics2D g2){
		if (grimPaint!=null){
			g2.setPaint(grimPaint);
			g2.fill(shape);
		}
		
		if (strokeColor!=null){
			g2.setStroke(new BasicStroke(this.strokeWidth));
			g2.setColor(strokeColor);
			g2.draw(shape);
		}
		
	}
	
	public void translate(float dx, float dy){
		AffineTransform tr = new AffineTransform();
		tr.setToTranslation(dx, dy);
		this.shape = tr.createTransformedShape(this.shape);
	}

	public boolean contains(double px, double py){
		return this.shape.contains(px, py);
	}

	/**
	 * @return the grimShape
	 */
	public Shape getShape() {
		return shape;
	}
	/**
	 * @param grimShape the grimShape to set
	 */
	public void setShape(Shape gpShape) {
		this.shape = gpShape;
	}
	/**
	 * @return the grimStrokeWidth
	 */
	public float getStrokeWidth() {
		return strokeWidth;
	}
	/**
	 * @param grimStrokeWidth the grimStrokeWidth to set
	 */
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	/**
	 * @return the grimStrokeColor
	 */
	public Color getStrokeColor() {
		return strokeColor;
	}
	/**
	 * @param grimStrokeColor the grimStrokeColor to set
	 */
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	public Paint getGrimPaint() {
		return grimPaint;
	}
	public void setGrimPaint(Paint grimPaint) {
		this.grimPaint = grimPaint;
	}
}