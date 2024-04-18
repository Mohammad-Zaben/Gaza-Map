
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Dijkstra {
	private PriorityQueue<TableNode> pqueue;
	private Graph g;
	private TableNode arr[];
	double distance = 0;
	ArrayList<Vertex> Path = new ArrayList<>();

	public void intializeTable(Vertex start, Graph g) {
		this.g = g;
		pqueue = new PriorityQueue<TableNode>();
		arr = new TableNode[g.getNumOfVerticies()];
		TableNode temp;
		for (int i = 0; i < g.getNumOfVerticies(); i++) {
			temp = new TableNode(g.getVList()[i]);
			arr[i] = new TableNode(g.getVList()[i]);
			if (temp.getV().getName().compareTo(start.getName()) == 0) {
				temp.setDistance(0);
				arr[i].setDistance(0);
				pqueue.add(temp);
			} else {
				temp.setDistance(Integer.MAX_VALUE);
				arr[i].setDistance(Integer.MAX_VALUE);
			}
			temp.setKnown(false);
			arr[i].setKnown(false);
			temp.setPrevious(new Vertex(0, "0", 0, 0, 0));
			arr[i].setPrevious(new Vertex(0, "0", 0, 0, 0));
		}
	}

	public void find(Vertex s, Vertex d) {
		while (!pqueue.isEmpty()) {
			System.out.println(pqueue);
			int ind = pqueue.poll().getV().getIndex();
			arr[ind].setKnown(true);
			if (d.getName().compareToIgnoreCase(arr[ind].getV().getName()) == 0) {
				break;
			}
			for (int i = 0; i < g.getAdgasentlist()[ind].size(); i++) {
				int ind2 = ((Edge) (g.getAdgasentlist()[ind].get(i))).getDestination().getIndex();
				if (!arr[ind2].isKnown()) {
					if (arr[ind].getDistance() + (((Edge) (g.getAdgasentlist()[ind].get(i))).getdistance()) < arr[ind2]
							.getDistance()) {
						arr[ind2].setDistance(
								(arr[ind].getDistance() + ((Edge) g.getAdgasentlist()[ind].get(i)).getdistance()));
						arr[ind2].setPrevious(arr[ind].getV());
						pqueue.add(arr[ind2]);
					}
				}
			}
		}
		distance = arr[d.getIndex()].getDistance();
	}

	public void printPath(Vertex v) {
		if (arr[v.getIndex()].getPrevious().getName().compareTo("0") != 0) {
			Path.add(v);
			printPath(arr[v.getIndex()].getPrevious());
		}
	}
}
