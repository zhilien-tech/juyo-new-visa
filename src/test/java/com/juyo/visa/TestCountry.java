package com.juyo.visa;


/**
 * 
 * 更新国家 国际代码
 *
 */

public class TestCountry {

	public static void main(String[] args) {

		String countrys = "AFGH&&&AFGHANISTAN@"+
				"ALB&&&ALBANIA@"+
				"ALGR&&&ALGERIA@"+
				"ASMO&&&AMERICAN SAMOA@"+
				"ANDO&&&ANDORRA@"+
				"ANGL&&&ANGOLA@"+
				"ANGU&&&ANGUILLA@"+
				"ANTI&&&ANTIGUA AND BARBUDA@"+
				"ARG&&&ARGENTINA@"+
				"ARM&&&ARMENIA@"+
				"ARB&&&ARUBA@"+
				"XAS&&&AT SEA@"+
				"ASTL&&&AUSTRALIA@"+
				"AUST&&&AUSTRIA@"+
				"AZR&&&AZERBAIJAN@"+
				"BAMA&&&BAHAMAS@"+
				"BAHR&&&BAHRAIN@"+
				"BANG&&&BANGLADESH@"+
				"BRDO&&&BARBADOS@"+
				"BYS&&&BELARUS@"+
				"BELG&&&BELGIUM@"+
				"BLZ&&&BELIZE@"+
				"BENN&&&BENIN@"+
				"BERM&&&BERMUDA@"+
				"BHU&&&BHUTAN@"+
				"BOL&&&BOLIVIA@"+
				"BON&&&BONAIRE@"+
				"BIH&&&BOSNIA-HERZEGOVINA@"+
				"BOT&&&BOTSWANA@"+
				"BRZL&&&BRAZIL@"+
				"IOT&&&BRITISH INDIAN OCEAN TERRITORY@"+
				"BRNI&&&BRUNEI@"+
				"BULG&&&BULGARIA@"+
				"BURK&&&BURKINA FASO@"+
				"BURM&&&BURMA@"+
				"BRND&&&BURUNDI@"+
				"CBDA&&&CAMBODIA@"+
				"CMRN&&&CAMEROON@"+
				"CAN&&&CANADA@"+
				"CAVI&&&CABO VERDE@"+
				"CAYI&&&CAYMAN ISLANDS@"+
				"CAFR&&&CENTRAL AFRICAN REPUBLIC@"+
				"CHAD&&&CHAD@"+
				"CHIL&&&CHILE@"+
				"CHIN&&&CHINA@"+
				"CHRI&&&CHRISTMAS ISLAND@"+
				"COCI&&&COCOS KEELING ISLANDS@"+
				"COL&&&COLOMBIA@"+
				"COMO&&&COMOROS@"+
				"COD&&&CONGO, DEMOCRATIC REPUBLIC OF THE@"+
				"CONB&&&CONGO, REPUBLIC OF THE@"+
				"CKIS&&&COOK ISLANDS@"+
				"CSTR&&&COSTA RICA@"+
				"IVCO&&&COTE D`IVOIRE@"+
				"HRV&&&CROATIA@"+
				"CUBA&&&CUBA@"+
				"CUR&&&CURACAO@"+
				"CYPR&&&CYPRUS@"+
				"CZEC&&&CZECH REPUBLIC@"+
				"DEN&&&DENMARK@"+
				"DJI&&&DJIBOUTI@"+
				"DOMN&&&DOMINICA@"+
				"DOMR&&&DOMINICAN REPUBLIC@"+
				"ECUA&&&ECUADOR@"+
				"EGYP&&&EGYPT@"+
				"ELSL&&&EL SALVADOR@"+
				"EGN&&&EQUATORIAL GUINEA@"+
				"ERI&&&ERITREA@"+
				"EST&&&ESTONIA@"+
				"ETH&&&ETHIOPIA@"+
				"FKLI&&&FALKLAND ISLANDS@"+
				"FRO&&&FAROE ISLANDS@"+
				"FIJI&&&FIJI@"+
				"FIN&&&FINLAND@"+
				"FRAN&&&FRANCE@"+
				"FRGN&&&FRENCH GUIANA@"+
				"FPOL&&&FRENCH POLYNESIA@"+
				"FSAT&&&FRENCH SOUTHERN AND ANTARCTIC TERRITORIES@"+
				"GABN&&&GABON@"+
				"GAM&&&GAMBIA, THE@"+
				"XGZ&&&GAZA STRIP@"+
				"GEO&&&GEORGIA@"+
				"GER&&&GERMANY@"+
				"GHAN&&&GHANA@"+
				"GIB&&&GIBRALTAR@"+
				"GRC&&&GREECE@"+
				"GRLD&&&GREENLAND@"+
				"GREN&&&GRENADA@"+
				"GUAD&&&GUADELOUPE@"+
				"GUAM&&&GUAM@"+
				"GUAT&&&GUATEMALA@"+
				"GNEA&&&GUINEA@"+
				"GUIB&&&GUINEA - BISSAU@"+
				"GUY&&&GUYANA@"+
				"HAT&&&HAITI@"+
				"HMD&&&HEARD AND MCDONALD ISLANDS@"+
				"VAT&&&HOLY SEE (VATICAN CITY)@"+
				"HOND&&&HONDURAS@"+
				"HOKO&&&HONG KONG BNO@"+
				"HNK&&&HONG KONG SAR@"+
				"XHI&&&HOWLAND ISLAND@"+
				"HUNG&&&HUNGARY@"+
				"ICLD&&&ICELAND@"+
				"XIR&&&IN THE AIR@"+
				"IND&&&INDIA@"+
				"IDSA&&&INDONESIA@"+
				"IRAN&&&IRAN@"+
				"IRAQ&&&IRAQ@"+
				"IRE&&&IRELAND@"+
				"ISRL&&&ISRAEL@"+
				"ITLY&&&ITALY@"+
				"JAM&&&JAMAICA@"+
				"JPN&&&JAPAN@"+
				"JRSM&&&JERUSALEM@"+
				"JORD&&&JORDAN@"+
				"KAZ&&&KAZAKHSTAN@"+
				"KENY&&&KENYA@"+
				"KIRI&&&KIRIBATI@"+
				"PRK&&&KOREA, DEMOCRATIC REPUBLIC OF (NORTH)@"+
				"KOR&&&KOREA, REPUBLIC OF (SOUTH)@"+
				"KSV&&&KOSOVO@"+
				"KUWT&&&KUWAIT@"+
				"KGZ&&&KYRGYZSTAN@"+
				"LAOS&&&LAOS@"+
				"LATV&&&LATVIA@"+
				"LEBN&&&LEBANON@"+
				"LES&&&LESOTHO@"+
				"LIBR&&&LIBERIA@"+
				"LBYA&&&LIBYA@"+
				"LCHT&&&LIECHTENSTEIN@"+
				"LITH&&&LITHUANIA@"+
				"LXM&&&LUXEMBOURG@"+
				"MAC&&&MACAU@"+
				"MKD&&&MACEDONIA@"+
				"MADG&&&MADAGASCAR@"+
				"MALW&&&MALAWI@"+
				"MLAS&&&MALAYSIA@"+
				"MLDI&&&MALDEN ISLAND@"+
				"MLDV&&&MALDIVES@"+
				"MALI&&&MALI@"+
				"MLTA&&&MALTA@"+
				"RMI&&&MARSHALL ISLANDS@"+
				"MART&&&MARTINIQUE@"+
				"MAUR&&&MAURITANIA@"+
				"MRTS&&&MAURITIUS@"+
				"MYT&&&MAYOTTE@"+
				"AGS&&&MEXICO - AGUASCALIENTES@"+
				"BC&&&MEXICO - BAJA CALIFORNIA NORTE@"+
				"BCSR&&&MEXICO - BAJA CALIFORNIA SUR@"+
				"CAMP&&&MEXICO - CAMPECHE@"+
				"CHIS&&&MEXICO - CHIAPAS@"+
				"CHIH&&&MEXICO - CHIHUAHUA@"+
				"COAH&&&MEXICO - COAHUILA@"+
				"COLI&&&MEXICO - COLIMA@"+
				"DF&&&MEXICO - DISTRITO FEDERAL@"+
				"DGO&&&MEXICO - DURANGO@"+
				"GTO&&&MEXICO - GUANAJUATO@"+
				"GRO&&&MEXICO - GUERRERO@"+
				"HGO&&&MEXICO - HIDALGO@"+
				"JAL&&&MEXICO - JALISCO@"+
				"MCH&&&MEXICO - MICHOACAN@"+
				"MOR&&&MEXICO - MORELOS@"+
				"NAY&&&MEXICO - NAYARIT@"+
				"NL&&&MEXICO - NUEVO LEON@"+
				"OAX&&&MEXICO - OAXACA@"+
				"PUE&&&MEXICO - PUEBLA@"+
				"QRO&&&MEXICO - QUERETARO@"+
				"QROO&&&MEXICO - QUINTANA ROO@"+
				"SLP&&&MEXICO - SAN LUIS POTOSI@"+
				"SIN&&&MEXICO - SINALOA@"+
				"SON&&&MEXICO - SONORA@"+
				"MXCO&&&MEXICO - STATE OF MEXICO@"+
				"TAB&&&MEXICO - TABASCO@"+
				"TAMP&&&MEXICO - TAMAULIPAS@"+
				"TLAX&&&MEXICO - TLAXCALA@"+
				"VER&&&MEXICO - VERACRUZ@"+
				"YUC&&&MEXICO - YUCATAN@"+
				"ZAC&&&MEXICO - ZACATECAS@"+
				"FSM&&&MICRONESIA@"+
				"MDWI&&&MIDWAY ISLANDS@"+
				"MLD&&&MOLDOVA@"+
				"MON&&&MONACO@"+
				"MONG&&&MONGOLIA@"+
				"MTG&&&MONTENEGRO@"+
				"MONT&&&MONTSERRAT@"+
				"MORO&&&MOROCCO@"+
				"MOZ&&&MOZAMBIQUE@"+
				"NAMB&&&NAMIBIA@"+
				"NAU&&&NAURU@"+
				"NEP&&&NEPAL@"+
				"NETH&&&NETHERLANDS@"+
				"NCAL&&&NEW CALEDONIA@"+
				"NZLD&&&NEW ZEALAND@"+
				"NIC&&&NICARAGUA@"+
				"NIR&&&NIGER@"+
				"NRA&&&NIGERIA@"+
				"NIUE&&&NIUE@"+
				"NFK&&&NORFOLK ISLAND@"+
				"MNP&&&NORTH MARIANA ISLANDS@"+
				"NIRE&&&NORTHERN IRELAND@"+
				"NORW&&&NORWAY@"+
				"OMAN&&&OMAN@"+
				"PKST&&&PAKISTAN@"+
				"PALA&&&PALAU@"+
				"PLMR&&&PALMYRA ATOLL@"+
				"PAN&&&PANAMA@"+
				"PNG&&&PAPUA NEW GUINEA@"+
				"PARA&&&PARAGUAY@"+
				"PERU&&&PERU@"+
				"PHIL&&&PHILIPPINES@"+
				"PITC&&&PITCAIRN ISLANDS@"+
				"POL&&&POLAND@"+
				"PORT&&&PORTUGAL@"+
				"PR&&&PUERTO RICO@"+
				"QTAR&&&QATAR@"+
				"REUN&&&REUNION@"+
				"ROM&&&ROMANIA@"+
				"RUS&&&RUSSIA@"+
				"RWND&&&RWANDA@"+
				"SABA&&&SABA ISLAND@"+
				"MAF&&&SAINT MARTIN@"+
				"WSAM&&&SAMOA@"+
				"SMAR&&&SAN MARINO@"+
				"STPR&&&SAO TOME AND PRINCIPE@"+
				"SARB&&&SAUDI ARABIA@"+
				"SENG&&&SENEGAL@"+
				"SBA&&&SERBIA@"+
				"SEYC&&&SEYCHELLES@"+
				"SLEO&&&SIERRA LEONE@"+
				"SING&&&SINGAPORE@"+
				"STM&&&SINT MAARTEN@"+
				"SVK&&&SLOVAKIA@"+
				"SVN&&&SLOVENIA@"+
				"SLMN&&&SOLOMON ISLANDS@"+
				"SOMA&&&SOMALIA@"+
				"SAFR&&&SOUTH AFRICA@"+
				"SGS&&&SOUTH GEORGIA AND THE SOUTH SANDWICH ISLAND@"+
				"SSDN&&&SOUTH SUDAN@"+
				"SPN&&&SPAIN@"+
				"SRL&&&SRI LANKA@"+
				"STBR&&&ST. BARTHELEMY@"+
				"STEU&&&ST. EUSTATIUS@"+
				"SHEL&&&ST. HELENA@"+
				"STCN&&&ST. KITTS AND NEVIS@"+
				"SLCA&&&ST. LUCIA@"+
				"SPMI&&&ST. PIERRE AND MIQUELON@"+
				"STVN&&&ST. VINCENT AND THE GRENADINES@"+
				"SUDA&&&SUDAN@"+
				"SURM&&&SURINAME@"+
				"SJM&&&SVALBARD@"+
				"SZLD&&&SWAZILAND@"+
				"SWDN&&&SWEDEN@"+
				"SWTZ&&&SWITZERLAND@"+
				"SYR&&&SYRIA@"+
				"TWAN&&&TAIWAN@"+
				"TJK&&&TAJIKISTAN@"+
				"TAZN&&&TANZANIA@"+
				"THAI&&&THAILAND@"+
				"TMOR&&&TIMOR-LESTE@"+
				"TOGO&&&TOGO@"+
				"TKL&&&TOKELAU@"+
				"TONG&&&TONGA@"+
				"TRIN&&&TRINIDAD AND TOBAGO@"+
				"TNSA&&&TUNISIA@"+
				"TRKY&&&TURKEY@"+
				"TKM&&&TURKMENISTAN@"+
				"TCIS&&&TURKS AND CAICOS ISLANDS@"+
				"TUV&&&TUVALU@"+
				"UGAN&&&UGANDA@"+
				"UKR&&&UKRAINE@"+
				"UAE&&&UNITED ARAB EMIRATES@"+
				"GRBR&&&UNITED KINGDOM@"+
				"USA&&&UNITED STATES OF AMERICA@"+
				"URU&&&URUGUAY@"+
				"UZB&&&UZBEKISTAN@"+
				"VANU&&&VANUATU@"+
				"VENZ&&&VENEZUELA@"+
				"VTNM&&&VIETNAM@"+
				"VI&&&VIRGIN ISLANDS (U.S.)@"+
				"BRVI&&&VIRGIN ISLANDS, BRITISH@"+
				"WKI&&&WAKE ISLAND@"+
				"WAFT&&&WALLIS AND FUTUNA ISLANDS@"+
				"XWB&&&WEST BANK@"+
				"SSAH&&&WESTERN SAHARA@"+
				"YEM&&&YEMEN@"+
				"ZAMB&&&ZAMBIA@"+
				"ZIMB&&&ZIMBABWE";
		
		
		String sqlStr = "";
		String[] split = countrys.split("@");
		System.out.println(split.length);
		for (String string : split) {
			String[] ss = string.split("&&&");
			sqlStr += "INSERT INTO t_country_r (internationalcode, name) VALUES ('"+ss[0]+"','"+ss[1]+"');";
			
			String code = ss[0];
			
			
		}
		
		System.out.println(sqlStr);


	}
}
