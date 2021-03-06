package com.dsep.util;

//import org.apache.commons.net.ftp.FTPClient; 
//import java.io.File; 
//import java.io.IOException; 
//import java.io.FileOutputStream; 
//import java.io.OutputStream;

//public class FTPHelper {
//	
//	private static final String ENCODING = "UTF-8";
//	private static final String FTP_SERVER = "192.168.3.74";
//	private static final int FTP_PORT = 21;
//	
//	FTPClient ftpClient;
//	
//	/** 
//     * connectServer
//     * 连接ftp服务器 
//     * @param path 文件夹，空代表根目录，相对路径
//     * @param password 密码 
//     * @param user    登陆用户 
//     * @param server 服务器地址 
//     */  
//	public void connectServer(String server,int port, String user, String password,  String path)
//    throws IOException
//	{
//    	// server：FTP服务器的IP地址；user:登录FTP服务器的用户名  
//    	// password：登录FTP服务器的用户名的口令；path：FTP服务器上的路径  
//    	ftpClient = new FTPClient();  
//    	ftpClient.connect(server, port);
//		ftpClient.login(user, password);
//		//path是ftp服务下主目录的子目录  
//		if (path.length() != 0) {  
//            ftpClient.changeWorkingDirectory(path);
//		}
//		
//		//一些参数
//		ftpClient.setBufferSize(1024); 
//        ftpClient.setControlEncoding(ENCODING);
//        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
//    }  
//	
//	/** 
//     * closeServer 
//     * 断开与ftp服务器的链接 
//     * @throws java.io.IOException 
//    */  
//	public void closeServer()  
//	throws IOException  
//	{     
//		if (ftpClient.isConnected()) {  
//            ftpClient.disconnect();  
//        }
//	}  
//
//	/**
//	 * 下载
//	 * @param remoteFileName
//	 * @param localFileName
//	 * @return
//	 * @throws IOException
//	 */
//	public boolean download(String remoteFileName, String localFileName)  
//    throws IOException { 
//        boolean flag = false;  
//        File outfile = new File(localFileName);  
//        OutputStream oStream = null;  
//        try {  
//            oStream = new FileOutputStream(outfile);  
//            flag = ftpClient.retrieveFile(remoteFileName, oStream);
//        } catch (IOException e) {  
//            flag = false;  
//            return flag;  
//        } finally {  
//            oStream.close();  
//        }  
//        return flag;  
//    }  
//	
//    
//    public static void main(String[] args) { 
//        FTPHelper ftp  = new FTPHelper();
//        
//        try {
//			ftp.connectServer(FTP_SERVER, FTP_PORT, "dsep", "dsep", "\\10006\\");
//			ftp.download("2007121315930907_8D76A44E82F342078A3C45945EF8F575.jpg", "C:\\Users\\Monar\\Desktop\\1.jpg");
//			ftp.closeServer();
//        } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//    } 
//}

import java.io.File;   
import java.io.FileInputStream;   
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.net.SocketException;   
  
import org.apache.commons.net.ftp.FTPClient;   
import org.apache.commons.net.ftp.FTPReply;   

public class FTPHelper {  
    private String         host;   
    private int            port;   
    private String         username;   
    private String         password;   
  
    private boolean        binaryTransfer = true;   
    private boolean        passiveMode    = true;   
    private String         encoding       = "UTF-8";   
    private int            clientTimeout  = 3000;   
    private boolean flag=true;
    
