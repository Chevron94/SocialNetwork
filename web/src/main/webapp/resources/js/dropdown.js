/**
 * Created by roman on 10/20/15.
 */
function updateContinentSelectOptions() {
    var url = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/registration/countriesByContinent';
    var id = $('#continent-select').select().val();
    $('#city-select').html('<option value="0" selected>Select city</option>');
    $('#city').val('0');
    $('#continent').val(id);
    $.getJSON(url,
        {searchId: id,
            ajax : 'true'},

        function(data) {
            var html = '<option value="0" selected>Select country</option>';
            var len = data.length;
            for (var i = 0; i< len; i++) {
                html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }
            $('#country-select').html(html);
            $('#country').val('0');
        }
    );
}


function updateCountrySelectOptions() {
    var url = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/registration/citiesByCountry';
    var id = $('#country-select').select().val();
    updateValues("country-select","country")
    $.getJSON(url,
        {searchId: id,
            ajax : 'true'},

        function(data) {
            var html = '<option value="0" selected>Select city</option>';
            var len = data.length;
            for (var i = 0; i< len; i++) {
                html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }
            $('#city-select').html(html);
            $('#city').val('0');
        }
    );
}

function updateValues(dropdown,input) {
    $('#'+input).val($('#'+dropdown).select().val());
}
