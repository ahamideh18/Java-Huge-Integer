import java.util.Random;

public class HugeInteger {
	
	public int intSize;
	public int[] lgInt;
	public int bool=1;
	
	//Constructor 1
	public HugeInteger(String val) {
		if (val.length()==0) { //Case where empty string is inputted
			throw new IllegalArgumentException("val cannot be an empty string");
		}
		
		if (val.charAt(0)=='-') {  
			bool=0; //If first character in string is '-', the flag indicating whether the HugeInteger is negative is set to 0
            if (val.length()==1 ) {
                throw new IllegalArgumentException("val should contain at least one integer");
            }
            
            if(val.charAt(1)=='0'){
            	for(;val.charAt(1) == '0' && val.length() > 2;) {
                    val=val.substring(1,val.length()); //Removing leading zeroes by taking substring until none remaining
                }
            }
            
            if(val.charAt(1)=='0' && val.length()==2){ //If -0 is inputted, 0 is outputted
            	bool=1;
            }
            
            intSize=val.length()-1;   //the size is 1 less than length of string to account for negative sign                                        
            lgInt=new int[intSize];                                           
            for (int i=1;i<intSize+1;i++) {  //For loop starts at index 1 to skip over negative sign                            
                if(isInteger(val.charAt(i))==0) {    //Function to check if character in string is integer           
                    throw new IllegalArgumentException("val should only consist of integers");
                }
            }
            for(int i=0;i<intSize;i++) {                                     
                lgInt[i]=Integer.parseInt(String.valueOf(val.charAt(i+1))); //Adding each respective integer into array LgInt     
            }
        }

		else {
			bool=1;  //If there is no negative sign in the string, the flag is set to 1 (positive)
			if (val.charAt(0)=='0'){
				String strPattern = "^0+(?!$)";  //Removing leading zeroes using regular expression
				val=val.replaceAll(strPattern, "");
			}
			                                                 
            intSize=val.length();   //Since there is no negative sign in this string, the array size is set to string length                                          
            lgInt=new int[intSize];                                           
            for (int i=0;i<intSize;i++) {                                     
                if (isInteger(val.charAt(i))==0) {    //Check if all characters are integers               
                    throw new IllegalArgumentException("val should only consist of integers");
                }
                else {
                	lgInt[i]=Integer.parseInt(String.valueOf(val.charAt(i))); //Appends respective integers to list
                }
            }
		}
	}	
	
	//Constructor 2
	public HugeInteger(int n) throws IllegalArgumentException{
		if(n < 1) {     //Input checking to make sure that an integer >=1 is inputted 
			throw new IllegalArgumentException("n must be greater than or equal to 1");
		}
		
		else {
			intSize=n;
			Random rand=new Random();
			lgInt=new int[n];    //Creates an array of length n 
			lgInt[0]=rand.nextInt(9)+1;    //Generates a random integer between 1 and 9 since first digit can't be 0 //(int)(Math.random()*8+1);
			for(int i=1; i<n; i++) {
				lgInt[i]=rand.nextInt(10);    //Generate integer between 0 and 9 
			}
			bool=rand.nextInt(2)==1?1:0;    //Randomly chooses either 1 or 0 indicating whether HugeInteger is +ve or -ve
        }
    }
	
