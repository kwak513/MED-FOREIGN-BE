package com.medical.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.medical.common.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

@Service
public class HospitalService {
	@PersistenceContext
	EntityManager em;
	
	

	// 메인 리스트 페이지에서 사용할 강남구와 강동구 병원 정보 가져오기(id, 병원명, 시·구 주소, 가능 언어, 대표과 1개)
	public List<Map<String, Object>> select15FromGangnamGangDongHospital(int offsetNum){
		
		try {
			String sql = 
					
						"(SELECT gangdong_name AS hospital_name, "
						+ "gangdong_languages AS hospital_languages, "
						+ "gangdong_main_address AS hospital_main_address, "
						+ "SUBSTRING_INDEX(gangdong_category, ',', 1) AS hospital_main_category, "
						+ "'gangdong' AS source, "
						+ "id AS hospital_id "
						+ "FROM gangdong_hospital "
						+ "ORDER BY hospital_id ASC "
						+ "LIMIT 15 OFFSET :OFFSET) "
						+ "UNION "
						+ "(SELECT gangnam_name AS hospital_name, "
						+ "gangnam_languages AS hospital_languages, "
						+ "gangnam_main_address AS hospital_main_address, "
						+ "SUBSTRING_INDEX(gangnam_category, ',', 1) AS hospital_main_category, "
						+ "'gangnam' AS source, "
						+ "id AS hospital_id "
						+ "FROM gangnam_hospital "
						+ "ORDER BY hospital_id ASC "
						+ "LIMIT 15 OFFSET :OFFSET);"
						
//						 강남구 데이터 먼저 
//						"(SELECT gangnam_name AS hospital_name, "
//						+ "gangnam_languages AS hospital_languages, "
//						+ "gangnam_main_address AS hospital_main_address, "
//						+ "SUBSTRING_INDEX(gangnam_category, ',', 1) AS hospital_main_category, "
//						+ "'gangnam' AS source, "
//						+ "id AS hospital_id "
//						+ "FROM gangnam_hospital "
//						+ "ORDER BY hospital_id ASC "
//						+ "LIMIT 15 OFFSET :OFFSET) "
//						+ "UNION "
//						+"(SELECT gangdong_name AS hospital_name, "
//						+ "gangdong_languages AS hospital_languages, "
//						+ "gangdong_main_address AS hospital_main_address, "
//						+ "SUBSTRING_INDEX(gangdong_category, ',', 1) AS hospital_main_category, "
//						+ "'gangdong' AS source, "
//						+ "id AS hospital_id "
//						+ "FROM gangdong_hospital "
//						+ "ORDER BY hospital_id ASC "
//						+ "LIMIT 15 OFFSET :OFFSET);"
					;
			Query query = em.createNativeQuery(sql, Tuple.class);
			query.setParameter("OFFSET", offsetNum);
		
			List<Tuple> rs = query.getResultList();
			
			List<Map<String, Object>> rsToMap = JPAUtil.convertTupleToMap(rs);
			return rsToMap;
			
		} catch(Exception e) {
			System.out.println("select15FromGangnamGangDongHospital failed: "+ e.getMessage());
			return new ArrayList<>();
		}
	}

