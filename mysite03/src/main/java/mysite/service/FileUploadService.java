package mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private static final String SAVE_PATH = "/mysite-uploads";
	private static final String URL = "/assets/upload-images";
	
	public String restore(MultipartFile file) throws RuntimeException {
		try {
			File uploadDirectory = new File(SAVE_PATH);
			if(!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
				return null; 
			}
			
			if(file.isEmpty()) {
				return null;
			}
			
			String originFilename = Optional.ofNullable(file.getOriginalFilename()).orElse("");
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);	// 파일 확장자(.png, .pdf 등)
			String saveFilename = generateSaveFilename(extName);
			long fileSize = file.getSize();
			
			System.out.println("######" + originFilename);
			System.out.println("######" + saveFilename);
			System.out.println("######" + fileSize);
			
			byte[] data = file.getBytes();
			
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
			
			return URL + "/" + saveFilename;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String generateSaveFilename(String extName) {
		Calendar calendar = Calendar.getInstance();
		
		return ""
			+ calendar.get(Calendar.YEAR)
			+ calendar.get(Calendar.MONTH)
			+ calendar.get(Calendar.DATE)
			+ calendar.get(Calendar.HOUR)
			+ calendar.get(Calendar.YEAR)
			+ calendar.get(Calendar.MINUTE)
			+ calendar.get(Calendar.SECOND)
			+ calendar.get(Calendar.MILLISECOND)
			+ ("." + extName);
	}

}

