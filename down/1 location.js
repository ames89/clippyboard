/*2*/
(function() {
    var data = '';

    var inputs = ['input[id^="location_locationSummary"]', 
				  'input[id^="location_nearestAirport_name"]', 
				  'input[id^="location_nearestBeach_name"]', 
				  'input[id^="location_nearestFerry_name"]', 
				  'input[id^="location_nearestTrain_name"]', 
				  'input[id^="location_nearestHighway_name"]',
				  'input[id^="location_nearestBar_name"]', 
				  'input[id^="location_nearestSki_name"]', 
				  'input[id^="location_nearestGolf_name"]', 
				  'input[id^="location_nearestRestaurant_name"]',
				  'textarea[id^="location_description"]'];

    $.each(inputs, function(i, val) {
        if ($(val).length && $(val).val().trim()) {
            data += $(val).val().trim() + '\n\n\n\n\n\n';
        }
    });

    console.info(data);
})();