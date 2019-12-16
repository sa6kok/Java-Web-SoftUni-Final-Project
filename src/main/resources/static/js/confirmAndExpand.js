$('.confirmation').on('click', function () {
    return confirm('Are you sure?');
});


function showReviews(event) {

        let btnShow = $('#btnReviewShow');
    let field = $('#reviewsShow');
    let text = btnShow.html();
    if (text.trim().toUpperCase() === 'SHOW REVIEWS') {
        btnShow.html('HIDE REVIEWS');
        field.removeAttr("style").show()
    } else {
        btnShow.html('SHOW REVIEWS');
            field.hide();
    }
    event.preventDefault();
    return false;
}

