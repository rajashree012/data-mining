import java.io.*;
import java.util.*;
class modifiedkmeans
{
	int k;
	List<centroid> centroids=new ArrayList<centroid>();
	void input(List<points> dots1)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter number of clusters");
		k=sc.nextInt();
		int X=0,Y=0;
		System.out.println("calculating the initial coordinates");
		double minx=dots1.get(0).x_coord,miny=dots1.get(0).y_coord,maxx=0,maxy=0;
		for(int i=0;i<dots1.size();i++)
		{
			if(dots1.get(i).x_coord<minx)
			minx=dots1.get(i).x_coord;
			if(dots1.get(i).y_coord<miny)
			miny=dots1.get(i).y_coord;
			if(dots1.get(i).x_coord>maxx)
			maxx=dots1.get(i).x_coord;
			if(dots1.get(i).y_coord>maxy)
			maxy=dots1.get(i).y_coord;
		}
		double rangex=(maxx-minx)/k;
		double rangey=(maxy-miny)/k;
		int frequency[][]=new int[k][k];
		for(int i=0;i<k;i++)
		{
			for(int j=0;j<k;j++)
			{
				frequency[i][j]=0;
			}
		}
		for(int i=0;i<dots1.size();i++)
		{
			X=(int)(Math.floor((dots1.get(i).x_coord-minx)/(maxx-minx)));
			Y=(int)(Math.floor((dots1.get(i).y_coord-miny)/(maxy-miny)));
			if(X>=k)
			X=X-1;
			if(Y>=k)
			Y=Y-1;
			System.out.println(X+" "+Y);
			frequency[X][Y]++;
			dots1.get(i).modifiedclusterno=X*10+Y;
		}
		int maximum=0,XX=0,YY=0;
		double sumXX=0,sumYY=0;
		for(int h=0;h<k;h++)
		{
			maximum=0;
			sumXX=0;sumYY=0;
			XX=0;YY=0;
			for(int i=0;i<k;i++)
			{
				for(int j=0;j<k;j++)
				{
					if(frequency[i][j]>maximum)
					{maximum=frequency[i][j];XX=i;YY=j;}
				}
			}
			for(int i=0;i<dots1.size();i++)
			{
				if(dots1.get(i).modifiedclusterno==XX*10+YY)
				{sumXX=sumXX+(dots1.get(i)).x_coord;sumYY=sumYY+(dots1.get(i)).y_coord;}
			}
			centroid c1=new centroid();
			c1.serial=h+1;
			c1.xcoord=sumXX/frequency[XX][YY];
			c1.ycoord=sumYY/frequency[XX][YY];
			c1.xcoordprev=0;
			c1.ycoordprev=0;
			centroids.add(c1);
			System.out.println(c1.xcoord+"@@@@@@@@"+c1.ycoord);
			frequency[XX][YY]=0;
		}			
	}
	void cluster(List<points> dots1)
	{
		double min=0,dist=0,mindist=9999,distance=0;
		int k=0;
		for(int i=0;i<centroids.size();i++)
		{
			mindist=9999;
			for(int j=0;j<centroids.size();j++)
			{
			if(i!=j)
{distance=Math.sqrt(Math.pow(((centroids.get(j)).xcoord-(centroids.get(i)).xcoord),2)+Math.pow(((centroids.get(j)).ycoord- (centroids.get(i)).ycoord),2));
				if(mindist>(distance/2))
				mindist=distance/2;
				System.out.println(distance+"*******");
			}}
			centroids.get(i).threshold=mindist;
			System.out.println(mindist+"#########");
		}
		for(int i=0;i<dots1.size();i++)
		{
			int j;
			for(j=0;j<centroids.size();j++)
			{
dist=Math.sqrt(Math.pow(((centroids.get(j)).xcoord-(dots1.get(i)).x_coord),2)+Math.pow(((centroids.get(j)).ycoord- (dots1.get(i)).y_coord),2));
			if(dist<centroids.get(j).threshold)
			{
				k=j+1;
				break;
			}
			}
			if(j>=centroids.size())
			{
				//for(int a=0;a<dots1.size();a++)
				//{
					for(int b=0;b<centroids.size();b++)
					{
						if(b==0)
						{
min=Math.sqrt(Math.pow(((centroids.get(b)).xcoord-(dots1.get(i)).x_coord),2)+Math.pow(((centroids.get(b)).ycoord- (dots1.get(i)).y_coord),2));
						k=1;      
						}
						if(b>0)
						{
dist=Math.sqrt(Math.pow(((centroids.get(b)).xcoord-(dots1.get(i)).x_coord),2)+Math.pow(((centroids.get(b)).ycoord- (dots1.get(i)).y_coord),2)); 
							if(min>dist)
							{min=dist;k=b+1;}     
						}                                                                                           
					}
				//}
			}
			
			(dots1.get(i)).clusterno=k;
			System.out.println(k);
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
		modifiedkmeans k1=new modifiedkmeans();
		database db=new database();
		List<points> dots1=db.dots;
		db.connect();
		k1.input(dots1);
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