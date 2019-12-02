
function checkUsername () {
    let usernameLabel = $("#usernameCheckUser");
    usernameLabel.hide();
    const username = $("#inputUsernameUser").val();

    fetch(`/user/register/${username}`)
        .then(response => response.json())
        .then(response => {
            if(!response) {
                return;
            }
            usernameLabel.show();
        });


};

function checkEmail () {
    let emailLabel = $("#emailCheckUser");
    emailLabel.hide();
    const email = $("#inputEmailUser").val();

    fetch(`/user/registerWithEmail/${email}`)
        .then(response => response.json())
        .then(response => {
            if(!response) {
                return;
            }
            emailLabel.show();
        });


};


