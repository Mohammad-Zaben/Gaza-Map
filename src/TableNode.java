

public class TableNode implements Comparable<TableNode> {
	private Vertex V;
	private boolean Known;
	private double Distance;
	private Vertex Previous;

	public TableNode() {
	}

	public TableNode(Vertex v) {
		super();
		V = v;
	}

	public TableNode(Vertex v, int weight) {
		super();
		V = v;
	}

	public Vertex getV() {
		return V;
	}

	public boolean isKnown() {
		return Known;
	}

	public void setKnown(boolean known) {
		Known = known;
	}

	public double getDistance() {
		return Distance;
	}

	public void setDistance(double distance) {
		Distance = distance;
	}

	public Vertex getPrevious() {
		return Previous;
	}

	public void setPrevious(Vertex previous) {
		Previous = previous;
	}

	@Override
	public int compareTo(TableNode t) {
		if (this.getDistance() > t.getDistance()) {
			return 1;
		} else if (this.getDistance() < t.getDistance()) {
			return -1;
		}
		return 0;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return V.getName();
	}

}
