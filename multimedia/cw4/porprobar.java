package cw4;
import java.applet.*;
import java.awt.*;

import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import cw4.movementtrial.CollisionBehaviour;

import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.behaviors.keyboard.*;

import com.sun.j3d.loaders.Scene;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.*;

import com.sun.j3d.utils.geometry.*;

public class porprobar extends Applet {

	private SimpleUniverse universe = null;
	private Canvas3D canvas = null;
	private TransformGroup viewtrans = null;
	
	public porprobar() {
		setLayout(new BorderLayout());
		  GraphicsConfiguration config = SimpleUniverse
		    .getPreferredConfiguration();

		  canvas = new Canvas3D(config);
		  add("Center", canvas);
		  universe = new SimpleUniverse(canvas);

		  BranchGroup scene = createSceneGraph();
		  universe.getViewingPlatform().setNominalViewingTransform();

		  universe.getViewer().getView().setBackClipDistance(100.0);
		  universe.addBranchGraph(scene);	
	
	}
	
	private BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();

		  BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);

		  viewtrans = universe.getViewingPlatform().getViewPlatformTransform();

		  KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(viewtrans);
		  keyNavBeh.setSchedulingBounds(bounds);
		  PlatformGeometry platformGeom = new PlatformGeometry();
		  platformGeom.addChild(keyNavBeh);
		  universe.getViewingPlatform().setPlatformGeometry(platformGeom);
		  
		  objRoot.addChild(createScene());

		  return objRoot;
	}
	
	private BranchGroup createScene(){
		
		BranchGroup objRoot = new BranchGroup();
		
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
		
		t3d.rotZ(-(Math.PI/2));
		tg.setTransform(t3d);
		
		tg.addChild(createMovingTrain());
		tg.addChild(createTracks());
		tg.addChild(createRock());
		
		objRoot.addChild(tg);
		objRoot.compile();
		return objRoot;
	}
	
	
	private Appearance createAppearance(Color3f col) {
	  Appearance ap = new Appearance();
	  Material ma = new Material();
	  ma.setDiffuseColor(col);
	  ma.setEmissiveColor(col);
	  ap.setMaterial(ma);
	  return ap;
	}
	
	private Appearance createAppearance(Color3f col, float shine) {
		  Appearance ap = new Appearance();
		  Material ma = new Material();
		  ma.setDiffuseColor(col);
		  ma.setEmissiveColor(col);
		  //shininess goes from 1.0 to 128.0 and default is 64.0
		  ma.setShininess(shine);
		  ap.setMaterial(ma);
		  return ap;
	}
	
	
	
	private BranchGroup createLocomotor() {
	
	BranchGroup objRoot = new BranchGroup();
	
	  TransformGroup tg = new TransformGroup();
	  Transform3D t3d = new Transform3D();
	
	  t3d.setTranslation(new Vector3d(0.0, 0.0, -15.0));
	  tg.setTransform(t3d);
	
	  //front "bulb" of the train
	  TransformGroup tg_sp = new TransformGroup();
	  Transform3D t3d_sp = new Transform3D();
	  
	  t3d_sp.setTranslation(new Vector3d(0.0, 1.0, 0.0));
	  tg_sp.setTransform(t3d_sp);
	
	  Appearance ap_sp = createAppearance(new Color3f(0.04f, 0.34f, 0.93f));
	  tg_sp.addChild(new Sphere(0.5f, ap_sp));
	  
	  //main body of the train  
	  TransformGroup tg_cilinder = new TransformGroup();
	  Transform3D t3d_cilinder = new Transform3D();
	
	  t3d_cilinder.setTranslation(new Vector3d(0.0, 0.0, 0.0));
	  tg_cilinder.setTransform(t3d_cilinder);
	  
	  Appearance ap_cilinder = createAppearance(new Color3f(0.04f, 0.34f, 0.93f));
	  tg_cilinder.addChild(new Cylinder(0.5f, 2.0f, ap_cilinder));
	  
	  //Cabin of locomotor
	  TransformGroup tg_cube = new TransformGroup();
	  Transform3D t3d_cube = new Transform3D();
	  
	  t3d_cube.setTranslation(new Vector3d(-0.2, -1.0, 0.0));
	  tg_cube.setTransform(t3d_cube);
	  
	  Appearance ap_cube = createAppearance(new Color3f(0.04f, 0.34f, 0.93f), 85.0f);
	  tg_cube.addChild(new Box(0.7f, 0.5f, 0.5f, ap_cube));
	  
	  //Chimney
	  TransformGroup tg_cone = new TransformGroup();
	  Transform3D t3d_cone = new Transform3D();
	  t3d_cone.rotZ(-(Math.PI/2));
	  t3d_cone.setTranslation(new Vector3d(-0.6, 0.9, 0.0));
	  tg_cone.setTransform(t3d_cone);
	  
	  Appearance ap_cone = createAppearance(new Color3f(0.04f, 0.34f, 0.93f), 30.0f);
	  tg_cone.addChild(new Cone(0.15f, 0.5f, ap_cone));
	  
	  
	  tg.addChild(tg_sp);
	  tg.addChild(tg_cilinder);
	  tg.addChild(tg_cube);
	  tg.addChild(tg_cone);
	  objRoot.addChild(tg);
	  
	  objRoot.addChild(createLight());
	  objRoot.compile();
	  return objRoot;
	}
	
	private BranchGroup createWheel(){
		BranchGroup objRoot = new BranchGroup();
		BoundingSphere bounds =
			    new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000);
			
		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D t3d = new Transform3D();
		Alpha rotationAlpha1 = new Alpha(-1, 4000);
		RotationInterpolator rotator1 = new RotationInterpolator(rotationAlpha1,
				  tg, t3d, 0.0f, (float) -(Math.PI * (2.0f)));
		  rotator1.setSchedulingBounds(bounds);
		  Appearance ap_wheel1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
		  
		  tg.addChild(rotator1);
		  tg.addChild(new Cylinder(0.1f, 1.0f, ap_wheel1));
		  
		  objRoot.addChild(tg);
		  
		  objRoot.compile();
		  return objRoot;
		  
	}
	
	
	private BranchGroup createWheels(){
		
	BranchGroup objRoot = new BranchGroup();
	BoundingSphere bounds =
		    new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000);
		
	TransformGroup tg = new TransformGroup();
	Transform3D t3d = new Transform3D();
		
	t3d.setTranslation(new Vector3d(0.0, 0.0, -15.0));
	tg.setTransform(t3d);
	
	  //wheel1
	  TransformGroup tg_wheel1 = new TransformGroup();	  
      Transform3D t3d_wheel1 = new Transform3D();
      
      t3d_wheel1.rotX(Math.PI/2);
      t3d_wheel1.setTranslation(new Vector3d(0.6, 0.9, 0.0));
      tg_wheel1.setTransform(t3d_wheel1);
      
      tg_wheel1.addChild(createWheel());
    		  
	  //wheel2
	  TransformGroup tg_wheel2 = new TransformGroup();
	  Transform3D t3d_wheel2 = new Transform3D();
	  
	  t3d_wheel2.rotX(Math.PI/2);
	  t3d_wheel2.setTranslation(new Vector3d(0.6, -0.1, 0.0));
	  tg_wheel2.setTransform(t3d_wheel2);
	  
	  tg_wheel2.addChild(createWheel());
	  //wheel3
	  TransformGroup tg_wheel3 = new TransformGroup();
	  Transform3D t3d_wheel3 = new Transform3D();
	  
	  t3d_wheel3.rotX(Math.PI/2);
	  t3d_wheel3.setTranslation(new Vector3d(0.6, -1.4, 0.0));
	  tg_wheel3.setTransform(t3d_wheel3);
	  
	  tg_wheel3.addChild(createWheel());
	  
	  tg.addChild(tg_wheel1);
	  tg.addChild(tg_wheel2);
	  tg.addChild(tg_wheel3);
	  objRoot.addChild(tg);
	  
	  objRoot.compile();
	  return objRoot;
	  
	}
	
	private BranchGroup createMovingTrain(){
		BranchGroup objRoot = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);
		
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
		
		t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		tg.setTransform(t3d);
		
		TransformGroup tg_train = new TransformGroup();
		
		tg_train.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha trainAlpha = new Alpha(1, 20000);
		//trainAlpha.setStartTime(200);
		Transform3D t3d_train = new Transform3D();
		t3d_train.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		t3d_train.rotZ((Math.PI/2));
		
		PositionInterpolator moveR = new PositionInterpolator(trainAlpha,tg_train,t3d_train, 0.0f, 190.0f);
		moveR.setSchedulingBounds(bounds);
		
		tg_train.setTransform(t3d_train);
		BranchGroup blueloco = createLocomotor();
		BranchGroup redloco = createLocomotor();
	    tg_train.addChild(blueloco);
		tg_train.addChild(createWheels());
