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
	c.fax,
	c.address,
	c.comType,
	(
		SELECT
			GROUP_CONCAT(
				cast(cbs.countryId AS CHAR) SEPARATOR ','
			)
		FROM
			t_com_businessscope cbs
		WHERE
			cbs.comId = c.id
	) AS scopes,
	c.license,
	c.seal,
	c.createTime
FROM
	t_company c
LEFT JOIN t_user u ON c.adminId = u.id
$condition

/*get_company_functions_list*/
SELECT
	tf.*
FROM
	t_company tc
INNER JOIN t_com_function tcf ON tc.id = tcf.comId
INNER JOIN t_function tf ON tcf.funId = tf.id
where tc.id = @comId
GROUP BY
	tf.funName 
ORDER BY
	tf.sort
