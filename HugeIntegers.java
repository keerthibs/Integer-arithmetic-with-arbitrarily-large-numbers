import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.print.attribute.standard.PrinterMessageFromOperator;

/**
 * Class that computes Addition, subtraction, product, and power of two large
 * integers
 * 
 * @author Keerthi , Monish, Aastha, Maitri
 * 
 */
public class HugeIntegers extends ArrayList<Integer> {

	static final int base = 10;
	boolean minusValue = false;
	boolean initialValue = false;
	int length = 0;

	// constructors
	public HugeIntegers(String input) {
		convert(input);
	}

	public HugeIntegers(Long longInput) {
		convert(longInput.toString());
	}

	public HugeIntegers() {

	}

	/**
	 * Helper function that converts the given String into HugeInteger
	 * 
	 * 
	 * @param input
	 *            : String which has to be converted into HugeInteger
	 * @return void
	 */
	void convert(String input) {
		int inputLength = input.length();

		length = inputLength;

		if (input.charAt(0) == '-') {
			this.minusValue = true;
			this.initialValue = true;
		}

		while (inputLength > 1) {
			this.add(Integer.parseInt(String.valueOf(input.charAt(inputLength - 1))));
			inputLength--;
		}

		if (minusValue) {
			// this.add(Integer.parseInt(String.valueOf("0")));

		} else {

			this.add(Integer.parseInt(String.valueOf(input.charAt(inputLength - 1))));
		}

	}

	/**
	 * Helper function that returns the next element of an iterator if present,
	 * false otherwise
	 * 
	 * @param iterator
	 *            : the iterator whos elements are to be inspected
	 * @return element if there exists a next element in iterator, and null
	 *         otherwise
	 */
	static Integer next(ListIterator<Integer> iterator) {
		if (iterator.hasNext())
			return iterator.next();

		else
			return null;
	}

	/**
	 * Helper function for performing addition function - THis uses the main addition() function
	 * and sub() function to give the desired output. This helps in handling negative integers too
	 * 
	 * 
	 * @param a
	 *            : the first operand as HugeInteger object
	 * @param b
	 *            : the second operand as HugeInteger object
	 * @return HugeInteger - the result of a+b
	 */

	public static HugeIntegers add(HugeIntegers a, HugeIntegers b) {
		HugeIntegers c = new HugeIntegers("0");
		printFlag = 0;
		// b-negative a-positive So a-b
		if (b.minusValue && !a.minusValue) {
			b.minusValue = false;
			c = sub(a, b);

		} else if (a.minusValue && !b.minusValue) {
			// b-positive a-negative So b-a
			a.minusValue = false;
			c = sub(b, a);

		} else if (a.minusValue && b.minusValue) {

			// both negative so -(a+b)
			c = c.addition(a, b);
			c.minusValue = true;

		} else {
			// both positive so a+b
			c.minusValue = false; // optional line
			c = c.addition(a, b);
		}

		refresh(a, b);//refreshes the list, so that it restores a and b values.
		return c;

	}

	static void refresh(HugeIntegers a, HugeIntegers b) {
		a.minusValue = a.initialValue;
		b.minusValue = b.initialValue;
	}

	/**
	 * Procedure to calculate sum of two large integers. Stores the sum of List
	 * x and List y into List z
	 *
	 * @param a
	 *            : HugeIntegers List - the 1st summand - the numbers are stored from
	 *            right to left
	 * @param b
	 *            : HugeIntegers List - the 2nd summand - the numbers are stored from
	 *            right to left Pre-condition : Each index of list will hold
	 *            only one digit Else result is arbitary
	 * @param z
	 *            : Output, i.e the sum of the two integers in a and b
	 * 
	 * 
	 */

