package com.cosec.decryptor1qaz2wsx;

import java.io.File;

public class DecryptorMain {

	public static String bss(String str) {
		String substring = str.substring(0, str.lastIndexOf(File.separator));
		return String.valueOf(substring) + File.separator + "temp_" + str.substring(str.lastIndexOf("/") + 1);
	}

	public static void main(String[] args) {
		System.out.println("[+] Started execution of Redstone1qaz2wsz_Decryptor.");
		if (args.length != 2) {
			System.out.println("[-] USAGE: Decryptor1qaz2wsx <file_to_decrypt> <output_file>");
			return;
		}

		if (CryptoClass.isEncrypted(args[0])) {
			CryptoClass.decrypt(args[0], args[1]);
		} else {
			System.out.println("[-] Not found encryption signals in '" + args[0] + "' please try with another file.");
		}

		System.out.println("[+] Finished execution.");
	}

}
