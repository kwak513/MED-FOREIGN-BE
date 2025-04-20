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

	// 강남구와 강동구 병원 정보 가져오기(병원명, 시·구 주소, 가능 언어, 대표과 1개), 메인 리스트 페이지에서 사용할거임.
	@GetMapping("/select15FromGangnamGangDongHospital")
	public List<Map<String, Object>> select15FromGangnamGangDongHospital(@RequestParam int offsetNum){
		return hospitalService.select15FromGangnamGangDongHospital(offsetNum);
	}
	
	/*
	 [
	  {
	    "hospital_languages": "영어",
	    "source": "gangdong",
	    "hospital_main_category": "피부과",
	    "hospital_main_address": "서울시 강동구",
	    "hospital_name": "비너스의원"
	  },
	  {
	    "hospital_languages": "영어",
	    "source": "gangdong",
	    "hospital_main_category": "산부인과",
	    "hospital_main_address": "서울시 강동구",
	    "hospital_name": "강동미즈여성병원"
	  }, {}, {} , ...
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
