package org.tnt.pt.service.downPDF;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.entity.TariffGroup;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.util.DoubleUtil;
import org.tnt.pt.util.PTPARAMETERS;
import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders.Color;

import com.itextpdf.text.BaseColor;
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
    
    private static Font keyfont2;// 设置字体大小  
    private static Font keyfont3;// 设置字体大小  
    private static Font keyfont4;// 设置字体大小  
    private static Font textfont;// 设置字体大小  
    private static Font textfont2;// 设置字体大小
    private static java.awt.Color colors = new java.awt.Color(Integer.parseInt("FF9900", 16));
    private static java.awt.Color headColor = new java.awt.Color(Integer.parseInt("FF6500", 16));
    private static java.awt.Color rowColor = new java.awt.Color(Integer.parseInt("C0C0C0", 16));
    static{  
        BaseFont bfChinese;  
        try {  
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);  
            headfont = new Font(bfChinese, 9, Font.BOLD);// 设置字体大小  
            keyfont = new Font(bfChinese, 7, Font.BOLD);// 设置字体大小  
            keyfont2 = new Font(bfChinese, 15, Font.BOLD);// 设置字体大小  
            keyfont3 = new Font(bfChinese, 11, Font.NORMAL);// 设置字体大小  
            keyfont4 = new Font(bfChinese, 9, Font.NORMAL);// 设置字体大小  
            textfont = new Font(bfChinese, 7, Font.NORMAL);// 设置字体大小  
            textfont2 = new Font(bfChinese, 7, Font.NORMAL);// 设置字体大小  
            
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
     
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,java.awt.Color color){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);          
         cell.setHorizontalAlignment(align);      
         cell.setPhrase(new Phrase(value,font)); 
         cell.setBackgroundColor(color);
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
     
     
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan,java.awt.Color color){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));
         cell.setBackgroundColor(color);
        return cell;  
    }
      
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan,boolean boderFlag,java.awt.Color color){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
         cell.setBackgroundColor(color);
         cell.setPadding(0.5f);  
         if(!boderFlag){  
             cell.setBorder(0);  
             cell.setPaddingTop(2.0f);  
             cell.setPaddingBottom(2.0f);  
         }  
        return cell;  
    }
     
    public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan,boolean boderFlag){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);
         cell.setPhrase(new Phrase(value,font));  
         cell.setPadding(0.5f);  
         if(!boderFlag){  
             cell.setBorder(0);  
             cell.setPaddingTop(3.0f);  
             cell.setPaddingBottom(4.0f);  
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
     //pdf价卡导出
     public ByteArrayOutputStream generatePDF(List<ZoneGroup> zoneGroupList,List<Product> productList,Map<String,Double> traiffMapSP,Map<String,Double> traiffMapRP,List<TariffGroup> GdocumentListSP,List<TariffGroup> GndocumentListSP,List<TariffGroup> GeonomyListSP,List<TariffGroup> GdocumentListRP,List<TariffGroup> GndocumentListRP,
			 	List<TariffGroup> GeonomyListRP,Business business,Customer customer,String logoPath,String oilFee,String zoneImage,String zoneImage2,String zoneImage3,String zoneImage4,String zoneImage5,String zoneImage6,String pdfPath,Map<String,Double> HWtraiffMapSP,Map<String,Double> HWtraiffMapRP,
			 	List<String> list1,List<String> list2,List<String> list3,List<String> list4) throws Exception{  
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
    	 tableCUS.addCell(createCell("PT Reference No. "+business.getApplicationReference(), keyfont,Element.ALIGN_LEFT,4,false));
    	 tableCUS.addCell(createCell("Terms of Payment:	SP", keyfont,Element.ALIGN_LEFT,3,false));
    	 document.add(tableCUS);
    	 document.newPage();
        PdfPTable table1 = createTable(zoneGroupList.size()+1);  
        table1.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
        table1.addCell(createCell("page 2", keyfont,Element.ALIGN_RIGHT,1,false));
        
        table1.addCell(createCell("全球快递 - 出口", keyfont2,Element.ALIGN_LEFT,9,false));
        table1.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,3,false));
        table1.addCell(createCell("在下一个或者最近一个可能的工作日结束前派送 ", keyfont3,Element.ALIGN_LEFT,12,false));
        table1.addCell(createCell("• 工作日当日取件", keyfont4,Element.ALIGN_LEFT,12,false));
        table1.addCell(createCell("• 适用于500公斤以下的文件", keyfont4,Element.ALIGN_LEFT,12,false));
        table1.addCell(createCell("• 可选高额责任保障服务和优先服务", keyfont4,Element.ALIGN_LEFT,12,false));
        table1.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        
        table1.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_CENTER,1,false,rowColor));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table1.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,1,false,rowColor));  
        }
        table1.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        
        table1.addCell(createCell("全球快递-文件出口", keyfont,Element.ALIGN_LEFT,15,false,headColor));
        
        for(int i=0;i<GdocumentListSP.size();i++){
        	TariffGroup tg = GdocumentListSP.get(i);
        	if(i%2==0){
        		if(tg.getWeight()<=20){
        			table1.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}else{
        			table1.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
                	table1.addCell(createCell(traiffMapSP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));  
                }
        	}else{
        		if(tg.getWeight()<=20){
        			table1.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false));
        		}else{
        			table1.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
                	table1.addCell(createCell(traiffMapSP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false));  
                }
        	}
        }  
        document.add(table1);
        document.newPage();
        PdfPTable table2 = createTable(zoneGroupList.size()+1);  
        table2.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
        table2.addCell(createCell("page 3", keyfont,Element.ALIGN_RIGHT,1,false));
        
        table2.addCell(createCell("全球快递 - 出口", keyfont2,Element.ALIGN_LEFT,9,false));
        table2.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,3,false));
        table2.addCell(createCell("在下一个或者最近一个可能的工作日结束前派送 ", keyfont3,Element.ALIGN_LEFT,12,false));
        table2.addCell(createCell("• 工作日当日取件", keyfont4,Element.ALIGN_LEFT,12,false));
        table2.addCell(createCell("• 适用于500公斤以下的文件", keyfont4,Element.ALIGN_LEFT,12,false));
        table2.addCell(createCell("• 可选高额责任保障服务和优先服务", keyfont4,Element.ALIGN_LEFT,12,false));
        table2.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table2.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_CENTER,1,false,rowColor));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table2.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,1,false,rowColor));  
        }
        table2.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table2.addCell(createCell("全球快递-非文件出口", keyfont,Element.ALIGN_LEFT,15,false,headColor));
        for(int i=0;i<GndocumentListSP.size();i++){  
        	TariffGroup tg = GndocumentListSP.get(i);
        	if(i%2==0){
        		if(tg.getWeight()<=20){
        			table2.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}else{
        			table2.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table2.addCell(createCell(traiffMapSP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));  
                }
        	}else{
        		if(tg.getWeight()<=20){
        			table2.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false));
        		}else{
        			table2.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table2.addCell(createCell(traiffMapSP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false));  
                }
        	}
        }  
        document.add(table2);
        document.newPage();
        PdfPTable table3 = createTable(zoneGroupList.size()+1);  
        table3.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
        table3.addCell(createCell("page 4", keyfont,Element.ALIGN_RIGHT,1,false));
        
        table3.addCell(createCell("经济快递 - 出口", keyfont2,Element.ALIGN_LEFT,9,false));
        table3.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_LEFT,3,false));
        table3.addCell(createCell("包裹和空运产品的经济定日派送", keyfont3,Element.ALIGN_LEFT,12,false));
        table3.addCell(createCell("• 工作日当日取件", keyfont4,Element.ALIGN_LEFT,12,false));
        table3.addCell(createCell("• 货物最大重量可达7,000公斤", keyfont4,Element.ALIGN_LEFT,12,false));
        table3.addCell(createCell("• 可选高额责任保障服务和优先服务", keyfont4,Element.ALIGN_LEFT,12,false));
        table3.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        
        table3.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_CENTER,1,false,rowColor));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table3.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,1,false,rowColor));  
        }
        table3.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table3.addCell(createCell("经济快递-出口", keyfont,Element.ALIGN_LEFT,15,false,headColor));
        
        for(int i=0;i<GeonomyListSP.size();i++){
        	TariffGroup tg = GeonomyListSP.get(i);
        	if(i%2==0){
        		if(tg.getWeight()<=20){
        			table3.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}else{
        			table3.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), "15N"), textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table3.addCell(createCell(traiffMapSP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));  
                }
        	}else{
        		if(tg.getWeight()<=20){
        			table3.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false));
        		}else{
        			table3.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), "15N"), textfont,Element.ALIGN_CENTER,1,false));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table3.addCell(createCell(traiffMapSP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false));  
                }
        	}
        }
        document.add(table3);
        document.newPage();
        PdfPTable table4 = createTable(zoneGroupList.size()+1); 
        table4.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
        table4.addCell(createCell("page 5", keyfont,Element.ALIGN_RIGHT,1,false));
        
        table4.addCell(createCell("全球快递 - 进口", keyfont2,Element.ALIGN_LEFT,9,false));
        table4.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,3,false));
        table4.addCell(createCell("在下一个或者最近一个可能的工作日结束前派送 ", keyfont3,Element.ALIGN_LEFT,12,false));
        table4.addCell(createCell("• 工作日当日取件", keyfont4,Element.ALIGN_LEFT,12,false));
        table4.addCell(createCell("• 适用于500公斤以下的文件", keyfont4,Element.ALIGN_LEFT,12,false));
        table4.addCell(createCell("• 可选高额责任保障服务和优先服务", keyfont4,Element.ALIGN_LEFT,12,false));
        table4.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table4.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_CENTER,1,false,rowColor));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table4.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,1,false,rowColor));  
        }
        table4.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table4.addCell(createCell("全球快递-文件进口", keyfont,Element.ALIGN_LEFT,15,false,headColor));
        
        for(int i=0;i<GdocumentListRP.size();i++){  
        	TariffGroup tg = GdocumentListRP.get(i);
        	if(i%2==0){
        		if(tg.getWeight()<=20){
        			table4.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}else{
        			table4.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table4.addCell(createCell(traiffMapRP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));  
                }
        	}else{
        		if(tg.getWeight()<=20){
        			table4.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false));
        		}else{
        			table4.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table4.addCell(createCell(traiffMapRP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false));  
                }
        	}
        }  
        document.add(table4);
        document.newPage();
        PdfPTable table5 = createTable(zoneGroupList.size()+1);  
        table5.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
        table5.addCell(createCell("page 6", keyfont,Element.ALIGN_RIGHT,1,false));
       
        table5.addCell(createCell("全球快递 - 进口", keyfont2,Element.ALIGN_LEFT,8,false));
        table5.addCell(createCell("更多详情  800-820-9868    www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,4,false));
        table5.addCell(createCell("在下一个或者最近一个可能的工作日结束前派送 ", keyfont3,Element.ALIGN_LEFT,12,false));
        table5.addCell(createCell("• 工作日当日取件", keyfont4,Element.ALIGN_LEFT,12,false));
        table5.addCell(createCell("• 适用于500公斤以下的文件", keyfont4,Element.ALIGN_LEFT,12,false));
        table5.addCell(createCell("• 可选高额责任保障服务和优先服务", keyfont4,Element.ALIGN_LEFT,12,false));
        table5.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        
        table5.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_CENTER,1,false,rowColor));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table5.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,1,false,rowColor));  
        }
        table5.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table5.addCell(createCell("全球快递-非文件进口", keyfont,Element.ALIGN_LEFT,15,false,headColor));
        
        for(int i=0;i<GndocumentListRP.size();i++){  
        	TariffGroup tg = GndocumentListRP.get(i);
        	if(i%2==0){
        		if(tg.getWeight()<=20){
        			table5.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}else{
        			table5.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table5.addCell(createCell(traiffMapRP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));  
                }
        	}else{
        		if(tg.getWeight()<=20){
        			table5.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false));
        		}else{
        			table5.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), ""), textfont,Element.ALIGN_CENTER,1,false));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table5.addCell(createCell(traiffMapRP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false));  
                }
        	}
        }  
        document.add(table5);
        document.newPage();
        PdfPTable table6 = createTable(zoneGroupList.size()+1);  
        table6.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
        table6.addCell(createCell("page 7", keyfont,Element.ALIGN_LEFT,1,false));
        
        table6.addCell(createCell("经济快递 - 进口", keyfont2,Element.ALIGN_LEFT,9,false));
        table6.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,3,false));
        table6.addCell(createCell("包裹和空运产品的经济定日派送", keyfont3,Element.ALIGN_LEFT,12,false));
        table6.addCell(createCell("• 工作日当日取件", keyfont4,Element.ALIGN_LEFT,12,false));
        table6.addCell(createCell("• 货物最大重量可达7,000公斤", keyfont4,Element.ALIGN_LEFT,12,false));
        table6.addCell(createCell("• 可选高额责任保障服务和优先服务", keyfont4,Element.ALIGN_LEFT,12,false));
        table6.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        
        table6.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_CENTER,1,false,rowColor));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table6.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,1,false,rowColor));  
        }
        table6.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
        table6.addCell(createCell("经济快递-进口", keyfont,Element.ALIGN_LEFT,15,false,headColor));
        
        for(int i=0;i<GeonomyListRP.size();i++){
        	TariffGroup tg = GeonomyListRP.get(i);
        	if(i%2==0){
        		if(tg.getWeight()<=20){
        			table6.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}else{
        			table6.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), "15N"), textfont,Element.ALIGN_CENTER,1,false,rowColor));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table6.addCell(createCell(traiffMapRP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false,rowColor));  
                }
        	}else{
        		if(tg.getWeight()<=20){
        			table6.addCell(createCell(tg.getWeight()+"", textfont,Element.ALIGN_CENTER,1,false));
        		}else{
        			table6.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(), "15N"), textfont,Element.ALIGN_CENTER,1,false));
        		}
            	for(ZoneGroup zoneGroup:zoneGroupList){
            		String keyId = tg.getId()+"_"+zoneGroup.getId();
            		table6.addCell(createCell(traiffMapRP.get(keyId)+"", textfont,Element.ALIGN_CENTER,1,false));  
                }
        	}
        }
        document.add(table6);
    	document.newPage();
    	
    	PdfPTable table7 = createTable(12);  
    	table7.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
    	table7.addCell(createCell("page 8", keyfont,Element.ALIGN_LEFT,1,false));
        if(HWtraiffMapSP.size()>0){
        	table7.addCell(createCell("重货特价-出口", keyfont2,Element.ALIGN_LEFT,9,false));
        	table7.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,3,false));
        	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            if(list1.size()>0){
            	table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	for(String country:list1){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list1.size(),false,rowColor));
            	
            	table7.addCell(createCell("全球快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GndocumentListRP.size();i++){  
                	TariffGroup tg = GndocumentListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list1){
                			String keyString = country+"2_"+DoubleUtil.getWeightBand(tg.getWeight(), "");
                			String rate = HWtraiffMapSP.get(keyString)==null?"0.0":HWtraiffMapSP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list1.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list1.size(),false));
                		}
                	}
                } 
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            }
        	
        	if(list2.size()>0){
        		table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
        		for(String country:list2){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list2.size(),false,rowColor));
            	
            	table7.addCell(createCell("经济快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GeonomyListRP.size();i++){  
                	TariffGroup tg = GeonomyListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list2){
                			String keyString = country+"3_"+DoubleUtil.getWeightBand(tg.getWeight(), "15N");
                			String rate = HWtraiffMapSP.get(keyString)==null?"0.0":HWtraiffMapSP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list2.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list2.size(),false));
                		}
                	}
                }
        	}
        }
        if(HWtraiffMapRP.size()>0){
        	table7.addCell(createCell("重货特价-进口", keyfont2,Element.ALIGN_LEFT,9,false));
        	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            if(list3.size()>0){
            	table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	for(String country:list3){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list3.size(),false,rowColor));
            	
            	table7.addCell(createCell("全球快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GndocumentListRP.size();i++){  
                	TariffGroup tg = GndocumentListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list3){
                			String keyString = country+"2_"+DoubleUtil.getWeightBand(tg.getWeight(), "");
                			String rate = HWtraiffMapRP.get(keyString)==null?"0.0":HWtraiffMapRP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list3.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list3.size(),false));
                		}
                	}
                } 
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            }
        	if(list4.size()>0){
        		table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
        		for(String country:list4){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list4.size(),false,rowColor));
            	
            	table7.addCell(createCell("经济快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GeonomyListRP.size();i++){  
                	TariffGroup tg = GeonomyListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list4){
                			String keyString = country+"3_"+DoubleUtil.getWeightBand(tg.getWeight(), "15N");
                			String rate = HWtraiffMapRP.get(keyString)==null?"0.0":HWtraiffMapRP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list4.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list4.size(),false));
                		}
                	}
                }
        	}
        }
        if(HWtraiffMapRP.size()>0||HWtraiffMapSP.size()>0){
        	document.add(table7);
        	document.newPage();
        }
		Image png1 = Image.getInstance(zoneImage);
		Image png2 = Image.getInstance(zoneImage2);
		Image png3 = Image.getInstance(zoneImage3);
		Image png4 = Image.getInstance(zoneImage4);
		Image png6 = Image.getInstance(zoneImage6);//附加信息
        Image jpg2 = Image.getInstance(oilFee);
	    jpg2.setAlignment(Image.MIDDLE);
	    jpg2.setAlignment(Image.TEXTWRAP);
	    jpg2.scalePercent(percent-5);
		document.add(jpg2);
		document.newPage();
		png6.setAlignment(Image.MIDDLE);
		png6.setAlignment(Image.TEXTWRAP);
		png6.scalePercent(percent);
		document.add(png6);
		document.newPage();
        png1.setAlignment(Image.MIDDLE);
        png1.setAlignment(Image.TEXTWRAP);
        png1.scalePercent(percent-5);
        png2.setAlignment(Image.MIDDLE);
        png2.setAlignment(Image.TEXTWRAP);
        png2.scalePercent(percent-5);
        png3.setAlignment(Image.MIDDLE);
        png3.setAlignment(Image.TEXTWRAP);
        png3.scalePercent(percent-5);
        png4.setAlignment(Image.MIDDLE);
        png4.setAlignment(Image.TEXTWRAP);
        png4.scalePercent(percent-5);
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
     
     //财务需要的价卡导出生成
     public ByteArrayOutputStream generatePDF_C(List<ZoneGroup> zoneGroupList,List<WeightBand> documentList,List<WeightBand> ndocumentList,List<WeightBand> eonomyList,Map<String,Double> discountMap,
    		 Map<String,Double> recDiscountMap,List<Product> productList,Business business,Customer customer,String pdfPath,Map<String,Double> HWtraiffMapSP,Map<String,Double> HWtraiffMapRP,
    		 List<String> list1,List<String> list2,List<String> list3,List<String> list4,List<TariffGroup> GdocumentListSP,List<TariffGroup> GndocumentListSP,List<TariffGroup> GeonomyListSP,
    		 List<TariffGroup> GdocumentListRP,List<TariffGroup> GndocumentListRP,List<TariffGroup> GeonomyListRP) throws Exception{  
    	 ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
    	 // 定义输出位置并把文档对象装入输出对象中
    	 PdfWriter docWriter = null;
    	 docWriter = PdfWriter.getInstance(document, baosPDF);
    	 // 打开文档对象
    	 document.open();
    	 PdfPTable tableIndex = createTable(2);
    	 tableIndex.addCell(createCell("PT Approval Ref. No. "+business.getApplicationReference(), keyfont,Element.ALIGN_LEFT,2,false));
    	 tableIndex.addCell(createCell(" ", keyfont,Element.ALIGN_LEFT,2,false));
    	 tableIndex.addCell(createCell("Customer & Shopping Profile", keyfont,Element.ALIGN_LEFT,2,false));
    	 
    	 document.add(tableIndex);
    	 PdfPTable tableCUS = createTable(2);
    	 tableCUS.addCell(createCell("customer name:    ", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(customer.getCusName(), textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Account number:  ", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(customer.getAccount(), textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Origin Depot:    ", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(business.getDepotCode(), textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Averege Weight Per Consignment:    ", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(" ", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Porjected Monthly Revenue in CNY:", textfont,Element.ALIGN_LEFT));
    	 String ss = business.getTotalRev_S()==null?"":business.getTotalRev_S()+"";
    	 String ss2 = business.getTotalRev_R()==null?"":business.getTotalRev_R()+"";
    	 tableCUS.addCell(createCell(ss+ss2, textfont,Element.ALIGN_LEFT));
    	 
    	 tableCUS.addCell(createCell(" ", textfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Loading Details", textfont,Element.ALIGN_LEFT,3,false));
    	 tableCUS.addCell(createCell("Division:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(" G", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Rate Category:   ", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(" 13PTA", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Approval date:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("<instert date>", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Effective Date:", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("to be confirmed", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("End Date:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("Open", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Review Date:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("Open", textfont,Element.ALIGN_LEFT));
    	 String proStr = "";
    	 for(int i=0;i<productList.size();i++){
          	Product product = productList.get(i);
          	proStr = " " + proStr + product.getProduct() + "    ";
         }
    	 tableCUS.addCell(createCell("Product:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell(""+proStr, textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Option:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("No Discount for Options	", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Currency:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("CNY", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Zoning:	", textfont,Element.ALIGN_LEFT));tableCUS.addCell(createCell("Set Default	", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("Fuel Surcharge：", textfont,Element.ALIGN_LEFT));
    	 tableCUS.addCell(createCell("FSI according to public fuel surcharge", textfont,Element.ALIGN_LEFT));
    	 document.add(tableCUS);
		
		PdfPTable tableDetail = createTable(3);
		tableDetail.addCell(createCell("Terms & Conditions:", keyfont,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("1. PT approval can be loaded to system within 3 months from the commercial approval date and valid no more than 1 year from the PT effective date. The PT discounts will expire after the PT end date and the customer shall be set back to the default full tariff. This practice shall be always followed unless specific approval is given by Commercial.", textfont,Element.ALIGN_LEFT,3,false));
		tableDetail.addCell(createCell("2. The PT effective date is recommended as the date next to the invoice date to avoid 2 sets of rates in the same invoice period. If customer wants to set the PT effective date other than the date next to the invoice date, sales should ensure that customer is aware of the different rates applied in the same invoice period."+
										"PT effective date cannot be earlier than the first day of the current invoice cycle. Since there are several types of invoice cycles,such as TNT Calendar or every 25th of the month, the actual effective date will be subject to Billing's approval. No traceback is allowed."
										+"The PT start date cannot be Tuesday as no loading work can be done on this day of the week.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("3. If sales want to renew or review the PT discounts after the PT expires, a new PT request shall be raised to Commercial at least 2 weeks before the PT end date.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("4. Quarterly review of the actual shipping profile, achievement of revenue target and gross margin should be conducted. If actual performance significantly differs from that stipulated in the PT application, PT agreement with the customer should be amended / stopped.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("5. The consignments should follow the weight (per piece and per consignment) and volume restrictions of the respective product, otherwise specific approval should be obtained from Commercial.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("6. Fuel surcharge is NOT exemptible / deductible whether or not the customer is currently on fuel surcharge exemption or deduction, unless specifically approved by Commercial in the PT approval.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("7. All goods are carried subject to TNT Express Terms and Conditions of Carriage, copies of which are available upon request.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("8. When the volumetric weight exceeds the actual weight , the volumetric weight is applied to determine the consignment cost. The volumetric weight is calculated as follows: (Length X Height X Width) in m³ and multiply this by 167. Weight determined by TNT is considered final.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("9. Maximum Dimensions:", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("9:00 Express & 10:00 Express >>Maximums: 30 kg/piece, 210 kg/con, 3m³/con, 1.00 x 0.60 x 0.70 (m)", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("12:00 Express >>Maximums: 30 kg/piece, 500 kg/con, 3m³/con, 1.00 x 0.60 x 0.70 (m)", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("Express >>Maximums: 30 kg/piece, 500 kg/con, 3m³/con, 1.00 x 0.60 x 0.70 (m)", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("Economy Express >>Maximums: 70 kg/piece, 500 kg/con, 3m³/con, 1.00 x 0.60 x 0.70 (m)", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("For consignment over 500kgs, additional charges may apply. Please call out Customer Service Hotline at 8008209868 (mobile phone users) or 4008209868 (fixed phone user) for details.", textfont2,Element.ALIGN_LEFT,3));
		tableDetail.addCell(createCell("*The above rates are subject to fuel surcharge. Please refer to TNT China website for prevailinh fuel surcharge precentage.", textfont2,Element.ALIGN_LEFT,3));
   	 	document.add(tableDetail);
   	 	document.newPage();
        PdfPTable table1 = createTable(zoneGroupList.size()*2+3);  
        if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
        	table1.addCell(createCell("Terms of Payment:	SP", keyfont,Element.ALIGN_LEFT,25,false));
    	}else if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
    		table1.addCell(createCell("Terms of Payment:	SP/RP", keyfont,Element.ALIGN_LEFT,25,false));
    	}else{
    		table1.addCell(createCell("Terms of Payment:	"+customer.getPayment(), keyfont,Element.ALIGN_LEFT,25,false));
    	}
        table1.addCell(createCell("Discounts Profile-15D/12D/10D/09D", keyfont,Element.ALIGN_LEFT,25,false));
        
        table1.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER,colors));
        table1.addCell(createCell("Inc", keyfont,Element.ALIGN_CENTER,colors));
        table1.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER,colors));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table1.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,2,colors));  
        }
        table1.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        table1.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        table1.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table1.addCell(createCell("Base", keyfont,Element.ALIGN_CENTER,colors));  
        	table1.addCell(createCell("Int", keyfont,Element.ALIGN_CENTER,colors));  
        }
        for(int i=0;i<documentList.size();i++){  
        	WeightBand wd = documentList.get(i);
        	table1.addCell(createCell(wd.getName(), textfont));
        	table1.addCell(createCell(wd.getAddOnWeightInt()+"", textfont));
        	table1.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
            	table1.addCell(createCell(wd.getType().equals("base")?discountMap.get(keyId)+"%":"", textfont));
            	table1.addCell(createCell(discountMap.get(keyId)+"%", textfont));
            }
        }  
        document.add(table1);
        
        PdfPTable table2 = createTable(zoneGroupList.size()*2+3);  
        table2.addCell(createCell("Discounts Profile-15N/12N/10N/09N", keyfont,Element.ALIGN_LEFT,25,false));
        table2.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER,colors)); 
        table2.addCell(createCell("Inc", keyfont,Element.ALIGN_CENTER,colors));
        table2.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER,colors));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table2.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,2,colors));  
        }
        table2.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        table2.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        table2.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table2.addCell(createCell("Base", keyfont,Element.ALIGN_CENTER,colors));  
        	table2.addCell(createCell("Int", keyfont,Element.ALIGN_CENTER,colors));  
        }
        for(int i=0;i<ndocumentList.size();i++){  
        	WeightBand wd = ndocumentList.get(i);
        	table2.addCell(createCell(wd.getName(), textfont));
        	table2.addCell(createCell(wd.getAddOnWeightInt()+"", textfont));
        	table2.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
        		table2.addCell(createCell(wd.getType().equals("base")?discountMap.get(keyId)+"%":"", textfont));  
        		table2.addCell(createCell(discountMap.get(keyId)+"%", textfont));
            }
        }  
        document.add(table2);
        
        PdfPTable table3 = createTable(zoneGroupList.size()*2+3);  
        table3.addCell(createCell("Discounts Profile-48N", keyfont,Element.ALIGN_LEFT,25,false));
        table3.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER,colors));
        table3.addCell(createCell("Inc", keyfont,Element.ALIGN_CENTER,colors));
        table3.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER,colors));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table3.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,2,colors));  
        }
        table3.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        table3.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        table3.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
        for(ZoneGroup zoneGroup:zoneGroupList){
        	table3.addCell(createCell("Base", keyfont,Element.ALIGN_CENTER,colors));  
        	table3.addCell(createCell("Int", keyfont,Element.ALIGN_CENTER,colors));  
        }
        for(int i=0;i<eonomyList.size();i++){  
        	WeightBand wd = eonomyList.get(i);
        	table3.addCell(createCell(wd.getName(), textfont));
        	table3.addCell(createCell(wd.getAddOnWeightInt()+"", textfont));
        	table3.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
        	for(ZoneGroup zoneGroup:zoneGroupList){
        		String keyId = wd.getId()+"_"+zoneGroup.getId();
        		table3.addCell(createCell(wd.getType().equals("base")?discountMap.get(keyId)+"%":"", textfont));
        		table3.addCell(createCell(discountMap.get(keyId)+"%", textfont));
            }
        }
        document.add(table3);
        
        //如果是both  则一个rp  一个sp
        if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){
        	 PdfPTable tableRP = createTable(3);
        	 tableRP.addCell(createCell("Terms of Payment:	RP", keyfont,Element.ALIGN_LEFT,3,false));
        	 document.add(tableRP);
        	 PdfPTable table4 = createTable(zoneGroupList.size()*2+3);  
        	 table4.addCell(createCell("Discounts Profile-15D/12D/10D/09D", keyfont,Element.ALIGN_LEFT,25,false));
             
             table4.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER,colors));
             table4.addCell(createCell("Inc", keyfont,Element.ALIGN_CENTER,colors));
             table4.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER,colors));
             for(ZoneGroup zoneGroup:zoneGroupList){
             	table4.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,2,colors));  
             }
             table4.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             table4.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             table4.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             for(ZoneGroup zoneGroup:zoneGroupList){
             	table4.addCell(createCell("Base", keyfont,Element.ALIGN_CENTER,colors));  
             	table4.addCell(createCell("Int", keyfont,Element.ALIGN_CENTER,colors));  
             }
             for(int i=0;i<documentList.size();i++){  
             	WeightBand wd = documentList.get(i);
             	table4.addCell(createCell(wd.getName(), textfont));
             	table4.addCell(createCell(wd.getAddOnWeightInt()+"", textfont));
             	table4.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
                 	table4.addCell(createCell(wd.getType().equals("base")?recDiscountMap.get(keyId)+"%":"", textfont));
                 	table4.addCell(createCell(recDiscountMap.get(keyId)+"%", textfont));
                 }
             }  
             document.add(table4);
             
             PdfPTable table5 = createTable(zoneGroupList.size()*2+3);  
             table5.addCell(createCell("Discounts Profile-15N/12N/10N/09N", keyfont,Element.ALIGN_LEFT,25,false));
             table5.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER,colors)); 
             table5.addCell(createCell("Inc", keyfont,Element.ALIGN_CENTER,colors));
             table5.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER,colors));
             for(ZoneGroup zoneGroup:zoneGroupList){
             	table5.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,2,colors));  
             }
             table5.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             table5.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             table5.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             for(ZoneGroup zoneGroup:zoneGroupList){
             	table5.addCell(createCell("Base", keyfont,Element.ALIGN_CENTER,colors));  
             	table5.addCell(createCell("Int", keyfont,Element.ALIGN_CENTER,colors));  
             }
             for(int i=0;i<ndocumentList.size();i++){  
             	WeightBand wd = ndocumentList.get(i);
             	table5.addCell(createCell(wd.getName(), textfont));
             	table5.addCell(createCell(wd.getAddOnWeightInt()+"", textfont));
             	table5.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		table5.addCell(createCell(wd.getType().equals("base")?recDiscountMap.get(keyId)+"%":"", textfont));  
             		table5.addCell(createCell(recDiscountMap.get(keyId)+"%", textfont));
                 }
             }  
             document.add(table5);
             
             PdfPTable table6 = createTable(zoneGroupList.size()*2+3);  
             table6.addCell(createCell("Discounts Profile-48N", keyfont,Element.ALIGN_LEFT,25,false));
             table6.addCell(createCell("Weightband", keyfont,Element.ALIGN_CENTER,colors));
             table6.addCell(createCell("Inc", keyfont,Element.ALIGN_CENTER,colors));
             table6.addCell(createCell("Add-On", keyfont,Element.ALIGN_CENTER,colors));
             for(ZoneGroup zoneGroup:zoneGroupList){
             	table6.addCell(createCell(zoneGroup.getZone(), keyfont,Element.ALIGN_CENTER,2,colors));  
             }
             table6.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             table6.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             table6.addCell(createCell(" ", keyfont,Element.ALIGN_CENTER,colors));
             for(ZoneGroup zoneGroup:zoneGroupList){
             	table6.addCell(createCell("Base", keyfont,Element.ALIGN_CENTER,colors));  
             	table6.addCell(createCell("Int", keyfont,Element.ALIGN_CENTER,colors));  
             }
             for(int i=0;i<eonomyList.size();i++){  
             	WeightBand wd = eonomyList.get(i);
             	table6.addCell(createCell(wd.getName(), textfont));
             	table6.addCell(createCell(wd.getAddOnWeightInt()+"", textfont));
             	table6.addCell(createCell(wd.getType().equals("base")?"N":"Y", textfont));
             	for(ZoneGroup zoneGroup:zoneGroupList){
             		String keyId = wd.getId()+"_"+zoneGroup.getId();
             		table6.addCell(createCell(wd.getType().equals("base")?recDiscountMap.get(keyId)+"%":"", textfont));
             		table6.addCell(createCell(recDiscountMap.get(keyId)+"%", textfont));
                 }
             }
             document.add(table6);
     	}     
    	document.newPage();
    	
    	PdfPTable table7 = createTable(12);  
    	table7.addCell(createCell("2014国际快递费率及服务指南 | 中国大陆地区", textfont,Element.ALIGN_LEFT,11,false));
    	table7.addCell(createCell("page 8", keyfont,Element.ALIGN_LEFT,1,false));
        if(HWtraiffMapSP.size()>0){
        	table7.addCell(createCell("重货特价-出口", keyfont2,Element.ALIGN_LEFT,9,false));
        	table7.addCell(createCell("更多详情  800-820-9868     www.tnt.com/express/zh_cn", keyfont4,Element.ALIGN_RIGHT,3,false));
        	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            if(list1.size()>0){
            	table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	for(String country:list1){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list1.size(),false,rowColor));
            	
            	table7.addCell(createCell("全球快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GndocumentListRP.size();i++){  
                	TariffGroup tg = GndocumentListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list1){
                			String keyString = country+"2_"+DoubleUtil.getWeightBand(tg.getWeight(), "");
                			String rate = HWtraiffMapSP.get(keyString)==null?"0.0":HWtraiffMapSP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list1.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list1.size(),false));
                		}
                	}
                } 
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            }
        	
        	if(list2.size()>0){
        		table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
        		for(String country:list2){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list2.size(),false,rowColor));
            	
            	table7.addCell(createCell("经济快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GeonomyListRP.size();i++){  
                	TariffGroup tg = GeonomyListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list2){
                			String keyString = country+"3_"+DoubleUtil.getWeightBand(tg.getWeight(), "");
                			String rate = HWtraiffMapSP.get(keyString)==null?"0.0":HWtraiffMapSP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list2.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list2.size(),false));
                		}
                	}
                }
        	}
        }
        if(HWtraiffMapRP.size()>0){
        	table7.addCell(createCell("重货特价-进口", keyfont2,Element.ALIGN_LEFT,9,false));
        	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            if(list3.size()>0){
            	table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	for(String country:list3){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list3.size(),false,rowColor));
            	
            	table7.addCell(createCell("全球快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GndocumentListRP.size();i++){  
                	TariffGroup tg = GndocumentListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"15N")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list3){
                			String keyString = country+"2_"+DoubleUtil.getWeightBand(tg.getWeight(), "");
                			String rate = HWtraiffMapRP.get(keyString)==null?"0.0":HWtraiffMapRP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list3.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list3.size(),false));
                		}
                	}
                } 
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,15,false));
            }
        	if(list4.size()>0){
        		table7.addCell(createCell("重量/公斤", keyfont,Element.ALIGN_LEFT,1,false,rowColor));
        		for(String country:list4){
            		table7.addCell(createCell(country, keyfont,Element.ALIGN_LEFT,1,false,rowColor));
            	}
            	table7.addCell(createCell("", keyfont,Element.ALIGN_LEFT,12-list4.size(),false,rowColor));
            	
            	table7.addCell(createCell("经济快递", keyfont,Element.ALIGN_LEFT,12,false,headColor));
            	table7.addCell(createCell("以下为每公斤单价", keyfont,Element.ALIGN_LEFT,12,false,rowColor));
            	for(int i=0;i<GeonomyListRP.size();i++){  
                	TariffGroup tg = GeonomyListRP.get(i);
                	if(tg.getWeight()>20){
                		if(i%2==0){
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false,rowColor));
                		}else{
                			table7.addCell(createCell(DoubleUtil.getWeightBand(tg.getWeight(),"")+"", textfont,Element.ALIGN_LEFT,1,false));
                		}
                		for(String country:list4){
                			String keyString = country+"3_"+DoubleUtil.getWeightBand(tg.getWeight(), "");
                			String rate = HWtraiffMapRP.get(keyString)==null?"0.0":HWtraiffMapRP.get(keyString)+"";
                			if(i%2==0){
            	        		if(tg.getWeight()>20){
            	        			table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false,rowColor));
            	        			}
            		        	}else{
            		        		table7.addCell(createCell(rate, textfont,Element.ALIGN_LEFT,1,false));
            		        	}
            	        	}
                		if(i%2==0){
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list4.size(),false,rowColor));
                		}else{
                			table7.addCell(createCell("", textfont,Element.ALIGN_LEFT,12-list4.size(),false));
                		}
                	}
                }
        	}
        }
        if(HWtraiffMapRP.size()>0||HWtraiffMapSP.size()>0){
        	document.add(table7);
        	document.newPage();
        }
        document.close(); 
        docWriter.close();
		return baosPDF;
     }
     
     private int getPercent2(float h, float w) {
         int p = 0;
         float p2 = 0.0f;
         p2 = 530 / w * 100;
         p = Math.round(p2);
         return p;
     }
     
}