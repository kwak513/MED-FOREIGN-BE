package com.medical.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dto.HospitalReservationDto;
import com.medical.dto.HospitalReviewDto;
import com.medical.dto.MemberRegisterDto;
import com.medical.service.HospitalService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
public class HospitalController {
	@Autowired
	HospitalService hospitalService;

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
	
	// 병원 리뷰 작성
	@PostMapping("/insertHospitalReview")
	public boolean insertHospitalReview(@RequestBody HospitalReviewDto hospitalReviewDto) {
		return hospitalService.insertHospitalReview(hospitalReviewDto);
	}
	
	
	// 병원 id 통해서, hospital_review select 해오기
	@GetMapping("/selectFromHospitalReview")
	public List<Map<String, Object>> selectFromHospitalReview(@RequestParam int hospitalId, @RequestParam String source){
		return hospitalService.selectFromHospitalReview(hospitalId, source);
	}
		
	
	// 진료예약 insert - hospital_reservation, gangnam_reservation/gangnam_reservation 연결 테이블, member_reservation 연결 테이블에 insert
	@PostMapping("/insertHospitalReservation")
	public boolean insertHospitalReservation(@RequestBody HospitalReservationDto hospitalReservationDto) {
		return hospitalService.insertHospitalReservation(hospitalReservationDto);
	}
	
	// member id 통해서, hospital_reservation select 해오기
	@GetMapping("/selectFromHospitalReservation")
	public List<Map<String, Object>> selectFromHospitalReservation(@RequestParam int memberId, @RequestParam String source){
		return hospitalService.selectFromHospitalReservation(memberId, source);
	}
		
//	@PutMapping("/updateChartDashboardConnect")
//	public boolean updateChartDashboardConnect(@RequestBody ChartDashboardConnectDto chartDashboardConnectDto) {
//		return queryResultTableService.updateChartDashboardConnect(chartDashboardConnectDto);
//	}
}
