/**
 * Describe class (Step1) Example3D here.
 *
 * Time-stamp: <2017-02-10 18:35:21 rlc3>
 *
 * R. L. Crole 
 * 
 */

import java.awt.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.mouse.*; 
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Box;
import javax.swing.JFrame;

public class Example3D extends JFrame {
        
     // create a "standard" universe using SimpleUniverse 
    public Example3D() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container cp = getContentPane();
	cp.setLayout(new BorderLayout());
	Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration() );
	cp.add("Center", c);
	BranchGroup scene = createSceneGraph();
	SimpleUniverse u = new SimpleUniverse(c);
	u.getViewingPlatform().setNominalViewingTransform();
	u.addBranchGraph(scene);

    	// *** create a viewing platform
	TransformGroup cameraTG = u.getViewingPlatform().
	                            getViewPlatformTransform();
	// starting postion of the viewing platform
	Vector3f translate = new Vector3f(); 
      	Transform3D T3D = new Transform3D();
	// move along z axis by 10.0f ("move away from the screen") 
	translate.set( 0.0f, 0.0f, 10.0f);
        T3D.setTranslation(translate);
	cameraTG.setTransform(T3D);
        setTitle("Step 1: A simple cube");
        setSize(512,512);
        setVisible(true);
    }

    public BranchGroup createSceneGraph() {

	// creating the bounds of the universe
	// see mouse behaviour below 
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 10.0);
	
 	// creating the (single) branch group
	BranchGroup objRoot = new BranchGroup();
	
        // creating the transform group for the (one) branchgroup  
	TransformGroup mainTG = new TransformGroup();		
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	
	// create a 3D shape: colorcube
	ColorCube c = new ColorCube();
  
        // make edge relations with the scene graph nodes
	objRoot.addChild(mainTG);
	mainTG.addChild(c);
	
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
 
    public static void main(String[] args) {        

	Example3D step1 = new Example3D();

    }
    
}