    /**
     * 解析ftp服务器的地址信息
     * @param fileAddress
     * @return
     */
    public String parseFTPAddress(String fileAddress)
    {
    	String result = "";
    	if(fileAddress.startsWith("ftp://"))
    	{
        	int iPos = fileAddress.indexOf("//");        	
    		String value = fileAddress.substring(iPos+2);
    		int ePos = value.indexOf("/");
    		result = value.substring(ePos);
    		if (ePos >= 0)
    		{
    			value = value.substring(0,ePos);
    		}    		
    		int pos1 = value.indexOf('@');
    		//解析用户名和密码
    		if(pos1 >= 0)
    		{    			
    			int pos2 = value.indexOf(':');
    			if (pos2>=0) {
    				setUsername(value.substring(0, pos2));
    				setPassword(value.substring(pos2+1, pos1));
    			}else{
        			setUsername(value);//设为匿名用户
        			setPassword("");
    			}
    			value = value.substring(pos1+1);
    		}else{
    			setUsername("anonymous");//设为匿名用户
    			setPassword("");
    		}
    		//解析主机和端口号
    		pos1 = value.indexOf(':');
    		if(pos1>=0)
    		{
    			setHost(value.substring(0, pos1));
    			setPort(Integer.parseInt(value.substring(pos1+1)));
    		}else{
    			setHost(value);
    			setPort(21);
    		}
    	}
    	return result;
    }
    
    public String getHost() {   
        return host;   
    }   
  
    public void setHost(String host) {   
        this.host = host;   
    }   
  
    public int getPort() {   
        return port;   
    }   
  
    public void setPort(int port) {   
        this.port = port;   
    }   
  
    public String getUsername() {   
        return username;   
    }   
  
    public void setUsername(String username) {   
        this.username = username;   
    }   
  
    public String getPassword() {   
        return password;   
    }   
  
    public void setPassword(String password) {   
        this.password = password;   
    }   
  
    public boolean isBinaryTransfer() {   
        return binaryTransfer;   
    }   
  
    public void setBinaryTransfer(boolean binaryTransfer) {   
        this.binaryTransfer = binaryTransfer;   
    }   
  
    public boolean isPassiveMode() {   
        return passiveMode;   
    }   
  
    public void setPassiveMode(boolean passiveMode) {   
        this.passiveMode = passiveMode;   
    }   
  
    public String getEncoding() {   
        return encoding;   
    }   
  
    public void setEncoding(String encoding) {   
        this.encoding = encoding;   
    }   
  
    public int getClientTimeout() {   
        return clientTimeout;   
    }   
  
    public void setClientTimeout(int clientTimeout) {   
        this.clientTimeout = clientTimeout;   
    }   
  
    //---------------------------------------------------------------------   
    // private method   
    //---------------------------------------------------------------------   
    /**  
     * 返回一个FTPClient实例  
     *   
     * @throws Exception  
     */  
    private FTPClient getFTPClient() throws Exception {   
        FTPClient ftpClient = new FTPClient(); //构造一个FtpClient实例   
        ftpClient.setControlEncoding(encoding); //设置字符集   
  
        connect(ftpClient); //连接到ftp服务器   
        //设置为passive模式   
        if (passiveMode) {   
            ftpClient.enterLocalPassiveMode();   
        }   
        setFileType(ftpClient); //设置文件传输类型   
           
        try {   
            ftpClient.setSoTimeout(clientTimeout);   
        } catch (SocketException e) {   
            throw new Exception("Set timeout error.", e);   
        }   
  
        return ftpClient;   
    }   
  
