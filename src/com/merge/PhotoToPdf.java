package com.merge;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 将多张图片合并转为PDF；需要用到iTextpdf包，
 *
 * @author oozerone
 *
 */
public class PhotoToPdf {
    /**
     *
     * @param imageFolderPath
     *            图片文件夹地址
     * @param pdfPath
     *            PDF文件保存地址
     *
     */
    public static void toPdf(String imageFolderPath, String pdfPath) {
        try {
            // 图片文件夹地址
            // String imageFolderPath = "D:/Demo/ceshi/";
            // 图片地址
            String imagePath = null;
            // PDF文件保存地址
            // String pdfPath = "D:/Demo/ceshi/hebing.pdf";
            // 输入流
            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            //doc.open();
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img = null;
            // 实例化图片
            Image image = null;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();


            //对文件夹内文件进行排序
            //中间值
            File temp =null;
            //外循环:我认为最小的数,从0~长度-1
            for(int j = 0; j<files.length-1;j++){
                //最小值:假设第一个数就是最小的
                String min = files[j].getName();
                //记录最小数的下标的
                int minIndex=j;
                //内循环:拿我认为的最小的数和后面的数一个个进行比较
                for(int k=j+1;k<files.length;k++){
                    //找到最小值
                    if (Integer.parseInt(min.substring(0,min.indexOf(".")))>Integer.parseInt(files[k].getName().substring(0,files[k].getName().indexOf(".")))){
                        //修改最小
                        min=files[k].getName();
                        minIndex=k;
                    }
                }
                //当退出内层循环就找到这次的最小值
                //交换位置
                temp = files[j];
                files[j]=files[minIndex];
                files[minIndex]=temp;
            }


            // 循环获取图片文件夹内的图片
            for (File file1 : files) {
                if (file1.getName().endsWith(".png")
                        || file1.getName().endsWith(".jpg")
                        || file1.getName().endsWith(".gif")
                        || file1.getName().endsWith(".jpeg")
                        || file1.getName().endsWith(".tif")) {
                    // System.out.println(file1.getName());
                    imagePath = imageFolderPath + file1.getName();
                    System.out.println("正在处理:"+file1.getName());
                    // 读取图片流
                    img = ImageIO.read(new File(imagePath));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img
                            .getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.open();
                    doc.add(image);
                }
            }
            // 关闭文档
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();

        //修改照片路径以及生成PDF路径
        toPdf("C:/Users/Admin/Downloads/test/", "C:/Users/Admin/Downloads/test.pdf");
        long time2 = System.currentTimeMillis();
        int time = (int) ((time2 - time1)/1000);
        System.out.println("执行了："+time+"秒！");
    }

}
