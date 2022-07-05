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
			
			int no = selectMenu();	//�޴����� �Լ� ȣ��
			
			switch(no) {
			case 1: studentDataInsert(); break;	//����
			case 2: studentDataSearch(); break;	//�˻�
			case 3: studentDataDelete(); break;	//����
			case 4: studentDataModify(); break;	//����
			case 5: studentDataPrint(); break;	//���
			case 6: studentDataSort(); break;	//����
			case 7: flag = true; break;	        //����
			default:
				System.out.println("�ٽ� �Է��ϼ���");
				break;
		
			
			}//end of switch

			
		}//end of while
		
	}// end of main

	
	//�Է�(name id gender schoolGrade kor eng math)
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

		//�й�
		while(true) {
			System.out.println("�й� �Է�: ");
			id=scan.nextLine();
			
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("�ٽ� �Է����ּ���");
			}
			
		}//end of id
			
		//������ ��ü ��ȸ
			List<StudentDataModel> list = new ArrayList<StudentDataModel>();
			list = DBController.studentDataSelect();
			
			//id �ߺ� Ȯ��
			if(list.size()>0) {
				for(StudentDataModel data : list) {
					if(id.equals(data.getId())) {
						System.out.println("�̹� �Էµ� �й��Դϴ�.");
						return;
					}
				}
			}	
		
		
		
		
		
			
		
		//�̸�
		while(true) {
			System.out.println("�̸� �Է�: ");
			name=scan.nextLine();
			
			if(patternCheck(name,NAME)) {
				break;
			}
			else {
				System.out.println("�ٽ� �Է����ּ���");
			}
			
		}//end of name
		

		
		
		//����
		while(true) {
			System.out.println("����(����,����) �Է�: ");
			gender = scan.nextLine();
					
			if(gender.equals("����") || gender.equals("����")) {
				break;
			}else {
				System.out.println("�ٽ� �Է����ּ���");
			}			
		}//end of gender		
		
		
		//�г�
		while(true) {
			System.out.println("�г� �Է�:");
			schoolGrade = scan.nextInt();
			
			if(schoolGrade>=1 && schoolGrade<=3) {
				break;
			}else {
				System.out.println("�ٽ� �Է����ּ���");
			}			
		}
		
		
	    //���� ����
		while(true) {
			System.out.println("���� ���� �Է�:");
			kor = scan.nextInt();
			
			if(kor>=0 && kor<=100) {
				break;
			}	
		}

		while(true) {
			System.out.println("���� ���� �Է�:");
			eng = scan.nextInt();
			
			if(eng>=0 && eng<=100) {
				break;
			}	
		}
		
		while(true) {
			System.out.println("���� ���� �Է�:");
			math = scan.nextInt();
			
			if(math>=0 && math<=100) {
				break;
			}	
		}		
		
		
		 //������ �� ����
		 StudentDataModel studentDataModel =new StudentDataModel(name, id, gender, schoolGrade, kor, eng, math, total, avr, scoreGrade);		
	
		 //����,���,�ձ� ���
		 studentDataModel.calTotal();
		 studentDataModel.calAvg();
		 studentDataModel.calGrade();
	
		
     int returnValue =DBController.studentDataInsert(studentDataModel);
		
	 if(returnValue!=0) {
		 System.out.println(studentDataModel.getName()+"�� ���� ����");
	 }
	 else {
	 	 System.out.println(studentDataModel.getName()+"�� ���� ����");
	 }		
		
		
	}	
	
	
	//����(����)
	private static void studentDataSort() {
		
		List<StudentDataModel> list = new ArrayList<StudentDataModel>();
		int no=0;
		boolean flag = false;
		
		while(!flag) {
			
			//1.����(��������,��������)
			System.out.println("1.��������(����) 2.��������(����)");
			System.out.print("���Ĺ�� ����: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());	
			}catch(InputMismatchException e) {
				System.out.println("���: ���ڸ� �Է� ���ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("���: ���ڸ� �Է� ���ּ���!");
				continue;
			}
		
		if(no>=1 && no<=2) {
			flag=true;
		}else {
			System.out.println("���: 1~2 ������ ���� �Է��ϼ���");
		}
		
	}//end of while		
		
		
		//2.(���ĵ� ������) ��¹��� �����´�
		list = DBController.studentDataSort(no);
		
		if(list.size()<=0) {
			System.out.println("������ ������ �����ϴ�.");
			return;
		}
		
		for(StudentDataModel data: list) {
			System.out.println(data.toString());
		}
				
		return;		
		
	}


	//���
	private static void studentDataPrint() {
		
	List<StudentDataModel> list = new ArrayList<StudentDataModel>();
	list = DBController.studentDataSelect();
		
	if(list.size()<=0) {
		System.out.println("����� ������ �����ϴ�.");
		return;
	}

	System.out.println("----------------------------------------------------------------------------------");
	System.out.println(" �̸�     �й�     ����     �г�     ����     ����     ����     ����           ���     ���");
	System.out.println("----------------------------------------------------------------------------------");
	for(StudentDataModel data:list) {
		System.out.println(data.toString());
	}
}


	//����(����)
	private static void studentDataModify() {
		
		final int ID_NUM=2;
		
		//1. ���� ����� �й� �˻�
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
			System.out.println("���� ����� �й� �Է�: ");
			id = scan.nextLine();
					
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("�ٽ� �Է����ּ���");
			}				
		}
	
		searchData=id;
		number=ID_NUM;	
	
		list = DBController.studentDataSearch(searchData,number);
		
		if(list.size()<=0) {
			System.out.println("����ڸ� ã�� ���߽��ϴ�");
			return;
		}
		
		StudentDataModel data_buffer = null;	
		
		//���� ������� ���� ����
	for(StudentDataModel data : list) {
			data_buffer = data;
		}
				
		
		
	///2. ���� ��� �л��� ���� ����
		System.out.println(data_buffer.getName()+"�� ���� ���� [����: "+data_buffer.getKor()+" ����: "+data_buffer.getEng()+" ����: "+data_buffer.getMath()+"]");
	
		while(true) {
			System.out.println("������ �������� �Է�:");
			kor = scan.nextInt();
			
			if(kor>=0 && kor<=100) {
				break;
			}	
		}

		while(true) {
			System.out.println("������ �������� �Է�:");
			eng = scan.nextInt();
			
			if(eng>=0 && eng<=100) {
				break;
			}	
		}
		
		while(true) {
			System.out.println("������ �������� �Է�:");
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
		
		
	//3. ����� Ȯ��
	resultNumber=DBController.studentDataModify(id,kor,eng,math,total,avr,scoreGrade);
	
	if(resultNumber != 0) {
		System.out.println("���� �����Ϸ�");
		
	}
	else {
		System.out.println("���� ��������");
	}
	
	
	return;
	
		
	}
		
	
	//����(�й�)
	private static void studentDataDelete() {
		
		final int ID_NUM=2;
		String id = null;
		String deleteData = null;
		int number=0;
		int resultNumber=0;
	
		while(true) {
			System.out.println("���� ������� �й� �Է�: ");
			id = scan.nextLine();
				
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("�ٽ� �Է����ּ���");
			}				
		}
		
		
		deleteData=id;
		number=ID_NUM;	
		resultNumber = DBController.studentDataDelete(deleteData,number);
		
		if(resultNumber != 0) {
			System.out.println("�й�: "+id+" �����Ϸ�");
		}
		else {
			System.out.println("�й�: "+id+" ��������");
		}
		
		
		return;	
		
	}

	
	//�˻�(�̸�,�й�)
	private static void studentDataSearch() {

		final int NAME_NUM=1,ID_NUM=2,EXIT=3;
		
		List<StudentDataModel>list = new ArrayList<StudentDataModel>();
		
		//�˻� ���� ����(name,id)
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
					System.out.println("�˻� ������� �̸� �Է�: ");
					name=scan.nextLine();
					
					if(patternCheck(name,NAME)) {
						break;
					}
					else {
						System.out.println("�ٽ� �Է����ּ���");
					}
					
				}		
				
				searchData=name;
				number=NAME_NUM;
				break;
			
			case ID_NUM: 
				while(true) {
					System.out.println("�˻� ������� �й� �Է�: ");
					id=scan.nextLine();
					
					if(patternCheck(id,ID)) {
						break;
					}
					else {
						System.out.println("�ٽ� �Է����ּ���");
					}
					
				}//end of id

				searchData=id;
				number=ID_NUM;
				break;
				
			case EXIT: 
				System.out.println("�˻� ���");
				flag=true;
				break;
		
		}//END OF SWITCH
		
		if(flag) {
			return;
		}
		
		list = DBController.studentDataSearch(searchData,number);
		
		if(list.size()<=0) {
			System.out.println(searchData+"�˻� ����");
		}
		
		
		//�˻��� ���� ���
		for(StudentDataModel data : list) {
			System.out.println(data);
		}
		
	
	}


	//�˻��޴�����
	private static int searchMenu() {
		
		boolean flag=false;
		int no=0;
		
		while(!flag) {
			
			System.out.println("******************");
			System.out.println("1.�̸� 2.�й� 3.����");
			System.out.println("******************");
			System.out.println("������ �޴� ����: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());	
			}catch(InputMismatchException e) {
				System.out.println("���: ���ڸ� �Է� ���ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("���: ���ڸ� �Է� ���ּ���!");
				continue;
			}
		
		if(no>=1 && no<=3) {
			flag=true;
		}else {
			System.out.println("���: 1~3 ������ ���� �Է��ϼ���");
		}
		
	}//end of while
		
		return no;		
		
	}
		

	//����üũ �Լ�(�̸�,�й�)
	private static boolean patternCheck(String patternData, int patternType) {
			
		String filter = null;
		switch(patternType) {
		case NAME: filter= "^[��-�R]{2,5}$"; break;
		case ID: filter= "\\d{4}"; break;
		}
			
		Pattern pattern = Pattern.compile(filter);
	    Matcher matcher = pattern.matcher(patternData);
		 
		return matcher.matches();
			
	}

	
	//��ü�޴�����
	private static int selectMenu() {
		
		boolean flag=false;
		int no =0;
		
		while(!flag) {
			
			System.out.println("**********************************************");
			System.out.println("1.�Է� 2.�˻� 3.���� 4.���� 5.��� 6.���� 7.����");
			System.out.println("**********************************************");
			System.out.println("������ �޴� ����: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());
			}catch(InputMismatchException e) {
				System.out.println("���: ���ڸ� �Է� ���ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("���: ���ڸ� �Է� ���ּ���!");
				continue;
			}
		
		if(no>=1 && no<=7) {
			flag=true;
		}else {
			System.out.println("���: 1~7 ������ ���� �Է��ϼ���");
		}
		
	}//end of while
		
		return no;
		
	}//end of selcetMenu

	





}//end of class
