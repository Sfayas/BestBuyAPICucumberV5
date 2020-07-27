package utilities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import cucumber.api.java.After;


public class ExcelFunctions {
	public static String xlfilepath;
	public static FileInputStream fis =null;
	public FileOutputStream fos =null;
	public static XSSFWorkbook workbook = null;
	public static XSSFSheet sheet =null;
	public static XSSFRow row =null;
	public static XSSFCell cell =null;
	
	
	public  ExcelFunctions(String filepath) throws Exception {
		
			xlfilepath=filepath;
			fis =new FileInputStream(xlfilepath);
			workbook = new XSSFWorkbook(fis);
					
		
	}
	
	public  String readCellData(int rownum,int columnno) {
		
		String cellValue="";
		
		row = sheet.getRow(rownum);
		cell = row.getCell(columnno);
		if(cell!= null) {
		 switch (cell.getCellType()) {
		 
	         case Cell.CELL_TYPE_STRING:
	             cellValue=cell.getRichStringCellValue().getString().trim();
	             break;
	             
	         case Cell.CELL_TYPE_NUMERIC:
	        	 
	             if (DateUtil.isCellDateFormatted(cell)) {
	            	 cellValue =String.valueOf(new DataFormatter().formatCellValue((Cell) cell.getDateCellValue())).trim();
	             } 
	             
	             else {
	            	 cell.setCellType(Cell.CELL_TYPE_STRING);
	            	 cellValue =cell.getRichStringCellValue().getString().trim();
	             }
	             break;
	             
	          case Cell.CELL_TYPE_FORMULA:
	        	  cellValue =String.valueOf(cell.getCellFormula()).trim();
	             break;
	             
	             
	          case Cell.CELL_TYPE_BLANK:
	        	  
	             break;
	             
	           default:
	        	   break;
		 	}
		}	 
		
		
		return cellValue;
	}
	
	public  int getRowCount(String sheetName) {
		
		int rowcount;
		sheet=workbook.getSheet(sheetName);
		 rowcount =sheet.getLastRowNum()+1;		
		 return rowcount;
	}
	
	public  int getColumnCount() {
			
		return sheet.getRow(0).getLastCellNum();		
			
	}
	
	public int findRowNo(int rowcount, String rowValue) {
		
		int rowNo=0;
		
		for(int rn=0;rn<rowcount;rn++){
		    Row temproryrow=sheet.getRow(rn);
		    
		    if (temproryrow == null) {
		      // This row is all blank, skip
		      continue;
		    }
		    
		    //for(int cn=0;cn<temproryrow.getLastCellNum();cn++){
		    	
		        Cell cell=temproryrow.getCell(0);
		        if (cell == null) {
		        	continue;
		           // No cell here
		        } 
		        
		        else {
		           try{ 
		               
		               if(cell.getStringCellValue().equals(rowValue)){
		            	   rowNo=rn;
		                  break;
		               }
		               
		           }catch(Exception e){
		              continue;
		           }
		        }
		   // }
		    if(!(rowNo==0)) {
		    	break;
		    }
		    
		}
		
		return rowNo;
		
	}
	public  int findColumnNo(int columncount,String TSName) {
		
		int columnNo;
		boolean findColumn= false;
		row=sheet.getRow(1);
		for (columnNo=1;columnNo<columncount;columnNo++) {
			cell= row.getCell(columnNo);
					
			if(TSName.equals(cell.getStringCellValue().trim())) {
				findColumn= true;
				break;
			}
		}
		
		if (findColumn) {
			return columnNo;
		}
		else {
			return 0;
		}
	}
	
	@After
	public void afterCalss() throws Exception {
		 fis.close();
	}
	
	
}