	// 병원 상세 페이지에서 사용할 '강남구' 병원 정보 가져오기
	public List<Map<String, Object>> selectFromGangnamHospital(int hospitalId){
		
		try {
			String sql = "SELECT gangnam_name AS hospital_name, "
					+ "gangnam_phone_number AS hospital_phone_number, "
					+ "gangnam_languages AS hospital_languages, "
					+ "gangnam_main_address AS hospital_main_address, "
					+ "gangnam_address AS hospital_address, "
					+ "gangnam_category AS hospital_category "
					+ "FROM gangnam_hospital WHERE ID = :ID";;
			Query query = em.createNativeQuery(sql, Tuple.class);
			query.setParameter("ID", hospitalId);
		
			List<Tuple> rs = query.getResultList();
			
			List<Map<String, Object>> rsToMap = JPAUtil.convertTupleToMap(rs);
			return rsToMap;
			
		} catch(Exception e) {
			System.out.println("selectFromGangnamHospital failed: "+ e.getMessage());
			return new ArrayList<>();
		}
	}

	
	// 병원 상세 페이지에서 사용할 '강동구' 병원 정보 가져오기
	public List<Map<String, Object>> selectFromGangdongHospital(int hospitalId){
		
		try {
			String sql = "SELECT gangdong_name AS hospital_name, "
					+ "gangdong_phone_number AS hospital_phone_number, "
					+ "gangdong_languages AS hospital_languages, "
					+ "gangdong_main_address AS hospital_main_address, "
					+ "gangdong_address AS hospital_address, "
					+ "gangdong_category AS hospital_category "
					+ "FROM gangdong_hospital WHERE ID = :ID";
			
			
			Query query = em.createNativeQuery(sql, Tuple.class);
			query.setParameter("ID", hospitalId);
		
			List<Tuple> rs = query.getResultList();
			
			List<Map<String, Object>> rsToMap = JPAUtil.convertTupleToMap(rs);
			return rsToMap;
			
		} catch(Exception e) {
			System.out.println("selectFromGangdongHospital failed: "+ e.getMessage());
			return new ArrayList<>();
		}
	}

//	// 병원명 검색하기
//	public List<Map<String, Object>> selectByHospitalName(String hospitalName, int offsetNum){
//		
//		try {
//			String sql = "(SELECT gangdong_name AS hospital_name, "
//						+ "gangdong_languages AS hospital_languages, "
//						+ "gangdong_main_address AS hospital_main_address, "
//						+ "SUBSTRING_INDEX(gangdong_category, ',', 1) AS hospital_main_category, "
//						+ "'gangdong' AS source, "
//						+ "id AS hospital_id "
//						+"FROM gangdong_hospital "
//						+"WHERE gangdong_name LIKE CONCAT('%', :hospital_name, '%') "
//						+ "ORDER BY id ASC "
//						+ "LIMIT 15 OFFSET :OFFSET) "
//						+ "UNION "
//						+"(SELECT gangnam_name AS hospital_name, "
//						+ "gangnam_languages AS hospital_languages, "
//						+ "gangnam_main_address AS hospital_main_address, "
//						+ "SUBSTRING_INDEX(gangnam_category, ',', 1) AS hospital_main_category, "
//						+ "'gangnam' AS source, "
//						+ "id AS hospital_id "
//						+"FROM gangnam_hospital "
//						+"WHERE gangnam_name LIKE CONCAT('%', :hospital_name, '%') "
//						+ "ORDER BY id ASC "
//						+ "LIMIT 15 OFFSET :OFFSET) "
//						;
//					
//			
//			
//			
//			Query query = em.createNativeQuery(sql, Tuple.class);
//			query.setParameter("hospital_name", hospitalName);
//			query.setParameter("OFFSET", offsetNum);
//		
//			List<Tuple> rs = query.getResultList();
//			
//			List<Map<String, Object>> rsToMap = JPAUtil.convertTupleToMap(rs);
//			return rsToMap;
//			
//		} catch(Exception e) {
//			System.out.println("selectByHospitalName failed: "+ e.getMessage());
//			return new ArrayList<>();
//		}
//	}
//
//	// 필터링 기능(사용 언어, 진료과목, 지역)
//	public List<Map<String, Object>> filterHospitalByLangDepartLocation(String language, String department, String location, int offsetNum){
//		
//		try {
//			String sql = ""
//			        + "(SELECT gangdong_name AS hospital_name, "
//			        + "gangdong_languages AS hospital_languages, "
//			        + "gangdong_main_address AS hospital_main_address, "
//			        + "SUBSTRING_INDEX(gangdong_category, ',', 1) AS hospital_main_category, "
//			        + "'gangdong' AS source, "
//			        + "id AS hospital_id "
//			        + "FROM gangdong_hospital "
//			        + "WHERE (:LANGUAGE IS NULL OR gangdong_languages LIKE CONCAT('%', :LANGUAGE, '%')) "
//			        + "  AND (:DEPARTMENT IS NULL OR gangdong_category LIKE CONCAT('%', :DEPARTMENT, '%')) "
//			        + "  AND (:LOCATION IS NULL OR gangdong_main_address LIKE CONCAT('%', :LOCATION, '%')) "
//			        + "ORDER BY hospital_id ASC "
//			        + "LIMIT 15 OFFSET :OFFSET) "
//			        + "UNION ALL"
//			        + "(SELECT gangnam_name AS hospital_name, "
//			        + "gangnam_languages AS hospital_languages, "
//			        + "gangnam_main_address AS hospital_main_address, "
//			        + "SUBSTRING_INDEX(gangnam_category, ',', 1) AS hospital_main_category, "
//			        + "'gangnam' AS source, "
//			        + "id AS hospital_id "
//			        + "FROM gangnam_hospital "
//			        + "WHERE (:LANGUAGE IS NULL OR gangnam_languages LIKE CONCAT('%', "
//			        + "    CASE :LANGUAGE "
//			        + "        WHEN '영어' THEN '미국' "
//			        + "        WHEN '일어' THEN '일본' "
//			        + "        WHEN '중국어' THEN '중국' "
//			        + "        WHEN '러시아어' THEN '러시아' "
//			        + "        WHEN '중동어' THEN '중동' "
//			        + "        WHEN '몽골어' THEN '몽골' "
//			        + "        WHEN '베트남어' THEN '베트남' "
//			        + "        ELSE :LANGUAGE "
//			        + "    END, "
//			        + "    '%')) "
//			        + "  AND (:DEPARTMENT IS NULL OR gangnam_category LIKE CONCAT('%', :DEPARTMENT, '%')) "
//			        + "  AND (:LOCATION IS NULL OR gangnam_main_address LIKE CONCAT('%', :LOCATION, '%')) "
//			        + "ORDER BY hospital_id ASC "
//			        + "LIMIT 15 OFFSET :OFFSET);";
//					
//			Query query = em.createNativeQuery(sql, Tuple.class);
//			query.setParameter("LANGUAGE", language.trim());
//			query.setParameter("DEPARTMENT", department.trim());
//			query.setParameter("LOCATION", location.trim());
//			query.setParameter("OFFSET", offsetNum);
//		
//			List<Tuple> rs = query.getResultList();
//			
//			List<Map<String, Object>> rsToMap = JPAUtil.convertTupleToMap(rs);
//			return rsToMap;
//			
//		} catch(Exception e) {
//			System.out.println("filterHospitalByLangDepartLocation failed: "+ e.getMessage());
//			return new ArrayList<>();
//		}
//	}
	
