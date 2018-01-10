/*logs_user_select_list*/
SELECT
	tu.id userid,
	tu.NAME username,
	tu.comid,
	tf.funName 
FROM
	t_user tu
	INNER JOIN t_user_job tuj ON tuj.empId = tu.id
	INNER JOIN t_com_job tcj ON tcj.id = tuj.comJobId
	INNER JOIN t_job tj ON tj.id = tcj.jobId
	INNER JOIN t_comfunction_job tcfj ON tcfj.jobId = tj.id
	INNER JOIN t_com_function tcf ON tcf.id = tcfj.comFunId
	INNER JOIN t_function tf ON tf.id = tcf.funId 
$condition

/*logs_order_info*/
SELECT
	o.id,
	o.orderNum,
	o.salesOpid,
	o.trialOpid,
	o.receptionOpid,
	o.visaOpid,
	o.aftermarketOpid,
	o.updateTime
FROM
	t_order o
WHERE
	o.id = @orderid
