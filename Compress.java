//package intro;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.io.*;
import java.math.*;

class DictNode{
    String pattern;
    String binary;
    int id;
}

public class Compress {
    int size = (int) Math.pow(2, 18)+1;
    DictNode[] arr = new DictNode[size];
    int countdict = 128;
    int count;

    DictNode newDictNode(int id, String pattern, String binary) {
        DictNode n = new DictNode();
        n.id = id;
        n.pattern = pattern;
        n.binary = binary;
        return n;
    }

    public int HashFunction(String s) {
        long count2 = 0;
        for (int i=0; i<s.length(); i++) {
            //count = (((int)s.charAt(i))*31 + count)%(int) Math.pow(2, 16);
            count2 = (count2*2111 +(int)s.charAt(i))%size;
        }
        return (int)count2;
    }

    public void Hashing(String s) {
        count = HashFunction(s);
        //System.out.println(count);//being printed
        String a = Integer.toBinaryString(countdict);
        int len = a.length();
        if (arr[count]==null) {
            len = 16 - len;
            int j = 1;
            String addzero="" ;
            while (j<=len) {
                addzero = addzero + "0";
                j=j+1;
            }
            a = addzero + a;
//            System.out.println(a);
            arr[count] = newDictNode(countdict, s, a);
            //System.out.println("HOHO");
            //System.out.println(s);
            countdict= countdict+1;
        }
        else {
//       System.out.println("udja");
//       System.out.println(arr[count].pattern);
//       System.out.println(arr[count].pattern.length());
            int k =1;
            int dcount = count;
            while (arr[dcount]!=null) {
                dcount = (count + (int) Math.pow(k, 2))%size;
                k=k+1;
            }
            len = 16 - len;
            int j = 1;
            String addzero="" ;
            while (j<=len) {
                addzero = addzero + "0";
                j=j+1;
            }
            a = addzero + a;
//            System.out.println(a);
            arr[dcount] = newDictNode(countdict, s, a);
//       System.out.println(arr[dcount].id);
//       System.out.println(arr[dcount].pattern);
//       System.out.println(arr[dcount].binary);

            countdict= countdict+1;
        }
    }

    public boolean CheckExistence(String s){
        int check = this.HashFunction(s);
        //System.out.println(check);
        if (arr[check]==null) {
            return false;
        }
        else {
            int k=1,min=1;
            int dcheck = check;
            while (arr[dcheck]!=null && k<size) {
                if ((arr[dcheck].pattern).equals(s)) {
                    return true;
                }
                dcheck = (check + min*(int) Math.pow(k, 2))%size;
//                min=min*-1;
                k=k+1;
            }
            return false;
        }
    }

