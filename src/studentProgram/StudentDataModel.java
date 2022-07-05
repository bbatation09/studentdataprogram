package studentProgram;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StudentDataModel implements Comparable<Object>,Serializable {
	
	public final static int SUBJECT=3;
	
	//name id gender schoolGrade kor eng math total avr scoreGrade
	private String name;
	private String id;
	private String gender;
	private int schoolGrade;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double avr;
	private String scoreGrade;
	

	//오버로딩한 생성자
	public StudentDataModel(String name, String id, String gender, int schoolGrade, int kor, int eng, int math,
			int total, double avr, String scoreGrade) {
		super();
		this.name = name;
		this.id = id;
		this.gender = gender;
		this.schoolGrade = schoolGrade;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total =total;
		this.avr = avr;
		this.scoreGrade = scoreGrade;
	}




	//id
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	//id
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentDataModel)) {
			throw new IllegalArgumentException("without PhoneBookModel class");
		}
		StudentDataModel studentDataModel=(StudentDataModel) obj;
		return this.id.equals(studentDataModel.id);
	}

	
	//name
	@Override
	public int compareTo(Object o){
		
		if(!(o instanceof StudentDataModel)) {
			throw new IllegalArgumentException("without StudentDataModel class");
		}
		StudentDataModel studentDataModel =(StudentDataModel) o;
		return this.name.compareTo(studentDataModel.name);
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getSchoolGrade() {
		return schoolGrade;
	}


	public void setSchoolGrade(int schoolGrade) {
		this.schoolGrade = schoolGrade;
	}


	public int getKor() {
		return kor;
	}


	public void setKor(int kor) {
		this.kor = kor;
	}


	public int getEng() {
		return eng;
	}


	public void setEng(int eng) {
		this.eng = eng;
	}


	public int getMath() {
		return math;
	}


	public void setMath(int math) {
		this.math = math;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public double getAvr() {
		return avr;
	}


	public void setAvr(double avr) {
		this.avr = avr;
	}


	public String getScoreGrade() {
		return scoreGrade;
	}


	public void setScoreGrade(String scoreGrade) {
		this.scoreGrade = scoreGrade;
	}


	@Override
	public String toString() {
	return name + "\t" + id + "\t" + gender+ "\t" + schoolGrade +"학년\t" 
	            + kor +"점\t" + eng +"점\t" + math +"점\t" + total
				+"점\t" + String.format("%10.2f", avr) +"점\t" +scoreGrade;

	}
	
	
	
	//총점함수
	public void calTotal() {
	      this.total = this.kor + this.math + this.eng;
	   }
	   
	//평균계산함수
	public void calAvg() {
	      this.avr = this.total/(double)SUBJECT;
	   }
	   
	//등급계산함수
	public void calGrade() {
	      if(this.avr>=90) {
	         this.scoreGrade = "A";
	      }
	      else if(this.avr>=80 && this.avr<90) {
	         this.scoreGrade = "B";
	      }
	      else if(this.avr>=70 && this.avr<80) {
	         this.scoreGrade = "C";
	      }
	      else if(this.avr>=60 && this.avr<70) {
	         this.scoreGrade = "D";
	      }
	      else {
	         this.scoreGrade = "F";
	      }
	   }
	

}
