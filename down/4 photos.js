/*2*/
var list='';
$('#ulPhoto').find('a').each(function(){if($(this).attr('title'))list+='\n\n\n\n\n\n'+$(this).attr('title');});
console.info(list||'No existen descripciones a mostrar');