/*cityselectBygodeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.goDepartureCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition

/*cityselectBygoarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.goArrivedCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectByreturndeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.returnDepartureCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition

/*cityselectByreturnarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.returnArrivedCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*getMainapplyinfo*/
SELECT
ta.province,
ta.detailedAddress,
taoj.isMainApplicant
FROM
t_applicant ta
LEFT JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id
INNER JOIN t_order tr ON toj.orderId = tr.id
WHERE
toj.id = @id AND taoj.isMainApplicant = 1