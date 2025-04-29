package com.medical.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.HospitalReservationDto;
import com.medical.dto.HospitalReviewDto;
import com.medical.dto.MemberFavoriteDto;
import com.medical.dto.MemberRegisterDto;
import com.medical.service.HospitalService;

@RestController
@CrossOrigin("*")
public class HospitalController {
	@Autowired
	HospitalService hospitalService;
	
//------------------------ 병원 관련 ------------------------	
	// 메인 리스트 페이지에서 사용할 강남구와 강동구 병원 정보 가져오기(id, 병원명, 시·구 주소, 가능 언어, 대표과 1개)
	@GetMapping("/select15FromGangnamGangDongHospital")
	public List<Map<String, Object>> select15FromGangnamGangDongHospital(@RequestParam int offsetNum){
System.out.println("offsetNum: " + offsetNum);
		return hospitalService.select15FromGangnamGangDongHospital(offsetNum);
	}
	
	/*
	 [
	  {
	    "hospital_languages": "영어",
	    "source": "gangdong",
	    "hospital_main_category": "내과",
	    "hospital_id": 1,
	    "hospital_main_address": "서울시 강동구",
	    "hospital_name": "중앙보훈병원"
	  },
	  {
	    "hospital_languages": "영어,일어",
	    "source": "gangdong",
	    "hospital_main_category": "신경외과",
	    "hospital_id": 2,
	    "hospital_main_address": "서울시 강동구",
	    "hospital_name": "허리나은병원"
	  }, {}, {} , ...
	 ]
	 */
	
	// 병원 상세 페이지에서 사용할 '강남구' 병원 정보 가져오기
	@GetMapping("/selectFromGangnamHospital")
	public List<Map<String, Object>> selectFromGangnamHospital(@RequestParam int hospitalId){
		return hospitalService.selectFromGangnamHospital(hospitalId);
	}
	
	/*
	[
	  {
	    "hospital_category": "의원",
	    "hospital_languages": "중국",
	    "hospital_phone_number": "02-6958-7532",
	    "hospital_main_address": "서울시 강남구",
	    "hospital_address": "서울특별시 강남구 봉은사로 230 (역삼동, 봉암빌딩) 5층",
	    "hospital_name": "서울베스트비뇨의학과의원"
	  }
	]
	 */
	
	// 병원 상세 페이지에서 사용할 '강동구' 병원 정보 가져오기
	@GetMapping("/selectFromGangdongHospital")
	public List<Map<String, Object>> selectFromGangdongHospital(int hospitalId){
		return hospitalService.selectFromGangdongHospital(hospitalId);
	}
	/*
	[
	  {
	    "hospital_category": "신경외과,내과,정형외과,영상의학과,마취통증의학과",
	    "hospital_languages": "영어,일어",
	    "hospital_phone_number": "02-472-0114",
	    "hospital_main_address": "서울시 강동구",
	    "hospital_address": "서울 강동구 성내2동 62-4",
	    "hospital_name": "허리나은병원"
	  }
	]
	 */
	
//	// 병원명 검색하기
//	@GetMapping("/selectByHospitalName")
//	public List<Map<String, Object>> selectByHospitalName(@RequestParam String hospitalName, @RequestParam int offsetNum){
//		return hospitalService.selectByHospitalName(hospitalName, offsetNum);
//	}
//	
//	// 필터링 기능(사용 언어, 진료과목, 지역)
//	@GetMapping("/filterHospitalByLangDepartLocation")
//	public List<Map<String, Object>> filterHospitalByLangDepartLocation(@RequestParam(required = false) String language, @RequestParam(required = false) String department, @RequestParam(required = false) String location, @RequestParam int offsetNum){
//System.out.println("language: " + language + "department: " + department + "location: " + location + "offsetNum: " + offsetNum);		
//		return hospitalService.filterHospitalByLangDepartLocation(language, department, location, offsetNum);
//	}
	
	// 병원 & 필터링 기능(사용 언어, 진료과목, 지역) 동시에.
	@GetMapping("/searchAndFilterHospital")
	public List<Map<String, Object>> searchAndFilterHospital(@RequestParam(required = false) String hospitalName, @RequestParam(required = false) String language, @RequestParam(required = false) String department, @RequestParam(required = false)String location, int offsetNum){
		return hospitalService.searchAndFilterHospital(hospitalName, language, department, location, offsetNum);
	}
// -------------------------- 회원  --------------------------
	// 회원가입
	@PostMapping("/memberRegister")
	public boolean memberRegister(@RequestBody MemberRegisterDto memberRegisterDto) {
		return hospitalService.memberRegister(memberRegisterDto);
	}
	
	// 로그인
	@GetMapping("/memberLogin")
	public int memberLogin(@RequestParam String username, @RequestParam String password) {
		return hospitalService.memberLogin(username, password);
	}

	// 회원 정보 조회(username, phone_num, gender, birth_date, email)
	@GetMapping("/selectUserInfo")
	public Map<String, Object> selectUserInfo(Long memberId) {
		return hospitalService.selectUserInfo(memberId);
	}
	
