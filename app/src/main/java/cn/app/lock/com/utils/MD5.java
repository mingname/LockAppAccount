package cn.app.lock.com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密类
 * @author mapler
 *
 */
public class MD5 {
		public static String getMD5(String val) throws NoSuchAlgorithmException {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(val.getBytes());
			byte[] m = md5.digest();
			return getString(m);
		}
		private static String getString(byte[] b){
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < b.length; i ++){
				sb.append(b[i]);
			}
			return sb.toString();
		}
		
		/**
		 * MD5加密    
		 * @param password : 要加密的密码
		 * @return
		 */
	    public static String MD5Encryption(String password) {
	        StringBuffer hexString = new StringBuffer();
	        if (password != null && password.trim().length() != 0) {   
	            try {   
	                MessageDigest md = MessageDigest.getInstance("MD5");
	                md.update(password.getBytes());   
	                byte[] hash = md.digest();   
	                for (int i = 0; i < hash.length; i++) {   
	                    if ((0xff & hash[i]) < 0x10) {   
	                        hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
	                    } else {   
	                        hexString.append(Integer.toHexString(0xFF & hash[i]));
	                    }   
	                }   
	            } catch (NoSuchAlgorithmException e) {
	                e.printStackTrace();   
	            }   
	  
	        }   
	        return hexString.toString();   
	    } 
  
}
