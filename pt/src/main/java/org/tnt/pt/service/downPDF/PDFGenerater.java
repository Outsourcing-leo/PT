package org.tnt.pt.service.downPDF;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.xerces.impl.dv.xs.YearDV;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.util.PTPARAMETERS;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
  
public class PDFGenerater{  
    Document document = new Document();// 建立一个Document对象      
      
    private static Font headfont ;// 设置字体大小  
    private static Font keyfont;// 设置字体大小  
    private static Font textfont;// 设置字体大小  
      
    static{  
        BaseFont bfChinese;  
        try {  
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);  
            headfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小  
            keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小  
            textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小  
        } catch (Exception e) {           
            e.printStackTrace();  
        }   
    }  
      
      
    int maxWidth = 520;  
      
      
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);          
         cell.setHorizontalAlignment(align);      
         cell.setPhrase(new Phrase(value,font));  
         return cell;  
    }  
      
     public PdfPCell createCell(String value,com.lowagie.text.Font font){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);   
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
  
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
    public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan,boolean boderFlag){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
         cell.setPadding(3.0f);  
         if(!boderFlag){  
             cell.setBorder(0);  
             cell.setPaddingTop(15.0f);  
             cell.setPaddingBottom(8.0f);  
         }  
        return cell;  
    }  
     public PdfPTable createTable(int colNumber){  
        PdfPTable table = new PdfPTable(colNumber);  
        try{  
            table.setTotalWidth(maxWidth);  
            table.setLockedWidth(true);  
            table.setHorizontalAlignment(Element.ALIGN_CENTER);       
            table.getDefaultCell().setBorder(1);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return table;  
    }  
     public PdfPTable createTable(float[] widths){  
            PdfPTable table = new PdfPTable(widths);  
            try{  
                table.setTotalWidth(maxWidth);  
                table.setLockedWidth(true);  
                table.setHorizontalAlignment(Element.ALIGN_CENTER);       
                table.getDefaultCell().setBorder(1);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return table;  
        }  
      
     public PdfPTable createBlankTable(){  
         PdfPTable table = new PdfPTable(1);  
         table.getDefaultCell().setBorder(0);  
         table.addCell(createCell("", keyfont));
         table.setSpacingAfter(20.0f);  
         table.setSpacingBefore(20.0f);  
         return table;  
     }
       
     public ByteArrayOutputStream generatePDF(List<ZoneGroup> zoneGroupList,List<WeightBand> documentList,List<WeightBand> ndocumentList,List<WeightBand> eonomyList,Map<String,Double> discountMap,
    		 Map<String,Double> recDiscountMap,Map<String,Double> traiffMap,List<ZoneGroup> zoneGroupDefaultList,List<Product> productList,Map<String,Double> discountDefaultMap,
    		 Business business,Customer customer,String logoPath,String oilFee,String zoneImage,String zoneImage2,String zoneImage3,String zoneImage4,String zoneImage5,String zoneImage6,String pdfPath) throws Exception{  
    	 ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
    	 // 定义输出位置并把文档对象装入输出对象中
    	 PdfWriter docWriter = null;
    	 docWriter = PdfWriter.getInstance(document, baosPDF);
    	 // 打开文档对象
    	 document.open();
		 Image jpg = Image.getInstance(logoPath);
		 jpg.setAlignment(Image.MIDDLE);
		 float heigth = jpg.getHeight();
	     float width = jpg.getWidth();
	     int percent = this.getPercent2(heigth, width);
	     jpg.scalePercent(percent + 10);
		 document.add(jpg);
    	 PdfPTable tableCUS = createTable(3);
    	 tableCUS.addCell(createCell("International Tariff for Express and Economy Express	", keyfont,Element.ALIGN_LEFT,4,false));
    	 /*tableCUS.addCell(createCell("Customer Profile", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Account number:  "+customer.getAccount(), keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Account name:    "+customer.getCusName(), keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Origin Depot:    "+business.getDepotCode(), keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Porjected Monthly Revenue in CNY:"+business.getTotalRev(), keyfont,Element.ALIGN_LEFT,4,false));*/
    	 tableCUS.addCell(createCell("PT Reference No. "+business.getApplicationReference(), keyfont,Element.ALIGN_LEFT,4,false));
    	 
    	 /*tableCUS.addCell(createCell("", keyfont,Element.ALIGN_LEFT,4,false));
    	 
    	 tableCUS.addCell(createCell("Loading Request", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Division:	G", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Rate Category:	13PTA", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Approval date:	<instert date>", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Effective Date:	to be confirmed", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("End Date:	Open", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Review Date:	Open", keyfont,Element.ALIGN_LEFT,3,false));
    	 for(int i=0;i<productList.size();i++){
          	Product product = productList.get(i);
          	if(i==0){
          		tableCUS.addCell(createCell("Product:	"+product.getProduct(), keyfont,Element.ALIGN_LEFT,3,false));
          	}else{
          		tableCUS.addCell(createCell("           "+product.getProduct(), keyfont,Element.ALIGN_LEFT,3,false));
          	}
         }
    	 
    	 tableCUS.addCell(createCell("Option:	No Discount for Options	", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Currency:	CNY", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Zoning:	Set Default	", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("FSI 	Full", keyfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("ESS	Full", keyfont,Element.ALIGN_LEFT,3,false));*/
    	 if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])){
    		 tableCUS.addCell(createCell("Terms of Payment:	SP", keyfont,Element.ALIGN_LEFT,3,false));
    	 }else{
    		 tableCUS.addCell(createCell("Terms of Payment:	"+customer.getPayment(), keyfont,Element.ALIGN_LEFT,3,false));
    	 }
    	 document.add(tableCUS);
    	 
    	 PdfPTable table0 = createTable(zoneGroupDefaultList.size()+1);  
         table0.addCell(createCell("Discounts (% off 13PTA)", keyfont,Element.ALIGN_LEFT,15,false));
         table0.addCell(createCell("PRODUCT", keyfont,Element.ALIGN_CENTER));  
         for(ZoneGroup zoneGroup:zoneGroupDefaultList){
         	table0.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
         }
         for(int i=0;i<productList.size();i++){
         	Product product = productList.get(i);
         	table0.addCell(createCell(product.getProduct(), textfont));
         	for(ZoneGroup zoneGroup:zoneGroupDefaultList){
         		String keyId = product.getId()+"_"+zoneGroup.getId();
             	table0.addCell(createCell(discountDefaultMap.get(keyId)+"", textfont));  
             }
         }  
        document.add(table0);
    	 
        PdfPTable table1 = createTable(zoneGroupList.size()+2);  
        table1.addCell(createCell("Discounts Profile-15D/12D/10D/09D", keyfont,Element.ALIGN_LEFT,15,false));
        table1.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));
        table1.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table1.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
        }
        for(int i=0;i<documentList.size();i++){  
        	WeightBand wd = documentList.get(i);
        	table1.addCell(createCell(wd.getName(), textfont));
        	table1.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
            	table1.addCell(createCell(discountMap.get(keyId)+"", textfont));  
            }
        }  
        document.add(table1);
        
        PdfPTable table2 = createTable(zoneGroupList.size()+2);  
        table2.addCell(createCell("Discounts Profile-15N/12N/10N/09N", keyfont,Element.ALIGN_LEFT,15,false));
        table2.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));  
        table2.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table2.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
        }
        for(int i=0;i<ndocumentList.size();i++){  
        	WeightBand wd = ndocumentList.get(i);
        	table2.addCell(createCell(wd.getName(), textfont));
        	table2.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
            	table2.addCell(createCell(discountMap.get(keyId)+"", textfont));  
            }
        }  
        document.add(table2);
        
        PdfPTable table3 = createTable(zoneGroupList.size()+2);  
        table3.addCell(createCell("Discounts Profile-48N", keyfont,Element.ALIGN_LEFT,15,false));
        table3.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));
        table3.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table3.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
        }
        for(int i=0;i<eonomyList.size();i++){
        	WeightBand wd = eonomyList.get(i);
        	table3.addCell(createCell(wd.getName(), textfont));
        	table3.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
            	table3.addCell(createCell(discountMap.get(keyId)+"", textfont));  
            }
        }
        document.add(table3);
        
        //价格卡
        PdfPTable table4 = createTable(zoneGroupList.size()+1);  
        table4.addCell(createCell("Discounts Profile-15D/12D/10D/09D", keyfont,Element.ALIGN_LEFT,15,false));
        table4.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table4.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
        }
        for(int i=0;i<documentList.size();i++){  
        	WeightBand wd = documentList.get(i);
        	table4.addCell(createCell(wd.getName(), textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
        		if(discountMap.get(keyId)!=null){
        			table4.addCell(createCell(discountMap.get(keyId)*traiffMap.get(keyId)/100+"", textfont));  
        		}else{
        			table4.addCell(createCell("", textfont));  
        		}
            }
        }  
        document.add(table4);
        
        PdfPTable table5 = createTable(zoneGroupList.size()+1);  
        table5.addCell(createCell("Discounts Profile-15N/12N/10N/09N", keyfont,Element.ALIGN_LEFT,15,false));
        table5.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));  
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table5.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
        }
        for(int i=0;i<ndocumentList.size();i++){  
        	WeightBand wd = ndocumentList.get(i);
        	table5.addCell(createCell(wd.getName(), textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
        		if(discountMap.get(keyId)!=null){
        			table5.addCell(createCell(discountMap.get(keyId)*traiffMap.get(keyId)/100+"", textfont));  
        		}else{
        			table5.addCell(createCell("", textfont));  
        		}
            }
        }  
        document.add(table5);
        
        PdfPTable table6 = createTable(zoneGroupList.size()+1);  
        table6.addCell(createCell("Discounts Profile-48N", keyfont,Element.ALIGN_LEFT,15,false));
        table6.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table6.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
        }
        for(int i=0;i<eonomyList.size();i++){
        	WeightBand wd = eonomyList.get(i);
        	table6.addCell(createCell(wd.getName(), textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
        		if(discountMap.get(keyId)!=null){
        			table6.addCell(createCell(discountMap.get(keyId)*traiffMap.get(keyId)/100+"", textfont));  
        		}else{
        			table6.addCell(createCell("", textfont));  
        		}
            }
        }
        document.add(table6);
        
        //如果是both  则一个rp  一个sp
        if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])){
        	 PdfPTable tableRP = createTable(3);
        	 tableRP.addCell(createCell("Terms of Payment:	RP", keyfont,Element.ALIGN_LEFT,3,false));
        	 document.add(tableRP);
        	 PdfPTable table7 = createTable(zoneGroupList.size()+2);  
        	 table7.addCell(createCell("Discounts Profile-15D/12D/10D/09D", keyfont,Element.ALIGN_LEFT,15,false));
        	 table7.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));
        	 table7.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
             for(ZoneGroup zoneGroup:zoneGroupList){
            	 table7.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
             }
             for(int i=0;i<documentList.size();i++){  
             	WeightBand wd = documentList.get(i);
             	table7.addCell(createCell(wd.getName(), textfont));
             	table7.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		table7.addCell(createCell(recDiscountMap.get(keyId)+"", textfont));  
                 }
             }  
             document.add(table7);
             
             PdfPTable table8 = createTable(zoneGroupList.size()+2);  
             table8.addCell(createCell("Discounts Profile-15N/12N/10N/09N", keyfont,Element.ALIGN_LEFT,15,false));
             table8.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));  
             table8.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
             for(ZoneGroup zoneGroup:zoneGroupList){
            	 table8.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
             }
             for(int i=0;i<ndocumentList.size();i++){  
             	WeightBand wd = ndocumentList.get(i);
             	table8.addCell(createCell(wd.getName(), textfont));
             	table8.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		table8.addCell(createCell(recDiscountMap.get(keyId)+"", textfont));  
                 }
             }  
             document.add(table8);
             
             PdfPTable table9 = createTable(zoneGroupList.size()+2);  
             table9.addCell(createCell("Discounts Profile-48N", keyfont,Element.ALIGN_LEFT,15,false));
             table9.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));
             table9.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
             for(ZoneGroup zoneGroup:zoneGroupList){
            	 table9.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
             }
             for(int i=0;i<eonomyList.size();i++){
             	WeightBand wd = eonomyList.get(i);
             	table9.addCell(createCell(wd.getName(), textfont));
             	table9.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		table9.addCell(createCell(recDiscountMap.get(keyId)+"", textfont));  
                 }
             }
             document.add(table9);
             
             //价格卡
             PdfPTable table10 = createTable(zoneGroupList.size()+1);  
             table10.addCell(createCell("Discounts Profile-15D/12D/10D/09D", keyfont,Element.ALIGN_LEFT,15,false));
             table10.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));
             for(ZoneGroup zoneGroup:zoneGroupList){
            	 table10.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
             }
             for(int i=0;i<documentList.size();i++){  
             	WeightBand wd = documentList.get(i);
             	table10.addCell(createCell(wd.getName(), textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		if(recDiscountMap.get(keyId)!=null){
             			table10.addCell(createCell(recDiscountMap.get(keyId)*traiffMap.get(keyId)/100+"", textfont)); 
            		}else{
            			table10.addCell(createCell("", textfont));  
            		}
             		 
                 }
             }  
             document.add(table10);
             
             PdfPTable table11 = createTable(zoneGroupList.size()+1);  
             table11.addCell(createCell("Discounts Profile-15N/12N/10N/09N", keyfont,Element.ALIGN_LEFT,15,false));
             table11.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER));  
             for(ZoneGroup zoneGroup:zoneGroupList){
            	 table11.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
             }
             for(int i=0;i<ndocumentList.size();i++){  
             	WeightBand wd = ndocumentList.get(i);
             	table11.addCell(createCell(wd.getName(), textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		if(recDiscountMap.get(keyId)!=null){
             			table11.addCell(createCell(recDiscountMap.get(keyId)*traiffMap.get(keyId)/100+"", textfont));  
            		}else{
            			table11.addCell(createCell("", textfont));  
            		}
                 }
             }  
             document.add(table11);
             
             PdfPTable table12 = createTable(zoneGroupList.size()+1);  
             table12.addCell(createCell("Discounts Profile-48N", keyfont,Element.ALIGN_LEFT,15,false));
             table12.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER));
             for(ZoneGroup zoneGroup:zoneGroupList){
            	 table12.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER));  
             }
             for(int i=0;i<eonomyList.size();i++){
             	WeightBand wd = eonomyList.get(i);
             	table12.addCell(createCell(wd.getName(), textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		if(recDiscountMap.get(keyId)!=null){
             			table12.addCell(createCell(recDiscountMap.get(keyId)*traiffMap.get(keyId)/100+"", textfont));  
            		}else{
            			table12.addCell(createCell("", textfont));  
            		}
                 }
             }
             document.add(table12);
        	 
        }
    	document.newPage();
		Image png1 = Image.getInstance(zoneImage);
		Image png2 = Image.getInstance(zoneImage2);
		Image png3 = Image.getInstance(zoneImage3);
		Image png4 = Image.getInstance(zoneImage4);
		Image png6 = Image.getInstance(zoneImage6);//附加信息
        Image jpg2 = Image.getInstance(oilFee);
	    jpg2.setAlignment(Image.MIDDLE);
	    jpg2.setAlignment(Image.TEXTWRAP);
	    jpg2.scalePercent(percent + 10);
		document.add(jpg2);
		document.newPage();
		png6.setAlignment(Image.MIDDLE);
		png6.setAlignment(Image.TEXTWRAP);
		png6.scalePercent(percent + 10);
		document.add(png6);
		document.newPage();
        png1.setAlignment(Image.MIDDLE);
        png1.setAlignment(Image.TEXTWRAP);
        png1.scalePercent(percent + 10);
        png2.setAlignment(Image.MIDDLE);
        png2.setAlignment(Image.TEXTWRAP);
        png2.scalePercent(percent + 10);
        png3.setAlignment(Image.MIDDLE);
        png3.setAlignment(Image.TEXTWRAP);
        png3.scalePercent(percent + 10);
        png4.setAlignment(Image.MIDDLE);
        png4.setAlignment(Image.TEXTWRAP);
        png4.scalePercent(percent + 10);
        document.newPage();
		document.add(png1);
		document.newPage();
		document.add(png2);
		document.newPage();
		document.add(png3);
		document.newPage();
		document.add(png4);
		document.newPage();
        document.close(); 
        docWriter.close();
		return baosPDF;
     }  
       
     public static void main(String[] args) throws Exception {  
         File file = new File("D:\\text.pdf");  
         file.createNewFile();
//        new PDFGenerater(file).generatePDF();        
    }
      
     private int getPercent2(float h, float w) {
         int p = 0;
         float p2 = 0.0f;
         p2 = 530 / w * 100;
         p = Math.round(p2);
         return p;
     }
}