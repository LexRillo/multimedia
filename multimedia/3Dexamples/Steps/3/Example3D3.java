/**
 * Describe class (Step 3) Example3D here.
 *
 * Time-stamp: <2017-02-10 18:37:22 rlc3> edited by rlc 
 *
 */

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import com.sun.j3d.utils.behaviors.mouse.*; 
import javax.swing.JFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Box;

public class Example3D3 extends JFrame {
    
    public BranchGroup createSceneGraph() {

	// creating the bounds of the universe
	// see mouse behaviour below 
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
 
	// creating a single branch group
	BranchGroup objRoot = new BranchGroup();

	// creating the main transform group 
	TransformGroup mainTG = new TransformGroup();		
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

	// creating another transform group (cubeTG, from t)
        // first creating a transformation t 
	// t is identity matrix when first created 
	Transform3D t = new Transform3D(); 
	// SET matrix scale components to (2,2,2) 
        // SET matrix translate components to (0,0,-5)
	// ORDER of listing of SET commands does not matter below !! 
        t.setScale(new Vector3d(2.0, 2.0 ,2.0));
        t.setTranslation(new Vector3d(0.0, 0.0 ,-5.0));
	// Let T be the matrix for the translation; ditto S. Then ... 
	// !!****! The matrix for t is M_t = T . S !!! WHY? !!****! 
        // so the effect is scale by 2 THEN translate -5 along z-axis !!

	// How to get the entries of the matrix of t 
	Matrix4d M_t = new Matrix4d(); 
        t.get(M_t); 
	// element at (row = 2, col = 3) = -5
        System.out.println("element of M_t at (2,3) = "+M_t.getElement(2,3));

	/* TRY THIS OUT: -----------------------------------  I dont get it but whatever

	 Transform3D ST = new Transform3D(); 
	 Transform3D TS = new Transform3D(); 
         Transform3D scale = new Transform3D(); 
         scale.setScale(new Vector3d(2.0, 2.0 ,2.0));
         Transform3D trans = new Transform3D(); 
         trans.setTranslation(new Vector3d(0.0, 0.0 ,-5.0));
  	 ST.mul(scale);
  	 ST.mul(trans); // M_ST := id . M_scale . M_trans      
  	 TS.mul(trans);
         TS.mul(scale); // M_TS := id . M_trans . M_scale 
     	 Matrix4d M_ST = new Matrix4d(); 
     	 Matrix4d M_TS = new Matrix4d(); 
  	 ST.get(M_ST); 
	 // element at (row = 2, col = 3) = -10
         System.out.println("element of M_ST at (2,3) = "+(M_ST).getElement(2,3));
  	 TS.get(M_TS); 
	 // element at (row = 2, col = 3) = -5 
         System.out.println("element of M_TS at (2,3) = "+(M_TS).getElement(2,3));

	END TRY THIS OUT: ----------------------------------- */

	TransformGroup cubeTG = new TransformGroup(t);

	// creating an appearance 
	Appearance greenApp = new Appearance();
	Color3f greenColor = new Color3f(0.0f, 1.0f, 0.0f);
	ColoringAttributes greenCA = new ColoringAttributes();
	greenCA.setColor(greenColor);
	greenApp.setColoringAttributes(greenCA);

        // create a box with that appearance 
	Box b = new Box(0.8f,0.8f,.1f,greenApp);

	// creating 3D shapes: colorcubes
        // try altering the ".5" sizing parameter
	ColorCube c = new ColorCube(.5);
	ColorCube c2 = new ColorCube(.5);

        // make edge relations between the scene graph nodes
	// cube c is scaled by 2 and THEN moved -5 along z axis (by cubeTG)
        // cube c2 and box b at the origin 
	objRoot.addChild(mainTG);
	mainTG.addChild(cubeTG);
	cubeTG.addChild(c);
	mainTG.addChild(b);
	mainTG.addChild(c2);
	
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
    public Example3D3() {
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
        setTitle("Step 3: A cube+green box and translated scaled cube");
        setSize(512,512);
        setVisible(true);
    }

    public static void main(String[] args) {        

	Example3D3 step3 = new Example3D3();

    }
    
}
