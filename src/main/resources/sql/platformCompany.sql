/*platformCompany_list*/
SELECT
	c.id,
	c.`name`,
	c.shortName,
	c.adminId,
	u.mobile AS adminLoginName,
	c.linkman,
	c.mobile,
	c.email,
	c.address,
	c.comType,
	c.license,
	c.createTime
FROM
	t_company c
LEFT JOIN t_user u ON c.adminId = u.id
$condition

/*get_company_functions_list*/
select tf.*
from t_company tc
INNER JOIN t_com_function tcf
INNER JOIN t_function tf
where tc.id = @comId
