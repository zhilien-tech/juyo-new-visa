/*baoying_staff_list*/
SELECT
	tasb.id staffid,
	tasb.comid,
	CONCAT( tasb.firstname, tasb.lastname ) NAME,
	tasb.telephone,
	tasb.email,
	tasb.interviewdate,
	tou.id orderid,
	tou.ordernumber,
	tou.cityid,
	tou.STATUS orderstatus,
	tou.isdisable 
FROM
	t_app_staff_basicinfo tasb
	INNER JOIN t_app_staff_order_us tasou ON tasou.staffid = tasb.id
	INNER JOIN t_order_us tou ON tou.id = tasou.orderid
	$condition