	// 병원 & 필터링 기능(사용 언어, 진료과목, 지역) 동시에.
		public List<Map<String, Object>> searchAndFilterHospital(String hospitalName, String language, String department, String location, int offsetNum){
			
			try {
				String sql = 
						"(SELECT gangdong_name AS hospital_name, "
				        + "gangdong_languages AS hospital_languages, "
				        + "gangdong_main_address AS hospital_main_address, "
				        + "SUBSTRING_INDEX(gangdong_category, ',', 1) AS hospital_main_category, "
				        + "'gangdong' AS source, "
				        + "id AS hospital_id "
				        + "FROM gangdong_hospital "
				        + "WHERE (:LANGUAGE IS NULL OR gangdong_languages LIKE CONCAT('%', :LANGUAGE, '%')) "
				        + "  AND (:DEPARTMENT IS NULL OR gangdong_category LIKE CONCAT('%', :DEPARTMENT, '%')) "
				        + "  AND (:LOCATION IS NULL OR gangdong_main_address LIKE CONCAT('%', :LOCATION, '%')) "
						+ "  AND (:hospital_name IS NULL OR gangdong_name LIKE CONCAT('%', :hospital_name, '%')) "
				        

				        + "ORDER BY hospital_id ASC "
				        + "LIMIT 15 OFFSET :OFFSET) "
				        + "UNION ALL"
				        + "(SELECT gangnam_name AS hospital_name, "
				        + "gangnam_languages AS hospital_languages, "
				        + "gangnam_main_address AS hospital_main_address, "
				        + "SUBSTRING_INDEX(gangnam_category, ',', 1) AS hospital_main_category, "
				        + "'gangnam' AS source, "
				        + "id AS hospital_id "
				        + "FROM gangnam_hospital "
				        + "WHERE (:LANGUAGE IS NULL OR gangnam_languages LIKE CONCAT('%', "
				        + "    CASE :LANGUAGE "
				        + "        WHEN '영어' THEN '미국' "
				        + "        WHEN '일어' THEN '일본' "
				        + "        WHEN '중국어' THEN '중국' "
				        + "        WHEN '러시아어' THEN '러시아' "
				        + "        WHEN '중동어' THEN '중동' "
				        + "        WHEN '몽골어' THEN '몽골' "
				        + "        WHEN '베트남어' THEN '베트남' "
				        + "        ELSE :LANGUAGE "
				        + "    END, "
				        + "    '%')) "
				        + "  AND (:DEPARTMENT IS NULL OR gangnam_category LIKE CONCAT('%', :DEPARTMENT, '%')) "
				        + "  AND (:LOCATION IS NULL OR gangnam_main_address LIKE CONCAT('%', :LOCATION, '%')) "
						+ "  AND (:hospital_name IS NULL OR gangnam_name LIKE CONCAT('%', :hospital_name, '%')) "
				        + "ORDER BY hospital_id ASC "
				        + "LIMIT 15 OFFSET :OFFSET);";

		        Query query = em.createNativeQuery(sql, Tuple.class);
		        query.setParameter("hospital_name", hospitalName);
		        query.setParameter("LANGUAGE", language.trim());
				query.setParameter("DEPARTMENT", department.trim());
				query.setParameter("LOCATION", location.trim());
		        query.setParameter("OFFSET", offsetNum);

			
				List<Tuple> rs = query.getResultList();
				
				List<Map<String, Object>> rsToMap = JPAUtil.convertTupleToMap(rs);
				return rsToMap;
				
			} catch(Exception e) {
				System.out.println("filterHospitalByLangDepartLocation failed: "+ e.getMessage());
				return new ArrayList<>();
			}
		}

}
