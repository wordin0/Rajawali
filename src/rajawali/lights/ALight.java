package rajawali.lights;

import rajawali.ITransformable3D;
import rajawali.math.Number3D;

public abstract class ALight implements ITransformable3D {
	protected float[] mColor = new float[] { 1.0f, 1.0f, 1.0f };
	protected Number3D mPosition, mRotation;
	protected float mPower = .5f;
	
	protected boolean mUseObjectTransform;
	
	public ALight() {
		mPosition = new Number3D();
		mRotation = new Number3D();
	}
	
	public void setColor(final float r, final float g, final float b) {
		mColor[0] = r; mColor[1] = g; mColor[2] = b;
	}
	
	public float[] getColor() {
		return mColor;
	}
	
	public void setPower(float power) {
		mPower = power;
	}
	
	public float getPower() {
		return mPower;
	}

	public boolean shouldUseObjectTransform() {
		return mUseObjectTransform;
	}

	public void shouldUseObjectTransform(boolean useObjectTransform) {
		this.mUseObjectTransform = useObjectTransform;
	}
	
	public void setPosition(float x, float y, float z) {
		mPosition.x = x;
		mPosition.y = y;
		mPosition.z = z;
	}

	public void setPosition(Number3D position) {
		mPosition = position;
	}

	public Number3D getPosition() {
		return mPosition;
	}

	public Number3D getRotation() {
		return mRotation;
	}

	public float getX() {
		return mPosition.x;
	}

	public void setX(float x) {
		mPosition.x = x;
	}

	public float getY() {
		return mPosition.y;
	}

	public void setY(float y) {
		mPosition.y = y;
	}

	public float getZ() {
		return mPosition.z;
	}

	public void setZ(float z) {
		mPosition.z = z;
	}

	public void setRotation(float rotX, float rotY, float rotZ) {
		mRotation.x = rotX;
		mRotation.y = rotY;
		mRotation.z = rotZ;
	}

	public void setRotX(float rotX) {
		mRotation.x = rotX;
	}

	public float getRotX() {
		return mRotation.x;
	}

	public void setRotY(float rotY) {
		mRotation.y = rotY;
	}

	public float getRotY() {
		return mRotation.y;
	}

	public void setRotZ(float rotZ) {
		mRotation.z = rotZ;
	}

	public float getRotZ() {
		return mRotation.z;
	}
	
	public void setScale(float scale) {}

	public void setScale(float scaleX, float scaleY, float scaleZ) {}

	public void setScaleX(float scaleX) {}

	public float getScaleX() { return 0; }

	public void setScaleY(float scaleY) {}

	public float getScaleY() { return 0; }

	public void setScaleZ(float scaleZ) {}

	public float getScaleZ() { return 0; }

	public void setRotation(Number3D rotation) {
		mRotation = rotation;
	}

	public Number3D getScale() { return null; }

	public void setScale(Number3D scale) {}
	
	public void setLookAt(float x, float y, float z) {}
	public void setLookAt(Number3D lookAt) {}
}
