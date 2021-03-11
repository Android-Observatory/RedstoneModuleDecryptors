package com.cosec.decryptor1qaz2wsx;

/***
 * 
 * @author fare9
 *
 */
public class PrimeClass {

	/***
	 * Get if a given number is prime or not.
	 * 
	 * @param i: number to check
	 * @return boolean
	 */
	public static boolean isPrime(int i) {
		if (i < 2) {
			return false;
		}
		if (i == 2) {
			return true;
		}
		if (i % 2 == 0) {
			return false;
		}
		for (int i2 = 3; i2 * i2 <= i; i2 += 2) {
			if (i % i2 == 0) {
				return false;
			}
		}
		return true;
	}
}
