import java.io.*;
import java.util.*;
class kmeans
{
	int k;
	List<centroid> centroids=new ArrayList<centroid>();
	void input()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter number of clusters");
		k=sc.nextInt();
		System.out.println("enter coordinates of initial centroids");
		for(int i=0;i<k;i++)
		{
			centroid c1=new centroid();
			c1.serial=i+1;
			c1.xcoord=sc.nextDouble();
			c1.ycoord=sc.nextDouble();
			c1.xcoordprev=0;
			c1.ycoordprev=0;
			centroids.add(c1);
		}
	}
	void cluster(List<points> dots1)
	{
		double min=0,dist=0;
		int k=0;
		for(int i=0;i<dots1.size();i++)
		{
			for(int j=0;j<centroids.size();j++)
			{
				if(j==0)
				{
min=Math.sqrt(Math.pow(((centroids.get(j)).xcoord-(dots1.get(i)).x_coord),2)+Math.pow(((centroids.get(j)).ycoord- (dots1.get(i)).y_coord),2));
				k=1;      
		                }
				if(j>0)
				{
dist=Math.sqrt(Math.pow(((centroids.get(j)).xcoord-(dots1.get(i)).x_coord),2)+Math.pow(((centroids.get(j)).ycoord- (dots1.get(i)).y_coord),2)); 
				if(min>dist)
				{min=dist;k=j+1;}     
		                }                                                                                            
			}
			(dots1.get(i)).clusterno=k;
		}
	}
	void calculate(List<points> dots1)
	{
		double sumx=0,sumy=0;int count=0;
		for(int j=0;j<centroids.size();j++)
		{
			sumx=0;sumy=0;count=0;
			for(int i=0;i<dots1.size();i++)
			{
				if((dots1.get(i)).clusterno==j+1)
				{sumx=sumx+(dots1.get(i)).x_coord;sumy=sumy+(dots1.get(i)).y_coord;count++;}
			}
			(centroids.get(j)).xcoordprev=(centroids.get(j)).xcoord;	
			(centroids.get(j)).ycoordprev=(centroids.get(j)).ycoord;
			(centroids.get(j)).xcoord=sumx/count;
			(centroids.get(j)).ycoord=sumy/count;
		}				
	}		
	public static void main(String args[])
	{
		boolean flag=false;
		int count=0;
		double dist1=0;
		kmeans k1=new kmeans();
		k1.input();
		database db=new database();
		List<points> dots1=db.dots;
		db.connect();
		k1.cluster(dots1);
		while(!flag)
		{
			k1.calculate(dots1);
			count=0;
			for(int j=0;j<k1.centroids.size();j++)
			{
dist1=Math.sqrt(Math.pow(((k1.centroids.get(j)).xcoord-(k1.centroids.get(j)).xcoordprev),2)+ Math.pow(((k1.centroids.get(j)).ycoord-(k1.centroids.get(j)).ycoordprev),2));
				if(dist1<0.1)
				count++;
			}
			if(count>=3*Math.floor(k1.k/4))
			flag=true;
			k1.cluster(dots1);
		}
		for(int i=0;i<dots1.size();i++)
		System.out.println(dots1.get(i).slno+" "+dots1.get(i).clusterno);				
	}
}