//		Appearance ap_wheel1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
//		tg_train.addChild(new Box(2f, 2f, 2f, ap_wheel1));
		tg_train.addChild(moveR);
		tg.addChild(tg_train);
	   		
		objRoot.addChild(tg);
		objRoot.compile();
		return objRoot;
	}
	
	private BranchGroup createTracks(){

	 BranchGroup objRoot = new BranchGroup();

	 TransformGroup tg = new TransformGroup();
	 Transform3D t3d = new Transform3D();

	 t3d.setTranslation(new Vector3d(0.0, 0.0, -15.0));
	 tg.setTransform(t3d);
	 
	 
	 //first track
	 TransformGroup tg_track1 = new TransformGroup();
	 Transform3D t3d_track1 = new Transform3D();
	  
	 t3d_track1.setTranslation(new Vector3d(0.78, 95.0, 0.5));
	 tg_track1.setTransform(t3d_track1);
	  
	 Appearance ap_track1 = createAppearance(new Color3f(0.24f, 0.24f, 0.24f));
	 tg_track1.addChild(new Box(0.08f, 100.0f, 0.05f, ap_track1));
	 
	 
	 //second track
	 TransformGroup tg_track2 = new TransformGroup();
	 Transform3D t3d_track2 = new Transform3D();
	  
	 t3d_track2.setTranslation(new Vector3d(0.78, 95.0, -0.5));
	 tg_track2.setTransform(t3d_track2);
	  
	 Appearance ap_track2 = createAppearance(new Color3f(0.24f, 0.24f, 0.24f));
	 tg_track2.addChild(new Box(0.08f, 100.0f, 0.05f, ap_track2));
	 
	 
	 //position start of tracks
	 TransformGroup tg_startoftrack = new TransformGroup();
	 Transform3D t3d_startoftrack = new Transform3D();
	  
	 t3d_startoftrack.setTranslation(new Vector3d(0.0, -4.95, 0.0));
	 tg_startoftrack.setTransform(t3d_startoftrack);
	
	 tg_startoftrack.addChild(createEndsOfTrack());
	 
	 //position end of tracks
	 TransformGroup tg_endoftrack = new TransformGroup();
	 Transform3D t3d_endoftrack = new Transform3D();
	  
	 t3d_endoftrack.setTranslation(new Vector3d(0.0, 194.95, 0.0));
	 tg_endoftrack.setTransform(t3d_endoftrack);
	
	 tg_endoftrack.addChild(createEndsOfTrack());
	 
	 
	 
	 tg.addChild(tg_track1);
	 tg.addChild(tg_track2);
	 tg.addChild(tg_startoftrack);
	 tg.addChild(tg_endoftrack);
	 objRoot.addChild(tg);
	  
	 objRoot.compile();
	 return objRoot;
	}

	private BranchGroup createEndsOfTrack(){
		BranchGroup objRoot = new BranchGroup();

		 TransformGroup tg = new TransformGroup();
		 Transform3D t3d = new Transform3D();

		 t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		 tg.setTransform(t3d);
		 
	     //stands
		 //1
		 TransformGroup tg_stand1 = new TransformGroup();
		 Transform3D t3d_stand1 = new Transform3D();
		  
		 t3d_stand1.setTranslation(new Vector3d(0.2, 0.0, 0.5));//(0.2, -4.95, 0.5)
		 tg_stand1.setTransform(t3d_stand1);
		  
		 Appearance ap_stand = createAppearance(new Color3f(0.45f, 0.21f, 0.0f));
		 tg_stand1.addChild(new Box(0.5f, 0.05f, 0.05f, ap_stand));
		 
		 //2
		 TransformGroup tg_stand2 = new TransformGroup();
		 Transform3D t3d_stand2 = new Transform3D();
		  
		 t3d_stand2.setTranslation(new Vector3d(0.2, 0.0, -0.5));//(0.2, -4.95, -0.5)
		 tg_stand2.setTransform(t3d_stand2);

		 tg_stand2.addChild(new Box(0.5f, 0.05f, 0.05f, ap_stand));
		 
		 //stopper
		 TransformGroup tg_stopper = new TransformGroup();
		 Transform3D t3d_stopper = new Transform3D();
		  
		 t3d_stopper.setTranslation(new Vector3d(0.0, 0.0, 0.0));//(0.2, -4.95, -0.5)
		 tg_stopper.setTransform(t3d_stopper);
		 
		 Appearance ap_stopper = createAppearance(new Color3f(0.98f, 0.2f, 0.0f));
		 tg_stopper.addChild(new Box(0.3f, 0.05f, 0.5f, ap_stopper));
		 
		 
		 tg.addChild(tg_stand1);
		 tg.addChild(tg_stand2);
		 tg.addChild(tg_stopper);
		 
		 objRoot.addChild(tg);		  
		 objRoot.compile();
		 return objRoot;
	}
	
	private BranchGroup createRock(){
		
		BranchGroup objRoot = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000);
			
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
			
		t3d.setTranslation(new Vector3d(0.7, 192.2, -15.0));
		tg.setTransform(t3d);
		
		
		TransformGroup tg_rock = new TransformGroup();
		  tg_rock.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		  Transform3D t3d_rock = new Transform3D();
		  t3d_rock.rotX((Math.PI/8));
		  t3d_rock.rotY(-(Math.PI/2));
		  t3d_rock.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		  tg_rock.setTransform(t3d_rock);
		  
		Appearance ap_rock = createAppearance(new Color3f(0.48f, 0.27f, 0.0f));
		Appearance ap_rock2 = createAppearance(new Color3f(1.0f, 0.0f, 0.0f));
		
		Point3d[] vertexCoordinates = {
			new Point3d(-1.0,-1.0,0.0),
			new Point3d(1.0,-1.0,0.0),
			new Point3d(0.0,1.0,0.0),
			new Point3d(0.0,0.0,2.0)
		};
		
		int triangles[] = {
				0,2,1,  // Base – vertices[0], vertices[2], vertices[1]
				0,1,3,  // Side 1
				2,0,3,  // Side 2
				1,2,3   // Side 3
		};
		
		GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
		gi.setCoordinates(vertexCoordinates);
		gi.setCoordinateIndices(triangles);
		
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);
		
		GeometryArray ga = gi.getGeometryArray();
		Shape3D tetrahedron = new Shape3D(ga,ap_rock);
		
		//collision
		Shape3D redrock = new Shape3D(ga,ap_rock2);
		
		Switch colourSwitch = new Switch();
	    colourSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
	    colourSwitch.addChild(redrock); // child 0
	    colourSwitch.addChild(tetrahedron); // child 1
	    colourSwitch.setWhichChild(1);
	    tg_rock.addChild(colourSwitch);
	    
	  //The CollisionBounds for the spheres. 
	    colourSwitch.setCollisionBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),0.01f)); 
	    //Enabled for collision purposes
	    colourSwitch.setCollidable(true);
	    
	    CollisionBehaviour scb = new CollisionBehaviour(tetrahedron, colourSwitch,bounds);
	    tg.addChild(scb);
		tg.addChild(tg_rock);		
		
		objRoot.addChild(tg);
		  
		  objRoot.compile();
		  return objRoot;
	}


	class CollisionBehaviour extends Behavior {
		
		public WakeupOnCollisionEntry i_and_pS_criterion;

		public Shape3D locom;
		
		public Switch colourswitch;
		
		public CollisionBehaviour(Shape3D thlocom, Switch theSwitch, Bounds theBounds){
			locom = thlocom;
	        colourswitch = theSwitch;
	        setSchedulingBounds(theBounds);
	    }
		
		public void initialize(){
			
			i_and_pS_criterion = new WakeupOnCollisionEntry(locom);
			wakeupOn(i_and_pS_criterion);
		}
		
		public void processStimulus(Enumeration criteria){
			while (criteria.hasMoreElements())
		      {
		          WakeupCriterion theCriterion = (WakeupCriterion) criteria.nextElement();
		          if (theCriterion instanceof WakeupOnCollisionEntry){
		        	  colourswitch.setWhichChild(0);
		        	  
		        	  System.out.println("CRASH!");
		          }
		      }
		}
	}
	
	
	private Light createLight() {
		  DirectionalLight light = new DirectionalLight(true, new Color3f(1.0f,
		    1.0f, 1.0f), new Vector3f(-0.3f, 0.2f, -1.0f));

		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));

		  return light;
	}

	public static void main(String[] args) {        
		porprobar applet = new porprobar();
		Frame frame = new MainFrame(applet, 800, 600);
	}
}