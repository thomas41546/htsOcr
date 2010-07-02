import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Comparator;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;


public class Character implements Comparable<Character> {
	
	private int pixelMapOffsetx = 0; //relative entire screen 0,0 to pixels[] map
	private int pixelMapOffsety = 0;
	
	private int avgPixelx = 0; //relative to screen
	private int avgPixely = 0;
	
	private int pixelCount = 0;
	
	private int rotation = -1;
	
	private int distanceFromOrigin = 0;
	
	private int letterPosition=0;
	
	private int ID;
	

	private int[] pixelMap;
	private int[] pixelMapROTATED;
	
	public  org.apache.pivot.collections.ArrayList<Spex> CharSpex = new org.apache.pivot.collections.ArrayList<Spex>(); //relative to screen
	
	public int SpexTL =0;
	public int SpexTR =0;
	public int SpexBL =0;
	public int SpexBR =0;
	
	
	
	public int DEBUG1 =0;
	public int DEBUG2 =0;
	
	public Character(){
		this.pixelMapOffsetx = 0;
		this.pixelMapOffsety = 0;
		this.pixelMap = null;
		this.ID = 0;
	}
	
	
	public void POLL(){
		
		System.out.printf("Pixel count: %d\n",this.pixelCount);
		
	}
	public int getPixelCount(){
		return this.pixelCount;
	}

	

	/*
	public void transversePixelMap(int ox, int oy){ //XXX investigate
		int[] newPixelMap = new int[htsOcr.CropSize*htsOcr.CropSize];
		
        for(int y=0 ;y < htsOcr.CropSize; y++){
        	for(int x=0 ;x < htsOcr.CropSize; x++){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		
            		if(((x+ox)+(y+oy)*htsOcr.CropSize) >= 0 && ((x+ox)+(y+oy)*htsOcr.CropSize) < htsOcr.CropSize*htsOcr.CropSize)
            			newPixelMap[((x+ox)+(y+oy)*htsOcr.CropSize)] = pixelMap[(x+y*htsOcr.CropSize)];
            		else
            		{
            			System.err.print("Error: Transversing Out of Boundary \n");
            		}
            	}
            }
        }
        for(int i=0 ;i < htsOcr.CropSize*htsOcr.CropSize; i++){
        	this.pixelMap[i] = newPixelMap[i];
        }
        
        
	}
	public void alignTopLeftPixelMap(){
        for(int y=0 ;y < 9; y++){
        	for(int x=0 ;x < 12; x++){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		transversePixelMap(12-x,9-x); //correct i believe
            		return;
            	}
            }
        }
	}
	public void alignTopRightPixelMap(){
        for(int y=0 ;y < 9; y++){
        	for(int x=35 ;x >= 24; x--){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		transversePixelMap(23-x,9-y); //should be right
            		return;
            	}
            }
        }
	}
	public void alignBottomLeftPixelMap(){
        for(int y=35 ;y <= 27; y--){
        	for(int x=0 ;x < 12; x++){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		transversePixelMap(12-x,26-y);//should be right
            		return;
            	}
            }
        }
	}
	public void alignBottomRightPixelMap(){
        for(int y=35 ;y <= 27; y--){
        	for(int x=35 ;x <= 24; x--){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		transversePixelMap(23-x,26-y);//should be right
            		return;
            	}
            }
        }
	}*/
	
	public Character(int ox, int oy, int[] pixels){
		this.pixelMapOffsetx = ox;
		this.pixelMapOffsety = oy;
		this.pixelMap = pixels;
		this.ID = (int) (Math.random() % 1000000000 +1);
		
		
        double xxsum=0;
        double yysum=0;
        double ccsum=0;
        for(int y=0 ;y < htsOcr.CropSize; y++){
        	for(int x=0 ;x < htsOcr.CropSize; x++){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		xxsum += x;
            		yysum += y;
            		ccsum += 1;
            	}
            }
        }
        this.avgPixelx = (int)(pixelMapOffsetx + (xxsum/ccsum) );
        this.avgPixely = (int)(pixelMapOffsety + (yysum/ccsum) );
        this.pixelCount = (int) ccsum;
        
       
        
