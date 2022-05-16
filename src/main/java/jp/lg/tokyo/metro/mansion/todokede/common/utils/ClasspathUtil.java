package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.transaction.SystemException;

public class ClasspathUtil {

	public static File getFile(String classpath) throws SystemException {
		try {
			URL url = ClasspathUtil.class.getResource(classpath);
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			throw new SystemException("クラスパス不正");
		}
	}

	public static String getPath(String classpath) throws SystemException {
		return getFile(classpath).getAbsolutePath();
	}

}
