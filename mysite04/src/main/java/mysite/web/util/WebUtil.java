package mysite.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtil {
	public static String encodeURL(String url, String encode) throws RuntimeException {
		try {
			return URLEncoder.encode(url, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
