package indexing;

//public class secon {
	//package indexing;

	 import java.io.FileNotFoundException;
	 import java.io.IOException;
	 import java.io.RandomAccessFile;
	 import java.util.Scanner;
	 import java.util.StringTokenizer;

	 public class secon {
	 private demo2[] sI = new demo2[110];
	  
	     private String id,ProductId,UserId,ProfileName,Ratings,Time,Reviews;
	  int reccount = 0;
	 public void getData(){
	       @SuppressWarnings("resource")
	   Scanner scanner = new Scanner(System.in);
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
	   String data=id +","+ ProductId +","+ UserId +","+ ProfileName +","+ Ratings+","+Time+","+Reviews+"";
	  try{   
	    RandomAccessFile recordfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
	    recordfile.seek(recordfile.length());
	    long pos = recordfile.getFilePointer();
	    recordfile.writeBytes(data+"\n");
	    recordfile.close();
	    
	    RandomAccessFile indexfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\secondary.txt","rw");
	    indexfile.seek(indexfile.length());
	    indexfile.writeBytes(id+","+pos+"\n");
	    indexfile.close();
	   }
	   catch(IOException e){
	    System.out.println(e);
	   }
	   
	 
	 }                    
	     public void priIndex(){
	   String line
	                         ,seckey = null,pos = null;
	   int count = 0;
	   int sIIndex = 0;
	   reccount=0;
	   RandomAccessFile indexfile;
	   try {
	    indexfile = new RandomAccessFile("C:\\Users\\Sneha\\Desktop\\secondary.txt","rw");
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
	         seckey = st.nextToken();
	       pos = st.nextToken();
	                                         
	         }
	      sI[sIIndex] = new demo2();
	      sI[sIIndex].setRecPos(pos);
	      sI[sIIndex].setseckey(seckey);
	      reccount++;
	      sIIndex++;
	                                         if(sIIndex==110)
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
	   demo2 temp = new demo2();
	   
	   for(int i=0; i<reccount; i++)
	       { 
	     for(int j=i+1; j<reccount; j++) {
	      if(sI[i].getseckey().compareTo(sI[j].getseckey())  > 0)
	               {
	                   temp.setseckey(sI[i].getseckey());
	             temp.setRecPos(sI[i].getRecPos());
	     
	             sI[i].setseckey(sI[j].getseckey());
	             sI[i].setRecPos(sI[j].getRecPos());
	     
	             sI[j].setseckey(temp.getseckey());
	             sI[j].setRecPos(temp.getRecPos());
	               }
	     }
	      
	    } 
	   
	  }
	         public void search(){
	           System.out.println("Enter the id to search: ");
	              @SuppressWarnings("resource")
	    Scanner scanner = new Scanner(System.in);
	              String id = scanner.next();
	             
	             
	              int oripos = binarySearch(sI, 0, reccount-1, id);
	             
	              if (oripos == -1) {
	                  System.out.println("Record not found in the record file");
	                  return ;
	              }
	             
	              int pos = oripos;
	             
	              do {
	                  readFile(pos);
	                  pos--;
	                  if (pos < 0) { break;}
	              }while(sI[pos].getseckey().compareTo(id)==0);
	             
	              pos = oripos + 1 ;
	             
	              while(sI[pos].getseckey().compareTo(id)==0 && pos > reccount-1) {
	                  readFile(pos);
	                  pos++;
	              }
	         }
	  public void readFile(int pos) {
	            
	             RandomAccessFile recordfile;
	             try {
	                 recordfile = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
	                 try {
	                     recordfile.seek(Long.parseLong(sI[pos].getRecPos()));
	                     String record = recordfile.readLine();
	                     StringTokenizer st = new StringTokenizer(record,",");
	                    
	                     int count = 0;
	                       
	                     while (st.hasMoreTokens()){
	                              count += 1;
	                                if(count==1){
	                               
	                                String tmp_prikey = st.nextToken();
	                               System.out.println("id: "+tmp_prikey);
	                               this.id = tmp_prikey;
	                               
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
	                    
	                     recordfile.close();
	                 }
	                     catch (NumberFormatException e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                 }
	                 catch (IOException e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                 }
	                
	                
	                 }
	                                        
	                 catch (FileNotFoundException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
	  }
	         int binarySearch(demo2 s[], int l, int r, String id) {
	      
	      int mid;
	      while (l<=r) {
	            
	       mid = (l+r)/2;
	       if(s[mid].getseckey().compareTo(id)==0) {return mid;}
	       if(s[mid].getseckey().compareTo(id)<0) l = mid + 1;
	       if(s[mid].getseckey().compareTo(id)>0) r = mid - 1;
	      }
	      return -1;
	     }
	   public  void indexing()
	   {
	          try{
	         RandomAccessFile rm=new RandomAccessFile("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
	  
	        
	         RandomAccessFile indexfile=new RandomAccessFile("C:\\Users\\Sneha\\Desktop\\secondary.txt","rw");
	         String line;
	  long       pos=rm.getFilePointer();
	         while((line =rm.readLine())!=null)
	         {
	             if(line.contains("*")) {
	                    continue;
	                   }
	             String[] columns=line.split(",");
	                                 
	                        
	         
	             indexfile.writeBytes(columns[1]+","+pos+"\n");
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
	   indexing();
	     
	      System.out.println("Enter the profileName to delete: ");
	      Scanner scanner = new Scanner(System.in);
	      String profileName = scanner.next();
	      String ans = "n";
	      int pos;
	     
	      int oripos = binarySearch(sI, 0, reccount-1,profileName );
	     
	      if (oripos == -1) {
	          System.out.println("Record not found in the record file");
	          return ;
	      }
	     
	      pos = oripos;
	     
	      do {
	          readFile(pos);
	         
	          System.out.println("Do You Want To delete This Record ?(y/n) ");
	          ans = scanner.next();
	         
	          if (ans.compareTo("y")==0) {
	              markDelete(pos, id);
	          }
	          pos--;
	          if (pos < 0) { break;}
	      }while(sI[pos].getseckey().compareTo(id)==0);
	         
	     
	      pos = oripos + 1 ;
	     
	     
	      while(sI[pos].getseckey().compareTo(id)==0 && pos > reccount-1){
	          readFile(pos);
	         
	          System.out.println("Do You Want To delete This Record ?(y/n) ");
	          ans = scanner.next();
	         
	          if (ans.compareTo("y")==0) {
	              markDelete(pos, id);
	              break;
	          }
	          pos++;
	          if (pos > reccount-1) { break;}
	      }
	 }
	  public void markDelete(int pos, String country) {
	      try {
	          RandomAccessFile recordfilee = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\fsr.csv","rw");
	          RandomAccessFile indexfilee = new RandomAccessFile ("C:\\Users\\Sneha\\Desktop\\secondary.txt","rw");
	     
	              recordfilee.seek(Long.parseLong(sI[pos].getRecPos()));
	              recordfilee.writeBytes("*");
	              System.out.println("Done");
	              recordfilee.close();
	              String line;
	              String indexName;
	              long indexPos = 0;
	              long delPosi;
	              while((line = indexfilee.readLine())!=null) {
	                  if(line.contains("*"))
	                      continue;
	                  StringTokenizer st = new StringTokenizer(line,",");
	                 delPosi = indexfilee.getFilePointer();
	                
	                
	                  delPosi = delPosi - (line.length()+2);
	                 
	                                  
	                  while(st.hasMoreTokens()) {
	                      indexName=st.nextToken();
	                      indexPos= Long.parseLong(st.nextToken());
	                    
	                      if(indexName.equals(country) && indexPos == Long.parseLong(sI[pos].getRecPos()) ) {
	                          indexfilee.seek(delPosi);
	                          indexfilee.writeBytes("*");
	                          indexfilee.close();
	                          System.out.println("Deleted");
	                          indexing();
	                          return;
	                      }
	                  }
	              }
	              }
	         
	          catch (Exception e) {
	              e.printStackTrace();
	          }
	  }
	 
	}

