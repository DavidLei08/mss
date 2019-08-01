package com.mss.backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * window系统Mysql数据库备份工具类
 * 
 * @author David Lei
 *
 */
public class MysqlDump {
	
private Logger logger=LoggerFactory.getLogger(MysqlDump.class);
	
	private DumpConfig dumpConfig;

	public  void backup() throws IOException {
		
		if(dumpConfig!=null){
			logger.info(" database:"+dumpConfig.getMysqlDataBase()+ " port:"+dumpConfig.getJdbcPort()+ "  start backup");

		 String sqlurl = dumpConfig.getInstallPath()+"mysqldump -u"+
			        dumpConfig.getJdbcUserName()+"	-p"+
					dumpConfig.getJdbcPassword()+
					" -P"+dumpConfig.getJdbcPort()+" "+
					dumpConfig.getMysqlDataBase()+" ";
		 
		 String path =dumpConfig.getBackupPath();
		 
			Runtime rt = Runtime.getRuntime(); // 返回与当前的Java应用程序的运行时对象
			// 调用 调用mysql的安装目录的命令
			Process child = rt.exec(sqlurl);
			// 设置导出编码为utf-8。这里必须是utf-8
			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
			
			InputStreamReader xx = new InputStreamReader(in, "utf-8");
			// 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			if(StringUtils.isEmpty(outStr)){
				throw new IOException("DumpConfig Object is not true,backup failed");
			}
			// 要用来做导入用的sql目标文件：
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String date = sdf.format(d);
			File fd = new File(path + "/" + date + "-backup.sql");
			File fs = new File(path);
			if (!fd.exists()) {// 判断导出文件是否存在，如果不存在则创建它
				fs.mkdirs();
				fd.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(fd);

			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
			writer.write(outStr);
			writer.flush();
			// 资源释放
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();
			
		}else{
			throw new IOException("DumpConfig Object is null,backup failed");
		}
	}

	public DumpConfig getDumpConfig() {
		return dumpConfig;
	}

	public void setDumpConfig(DumpConfig dumpConfig) {
		this.dumpConfig = dumpConfig;
	}

}