package geom.basic;

/**
 * @author Rory McGuire
 * 2/7/2023
 * RVector
 * Represents a 2D Vector
*/
public class RVector  implements Comparable<RVector> {	
	private double x, y;
	
	/**
	 * Creates a new RVector with x and y values 0
	 */
	public RVector() {
		this(0, 0);
	}
	
	/**
	 * Creates a new RVector with given x and y values
	 * @param x the x value for the new RVector
	 * @param y the y value for the new RVector
	 */
	public RVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new RVector with the same x and y values as a given RVector
	 * @param r the RVector to model the new object after
	 */
	public RVector(RVector r) {
		this(r.x, r.y);
	}
	
	/**
	 * Gives the result of rotating a given vector by a specified number of degrees
	 * @param r the vector to be rotated
	 * @param deg the number fo degrees to rotate
	 * @return an adequately rotated RVector
	 */
	public static RVector rotate(RVector r, double deg) {
		RVector temp = new RVector(r);
		temp.rotate(deg);
		return temp;
	}
	
	/**
	 * Gives the result of rotating a given vector by a specified number of degrees around a given fixed point
	 * @param r the vector to be rotated
	 * @param deg the number of degrees to rotate
	 * @param anchor the point around which to rotate
	 * @return an adequately rotated RVector
	 */
	public static RVector rotate(RVector r, double deg, RVector anchor) {
		RVector temp = new RVector(r);
		temp.rotate(deg, anchor);
		return temp;
	}
	
	/**
	 * Finds the midpoint between two points and returns it as an RVector
	 * @param r1 one of the points
	 * @param r2 the second point
	 * @return the midpoint between the points (as an RVector)
	 */
	public static RVector midpoint(RVector r1, RVector r2) {
		return new RVector((r1.x + r2.x) / 2, (r1.y + r2.y) / 2);
	}
	
	/**
	 * Finds and returns the the given RVector after rotating it a given number of radians
	 * @param r the RVector to find the rotation
	 * @param rad the number of radians to rotate the RVector
	 * @return The rotated RVector
	 */
	public static RVector rotateRadians(RVector r, double rad) {
		RVector temp = new RVector(r);
		temp.rotateRadians(rad);
		return temp;
	}
	
	/**
	 * Finds and returns the the given RVector after rotating it a given number of radians around a given point
	 * @param r the RVector to find the rotation
	 * @param rad the number of radians to rotate the RVector
	 * @anchor the point around which to rotate the RVector
	 * @return The rotated RVector
	 */
	public static RVector rotateRadians(RVector r, double rad, RVector anchor) {
		RVector temp = new RVector(r);
		temp.rotateRadians(rad, anchor);
		return temp;
	}
	
	/**
	 * Rotates this vector by the specified degrees around the anchor point
	 * @param deg the number of degrees to rotate this vector
	 * @param anchor the point around which to rotate this
	 */
	public void rotate(double deg, RVector anchor) {
		rotateRadians(Math.toRadians(deg), anchor);
	}
	
	/**
	 * Rotates this vector by the specified degrees
	 * @param deg the number of degrees to rotate this
	 */
	public void rotate(double deg) {
		rotateRadians(Math.toRadians(deg));
	}

	/**
	 * Rotates this vector by the specified radians around the anchor point
	 * @param rad The number of radians to rotate this vector
	 * @param anchor the point around which to rotate this
	 */
	public void rotateRadians(double rad, RVector anchor) {
		this.sub(anchor);
		this.rotateRadians(rad);
		this.add(anchor);
	}
	
	/**
	 * Rotates this vector by the specified radians
	 * @param rad The number of radians to rotate the vector
	 */
	public void rotateRadians(double rad) {
		setLocation(x * Math.cos(rad) - y * Math.sin(rad),
							x * Math.sin(rad) + y * Math.cos(rad));
	}
	
	/**
	 * Returns the difference in rotation between this and another RVector in radians (the rotation required to rotate this to the other's rotation)
	 * @param r The Rvector to compare to this RVector
	 * @return The difference between the given RVector's rotation and this RVector's rotation
	*/
	public double findRotationTo(RVector r) {
		return r.radians() - this.radians();
	}
	
	/**
	 * Returns the distance between this point and another RVector
	 * @param other The RVector to find the distance to
	 * @return The distance between this and another RVector
	 */
	public double distance(RVector other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}

