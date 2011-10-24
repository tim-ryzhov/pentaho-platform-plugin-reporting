package org.pentaho.reporting.platform.plugin.gwt.client;

import java.util.HashMap;

/**
 * Todo: Document me!
 * <p/>
 * Date: 27.07.2010
 * Time: 16:10:24
 *
 * @author Thomas Morgner.
 * @noinspection HardCodedStringLiteral
 */
public class TimeZoneOffsets
{
  private static TimeZoneOffsets instance;
  private HashMap<String, Integer> map;

  public TimeZoneOffsets()
  {
    map = new HashMap<String, Integer>();
    map.put("ACT", 570);
    map.put("AET", 600);
    map.put("AGT", -180);
    map.put("ART", 120);
    map.put("AST", -540);
    map.put("Africa/Abidjan", 0);
    map.put("Africa/Accra", 0);
    map.put("Africa/Addis_Ababa", 180);
    map.put("Africa/Algiers", 60);
    map.put("Africa/Asmara", 180);
    map.put("Africa/Asmera", 180);
    map.put("Africa/Bamako", 0);
    map.put("Africa/Bangui", 60);
    map.put("Africa/Banjul", 0);
    map.put("Africa/Bissau", 0);
    map.put("Africa/Blantyre", 120);
    map.put("Africa/Brazzaville", 60);
    map.put("Africa/Bujumbura", 120);
    map.put("Africa/Cairo", 120);
    map.put("Africa/Casablanca", 0);
    map.put("Africa/Ceuta", 60);
    map.put("Africa/Conakry", 0);
    map.put("Africa/Dakar", 0);
    map.put("Africa/Dar_es_Salaam", 180);
    map.put("Africa/Djibouti", 180);
    map.put("Africa/Douala", 60);
    map.put("Africa/El_Aaiun", 0);
    map.put("Africa/Freetown", 0);
    map.put("Africa/Gaborone", 120);
    map.put("Africa/Harare", 120);
    map.put("Africa/Johannesburg", 120);
    map.put("Africa/Kampala", 180);
    map.put("Africa/Khartoum", 180);
    map.put("Africa/Kigali", 120);
    map.put("Africa/Kinshasa", 60);
    map.put("Africa/Lagos", 60);
    map.put("Africa/Libreville", 60);
    map.put("Africa/Lome", 0);
    map.put("Africa/Luanda", 60);
    map.put("Africa/Lubumbashi", 120);
    map.put("Africa/Lusaka", 120);
    map.put("Africa/Malabo", 60);
    map.put("Africa/Maputo", 120);
    map.put("Africa/Maseru", 120);
    map.put("Africa/Mbabane", 120);
    map.put("Africa/Mogadishu", 180);
    map.put("Africa/Monrovia", 0);
    map.put("Africa/Nairobi", 180);
    map.put("Africa/Ndjamena", 60);
    map.put("Africa/Niamey", 60);
    map.put("Africa/Nouakchott", 0);
    map.put("Africa/Ouagadougou", 0);
    map.put("Africa/Porto-Novo", 60);
    map.put("Africa/Sao_Tome", 0);
    map.put("Africa/Timbuktu", 0);
    map.put("Africa/Tripoli", 120);
    map.put("Africa/Tunis", 60);
    map.put("Africa/Windhoek", 60);
    map.put("America/Adak", -600);
    map.put("America/Anchorage", -540);
    map.put("America/Anguilla", -240);
    map.put("America/Antigua", -240);
    map.put("America/Araguaina", -180);
    map.put("America/Argentina/Buenos_Aires", -180);
    map.put("America/Argentina/Catamarca", -180);
    map.put("America/Argentina/ComodRivadavia", -180);
    map.put("America/Argentina/Cordoba", -180);
    map.put("America/Argentina/Jujuy", -180);
    map.put("America/Argentina/La_Rioja", -180);
    map.put("America/Argentina/Mendoza", -180);
    map.put("America/Argentina/Rio_Gallegos", -180);
    map.put("America/Argentina/Salta", -180);
    map.put("America/Argentina/San_Juan", -180);
    map.put("America/Argentina/San_Luis", -240);
    map.put("America/Argentina/Tucuman", -180);
    map.put("America/Argentina/Ushuaia", -180);
    map.put("America/Aruba", -240);
    map.put("America/Asuncion", -240);
    map.put("America/Atikokan", -300);
    map.put("America/Atka", -600);
    map.put("America/Bahia", -180);
    map.put("America/Barbados", -240);
    map.put("America/Belem", -180);
    map.put("America/Belize", -360);
    map.put("America/Blanc-Sablon", -240);
    map.put("America/Boa_Vista", -240);
    map.put("America/Bogota", -300);
    map.put("America/Boise", -420);
    map.put("America/Buenos_Aires", -180);
    map.put("America/Cambridge_Bay", -420);
    map.put("America/Campo_Grande", -240);
    map.put("America/Cancun", -360);
    map.put("America/Caracas", -270);
    map.put("America/Catamarca", -180);
    map.put("America/Cayenne", -180);
    map.put("America/Cayman", -300);
    map.put("America/Chicago", -360);
    map.put("America/Chihuahua", -420);
    map.put("America/Coral_Harbour", -300);
    map.put("America/Cordoba", -180);
    map.put("America/Costa_Rica", -360);
    map.put("America/Cuiaba", -240);
    map.put("America/Curacao", -240);
    map.put("America/Danmarkshavn", 0);
    map.put("America/Dawson", -480);
    map.put("America/Dawson_Creek", -420);
    map.put("America/Denver", -420);
    map.put("America/Detroit", -300);
    map.put("America/Dominica", -240);
    map.put("America/Edmonton", -420);
    map.put("America/Eirunepe", -240);
    map.put("America/El_Salvador", -360);
    map.put("America/Ensenada", -480);
    map.put("America/Fort_Wayne", -300);
    map.put("America/Fortaleza", -180);
    map.put("America/Glace_Bay", -240);
    map.put("America/Godthab", -180);
    map.put("America/Goose_Bay", -240);
    map.put("America/Grand_Turk", -300);
    map.put("America/Grenada", -240);
    map.put("America/Guadeloupe", -240);
    map.put("America/Guatemala", -360);
    map.put("America/Guayaquil", -300);
    map.put("America/Guyana", -240);
    map.put("America/Halifax", -240);
    map.put("America/Havana", -300);
    map.put("America/Hermosillo", -420);
    map.put("America/Indiana/Indianapolis", -300);
    map.put("America/Indiana/Knox", -360);
    map.put("America/Indiana/Marengo", -300);
    map.put("America/Indiana/Petersburg", -300);
    map.put("America/Indiana/Tell_City", -360);
    map.put("America/Indiana/Vevay", -300);
    map.put("America/Indiana/Vincennes", -300);
    map.put("America/Indiana/Winamac", -300);
    map.put("America/Indianapolis", -300);
    map.put("America/Inuvik", -420);
    map.put("America/Iqaluit", -300);
    map.put("America/Jamaica", -300);
    map.put("America/Jujuy", -180);
    map.put("America/Juneau", -540);
    map.put("America/Kentucky/Louisville", -300);
    map.put("America/Kentucky/Monticello", -300);
    map.put("America/Knox_IN", -360);
    map.put("America/La_Paz", -240);
    map.put("America/Lima", -300);
    map.put("America/Los_Angeles", -480);
    map.put("America/Louisville", -300);
    map.put("America/Maceio", -180);
    map.put("America/Managua", -360);
    map.put("America/Manaus", -240);
    map.put("America/Marigot", -240);
    map.put("America/Martinique", -240);
    map.put("America/Matamoros", -360);
    map.put("America/Mazatlan", -420);
    map.put("America/Mendoza", -180);
    map.put("America/Menominee", -360);
    map.put("America/Merida", -360);
    map.put("America/Mexico_City", -360);
    map.put("America/Miquelon", -180);
    map.put("America/Moncton", -240);
    map.put("America/Monterrey", -360);
    map.put("America/Montevideo", -180);
    map.put("America/Montreal", -300);
    map.put("America/Montserrat", -240);
    map.put("America/Nassau", -300);
    map.put("America/New_York", -300);
    map.put("America/Nipigon", -300);
    map.put("America/Nome", -540);
    map.put("America/Noronha", -120);
    map.put("America/North_Dakota/Center", -360);
    map.put("America/North_Dakota/New_Salem", -360);
    map.put("America/Ojinaga", -420);
    map.put("America/Panama", -300);
    map.put("America/Pangnirtung", -300);
    map.put("America/Paramaribo", -180);
    map.put("America/Phoenix", -420);
    map.put("America/Port-au-Prince", -300);
    map.put("America/Port_of_Spain", -240);
    map.put("America/Porto_Acre", -240);
    map.put("America/Porto_Velho", -240);
    map.put("America/Puerto_Rico", -240);
    map.put("America/Rainy_River", -360);
    map.put("America/Rankin_Inlet", -360);
    map.put("America/Recife", -180);
    map.put("America/Regina", -360);
    map.put("America/Resolute", -300);
    map.put("America/Rio_Branco", -240);
    map.put("America/Rosario", -180);
    map.put("America/Santa_Isabel", -480);
    map.put("America/Santarem", -180);
    map.put("America/Santiago", -240);
    map.put("America/Santo_Domingo", -240);
    map.put("America/Sao_Paulo", -180);
    map.put("America/Scoresbysund", -60);
    map.put("America/Shiprock", -420);
    map.put("America/St_Barthelemy", -240);
    map.put("America/St_Johns", -210);
    map.put("America/St_Kitts", -240);
    map.put("America/St_Lucia", -240);
    map.put("America/St_Thomas", -240);
    map.put("America/St_Vincent", -240);
    map.put("America/Swift_Current", -360);
    map.put("America/Tegucigalpa", -360);
    map.put("America/Thule", -240);
    map.put("America/Thunder_Bay", -300);
    map.put("America/Tijuana", -480);
    map.put("America/Toronto", -300);
    map.put("America/Tortola", -240);
    map.put("America/Vancouver", -480);
    map.put("America/Virgin", -240);
    map.put("America/Whitehorse", -480);
    map.put("America/Winnipeg", -360);
    map.put("America/Yakutat", -540);
    map.put("America/Yellowknife", -420);
    map.put("Antarctica/Casey", 660);
    map.put("Antarctica/Davis", 300);
    map.put("Antarctica/DumontDUrville", 600);
    map.put("Antarctica/Mawson", 300);
    map.put("Antarctica/McMurdo", 720);
    map.put("Antarctica/Palmer", -240);
    map.put("Antarctica/Rothera", -180);
    map.put("Antarctica/South_Pole", 720);
    map.put("Antarctica/Syowa", 180);
    map.put("Antarctica/Vostok", 360);
    map.put("Arctic/Longyearbyen", 60);
    map.put("Asia/Aden", 180);
    map.put("Asia/Almaty", 360);
    map.put("Asia/Amman", 120);
    map.put("Asia/Anadyr", 720);
    map.put("Asia/Aqtau", 300);
    map.put("Asia/Aqtobe", 300);
    map.put("Asia/Ashgabat", 300);
    map.put("Asia/Ashkhabad", 300);
    map.put("Asia/Baghdad", 180);
    map.put("Asia/Bahrain", 180);
    map.put("Asia/Baku", 240);
    map.put("Asia/Bangkok", 420);
    map.put("Asia/Beirut", 120);
    map.put("Asia/Bishkek", 360);
    map.put("Asia/Brunei", 480);
    map.put("Asia/Calcutta", 330);
    map.put("Asia/Choibalsan", 480);
    map.put("Asia/Chongqing", 480);
    map.put("Asia/Chungking", 480);
    map.put("Asia/Colombo", 330);
    map.put("Asia/Dacca", 360);
    map.put("Asia/Damascus", 120);
    map.put("Asia/Dhaka", 360);
    map.put("Asia/Dili", 540);
    map.put("Asia/Dubai", 240);
    map.put("Asia/Dushanbe", 300);
    map.put("Asia/Gaza", 120);
    map.put("Asia/Harbin", 480);
    map.put("Asia/Ho_Chi_Minh", 420);
    map.put("Asia/Hong_Kong", 480);
    map.put("Asia/Hovd", 420);
    map.put("Asia/Irkutsk", 480);
    map.put("Asia/Istanbul", 120);
    map.put("Asia/Jakarta", 420);
    map.put("Asia/Jayapura", 540);
    map.put("Asia/Jerusalem", 120);
    map.put("Asia/Kabul", 270);
    map.put("Asia/Kamchatka", 720);
    map.put("Asia/Karachi", 300);
    map.put("Asia/Kashgar", 480);
    map.put("Asia/Kathmandu", 345);
    map.put("Asia/Katmandu", 345);
    map.put("Asia/Kolkata", 330);
    map.put("Asia/Krasnoyarsk", 420);
    map.put("Asia/Kuala_Lumpur", 480);
    map.put("Asia/Kuching", 480);
    map.put("Asia/Kuwait", 180);
    map.put("Asia/Macao", 480);
    map.put("Asia/Macau", 480);
    map.put("Asia/Magadan", 660);
    map.put("Asia/Makassar", 480);
    map.put("Asia/Manila", 480);
    map.put("Asia/Muscat", 240);
    map.put("Asia/Nicosia", 120);
    map.put("Asia/Novokuznetsk", 360);
    map.put("Asia/Novosibirsk", 360);
    map.put("Asia/Omsk", 360);
    map.put("Asia/Oral", 300);
    map.put("Asia/Phnom_Penh", 420);
    map.put("Asia/Pontianak", 420);
    map.put("Asia/Pyongyang", 540);
    map.put("Asia/Qatar", 180);
    map.put("Asia/Qyzylorda", 360);
    map.put("Asia/Rangoon", 390);
    map.put("Asia/Riyadh", 180);
    map.put("Asia/Riyadh87", 187);
    map.put("Asia/Riyadh88", 187);
    map.put("Asia/Riyadh89", 187);
    map.put("Asia/Saigon", 420);
    map.put("Asia/Sakhalin", 600);
    map.put("Asia/Samarkand", 300);
    map.put("Asia/Seoul", 540);
    map.put("Asia/Shanghai", 480);
    map.put("Asia/Singapore", 480);
    map.put("Asia/Taipei", 480);
    map.put("Asia/Tashkent", 300);
    map.put("Asia/Tbilisi", 240);
    map.put("Asia/Tehran", 210);
    map.put("Asia/Tel_Aviv", 120);
    map.put("Asia/Thimbu", 360);
    map.put("Asia/Thimphu", 360);
    map.put("Asia/Tokyo", 540);
    map.put("Asia/Ujung_Pandang", 480);
    map.put("Asia/Ulaanbaatar", 480);
    map.put("Asia/Ulan_Bator", 480);
    map.put("Asia/Urumqi", 480);
    map.put("Asia/Vientiane", 420);
    map.put("Asia/Vladivostok", 600);
    map.put("Asia/Yakutsk", 540);
    map.put("Asia/Yekaterinburg", 300);
    map.put("Asia/Yerevan", 240);
    map.put("Atlantic/Azores", -60);
    map.put("Atlantic/Bermuda", -240);
    map.put("Atlantic/Canary", 0);
    map.put("Atlantic/Cape_Verde", -60);
    map.put("Atlantic/Faeroe", 0);
    map.put("Atlantic/Faroe", 0);
    map.put("Atlantic/Jan_Mayen", 60);
    map.put("Atlantic/Madeira", 0);
    map.put("Atlantic/Reykjavik", 0);
    map.put("Atlantic/South_Georgia", -120);
    map.put("Atlantic/St_Helena", 0);
    map.put("Atlantic/Stanley", -240);
    map.put("Australia/ACT", 600);
    map.put("Australia/Adelaide", 570);
    map.put("Australia/Brisbane", 600);
    map.put("Australia/Broken_Hill", 570);
    map.put("Australia/Canberra", 600);
    map.put("Australia/Currie", 600);
    map.put("Australia/Darwin", 570);
    map.put("Australia/Eucla", 525);
    map.put("Australia/Hobart", 600);
    map.put("Australia/LHI", 630);
    map.put("Australia/Lindeman", 600);
    map.put("Australia/Lord_Howe", 630);
    map.put("Australia/Melbourne", 600);
    map.put("Australia/NSW", 600);
    map.put("Australia/North", 570);
    map.put("Australia/Perth", 480);
    map.put("Australia/Queensland", 600);
    map.put("Australia/South", 570);
    map.put("Australia/Sydney", 600);
    map.put("Australia/Tasmania", 600);
    map.put("Australia/Victoria", 600);
    map.put("Australia/West", 480);
    map.put("Australia/Yancowinna", 570);
    map.put("BET", -180);
    map.put("BST", 360);
    map.put("Brazil/Acre", -240);
    map.put("Brazil/DeNoronha", -120);
    map.put("Brazil/East", -180);
    map.put("Brazil/West", -240);
    map.put("CAT", 120);
    map.put("CET", 60);
    map.put("CNT", -210);
    map.put("CST", -360);
    map.put("CST6CDT", -360);
    map.put("CTT", 480);
    map.put("Canada/Atlantic", -240);
    map.put("Canada/Central", -360);
    map.put("Canada/East-Saskatchewan", -360);
    map.put("Canada/Eastern", -300);
    map.put("Canada/Mountain", -420);
    map.put("Canada/Newfoundland", -210);
    map.put("Canada/Pacific", -480);
    map.put("Canada/Saskatchewan", -360);
    map.put("Canada/Yukon", -480);
    map.put("Chile/Continental", -240);
    map.put("Chile/EasterIsland", -360);
    map.put("Cuba", -300);
    map.put("EAT", 180);
    map.put("ECT", 60);
    map.put("EET", 120);
    map.put("EST", -300);
    map.put("EST5EDT", -300);
    map.put("Egypt", 120);
    map.put("Eire", 0);
    map.put("Etc/GMT", 0);
    map.put("Etc/GMT+0", 0);
    map.put("Etc/GMT+1", -60);
    map.put("Etc/GMT+10", -600);
    map.put("Etc/GMT+11", -660);
    map.put("Etc/GMT+12", -720);
    map.put("Etc/GMT+2", -120);
    map.put("Etc/GMT+3", -180);
    map.put("Etc/GMT+4", -240);
    map.put("Etc/GMT+5", -300);
    map.put("Etc/GMT+6", -360);
    map.put("Etc/GMT+7", -420);
    map.put("Etc/GMT+8", -480);
    map.put("Etc/GMT+9", -540);
    map.put("Etc/GMT-0", 0);
    map.put("Etc/GMT-1", 60);
    map.put("Etc/GMT-10", 600);
    map.put("Etc/GMT-11", 660);
    map.put("Etc/GMT-12", 720);
    map.put("Etc/GMT-13", 780);
    map.put("Etc/GMT-14", 840);
    map.put("Etc/GMT-2", 120);
    map.put("Etc/GMT-3", 180);
    map.put("Etc/GMT-4", 240);
    map.put("Etc/GMT-5", 300);
    map.put("Etc/GMT-6", 360);
    map.put("Etc/GMT-7", 420);
    map.put("Etc/GMT-8", 480);
    map.put("Etc/GMT-9", 540);
    map.put("Etc/GMT0", 0);
    map.put("Etc/Greenwich", 0);
    map.put("Etc/UCT", 0);
    map.put("Etc/UTC", 0);
    map.put("Etc/Universal", 0);
    map.put("Etc/Zulu", 0);
    map.put("Europe/Amsterdam", 60);
    map.put("Europe/Andorra", 60);
    map.put("Europe/Athens", 120);
    map.put("Europe/Belfast", 0);
    map.put("Europe/Belgrade", 60);
    map.put("Europe/Berlin", 60);
    map.put("Europe/Bratislava", 60);
    map.put("Europe/Brussels", 60);
    map.put("Europe/Bucharest", 120);
    map.put("Europe/Budapest", 60);
    map.put("Europe/Chisinau", 120);
    map.put("Europe/Copenhagen", 60);
    map.put("Europe/Dublin", 0);
    map.put("Europe/Gibraltar", 60);
    map.put("Europe/Guernsey", 0);
    map.put("Europe/Helsinki", 120);
    map.put("Europe/Isle_of_Man", 0);
    map.put("Europe/Istanbul", 120);
    map.put("Europe/Jersey", 0);
    map.put("Europe/Kaliningrad", 120);
    map.put("Europe/Kiev", 120);
    map.put("Europe/Lisbon", 0);
    map.put("Europe/Ljubljana", 60);
    map.put("Europe/London", 0);
    map.put("Europe/Luxembourg", 60);
    map.put("Europe/Madrid", 60);
    map.put("Europe/Malta", 60);
    map.put("Europe/Mariehamn", 120);
    map.put("Europe/Minsk", 120);
    map.put("Europe/Monaco", 60);
    map.put("Europe/Moscow", 180);
    map.put("Europe/Nicosia", 120);
    map.put("Europe/Oslo", 60);
    map.put("Europe/Paris", 60);
    map.put("Europe/Podgorica", 60);
    map.put("Europe/Prague", 60);
    map.put("Europe/Riga", 120);
    map.put("Europe/Rome", 60);
    map.put("Europe/Samara", 240);
    map.put("Europe/San_Marino", 60);
    map.put("Europe/Sarajevo", 60);
    map.put("Europe/Simferopol", 120);
    map.put("Europe/Skopje", 60);
    map.put("Europe/Sofia", 120);
    map.put("Europe/Stockholm", 60);
    map.put("Europe/Tallinn", 120);
    map.put("Europe/Tirane", 60);
    map.put("Europe/Tiraspol", 120);
    map.put("Europe/Uzhgorod", 120);
    map.put("Europe/Vaduz", 60);
    map.put("Europe/Vatican", 60);
    map.put("Europe/Vienna", 60);
    map.put("Europe/Vilnius", 120);
    map.put("Europe/Volgograd", 180);
    map.put("Europe/Warsaw", 60);
    map.put("Europe/Zagreb", 60);
    map.put("Europe/Zaporozhye", 120);
    map.put("Europe/Zurich", 60);
    map.put("GB", 0);
    map.put("GB-Eire", 0);
    map.put("GMT", 0);
    map.put("GMT0", 0);
    map.put("Greenwich", 0);
    map.put("HST", -600);
    map.put("Hongkong", 480);
    map.put("IET", -300);
    map.put("IST", 330);
    map.put("Iceland", 0);
    map.put("Indian/Antananarivo", 180);
    map.put("Indian/Chagos", 360);
    map.put("Indian/Christmas", 420);
    map.put("Indian/Cocos", 390);
    map.put("Indian/Comoro", 180);
    map.put("Indian/Kerguelen", 300);
    map.put("Indian/Mahe", 240);
    map.put("Indian/Maldives", 300);
    map.put("Indian/Mauritius", 240);
    map.put("Indian/Mayotte", 180);
    map.put("Indian/Reunion", 240);
    map.put("Iran", 210);
    map.put("Israel", 120);
    map.put("JST", 540);
    map.put("Jamaica", -300);
    map.put("Japan", 540);
    map.put("Kwajalein", 720);
    map.put("Libya", 120);
    map.put("MET", 60);
    map.put("MIT", -660);
    map.put("MST", -420);
    map.put("MST7MDT", -420);
    map.put("Mexico/BajaNorte", -480);
    map.put("Mexico/BajaSur", -420);
    map.put("Mexico/General", -360);
    map.put("Mideast/Riyadh87", 187);
    map.put("Mideast/Riyadh88", 187);
    map.put("Mideast/Riyadh89", 187);
    map.put("NET", 240);
    map.put("NST", 720);
    map.put("NZ", 720);
    map.put("NZ-CHAT", 765);
    map.put("Navajo", -420);
    map.put("PLT", 300);
    map.put("PNT", -420);
    map.put("PRC", 480);
    map.put("PRT", -240);
    map.put("PST", -480);
    map.put("PST8PDT", -480);
    map.put("Pacific/Apia", -660);
    map.put("Pacific/Auckland", 720);
    map.put("Pacific/Chatham", 765);
    map.put("Pacific/Easter", -360);
    map.put("Pacific/Efate", 660);
    map.put("Pacific/Enderbury", 780);
    map.put("Pacific/Fakaofo", -600);
    map.put("Pacific/Fiji", 720);
    map.put("Pacific/Funafuti", 720);
    map.put("Pacific/Galapagos", -360);
    map.put("Pacific/Gambier", -540);
    map.put("Pacific/Guadalcanal", 660);
    map.put("Pacific/Guam", 600);
    map.put("Pacific/Honolulu", -600);
    map.put("Pacific/Johnston", -600);
    map.put("Pacific/Kiritimati", 840);
    map.put("Pacific/Kosrae", 660);
    map.put("Pacific/Kwajalein", 720);
    map.put("Pacific/Majuro", 720);
    map.put("Pacific/Marquesas", -570);
    map.put("Pacific/Midway", -660);
    map.put("Pacific/Nauru", 720);
    map.put("Pacific/Niue", -660);
    map.put("Pacific/Norfolk", 690);
    map.put("Pacific/Noumea", 660);
    map.put("Pacific/Pago_Pago", -660);
    map.put("Pacific/Palau", 540);
    map.put("Pacific/Pitcairn", -480);
    map.put("Pacific/Ponape", 660);
    map.put("Pacific/Port_Moresby", 600);
    map.put("Pacific/Rarotonga", -600);
    map.put("Pacific/Saipan", 600);
    map.put("Pacific/Samoa", -660);
    map.put("Pacific/Tahiti", -600);
    map.put("Pacific/Tarawa", 720);
    map.put("Pacific/Tongatapu", 780);
    map.put("Pacific/Truk", 600);
    map.put("Pacific/Wake", 720);
    map.put("Pacific/Wallis", 720);
    map.put("Pacific/Yap", 600);
    map.put("Poland", 60);
    map.put("Portugal", 0);
    map.put("ROK", 540);
    map.put("SST", 660);
    map.put("Singapore", 480);
    map.put("SystemV/AST4", -240);
    map.put("SystemV/AST4ADT", -240);
    map.put("SystemV/CST6", -360);
    map.put("SystemV/CST6CDT", -360);
    map.put("SystemV/EST5", -300);
    map.put("SystemV/EST5EDT", -300);
    map.put("SystemV/HST10", -600);
    map.put("SystemV/MST7", -420);
    map.put("SystemV/MST7MDT", -420);
    map.put("SystemV/PST8", -480);
    map.put("SystemV/PST8PDT", -480);
    map.put("SystemV/YST9", -540);
    map.put("SystemV/YST9YDT", -540);
    map.put("Turkey", 120);
    map.put("UCT", 0);
    map.put("US/Alaska", -540);
    map.put("US/Aleutian", -600);
    map.put("US/Arizona", -420);
    map.put("US/Central", -360);
    map.put("US/East-Indiana", -300);
    map.put("US/Eastern", -300);
    map.put("US/Hawaii", -600);
    map.put("US/Indiana-Starke", -360);
    map.put("US/Michigan", -300);
    map.put("US/Mountain", -420);
    map.put("US/Pacific", -480);
    map.put("US/Pacific-New", -480);
    map.put("US/Samoa", -660);
    map.put("UTC", 0);
    map.put("Universal", 0);
    map.put("VST", 420);
    map.put("W-SU", 180);
    map.put("WET", 0);
    map.put("Zulu", 0);
  }

