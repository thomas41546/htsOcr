import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;


public class Spex {
    public static enum PointType { LINE_START, LINE_END, ARC_START,ARC_END};
    
    private PointType type;
    private Vector2d position; //relative to screen
    private double RelativeAngleToCenter;
    
    private boolean scanned;
    
    public Spex(Vector2d ipos, PointType itype){
    	this.position = ipos;
    	this.type = itype;
    	this.scanned = false;
    	this.RelativeAngleToCenter = 0.000;
    }
    
	public PointType getType() {
		return type;
	}
	public boolean scannedThis() {
		return scanned;
	}
	
	public void setScanned() {
		this.scanned = true;
	}
	
	public Vector2d getPosition() {
		return position;
	}
	public void setPosition(Vector2d pos) {
		this.position = pos;
	}

	public void setRelativeAngleToCenter(double relativeAngleToCenter) {
		RelativeAngleToCenter = relativeAngleToCenter;
	}

	public double getRelativeAngleToCenter() {
		return RelativeAngleToCenter;
	}
	

	
	public Vector2d getRotatedPosition(){
		double degrees = this.RelativeAngleToCenter;
		
		Vector2d point = new Vector2d(this.position);
	
		Vector2d origin = new Vector2d();
		origin.x = htsOcr.avg_x + htsOcr.SpiralAlignX;
		origin.y = htsOcr.avg_y + htsOcr.SpiralAlignY;
		
		double cosma = Math.cos(Math.toRadians(-degrees));
		double sinma = Math.sin(Math.toRadians(-degrees));
		double xs = ((cosma * (point.x-origin.x) - sinma * (point.y-origin.y)) + origin.x);
		double ys = ((sinma * (point.x-origin.x) + cosma * (point.y-origin.y)) + origin.y);
		return new Vector2d(xs,ys);
	}
	
}
