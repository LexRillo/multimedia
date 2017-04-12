package cw4;

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

public class RotationTrial extends Applet{
	private SimpleUniverse universe = null;
	private Canvas3D canvas = null;
	private TransformGroup viewtrans = null;
	
	public RotationTrial() {
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
		tg.addChild(createWheels());
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
		  tg_wheel1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		  Alpha rotationAlpha1 = new Alpha(-1, 4000);
		  Transform3D t3d_wheel1 = new Transform3D();
		  
		  //rotating pi/2
		  //t3d_wheel1.rotX(Math.PI/2);
		  t3d_wheel1.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		  
		  //rotation movement
		  RotationInterpolator rotator1 = new RotationInterpolator(rotationAlpha1,
				  tg_wheel1, t3d_wheel1, 0.0f, (float) Math.PI * (2.0f));
		  rotator1.setSchedulingBounds(bounds);
		  tg_wheel1.setTransform(t3d_wheel1);
			
		  Appearance ap_wheel1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
		  //probably need to change longitude of it 1.5 -> 0.5
		  tg_wheel1.addChild(new Box(2f, 5f, 2f, ap_wheel1));
		  tg_wheel1.addChild(rotator1);
		  
//		  //wheel2
//		  TransformGroup tg_wheel2 = new TransformGroup();
//		  Transform3D t3d_wheel2 = new Transform3D();
//		  
//		  t3d_wheel2.rotX(Math.PI/2);
//		  t3d_wheel2.setTranslation(new Vector3d(0.6, -0.1, 0.0));
//		  tg_wheel2.setTransform(t3d_wheel2);
//		  
//		  Appearance ap_wheel2 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
//		  tg_wheel2.addChild(new Cylinder(0.1f, 1.5f, ap_wheel2));
//		  //wheel3
//		  TransformGroup tg_wheel3 = new TransformGroup();
//		  Transform3D t3d_wheel3 = new Transform3D();
//		  
//		  t3d_wheel3.rotX(Math.PI/2);
//		  t3d_wheel3.setTranslation(new Vector3d(0.6, -1.4, 0.0));
//		  tg_wheel3.setTransform(t3d_wheel3);
//		  
//		  Appearance ap_wheel3 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
//		  tg_wheel3.addChild(new Cylinder(0.1f, 1.5f, ap_wheel3));
		  
		  tg.addChild(tg_wheel1);
//		  tg.addChild(tg_wheel2);
//		  tg.addChild(tg_wheel3);
		  objRoot.addChild(tg);
		  
		  objRoot.compile();
		  return objRoot;
		  
		}

	public static void main(String[] args) {        
		RotationTrial applet = new RotationTrial();
		Frame frame = new MainFrame(applet, 800, 600);
	}
}
