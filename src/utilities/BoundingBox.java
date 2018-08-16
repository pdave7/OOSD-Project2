/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 */
package utilities;

import org.newdawn.slick.Image;

public class BoundingBox {
	private float left;
	private float top;
	private float width;
	private float height;

	public BoundingBox(float top, float left, float width, float height) {
		this.width = width;
		this.height = height;
		this.setLeft(left);
		this.setTop(top);
	}

	public BoundingBox(Image img, float top, float left) {
		width = img.getWidth();
		height = img.getHeight();
		this.setTop(top);
		this.setLeft(left);
	}

	public BoundingBox(BoundingBox bb) {
		width = bb.width;
		height = bb.height;
		left = bb.left;
		top = bb.top;
	}

	/*
	 * Sets the x and y position at the centre of the bounding box.
	 */
	public void setLeft(float left) {
		this.left = left;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public void setWidth(float w) {
		width = w;
	}

	public void setHeight(float h) {
		height = h;
	}

	public float getLeft() {
		return left;
	}

	public float getTop() {
		return top;
	}

	public float getRight() {
		return left + width;
	}

	public float getBottom() {
		return top + height;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean intersects(BoundingBox other) {
		return !(other.left > getRight() || other.getRight() < left || other.top > getBottom()
				|| other.getBottom() < top);
	}
}
