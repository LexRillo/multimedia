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

public class movementtrial extends Applet{
	private SimpleUniverse universe = null;
	private Canvas3D canvas = null;
	private TransformGroup viewtrans = null;
	
	public movementtrial() {
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
		
		//t3d.rotZ(-(Math.PI/2));
		tg.setTransform(t3d);
		tg.addChild(createWheels());
		objRoot.addChild(tg);
		objRoot.compile();
		return objRoot;
	}

private BranchGroup createWheels(){
	BranchGroup objRoot = new BranchGroup();
	
	BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);
	
	TransformGroup tg = new TransformGroup();
	Transform3D t3d = new Transform3D();
	
	t3d.setTranslation(new Vector3d(0.0, 0.0, -15.0));
	tg.setTransform(t3d);
	
	TransformGroup tg_train = new TransformGroup();
	
	tg_train.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	//tg_wheel1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	Alpha trainalpha = new Alpha(3,2000);
	//trainalpha.setStartTime(200);
	Transform3D t3d_train = new Transform3D();
	
	t3d_train.setTranslation(new Vector3d(0.0, 0.0, 0.0));
	
	PositionInterpolator moveR = new PositionInterpolator(trainalpha,tg_train,t3d_train, 0.0f, 5.0f);
	moveR.setSchedulingBounds(bounds);
	
	tg_train.setTransform(t3d_train);
	
	Appearance ap_wheel1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
	
	tg_train.addChild(new Box(2f, 2f, 2f, ap_wheel1));
	//tg_train.addChild(new Sphere(3f));
	tg_train.addChild(moveR);
	
	tg.addChild(tg_train);
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

public static void main(String[] args) {        
	RotationTrial applet = new RotationTrial();
	Frame frame = new MainFrame(applet, 800, 600);
}
}
