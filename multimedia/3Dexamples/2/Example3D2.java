/**
 * Describe class (scene graph example 1) Example3D here.
 *
 * Time-stamp: <2017-02-10 18:53:14 rlc3> edited by rlc 
 *
 */

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import com.sun.j3d.utils.behaviors.mouse.*; 
import javax.swing.JFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Box;

public class Example3D2 extends JFrame {
    
    public BranchGroup createSceneGraph() {

	// creating the bounds of the universe
	// see mouse behaviour below 
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
 
	// creating branch groups
	BranchGroup objRoot = new BranchGroup();
	BranchGroup bgA = new BranchGroup();
	BranchGroup bgB = new BranchGroup();

	// creating a main transform group (inherits mouse behavior) 
	TransformGroup mainTG = new TransformGroup();		
	// set the capability to TRANSFORM the main TG (eg mouse MOVEMENT) 
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        // top-level transform groups for bgA and bgB
	TransformGroup TGA = new TransformGroup();		
	// set the capability to TRANSFORM the TGA (by ROTATION) 
	TGA.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	TGA.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	TransformGroup TGB = new TransformGroup();		
	objRoot.addChild(mainTG);
        mainTG.addChild(bgA);
        mainTG.addChild(bgB);
        bgA.addChild(TGA);
        bgB.addChild(TGB);

	// creating a rotation interpolator for TGA 
        Transform3D yAxis = new Transform3D();
        // rotate the y axis pi/2 around x axis 
        // so the y axis "becomes" the z axis
        // hence bgA rotates around the z axis 
        Transform3D zAxis = new Transform3D();
        yAxis.rotX(Math.PI/2); 
        zAxis = yAxis; // use SENSIBLE VARIABLE NAMES !!
        Alpha rotationAlphaTGA = new Alpha(-1, 4000);
        // this rotator is for TGA - cubes c and co
        RotationInterpolator rotatorTGA = new RotationInterpolator(rotationAlphaTGA,
         TGA, zAxis, 0.0f, (float) Math.PI * (2.0f));
         rotatorTGA.setSchedulingBounds(bounds);

	// creating a transform group (cubeTG1, from t)
        // first creating a transformation t 
	Transform3D t = new Transform3D();
        t.setTranslation(new Vector3d(0.0, 0.0 ,-5));
	TransformGroup cubeTG1 = new TransformGroup(t);

	// creating a transform group (cubeTG2, from tt)
	Transform3D tt = new Transform3D();
        tt.setScale(new Vector3d(2.0, 2.0 ,2.0));
	TransformGroup cubeTG2 = new TransformGroup(tt);

	// creating another transform group (boxTG1, from ttt)
        // first creating a transformation ttt
	Transform3D ttt = new Transform3D();
        // translate 3 along x-axis 
        ttt.setTranslation(new Vector3d(3.0, 0.0 ,0.0));
	TransformGroup boxTG1 = new TransformGroup(ttt);

	// creating a rotation interpolator for a new boxTG2
	TransformGroup boxTG2 = new TransformGroup();
	// set capability for TRANSFORM boxTG2 (by ROTATION)
	boxTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	boxTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        Transform3D yAxisboxTG2 = new Transform3D();
        Alpha rotationAlphaboxTG2 = new Alpha(-1, 4000);
	      // you can set the time for the Alpha object to begin (eg after 10 seconds) 
	      // rotationAlphaboxTG2.setStartTime(System.currentTimeMillis() + 10000);
        // this rotator is for boxTG2 ie box b 
        RotationInterpolator rotatorboxTG2 = new RotationInterpolator(rotationAlphaboxTG2,
         boxTG2, yAxisboxTG2, 0.0f, (float) Math.PI * (2.0f));
         rotatorboxTG2.setSchedulingBounds(bounds);

	// creating an appearance (for a box)
	Appearance groundApp = new Appearance();
	Color3f groundColor = new Color3f(0.0f, 1.0f, 0.0f);
	ColoringAttributes groundCA = new ColoringAttributes();
	groundCA.setColor(groundColor);
	groundApp.setColoringAttributes(groundCA);

        // create a box
	Box b = new Box(0.8f,0.8f,.1f,groundApp);
	// creating 3D shapes: colorcubes
        // try altering the ".5" sizing parameter
	ColorCube c = new ColorCube(.5);
	ColorCube co = new ColorCube(.5);

        // make edge relations between the scene graph nodes
	TGA.addChild(cubeTG1);
	cubeTG1.addChild(cubeTG2);
        cubeTG2.addChild(c);
        TGA.addChild(rotatorTGA);
	TGA.addChild(co);
        TGB.addChild(boxTG1); 
        boxTG1.addChild(boxTG2);
        boxTG2.addChild(b);
        boxTG2.addChild(rotatorboxTG2);

	// Create the rotate behavior node
	MouseRotate behavior = new MouseRotate();
	behavior.setTransformGroup(mainTG);
	objRoot.addChild(behavior);
	behavior.setSchedulingBounds(bounds);
	
	// Create the zoom behavior node
	MouseZoom behavior2 = new MouseZoom();
	behavior2.setTransformGroup(mainTG);
	objRoot.addChild(behavior2);	
        behavior2.setSchedulingBounds(bounds);
	
	// Create the translate behavior node
	 MouseTranslate behavior3 = new MouseTranslate();
	behavior3.setTransformGroup(mainTG);
	objRoot.addChild(behavior3);
	behavior3.setSchedulingBounds(bounds);

	objRoot.compile();
	return objRoot;
    }
 
    // create a "standard" universe using SimpleUniverse 
    public Example3D2() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container cp = getContentPane();
	cp.setLayout(new BorderLayout());
	Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration() );
	cp.add("Center", c);
	BranchGroup scene = createSceneGraph();
	SimpleUniverse u = new SimpleUniverse(c);
	u.getViewingPlatform().setNominalViewingTransform();
	u.addBranchGraph(scene);
        setTitle("Scene Graph 1");
        setSize(700,700);
        setVisible(true);
    }

    public static void main(String[] args) {        

	Example3D sg1 = new Example3D();

    }
    
}
