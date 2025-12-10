package com.scm.common.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncrypter {

	protected static final String KEY_FILE = "key.bin";
	private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	protected static final String ALGO_AES = "AES";
	private static final String DES_TRANSFORMATION = "DES/CBC/PKCS5Padding";
	protected static final String ALGO_DES = "DES";
	private static final int AES_MAX_KEY_STRENGTH = 256; // only used for AES
	protected static String algo = ALGO_DES.equals(System.getProperty("com.scm.encryption")) ? ALGO_DES : ALGO_AES;
	protected static boolean KEY_ENCRYPTION_PBE = "PBE".equals(System.getProperty("com.scm.secret.key"));
	private static int keySize;

	static {
		String tmp = System.getProperty("com.scm.keySize");
		if (tmp == null) {
			System.out.println("KeySize passed is " + tmp + ". Defaulting to " + AES_MAX_KEY_STRENGTH);
			keySize = AES_MAX_KEY_STRENGTH;
		} else {
			if ("128".equals(tmp) || "192".equals(tmp) || "256".equals(tmp))
				keySize = Integer.parseInt(tmp);
			else {
				System.out.println("Invalid keySize supplied in -Dcom.scm.keySize. Valid values are 128, 192, 256(default). Using 256");
				keySize = AES_MAX_KEY_STRENGTH;
			}
		}
	}

	public static InputStream getInputStream(String path) {
		try {
			InputStream is = null;
			try {
				is = new FileInputStream(path);
			} catch (Exception x) {
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			}
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE, getSecretKey(null, null));
			CipherInputStream cis = new CipherInputStream(is, cipher);
			return cis;
		} catch (Exception x) {
			if (x instanceof StreamCorruptedException)
				System.err.println("Most likely the key is generated using different algo than one provided. " + "" + "Override algo used using -Dscm.com.encryption="
						+ ((ALGO_DES.equals(algo)) ? ALGO_AES : ALGO_DES));
			x.printStackTrace();
			throw new RuntimeException("Error decrypting file: " + path, x);
		}
	}

	public static void main(String[] arg) throws Exception {
		if (arg.length < 2) {
			System.err.println("FileEncrypter <key_file_dir> account_list <input_file1> <input_file2>");
			System.exit(1);
		}

		File keyDir = new File(arg[0]);
		if (!keyDir.isDirectory()) {
			System.err.println("Key file directory invalid: " + arg[0]);
			System.exit(2);
		}

		// obtain encryption key or generate one in keyDir
		String accountList = arg[1]; // accountList - the account list will be
										// stored in the key.bin file and used
										// as part of key to encrypt the secret
										// key
		SecretKey key = getSecretKey(keyDir, accountList); //
		if (key == null) {
			System.err.println("Key file unreachable in: " + keyDir);
			System.exit(3);
		}

		// encrypt the given list of files
		for (int i = 2; i < arg.length; i++) {
			try {
				File src = new File(arg[i]);
				if (src.isFile()) {
					// prepare streams
					FileInputStream fis = new FileInputStream(src);
					FileOutputStream fos = new FileOutputStream(arg[i] + ".bin");
					Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key);
					CipherOutputStream cos = new CipherOutputStream(fos, cipher);

					// encrypt file
					int count = 0;
					byte[] buf = new byte[1024];
					while ((count = fis.read(buf)) >= 0) {
						cos.write(buf, 0, count);
					}

					cos.close();
					fis.close();
				} else {
					System.err.println("Input file not found: " + arg[i]);
				}
			} catch (Exception x) {
				System.err.println("Error processing input file: " + arg[i]);
				x.printStackTrace(System.err);
			}
		}

		System.out.println("File(s) encrypted successfully.");
		System.out.println("Please refresh your project now.");

		System.exit(0);
	}

	protected static Cipher getCipher(int mode, SecretKey key) throws Exception {
		byte[] iv = { (byte) 0xec, 0x1d, 0x26, 0x2b, (byte) 0xf2, (byte) 0xd9, (byte) 0xb3, 0x22 }; // for
																									// older
																									// DES
																									// algo

		// Instead of hardcoding the Iv get it from Key itself
		IvParameterSpec spec = null;
		if (ALGO_AES.equals(algo)) {
			int iVsize = 16;// (ALGO_AES.equals(algo))?16:8;
			byte[] tempIv = new byte[iVsize];
			for (int i = 0; i < iVsize; i++)
				tempIv[i] = key.getEncoded()[i];
			spec = new IvParameterSpec(tempIv);
		} else
			spec = new IvParameterSpec(iv);

		Cipher cipher;
		if (ALGO_AES.equals(algo))
			cipher = Cipher.getInstance(AES_TRANSFORMATION);
		else
			cipher = Cipher.getInstance(DES_TRANSFORMATION);
		cipher.init(mode, key, spec);
		return cipher;
	}

	private static SecretKey getSecretKey(File genKeyDir, String accountList) throws Exception {
		// read key file or, if not found, generate one at genKeyDir location

		// look for key file from classpath
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEY_FILE);
		if (in == null) {
			// if in read mode, stop and report problem
			if (genKeyDir == null) {
				throw new IllegalStateException("Decryption key not found in classpath");
			}

			// if key file already exists, use it, otherwise generate one
			File genKeyFile = new File(genKeyDir, KEY_FILE);
			if (genKeyFile.isFile()) {
				in = new FileInputStream(genKeyFile);
			} else {
				return createKeyFile(genKeyFile, accountList);
			}
		}

		// read key file
		CipherInputStream cis = null;
		if (KEY_ENCRYPTION_PBE)
			cis = new CipherInputStream(in, getPBECipher(Cipher.DECRYPT_MODE));
		else {
			// read account list
			int length = in.read();
			byte[] accountBytes = new byte[length];
			in.read(accountBytes);

			// check runtime account permission on decryption
			String accounts = new String(accountBytes);
			String[] approvedAccounts = accounts.split(",");
			// if (! OSAccountChecker.checkAccountPermissions(approvedAccounts))
			// throw new
			// RuntimeException("the account has no permission to run decryption");

			cis = new CipherInputStream(in, getAESCipher(Cipher.DECRYPT_MODE, accountBytes));
		}
		ObjectInputStream ois = new ObjectInputStream(cis);
		SecretKey key = (SecretKey) ois.readObject();
		ois.close();

		return key;
	}

	protected static SecretKey createKeyFile(File genKeyFile, String accountList) throws Exception {
		// generate key
		KeyGenerator keyGen = KeyGenerator.getInstance(algo);

		if (ALGO_AES.equals(algo)) {
			int maxKeySize = Cipher.getMaxAllowedKeyLength(AES_TRANSFORMATION);
			if (maxKeySize < keySize) {
				System.out.println("Max key size supported is lower than supplied " + keySize + " bits. Either download unlimited strength JRE or "
						+ "override key strength using -Dscm.com.keySize. \nUsing max allowed strenght of keyLength=" + maxKeySize);
				// throw new
				// InvalidKeyException("Max key size supported is lower than default. Either download unlimited strength JRE or "
				// +
				// "override key strength using -Dscm.com.keySize=");
				keyGen.init(maxKeySize);
			} else {
				keyGen.init(keySize);
				System.out.println("Using keySize=" + keySize);
			}
		}
		SecretKey key = keyGen.generateKey();

		// write out to key file
		FileOutputStream fos = new FileOutputStream(genKeyFile);
		CipherOutputStream cos = null;
		if (KEY_ENCRYPTION_PBE) {
			cos = new CipherOutputStream(fos, getPBECipher(Cipher.ENCRYPT_MODE));
		} else {
			byte[] accountBytes = accountList.getBytes();
			fos.write(accountBytes.length);
			fos.write(accountBytes);
			cos = new CipherOutputStream(fos, getAESCipher(Cipher.ENCRYPT_MODE, accountBytes));
		}
		ObjectOutputStream oos = new ObjectOutputStream(cos);
		oos.writeObject(key);
		oos.close();

		System.out.println("Key file generated successfully: " + genKeyFile);

		return key;
	}

	protected static Cipher getPBECipher(int mode) throws Exception {
		int iterationCount = 3;
		byte[] salt = { 0x3d, 0x02, 0x57, (byte) 0x9b, 0x1f, (byte) 0xfd, (byte) 0xc4, 0x61 };
		PBEKeySpec keySpec = new PBEKeySpec(FileEncrypter.class.getName().toCharArray(), salt, iterationCount);
		SecretKey key;
		if (ALGO_DES.equals(algo)) // to be backward compatible with older
									// implementations
			key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		else
			key = SecretKeyFactory.getInstance("PBEWITHSHA1ANDDESEDE").generateSecret(keySpec);
		PBEParameterSpec spec = new PBEParameterSpec(salt, iterationCount);
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(mode, key, spec);
		return cipher;
	}

	/*
	 * This method generates a AES key for encrypting the secret key used for
	 * the first round encryption
	 */
	protected static Cipher getAESCipher(int mode, byte[] accounts) throws Exception {
		byte[] key1 = { (byte) 0xa4, (byte) 0x10, (byte) 0x04, (byte) 0x8b, (byte) 0x90, (byte) 0x49, (byte) 0x24, (byte) 0xa9, (byte) 0x57, (byte) 0x5c, (byte) 0x2e, (byte) 0x89,
				(byte) 0xb7, (byte) 0x76, (byte) 0x1c, (byte) 0xb6 };

		// we use key1 xor accounts to prepare the key for encryption or
		// decryption
		byte[] key2 = xorOp(key1, accounts);

		SecretKey key = null;
		if (key2.length > 16) // if key2 is longer than 16 bytes, we take the
								// first 16
		{
			byte[] key16 = new byte[16];
			System.arraycopy(key2, 0, key16, 0, 16);
			key = new SecretKeySpec(key16, ALGO_AES);
		} else
			key = new SecretKeySpec(key2, ALGO_AES);

		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(mode, key);
		return cipher;
	}

	/*
   * 
   */
	private static byte[] xorOp(byte[] aBytes, byte[] bBytes) {
		int alen = aBytes.length;
		int blen = bBytes.length;

		if (alen <= blen) // array a is shorter than array b; we need to repeat
							// array a on array b a few times.
		{
			byte[] r = new byte[blen]; // return array has the same size as
										// array b
			int count = 0;
			for (int i = 0; i < bBytes.length; i++) {
				if (count >= alen)
					count = 0;
				r[i] = (byte) (bBytes[i] ^ aBytes[count]);
			}
			return r;
		} else // array a is longer than array b; we need to repeat array b on
				// array a a few times.
		{
			byte[] r = new byte[alen]; // return array has the same size as
										// array b
			int count = 0;
			for (int i = 0; i < aBytes.length; i++) {
				if (count >= blen)
					count = 0;
				r[i] = (byte) (aBytes[i] ^ bBytes[count]);
			}
			return r;
		}
	}
}
