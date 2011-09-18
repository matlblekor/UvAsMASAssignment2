package nl.uva.mas.amm.common;

public class Position implements Comparable<Position>{
	public int x;
	public int y;
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Position pos) {
		if(pos.x > this.x || (pos.x == this.x && pos.y > this.y))
			return 0;
		else
			return 1;
	}
	
	@Override
	public String toString() {
		return "x: " + this.x + ", y: " + this.y;
	}
	

}