	public HugeInteger add(HugeInteger h) {
		String s1=this.toString(); String s2=h.toString();
		int borVal=0;
		String out=""; 
		
        if((h.bool==1 && this.bool==1)||(h.bool==0 && this.bool==0)){ //Condition if both numbers being added have he same sign
        	if(h.bool==0 && this.bool==0) { //If both HugeIntegers are negative. We will add the absolute values, then add a negative sign in front.
        		s1=s1.substring(1,s1.length());
        		s2=s2.substring(1,s2.length());
        	}
        	
        	//We will find the integer with larger absolute value in order to add the smallest integer to larger one
        	//1234567
        	//   3457
        	//+______
        	//For the example above, s2 will be 1234567 (s2 is always the longer integer)
        	if(s2.length()<s1.length()) { 
        		String temp=s1; 
        		s1=s2; 
        		s2=temp; 
        	} 
        	
        	int len1=s1.length(), len2=s2.length(); 
        	s1=new StringBuilder(s1).reverse().toString(); //Strings are reversed in order to add from right to left
        	s2=new StringBuilder(s2).reverse().toString(); 
        	
        	for (int i=0;i<len1;i++) { 
        		int sum=(int)(s1.charAt(i)-'0')+(int)(s2.charAt(i)-'0')+borVal; 
        		out+=(char)(sum%10+'0'); //'0' is being added to account for difference in ascii code
        		borVal=sum/10; //The value carried over to the next digit is the integer division by 10 of the sum of the first digits
        	} 
        	
        	for (int i=len1;i<len2;i++) { //The remaining digits in the longer integer are added in their digit places respectively 
        		int sum=((int)(s2.charAt(i)-'0')+borVal); 
        		out+=(char)(sum%10+'0'); 
        		borVal=sum/10; 
        	} 
        	
        	if (borVal>0) { //If there is still a borrow value, it is added to the beginning of the new sum
        		out+=(char)(borVal+'0');
        	}
        	
        	out=new StringBuilder(out).reverse().toString(); //The final string is reversed once again to display the integer correctly
        	HugeInteger HI=new HugeInteger(out);  //The string is converted to type HugeInteger
        	if(h.bool==0 && this.bool==0) {
        		HI.bool=0; //If both integers are negative, we add a negative sign to the HugeInteger since we added absolute values initially.
        	}
        	return HI;
        }
        
        else { //Checks whether sign is different from the other, in that case a subtraction is performed
        	if(s1.charAt(0)=='-') { //Subtract absolute values instead of adding a negative
        		s1=s1.substring(1);
        	}
        	if(s2.charAt(0)=='-') {
        		s2=s2.substring(1);
        	}
        	
        	if(lessTest(s1,s2)) //Switch HugeIntegers if needed in order to subtract the smaller one from the larger one
        	{ 
        		String temp=s1; 
        		s1=s2; 
        		s2=temp; 
        	} 

        	int len1=s1.length(), len2=s2.length(); 
        	s1=new StringBuilder(s1).reverse().toString(); //Reverse the strings to subtract them from right to left
        	s2=new StringBuilder(s2).reverse().toString(); 

        	for (int i=0;i<len2;i++) { 
        		int sum=(int)(s1.charAt(i)-'0')-(int)(s2.charAt(i)-'0')-borVal; //Corresponding digits are subtracted allng with any value borrowed from the previous subtraction
        		if (sum<0) { 
        			sum=sum+10; //If the subtraction of any two digits give a negative number, we add 10 and also add a borrow value of 1
        			borVal=1; 
        		} 
        		else if(sum>0){
        			borVal=0;
        		}
        		out+=(char)(sum + '0'); //Append the resulting digit to the output string with '0' to compensate for ascii difference
        	}
        	for (int i=len2;i<len1;i++) { //This for loop subtracts the remaining digits for the longer integer
        		int sum=(int)(s1.charAt(i)-'0')-borVal; 
        		if (sum<0) { 
        			sum=sum+10; 
        			borVal=1; 
        		} 
        		else {
        			borVal=0; 
        		}
        		out+=(char)(sum+'0'); 
        	}

        	out=new StringBuilder(out).reverse().toString(); 
        	HugeInteger HI=new HugeInteger(out); //Uses the HugeInteger constructor with the resulting string
        	
        	if((h.bool==0 && absVal(h).compareTo(absVal(this))==1)||(this.bool==0 && absVal(this).compareTo(absVal(h))==1)) {
        		HI.bool=0; //If the length of a negative number is larger than a positive number, a negative sign is added.
        	}
        	return HI;
       	}
	}
	
	public  HugeInteger subtract(HugeInteger h) { //Subtract uses the add method. This method just manipulates the sign of the HugeInteger
		if(h.bool==0 && this.bool==1){ //Performs this-(-h) if h is negative; just add this-(-h)=this+h
			h.bool=1; 
			HugeInteger sub=h.add(this);
			return sub;
		}
		else if(h.bool==1 && this.bool==0){ //-this-h
			this.bool=1;  //Takes the absolute value of this because -this-h=-(this+h)
			HugeInteger sub=h.add(this);
			sub.bool=0; //Add a negative sign to the integer
			return sub;
		}
		
		else if(h.bool==1 && this.bool==1){ //this-h
			if(h.compareTo(this)==1||h.compareTo(this)==0) { //Finds the larger integer to determine if result is positive or negative depending on if h is smaller than this
				h.bool=0; //changes h sign to negative
				HugeInteger sub=this.add(h); //the add() method handles if the integers have different signs then subtract
				sub.bool=0; //If h is larger the result is negative
				return sub;
			}
			else {
				h.bool=0; //If h is smaller than this the result is positive
				HugeInteger sub=this.add(h);
				return sub;
			}
		}
		
		else { //Condition if both integers are negative. -this-(-h)=-this+h
			if(h.compareTo(this)==1||h.compareTo(this)==0) { //If h is larger the result is positive
				h.bool=1; //Add absolute value of h to -this
				HugeInteger sub=this.add(h);
				sub.bool=0;
				return sub;
			}
			else { //If h is smaller the result will be negative
				h.bool=1;
				HugeInteger sub=this.add(h);
				return sub;
			}
		}
	}
	
