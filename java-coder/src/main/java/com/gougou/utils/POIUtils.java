package com.gougou.utils;

import com.gougou.utils.interfaceutils.InputStreamPeocess1;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;


/**
 * @author Liruilong
 * @return
 * @description 文本写入excel
 * @date 2020年04月14日  16:04:20
 **/

public class POIUtils {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static List<String> fileNameExtension = Arrays.asList(XLS, XLSX);

    /**
     * @param inFile     文件输入 ： txt
     * @param outFile    文件输出位置 ：(文件|目录)
     * @param splie      分割符 正则匹配，特殊字符需要转义
     * @param append     文件存在时的写入方式
     * @param columnName
     * @return
     * @description  文件转换
     * @author Liruilong
     * @date  2020年04月18日  11:04:51
     **/

    public static void txt2Excel(File inFile, File outFile, String splie,boolean append, String... columnName) {
        List<String> list = txt2List(inFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = null;
        String fileName = inFile.getName().substring(0, inFile.getName().indexOf("."));
        try {
            if (!outFile.exists()) {
                System.out.println("***************************文件路径错误****************************");
                throw new Exception();
            } else if (outFile.isDirectory()) {
                outFile.mkdirs();
                fileOutputStream = new FileOutputStream(outFile.getAbsolutePath() + "\\" + fileName + ".xlsx");
            } else {
                if (outFile.isFile()) {
                    String tempFileNameExtension = outFile.getName().substring(outFile.getName().indexOf(".") + 1);
                    if (fileNameExtension.stream().anyMatch( o ->o.equals(tempFileNameExtension))){
                        fileOutputStream = new FileOutputStream(outFile,append);
                    }
                    fileOutputStream = new FileOutputStream(outFile.getParent() + "\\" + fileName + ".xlsx");
                }
            }
            createExcel(fileName,splie,list,columnName).write(baos);
            fileOutputStream.flush();
            fileOutputStream.write(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param inFile     文件输入 ： txt
     * @param outFile    文件输出位置 ：(文件|目录)
     * @param splie      分割符 正则匹配，特殊字符需要转义
     * @param columnName  列名
     * @return
     * @description  以默认方式写入
     * @author Liruilong
     * @date  2020年04月18日  11:04:51
     **/

    public static void txt2Excel(File inFile, File outFile, String splie, String... columnName) {
        List<String> list = txt2List(inFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = null;
        String fileName = inFile.getName().substring(0, inFile.getName().indexOf("."));
        try {
            if (!outFile.exists()) {
                System.out.println("***************************文件路径错误****************************");
                throw new Exception();
            } else if (outFile.isDirectory()) {
                outFile.mkdirs();
                fileOutputStream = new FileOutputStream(outFile.getAbsolutePath() + "\\" + fileName + ".xlsx");
            } else {
                if (outFile.isFile()) {
                    //获取输出文件后缀
                    String tempFileNameExtension = outFile.getName().substring(outFile.getName().indexOf(".") + 1);
                    if (fileNameExtension.stream().anyMatch( o ->o.equals(tempFileNameExtension))){
                        fileOutputStream = new FileOutputStream(outFile,false);
                    }
                    fileOutputStream = new FileOutputStream(outFile.getParent() + "\\" + fileName + ".xlsx");
                }
            }
            createExcel(fileName,splie,list,columnName).write(baos);
            fileOutputStream.flush();
            fileOutputStream.write(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileName   文件名
     * @param splie      分割符 正则匹配，特殊字符需要转义
     * @param list       文件转换后的List
     * @param columnName 列名
     * @return
     * @description
     * @author Liruilong
     * @date  2020年04月18日  11:04:46
     **/

    public static HSSFWorkbook createExcel(String fileName,String splie,List<String> list,String...columnName) {
        //1. 创建一个 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 创建文档摘要
        workbook.createInformationProperties();
        //3. 获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory(fileName);
        //文档管理员
        docInfo.setManager("liruilong");
        //设置公司信息
        docInfo.setCompany("www.liruiong.org");
        //4. 获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle(fileName);
        //文档作者
        summInfo.setAuthor("liruilong");
        // 文档备注
        summInfo.setComments("本文档由 liruilong 提供");
        //5. 创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet(fileName);
        HSSFRow rx = null;
        //构造表格框架
        rx = sheet.createRow(0);
        for (int i = 0; i < columnName.length; i++) {
            //设置列的宽度
            sheet.setColumnWidth(i, 12 * 456);
            //创建标题行
            HSSFCell cx = rx.createCell(i);
            cx.setCellValue(columnName[i]);
            cx.setCellStyle(headerStyle);
        }
        // 填充数据
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            String[] tempLine = list.get(i).toString().split(splie);
            for (int i1 = 0; i1 < tempLine.length; i1++) {
                row.createCell(i1).setCellValue(tempLine[i1]);
            }
        }
        return workbook;
    }

    /**
     * @param file
     * @param data 每行数据
     * @return
     * @description 写入excel数据
     * @author Liruilong
     * @date 2020年04月15日  10:04:40
     **/

    public static void excelWriter(File file, String... data) {
        //获取文件类型
        String fileType = file.getName().substring(file.getName().indexOf(".") + 1);
        try (POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file))) {
            Workbook workbook = null;
            if (fileType.equalsIgnoreCase(XLS)) {
                workbook = new HSSFWorkbook(poifsFileSystem);
            } else if (fileType.equalsIgnoreCase(XLSX)) {
                workbook = new XSSFWorkbook(file);
            }
            Sheet sheetAt = workbook.getSheetAt(0);
            Row row = sheetAt.getRow(0);
            System.out.println("最后一行的行号为：" + sheetAt.getLastRowNum() + "--记录为:" + row.getLastCellNum());
            row = sheetAt.createRow(sheetAt.getLastRowNum() + 1);
            for (int i = 0; i < data.length; i++) {
                row.createCell(i).setCellValue(data[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param file
     * @return
     * @description 判断文件的Excel类型，输出Excel对象
     * @author Liruilong
     * @date 2020年04月15日  10:04:37
     **/

    public static Workbook getWorkbook(File file) {
        Workbook workbook = null;
        String fileType = file.getName().substring(file.getName().indexOf(".") + 1);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            if (fileType.equalsIgnoreCase(XLS)) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else if (fileType.equalsIgnoreCase(XLSX)) {
                workbook = new XSSFWorkbook(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * @param file
     * @return
     * @description 文本文件 --》 内存List
     * @author Liruilong
     * @date 2020年04月14日  18:04:01
     **/

    public static List<String> txt2List(File file) {
        String string = null;
        List<String> txtListAll = new ArrayList<>();
        if (!file.exists() || file.isDirectory()) {
            System.out.println("***************************文件错误****************************");
        } else {
            fileToBufferedReader((bufferedReader) -> {
                String str = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((str = bufferedReader.readLine()) != null) {
                    // TODO 此处可以书写去重逻辑。
                    if (!txtListAll.contains(str)) {
                        txtListAll.add(str);
                    }
                }
            }, file);
        }
        if (txtListAll.size() > 65535) {
            System.out.println("***************************行数太多错误****************************");
        }
        return txtListAll;
    }

    /**
     * @param inputStreamPeocess
     * @param file
     * @return 环绕处理
     * @description
     * @author Liruilong
     * @date 2020年04月14日  16:04:57
     **/

    public static void fileToBufferedReader(InputStreamPeocess1 inputStreamPeocess, File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream)) {
                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    inputStreamPeocess.peocess(bufferedReader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileParentPath 文件父路径
     * @param shilp          分割符
     * @return
     * @description 以父目录的形式进行转化，默认输出位置，列名，写入方式
     * @author Liruilong
     * @date 2020年04月18日  10:04:28
     **/

    public static void dir2Excel(String fileParentPath, String shilp) {
        File[] files = new File(fileParentPath).listFiles();
        Arrays.stream(files).forEach(f -> {
            txt2Excel(f, f, shilp, "");
        });
    }

    /**
     * @param fileParentPath 输入文件父路径
     * @param outParentPath  输出文件父路径
     * @param shilp          分割符
     * @return
     * @description 以指定输出父目录输出，列名默认
     * @author Liruilong
     * @date 2020年04月18日  10:04:41
     **/

    public static void dir2Excel(String fileParentPath, String outParentPath, String shilp) {
        File[] files = new File(fileParentPath).listFiles();
        Arrays.stream(files).forEach(f -> {
            txt2Excel(f, new File(outParentPath), shilp, "");
        });
    }

    /**
     * @param fileParentPath 输入文件父路径
     * @param outParentPath  输出文件父路径
     * @param shilp          分割符
     * @return
     * @description 以指定输出父目录输出，列名指定
     * @author Liruilong
     * @date 2020年04月18日  10:04:23
     **/

    public static void dir2Excel(String fileParentPath, String outParentPath, String shilp, String... columnName) {
        File[] files = new File(fileParentPath).listFiles();
        Arrays.stream(files).forEach(f -> {
            txt2Excel(f, new File(outParentPath), shilp, columnName);
        });
    }


    /**
     * @param filePath 文件路径
     * @param shilp    分割符
     * @return
     * @description 单文件转化，指定分割符，
     * @author Liruilong
     * @date 2020年04月18日  10:04:08
     **/

    public static void OneTxt2Excel(String filePath, String shilp) {
        File file = new File(filePath);
        txt2Excel(file, file, shilp,"");
    }

    /**
     * @param filePath
     * @param outFilePath
     * @param shilp
     * @param append
     * @return
     * @description 单文件转化，指定分割符，写入方式
     * @author Liruilong
     * @date  2020年04月18日  11:04:07
     **/

    public static void OneTxt2Excel(String filePath,  String outFilePath, String shilp, boolean append) {
        File file = new File(filePath);
        File outFile = new File(outFilePath);
        txt2Excel(file, outFile, shilp,append, "");
    }

    public static void main(String[] args) {

        /*父目录转换
        public static void dir2Excel(String)
        public static void dir2Excel(String,String)
        public static void dir2Excel(String,String,String[])*/

        /* 文件转换
        public static void OneTxt2Excel(String,String,boolean)
        public static void OneTxt2Excel(String)*/
        Arrays.stream(POIUtils.class.getMethods()).forEach( m ->{
            System.out.println(m.getName());
        });

    }

}
