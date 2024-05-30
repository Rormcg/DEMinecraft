
package geom.basic;

/**
 * @author Rory McGuire
 * 2/7/2023
 * RVector
 * Represents a 3D Vector
 */
public class RVector3D extends RVector {
	private double z;

	public RVector3D() {
		this(0, 0, 0);
	}

	public RVector3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}
	
	public RVector3D(RVector3D r) {
		this(r.getX(), r.getY(), r.z);
	}

	// Rotates this vector by the specified degrees
	public void rotate(double rx, double ry, double rz) {
		rotateRadians(Math.toRadians(rx), Math.toRadians(ry), Math.toRadians(rz));
	}
	
	// Rotates this vector by the specified degrees in each direction
	public void rotate(RVector3D r) {
		rotate(Math.toRadians(r.getX()), Math.toRadians(r.getY()), Math.toRadians(r.z));
	}
	
	public void rotate(RVector3D r, RVector3D anchor) {
		this.sub(anchor);
		this.rotate(r);
		this.add(anchor);
	}
	
	public void rotate(double x, double y, double z, RVector3D anchor) {
		rotate(new RVector3D(x, y, z), anchor);
	}
	
	// Rotates this vector by the specified radians in each direction (rx will
	// rotate around x-axis)
	public void rotateRadians(double rx, double ry, double rz) {
		// temporarily save pos values
		double xt, zt;
		xt = getX();
		// rotate around z-axis
		setX(getX() * Math.cos(rz) - getY() * Math.sin(rz));
		setY(xt * Math.sin(rz) + getY() * Math.cos(rz));
		// rotate around x-axis
		zt = z;
		z = z * Math.cos(rx) - getY() * Math.sin(rx);
		setY(zt * Math.sin(rx) + getY() * Math.cos(rx));
		// rotate around y-axis
		xt = getX();
		setX(getX() * Math.cos(ry) - z * Math.sin(ry));
		z = xt * Math.sin(ry) + z * Math.cos(ry);
	}
	
	// Rotates this vector by the specified degrees
	public void rotateRadians(RVector3D r) {
		rotateRadians(Math.toRadians(r.getX()), Math.toRadians(r.getY()), Math.toRadians(r.z));
	}

	// Returns the distance between this point and another RVector
	public double distance(RVector3D other) {
		return Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2) + Math.pow(z - other.z, 2));
	}

	// Returns the distance between two points (RVectors)
	public static double distance(RVector3D v1, RVector3D v2) {
		return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2) + Math.pow(v1.z - v2.z, 2));
	}

	// Sets this vector's magnitude to the specified length
	public void setMagnitude(double mag) {
		setX(getX() / magnitude() * mag);
		setY(getY() / magnitude() * mag);
		z /= magnitude() * mag;
	}
	
	public void normalize() {
		setMagnitude(1);
	}

	// Returns the direction of this vector in degrees depending on the input value:
	// "x" will return the rotation around the x-axis
	public double degrees(String axis) {
		return Math.toDegrees(radians(axis));
	}

	// Returns the direction of this vector in radians depending on the input value:
	// "x" will return the rotation around the x-axis
	public double radians(String axis) {
		double dir;
		switch (axis) {
		case "x":
		case "X":
			dir = Math.atan(getY() / z);

			if ((z <= 0 && getY() >= 0) || (z <= 0 && getY() <= 0)) {
				dir *= -1;
				dir += Math.PI;
			} else if (z >= 0 && getY() >= 0) {
				dir += Math.PI * 2;
			} else if (z >= 0 && getY() <= 0) {
				dir *= -1;
			}
			return dir;
		case "y":
		case "Y":
			dir = Math.atan(z / getX());

			if ((getX() <= 0 && z >= 0) || (getX() <= 0 && z <= 0)) {
				dir *= -1;
				dir += Math.PI;
			} else if (getX() >= 0 && z >= 0) {
				dir += Math.PI * 2;
			} else if (getX() >= 0 && z <= 0) {
				dir *= -1;
			}
			return dir;
		case "z":
		case "Z":
			dir = Math.atan(getY() / getX());

			if ((getX() <= 0 && getY() >= 0) || (getX() <= 0 && getY() <= 0)) {
				dir *= -1;
				dir += Math.PI;
			} else if (getX() >= 0 && getY() >= 0) {
				dir += Math.PI * 2;
			} else if (getX() >= 0 && getY() <= 0) {
				dir *= -1;
			}
			return dir;
		default:
			throw new IllegalArgumentException("Axis \"" + axis + "\" not recognized");
			//return 0;
		}
	}
	
	public RVector rotation() {
		return new RVector(degrees("X"), degrees("Y"));
	}

	// returns the slope of line containing the getX() and getY() values of this and another
	// RVector3D
	public double slope2D(RVector3D other) {
		double s = (getY() - other.getY()) / (getX() - other.getX());
		if (s == java.lang.Double.NEGATIVE_INFINITY)
			s = java.lang.Double.POSITIVE_INFINITY;
		return s;
	}

	// Returns the magnitude of this vector
	public double magnitude() {
		return Math.sqrt(getX() * getX() + getY() * getY() + z * z);
	}
	
	//can return a negative magnitude
	public double signedMagnitude() {
		return magnitude() * ((getX() < 0 ^ getY() < 0 ^ getZ() < 0) || (getX() < 0 && getY() < 0 && getZ() < 0) ? -1 : 1);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RVector3D))
			return false;
		RVector3D other = (RVector3D) o;
		return other.getX() == getX() && other.getY() == getY() && other.z == z;
	}

	/*
	 * @Override //TO DO: make compareTo better public int compareTo(RVector3D
	 * other) { return (int) (magnitude() - other.magnitude()); }
	 */

	public RVector3D copy() {
		return new RVector3D(getX(), getY(), z);
	}
	
	///BASIC MATHEMATICAL METHODS///
	public static RVector3D mult(RVector3D r, double a) {
		return new RVector3D(r.getX()*a, r.getY()*a, r.getZ()*a);
	}
	
	public void mult(double a) {
		this.moveTo(getX()*a, getY()*a, getZ()*a);
	}
	
	public static RVector3D mult(RVector3D r, RVector3D r2) {
		return new RVector3D(r.getX()*r2.getX(), r.getY()*r2.getY(), r.getZ()*r2.getZ());
	}
	
	public void mult(RVector3D r) {
		this.moveTo(getX()*r.getX(), getY()*r.getY(), getZ()*r.getZ());
	}
	
	public static RVector3D add(RVector3D r, double a) {
		return new RVector3D(r.getX()+a, r.getY()+a, r.getZ()+a);
	}
	
	public void add(double a) {
		this.moveTo(getX()+a, getY()+a, getZ()+a);
	}
	
	public static RVector3D add(RVector3D r, RVector3D r2) {
		return new RVector3D(r.getX()+r2.getX(), r.getY()+r2.getY(), r.getZ()+r2.getZ());
	}
	
	public void add(RVector3D r) {
		this.moveTo(getX()+r.getX(), getY()+r.getY(), getZ()+r.getZ());
	}
	
	public static RVector3D sub(RVector3D r, double a) {
		return new RVector3D(r.getX()-a, r.getY()-a, r.getZ()-a);
	}
	
	public void sub(double a) {
		this.moveTo(getX()-a, getY()-a, getZ()-a);
	}
	
	public static RVector3D sub(RVector3D r, RVector3D r2) {
		return new RVector3D(r.getX()-r2.getX(), r.getY()-r2.getY(), r.getZ()-r2.getZ());
	}
	
	public void sub(RVector3D r) {
		this.moveTo(getX()-r.getX(), getY()-r.getY(), getZ()-r.getZ());
	}
	
	public static RVector3D div(RVector3D r, double a) {
		return new RVector3D(r.getX()/a, r.getY()/a, r.getZ()/a);
	}
	
	public void div(double a) {
		this.moveTo(getX()/a, getY()/a, getZ()/a);
	}
	
	public static RVector3D div(RVector3D r, RVector3D r2) {
		return new RVector3D(r.getX()/r2.getX(), r.getY()/r2.getY(), r.getZ()/r2.getZ());
	}
	
	public void div(RVector3D r) {
		this.moveTo(getX()/r.getX(), getY()/r.getY(), getZ()/r.getZ());
	}

	public double getZ() {
		return z;
	}

	public void setZ(double a) {
		z = a;
	}

	public void moveTo(double x, double y, double z) {
		setX(x);
		setY(y);
		this.z = z;
	}
}