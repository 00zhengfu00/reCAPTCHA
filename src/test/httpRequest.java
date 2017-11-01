package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
/**
 * @˵�� �������ȡͼƬ������
 * @version 1.0
 * @since
 */  
public class httpRequest {  
    /**
     * ����
     * @param args
     */  
    public static void main(String[] args) {
    	writeImageToDisk(getImageFromNetByUrl("https://ecss.pingan.com/createImageCode?t=1509343555818"),"test.jpg");
    }

    /**
     * ��ͼƬд�뵽����
     * @param img ͼƬ������
     * @param img_save_path �ļ�����ʱ������
     */  
    public static void writeImageToDisk(byte[] img, String img_save_path){
        try {  
            File file = new File(img_save_path);
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /**
     * ���ݵ�ַ������ݵ��ֽ���
     * @param strUrl �������ӵ�ַ
     * @return
     */  
    public static byte[] getImageFromNetByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.addRequestProperty("Referer",strUrl);
            conn.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0");
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(8*1000);
            InputStream inStream = conn.getInputStream();//ͨ����������ȡͼƬ����  
            byte[] btImg = readInputStream(inStream);//�õ�ͼƬ�Ķ���������  
            return btImg;  
        } catch (Exception e) {
            System.out.println("����ͼƬ�쳣��"+e.getLocalizedMessage());
        }  
        return null;  
    }  
    /**
     * ���������л�ȡ����
     * @param inStream ������
     * @return
     * @throws Exception
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  
}