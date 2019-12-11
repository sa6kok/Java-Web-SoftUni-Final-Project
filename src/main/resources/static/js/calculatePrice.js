$("#endDatePickInput").change(function () {

    let startDate = $("#startDatePickInput").val();
    let endDate = $("#endDatePickInput").val();
    let price = $("#selectedPropertyPrice").val();

    let correctStart = swap(startDate);
    let correctEnd = swap(endDate);
 

    let date1 = new Date(correctStart);
    let date2 = new Date(correctEnd);


    let Difference_In_Time = date2.getTime() - date1.getTime();

    let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
    let totalPrice = Difference_In_Days * Number(price);
    $("#totalAmount").val(totalPrice);

})
function swap(date) {
    let arrStart = date.split(".");
    return  arrStart[1] + "." + arrStart[0] + "." + arrStart[2]
}