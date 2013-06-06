package cinema.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.TypedQuery;

public class Utils {
	public static String transform(String original) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(original.getBytes());

		byte[] byteData = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}
	 public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
		    query.setMaxResults(1);
		    List<T> list = query.getResultList();
		    if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
		}
}