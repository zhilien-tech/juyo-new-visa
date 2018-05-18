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
	( SELECT tcb.designatedNum FROM t_com_businessscope tcb WHERE tcb.comId = c.id GROUP BY tcb.comId) AS designatedNum,
	c.license,
	c.createTime 
FROM
	t_company c
	LEFT JOIN t_user u ON c.adminId = u.id
$condition

/*companyInfo_list_company*/
SELECT
	*
FROM
	t_company c
$condition

/*companyInfo_list_sendcompany*/
select
	tc.*,
	tcoc.id cocid,
	tcoc.sendcomid  
	from 
		t_company tc
	left join 
		t_company_of_customer tcoc 
	on 
	tcoc.sendcomid = tc.id 
	$condition

/*companyInfo_list_company_by_realx*/
SELECT
	c.id,
	c.comid
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
	( SELECT tcb.designatedNum FROM t_com_businessscope tcb WHERE tcb.comId = c.id GROUP BY tcb.comId) AS designatedNum,
	c.license,
	c.createTime 
FROM
	t_company c
	LEFT JOIN t_user u ON c.adminId = u.id

