
public class Graph {
	private Vertex VList[];
	private Edge EList[];
	private LinkedList Adgasentlist[];
	private int NumOfVerticies;
	private int NumOfEdges;
	private int i = 0;
	private int j = 0;

	public Graph(int numV, int numE) {
		VList = new Vertex[numV];
		EList = new Edge[numE];
		Adgasentlist = new LinkedList[numV];
		NumOfVerticies = numV;
		NumOfEdges = numE;
	}

	public void AddVertix(Vertex v) {
		VList[i] = v;
		Adgasentlist[i++] = new LinkedList();
	}

	public void AddEdge(int index, Edge e) {
		EList[j++] = e;
		Adgasentlist[index].addLast(e);
	}

	public Vertex[] getVList() {
		return VList;
	}

	public void setVList(Vertex[] vList) {
		VList = vList;
	}

	public Edge[] getEList() {
		return EList;
	}

	public void setEList(Edge[] eList) {
		EList = eList;
	}

	public LinkedList[] getAdgasentlist() {
		return Adgasentlist;
	}

	public void setAdgasentlist(LinkedList[] adgasentlist) {
		Adgasentlist = adgasentlist;
	}
	
	

	public int getNumOfVerticies() {
		return NumOfVerticies;
	}

	public void setNumOfVerticies(int numOfVerticies) {
		NumOfVerticies = numOfVerticies;
	}

	public int getNumOfEdges() {
		return NumOfEdges;
	}

	public void setNumOfEdges(int numOfEdges) {
		NumOfEdges = numOfEdges;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}
