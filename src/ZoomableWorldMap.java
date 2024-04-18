import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class ZoomableWorldMap extends Pane {
    private double originalWidth;
    private double originalHeight;
    private double scaleValue = 1.0;
    private double startX, startY;

    public ZoomableWorldMap(double originalWidth, double originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        initialize();
    }

    private void initialize() {
        setOnScroll((ScrollEvent event) -> {
            double scaleFactor = 1.1;
            if (event.getDeltaY() < 0) {
                // Zoom out
                scaleValue /= scaleFactor;
            } else {
                // Zoom in
                scaleValue *= scaleFactor;
            }

            // Restrict zoom to original size
            double scaledWidth = originalWidth * scaleValue;
            double scaledHeight = originalHeight * scaleValue;

            if (scaledWidth < originalWidth || scaledHeight < originalHeight) {
                scaleValue = Math.max(originalWidth / originalWidth, originalHeight / originalHeight);
            }

            setScaleX(scaleValue);
            setScaleY(scaleValue);
            event.consume();
        });

        setOnMousePressed(event -> {
            startX = event.getSceneX();
            startY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - startX;
            double deltaY = event.getSceneY() - startY;

            double maxX = (scaledWidth() - getWidth()) / 2;
            double minX = -maxX;
            double maxY = (scaledHeight() - getHeight()) / 2;
            double minY = -maxY;

            double newX = clamp(getTranslateX() + deltaX, minX, maxX);
            double newY = clamp(getTranslateY() + deltaY, minY, maxY);

            setTranslateX(newX);
            setTranslateY(newY);

            startX = event.getSceneX();
            startY = event.getSceneY();
        });
    }

    private double scaledWidth() {
        return originalWidth * scaleValue;
    }

    private double scaledHeight() {
        return originalHeight * scaleValue;
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }
}
