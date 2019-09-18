package com.dyl.utils.excle;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.common.Assert;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class JxlUtil {

    private static int maxRowCount = 5000;
    private static String excelPath = "D:\\tmp";

    /**
     * 将实体类的信息写入Excel文件  
     *
     * @param list              实体类集合
     * @param titles            excel标题名称
     * @param fileds            对应标题所填充的实体类信息（属性名）
     * @throws IOException
     * @throws WriteException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static File writeExcel(List<Map<String, Object>> list, String[] titles, String[] fileds) throws IOException, WriteException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
        long currentTimeMillis = System.currentTimeMillis();
        String random = Double.toString(Math.random());
        WorkbookSettings wbSetting = new WorkbookSettings();
        wbSetting.setUseTemporaryFileDuringWrite(true);
        wbSetting.setTemporaryFileDuringWriteDirectory(new File(excelPath));//临时文件夹的位置
        File createTempFile = File.createTempFile(String.valueOf(currentTimeMillis + random), ".xls");
        WritableWorkbook wwb = Workbook.createWorkbook(createTempFile, wbSetting);

        Assert.verify(null != list);

        assert list != null;

        int ceil = (int) Math.ceil((double) list.size() / (double) maxRowCount);
        for (int f = 0; f < ceil; f++) {
            List<Map<String, Object>> subList = list.subList(f * maxRowCount,
                    (f + 1) * maxRowCount <= list.size() ? (f + 1) * maxRowCount : list.size());
            // 创建一个可写入的工作表
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet" + (f + 1), 0);

            /*
             * 表头单元格样式的设定 WritableFont.createFont("宋体")：设置字体为宋体 12：设置字体大小
             * WritableFont.BOLD:设置字体加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
             * UnderlineStyle.NO_UNDERLINE：没有下划线 Colour.BLACK 字体颜色 黑色
             */
				/*WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				WritableCellFormat titleCellFormat = new WritableCellFormat(titleFont);
				// 字休居中
				titleCellFormat.setAlignment(Alignment.CENTRE);
				// 设置单元格背景色：表体为白色
				titleCellFormat.setBackground(Colour.WHITE);
				// 整个表格线为细线、黑色
				titleCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

				WritableFont contentFont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD,
						false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				WritableCellFormat contentCellFormat = new WritableCellFormat(contentFont);
				// 字休居中
				contentCellFormat.setAlignment(Alignment.CENTRE);
				// 设置单元格背景色：表体为白色
				contentCellFormat.setBackground(Colour.WHITE);
				// 整个表格线为细线、黑色
				contentCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);*/

            for (int i = 0; i < titles.length; i++) {
                // ws.setColumnView(i, columnLength[i]); // 设置列的宽度
                Label label = new Label(i, 0, titles[i]);
                ws.addCell(label);
            }
            // 填充实体类的基本信息
            for (int j = 0; !subList.isEmpty() && j < subList.size(); j++) {
                Map<String, Object> t = subList.get(j);
                String[] contents = new String[fileds.length];
                for (int i = 0; i < fileds.length; i++) {
                    String str = String.valueOf(t.get(fileds[i]));
                    if (str == null || str.equals("null"))
                        str = "";
                    contents[i] = str;
                }

                for (int n = 0; n < contents.length; n++) {
                    // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                    Label labelC = new Label(n, j + 1, contents[n]);
                    // 将生成的单元格添加到工作表中
                    ws.addCell(labelC);
                }
            }
        }
        // 从内存中写入文件中
        wwb.write();
        // 关闭资源，释放内存
        wwb.close();
        return createTempFile;
    }

    /**
     * 将第一个字母转换为大写字母并和get拼合成方法  
     *
     * @param origin
     * @return
     */
    public static String toUpperCaseFirstOne(String origin) {
        StringBuffer sb = new StringBuffer(origin);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "get");
        return sb.toString();
    }


}
