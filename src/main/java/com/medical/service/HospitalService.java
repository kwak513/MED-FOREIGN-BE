package com.medical.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.common.JPAUtil;
import com.medical.dto.HospitalReservationDto;
import com.medical.dto.HospitalReviewDto;
import com.medical.dto.MemberRegisterDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

@Service
public class HospitalService {
	@PersistenceContext
	EntityManager em;
	@Autowired
	PasswordEncoder passwordEncoder;
	
//------------------------ 병원 관련 ------------------------	

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

		
//------------------------ 회원 관련 ------------------------	
	
	// 회원가입	
	@Transactional
	public boolean memberRegister(MemberRegisterDto memberRegisterDto){
		
		try {
			// 이미 가입된 회원 있는지 확인
			String sql = "SELECT COUNT(*) FROM MEMBER WHERE USERNAME = :USERNAME";
					

	        Query query = em.createNativeQuery(sql);
	        query.setParameter("USERNAME", memberRegisterDto.getUsername());
	        
	        Long rs = (Long) query.getSingleResult();
	        
	        if(rs > 0) {
	        	System.out.println("이미 존재하는 회원입니다. 다른 아이디를 입력하세요.");
	        	return false;
	        }
			
	        else {
	        	String hashedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());

                String sql2 = "INSERT INTO MEMBER (USERNAME, PASSWORD) VALUES (:USERNAME, :PASSWORD)";
                Query query2 = em.createNativeQuery(sql2);
                query2.setParameter("USERNAME", memberRegisterDto.getUsername());
                query2.setParameter("PASSWORD", hashedPassword); // 해싱된 비밀번호 저장

                int registerNum = query2.executeUpdate();
		        
		        if(registerNum == 1) {
		        	System.out.println("회원가입 성공");
		        	return true;
		        }
	        	
		        return false;
	        }
			
		} catch(Exception e) {
			System.out.println("memberRegister failed: "+ e.getMessage());
			return false;
		}
	}
	
	// 로그인	
	public int memberLogin(String username, String password){
		// 유효성 체크
	    if (username == null || username.trim().isEmpty()) {
	        return -1;
	    }
	    if (password == null || password.trim().isEmpty()) {
	        return -1;
	    }
		
		try {
			// 이미 가입된 회원 있는지 확인
			String sql = "SELECT id, username, password FROM MEMBER WHERE USERNAME = :USERNAME";
					
	        Query query = em.createNativeQuery(sql, Tuple.class);
	        query.setParameter("USERNAME", username);
	        
	        Tuple rs = (Tuple) query.getSingleResult();
	        Map<String, Object> rsToMap = JPAUtil.convertTupleToMap(rs);
			
	        Integer id =  (Integer) rsToMap.get("id");
	        String usernameResult = (String) rsToMap.get("username");
	        String passwordResult = (String) rsToMap.get("password");
	        
	        // 비밀번호 확인 (저장된 해시값과 입력된 비밀번호 비교)
            if (passwordEncoder.matches(password, passwordResult)) {
                System.out.println("로그인 성공: " + usernameResult);
                // 필요한 경우 id나 usernameResult를 반환하거나 세션에 저장하는 등의 처리
                
                
                
                return id;
            } else {
                System.out.println("존재하지 않는 회원입니다. ");
                return -1;
            }
	
		} catch(Exception e) {
			System.out.println("memberLogin failed: "+ e.getMessage());
			return -1;
		}
	}

// -------------------------- 리뷰  --------------------------
	// text 받으면 어떤 언어인지 탐지해줌.
	private static final String API_URL = "https://ws.detectlanguage.com/0.2/detect";
    private static final String API_KEY = "ebf3e197af3c0e708f2b03f7c14d459e"; 

    public String detectLanguage(String text) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 본문을 Map 형식으로 담아서 보냄
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("q", text);

        // HTTP 헤더에 API 키 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);

        // 요청 본문과 헤더를 HttpEntity에 담아서 전달
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // POST 요청을 보내고 응답을 받음
            ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            // 응답 코드가 200(성공)이면 언어 추출
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                // JSON 응답에서 언어 코드 추출
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response);
                
                JsonNode languageNode = rootNode.path("data").path("detections").get(0).path("language");
                String language = languageNode.asText();
