package com.edu.seu.yujun.OS;
/**
 * 
 * @author 15052
 * 
 *
 */
public class workThread implements Runnable {
	
	private int start;
	private int [][]A;
	private int [][]B;
	private int [][]C;
	public workThread(int start,int [][]A,int [][]B,int [][]C){
		this.start=start;
		this.A=A;
		this.B=B;
		this.C=C;
	}
	
	public void run() {
		int i,j,k;
		for(i=start; i<Driver.M; i +=Driver.NUM_THREADS){
			for(j=0;j<Driver.N;j++) {
				
				for( k=0; k< Driver.K;k++)
					C[i][j]+=A[i][k]*B[k][j]; 
				
			}
			
			
		}
		
	}
	
}
