
package fileserver.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;


@ManagedBean
@Stateless
public class FileUtil {
    @EJB 
    private DataAccess dataAccess;

    private Part file;
    private String message1;
    private String md5hash;
    private Boolean check_exist;
    private File targetFile;
    public void setFile(Part file){
    this.file = file;
    }
    
    public Part getFile(){
    return file;
    }
    
     public String getMessage() {
        return message1;
    }
    public void setMessage(String message) {
        this.message1 = message;
    }
    
     public static String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1)
                        .trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }
    
       public void upload() throws IOException{

        FacesContext fcontext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) fcontext.getExternalContext().getContext();
        String realPath = scontext.getRealPath("/resources/files");
  

    InputStream initialStream = file.getInputStream();
    byte[] buffer = new byte[initialStream.available()];
    md5hash = md5(buffer);  // вычисляем MD5 hash
    initialStream.read(buffer);
    String fileName = getFileNameFromPart(file);
    targetFile = new File(realPath+"/"+fileName);
    if(!targetFile.exists()){
    OutputStream outStream = new FileOutputStream(targetFile);
    
    outStream.write(buffer);
    outStream.close();
    insertFileToDB(fileName, md5hash);  // записываем в БД
    if(check_exist) {
        message1 = "Загружен файл: "+fileName;
    }    
    else { message1 = "Файл с таким хэшем уже существует";  }
    
    }
    
    else {
    message1 = "Уже существует файл с таким именем: "+fileName;
    }
       }
    
       
       public void insertFileToDB(String name, String md5hash){
       
           FilesEntity file_entity = new FilesEntity(name, md5hash);
           check_exist = dataAccess.addNewFile(file_entity);
           
           // удаление загруж-го файла так как есть совпадение хеша
           if(!check_exist){
               targetFile.delete(); 
           }
           
          
       }
       
       
       
       public String md5(byte[] byteInput){
	try{
		MessageDigest md = MessageDigest.getInstance("MD5");
		
                md.update(byteInput);
		String hash = new BigInteger(1, md.digest()).toString(16);
		while(hash.length() < 32) hash = "0" + hash;
		return hash;
            }
        catch(NoSuchAlgorithmException e){}
        
	return "";
           
       } 
    }
            
       
       
