import java.math.BigInteger;

public class Test {
	public static void main(String[] args) {
        try {
        	System.out.println("--------------------------------------------------------------------------------------------------------------");
        	System.out.println("CONSTRUCTOR 1 TEST (Invalid [-ve sign only]): ");
        	HugeInteger a=new HugeInteger("-") ;
        }
        catch (IllegalArgumentException e){
    			System.out.println(e);
        }
        
        try {
        	System.out.println("--------------------------------------------------------------------------------------------------------------");
        	System.out.println("CONSTRUCTOR 1 TEST (Invalid [empty string]): ");
        	HugeInteger b=new HugeInteger("") ;
        }
        catch (IllegalArgumentException e){
    			System.out.println(e);
        }
        
        try {
        	System.out.println("--------------------------------------------------------------------------------------------------------------");
        	System.out.println("CONSTRUCTOR 1 TEST (Invalid [non-integer]): ");
        	HugeInteger c=new HugeInteger("-+$12345%23453442") ;
        }
        catch (IllegalArgumentException e){
    			System.out.println(e);
        }
        
        try {
        	System.out.println("--------------------------------------------------------------------------------------------------------------");
        	System.out.println("CONSTRUCTOR 2 TEST (Invalid [Integer below 0]): ");
        	HugeInteger d=new HugeInteger(0) ;
        }
        catch (IllegalArgumentException e){
    			System.out.println(e);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("CONSTRUCTOR 1 TEST (Valid Inputs): ");
        
        HugeInteger s1=new HugeInteger("00001") ;
        HugeInteger s2=new HugeInteger("-0000056") ;
        HugeInteger s3=new HugeInteger("-1333123123123");
        HugeInteger s4=new HugeInteger("-1333");
        HugeInteger s5=new HugeInteger("-999");
        HugeInteger s6=new HugeInteger("-0000056");
	
        System.out.println("HugeInteger s1 = " + s1.toString());
        System.out.println("HugeInteger s2 = " + s2.toString());
        System.out.println("HugeInteger s3 = " + s3.toString());
        System.out.println("HugeInteger s4 = " + s4.toString());
        System.out.println("HugeInteger s4 = " + s5.toString());
        System.out.println("HugeInteger s4 = " + s6.toString());

        System.out.println("--------------------------------------------------------------------------------------------------------------");
        HugeInteger k=new HugeInteger(10);
        HugeInteger l=new HugeInteger(20);
        System.out.println("CONSTRUCTOR 2 TEST (Valid Inputs): ");
        System.out.println("Random HugeInteger of size " + k.intSize + ": " + k.toString());
        System.out.println("Random HugeInteger of size " + l.intSize + ": " + l.toString());
        
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("ADD FUNCTION TEST: ");
        HugeInteger s13=new HugeInteger("-2222");
        HugeInteger s14=new HugeInteger("3333");	
        System.out.println("Add function test: " + s13.add(s14));
        System.out.println("Add function test: " + s2.add(s2));
        System.out.println("Add function test (random): " + k.add(l));
       
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("SUBTRACT FUNCTION TEST: ");
        HugeInteger s15=new HugeInteger("22222");
        HugeInteger s16=new HugeInteger("22222");
        System.out.println("Subtract function test: " + s4.subtract(s1));
        
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("MULTIPLY FUNCTION TEST: ");
        HugeInteger s7=new HugeInteger("-9876543212345678987654321");
        HugeInteger s8=new HugeInteger("-1333");
        HugeInteger s9=new HugeInteger("-0000056");
        HugeInteger s10=new HugeInteger("2222");
        HugeInteger s19=new HugeInteger("-99");
        HugeInteger s20=new HugeInteger("0");
        System.out.println("Multiply function test: " + s19.multiply(s20));
        System.out.println("Multiply function test: " + s8.multiply(s9));
        System.out.println("Multiply function test: " + s7.multiply(s10));
        System.out.println("Multiply function test: " + s7.multiply(s8));
        
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        HugeInteger m=new HugeInteger("-0000");
        HugeInteger n=new HugeInteger("00001");
        HugeInteger s17=new HugeInteger("-22222");
        HugeInteger s18=new HugeInteger("-2222");
        System.out.println("COMPARE FUNCTION TEST: ");
        System.out.println("Compare function test: " + s17.compareTo(s18));
        System.out.println("Compare function test: " + n.compareTo(m));
       
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("RUN TIME TEST: ");
        HugeInteger huge1, huge2, hugeAdd, hugeMultiply, hugeSubtract; 
        int hugeCompare;
		long startTime, endTime;
		double runTimeAdd=0.0; double runTimeMult=0.0; double runTimeComp=0.0; double runTimeSub=0.0;
		
		for (int numInts=0; numInts < 100; numInts++) {
			huge1 = new HugeInteger(1);        
			huge2 = new HugeInteger(1);           
			startTime = System.currentTimeMillis();
			for(int numRun=0; numRun < 100; numRun++) { 
				hugeAdd = huge1.add(huge2); 
			}
			endTime = System.currentTimeMillis();
			runTimeAdd +=(double) (endTime - startTime)/((double)100);
		}
		runTimeAdd = runTimeAdd/((double)100);
		System.out.println("Run time for addition: " + runTimeAdd);
		
		for (int numInts=0; numInts < 100; numInts++) {       
			huge1 = new HugeInteger(1);        
			huge2 = new HugeInteger(1);         
			startTime = System.currentTimeMillis();
			for(int numRun=0; numRun < 100; numRun++) { 
				hugeSubtract = huge1.subtract(huge2); 
			}
			endTime = System.currentTimeMillis();
			runTimeSub +=(double) (endTime - startTime)/((double)100);
		}
		runTimeSub = runTimeSub/((double)100);
		System.out.println("Run time for subtraction: " + runTimeSub);
		
		for (int numInts=0; numInts < 100; numInts++) {
			huge1 = new HugeInteger(1);        
			huge2 = new HugeInteger(1);         
			startTime = System.currentTimeMillis();
			for(int numRun=0; numRun < 100; numRun++) { 
				hugeMultiply = huge1.multiply(huge2); 
			}
			endTime = System.currentTimeMillis();
			runTimeMult +=(double) (endTime - startTime)/((double)100);
		}
		runTimeMult = runTimeMult/((double)100);
		System.out.println("Run time for multiplication: " + runTimeMult);
		
		for (int numInts=0; numInts < 100; numInts++) {
			huge1 = new HugeInteger(1);        
			huge2 = new HugeInteger(1);       
			startTime = System.currentTimeMillis();
			for(int numRun=0; numRun < 100; numRun++) { 
				hugeCompare = huge1.compareTo(huge2); 
			}
			endTime = System.currentTimeMillis();
			runTimeComp +=(double) (endTime - startTime)/((double)100);
		}
		runTimeComp = runTimeComp/((double)100);
		System.out.println("Run time for comparison: " + runTimeComp);
	}
}