	public static HugeIntegers addition(HugeIntegers a, HugeIntegers b) {

		HugeIntegers z = new HugeIntegers();

		if (a == null || b == null || a.isEmpty() || b.isEmpty()) {
			System.out.println("One or more input is empty");
			return null;
		}
		ListIterator<Integer> aIterator = a.listIterator();
		ListIterator<Integer> bIterator = b.listIterator();

		Integer aTemp = next(aIterator);// Holds List x element
		Integer bTemp = next(bIterator);// Holds List y element

		int carry = 0; // holds the digit to carry forward to the next left
		// digit
		int sum = 0; // temporary variable to store the sum of two digits

		while (aTemp != null && bTemp != null) {
			// Calculating the sum of element pointed by x iterator and y
			// iterator and carry
			// System.out.println(aTemp +" "+bTemp);
			sum = aTemp + bTemp + carry;
			// When sum exceeds the base value, we take the carry by division of
			// sum by base, else
			// carry is set to 0 in other cases
			if (sum > base - 1) {
				carry = sum / base;
			} else {
				carry = 0;

			}
			// The remainder is stored in the output as sum of two digits
			sum = sum % base;
			z.add(sum);
			aTemp = next(aIterator);
			bTemp = next(bIterator);
		}

		// When list y is exhausted, elements of list x is copied to the output
		// along with the carry
		while (aTemp != null) {
			if (carry != 0) {
				sum = aTemp + carry;
				if (sum > base - 1) {
					carry = sum / base;
				} else {
					carry = 0;
				}
				sum = sum % base;
			} else {
				sum = aTemp;
			}
			z.add(sum);
			aTemp = next(aIterator);
		}
		// When list x is exhausted, elements of list y is copied to the output
		// along with the carry
		while (bTemp != null) {
			if (carry != 0) {
				sum = bTemp + carry;
				if (sum > base - 1) {
					carry = sum / base;
				} else {
					carry = 0;
				}
			} else {
				sum = bTemp;
			}
			z.add(sum);
			bTemp = next(bIterator);
		}

		// Adding any addition carry that is overflowing
		if (carry != 0)
			z.add(carry);

		return z;
	}

	/**
	 * Procedure to trim the leading zeroes in a list Return the list after
	 * removing the leading zeroes(non significant zeros) if any present.
	 * 
	 * @param a
	 *            : Integer List. removes all non significant zeros in the list,
	 *            else returns the list unchanged
	 */
	static void trim(List<Integer> a) {
		int i = a.size() - 1;

		while (a.get(i).intValue() == 0 && i != 0) {
			a.remove(i);
			i--;
		}

	}


	static void trimReverse(List<Integer> a) {
		int n = a.size();
		if (n == 1) {
			return;
		}

		for (int i = 0; i < n; i++) {
			if (a.get(i).intValue() == 0) {
				a.remove(i);
			} else {
				return;
			}
		}
	}

	/**
	 * Procedure that returns 1 is a is bigger than b,0 if equal, -1 is a is smaller
	 * removing the leading zeroes(non significant zeros) if any present.
	 * 
	 * @param a and b
	 *            : HugeIntegers references who's bigger among the two is to be found and
	 *            return  a result
	 *            
	 */
	

	public static int bigger(HugeIntegers a, HugeIntegers b) {

		trim(a);
		trim(b);
		
		int aSize = a.size();
		int bSize = b.size();
		if (aSize > bSize) {
			return 1; 
		} else if (aSize < bSize) {
			return -1; 
		} else {

			aSize--;
			while (aSize != 0) {

				if (a.get(aSize) > b.get(aSize)) {
					return 1;
				} else if (a.get(aSize) < b.get(aSize)) {
					return -1;
				} else {
					aSize--;

				}

			}
			return 0;
		}
	}
	/**
	 * Procedure to calculate power of a large integer. 
	 *
	 * @param a
	 *            : HugeIntegers List - the numbers are stored from
	 *            right to left
	 * @param n
	 *            : Long digit which represents the power component
	 * @param z
	 *            : Output, i.e the power of a raised to the power n
	 * 
	 * 
	 */
	public HugeIntegers power(HugeIntegers a, Long n) {
		printFlag = 1;
		if (n == 0) {
			HugeIntegers x = new HugeIntegers("1");
			return x;
		} else if (n == 1) {
			return a;
		} else {
			HugeIntegers half = power(a, n / 2);

			HugeIntegers result = multiply(half, half);

			if (n % 2 == 0) {
				return result;
			} else {
				HugeIntegers result2 = multiply(result, a);
				return result2;

			}

		}

	}
	/**
	 * Helper function for performing subtraction function - THis uses the main subtraction() function
	 * and add() function to give the desired output. This helps in handling negative integers too
	 * 
	 * 
	 * @param a
	 *            : the first operand as HugeInteger object
	 * @param b
	 *            : the second operand as HugeInteger object
	 * @return HugeInteger - the result of a-b
	 */
	public static HugeIntegers sub(HugeIntegers a, HugeIntegers b) {
		printFlag = 0;
		HugeIntegers c = new HugeIntegers("0");

		if (a.minusValue && b.minusValue) {

			if (bigger(a, b) >= 0) {
				a.minusValue = false;
				b.minusValue = false;
				c = c.subtraction(a, b);
				c.minusValue = true;
				return c;
			} else {
				a.minusValue = false;
				b.minusValue = false;
				c = c.subtraction(b, a);
				c.minusValue = false;
				return c;
			}

		} else if (!a.minusValue && !b.minusValue) {

			if (bigger(a, b) >= 0) {
				c = c.subtraction(a, b);

			} else {
				c = c.subtraction(b, a);
				c.minusValue = true;

			}

		} else if (a.minusValue && !b.minusValue) {
			a.minusValue = false;
			b.minusValue = false;
			c = c.add(a, b);
			c.minusValue = true;

		} else if (!a.minusValue && b.minusValue) {
			a.minusValue = false;
			b.minusValue = false;
			c = c.add(a, b);

		} else {
			c = c.subtraction(a, b);

		}
		refresh(a, b);
		return c;
	}

