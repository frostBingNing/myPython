package mysql;
import java.util.Enumeration; // 枚举类型包
import java.util.Vector;      // 自动添加数组包
import java.math.BigInteger;  
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;  
//import org.apache.commons.codec.digest.DigestUtils; 
public class vector {
	 public static String md5Password(String password) {

	        try {
	            // 得到一个信息摘要器
	            MessageDigest digest = MessageDigest.getInstance("md5");
	            byte[] result = digest.digest(password.getBytes());
	            StringBuffer buffer = new StringBuffer();
	            // 把每一个byte 做一个与运算 0xff;
	            for (byte b : result) {
	                // 与运算
	                int number = b & 0xff;// 加盐
	                String str = Integer.toHexString(number);
	                if (str.length() == 1) {
	                    buffer.append("0");
	                }
	                buffer.append(str);
	            }

	            // 标准的md5加密后的结果
	            return buffer.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return "";
	        }
	 }
	public static void main(String [] args)
	{
	   // 演示hash 算法加密 的功能  
		// 采用中转的方法进行汉字的输入查询    否则汉字查询会产生空值
		System.out.println(md5Password("李欣东"));
		

	}
}
