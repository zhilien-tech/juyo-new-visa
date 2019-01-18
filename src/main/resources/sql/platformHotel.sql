/*platformHotel_list*/
SELECT
	h.id,
	h.`name`,
	h.nameJp,
	h.address,
	h.addressJp,
	h.mobile,
	c.city,
	h.region,
	h.createTime,
	h.updateTime
FROM
	t_hotel h
LEFT JOIN t_city c ON h.cityId = c.id
$condition