	/**
	 * Finds and returns the slope of the line containing this and another RVector
	 * @param other the second point on the line 
	 * @return the slope of the line containing both this and the given RVector
	 */
	public double slope(RVector other) {
		double s = (y - other.y) / (x - other.x);
		if (s == java.lang.Double.NEGATIVE_INFINITY)
			s = java.lang.Double.POSITIVE_INFINITY;
		return s;
	}

	/**
	 * Finds and returns the slope of a line perpendicular to the line between this and another RVector
	 * @param other the other point on the line with this RVector
	 * @return The slope of a line perpendicular to the line containing both points
	 */
	public double perpendicularSlope(RVector other) {
		double s = (y - other.y) / (x - other.x);
		if (s == java.lang.Double.NEGATIVE_INFINITY || s == java.lang.Double.POSITIVE_INFINITY)
			return 0;
		return -1.0 / s;
	}

	/**
	 * Finds and returns the slope of the line containing two RVectors
	 * @param a The first point on the line
	 * @param b The second point on the line
	 * @return The slope of the line containing both points
	 */
	public static double slope(RVector a, RVector b) {
		double s = (a.y - b.y) / (a.x - b.x);
		if (s == java.lang.Double.NEGATIVE_INFINITY)
			s = java.lang.Double.POSITIVE_INFINITY;
		return s;
	}

	/**
	 * Finds and returns the distance between two points (RVectors)
	 * @param v1 The first point
	 * @param v2 The second point
	 * @return The distance between the two points
	 */
	public static double distance(RVector v1, RVector v2) {
		return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
	}

	/**
	 * Finds and returns the solution of two linear equations in slope-intercept form as an RVector (AKA the intersection of two lines)
	 * @param m1 The slope of the first line
	 * @param b1 the y-intercept of the first line
	 * @param m2 The slope of the second line
	 * @param b2 The y-intercept of the second line
	 * @return The point at which the two line intercept
	 */
	public static RVector solutionSlopeIntercept(double m1, double b1, double m2, double b2) {
		if (Math.abs(m1 - m2) <= 0.0000001) {
			return null; // if the two lines are parallel (don't intersect)
		}

		// If one/both of the lines have an undefined slope
		if (m1 == java.lang.Double.POSITIVE_INFINITY || m1 == java.lang.Double.NEGATIVE_INFINITY) {
			if (m2 == java.lang.Double.POSITIVE_INFINITY || m2 == java.lang.Double.NEGATIVE_INFINITY) {
				return null;
			} else {
				return new RVector(b1, m2 * b1 + b2);
			}
			// return null;
		} else if (m2 == java.lang.Double.POSITIVE_INFINITY || m2 == java.lang.Double.NEGATIVE_INFINITY) {
			return new RVector(b2, m1 * b2 + b1);
		}

		double tempX = (b1 - b2) / (m2 - m1);
		return new RVector(tempX, m1 * tempX + b1);
	}

	/**
	 * Finds and returns the solution of two linear equations in point-slope form as an RVector (AKA the intersection of two lines)
	 * @param m1 The slope of the first line
	 * @param x1 The x-value of a point on the first line
	 * @param y1 The y-value of a point on the first line
	 * @param m2 The slope of the second line
	 * @param x2 The x-value of a point on the second line
	 * @param y2 The y-value of a point on the second line
	 * @return The point of intersection of the two lines
	 */
	public static RVector solutionPointSlope(double m1, double x1, double y1, double m2, double x2, double y2) {
		if (Math.abs(m1 - m2) <= 0.0000001) {
			return null; // if the two lines have the same slope/are parallel (they don't intersect)
		}

		// If one/both of the lines have an undefined slope
		if (m1 == java.lang.Double.POSITIVE_INFINITY || m1 == java.lang.Double.NEGATIVE_INFINITY) {
			if (m2 == java.lang.Double.POSITIVE_INFINITY || m2 == java.lang.Double.NEGATIVE_INFINITY) {
				return null;
			} else {
				return new RVector(x1, m2 * x1 + (-m2 * x2 + y2));
			}
		} else if (m2 == java.lang.Double.POSITIVE_INFINITY || m2 == java.lang.Double.NEGATIVE_INFINITY) {
			return new RVector(x2, m1 * x2 + (-m1 * x1 + y1));
		}
		return solutionSlopeIntercept(m1, -m1 * x1 + y1, m2, -m2 * x2 + y2);
	}

