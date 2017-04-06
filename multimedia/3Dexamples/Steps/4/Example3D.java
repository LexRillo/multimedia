/**
 * Describe class (Step 4) Example3D here.
 *
 * Time-stamp: <2017-02-10 18:47:16 rlc3> edited by rlc 
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

public class Example3D extends JFrame {
    
          //The canvas to be drawn upon.
  	  public Canvas3D myCanvas3D;

    public BranchGroup createSceneGraph() {

	// creating the bounds of the universe
	// see mouse behaviour below 
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
 
	// creating a branch group
	BranchGroup objRoot = new BranchGroup();

	// creating a transform group 
	TransformGroup mainTG = new TransformGroup();		
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

	// creating another transform group (cubeTG1, from t)
        // first creating a transformation t 
	Transform3D t = new Transform3D();
	// SET matrix scale components to (2,2,2) 
        // SET matrix translate components to (0,0,-5)
	// ORDER of listing of SET commands does not matter below !! 
        t.setScale(new Vector3d(2.0, 2.0 ,2.0));
        t.setTranslation(new Vector3d(0.0, 0.0 ,-5.0));
        // remember the effect is scale by 2 THEN translate -5 along z-axis !!
	// you can also use this: t.set(2, new Vector3d(0.0, 0.0 ,-5));
	TransformGroup cubeTG1 = new TransformGroup(t);

	// creating another transform group (cubeTG2, also from t)
	// !!! previous (matrix of) t gets overwritten !!! 
	// arguably this is poor coding; best create a new transform say tt 
	t.rotZ(Math.PI/2);
	TransformGroup cubeTG2 = new TransformGroup(t);
	
	// creating an appearance 
	Appearance greenApp = new Appearance();
	Color3f greenColor = new Color3f(0.0f, 1.0f, 0.0f);
	ColoringAttributes greenCA = new ColoringAttributes();
	greenCA.setColor(greenColor);
	greenApp.setColoringAttributes(greenCA);

        // create a box
	Box b = new Box(0.8f,0.8f,.1f,greenApp);

	// creating 3D shapes: colorcubes
        // try altering the ".5" sizing parameter
	ColorCube c = new ColorCube(.5);
	ColorCube c2 = new ColorCube(.5);

        // make edge relations between the scene graph nodes
	// cube c is moved -5 along z axis (by cubeTG1)
        // cube c2 and box b at the origin 
	objRoot.addChild(mainTG);
	mainTG.addChild(cubeTG1);
	cubeTG1.addChild(cubeTG2);
	cubeTG2.addChild(c);
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
    public Example3D() {

        //Mechanism for closing the window and ending the program.
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration() );
	getContentPane().add("Center", myCanvas3D);
        //Construct the SimpleUniverse:
        //First generate it using the Canvas.
        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

    	// *** create a viewing platform
	TransformGroup cameraTG = simpUniv.getViewingPlatform().
	                            getViewPlatformTransform();
	// starting postion of the viewing platform
	Vector3f translate = new Vector3f(); 
      	Transform3D T3D = new Transform3D();
	// move along z axis by 10.0f ("move away from the screen") 
	translate.set( 0.0f, 0.0f, 10.0f);
        T3D.setTranslation(translate);
	cameraTG.setTransform(T3D);

        //The scene is generated in this method.
        BranchGroup scene = createSceneGraph();
        //Add everything to the universe.
        simpUniv.addBranchGraph(scene);
        addLight(simpUniv);
        setTitle("Step 4: A cube+green box and translated scaled rotated cube");
        setSize(700,700);
        setVisible(true);

    }

    public static void main(String[] args) {      
  
	Example3D step4 = new Example3D();

    }
    
  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    /* LIGHTING --------------------------------- */

    BranchGroup bgLight = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

	// Set up the global lights
	// first the ambient light
	// light from all directions
	// typically use white or 'gray' light
	Color3f alColor = new Color3f(0.6f, 0.6f, 0.6f);
	AmbientLight aLgt = new AmbientLight(alColor);
	aLgt.setInfluencingBounds(bounds);
        bgLight.addChild(aLgt);

       // next the directional light
       // parallel light rays come FROM infinity TOWARDS the vector lightDir1 
       Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
       // try out x, y and z each being + or - 1, and other coords = 0 
       // with (0,1,0) the bottom of the cube should be lit
       // Vector3f lightDir1  = new Vector3f(0.0f,1.0f,0.0f);
       Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
       DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
       // light has no effect, ie Influence, outside of the bounds 
       light1.setInfluencingBounds(bounds);
       bgLight.addChild(light1);

       Vector3f lightDir2  = new Vector3f(1.0f,-1.0f,0.5f);
       DirectionalLight light2 = new DirectionalLight(lightColour1, lightDir2);
       light2.setInfluencingBounds(bounds);
       bgLight.addChild(light2);

       su.addBranchGraph(bgLight);
        
    /* END LIGHTING --------------------------------- */ 

  } // ------------------ end addLight

}
