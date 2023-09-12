/*
Rory McGuire
2/7/2023
RVector
Represents a 2D Vector
*/

import java.awt.geom.Point2D;

public class RVector extends Point2D.Double implements Comparable<RVector>{ 
   RVector() {super();}
   RVector (double x, double y) {super(x, y);}
   
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
   
   //Returns the distance between two points (RVectors)
   public static double distance(RVector v1, RVector v2) {
      return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
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