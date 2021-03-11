package com.cosec.decryptor1qaz2wsx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/***
 * 
 * @author fare9
 *
 *         Crypto class used to decrypt redstone packages loaded dynamically.
 */

public class CryptoClass {
	private static final String ALGORITHM = "PBEWITHMD5ANDDES";
	private static final String TAG = "VerifyUtils";
	private static final String encrypt_header = "1qaz2wsx!@#$";
	private static final String iv = "def8&#@sdf23dfer";
	private static final String q = "dsf#&*HJhkj23hjsHKHYYy8868ggjF88^%&$$GjgjLJLGIU868Shjt555GJGJF6765%&%$$h&bgguufff&t&9&*gjhgvvGJUG98676==";
	private static final String salt = "12345678";

	/***
	 * Method to store given content into a file.
	 * 
	 * @param content:     content of file to write
	 * @param output_file: name of the output file
	 */
	private static void saveFile(byte[] content, java.lang.String output_file) {
		FileOutputStream fos = null;
		try {
			File oFile = new File(output_file);
			if (oFile.exists()) {
				oFile.delete();
			}
			oFile.createNewFile();
			fos = new FileOutputStream(oFile);

			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(content);
			bos.flush();
			fos.close();

			System.out.println("[!] File saved in " + output_file);

		} catch (FileNotFoundException ex) {
			Logger.getLogger(CryptoClass.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(CryptoClass.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				fos.close();
			} catch (IOException ex) {
				Logger.getLogger(CryptoClass.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/***
	 * Method to test if the given file is encrypted or not, checking with magic
	 * bytes of encrypted file.
	 * 
	 * @param file: file to test if is encrypted.
	 * @return - true: if header is correct. - false: if header is empty or not the
	 *         same header.
	 */
	public static boolean isEncrypted(String file) {
		try {
			String header = getHeader(file);
			return !header.isEmpty() && header.equals(encrypt_header);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * Take a password to generate a SecretKey object for PBEWITHMD5ANDDES
	 * 
	 * @param password
	 * @return Key object
	 */
	private static Key getPBEKey(String password) {
		try {
			return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new PBEKeySpec(password.toCharArray()));
		} catch (Exception e) {
			System.out.println(TAG + e.getMessage());
			return null;
		}
	}

	public static String getK(String input_file) {
		int i = 0;
		try {
			byte[] header2 = getHeader2(input_file);
			String header2_str = "";
			for (int index = 0; index < header2.length; index++)
				header2_str += (char) header2[index];
			System.out.println("[+] Header2 of file = " + header2_str);
			byte[] bArr = new byte[iv.getBytes().length];
			if (header2 == null) {
				return "";
			}
			for (int i2 = 0; i2 < header2.length; i2++) {
				if (PrimeClass.isPrime(i2)) {
					bArr[i] = header2[i2];
					i++;
				}
				if (i >= iv.getBytes().length) {
					break;
				}
			}

			return new String(bArr);
		} catch (Exception e) {
			System.out.println(TAG + e.getMessage());
			return "";
		}
	}

	private static byte[] getHeader2(java.lang.String input_file) {
		try {
			java.io.File r1 = new java.io.File(input_file);

			if (!r1.exists()) {
				return null;
			}

			java.lang.String bl = "dsf#&*HJhkj23hjsHKHYYy8868ggjF88^%&$$GjgjLJLGIU868Shjt555GJGJF6765%&%$$h&bgguufff&t&9&*gjhgvvGJUG98676==";
			byte[] bf = bl.getBytes();
			int blsize = bf.length;

			ByteArrayOutputStream output_stream = new ByteArrayOutputStream(blsize);
			RandomAccessFile r2 = new RandomAccessFile(input_file, "r");

			String header = "1qaz2wsx!@#$";
			byte[] header_bytes = header.getBytes();
			int header_size = header_bytes.length;
			long header_size_long = (long) header_size;
			r2.seek(header_size_long);
			java.lang.String base64_string = "dsf#&*HJhkj23hjsHKHYYy8868ggjF88^%&$$GjgjLJLGIU868Shjt555GJGJF6765%&%$$h&bgguufff&t&9&*gjhgvvGJUG98676==";
			byte[] base64_bytes = base64_string.getBytes();
			int base64_size = base64_bytes.length;
			byte[] buffer_for_file = new byte[base64_size];
			int size_read = r2.read(buffer_for_file, 0, base64_size);
			r2.close();
			output_stream.write(buffer_for_file, 0, base64_size);
			String string_of_output_stream = new String(output_stream.toByteArray());
			String verifyutils = "VerifyUtils";
			StringBuilder header_equals = new StringBuilder("header2=");
			StringBuilder string_builder_appended = header_equals.append(string_of_output_stream);
			String final_string = string_builder_appended.toString();
			System.out.println(verifyutils + final_string);
			byte[] array_return = output_stream.toByteArray();
			return array_return;
		} catch (FileNotFoundException ex) {
			Logger.getLogger(CryptoClass.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(CryptoClass.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private static String getHeader(String str) throws IOException {
		BufferedInputStream bufferedInputStream;
		Throwable th;
		IOException e;
		String str2 = "";
		File file = new File(str);
		if (!file.exists()) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(encrypt_header.getBytes().length);
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			try {
				int length = encrypt_header.getBytes().length;
				byte[] bArr = new byte[length];
				byteArrayOutputStream.write(bArr, 0, bufferedInputStream.read(bArr, 0, length));
				for (int i = 0; i < bArr.length; i++)
					str2 += (char) bArr[i];

				System.out.println("[+] File header = " + str2);
				try {
					bufferedInputStream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				byteArrayOutputStream.close();
				return str2;
			} catch (IOException e3) {
				e = e3;
				try {
					e.printStackTrace();
					try {
						bufferedInputStream.close();
					} catch (IOException e4) {
						e4.printStackTrace();
					}
					byteArrayOutputStream.close();
					return "";
				} catch (Throwable th2) {
					th = th2;
					try {
						bufferedInputStream.close();
					} catch (IOException e5) {
						e5.printStackTrace();
					}
					byteArrayOutputStream.close();
					throw th;
				}
			}
		} catch (IOException e6) {
			IOException iOException = e6;
			bufferedInputStream = null;
			e = iOException;
			e.printStackTrace();
			bufferedInputStream.close();
			byteArrayOutputStream.close();
			return "";
		} catch (Throwable th3) {
			Throwable th4 = th3;
			bufferedInputStream = null;
			th = th4;
			bufferedInputStream.close();
			byteArrayOutputStream.close();
		}

		return null;
	}

	private static byte[] fileToByteWithHeader(String str) {
		File file = new File(str);
		if (!file.exists()) {
			return null;
		}
		int length = encrypt_header.getBytes().length + q.getBytes().length + 16;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(((int) file.length()) - length);
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
			byte[] bArr = new byte[1024];
			randomAccessFile.seek((long) length);
			while (true) {
				int read = randomAccessFile.read(bArr);
				if (-1 == read) {
					byte[] byteArray = byteArrayOutputStream.toByteArray();
					byteArrayOutputStream.close();
					randomAccessFile.close();
					return byteArray;
				}
				byteArrayOutputStream.write(bArr, 0, read);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private static byte[] decrypt(String str, byte[] bArr) {
		try {
			Key pBEKey = getPBEKey(str);
			System.out.println("[*] Key for decryption: " + pBEKey.toString());
			PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(salt.getBytes(), 1);
			Cipher instance = Cipher.getInstance(ALGORITHM);
			instance.init(2, pBEKey, pBEParameterSpec);
			return instance.doFinal(bArr);
		} catch (Exception ex) {
			Logger.getLogger(CryptoClass.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static boolean decrypt(String input_file, String output_file) {
		try {
			System.out.println("[+] Decrypting the file " + input_file + " to " + output_file);
			String k = getK(input_file);
			System.out.println("[+] Value k used to get key " + k);
			if (!k.isEmpty()) {
				byte[] fileToByteWithHeader = fileToByteWithHeader(input_file);
				if (fileToByteWithHeader != null) {
					byte[] decrypt = decrypt(k, fileToByteWithHeader);
					if (decrypt != null) {
						System.out.println("[!] Decryption correct, saving in " + output_file);
						saveFile(decrypt, output_file);
					}
				}
			} else {
				System.out.println(TAG + "get k failed......");
			}
		} catch (Exception e) {
		}
		return false;
	}
}
