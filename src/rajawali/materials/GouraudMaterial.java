package rajawali.materials;

import android.graphics.Color;
import android.opengl.GLES20;


public class GouraudMaterial extends AAdvancedMaterial {
	protected static final String mVShader = 
		"uniform mat4 uMVPMatrix;\n" +
		"uniform mat3 uNMatrix;\n" +
		"uniform mat4 uMMatrix;\n" +
		"uniform mat4 uVMatrix;\n" +
		"uniform vec4 uAmbientColor;\n" +
		"uniform vec4 uAmbientIntensity;\n" +
		
		"attribute vec4 aPosition;\n" +
		"attribute vec3 aNormal;\n" +
		"attribute vec2 aTextureCoord;\n" +
		"attribute vec4 aColor;\n" +
		
		"varying vec2 vTextureCoord;\n" +
		"varying float vSpecularIntensity;\n" +
		"varying float vDiffuseIntensity;\n" +
		"varying vec4 vColor;\n" +
		
		M_LIGHTS_VARS +
		
		"\n#ifdef VERTEX_ANIM\n" +
		"attribute vec4 aNextFramePosition;\n" +
		"attribute vec3 aNextFrameNormal;\n" +
		"uniform float uInterpolation;\n" +
		"#endif\n\n" +
		
		"void main() {\n" +
		"	vec4 position = aPosition;\n" +
		"	vec3 normal = aNormal;\n" +
		"	#ifdef VERTEX_ANIM\n" +
		"	position = aPosition + uInterpolation * (aNextFramePosition - aPosition);\n" +
		"	normal = aNormal + uInterpolation * (aNextFrameNormal - aNormal);\n" +
		"	#endif\n" +
		
		"	gl_Position = uMVPMatrix * position;\n" +
		"	vTextureCoord = aTextureCoord;\n" +
		
		"	vec4 vertexPosCam = uMMatrix * position;\n" +
		"	vec3 normalCam = normalize(uNMatrix * normal);\n" +
		
		"	for(int i=0; i<" +MAX_LIGHTS+ "; i++) {" +
		"		vec4 lightPosCam = vec4(uLightPos[i], 1.0);\n" +

		"		vec3 lightVert = normalize(vec3(lightPosCam - vertexPosCam));\n" +
		"		vec3 lightRefl = normalize(reflect(lightVert, normalCam));\n" +

		"		vDiffuseIntensity += uLightPower[i] * max(dot(lightVert, normalCam), 0.0);\n" +
		"		float intens = uLightPower[i] * max(dot(lightRefl, normalize(vec3(vertexPosCam))), 0.0);\n" +
		"		vSpecularIntensity += pow(intens, 6.0);\n" +
		"	}" +
		"	vSpecularIntensity = clamp(vSpecularIntensity, 0.0, 1.0);" +
		"	vColor = aColor;\n" +
		"}";
		
	protected static final String mFShader = 
		"precision mediump float;\n" +

		"varying vec2 vTextureCoord;\n" +
		"varying float vSpecularIntensity;\n" +
		"varying float vDiffuseIntensity;\n" +
		"varying vec4 vColor;\n" +
		
		"uniform sampler2D uDiffuseTexture;\n" +
		"uniform bool uUseTexture;\n" +
		"uniform vec4 uAmbientColor;\n" +
		"uniform vec4 uAmbientIntensity;\n" + 
		"uniform vec4 uSpecularColor;\n" +
		"uniform vec4 uSpecularIntensity;\n" +
		
		"void main() {\n" +
		"	vec4 texColor = uUseTexture ? texture2D(uDiffuseTexture, vTextureCoord) : vColor;\n" +
		"	gl_FragColor = texColor * vDiffuseIntensity + uSpecularColor * vSpecularIntensity * uSpecularIntensity;\n" +
		"	gl_FragColor.a = texColor.a;\n" +
		"	gl_FragColor += uAmbientColor * uAmbientIntensity;" +
		"}";
	
	protected int muSpecularColorHandle;
	protected int muSpecularIntensityHandle;
	protected float[] mSpecularColor;
	protected float[] mSpecularIntensity;
	
	public GouraudMaterial() {
		this(false);
	}
	
	public GouraudMaterial(boolean isAnimated) {
		super(mVShader, mFShader, isAnimated);
		mSpecularColor = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		mSpecularIntensity = new float[] { .2f, .2f, .2f, 1.0f };
	}
	
	public GouraudMaterial(float[] specularColor) {
		this();
		mSpecularColor = specularColor;
	}

	@Override
	public void useProgram() {
		super.useProgram();
		GLES20.glUniform4fv(muSpecularColorHandle, 1, mSpecularColor, 0);
		GLES20.glUniform4fv(muSpecularIntensityHandle, 1, mSpecularIntensity, 0);
	}
	
	public void setSpecularColor(float[] color) {
		mSpecularColor = color;
	}
	
	public void setSpecularColor(float r, float g, float b, float a) {
		mSpecularColor[0] = r;
		mSpecularColor[1] = g;
		mSpecularColor[2] = b;
		mSpecularColor[3] = a;
	}
	
	public void setSpecularColor(int color) {
		setSpecularColor(Color.red(color) / 255f, Color.green(color) / 255f, Color.blue(color) / 255f, Color.alpha(color) / 255f);
	}
	
	public void setSpecularIntensity(float[] intensity) {
		mSpecularIntensity = intensity;
	}
	
	public void setSpecularIntensity(float r, float g, float b, float a) {
		mSpecularIntensity[0] = r;
		mSpecularIntensity[1] = g;
		mSpecularIntensity[2] = b;
		mSpecularIntensity[3] = a;
	}
	
	@Override
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muSpecularColorHandle = getUniformLocation("uSpecularColor");
		muSpecularIntensityHandle = getUniformLocation("uSpecularIntensity");
	}
}