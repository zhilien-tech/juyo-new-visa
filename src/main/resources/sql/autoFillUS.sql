/*getAutofilldata*/
SELECT
tou.id orderusid,
tou.ordernumber ordernum,
tou.cityid,
tasb.telecodefirstname,
tasb.telecodelastname,
tasb.firstname tasbfirstname,
tasb.firstnameen tasbfirstnameen,
tasb.lastname tasblastname,
tasb.lastnameen tasblastnameen,
tasb.nationality tasbnationality,
tasb.birthcountry,
tasb.nowcountry,
tasb.mailcityen,
tasb.mailprovinceen,
tasb.mailcountry,
tasb.mailaddressen,
tasb.othercountry,
tasb.otherfirstname,
tasb.otherfirstnameen,
tasb.otherlastname,
tasb.otherlastnameen,
tasb.cardcity,
tasb.cardcityen,
tasb.cardprovince,
tasb.cardprovinceen,
tasb.cardId,
tasb.telephone tasbtelephone,
tasb.email tasbemail,
tasb.marrystatus,
tasb.detailedaddress,
tasb.detailedaddressen,
tasb.city tasbcity,
tasb.cityen tasbcityen,
tasb.province tasbprovince,
tasb.provinceen tasbprovinceen,
tasb.socialsecuritynumber,
tasb.taxpayernumber,
tasb.nationalidentificationnumber,
tasb.ismailsamewithlive,
tasf.fatherfirstname,
tasf.fatherfirstnameen,
tasf.fatherlastname,
tasf.fatherlastnameen,
tasf.fatherbirthday,
tasf.motherfirstname,
tasf.motherfirstnameen,
tasf.motherlastname,
tasf.motherlastnameen,
tasf.motherbirthday,
tasf.spousefirstname,
tasf.spousefirstnameen,
tasf.spouselastname,
tasf.spouselastnameen,
tasf.spousecity,
tasf.spousecityen,
tasf.spousecountry,
tasf.spousebirthday,
tasf.spousenationality,
tasf.spouseaddress,
tasf.spouseaddressen,
tasf.firstaddress,
tasf.city tasfcity,
tasf.province tasfprovince,
tasf.country tasfcountry,
tasf.zipcode tasfzipcode,
tasf.isfatherinus,
tasf.fatherstatus,
tasf.ismotherinus,
tasf.motherstatus,
tasf.hasotherrelatives,
tasf.hasimmediaterelatives,
tasf.marrieddate,
tasf.divorcedate,
tasf.divorcecountry,
tasp.issuedplace,
tasp.type passporttype,
tasp.passport,
tasp.issueddate,
tasp.validenddate,
tasp.sex taspsex,
tasp.birthday taspbirthday,
tasp.issuedplaceen,
tout.travelpurpose,
tout.goFlightNum,
tout.goArrivedCity,
tout.returnDepartureCity,
tout.arrivedate toutarrivedate,
tout.staydays,
tout.stayunit,
tout.state planstate,
tout.city plancity,
tout.cityen plancityen,
tout.hotelname,
tout.hotelnameen,
tout.addressen planaddressen,
tout.address planaddress,
tout.leavedate toutleavedate,
tout.returnFlightNum,
tout.hastripplan,
tout.specify,
tout.telephone plantelephone,
tast.groupname,
tasc.firstname tascfirstname,
tasc.firstnameen tascfirstnameen,
tasc.lastname tasclastname,
tasc.lastnameen tasclastnameen,
tasc.organizationname,
tasc.ralationship,
tasc.telephone tasctelephone,
tasc.email tascemail,
tasc.address tascaddress,
tasc.city tasccity,
tasc.state tascstate,
tasc.zipcode tasczipcode,
taspt.issueddate tasptissueddate,
taspt.visanumber,
taspt.costpayer,
taspt.isapplyingsametypevisa,
taspt.isissuedvisa,
taspt.issamecountry,
taspt.istenprinted,
taspt.islost,
taspt.lostyear,
taspt.lostexplainen,
taspt.iscancelled,
taspt.cancelexplain,
taspt.isrefused,
taspt.refusedexplain,
taspt.islegalpermanentresident,
taspt.permanentresidentexplain,
taspt.isfiledimmigrantpetition,
taspt.immigrantpetitionexplainen,
taspt.payrelationwithyou,
taspt.payaddressissamewithyou,
taspt.paycountryen,
taspt.payprovinceen,
taspt.paycityen,
taspt.payaddressen,
taspt.payfirstname,
taspt.payfirstnameen,
taspt.paylastname,
taspt.paylastnameen,
taspt.paytelephone,
taspt.paymail,
taspt.comnameen,
taspt.comtelephone,
taspt.comrelationwithyouen,
taswet.occupation,
taswet.clannameen,
taswet.skillexplainen,
taswet.paramilitaryunitexplainen,
taswet.isemployed,
taswet.hasservedinmilitary,
taswet.issecondarylevel
FROM
t_order_us tou
INNER JOIN
t_app_staff_order_us tasou ON tou.id = tasou.orderid
INNER JOIN
t_app_staff_basicinfo tasb ON tasb.id = tasou.staffid
LEFT JOIN
t_app_staff_passport tasp ON tasp.staffid = tasb.id
LEFT JOIN
t_app_staff_familyinfo tasf ON tasf.staffid = tasb.id
LEFT JOIN
t_app_staff_work_education_training taswet ON taswet.staffid = tasb.id
LEFT JOIN
t_order_us_travelinfo tout ON tout.orderid = tou.id
LEFT JOIN
t_app_staff_travelcompanion tast ON tast.staffid = tasb.id
LEFT JOIN
t_app_staff_contactpoint tasc ON tasc.staffid = tasb.id
LEFT JOIN
t_app_staff_previoustripinfo taspt ON taspt.staffid = tasb.id
$condition