	public HugeInteger multiply(HugeInteger h) {
		String s1=this.toString(); String s2=h.toString(); //Convert both HugeIntegers to strings
		
		if(this.bool==0) { //We multiply the absolute values, then add negative sign at end.
    		s1=s1.substring(1,s1.length());
		}
		
		if(h.bool==0) { 
			s2=s2.substring(1,s2.length());
		}
		
		s1=new StringBuilder(s1).reverse().toString(); //Reverse the strings to multiply them from right to left
    	s2=new StringBuilder(s2).reverse().toString();
    	
    	int[] result=new int[s1.length()+s2.length()]; //The result is stored in array of len1+len2 since len(x*y)=len(x)+len(y)
        
    	for (int i=0;i<s1.length();i++)  {  //Traverse each integer
    		for (int j=0;j<s2.length();j++)  { 
    			result[i+j]=result[i+j]+(s1.charAt(i)-'0')*(s2.charAt(j)-'0'); //Multiply each integer in the first number with its corresponding in the second
    		} 
    	}
    	
    	String prod=new String(); //Each respective integer in the array is appended to the product string
        for (int i=0;i<result.length;i++) 
        { 
            int digit=result[i]%10; 
            int borVal=result[i]/10; 
            if(i+1<result.length) 
            { 
                result[i+1]=result[i+1]+borVal; 
            } 
            prod=digit+prod; 
        } 
        
        while(prod.length()>1 && prod.charAt(0)=='0') { //Remove any leading zeroes from the product
            prod=prod.substring(1); 
        } 
        
        HugeInteger out=new HugeInteger(prod);
        
        if((h.bool==0 && this.bool==1)||(h.bool==1 && this.bool==0)) { //If one sign is negative and the other positive the product is negative
	    	out.bool=0;
	    }
	    else if((h.bool==1 && this.bool==1)||(h.bool==0 && this.bool==0)) { //If both signs are positive or negative the product is positive
	    	out.bool=1;
	    }
        
        return out;
	}
	
	public int compareTo(HugeInteger h) { //compareTo returns 1 if this is larger than h;-1 if h is larger than this;0 otherwise
		if(h.bool==0 && this.bool==0){ //If both signs are negative, continue                     
            if(h.intSize>this.intSize){ //The longer integer with negative sign is smaller
                return 1;
            }
            else if(this.intSize>h.intSize){
                return -1;
            }
            else if(this.intSize==h.intSize){ //If the sizes are equal, this loop compares each digit one by one
            	for(int i=0;i<this.intSize;i++) {
            		if(h.lgInt[i]>this.lgInt[i]) { //If the digit of this is less than h, 1 is returned because the number is negative
            			return 1;
            		}
            		if(this.lgInt[i]>h.lgInt[i]) { //If this digit is larger than h digit, -1 is returned because the number is negative
            			return -1;
            		}
            	}
            	return 0; //If the numbers are equal
            } 
		}
		
		else if(h.bool==1 && this.bool==1) { //If both are positive, a similar algorithm to if both are negative is followed
			if(h.intSize>this.intSize){ //Firstly compares integer length, the longer integer is also the larger
                return -1;
            }
			else if(this.intSize>h.intSize){
                return 1;
            }
            else if(this.intSize==h.intSize){
            	for(int i=0;i<this.intSize;i++) { //If they have the same length each digit is compared 1-by-1
            		if(h.lgInt[i]>this.lgInt[i]) { //The larger corresponding digit means the larger number since it is positive
            			return -1;
            		}
            		if(this.lgInt[i]>h.lgInt[i]) { //If h digit is smaller than this.digit 1 is returned since the number is positive
            			return 1;
            		}
            	}
            	return 0; //If they are equal
            }
		}
		
		else if(h.bool==1 && this.bool==0) { //If h is positive while this is negative, -1 is returned
			return -1;
		}
		
		else {
			return 1;
		}
		return 0; //If no conditions are met, the integers are equal
	}
	
	public String toString() { //Converts HugeInteger to string representation of the number
        String output="";
        if(bool==0 && this.lgInt[0]==0 && this.intSize==1) { //If the HugeInteger is one digit that is 0, the negative sign will be removed
        	bool=1;
        }
        if(bool==0) {    //If the HugeInteger flag is 0, a negative sign is put at the beginning of string and opposite if 1
            output+="-";
        }
        for (int i=0;i<intSize;i++) {
            output+=lgInt[i];
        }
        return output;
    }
	
	static int isInteger(char c) {
		if(c>='0' && c<='9') {     //Checks if character in string is between 0 and 9 using ASCII standard
			return 1;
		}
		else return 0;
	}
	
	static HugeInteger absVal(HugeInteger h) {
		if(h.bool==0) {
			h.bool=1;
		}
		return h;
	}
	
	static boolean lessTest(String s1, String s2) 
	{ 
		int len1=s1.length(), len2=s2.length(); //Checks if length of one HugeInteger is less than the other
		if (len2<len1) 
			return false; 
		if (len1<len2) 
			return true; 
		
		for (int i=0;i<len1;i++) //If sizes are equal, check for the greater in terms of each digit (ex. 9>3)
		if (s1.charAt(i)<s2.charAt(i)) 
			return true; 
		else if (s1.charAt(i)>s2.charAt(i)) 
			return false; 

		return false; 
	} 
	
	
} //End HugeInteger class

