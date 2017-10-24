/*authority_list*/
SELECT
	c.name as comName,
	c.createtime,
	d.id AS deptId,
	d.deptName,
	j.id AS jobId,
	GROUP_CONCAT(DISTINCT j.jobName) AS jobName,
	f.id AS functionId,
	GROUP_CONCAT(DISTINCT f.funName) AS moduleName
FROM
	t_company c
LEFT JOIN t_department d ON c.id = d.comId
LEFT JOIN t_job j ON d.id = j.deptId
LEFT JOIN t_comfunction_job cfj ON j.id = cfj.jobId
LEFT JOIN t_com_function cf ON cfj.comFunId = cf.id
LEFT JOIN t_function f ON cf.funId = f.id
$condition