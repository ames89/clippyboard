/*2*/
(function() {
    var data = '';

    var inputs = ['input[id^="propertyNameField"]', 'input[id^="headlineField"]', 'input[id^="accommodationsSummaryField"]', 'textarea[id^="descriptionField"]', 'textarea[id^="ownerListingStoryField"]', 'textarea[id^="whyPurchasedField"]', 'textarea[id^="uniqueBenefitsField"]'];

    $.each(inputs, function(i, val) {
        if ($(val).length && $(val).val().trim()) {
            data += $(val).val().trim() + '\n\n\n\n\n\n';
        }
    });

    console.info(data);
})();