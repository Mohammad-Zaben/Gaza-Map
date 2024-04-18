import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class Vertex {

	private String name;
	private double X;
	private double Y;
	private double Mx;
	private double My;
	private boolean key;
	private Circle c;
	private int index;
	private Label l;

	public Vertex() {
		super();
	} 

	public Vertex(int index, String name, double mx, double my, int key) {
		super();
		this.name = name;
		Mx = mx;
		My = my; 
		this.index = index;
		if (key == 0)
			this.key = false;
		else
			this.key = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public double getMx() {
		return Mx;
	}

	public void setMx(double mx) {
		Mx = mx;
	}

	public double getMy() {
		return My;
	}

	public void setMy(double my) {
		My = my;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public Circle getC() {
		return c;
	}

	public void setC(Circle c) {
		this.c = c;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Label getL() {
		return l;
	}

	public void setL(Label l) {
		this.l = l;
	}
	


}