    public String GetExistence(String s) {

        if (s.length()==1) {
            //System.out.println(arr[(int) s.charAt(0)].binary);
            return (arr[(int) s.charAt(0)].binary);
        }
        int c = this.HashFunction(s);
        //System.out.println(c);
        if ((arr[c].pattern).equals(s)) {
            //System.out.println(arr[c].binary);//being printed
            return (arr[c].binary);
        }
        else {
            //System.out.println("trrr");
            int k=1,min=1;
            int dc = c;
            while (!arr[dc].pattern.equals(s)) {
                //System.out.println("gffg");
                dc = (c + min*(int) Math.pow(k, 2))%size;
//                min=min*-1;
                k=k+1;
                if ((arr[dc].pattern).equals(s)) {
//                    System.out.println(arr[dc].binary);
                    return (arr[dc].binary);
                }
            }
        }
        return s;
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Compress c = new Compress();
        try (FileInputStream fin=new FileInputStream(args[0])){
            try (OutputStream os = new FileOutputStream(args[1])){
                //String sample = "aaabbbbbbaabaaba";
                int l=0;
                String sample="";
                while((l=fin.read())!=-1){
                    sample = sample + Character.toString((char)l);
                }

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
                    c.arr[i] = c.newDictNode(i,Character.toString((char)i), a);
                    //System.out.println((char)i);
                    //System.out.println(arr[(char)i].pattern);
                }
                int i = 0;
                String newpat = c.arr[(int) (sample.charAt(i))].pattern;
                //System.out.println(c.arr[(int) (sample.charAt(i))].binary);
                boolean dd=true;
                while (i<sample.length()-1) {
                    i=i+1;
                    String nnp = newpat;
                    //System.out.println(nnp);
                    newpat = newpat + c.arr[(int) (sample.charAt(i))].pattern;
                    dd = c.CheckExistence(newpat);
//          System.out.println("GOGOO");
//          System.out.println(newpat);
                    if (newpat.length()==1) {
                        dd=true;
                    }

                    if (!dd) {
                        //try (OutputStream os = new FileOutputStream("/Users/akash/Desktop/out.text")){
                        String b = c.GetExistence(nnp);

                        byte[] bi1 = new BigInteger(b.substring(0, 8), 2).toByteArray();
                        byte[] bi2 = new BigInteger(b.substring(8, 16), 2).toByteArray();
                        //System.out.println(bi1[bi1.length-1]);
                        //System.out.println(bi2[bi2.length-1]);
                        os.write(bi1[bi1.length-1]);
                        os.write(bi2[bi2.length-1]);
                        //System.out.println(b1[b1.length-1]);
                        //c.GetExistence(nnp);
                        if(c.countdict<(int) Math.pow(2, 16)) {
                            c.Hashing(newpat);
//                            System.out.println(c.countdict);
//                            System.out.println(newpat);
                        }
//                        else{System.out.println("grbd");}
                        //System.out.println(newpat);
                        newpat=c.arr[(int) (sample.charAt(i))].pattern;
                    }
                }
                //OUTSIDE LOOP
                if (dd) {
                    String b = c.GetExistence(newpat);
                    byte[] bi1 = new BigInteger(b.substring(0, 8), 2).toByteArray();
                    byte[] bi2 = new BigInteger(b.substring(8, 16), 2).toByteArray();
                    //System.out.println(bi1[bi1.length-1]);
                    //System.out.println(bi2[bi2.length-1]);
                    os.write(bi1[bi1.length-1]);
                    os.write(bi2[bi2.length-1]);

                }
            }

        }
        catch (IOException e) {
            return;
        }
    }
}


