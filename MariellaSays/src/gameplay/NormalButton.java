package gameplay;
import processing.core.PApplet;

public class NormalButton {

	private PApplet parent;
	private String label;
	private float x; // top left corner x position
	private float y; // top left corner y position
	private float w; // width of button
	private float h; // height of button

	public NormalButton(PApplet parent, String label, float x, float y, float w, float h) {
		super();
		this.parent = parent;
		this.label = label;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void Draw() {
		parent.fill(218);
		parent.stroke(141);
		parent.rect(x, y, w, h, 10);
		parent.textAlign(parent.CENTER, parent.CENTER);
		parent.fill(0);
		parent.text(label, x + (w / 2), y + (h / 2));
	}

	public boolean MouseIsOver() {
		if (parent.mouseX > x && parent.mouseX < (x + w) && parent.mouseY > y && parent.mouseY < (y + h)) {
			return true;
		}
		return false;
	}

}
