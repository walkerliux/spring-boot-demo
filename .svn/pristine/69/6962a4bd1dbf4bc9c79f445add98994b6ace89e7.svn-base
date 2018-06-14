package com.example.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.Solution52;
import com.example.demo.dao.Solution52Mapper;
import com.example.demo.service.SolutionService;
@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {
	
	@Autowired
	private Solution52Mapper solution52Mapper;
	@Override
	public void insert(Solution52 solution) {
		solution52Mapper.insert(solution);

	}
	@Override
	public void deleteById(Solution52 solution) {
		// TODO Auto-generated method stub
		solution52Mapper.deleteByPrimaryKey(solution.getId());
	}

}
