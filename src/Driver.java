import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.Destination;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Driver extends Application {

	private VBox controlPane;
	private ComboBox<String> Sorce;
	private ComboBox<String> Target;
	private Button runBtn;
	private Button clearbtn;
	private TextArea Path;
	private TextField Distance;

	private BorderPane rootPane;

	private Pane Gaza;

	private double scaleValue = 1.0;
	private double startX, startY;

	private ArrayList<String> ListOfComb = new ArrayList<String>();

	private final double MxMin = 34.1706744789612; // 31.615997227825954, 34.57473584174177
	private final double MyMin = 31.615997227825954; // 31.22103831786126, 34.1706744789612
	private final double MxMax = 34.57473584174177;// 31.218657674231572, 34.56019446042206
	private final double MyMax = 31.22103831786126;

	private double MinX;
	private double MinY;

	private double MaxX;
	private double MaxY;

	private int NumOfVirtix;
	private int NumOfEdge;
	private Graph graph;

	@Override
	public void start(Stage stage) throws Exception {
		DoControlPane();

		Scene scene = new Scene(Gaza, 560, 650);
		stage.setScene(scene);
		stage.setX(100);
		stage.setY(100);
		stage.show();

		Stage stage1 = new Stage();
		Scene scene1 = new Scene(controlPane, 310, 650);
		stage1.setScene(scene1);
		stage1.setX(stage.getX() + stage.getWidth() - 15);
		stage1.setY(stage.getY());
		stage1.setY(100);
		stage1.show();

		stage.setOnCloseRequest(e -> {
			stage1.close();
		});
		stage1.setOnCloseRequest(e -> {
			stage.close();
		});

		GetCornerXY();

		File file = new File("Data.txt");

		if (file.length() != 0) {
			Scanner input = new Scanner(file);

			NumOfVirtix = input.nextInt();
			NumOfEdge = input.nextInt();
			input.nextLine();
			System.out.println(NumOfEdge + "" + NumOfVirtix);
			graph = new Graph(NumOfVirtix, NumOfEdge);
			for (int i = 0; i < NumOfVirtix; i++) {
				System.out.println(i);
				String[] split = input.nextLine().split(" ");
				Vertex vertex = new Vertex(i, split[0], Double.parseDouble(split[2]), Double.parseDouble(split[1]),
						Integer.parseInt(split[3]));

				Circle circle = new Circle(4);
				circle.setFill(Color.GRAY);
				double x = getX(Double.parseDouble(split[2]));
				double y = getY(Double.parseDouble(split[1]));

				circle.setLayoutX(x);
				circle.setLayoutY(y);
				vertex.setC(circle);
				graph.AddVertix(vertex);

				Label l = new Label(split[0]);
				Font font = new Font("Arial", 8);
				l.setFont(font);
				l.setLayoutX(x - 13);
				l.setLayoutY(y - 13);
				vertex.setL(l);
				if (vertex.isKey()) {
					Gaza.getChildren().addAll(circle, l);
					ListOfComb.add(split[0]);
				}

				l.setOnMouseClicked(e -> {
					if (Sorce.getValue() == null) {
						for (int j = 0; j < ListOfComb.size(); j++)
							if (vertex.getName().equals(ListOfComb.get(j))) {
								Sorce.setValue(vertex.getName());
								vertex.getC().setRadius(6);
								vertex.getC().setFill(Color.LIGHTSEAGREEN);
								Font font1 = Font.font("Arial", FontWeight.BOLD, 12);
								l.setFont(font1);
							}
					} else if (Target.getValue() == null) {
						for (int j = 0; j < ListOfComb.size(); j++)
							if (vertex.getName().equals(ListOfComb.get(j))) {
								Target.setValue(vertex.getName());
								vertex.getC().setRadius(6);
								vertex.getC().setFill(Color.RED);
								Font font1 = Font.font("Arial", FontWeight.BOLD, 12);
								l.setFont(font1);
							}
					}
				});

				LabelAnimation(l);
			}

			int sorce = 0;
			int target = 0;
			for (int i = 0; i < NumOfEdge; i++) {
				String[] split = input.nextLine().split(" ");
				for (int j = 0; j < NumOfVirtix; j++) {
					if (graph.getVList()[j].getName().equals(split[0])) {
						sorce = j;
					} else if (graph.getVList()[j].getName().equals(split[1])) {
						target = j;
					}
				}
				double distance = distance(graph.getVList()[sorce].getMx(), graph.getVList()[target].getMx(),
						graph.getVList()[sorce].getMy(), graph.getVList()[target].getMy());
				Edge edge = new Edge(graph.getVList()[sorce], graph.getVList()[target], distance);
				graph.AddEdge(sorce, edge);

			}

		}
		ObservableList<String> observableList = FXCollections.observableArrayList(ListOfComb);
		Sorce.setItems(observableList);
		Sorce.setOnAction(e -> {
			if (Sorce.getSelectionModel().getSelectedIndex() != -1) {
				Font font1 = Font.font("Arial", FontWeight.BOLD, 12);
				graph.getVList()[Sorce.getSelectionModel().getSelectedIndex()].getC().setRadius(6);
				graph.getVList()[Sorce.getSelectionModel().getSelectedIndex()].getC().setFill(Color.LIGHTSEAGREEN);
				graph.getVList()[Sorce.getSelectionModel().getSelectedIndex()].getL().setFont(font1);
			}

		});
		Target.setItems(observableList);
		Target.setOnAction(e -> {
			if (Target.getSelectionModel().getSelectedIndex() != -1) {

				Font font1 = Font.font("Arial", FontWeight.BOLD, 12);
				graph.getVList()[Target.getSelectionModel().getSelectedIndex()].getC().setRadius(6);
				graph.getVList()[Target.getSelectionModel().getSelectedIndex()].getC().setFill(Color.RED);
				graph.getVList()[Target.getSelectionModel().getSelectedIndex()].getL().setFont(font1);
			}
		});

	}

	private void RunbtnAction() {
		try {
			int sourceIndex = Sorce.getSelectionModel().getSelectedIndex();
			int targetIndex = Target.getSelectionModel().getSelectedIndex();
			if (sourceIndex != -1 && targetIndex != -1) {

				Vertex sorce = graph.getVList()[sourceIndex];
				Vertex target = graph.getVList()[targetIndex];

				if (sorce.getName().equals(target.getName())) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("the Sorce is Can't be Target");
					alert.show();
					return;
				}

				Dijkstra d = new Dijkstra();
				d.intializeTable(sorce, graph);
				d.find(sorce, target);
				d.printPath(target);
				if (d.distance == Integer.MAX_VALUE) {

					Path.appendText("No Path");
					return;
				}
				// tfDistance.setText(String.valueOf(d.distance));
				Path.appendText(sorce.getName());
				Line l = new Line();

				l.setStartX(getX(sorce.getMx()));
				l.setStartY(getY(sorce.getMy()));
				System.out.println(getX(sorce.getMx()));
				for (int i = d.Path.size() - 1; i >= 0; i--) {

					l.setEndY(getY(d.Path.get(i).getMy()));
					l.setEndX(getX(d.Path.get(i).getMx()));
					drawLineSlowly(l, l.getEndX(), l.getEndY());

					Gaza.getChildren().add(l);

					l = new Line();
					l.setStartY(getY(d.Path.get(i).getMy()));
					l.setStartX(getX(d.Path.get(i).getMx()));
					Path.appendText("\n ---> " + d.Path.get(i).getName());
					DecimalFormat decimalFormat = new DecimalFormat("#.###");
					Distance.setText("The Distance is: " + decimalFormat.format(d.distance) + " KM");

				}

			}

		} catch (NullPointerException e) {
			// TODO: handle exception
		}

	}

	private void ClearbtnAction() {
		Font font = new Font("Arial", 8);
		Gaza.getChildren().removeIf(node -> node instanceof Line);
		if (Sorce.getSelectionModel().getSelectedIndex() != -1) {//
			graph.getVList()[Sorce.getSelectionModel().getSelectedIndex()].getC().setRadius(4);
			graph.getVList()[Sorce.getSelectionModel().getSelectedIndex()].getC().setFill(Color.GRAY);
			graph.getVList()[Sorce.getSelectionModel().getSelectedIndex()].getL().setFont(font);

			Sorce.getSelectionModel().clearSelection();

		}
		if (Target.getSelectionModel().getSelectedIndex() != -1) {
			graph.getVList()[Target.getSelectionModel().getSelectedIndex()].getC().setRadius(4);
			graph.getVList()[Target.getSelectionModel().getSelectedIndex()].getC().setFill(Color.GRAY);
			graph.getVList()[Target.getSelectionModel().getSelectedIndex()].getL().setFont(font);

			Target.getSelectionModel().clearSelection();

		}
		Path.clear();
		Distance.clear();
	}

	public void DoControlPane() {
		rootPane = new BorderPane();

		controlPane = new VBox();
		controlPane.setPadding(new Insets(10, 10, 10, 10));
		controlPane.setSpacing(20);
		controlPane.setPrefSize(334, 800);

		BackgroundFill backgroundFill = new BackgroundFill(Color.web("4a5759"), CornerRadii.EMPTY,
				javafx.geometry.Insets.EMPTY);
		Background background1 = new Background(backgroundFill);
		controlPane.setBackground(background1);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10); // Set horizontal gap between nodes
		gridPane.setVgap(10); // Set vertical gap between nodes
		gridPane.setPadding(new javafx.geometry.Insets(10));

		Label label1 = new Label("Source:");
		label1.setStyle("-fx-font-family: Arial; -fx-font-size: 14; -fx-font-weight: bold;-fx-text-fill: white;");
		Label label2 = new Label("Target:");
		label2.setStyle("-fx-font-family: Arial; -fx-font-size: 14; -fx-font-weight: bold;-fx-text-fill: white;");

		Sorce = new ComboBox<String>();
		Sorce.setStyle(
				"-fx-background-radius: 10;-fx-font-size: 14; -fx-background-color: #f9f7f3; -fx-text-fill: #333333;-fx-font-family: Arial; -fx-font-size: 14;-fx-font-weight: bold;");

		Target = new ComboBox<String>();
		Target.setStyle(
				"-fx-background-radius: 10;-fx-font-size: 14; -fx-background-color: #f9f7f3; -fx-text-fill: #333333;-fx-font-family: Arial; -fx-font-size: 14;-fx-font-weight: bold;");

		Image image = new Image("file:///C:/java-2/Algorithm_project3/1476732.png");
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(40);
		imageview.setFitWidth(40);
		runBtn = new Button("Run", imageview);
		runBtn.setStyle(
				"-fx-background-radius: 15;-fx-background-color: #f9f7f3; -fx-text-fill: black; -fx-font-family: Arial; -fx-font-size: 14;-fx-font-weight: bold; -fx-padding: 10px 20px;");
		runBtn.setOnAction(e -> RunbtnAction());

		Image image1 = new Image("file:///C:/java-2/Algorithm_project3/clear_ic.png");
		ImageView imageview1 = new ImageView(image1);
		imageview1.setFitHeight(40);
		imageview1.setFitWidth(40);

		clearbtn = new Button("Clear", imageview1);
		clearbtn.setStyle(
				"-fx-background-radius: 15;-fx-background-color: #f9f7f3; -fx-text-fill: black; -fx-font-family: Arial; -fx-font-size: 14;-fx-font-weight: bold; -fx-padding: 10px 20px;");

		clearbtn.setOnAction(e -> ClearbtnAction());

		gridPane.add(label1, 0, 0);
		gridPane.add(Sorce, 1, 0);
		gridPane.add(label2, 0, 1);
		gridPane.add(Target, 1, 1);

		Path = new TextArea();
		Path.setPromptText("Path...");
		Path.setStyle(
				"-fx-background-radius: 10;-fx-font-family: Arial; -fx-font-size: 14; -fx-font-weight: bold; -fx-border-color: lightgray; -fx-border-width: 2;");
		Path.setEditable(false);
		//textArea.setStyle("-fx-border-radius: 10; -fx-border-color: lightgray; -fx-border-width: 2;");

		VBox root = new VBox(10);
		Distance = new TextField();
		Distance.setPromptText("Distance...");
		Distance.setStyle(
				"-fx-background-radius: 10;-fx-font-family: Arial; -fx-font-size: 14; -fx-font-weight: bold;");
		Distance.setEditable(false);

		controlPane.getChildren().addAll(gridPane, runBtn, clearbtn, Path, Distance);

		Gaza = new ZoomableWorldMap(560, 650);
		//Gaza =new Pane();
		

		Image backgroundImage = new Image("file:///C:/java-2/Algorithm_project3/GazaMap.jpg"); // Replace

		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, javafx.scene.layout.BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		Gaza.setBackground(new Background(background));

	}

	public void GetCornerXY() {
		MinX = Gaza.localToScene(0, 0).getX();
		MinY = Gaza.localToScene(0, 0).getY();
		MaxX = Gaza.localToScene(Gaza.getWidth(), 0).getX();
		MaxY = Gaza.localToScene(Gaza.getWidth(), Gaza.getHeight()).getY();

		System.out.println("MinX:" + MinX);
		System.out.println("MinY:" + MinY);
		System.out.println("MaxX:" + MaxX);
		System.out.println("MaxY:" + MaxY);

	}

	public double getX(double Mx) {
		double x = ((((MaxX - MinX) * (Mx - MxMin)) / (MxMax - MxMin))) + MinX;
		return x;
	}

	public double getY(double My) {
		double x = ((((MaxY - MinY) * (My - MyMin)) / (MyMax - MyMin))) + MinY;
		return x;
	}

	public static double distance(double lat1, double lat2, double lon1, double lon2) {

		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// ãÚÇÏáÉ åÇÝÑÓÇíä
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		double r = 6371; // The radius of the Earth
		return (c * r);
	}

	private void LabelAnimation(Label l) {

		ScaleTransition scaleTransitionl1 = new ScaleTransition(Duration.millis(200), l); // bt1 effects
		ScaleTransition scaleTransitionl2 = new ScaleTransition(Duration.millis(200), l);
		l.setOnMouseEntered(e -> {
			scaleTransitionl1.setToX(2);
			scaleTransitionl1.setToY(2);
			scaleTransitionl1.play();
		});
		l.setOnMouseExited(e -> {
			scaleTransitionl2.setToX(1.0);
			scaleTransitionl2.setToY(1.0);
			scaleTransitionl2.play();
		});
	}

	private void drawLineSlowly(Line line, double endX, double endY) {// this method to
		line.setStrokeWidth(1.5);
		Color c = Color.web("0077b6");
		line.setStroke(c);
		line.setEndX(endX);
		line.setEndY(endY);
	}

	public static void main(String[] args) {
		launch();
	}
}
