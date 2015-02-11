import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;

public class UnzipTest {
	public static void unzip(String zipPath, String filename, String passwd)
			throws ZipException, IOException {
		ZipFile zipFile = new ZipFile(zipPath);
		if (zipFile.isEncrypted()) {
			zipFile.setPassword(passwd);
		}
		List fileHeaderList = zipFile.getFileHeaders();
		FileHeader fileHeader = null;
		for (int i = 0; i < fileHeaderList.size(); i++) {
			fileHeader = (FileHeader) fileHeaderList.get(i);
			if (fileHeader != null && fileHeader.getFileName().equals(filename)) {
				ZipInputStream is = zipFile.getInputStream(fileHeader);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String fieldline = br.readLine();
				while (fieldline != null) {
					System.out.println(fieldline + "\n");
					fieldline = br.readLine();
				}
				is.close();
			}
		}
	}

	public static void main(String[] args) {
		try {
			/*
			 * UnzipTest.unzip(
			 * "C:/Users/zangyunfei/Desktop/paychannelconfig_sqlmap.zip",
			 * "C:/Users/zangyunfei/Desktop/test", "zyf");
			 */
			UnzipTest.unzip(
					"C:/Users/zangyunfei/Desktop/paychannelconfig_sqlmap.zip",
					"paychannelconfig_sqlmap.xml", "zyf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
