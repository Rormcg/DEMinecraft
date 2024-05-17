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
	
	public static RVector midpoint(RVector r1, RVector r2) {
		return new RVector((r1.x + r2.x) / 2, (r1.y + r2.y) / 2);
	}
	
	public static RVector rotateRadians(RVector r, double rad) {
		RVector temp = new RVector(r);
		temp.rotateRadians(rad);
		return temp;
	}
	
	public static RVector rotateRadians(RVector r, double rad, RVector anchor) {
		RVector temp = new RVector(r);
		temp.rotateRadians(rad, anchor);
		return temp;
	}
	
	// Rotates this vector by the specified degrees around the anchor point
	public void rotate(double deg, RVector anchor) {
		rotateRadians(Math.toRadians(deg), anchor);
	}
	
	// Rotates this vector by the specified degrees
	public void rotate(double deg) {
		rotateRadians(Math.toRadians(deg));
	}

	// Rotates this vector by the specified radians around the anchor point
	public void rotateRadians(double rad, RVector anchor) {
		this.sub(anchor);
		this.rotateRadians(rad);
		this.add(anchor);
	}
	
	// Rotates this vector by the specified radians
	public void rotateRadians(double rad) {
		setLocation(x * Math.cos(rad) - y * Math.sin(rad),
							x * Math.sin(rad) + y * Math.cos(rad));
	}

	// Returns the difference in rotation between this and another RVector (the
	// rotation required to rotate this to the other's rotation)
	// returns as radians
	public double findRotationTo(RVector r) {
		return r.radians() - this.radians();
	}
	
	// Returns the distance between this point and another RVector
	public double distance(RVector other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}

	// returns the slope of the line containing this and another RVector
	public double slope(RVector other) {
		double s = (y - other.y) / (x - other.x);
		if (s == java.lang.Double.NEGATIVE_INFINITY)
			s = java.lang.Double.POSITIVE_INFINITY;
		return s;
	}

	// returns the slope of a line perpendicular to the line between this and
	// another RVector
	public double perpendicularSlope(RVector other) {
		double s = (y - other.y) / (x - other.x);
		if (s == java.lang.Double.NEGATIVE_INFINITY || s == java.lang.Double.POSITIVE_INFINITY)
			return 0;
		return -1.0 / s;
	}

	// returns the slope of the line containing two RVectors
	public static double slope(RVector a, RVector b) {
		double s = (a.y - b.y) / (a.x - b.x);
		if (s == java.lang.Double.NEGATIVE_INFINITY)
			s = java.lang.Double.POSITIVE_INFINITY;
		return s;
	}

	// Returns the distance between two points (RVectors)
	public static double distance(RVector v1, RVector v2) {
		return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
	}

	// returns the solution of two linear equations in slope-intercept form as an
	// RVector (AKA the intersection of two lines)
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

	// returns the solution of two linear equations in point-slope form as an
	// RVector (AKA the intersection of two lines)
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

	// Overloaded version of solutionPointSlope, this time accepting just two slopes
	// and two RVectors
	public static RVector solutionPointSlope(double m1, RVector r1, double m2, RVector r2) {
		return solutionPointSlope(m1, r1.x, r1.y, m2, r2.x, r2.y);
	}

	// Sets this vector's magnitude to the specified length
	public void setMagnitude(double mag) {
		setLocation(x / magnitude() * mag, y / magnitude() * mag);
	}

	// Returns the direction of this vector in degrees
	public double degrees() {
		return Math.toDegrees(radians());
	}

	// Returns the direction of this vector in radians
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

	// Returns the magnitude of this vector
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RVector))
			return false;
		RVector other = (RVector) o;
		return other.x == x && other.y == y;
		// return Math.abs(compareTo(other)) <= 0.0001 && radians() - other.radians() <=
		// 0.0001;
	}

	@Override
	// TO DO: make compareTo better
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