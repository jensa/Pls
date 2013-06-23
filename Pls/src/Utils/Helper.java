package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
	
	public static int[][] cloneMatrix (int[][] mat){
		int [][] ret = new int[mat.length][];
		for(int i = 0; i < mat.length; i++)
			ret[i] = mat[i].clone();
		return ret;
	}
	
	public static void log (String msg){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)+": "+msg);
	}

}