       //XXX completely UNtested
       /*
        this.alignTopLeftPixelMap();
        this.alignTopRightPixelMap();
        this.alignBottomLeftPixelMap();
        this.alignBottomRightPixelMap();*/
	}
	
	public Vector2d pixelRotation(Vector2d vector2d, double degrees){
		double cosma = Math.cos(Math.toRadians(-degrees));
		double sinma = Math.sin(Math.toRadians(-degrees));
		int hwidth = htsOcr.CropSize/2;
		int hheight = htsOcr.CropSize/2;
		
		vector2d.sub(new Vector2d(hwidth,hheight));
		
		int xs = (int)((cosma * vector2d.x - sinma * vector2d.y) + hwidth);
		int ys = (int)((sinma * vector2d.x + cosma * vector2d.y) + hheight);
		
		return new Vector2d(xs,ys);
	}
	
	public Vector2d pixelRotationOrigin(Vector2d origin,Vector2d point, double degrees){
		double cosma = Math.cos(Math.toRadians(-degrees));
		double sinma = Math.sin(Math.toRadians(-degrees));
		double xs = ((cosma * (point.x-origin.x) - sinma * (point.y-origin.y)) + origin.x);
		double ys = ((sinma * (point.x-origin.x) + cosma * (point.y-origin.y)) + origin.y);
		return new Vector2d(xs,ys);
	}
	


	
	public void generateRotatedMap(){
		pixelMapROTATED = new int[htsOcr.CropSize*htsOcr.CropSize];
        for(int y=0 ;y < htsOcr.CropSize; y++){
        	for(int x=0 ;x < htsOcr.CropSize; x++){
            	if(pixelMap[(x+y*htsOcr.CropSize)] != 0){
            		
            		Vector2d rotatedPixel = pixelRotation(new Vector2d(x,y),this.getRotation());
            		
            		pixelMapROTATED[(int) (rotatedPixel.x + rotatedPixel.y*htsOcr.CropSize)] = pixelMap[(x+y*htsOcr.CropSize)];
            	}
            }
        }
	}
	public int[] getPixelMapROTATED() {
		return this.pixelMapROTATED;
	}
	public byte[] getPixelMapROTATEDByte() {
		byte[] bytebuffer = new byte[this.pixelMapROTATED.length];
		
		for(int i=0;i<this.pixelMapROTATED.length; i++){
			if(this.pixelMapROTATED[i] != 0)
				bytebuffer[i] = 0;
			else{
				bytebuffer[i] = (byte) 256*256-1;
			}
		}
		
		return bytebuffer;
	}
	
	public void generateSpexRotation(){
		for(int i=0; i < this.CharSpex.getLength(); i++){
			this.CharSpex.get(i).setRelativeAngleToCenter(this.getRotation());
		}
	}
	public double loopCrop(double number,double lower, double upper){ //[lower,upper) XXX untested
		while(number < lower){
			number += (upper-lower);
		}
		while(number >= upper){
			number -= (upper-lower);
		}
		return number;
	}
	
	public double getDistance(Vector2d point1,Vector2d point2) {
		Vector2d temp = new Vector2d(point1);
		temp.sub(point2);
		return temp.lengthSquared();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
 * 
 	Comparator<Character> distanceComparator = new Comparator<Character>() {

		@Override
		public int compare(Character o1, Character o2) {
			return o1.getDistanceFromOrigin()
					- o2.getDistanceFromOrigin();
		}

	};

	org.apache.pivot.collections.ArrayList.sort(CharBuffer,
			distanceComparator);+
 */

	/*Vector2d topArc = new Vector2d(this.CharSpex.get(0).getPosition());
	Vector2d bottomArc = new Vector2d(this.CharSpex.get(1).getPosition());
	
	if(topArc.y > bottomArc.y){ //NOTE y-axis is large at bottom.
		 Vector2d swap = new Vector2d(topArc);
		 topArc = new Vector2d(bottomArc);
	     bottomArc  = new Vector2d(swap);
	}
	Vector2d diff = new Vector2d(bottomArc);
	diff.sub(topArc);
	diff.normalize();
	
	double angle = Math.toDegrees(Math.atan(diff.y/diff.x));
	//angle = loopCrop(angle,-90,180);
	*/
	
	
	
	public String getSpexCharacter(){
		org.apache.pivot.collections.ArrayList<Spex> SpexPool = new org.apache.pivot.collections.ArrayList<Spex>();
		org.apache.pivot.collections.ArrayList<Spex> ArcPool = new org.apache.pivot.collections.ArrayList<Spex>();
		org.apache.pivot.collections.ArrayList<Spex> LinePool = new org.apache.pivot.collections.ArrayList<Spex>();
		String character = "";
		
	 	Comparator<Spex> spexSortHeight = new Comparator<Spex>() { //top to bottom sort .. left to right
			public int compare(Spex o1, Spex o2) {
				
				if (Math.abs(o1.getPosition().y - o2.getPosition().y) < 3.0){
					return (int)(o1.getPosition().x - o2.getPosition().x);
				}
				
				return (int)(o1.getPosition().y - o2.getPosition().y);
			}
		};
		
		class SpexGroup{
			public int index = 0; //height index
			public org.apache.pivot.collections.ArrayList<Spex> BoundedSpex = new org.apache.pivot.collections.ArrayList<Spex>();
			public org.apache.pivot.collections.ArrayList<Spex> CollsionSpex = new org.apache.pivot.collections.ArrayList<Spex>();
			public int collisionMeter = 0; //200 = 2 arc coll, 202, 2 arc & 2 line..... 004, 4 line
		}
		
		
		for(int i=0; i < this.CharSpex.getLength(); i++){
			SpexPool.add(new Spex(this.CharSpex.get(i).getRotatedPosition(),this.CharSpex.get(i).getType()));
			if(this.CharSpex.get(i).getType() == Spex.PointType.LINE_END || this.CharSpex.get(i).getType() == Spex.PointType.LINE_START){
				LinePool.add(new Spex(this.CharSpex.get(i).getRotatedPosition(),this.CharSpex.get(i).getType()));
			}
			else{
				ArcPool.add(new Spex(this.CharSpex.get(i).getRotatedPosition(),this.CharSpex.get(i).getType()));
			}
		}
		


		org.apache.pivot.collections.ArrayList.sort(SpexPool,spexSortHeight);
		org.apache.pivot.collections.ArrayList.sort(ArcPool,spexSortHeight);
		org.apache.pivot.collections.ArrayList.sort(LinePool,spexSortHeight);
		
		org.apache.pivot.collections.ArrayList<SpexGroup> SpexGroups = new org.apache.pivot.collections.ArrayList<SpexGroup>();
		double pivot = -1;
		int spexGroupIndex = 0;
		for(int i=0; i < SpexPool.getLength(); i++){
			if(pivot == -1){
				SpexGroups.add(new SpexGroup());
				SpexGroups.get(spexGroupIndex).index = spexGroupIndex;
				SpexGroups.get(spexGroupIndex).BoundedSpex.add(SpexPool.get(i));
				pivot = (double)SpexPool.get(i).getPosition().y;
				continue;
			}
			if(Math.abs(pivot - SpexPool.get(i).getPosition().y) < 4.2){ //y pixels
				SpexGroups.get(spexGroupIndex).BoundedSpex.add(SpexPool.get(i));
			}
			else{
				spexGroupIndex++;
				SpexGroups.add(new SpexGroup());
				SpexGroups.get(spexGroupIndex).index = spexGroupIndex;
				SpexGroups.get(spexGroupIndex).BoundedSpex.add(SpexPool.get(i));
				pivot = (double)SpexPool.get(i).getPosition().y;
			}
		}
		//calculate collision spots
		for(int i=0; i < SpexGroups.getLength(); i++){
			for(int x=0; x < SpexGroups.get(i).BoundedSpex.getLength(); x++){
				for(int y=0; y < SpexGroups.get(i).BoundedSpex.getLength(); y++){
					if(x == y)
						continue;
					
					double dist = getDistance(SpexGroups.get(i).BoundedSpex.get(x).getPosition(),SpexGroups.get(i).BoundedSpex.get(y).getPosition());
					if(dist < 3.0){//XXX calibrate
						if(SpexGroups.get(i).CollsionSpex.indexOf(SpexGroups.get(i).BoundedSpex.get(x)) == -1){
							SpexGroups.get(i).CollsionSpex.add(SpexGroups.get(i).BoundedSpex.get(x));
							if(SpexGroups.get(i).BoundedSpex.get(x).getType() == Spex.PointType.ARC_START || 
								SpexGroups.get(i).BoundedSpex.get(x).getType() == Spex.PointType.ARC_END){
								SpexGroups.get(i).collisionMeter += 100;
							}
							else{
								SpexGroups.get(i).collisionMeter += 1;
							}
							break;
						}
					}
				}
			}
		}
		switch(SpexPool.getLength()){
			case 2:
				if(ArcPool.getLength() == 2){ //C
					character = "C";
					break;
				}
				break;
				
			case 4:
				if(ArcPool.getLength() == 4){ //8
					character = "8";
					break;
				}
				if(ArcPool.getLength() == 2 && LinePool.getLength() == 2){
					double arcy = ArcPool.get(0).getPosition().y;
					
					boolean lower = true; //heigher on screen
					for(int v=0; v < LinePool.getLength(); v++){
						if(LinePool.get(v).getPosition().y < arcy){
							lower = false;
							break;
						}
					}
					if(lower){
						character = "9";
						break;
					}
					else{
						character = "6";
						break;
					}
				}
				break;
				
				
			case 6:
				if(LinePool.getLength() == 6){ //MANY
					
					if(SpexGroups.getLength() >= 2 && SpexGroups.get(1).collisionMeter == 2){ //middle element has two-line collision
						character = "4";
						break;
					}
					
					//may need work done
					Vector2d v1;
					double rot;
					v1 = new Vector2d(LinePool.get(0).getPosition());
					v1.sub(LinePool.get(LinePool.getLength()-1).getPosition()); //5
					rot = Math.toDegrees(Math.atan(v1.y/v1.x));
					if(rot > 60 && rot < 75){
						character = "A";
						break;
					}
					if(rot < -85 || rot > 85){
						character = "F";
						break;
					}
					
					v1 = new Vector2d(LinePool.get(0).getPosition());
					v1.sub(LinePool.get(2).getPosition()); //2
					rot = Math.toDegrees(Math.atan(v1.y/v1.x));
					if(rot < -40 && rot > -65){
						character = "1";
						break;
					}
					
					v1 = new Vector2d(LinePool.get(0).getPosition());
					v1.sub(LinePool.get(LinePool.getLength()-1).getPosition()); //5
					rot = Math.toDegrees(Math.atan(v1.y/v1.x));
					if((rot > 75 && rot < 85)){
						character = "7";
						break;
					}
					
				}
				if(LinePool.getLength() == 4 && ArcPool.getLength() == 2){
					if(SpexGroups.getLength() >= 2 && SpexGroups.get(0).collisionMeter == 101
						&& SpexGroups.getLength() >= 2 && SpexGroups.get(1).collisionMeter == 2){
						character = "2";
						break;
					}
				}
				if(LinePool.getLength() == 2 && ArcPool.getLength() == 4){
					character = "3";
					break;
				}
				

				
				
				break;
				
				
			case 8:
				if(ArcPool.getLength() == 0){
					character = "E";
					break;
				}
				if(ArcPool.getLength() == 4 && LinePool.getLength() == 4){
					character = "0";
					break;
				}
				
				if(ArcPool.getLength() == 2 && LinePool.getLength() == 6){
					
					if(SpexGroups.getLength() == 2){
							if(Math.abs(ArcPool.get(0).getPosition().y - ArcPool.get(1).getPosition().y) > 5){
								character = "D";
								break;
							}
					}
					
					if(SpexGroups.getLength() >= 2){
						int collisionMeterSum=0;
						for(int z=0; z < SpexGroups.getLength(); z++){
							collisionMeterSum += SpexGroups.get(z).collisionMeter;
						}
						if(collisionMeterSum == 105){
							character = "5";
							break;
						}
						
						
					}

					break;
				}
				break;
				
				
				
			case 10:
				if(ArcPool.getLength() == 0){
					character = "E";
					break;
				}
				if(ArcPool.getLength() == 4){
					character = "B"; //IMPROVE
					break;
				}

				break;
				
			case 12:
				if(ArcPool.getLength() == 4){
					character = "B";
					break;
				}
				break;
		
		
		}
		if(!character.equals("")){
			//System.out.printf("%s\n", character);
		}
		
		
		
		
		
		return character;
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	/* assuming width and height are integers with the image's dimensions */

	public void generateSpexMetrics(){ ///XXX FAILS, remove this an implement....
		/*
		 * Split the Character into 4 sub boundaries, determine which spex is in each boundary.
		 * 
		 * 
		 */
		
		//Parallel To Character Rotations
		double centerX = this.avgPixelx;
		double centerY = this.avgPixely;
		
		double cosma_ll =  (Math.cos(Math.toRadians(this.rotation-90))*15.0);
		double sinma_ll =  (Math.sin(Math.toRadians(this.rotation-90))*15.0);
		
		
		double cosma_T = (Math.cos(Math.toRadians(this.rotation))*15.0);
		double sinma_T = (Math.sin(Math.toRadians(this.rotation))*15.0);
		
		Polygon poly_TR = new Polygon();
		Polygon poly_BR = new Polygon();
		
		Polygon poly_TL = new Polygon();
		Polygon poly_BL = new Polygon();
		
		poly_TR.addPoint((int) centerX, (int) centerY);
		poly_TR.addPoint((int) (centerX + cosma_ll), (int) (centerY + sinma_ll));
		poly_TR.addPoint((int) (centerX + cosma_T), (int) (centerY + sinma_T));
		//g.fillPolygon(poly_TR);
		
		
		poly_BR.addPoint((int) centerX, (int) centerY);
		poly_BR.addPoint((int) (centerX - cosma_ll), (int) (centerY - sinma_ll));
		poly_BR.addPoint((int) (centerX + cosma_T), (int) (centerY + sinma_T));
		//g.fillPolygon(poly_BR);
		
		poly_TL.addPoint((int) centerX, (int) centerY);
		poly_TL.addPoint((int) (centerX + cosma_ll), (int) (centerY + sinma_ll));
		poly_TL.addPoint((int) (centerX - cosma_T), (int) (centerY - sinma_T));
		//g.fillPolygon(poly_TL);
		
		poly_BL.addPoint((int) centerX, (int) centerY);
		poly_BL.addPoint((int) (centerX - cosma_ll), (int) (centerY - sinma_ll));
		poly_BL.addPoint((int) (centerX - cosma_T), (int) (centerY - sinma_T));
		//g.fillPolygon(poly_BL);
		
		
		for(int i=0; i < this.CharSpex.getLength(); i++){
			int multiplier = 1;
			if(this.CharSpex.get(i).getType() == Spex.PointType.LINE_START ||
				this.CharSpex.get(i).getType() == Spex.PointType.LINE_END){
				multiplier = 1;
			}
			else if (this.CharSpex.get(i).getType() == Spex.PointType.ARC_START || 
					this.CharSpex.get(i).getType() == Spex.PointType.ARC_END){
				multiplier = 100;
			}
			
			if(poly_TR.contains(this.CharSpex.get(i).getPosition().x, this.CharSpex.get(i).getPosition().y)){
				this.SpexTR+=multiplier;
					
			}
			else if(poly_TL.contains(this.CharSpex.get(i).getPosition().x, this.CharSpex.get(i).getPosition().y)){
				this.SpexTL+=multiplier;
			}
			else if(poly_BL.contains(this.CharSpex.get(i).getPosition().x, this.CharSpex.get(i).getPosition().y)){
				this.SpexBL+=multiplier;
			}
			else if(poly_BR.contains(this.CharSpex.get(i).getPosition().x, this.CharSpex.get(i).getPosition().y)){
				this.SpexBR+=multiplier;
			}
		}
	}
	
	public int getSpexTL(){
		return this.SpexTL;
	}
	public int getSpexTR(){
		return this.SpexTR;
	}
	
	public int getSpexBL(){
		return this.SpexBL;
	}
	
	public int getSpexBR(){
		return this.SpexBR;
	}
	

	public int getPixelMapOffsetx() {
		return pixelMapOffsetx;
	}
	public int getPixelMapOffsety() {
		return pixelMapOffsety;
	}
	
	public int[] getPixelMap() {
		return pixelMap;
	}

	public int getAvgPixely() {
		return avgPixely;
	}

	public int getAvgPixelx() {
		return avgPixelx;
	}

	public void setRotation(int rotation) {
		while(rotation < 0)
			rotation += 360;
		while(rotation >= 360)
			rotation -= 360;
		
		this.rotation = rotation;
	}

	public int getRotation() {
		return rotation;
	}

	public void setDistanceFromOrigin(Vector2d origin) {
		Vector2d avgPixel = new Vector2d(this.avgPixelx,this.avgPixely);
		avgPixel.sub(origin);
		this.distanceFromOrigin = (int) avgPixel.length();
	}
	

	public int getDistanceFromOrigin() {
		return distanceFromOrigin;
	}
	
	public boolean Equals(Character other) {
		return other.ID == this.ID;
	}

	public void setLetterPosition(int letterPosition) {
		this.letterPosition = letterPosition;
	}

	public int getLetterPosition() {
		return letterPosition;
	}

	@Override
	public int compareTo(Character o) {
		return this.getLetterPosition() - o.getLetterPosition() ;
	}
	
}