//import java.util.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.Math;
//import java.io.*;
//import java.math.*;
//
//class DictNode{
//	String pattern;
//	String binary;
//	int id;
//}
//
//public class Compress {
//	int size = (int) Math.pow(2, 18)+1;
//	DictNode[] arr = new DictNode[size];
//	int countdict = 128;
//	int count;
//	
//	DictNode newDictNode(int id, String pattern, String binary) {
//		DictNode n = new DictNode();
//		n.id = id;
//		n.pattern = pattern;
//		n.binary = binary;
//		return n;
//	}
//	
//	public int HashFunction(String s) {
//		count = 0;
//		for (int i=0; i<s.length(); i++) {
//			//count = (((int)s.charAt(i))*31 + count)%(int) Math.pow(2, 16);
//			count = (count*31 +(int)s.charAt(i))%(int) Math.pow(2, 16);
//		}
//		return count;
//	}
//	
//	public void Hashing(String s) {
//		count = HashFunction(s);
//		System.out.println(count);
//		String a = Integer.toBinaryString(countdict);
//		int len = a.length();
//		if (arr[count]==null) {	
//			len = 16 - len;
//			int j = 1;
//			String addzero = "0";
//			while (j<=len-1) {
//				addzero = addzero + "0";
//				j=j+1;
//			}
//			a = addzero + a;
//			arr[count] = newDictNode(countdict, s, a);
//			//System.out.println("HOHO");
//			//System.out.println(s);
//			countdict= countdict+1;
//		}
//		else {
////			System.out.println("udja");
////			System.out.println(arr[count].pattern);
////			System.out.println(arr[count].pattern.length());
//			int k =1;
//			int dcount = count;
//			while (arr[dcount]!=null) {
//				dcount = (count + (int) Math.pow(k, 2))%(int) Math.pow(2, 16);				
//				k=k+1;	
//			}
//			len = 16 - len;
//			int j = 1;
//			String addzero = "0";
//			while (j<=len-1) {
//				addzero = addzero + "0";
//				j=j+1;
//			}
//			a = addzero + a;
//			arr[dcount] = newDictNode(countdict, s, a);
////			System.out.println(arr[dcount].id);
////			System.out.println(arr[dcount].pattern);
////			System.out.println(arr[dcount].binary);
//			
//			countdict= countdict+1;
//		}
//	}
//	
//	public boolean CheckExistence(String s){
//		int check = this.HashFunction(s);
//		//System.out.println(check);
//		if (arr[check]==null) {
//			return false;
//		}
//		else {
//			int k=1;
//			int dcheck = check;
//			while (arr[dcheck]!=null) {
//				if ((arr[dcheck].pattern).equals(s)) {
//					return true;
//				}
//				dcheck = (check + (int) Math.pow(k, 2))%(int) Math.pow(2, 16);
//				k=k+1;
//			}
//			return false;
//		}
//	}
//	
//	public String GetExistence(String s) {
//		
//		if (s.length()==1) {
//			System.out.println(arr[(int) s.charAt(0)].binary);
//			return (arr[(int) s.charAt(0)].binary);
//		}
//		int c = this.HashFunction(s);
//		//System.out.println(c);
//		if ((arr[c].pattern).equals(s)) {
//			System.out.println(arr[c].binary); 
//			return (arr[c].binary);
//		}
//		else {
//			//System.out.println("trrr");
//			int k=1;
//			int dc = c;
//			while (arr[dc].pattern.equals(s)==false) {
//				//System.out.println("gffg");
//				dc = (c + (int) Math.pow(k, 2))%(int) Math.pow(2, 16);
//				k=k+1;
//				if ((arr[dc].pattern).equals(s)) {
//					System.out.println(arr[dc].binary); 
//					return (arr[dc].binary);
//				}
//			}
//		}
//		return s;
//	}
//	
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub		
//		Compress c = new Compress();
//		try (FileInputStream fin=new FileInputStream("/Users/akash/Desktop/input3.dms")){
//			try (OutputStream os = new FileOutputStream("/Users/akash/Desktop/out2.text")){
//			//String sample = "aaabbbbbbaabaaba";
//			int l=0;
//			String sample="";
//            while((l=fin.read())!=-1){  
//             sample = sample + Character.toString((char)l);    
//            }
//
//			for (int i=0; i<=127; i++) {
//				String a = Integer.toBinaryString(i);
//				int len = a.length();
//				len = 16 - len;
//				int j = 1;
//				String addzero = "0";
//				while (j<=len-1) {
//					addzero = addzero + "0";
//					j=j+1;
//				}
//				a = addzero + a;
//				c.arr[i] = c.newDictNode(i,Character.toString((char)i), a);
//				//System.out.println((char)i);
//				//System.out.println(arr[(char)i].pattern);	
//			}
//			int i = 0;
//			String newpat = c.arr[(int) (sample.charAt(i))].pattern;
//			//System.out.println(c.arr[(int) (sample.charAt(i))].binary);
//			boolean dd=true;
//			while (i<sample.length()-1) {
//				i=i+1;
//				String nnp = newpat;
//				//System.out.println(nnp);
//				newpat = newpat + c.arr[(int) (sample.charAt(i))].pattern;
//				dd = c.CheckExistence(newpat);
//				if (newpat.length()==1) {
//					dd=true;
//				}
//				
//				if (dd==false) {
//					//try (OutputStream os = new FileOutputStream("/Users/akash/Desktop/out.text")){
//						String b = c.GetExistence(nnp);
//						byte[] bi1 = new BigInteger(b.substring(0, 8), 2).toByteArray();
//						byte[] bi2 = new BigInteger(b.substring(8, 16), 2).toByteArray();
//						//System.out.println(bi1[bi1.length-1]);
//						//System.out.println(bi2[bi2.length-1]);
//						os.write(bi1[bi1.length-1]);
//						os.write(bi2[bi2.length-1]);
//						//System.out.println(b1[b1.length-1]);
//					//c.GetExistence(nnp);
//					if(c.countdict<=(int) Math.pow(2, 16)) {	
//						c.Hashing(newpat);
//					}
//					//System.out.println(newpat);
//					newpat=c.arr[(int) (sample.charAt(i))].pattern;
//				}
//			}
//			//OUTSIDE LOOP
//			if (dd==true) {
//					String b = c.GetExistence(newpat);
//					byte[] bi1 = new BigInteger(b.substring(0, 8), 2).toByteArray();
//					byte[] bi2 = new BigInteger(b.substring(8, 16), 2).toByteArray();
//					//System.out.println(bi1[bi1.length-1]);
//					//System.out.println(bi2[bi2.length-1]);
//					os.write(bi1[bi1.length-1]);
//					os.write(bi2[bi2.length-1]);
//				
//			}
//		}
//		
//		}
//		catch (IOException e) {
//			return;
//		}
//	}
//}
