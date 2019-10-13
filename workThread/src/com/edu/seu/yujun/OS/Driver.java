package com.edu.seu.yujun.OS;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

public class Driver {

	/**
	 * @param args
	 */
	public final static int M=1024;//���峣��������A������
	public final static int K=1024;//���峣��������A������������B������
	public final static int N=1024;//���峣��������B������
	final static int NUM_THREADS=2;//���峣�����߳�����
	private static int [][]A;//����A
	private static int [][]B;//����B
	private static int [][]C;//����C
	public Driver(){
		A=new int[M][K];
		B=new int[K][N];
		C=new int[M][N];//A��B��C��ʼ��
		fillRandom(A);//��0-99���������ʼ������A
		fillRandom(B);//��0-99���������ʼ������B
		for(int i=0;i<M;i++)
			for(int j=0;j<N;j++)
				C[i][j]=0;//��C����ȫ����	
	}
	private void fillRandom(int[][] A){
		for(int i=0;i<A.length;i++){
			for(int j=0;j<A[i].length;j++)
				A[i][j]=(int)(Math.random()*100);
		}
		
		
	}
	
	public static void singleThread(){
		for(int i=0;i<M;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<K;k++)
					C[i][j]+=A[i][k]*B[k][j];
			}
		}
		
		
	}
	
	
	public static void main(String[] args) {
		new Driver();
		int []rol=new int[3];
		int []col=new int[3];
		for(int i=0;i<rol.length;i++){
			rol[i]=(int)(Math.random()*M);
			col[i]=(int)(Math.random()*N);
		}
		Thread[] workers=new Thread[NUM_THREADS];
		for(int i=0;i<NUM_THREADS;i++)
			workers[i]=new Thread(new workThread(i,A,B,C));
		long time1= System.currentTimeMillis();
		for(int i=0;i<NUM_THREADS;i++){
			workers[i].start();
			
		}
		for(int i=0;i<NUM_THREADS;i++){
			try{
				workers[i].join();
			}catch(InterruptedException e){
				
				e.printStackTrace();
			}
			
			
		}
		long time2=System.currentTimeMillis();//��¼����ʱ��
		System.out.println("����["+M+","+K+"]��["+K+","+N+"]�׾���˷�,����("+NUM_THREADS+"�߳�)��ʱ:"+(time2-time1)+"����");
		System.out.println(C[rol[0]][col[0]]+" "+C[rol[1]][col[1]]+" "+C[rol[2]][col[2]]);
		System.out.println();
		
		//������������Ҳ����ֱ�ӽ�������
		for(int i=0;i<M;i++)
			for(int j=0;j<N;j++)
				C[i][j]=0;//��C����ȫ����	
		long time3=System.currentTimeMillis();//��¼��ʼʱ��
		singleThread();//���ô��м��㺯��
		long time4=System.currentTimeMillis();//��¼����ʱ��
		System.out.println("����["+M+","+K+"]��["+K+","+N+"]�׾���˷�,ֱ�Ӽ�����ʱ:"+(time4-time3)+"����");
		System.out.println(C[rol[0]][col[0]]+" "+C[rol[1]][col[1]]+" "+C[rol[2]][col[2]]);
		System.out.println();
		//��������ʹ���̳߳ط�����������
		for(int i=0;i<M;i++)
			for(int j=0;j<N;j++)
				C[i][j]=0;//��C����ȫ����	
		//�����ĸ������߳�
		Thread []poolThreads=new Thread[NUM_THREADS];
		for(int i=0;i<NUM_THREADS;i++)
			poolThreads[i]=new Thread(new workThread(i,A,B,C));
		//�����̳߳�
		ExecutorService pool = Executors.newCachedThreadPool();
		long time5=System.currentTimeMillis();//��¼��ʼʱ��
		for(int i=0;i<NUM_THREADS;i++)
			pool.execute(poolThreads[i]);//���ĸ������̷߳����̳߳���ִ��
		pool.shutdown();//���̳߳���ֹǰ����ִ����ǰ�ύ������
		while (true) {  
			if (pool.isTerminated()) {   
				 break;  
			}
		}
		long time6=System.currentTimeMillis();//��¼����ʱ��
		System.out.println("����["+M+","+K+"]��["+K+","+N+"]�׾���˷�,�̳߳ؼ�����ʱ:"+(time6-time5)+"����");
		System.out.println(C[rol[0]][col[0]]+" "+C[rol[1]][col[1]]+" "+C[rol[2]][col[2]]);
	}

}
