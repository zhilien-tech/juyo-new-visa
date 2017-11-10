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

/*authority_user_dept*/
SELECT
	uj.empid,
	d.id AS did,
	d.deptName 
FROM
	t_department d
	LEFT JOIN t_job j ON j.deptid = d.id
	LEFT JOIN t_com_job cj ON cj.jobId = j.id
	LEFT JOIN t_user_job uj On uj.comJobId = cj.id
	where uj.empid = @adminId


/*authority_com_fun*/
SELECT
	tf.id, 
	tf.parentId, 
	tf.funName, 
	tf.url, 
	tf.LEVEL, 
	tf.createTime, 
	tf.updateTime, 
	tf.remark, 
	tf.sort,
	tf.portrait
FROM
	t_function tf
LEFT JOIN t_com_function tcf ON tf.id = tcf.funId
WHERE
	tcf.comId =@comId
	
/*authority_job_fun*/
SELECT
	tf.id, 
	tf.parentId, 
	tf.funName, 
	tf.url, 
	tf.LEVEL, 
	tf.createTime, 
	tf.updateTime, 
	tf.remark, 
	tf.sort
FROM
t_comfunction_job cfm
LEFT JOIN t_com_function cf ON cf.id = cfm.comFunId
LEFT JOIN t_function tf ON tf.id = cf.funId
WHERE cfm.jobId=@jobId

/*authority_com_dep*/
SELECT
	d.id,
	d.deptName,
	d.comId,
	d.remark
FROM
	t_department d
INNER JOIN t_company c ON c.id = d.comId
WHERE
	c.id =@comId
AND d.deptName =@deptName

/*authority_com_job*/
SELECT
	j.id,
	j.createTime,
	j.deptId,
	j.remark,
	j.jobName AS name
FROM
	t_job j
INNER JOIN t_department d ON j.deptId = d.id
INNER JOIN t_company c ON c.id = d.comId
WHERE c.id=@comId
AND j.jobName=@jobName

/*authority_com_job_update*/
SELECT
	j.id,
	j.createTime,
	j.deptId,
	j.remark,
	j.jobName AS name
FROM
	t_job j
INNER JOIN t_department d ON j.deptId = d.id
INNER JOIN t_company c ON c.id = d.comId
WHERE c.id=@comId
AND j.jobName=@jobName
AND j.id !=@jobId

/*authority_deptJob_select*/
SELECT
	tj.id AS jobId,
	tj.jobName,
	dp.id AS deptId,
	dp.deptName
FROM
	t_job tj
INNER JOIN t_department dp ON dp.id=tj.deptId
WHERE tj.id=@jobId

/*authority_delete_job*/
SELECT
	u.id,
	u.userName
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.userid
INNER JOIN t_company_job cj ON uj.companyJobId = cj.id
WHERE
	cj.posid = @jobId
AND uj.status = @jobStatus
AND cj.comId = @companyId

/*authority_jobName_count*/
SELECT
	j.id,
	j.jobName
FROM
	t_job j
LEFT JOIN t_department d ON j.deptId = d.id
$condition