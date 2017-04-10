//package cw4;
import java.applet.*;
import java.awt.*;

import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
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
		
		
		tg.addChild(createLocomotor());
		tg.addChild(createWheels());
		tg.addChild(createTracks());
		FPSAnimator translation = new FPSAnimator();
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
	
	  Appearance ap_sp = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_sp.addChild(new Sphere(0.5f, ap_sp));
	  
	  //main body of the train  
	  TransformGroup tg_cilinder = new TransformGroup();
	  Transform3D t3d_cilinder = new Transform3D();
	
	  t3d_cilinder.setTranslation(new Vector3d(0.0, 0.0, 0.0));
	  tg_cilinder.setTransform(t3d_cilinder);
	  
	  Appearance ap_cilinder = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_cilinder.addChild(new Cylinder(0.5f, 2.0f, ap_cilinder));
	  
	  //Cabin of locomotor
	  TransformGroup tg_cube = new TransformGroup();
	  Transform3D t3d_cube = new Transform3D();
	  
	  t3d_cube.setTranslation(new Vector3d(-0.2, -1.0, 0.0));
	  tg_cube.setTransform(t3d_cube);
	  
	  Appearance ap_cube = createAppearance(new Color3f(1.0f, 0.3f, 0.0f), 85.0f);
	  tg_cube.addChild(new Box(0.7f, 0.5f, 0.5f, ap_cube));
	  
	  //Chimney
	  TransformGroup tg_cone = new TransformGroup();
	  Transform3D t3d_cone = new Transform3D();
	  t3d_cone.rotZ(-(Math.PI/2));
	  t3d_cone.setTranslation(new Vector3d(-0.6, 0.9, 0.0));
	  tg_cone.setTransform(t3d_cone);
	  
	  Appearance ap_cone = createAppearance(new Color3f(1.0f, 0.3f, 0.0f), 30.0f);
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
	
	private BranchGroup createWheels(){
		
	BranchGroup objRoot = new BranchGroup();
		
	TransformGroup tg = new TransformGroup();
	Transform3D t3d = new Transform3D();
		
	t3d.setTranslation(new Vector3d(0.0, 0.0, -15.0));
	tg.setTransform(t3d);
	
	  //wheel1
	  TransformGroup tg_wheel1 = new TransformGroup();
	  Transform3D t3d_wheel1 = new Transform3D();
	  
	  //rotating pi/2
	  t3d_wheel1.rotX(Math.PI/2);
	  t3d_wheel1.setTranslation(new Vector3d(0.6, 0.9, 0.0));
	  tg_wheel1.setTransform(t3d_wheel1);
		
	  Appearance ap_wheel1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  //probably need to change longitude of it 1.5 -> 0.5
	  tg_wheel1.addChild(new Cylinder(0.1f, 1.5f, ap_wheel1));
	  
	  //wheel2
	  TransformGroup tg_wheel2 = new TransformGroup();
	  Transform3D t3d_wheel2 = new Transform3D();
	  
	  t3d_wheel2.rotX(Math.PI/2);
	  t3d_wheel2.setTranslation(new Vector3d(0.6, -0.1, 0.0));
	  tg_wheel2.setTransform(t3d_wheel2);
	  
	  Appearance ap_wheel2 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_wheel2.addChild(new Cylinder(0.1f, 1.5f, ap_wheel2));
	  //wheel3
	  TransformGroup tg_wheel3 = new TransformGroup();
	  Transform3D t3d_wheel3 = new Transform3D();
	  
	  t3d_wheel3.rotX(Math.PI/2);
	  t3d_wheel3.setTranslation(new Vector3d(0.6, -1.4, 0.0));
	  tg_wheel3.setTransform(t3d_wheel3);
	  
	  Appearance ap_wheel3 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_wheel3.addChild(new Cylinder(0.1f, 1.5f, ap_wheel3));
	  
	  tg.addChild(tg_wheel1);
	  tg.addChild(tg_wheel2);
	  tg.addChild(tg_wheel3);
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
	  
	 t3d_track1.setTranslation(new Vector3d(0.78, 40.0, 0.5));
	 tg_track1.setTransform(t3d_track1);
	  
	 Appearance ap_track1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	 tg_track1.addChild(new Box(0.08f, 100.0f, 0.05f, ap_track1));
	 //second track
	 TransformGroup tg_track2 = new TransformGroup();
	 Transform3D t3d_track2 = new Transform3D();
	  
	 t3d_track2.setTranslation(new Vector3d(0.78, 40.0, -0.5));
	 tg_track2.setTransform(t3d_track2);
	  
	 Appearance ap_track2 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	 tg_track2.addChild(new Box(0.08f, 100.0f, 0.5f, ap_track2));
	 
	 //maybe I can add some ends to the tracks
	 
	 tg.addChild(tg_track1);
	 tg.addChild(tg_track2);
	 //tg.addChild();
	 objRoot.addChild(tg);
	  
	 objRoot.compile();
	 return objRoot;
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