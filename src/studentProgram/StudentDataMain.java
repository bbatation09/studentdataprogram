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
			
			int no = selectMenu();	//메뉴선택 함수 호출
			
			switch(no) {
			case 1: studentDataInsert(); break;	//삽입
			case 2: studentDataSearch(); break;	//검색
			case 3: studentDataDelete(); break;	//삭제
			case 4: studentDataModify(); break;	//수정
			case 5: studentDataPrint(); break;	//출력
			case 6: studentDataSort(); break;	//정렬
			case 7: flag = true; break;	        //종료
			default:
				System.out.println("다시 입력하세요");
				break;
		
			
			}//end of switch

			
		}//end of while
		
	}// end of main

	
	//입력(name id gender schoolGrade kor eng math)
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

		//학번
		while(true) {
			System.out.println("학번 입력: ");
			id=scan.nextLine();
			
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("다시 입력해주세요");
			}
			
		}//end of id
			
		//데이터 전체 조회
			List<StudentDataModel> list = new ArrayList<StudentDataModel>();
			list = DBController.studentDataSelect();
			
			//id 중복 확인
			if(list.size()>0) {
				for(StudentDataModel data : list) {
					if(id.equals(data.getId())) {
						System.out.println("이미 입력된 학번입니다.");
						return;
					}
				}
			}	
		
		
		
		
		
			
		
		//이름
		while(true) {
			System.out.println("이름 입력: ");
			name=scan.nextLine();
			
			if(patternCheck(name,NAME)) {
				break;
			}
			else {
				System.out.println("다시 입력해주세요");
			}
			
		}//end of name
		

		
		
		//성별
		while(true) {
			System.out.println("성별(남자,여자) 입력: ");
			gender = scan.nextLine();
					
			if(gender.equals("남자") || gender.equals("여자")) {
				break;
			}else {
				System.out.println("다시 입력해주세요");
			}			
		}//end of gender		
		
		
		//학년
		while(true) {
			System.out.println("학년 입력:");
			schoolGrade = scan.nextInt();
			
			if(schoolGrade>=1 && schoolGrade<=3) {
				break;
			}else {
				System.out.println("다시 입력해주세요");
			}			
		}
		
		
	    //과목별 점수
		while(true) {
			System.out.println("국어 점수 입력:");
			kor = scan.nextInt();
			
			if(kor>=0 && kor<=100) {
				break;
			}	
		}

		while(true) {
			System.out.println("영어 점수 입력:");
			eng = scan.nextInt();
			
			if(eng>=0 && eng<=100) {
				break;
			}	
		}
		
		while(true) {
			System.out.println("수학 점수 입력:");
			math = scan.nextInt();
			
			if(math>=0 && math<=100) {
				break;
			}	
		}		
		
		
		 //삽입할 모델 생성
		 StudentDataModel studentDataModel =new StudentDataModel(name, id, gender, schoolGrade, kor, eng, math, total, avr, scoreGrade);		
	
		 //총점,평균,둥급 계산
		 studentDataModel.calTotal();
		 studentDataModel.calAvg();
		 studentDataModel.calGrade();
	
		
     int returnValue =DBController.studentDataInsert(studentDataModel);
		
	 if(returnValue!=0) {
		 System.out.println(studentDataModel.getName()+"님 삽입 성공");
	 }
	 else {
	 	 System.out.println(studentDataModel.getName()+"님 삽입 실패");
	 }		
		
		
	}	
	
	
	//정렬(총점)
	private static void studentDataSort() {
		
		List<StudentDataModel> list = new ArrayList<StudentDataModel>();
		int no=0;
		boolean flag = false;
		
		while(!flag) {
			
			//1.정렬(오름차순,내림차순)
			System.out.println("1.오름차순(총점) 2.내림차순(총점)");
			System.out.print("정렬방식 선택: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());	
			}catch(InputMismatchException e) {
				System.out.println("경고: 숫자를 입력 해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("경고: 숫자를 입력 해주세요!");
				continue;
			}
		
		if(no>=1 && no<=2) {
			flag=true;
		}else {
			System.out.println("경고: 1~2 사이의 숫자 입력하세요");
		}
		
	}//end of while		
		
		
		//2.(정렬된 상태의) 출력문을 가져온다
		list = DBController.studentDataSort(no);
		
		if(list.size()<=0) {
			System.out.println("정렬할 내용이 없습니다.");
			return;
		}
		
		for(StudentDataModel data: list) {
			System.out.println(data.toString());
		}
				
		return;		
		
	}


	//출력
	private static void studentDataPrint() {
		
	List<StudentDataModel> list = new ArrayList<StudentDataModel>();
	list = DBController.studentDataSelect();
		
	if(list.size()<=0) {
		System.out.println("출력할 성적이 없습니다.");
		return;
	}

	System.out.println("----------------------------------------------------------------------------------");
	System.out.println(" 이름     학번     성별     학년     국어     영어     수학     총점           평균     등급");
	System.out.println("----------------------------------------------------------------------------------");
	for(StudentDataModel data:list) {
		System.out.println(data.toString());
	}
}


	//수정(성적)
	private static void studentDataModify() {
		
		final int ID_NUM=2;
		
		//1. 수정 대상자 학번 검색
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
			System.out.println("수정 대상자 학번 입력: ");
			id = scan.nextLine();
					
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("다시 입력해주세요");
			}				
		}
	
		searchData=id;
		number=ID_NUM;	
	
		list = DBController.studentDataSearch(searchData,number);
		
		if(list.size()<=0) {
			System.out.println("대상자를 찾지 못했습니다");
			return;
		}
		
		StudentDataModel data_buffer = null;	
		
		//수정 대상자의 정보 전달
	for(StudentDataModel data : list) {
			data_buffer = data;
		}
				
		
		
	///2. 수정 대상 학생의 성적 수정
		System.out.println(data_buffer.getName()+"의 현재 성적 [국어: "+data_buffer.getKor()+" 영어: "+data_buffer.getEng()+" 수학: "+data_buffer.getMath()+"]");
	
		while(true) {
			System.out.println("수정할 국어점수 입력:");
			kor = scan.nextInt();
			
			if(kor>=0 && kor<=100) {
				break;
			}	
		}

		while(true) {
			System.out.println("수정할 영어점수 입력:");
			eng = scan.nextInt();
			
			if(eng>=0 && eng<=100) {
				break;
			}	
		}
		
		while(true) {
			System.out.println("수정할 수학점수 입력:");
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
		
		
	//3. 결과값 확인
	resultNumber=DBController.studentDataModify(id,kor,eng,math,total,avr,scoreGrade);
	
	if(resultNumber != 0) {
		System.out.println("성적 수정완료");
		
	}
	else {
		System.out.println("성적 수정실패");
	}
	
	
	return;
	
		
	}
		
	
	//삭제(학번)
	private static void studentDataDelete() {
		
		final int ID_NUM=2;
		String id = null;
		String deleteData = null;
		int number=0;
		int resultNumber=0;
	
		while(true) {
			System.out.println("삭제 대상자의 학번 입력: ");
			id = scan.nextLine();
				
			if(patternCheck(id,ID)) {
				break;
			}
			else {
				System.out.println("다시 입력해주세요");
			}				
		}
		
		
		deleteData=id;
		number=ID_NUM;	
		resultNumber = DBController.studentDataDelete(deleteData,number);
		
		if(resultNumber != 0) {
			System.out.println("학번: "+id+" 삭제완료");
		}
		else {
			System.out.println("학번: "+id+" 삭제실패");
		}
		
		
		return;	
		
	}

	
	//검색(이름,학번)
	private static void studentDataSearch() {

		final int NAME_NUM=1,ID_NUM=2,EXIT=3;
		
		List<StudentDataModel>list = new ArrayList<StudentDataModel>();
		
		//검색 기준 선택(name,id)
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
					System.out.println("검색 대상자의 이름 입력: ");
					name=scan.nextLine();
					
					if(patternCheck(name,NAME)) {
						break;
					}
					else {
						System.out.println("다시 입력해주세요");
					}
					
				}		
				
				searchData=name;
				number=NAME_NUM;
				break;
			
			case ID_NUM: 
				while(true) {
					System.out.println("검색 대상자의 학번 입력: ");
					id=scan.nextLine();
					
					if(patternCheck(id,ID)) {
						break;
					}
					else {
						System.out.println("다시 입력해주세요");
					}
					
				}//end of id

				searchData=id;
				number=ID_NUM;
				break;
				
			case EXIT: 
				System.out.println("검색 취소");
				flag=true;
				break;
		
		}//END OF SWITCH
		
		if(flag) {
			return;
		}
		
		list = DBController.studentDataSearch(searchData,number);
		
		if(list.size()<=0) {
			System.out.println(searchData+"검색 실패");
		}
		
		
		//검색한 내용 출력
		for(StudentDataModel data : list) {
			System.out.println(data);
		}
		
	
	}


	//검색메뉴선택
	private static int searchMenu() {
		
		boolean flag=false;
		int no=0;
		
		while(!flag) {
			
			System.out.println("******************");
			System.out.println("1.이름 2.학번 3.종료");
			System.out.println("******************");
			System.out.println("실행할 메뉴 선택: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());	
			}catch(InputMismatchException e) {
				System.out.println("경고: 숫자를 입력 해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("경고: 숫자를 입력 해주세요!");
				continue;
			}
		
		if(no>=1 && no<=3) {
			flag=true;
		}else {
			System.out.println("경고: 1~3 사이의 숫자 입력하세요");
		}
		
	}//end of while
		
		return no;		
		
	}
		

	//패턴체크 함수(이름,학번)
	private static boolean patternCheck(String patternData, int patternType) {
			
		String filter = null;
		switch(patternType) {
		case NAME: filter= "^[가-힣]{2,5}$"; break;
		case ID: filter= "\\d{4}"; break;
		}
			
		Pattern pattern = Pattern.compile(filter);
	    Matcher matcher = pattern.matcher(patternData);
		 
		return matcher.matches();
			
	}

	
	//전체메뉴선택
	private static int selectMenu() {
		
		boolean flag=false;
		int no =0;
		
		while(!flag) {
			
			System.out.println("**********************************************");
			System.out.println("1.입력 2.검색 3.삭제 4.수정 5.출력 6.정렬 7.종료");
			System.out.println("**********************************************");
			System.out.println("실행할 메뉴 선택: ");
			
			try {
				no = Integer.parseInt(scan.nextLine());
			}catch(InputMismatchException e) {
				System.out.println("경고: 숫자를 입력 해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("경고: 숫자를 입력 해주세요!");
				continue;
			}
		
		if(no>=1 && no<=7) {
			flag=true;
		}else {
			System.out.println("경고: 1~7 사이의 숫자 입력하세요");
		}
		
	}//end of while
		
		return no;
		
	}//end of selcetMenu

	





}//end of class
