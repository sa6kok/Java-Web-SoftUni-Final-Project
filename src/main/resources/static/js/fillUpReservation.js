$("#endDatePickInput").change(function () {

    calculateAndFillPrice();

})
function swapDateMonth(date) {
    let arrStart = date.split(".");
    return  arrStart[1] + "." + arrStart[0] + "." + arrStart[2]
}



$(window).on( "load", function () {
     let startDate = $("#startDatePickInput");
    let endDate = $("#endDatePickInput");
    let occupancy = $("#occupancyInput");

    if(startDate.val() === "") {
         return;
     }
    startDate.attr("readonly", "readonly");
    endDate.attr("readonly", "readonly");
    occupancy.attr("readonly", "readonly");

     calculateAndFillPrice();
 })

function calculateAndFillPrice() {
    let startDate = $("#startDatePickInput").val();
    let endDate = $("#endDatePickInput").val();
    let price = $("#selectedPropertyPrice").val();

    let correctStart = swapDateMonth(startDate);
    let correctEnd = swapDateMonth(endDate);


    let date1 = new Date(correctStart);
    let date2 = new Date(correctEnd);


    let Difference_In_Time = date2.getTime() - date1.getTime();

    let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
    let totalPrice = Difference_In_Days * Number(price);
    if(isNaN(totalPrice)) {
        totalPrice = 0;
    }
    $("#totalAmount").val(totalPrice);
}

