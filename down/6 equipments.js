/*2*/
(function(){
var data='',regex=new RegExp('^([A-z]+):'),jumplines='\n\n\n\n\n\n';

if($('input[id^="unitNameField"]').length){
    if($('input[id^="unitNameField"]').val().trim()){
        data+=$('input[id^="unitNameField"]').val().trim()+jumplines;
    }
}

if($('textarea[id^="unitDescriptionField"]').length){
    if($('textarea[id^="unitDescriptionField"]').val().trim()){
        data+=$('textarea[id^="unitDescriptionField"]').val().trim()+jumplines;
    }
}

if($('input[id^="checkBoxId_description"]').length){
    if($('input[id^="checkBoxId_description"]').val().trim()){
        data+=$('input[id^="checkBoxId_description"]').val().trim()+jumplines;
    }
}

$('#bedrooms').find('div.room-details').each(function(){
  $(this).find('a, .room-note').each(function(){
    if($(this).html().trim()){
      data+=$(this).html().trim().replace(regex,'').trim()+jumplines;
    }
  });
});

if($('textarea[id^="bedroomDetailsField"]').length){
    if($('textarea[id^="bedroomDetailsField"]').val().trim()){
        data+=$('textarea[id^="bedroomDetailsField"]').val().trim()+jumplines;
    }
}

$('#bathrooms').find('div.room-details').each(function(){
  $(this).find('a, .room-note').each(function(){
    if($(this).html().trim()){
      data+=$(this).html().trim().replace(regex,'').trim()+jumplines;
    }
  });
});

var first=false;
$('form#amenities').find('textarea').each(function(){
  if($(this)[0].id.indexOf('bathroomDetailsField')>=0){
    first=true;
  }
  if(first&&$(this).val().trim()){
    data+=$(this).val().trim()+jumplines;
  }
});
console.info(data.trim());
})()