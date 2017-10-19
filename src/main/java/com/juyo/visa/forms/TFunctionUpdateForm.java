package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**上级功能id*/
	private Integer parentId;
		
	/**功能名称*/
	private String funName;
		
	/**访问地址*/
	private String url;
		
	/**功能等级，是指在功能树中所处的层级*/
	private Integer level;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
	/**备注*/
	private String remark;
		
	/**序号*/
	private Integer sort;
		
	/**菜单栏图标*/
	private String portrait;
		
}