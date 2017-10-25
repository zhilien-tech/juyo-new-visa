/*platformScenic_list*/
SELECT
	s.id,
	s.`name`,
	s.nameJp,
	c.city,
	s.createTime,
	s.updateTime
FROM
	t_scenic s
LEFT JOIN t_city c ON s.cityId = c.id
$condition