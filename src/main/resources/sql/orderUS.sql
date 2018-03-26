/*orderUS_orderNum_nowDate*/
SELECT
	tou.id orderId,
	tou.ordernumber,
	tou.createtime
FROM
	t_order_us tou 
WHERE
	DATE( tou.createtime ) = DATE( NOW( ) )