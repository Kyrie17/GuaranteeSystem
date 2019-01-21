package tools;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import com.sun.org.apache.xml.internal.security.algorithms.Algorithm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CommonUtils {
	/*
	 * 密文，用于加密解密Signature
	 */
	private static final String JWT_SECRET="sdf5154fsd_156f_1s56fdsf_fds64f_15648fwrfewfg65465ewf15";
	/*
	 * 创建JWT
	 * @param ttlMillis 过期的时间长度
	 */
	public static String createJWT(String subject,long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		long nowMillis=System.currentTimeMillis();//签发jwt的时间
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
		.setHeader(map)//header
		.setIssuedAt(new Date())//payload
		.setSubject(subject)
		.setExpiration(new Date(nowMillis + ttlMillis))
		.signWith(signatureAlgorithm, key);//加密
		return builder.compact();
		
	}
	private static SecretKey generalKey() {
		String tokenKey=CommonUtils.JWT_SECRET;
		byte[] encodedKey=Base64.decodeBase64(tokenKey);
		// 根据给定的字节数组构造一个密钥，使用 key 中的始于且包含 offset 的前 len 个字节,aes为加密方法
		SecretKey key=new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
	/*
	 * 验证token
	 */
	public static Claims parseJWT(String jwt)throws Exception{
		SecretKey key=generalKey();
		Claims claims=Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(jwt).getBody();
		return claims;
	}
	public static void main(String[] args) {
		String token=CommonUtils.createJWT("jnxj", 30*1000L);
		try {
			Thread.sleep(3000);
			System.out.println(CommonUtils.parseJWT(token).getSubject());
		} catch (InterruptedException e) {
			System.out.println("token过期");
		}
		catch(Exception e) {
			
		}
		
	}
}
