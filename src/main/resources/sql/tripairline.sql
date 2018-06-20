/*tripairline_airlines*/
SELECT
tf.*
FROM
t_flight tf
LEFT JOIN t_city tc ON tf.takeOffCityId = tc.id
$condition

/*tripairline_airlines_zhuan*/
SELECT
tf.flightnum tfflightnum,
tf.takeOffName tftakeoffname,
tf.landingName tflandingname,
tf.takeOffTime tftakeofftime,
tf.landingTime tflandingtime,
tf2.flightnum tfflightnum2,
tf2.takeOffName tftakeoffname2,
tf2.landingName tflandingname2,
tf2.takeOffTime tftakeofftime2,
tf2.landingTime tflandingtime2
FROM
t_flight tf
LEFT JOIN t_flight tf2 ON tf.relationflight = tf2.id
$condition