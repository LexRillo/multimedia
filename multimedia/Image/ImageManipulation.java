
/**
 *
 * Time-stamp: <2017-01-24 17:29:51 rlc3>
 *
 * ImageManipulation.java
 *
 * Class allows the manipulation of an image by
 * three alternative methods.
 *
 * @author Roy Crole
 *
 */


import java.awt.image.*;
import java.awt.*;
import java.util.*;

public class ImageManipulation {

// ---- BEGIN linearBox

    static public void linearBox(BufferedImage image, int n, int x, int y, int size) {

       BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
       (temp.getGraphics()).drawImage(image, 0, 0, image.getWidth(), image.getHeight(),null);

// ----- template code commented out BEGIN

// check if A(x,y) lies within image
if (x +size<= image.getWidth() -1 && x-size >= 0 && y + size <= image.getHeight() -1 && y-size >= 0) {
// loop visiting each pixel in A(x,y) at coordinate (i,j)
   for (int i= x-size; i<= x + size; i++) {
	for (int j= y - size; j<= y + size; j++) {// <---- start a for loop for j here

		 // set values for O, P, D
		 int O = x-size;
		 int P = x+size;
		 int D = x+n;

		 // check if i is between O and D
		 // if so do the transformation and "get and set the RGB"
		 // if not make pixel (i,j) gray at 0x:aaaaaa
		  if(i >= O && i <= D){
			  int prei = linTrans(O, i, D, P);
			  //System.out.println("prei =" +prei);
		  image.setRGB(i, j, temp.getRGB(prei, j));
		  }else{
		  image.setRGB(i, j, 0xaaaaaa);
		  }

	}// <--- end forLoop j here
   	} // end forLoop i
    } // end check that A(x,y) is in image
 } // end method linearBox

    static int linTrans (int O, int i, int D, int P) {
		//since we are try to flip the image to mirror the one on the left, we 
		//take the cos of the transformation (because we try to flip in respect of the y axis)
		//which is -1 and we take the relation between the two distances O and P so that
		//prei = (D-P/D-O) + D*(1-m)
		int m = ((P-D)/(O-D));
    	int k = D*(1-m);    	
    	int prei = m*i + k;
    	return prei;
    } // END method linTrans

  //----- template code commented out END */


// ---- BEGIN linearOct

    static public void linearOct(BufferedImage image, int n, int x, int y, int size) {

	BufferedImage temp=new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        (temp.getGraphics()).drawImage(image, 0, 0, image.getWidth(), image.getHeight(),null);

 /*----- template code commented out BEGIN

// check if A(x,y) lies within image
if (x +size<= image.getWidth() && x-size >= 0 && y + size <= image.getHeight() && y-size >= 0) {
// loop visiting each pixel in A(x,y) at image coordinate (i,j)
   for (int i= x; i<= x + size; i++) {
	for (int j= y - size; j<= y + size; j++) {

		    // a list to store preI and preJ as element 0 and 1
		    // pre is calculated below using octlinTrans
		    int [] pre = new int[2];
		    int preI;
		    int preJ;

       	    	    // translate image coordinate (x,y) to (0,0) and translate all (i,j) similarly
		    // convert translated image coordinates to cartesian coordinate
                    int I = i -x;
		    int J = j +y;

	            // set d = distance of origin to (I,J)
			d=sqrRoot(I^2 + J^2)

          // if (I,J) is inside the circle of radius size
	  // then we apply the linear transformation to (I,J)
          if (d<=size) {

		     // perform linear transformation in octant OGV
		     // 0 < J < I
         	 if (J>0 && I>J) {
                       pre = ?? ; // calculate pre using octlinTrans
		       preI = ?? ; preJ = ?? ;}
		     else if(J<=0 && I > -J){  <--- ???OVH
				J = -J;
				pre = ...
			 }else if(J<0 && I<J){UKO
			 
				I=-I;
				j=-J;
				pre=...
		       // You need to eventually add in nested if statements here
		       // to deal with the other two octants.
		       // The final else clause deals with
		       // the remaining pixels that are not in the domain
		       // of the linear transformation
		       ?? } // end nested if statements

       	    	    // transform cartesian coordinate (preI,preJ)
		    // back to image coordinate (prei,prej)
		    ??

		    // set RGB of pixel at (i,j) to RGB from (prei,prej)
		    image.setRGB(i,j, temp.getRGB(prei, prej);

              } else {
	      	       image.setRGB(i, j, 0xaaaaaa);// what do we do outside the circle?
		      } // end if

		} // end forLoop j
       	} // end forLoop i
    } // end check that A(x,y) is in image

   ----- template code commented out END */

 } // end method linearOct

   // take a cartesian coordinate (I,J) assumed to be in the positive octant OGV
   // apply the linear transformation described above at (LT)
   //

  static int[] octlinTrans (int O, double d, int D, int I, int J) {

	            // we will compute preI and preJ and return them in list
		    int [] list = new int[2];

/* ----- template code commented out BEGIN

		    // calculate P (from theta, itself from I and J)
		    double theta = arctan(J/I);
		    double P = size/(cos(theta);

		    // in (LT) above, to calculate pred we have
		    // double d instead of int i (pred instead of prei)
  	      	    double pred = ?? ;
				double newI =pred*(cos(theta));
				double newJ =pred*(sin(theta));

		    int preI = (int) newI;
                preJ = (int) newJ;

   ----- template code commented out END */

		    return list;

	  } // end octlinTrans

// ---- END linearOct

// ---- BEGIN phaseShift

    static public void phaseShift(BufferedImage image, int n, int x, int y, int size) {

	// creates a copy of the image called temp
        BufferedImage temp=new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        (temp.getGraphics()).drawImage(image,0,0,image.getWidth(), image.getHeight(),null );



	// loop through each pixel (i,j) in "A(x,y) intersect (image - boundary)"
	//for (int i = x - size; i<= Math.min(image.getWidth()-1-2*n, x+size); ++i) {
		//for(int j = y-size; j <= y + size; j++){
	for(int i= Math.max(2*n, x- size); i<= Math.min(image.getWidth()-1-2*n, x+size); ++i){
		for(int j = Math.max(2*n, y- size); j<=Math.min(image.getHeight()-1-2*n, y+size); ++j){
			if(i >= image.getWidth()-400){
				int pureBlue = 0x0000FF;
				image.setRGB(i,j, pureBlue);
			}else{
				int prei= i +2*n;
				int greenchannel = (temp.getRGB(prei, j) & 0x00FF00) >> 8;
				int grayscale= (((greenchannel<<8)|greenchannel)<<8|greenchannel);
				//int rgb = temp.getRGB(prei, j);
        			//int g = (rgb >> 8) & 0xFF;
				//int grayscale= (g << 16) + (g << 8) + g; 
				image.setRGB(i,j, grayscale);
			}
		}//end loop j	
	} // end loop i

 } // end phaseShift

// ---- end phaseShift

} // ImageManipulation
