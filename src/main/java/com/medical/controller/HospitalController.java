package com.medical.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.service.HospitalService;

@RestController
@CrossOrigin("*")
public class HospitalController {
	@Autowired
	HospitalService hospitalService;

	// 메인 리스트 페이지에서 사용할 강남구와 강동구 병원 정보 가져오기(id, 병원명, 시·구 주소, 가능 언어, 대표과 1개)
	@GetMapping("/select15FromGangnamGangDongHospital")
	public List<Map<String, Object>> select15FromGangnamGangDongHospital(@RequestParam int offsetNum){
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
	
	

//	@PostMapping("/insertIntoChartInfo")
//	public boolean insertIntoChartInfo(@RequestBody ChartInfoDto chartInfoDto) {
//		return queryResultTableService.insertIntoChartInfo(chartInfoDto);
//	}
//
//	@PutMapping("/updateChartDashboardConnect")
//	public boolean updateChartDashboardConnect(@RequestBody ChartDashboardConnectDto chartDashboardConnectDto) {
//		return queryResultTableService.updateChartDashboardConnect(chartDashboardConnectDto);
//	}
}
