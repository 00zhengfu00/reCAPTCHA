package test;

import java.net.Socket;

public class DoRequest {
	public 
    public static String sendSocketGet(String urlParam, Map<String, Object> params, String charset) {  
        String result = "";  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> entry : params.entrySet()) {  
                sbParams.append(entry.getKey());  
                sbParams.append("=");  
                sbParams.append(entry.getValue());  
                sbParams.append("&");  
            }  
        }  
        Socket socket = null;  
        OutputStreamWriter osw = null;  
        InputStream is = null;  
        try {  
            URL url = new URL(urlParam);  
            String host = url.getHost();  
            int port = url.getPort();  
            if (-1 == port) {  
                port = 80;  
            }  
            String path = url.getPath();  
            socket = new Socket(host, port);  
            StringBuffer sb = new StringBuffer();  
            sb.append("GET " + path + " HTTP/1.1\r\n");  
            sb.append("Host: " + host + "\r\n");  
            sb.append("Connection: Keep-Alive\r\n");  
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");  
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");  
            // ����һ���س����У���ʾ��Ϣͷд�꣬��Ȼ������������ȴ�  
            sb.append("\r\n");  
            if (sbParams != null && sbParams.length() > 0) {  
                sb.append(sbParams.substring(0, sbParams.length() - 1));  
            }  
            osw = new OutputStreamWriter(socket.getOutputStream());  
            osw.write(sb.toString());  
            osw.flush();  
            is = socket.getInputStream();  
            String line = null;  
            // ��������Ӧ�����ݳ���  
            int contentLength = 0;  
            // ��ȡhttp��Ӧͷ����Ϣ  
            do {  
                line = readLine(is, 0, charset);  
                if (line.startsWith("Content-Length")) {  
                    // �õ���Ӧ�����ݳ���  
                    contentLength = Integer.parseInt(line.split(":")[1].trim());  
                }  
                // ���������һ�������Ļس����У����ʾ����ͷ����  
            } while (!line.equals("\r\n"));  
            // ��ȡ����Ӧ�����ݣ�������Ҫ�����ݣ�  
            result = readLine(is, contentLength, charset);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (osw != null) {  
                try {  
                    osw.close();  
                } catch (IOException e) {  
                    osw = null;  
                    throw new RuntimeException(e);  
                } finally {  
                    if (socket != null) {  
                        try {  
                            socket.close();  
                        } catch (IOException e) {  
                            socket = null;  
                            throw new RuntimeException(e);  
                        }  
                    }  
                }  
            }  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    is = null;  
                    throw new RuntimeException(e);  
                } finally {  
                    if (socket != null) {  
                        try {  
                            socket.close();  
                        } catch (IOException e) {  
                            socket = null;  
                            throw new RuntimeException(e);  
                        }  
                    }  
                }  
            }  
        }  
        return result;  
    }  
  
    /** 
     * @Description:��ȡһ�����ݣ�contentLe���ݳ���Ϊ0ʱ����ȡ��Ӧͷ��Ϣ����Ϊ0ʱ������ 
     * @time:2016��5��17�� ����6:11:07 
     */  
    private static String readLine(InputStream is, int contentLength, String charset) throws IOException {  
        List<Byte> lineByte = new ArrayList<Byte>();  
        byte tempByte;  
        int cumsum = 0;  
        if (contentLength != 0) {  
            do {  
                tempByte = (byte) is.read();  
                lineByte.add(Byte.valueOf(tempByte));  
                cumsum++;  
            } while (cumsum < contentLength);// cumsum����contentLength��ʾ�Ѷ���  
        } else {  
            do {  
                tempByte = (byte) is.read();  
                lineByte.add(Byte.valueOf(tempByte));  
            } while (tempByte != 10);// ���з���ascii��ֵΪ10  
        }  
  
        byte[] resutlBytes = new byte[lineByte.size()];  
        for (int i = 0; i < lineByte.size(); i++) {  
            resutlBytes[i] = (lineByte.get(i)).byteValue();  
        }  
        return new String(resutlBytes, charset);  
    }  
      
}  

}
