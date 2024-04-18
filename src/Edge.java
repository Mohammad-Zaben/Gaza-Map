public class Edge {
	private Vertex source;
	private Vertex destination;
	private double distance;

	public Edge(Vertex source, Vertex destination, double distance) {
		super();
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	} 

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public double getdistance() {
		return distance;
	}

	public void setdistance(double distance) {
		this.distance = distance;
	}

}