	/**
	 * Procedure to calcuate difference of two large integers. Stores the
	 * difference of HugeIntegers a and HugeIntegers b in HugeIntegers z
	 *
	 * @param a
	 *            :  the minuend - the numbers are stored from
	 *            right to left
	 * @param b
	 *            :  the subtrahend - the numbers are stored from
	 *            right to left Pre-condition :  Each index of list will hold
	 *            only one digit Else result is arbitary
	 * @param z
	 *            : Output, i.e the difference
	 * 
	 * 
	 */
	public static HugeIntegers subtraction(HugeIntegers a, HugeIntegers b) {
		HugeIntegers c = new HugeIntegers();
		
		if (a == null || b == null || a.isEmpty() || b.isEmpty()) {
			System.out.println("One or more input is empty");
			return null;
		}

		int aSize = a.size();
		int bSize = b.size();

		// Logic to append leading zeros if List Y is smaller than List X
		if (bSize < aSize) {
			while (bSize < aSize) {
				b.add(0);
				bSize++;
			}
		}
		ListIterator<Integer> aIterator = a.listIterator();
		ListIterator<Integer> bIterator = b.listIterator();

		Integer aTemp = next(aIterator);// Holds List x element
		Integer bTemp = next(bIterator);// Holds List y element

		int borrow = 0; // variable to store the borrow for assiting the
		// subtraction
		int sub = 0; // temporary variable to store the result of difference
		// between two integers
		while (aTemp != null && bTemp != null) {

			/*
			 * When element pointed by List x iterator is greater than or equal
			 * to y iterator, then borrow is not required.
			 */
			if (aTemp >= bTemp) {
				borrow = 0;
			}
			/*
			 * In other case where Y is greater than X, we borrow one from the
			 * left, borrow is set to 1, and base is added to the element
			 * pointed by x iterator
			 */
			else {
				aTemp = aTemp + base;
				borrow = 1;
			}
			// subraction is performed and stored in a temporary variable - sub.
			sub = aTemp - bTemp;
			c.add(sub); // difference added to the output.
			aTemp = next(aIterator); // we move to the next digit of List x

			// Check if the value pointed by x iterator is not null, then
			// subtract borrow from the temp.
			// borrow will be 0 in case if X>=Y in above steps, 1 otherwise. If
			// x is null then set aTemp
			// to zero.
			if (aTemp != null)
				aTemp = aTemp - borrow;
			else
				aTemp = 0;
			// If bTemp is not null, then move to the next digit of List y
			if (bTemp != null)
				bTemp = next(bIterator);

		}
		// When List y is exhausted, we add the remaining elements in List x to
		// the output
		while (aTemp != null) {
			c.add(aTemp);
			aTemp = next(aIterator);
		}
		trim(b);

		trim(c);// this function trims the leading zeroes in the output.
		
		return c;
	}

	/**
	 * Helper function for performing product function - THis uses the main multiply() function
	 * to give the desired output. 
	 * 
	 * 
	 * @param A
	 *            : the first operand as HugeInteger object
	 * @param B
	 *            : the second operand as HugeInteger object
	 * @return HugeInteger - the result of a*b
	 */
	public HugeIntegers product(HugeIntegers A, HugeIntegers B) {
		Collections.reverse(A);
		Collections.reverse(B);
		HugeIntegers c = multiply(A, B);
		c.trimReverse(c);
		printFlag = 1;
		return c;
	}

