/*appevents_detail_by_eventId*/
SELECT
	tae.id eventId,
	tae.eventsNum,
	tae.eventsName,
	tae.pictureUrl,
	tae.detailsUrl,
	tae.dueDate,
	tae.departureDate,
	tae.returnDate,
	tae.visaCountry,
	tae.visitCity,
	tae.attentions,
	tae.descriptions,
	( SELECT count( * ) FROM t_app_staff_events tase WHERE tase.eventsId = tae.id ) signUpCount 
FROM
	t_app_events tae 
WHERE
	tae.id = @eventId

	
/*appevents_staffs_infos_by_eventId*/
SELECT
	tase.eventsId,
	tasb.* 
FROM
	t_app_staff_basicinfo tasb
	INNER JOIN t_app_staff_events tase ON tasb.id = tase.staffId
WHERE
	tase.eventsId = @eventId
GROUP BY
	tasb.id
