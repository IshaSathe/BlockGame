import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;


public class SmartBox extends Box {
	
	Rotate r;
	
	public void rotateByX(double ang) {
		r = new Rotate(ang, Rotate.X_AXIS);
		this.getTransforms().addAll(r);
	}
	
	public void rotateByY(double ang) {
		r = new Rotate(ang, Rotate.Y_AXIS);
		this.getTransforms().addAll(r);
	}
	
	public void rotateByZ(double ang) {
		r = new Rotate(ang, Rotate.Z_AXIS);
		this.getTransforms().add(r);
	}
	public void moveY(double num) {
		this.translateYProperty().set(this.translateYProperty().get() + num);
	}
	
	public void moveX(double num) {
		this.translateXProperty().set(this.translateXProperty().get() + num);
	}

	public void moveZ(double num) {
		this.translateZProperty().set(this.translateZProperty().get() + num);
	}

}
