package at.neonartworks.parallaxfx;

public class Movement
{
	private double up;
	private double down;
	private double left;
	private double right;

	public Movement(double up, double down, double left, double right)
	{
		super();
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}

	public double getUp()
	{
		return up;
	}

	public void setUp(double up)
	{
		this.up = up;
	}

	public double getDown()
	{
		return down;
	}

	public void setDown(double down)
	{
		this.down = down;
	}

	public double getLeft()
	{
		return left;
	}

	public void setLeft(double left)
	{
		this.left = left;
	}

	public double getRight()
	{
		return right;
	}

	public void setRight(double right)
	{
		this.right = right;
	}

}