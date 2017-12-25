/*companyInfo_admin_company*/
SELECT
	c.id,
	c.`name` fullname,
	c.shortName,
	c.adminId,
	u.mobile AS adminLoginName,
	c.linkman,
	c.mobile,
	c.email,
	c.address,
	c.comType,
	( SELECT GROUP_CONCAT( cast( tcb.countryId AS CHAR ) SEPARATOR ',' ) FROM t_com_businessscope tcb WHERE tcb.comId = c.id ) AS scopes,
	( SELECT tcb.designatedNum FROM t_com_businessscope tcb WHERE tcb.comId = c.id ) AS designatedNum,
	c.license,
	c.createTime 
FROM
	t_company c
	LEFT JOIN t_user u ON c.adminId = u.id
$condition
