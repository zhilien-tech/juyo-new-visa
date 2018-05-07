/*select_function_by_userid*/
SELECT
	tf.*
FROM
	t_user_job tuj
INNER JOIN t_com_job tcj ON tuj.comJobId = tcj.id
INNER JOIN t_job tj ON tcj.jobId = tj.id
INNER JOIN t_comfunction_job tcfj ON tcfj.jobId = tj.id
INNER JOIN t_com_function tcf ON tcfj.comFunId = tcf.id
INNER JOIN t_function tf ON tcf.funId = tf.id
WHERE
	tuj.empId = @userid 
GROUP BY
	tf.id
ORDER BY
	tf.sort ASC