System.out.println("반환 language: " + language);                
                return language; // 예: "en"
            } else {
                return "unknown";  // API 응답이 성공적이지 않으면 "unknown"
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";  // 오류 발생 시 기본값
        }
	}
    
    
	// 병원 리뷰 작성 - hospital_review, gangnam_review/gangdong_review 연결 테이블, member_review 연결 테이블에 insert
	@Transactional
	public boolean insertHospitalReview(HospitalReviewDto hospitalReviewDto){
		
		String originalLanguage = detectLanguage(hospitalReviewDto.getOriginalTxt());
		
		
		try {
			// hospital_review 테이블에 insert
			String sql = "INSERT INTO hospital_review (rate, original_language, original_text) VALUES (:rate, :original_language, :original_text)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("rate", hospitalReviewDto.getRate());
	        query.setParameter("original_language", originalLanguage);
	        query.setParameter("original_text", hospitalReviewDto.getOriginalTxt());
	        
System.out.println("rate: " + hospitalReviewDto.getRate() + "original_language: " + originalLanguage + "original_text: " + hospitalReviewDto.getOriginalTxt()); 
	        int insertReviewNum = query.executeUpdate();
	        
	        // hospital_review의 id 가져오기
	        String getLastIdSql = "SELECT LAST_INSERT_ID()";
	        Query query2 = em.createNativeQuery(getLastIdSql);
	        Long lastReviewId = (Long) query2.getSingleResult();
	        
	        if(insertReviewNum == 1) {
	        	System.out.println("insertHospitalReview 성공");
	        	
	        	// 강남구 병원에 대한 리뷰이면, 
	        	if(hospitalReviewDto.getSource().equals("gangnam")) {
	        		// gangnam_review 연결 테이블에 insert
	        		insertIntoGangnamHospital(hospitalReviewDto.getHospitalId(), lastReviewId);
	        	}
	        	// 강동구 병원에 대한 리뷰이면, 
	        	else if(hospitalReviewDto.getSource().equals("gangdong")) {
	        		// gangdong_review 연결 테이블에 insert
	        		insertIntoGangdongHospital(hospitalReviewDto.getHospitalId(), lastReviewId);
	        	}
	        	
	        	// member_review 연결 테이블에 insert
	        	insertIntoMemberReview(hospitalReviewDto.getMemberId(), lastReviewId);
	        	
	        	
	        	return true;
	        }
	        System.out.println("insertHospitalReview 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertHospitalReview failed: "+ e.getMessage());
			return false;
		}
	}
	
	
	// gangnam_review 연결 테이블에 insert - 단독 실행 X, insertHospitalReview 내부에서 실행됨
	@Transactional
	public boolean insertIntoGangnamHospital(Long hospitalId, Long reviewId){
		
		try {
			String sql = "INSERT INTO gangnam_review (gangnam_id, hospital_review_id) VALUES (:gangnam_id, :hospital_review_id)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("gangnam_id", hospitalId);
	        query.setParameter("hospital_review_id", reviewId);
	        
	        int insertConnectTableNum = query.executeUpdate();
	        
	        if(insertConnectTableNum == 1) {
	        	System.out.println("insertIntoGangnamHospital 성공");
	
	        	return true;
	        }
	        System.out.println("insertIntoGangnamHospital 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertIntoGangnamHospital failed: "+ e.getMessage());
			return false;
		}
	}
	
	// gangdong_review 연결 테이블에 insert - 단독 실행 X, insertHospitalReview 내부에서 실행됨
	@Transactional
	public boolean insertIntoGangdongHospital(Long hospitalId, Long reviewId){
		
		try {
			String sql = "INSERT INTO gangdong_review (gangdong_id, hospital_review_id) VALUES (:gangdong_id, :hospital_review_id)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("gangdong_id", hospitalId);
	        query.setParameter("hospital_review_id", reviewId);
	        
	        int insertConnectTableNum = query.executeUpdate();
	        
	        if(insertConnectTableNum == 1) {
	        	System.out.println("insertIntoGangdongHospital 성공");
	
	        	return true;
	        }
	        System.out.println("insertIntoGangdongHospital 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertIntoGangdongHospital failed: "+ e.getMessage());
			return false;
		}
	}
	
	// member_review 연결 테이블에 insert - 단독 실행 X, insertHospitalReview 내부에서 실행됨
	@Transactional
	public boolean insertIntoMemberReview(Long memberId, Long hospitalReviewId){
		
		try {
			String sql = "INSERT INTO member_review (member_id, hospital_review_id) VALUES (:member_id, :hospital_review_id)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("member_id", memberId);
	        query.setParameter("hospital_review_id", hospitalReviewId);
	        
	        int insertConnectTableNum = query.executeUpdate();
	        
	        if(insertConnectTableNum == 1) {
	        	System.out.println("insertIntoMemberReview 성공");
	
	        	return true;
	        }
	        System.out.println("insertIntoMemberReview 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertIntoMemberReview failed: "+ e.getMessage());
			return false;
		}
	}

	// 병원 id 통해서, hospital_review select 해오기
	public List<Map<String, Object>> selectFromHospitalReview(int hospitalId, String source){
		
		try {
			String sqlForReviewIds = "";

			if(source.equals("gangnam")) {
				sqlForReviewIds = "SELECT hospital_review_id FROM gangnam_review WHERE gangnam_id = :hospital_id";
			} else if(source.equals("gangdong")) {
				sqlForReviewIds = "SELECT hospital_review_id FROM gangdong_review WHERE gangdong_id = :hospital_id";
			} else {
				return new ArrayList<>();
			}

			Query reviewIdQuery = em.createNativeQuery(sqlForReviewIds);
			reviewIdQuery.setParameter("hospital_id", hospitalId);

			List<Integer> reviewIds = reviewIdQuery.getResultList();
System.out.println("reviewIds: " + reviewIds);
			if(reviewIds.isEmpty()) {
				return new ArrayList<>(); // 해당하는 리뷰 ID가 없으면 빈 리스트 반환
			}

			// hospital_review_id 목록으로 hospital_review 테이블에서 모든 정보 조회
			String sqlForReviews = "SELECT * FROM HOSPITAL_REVIEW WHERE id IN (:reviewIds)";
			Query reviewQuery = em.createNativeQuery(sqlForReviews, Tuple.class);
			reviewQuery.setParameter("reviewIds", reviewIds);

			List<Tuple> reviews = reviewQuery.getResultList();
			return JPAUtil.convertTupleToMap(reviews);

		} catch(Exception e) {
			System.out.println("selectFromHospitalReview failed: "+ e.getMessage());
			return new ArrayList<>();
		}
	}
	
	
