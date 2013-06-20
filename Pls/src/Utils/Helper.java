package Utils;

public class Helper {
	
	public static int[][] cloneMatrix (int[][] mat){
		int [][] ret = new int[mat.length][];
		for(int i = 0; i < mat.length; i++)
			ret[i] = mat[i].clone();
		return ret;
	}

}
