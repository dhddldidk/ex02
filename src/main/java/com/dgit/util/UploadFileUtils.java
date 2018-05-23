package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

	//파일 업로드 하는 함수
	//return : /년/월/일/파일이름    + 썸네일 이미지(작은사이즈 이미지)
	public static String uploadFile(String uploadPath,
									String originalName,
									byte[] fileData) throws IOException{
		//1. c:경로를 매개변수로 오게 할꺼임
		//2. 원본 파일 이름
		
		
		//중복처리
		UUID uid = UUID.randomUUID();//중복되지 않는 고유한 키 값을 만들어 줌
		//파일 이름이 중복되지 않게 랜덤수+고유이름으로 만들어 줌
		String savedName = uid.toString()+"_"+originalName;
		String savedPath = calcPath(uploadPath);
		
		
		//uploadPath - c://zzz/upload
		//savePath - /2018/05/23
		//saveName - uuid_aaa.jpg
		
		
		//파일을 만들어 줌
		File target = new File(uploadPath+savedPath+"/"+savedName);

		FileCopyUtils.copy(fileData, target);//(a,b)a꺼를 b에 복사해줌
		
		//여기 까지는 원본파일 업로드
		///////////////////////////////////////////////////////////////////////
		//여기부터는 썸네일 업로드
		
		//thumbnail 파일을 만들어서 경로 리턴
		String thumbPath = makeThumbnail(uploadPath, savedPath, savedName);
		
		
		
		//년,월,일과 함께 저장된 이름을 return 해줘야 함
		return thumbPath;//썸네일       //savedPath+"/"+savedName; 원본
	}
	
	//년/월/일 만들어 주는 함수
	//uploadPath : 서버 업로드 주소
	public static String calcPath(String uploadPath){
		//서버의 년/월/일 폴더 만들기-calendar of date 객체 사용
		
		Calendar cal = Calendar.getInstance();
		String yearPath = "/"+cal.get(Calendar.YEAR);
		
		//달에 05처럼 0 붙이기 위해 ex)2018/05
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1); 
		// /2018/05       %02d:2자리 정수가 오는데 앞에 없으면 0을 붙여 달라
		
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;//datePath가 년,월,일 다 갖고 있어서 datePath만 return해주면 됨
		//   /datePath 이렇게 return 됨
		
	}
	
	public static void makeDir(String uploadPath, String...paths){//매개변수가 몇개 오는지 모를때 ...
		
		for(String path: paths){
			File dirPath = new File(uploadPath+path);
			if(dirPath.exists()==false){
				dirPath.mkdir();
			}
		}
	}
	
	//썸네일 이미지 만드는 함수                 
	//uploadPath : c://zzz/upload
	//path : /년/월/일
	//filename : uuid_aaa.jpg
	private static String makeThumbnail(String uploadPath, String path, String filename) throws IOException{
		//작은 이미지가 들어가도록 가상공간 만들기
		//원본 이미지의 데이터를 뽑아옴
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path, filename));
		
		//FIT_TO_HEIGHT : 썸네일의 이미지의 높이를 뒤의 100px로 동일하게 만들음.
		
		//뽑은 이미지로 작은 이미지를 만듬 - 파일이 아니라 데이터로만 존재가 됨
		BufferedImage destImg = Scalr.resize(sourceImg, 
											Scalr.Method.AUTOMATIC,
											Scalr.Mode.FIT_TO_HEIGHT,100);//Thumbnail의 높이 100px고정
	
		
		//작은 이미지로 파일을 만듬
		//Thumbnail 파일이름 : "s_"를 붙임
		String thumbnailName = 
					uploadPath+path+"/"+"s_"+filename;
		
		File newFile = new File(thumbnailName);
		
		//확장자 찾기
		String formatName = filename.substring(filename.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		String savedName = thumbnailName.substring(uploadPath.length());
		
		//실제 썸네일 주소를 만들어서 리턴함
		return savedName;//    /년/월/일/s_uuid_aaa.jpg
	}
	
	//파일 삭제-원본과 썸네일 둘 다 지워짐
	public static void deleteFile(String uploadPath, String fileName){
		//원본, 썸네일 2가지 파일 삭제
		
				//1.썸네일 지우기
				File file = new File(uploadPath+fileName);
				if(file.exists()){
					file.delete();
				}
				
				//원본파일 경로 뽑아내기
				String front = fileName.substring(0, 12);//0에서부터 11까지 가져옴 /2018/05/23/까지
				String end = fileName.substring(14);//12,13인 s_빼고 14부터 다 가져옴
				String originalName = front+end;
				
				File file2 = new File(uploadPath+originalName);
				if(file2.exists()){
					file2.delete();
				}	
	}
}
