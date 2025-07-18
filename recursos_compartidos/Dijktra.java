import java.util.*;
public class dijks
 {
		int costo[];
		int camino[];
		boolean visitado[];
		int[][]graf;
 
 
    public dijks(int nver)
    {
    	this.costo=new int[nver];
    	this.camino=new int[nver];
    	this.visitado=new boolean[nver];
    	this.graf=new int [nver][nver];
 
    	visitado[0]=true;
		camino[0]=1;
		costo[0]=0;
    }
 
	public void leetecl(int nver)
	{
		int nady, ady, peso;
		Scanner valor= new Scanner(System.in);
		for(int i=0;i<nver;i++)
		{
			System.out.print("\n De el numero de vertices adyacentes al vertice "+(i+1)+" :");
		    nady=valor.nextInt();
		   	while(nady !=0)
		   	{
		   		System.out.print("\n   Da vertice adyacente al vertce "+(i+1)+": ");
		   		ady=valor.nextInt()-1;
		        System.out.print("  Da el peso de esta arista: ");
		        peso=valor.nextInt();
		        graf[i][ady]=peso;
		        nady--;
		   	}
		}
	}
 
    public void infin(int nver)
	{
		for(int i=0;i<nver;i++)
		   for(int j=0;j<nver;j++)
		   	   if(graf[i][j]==0)
		   	         graf[i][j]=10000;
	}
 
    public int indi(int nver)
    {
		int aux=10000;
		int in=0;
		for(int i=1;i<nver;i++)
			{
			if(visitado[i]==false)
			   if(costo[i]<aux)
			   	{
			   	aux=costo[i];
			   	in=i;
			    }
		     }
		return in;
	}
 
	public void dijks1(int nver)
	{
		int i;
		int j=0;
		int ind=0;
		for(i=1;i<nver;i++)
		{
			visitado[i]=false;
			costo[i]=graf[0][i];
			camino[i]=0;
		}
 
		for(i=0;i<nver;i++)
			{
			ind=indi(nver);
			if(ind!=0)
				{
				visitado[ind]=true;
				for(j=0;j<nver;j++)
					{
					if(graf[ind][j]!=10000)
					   if(visitado[j]==false)
					      if((costo[ind]+graf[ind][j])<costo[j])
					      	{
					      	costo[j]=costo[ind]+graf[ind][j];
					      	camino[j]=ind;
					        }
					}
				}
			}
	}
 
	public void camin(int ver)
	{
		if(ver!=0)
		camin(camino[ver]);
 
		System.out.print(" "+(ver+1)+",");
	}
 
	public void Imprime(int nver)
	{
		System.out.println("\n\n  * Direccion y costo de caminos minimos * \n\n");
		for(int i=1;i<nver;i++)
			{
			System.out.print("\t De 1 a "+(i+1)+" =");
			camin(i);
			System.out.print(" = "+costo[i]+"\n");
			//System.out.println("\n");
			}
	}
 
	public static void main(String args[])
	{
		int nver;
    	Scanner valores = new Scanner(System.in);
 
		System.out.print("  De el numero de vertices del Grafo: ");
		nver=valores.nextInt();
 
 
		dijks grafo=new dijks(nver);
 
		grafo.leetecl(nver);
		grafo.infin(nver);
		grafo.dijks1(nver);
		grafo.Imprime(nver);
 
	}
 
}
