package com.dyl.utils.excle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Cvs文件生成工具类
 * 
 * @author admin
 *
 */
public class CsvUtil {

	/**
	 * 写一行数据
	 * 
	 * @param row
	 *            数据列表
	 * @param csvWriter
	 *            文件对象
	 * @throws IOException
	 */
	public static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
		StringBuffer sb = new StringBuffer();
		for (Object data : row) {
			if (null == data) {
				data = "";
			}
			sb.append("\"").append(data).append("\",");
		}
		csvWriter.append(sb.toString());
		csvWriter.newLine();
	}



	/**
	 * 创建写入文件环境
	 * 
	 * @param heads
	 *            表头
	 * @param data
	 *            需要写入的全部数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public static File createCvsFile(Object[] heads, List<Map<String, Object>> data,List<String> columnNames) throws IOException {
		List<Object> headList = Arrays.asList(heads);
		List<Object> rowList = new ArrayList<Object>();
		String prefix = String.valueOf(System.currentTimeMillis() + (Math.random() * 9 + 1) * 100000);// 文件名
		String suffix = ".csv"; // 文件格式

		// 创建临时文件
		File csvFile = File.createTempFile(prefix, suffix);
		// GB2312使正确读取分隔符","
		BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
				1024);
		// 写入表头
		writeRow(headList, csvWriter);
		// 写入文件内容
		long l1 = System.currentTimeMillis();
		for (Map<String, Object> row : data) {
			List<Object> list = new ArrayList<>(columnNames.size());
			rowList.clear();

			for (String columnName : columnNames) {
				list.add(row.get(columnName));
			}
			rowList.addAll(list);
			writeRow(rowList, csvWriter);
		}
		long l2 = System.currentTimeMillis();
		System.err.println(l2 -l1);
		data.clear();
		csvWriter.flush();
		csvWriter.close();

		return csvFile;

	}

}
