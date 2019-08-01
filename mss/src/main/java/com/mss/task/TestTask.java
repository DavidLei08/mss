package com.mss.task;


import java.io.IOException;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mss.backup.DumpConfig;
import com.mss.backup.MysqlDump;

@Component
public class TestTask {
	
	@Inject
	private DumpConfig dumpConfig;

	@Scheduled(cron="0 0 0-2 * * ?")
	void test(){
		MysqlDump mysqlDump=new MysqlDump();
		try {
			mysqlDump.setDumpConfig(dumpConfig);
			mysqlDump.backup();
			System.out.println("备份成功");
		} catch (IOException e) {
			System.out.println("备份失败");
			e.printStackTrace();
		}
	}
}
