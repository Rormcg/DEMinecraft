/*
Rory McGuire
2/7/2023
RVector
Represents a 2D Vector
*/
package geom;

import java.awt.geom.Point2D;

public class RVector extends Point2D.Double implements Comparable<RVector>{ 
   public RVector() {super();}
   public RVector (double x, double y) {super(x, y);}
   
   //Rotates this vector by the specified degrees
   public void rotate(double deg) {
      rotateRadians(Math.toRadians(deg));
   }
   
   //Rotates this vector by the specified radians
   public void rotateRadians(double rad) {
      super.setLocation(getX()*Math.cos(rad) - getY()*Math.sin(rad),
                        getX()*Math.sin(rad) + getY()*Math.cos(rad));
   }
   
   //Returns the distance between this point and another RVector
   public double distance(RVector other) {
      return Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
   }
   
   //returns the slope of the line containing this and another RVector
   public double slope(RVector other) {
	   return (getY() - other.getY()) / (getX() - other.getX());
   }
   
   //returns the slope of the line containing two RVectors
   public static double slope(RVector a, RVector b) {
	   return (a.getY() - b.getY()) / (a.getX() - b.getX());
   }
   
   //Returns the distance between two points (RVectors)
   public static double distance(RVector v1, RVector v2) {
      return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
   }
   
   //returns the solution of two linear equations in slope-intercept form as an RVector (AKA the intersection of two lines)
   public static RVector solutionSlopeIntercept(double m1, double b1, double m2, double b2) {
	   double tempX = (b1 - b2) / (m1 - m2);
	   return new RVector(tempX, m1 * tempX + b1);
   }
   
   //returns the solution of two linear equations in point-slope form as an RVector (AKA the intersection of two lines)
   public static RVector solutionPointSlope(double m1, double y1, double x1, double m2, double y2, double x2) {
	   return solutionSlopeIntercept(m1, -m1 * x1 + y1, m2, -m2 * x2 + y2);
   }
   
   //Sets this vector's magnitude to the specified length
   public void setMagnitude(double mag) {
      super.setLocation(getX() / magnitude() * mag, getY() / magnitude() * mag);
   }
   
   //Returns the direction of this vector in degrees
   public double degrees() {
      return Math.toDegrees(radians());
   }
   
   //Returns the direction of this vector in radians
   public double radians() {
      double dir = Math.atan(getY() / getX());
      
      if((getX() < 0 && getY() > 0) || (getX() < 0 && getY() < 0)) {
         dir += 3*Math.PI / 2;
      } else  {
         dir += Math.PI / 2;
      }
      return dir % (2 * Math.PI);
   }
   
   //Returns the magnitude of this vector
   public double magnitude() {
      return Math.sqrt(getX()*getX() + getY()*getY());
   }
   
   @Override
   public boolean equals(Object o) {
      if(!(o instanceof RVector)) return false;
      RVector other = (RVector)o;
      return other.getX() == getX() && other.getY() == getY();
      //return Math.abs(compareTo(other)) <= 0.0001 && radians() - other.radians() <= 0.0001;
   }
   
   @Override
   //TO DO: make compareTo better
   public int compareTo(RVector other) {
      return (int) (magnitude() - other.magnitude());
   }
   
   public RVector copy() {
      return new RVector(getX(), getY());
   }
}