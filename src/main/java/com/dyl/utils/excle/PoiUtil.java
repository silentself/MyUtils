package com.dyl.utils.excle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2019/7/22 16:59
 * @Author Dong YL
 * @Email silentself@126.com
 */
public class PoiUtil {

    static String EXCEL_TYPE_XLSX = ".xlsx";
    static String OBS_DIR_SEPARATOR = "/";
    static String OBS_DIR_EXCEL = "excel";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");

    /**
     * 生成obs对象上传Excel的key
     *
     * @param devOrProd  开发环境或是生产环境（dev-开发，prod-生产）
     * @param moduleName 导出的模块名字，如：MCN数据统计_MCN内容统计
     * @param uuid       用户id，如：10000000
     * @return obsObject (组成：dev(prod)/excel/用户id/fileName(如：MCN数据统计_MCN内容统计)时间戳.xlsx)
     * 例如：dev/excel/10000000/MCN数据统计_MCN内容统计201905151121041.xlsx
     * prod/excel/10000000/MCN数据统计_MCN内容统计201905151121041.xlsx
     */
    public static String getObsKey(String devOrProd, String moduleName, Long uuid) {
        StringBuilder obsObject = new StringBuilder();
        obsObject.append(devOrProd).append(OBS_DIR_SEPARATOR)
                .append(OBS_DIR_EXCEL).append(OBS_DIR_SEPARATOR)
                .append(uuid).append(OBS_DIR_SEPARATOR)
                .append(moduleName).append(simpleDateFormat.format(new Date())).append(EXCEL_TYPE_XLSX);
        return obsObject.toString();
    }

    /**
     * 生成流式版XSSFWorkbook
     *
     * @param heads  请求头
     * @param values 参数名
     * @param data   数据
     * @return 流式版XSSFWorkbook
     */
    public static SXSSFWorkbook createSXSSFWorkbook(List<String> heads, List<String> values, List<Map<String, Object>> data) {
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        for (int rownum = 0; rownum < data.size(); rownum++) {
            Row row = sh.createRow(rownum + 1);
            //设置表头
            if (rownum == 0) {
                Row rowOne = sh.createRow(0);
                for (int cellnum = 0; cellnum < heads.size(); cellnum++) {
                    Cell cell = rowOne.createCell(cellnum);
                    cell.setCellValue(heads.get(cellnum));
                }
            }
            //填充数据
            Map<String, Object> dataMap = data.get(rownum);
            for (int cellnum = 0; cellnum < values.size(); cellnum++) {
                Cell cell = row.createCell(cellnum);
                cell.setCellValue(String.valueOf(dataMap.get(values.get(cellnum))));
            }
        }
        return wb;
    }

    /**
     * 将SXSSFWorkbook转换到缓冲区
     *
     * @param workbook 流式版XSSFWorkbook
     * @return 输入流
     */
    public static InputStream workbookConvertToStream(SXSSFWorkbook workbook) throws IOException {
        //临时缓冲区
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            //创建临时文件
            workbook.write(out);
            byte[] bookByteAry = out.toByteArray();
            return new ByteArrayInputStream(bookByteAry);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return null;
    }

}
