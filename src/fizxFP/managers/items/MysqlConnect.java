package fizxFP.managers.items;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 *
 * @author SirFizX
 */
public class MysqlConnect{
    
  private String s = null;
  
  public MysqlConnect()  {
    
  }
  
  public String getConStatus(){
      String status=s;
      return status;
  }
  
  	public String getNameFromDb() throws IOException //the pid and ver are parameters I want to POST results from
		{ 
		String success = "false";
		
		String parametersAsString = "var1=" + 3 + "&var2=" + 4;		 

		byte[] parameterAsBytes = parametersAsString.getBytes(); 

		URL url = new URL("http://sirfizx.x10.mx/apps/java/test2/php/sendToApplet.php");
		
		URLConnection con = url.openConnection(); 
		((HttpURLConnection) con).setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
 
		OutputStream oStream = con.getOutputStream(); 
		oStream.write(parameterAsBytes); 
		oStream.flush(); 

		BufferedReader iStream = new BufferedReader(new InputStreamReader(con.getInputStream()));
 
		String aLine = iStream.readLine();
                return aLine;
//		while(aLine != null) 
//		{
//                    success = aLine;
//			if(aLine.contains("success"))
//				success = "true";
//			if(aLine.equals("")) break;
//				aLine = iStream.readLine();
//		}
//		iStream.close(); 
//		oStream.close(); 
//		  
//		return success;
		  
	}

  
  
}
