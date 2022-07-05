package studentProgram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBController {

	//1-����
	public static int studentDataInsert(StudentDataModel studentDataModel) {
	
		//1. �����ͺ��̽� ���ӿ�û: �����ͺ��̽��� ������ �� �ִ� key�� con
		Connection con = DBUtility.getConnection();
		//�����ͺ��̽� ��ɹ� ���ϰ�
		int returnValue=0;
		
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
		
		
		//2. ���� ��ɹ� �ϴ�
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
			
			//3. ��ɹ� ���� �� ������ ���� ������ ���� ���� �ޱ�
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
		}//ps�� con�� null�̸� ������ �ȵ�
		
	    //4. ������� �뺸�Ѵ�
		return returnValue;
	}
	
	//5-���
    public static List<StudentDataModel> studentDataSelect() {

		//���̺� �ִ� ���ڵ���� �������� ���� ArrayList <PhoneBookModel> 
		 List <StudentDataModel> list = new ArrayList <StudentDataModel>();
		
		 //1. �����ͺ��̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		
		//�����ͺ��̽� ��ɹ� ���ϰ�
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		
		//2. select ������ɹ� �ϴ�
		String selectQuery= "select * from studentdatadb.studentdatatbl";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(selectQuery);
			
			//3. ��ɹ� ���� �� ������ ���� ������ ���� ���� �ޱ�
			resultSet=ps.executeQuery(); 
			
			//���ڵ���� ����Ʈ�� ������
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
		
	    //4. ������� �뺸�Ѵ�
		return list;			
		
		
	}
	
	
	//2-�˻� 
	public static List<StudentDataModel> studentDataSearch(String searchData, int number) {
		
		final int NAME_NUM=1,ID_NUM=2;
		
		//���ڵ���� �����ϱ� ���� list collection
		List<StudentDataModel> list = new ArrayList<StudentDataModel>();
		
		//1.�����ͺ��̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		//2. �˻���ɹ� �ϴ�
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
			
			//3. ��ɹ� ���� �� ������ ���� ������ ���� ���� �ޱ�
			resultSet =ps.executeQuery(); 
			
			//3-1. ���ϰ� resultset�� Arraylist<PhoneBookModel>�� ��ȯ
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
				//������� ���ڵ���� list�� ����
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
		
		//4. ����� �뺸
		return list;
	}

	//3-����
	public static int studentDataDelete(String deleteData, int number) {
		
		final int ID_NUM=2; 
		
		//1.�����ͺ��̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
		
		//2. �˻���ɹ� �ϴ�
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
			
			//3. ���Ե� ���� ����
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
		
		//4. ����� �뺸
		return resultNumber; 		
		
		
		
		
		
	}

	//6-����
	public static List<StudentDataModel> studentDataSort(int no) {
		final int ASC=1,DESC=2;
		
		//���̺� �ִ� ���ڵ� ���� �������� ���� ArrayList <PhoneBookModel> 
		 List <StudentDataModel> list = new ArrayList <StudentDataModel>();
		 
		 String sortQuery= null;
		 
		 //1. �����ͺ��̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		
		//�����ͺ��̽� ��ɹ� ���ϰ�
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		
		//2. sort ������ɹ� �ϴ�
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
			
			//3. ��ɹ� ���� �� ���ϰ� ����
			resultSet=ps.executeQuery(); 
			
			//���ڵ���� ����Ʈ�� ������
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
		
	    //4. ������� �뺸�Ѵ�
		return list;		
		
	}

	
    //4-����
    public static int studentDataModify(String id, int kor, int eng, int math,int total,double avr,String scoreGrade) {

	final int ID_NUM=2; 
	
	//1.�����ͺ��̽� ���ӿ�û
	Connection con = DBUtility.getConnection();
	if(con == null) {
		System.out.println("Mysql Connection Fail");
		return 0;
	}
	
	//2. ������ɹ� �ϴ�
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
		
		
		//3. ���Ե� ���� ����
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
	
	//4. ����� �뺸
	return resultNumber; 	

}



}
