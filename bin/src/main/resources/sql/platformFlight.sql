/*platformFlight_list*/
SELECT
	f.id,
	f.flightnum,
	f.airlinecomp,
	f.takeOffName,
	f.takeOffCode,
	f.landingName,
	f.landingCode,
	c1.city AS takeOffCity,
	c2.city AS landingCity,
	f.takeOffTime,
	f.landingTime,
	f.takeOffTerminal,
	f.landingTerminal,
	f.createTime,
	f.updateTime
FROM
	t_flight f
LEFT JOIN t_city c1 ON f.takeOffCityId = c1.id
LEFT JOIN t_city c2 ON f.landingCityId = c2.id
$condition