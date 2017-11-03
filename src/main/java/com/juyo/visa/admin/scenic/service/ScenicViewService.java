package com.juyo.visa.admin.scenic.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.forms.TScenicAddForm;
import com.juyo.visa.forms.TScenicForm;
import com.juyo.visa.forms.TScenicUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class ScenicViewService extends BaseService<TScenicEntity> {
	private static final Log log = Logs.get();

	public Object listData(TScenicForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	/**
	 * 
	 * TODO 加载更新页面时回显
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object fetchScenic(final long id) {
		Map<String, Object> result = Maps.newHashMap();
		TScenicEntity scenic = this.fetch(id);
		TCityEntity city = dbDao.fetch(TCityEntity.class, new Long(scenic.getCityId()).intValue());
		result.put("scenic", scenic);
		result.put("city", city);
		return result;
	}

	/**
	 * 
	 * TODO 添加
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addScenic(TScenicAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	/**
	 * 
	 * TODO 更新
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateScenic(TScenicUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TScenicEntity scenic = this.fetch(updateForm.getId());
		updateForm.setCreateTime(scenic.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

	/**
	 * 获取景区select2下拉
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param scenicname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getScenicSelect(String scenicname) {
		List<TScenicEntity> scenics = Lists.newArrayList();
		try {
			scenics = dbDao.query(TScenicEntity.class, Cnd.where("name", "like", "%" + Strings.trim(scenicname) + "%"),
					null);
			if (scenics.size() > 5) {
				scenics = scenics.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenics;
	}

}