/*personalInfo_list*/
SELECT
	u.*,
	d.deptname,
	j.jobname
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.empId
INNER JOIN t_com_job cj ON cj.id = uj.comJobId
INNER JOIN t_job j ON j.id = cj.jobId
INNER JOIN t_department d ON d.id = j.deptId
$condition