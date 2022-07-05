package studentProgram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBController {

	//1-삽입
	public static int studentDataInsert(StudentDataModel studentDataModel) {
	
		//1. 데이터베이스 접속요청: 데이터베이스에 접속할 수 있는 key인 con
		Connection con = DBUtility.getConnection();
		//데이터베이스 명령문 리턴값
		int returnValue=0;
		
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
		
		
		//2. 삽입 명령문 하달
		String insertQuery= "insert into studentdatadb.studentdatatbl values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		
		try {
			
			//insert query binding
			ps = (PreparedStatement) con.prepareStatement(insertQuery);
			ps.setString(1,studentDataModel.getName()); 
			ps.setString(2,studentDataModel.getId());
			ps.setString(3,studentDataModel.getGender());
			ps.setInt(4,studentDataModel.getSchoolGrade());
			ps.setInt(5,studentDataModel.getKor());
			ps.setInt(6,studentDataModel.getEng());
			ps.setInt(7,studentDataModel.getMath());
			ps.setInt(8,studentDataModel.getTotal());
			ps.setDouble(9,studentDataModel.getAvr());
			ps.setString(10,studentDataModel.getScoreGrade());
			
			//3. 명령문 실행 후 삽입한 행의 갯수에 따라 리턴 받기
			returnValue=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !(ps.isClosed())) {
					ps.close();	
				}
				if(con != null && !(con.isClosed())) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//ps나 con이 null이면 닫으면 안됨
		
	    //4. 결과값을 통보한다
		return returnValue;
	}
	
	//5-출력
    public static List<StudentDataModel> studentDataSelect() {

		//테이블에 있는 레코드셋을 가져오기 위한 ArrayList <PhoneBookModel> 
		 List <StudentDataModel> list = new ArrayList <StudentDataModel>();
		
		 //1. 데이터베이스 접속요청
		Connection con = DBUtility.getConnection();
		
		//데이터베이스 명령문 리턴값
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		
		//2. select 쿼리명령문 하달
		String selectQuery= "select * from studentdatadb.studentdatatbl";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(selectQuery);
			
			//3. 명령문 실행 후 삽입한 행의 갯수에 따라 리턴 받기
			resultSet=ps.executeQuery(); 
			
			//레코드셋을 리스트로 가져옴
			while(resultSet.next()) {
				String name = resultSet.getString(1); 
				String id = resultSet.getString(2);
				String gender = resultSet.getString(3);
				int schoolGrade = resultSet.getInt(4);
				int kor = resultSet.getInt(5);
				int eng = resultSet.getInt(6);
				int math = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avr = resultSet.getDouble(9);
				String scoreGrade = resultSet.getString(10);
				
				StudentDataModel studentDataModel= new StudentDataModel(name, id, gender, schoolGrade, kor, eng, math, total, avr, scoreGrade);
				list.add(studentDataModel);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !(ps.isClosed())) {
					ps.close();	
				}
				if(con != null && !(con.isClosed())) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	    //4. 결과값을 통보한다
		return list;			
		
		
	}
	
	
	//2-검색 
	public static List<StudentDataModel> studentDataSearch(String searchData, int number) {
		
		final int NAME_NUM=1,ID_NUM=2;
		
		//레코드셋을 저장하기 위한 list collection
		List<StudentDataModel> list = new ArrayList<StudentDataModel>();
		
		//1.데이터베이스 접속요청
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		//2. 검색명령문 하달
		String searchQuery = null; 
		
		PreparedStatement ps = null; 
		ResultSet resultSet = null; 
		
		
		switch(number) {
		case NAME_NUM: 
			searchQuery = "select * from studentdatadb.studentdatatbl where name like ?";
			break;
		
		case ID_NUM: 
			searchQuery= "select * from studentdatadb.studentdatatbl where id like ?";
			break;
		
		}
		
	
		try {
			ps = (PreparedStatement) con.prepareStatement(searchQuery);
			searchData="%"+searchData+"%";
			ps.setString(1,searchData);	
			
			//3. 명령문 실행 후 삽입한 행의 갯수에 따라 리턴 받기
			resultSet =ps.executeQuery(); 
			
			//3-1. 리턴값 resultset을 Arraylist<PhoneBookModel>로 변환
			while(resultSet.next()) {
				String name = resultSet.getString(1); 
				String id = resultSet.getString(2);
				String gender = resultSet.getString(3);
				int schoolGrade = resultSet.getInt(4);
				int kor = resultSet.getInt(5);
				int eng = resultSet.getInt(6);
				int math = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avr = resultSet.getDouble(9);
				String scoreGrade = resultSet.getString(10);
				
				 StudentDataModel studentDataModel = new StudentDataModel(name, id, gender, schoolGrade, kor, eng, math, total, avr, scoreGrade);
				 list.add(studentDataModel);
				//만들어진 레코드셋을 list에 저장
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !(ps.isClosed())) {
					ps.close();	
				}
				if(con != null && !(con.isClosed())) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//4. 결과값 통보
		return list;
	}

	//3-삭제
	public static int studentDataDelete(String deleteData, int number) {
		
		final int ID_NUM=2; 
		
		//1.데이터베이스 접속요청
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
		
		//2. 검색명령문 하달
		String delteQuery = null; 
		PreparedStatement ps = null;
		int resultNumber = 0;
		
		switch(number) {
		case ID_NUM: 
			delteQuery = "delete from studentdatadb.studentdatatbl where id like ?";
			break;
		}
		
		try {
			ps = (PreparedStatement) con.prepareStatement(delteQuery);
			deleteData="%"+deleteData+"%";
			ps.setString(1,deleteData);
			
			//3. 삽입된 갯수 리턴
			resultNumber =ps.executeUpdate();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !(ps.isClosed())) {
					ps.close();	
				}
				if(con != null && !(con.isClosed())) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//4. 결과값 통보
		return resultNumber; 		
		
		
		
		
		
	}

	//6-정렬
	public static List<StudentDataModel> studentDataSort(int no) {
		final int ASC=1,DESC=2;
		
		//테이블에 있는 레코드 셋을 가져오기 위한 ArrayList <PhoneBookModel> 
		 List <StudentDataModel> list = new ArrayList <StudentDataModel>();
		 
		 String sortQuery= null;
		 
		 //1. 데이터베이스 접속요청
		Connection con = DBUtility.getConnection();
		
		//데이터베이스 명령문 리턴값
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		
		//2. sort 쿼리명령문 하달
		switch(no) {
		case ASC: 
			sortQuery= "select * from studentdatadb.studentdatatbl order by total asc";
			break;
		case DESC:
			sortQuery= "select * from studentdatadb.studentdatatbl order by total desc";
			break;
		}
		
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			
			ps = (PreparedStatement) con.prepareStatement(sortQuery);
			
			//3. 명령문 실행 후 리턴값 저장
			resultSet=ps.executeQuery(); 
			
			//레코드셋을 리스트로 가져옴
			while(resultSet.next()) {
				String name = resultSet.getString(1); 
				String id = resultSet.getString(2);
				String gender = resultSet.getString(3);
				int schoolGrade = resultSet.getInt(4);
				int kor = resultSet.getInt(5);
				int eng = resultSet.getInt(6);
				int math = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avr = resultSet.getDouble(9);
				String scoreGrade = resultSet.getString(10);
				
				StudentDataModel studentDataModel = new StudentDataModel(name, id, gender, schoolGrade, kor, eng, math, total, avr, scoreGrade);
				list.add(studentDataModel);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !(ps.isClosed())) {
					ps.close();	
				}
				if(con != null && !(con.isClosed())) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	    //4. 결과값을 통보한다
		return list;		
		
	}

	
    //4-수정
    public static int studentDataModify(String id, int kor, int eng, int math,int total,double avr,String scoreGrade) {

	final int ID_NUM=2; 
	
	//1.데이터베이스 접속요청
	Connection con = DBUtility.getConnection();
	if(con == null) {
		System.out.println("Mysql Connection Fail");
		return 0;
	}
	
	//2. 수정명령문 하달
	String updateQuery = null; 
	PreparedStatement ps = null;
	int resultNumber = 0;
	
	switch(ID_NUM) {
	case ID_NUM: 
		updateQuery = "update studentdatadb.studentdatatbl set kor = ?, eng = ?, math= ?, total= ?, avr = ?, scoreGrade=?  where id = ? ";
		
		break;
	}
	
	try {
		ps = (PreparedStatement) con.prepareStatement(updateQuery);
		ps.setInt(1,kor);
		ps.setInt(2,eng);
		ps.setInt(3,math);
		ps.setInt(4,total);
		ps.setDouble(5,avr);
		ps.setString(6,scoreGrade);
		ps.setString(7,id);
		
		
		//3. 삽입된 갯수 리턴
		resultNumber =ps.executeUpdate();
	
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if(ps != null && !(ps.isClosed())) {
				ps.close();	
			}
			if(con != null && !(con.isClosed())) {
				con.close();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//4. 결과값 통보
	return resultNumber; 	

}



}
