package strcmppkg;

import java.util.Random;


public class CMPTest {
	
	
	public static void main(String[] args) {
		int x=1000;
		int y=1000;
		byte list[][] = new byte[x][y];
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<x;i++)
			for(int j=0;j<y;j++){
				list[i][j] = (byte)('A' + rand.nextInt(26));
				//if(i==0)
				//System.out.print((char)list[i][j]);
			}
		
		timeit(0,list);
		timeit(1,list);
		timeit(2,list);
	}
	
	public static void timeit(int i,byte list[][]){
		long st = System.currentTimeMillis();
		if(i==0) cmp(list);
		else if(i==1) cmp_duo(list);
		else if(i==2) cmp_quad(list);
		long et = System.currentTimeMillis();
		
		System.out.println("("+i+")Time="+((double)(et-st)/1000));
	}

	public static int cmp(byte list[][]){
		int result=0;
		for(int i=0;i<list.length;i++){
			for(int j=0;i!=j && j<list.length;j++){
				for(int k=0;k<list[i].length;){
					result = list[i][k] - list[j][k];
					k++;
					if(k<list[i].length){
						result = list[i][k] - list[j][k];
						k++;
						if(k<list[i].length){
							result = list[i][k] - list[j][k];
							k++;
							if(k<list[i].length){
								result = list[i][k] - list[j][k];
								k++;
								if(k<list[i].length){
									result = list[i][k] - list[j][k];
									k++;
									if(k<list[i].length){
										result = list[i][k] - list[j][k];
										k++;
										if(k<list[i].length){
											result = list[i][k] - list[j][k];
											k++;
											if(k<list[i].length){
												result = list[i][k] - list[j][k];
												k++;
												if(k<list[i].length){
													result = list[i][k] - list[j][k];
													k++;
													if(k<list[i].length){
														result = list[i][k] - list[j][k];
														k++;
														if(k<list[i].length){
															result = list[i][k] - list[j][k];
															k++;
															if(k<list[i].length){
																result = list[i][k] - list[j][k];
																k++;
																if(k<list[i].length){
																	result = list[i][k] - list[j][k];
																	k++;
																	if(k<list[i].length){
																		result = list[i][k] - list[j][k];
																		k++;
																		if(k<list[i].length){
																			result = list[i][k] - list[j][k];
																			k++;
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					//if(result!=0) return result;
				}
			}
		}
		return result;
	}
	
	public static int cmp_duo(byte list[][]){
		int result=0;
		//int listlen = list.length/2;
		for(int i=0;i<list.length;i++){
			for(int j=0;i!=j && j<list.length;j++){
				for(int k=0;k<list[i].length;k+=2){
					int c1 = list[i][k]<<8 | list[i][k+1];
					int c2 = list[j][k]<<8 | list[j][k+1];
					result = c1-c2;
				}
			}
		}
		return result;
	}
	
	public static int cmp_quad(byte list[][]){
		int result=0;
		//int listlen = list.length/2;
		for(int i=0;i<list.length;i++){
			for(int j=0;i!=j && j<list.length;j++){
				for(int k=0;k<list[i].length;k+=4){
					int c1 = list[i][k]<<24 | list[i][k+1]<<16 | list[i][k+2]<<8 | list[i][k+3];
					int c2 = list[j][k]<<24 | list[j][k+1]<<16 | list[j][k+2]<<8 | list[j][k+3];
					result = c1-c2;
				}
			}
		}
		return result;
	}
}