	// 회원 아이디 찾기
	@GetMapping("/selectUserName")
	public String selectUserName(@RequestParam String email) {
		return hospitalService.selectUserName(email);
	}
	
	// 회원 비밀번호 찾기(실제 구현X, 이메일 입력하면 존재하는 회원인지만 체크해서 메일 발송 알림만)
	@GetMapping("/isUserExist")
	public boolean isUserExist(String email) {
		return hospitalService.isUserExist(email);
	}
// -------------------------- 리뷰  --------------------------
	// 병원 리뷰 작성
	@PostMapping("/insertHospitalReview")
	public boolean insertHospitalReview(@RequestBody HospitalReviewDto hospitalReviewDto) {
		return hospitalService.insertHospitalReview(hospitalReviewDto);
	}
	
	
	// 병원 id 통해서, hospital_review select 해오기
	@GetMapping("/selectFromHospitalReview")
	public List<Map<String, Object>> selectFromHospitalReview(@RequestParam Long hospitalId, @RequestParam String source){
		return hospitalService.selectFromHospitalReview(hospitalId, source);
	}
	
	// 회원이 작성한 리뷰 조회
	@GetMapping("/selectReviewByMemberId")
	public List<Map<String, Object>> selectReviewByMemberId(@RequestParam Long memberId){
		return hospitalService.selectReviewByMemberId(memberId);
	}
	/*
	[
	  {
	    "rate": 4,
	    "created_at": "2025-04-27T11:56:46.000+00:00",
	    "original_text": "doctors were nice!",
	    "hospital_name": "아이비성형외과의원"
	  },
	  {
	    "rate": 4,
	    "created_at": "2025-04-27T13:13:48.000+00:00",
	    "original_text": "this place is wonderful.",
	    "hospital_name": "누브의원"
	  }, {}, {} ,...
	*/	
// -------------------------- 진료예약 관련--------------------------		
	// 진료예약 insert - hospital_reservation, gangnam_reservation/gangnam_reservation 연결 테이블, member_reservation 연결 테이블에 insert
	@PostMapping("/insertHospitalReservation")
	public boolean insertHospitalReservation(@RequestBody HospitalReservationDto hospitalReservationDto) {
		return hospitalService.insertHospitalReservation(hospitalReservationDto);
	}
	
	// member id 통해서, hospital_reservation select 해오기
	@GetMapping("/selectFromHospitalReservation")
	public List<Map<String, Object>> selectFromHospitalReservation(@RequestParam Long memberId){
		return hospitalService.selectFromHospitalReservation(memberId);
	}
	/*
	[
	  {
	    "sub_symptom": "설사/변비",
	    "language": "영어",
	    "reservation_time": "2025-05-07T15:00:00.000+00:00",
	    "main_symptom": "소화기 질환",
	    "detail_symptom": "테스트1",
	    "hospital_name": "중앙보훈병원"
	  }, {}, {} ,...
	]
	 */
// -------------------------- 즐겨찾기 관련--------------------------	
	// 즐겨찾기 추가 - member_favorite 테이블에 insert
	@PostMapping("/insertIntoMemberFavorite")
	public boolean insertIntoMemberFavorite(@RequestBody MemberFavoriteDto memberFavoriteDto) {
		return hospitalService.insertIntoMemberFavorite(memberFavoriteDto);
	}
		
	// 회원의 즐겨찾기 조회(병원 id, 병원명, 병원 메인 주소)
	@GetMapping("/selectFromMemberFavorite")
	public List<Map<String, Object>> selectFromMemberFavorite(@RequestParam Long memberId){
		return hospitalService.selectFromMemberFavorite(memberId);
	}
	/*
	[
	  {
	    "hospital_id": 1,
	    "hospital_main_address": "서울시 강동구",
	    "hospital_name": "중앙보훈병원"
	  }, {}, {}, ...
	] 
	*/
	// 병원 id와 회원 id로, 회원이 즐겨찾기한 병원인지 확인 
	@GetMapping("/isFavoriteCheck")
	public boolean isFavoriteCheck(@RequestParam Long memberId, @RequestParam Long hospitalId, @RequestParam String source) {
		return hospitalService.isFavoriteCheck(memberId, hospitalId, source);
	}
	
	// 즐겨찾기 삭제(취소)
	@DeleteMapping("/deleteMemberFavorite")
	public boolean deleteMemberFavorite(@RequestParam Long memberId, @RequestParam Long hospitalId, @RequestParam String source) {
		return hospitalService.deleteMemberFavorite(memberId, hospitalId, source);
	}
		
//	@PutMapping("/updateChartDashboardConnect")
//	public boolean updateChartDashboardConnect(@RequestBody ChartDashboardConnectDto chartDashboardConnectDto) {
//		return queryResultTableService.updateChartDashboardConnect(chartDashboardConnectDto);
//	}
}
