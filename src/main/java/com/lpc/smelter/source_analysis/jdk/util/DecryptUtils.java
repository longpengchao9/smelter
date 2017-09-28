package com.lpc.smelter.source_analysis.jdk.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by 58 on 2017/4/27.
 */
public class DecryptUtils {

	private static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCnOMrGXt0nkjYD"
			+ "hQ6TkB4NbaooR46FeEqZHNyc5ifRvz70SpeuHKmjQOm3W7BOmJTytwdotHopFBCY"
			+ "6AW+cpKPSPZL3UChdk3KZN6NTJK0ZcZqBEGZNR1Jr/2TScr0M3a+aU337PYW2MiZ"
			+ "w127UhM6B6CMc9ku2F75TkZ4XE5quVcgBk7FaDpSBd8+T3allARRgymEIXEZpC9y"
			+ "oiQEUG/5NzcZBFB2diiY/f1NIu9dSSIdkWDQb43gvozTB+8ie9A82XTAW0HSPVhv"
			+ "MLF+DWggTjlaIUnzSeClMx9bQGG8GI1oKCr5IZ8s8bWcC9KMR4fdRnDv1zd0lP3I"
			+ "4Q7xbJEzAgMBAAECggEBAIF4Ch9eUVIS9Pjyqt97JJl2/9hh1qnmAbPcUb60v724"
			+ "mZBgtGFc1caNOqb5OV8Q6bmmFfluSmrFmzgX5GKYqHqBnhgMvL6GLyJ1yI2T7L35"
			+ "Uo7Art1k70EfHnBMIPPDaLtyIqjC8aGsxFuwlEC1AifaweYcx9lqMtZJUWxc42Xi"
			+ "yyYoxQBVlfYUPZ85z/sww9UR6SVIlk0yYMySnW9ug+oOim0clpvhsTkjtM5bz9na"
			+ "H/fTbsEqbMqYUQ9rKGWx74G3amN4yZwV9YW5lHUTpURZl5A5oujZ3zow/tHxzKr8"
			+ "SmkACxQJgzLeLU684dI1JU/dO6QyL5/T0hrbhmR/A2kCgYEA2JO6Q256GfCbfTia"
			+ "2c68CiaZpC4C88qHoOM6SEvzTrAokYjtYjQ4j28PfP1apWKsyk9Ho0b+yquGCxpp"
			+ "AeFWql93E0ELSFVdZtn9HoRJvNax4Q2iLEfMGHFEIQCZF1B+5HjLDI39XLM2lj6I"
			+ "zh9goLOmg2Owl5gOb0Qsp84sVi8CgYEAxakpRZIy1gKvcD9a7Czqo7AsUtpUqDwk"
			+ "omIVEjF61aeNVHuTWj6WGzICaPOwTYrmPIBTv0eNzZdDnjkXaGTmIxOXNDEUhrWe"
			+ "4y4m8esjP83SWXhhAqUqbKL88ENZ+sRuipdu5ddF57W8rPHn+sdJlHs37ZSjSGwn"
			+ "8FZABfdqeD0CgYA9oAtoFI95whT8VJb7Uu/+k200YkNEy2Q8S2vSFops4YMppiSl"
			+ "xOYzKg7mKdEZLjKsflKMCy+Ey7mgfytR9aSLZg6j0+Y59tBU68IyZMH2kcCboR18"
			+ "LBHPAqu4GEt3btgsepKMR/rH3dayeajDvfnowDWpPTyKZiH1Vxi9xtSm0QKBgQCs"
			+ "GNYDagBzD1F69N6QRadiL9DXrpsJA5yN+cII3iXqAoFuLk4kw9tkAE3S3WICkc5W"
			+ "nRDNuVpAx9Qq3eCAl2E85yC+Y4FQrK+Tc6qa9bTyJ51fIw2sBOyEPADonGuqh/8L"
			+ "EkCPQ7jsvvJwzgrB8WQiMIqIg+MK+ohnnGEQENSVrQKBgQC0WyJpk28WgM8hMpVn"
			+ "ghVt7SvB/Krmc1Xwx+w44F7RRKwcjUolTxYsT90XZIfZi3yuTW6GwuVe7ZlmdUqz"
			+ "OLUeyEhyBImL8Gr7YYF8Z8MGrUbuZROonkfUAYuEeFySd80QRrhANz4pukmgIRKT" + "Ycs0NMhTTX0R5JoBRWk4JzJl8w==";

	public static String decrypt(String key, String content) throws Exception {
		byte[] encryptKey = hexStringToBytes(key);
		byte[] decryptKey = decryptRSA(encryptKey);
		byte[] encryptContent = hexStringToBytes(content);
		byte[] decryptContent = decryptAES(encryptContent, decryptKey);
		return new String(decryptContent, "UTF-8");
	}

	private static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	private static byte[] decryptRSA(byte[] bytes) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// PKCS8EncodedKeySpec keySpec = new
		// PKCS8EncodedKeySpec(Base64.decode(prk, Base64.DEFAULT));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(PRIVATE_KEY));
		Key key = keyFactory.generatePrivate(keySpec);
		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		c.init(Cipher.DECRYPT_MODE, key);
		return c.doFinal(bytes);
	}

	private static byte[] decryptAES(byte[] bytes, byte[] seed) throws Exception {
		SecretKey ks = new SecretKeySpec(seed, "AES");
		// Security.addProvider(new BouncyCastleProvider());
		// IOS 使用AES/ECB/PKCS7Padding,默认配置下与AES/ECB/PKCS5Padding算法通用
		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, ks);
		return c.doFinal(bytes);
	}

	public static JSONObject decryptJson(String key, String data) {

		JSONObject resultJson = new JSONObject();

		if (StringUtils.isBlank(key) || StringUtils.isBlank(data)) {
			System.out.println("数据解密 失败 key=" + key + " data=" + data);
			return resultJson;
		}

		try {
			resultJson = JSONObject.parseObject(decrypt(key, data));
		} catch (Exception e) {
			System.out.println("数据解密 失败 key=" + key + " data=" + data);
		}

		return resultJson;

	}

	public static void main(String[] args) {

		String key = "804348a682de904b3f56ddba8423a88cd00c8bb6f99da34d4a0ff69b56d5d4218fe418130046e4b6d3bde10968a770706169554e414aae3511908c0ad112e01502bb8eb250d5b0c464d687c2f7d64904296a5a57be7d07c07a3ef0dfc25b39da5923e0fb17cf3672a2394ea64a8c33b72f2f7a7ff24486a174df50eee580bf81a51cece23703b80e99aba0b82ceed444b001b338ee238fac373f7ec58a1eb84d7620a7351410d71dc756d63d2cc28ce707433d666b893cc13eaa14c1e11acbc1a59a55127d298d4f0b098a1e8a9c9fda53a0a86ce9c10a6542ded5c441415fd6d357ed3216c11a35b0d2726d9d44fdd36e241d636773bb9dc12a1111aec57c99";
		String data = "6b5d7dd6012cc409d7995ce812bbe9dbf26dc94b4efd1d6bc1d67c0c2fa89e81175b2dd36ddeae53561958da3bb9aa367c81101f9412c54523c4c14c2b4ef12af6cb23b2ebd55701b25d8bca597958109e461a92d9513268a09857c33701bbea2eb1e07471853628d7792a967358f85d35743da87dd43e5f16aa0ff6a9842428";

		try {
			System.out.println(decrypt(key, data));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
