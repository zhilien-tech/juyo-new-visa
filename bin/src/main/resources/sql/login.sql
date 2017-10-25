/*select_login_company*/
SELECT
	tc.*
FROM
	t_user tu
INNER JOIN t_user_job tuj ON tu.id = tuj.empId
INNER JOIN t_com_job tcj ON tcj.id = tuj.comJobId
INNER JOIN t_company tc ON tc.id = tcj.comId
where tu.id = @userid