    /**  
     * 设置文件传输类型  
     *   
     * @throws Exception  
     * @throws IOException  
     */  
    private void setFileType(FTPClient ftpClient) throws Exception {   
        try {   
            if (binaryTransfer) {   
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);   
            } else {   
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);   
            }   
        } catch (IOException e) {   
            throw new Exception("Could not to set file type.", e);   
        }   
    }   
  
    /**  
     * 连接到ftp服务器  
     *   
     * @param ftpClient  
     * @return 连接成功返回true，否则返回false  
     * @throws Exception  
     */  
    public boolean connect(FTPClient ftpClient) throws Exception {   
        try {   
            ftpClient.connect(host, port);   
  
            // 连接后检测返回码来校验连接是否成功   
            int reply = ftpClient.getReplyCode();   
  
            if (FTPReply.isPositiveCompletion(reply)) {   
                //登陆到ftp服务器   
                if (ftpClient.login(username, password)) {   
                    setFileType(ftpClient);   
                    return true;   
                }   
            } else {   
                ftpClient.disconnect();   
                throw new Exception("FTP server refused connection.");   
            }   
        } catch (IOException e) {   
            if (ftpClient.isConnected()) {   
                try {   
                    ftpClient.disconnect(); //断开连接   
                } catch (IOException e1) {   
                    throw new Exception("Could not disconnect from server.", e);   
                }   
  
            }   
            throw new Exception("Could not connect to server.", e);   
        }   
        return false;   
    }   
  
    /**  
     * 断开ftp连接  
     *   
     * @throws Exception  
     */  
    private void disconnect(FTPClient ftpClient) throws Exception {   
        try {   
            ftpClient.logout();   
            if (ftpClient.isConnected()) {   
                ftpClient.disconnect();   
            }   
        } catch (IOException e) {   
            throw new Exception("Could not disconnect from server.", e);   
        }   
    }   
  
    //---------------------------------------------------------------------   
    // public method   
    //---------------------------------------------------------------------   
    /**  
     * 上传一个本地文件到远程指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @return 成功时，返回true，失败返回false  
     * @throws Exception  
     */  
    public boolean put(String serverFile, String localFile) throws Exception {   
        return put(serverFile, localFile, false);   
    }   
  
    /**  
     * 上传一个本地文件到远程指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @param delFile 成功后是否删除文件  
     * @return 成功时，返回true，失败返回false  
     * @throws Exception  
     */  
    public boolean put(String serverFile, String localFile, boolean delFile) throws Exception {   
        FTPClient ftpClient = null;   
        InputStream input = null;   
        try {   
            ftpClient = getFTPClient();   
            // 处理传输   
            input = new FileInputStream(localFile);   
            ftpClient.storeFile(serverFile, input); 
            input.close();   
            if (delFile) {   
                (new File(localFile)).delete();   
            }   
            return true;   
        } catch (FileNotFoundException e) {   
            throw new Exception("local file not found.", e);   
        } catch (IOException e) {   
            throw new Exception("Could not put file to server.", e);   
        } finally {   
            try {   
                if (input != null) {   
                    input.close();   
                }   
            } catch (Exception e) {   
                throw new Exception("Couldn't close FileInputStream.", e);   
            }   
            if (ftpClient != null) {   
                disconnect(ftpClient); //断开连接   
            }   
        }   
    }   
  
    /**  
     * 下载一个远程文件到本地的指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @return 成功时，返回true，失败返回false  
     * @throws Exception  
     */  
    public boolean get(String serverFile, String localFile) throws Exception {   
        return get(serverFile, localFile, false);   
    }   
  
    /**  
     * 下载一个远程文件到本地的指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @return 成功时，返回true，失败返回false  
     * @throws Exception  
     */  
    public boolean get(String serverFile, String localFile, boolean delFile) throws Exception {   
        OutputStream output = null;   
        try {   
            output = new FileOutputStream(localFile);   
            return get(serverFile, output, delFile);   
        } catch (FileNotFoundException e) {   
            throw new Exception("local file not found.", e);   
        } finally {   
            try {   
                if (output != null) {   
                    output.close();   
                }   
            } catch (IOException e) {   
                throw new Exception("Couldn't close FileOutputStream.", e);   
            }   
        }   
    }   
       
    /**  
     * 下载一个远程文件到指定的流  
     * 处理完后记得关闭流  
     *   
     * @param serverFile  
     * @param output  
     * @return  
     * @throws Exception  
     */  
    public boolean get(String serverFile, OutputStream output) throws Exception {   
        return get(serverFile, output, false);   
    }   
       
    /**  
     * 下载一个远程文件到指定的流  
     * 处理完后记得关闭流  
     *   
     * @param serverFile  
     * @param output  
     * @param delFile  
     * @return  
     * @throws Exception  
     */  
    public boolean get(String serverFile, OutputStream output, boolean delFile) throws Exception {   
        FTPClient ftpClient = null;   
        try {   
            ftpClient = getFTPClient();   
            // 处理传输   
            ftpClient.retrieveFile(serverFile, output);   
            if (delFile) { // 删除远程文件   
                ftpClient.deleteFile(serverFile);   
            }   
            return true;   
        } catch (IOException e) {   
            throw new Exception("Couldn't get file from server.", e);   
        } finally {   
            if (ftpClient != null) {   
                disconnect(ftpClient); //断开连接   
            }   
        }   
    }   
       
    /**  
     * 从ftp服务器上删除一个文件  
     *   
     * @param delFile  
     * @return  
     * @throws Exception  
     */  
    public boolean delete(String delFile) throws Exception {   
        FTPClient ftpClient = null;   
        try {   
            ftpClient = getFTPClient();   
            ftpClient.deleteFile(delFile);   
            return true;   
        } catch (IOException e) {   
            throw new Exception("Couldn't delete file from server.", e);   
        } finally {   
            if (ftpClient != null) {   
                disconnect(ftpClient); //断开连接   
            }   
        }   
    }   
       
    /**  
     * 批量删除  
     *   
     * @param delFiles  
     * @return  
     * @throws Exception  
     */  
    public boolean delete(String[] delFiles) throws Exception {   
        FTPClient ftpClient = null;   
        try {   
            ftpClient = getFTPClient();   
            for (String s : delFiles) {   
                ftpClient.deleteFile(s);   
            }   
            return true;   
        } catch (IOException e) {   
            throw new Exception("Couldn't delete file from server.", e);   
        } finally {   
            if (ftpClient != null) {   
                disconnect(ftpClient); //断开连接   
            }   
        }   
    }   
  
    /**  
     * 列出远程默认目录下所有的文件  
     *   
     * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组  
     * @throws Exception  
     */  
    public String[] listNames() throws Exception {   
        return listNames(null);   
    }   
  
    /**  
     * 列出远程目录下所有的文件  
     *   
     * @param remotePath 远程目录名  
     * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组  
     * @throws Exception  
     */  
    public String[] listNames(String remotePath) throws Exception {   
        FTPClient ftpClient = null;   
        try {   
            ftpClient = getFTPClient();   
            String[] listNames = ftpClient.listNames(remotePath);   
            return listNames;   
        } catch (IOException e) {   
            throw new Exception("列出远程目录下所有的文件时出现异常", e);   
        } finally {   
            if (ftpClient != null) {   
                disconnect(ftpClient); //断开连接   
            }   
        }   
    }   
    public boolean isExist(String remoteFilePath)throws Exception{
    	
    	 FTPClient ftpClient = null;   
    	try{
    		ftpClient = getFTPClient();
    		File file=new File(remoteFilePath);
    		 
    		String remotePath=remoteFilePath.substring(0,(remoteFilePath.indexOf(file.getName())-1));
    		String[] listNames = ftpClient.listNames(remotePath);   
    		System.out.println(remoteFilePath);
    		for(int i=0;i<listNames.length;i++){

    			if(remoteFilePath.equals(listNames[i])){
    				flag=true;
    				System.out.println("文件:"+file.getName()+"已经存在了");
    				break;
    				
    			}else {
    				flag=false;
    			}
    		}
    		
    	} catch (IOException e) {   
            throw new Exception("查询文件是否存在文件时出现异常", e);   
        } finally {   
    		if (ftpClient != null) {   
                disconnect(ftpClient); //断开连接   
            }   
        }   
        return flag;
    }
    
    public static void main(String[] args) throws Exception {   
        FTPHelper ftp = new FTPHelper();
        String serverFile = ftp.parseFTPAddress("ftp://user:passwd@address:port/share/incoming/simkai.ttf");
   
        String localFile="D:/simkai.ttf";
        ftp.get(serverFile, localFile);
    }   

}