	/**
	 * Finds and returns the solution of two linear equations in point-slope form as an RVector (AKA the intersection of two lines)
	 * @param m1 The slope of the first line
	 * @param r1 A point on the first line
	 * @param m2 The slope of the second line
	 * @param r2 A point on the second line
	 * @return The point of intersection of the two lines
	 */
	public static RVector solutionPointSlope(double m1, RVector r1, double m2, RVector r2) {
		return solutionPointSlope(m1, r1.x, r1.y, m2, r2.x, r2.y);
	}

	/**
	 * Sets this vector's magnitude to the specified length
	 * @param mag The magnitude to set this RVector to
	 */
	public void setMagnitude(double mag) {
		setLocation(x / magnitude() * mag, y / magnitude() * mag);
	}

	/**
	 * Finds and returns the rotation of this vector in degrees
	 * @return The rotation of this RVector in degrees
	 */
	public double degrees() {
		return Math.toDegrees(radians());
	}

	/**
	 * Returns the direction of this vector in radians
	 * @return the rotation of this RVector in radians
	 */
	public double radians() {
		if (x == 0 && y != 0)
			return y > 0 ? 3 * Math.PI / 2 : Math.PI / 2;
		else if (y == 0 && x == 0)
			return 0;

		double dir = Math.atan(y / x);

		if ((x <= 0 && y >= 0) || (x <= 0 && y <= 0)) {
			dir *= -1;
			dir += Math.PI;
		} else if (x >= 0 && y >= 0) {
			dir += Math.PI * 2;
		} else if (x >= 0 && y <= 0) {
			dir *= -1;
		}
		return dir;
	}

	/**
	 * Finds and returns the magnitude of this vector
	 * @return the magnitude of this RVector
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Returns whether this RVector is equivalent to a given Object
	 * @param o The other Object to compare with this RVector
	 * @return whether this is equivalent to the other Object
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RVector))
			return false;
		RVector other = (RVector) o;
		return other.x == x && other.y == y;
		// return Math.abs(compareTo(other)) <= 0.0001 && radians() - other.radians() <=
		// 0.0001;
	}
	
	// TO DO: make compareTo better
	@Override
	public int compareTo(RVector other) {
		if(other == null) return 1;
		return (int) (magnitude() - other.magnitude());
	}
	
	///BASIC MATHEMATICAL METHODS///
	
	
	public static RVector mult(RVector r, double a) {
		return new RVector(r.x*a, r.y*a);
	}
	
	public void mult(double a) {
		this.setLocation(x*a, y*a);
	}
	
	public static RVector mult(RVector r, RVector r2) {
		return new RVector(r.x*r2.x, r.y*r2.y);
	}
	
	public void mult(RVector r) {
		this.setLocation(x*r.x, y*r.y);
	}
	
	public static RVector add(RVector r, double a) {
		return new RVector(r.x+a, r.y+a);
	}
	
	public void add(double a) {
		this.setLocation(x+a, y+a);
	}
	
	public static RVector add(RVector r, RVector r2) {
		return new RVector(r.x+r2.x, r.y+r2.y);
	}
	
	public void add(RVector r) {
		this.setLocation(x+r.x, y+r.y);
	}
	
	public static RVector sub(RVector r, double a) {
		return new RVector(r.x-a, r.y-a);
	}
	
	public void sub(double a) {
		this.setLocation(x-a, y-a);
	}
	
	public static RVector sub(RVector r, RVector r2) {
		return new RVector(r.x-r2.x, r.y-r2.y);
	}
	
	public void sub(RVector r) {
		this.setLocation(x-r.x, y-r.y);
	}
	
	public static RVector div(RVector r, double a) {
		return new RVector(r.x/a, r.y/a);
	}
	
	public void div(double a) {
		this.setLocation(x/a, y/a);
	}
	
	public static RVector div(RVector r, RVector r2) {
		return new RVector(r.x/r2.x, r.y/r2.y);
	}
	
	public void div(RVector r) {
		this.setLocation(x/r.x, y/r.y);
	}
	
	public void setLocation(double a, double b) {
		this.x = a;
		this.y = b;
	}
	
	public void setLocation(RVector r) {
		setLocation(r.x, r.y);
	}
	
	public void setX(double a) {
		setLocation(a, y);
	}
	
	public void setY(double a) {
		setLocation(x, a);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public RVector copy() {
		return new RVector(x, y);
	}
}