package indexing;


	import java.io.*;

	import java.util.Scanner;
	import java.util.StringTokenizer;

	public class pri {
	private demo1[] sI = new demo1[100];
		
	    private String prikey,id,ProductId,UserId,ProfileName,Ratings,Time,Reviews;
		int reccount = 0;
	    int reccount1=0;
	public void getData(){
	    		@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the primary key: ");
			prikey = scanner.next();
			System.out.println("Enter the Id: ");
			id = scanner.next();
			System.out.println("Enter the ProductId: ");
			ProductId = scanner.next();
			System.out.println("Enter the UserId: ");
			UserId = scanner.next();
			System.out.println("Enter the ProfileName: ");
			ProfileName = scanner.next();
			System.out.println("Enter the Ratings: ");
			Ratings = scanner.next();
			System.out.println("Enter the Time: ");
			Time= scanner.next();
			
	}
	public void add(){
	        String data=prikey +","+  id +","+ ProductId +","+ UserId +","+ ProfileName +","+ Ratings+","+Time+","+Reviews+"";

	 try{			
				RandomAccessFile recordfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
				recordfile.seek(recordfile.length());
				long pos = recordfile.getFilePointer();
				recordfile.writeBytes(data+"\n");
				recordfile.close();
				
				RandomAccessFile indexfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\primary.txt","rw");
				indexfile.seek(indexfile.length());
				indexfile.writeBytes(prikey+","+pos+"\n");
				indexfile.close();
				
				RandomAccessFile indexfile1 = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\secondary.txt","rw");
				indexfile1.seek(indexfile1.length());
				indexfile1.writeBytes(id+","+pos+"\n");
				indexfile1.close();
			}
			catch(IOException e){
				System.out.println(e);
			}
			
	 
	}                     
	    @SuppressWarnings("resource")
		public void priIndex(){

			String line,prikey = null,pos = null;
			int count = 0;
			int sIIndex = 0;
			reccount=0;
			RandomAccessFile indexfile;
			try {
				indexfile = new RandomAccessFile("C:\\Users\\Sneha\\Desktop\\primary.txt","rw");

				try {
					
					while((line = indexfile.readLine())!= null){
	                                     if(line.contains("*")) {
		                		continue;
		                	}
						count = 0;
	                                                 
	                                       
		          
						
						StringTokenizer st = new StringTokenizer(line,",");
						while (st.hasMoreTokens()){
						 count+=1;
						 if(count==1)
					    prikey = st.nextToken();
						 pos = st.nextToken();
	                                     
					    }
						sI[sIIndex] = new demo1();
						sI[sIIndex].setRecPos(pos);
						sI[sIIndex].setprikey(prikey);
						reccount++;
						sIIndex++;
	                                        if(sIIndex==100)
	                                        {
	                                            break;
	                                        }
	                                }
				} catch (IOException e) {
					
					e.printStackTrace();
					return;
				}
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				return;
			} 
			
			System.out.println("total records" + reccount);
			if (reccount==1) { return;}
			sortIndex();
		}
	  
		public void sortIndex() {
			demo1 temp = new demo1();
			
			for(int i=0; i<reccount; i++)
			    {	
					for(int j=i+1; j<reccount; j++) {
						if(sI[i].getprikey().compareTo(sI[j].getprikey())  > 0)
			            {
			                temp.setprikey(sI[i].getprikey()); 
					        temp.setRecPos(sI[i].getRecPos());
					
				        	sI[i].setprikey(sI[j].getprikey());
				        	sI[i].setRecPos(sI[j].getRecPos());
					
				        	sI[j].setprikey(temp.getprikey());
				        	sI[j].setRecPos(temp.getRecPos());
			            }
					}
						
				}	
			
		}
		
	        public void search(){
	            System.out.println("Enter the primary key to search: ");
						Scanner scanner = new Scanner(System.in);
						String prikey = scanner.next();
						System.out.println(reccount);
						int pos = binarySearch(sI, 0, reccount-1, prikey);
	                                        
						
						if (pos == -1) {
							System.out.println("Record not found in the record file");
							return ;
						}
						
						RandomAccessFile recordfile;
						try {
							recordfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
							try {
								recordfile.seek(Long.parseLong(sI[pos].getRecPos()));
								String record = recordfile.readLine();
								StringTokenizer st = new StringTokenizer(record,",");
								
								int count = 0;
	                                                        
			               	    
			                	while (st.hasMoreTokens()){
			                		     count+=1;
			                  	    	 if(count==1){
			                  	    	 String tmp_prikey = st.nextToken();
	                                                 if(tmp_prikey.contains("*"))
	                                                 {
	                                                     System.out.println("it has been deleted");
	                                                     break;
	                                                 }
							System.out.println("prikey: "+tmp_prikey);
			                  	         this.prikey = tmp_prikey;
			                  	    	
			                  	          String tmp_value = st.nextToken();
			                     	      System.out.println("id: "+tmp_value);
			                     	      this.id = tmp_value;
			                     	       
			                     	       String tmp_product = st.nextToken();
			                     	       System.out.println("ProductId: "+tmp_product);
			                     	       this.ProductId = tmp_product;
			                  	    	 
			                     	        String tmp_user = st.nextToken();
			                     	        System.out.println("UserId: "+tmp_user);
			                     	        this.UserId = tmp_user;
			                     	      
			                     	        String tmp_Name = st.nextToken();
			                     	        System.out.println("ProfileName: "+tmp_Name);
			                     	        this.ProfileName = tmp_Name;
			                     	     
			                     	        String tmp_Score = st.nextToken();
			                     	        System.out.println("Ratings: "+tmp_Score);
			                     	        this.Ratings = tmp_Score;
			                  	    	 	System.out.println();
			                  	    	 	
			                  	    	 	String tmp_tme = st.nextToken();
			                     	        System.out.println("Time: "+tmp_tme);
			                     	        this.Time = tmp_tme;
			                  	    	 	System.out.println();
			                  	    	 	
			                  	    	 	String tmp_desp = st.nextToken();
			                     	        System.out.println("Reviews: "+tmp_desp);
			                     	        this.Reviews = tmp_desp;
			                  	    	 	System.out.println();
			                  	    	 	
			                  	    	 	
	                                                       
	                                                       
			                  	    	 			                  	    	 }

			                  	    	 else
			                  	    		 break;
			                       }
			                	
							} 
								catch (NumberFormatException e) {
								
								e.printStackTrace();
							} 
							catch (IOException e) {
								
								e.printStackTrace();
							}
							
							
							}
													
		                	catch (FileNotFoundException e) {
							
							e.printStackTrace();
						}
	        }
	        
	        int binarySearch(demo1 s[], int l, int r, String prikey) {
	    	
	    	int mid;
	    	while (l<=r) {
	            
	    		mid = (l+r)/2;
	    		if(s[mid].getprikey().compareTo(prikey)==0) {return mid;}
	    		if(s[mid].getprikey().compareTo(prikey)<0) l = mid + 1;
	    		if(s[mid].getprikey().compareTo(prikey)>0) r = mid - 1;
	    	}
	    	return -1;
	    }
	      

	  public  void indexing() 
	  {
	         try{
	        RandomAccessFile rm=new RandomAccessFile("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
	  
	    			

	        RandomAccessFile indexfile=new RandomAccessFile("C:\\Users\\Sneha\\Desktop\\primary.txt","rw");
	        String line;
	 long       pos=rm.getFilePointer();
	        while((line = rm.readLine())!=null)
	        {
	            if(line.contains("*")) {
		                		continue;
		                	}

	            String[] columns=line.split(",");
	                                 

	                        

	         
	            indexfile.writeBytes(columns[0]+","+pos+"\n");
	            pos=rm.getFilePointer();
	        } indexfile.close();
	        rm.close();
	                
	        
	         
	       
	         }
	    
	    catch(IOException e)
	    {
	        System.out.println(e);
	    }
	  }
	 

	                        

	         
	           	 public   void delete() throws IOException {
	         System.out.println("Enter the primary key to delete record ");
						Scanner scanner = new Scanner(System.in);
						String prikey = scanner.next();
	        int pos = binarySearch(sI, 0, reccount-1, prikey);
						
						if (pos == -1) {
System.out.println("Record not found in the record file");
							return ;
						}
	                                        RandomAccessFile recordfile;
	                                        
						
							recordfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
							try {
								recordfile.seek(Long.parseLong(sI[pos].getRecPos()));
	                                                        recordfile.writeBytes("*");
	                                                        recordfile.close();
	                                                
	                                                        }    
	                                                            
	                                             catch (NumberFormatException e) {
								
								e.printStackTrace();
							} 
							catch (IOException e) {
								
								e.printStackTrace();
							}
							
							
							}
								


	


}

