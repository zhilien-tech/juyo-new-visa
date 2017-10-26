/*userManager_list*/
SELECT
	u.id,
	u.`name`,
	u.mobile,
	d.deptName,
	j.jobName,
	u.createTime,
	u.updateTime
FROM
	t_user u
LEFT JOIN t_department d ON u.departmentId = d.id
LEFT JOIN t_job j ON u.jobId = j.id
LEFT JOIN t_company c ON u.comId = c.id
$condition