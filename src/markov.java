import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;


public class markov {

	public static HashMap<String, Integer> hm1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

		ATNT();
		Physics();
		Yeast();

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


	}

	public static double[][] selfloop(double[][] arr)
	{
		int row= arr.length;
		int col=arr[0].length;

		//double[][] mat= new double[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(i==j)
				{
					arr[i][j]=1.0;
				}
			}
		}
		return arr;
	}

	public static double[][] norm(double[][] arr)
	{
		int row= arr.length;
		int col=arr[0].length;


		for(int i=0;i<col;i++)
		{
			double count=0;
			for(int j=0;j<row;j++)
			{
				//if(arr[j][i]==1.0)
				count+=arr[j][i];
			}

			for(int j=0;j<row;j++)
			{
				//if(arr[j][i]==1.0)
				//{
				arr[j][i] =arr[j][i]/count;
				//}

			}

		}

		return arr;
	}

	public static double[][] expand(double[][] arr1,double[][] arr2)
	{
		int r1 = arr1.length;
		int c1 = arr1[0].length;
		int r2 = arr2.length;
		int c2 = arr2[0].length;

		double[][] mat = new double[r1][c2];

		for (int i = 0; i < r1; i++) { // aRow
			for (int j = 0; j < c2; j++) { // bColumn
				for (int k = 0; k < c1; k++) { // aColumn
					mat[i][j] += arr1[i][k] * arr2[k][j];
				}
			}
		}

		return mat;

	}

	public static double[][] inflate(double[][] arr ,double r)
	{
		int row= arr.length;
		int col=arr[0].length;

		double[][] inflateMat= new double[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				inflateMat[i][j] = Math.pow(arr[i][j],r);
			}
		}

		return  inflateMat;//norm(inflateMat);

	}

	public static double[][] copy(double[][] arr)
	{
		return arr;
	}

	public static boolean IsSame(double[][] arr1,double[][] arr2)
	{
		int rows1 = arr1.length;
		int cols1 = arr1[0].length;
		int rows2 = arr2.length;
		int cols2 = arr2[0].length;
		if((rows1==rows2) && (cols1==cols2)){
			for(int i = 0; i<rows1;i++){
				for(int j=0;j<cols2;j++){
					if(arr1[i][j] != arr2[i][j]){
						return false;
					}
				}
			}
		}

		return true;
	}

	public static void printMatrix(double[][] doubleMatrix1){
		int rows = doubleMatrix1.length;
		int cols = doubleMatrix1[0].length;
		for(int i = 0; i<rows;i++){
			for(int j=0;j<cols;j++){

				System.out.print(doubleMatrix1[i][j] + " ");
			}
			System.out.println("");
		}


	}

	public static double[][] readAtnt()
	{

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("attweb_net.txt"));

			String line = null;
			Set<Integer> set = new TreeSet<Integer>();
			while ((line = br.readLine()) != null){
				String[] values = line.split(" ");
				for(int i=0;i<values.length;i++){
					set.add(Integer.parseInt(values[i]));
				}
			}

			Integer max= Integer.MIN_VALUE;
			for (Integer integer : set) {
				if(max<integer)
					max=integer;
			}

			Integer count = max+1;
			double[][] atntMatrix = new double[count][count];


			br = new BufferedReader(new FileReader("attweb_net.txt"));
			line = null;

			while ((line = br.readLine()) != null)
			{
				String[] values = line.split(" ");
				int index1 = Integer.parseInt(values[0]);
				int index2 = Integer.parseInt(values[1]);
				atntMatrix[index1][index2] = 1.0;
				atntMatrix[index2][index1]=1.0;
			}
			return atntMatrix;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static double[][] MarkovsAlgo(double[][] inputMat,double r)
	{
		double[][] selfloop= selfloop(inputMat);
		//System.out.println("Printing Self loop Matrix :");
		//printMatrix(selfloop);
		//System.out.println();
		//System.out.println();
		double[][] assoNorm =norm(selfloop);

		//System.out.println("Printing Normalized Matrix :");
		//printMatrix(assoNorm);
		//System.out.println();
		//System.out.println();
		double[][] expandMat = expand(assoNorm, assoNorm);

		//System.out.println("Printing Expanded Matrix :");
		//printMatrix(expandMat);
		//System.out.println();
		//System.out.println();

		double[][] inflateMat = inflate(expandMat,r);

		//System.out.println("Printing inflateed Matrix :");
		//printMatrix(inflateMat);
		//System.out.println();
		//System.out.println();

		double[][]  inflate_norm= norm(inflateMat);
		//System.out.println("Printing normalized inflateed Matrix :");
		//printMatrix(inflate_norm);
		//System.out.println();
		//System.out.println();

		double[][] oldinflate= new double[inflateMat.length][inflateMat[0].length];
		int count1=1;
		do
		{
			//System.out.println("----------------In While--------------------"+count1++);

			oldinflate= copy(inflate_norm);
			//System.out.println("Printing old inflate Matrix :");
			//printMatrix(oldinflate);
			//System.out.println();
			//System.out.println();

			expandMat= expand(oldinflate, oldinflate);
			//System.out.println("Printing Expanded Matrix :");
			//printMatrix(expandMat);
			//System.out.println();
			//System.out.println();

			inflateMat=inflate(expandMat,r);
			//System.out.println("Printing inflateed Matrix :");
			//printMatrix(inflateMat);
			//System.out.println();
			//System.out.println();

			inflate_norm= norm(inflateMat);

			//System.out.println("Printing normalized inflateed Matrix :");
			//printMatrix(inflate_norm);
			//System.out.println();
			//System.out.println();

		}while(IsSame(inflate_norm, oldinflate)==false);


		//System.out.println("Printing final inflateed Matrix :");
		//printMatrix(inflate_norm);

		return inflate_norm;
	}

	public static double[][] readPhy(String fileName)
	{
		hm1= new HashMap<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));

			String line = null;

			int index=0;
			while ((line = br.readLine()) != null){
				String[] values = line.split(" ");
				for(int i=0;i<values.length;i++){
					//set.add(Integer.parseInt(values[i]));

					if(!hm1.containsKey(values[i]))
					{
						hm1.put(values[i], index++);
					}

				}
			}

			double[][] phyMat= new double[hm1.size()][hm1.size()];

			HashMap<String, ArrayList<String>> hm2 = new HashMap<>();
			br = new BufferedReader(new FileReader(fileName));
			line = null;

			while ((line = br.readLine()) != null)
			{
				String[] values = line.split(" ");

				if(hm2.containsKey(values[0]))
				{
					ArrayList<String> a= new ArrayList<>();
					a=hm2.get(values[0]);
					a.add(values[1]);
					hm2.put(values[0],a );
				}
				else
				{
					ArrayList<String> a= new ArrayList<>();
					a.add(values[1]);
					hm2.put(values[0], a);
				}

			}

			for (String key:hm2.keySet()) {

				ArrayList<String> al= new ArrayList<>();
				al=hm2.get(key);
				for (String string : al) {

					phyMat[hm1.get(key)][hm1.get(string)]=1.0;
					phyMat[hm1.get(string)][hm1.get(key)]=1.0;

				}
			}

			System.out.println();
			return phyMat;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static double[][] readYeast(String fileName)
	{
		hm1= new HashMap<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));

			String line = null;

			int index=0;
			while ((line = br.readLine()) != null){
				String[] values = line.split("\t");
				for(int i=0;i<values.length;i++){
					//set.add(Integer.parseInt(values[i]));

					if(!hm1.containsKey(values[i]))
					{
						hm1.put(values[i], index++);
					}

				}
			}

			double[][] phyMat= new double[hm1.size()][hm1.size()];

			HashMap<String, ArrayList<String>> hm2 = new HashMap<>();
			br = new BufferedReader(new FileReader(fileName));
			line = null;

			while ((line = br.readLine()) != null)
			{
				String[] values = line.split("\t");

				if(hm2.containsKey(values[0]))
				{
					ArrayList<String> a= new ArrayList<>();
					a=hm2.get(values[0]);
					a.add(values[1]);
					hm2.put(values[0],a );
				}
				else
				{
					ArrayList<String> a= new ArrayList<>();
					a.add(values[1]);
					hm2.put(values[0], a);
				}

			}

			for (String key:hm2.keySet()) {

				ArrayList<String> al= new ArrayList<>();
				al=hm2.get(key);
				for (String string : al) {

					phyMat[hm1.get(key)][hm1.get(string)]=1.0;
					phyMat[hm1.get(string)][hm1.get(key)]=1.0;

				}
			}

			System.out.println();
			return phyMat;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static LinkedHashMap<Integer, LinkedHashSet<Integer>> interpreteCluster(double[][] arr)
	{
		LinkedHashMap<Integer, Boolean> attractor = new LinkedHashMap<Integer, Boolean>();

		// HashMap<Integer, ArrayList<Integer>> clusters = new HashMap<Integer, ArrayList<Integer>>();

		LinkedHashMap<Integer, LinkedHashSet<Integer>> clusters = new LinkedHashMap<Integer, LinkedHashSet<Integer>>();

		int row= arr.length;
		int col=arr[0].length;



		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(i==j && arr[i][j]!=0)
				{
					attractor.put(i, false);
				}
			}
		}

		int cluster_no = 0;
		for (Map.Entry<Integer, Boolean> entry : attractor.entrySet())
		{
			if(entry.getValue()==false)
			{
				attractor.put(entry.getKey(),true);
				int col_index = entry.getKey();
				LinkedHashSet<Integer> hs = new LinkedHashSet<Integer>();
				for(int i=0;i<row;i++)
				{
					if(arr[i][col_index]!=0)
					{
						hs.add(i);
						if(attractor.containsKey(i))
						{
							//hs.add(i);
							attractor.put(i, true);
						}
					}
				}
				clusters.put(++cluster_no, hs);
			}
		}


		//System.out.println("******************   " + clusters.size());

		LinkedHashMap<Integer, LinkedHashSet<Integer>> final_cluster= new LinkedHashMap<Integer, LinkedHashSet<Integer>>();
		for (Entry<Integer, LinkedHashSet<Integer>> entry : clusters.entrySet())
		{

			LinkedHashSet<Integer> al= entry.getValue();

			LinkedHashSet<Integer> al_new = new LinkedHashSet<Integer>();

			for (Integer integer : al) 
			{
				al_new.add(integer);

				for(int j=0;j<col;j++)
				{
					if(arr[integer][j] != 0)
					{
						al_new.add(j);
					}
				}
			}

			final_cluster.put(entry.getKey(), al_new);
		}

		return final_cluster;
	}

	public static void writeATNTDataToclu(LinkedHashMap<Integer, LinkedHashSet<Integer>> final_cluster)
	{
		int[] clu= new int[180];
		//System.out.println("******************   " + final_cluster.size());
		for (Entry<Integer, LinkedHashSet<Integer>> entry : final_cluster.entrySet())
		{

			System.out.println("Cluster Number-------   "+entry.getKey()+"-----------");
			LinkedHashSet<Integer> al= entry.getValue();

			for (Integer integer : al) 
			{
				System.out.print("   "+integer);
				clu[integer]=entry.getKey();
			}

			System.out.println();
		}





		try{
			BufferedReader br = new BufferedReader(new FileReader("attweb_net.net"));
			String line = null;
			LinkedHashMap<Integer,Integer> set = new LinkedHashMap<>();
			while ((line = br.readLine()) != null){

				String[] values = line.split("\"");
				set.put(Integer.valueOf(values[1]), clu[Integer.valueOf(values[1])]);
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter("att.clu"));
			String str = "*Vertices"+" "+180;
			writer.write(str);
			writer.newLine();


			for (Integer node : set.keySet()) {
				Integer clusternumber= set.get(node);
				writer.write(clusternumber.toString());
				writer.newLine();
			}


			writer.flush();
			writer.close();
		}
		catch (IOException e){
			System.out.println("IOException : " + e);
		}
	}

	public static void writeCLU(LinkedHashMap<Integer, LinkedHashSet<Integer>> final_cluster, String dotnetFileName,String fileName)
	{
		int[] clu= new int[hm1.size()];

		//System.out.println("******************   " + final_cluster.size());
		for (Entry<Integer, LinkedHashSet<Integer>> entry : final_cluster.entrySet())
		{

			System.out.println("Cluster Number-------   "+entry.getKey()+"-----------");
			LinkedHashSet<Integer> al= entry.getValue();

			for (Integer integer : al) 
			{
				System.out.print("   "+integer);
				clu[integer]=entry.getKey();
			}

			System.out.println();
		}





		try{

			BufferedReader br = new BufferedReader(new FileReader(dotnetFileName));
			String line = null;
			LinkedHashMap<String ,Integer> set = new LinkedHashMap<>();
			while ((line = br.readLine()) != null){

				String[] values = line.split("\"");


				set.put(values[1], clu[hm1.get(values[1])]);
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			String str = "*Vertices"+" "+hm1.size();
			writer.write(str);
			writer.newLine();


			for (String node : set.keySet()) {
				Integer clusternumber= set.get(node);
				writer.write(clusternumber.toString());
				writer.newLine();
			}


			writer.flush();
			writer.close();
		}
		catch (IOException e){
			System.out.println("IOException : " + e);
		}


	}

	public static void ATNT()
	{
		System.out.println("******************ATNT Clusters******************");
		double[][] atntMatrix = readAtnt();
		double[][] finalOutputMat= MarkovsAlgo(atntMatrix,1.35);
		writeATNTDataToclu(interpreteCluster(finalOutputMat));

	}

	public static void Physics()
	{
		System.out.println();
		System.out.println("******************Physics Clusters******************");
		double[][] phyMat= readPhy("physics_collaboration_net.txt");
		double[][] finalOutputMat= MarkovsAlgo(phyMat,1.27);
		//writeATNTDataToclu(interpreteCluster(finalOutputMat));
		writeCLU(interpreteCluster(finalOutputMat), "physics_collaboration_net.net", "Phy.clu");

	}
	
	public static void Yeast()
	{
		System.out.println();
		System.out.println("******************Yeast Clusters******************");
		double[][] phyMat= readYeast("yeast_undirected_metabolic.txt");
		double[][] finalOutputMat= MarkovsAlgo(phyMat,1.205);
		//writeATNTDataToclu(interpreteCluster(finalOutputMat));
		writeCLU(interpreteCluster(finalOutputMat), "yeast_undirected_metabolic.net", "yeast.clu");

	}


}