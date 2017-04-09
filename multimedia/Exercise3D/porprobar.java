import java.applet.*;
import java.awt.*;

import javax.media.j3d.*;
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

	public porprobar() {
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
			TransformGroup cameraTG = u.getViewingPlatform().getViewPlatformTransform();
			// starting postion of the viewing platform
			Vector3f translate = new Vector3f(); 
		      	Transform3D T3D = new Transform3D();
			// move along z axis by 10.0f ("move away from the screen") 
			translate.set( 0.0f, 0.0f, 10.0f);
		        T3D.setTranslation(translate);
			cameraTG.setTransform(T3D);
		        setTitle("Step 7: two rotation interpolators");
		        setSize(700,700);
		        setVisible(true);	
	
	}
	
	private BranchGroup createSceneGraph() {}
	
	private Appearance createAppearance(Color3f col) {
	  Appearance ap = new Appearance();
	  Material ma = new Material();
	  ma.setDiffuseColor(col);
	  //ma.setEmissiveColor(col);
	  ap.setMaterial(ma);
	  return ap;
	 }
	
	private Appearance createAppearance(Color3f col, float shine) {
		  Appearance ap = new Appearance();
		  Material ma = new Material();
		  ma.setDiffuseColor(col);
		  //ma.setEmissiveColor(col);
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
	  
	  t3d_cube.setTranslation(new Vector3d(0.0, -1.0, 0.0));
	  tg_cube.setTransform(t3d_cube);
	  
	  Appearance ap_cube = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_cube.addChild(new Box(0.5f, 0.5f, 0.5f, ap_cube));
	}
	
	private BranchGroup createWheels(){
	  //wheel1
	  TransformGroup tg_wheel1 = new TransformGroup();
	  Transform3D t3d_wheel1 = new Transform3D();
	  
	  t3d_wheel1.setTranslation(new Vector3d(0.55, 0.95, 0.0));
	  tg_wheel1.setTransform(t3d_wheel1);
		
	  Appearance ap_wheel1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  //probably need to change longitude of it 1.5 -> 0.5
	  tg_wheel1.addChild(new Cylinder(0.05f, 1.5f, ap_wheel1));
	  //rotating pi/2
	  t3d_wheel1.rotX(100);
	  //wheel2
	  TransformGroup tg_wheel2 = new TransformGroup();
	  Transform3D t3d_wheel2 = new Transform3D();
	  
	  t3d_wheel2.setTranslation(new Vector3d(0.55, 0.0, 0.0));
	  tg_wheel2.setTransform(t3d_wheel2);
	  
	  Appearance ap_wheel2 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_wheel2.addChild(new Cylinder(0.05f, 1.5f, ap_wheel2));
	  t3d_wheel2.rotX(100);
	  //wheel3
	  TransformGroup tg_wheel3 = new TransformGroup();
	  Transform3D t3d_wheel3 = new Transform3D();
	  
	  t3d_wheel3.setTranslation(new Vector3d(0.55, -1.45, 0.0));
	  tg_wheel3.setTransform(t3d_wheel3);
	  
	  Appearance ap_wheel3 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	  tg_wheel3.addChild(new Cylinder(0.05f, 1.5f, ap_wheel3));
	  t3d_wheel3.rotX(100);
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
	  
	 t3d_track1.setTranslation(new Vector3d(0.64, 40.0, 0.5));
	 tg_track1.setTransform(t3d_track1);
	  
	 Appearance ap_track1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	 tg_track1.addChild(new Box(0.08f, 100.0f, 0.05f, ap_track1));
	 //second track
	 TransformGroup tg_track2 = new TransformGroup();
	 Transform3D t3d_track2 = new Transform3D();
	  
	 t3d_track2.setTranslation(new Vector3d(0.6, 0.0, -0.5));
	 tg_track2.setTransform(t3d_track2);
	  
	 Appearance ap_track2 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	 tg_track2.addChild(new Box(0.5f, 0.5f, 0.5f, ap_track2));
	 
	 //maybe I can add some ends to the tracks
}

	public static void main(String[] args) {        
		porprobar something = porprobar();
	}
}