// -------------------------- 진료예약 관련--------------------------
	// 진료예약 insert - hospital_reservation, gangnam_reservation/gangnam_reservation 연결 테이블, member_reservation 연결 테이블에 insert
	@Transactional
	public boolean insertHospitalReservation(HospitalReservationDto hospitalReservationDto){
		
		
		try {
			// hospital_reservation 테이블에 insert
			String sql = "INSERT INTO hospital_reservation (language, main_symptom, sub_symptom, detail_symptom) VALUES (:language, :main_symptom, :sub_symptom, :detail_symptom)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("language", hospitalReservationDto.getLanguage());
	        query.setParameter("main_symptom", hospitalReservationDto.getMainSymptom());
	        query.setParameter("sub_symptom", hospitalReservationDto.getSubSymptom());
	        query.setParameter("detail_symptom", hospitalReservationDto.getDetailSymptom());
	        
	        int insertReservationNum = query.executeUpdate();
	        
	        // hospital_reservation의 id 가져오기
	        String getLastIdSql = "SELECT LAST_INSERT_ID()";
	        Query query2 = em.createNativeQuery(getLastIdSql);
	        Long lastReservationId = (Long) query2.getSingleResult();
	        
	        if(insertReservationNum == 1) {
	        	
	        	// 강남구 병원에 대한 리뷰이면, 
	        	if(hospitalReservationDto.getSource().equals("gangnam")) {
	        		// gangnam_reservation 연결 테이블에 insert
	        		insertIntoGangnamReservation(hospitalReservationDto.getHospitalId(), lastReservationId);
	        	}
	        	// 강동구 병원에 대한 리뷰이면, 
	        	else if(hospitalReservationDto.getSource().equals("gangdong")) {
	        		// gangdong_reservation 연결 테이블에 insert
	        		insertIntoGangdongReservation(hospitalReservationDto.getHospitalId(), lastReservationId);
	        	}
	        	
	        	// member_reservation 연결 테이블에 insert
	        	insertIntoMemberReservation(hospitalReservationDto.getMemberId(), lastReservationId);
	        	
	        	
	        	return true;
	        }
	        System.out.println("insertHospitalReview 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertHospitalReview failed: "+ e.getMessage());
			return false;
		}
	}
		
		
	// gangnam_reservation 연결 테이블에 insert - 단독 실행 X, insertHospitalReview 내부에서 실행됨
	@Transactional
	public boolean insertIntoGangnamReservation(Long hospitalId, Long lastReservationId){
		
		try {
			String sql = "INSERT INTO gangnam_reservation (gangnam_id, reservation_id) VALUES (:gangnam_id, :reservation_id)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("gangnam_id", hospitalId);
	        query.setParameter("reservation_id", lastReservationId);
	        
	        int insertConnectTableNum = query.executeUpdate();
	        
	        if(insertConnectTableNum == 1) {
	        	System.out.println("insertIntoGangnamReservation 성공");
	
	        	return true;
	        }
	        System.out.println("insertIntoGangnamReservation 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertIntoGangnamReservation failed: "+ e.getMessage());
			return false;
		}
	}
	
	// gangdong_reservation 연결 테이블에 insert - 단독 실행 X, insertHospitalReview 내부에서 실행됨
	@Transactional
	public boolean insertIntoGangdongReservation(Long hospitalId, Long lastReservationId){
		
		try {
			String sql = "INSERT INTO gangdong_reservation (gangdong_id, reservation_id) VALUES (:gangdong_id, :reservation_id)";
					
	        Query query = em.createNativeQuery(sql);
	        query.setParameter("gangdong_id", hospitalId);
	        query.setParameter("reservation_id", lastReservationId);
	        
	        int insertConnectTableNum = query.executeUpdate();
	        
	        if(insertConnectTableNum == 1) {
	        	System.out.println("insertIntoGangdongReservation 성공");
	
	        	return true;
	        }
	        System.out.println("insertIntoGangdongReservation 실패");
	        return false;
	        
		} catch(Exception e) {
			System.out.println("insertIntoGangdongReservation failed: "+ e.getMessage());
			return false;
		}
	}
	
	//  member_reservation 연결 테이블에 insert - 단독 실행 X, insertHospitalReview 내부에서 실행됨
	@Transactional
	public boolean insertIntoMemberReservation(Long memberId, Long lastReservationId){
			
			try {
				String sql = "INSERT INTO member_reservation (member_id, reservation_id) VALUES (:member_id, :reservation_id)";
						
		        Query query = em.createNativeQuery(sql);
		        query.setParameter("member_id", memberId);
		        query.setParameter("reservation_id", lastReservationId);
		        
		        int insertConnectTableNum = query.executeUpdate();
		        
		        if(insertConnectTableNum == 1) {
		        	System.out.println("insertIntoMemberReservation 성공");
		
		        	return true;
		        }
		        System.out.println("insertIntoMemberReservation 실패");
		        return false;
		        
			} catch(Exception e) {
				System.out.println("insertIntoMemberReservation failed: "+ e.getMessage());
				return false;
			}
		}

	// member id 통해서, hospital_reservation select 해오기
	public List<Map<String, Object>> selectFromHospitalReservation(int memberId, String source){
		
		try {
	        String sql = "";

	        if ("gangnam".equals(source)) {
	            sql = "SELECT " +
	                  "   hr.language AS language, " +
	                  "   hr.main_symptom AS main_symptom, " +
	                  "   hr.sub_symptom AS sub_symptom, " +
	                  "   hr.detail_symptom AS detail_symptom, " +
	                  "   gh.gangnam_name AS hospital_name " +
	                  "FROM " +
	                  "   member_reservation mr " +
	                  "JOIN " +
	                  "   hospital_reservation hr ON mr.reservation_id = hr.id " +
	                  "LEFT JOIN " +
	                  "   gangnam_reservation gr ON hr.id = gr.reservation_id " +
	                  "LEFT JOIN " +
	                  "   gangnam_hospital gh ON gr.gangnam_id = gh.id " +
	                  "WHERE " +
	                  "   mr.member_id = :memberId";
	        } else if ("gangdong".equals(source)) {
	            sql = "SELECT " +
	                  "   hr.language AS language, " +
	                  "   hr.main_symptom AS main_symptom, " +
	                  "   gdh.gangdong_name AS hospital_name " +
	                  "FROM " +
	                  "   member_reservation mr " +
	                  "JOIN " +
	                  "   hospital_reservation hr ON mr.reservation_id = hr.id " +
	                  "LEFT JOIN " +
	                  "   gangdong_reservation gdr ON hr.id = gdr.reservation_id " +
	                  "LEFT JOIN " +
	                  "   gangdong_hospital gdh ON gdr.gangdong_id = gdh.id " +
	                  "WHERE " +
	                  "   mr.member_id = :memberId";
	        } else {
	            return new ArrayList<>(); // 유효하지 않은 source
	        }

	        Query query = em.createNativeQuery(sql, Tuple.class);
	        query.setParameter("memberId", memberId);

	        List<Tuple> rs = query.getResultList();
	        return JPAUtil.convertTupleToMap(rs);

	    } catch (Exception e) {
	        System.out.println("selectFromHospitalReservation failed: " + e.getMessage());
	        return new ArrayList<>();
	    }
		
	}
		

}
