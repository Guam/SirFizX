package fizxFP.managers;

import fizxFP.managers.items.MysqlConnect;
import java.io.IOException;

/*
 * @author SirFizX
 */
public class MysqlManager {
    
    private MysqlConnect con;
    
    public MysqlManager(){
        con = new MysqlConnect();
    }
    
    public String GetRndData(String tableName){
        String rndVal=null;
        return rndVal;
    }
    
    public String GetConStatus() throws IOException{
        return con.getNameFromDb();//con.getConStatus();
    }
    
}
