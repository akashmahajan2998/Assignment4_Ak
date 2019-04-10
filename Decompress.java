
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.Math;

class DNode{
    String pattern;
    String binary;
}

public class Decompress {
    int size = (int) Math.pow(2, 18)+1;
    DNode[] arr = new DNode[size];
    int countdict = 128;
    int count;

    DNode newDNode( String pattern, String binary) {
        DNode n = new DNode();
        n.pattern = pattern;
        n.binary = binary;
        return n;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BufferedWriter bw = null;
        Decompress d = new Decompress();
        for (int i=0; i<=127; i++) {
            String a = Integer.toBinaryString(i);

            int len = a.length();
            len = 16 - len;
            int j = 1;
            String addzero = "0";
            while (j<=len-1) {
                addzero = addzero + "0";
                j=j+1;
            }
            a = addzero + a;
            d.arr[i] = d.newDNode(Character.toString((char)i), a);
        }
        try (FileInputStream fin=new FileInputStream(args[0])){
            FileWriter fw = new FileWriter(args[1]);
            bw = new BufferedWriter(fw);
            //try (OutputStream os = new FileOutputStream("/Users/akash/Desktop/deout.text")){
            int l=0;
            String sample="";
            while((l=fin.read())!=-1){
                sample = sample + Character.toString((char)l);
            }
            //System.out.println(sample);
            int track=0;
            String old_a="";
            for (int i=0; i<sample.length()-1; i=i+2) {
                int v = (int)sample.charAt(i);
                String a = Integer.toBinaryString(v);
                int len = a.length();
                while (len<8) {
                    a = "0"+a;
                    len++;
                }

                int u = (int)sample.charAt(i+1);
                String b = Integer.toBinaryString(u);

                len = b.length();

                while (len<8) {
                    b = "0"+b;
                    len++;
                }
                a = a+b;
                //System.out.println(a);
                if(track==0) {

                    bw.write(d.arr[Integer.parseInt(a,2)].pattern);
                    //System.out.println("ffgg");
                    System.out.println(d.arr[Integer.parseInt(a,2)].pattern);
                    //os.write(d.arr[Integer.parseInt(a,2)].pattern);
                    track=1;
                }
                else {
                    if(d.arr[Integer.parseInt(a,2)]!=null) {
                        bw.write(d.arr[Integer.parseInt(a,2)].pattern);
                        System.out.println(d.arr[Integer.parseInt(a,2)].pattern);
                        String bin = Integer.toBinaryString(d.countdict);

                        len = bin.length();
                        //len = 8 - len;
                        while (len<8) {
                            bin = "0"+bin;
                            len++;
                        }
                        d.arr[d.countdict] = d.newDNode((d.arr[Integer.parseInt(old_a,2)].pattern + Character.toString(d.arr[Integer.parseInt(a,2)].pattern.charAt(0))), bin);
                        d.countdict = d.countdict+1;
                    }
                    else {
                        //System.out.println(d.arr[d.countdict]);
                        d.arr[d.countdict] = d.newDNode((d.arr[Integer.parseInt(old_a,2)].pattern + Character.toString(d.arr[Integer.parseInt(old_a,2)].pattern.charAt(0))), a);
                        bw.write(d.arr[d.countdict].pattern);
                        System.out.println(d.arr[d.countdict].pattern);
                        d.countdict = d.countdict+1;
                    }
                }
                old_a = a;
                //System.out.println(d.arr[128]);
                //System.out.println(a);
            }
            bw.close();
            //}
        }
        catch (IOException e) {
            System.out.println(e);
            return;
        }
    }

}

//package intro;
//
//import java.util.*;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.Math;
//
//class DNode{
//	String pattern;
//	String binary;
//}
//
//public class Decompress {
//	int size = (int) Math.pow(2, 18)+1;
//	DNode[] arr = new DNode[size];
//	int countdict = 128;
//	int count;
//
//	DNode newDNode( String pattern, String binary) {
//		DNode n = new DNode();
//		n.pattern = pattern;
//		n.binary = binary;
//		return n;
//	}
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		BufferedWriter bw = null;
//		Decompress d = new Decompress();
//		for (int i=0; i<=127; i++) {
//			String a = Integer.toBinaryString(i);
//			
//			int len = a.length();
//			len = 16 - len;
//			int j = 1;
//			String addzero = "0";
//			while (j<=len-1) {
//				addzero = addzero + "0";
//				j=j+1;
//			}
//			a = addzero + a;
//			d.arr[i] = d.newDNode(Character.toString((char)i), a);
//		}
//		try (FileInputStream fin=new FileInputStream("/Users/akash/Desktop/out2.text")){
//			FileWriter fw = new FileWriter("/Users/akash/Desktop/deout.text");
//			bw = new BufferedWriter(fw);
//			//try (OutputStream os = new FileOutputStream("/Users/akash/Desktop/deout.text")){
//			int l=0;
//			String sample="";
//            while((l=fin.read())!=-1){  
//             sample = sample + Character.toString((char)l);
//            }
//            //System.out.println(sample);
//            int track=0;
//            String old_a="";
//            for (int i=0; i<sample.length()-1; i=i+2) {
//            	int v = (int)sample.charAt(i); 
//            	String a = Integer.toBinaryString(v);
//    			int len = a.length();
//    			while (len<8) {
//    				a = "0"+a;
//    				len++;
//    			}
// 
//    			int u = (int)sample.charAt(i+1);
//            	String b = Integer.toBinaryString(u);
//    			
//    			len = b.length();
//
//    			while (len<8) {
//    				b = "0"+b;
//    				len++;
//    			}
//    			a = a+b;
//    			//System.out.println(a);
//    			if(track==0) {
//    				
//    				bw.write(d.arr[Integer.parseInt(a,2)].pattern);
//    				//System.out.println("ffgg");
//    				System.out.println(d.arr[Integer.parseInt(a,2)].pattern);
//    				//os.write(d.arr[Integer.parseInt(a,2)].pattern);
//    				track=1;
//    			}
//    			else {
//    				if(d.arr[Integer.parseInt(a,2)]!=null) {
//    					bw.write(d.arr[Integer.parseInt(a,2)].pattern);
//    					System.out.println(d.arr[Integer.parseInt(a,2)].pattern); 
//    					String bin = Integer.toBinaryString(d.countdict);
//    	    			
//    	    			len = bin.length();
//    	    			//len = 8 - len;
//    	    			while (len<8) {
//    	    				bin = "0"+bin;
//    	    				len++;
//    	    			}
//    					d.arr[d.countdict] = d.newDNode((d.arr[Integer.parseInt(old_a,2)].pattern + Character.toString(d.arr[Integer.parseInt(a,2)].pattern.charAt(0))), bin);
//    					d.countdict = d.countdict+1;
//    				}
//    				else {
//    					//System.out.println(d.arr[d.countdict]);
//    					d.arr[d.countdict] = d.newDNode((d.arr[Integer.parseInt(old_a,2)].pattern + Character.toString(d.arr[Integer.parseInt(old_a,2)].pattern.charAt(0))), a);
//    					bw.write(d.arr[d.countdict].pattern);
//    					System.out.println(d.arr[d.countdict].pattern);
//    					d.countdict = d.countdict+1;
//    				}
//    			}
//    			old_a = a;
//    			//System.out.println(d.arr[128]);
//    			//System.out.println(a);
//            }
//            bw.close();
//			//}
//		}
//		catch (IOException e) {
//			return;
//		}
//	}
//
//}
