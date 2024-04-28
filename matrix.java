import java.util.*;
import java.io.*;
import java.lang.*;

public class matrix
{
	public matrix()
	{
		File files = new File("matrixs.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(files));
			String str;
			while( (str=input.readLine())!=null)
			{

				String [] pieces = str.split("\t");
				int[][] matrix1 =null;
				int[][] matrix2 =null;

				for(int i = 0;i<2;i++)
				{
					pieces[i] = pieces[i].substring(2,pieces[i].length()-2);

					//},{
					String[] rows= pieces[i].split("},\\{");
					if(i==0)
					{
						matrix1=new int[rows.length][];
					}
					else
					matrix2 = new int[rows.length][];

					for(int r = 0; r<rows.length;r++)
					{
						String[] rowSt= rows[r].split(",");
						int[] nums = new int [rowSt.length];
						for(int n = 0; n<rowSt.length;n++)
						{
								nums[n]=Integer.parseInt(rowSt[n]);
						}
						if(i==0)
							matrix1[r]=nums;
						else
							matrix2[r]=nums;
					}
				}
				displayMatrix(matrix1);
					System.out.println();
				displayMatrix(matrix2);
				add(matrix1,matrix2);
				subtract(matrix1,matrix2);
				multiply(matrix1,matrix2);
				division(matrix1,matrix2);
			}

		}

		catch(IOException e)
		{
					System.out.print("Error");
		}
	}

	public void displayMatrix(int [][] mat)
	{
		for(int r= 0 ;r<mat.length; r++)
		{
			for(int c = 0; c< mat[0].length;c++)
			{
				System.out.print(mat[r][c]+" ");
			}
			System.out.println();
		}

	}
	public void add(int[][] mat1, int [][] mat2)
	{

		if(mat1.length==mat2.length && mat1[0].length == mat2[0].length)
			{
			int [][] result = new int[mat1.length][mat1[0].length];

			for(int r= 0 ;r<mat1.length; r++)
			{
				for(int c = 0; c< mat1[0].length;c++)
				{
					result[r][c]=mat1[r][c]+mat2[r][c];
				}
			}
			System.out.println();
					displayMatrix(result);

		}
		else
		System.out.println("Matracies cannot be added.");

	}

	public void subtract(int[][] mat1, int [][] mat2)
	{

		if(mat1.length==mat2.length && mat1[0].length == mat2[0].length)
			{
			int [][] result = new int[mat1.length][mat1[0].length];

			for(int r= 0 ;r<mat1.length; r++)
			{
				for(int c = 0; c< mat1[0].length;c++)
				{
					result[r][c]=mat1[r][c]-mat2[r][c];
				}
			}
			System.out.println();
					displayMatrix(result);

		}
		else
		System.out.println("Matracies cannot be added.");

	}
	public void multiply(int[][] mat1, int [][] mat2)
		{

			if(mat1.length==mat2.length && mat1[0].length == mat2[0].length)
				{
				int [][] result = new int[mat1.length][mat1[0].length];


					for(int r= 0 ;r<mat1.length; r++)
					{
						for(int c = 0; c< mat1[0].length;c++)
						{
							int sum=0;
							for(int c1= 0 ;c1<mat1[0].length; c1++)
							{
							sum+=mat1[r][c1]*mat2[c1][c];
							}
							result[r][c]=sum;
						}
					}
				System.out.println();
						displayMatrix(result);

			}
			else
			System.out.println("Matracies cannot be added.");

	}
	public void division(int[][] mat1, int [][] mat2)
			{

				if(mat1.length==mat2.length && mat1[0].length == mat2[0].length)
					{
					int [][] result = new int[mat1.length][mat1[0].length];


						for(int r= 0 ;r<mat1.length; r++)
						{
							for(int c = 0; c< mat1[0].length;c++)
							{
								int sum=0;
								for(int c1= 0 ;c1<mat1[0].length; c1++)
								{
								sum+=mat1[r][c1]/mat2[c1][c];
								}
								result[r][c]=sum;
							}
						}
					System.out.println();
					displayMatrix(result);
				}
				else
				System.out.println("Matracies cannot be added.");

	}


	public static void main(String[] args)
			{
					matrix apps=new matrix();
		}
}