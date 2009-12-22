package iweb2.ch5.usecase.fraud.data;

public class TransactionLocation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7742289669577088001L;
	
	private double x;
    private double y;
    
    public TransactionLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(TransactionLocation location) {
        return Math.sqrt( 
                (x - location.getX()) * (x - location.getX()) +  
                (y - location.getY()) * (y - location.getY()) ); 
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
	public String toString() {
        return "[" + "x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TransactionLocation other = (TransactionLocation) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    
    
    
}
