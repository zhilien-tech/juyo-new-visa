/**bigCustomer_staff_list*/
SELECT
	tasb.id staffid,
	CONCAT( tasb.firstname, tasb.lastname ) NAME,
	tasb.telephone,
	tasb.department,
	tasb.job,
	tasp.passport 
FROM
	t_app_staff_basicinfo tasb
	LEFT JOIN t_app_staff_passport tasp ON tasp.staffId = tasb.id
	$condition