function checkUsername() {
    let usernameLabel = $("#usernameCheckUser");
    usernameLabel.hide();
    const username = $("#inputUsernameUser").val();

    fetch(`/user/api/register/${username}`)
        .then(response => response.json())
        .then(response => {
            if (!response['username']) {
                return;
            }
            usernameLabel.show();
        });
}

function checkEmail() {
    let emailLabel = $("#emailCheckUser");
    emailLabel.hide();
    const email = $("#inputEmailUser").val();

    fetch(`/user/api/registerWithEmail/${email}`)
        .then(response => response.json())
        .then(response => {
            if (!response['username']) {
                return;
            }
            emailLabel.show();
        });
}


