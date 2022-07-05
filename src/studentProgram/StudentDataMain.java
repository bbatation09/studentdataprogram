package studentProgram;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StudentDataMain {

	public static Scanner scan = new Scanner(System.in);
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, MODIFY = 4, PRINT = 5, SORT = 6, EXIT = 7;
	public static final int NAME = 1, ID = 2;
	public final static int SUBJECT=3;
	
	public static void main(String[] args) {
		boolean flag=false;
		
		while(!flag) {
			
			int no = selectMenu();	//¸Ş´º¼±ÅÃ ÇÔ¼ö È£Ãâ
			
			switch(no) {
			case 1: studentDataInsert(); break;	//»ğÀÔ
			case 2: studentDataSearch(); break;	//°Ë»ö
			case 3: studentDataDelete(); break;	//»èÁ¦
			case 4: studentDataModify(); break;	//¼öÁ¤
			case 5: studentDataPrint(); break;	//Ãâ·Â
			case 6: studentDataSort(); break;	//Á¤·Ä
			case 7: flag = true; break;	        //Á¾·á
			default:
				System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä");
				break;
		
			
			}//end of switch

			
		}//end of while
		
	}// end of main

	
	//ÀÔ·Â(name id gender schoolGrade kor eng math)
	private static void studentDataInsert() {
		
		String name=null;
		String id=null;
		String gender=null;
		int schoolGrade=0;
		int kor=0;
		int eng=0;
		int math=0;	
		int total=0;
		double avr=0.0;
		String scoreGrade=null;

		//ÇĞ¹ø
		while(true) {
			System.out.println("ÇĞ¹ø ÀÔ·Â: ");
			id=scan.nextLine();
			
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
			}
			
		}//end of id
			
		//µ¥ÀÌÅÍ ÀüÃ¼ Á¶È¸
			List<StudentDataModel> list = new ArrayList<StudentDataModel>();
			list = DBController.studentDataSelect();
			
			//id Áßº¹ È®ÀÎ
			if(list.size()>0) {
				for(StudentDataModel data : list) {
					if(id.equals(data.getId())) {
						System.out.println("ÀÌ¹Ì ÀÔ·ÂµÈ ÇĞ¹øÀÔ´Ï´Ù.");
						return;
					}
				}
			}	
		
		
		
		
		
			
		
		//ÀÌ¸§
		while(true) {
			System.out.println("ÀÌ¸§ ÀÔ·Â: ");
			name=scan.nextLine();
			
			if(patternCheck(name,NAME)) {
				break;
			}
			else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
			}
			
		}//end of name
		

		
		
		//¼ºº°
		while(true) {
			System.out.println("¼ºº°(³²ÀÚ,¿©ÀÚ) ÀÔ·Â: ");
			gender = scan.nextLine();
					
			if(gender.equals("³²ÀÚ") || gender.equals("¿©ÀÚ")) {
				break;
			}else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
			}			
		}//end of gender		
		
		
		//ÇĞ³â
		while(true) {
			System.out.println("ÇĞ³â ÀÔ·Â:");
			schoolGrade = scan.nextInt();
			
			if(schoolGrade>=1 && schoolGrade<=3) {
				break;
			}else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
			}			
		}
		
		
	    //°ú¸ñº° Á¡¼ö
		while(true) {
			System.out.println("±¹¾î Á¡¼ö ÀÔ·Â:");
			kor = scan.nextInt();
			
			if(kor>=0 && kor<=100) {
				break;
			}	
		}

		while(true) {
			System.out.println("¿µ¾î Á¡¼ö ÀÔ·Â:");
			eng = scan.nextInt();
			
			if(eng>=0 && eng<=100) {
				break;
			}	
		}
		
		while(true) {
			System.out.println("¼öÇĞ Á¡¼ö ÀÔ·Â:");
			math = scan.nextInt();
			
			if(math>=0 && math<=100) {
				break;
			}	
		}		
		
		
		 //»ğÀÔÇÒ ¸ğµ¨ »ı¼º
		 StudentDataModel studentDataModel =new StudentDataModel(name, id, gender, schoolGrade, kor, eng, math, total, avr, scoreGrade);		
	
		 //ÃÑÁ¡,Æò±Õ,µÕ±Ş °è»ê
		 studentDataModel.calTotal();
		 studentDataModel.calAvg();
		 studentDataModel.calGrade();
	
		
     int returnValue =DBController.studentDataInsert(studentDataModel);
		
	 if(returnValue!=0) {
		 System.out.println(studentDataModel.getName()+"´Ô »ğÀÔ ¼º°ø");
	 }
	 else {
	 	 System.out.println(studentDataModel.getName()+"´Ô »ğÀÔ ½ÇÆĞ");
	 }		
		
		
	}	
	
	
	//Á¤·Ä(ÃÑÁ¡)
	private static void studentDataSort() {
		
		List<StudentDataModel> list = new ArrayList<StudentDataModel>();
		int no=0;
		boolean flag = false;
		
		while(!flag) {
			
			//1.Á¤·Ä(¿À¸§Â÷¼ø,³»¸²Â÷¼ø)
			System.out.println("1.¿À¸§Â÷¼ø(ÃÑÁ¡) 2.³»¸²Â÷¼ø(ÃÑÁ¡)");
			System.out.print("Á¤·Ä¹æ½Ä ¼±ÅÃ: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());	
			}catch(InputMismatchException e) {
				System.out.println("°æ°í: ¼ıÀÚ¸¦ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("°æ°í: ¼ıÀÚ¸¦ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
		
		if(no>=1 && no<=2) {
			flag=true;
		}else {
			System.out.println("°æ°í: 1~2 »çÀÌÀÇ ¼ıÀÚ ÀÔ·ÂÇÏ¼¼¿ä");
		}
		
	}//end of while		
		
		
		//2.(Á¤·ÄµÈ »óÅÂÀÇ) Ãâ·Â¹®À» °¡Á®¿Â´Ù
		list = DBController.studentDataSort(no);
		
		if(list.size()<=0) {
			System.out.println("Á¤·ÄÇÒ ³»¿ëÀÌ ¾ø½À´Ï´Ù.");
			return;
		}
		
		for(StudentDataModel data: list) {
			System.out.println(data.toString());
		}
				
		return;		
		
	}


	//Ãâ·Â
	private static void studentDataPrint() {
		
	List<StudentDataModel> list = new ArrayList<StudentDataModel>();
	list = DBController.studentDataSelect();
		
	if(list.size()<=0) {
		System.out.println("Ãâ·ÂÇÒ ¼ºÀûÀÌ ¾ø½À´Ï´Ù.");
		return;
	}

	System.out.println("----------------------------------------------------------------------------------");
	System.out.println(" ÀÌ¸§     ÇĞ¹ø     ¼ºº°     ÇĞ³â     ±¹¾î     ¿µ¾î     ¼öÇĞ     ÃÑÁ¡           Æò±Õ     µî±Ş");
	System.out.println("----------------------------------------------------------------------------------");
	for(StudentDataModel data:list) {
		System.out.println(data.toString());
	}
}


	//¼öÁ¤(¼ºÀû)
	private static void studentDataModify() {
		
		final int ID_NUM=2;
		
		//1. ¼öÁ¤ ´ë»óÀÚ ÇĞ¹ø °Ë»ö
		List<StudentDataModel>list = new ArrayList<StudentDataModel>();

		String id=null;
		
		String searchData = null;
		int number=0;
		
		int resultNumber=0;
		
		int kor=0;
		int eng=0;
		int math=0;
		
		int total=0;
		double avr=0.0;
		String scoreGrade=null;	
		
		
		
		while(true) {
			System.out.println("¼öÁ¤ ´ë»óÀÚ ÇĞ¹ø ÀÔ·Â: ");
			id = scan.nextLine();
					
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
			}				
		}
	
		searchData=id;
		number=ID_NUM;	
	
		list = DBController.studentDataSearch(searchData,number);
		
		if(list.size()<=0) {
			System.out.println("´ë»óÀÚ¸¦ Ã£Áö ¸øÇß½À´Ï´Ù");
			return;
		}
		
		StudentDataModel data_buffer = null;	
		
		//¼öÁ¤ ´ë»óÀÚÀÇ Á¤º¸ Àü´Ş
	for(StudentDataModel data : list) {
			data_buffer = data;
		}
				
		
		
	///2. ¼öÁ¤ ´ë»ó ÇĞ»ıÀÇ ¼ºÀû ¼öÁ¤
		System.out.println(data_buffer.getName()+"ÀÇ ÇöÀç ¼ºÀû [±¹¾î: "+data_buffer.getKor()+" ¿µ¾î: "+data_buffer.getEng()+" ¼öÇĞ: "+data_buffer.getMath()+"]");
	
		while(true) {
			System.out.println("¼öÁ¤ÇÒ ±¹¾îÁ¡¼ö ÀÔ·Â:");
			kor = scan.nextInt();
			
			if(kor>=0 && kor<=100) {
				break;
			}	
		}

		while(true) {
			System.out.println("¼öÁ¤ÇÒ ¿µ¾îÁ¡¼ö ÀÔ·Â:");
			eng = scan.nextInt();
			
			if(eng>=0 && eng<=100) {
				break;
			}	
		}
		
		while(true) {
			System.out.println("¼öÁ¤ÇÒ ¼öÇĞÁ¡¼ö ÀÔ·Â:");
			math = scan.nextInt();
			
			if(math>=0 && math<=100) {
				break;
			}	
		}				

		total =kor + math + eng;
		avr = total/(double)SUBJECT;
		   
		while(true) {
			
		 if(avr>=90) {
		         scoreGrade = "A";
		         break;
		      }
		      else if(avr>=80 && avr<90) {
		         scoreGrade = "B";
		         break;
		      }
		      else if(avr>=70 && avr<80) {
		         scoreGrade = "C";
		         break;
		      }
		      else if(avr>=60 && avr<70) {
		         scoreGrade = "D";
		         break;
		      }
		      else {
		         scoreGrade = "F";
		         break;
		      }
		   }		
		
		
	//3. °á°ú°ª È®ÀÎ
	resultNumber=DBController.studentDataModify(id,kor,eng,math,total,avr,scoreGrade);
	
	if(resultNumber != 0) {
		System.out.println("¼ºÀû ¼öÁ¤¿Ï·á");
		
	}
	else {
		System.out.println("¼ºÀû ¼öÁ¤½ÇÆĞ");
	}
	
	
	return;
	
		
	}
		
	
	//»èÁ¦(ÇĞ¹ø)
	private static void studentDataDelete() {
		
		final int ID_NUM=2;
		String id = null;
		String deleteData = null;
		int number=0;
		int resultNumber=0;
	
		while(true) {
			System.out.println("»èÁ¦ ´ë»óÀÚÀÇ ÇĞ¹ø ÀÔ·Â: ");
			id = scan.nextLine();
				
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
			}				
		}
		
		
		deleteData=id;
		number=ID_NUM;	
		resultNumber = DBController.studentDataDelete(deleteData,number);
		
		if(resultNumber != 0) {
			System.out.println("ÇĞ¹ø: "+id+" »èÁ¦¿Ï·á");
		}
		else {
			System.out.println("ÇĞ¹ø: "+id+" »èÁ¦½ÇÆĞ");
		}
		
		
		return;	
		
	}

	
	//°Ë»ö(ÀÌ¸§,ÇĞ¹ø)
	private static void studentDataSearch() {

		final int NAME_NUM=1,ID_NUM=2,EXIT=3;
		
		List<StudentDataModel>list = new ArrayList<StudentDataModel>();
		
		//°Ë»ö ±âÁØ ¼±ÅÃ(name,id)
		String name=null;
		String id=null;
		
		boolean flag=false;
		
		String searchData =	null;
		int no=0;
		int number=0;
		no= searchMenu();
		
		switch(no) {
			case NAME_NUM: 
				while(true) {
					System.out.println("°Ë»ö ´ë»óÀÚÀÇ ÀÌ¸§ ÀÔ·Â: ");
					name=scan.nextLine();
					
					if(patternCheck(name,NAME)) {
						break;
					}
					else {
						System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
					}
					
				}		
				
				searchData=name;
				number=NAME_NUM;
				break;
			
			case ID_NUM: 
				while(true) {
					System.out.println("°Ë»ö ´ë»óÀÚÀÇ ÇĞ¹ø ÀÔ·Â: ");
					id=scan.nextLine();
					
					if(patternCheck(id,ID)) {
						break;
					}
					else {
						System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
					}
					
				}//end of id

				searchData=id;
				number=ID_NUM;
				break;
				
			case EXIT: 
				System.out.println("°Ë»ö Ãë¼Ò");
				flag=true;
				break;
		
		}//END OF SWITCH
		
		if(flag) {
			return;
		}
		
		list = DBController.studentDataSearch(searchData,number);
		
		if(list.size()<=0) {
			System.out.println(searchData+"°Ë»ö ½ÇÆĞ");
		}
		
		
		//°Ë»öÇÑ ³»¿ë Ãâ·Â
		for(StudentDataModel data : list) {
			System.out.println(data);
		}
		
	
	}


	//°Ë»ö¸Ş´º¼±ÅÃ
	private static int searchMenu() {
		
		boolean flag=false;
		int no=0;
		
		while(!flag) {
			
			System.out.println("******************");
			System.out.println("1.ÀÌ¸§ 2.ÇĞ¹ø 3.Á¾·á");
			System.out.println("******************");
			System.out.println("½ÇÇàÇÒ ¸Ş´º ¼±ÅÃ: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());	
			}catch(InputMismatchException e) {
				System.out.println("°æ°í: ¼ıÀÚ¸¦ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("°æ°í: ¼ıÀÚ¸¦ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
		
		if(no>=1 && no<=3) {
			flag=true;
		}else {
			System.out.println("°æ°í: 1~3 »çÀÌÀÇ ¼ıÀÚ ÀÔ·ÂÇÏ¼¼¿ä");
		}
		
	}//end of while
		
		return no;		
		
	}
		

	//ÆĞÅÏÃ¼Å© ÇÔ¼ö(ÀÌ¸§,ÇĞ¹ø)
	private static boolean patternCheck(String patternData, int patternType) {
			
		String filter = null;
		switch(patternType) {
		case NAME: filter= "^[°¡-ÆR]{2,5}$"; break;
		case ID: filter= "\\d{4}"; break;
		}
			
		Pattern pattern = Pattern.compile(filter);
	    Matcher matcher = pattern.matcher(patternData);
		 
		return matcher.matches();
			
	}

	
	//ÀüÃ¼¸Ş´º¼±ÅÃ
	private static int selectMenu() {
		
		boolean flag=false;
		int no =0;
		
		while(!flag) {
			
			System.out.println("**********************************************");
			System.out.println("1.ÀÔ·Â 2.°Ë»ö 3.»èÁ¦ 4.¼öÁ¤ 5.Ãâ·Â 6.Á¤·Ä 7.Á¾·á");
			System.out.println("**********************************************");
			System.out.println("½ÇÇàÇÒ ¸Ş´º ¼±ÅÃ: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());
			}catch(InputMismatchException e) {
				System.out.println("°æ°í: ¼ıÀÚ¸¦ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("°æ°í: ¼ıÀÚ¸¦ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
		
		if(no>=1 && no<=7) {
			flag=true;
		}else {
			System.out.println("°æ°í: 1~7 »çÀÌÀÇ ¼ıÀÚ ÀÔ·ÂÇÏ¼¼¿ä");
		}
		
	}//end of while
		
		return no;
		
	}//end of selcetMenu

	





}//end of class
