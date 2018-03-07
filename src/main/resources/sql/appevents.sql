/*appevents_detail_byId*/
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
