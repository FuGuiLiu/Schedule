package com.idea.sky.service;

import com.idea.sky.dao.SystemJobDAO;
import com.idea.sky.entity.SystemJob;
import com.idea.sky.service.impl.SystemJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemJobServiceImpl implements SystemJobService {

	@Resource
	private SystemJobDAO systemJobDAO;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return systemJobDAO.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SystemJob record) {
		return systemJobDAO.insert(record);
	}

	@Override
	public int insertSelective(SystemJob record) {
		return systemJobDAO.insertSelective(record);
	}

	@Override
	public SystemJob selectByPrimaryKey(Long id) {
		return systemJobDAO.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SystemJob record) {
		return systemJobDAO.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SystemJob record) {
		return systemJobDAO.updateByPrimaryKey(record);
	}

	@Override
	public List<SystemJob> queryAllSystemJob() {
		return systemJobDAO.queryAllSystemJob();
	}
}
