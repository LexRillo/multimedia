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

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.Cube;

public class porprobar extends Applet {

 private SimpleUniverse universe = null;
 private Canvas3D canvas = null;
 private TransformGroup viewtrans = null;

public porprobar() {}

private BranchGroup createSceneGraph() {}

private Appearance createAppearance(Color3f col) {
  Appearance ap = new Appearance();
  Material ma = new Material();
  ma.setDiffuseColor(col);
  //ma.setEmissiveColor(col);
  ap.setMaterial(ma);
  return ap;
 }

private BranchGroup createLocomotor() {

BranchGroup objRoot = new BranchGroup();

  TransformGroup tg = new TransformGroup();
  Transform3D t3d = new Transform3D();

  t3d.setTranslation(new Vector3d(0.0, 0.0, -15.0));
  tg.setTransform(t3d);

  TransformGroup tg_sp = new TransformGroup();
  Transform3D t3d_sp = new Transform3D();

  Appearance ap_sp = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
  tg_sp.addChild(new Sphere(0.5f, ap_sp));

  Appearance ap_cy = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
  tg_cy.addChild(new Cylinder(0.5f, 2.0f, ap_cy));
}

private BranchGroup createWheels(){
Appearance ap_cy1 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
  tg_cy1.addChild(new Cylinder(0.05f, 1.5f, ap_cy1));
Appearance ap_cy2 = createAppearance(new Color3f(1.0f, 0.3f, 0.0f));
  tg_cy2.addChild(new Cylinder(0.05f, 1.5f, ap_cy2));
}

private BranchGroup createTracks(){

}