  /**
   * Returns the timezone offset in minutes.
   * 
   * @param timezone
   * @return
   */
  public int getOffset(final String timezone)
  {
    final Integer integer = map.get(timezone);
    if (integer == null)
    {
      return 0;
    }
    return integer;
  }

  public String getOffsetAsString(final String timezone)
  {
    final StringBuffer value = new StringBuffer();
    final int minutesRaw = getOffset(timezone);
    final int hours = Math.abs(minutesRaw / 60);
    final int minutes = Math.abs(minutesRaw % 60);

    if (minutesRaw >= 0)
    {
      value.append('+');
    }
    else
    {
      value.append('-');
    }
    if (hours < 10)
    {
      value.append("0");
    }
    value.append(hours);
    if (minutes < 10)
    {
      value.append("0");
    }
    value.append(minutes);
    return value.toString();
  }

  public static String formatOffset(final long offsetInMinutes)
  {
    final StringBuffer value = new StringBuffer();
    final int minutesRaw = (int) offsetInMinutes;
    final int hours = Math.abs(minutesRaw / 60);
    final int minutes = Math.abs(minutesRaw % 60);

    if (minutesRaw >= 0)
    {
      value.append('+');
    }
    else
    {
      value.append('-');
    }
    if (hours < 10)
    {
      value.append("0");
    }
    value.append(hours);
    if (minutes < 10)
    {
      value.append("0");
    }
    value.append(minutes);
    return value.toString();
  }


  public static TimeZoneOffsets getInstance()
  {
    if (instance == null)
    {
      instance = new TimeZoneOffsets();
    }
    return instance;
  }
}