	static int printFlag = 0;

	/**
	 * Procedure to calcuate Product of two large integers. Stores the
	 * output of product of HugeIntegers a and HugeIntegers b in HugeIntegers z
	 *
	 * @param A
	 *            :  the first operand - the numbers are stored from
	 *            right to left
	 * @param B
	 *            :  the second operand - the numbers are stored from
	 *            right to left Pre-condition : Each index of A and B will hold
	 *            only one digit Else result is arbitary
	 * @param z
	 *            : Output, i.e the difference
	 * 
	 * 
	 */
	public HugeIntegers multiply(HugeIntegers A, HugeIntegers B) {

		int n2 = B.size();
		int n1 = A.size();

		int n = 0;

		if (n1 < n2) {
			n = n2;
		} else {
			n = n1;
		}
		if (n == 1) {

			Integer product = A.get(0) * B.get(0);
			HugeIntegers hi = new HugeIntegers(product.toString());
			Collections.reverse(hi);
			return hi;
		} else {

			if (n % 2 != 0) {
				n++;
			}

			A = paddingZeroes(A, n1, n);
			B = paddingZeroes(B, n2, n);

			HugeIntegers A1 = new HugeIntegers();
			HugeIntegers A2 = new HugeIntegers();
			HugeIntegers B1 = new HugeIntegers();
			HugeIntegers B2 = new HugeIntegers();

			for (int i = 0; i < n / 2; i++) {
				A1.add(A.get(i));
				B1.add(B.get(i));
			}

			for (int i = n / 2; i < n; i++) {
				A2.add(A.get(i));
				B2.add(B.get(i));
			}

			HugeIntegers result1 = multiply(A1, B1);

			HugeIntegers result2 = multiplyHelper1(A1, A2);

			HugeIntegers result3 = multiplyHelper1(B1, B2);

			HugeIntegers result4 = multiply(result2, result3);

			HugeIntegers result5 = multiply(A2, B2);

			HugeIntegers result9 = multiplyHelper2(result4, result1);

			result4 = multiplyHelper2(result9, result5);

			result1 = HugeIntegers.baseShift(result1, n);

			result4 = HugeIntegers.baseShift(result4, n / 2);

			HugeIntegers finalResult = multiplyHelper1(result1, result4);

			HugeIntegers fiinal = multiplyHelper1(finalResult, result5);

			return fiinal;

		}

	}
	/**
	 * Function that helps in padding zeroes to assist in multiplication
	 * 
	 * 
	 */
	public HugeIntegers paddingZeroes(HugeIntegers A, int n1, int n) {

		HugeIntegers newNum = new HugeIntegers();

		while (n > n1) {

			newNum.add(0);
			n--;
		}

		for (Integer i : A) {
			newNum.add(i);
		}
		return newNum;
	}

	/**
	 * Procedure that helps in reversing the HugeIntegers to help in 
	 * multiply. 
	 * 
	 */
	public static HugeIntegers multiplyHelper2(HugeIntegers a, HugeIntegers b) {
		Collections.reverse(a);
		Collections.reverse(b);
		HugeIntegers z = new HugeIntegers();
		z = subtraction(a, b);
		Collections.reverse(a);
		Collections.reverse(b);
		Collections.reverse(z);
		return z;

	}
	
	/**
	 * Procedure that helps in reversing the HugeIntegers to help in 
	 * multiply. 
	 * 
	 */
	public static HugeIntegers multiplyHelper1(HugeIntegers a, HugeIntegers b) {
		Collections.reverse(a);
		Collections.reverse(b);
		HugeIntegers z = new HugeIntegers();
		z = addition(a, b);
		Collections.reverse(a);
		Collections.reverse(b);
		Collections.reverse(z);
		return z;

	}

	public static HugeIntegers baseShift(HugeIntegers num, Integer n) {

		while (n > 0) {
			num.add(0);
			n--;
		}
		return num;

	}

	public String toString() {

		if (printFlag == 1) {
			for (Integer i : this) {
				System.out.print(i);
			}

			return "";
		}

		else {
			trim(this);
			int length = this.size() - 1;
			StringBuilder temp = new StringBuilder();
			if (this.minusValue) {
				temp.append("-");
			}
			while (length >= 0) {
				temp.append(this.get(length--).toString());
			}
			return temp.toString();
		}
	}

	void printList(HugeIntegers c) {

		System.out.print(base + " : ");
		for (Integer i : c) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
