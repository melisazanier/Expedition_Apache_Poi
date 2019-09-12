import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Map {
    private int newx,newy,valBefore=0;
    
	public static int[][] mapMatrix= new int [30][30];
	public static int[][] trace= new int [30][30];
	
    public int[] up= {0,-1,1,0,0};
    public int[] down= {0,0,0,-1,1};

    public static int count1, count2,succes=0,start1,start2,finish1,finish2;

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Here I read from file and print the matrix in fine and on console~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void readMap()throws Exception
	{
		@SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook();
		workbook = WorkbookFactory.create(new File ("readMap.xlsx"));  		
	    final Sheet sheet = workbook.getSheetAt(0);
   
	    for(int rowN =sheet.getFirstRowNum();rowN <= sheet.getLastRowNum();rowN++)
	    { 
	    	final Row row = sheet.getRow(rowN);
	    	count1=sheet.getLastRowNum();
	    	count2=row.getLastCellNum();
	    	
			for (int col = row.getFirstCellNum(); col < row.getLastCellNum(); col++) 
			 {
				@SuppressWarnings("deprecation")
				final Cell cell = row.getCell(col, Row.RETURN_BLANK_AS_NULL);
				mapMatrix[rowN][col]=(int) cell.getNumericCellValue();
	          }			
	     }

		workbook.close();
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void writeMap() throws FileNotFoundException, IOException
	{
		final Workbook workbook = new HSSFWorkbook();
		final Sheet sheet = workbook.createSheet("Trace");
		
		
		for(int i=0;i<count1;i++)
		{  
			HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
			final Row row = sheet.createRow(i);
		    HSSFCell cell;
			style.setFillForegroundColor(HSSFColor.LIME.index);
		    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    HSSFFont font = (HSSFFont) workbook.createFont();
		    font.setColor(HSSFColor.ORANGE.index);
		    style.setFont(font);

		    
			for(int j=0;j<count2;j++)
			{ 
				if(trace[i][j]!=0)
		        {
		             cell = (HSSFCell) row.createCell(j);
		             cell.setCellValue(trace[i][j]);
		             cell.setCellStyle(style);
		        }
				else
				{
		        cell = (HSSFCell) row.createCell(j);
		        cell.setCellValue(trace[i][j]);
				}
		        
				
			}
		}
		try (FileOutputStream  out = new FileOutputStream("Excursionist'sTrace.xls")) {
	        workbook.write(out);
	    }
	
         workbook.close();

	}
	
	
	
	public void printInitialMatrix() throws Exception
	{
	    for(int i=0;i<count1;i++)
	    {
	      for(int j=0 ;j<count2;j++)
	    	  System.out.print(mapMatrix[i][j] + " ");
	      System.out.println();
	    }
	}	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Where the excursionist start and end ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void getTheFinishPoint(int count1, int count2)
	{

		  finish1=0;
		  System.out.println("Finish 1 ="+finish1);
		  
		  finish2=0;
		  System.out.println("Finish 2 =" +finish2);
		  
		  //System.out.println(mapMatrix[finish1][finish2]);
		 
	}	
	
	
	public void getTheStartPoint(int count1, int count2) throws Exception
	{
		  Random rand = new Random();
		  
		  start1 = rand.nextInt(count1) + 0;
		  System.out.println("start 1 ="+start1);
		  
		  start2 = rand.nextInt(count2) + 0;
		  System.out.println("Start 2 ="+start2);
		  
		  //System.out.println(mapMatrix[start1][start2]);
		  getTheFinishPoint(Map.count1,Map.count2);
	}
	
	
	public void printI() throws Exception
	{
	    for(int i=0;i<count1;i++)
	    {
	      for(int j=0 ;j<count2;j++)
	    	  System.out.print(trace[i][j] + " ");
	      System.out.println();
	    }
	}	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~The backtraking part~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public int solution(int start1, int start2, int finish1, int finish2)
	{
		if((start1==finish1) && (start2==finish2))
			return 1;
		return 0;
	}
	
	public void coverTheMatrix(int k) throws Exception
	{  
		trace[start1][start2]=k;
		
	   for(int i = 0 ; i < 4 ; i ++)
		   {
		      valBefore=mapMatrix[start1][start2];
		      
			   newx =start1+up[i];
		       newy =start2+down[i];
		      
		       if(Excursionist.valid(newx, newy,valBefore,start1,start2) == 1)
	            {  
		    	   start1=newx;
	               start2=newy;
	               
	    	        if(solution(start1, start2, finish1, finish2) == 1)
	    	        {
	    	        	System.out.println("Am reusit !!!");
	    	        	
	    	            succes=1;
	    	            trace[start1][start2]=k+1;
	    	            printI();
	    	            break;
	    	        }
	    	        
	    	      if(k<count1)
	    	    	  
	    	        this.coverTheMatrix(k+1);
	    	      
	    	      if(i==4)      trace[start1-up[i]][start2-down[i]]=0;
	    	    	  
	            } 
		       
		       else if(i==4) 	  trace[start1-up[i]][start2-down[i]]=0;
 
		   }
	}
}










