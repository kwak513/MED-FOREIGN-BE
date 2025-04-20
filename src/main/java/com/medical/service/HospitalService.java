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
	
	

	// 강남구와 강동구 병원 정보 가져오기(병원명, 시·구 주소, 가능 언어, 대표과 1개), 메인 리스트 페이지에서 사용할거임.
	public List<Map<String, Object>> select15FromGangnamGangDongHospital(int offsetNum){
		
		try {
			String sql = 
						"(SELECT gangdong_name AS hospital_name, "
						+ "gangdong_languages AS hospital_languages, "
						+ "gangdong_main_address AS hospital_main_address, "
						+ "SUBSTRING_INDEX(gangdong_category, ',', 1) AS hospital_main_category, "
						+ "'gangdong' AS source "
						+ "FROM gangdong_hospital "
						+ "LIMIT 15 OFFSET :OFFSET) "
						+ "UNION "
						+ "(SELECT gangnam_name AS hospital_name, "
						+ "gangnam_languages AS hospital_languages, "
						+ "gangnam_main_address AS hospital_main_address, "
						+ "SUBSTRING_INDEX(gangnam_category, ',', 1) AS hospital_main_category, "
						+ "'gangnam' AS source "
						+ "FROM gangnam_hospital "
						+ "LIMIT 15 OFFSET :OFFSET);"
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

	
	
}
