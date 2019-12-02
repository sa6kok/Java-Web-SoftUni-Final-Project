$('#startDate').datetimepicker({
    language: 'en',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0,
    min: 0,
});

$("input[name='startDate']").change(function () {
    let current
        = $("input[name='startDate']").val();

    $('#endDate').datetimepicker({
        startDate: current,
        language: 'en',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        min: 0,
    });
});