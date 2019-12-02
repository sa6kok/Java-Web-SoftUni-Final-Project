
function checkUsername () {
    let usernameLabel = $("#usernameCheckHost");
    usernameLabel.hide();
    const username = $("#inputUsernameHost").val();

    fetch(`/host/register/username/${username}`)
        .then(response => response.json())
        .then(response => {
            if(!response) {
                return;
            }
            usernameLabel.show();
        });


};

function checkEmail () {
    let emailLabel = $("#emailCheckHost");
    emailLabel.hide();
    const email = $("#inputEmailHost").val();

    fetch(`/host/register/email/${email}`)
        .then(response => response.json())
        .then(response => {
            if(!response) {
                return;
            }
            emailLabel.show();
        });


};


