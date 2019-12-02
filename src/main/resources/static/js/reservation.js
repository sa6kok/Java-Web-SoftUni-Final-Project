function renderAllCities() {
    let country = $("#selectCountry").val();
    let url = `/reservation/create/${country}`;

    let selectCity ='<select class="browser-default custom-select" id="selectCity" name="city">\n                                    <option selected value="">Select City</option>';
    fetch(url).then(response => response.json())
            .then( cities => {
                if(cities.length === 0) {
                    selectCity += '</select><label for="selectCity" class="col-md-9 control-label text-danger">No Cities. Select another Country!</label>'
                } else {
                    cities.forEach(city => {
                        selectCity += `\n<option th:value="${city}" th:text="${city}">${city}</option>`;
                    });
                    selectCity += '</select><label for="selectCity" class="col-md-5 control-label">Select City</label>';
                }
                $("#citiesToFill").html(selectCity);
            });

}