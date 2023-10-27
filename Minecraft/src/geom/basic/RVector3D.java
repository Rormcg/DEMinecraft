/*
Rory McGuire
2/7/2023
RVector
Represents a 3D Vector
*/
package geom.basic;

public class RVector3D extends RVector { 
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   
   private double z;
   
   
   RVector3D() {
      super(0, 0);
      z = 0;
   }
   RVector3D (double x, double y, double z) {
      super(x, y);
      this.z = z;
   }
   
   //Rotates this vector by the specified degrees
   public void rotate(double rx, double ry, double rz) {
      rotateRadians(Math.toRadians(rx), Math.toRadians(ry), Math.toRadians(rz));
   }
   
   //Rotates this vector by the specified radians in each direction (rx will rotate around x-axis)
   public void rotateRadians(double rx, double ry, double rz) {
      //temporarily save pos values
      double xt, zt;
      xt = x;
      //rotate around z-axis
      x=x*Math.cos(rz) - y*Math.sin(rz);
      y=xt*Math.sin(rz) + y*Math.cos(rz);
      //rotate around x-axis
      zt = z;
      z=z*Math.cos(rx) - y*Math.sin(rx);
      y=zt*Math.sin(rx) + y*Math.cos(rx);
      //rotate around y-axis
      xt = x;
      x=x*Math.cos(ry) - z*Math.sin(ry);
      z=xt*Math.sin(ry) + z*Math.cos(ry);
   }
   
   //Returns the distance between this point and another RVector
   public double distance(RVector3D other) {
      return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
   }
   
   //Returns the distance between two points (RVectors)
   public static double distance(RVector3D v1, RVector3D v2) {
      return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2) + Math.pow(v1.z - v2.z, 2));
   }
   
   //Sets this vector's magnitude to the specified length
   public void setMagnitude(double mag) {
      x=x / magnitude() * mag;
      y=y / magnitude() * mag;
      z=z / magnitude() * mag;
   }
   
   //Returns the direction of this vector in degrees depending on the input value: "x" will return the rotation around the x-axis
   public double degrees(String axis) {
      return Math.toDegrees(radians(axis));
   }
   
   //Returns the direction of this vector in radians depending on the input value: "x" will return the rotation around the x-axis
   public double radians(String axis) {
      double dir;
      switch(axis) {
         case "x":
         case "X":
            dir = Math.atan(y / z);
            
            if((z < 0 && y > 0) || (z < 0 && y < 0)) {
               dir += 3*Math.PI / 2;
            } else {
               dir += Math.PI / 2;
            }
            return dir % (2 * Math.PI);
         case "y":
         case "Y":
            dir = Math.atan(z / x);
            
            if((x < 0 && z > 0) || (x < 0 && z < 0)) {
               dir += 3*Math.PI / 2;
            } else {
               dir += Math.PI / 2;
            }
            return dir % (2 * Math.PI);
         case "z":
         case "Z":
         default:
            dir = Math.atan(y / x);
            
            if((x < 0 && y > 0) || (x < 0 && y < 0)) {
               dir += 3*Math.PI / 2;
            } else {
               dir += Math.PI / 2;
            }
            return dir % (2 * Math.PI);
      }
   }
   
   //returns the slope of line containing the x and y values of this and another RVector3D
   public double slope2D(RVector3D other) {
	   double s = (getY() - other.getY()) / (getX() - other.getX());
      if(s == java.lang.Double.NEGATIVE_INFINITY) s = java.lang.Double.POSITIVE_INFINITY;
      return s;
   }

   //Returns the magnitude of this vector
   public double magnitude() {
      return Math.sqrt(x*x + y*y + z*z);
   }
   
   @Override
   public boolean equals(Object o) {
      if(!(o instanceof RVector3D)) return false;
      RVector3D other = (RVector3D)o;
      return other.x == x && other.y == y && other.z == z;
   }
   
   /*@Override
   //TO DO: make compareTo better
   public int compareTo(RVector3D other) {
      return (int) (magnitude() - other.magnitude());
   }*/
   
   public RVector3D copy() {
      return new RVector3D(x, y, z);
   }
   
   public double getX() {
      return x;
   }
   
   public double getY() {
      return y;
   }
   
   public double getZ() {
      return z;
   }
   
   public void setX(double a) {
      x = a;
   }
   
   public void setY(double a) {
      y = a;
   }
   
   public void setZ(double a) {
      z = a;
   }
   
   public void moveTo(double a, double b, double c) {
      x = a;
      y = b;
      z = c;
   }
   
   public void add(double a, double b, double c) {
      x += a;
      y += b;
      z += c;
   }
}