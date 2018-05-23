package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	private String innerUploadPath = "resources/upload";
	
	//이름id를 통해서 주입받음(servlet-context.xml) 외부 파일 저장
	@Resource(name="uploadPath")
	private String outerUploadPath;
	
	
	@RequestMapping(value="innerUpload", method=RequestMethod.GET)
	public String innerUploadForm(){
		return "innerUploadForm";
	}
	
	@RequestMapping(value="innerUpload", method=RequestMethod.POST)
	public String innerUploadResult(String writer, MultipartFile file, 
			HttpServletRequest request,
			Model model) throws IOException{//innerUploadForm에서 writer, file이 넘어옴
		logger.info("writer : "+ writer);
		logger.info("file : "+file.getOriginalFilename());//고유의 파일이름
		
		String root_path = request.getRealPath("/");//서버 root경로 받아오기
		logger.info("root_path : "+root_path);
		
		//자동으로 resources/upload파일 만들기 위해
		File dirPath = new File(root_path+"/"+innerUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdir();
		}
		
		//중복처리
		UUID uid = UUID.randomUUID();//중복되지 않는 고유한 키 값을 만들어 줌
		//파일 이름이 중복되지 않게 랜덤수+고유이름으로 만들어 줌
		String savedName = uid.toString()+"_"+file.getOriginalFilename();
		File target = new File(root_path+"/"+innerUploadPath+"/"+savedName);

		FileCopyUtils.copy(file.getBytes(), target);//(a,b)a꺼를 b에 복사해줌
		
		
		//작성자와 복사된 파일을 모델에 실어서 넘겨줄꺼임
		model.addAttribute("writer", writer);
		model.addAttribute("filePath", innerUploadPath+"/"+savedName);
		
		return "innerUploadResult";
	
	}
	
	
	@RequestMapping(value="innerMultiUpload", method=RequestMethod.GET)
	public String innerMultiUpload(){
		return "innerMultiUploadForm";
	}
	
	@RequestMapping(value="innerMultiUpload", method=RequestMethod.POST)
	public String innerMultiUploadResult(String writer, 
			List<MultipartFile> files,
			HttpServletRequest request,
			Model model) throws IOException{//파일이 여러개일 때
		logger.info("writer : "+ writer);

		for(MultipartFile file: files){
			logger.info("fileName : "+file.getOriginalFilename());
			logger.info("fileSize : "+file.getSize());
		}
		
		String root_path = request.getRealPath("/");//서버 root경로 받아오기
		logger.info("root_path : "+root_path);
		
		//자동으로 resources/upload파일 만들기 위해
		File dirPath = new File(root_path+"/"+innerUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdir();
		}
		////////////////////////여기까지 폴더 만들어 줌
		
		
		//files가 들고있는 개수 만큼 fileupload가 됨
		ArrayList<String> pathList = new ArrayList<>();//파일들을 담기위해
		for(MultipartFile file: files){
			//중복처리
			UUID uid = UUID.randomUUID();//중복되지 않는 고유한 키 값을 만들어 줌
			//파일 이름이 중복되지 않게 랜덤수+고유이름으로 만들어 줌
			String savedName = uid.toString()+"_"+file.getOriginalFilename();
			File target = new File(root_path+"/"+innerUploadPath+"/"+savedName);

			FileCopyUtils.copy(file.getBytes(), target);//(a,b)a꺼를 b에 복사해줌
			
			pathList.add(savedName);
		}
		
		//model에 실어 보냄
		model.addAttribute("writer", writer);
		model.addAttribute("listPath", pathList);
		return "innerMultiUploadResult";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//서버 외부 업로드
	//외부 저장소는 서블릿에 저쟁했기 때문에 주입해서 쓰면 됨(servlet-context.xml)
	//주소를 받아서 바이트 배열로 넘겨줌
	
	@RequestMapping(value="outerUpload", method=RequestMethod.GET)
	public String outerUploadForm(){
		return "uploadForm";
	}
	
	@RequestMapping(value="outerUpload", method=RequestMethod.POST)
	public String outerUploadResult(String writer, MultipartFile file, Model model) throws IOException{
		logger.info("writer", writer);
		logger.info("file : "+file.getOriginalFilename());
		logger.info("outerUploadPath : "+outerUploadPath);
		
		//File dirPath = new File("c:/zzz/upload");
		UUID uid = UUID.randomUUID();//중복되지 않는 고유한 키 값을 만들어 줌
		//파일 이름이 중복되지 않게 랜덤수+고유이름으로 만들어 줌
		String savedName = uid.toString()+"_"+file.getOriginalFilename();
		File target = new File(outerUploadPath+"/"+savedName);

		FileCopyUtils.copy(file.getBytes(), target);//(a,b)a꺼를 b에 복사해줌
		
		model.addAttribute("writer", writer);
		model.addAttribute("fileName", savedName);
		
		return "uploadResult";
	}
	
	
	//이미지 파일이 외부에 저장되어 있으면 고객 컴퓨터 서버로 못 땡겨서 옴
	//이때 이미지의 byte[] 바이트 배열을 넘겨서 보도록 함
	//restController가 아닌 경우 데이터만 넘겨줄 경우 ResponseBody(화면전환 아님)
	
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String filename) throws Exception{
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;//파일을 읽기위해
		
		logger.info("[displayFile] filename : "+ filename);
		
		try {
			
			//MediaType.IMAGE_JPEG, MediaType.png...... 확장자가 다름 util pagkage 만들어서 넣음
			//확장자 뽑아내기
			String format = filename.substring(filename.lastIndexOf(".")+1);
			//.jpg등등 뽑아냄
			
			MediaType mType = MediaUtils.getMediaType(format);
			
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mType);
			
			in = new FileInputStream(outerUploadPath+"/"+filename);
			
			//IOUtils통해서 in객체들을 뽑아줌 headers를 통해서 새로 생성하라고 함
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
														headers,
														HttpStatus.CREATED);
			//외부 저장소에 저장된 이미지 파일의 정보를 받아서 바이트 배열로 뽑아내서 바이트 배열을 실어서 보냄
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
		return entity;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="dragUpload", method=RequestMethod.GET)
	public String dragUploadForm(){
		return "uploadDragForm";
	}
	
	@ResponseBody//ajax에 데이터만 갈꺼임
	@RequestMapping(value="dragUpload", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dragUploadResult(String writer, List<MultipartFile> files) throws IOException{
		logger.info("writer : "+writer);
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			
			List<String> list = new ArrayList<>();
			
			for(MultipartFile file: files){
				logger.info("file : "+ file.getOriginalFilename());
				
				//외부경로에 upload 복사되도록
				UUID uid = UUID.randomUUID();//중복되지 않는 고유한 키 값을 만들어 줌
				//파일 이름이 중복되지 않게 랜덤수+고유이름으로 만들어 줌
				String savedName = uid.toString()+"_"+file.getOriginalFilename();
				File target = new File(outerUploadPath+"/"+savedName);

				FileCopyUtils.copy(file.getBytes(), target);//(a,b)a꺼를 b에 복사해줌
				list.add(savedName);
				
			}
			//파일이름을 실어보내고, 성공했는지 여부 두 개 실어 보낼꺼임(String, List<String>)
			//타입이 다를 때 Map
			Map<String, Object> map = new HashMap<>();
			map.put("result", "success");
			map.put("listFile", list);
			
			entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<>();
			map.put("result", "fail");
		
			entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//폼에서 사진 선택하면 이미지가 미리 보이도록 
	@RequestMapping(value="previewUpload", method=RequestMethod.GET)
	public String previewUploadForm(){
		return "previewForm";
	}
	
	@RequestMapping(value="previewUpload", method=RequestMethod.POST)
	public String previewUploadResult(String writer, 
			MultipartFile file, Model model) throws IOException{
		logger.info("writer : "+ writer);
		logger.info("file : "+file.getOriginalFilename());
		
		/*//외무 경로에 파일이 저장되도록 함
		UUID uid = UUID.randomUUID();//중복되지 않는 고유한 키 값을 만들어 줌
		//파일 이름이 중복되지 않게 랜덤수+고유이름으로 만들어 줌
		String savedName = uid.toString()+"_"+file.getOriginalFilename();
		File target = new File(outerUploadPath+"/"+savedName);

		FileCopyUtils.copy(file.getBytes(), target);//(a,b)a꺼를 b에 복사해줌
*/		
		
		//upload처리 해줌
		String filePath = UploadFileUtils.uploadFile(outerUploadPath, file.getOriginalFilename(), file.getBytes());
		
		model.addAttribute("writer", writer);
		model.addAttribute("file", filePath);
		
		return "previewResult";
	}
	
	//파일 삭제
	//2개의 파일 -원본, 썸네일 둘 다 지워야 함
	@RequestMapping(value="deleteFile", method=RequestMethod.GET)
	public String deleteFile(String fileName){
		logger.info("deleteFile -" +fileName);
		
		UploadFileUtils.deleteFile(outerUploadPath, fileName);
		
		return "deleteResult";
		
	}
}
